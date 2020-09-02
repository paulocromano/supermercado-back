package com.romano.Supermercado.exception.resource;

import java.sql.SQLIntegrityConstraintViolationException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.romano.Supermercado.exception.service.DataIntegrityException;
import com.romano.Supermercado.exception.service.ObjectNotFoundException;


/**
 * 
 * @author Paulo Romano - [paulo-romano_133@hotmail.com]
 * Classe responsável por tratar os erros das requisições e regras de negócios
 */
@ControllerAdvice
public class ResourceExceptionHandler {
	
	/**
	 * Método responsável por tratar o erro de quando um Objeto não foi encontrado
	 * @param error : ObjectNotFoundException
	 * @param request : HttpServletRequest
	 * @return ResponseEntity<StandardError> - Reposta da requisição com o erro tratado
	 */
	@ExceptionHandler(ObjectNotFoundException.class)
	public ResponseEntity<StandardError> objectNotFound(ObjectNotFoundException error, HttpServletRequest request) {
		
		StandardError standardError = new StandardError(System.currentTimeMillis(), HttpStatus.NOT_FOUND.value(), error.getMessage(), request.getRequestURI());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(standardError);
	}

	
	/**
	 * Método responsável por tratar o erro de quando algum dado sofre alguma violação
	 * @param error : DataIntegrityException
	 * @param request : HttpServletRequest
	 * @return ResponseEntity<StandardError> - Reposta da requisição com o erro tratado
	 */
	@ExceptionHandler(DataIntegrityException.class)
	public ResponseEntity<StandardError> dataIntegrity(DataIntegrityException error, HttpServletRequest request) {
		
		StandardError standardError = new StandardError(System.currentTimeMillis(), HttpStatus.BAD_REQUEST.value(), "Integridade de dados", request.getRequestURI());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(standardError);
	}
	
	
	/**
	 * Método responsável por tratar o erro de atributos que recebem um Argumento Inválido
	 * @param error : MethodArgumentNotValidException
	 * @param request : HttpServletRequest
	 * @return ResponseEntity<StandardError> - Reposta da requisição com o erro tratado
	 */
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<StandardError> validation(MethodArgumentNotValidException error, HttpServletRequest request) {
		
		ValidationError validationError = new ValidationError(System.currentTimeMillis(), HttpStatus.BAD_REQUEST.value(), "Erro de validação", request.getRequestURI());
		
		for (FieldError fieldError : error.getBindingResult().getFieldErrors()) {
			validationError.addError(fieldError.getField(), fieldError.getDefaultMessage());
		}
		
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(validationError);
	}
	
	
	/**
	 * Método responsável por tratar o erro de Integridade dos Dados do Banco ao tentar remover
	 * um item que possui ligação com outras tabelas
	 * @param error : SQLIntegrityConstraintViolationException
	 * @param request : HttpServletRequest
	 * @return ResponseEntity<StandardError> - Reposta da requisição com o erro tratado
	 */
	@ExceptionHandler(SQLIntegrityConstraintViolationException.class)
	public ResponseEntity<StandardError> integrityConstrainViolation(SQLIntegrityConstraintViolationException error, HttpServletRequest request) {
		
		StandardError standardError = new StandardError(System.currentTimeMillis(), HttpStatus.BAD_REQUEST.value(), error.getMessage(), request.getRequestURI());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(standardError);
	}
}
