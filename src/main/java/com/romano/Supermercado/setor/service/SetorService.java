package com.romano.Supermercado.setor.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.romano.Supermercado.setor.dto.SetorDTO;
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
	
	
	/**
	 * Método responsável por listar todos os Setores
	 * @return ResponseEntity<List<SetorDTO>>
	 */
	public ResponseEntity<List<SetorDTO>> listarTodosSetores() {
		return ResponseEntity.ok().body(SetorDTO.converterParaListaSetorDTO(setorRepository.findAll()));
	}
}
