package com.romano.Supermercado.compra.pedido.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.romano.Supermercado.compra.pedido.model.Pedido;


/**
 * 
 * @author Paulo Romano - [paulo-romano_133@hotmail.com] <br>
 * Interface responsável por acessar os dados de Pedido no Banco de Dados
 */
@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {

	/**
	 * Método responsável por buscar todos os pedidos de um respectivo Cliente
	 * @param id : Long
	 * @return List de Pedido
	 */
	List<Pedido> findByCliente_Id(Long id);


	/**
	 * Método responsável por buscar todos os pedidos que já foram finalizados
	 * @param codigo : Integer
	 * @return List de Pedido
	 */
	List<Pedido> findByStatusPedido(Integer codigo);


	/**
	 * Método responsável por buscar pedidos de um Cliente conforme o StatusPedido
	 * @param idCliente : Long
	 * @param codigo : Integer
	 * @return List de Pedido
	 */
	List<Pedido> findByCliente_IdAndStatusPedido(Long idCliente, Integer codigo);

}
