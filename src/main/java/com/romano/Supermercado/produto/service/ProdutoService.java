package com.romano.Supermercado.produto.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.romano.Supermercado.produto.dto.ProdutoDTO;
import com.romano.Supermercado.produto.repository.ProdutoRepository;


/**
 * 
 * @author Paulo Romano - [paulo-romano_133@hotmail.com]
 * Classe de Serviço responsável pelas regras de negócios do Produto
 */
@Service
public class ProdutoService {
	
	@Autowired
	private ProdutoRepository produtoRepository;
	
	
	public ResponseEntity<List<ProdutoDTO>> listarTodosProdutos() {
		return ResponseEntity.ok().body(ProdutoDTO.converterParaListaProdutoDTO(produtoRepository.findAll()));
	}
}
