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
	private String dataHora;
	
	@Column(name = "preco_total")
	private Double total;
	
	@ManyToOne
	@JoinColumn(name = "cliente_id")
	private Cliente cliente;
	
	@OneToMany(mappedBy="id.pedido")
	private Set<ItemPedido> itens = new HashSet<>();

	
	public Pedido() {

	}
	
	
	public Pedido( Cliente cliente, Set<ItemPedido> itens) {
		this.dataHora = Converter.localDateTimeAtualParaString();
		this.cliente = cliente;
		this.itens = itens;
		total = calculoTotalPedido();
	}


	public Integer getId() {
		return id;
	}

	public String getDataHora() {
		return dataHora;
	}
	
	public Double getTotal() {
		return total;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public Set<ItemPedido> getItens() {
		return itens;
	}	
	
	
	/**
	 * Método responsável por calcular o total do Pedido
	 * @return Double - Total
	 */
	private Double calculoTotalPedido() {
		total = 0D;
		
		for (ItemPedido itemAtual : itens) {
			total += itemAtual.getPreco() * itemAtual.getQuantidade();
		}
		
		return total;
	}
}
