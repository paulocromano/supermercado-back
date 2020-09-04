package com.romano.Supermercado.produto.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.romano.Supermercado.exception.service.ObjectNotFoundException;
import com.romano.Supermercado.produto.dto.ProdutoDTO;
import com.romano.Supermercado.produto.enums.StatusProduto;
import com.romano.Supermercado.produto.form.AtualizarProdutoFORM;
import com.romano.Supermercado.produto.form.ProdutoFORM;
import com.romano.Supermercado.produto.model.Produto;
import com.romano.Supermercado.produto.repository.ProdutoRepository;
import com.romano.Supermercado.setor.model.Setor;
import com.romano.Supermercado.setor.repository.SetorRepository;


/**
 * 
 * @author Paulo Romano - [paulo-romano_133@hotmail.com]
 * Classe de Serviço responsável pelas regras de negócios do Produto
 */
@Service
public class ProdutoService {
	
	@Autowired
	private ProdutoRepository produtoRepository;
	
	@Autowired
	private SetorRepository setorRepository;
	
	
	/**
	 * Método responsável por listar todos os Produtos
	 * @return ResponseEntity<List<ProdutoDTO>>
	 */
	public ResponseEntity<List<ProdutoDTO>> listarTodosProdutos() {
		return ResponseEntity.ok().body(ProdutoDTO.converterParaListaProdutoDTO(produtoRepository.findAll()));
	}
	
	
	/**
	 * Método responsável por listar todos os Produtos a partir do Setor informado
	 * @param nomeSetor : String
	 * @return ResponseEntity<List<ProdutoDTO>>
	 */
	public ResponseEntity<List<ProdutoDTO>> listarProdutosPorNomeSetor(String nomeSetor) {
		Optional<Setor> setorBuscado = setorRepository.findByNome(nomeSetor);
		
		if (setorBuscado.isEmpty()) {
			throw new ObjectNotFoundException("Setor informado não existe!");
		}
		
		return ResponseEntity.ok().body(ProdutoDTO.converterParaListaProdutoDTO(produtoRepository.findBySetor_Nome(nomeSetor)));
	}
	
	
	/**
	 * Método responsável por listar todos os Produtos a partir do Status do Produto
	 * @param codigoStatus : Integer
	 * @return ResponseEntity<List<ProdutoDTO>>
	 */
	public ResponseEntity<List<ProdutoDTO>> listarProdutosPeloStatus(Integer codigoStatus) {
		StatusProduto statusProduto = StatusProduto.converterParaEnum(codigoStatus);
		
		return ResponseEntity.ok().body(ProdutoDTO.converterParaListaProdutoDTO(produtoRepository.findByStatusProduto(statusProduto.getCodigo())));
	}
	
	
	/**
	 * Método responsável por listar os Produtos com estoque baixo
	 * @return ResponseEntity<List<ProdutoDTO>>
	 */
	public ResponseEntity<List<ProdutoDTO>> listarProdutosComEstoqueBaixo() {
		List<Produto> produtos = produtoRepository.findAll();
		produtos.removeIf(produto -> produto.getStatusProduto() != StatusProduto.ESTOQUE_BAIXO);
		
		return ResponseEntity.ok().body(ProdutoDTO.converterParaListaProdutoDTO(produtos));
	}
	
	
	/**
	 * Método responsável por listar os Produtos com os dias até a validade dentro do valor específicado. Caso
	 * não seja específicado o dia, será verificado a partir do valor padrão 10.
	 * @param dias : Integer - Dias restantes até a validade do Produto
	 * @return ResponseEntity<List<ProdutoDTO>>
	 */
	public ResponseEntity<List<ProdutoDTO>> listarProdutosPelosDiasRestantesDaValidade(Integer dias) {
		if (dias == null) {
			throw new NullPointerException("A quantidade de dias não pode estar vazia!");
		}
		
		LocalDate dataAtual = LocalDate.now();
		List<Produto> produtos = produtoRepository.findAll();
		produtos.removeIf(produto -> verificaDataValidadePelosDiasRestantes(dataAtual, produto.getDataValidade(), dias));
		
		return ResponseEntity.ok().body(ProdutoDTO.converterParaListaProdutoDTO(produtos));
	}
	
	
	/**
	 * Método responsável por verificar se a data de validade do Produto está dentro da
	 * quantidade de dias informado
	 * @param dataAtual : LocalDate
	 * @param dataValidade : LocalDate
	 * @param dias : Integer
	 * @return Boolean - True se o Produto estiver dentro do período de dias informado até
	 * a validade do Produto. False se estiver fora.
	 */
	private Boolean verificaDataValidadePelosDiasRestantes(LocalDate dataAtual, LocalDate dataValidade, Integer dias) {
		if (dataValidade.getYear() == dataAtual.getYear()) {
			if (dataValidade.getMonthValue() == dataAtual.getMonthValue()) {
				if ((dataValidade.getDayOfMonth() - dataAtual.getDayOfMonth()) <= dias) {
					return true;
				}
			}
		}
		
		return false;
	}
	
	/**
	 * Método responsável por cadastrar um Produto
	 * @param produtoFORM : ProdutoFORM
	 * @return ResponseEntity<Void>
	 */
	public ResponseEntity<Void> cadastrarProduto(ProdutoFORM produtoFORM) {
		if (produtoFORM == null) {
			throw new NullPointerException("É necessário preencher os campos para cadastrar um Produto!");
		}
 
		produtoRepository.save(produtoFORM.converterParaProduto());

		return ResponseEntity.ok().build();
	}
	
	
	/**
	 * Método responsável por atualizar as informações de um Produto
	 * @param id : Integer
	 * @param atualizarProdutoFORM : AtualizarProdutoFORM
	 * @return ResponseEntity<Void>
	 */
	public ResponseEntity<Void> atualizarProduto(Integer id, AtualizarProdutoFORM atualizarProdutoFORM) {
		Optional<Produto> produtoBuscado = produtoRepository.findById(id);
		
		if (produtoBuscado.isEmpty()) {
			throw new ObjectNotFoundException("Produto informado não existe!");
		}
		
		atualizarProdutoFORM.atualizarProduto(produtoBuscado.get());
		
		return ResponseEntity.ok().build();
	}
	
	
	/**
	 * Método responsável por remover um Produto
	 * @param id : Integer
	 * @return ResponseEntity<Void>
	 */
	public ResponseEntity<Void> removerProduto(Integer id) {
		Optional<Produto> produto = produtoRepository.findById(id);
		
		//TODO - Implementar os teste se algum cliente comprou o produto
		
		if (produto.isEmpty()) {
			throw new ObjectNotFoundException("Produto informado não existe!");
		}
		
		produtoRepository.deleteById(id);
		
		return ResponseEntity.ok().build();
	}
}
