package com.romano.Supermercado.produto.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.romano.Supermercado.compra.itemPedido.model.ItemPedido;
import com.romano.Supermercado.compra.pedido.model.Pedido;
import com.romano.Supermercado.produto.enums.StatusProduto;
import com.romano.Supermercado.setor.model.Setor;


/**
 * 
 * @author Paulo Romano - [paulo-romano_133@hotmail.com]
 * Classe Entidade de Produto
 */
@JsonIgnoreProperties(value = {"dataValidade", "preco", "desconto", "estoque", "estoqueMinimo", "statusProduto", "observacoes"})
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
	
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "id_setor")
	private Setor setor;
	
	@JsonIgnore
	@OneToMany(mappedBy = "id.produto")
	private Set<ItemPedido> itens = new HashSet<>();
	
	
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
	

	@JsonIgnore
	public List<Pedido> getPedidos() {
		List<Pedido> lista = new ArrayList<>();
		
		for (ItemPedido itemPedidoAtual : itens) {
			lista.add(itemPedidoAtual.getPedido());
		}
		
		return lista;
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
	
	public Set<ItemPedido> getItens() {
		return itens;
	}

	public void setItens(Set<ItemPedido> itens) {
		this.itens = itens;
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
	
	
	/**
	 * Método responsável por acrescentar no estoque do Produto a quantidade informada
	 * @param quantidade : Integer
	 */
	public void somarEstoqueProduto(Integer quantidade) {
		if (quantidade < 1) {
			throw new IllegalArgumentException("A quantidade deve ser maior do que 0!");
		}
		estoque += quantidade;
	}
	
	
	/**
	 * Método responsável por diminuir no estoque do Produto com base na quantidade informada
	 * @param quantidade : Integer
	 */
	public void diminuirEstoqueProduto(Integer quantidade) {
		if (quantidade < 1) {
			throw new IllegalArgumentException("A Quantidade deve ser maior do que 0!");
		}
		
		if (quantidade > estoque) {
			throw new IllegalArgumentException("Quantidade excedeu o limite de estoque!");
		}
		
		estoque -= quantidade;
		
		if (estoque == 0) {
			statusProduto = StatusProduto.ESGOTADO.getCodigo();
		}
	}
}
