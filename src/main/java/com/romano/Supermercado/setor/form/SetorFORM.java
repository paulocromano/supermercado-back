package com.romano.Supermercado.setor.form;

import com.romano.Supermercado.setor.model.Setor;

/**
 * 
 * @author Paulo Romano - [paulo-romano_133@hotmail.com]
 * Classe responsável por receber um novo Setor
 */
public class SetorFORM {
 
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
