package com.romano.Supermercado.cliente.enums;


/**
 * 
 * @author Paulo Romano - [paulo-romano_133@hotmail.com]
 * Enum responsável por deferir o Perfil do {@link Cliente}
 */
public enum PerfilCliente {
	ADMIN(1, "ROLE_ADMIN"),
	CLIENTE(2, "ROLE_CLIENTE");
	
	
	private Integer codigo;
	private String descicao;
	
	
	private PerfilCliente(Integer codigo, String descricao) {
		this.codigo = codigo;
		this.descicao = descricao;
	}

	
	public Integer getCodigo() {
		return codigo;
	}

	public String getDescricao() {
		return descicao;
	}
	
	
	/**
	 * Método responsável por converter o código do {@link PerfilCliente} para o Enum de
	 * Perfil do {@link Cliente}
	 * @param codigo : Integer
	 * @return {@link PerfilCliente} - Enum
	 */
	public static PerfilCliente converterParaEnum(Integer codigo) {
		if (codigo == null) {
			throw new NullPointerException("O Código de Perfil do Cliente não pode estar nulo!");
		}
		
		for (PerfilCliente perfilAtual : PerfilCliente.values()) {
			if (codigo.equals(perfilAtual.getCodigo())) {
				return perfilAtual;
			}
		}
		
		throw new IllegalArgumentException("Código (" + codigo + ") inválido!");
	}
}
