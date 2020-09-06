package com.romano.Supermercado.cliente.localidade.endereco.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.romano.Supermercado.cliente.localidade.cidade.model.Cidade;
import com.romano.Supermercado.cliente.model.Cliente;

@Entity
@Table(name = "endereco")
public class Endereco {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	private String logradouro;
	private String numero;
	private String complemento;
	private String bairro;
	private String cep;
	
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "cliente_id")
	private Cliente cliente;
	
	@ManyToOne
	@JoinColumn(name = "cidade_id")
	private Cidade cidade;
	
	
	public Endereco() {

	}


	/**
	 * Construtor
	 * @param logradouro : String
	 * @param numero : String
	 * @param complemento : String
	 * @param bairro : String
	 * @param cep : String
	 * @param cliente : Cliente
	 * @param cidade: Cidade
	 */
	public Endereco(String logradouro, String numero, String complemento, String bairro, String cep, Cliente cliente, Cidade cidade) {
		this.logradouro = logradouro;
		this.numero = numero;
		this.complemento = complemento;
		this.bairro = bairro;
		this.cep = cep;
		this.cliente = cliente;
		this.cidade = cidade;
	}


	public Integer getId() {
		return id;
	}

	public String getLogradouro() {
		return logradouro;
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

	public String getCep() {
		return cep;
	}
	
	public Cliente getCliente() {
		return cliente;
	}
	
	public Cidade getCidade() {
		return cidade;
	}
}
