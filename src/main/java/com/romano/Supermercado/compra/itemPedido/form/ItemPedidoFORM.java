package com.romano.Supermercado.compra.itemPedido.form;

import javax.validation.constraints.NotNull;

import com.romano.Supermercado.compra.itemPedido.model.ItemPedido;

/**
 * 
 * @author Paulo Romano - [paulo-romano_133@hotmail.com] <br>
 * Classe para receber os dados do Produto para adicionar ao Pedido
 */
public class ItemPedidoFORM {
	
	@NotNull(message = "Campo Quantidade não informado!")
	private Integer quantidade;


	public Integer getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}
	
	
	/**
	 * Método responsável por converter os dados de ItemPedidoFORM para ItemPedido
	 * @param itemPedido : ItemPedido
	 */
	public void converterParaItemPedido(ItemPedido itemPedido) {
		itemPedido.setQuantidade(quantidade);
	}
}
