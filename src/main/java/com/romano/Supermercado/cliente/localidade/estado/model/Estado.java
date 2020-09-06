package com.romano.Supermercado.cliente.localidade.estado.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "estado")
public class Estado {

	@Id
	private Integer id;
	
	private String nome;
	
	private String uf;
	

	public Integer getId() {
		return id;
	}
	
	public String getNome() {
		return nome;
	}

	public String getUf() {
		return uf;
	}
}
