package com.romano.Supermercado.cliente.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.romano.Supermercado.cliente.compra.pedido.model.Pedido;
import com.romano.Supermercado.cliente.enums.PerfilCliente;
import com.romano.Supermercado.cliente.enums.SexoCliente;
import com.romano.Supermercado.cliente.localidade.endereco.model.Endereco;


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
	
	@OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL)
	private List<Endereco> enderecos = new ArrayList<>();
	
	@JsonIgnore
	@OneToMany(mappedBy="cliente", cascade = CascadeType.ALL)
	private List<Pedido> pedidos = new ArrayList<>();
	
	
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

	public List<Endereco> getEnderecos() {
		return enderecos;
	}
	
	public List<Pedido> getPedidos() {
		return pedidos;
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

	public void adicionarEndereco(Endereco endereco) {
		enderecos.add(endereco);
	}
	
	public void adcionarPedido(Pedido pedido) {
		pedidos.add(pedido);
	}
}
