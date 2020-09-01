package com.romano.Supermercado.produto.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.romano.Supermercado.produto.enums.StatusProduto;
import com.romano.Supermercado.setor.model.Setor;


/**
 * 
 * @author Paulo Romano - [paulo-romano_133@hotmail.com]
 * Classe Entidade de Produto
 */
@Entity
@Table(name = "produto")
public class Produto {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@NotNull(message = "Nome do Produto não informado!")
	@NotBlank(message = "Nome do Produto não pode estar vazio!")
	@Size(min = 3, max = 40, message = "O campo nome deve ter entre 3 a 40 caracteres!")
	private String nome;
	
	@NotNull(message = "Nome da Marca não informada!")
	@NotBlank(message = "Nome da Marca não pode estar vazio!")
	@Size(max = 30, message = "Nome da Marca excede o limite de caracteres!")
	private String marca;
	
	@NotNull(message = "Data de Nascimento não informada!")
	@Column(name = "data_validade")
	private LocalDate dataValidade;
	
	@NotNull(message = "Preço não informado!")
	@NotBlank(message = "Preço não pode estar vazio!")
	private Double preco;
	
	private Double desconto;
	private Integer estoque;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "status_produto")
	private StatusProduto statusProduto = StatusProduto.ESGOTADO;
	
	private String observacoes;
	
	@ManyToOne
	@JoinColumn(name = "setor_id")
	private Setor setor;
	
	
	public Produto() {
		
	}

	/**
	 * Construtor de Produto
	 * @param nome : String
	 * @param marca : String
	 * @param dataValidade : LocalDate
	 * @param preco : Double
	 * @param desconto : Double
	 * @param estoque : Integer
	 * @param statusProduto : StatusProduto (Enum)
	 * @param observacoes : String
	 * @param setor : Setor
	 */
	public Produto(String nome, String marca, LocalDate dataValidade, Double preco, Double desconto, Integer estoque, 
			StatusProduto statusProduto, String observacoes, Setor setor) {
		this.nome = nome;
		this.marca = marca;
		this.dataValidade = dataValidade;
		this.preco = preco;
		this.desconto = desconto;
		this.estoque = estoque;
		this.statusProduto = statusProduto;
		this.observacoes = observacoes;
		this.setor = setor;
	}


	public Double getPreco() {
		return preco;
	}

	public void setPreco(Double preco) {
		this.preco = preco;
	}

	public Double getDesconto() {
		return desconto;
	}

	public void setDesconto(Double desconto) {
		this.desconto = desconto;
	}

	public Integer getEstoque() {
		return estoque;
	}

	public void setEstoque(Integer estoque) {
		this.estoque = estoque;
	}

	public StatusProduto getStatusProduto() {
		return statusProduto;
	}

	public void setStatusProduto(StatusProduto statusProduto) {
		this.statusProduto = statusProduto;
	}

	public String getObservacoes() {
		return observacoes;
	}

	public void setObservacoes(String observacoes) {
		this.observacoes = observacoes;
	}

	public Integer getId() {
		return id;
	}

	public String getNome() {
		return nome;
	}

	public String getMarca() {
		return marca;
	}

	public LocalDate getDataValidade() {
		return dataValidade;
	}
	
	public Setor getSetor() {
		return setor;
	}
}
