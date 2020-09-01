package com.romano.Supermercado.produto.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
