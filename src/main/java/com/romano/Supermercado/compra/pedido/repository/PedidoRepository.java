package com.romano.Supermercado.compra.pedido.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.romano.Supermercado.compra.pedido.enums.StatusPedido;
import com.romano.Supermercado.compra.pedido.model.Pedido;


/**
 * 
 * @author Paulo Romano - [paulo-romano_133@hotmail.com]
 * Interface responsável por acessar os dados de {@link Pedido} no Banco de Dados
 */
@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {

	/**
	 * Método responsável por buscar todos os pedidos de um respectivo {@link Cliente}
	 * @param id : Long
	 * @return List de {@link Pedido}
	 */
	List<Pedido> findByCliente_Id(Long id);


	/**
	 * Método responsável por buscar todos os pedidos que já foram finalizados
	 * @param codigo : Integer
	 * @return List de {@link Pedido}
	 */
	List<Pedido> findByStatusPedido(Integer codigo);


	/**
	 * Método responsável por buscar pedidos de um {@link Cliente} conforme o {@link StatusPedido}
	 * @param idCliente : Long
	 * @param codigo : Integer
	 * @return List de {@link Pedido}
	 */
	List<Pedido> findByCliente_IdAndStatusPedido(Long idCliente, Integer codigo);

}
