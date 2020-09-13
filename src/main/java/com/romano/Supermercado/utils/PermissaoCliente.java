package com.romano.Supermercado.utils;

import com.romano.Supermercado.cliente.enums.PerfilCliente;
import com.romano.Supermercado.exception.service.AuthorizationException;
import com.romano.Supermercado.security.UsuarioSecurity;
import com.romano.Supermercado.usuario.service.UsuarioService;

/**
 * 
 * @author Paulo Romano - [paulo-romano_133@hotmail.com]
 * Classe responsável por verificar se o Cliente tem acesso a uma determinada operação
 */
public final class PermissaoCliente {

	
	/**
	 * Método responsável por verficar se o Usuário está logado
	 */
	public static final void usuarioEValido() {
		UsuarioSecurity usuario = UsuarioService.authenticated();
		
		if (usuario.getId() == null) {
			throw new IllegalArgumentException("Acesso negado!");
		}
	}
	
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
}
