package com.romano.Supermercado.setor.model;

/**
 * 
 * @author Paulo Romano - [paulo-romano_133@hotmail.com]
 * Classe Entidade de Setor
 */
public class Setor {

	private Integer id;
	private String nome;
	

	public Setor(Integer id, String nome) {
		this.id = id;
		this.nome = nome;
	}


	public Integer getId() {
		return id;
	}

	public String getNome() {
		return nome;
	}
}
