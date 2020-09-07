package com.romano.Supermercado.cliente.compra.pedido.resource;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.romano.Supermercado.cliente.compra.itemPedido.form.ItemPedidoFORM;
import com.romano.Supermercado.cliente.compra.pedido.service.PedidoService;


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
	 * Método responsável por chamar o serviço de adicionar um Produto ao Pedido
	 * @param idCliente : Long
	 * @param idProduto : Integer
	 * @param itemPedidoFORM : ItemPedidoFORM
	 * @return ResponseEntity<Void> - Retorna a resposta da requisição
	 */
	@Transactional
	@PutMapping("/{idCliente}/{idProduto}")
	public ResponseEntity<Void> adicionarProdutoAoPedido(@PathVariable Long idCliente, @PathVariable Integer idProduto, @RequestBody ItemPedidoFORM itemPedidoFORM) {
		return pedidoService.adicionarProdutoAoPedido(idCliente, idProduto, itemPedidoFORM);
	}
	
}
