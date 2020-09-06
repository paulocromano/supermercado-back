package com.romano.Supermercado.cliente.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.romano.Supermercado.cliente.model.PerfilCliente;


/**
 * 
 * @author Paulo Romano - [paulo-romano_133@hotmail.com]
 * Interface responsável por acessar os dados de Perfil de Cliente no Banco de Dados
 */
@Repository
public interface PerfilClienteRepository extends JpaRepository<PerfilCliente, Long> {

}
