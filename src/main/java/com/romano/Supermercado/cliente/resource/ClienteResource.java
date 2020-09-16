package com.romano.Supermercado.cliente.resource;

import java.util.List;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.romano.Supermercado.cliente.dto.ClienteDTO;
import com.romano.Supermercado.cliente.form.AtualizarClienteFORM;
import com.romano.Supermercado.cliente.form.ClienteFORM;
import com.romano.Supermercado.cliente.service.ClienteService;


/**
 * 
 * @author Paulo Romano - [paulo-romano_133@hotmail.com] <br>
 * Classe responsável por chamar os métodos do ClienteService
 */
@RestController
@RequestMapping("/cliente")
public class ClienteResource {

	@Autowired
	private ClienteService  clienteService;
	
	
	/**
	 * Método responsável por chamar o serviço de listar todos os Clientes
	 * @return ResponseEntity - List de ClienteDTO (Retorna a resposta da requisição)
	 */
	@PreAuthorize("hasAnyRole('ADMIN')")
	@GetMapping("/listar-todos")
	public ResponseEntity<List<ClienteDTO>> listarTodosClientes() {
		return clienteService.listarTodosClientes();
	}
	
	
	/**
	 * Método responsável por chamar o serviço de buscar o Cliente pelo ID
	 * @param id : Long
	 * @return ResponseEntity - ClienteDTO (Retorna a resposta da requisição)
	 */
	@GetMapping("/{id}")
	public ResponseEntity<ClienteDTO> buscarClientePorID(@PathVariable Long id) {
		return clienteService.buscarClientePorID(id);
	}
	
	
	/**
	 * Método responsável por chamar o serviço de cadastro de Cliente
	 * @param clienteFORM : ClienteFORM
	 * @return ResponseEntity - Void (Retorna a resposta da requisição)
	 */
	@PostMapping
	public ResponseEntity<Void> cadastrarCliente(@RequestBody @Valid ClienteFORM clienteFORM) {
		return clienteService.cadastrarCliente(clienteFORM);
	}
	
	
	/**
	 * Método responsável por chamar o serviço de atualizar um Cliente
	 * @param id : Long
	 * @param atualizarClienteFORM : AtualizarClienteFORM
	 * @return ResponseEntity - Void (Retorna a resposta da requisição)
	 */
	@Transactional
	@PutMapping("/{id}")
	public ResponseEntity<Void> atualizarCliente(@PathVariable Long id, @RequestBody AtualizarClienteFORM atualizarClienteFORM) {
		return clienteService.atualizarCliente(id, atualizarClienteFORM);
	}
	
	
	/**
	 * Método responsável por chamar o serviço de adicionar PerfilCliente para um Cliente
	 * @param idCliente : Long
	 * @return ResponseEntity - Void (Retorna a resposta da requisição)
	 */
	@PreAuthorize("hasAnyRole('ADMIN')")
	@Transactional
	@PutMapping("/adicionar-permissao={idCliente}")
	public ResponseEntity<Void> adicionarPermissaoParaCliente(@PathVariable Long idCliente) {
		return clienteService.adicionarPermissaoParaCliente(idCliente);
	}
	
	
	/**
	 * Método responsável por chamar o serviço de remoção de Cliente
	 * @param id : Long
	 * @return ResponseEntity - Void (Retorna a resposta da requisição)
	 */
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> removerCliente(@PathVariable Long id) {
		return clienteService.removerCliente(id);
	}
}
