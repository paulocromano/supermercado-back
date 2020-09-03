package com.romano.Supermercado.produto.repository;

import java.util.List;
import java.util.Optional;

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

	/**
	 * Método responsável por buscar o ID de um Setor pela entidade de Produto
	 * @param id : Integer - ID de Setor
	 * @return Optional<Produto>
	 */
	Optional<Produto> findFirstBySetor_Id(Integer id);

	
	/**
	 * Método responsável por buscar os produtos a partir do Setor
	 * @param nomeSetor : String
	 * @return List<Produto>
	 */
	List<Produto> findBySetor_Nome(String nomeSetor);


	/**
	 * Método responsável por buscar os produtos a partir do Status do Produto
	 * @param codigo : Integer
	 * @return List<Produto>
	 */
	List<Produto> findByStatusProduto(Integer codigo);

}
