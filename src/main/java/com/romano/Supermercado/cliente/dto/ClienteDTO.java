package com.romano.Supermercado.cliente.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.romano.Supermercado.cliente.enums.PerfilCliente;
import com.romano.Supermercado.cliente.model.Cliente;
import com.romano.Supermercado.compra.pedido.model.Pedido;
import com.romano.Supermercado.localidade.endereco.model.Endereco;
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
	private Set<PerfilCliente> perfis;
	private String dataNascimento;
	private String sexo;
	private String telefone;
	private List<Endereco> enderecos = new ArrayList<>();
	private List<Pedido> pedidos = new ArrayList<>();
	
	
	/**
	 * Construtor de ClienteDTO
	 * @param cliente : {@link Cliente}
	 */
	public ClienteDTO(Cliente cliente) {
		id = cliente.getId();
		nome = cliente.getNome();
		email = cliente.getEmail();
		perfis = cliente.getPerfis();
		
		if (cliente.getDataNascimento() != null) {
			dataNascimento = Converter.localDateParaString(cliente.getDataNascimento());
		}
		
		sexo = cliente.getSexo().getSexoPorExtenso();
		telefone = cliente.getTelefone();
		enderecos = cliente.getEnderecos();
		pedidos = cliente.getPedidos();
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
		return perfis;
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

	public List<Endereco> getEnderecos() {
		return enderecos;
	}
	
	public List<Pedido> getPedidos() {
		return pedidos;
	}

	
	/**
	 * Método responsável por converter um List de {@link Cliente} para List de {@link ClienteDTO}
	 * @param listaClientes : List<Cliente>
	 * @return : List<ClienteDTO>
	 */
	public static List<ClienteDTO> converterParaListaClienteDTO(List<Cliente> listaClientes) {
		return listaClientes.stream().map(ClienteDTO::new).collect(Collectors.toList());
	}
}
