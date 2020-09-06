package com.romano.Supermercado.cliente.localidade.cidade.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.romano.Supermercado.cliente.localidade.estado.model.Estado;

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
