package com.romano.Supermercado.cliente.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.romano.Supermercado.cliente.dto.ClienteDTO;
import com.romano.Supermercado.cliente.enums.PerfilCliente;
import com.romano.Supermercado.cliente.form.AdicionarEnderecoFORM;
import com.romano.Supermercado.cliente.form.AtualizarClienteFORM;
import com.romano.Supermercado.cliente.form.ClienteFORM;
import com.romano.Supermercado.cliente.model.Cliente;
import com.romano.Supermercado.cliente.repository.ClienteRepository;
import com.romano.Supermercado.exception.service.DataIntegrityException;
import com.romano.Supermercado.exception.service.ObjectNotFoundException;
import com.romano.Supermercado.localidade.cidade.repository.CidadeRepository;
import com.romano.Supermercado.localidade.endereco.model.Endereco;
import com.romano.Supermercado.localidade.endereco.repository.EnderecoRepository;
import com.romano.Supermercado.security.UsuarioSecurity;
import com.romano.Supermercado.utils.VerificarUsuario;


/**
 * 
 * @author Paulo Romano - [paulo-romano_133@hotmail.com] <br>
 * Classe de Serviço responsável pelas regras de negócios do Cliente
 */
@Service
public class ClienteService {

	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private CidadeRepository cidadeRepository;
	
	@Autowired
	private EnderecoRepository enderecoRepository;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	
	/**
	 * Método responsável por listar todos os Clientes
	 * @return ResponseEntity - List ClienteDTO
	 */
	public ResponseEntity<List<ClienteDTO>> listarTodosClientes() {
		return ResponseEntity.ok().body(ClienteDTO.converterParaListaClienteDTO(clienteRepository.findAll()));
	}
	
	
	/**
	 * Método responsável por buscar o Cliente pelo ID 
	 * @param id : Long
	 * @return ResponseEntity - ClienteDTO
	 */
	public ResponseEntity<ClienteDTO> buscarClientePorID(Long id) {
		VerificarUsuario.usuarioTemPermissao(id);
		Cliente cliente = Cliente.existeCliente(clienteRepository, id);
		
		return ResponseEntity.ok().body(new ClienteDTO(cliente));
	}
	
	/**
	 * Método responsável por cadastrar um novo Cliente
	 * @param clienteFORM : ClienteFORM
	 * @return ResponseEntity - Void
	 */
	public ResponseEntity<Void> cadastrarCliente(ClienteFORM clienteFORM) {
		Cliente cliente = clienteFORM.converterParaCliente(bCryptPasswordEncoder);

		try {
			clienteRepository.save(cliente);
		}
		catch (RuntimeException e) {
			throw new DataIntegrityException("Email indisponível!");
		}
		
		return ResponseEntity.ok().build();
	}
	
	
	/**
	 * Método responsável por atualizar os dados cadastrais de um Cliente
	 * @param id : Long
	 * @param atualizarClienteFORM : AtualizarClienteFORM
	 * @return ResponseEntity - Void
	 */
	public ResponseEntity<Void> atualizarCliente(Long id, AtualizarClienteFORM atualizarClienteFORM) {
		VerificarUsuario.usuarioEValido();
		
		Cliente cliente = clienteRepository.getOne(id);
		atualizarClienteFORM.atualizarCliente(cliente);
		
		return ResponseEntity.ok().build();
	}
	
	
	/**
	 * Método responsável por adicionar um Endereço para o Cliente
	 * @param id : Long
	 * @param adicionarEnderecoFORM : AdicionarEnderecoFORM
	 * @return ResponseEntity - Void
	 */
	public ResponseEntity<Void> adicionarEndereco(Long id, AdicionarEnderecoFORM adicionarEnderecoFORM) {
		VerificarUsuario.usuarioEValido();
		
		Cliente cliente = clienteRepository.getOne(id);
		adicionarEnderecoFORM.adicionarEndereco(cliente, cidadeRepository);
		
		return ResponseEntity.ok().build();
	}
	
	
	/**
	 * Método responsável por adicionar permissão a um Cliente
	 * @param idCliente : Long
	 * @return ResponseEntity - Void
	 */
	public ResponseEntity<Void> adicionarPermissaoParaCliente(Long idCliente) {
		VerificarUsuario.usuarioEValido();
		verificarUsuarioParaDarPermissao(idCliente);
		
		Cliente cliente = clienteRepository.getOne(idCliente);
		cliente.adicionarPerfis(PerfilCliente.ADMIN);
		
		return ResponseEntity.ok().build();
	}
	
	
	/**
	 * Método responsável por verificar se o Usuário já possui permissão de ADMIN
	 * @param idCliente : Long
	 */
	private void verificarUsuarioParaDarPermissao(Long idCliente) {		
		Cliente cliente = Cliente.existeCliente(clienteRepository, idCliente);
		
		if (cliente.getPerfis().contains(PerfilCliente.ADMIN)) {
			throw new IllegalArgumentException("O Cliente informado já possui permissão de Administrador!");
		}
	}
	
	
	/**
	 * Método responsável por remover um Endereço de Cliente
	 * @param idEndereco : Long
	 * @return ResponseEntity - Void
	 */
	public ResponseEntity<Void> removerEndereco(Long idEndereco) {
		UsuarioSecurity usuario = VerificarUsuario.usuarioEValido();
		
		Cliente cliente = clienteRepository.getOne(usuario.getId());
		verificarSeEnderecoPertenceAoCliente(idEndereco, cliente);

		enderecoRepository.deleteById(idEndereco);
		
		cliente.getEnderecos().forEach(end -> System.out.println(end.getId()));
		
		return ResponseEntity.ok().build();
	}
	
	
	/**
	 * Método responsável por verificar se o Endereço pertence ao Cliente logado
	 * @param idEndereco : Long
	 * @param cliente : Cliente
	 */
	private void verificarSeEnderecoPertenceAoCliente(Long idEndereco, Cliente cliente) {
		for (Endereco endereco : cliente.getEnderecos()) {
			if (endereco.getId() == idEndereco) {
				return;
			}
		}
				
		throw new ObjectNotFoundException("Endereço não encontrado!");
	}
	
	
	/**
	 * Método responsável por remover um Cliente
	 * @param id : Long
	 * @return ResponseEntity - Void
	 */
	public ResponseEntity<Void> removerCliente(Long id) {
		VerificarUsuario.usuarioTemPermissao(id);
		Cliente.existeCliente(clienteRepository, id);
		
		clienteRepository.deleteById(id);
		
		return ResponseEntity.ok().build();
	}
}
