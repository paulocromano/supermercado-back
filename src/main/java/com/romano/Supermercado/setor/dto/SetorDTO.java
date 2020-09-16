package com.romano.Supermercado.setor.dto;

import java.util.List;
import java.util.stream.Collectors;

import com.romano.Supermercado.setor.model.Setor;

/**
 * 
 * @author Paulo Romano - [paulo-romano_133@hotmail.com] <br>
 * Classe DTO para recuperar as informações do Setor
 */
public class SetorDTO {

	private Integer id;
	private String nome;
	
	
	public SetorDTO(Setor setor) {
		id = setor.getId();
		nome = setor.getNome();
	}


	public Integer getId() {
		return id;
	}

	public String getNome() {
		return nome;
	}
	
	
	/**
	 * Método responsável por converter um List de Setor para List de SetorDTO
	 * @param listaSetor : List de Setor
	 * @return List de SetorDTO - Lista convertida
	 */
	public static List<SetorDTO> converterParaListaSetorDTO(List<Setor> listaSetor) {
		return listaSetor.stream().map(SetorDTO::new).collect(Collectors.toList());
	}
}
