package com.romano.Supermercado.cliente.service;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.romano.Supermercado.cliente.dto.ClienteDTO;
import com.romano.Supermercado.cliente.form.ClienteFORM;
import com.romano.Supermercado.cliente.model.Cliente;
import com.romano.Supermercado.cliente.repository.ClienteRepository;


/**
 * 
 * @author Paulo Romano - [paulo-romano_133@hotmail.com]
 * Classe de Serviço responsável pelas regras de negócios do Cliente
 */
@Service
public class ClienteService {

	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	
	/**
	 * Método responsável por listar todos os Clientes
	 * @return ResponseEntity<List<ClienteDTO>>
	 */
	public ResponseEntity<List<ClienteDTO>> listarTodosClientes() {
		return ResponseEntity.ok().body(ClienteDTO.converterParaListaClienteDTO(clienteRepository.findAll()));
	}
	
	
	/**
	 * Método responsável por cadastrar um novo Cliente
	 * @param clienteFORM : ClienteFORM
	 * @return ResponseEntity<Void>
	 * @throws SQLIntegrityConstraintViolationException
	 */
	public ResponseEntity<Void> cadastrarCliente(ClienteFORM clienteFORM) throws SQLIntegrityConstraintViolationException {
		Cliente cliente = clienteFORM.converterparaCliente(bCryptPasswordEncoder);

		if (verificarSeEmailJaExiste(cliente.getEmail())) {
			throw new SQLIntegrityConstraintViolationException("Email informado já está cadastrado! Por favor escolha outro.");
		}
		
		clienteRepository.save(cliente);
		
		return ResponseEntity.ok().build();
	}
	
	
	/**
	 * Método responsável por verificar se o email informado no momento do 
	 * cadastro já existe
	 * @param email : String
	 * @return Boolean - Retorna True se o email informado já existir. False
	 * se o email for novo
	 */ 
	private Boolean verificarSeEmailJaExiste(String email) {
		Optional<Cliente> cliente = clienteRepository.findByEmail(email);
		
		return (cliente.isPresent()) ? true : false;
	}
}
