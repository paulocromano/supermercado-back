package com.romano.Supermercado.setor.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.romano.Supermercado.setor.model.Setor;


/**
 * 
 * @author Paulo Romano - [paulo-romano_133@hotmail.com] <br>
 * Interface responsável por acessar os dados de Setor no Banco de Dados
 */
@Repository
public interface SetorRepository extends JpaRepository<Setor, Integer> {

	/**
	 * Método responsável por buscar um Setor por nome
	 * @param nomeSetor : String
	 * @return Optional de Setor
	 */
	Optional<Setor> findByNome(String nomeSetor);
	
}
