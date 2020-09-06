package com.romano.Supermercado.exception.resource;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.romano.Supermercado.exception.service.AuthorizationException;
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
	 * @return ResponseEntity<StandardError> - Resposta com o erro personalizado
	 */
	@ExceptionHandler(ObjectNotFoundException.class)
	public ResponseEntity<StandardError> objectNotFound(ObjectNotFoundException error, HttpServletRequest request) {
		return erroPersonalizado(error, HttpStatus.NOT_FOUND, error.getMessage(), request);
	}

	
	/**
	 * Método responsável por tratar o erro de quando alguma operação resulta em violação
	 * dos dados no banco
	 * @param error : DataIntegrityException
	 * @param request : HttpServletRequest
	 * @return ResponseEntity<StandardError> - Resposta com o erro personalizado
	 */
	@ExceptionHandler(DataIntegrityException.class)
	public ResponseEntity<StandardError> dataIntegrity(DataIntegrityException error, HttpServletRequest request) {
		return erroPersonalizado(error, HttpStatus.BAD_REQUEST, error.getMessage(), request);
	}
	
	
	/**
	 * Método responsável por tratar o erro de atributos que recebem um Argumento Inválido
	 * @param error : MethodArgumentNotValidException
	 * @param request : HttpServletRequest
	 * @return ResponseEntity<StandardError> - Resposta com o erro personalizado
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
	 * Método responsável por tratar o erro de Argumento Ilegal
	 * @param error : IllegalArgumentException
	 * @param request : HttpServletRequest
	 * @return ResponseEntity<StandardError> - Resposta com o erro personalizado
	 */
	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<StandardError> illegalArgument(IllegalArgumentException error,  HttpServletRequest request) {
		return erroPersonalizado(error, HttpStatus.BAD_REQUEST, error.getMessage(), request);
	}
	
	
	/**
	 * Método responsável por tratar o erro de Objeto Nulo
	 * @param error : NullPointerException
	 * @param request : HttpServletRequest
	 * @return ResponseEntity<StandardError> - Resposta com o erro personalizado
	 */
	@ExceptionHandler(NullPointerException.class)
	public ResponseEntity<StandardError> nullPointer(NullPointerException error,  HttpServletRequest request) {
		return erroPersonalizado(error, HttpStatus.BAD_REQUEST, error.getMessage(), request);
	}
	
	
	/**
	 * Método responsável por tratar o erro ao tentar acessar uma URL inexistente
	 * @param error : HttpRequestMethodNotSupportedException
	 * @param request : HttpServletRequest
	 * @return ResponseEntity<StandardError> - Resposta com o erro personalizado
	 */
	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	public ResponseEntity<StandardError> httpRequestMethodNotSupported(HttpRequestMethodNotSupportedException error,  HttpServletRequest request) {
		return erroPersonalizado(error, HttpStatus.NOT_FOUND, "URL requisitada não encontrada!",  request);
	}
	
	
	/**
	 * Método responsável por tratar o erro ao dar Acesso Negado
	 * @param error : HttpRequestMethodNotSupportedException
	 * @param request : HttpServletRequest
	 * @return ResponseEntity<StandardError> - Resposta com o erro personalizado
	 */
	@ExceptionHandler(AuthorizationException.class)
	public ResponseEntity<StandardError> authorization(AuthorizationException error,  HttpServletRequest request) {
		return erroPersonalizado(error, HttpStatus.FORBIDDEN, error.getMessage(),  request);
	}
	
	
	/**
	 * Método responsável por tratar o erro ao tentar acessar acessar uma URL não permitida
	 * @param error : AccessDeniedException
	 * @param request : HttpServletRequest
	 * @return ResponseEntity<StandardError> - Resposta com o erro personalizado
	 */
	@ExceptionHandler(AccessDeniedException.class)
	public ResponseEntity<StandardError> accessDenied(AccessDeniedException error,  HttpServletRequest request) {
		return erroPersonalizado(error, HttpStatus.FORBIDDEN, "Acesso negado!",  request);
	}
	
	
	/**
	 * Método responsável por tratar um erro
	 * @param error : Exception
	 * @param request : HttpServletRequest
	 * @param httpStatus : HttpStatus
	 * @param messageError : String
	 * @return ResponseEntity<StandardError> - Reposta da requisição com o erro tratado
	 */
	private ResponseEntity<StandardError> erroPersonalizado(Exception error, HttpStatus httpStatus, String messageError, HttpServletRequest request) {
		return ResponseEntity.status(httpStatus).body(new StandardError(System.currentTimeMillis(), httpStatus.value(), messageError, request.getRequestURI()));
	}
}
