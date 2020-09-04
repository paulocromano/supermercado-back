package com.romano.Supermercado.cliente.model;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.romano.Supermercado.cliente.enums.PerfilCliente;
import com.romano.Supermercado.cliente.enums.SexoCliente;


/**
 * 
 * @author Paulo Romano - [paulo-romano_133@hotmail.com]
 * Classe Entidade de Cliente
 */
@Entity
@Table(name = "cliente")
public class Cliente {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id; 
	
	private String nome;
	
	@Column(unique = true)
	private String email;
	
	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(name = "perfil")
	private Set<Integer> perfis = new HashSet<>();
	
	@JsonIgnore
	private String senha;
	
	@Column(name = "data_nascimento")
	private LocalDate dataNascimento;
	
	private String sexo;
	private String telefone;
	private String endereco;
	private String numero;
	private String complemento;
	private String bairro;
	private String cidade;
	private String cep;
	private String uf;
	
	
	public Cliente() {
		
	}


	/**
	 * Construtor de Cliente
	 * @param nome : String
	 * @param sexo : String
	 * @param email : String
	 * @param senha : String
	 */
	public Cliente(String nome, String sexo, String email, String senha) {
		this.nome = nome;
		this.sexo = SexoCliente.converterParaEnum(sexo).getSexoAbreviado();
		this.email = email;
		adicionaPerfis(PerfilCliente.CLIENTE);
		this.senha = senha;
	}

	
	public Long getId() {
		return id;
	}

	public String getNome() {
		return nome;
	}

	public String getEmail() {
		return email;
	}

	public Set<PerfilCliente> getPerfis() {
		return perfis.stream().map(perfil -> PerfilCliente.converterParaEnum(perfil)).collect(Collectors.toSet());
	}
	
	public String getSenha() {
		return senha;
	}

	public LocalDate getDataNascimento() {
		return dataNascimento;
	}

	public SexoCliente getSexo() {
		return SexoCliente.converterParaEnum(sexo);
	}

	public String getTelefone() {
		return telefone;
	}

	public String getEndereco() {
		return endereco;
	}

	public String getNumero() {
		return numero;
	}

	public String getComplemento() {
		return complemento;
	}

	public String getBairro() {
		return bairro;
	}

	public String getCidade() {
		return cidade;
	}

	public String getCep() {
		return cep;
	}

	public String getUf() {
		return uf;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void adicionaPerfis(PerfilCliente perfil) {
		this.perfis.add(perfil.getCodigo());
	}

	public void setDataNascimento(LocalDate dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public void setSexo(SexoCliente sexo) {
		this.sexo = sexo.getSexoAbreviado();
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public void setUf(String uf) {
		this.uf = uf;
	}
}
