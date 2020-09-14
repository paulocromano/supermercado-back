package com.romano.Supermercado.compra.pedido.dto;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.romano.Supermercado.cliente.model.Cliente;
import com.romano.Supermercado.compra.itemPedido.model.ItemPedido;
import com.romano.Supermercado.compra.pedido.model.Pedido;
import com.romano.Supermercado.localidade.endereco.model.Endereco;
import com.romano.Supermercado.utils.Converter;

/**
 * 
 * @author Paulo Romano - [paulo-romano_133@hotmail.com]
 * Classe DTO de {@link Pedido}
 */
public class PedidoDTO {

	private Long id;
	private String dataHoraPedidoFinalizado;
	private String total;
	private String statusPedido;
	private Cliente cliente;
	private Endereco enderecoEntrega;
	private Set<ItemPedido> itens = new HashSet<>();
	
	
	/**
	 * Construtor de {@link PedidoDTO}
	 * @param pedido : {@link Pedido}
	 */
	public PedidoDTO(Pedido pedido) {
		id = pedido.getId();
		dataHoraPedidoFinalizado = pedido.getDataHoraPedidoFinalizado();
		total = Converter.doubleParaStringComDuasCasasDecimais(pedido.getTotal());
		statusPedido = pedido.getStatusPedido().getDescricao();
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

	public String getTotal() {
		return total;
	}

	public String getStatusPedido() {
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
	 * Método responsável por converter um List de {@link Pedido} para {@link PedidoDTO}
	 * @param listaPedidos : List de {@link Pedido}
	 * @return List de {@link PedidoDTO}
	 */
	public static List<PedidoDTO> converterParaListaPedidoDTO(List<Pedido> listaPedidos) {
		return listaPedidos.stream().map(PedidoDTO::new).collect(Collectors.toList());
	}
}
