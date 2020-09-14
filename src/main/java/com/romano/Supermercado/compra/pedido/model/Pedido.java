package com.romano.Supermercado.compra.pedido.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
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
import com.romano.Supermercado.cliente.model.Cliente;
import com.romano.Supermercado.compra.itemPedido.model.ItemPedido;
import com.romano.Supermercado.compra.itemPedido.repository.ItemPedidoRepository;
import com.romano.Supermercado.compra.pedido.enums.StatusPedido;
import com.romano.Supermercado.localidade.endereco.model.Endereco;
import com.romano.Supermercado.produto.model.Produto;
import com.romano.Supermercado.produto.repository.ProdutoRepository;
import com.romano.Supermercado.utils.Converter;

/**
 * 
 * @author Paulo Romano - [paulo-romano_133@hotmail.com]
 * Classe Entidade responsável pelas informações do {@link Pedido}
 */
@Entity
@Table(name = "pedido")
public class Pedido {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "data_hora_pedido")
	private String dataHoraPedidoFinalizado;
	
	@Column(name = "preco_total")
	private Double total;
	
	@Column(name = "status_pedido")
	private Integer statusPedido;
	
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "cliente_id")
	private Cliente cliente;
	
	@ManyToOne
	@JoinColumn(name = "endereco_entrega_id")
	private Endereco enderecoEntrega;
	
	@JsonIgnore
	@OneToMany(mappedBy="id.pedido", cascade = CascadeType.MERGE)
	private Set<ItemPedido> itens = new HashSet<>();

	
	public Pedido() {

	}
	
	
	/**
	 * Construtor
	 * @param cliente : {@link Cliente}
	 */
	public Pedido(Cliente cliente) {
		statusPedido = StatusPedido.ABERTO.getCodigo();
		this.cliente = cliente;
		total = 0.0D;
	}


	public Long getId() {
		return id;
	}

	public String getDataHoraPedidoFinalizado() {
		return dataHoraPedidoFinalizado;
	}
	
	public Double getTotal() {
		return total;
	}
	
	public StatusPedido getStatusPedido() {
		return StatusPedido.converterParaEnum(statusPedido);
	}

	public Cliente getCliente() {
		return cliente;
	}
	
	public Endereco getEnderecoEntrega() {
		return enderecoEntrega;
	}

	public Set<ItemPedido> getItens() {
		return itens;
	}	
	
	public void setTotal(Double total) {
		this.total = total;
	}
	
	public void setItens(Set<ItemPedido> itens) {
		this.itens = itens;
	}
	
	
	/**
	 * Método responsável por atualizar o {@link Pedido} com a nova quantidade informada de um {@link Produto}
	 * @param produto : {@link Produto}
	 * @param itemPedido : {@link ItemPedido}
	 * @param novaQuantidade : Integer
	 */
	public void atualizarPrecoTotalDeProduto(Produto produto, ItemPedido itemPedido, Integer novaQuantidade) {
		total -= itemPedido.getPreco() * itemPedido.getQuantidade();
		itemPedido.atualizarItemPedido(produto, novaQuantidade);	
		
		total += itemPedido.getPreco() * itemPedido.getQuantidade();
	}
	
	
	/**
	 * Método responsável por adicionar um {@link Produto} ao {@link Pedido} e atualizar o valor total
	 * @param itemPedido : {@link ItemPedido}
	 */
	public void adicionarItemPedido(ItemPedido itemPedido) {
		itens.add(itemPedido);
		total += itemPedido.getPreco() * itemPedido.getQuantidade();
	}
	
	
	/**
	 * Método responsável por remover um {@link Produto} do {@link Pedido} e atualizar o valor total
	 * @param produtoASerRemovido : {@link Produto}
	 * @param itemPedidoRepository : {@link ItemPedidoRepository}
	 */
	public void removerItemPedido(Produto produtoASerRemovido, ItemPedidoRepository itemPedidoRepository) {
		itens.forEach(item -> {
			if (item.getProduto().equals(produtoASerRemovido)) {
				total -= item.getPreco();
				itemPedidoRepository.delete(item);
			}
		});
	}
	
	
	/**
	 * Método responsável por efetuar as operações quando o Usuário efetivar uma compra
	 * @param endereco : {@link Endereco}
	 * @param produtoRepository : {@link ProdutoRepository}
	 */
	public void finalizarPedido(Endereco endereco, ProdutoRepository produtoRepository) {
		dataHoraPedidoFinalizado = Converter.localDateTimeAtualParaString();
		statusPedido = StatusPedido.COMPRA_REALIZADA.getCodigo();
		enderecoEntrega = endereco;
		
		atualizarEstoqueProdutosComprados(produtoRepository);
	}
	
	
	/**
	 * Método responsável por atualizar o estoque do {@link Produto} que foi comprado
	 * @param produtoRepository : {@link ProdutoRepository}
	 */
	private void atualizarEstoqueProdutosComprados(ProdutoRepository produtoRepository) {
		itens.forEach(item -> {
			Produto produto = produtoRepository.getOne(item.getProduto().getId());
			produto.diminuirEstoqueProduto(item.getQuantidade());
		});
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Pedido other = (Pedido) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
