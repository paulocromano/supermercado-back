package com.romano.Supermercado.produto.dto;

import java.util.List;
import java.util.stream.Collectors;

import com.romano.Supermercado.produto.enums.StatusProduto;
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
	private Double preco;
	private Double desconto;
	private Integer estoque;
	private StatusProduto statusProduto = StatusProduto.ESGOTADO;
	private String observacoes;
	private Setor setor;
	
	
	public ProdutoDTO(Produto produto) {
		id = produto.getId();
		nome = produto.getNome();
		marca = produto.getMarca();
		dataValidade = Converter.converterLocalDateParaString(produto.getDataValidade());
		preco = produto.getPreco();
		desconto = produto.getDesconto();
		estoque = produto.getEstoque();
		statusProduto = produto.getStatusProduto();
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

	public Double getPreco() {
		return preco;
	}

	public Double getDesconto() {
		return desconto;
	}

	public Integer getEstoque() {
		return estoque;
	}

	public StatusProduto getStatusProduto() {
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
}
