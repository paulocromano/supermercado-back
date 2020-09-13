package com.romano.Supermercado.cliente.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
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
import com.romano.Supermercado.cliente.enums.PerfilCliente;
import com.romano.Supermercado.cliente.enums.SexoCliente;
import com.romano.Supermercado.cliente.repository.ClienteRepository;
import com.romano.Supermercado.compra.pedido.model.Pedido;
import com.romano.Supermercado.exception.service.ObjectNotFoundException;
import com.romano.Supermercado.localidade.endereco.model.Endereco;


/**
 * 
 * @author Paulo Romano - [paulo-romano_133@hotmail.com]
 * Classe Entidade de {@link Cliente}
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
	
	@JsonIgnore
	@OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL)
	private List<Endereco> enderecos = new ArrayList<>();
	
	@JsonIgnore
	@OneToMany(mappedBy="cliente", cascade = CascadeType.ALL)
	private List<Pedido> pedidos = new ArrayList<>();
	
	
	public Cliente() {
		
	}


	/**
	 * Construtor de {@link Cliente}
	 * @param nome : String
	 * @param sexo : String
	 * @param email : String
	 * @param senha : String
	 */
	public Cliente(String nome, String sexo, String email, String senha) {
		this.nome = nome;
		this.sexo = SexoCliente.converterParaEnum(sexo).getSexoAbreviado();
		this.email = email;
		adicionarPerfis(PerfilCliente.CLIENTE);
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

	public void adicionarPerfis(PerfilCliente perfil) {
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
	
	public void adicionarPedido(Pedido pedido) {
		pedidos.add(pedido);
	}
	
	
	/**
	 * Método responsável por verificar se o {@link Cliente} existe
	 * @param clienteRepository : {@link ClienteRepository}
	 * @param id : Long
	 * @return {@link Cliente} - Caso o cliente exista
	 */
	public static final Cliente existeCliente(ClienteRepository clienteRepository, Long id) {
		Optional<Cliente> cliente = clienteRepository.findById(id);
		
		if (cliente.isEmpty()) {
			throw new ObjectNotFoundException("Cliente não encontrado!");
		}
		
		return cliente.get();
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Cliente other = (Cliente) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
