package com.romano.Supermercado.usuario.service;

import org.springframework.security.core.context.SecurityContextHolder;

import com.romano.Supermercado.security.UsuarioSecurity;


/**
 * 
 * @author Paulo Romano - [paulo-romano_133@hotmail.com]
 * Classe responsável por recuperar as informações do Usuário logado
 */
public class UsuarioService {

	/**
	 * Método responsável por obter o Usuário logado
	 * @return UsuarioSecurity
	 */
	public static UsuarioSecurity authenticated() {
		try {
			return (UsuarioSecurity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();			
		}
		catch (Exception e) {
			return null;
		}
	}
}
