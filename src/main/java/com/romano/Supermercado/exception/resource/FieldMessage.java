package com.romano.Supermercado.exception.resource;

import java.io.Serializable;


/**
 * 
 * @author Paulo Romano - [paulo-romano_133@hotmail.com]
 * Classe responsáveç por receber o nome do campo e mensagem do erro
 */
public class FieldMessage implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String fieldName;
	private String message;
	
	
	public FieldMessage() {
		
	}

	/**
	 * Construtor
	 * @param fieldName : String
	 * @param message : String
	 */
	public FieldMessage(String fieldName, String message) {
		this.fieldName = fieldName;
		this.message = message;
	}


	public String getFieldName() {
		return fieldName;
	}

	public String getMessage() {
		return message;
	}
}
