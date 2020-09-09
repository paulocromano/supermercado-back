package com.romano.Supermercado.compra.pedido.dto;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.romano.Supermercado.cliente.model.Cliente;
import com.romano.Supermercado.compra.itemPedido.model.ItemPedido;
import com.romano.Supermercado.compra.pedido.enums.StatusPedido;
import com.romano.Supermercado.compra.pedido.model.Pedido;
import com.romano.Supermercado.localidade.endereco.model.Endereco;

/**
 * 
 * @author Paulo Romano - [paulo-romano_133@hotmail.com]
 * Classe DTO de Pedido
 */
public class PedidoDTO {

	private Long id;
	private String dataHoraPedidoFinalizado;
	private Double total;
	private StatusPedido statusPedido;
	private Cliente cliente;
	private Endereco enderecoEntrega;
	private Set<ItemPedido> itens = new HashSet<>();
	
	
	/**
	 * Construtor
	 * @param pedido : Pedido
	 */
	public PedidoDTO(Pedido pedido) {
		id = pedido.getId();
		dataHoraPedidoFinalizado = pedido.getDataHoraPedidoFinalizado();
		total = pedido.getTotal();
		statusPedido = pedido.getStatusPedido();
		cliente = pedido.getCliente();
		enderecoEntrega = pedido.getEnderecoEntrega();
		itens = pedido.getItens();
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
		return statusPedido;
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
	
	
	/**
	 * Método responsável por converter um List de Pedido para PedidoDTO
	 * @param listaPedidos : List<Pedido>
	 * @return List<PedidoDTO>
	 */
	public static List<PedidoDTO> converterParaListaPedidoDTO(List<Pedido> listaPedidos) {
		return listaPedidos.stream().map(PedidoDTO::new).collect(Collectors.toList());
	}
}
