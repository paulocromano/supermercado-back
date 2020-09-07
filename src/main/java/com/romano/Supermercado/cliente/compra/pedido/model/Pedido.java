package com.romano.Supermercado.cliente.compra.pedido.model;

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

import com.romano.Supermercado.cliente.compra.itemPedido.model.ItemPedido;
import com.romano.Supermercado.cliente.compra.pedido.enums.StatusPedido;
import com.romano.Supermercado.cliente.model.Cliente;
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
	private Integer id;
	
	@Column(name = "data_hora_pedido")
	private String dataHoraPedidoFinalizado;
	
	@Column(name = "preco_total")
	private Double total;
	
	@Column(name = "status_pedido")
	private Integer statusPedido;
	
	@ManyToOne
	@JoinColumn(name = "cliente_id")
	private Cliente cliente;
	
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


	public Integer getId() {
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
	 * Método responsável por efetuar as operações quando o Usuário efetivar uma compra
	 */
	public void compraFinalizada() {
		dataHoraPedidoFinalizado = Converter.localDateTimeAtualParaString();
		statusPedido = StatusPedido.COMPRA_REALIZADA.getCodigo();
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
