package com.romano.Supermercado.compra.pedido.enums;

/**
 * 
 * @author Paulo Romano - [paulo-romano_133@hotmail.com]
 * Enum responsável por deferir o Status do {@link Pedido}
 */
public enum StatusPedido {

	ABERTO(1, "Aberto"),
	COMPRA_REALIZADA(2, "Compra Realizada"),
	PAGAMENTO_CONFIRMADO(3, "Pagamento Confirmado"),
	PREPARACAO_TRANSPORTE(4, "Preparação para Transporte"),
	ENVIADO(5, "Enviado"),
	ENTREGUE(6, "Entregue");
	
	
	private Integer codigo;
	private String descricao;
	
	
	private StatusPedido(Integer codigo, String descricao) {
		this.codigo = codigo;
		this.descricao = descricao;
	}


	public Integer getCodigo() {
		return codigo;
	}

	public String getDescricao() {
		return descricao;
	}
	
	
	/**
	 * Método responsável por converter o código do Status do Pedido para o Enum de
	 * {@link StatusPedido}
	 * @param codigo : Integer
	 * @return {@link StatusPedido} - Enum
	 */
	public static StatusPedido converterParaEnum(Integer codigo) {
		if (codigo == null) {
			throw new NullPointerException("O Código de Status do Pedido não pode estar nulo!");
		}
		
		for (StatusPedido statusPedidoAtual : StatusPedido.values()) {
			if (codigo.equals(statusPedidoAtual.getCodigo())) {
				return statusPedidoAtual;
			}
		}
		
		throw new IllegalArgumentException("Código (" + codigo + ") inválido!");
	}
}
