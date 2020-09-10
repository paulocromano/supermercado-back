package com.romano.Supermercado.compra.itemPedido.model;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.romano.Supermercado.compra.itemPedidoPK.model.ItemPedidoPK;
import com.romano.Supermercado.compra.pedido.model.Pedido;
import com.romano.Supermercado.produto.model.Produto;


/**
 * 
 * @author Paulo Romano - [paulo-romano_133@hotmail.com]
 * Classe responsável pelo Item de Pedido
 */
@Entity
@Table(name = "item_pedido")
public class ItemPedido implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@JsonIgnore
	@EmbeddedId
	private ItemPedidoPK id = new ItemPedidoPK();
	
	private Double preco;
	private Integer quantidade;
	
	
	public ItemPedido() {

	}
	
	
	/**
	 * Construtor
	 * @param pedido : Pedido
	 * @param produto : Produto
	 */
	public ItemPedido(Pedido pedido, Produto produto) {
		id.setPedido(pedido);
		id.setProduto(produto);
		preco = produtoTemDesconto(produto);
	}
	
	
	@JsonIgnore
	public Pedido getPedido() {
		return id.getPedido();
	}
	

	public Produto getProduto() {
		return id.getProduto();
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

	public void setId(ItemPedidoPK id) {
		this.id = id;
	}
	
	public void setPreco(Double preco) {
		this.preco = preco;
	}

	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}
	
	
	/**
	 * Método para verificar se o Produto tem desconto
	 * @param produto : Produto
	 * @return Double - Preço atualizado caso o Produto tenha desconto. Preço original
	 * caso não tenha
	 */
	private Double produtoTemDesconto(Produto produto) {
		return (produto.getDesconto() != 0) ? produto.getPreco() - (produto.getPreco() * produto.getDesconto() / 100D) : produto.getPreco();
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ItemPedido other = (ItemPedido) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
