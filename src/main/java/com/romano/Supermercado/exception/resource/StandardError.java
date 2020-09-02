package com.romano.Supermercado.exception.resource;

import java.io.Serializable;


/**
 * 
 * @author Paulo Romano - [paulo-romano_133@hotmail.com]
 * Classe responsável por receber os dados personalizados dos erros de requisições e regras de negócios
 */
public class StandardError implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long timestamp;
	private Integer status;
	private String error;
	private String path;
	
	
	/**
	 * Construtor
	 * @param timestamp : Long
	 * @param status : Integer
	 * @param error : String
	 * @param path : String
	 */
	public StandardError(Long timestamp, Integer status, String error, String path) {
		super();
		this.timestamp = timestamp;
		this.status = status;
		this.error = error;
		this.path = path;
	}

	public Long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Long timestamp) {
		this.timestamp = timestamp;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}
}
