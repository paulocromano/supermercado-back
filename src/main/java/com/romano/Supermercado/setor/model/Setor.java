package com.romano.Supermercado.setor.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * 
 * @author Paulo Romano - [paulo-romano_133@hotmail.com]
 * Classe Entidade de Setor
 */
@Entity
@Table(name = "setor")
public class Setor {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	private String nome;
	
 	
	public Setor() {
		
	}
	
	public Setor(String nome) {
		this.nome = nome;
	}
	

	public Integer getId() {
		return id;
	}

	public String getNome() {
		return nome;
	}
}
