package com.romano.Supermercado.setor.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


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
	
	@NotNull(message = "Nome não informado!")
	@NotBlank(message = "Nome não pode estar vazio!")
	@Size(min = 3, max = 40, message = "O campo nome deve ter entre 3 a 40 caracteres!")
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
