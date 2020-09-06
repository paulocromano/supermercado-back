package com.romano.Supermercado.cliente.localidade.cidade.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.romano.Supermercado.cliente.localidade.cidade.model.Cidade;

@Repository
public interface CidadeRepository extends JpaRepository<Cidade, Integer> {

	Cidade findByNome(String cidade);

}
