package com.romano.Supermercado.produto.service;

import java.sql.SQLIntegrityConstraintViolationException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.romano.Supermercado.exception.service.DataIntegrityException;
import com.romano.Supermercado.exception.service.ObjectNotFoundException;
import com.romano.Supermercado.produto.dto.ProdutoDTO;
import com.romano.Supermercado.produto.enums.StatusProduto;
import com.romano.Supermercado.produto.form.AtualizarProdutoFORM;
import com.romano.Supermercado.produto.form.ProdutoFORM;
import com.romano.Supermercado.produto.model.Produto;
import com.romano.Supermercado.produto.repository.ProdutoRepository;
import com.romano.Supermercado.setor.model.Setor;
import com.romano.Supermercado.setor.repository.SetorRepository;
import com.romano.Supermercado.utils.Converter;


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
	 * Método responsável por listar os Produtos com os dias até a validade dentro do valor específicado. Caso
	 * não seja específicado o dia, será verificado a partir do valor padrão 10.
	 * @param dias : Integer - Dias restantes até a validade do Produto
	 * @return ResponseEntity<List<ProdutoDTO>>
	 */
	public ResponseEntity<List<ProdutoDTO>> listarProdutosPelaDataDaValidade(String data) {
		if (data == null) {
			throw new NullPointerException("A Data não pode estar vazia!");
		}
		
		LocalDate dataInformada = Converter.stringParaLocalDate(data);
		verificarSeDataInformadaEValida(dataInformada);
		
		List<Produto> produtos = produtoRepository.findAll();
		produtos.removeIf(produto -> verificarDataValidadePelaDataInformada(produto.getDataValidade(), dataInformada));
		
		return ResponseEntity.ok().body(ProdutoDTO.converterParaListaProdutoDTO(produtos));
	}
	
	
	/**
	 * Método responsável por verificar se a data atual é depois da data informada
	 * @param dataInformada : LocalDate
	 */
	private void verificarSeDataInformadaEValida(LocalDate dataInformada) {
		LocalDate dataAtual = LocalDate.now();
		
		if (dataAtual.isAfter(dataInformada)) {
			System.out.println("Chegou");
			throw new IllegalArgumentException("Informe uma Data a partir da Data atual!");
		}
	}
	
	
	/**
	 * Método responsável por verificar se a data de validade do Produto é depois da
	 * data informada
	 * @param dataValidade : LocalDate
	 * @param dataInformada : LocalDate
	 * @return Boolean - True se o Produto estiver fora da data informada até
	 * a validade do Produto. False se estiver dentro.
	 */
	private Boolean verificarDataValidadePelaDataInformada(LocalDate dataValidade, LocalDate dataInformada) {
		return (dataInformada.isAfter(dataValidade)) ? true : false;
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
	 * @throws SQLIntegrityConstraintViolationException 
	 */
	public ResponseEntity<Void> removerProduto(Integer id) {
		Optional<Produto> produto = produtoRepository.findById(id);
		
		if (produto.isEmpty()) {
			throw new ObjectNotFoundException("Produto informado não existe!");
		}
		
		try {
			produtoRepository.deleteById(id);
		}
		catch (RuntimeException e) {
			throw new DataIntegrityException("Não foi possível remover! Clientes compraram este Produto.");
		}
		
		return ResponseEntity.ok().build();
	}
	
	
	/**
	 * Método responsável por aumentar ou diminuir o estoque do Produto informado com base na quantidade
	 * @param idProduto : Integer
	 * @param aumentarEstoque : Boolean
	 * @param quantidade : Integer
	 * @return ResponseEntity<Void>
	 */
	public ResponseEntity<Void> aumentarOuDiminuirEstoqueProduto(Integer idProduto, Boolean aumentarEstoque, Integer quantidade) {
		
		if (quantidade == null) {
			throw new NullPointerException("Informe uma quantidade válida!");
		}
		
		Produto produto = produtoRepository.getOne(idProduto);
		
		if (aumentarEstoque) {
			produto.somarEstoqueProduto(quantidade);
		}
		else {
			produto.diminuirEstoqueProduto(quantidade);
		}
		
		return ResponseEntity.ok().build();
	}
}
