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
	
	@ManyToOne
	@JoinColumn(name = "cliente_id")
	private Cliente cliente;
	
	@OneToMany(mappedBy="id.pedido")
	private Set<ItemPedido> itens = new HashSet<>();

	
	public Integer getId() {
		return id;
	}

	public String getDataHora() {
		return dataHora;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public Set<ItemPedido> getItens() {
		return itens;
	}	
}
