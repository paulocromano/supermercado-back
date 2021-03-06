package com.romano.Supermercado.cliente.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 
 * @author Paulo Romano - [paulo-romano_133@hotmail.com] <br>
 * Classe Entidade do PerfilCliente relacionado com as permissões que o Cliente tem
 */
@Entity
@Table(name = "perfil")
public class PerfilCliente {

	@Id
	@Column(name = "cliente_id")
	private Long clienteID;
	
	private Integer perfis;

	
	public Long getClienteID() {
		return clienteID;
	}

	public Integer getPerfis() {
		return perfis;
	}
}
