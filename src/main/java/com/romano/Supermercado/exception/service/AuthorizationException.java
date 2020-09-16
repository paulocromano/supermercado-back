package com.romano.Supermercado.exception.service;


/**
 * 
 * @author Paulo Romano - [paulo-romano_133@hotmail.com]
 * Classe responsável por receber um erro de Autorização
 */
public class AuthorizationException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;
	
	
	public AuthorizationException(String msg) {
		super(msg);
	}
	
	public AuthorizationException(String msg, Throwable cause) {
		super(msg, cause);
	}
}
