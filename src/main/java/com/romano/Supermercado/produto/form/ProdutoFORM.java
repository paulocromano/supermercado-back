package com.romano.Supermercado.produto.form;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.NumberFormat;

import com.romano.Supermercado.produto.model.Produto;
import com.romano.Supermercado.setor.model.Setor;
import com.romano.Supermercado.utils.Converter;

/**
 * 
 * @author Paulo Romano - [paulo-romano_133@hotmail.com]
 * Classe responsável por receber um novo Produto
 */
public class ProdutoFORM {

	@NotNull(message = "Campo Produto não informado!")
	@NotEmpty(message = "O campo Produto não pode estar vazio!")
	@Size(min = 3, max = 40, message = "O campo nome deve ter entre {min} a {max} caracteres!")
	private String nome;
	
	@NotNull(message = "Campo Marca não informada!")
	@NotEmpty(message = "O campo Marca não pode estar vazio!")
	@Size(max = 30, message = "Nome da Marca excedeu o limite de {max} caracteres!")
	private String marca;
	
	@NotNull(message = "Data de Validade não informada!")
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private String dataValidade;
	
	@NotNull(message = "Preço não informado!")
	@NumberFormat(pattern = "#.##")
	private Double preco;
	
	@NotNull(message = "Desconto não informado!")
	@NumberFormat(pattern = "#.##")
	private Double desconto;
	
	@NotNull(message = "Estoque não informado!")
	@Digits(integer = 6, fraction = 0, message = "Tamanho do Estoque excedeu o limite! ({integer} digitos)")
	private Integer estoque;
	
	@NotNull(message = "Estoque Mínimo não informado!")
	@Digits(integer = 4, fraction = 0, message = "Tamanho do Estoque Mínimo excedeu o limite! ({integer} digitos)")
	private Integer estoqueMinimo;
	
	@Size(max = 100, message = "O campo Observações excedeu o limite de {max} caracteres!")
	private String observacoes;
	
	@NotNull(message = "Setor não informado!")
	private Setor setor;

	
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getMarca() {
		return marca;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}

	public String getDataValidade() {
		return dataValidade;
	}

	public void setDataValidade(String dataValidade) {
		this.dataValidade = dataValidade;
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
		return estoque;
	}

	public void setEstoqueMinimo(Integer estoqueMinimo) {
		this.estoqueMinimo = estoqueMinimo;
	}

	public String getObservacoes() {
		return observacoes;
	}

	public void setObservacoes(String observacoes) {
		this.observacoes = observacoes;
	}

	public Setor getSetor() {
		return setor;
	}

	public void setSetor(Setor setor) {
		this.setor = setor;
	}
	
	
	/**
	 * Método responsável por converter ProdutoFORM para Produto
	 * @return Produto - Produto convertido
	 */
	public Produto converterParaProduto() {
		return new Produto(nome, marca, Converter.stringParaLocalDate(dataValidade), preco, desconto, estoque, estoqueMinimo, observacoes, setor);
	}
}
