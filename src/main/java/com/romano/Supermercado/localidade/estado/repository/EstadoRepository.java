package com.romano.Supermercado.localidade.estado.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.romano.Supermercado.localidade.estado.model.Estado;


/**
 * 
 * @author Paulo Romano - [paulo-romano_133@hotmail.com] <br>
 * Interface responsável por acessar os dados de Estado no Banco de Dados
 */
@Repository
public interface EstadoRepository extends JpaRepository<Estado, Integer> {

}
