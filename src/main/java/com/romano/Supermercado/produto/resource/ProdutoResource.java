package com.romano.Supermercado.produto.resource;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.romano.Supermercado.produto.dto.ProdutoDTO;
import com.romano.Supermercado.produto.service.ProdutoService;


/**
 * 
 * @author Paulo Romano - [paulo-romano_133@hotmail.com]
 * Classe responsável por chamar os métodos do Service de Produto
 */
@RestController
@RequestMapping(value = "/produto")
public class ProdutoResource {

	@Autowired
	private ProdutoService produtoService;
	
	
	/**
	 * Método responsável por chamar o serviço de listagem de todos os Produtos
	 * @return ResponseEntity<List<ProdutoDTO>> - Retorna a resposta da requisição do método
	 * de listar todos os Setores da classe de Serviço
	 */
	@GetMapping("listar-todos")
	public ResponseEntity<List<ProdutoDTO>> listarTodosProdutos() {
		return produtoService.listarTodosProdutos();
	}
}
