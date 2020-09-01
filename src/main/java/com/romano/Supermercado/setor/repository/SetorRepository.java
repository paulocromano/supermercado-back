package com.romano.Supermercado.setor.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.romano.Supermercado.setor.model.Setor;


/**
 * 
 * @author Paulo Romano - [paulo-romano_133@hotmail.com]
 * Interface responsável por acessar os dados de Setor no Banco de Dados
 */
@Repository
public interface SetorRepository extends JpaRepository<Setor, Integer> {
	
}
