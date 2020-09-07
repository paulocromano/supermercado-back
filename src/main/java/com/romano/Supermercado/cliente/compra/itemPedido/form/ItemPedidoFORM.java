package com.romano.Supermercado.cliente.compra.itemPedido.form;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.romano.Supermercado.cliente.compra.itemPedido.model.ItemPedido;

/**
 * 
 * @author Paulo Romano - [paulo-romano_133@hotmail.com]
 * Classe para receber os dados do Produto para adicionar ao Pedido
 */
public class ItemPedidoFORM {
	
	@NotNull(message = "Campo Preço não informado!")
	@NotEmpty(message = "O campo Preço não pode estar vazio!")
	private Double preco;
	
	@NotNull(message = "Campo Quantidade não informado!")
	@NotEmpty(message = "O campo Quantidade não pode estar vazio!")
	private Integer quantidade;


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
	
	
	public void converterParaItemPedido(ItemPedido itemPedido) {
		itemPedido.setPreco(preco);
		itemPedido.setQuantidade(quantidade);
	}
}
