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
import com.romano.Supermercado.utils.UsuarioValido;


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
	 * @return ResponseEntity<List<PedidoDTO>>
	 */
	public ResponseEntity<List<PedidoDTO>> listarTodosPedidos() {
		UsuarioSecurity usuario = UsuarioValido.retornaUsuarioValido();
		
		if (usuario.hasRole(PerfilCliente.ADMIN)) {
			return ResponseEntity.ok().body(PedidoDTO.converterParaListaPedidoDTO(pedidoRepository.findAll()));
		}
	
		return ResponseEntity.ok().body(PedidoDTO.converterParaListaPedidoDTO(pedidoRepository.findByCliente_Id(usuario.getId())));
	}
	
	/**
	 * Método responsável por adicionar um produto ao pedido <br>
	 * Caso não exista um pedido em Aberto, será criado um novo
	 * @param idProduto : Integer
	 * @param itemPedidoFORM : ItemPedidoFORM
	 * @return ResponseEntity<Void>
	 */
	public ResponseEntity<Void> adicionarProdutoAoPedido(Integer idProduto, ItemPedidoFORM itemPedidoFORM) {
		UsuarioSecurity usuario = UsuarioValido.retornaUsuarioValido();
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
	 * @return ResponseEntity<Void>
	 */
	public ResponseEntity<Void> removerProdutoDePedido(Long idPedido, Integer idProduto) {
		UsuarioSecurity usuario = UsuarioValido.retornaUsuarioValido();
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
	 * Método responsável por verificar se o Pedido contém o Item que será removido
	 * @param pedido : Pedido
	 * @param produto : Produto
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
	 * Método responsável por remover um Pedido se não possuir Itens
	 * @param pedido : Pedido
	 */
	private void removerPedidoSemItens(Pedido pedido) {
		if (pedido.getItens().size() - 1 == 0) {
			pedidoRepository.delete(pedido);
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
	
	
	/**
	 * Método responsável por verificar se o Pedido existe e se pertence ao mesmo Cliente
	 * @param cliente : Cliente
	 * @param idPedido : Long
	 * @return Pedido
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
