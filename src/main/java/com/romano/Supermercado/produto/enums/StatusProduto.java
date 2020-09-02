package com.romano.Supermercado.produto.enums;


/**
 * 
 * @author Paulo Romano - [paulo-romano_133@hotmail.com]
 * Enum responsável por deferir um status para o Produto
 */
public enum StatusProduto {
	ATIVO(1, "Ativo"),
	ESTOQUE_BAIXO(2, "Estoque Baixo"),
	ESGOTADO(3, "Esgotado");
	
	private Integer codigo;
	private String descricao;
	
	
	private StatusProduto(Integer codigo, String descricao) {
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
	 * Método responsável por converter um código para o tipo de Status do Produto
	 * @param codigo : Integer
	 * @return StatusProduto - Enum
	 */
	public static StatusProduto converterParaEnum(Integer codigo) {
		if (codigo == null) {
			return null;
		}
		
		for (StatusProduto statusProdutoAtual : StatusProduto.values()) {
			if (codigo.equals(statusProdutoAtual.getCodigo())) {
				return statusProdutoAtual;
			}
		}
		
		throw new IllegalArgumentException("Código (" + codigo + ") de Status do Produto inválido!");
	}
}
