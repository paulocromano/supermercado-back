package com.romano.Supermercado.produto.service;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.romano.Supermercado.exception.service.DataIntegrityException;
import com.romano.Supermercado.exception.service.FileException;
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
 * @author Paulo Romano - [paulo-romano_133@hotmail.com] <br>
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
	 * @return ResponseEntity - List de ProdutoDTO
	 */
	public ResponseEntity<List<ProdutoDTO>> listarTodosProdutos() {
		return ResponseEntity.ok().body(ProdutoDTO.converterParaListaProdutoDTO(produtoRepository.findAll()));
	}
	
	
	/**
	 * Método responsável por listar todos os Produtos a partir do Setor informado
	 * @param nomeSetor : String
	 * @return ResponseEntity - List de ProdutoDTO
	 */
	public ResponseEntity<List<ProdutoDTO>> listarProdutosPorNomeSetor(String nomeSetor) {
		Optional<Setor> setorBuscado = setorRepository.findByNome(nomeSetor);
		
		if (setorBuscado.isEmpty()) {
			throw new ObjectNotFoundException("Setor informado não existe!");
		}
		
		return ResponseEntity.ok().body(ProdutoDTO.converterParaListaProdutoDTO(produtoRepository.findBySetor_Nome(nomeSetor)));
	}
	
	
	/**
	 * Método responsável por listar os Produtos com desconto
	 * @return ResponseEntity - List de ProdutoDTO
	 */
	public ResponseEntity<List<ProdutoDTO>> listarProdutosComDesconto() {
		List<Produto> produtosComDesconto = produtoRepository.findAll();
		
		produtosComDesconto.removeIf(produto -> produto.getDesconto() == 0);
				
		return ResponseEntity.ok().body(ProdutoDTO.converterParaListaProdutoDTO(produtosComDesconto));
	}
	
	
	/**
	 * Método responsável por listar todos os Produtos a partir do StatusProduto
	 * @param codigoStatus : Integer
	 * @return ResponseEntity - List de ProdutoDTO
	 */
	public ResponseEntity<List<ProdutoDTO>> listarProdutosPeloStatus(Integer codigoStatus) {
		StatusProduto statusProduto = StatusProduto.converterParaEnum(codigoStatus);
		
		return ResponseEntity.ok().body(ProdutoDTO.converterParaListaProdutoDTO(produtoRepository.findByStatusProduto(statusProduto.getCodigo())));
	}
	
	
	/**
	 * Método responsável por listar os Produtos com os dias até a validade dentro do valor específicado. Caso
	 * não seja específicado o dia, será verificado a partir do valor padrão 10.
	 * @param dias : Integer - Dias restantes até a validade do Produto
	 * @return ResponseEntity - List de ProdutoDTO
	 */
	public ResponseEntity<List<ProdutoDTO>> listarProdutosPelaDataDaValidade(String data) {
		if (data == null) {
			throw new NullPointerException("A Data não pode estar vazia!");
		}
		
		LocalDate dataInformada = Converter.stringParaLocalDate(data);
		verificarSeDataInformadaEValida(dataInformada);
		
		List<Produto> produtos = produtoRepository.findAll();
		produtos.removeIf(produto -> dataInformada.isAfter(produto.getDataValidade()));
		
		return ResponseEntity.ok().body(ProdutoDTO.converterParaListaProdutoDTO(produtos));
	}
	
	
	/**
	 * Método responsável por verificar se a data atual é depois da data informada
	 * @param dataInformada : LocalDate
	 */
	private void verificarSeDataInformadaEValida(LocalDate dataInformada) {
		LocalDate dataAtual = LocalDate.now();
		
		if (dataAtual.isAfter(dataInformada)) {
			throw new IllegalArgumentException("Informe uma Data a partir da Data atual!");
		}
	}
	
	
	/**
	 * Método responsável por cadastrar um Produto
	 * @param produtoFORM : ProdutoFORM
	 * @return ResponseEntity - Void
	 */
	public ResponseEntity<Void> cadastrarProduto(ProdutoFORM produtoFORM) { 
		produtoRepository.save(produtoFORM.converterParaProduto());

		return ResponseEntity.ok().build();
	}
	
	
	/**
	 * Método responsável por fazer o Upload da imagem do Produto
	 * @param id : Integer
	 * @param imagem : MultipartFile
	 * @return ResponseEntity - Void
	 */
	public ResponseEntity<Void> uploadImagemProduto(Integer id, MultipartFile imagem) {
		Produto produto = produtoRepository.getOne(id);
		
		try {
			System.out.println(imagem.getBytes() + " - " + imagem.getOriginalFilename());
			verificarSeImagemEValida(imagem);
			
			produto.setImagem(imagem.getBytes());
			
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
		
		return ResponseEntity.ok().build();
	}
	
	
	/**
	 * Método responsável por verificar se a Imagem é válida
	 * @param imagem : MultipartFile
	 */
	private void verificarSeImagemEValida(MultipartFile imagem) {
		String extensao = imagem.getOriginalFilename().split("\\.")[1];
		
		if (!extensao.equals("png") && !extensao.equals("jpg")) {
			throw new FileException("Somente imagens PNG e JPG são permitidas!");
		}
		
		if (imagem.getSize() > 500000L) {
			throw new FileException("O tamanho da Imagem deve ter no máximo 0.5MB!");
		}
		
		verificarTamanhoImagem(imagem);
	}
	
	
	/**
	 * Método responsável por verificar se a resolução da Imagem está dentro do limite definido
	 * @param imagem : MultipartFile
	 */
	private void verificarTamanhoImagem(MultipartFile imagem) {
		try {
			BufferedImage bufferedImage = ImageIO.read(imagem.getInputStream());
			
			if (bufferedImage.getHeight() > 300 || bufferedImage.getWidth() > 300) {
				throw new FileException("A resolução da Imagem deve ser no máximo 300 x 300px!");
			}
		} 
		catch (IOException e) {
			throw new FileException("Erro ao ler a Imagem!");
		}
	}
	
	
	/**
	 * Método responsável por atualizar as informações de um Produto
	 * @param id : Integer
	 * @param atualizarProdutoFORM : AtualizarProdutoFORM
	 * @return ResponseEntity - Void
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
	 * @return ResponseEntity - Void
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
	 * @return ResponseEntity - Void
	 */
	public ResponseEntity<Void> aumentarOuDiminuirEstoqueProduto(Integer idProduto, Boolean aumentarEstoque, Integer quantidade) {
		if (quantidade == null) {
			throw new NullPointerException("Informe uma quantidade válida!");
		}
		
		Optional<Produto> produto = produtoRepository.findById(idProduto);
		
		if (produto.isEmpty()) {
			throw new ObjectNotFoundException("Produto informado não existe!");
		}
		
		if (aumentarEstoque) {
			produto.get().somarEstoqueProduto(quantidade);
		}
		else {
			produto.get().diminuirEstoqueProduto(quantidade);
		}
		
		return ResponseEntity.ok().build();
	}
}
