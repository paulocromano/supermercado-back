package com.romano.Supermercado.produto.form;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.NumberFormat;

import com.romano.Supermercado.produto.enums.StatusProduto;
import com.romano.Supermercado.produto.model.Produto;
import com.romano.Supermercado.setor.model.Setor;
import com.romano.Supermercado.utils.Converter;

/**
 * 
 * @author Paulo Romano - [paulo-romano_133@hotmail.com]
 * Classe responsável por receber um novo Produto
 */
public class ProdutoFORM {

	@NotNull(message = "Nome do Produto não informado!")
	@NotBlank(message = "Nome do Produto não pode estar vazio!")
	@Size(min = 3, max = 40, message = "O campo nome deve ter entre 3 a 40 caracteres!")
	private String nome;
	
	@NotNull(message = "Nome da Marca não informada!")
	@NotBlank(message = "Nome da Marca não pode estar vazio!")
	@Size(max = 30, message = "Nome da Marca excedeu o limite de caracteres!")
	private String marca;
	
	@NotNull(message = "Data de Nascimento não informada!")
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private String dataValidade;
	
	@NotNull(message = "Preço não informado!")
	@NotBlank(message = "Preço não pode estar vazio!")
	@NumberFormat(pattern = "#.##")
	private Double preco;
	
	@NotNull(message = "Desconto não informado!")
	@NotBlank(message = "Desconto não pode estar vazio!")
	@NumberFormat(pattern = "#.##")
	private Double desconto;
	
	@NotNull(message = "Estoque não informado!")
	@NotBlank(message = "Estoque não pode estar vazio!")
	@Digits(integer = 6, fraction = 0, message = "Tamanho do Estoque excedeu o limite!")
	private Integer estoque;
	
	@NotNull(message = "Estoque Mínimo não informado!")
	@NotBlank(message = "Estoque Mínimo não pode estar vazio!")
	@Digits(integer = 4, fraction = 0, message = "Tamanho do Estoque Mínimo excedeu o limite!")
	private Integer estoqueMinimo;
	
	@Size(max = 100, message = "Observações excedeu o limite de caracteres!")
	private String observacoes;
	
	@NotNull(message = "Setor não informado!")
	@NotBlank(message = "Setor não pode estar vazio!")
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
		return new Produto(nome, marca, Converter.converterStringParaLocalDate(dataValidade), preco, desconto, estoque, 
				estoqueMinimo, setarStatusProduto(), observacoes, setor);
	}
	
	
	/**
	 * Método responsável por setar o Status do Produto conforme a quantidade em estoque
	 * @return StatusProduto
	 */
	private StatusProduto setarStatusProduto() {
		if (estoque == 0) {
			return StatusProduto.ESGOTADO;
		}
		else if (estoque < estoqueMinimo) {
			return StatusProduto.ESTOQUE_BAIXO;
		}
		
		return StatusProduto.ATIVO;
	}
}
