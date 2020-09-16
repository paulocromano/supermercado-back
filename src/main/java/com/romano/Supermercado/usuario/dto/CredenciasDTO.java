package com.romano.Supermercado.usuario.dto;

import java.io.Serializable;

/**
 * 
 * @author Paulo Romano - [paulo-romano_133@hotmail.com] <br>
 * Classe responsável por receber o email e senha do Usuário(Cliente)
 */
public class CredenciasDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String email;
	private String senha;
	
	
	public CredenciasDTO() {

	}
	
	
	public String getEmail() {
		return email;
	}
	
	public String getSenha() {
		return senha;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public void setSenha(String senha) {
		this.senha = senha;
	}
}
