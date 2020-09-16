package com.romano.Supermercado.localidade.cidade.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.romano.Supermercado.localidade.estado.model.Estado;


/**
 * 
 * @author Paulo Romano - [paulo-romano_133@hotmail.com] <br>
 * Classe Entidade de Cidade
 */
@Entity
@Table(name = "cidade")
public class Cidade {

	@Id
	private Integer id;
	
	private String nome;
	
	@ManyToOne
	@JoinColumn(name = "estado_id")
	private Estado estado;


	public Integer getId() {
		return id;
	}


	public String getNome() {
		return nome;
	}


	public Estado getEstado() {
		return estado;
	}
}
