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
 * @author Paulo Romano - [paulo-romano_133@hotmail.com] <br>
 * Classe de Serviço responsável pelas regras de negócios de Pedido
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
	 * Método responsável por listar Pedidos de todos os Clientes ou somente um
	 * Cliente específico
	 * @return ResponseEntity - List de PedidoDTO
	 */
	public ResponseEntity<List<PedidoDTO>> listarTodosPedidos(Long idCliente) {		
		if (idCliente == null) {
			return ResponseEntity.ok().body(PedidoDTO.converterParaListaPedidoDTO(pedidoRepository.findByStatusPedido(StatusPedido.COMPRA_REALIZADA.getCodigo())));
		}
	
		return ResponseEntity.ok().body(PedidoDTO.converterParaListaPedidoDTO(pedidoRepository.findByCliente_IdAndStatusPedido(
				idCliente, StatusPedido.COMPRA_REALIZADA.getCodigo())));
	}
	
	
	/**
	 * Método responsável por listar todos os Pedidos de um Cliente
	 * @return ResponseEntity - List de PedidoDTO
	 */
	public ResponseEntity<List<PedidoDTO>> listarTodosPedidosDoCliente() {
		UsuarioSecurity usuario = VerificarUsuario.usuarioEValido();

		List<Pedido> pedidos = pedidoRepository.findByCliente_Id(usuario.getId());
		atualizarPrecoProdutosDoPedidoAberto(pedidos);
		
		return ResponseEntity.ok().body(PedidoDTO.converterParaListaPedidoDTO(pedidos));
	}
	
	
	/**
	 * Método responsável por atualizar os preço dos Itens do Pedido com status Aberto
	 * @param pedidos - List de Pedido
	 */
	private void atualizarPrecoProdutosDoPedidoAberto(List<Pedido> pedidos) {
		Optional<Pedido> optionalPedido = pedidos
				.stream()
				.findAny()
				.filter(pedido -> pedido.getStatusPedido().equals(StatusPedido.ABERTO));
		
		if (optionalPedido.isPresent()) {
			Double precoTotalAtualizado = 0.0D;
			
			for (ItemPedido item : optionalPedido.get().getItens()) {
				Produto produto = item.getProduto();
				
				Double precoItem = item.produtoTemDesconto(produto);
				item.setPreco(precoItem);
				itemPedidoRepository.flush();
				
				precoTotalAtualizado += precoItem * item.getQuantidade();
			}
			
			optionalPedido.get().setTotal(precoTotalAtualizado);
			pedidoRepository.flush();
		}
	}
	
	
	/**
	 * Método responsável por adicionar um Produto ao Pedido <br>
	 * Caso não exista um Pedido em Aberto, será criado um novo
	 * @param idProduto : Integer
	 * @param itemPedidoFORM : ItemPedidoFORM
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
	 * Método responsável por criar um novo Pedido e adicionar um Produto a ele
	 * @param cliente : Cliente
	 * @param produto : Produto
	 * @param itemPedidoFORM : ItemPedidoFORM
	 */
	private void adicionarProdutoAoNovoPedido(Cliente cliente, Produto produto, ItemPedidoFORM itemPedidoFORM) {
		Pedido pedido = new Pedido(cliente);
		pedidoRepository.save(pedido);

		adicionarProdutoAoPedidoExistente(pedido, produto, itemPedidoFORM);
	}
	
	
	/**
	 * Método responsável por adicionar um Produto ao Pedido existente
	 * @param pedido : Pedido
	 * @param produto : Produto
	 * @param itemPedidoFORM : ItemPedidoFORM
	 */
	private void adicionarProdutoAoPedidoExistente(Pedido pedido, Produto produto, ItemPedidoFORM itemPedidoFORM) {
		ItemPedido itemPedido = new ItemPedido(pedido, produto);
		itemPedidoFORM.converterParaItemPedido(itemPedido);
		
		verificaSeQuantidadeRequisitadaExcedeEstoque(itemPedido.getQuantidade(), produto.getEstoque());
		
		pedido.adicionarItemPedido(itemPedido);
		itemPedidoRepository.save(itemPedido);
	}
	
	
	/**
	 * Método responsável por veriicar se a quantidade requisitada tem no estoque do Produto
	 * @param quantidade : Integer
	 * @param estoque : Integer
	 */
	private void verificaSeQuantidadeRequisitadaExcedeEstoque(Integer quantidade, Integer estoque) {
		if (quantidade > estoque) {
			throw new IllegalArgumentException("Quantidade excedeu o limite de estoque!");
		}
	}
	
	
	/**
	 * Método responsável por remover um Produto de um Pedido
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
	 * Método responsável por verificar se o Pedido contém o Produto informado
	 * @param pedido : Pedido
	 * @param produto : Produto
	 * @return ItemPedido
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
	 * Método responsável por remover um Pedido se não possuir Itens
	 * @param pedido : Pedido
	 */
	private void removerPedidoSemItens(Pedido pedido) {
		if (pedido.getItens().size() - 1 == 0) {
			pedidoRepository.delete(pedido);
		}
	}
	
	
	/**
	 * Método responsável por verificar se o ID do Pedido pertence ao Cliente logado
	 * @param idCliente : Long
	 * @param pedido : Pedido
	 */
	private void verificarSePedidoPertenceAoClienteLogado(Long idCliente, Pedido pedido) {
		Cliente cliente = clienteRepository.getOne(idCliente);
		
		if (!cliente.getPedidos().contains(pedido)) {
			throw new IllegalArgumentException("Acesso Negado!");
		}
	}

	
	/**
	 * Método responsável por verificar se o Produto existe e se ele está disponível para compra
	 * @param idProduto : Integer
	 */
	private void produtoExisteEstaDisponivel(Integer idProduto) {
		Produto produto = produtoExiste(produtoRepository, idProduto);
		
		if (produto.getStatusProduto().equals(StatusProduto.ESGOTADO) || produto.getStatusProduto().equals(StatusProduto.INATIVO)) {
			throw new ObjectNotFoundException("Produto Esgotado ou Inativo!");
		}
	}
	
	
	/**
	 * Método responsável por verificar se o Produto existe
	 * @param produtoRepository : ProdutoRepository
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
	 * Método responsável por verificar se o Pedido existe e se pertence ao mesmo Cliente
	 * @param cliente : Cliente
	 * @param idPedido : Long
	 * @return Pedido
	 */
	private Pedido pedidoExiste(Long idPedido) {
		Optional<Pedido> pedido = pedidoRepository.findById(idPedido);
		
		if (pedido.isEmpty()) {
			throw new ObjectNotFoundException("Pedido não encontrado!");
		}
		
		return pedido.get();
	}
	
	
	/**
	 * Método responsável por alterar a quantidade de um ItemPedido em uma compra que ainda 
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
	 * Método responsável por finalizar um Pedido
	 * @param idPedido : Long
	 * @param idEndereco : Long
	 * @return ResponseEntity - Void 
	 */
	public ResponseEntity<Void> finalizarCompra(Long idPedido, Long idEndereco) {
		UsuarioSecurity usuario = VerificarUsuario.usuarioEValido();
		Pedido pedido = pedidoExiste(idPedido);
		
		verificarSePedidoPertenceAoClienteLogado(usuario.getId(), pedido);
		verificarSeStatusPedidoEstaAberto(pedido.getStatusPedido());
		Endereco endereco = verificarEnderecoPertenceAoCliente(usuario.getId(), idEndereco);
		
		pedido.finalizarPedido(endereco);
		
		return ResponseEntity.ok().build();
	}
	
	
	/**
	 * Método responsável por verificar se o StatusPedido está em Aberto
	 * @param statusPedido : StatusPedido
	 */
	private void verificarSeStatusPedidoEstaAberto(StatusPedido statusPedido) {
		if (!statusPedido.equals(StatusPedido.ABERTO)) {
			throw new IllegalArgumentException("O Pedido já foi finalizado!");
		}
	}
	
	
	/**
	 * Método responsável por verificar se o Endereco informado pertence ao Usuário logado
	 * @param idUsuario : Long
	 * @param idEndereco : Long
	 * @return Endereco
	 */
	private Endereco verificarEnderecoPertenceAoCliente(Long idUsuario, Long idEndereco) {
		Endereco endereco = enderecoRepository.getOne(idEndereco);
		
		if (!idUsuario.equals(endereco.getCliente().getId())) {
			throw new IllegalArgumentException("Acesso negado!");
		}
		
		return endereco;
	}
}
