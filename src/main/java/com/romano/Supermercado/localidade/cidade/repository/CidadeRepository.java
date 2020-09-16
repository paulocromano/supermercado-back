package com.romano.Supermercado.localidade.cidade.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.romano.Supermercado.localidade.cidade.model.Cidade;


/**
 * 
 * @author Paulo Romano - [paulo-romano_133@hotmail.com] <br>
 * Interface responsável por acessar os dados de Cidade no Banco de Dados
 */
@Repository
public interface CidadeRepository extends JpaRepository<Cidade, Integer> {

	/**
	 * Método responsável por buscar uma Cidade por nome
	 * @param cidade : String
	 * @return Cidade
	 */
	Cidade findByNome(String cidade);

}
