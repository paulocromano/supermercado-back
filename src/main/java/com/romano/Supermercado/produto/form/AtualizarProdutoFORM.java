package com.romano.Supermercado.produto.form;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.NumberFormat;

import com.romano.Supermercado.produto.model.Produto;


/**
 * 
 * @author Paulo Romano - [paulo-romano_133@hotmail.com]
 * Classe responsável por atualizar as informações de um Produto
 */
public class AtualizarProdutoFORM {

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
	
	@Size(max = 100, message = "Observações excedeu o limite de {max} caracteres!")
	private String observacoes;

	
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

	public String getObservacoes() {
		return observacoes;
	}

	public void setObservacoes(String observacoes) {
		this.observacoes = observacoes;
	}
	
	
	/**
	 * Método responsável por atualizar o Produto com os novos dados
	 * @param produto : Produto
	 */
	public void atualizarProduto(Produto produto) {
		produto.setPreco(preco);
		produto.setDesconto(desconto);
		produto.setEstoque(estoque);
		produto.setEstoqueMinimo(estoqueMinimo);
		produto.atualizarStatusProduto();
		produto.setObservacoes(observacoes);		
	}
}
