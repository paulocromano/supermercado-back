package com.romano.Supermercado.produto.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.romano.Supermercado.produto.model.Produto;


/**
 * 
 * @author Paulo Romano - [paulo-romano_133@hotmail.com]
 * Interface responsável por acessar os dados de Produto no Banco de Dados
 */
@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Integer> {

}
