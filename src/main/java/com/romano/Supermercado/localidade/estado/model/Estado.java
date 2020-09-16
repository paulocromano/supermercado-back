package com.romano.Supermercado.localidade.estado.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * 
 * @author Paulo Romano - [paulo-romano_133@hotmail.com] <br>
 * Classe Entidade de Estado
 */
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
