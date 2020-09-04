package com.romano.Supermercado.produto.resource;

import java.util.List;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.romano.Supermercado.produto.dto.ProdutoDTO;
import com.romano.Supermercado.produto.form.AtualizarProdutoFORM;
import com.romano.Supermercado.produto.form.ProdutoFORM;
import com.romano.Supermercado.produto.service.ProdutoService;


/**
 * 
 * @author Paulo Romano - [paulo-romano_133@hotmail.com]
 * Classe responsável por chamar os métodos do Service de Produto
 */
@RestController
@RequestMapping("/produto")
public class ProdutoResource {

	@Autowired
	private ProdutoService produtoService;
	
	
	/**
	 * Método responsável por chamar o serviço de listar todos os Produtos
	 * @return ResponseEntity<List<ProdutoDTO>> - Retorna a resposta da requisição
	 */
	@GetMapping("/listar-todos")
	public ResponseEntity<List<ProdutoDTO>> listarTodosProdutos() {
		return produtoService.listarTodosProdutos();
	}
	
	
	/**
	 * Método responsável por chamar o serviço de listar os Produtos a partir do Setor informado
	 * ao Setor informado
	 * @param nomeSetor : String
	 * @return ResponseEntity<List<ProdutoDTO>> - Retorna a resposta da requisição
	 */
	@GetMapping("/listar-setor={nomeSetor}")
	public ResponseEntity<List<ProdutoDTO>> listarProdutosPorNomeSetor(@PathVariable String nomeSetor) {
		return produtoService.listarProdutosPorNomeSetor(nomeSetor);
	}
	
	
	/**
	 * Método responsável por chamar o serviço de listar os Produtos pelo status
	 * @param codigoStatus : Integer
	 * @return ResponseEntity<List<ProdutoDTO>> - Retorna a resposta da requisição
	 */
	@GetMapping("/listar-status={codigoStatus}")
	public ResponseEntity<List<ProdutoDTO>> listarProdutosPeloStatus(@PathVariable Integer codigoStatus) {
		return produtoService.listarProdutosPeloStatus(codigoStatus);
	}
	
	
	/**
	 * Método responsável por chamar o serviço de listar os Produtos que estão com estoque baixo
	 * @return ResponseEntity<List<ProdutoDTO>>
	 */
	@GetMapping("/listar-estoque-baixo")
	public ResponseEntity<List<ProdutoDTO>> listarProdutosComEstoqueBaixo() {
		return produtoService.listarProdutosComEstoqueBaixo();
	}
	
	
	/**
	 * Método responsável por chamar o serviço de listar os Produtos que estão dentro do período
	 * de dias até a validade do Produto
	 * @param dias : Integer
	 * @return ResponseEntity<List<ProdutoDTO>>
	 */
	@GetMapping("/listar-dias-validade={dias}")
	public ResponseEntity<List<ProdutoDTO>> listarProdutosPelosDiasRestantesDaValidade(@PathVariable Integer dias) {
		return produtoService.listarProdutosPelosDiasRestantesDaValidade(dias);
	}
	
	
	/**
	 * Método responsável por chamar o serviço de cadastrar Produto
	 * @param produtoFORM : ProdutoFORM
	 * @return ResponseEntity<Void> - Retorna a resposta da requisição
	 */
	@PostMapping("/cadastrar")
	public ResponseEntity<Void> cadastrarProduto(@RequestBody @Valid ProdutoFORM produtoFORM) {
		return produtoService.cadastrarProduto(produtoFORM);
	}
	
	
	/**
	 * Método responsável por chamar o serviço de atualização de Produto
	 * @param id : Integer
	 * @param atualizarProdutoFORM : AtualizarProdutoFORM
	 * @return ResponseEntity<Void> - Retorna a resposta da requisição
	 */
	@Transactional
	@PutMapping("/atualizar/{id}")
	public ResponseEntity<Void> atualizarProduto(@PathVariable Integer id, @RequestBody @Valid AtualizarProdutoFORM atualizarProdutoFORM) {
		return produtoService.atualizarProduto(id, atualizarProdutoFORM);
	}
	
	
	/**
	 * Método responsável por chamar o serviço de remoção de Produto
	 * @param id : Integer
	 * @return ResponseEntity<Void> - Retorna a resposta da requisição
	 */
	@DeleteMapping("/remover/{id}")
	public ResponseEntity<Void> removerProduto(@PathVariable Integer id) {
		return produtoService.removerProduto(id);
	}
}
