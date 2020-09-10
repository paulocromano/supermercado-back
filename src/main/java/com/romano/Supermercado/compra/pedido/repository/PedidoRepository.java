package com.romano.Supermercado.compra.pedido.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.romano.Supermercado.compra.pedido.model.Pedido;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {

	/**
	 * Método responsável por buscar todos os pedidos de um respectivo Cliente
	 * @param id : long
	 * @return List<Pedido>
	 */
	List<Pedido> findByCliente_Id(Long id);

}
