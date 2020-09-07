package com.romano.Supermercado.cliente.compra.pedido.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.romano.Supermercado.cliente.compra.itemPedido.form.ItemPedidoFORM;
import com.romano.Supermercado.cliente.compra.itemPedido.model.ItemPedido;
import com.romano.Supermercado.cliente.compra.itemPedido.repository.ItemPedidoRepository;
import com.romano.Supermercado.cliente.compra.pedido.enums.StatusPedido;
import com.romano.Supermercado.cliente.compra.pedido.model.Pedido;
import com.romano.Supermercado.cliente.compra.pedido.repository.PedidoRepository;
import com.romano.Supermercado.cliente.model.Cliente;
import com.romano.Supermercado.cliente.repository.ClienteRepository;
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
			Pedido pedido = new Pedido(cliente);
			pedidoRepository.save(pedido);
			
			adicionarItemAoPedido(pedido, produto, itemPedidoFORM);
			
			System.out.println(pedido.getId());
		}
		
		return ResponseEntity.ok().build();
	}
	
	
	/**
	 * Método responsável por adicionar um Item ao Pedido
	 * @param pedido : Pedido
	 * @param produto : Produto
	 * @param itemPedidoFORM : ItemPedidoFORM
	 */
	private void adicionarItemAoPedido(Pedido pedido, Produto produto, ItemPedidoFORM itemPedidoFORM) {
		System.out.println(pedido.getId());
		System.out.println(produto.getId());
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
