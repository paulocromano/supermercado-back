package com.romano.Supermercado.produto.resource;

import java.util.List;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.romano.Supermercado.produto.dto.ProdutoDTO;
import com.romano.Supermercado.produto.form.AtualizarProdutoFORM;
import com.romano.Supermercado.produto.form.ProdutoFORM;
import com.romano.Supermercado.produto.service.ProdutoService;


/**
 * 
 * @author Paulo Romano - [paulo-romano_133@hotmail.com] <br>
 * Classe responsável por chamar os métodos do ProdutoService
 */
@RestController
@RequestMapping("/produto")
public class ProdutoResource {

	@Autowired
	private ProdutoService produtoService;
	
	
	/**
	 * Método responsável por chamar o serviço de listar todos os Produtos
	 * @return ResponseEntity - List de ProdutoDTO (Retorna a resposta da requisição)
	 */
	@GetMapping("/listar-todos")
	public ResponseEntity<List<ProdutoDTO>> listarTodosProdutos() {
		return produtoService.listarTodosProdutos();
	}
	
	
	/**
	 * Método responsável por chamar o serviço de listar os Produtos a partir do Setor informado
	 * @param nomeSetor : String
	 * @return ResponseEntity - List de ProdutoDTO (Retorna a resposta da requisição)
	 */
	@GetMapping("/listar-setor={nomeSetor}")
	public ResponseEntity<List<ProdutoDTO>> listarProdutosPorNomeSetor(@PathVariable String nomeSetor) {
		return produtoService.listarProdutosPorNomeSetor(nomeSetor);
	}
	
	
	/**
	 * Método responsável por chamar o serviço de listar os Produtos com desconto
	 * @return ResponseEntity - List de ProdutoDTO (Retorna a resposta da requisição)
	 */
	@GetMapping("/listar-com-desconto")
	public ResponseEntity<List<ProdutoDTO>> listarProdutosComDesconto() {
		return produtoService.listarProdutosComDesconto();
	}
	
	/**
	 * Método responsável por chamar o serviço de listar os Produtos pelo StatusProduto
	 * @param codigoStatus : Integer
	 * @return ResponseEntity - List de ProdutoDTO (Retorna a resposta da requisição)
	 */
	@PreAuthorize("hasAnyRole('ADMIN')")
	@GetMapping("/listar-status={codigoStatus}")
	public ResponseEntity<List<ProdutoDTO>> listarProdutosPeloStatus(@PathVariable Integer codigoStatus) {
		return produtoService.listarProdutosPeloStatus(codigoStatus);
	}
	
	
	/**
	 * Método responsável por chamar o serviço de listar os Produtos que estão dentro da data 
	 * até a validade do Produto
	 * @param data : String
	 * @return ResponseEntity - List de ProdutoDTO (Retorna a resposta da requisição)
	 */
	@PreAuthorize("hasAnyRole('ADMIN')")
	@GetMapping("/listar-data-validade={data}")
	public ResponseEntity<List<ProdutoDTO>> listarProdutosPelaDataDaValidade(@PathVariable String data) {
		return produtoService.listarProdutosPelaDataDaValidade(data);
	}
	
	
	/**
	 * Método responsável por chamar o serviço de cadastrar Produto
	 * @param produtoFORM : ProdutoFORM
	 * @return ResponseEntity - Void (Retorna a resposta da requisição)
	 */
	@PreAuthorize("hasAnyRole('ADMIN')")
	@PostMapping
	public ResponseEntity<Void> cadastrarProduto(@RequestBody @Valid ProdutoFORM produtoFORM) {
		return produtoService.cadastrarProduto(produtoFORM);
	}
	
	
	/**
	 * Método responsável por chamar o serviço de Upload da imagem do Produto
	 * @param id : Integer
	 * @param imagem : MultipartFile
	 * @return ResponseEntity - Void (Retorna a resposta da requisição)
	 */
	@PreAuthorize("hasAnyRole('ADMIN')")
	@Transactional
	@PutMapping("/upload/{id}")
	public ResponseEntity<Void> uploadImagemProduto(@PathVariable Integer id, @RequestParam("imagem") MultipartFile imagem) {
		return produtoService.uploadImagemProduto(id, imagem);
	}
	
	/**
	 * Método responsável por chamar o serviço de atualização de Produto
	 * @param id : Integer
	 * @param atualizarProdutoFORM : AtualizarProdutoFORM
	 * @return ResponseEntity - Void (Retorna a resposta da requisição)
	 */
	@PreAuthorize("hasAnyRole('ADMIN')")
	@Transactional
	@PutMapping("/{id}")
	public ResponseEntity<Void> atualizarProduto(@PathVariable Integer id, @RequestBody @Valid AtualizarProdutoFORM atualizarProdutoFORM) {
		return produtoService.atualizarProduto(id, atualizarProdutoFORM);
	}
	
	
	/**
	 * Método responsável por chamar o serviço de remoção de Produto
	 * @param id : Integer
	 * @return ResponseEntity - Void (Retorna a resposta da requisição)
	 */
	@PreAuthorize("hasAnyRole('ADMIN')")
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> removerProduto(@PathVariable Integer id) {
		return produtoService.removerProduto(id);
	}
	
	
	/**
	 * Método responosável por chamar o serviço de aumentar ou diminuir o estoque do Produto informado com base na quantidade
	 * @param idProduto : Integer
	 * @param aumentarEstoque : Boolean
	 * @param quantidade : Integer
	 * @return ResponseEntity - Void (Retorna a resposta da requisição)
	 */
	@PreAuthorize("hasAnyRole('ADMIN')")
	@Transactional
	@PutMapping("/{idProduto}/{aumentarEstoque}/{quantidade}")
	public ResponseEntity<Void> aumentarOuDiminuirEstoqueProduto(@PathVariable Integer idProduto, @PathVariable Boolean aumentarEstoque, 
			@PathVariable Integer quantidade) {
		
		return produtoService.aumentarOuDiminuirEstoqueProduto(idProduto, aumentarEstoque, quantidade);
	}
}
