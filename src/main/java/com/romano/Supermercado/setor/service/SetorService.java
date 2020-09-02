package com.romano.Supermercado.setor.service;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import com.romano.Supermercado.exception.service.ObjectNotFoundException;
import com.romano.Supermercado.produto.model.Produto;
import com.romano.Supermercado.produto.repository.ProdutoRepository;
import com.romano.Supermercado.setor.dto.SetorDTO;
import com.romano.Supermercado.setor.form.SetorFORM;
import com.romano.Supermercado.setor.model.Setor;
import com.romano.Supermercado.setor.repository.SetorRepository;


/**
 * 
 * @author Paulo Romano - [paulo-romano_133@hotmail.com]
 * Classe de serviço responsável pelas regras de negócios do Setor
 */
@Service
public class SetorService {

	@Autowired
	private SetorRepository setorRepository;
	
	@Autowired
	private ProdutoRepository produtoRepository;
	
	
	/**
	 * Método responsável por listar todos os Setores
	 * @return ResponseEntity<List<SetorDTO>>
	 */
	public ResponseEntity<List<SetorDTO>> listarTodosSetores() {
		return ResponseEntity.ok().body(SetorDTO.converterParaListaSetorDTO(setorRepository.findAll()));
	}
	
	
	/**
	 * Método responsável por cadastrar um Setor
	 * @param setorFORM : SetorFORM
	 * @return ResponseEntity<Void> - Resposta da requisição de cadastro de Setor
	 */
	public ResponseEntity<Void> cadastrarSetor(SetorFORM setorFORM) {
		Setor setor = setorFORM.conveterParaSetor();
		setorRepository.save(setor);
		
		return ResponseEntity.ok().build();
	}
	
	
	/**
	 * Método responsável por remover um Setor
	 * @param id : Integer
	 * @return ResponseEntity<Void> - Resposta da requisição de remoção de Setor
	 * @throws SQLIntegrityConstraintViolationException 
	 */
	public ResponseEntity<Void> removerSetor(@PathVariable Integer id) throws SQLIntegrityConstraintViolationException {
		Optional<Setor> setor = setorRepository.findById(id);
		
		if (setor.isPresent()) {			
			Optional<Produto> produto = produtoRepository.findFirstBySetor_Id(id);
			
			if (produto.isPresent()) {
				throw new SQLIntegrityConstraintViolationException("Não foi possível remover. O Setor possui Produto(s) cadastrado(s)!");
			}
			
			setorRepository.delete(setor.get());
			
			return ResponseEntity.ok().build();
		}
		else {
			throw new ObjectNotFoundException("Setor não encontrado!");
		}
	}
}
