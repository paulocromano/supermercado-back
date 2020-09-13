package com.romano.Supermercado.compra.pedido.resource;

import java.util.List;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.romano.Supermercado.compra.itemPedido.form.ItemPedidoFORM;
import com.romano.Supermercado.compra.pedido.dto.PedidoDTO;
import com.romano.Supermercado.compra.pedido.service.PedidoService;


/**
 * 
 * @author Paulo Romano - [paulo-romano_133@hotmail.com]
 * Classe responsável por chamar os métodos do Service de Pedido
 */
@RestController
@RequestMapping("/pedido")
public class PedidoResource {

	@Autowired
	private PedidoService pedidoService;
	
	
	/**
	 * Método responsável por chamar o serviço de listar Pedidos conforme Perfil do Cliente
	 * @return ResponseEntity<List<PedidoDTO>> - Retorna a resposta da requisição
	 */
	@GetMapping("/listar-todos{id}")
	public ResponseEntity<List<PedidoDTO>> listarTodosPedidos() {
		return pedidoService.listarTodosPedidos();
	}
	
	/**
	 * Método responsável por chamar o serviço de adicionar um Produto ao Pedido
	 * @param idProduto : Integer
	 * @param itemPedidoFORM : ItemPedidoFORM
	 * @return ResponseEntity<Void> - Retorna a resposta da requisição
	 */
	@Transactional
	@PutMapping("/{idProduto}")
	public ResponseEntity<Void> adicionarProdutoAoPedido(@PathVariable Integer idProduto, 
			@RequestBody @Valid ItemPedidoFORM itemPedidoFORM) {
		
		return pedidoService.adicionarProdutoAoPedido(idProduto, itemPedidoFORM);
	}
	
	
	/**
	 * Método responsável por chamar o serviço de remoção de Produto de um Pedido
	 * @param idPedido : Long
	 * @param idProduto : Integer
	 * @return ResponseEntity<Void> - Retorna a resposta da requisição
	 */
	@Transactional
	@DeleteMapping("/{idPedido}/{idProduto}")
	public ResponseEntity<Void> removerProdutoDePedido(@PathVariable Long idPedido, @PathVariable Integer idProduto) {
		return pedidoService.removerProdutoDePedido(idPedido, idProduto);
	}
}
