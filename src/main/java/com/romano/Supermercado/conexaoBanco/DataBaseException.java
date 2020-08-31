package com.romano.Supermercado.conexaoBanco;


/**
 * 
 * @author Paulo Romano - [paulo-romano_133@hotmail.com]
 * Classe de exceção lançada ao gerar erro em alguma operação com
 * o Banco de Dados
 */
public class DataBaseException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public DataBaseException(String mensagemErro) {
		super(mensagemErro);
	}
}
