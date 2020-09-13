package com.romano.Supermercado.compra.pedido.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.romano.Supermercado.cliente.enums.PerfilCliente;
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
	
	
	/**
	 * Método responsável por listar Pedidos conforme o {@link PerfilCliente}
	 * @return ResponseEntity - List {@link PedidoDTO}
	 */
	public ResponseEntity<List<PedidoDTO>> listarTodosPedidos() {
		UsuarioSecurity usuario = VerificarUsuario.usuarioEValido();
		
		if (usuario.hasRole(PerfilCliente.ADMIN)) {
			return ResponseEntity.ok().body(PedidoDTO.converterParaListaPedidoDTO(pedidoRepository.findAll()));
		}
	
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
		produtoExiste(idProduto);
		
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
		produtoExiste(idProduto);
		
		Cliente cliente = clienteRepository.getOne(usuario.getId());
		Pedido pedido = pedidoExiste(cliente, idPedido);
		Produto produto = produtoRepository.getOne(idProduto);
		
		pedidoContemItemPedido(pedido, produto);
		pedido.removerItemPedido(produto, itemPedidoRepository);
		removerPedidoSemItens(pedido);
		
		return ResponseEntity.ok().build();
	}
	
	
	/**
	 * Método responsável por verificar se o {@link Pedido} contém o Item que será removido
	 * @param pedido : {@link Pedido}
	 * @param produto : {@link Produto}
	 */
	private void pedidoContemItemPedido(Pedido pedido, Produto produto) {
		Optional<ItemPedido> itemPedido = pedido
				.getItens()
				.stream()
				.findAny()
				.filter(item -> item.getProduto().equals(produto)); 
		
		if (itemPedido.isEmpty()) {
			throw new ObjectNotFoundException("Produto informado não existe no Pedido!");
		}
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
	 * Método responsável por verificar se o {@link Produto} existe e se ele está disponível para compra
	 * @param idProduto : Integer
	 */
	private void produtoExiste(Integer idProduto) {
		Optional<Produto> produto = produtoRepository.findById(idProduto);
		
		if (produto.isEmpty()) {
			throw new ObjectNotFoundException("Produto não encontrado!");
		}
		
		if (produto.get().getStatusProduto().equals(StatusProduto.ESGOTADO) || produto.get().getStatusProduto().equals(StatusProduto.INATIVO)) {
			throw new ObjectNotFoundException("Produto Esgotado ou Inativo!");
		}
	}
	
	
	/**
	 * Método responsável por verificar se o {@link Pedido} existe e se pertence ao mesmo {@link Cliente}
	 * @param cliente : {@link Cliente}
	 * @param idPedido : Long
	 * @return {@link Pedido}
	 */
	private Pedido pedidoExiste(Cliente cliente, Long idPedido) {
		Optional<Pedido> pedido = pedidoRepository.findById(idPedido);
		
		if (pedido.isEmpty()) {
			throw new ObjectNotFoundException("Pedido não encontrado!");
		}
		
		if (!pedido.get().getCliente().equals(cliente)) {
			throw new IllegalArgumentException("Acesso Negado!");
		}
		
		return pedido.get();
	}
}
