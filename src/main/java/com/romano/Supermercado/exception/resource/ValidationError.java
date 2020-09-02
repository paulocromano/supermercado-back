package com.romano.Supermercado.exception.resource;

import java.util.ArrayList;
import java.util.List;


/**
 * 
 * @author Paulo Romano - [paulo-romano_133@hotmail.com]
 * Classe responsável por receber os erros de Validações
 */
public class ValidationError extends StandardError {
	
	private static final long serialVersionUID = 1L;
	
	private List<FieldMessage> errors = new ArrayList<>();
	
	
	/**
	 * Construtor
	 * @param timestamp : Long
	 * @param status : Integer
	 * @param error : String
	 * @param path : String
	 */
	public ValidationError(Long timestamp, Integer status, String error, String path) {
		super(timestamp, status, error, path);
	
	}


	public List<FieldMessage> getErrors() {
		return errors;
	}

	public void addError(String fieldName, String message) {
		errors.add(new FieldMessage(fieldName, message));
	}
}
