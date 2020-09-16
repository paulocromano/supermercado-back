package com.romano.Supermercado.produto.enums;


/**
 * 
 * @author Paulo Romano - [paulo-romano_133@hotmail.com] <br>
 * Enum responsável por deferir um status para o Produto
 */
public enum StatusProduto {
	ATIVO(1, "Ativo"),
	INATIVO(2, "Inativo"),
	ESTOQUE_BAIXO(3, "Estoque Baixo"),
	ESGOTADO(4, "Esgotado");
	
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
	 * Método responsável por converter um código para o tipo StatusProduto
	 * @param codigo : Integer
	 * @return StatusProduto - Enum
	 */
	public static StatusProduto converterParaEnum(Integer codigo) {
		if (codigo == null) {
			throw new NullPointerException("O Status do Produto não pode estar nulo!");
		}
		
		for (StatusProduto statusProdutoAtual : StatusProduto.values()) {
			if (codigo.equals(statusProdutoAtual.getCodigo())) {
				return statusProdutoAtual;
			}
		}
		
		throw new IllegalArgumentException("Código (" + codigo + ") de Status do Produto inválido!");
	}
}
