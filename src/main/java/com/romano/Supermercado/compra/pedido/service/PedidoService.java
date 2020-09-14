package com.romano.Supermercado.compra.pedido.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.romano.Supermercado.cliente.model.Cliente;
import com.romano.Supermercado.cliente.repository.ClienteRepository;
import com.romano.Supermercado.compra.itemPedido.form.ItemPedidoFORM;
import com.romano.Supermercado.compra.itemPedido.model.ItemPedido;
import com.romano.Supermercado.compra.itemPedido.repository.ItemPedidoRepository;
import com.romano.Supermercado.compra.pedido.dto.PedidoDTO;
import com.romano.Supermercado.compra.pedido.enums.StatusPedido;
import com.romano.Supermercado.compra.pedido.model.Pedido;
import com.romano.Supermercado.compra.pedido.repository.PedidoRepository;
import com.romano.Supermercado.exception.service.ObjectNotFoundException;
import com.romano.Supermercado.localidade.endereco.model.Endereco;
import com.romano.Supermercado.localidade.endereco.repository.EnderecoRepository;
import com.romano.Supermercado.produto.enums.StatusProduto;
import com.romano.Supermercado.produto.model.Produto;
import com.romano.Supermercado.produto.repository.ProdutoRepository;
import com.romano.Supermercado.security.UsuarioSecurity;
import com.romano.Supermercado.utils.VerificarUsuario;


/**
 * 
 * @author Paulo Romano - [paulo-romano_133@hotmail.com]
 * Classe de Serviço responsável pelas regras de negócios de {@link Pedido}
 */
@Service
public class PedidoService {
	
	@Autowired
	private PedidoRepository pedidoRepository;

	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private ProdutoRepository produtoRepository;
	
	@Autowired
	private ItemPedidoRepository itemPedidoRepository;
	
	@Autowired
	private EnderecoRepository enderecoRepository;
	
	
	/**
	 * Método responsável por listar {@link Pedido}s de todos os {@link Cliente}s ou somente um
	 * Cliente específico
	 * @return ResponseEntity - List {@link PedidoDTO}
	 */
	public ResponseEntity<List<PedidoDTO>> listarTodosPedidos(Long idCliente) {		
		if (idCliente == null) {
			return ResponseEntity.ok().body(PedidoDTO.converterParaListaPedidoDTO(pedidoRepository.findAll()));
		}
	
		return ResponseEntity.ok().body(PedidoDTO.converterParaListaPedidoDTO(pedidoRepository.findByCliente_Id(idCliente)));
	}
	
	
	/**
	 * Método responsável por listar todos os {@link Pedido}s de um {@link Cliente}
	 * @return ResponseEntity - List {@link PedidoDTO}
	 */
	public ResponseEntity<List<PedidoDTO>> listarTodosPedidosDoCliente() {
		UsuarioSecurity usuario = VerificarUsuario.usuarioEValido();
		
		return ResponseEntity.ok().body(PedidoDTO.converterParaListaPedidoDTO(pedidoRepository.findByCliente_Id(usuario.getId())));
	}
	
	
	/**
	 * Método responsável por adicionar um {@link Produto} ao {@link Pedido} <br>
	 * Caso não exista um Pedido em Aberto, será criado um novo
	 * @param idProduto : Integer
	 * @param itemPedidoFORM : {@link ItemPedidoFORM}
	 * @return ResponseEntity - Void 
	 */
	public ResponseEntity<Void> adicionarProdutoAoPedido(Integer idProduto, ItemPedidoFORM itemPedidoFORM) {
		UsuarioSecurity usuario = VerificarUsuario.usuarioEValido();
		produtoExisteEstaDisponivel(idProduto);
		
		Cliente cliente = clienteRepository.getOne(usuario.getId());
		Produto produto = produtoRepository.getOne(idProduto);
		
		Optional<Pedido> optionalPedido = cliente.getPedidos()
				.stream()
				.findAny()
				.filter(pedidos -> pedidos.getStatusPedido().equals(StatusPedido.ABERTO));
		
		if (optionalPedido.isPresent()) {
			adicionarProdutoAoPedidoExistente(optionalPedido.get(), produto, itemPedidoFORM);
		}
		else {
			adicionarProdutoAoNovoPedido(cliente, produto, itemPedidoFORM);
		}
		
		return ResponseEntity.ok().build();
	}
	
	
	/**
	 * Método responsável por criar um novo {@link Pedido} e adicionar um {@link Produto} a ele
	 * @param cliente : {@link Cliente}
	 * @param produto : {@link Produto}
	 * @param itemPedidoFORM : {@link ItemPedidoFORM}
	 */
	private void adicionarProdutoAoNovoPedido(Cliente cliente, Produto produto, ItemPedidoFORM itemPedidoFORM) {
		Pedido pedido = new Pedido(cliente);
		pedidoRepository.save(pedido);

		adicionarProdutoAoPedidoExistente(pedido, produto, itemPedidoFORM);
	}
	
	
	/**
	 * Método responsável por adicionar um {@link Produto} ao {@link Pedido} existente
	 * @param pedido : {@link Pedido}
	 * @param produto : {@link Produto}
	 * @param itemPedidoFORM : {@link ItemPedidoFORM}
	 */
	private void adicionarProdutoAoPedidoExistente(Pedido pedido, Produto produto, ItemPedidoFORM itemPedidoFORM) {
		ItemPedido itemPedido = new ItemPedido(pedido, produto);
		itemPedidoFORM.converterParaItemPedido(itemPedido);
		
		verificaSeQuantidadeRequisitadaExcedeEstoque(itemPedido.getQuantidade(), produto.getEstoque());
		
		pedido.adicionarItemPedido(itemPedido);
		itemPedidoRepository.save(itemPedido);
	}
	
	
	/**
	 * Método responsável por veriicar se a quantidade requisitada tem no estoque do {@link Produto}
	 * @param quantidade : Integer
	 * @param estoque : Integer
	 */
	private void verificaSeQuantidadeRequisitadaExcedeEstoque(Integer quantidade, Integer estoque) {
		if (quantidade > estoque) {
			throw new IllegalArgumentException("Quantidade excedeu o limite de estoque!");
		}
	}
	
	
	/**
	 * Método responsável por remover um {@link Produto} de um {@link Pedido}
	 * @param idPedido : Long
	 * @param idProduto : Integer
	 * @return ResponseEntity - Void 
	 */
	public ResponseEntity<Void> removerProdutoDePedido(Long idPedido, Integer idProduto) {
		UsuarioSecurity usuario = VerificarUsuario.usuarioEValido();
		produtoExisteEstaDisponivel(idProduto);
		
		Pedido pedido = pedidoExiste(idPedido);
		verificarSePedidoPertenceAoClienteLogado(usuario.getId(), pedido);
		Produto produto = produtoRepository.getOne(idProduto);
		
		pedidoContemItemPedido(pedido, produto);
		pedido.removerItemPedido(produto, itemPedidoRepository);
		removerPedidoSemItens(pedido);
		
		return ResponseEntity.ok().build();
	}
	
	
	/**
	 * Método responsável por verificar se o {@link Pedido} contém o Produto informado
	 * @param pedido : {@link Pedido}
	 * @param produto : {@link Produto}
	 * @return {@link ItemPedido}
	 */
	private ItemPedido pedidoContemItemPedido(Pedido pedido, Produto produto) {
		Optional<ItemPedido> itemPedido = pedido
			.getItens()
			.stream()
			.findAny()
			.filter(item -> item.getProduto().equals(produto));
		
		if (itemPedido.isEmpty()) {
			throw new ObjectNotFoundException("Produto informado não existe no Pedido!");
		}
		
		return itemPedido.get();
	}
	
	
	/**
	 * Método responsável por remover um {@link Pedido} se não possuir Itens
	 * @param pedido : {@link Pedido}
	 */
	private void removerPedidoSemItens(Pedido pedido) {
		if (pedido.getItens().size() - 1 == 0) {
			pedidoRepository.delete(pedido);
		}
	}
	
	
	/**
	 * Método responsável por verificar se o ID do {@link Pedido} pertence ao {@link Cliente} logado
	 * @param idCliente : Long
	 * @param pedido : {@link Pedido}
	 */
	private void verificarSePedidoPertenceAoClienteLogado(Long idCliente, Pedido pedido) {
		Cliente cliente = clienteRepository.getOne(idCliente);
		
		if (!cliente.getPedidos().contains(pedido)) {
			throw new IllegalArgumentException("Acesso Negado!");
		}
	}

	
	/**
	 * Método responsável por verificar se o {@link Produto} existe e se ele está disponível para compra
	 * @param idProduto : Integer
	 */
	private void produtoExisteEstaDisponivel(Integer idProduto) {
		Produto produto = produtoExiste(produtoRepository, idProduto);
		
		if (produto.getStatusProduto().equals(StatusProduto.ESGOTADO) || produto.getStatusProduto().equals(StatusProduto.INATIVO)) {
			throw new ObjectNotFoundException("Produto Esgotado ou Inativo!");
		}
	}
	
	
	/**
	 * Método responsável por verificar se o {@link Produto} existe
	 * @param produtoRepository : {@link ProdutoRepository}
	 * @param id : Integer
	 */
	private Produto produtoExiste(ProdutoRepository produtoRepository, Integer id) {
		Optional<Produto> produto = produtoRepository.findById(id);
		
		if (produto.isEmpty()) {
			throw new ObjectNotFoundException("Produto não encontrado!");
		}
		
		return produto.get();
	}
	
	
	/**
	 * Método responsável por verificar se o {@link Pedido} existe e se pertence ao mesmo {@link Cliente}
	 * @param cliente : {@link Cliente}
	 * @param idPedido : Long
	 * @return {@link Pedido}
	 */
	private Pedido pedidoExiste(Long idPedido) {
		Optional<Pedido> pedido = pedidoRepository.findById(idPedido);
		
		if (pedido.isEmpty()) {
			throw new ObjectNotFoundException("Pedido não encontrado!");
		}
		
		return pedido.get();
	}
	
	
	/**
	 * Método responsável por alterar a quantidade de um {@link ItemPedido} em uma compra que ainda 
	 * não foi finalizada 
	 * @param idPedido : Long
	 * @param idProduto : Integer
	 * @param quantidade : Integer
	 * @return ResponseEntity - Void 
	 */
	public ResponseEntity<Void> alterarQuantidadeItemPedido(Long idPedido, Integer idProduto, Integer quantidade) {
		UsuarioSecurity usuario = VerificarUsuario.usuarioEValido();
		
		if (quantidade == null) {
			throw new IllegalArgumentException("Quantidade não pode estar nula!");
		}
		
		Pedido pedido = pedidoExiste(idPedido);
		verificarSePedidoPertenceAoClienteLogado(usuario.getId(), pedido);
		
		Produto produto = produtoExiste(produtoRepository, idProduto);
		ItemPedido itemPedido = pedidoContemItemPedido(pedido, produto);
		
		pedido.atualizarPrecoTotalDeProduto(produto, itemPedido, quantidade);
		
		return ResponseEntity.ok().build();
	}
	
	
	/**
	 * Método responsável por finalizar um {@link Pedido}
	 * @param idPedido : Long
	 * @param idEndereco : Long
	 * @return ResponseEntity - Void 
	 */
	public ResponseEntity<Void> finalizarCompra(Long idPedido, Long idEndereco) {
		UsuarioSecurity usuario = VerificarUsuario.usuarioEValido();
		Pedido pedido = pedidoExiste(idPedido);
		
		verificarSePedidoPertenceAoClienteLogado(usuario.getId(), pedido);
		
		if (!pedido.getStatusPedido().equals(StatusPedido.ABERTO)) {
			throw new IllegalArgumentException("O Pedido já foi finalizado!");
		}
		
		Endereco endereco = enderecoRepository.getOne(idEndereco);
		
		if (!usuario.getId().equals(endereco.getCliente().getId())) {
			throw new IllegalArgumentException("Acesso negado!");
		}
		
		pedido.finalizarPedido(endereco, produtoRepository);
		
		return ResponseEntity.ok().build();
	}
}
