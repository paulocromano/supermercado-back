package com.romano.Supermercado.setor.form;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.romano.Supermercado.setor.model.Setor;

/**
 * 
 * @author Paulo Romano - [paulo-romano_133@hotmail.com] <br>
 * Classe responsável por receber um novo Setor
 */
public class SetorFORM {
 
	@NotNull(message = "Nome não informado!")
	@NotEmpty(message = "O campo Nome não pode estar vazio!")
	@Size(min = 3, max = 40, message = "O campo nome deve ter entre {min} a {max} caracteres!")
	private String nome;

	
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	
	
	/**
	 * Método responsável por converter o SetorFORM para Setor
	 * @return Setor
	 */
	public Setor converterParaSetor() {
		return new Setor(nome);
	}
}
