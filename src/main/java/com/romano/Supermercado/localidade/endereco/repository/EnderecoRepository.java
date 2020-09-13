package com.romano.Supermercado.localidade.endereco.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.romano.Supermercado.localidade.endereco.model.Endereco;


/**
 * 
 * @author Paulo Romano - [paulo-romano_133@hotmail.com]
 * Interface responsável por acessar os dados de {@link Endereco} no Banco de Dados
 */
@Repository
public interface EnderecoRepository extends JpaRepository<Endereco, Integer> {

}
