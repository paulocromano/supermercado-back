package com.romano.Supermercado.compra.pedido.resource;

import java.util.List;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
 * @author Paulo Romano - [paulo-romano_133@hotmail.com] <br>
 * Classe responsável por chamar os métodos do PedidoService
 */
@RestController
@RequestMapping("/pedido")
public class PedidoResource {

	@Autowired
	private PedidoService pedidoService;
	
	
	/**
	 * Método responsável por chamar o serviço de listar Pedidos de todos os Clientes ou somente um
	 * Cliente específico
	 * @return ResponseEntity - List de PedidoDTO Retorna a resposta da requisição
	 */
	@PreAuthorize("hasAnyRole('ADMIN')")
	@GetMapping("/listar-todos/{idCliente}")
	public ResponseEntity<List<PedidoDTO>> listarTodosPedidos(@PathVariable Long idCliente) {
		return pedidoService.listarTodosPedidos(idCliente);
	}
	
	
	/**
	 * Método responsável por chamar o serviço de listar todos os Pedidos de um Cliente
	 * @return ResponseEntity - List de PedidoDTO Retorna a resposta da requisição
	 */
	@GetMapping("/cliente-listar-todos")
	public ResponseEntity<List<PedidoDTO>> listarTodosPedidosDoCliente() {
		return pedidoService.listarTodosPedidosDoCliente();
	}
	
	
	/**
	 * Método responsável por chamar o serviço de adicionar um Produto ao Pedido
	 * @param idProduto : Integer
	 * @param itemPedidoFORM : ItemPedidoFORM
	 * @return ResponseEntity - Void (Retorna a resposta da requisição)
	 */
	@Transactional
	@PutMapping("/{idProduto}")
	public ResponseEntity<Void> adicionarProdutoAoPedido(@PathVariable Integer idProduto, @RequestBody @Valid ItemPedidoFORM itemPedidoFORM) {
		
		return pedidoService.adicionarProdutoAoPedido(idProduto, itemPedidoFORM);
	}
	
	
	/**
	 * Método responsável por chamar o serviço de remoção de Produto de um Pedido
	 * @param idPedido : Long
	 * @param idProduto : Integer
	 * @return ResponseEntity - Void (Retorna a resposta da requisição)
	 */
	@Transactional
	@DeleteMapping("/{idPedido}/{idProduto}")
	public ResponseEntity<Void> removerProdutoDePedido(@PathVariable Long idPedido, @PathVariable Integer idProduto) {
		return pedidoService.removerProdutoDePedido(idPedido, idProduto);
	}
	
	
	/**
	 * Método responsável por chamar o serviço de alterar a quantidade de um ItemPedido em uma compra que ainda 
	 * não foi finalizada 
	 * @param idPedido : Long
	 * @param idProduto : Integer
	 * @param quantidade : Integer
	 * @return ResponseEntity - Void (Retorna a resposta da requisição)
	 */
	@Transactional
	@PutMapping("/{idPedido}/{idProduto}/alterar-quantidade-item={quantidade}")
	public ResponseEntity<Void> alterarQuantidadeItemPedido(@PathVariable Long idPedido, @PathVariable Integer idProduto, @PathVariable Integer quantidade) {
		return pedidoService.alterarQuantidadeItemPedido(idPedido, idProduto, quantidade);
	}
	
	
	/**
	 * Método responsável por chamar o serviço de finalizar um Pedido
	 * @param idPedido : Long
	 * @param idEndereco : Long
	 * @return ResponseEntity - Void (Retorna a resposta da requisição)
	 */
	@Transactional
	@PutMapping("finalizar-compra/{idPedido}/{idEndereco}")
	public ResponseEntity<Void> finalizarCompra(@PathVariable Long idPedido, @PathVariable Long idEndereco) {
		return pedidoService.finalizarCompra(idPedido, idEndereco);
	}
}
