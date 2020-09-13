package com.romano.Supermercado.compra.itemPedido.form;

import javax.validation.constraints.NotNull;

import com.romano.Supermercado.compra.itemPedido.model.ItemPedido;
import com.romano.Supermercado.compra.pedido.model.Pedido;

/**
 * 
 * @author Paulo Romano - [paulo-romano_133@hotmail.com]
 * Classe para receber os dados do Produto para adicionar ao {@link Pedido}
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
	 * Método responsável por converter os dados de {@link ItemPedidoFORM} para {@link ItemPedido}
	 * @param itemPedido : {@link ItemPedido}
	 */
	public void converterParaItemPedido(ItemPedido itemPedido) {
		itemPedido.setQuantidade(quantidade);
	}
}
