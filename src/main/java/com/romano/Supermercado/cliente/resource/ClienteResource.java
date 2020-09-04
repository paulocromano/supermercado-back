package com.romano.Supermercado.cliente.resource;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.romano.Supermercado.cliente.dto.ClienteDTO;
import com.romano.Supermercado.cliente.form.ClienteFORM;
import com.romano.Supermercado.cliente.service.ClienteService;


/**
 * 
 * @author Paulo Romano - [paulo-romano_133@hotmail.com]
 * Classe responsável por chamar os métodos do Service de Cliente
 */
@RestController
@RequestMapping("/cliente")
public class ClienteResource {

	@Autowired
	private ClienteService  clienteService;
	
	
	/**
	 * Método responsável por chamar o serviço de listar todos os Clientes
	 * @return ResponseEntity<List<ClienteDTO>> - Retorna a resposta da requisição
	 */
	@GetMapping("/listar-todos")
	public ResponseEntity<List<ClienteDTO>> listarTodosClientes() {
		return clienteService.listarTodosClientes();
	}
	
	
	/**
	 * Método responsável por chamar o serviço de cadastro de Cliente
	 * @param clienteFORM : ClienteFORM
	 * @return ResponseEntity<Void> - Retorna a resposta da requisição
	 * @throws SQLIntegrityConstraintViolationException
	 */
	@PostMapping("/cadastrar")
	public ResponseEntity<Void> cadastrarCliente(@RequestBody @Valid ClienteFORM clienteFORM) throws SQLIntegrityConstraintViolationException {
		return clienteService.cadastrarCliente(clienteFORM);
	}
}
