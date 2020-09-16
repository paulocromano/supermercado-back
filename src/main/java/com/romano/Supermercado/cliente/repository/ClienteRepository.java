package com.romano.Supermercado.cliente.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.romano.Supermercado.cliente.model.Cliente;


/**
 * 
 * @author Paulo Romano - [paulo-romano_133@hotmail.com] <br>
 * Interface responsável por acessar os dados de Cliente no Banco de Dados
 */
@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {

	/**
	 * Método responsável por buscar um Cliente pelo email
	 * @param email : String
	 * @return Optional de Cliente
	 */
	Optional<Cliente> findByEmail(String email);
}
