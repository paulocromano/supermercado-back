package com.romano.Supermercado.setor.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import com.romano.Supermercado.exception.service.DataIntegrityException;
import com.romano.Supermercado.exception.service.ObjectNotFoundException;
import com.romano.Supermercado.setor.dto.SetorDTO;
import com.romano.Supermercado.setor.form.SetorFORM;
import com.romano.Supermercado.setor.model.Setor;
import com.romano.Supermercado.setor.repository.SetorRepository;


/**
 * 
 * @author Paulo Romano - [paulo-romano_133@hotmail.com]
 * Classe de serviço responsável pelas regras de negócios do {@link Setor}
 */
@Service
public class SetorService {

	@Autowired
	private SetorRepository setorRepository;
	
	
	/**
	 * Método responsável por listar todos os {@link Setor}es
	 * @return ResponseEntity - List {@link SetorDTO}
	 */
	public ResponseEntity<List<SetorDTO>> listarTodosSetores() {
		return ResponseEntity.ok().body(SetorDTO.converterParaListaSetorDTO(setorRepository.findAll()));
	}
	
	
	/**
	 * Método responsável por cadastrar um {@link Setor}
	 * @param setorFORM : {@link SetorFORM}
	 * @return ResponseEntity - Void
	 */
	public ResponseEntity<Void> cadastrarSetor(SetorFORM setorFORM) {
		Setor setor = setorFORM.converterParaSetor();
		
		if (verificaSeNomeNovoSetorJaExiste(setor.getNome())) {
			throw new DataIntegrityException("O nome do Setor informado já existe!");
		}
		
		setorRepository.save(setor);
		
		return ResponseEntity.ok().build();
	}
	
	
	/**
	 * Método responsável por verificar se o nome do novo {@link Setor} já existe
	 * @param nomeSetor : String
	 * @return Boolean - Retorna True se já existir um Setor com o nome informado. False
	 * se não existir
	 */
	private Boolean verificaSeNomeNovoSetorJaExiste(String nomeSetor) {
		Optional<Setor> setor = setorRepository.findByNome(nomeSetor);
		
		return (setor.isPresent()) ? true : false;
	}
	
	
	/**
	 * Método responsável por remover um {@link Setor}
	 * @param id : Integer
	 * @return ResponseEntity - Void
	 */
	public ResponseEntity<Void> removerSetor(@PathVariable Integer id) {
		Optional<Setor> setor = setorRepository.findById(id);
		
		if (!setor.isPresent()) {	
			throw new ObjectNotFoundException("Setor não encontrado!");
		}
		
		try {
			setorRepository.delete(setor.get());
		}
		catch (RuntimeException e) {
			throw new DataIntegrityException("Não foi possível remover! O Setor possui Produto(s) cadastrado(s).");
		}
		
		return ResponseEntity.ok().build();
	}
}
