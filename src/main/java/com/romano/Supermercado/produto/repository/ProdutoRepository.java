package com.romano.Supermercado.produto.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.romano.Supermercado.produto.model.Produto;
import com.romano.Supermercado.setor.model.Setor;


/**
 * 
 * @author Paulo Romano - [paulo-romano_133@hotmail.com]
 * Interface responsável por acessar os dados de {@link Produto} no Banco de Dados
 */
@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Integer> {

	/**
	 * Método responsável por buscar o ID de um Setor pela entidade de {@link Produto}
	 * @param id : Integer - ID de {@link Setor}
	 * @return Optional de {@link Produto}
	 */
	Optional<Produto> findFirstBySetor_Id(Integer id);

	
	/**
	 * Método responsável por buscar os {@link Produto}s a partir do {@link Setor}
	 * @param nomeSetor : String
	 * @return List de {@link Produto}
	 */
	List<Produto> findBySetor_Nome(String nomeSetor);


	/**
	 * Método responsável por buscar os produtos a partir do Status do {@link Produto}
	 * @param codigo : Integer
	 * @return List de {@link Produto}
	 */
	List<Produto> findByStatusProduto(Integer codigo);

}
