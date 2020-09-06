package com.romano.Supermercado.cliente.compra.itemPedido.model;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.romano.Supermercado.cliente.compra.itemPedidoPK.model.ItemPedidoPK;
import com.romano.Supermercado.cliente.compra.pedido.model.Pedido;
import com.romano.Supermercado.produto.model.Produto;


/**
 * 
 * @author Paulo Romano - [paulo-romano_133@hotmail.com]
 * Classe responsável pelo Item de Pedido
 */
@Entity
@Table(name = "item_pedido")
public class ItemPedido {

	@JsonIgnore
	@EmbeddedId
	private ItemPedidoPK id = new ItemPedidoPK();
	
	private Double preco;
	private Integer quantidade;
	
	
	public ItemPedido() {

	}
	
	public ItemPedido(Pedido pedido, Produto produto, Double preco, Integer quantidade) {
		id.setPedido(pedido);
		id.setProduto(produto);
		this.preco = preco;
		this.quantidade = quantidade;
	}

	
	public ItemPedidoPK getId() {
		return id;
	}

	public Double getPreco() {
		return preco;
	}

	public Integer getQuantidade() {
		return quantidade;
	}

	public void setPreco(Double preco) {
		this.preco = preco;
	}

	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}
	
	
}
