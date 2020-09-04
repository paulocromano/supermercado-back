package com.romano.Supermercado.cliente.dto;

import java.util.List;
import java.util.stream.Collectors;

import com.romano.Supermercado.cliente.model.Cliente;
import com.romano.Supermercado.utils.Converter;

/**
 * 
 * @author Paulo Romano - [paulo-romano_133@hotmail.com]
 * Classe DTO de Cliente
 */
public class ClienteDTO {
	
	private Long id; 
	private String nome;
	private String email;
	private Integer perfil;
	private String senha;
	private String dataNascimento;
	private String sexo;
	private String telefone;
	private String endereco;
	private String numero;
	private String complemento;
	private String bairro;
	private String cidade;
	private String cep;
	private String uf;
	
	
	/**
	 * Construtor de ClienteDTO
	 * @param cliente : Cliente
	 */
	public ClienteDTO(Cliente cliente) {
		id = cliente.getId();
		nome = cliente.getNome();
		email = cliente.getEmail();
		perfil = cliente.getPerfil();
		senha = cliente.getSenha();
		
		if (cliente.getDataNascimento() != null) {
			dataNascimento = Converter.localDateParaString(cliente.getDataNascimento());
		}
		
		sexo = cliente.getSexo().getSexoPorExtenso();
		telefone = cliente.getTelefone();
		endereco = cliente.getEndereco();
		numero = cliente.getNumero();
		complemento = cliente.getComplemento();
		bairro = cliente.getBairro();
		cidade = cliente.getCidade();
		cep = cliente.getCep();
		uf = cliente.getUf();
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

	public Integer getPerfil() {
		return perfil;
	}
	
	public String getSenha() {
		return senha;
	}

	public String getDataNascimento() {
		return dataNascimento;
	}

	public String getSexo() {
		return sexo;
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

	
	/**
	 * Método responsável por converter um List de Cliente para List de ClienteDTO
	 * @param listaClientes : List<Cliente>
	 * @return : List<ClienteDTO>
	 */
	public static List<ClienteDTO> converterParaListaClienteDTO(List<Cliente> listaClientes) {
		return listaClientes.stream().map(ClienteDTO::new).collect(Collectors.toList());
	}
}
