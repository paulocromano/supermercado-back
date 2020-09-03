package com.romano.Supermercado.produto.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

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
	
	private String nome;
	private String marca;
	private LocalDate dataValidade;
	private Double preco;
	private Double desconto;
	private Integer estoque;
	
	@Column(name = "estoque_minimo")
	private Integer estoqueMinimo;
	
	@Column(name = "status_produto")
	private Integer statusProduto;
	
	private String observacoes;
	
	@ManyToOne
	@JoinColumn(name = "id_setor")
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
	 * @param estoqueMinimo : Integer
	 * @param statusProduto : StatusProduto (Enum)
	 * @param observacoes : String
	 * @param setor : Setor
	 */
	public Produto(String nome, String marca, LocalDate dataValidade, Double preco, Double desconto, Integer estoque, 
			Integer estoqueMinimo, String observacoes, Setor setor) {
		this.nome = nome;
		this.marca = marca;
		this.dataValidade = dataValidade;
		this.preco = preco;
		this.desconto = desconto;
		this.estoque = estoque;
		this.estoqueMinimo = estoqueMinimo;
		atualizarStatusProduto();
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
	
	public Integer getEstoqueMinimo() {
		return estoqueMinimo;
	}

	public void setEstoqueMinimo(Integer estoqueMinimo) {
		this.estoqueMinimo = estoqueMinimo;
	}

	public StatusProduto getStatusProduto() {
		return StatusProduto.converterParaEnum(statusProduto);
	}

	public void setStatusProduto(StatusProduto statusProduto) {
		this.statusProduto = statusProduto.getCodigo();
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
	
	
	/**
	 * Método para atualizar o preço do Produto caso ele tenha desconto
	 * @param preco : Double
	 * @param desconto : Double
	 * @return Double - Preço atualizado caso tenha desconto
	 */
	public static Double temDesconto(Double preco, Double desconto) {
		if (desconto != 0D) {
			preco -= (preco * desconto) / 100.0D; 
		}
		
		return preco;
	}
	
	/**
	 * Método responsável por atualizar o Status do Produto conforme a quantidade em estoque
	 */
	public void atualizarStatusProduto() {
		if (estoque == 0) {
			statusProduto = StatusProduto.ESGOTADO.getCodigo();
		}
		
		else if (estoque < estoqueMinimo) {
			statusProduto = StatusProduto.ESTOQUE_BAIXO.getCodigo();
		}
		
		else {
			statusProduto = StatusProduto.ATIVO.getCodigo();
		}
	}
}
