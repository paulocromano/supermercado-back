package com.romano.Supermercado.cliente.compra.itemPedidoPK.model;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.romano.Supermercado.cliente.compra.pedido.model.Pedido;
import com.romano.Supermercado.produto.model.Produto;


/**
 * 
 * @author Paulo Romano - [paulo-romano_133@hotmail.com]
 * Classe responsável por armazenar as chaves primárias das Tabelas
 * de Pedido e Produto
 */
@Embeddable
public class ItemPedidoPK implements Serializable {

	private static final long serialVersionUID = 1L;

	@ManyToOne
	@JoinColumn(name="pedido_id")
	private Pedido pedido;
	
	@ManyToOne
	@JoinColumn(name="produto_id")
	private Produto produto;

	
	public Pedido getPedido() {
		return pedido;
	}

	public Produto getProduto() {
		return produto;
	}

	public void setPedido(Pedido pedido) {
		this.pedido = pedido;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}
}
