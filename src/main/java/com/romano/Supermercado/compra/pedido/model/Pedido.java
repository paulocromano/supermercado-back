package com.romano.Supermercado.compra.pedido.model;

import java.util.HashSet;
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
import com.romano.Supermercado.cliente.model.Cliente;
import com.romano.Supermercado.compra.itemPedido.model.ItemPedido;
import com.romano.Supermercado.compra.itemPedido.repository.ItemPedidoRepository;
import com.romano.Supermercado.compra.pedido.enums.StatusPedido;
import com.romano.Supermercado.localidade.endereco.model.Endereco;
import com.romano.Supermercado.produto.model.Produto;
import com.romano.Supermercado.utils.Converter;

/**
 * 
 * @author Paulo Romano - [paulo-romano_133@hotmail.com]
 * Classe Entidade responsável pelas informações do Pedido
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
	@OneToMany(mappedBy="id.pedido")
	private Set<ItemPedido> itens = new HashSet<>();

	
	public Pedido() {

	}
	
	
	/**
	 * Construtor
	 * @param cliente : Cliente
	 */
	public Pedido(Cliente cliente) {
		statusPedido = StatusPedido.ABERTO.getCodigo();
		this.cliente = cliente;
		total = 0D;
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
	
	public void setItens(Set<ItemPedido> itens) {
		this.itens = itens;
	}
	
	
	/**
	 * Método responsável por adicionar um Produto ao Pedido e atualizar o valor total
	 * @param itemPedido : ItemPedido
	 */
	public void adicionarItemPedido(ItemPedido itemPedido) {
		itens.add(itemPedido);
		total += itemPedido.getPreco() * itemPedido.getQuantidade();
	}
	
	
	/**
	 * Método responsável por remover um Produto do Pedido e atualizar o valor total
	 * @param itemPedido : ItemPedido
	 * @param produtoASerRemovido : Produto
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
	 */
	public void compraFinalizada(Endereco endereco) {
		dataHoraPedidoFinalizado = Converter.localDateTimeAtualParaString();
		statusPedido = StatusPedido.COMPRA_REALIZADA.getCodigo();
		enderecoEntrega = endereco;
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
