package com.romano.Supermercado.cliente.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.romano.Supermercado.cliente.dto.ClienteDTO;
import com.romano.Supermercado.cliente.enums.PerfilCliente;
import com.romano.Supermercado.cliente.form.AtualizarClienteFORM;
import com.romano.Supermercado.cliente.form.ClienteFORM;
import com.romano.Supermercado.cliente.model.Cliente;
import com.romano.Supermercado.cliente.repository.ClienteRepository;
import com.romano.Supermercado.cliente.repository.PerfilClienteRepository;
import com.romano.Supermercado.exception.service.AuthorizationException;
import com.romano.Supermercado.exception.service.DataIntegrityException;
import com.romano.Supermercado.exception.service.ObjectNotFoundException;
import com.romano.Supermercado.security.UsuarioSecurity;
import com.romano.Supermercado.usuario.service.UsuarioService;


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
	private PerfilClienteRepository perfilClienteRepository;
	
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
	 * Método responsável por buscar o Cliente pelo ID 
	 * @param id : Long
	 * @return ResponseEntity<ClienteDTO>
	 */
	public ResponseEntity<ClienteDTO> buscarClientePorID(Long id) {
		usuarioTemPermissao(id);
				
		Optional<Cliente> cliente = clienteRepository.findById(id);
		
		if (cliente.isEmpty()) {
			throw new ObjectNotFoundException("Cliente não encontrado!");
		}
		
		return ResponseEntity.ok().body(new ClienteDTO(cliente.get()));
	}
	
	/**
	 * Método responsável por cadastrar um novo Cliente
	 * @param clienteFORM : ClienteFORM
	 * @return ResponseEntity<Void>
	 */
	public ResponseEntity<Void> cadastrarCliente(ClienteFORM clienteFORM) {
		Cliente cliente = clienteFORM.converterparaCliente(bCryptPasswordEncoder);

		if (verificarSeEmailJaExiste(cliente.getEmail())) {
			throw new DataIntegrityException("Email indisponível!");
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
	
	
	/**
	 * Método responsável por atualizar os dados cadastrais de um Cliente
	 * @param id : Long
	 * @param atualizarClienteFORM : AtualizarClienteFORM
	 * @return ResponseEntity<Void>
	 */
	public ResponseEntity<Void> atualizarCliente(Long id, AtualizarClienteFORM atualizarClienteFORM) {
		usuarioTemPermissaoParaAtualizarOsDados(id);
		
		Cliente cliente = clienteRepository.getOne(id);
		atualizarClienteFORM.atualizarCliente(cliente);
		
		return ResponseEntity.ok().build();
	}
	
	
	/**
	 * Método responsável por remover um Cliente
	 * @param id : Long
	 * @return ResponseEntity<Void>
	 */
	public ResponseEntity<Void> removerCliente(Long id) {
		usuarioTemPermissao(id);
		
		perfilClienteRepository.deleteById(id);
		clienteRepository.deleteById(id);
		
		return ResponseEntity.ok().build();
	}
	
	
	/**
	 * Método responsável por verificar se o Usuário tem permissão
	 * para efetuar alguma operação de CRUD
	 * @param id : Long
	 */
	private void usuarioTemPermissao(Long id) {
		UsuarioSecurity usuario = UsuarioService.authenticated();
		
		if (usuario == null || !usuario.hasRole(PerfilCliente.ADMIN) && !id.equals(usuario.getId())) {
			throw new AuthorizationException("Acesso negado!");
		}
	}
	
	
	/**
	 * Método responsável por verficar se o Usuário logado tem permissão para
	 * atualizar os dados de cadastro
	 * @param id : Long
	 */
	private void usuarioTemPermissaoParaAtualizarOsDados(Long id) {
		UsuarioSecurity usuario = UsuarioService.authenticated();
		
		if (id == null) {
			throw new ObjectNotFoundException("Cliente informado não existe!");
		}
		
		if (!id.equals(usuario.getId())) {
			throw new AuthorizationException("Acesso negado!");
		}
	}
}
