package com.romano.Supermercado.utils;

import java.util.Optional;

import com.romano.Supermercado.cliente.enums.PerfilCliente;
import com.romano.Supermercado.cliente.model.Cliente;
import com.romano.Supermercado.cliente.repository.ClienteRepository;
import com.romano.Supermercado.exception.service.AuthorizationException;
import com.romano.Supermercado.exception.service.ObjectNotFoundException;
import com.romano.Supermercado.security.UsuarioSecurity;
import com.romano.Supermercado.usuario.service.UsuarioService;

/**
 * 
 * @author Paulo Romano - [paulo-romano_133@hotmail.com]
 * Classe responsável por verificar se o Cliente tem acesso a uma determinada operação
 */
public final class UsuarioValido {

	
	/**
	 * Método responsável por verficar se o Usuário está logado
	 */
	public static final void usuarioEValido() {
		UsuarioSecurity usuario = UsuarioService.authenticated();
		
		if (usuario.getId() == null) {
			throw new IllegalArgumentException("Acesso negado!");
		}
	}
	
	
	/**
	 * Método responsável por retornar um UsuarioSecurity caso o Usuário seja válido
	 * @return UsuarioSecurity
	 */
	public static final UsuarioSecurity retornaUsuarioValido() {
		UsuarioSecurity usuario = UsuarioService.authenticated();
		
		if (usuario.getId() == null) {
			throw new IllegalArgumentException("Acesso negado!");
		}
		
		return usuario;
	}
	
	
	/**
	 * Método responsável por verificar se o Usuário tem permissão
	 * para efetuar alguma operação de CRUD
	 * @param id : Long
	 */
	public static final void usuarioTemPermissao(Long id) {
		UsuarioSecurity usuario = UsuarioService.authenticated();
		
		if (usuario == null || !usuario.hasRole(PerfilCliente.ADMIN) && !id.equals(usuario.getId())) {
			throw new AuthorizationException("Acesso negado!");
		}
	}
	
	
	/**
	 * Método responsável por verificar se o Usuário existe
	 * @param clienteRepository : {@link ClienteRepository}
	 * @param id : Long
	 * @return Cliente - Caso o cliente exista
	 */
	public static final Cliente existeUsuario(ClienteRepository clienteRepository, Long id) {
		Optional<Cliente> cliente = clienteRepository.findById(id);
		
		if (cliente.isEmpty()) {
			throw new ObjectNotFoundException("Cliente não encontrado!");
		}
		
		return cliente.get();
	}
}
