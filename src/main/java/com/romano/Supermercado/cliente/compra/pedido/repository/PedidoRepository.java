package com.romano.Supermercado.cliente.compra.pedido.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.romano.Supermercado.cliente.compra.pedido.model.Pedido;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Integer> {

}
