package com.romano.Supermercado.cliente.enums;


/**
 * 
 * @author Paulo Romano - [paulo-romano_133@hotmail.com]
 * Enum responsável por deferir o sexo do {@link Cliente}
 */
public enum SexoCliente {
	MASCULINO("M", "Masculino"),
	FEMINIMO("F", "Feminino");
	
	
	private String sexoAbreviado;
	private String sexoPorExtenso;
	
	
	private SexoCliente(String sexoAbreviado, String sexoPorExtenso) {
		this.sexoAbreviado = sexoAbreviado;
		this.sexoPorExtenso = sexoPorExtenso;
	}


	public String getSexoAbreviado() {
		return sexoAbreviado;
	}

	public String getSexoPorExtenso() {
		return sexoPorExtenso;
	}
	
	
	/**
	 * Método responsável por converter o sexo abreviado do {@link Cliente} para o Enum de
	 * {@link SexoCliente}
	 * @param sexoAbreviado : String
	 * @return {@link SexoCliente} - Enum
	 */
	public static SexoCliente converterParaEnum(String sexoAbreviado) {
		if (sexoAbreviado == null) {
			throw new NullPointerException("O Sexo do Cliente não pode estar nulo!");
		}
		
		for (SexoCliente sexoClienteAtual : SexoCliente.values()) {
			if (sexoAbreviado.equals(sexoClienteAtual.getSexoAbreviado())) {
				return sexoClienteAtual;
			}
		}
		
		throw new IllegalArgumentException("Sexo (" + sexoAbreviado + ") inválido!");
	}
}
