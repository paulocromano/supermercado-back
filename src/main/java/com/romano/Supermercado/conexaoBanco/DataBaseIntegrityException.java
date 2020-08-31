package com.romano.Supermercado.conexaoBanco;


/**
 * 
 * @author Paulo Romano - [paulo-romano_133@hotmail.com]
 * Classe de exceção lançada ao tentar remover um item que possui ligação
 * com outra tabela
 */
public class DataBaseIntegrityException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	
	public DataBaseIntegrityException(String mensagemErro) {
		super(mensagemErro);
	}
}
