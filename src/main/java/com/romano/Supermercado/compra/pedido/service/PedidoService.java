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
import com.romano.Supermercado.exception.service.AuthorizationException;
import com.romano.Supermercado.exception.service.ObjectNotFoundException;
import com.romano.Supermercado.produto.enums.StatusProduto;
import com.romano.Supermercado.produto.model.Produto;
import com.romano.Supermercado.produto.repository.ProdutoRepository;
import com.romano.Supermercado.security.UsuarioSecurity;
import com.romano.Supermercado.usuario.service.UsuarioService;


/**
 * 
 * @author Paulo Romano - [paulo-romano_133@hotmail.com]
 * Classe de Serviço responsável pelas regras de negócios de Pedidos
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
	 * Método resonsável por listar Pedidos conforme o Perfil do Cliente
	 * @param id : Long
	 * @return ResponseEntity<List<PedidoDTO>>
	 */
	public ResponseEntity<List<PedidoDTO>> listarTodosPedidos(Long id) {
		if (listarPedidosPorPermissaoCliente(id)) {
			return ResponseEntity.ok().body(PedidoDTO.converterParaListaPedidoDTO(pedidoRepository.findAll()));
		}
		return ResponseEntity.ok().body(PedidoDTO.converterParaListaPedidoDTO(pedidoRepository.findByCliente_Id(id)));
	}
	
	/**
	 * Método responsável por adicionar um produto ao pedido <br>
	 * Caso não exista um pedido em Aberto, será criado um novo
	 * @param idCliente : Long
	 * @param idProduto : Integer
	 * @param itemPedidoFORM : ItemPedidoFORM
	 * @return ResponseEntity<Void>
	 */
	public ResponseEntity<Void> adicionarProdutoAoPedido(Long idCliente, Integer idProduto, ItemPedidoFORM itemPedidoFORM) {
		usuarioTemPermissao(idCliente);
		produtoExiste(idProduto);
		
		Cliente cliente = clienteRepository.getOne(idCliente);
		Produto produto = produtoRepository.getOne(idProduto);
		
		Optional<Pedido> optionalPedido = cliente.getPedidos()
				.stream()
				.findAny()
				.filter(pedidos -> pedidos.getStatusPedido().equals(StatusPedido.ABERTO));
		
		if (optionalPedido.isPresent()) {
			adicionarItemAoPedido(optionalPedido.get(), produto, itemPedidoFORM);
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
		
		adicionarItemAoPedido(pedido, produto, itemPedidoFORM);
	}
	
	
	/**
	 * Método responsável por adicionar um Item ao Pedido
	 * @param pedido : Pedido
	 * @param produto : Produto
	 * @param itemPedidoFORM : ItemPedidoFORM
	 */
	private void adicionarItemAoPedido(Pedido pedido, Produto produto, ItemPedidoFORM itemPedidoFORM) {
		ItemPedido itemPedido = new ItemPedido(pedido, produto);
		itemPedidoFORM.converterParaItemPedido(itemPedido);
		
		verificaSeQuantidadeRequisitadaEValida(itemPedido.getQuantidade(), produto.getEstoque());
		
		pedido.adicionarItemPedido(itemPedido);
		itemPedidoRepository.save(itemPedido);
	}
	
	
	/**
	 * Método responsável por veriicar se a quantidade requisitada tem no estoque do Produto
	 * @param quantidade : Integer
	 * @param estoque : Integer
	 */
	private void verificaSeQuantidadeRequisitadaEValida(Integer quantidade, Integer estoque) {
		if (quantidade > estoque) {
			throw new IllegalArgumentException("Quantidade excedeu o limite de estoque!");
		}
	}
	
	
	/**
	 * Método responsável por verificar se o Usuário está logado para efetuar compras
	 * @param idCliente
	 */
	private void usuarioTemPermissao(Long idCliente) {
		UsuarioSecurity usuario = UsuarioService.authenticated();
		
		if (idCliente == null) {
			throw new IllegalArgumentException("É necessário estar logado para realizar compras!");
		}
		
		if (!idCliente.equals(usuario.getId())) {
			throw new AuthorizationException("Acesso negado!");
		}
	}
	
	
	/**
	 * Método responsável por verificar se o Usuário tem permissão para Listar
	 * os pedidos de todos os Clientes ou somente os seus respectivos Pedidos
	 * @param id : Long
	 * @return Boolean - Retorna True se o Usuário tiver permissão para listar os 
	 * pedidos de todos os Cliente. False se tiver permissão para listar somente os 
	 * seus respectivos Pedidos
	 */
	private Boolean listarPedidosPorPermissaoCliente(Long id) {
		UsuarioSecurity usuario = UsuarioService.authenticated();
		
		if (id == null || !usuario.hasRole(PerfilCliente.ADMIN) && !id.equals(usuario.getId())) {
			throw new AuthorizationException("Acesso negado!");
		}
		
		return (usuario.hasRole(PerfilCliente.ADMIN)) ? true : false;
	}
	
	
	/**
	 * Método responsável por verificar se o Produto existe e se ele está disponível para compra
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
}
