package com.romano.Supermercado.cliente.form;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.romano.Supermercado.cliente.model.Cliente;

/**
 * 
 * @author Paulo Romano - [paulo-romano_133@hotmail.com]
 * Classe responsável por receber os dados inciais de Login para
 * um novo Cliente
 */
public class ClienteFORM {

	@NotNull(message = "Nome não informado!")
	@NotEmpty(message = "O campo Nome não pode estar vazio!")
	@Size(min = 3, max = 40, message = "O campo nome deve ter entre {min} a {max} caracteres!")
	private String nome;
	
	@NotNull(message = "Sexo não informado!")
	@NotEmpty(message = "O campo Sexo não pode estar vazio!")
	private String sexo;
	
	@NotNull(message = "Email informado!")
	@NotEmpty(message = "O campo Email não pode estar vazio!")
	@Size(min = 3, max = 40, message = "O campo email deve ter entre {min} a {max} caracteres!")
	@Email(regexp = "^[-a-zA-Z0-9][-.a-zA-Z0-9]*@[-.a-zA-Z0-9]+(\\.[-.a-zA-Z0-9]+)*\\.(com|edu|info|gov|int|mil|net|org|biz|name|museum|coop|aero|pro|tv|[a-zA-Z]{2})$",
			message = "Formato de Email inválido!")
	private String email;
	
	@NotNull(message = "Senha não informada!")
	@NotEmpty(message = "O campo Senha não pode estar vazia!")
	@Size(min = 6, max = 20, message = "O campo senha deve ter entre {min} a {max} caracteres!")
	@Pattern(regexp = "((?=.*\\d)(?=.*[A-Z])(?=.*\\W).{6,20})", message = "A senha deve conter caracteres alfanuméricos, um caracter maiúsculo e um caracter especial!")
	private String senha;
	
	
	public String getNome() {
		return nome;
	}
	
	public String getSexo() {
		return sexo;
	}

	public String getEmail() {
		return email;
	}

	public String getSenha() {
		return senha;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}
	
	
	/**
	 * Método responsável por converter ClienteFORM para Cliente
	 * @param bCryptPasswordEncoder : BCryptPasswordEncoder
	 * @return Cliente - Cliente convertido
	 */
	public Cliente converterParaCliente(BCryptPasswordEncoder bCryptPasswordEncoder) {
		return new Cliente(nome, sexo, email, bCryptPasswordEncoder.encode(senha));
	}
}
