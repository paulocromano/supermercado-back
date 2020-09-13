package com.romano.Supermercado.produto.dto;

import java.util.List;
import java.util.stream.Collectors;

import com.romano.Supermercado.produto.model.Produto;
import com.romano.Supermercado.setor.model.Setor;
import com.romano.Supermercado.utils.Converter;

/**
 * 
 * @author Paulo Romano - [paulo-romano_133@hotmail.com]
 * Classe DTO de Produto
 */
public class ProdutoDTO {

	private Integer id;
	private String nome;
	private String marca;
	private String dataValidade;
	private String preco;
	private Double desconto;
	private String precoComDesconto;
	private Integer estoque;
	private Integer estoqueMinimo;
	private String statusProduto;
	private String observacoes;
	private Setor setor;
	
	
	public ProdutoDTO(Produto produto) {
		id = produto.getId();
		nome = produto.getNome();
		marca = produto.getMarca();
		dataValidade = Converter.localDateParaString(produto.getDataValidade());
		preco = Converter.doubleParaStringComDuasCasasDecimais(produto.getPreco());
		desconto = produto.getDesconto();
		precoComDesconto = Converter.doubleParaStringComDuasCasasDecimais(temDesconto(produto.getPreco()));
		estoque = produto.getEstoque();
		estoqueMinimo = produto.getEstoqueMinimo();
		statusProduto = produto.getStatusProduto().getDescricao();
		observacoes = produto.getObservacoes();
		setor = produto.getSetor();
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

	public String getDataValidade() {
		return dataValidade;
	}

	public String getPreco() {
		return preco;
	}

	public Double getDesconto() {
		return desconto;
	}
	
	public String getPrecoComDesconto() {
		return precoComDesconto;
	}

	public Integer getEstoque() {
		return estoque;
	}
	
	public Integer getEstoqueMinimo() {
		return estoqueMinimo;
	}

	public String getStatusProduto() {
		return statusProduto;
	}

	public String getObservacoes() {
		return observacoes;
	}

	public Setor getSetor() {
		return setor;
	}
	
	
	/**
	 * Método responsável por converter um List de Produto para List de ProdutoDTO
	 * @param listaProdutos : List<Produto>
	 * @return : List<ProdutoDTO>
	 */
	public static List<ProdutoDTO> converterParaListaProdutoDTO(List<Produto> listaProdutos) {
		return listaProdutos.stream().map(ProdutoDTO::new).collect(Collectors.toList());
	}
	
	
	/**
	 * Método para atualizar o preço do Produto caso ele tenha desconto
	 * @return Double - Preço atualizado caso tenha desconto
	 */
	private Double temDesconto(Double preco) {
		return (desconto != 0D) ? preco - (preco * desconto) / 100.0D : 0.0D;
	}
}
