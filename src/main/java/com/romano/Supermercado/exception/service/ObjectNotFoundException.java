package com.romano.Supermercado.exception.service;


/**
 * 
 * @author Paulo Romano - [paulo-romano_133@hotmail.com]
 * Classe responsável por receber um erro de Objeto não encontrado
 */
public class ObjectNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	
	/**
	 * Construtor
	 * @param msg :String
	 */
	public ObjectNotFoundException(String msg) {
		super(msg);
	}
	
	
	/**
	 * Construtor
	 * @param msg : String
	 * @param cause : Throwable
	 */
	public ObjectNotFoundException(String msg, Throwable cause) {
		super(msg, cause);
	}
}
