package com.romano.Supermercado.setor.form;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.romano.Supermercado.setor.model.Setor;

/**
 * 
 * @author Paulo Romano - [paulo-romano_133@hotmail.com]
 * Classe responsável por receber um novo Setor
 */
public class SetorFORM {
 
	@NotNull(message = "Nome não informado!")
	@NotBlank(message = "Nome não pode estar vazio!")
	@Size(min = 3, max = 40, message = "O campo nome deve ter entre 3 a 40 caracteres!")
	private String nome;

	
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	
	
	public Setor conveterParaSetor() {
		return new Setor(nome);
	}
}
