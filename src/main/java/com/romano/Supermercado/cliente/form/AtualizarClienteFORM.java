package com.romano.Supermercado.cliente.form;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

import com.romano.Supermercado.cliente.model.Cliente;
import com.romano.Supermercado.utils.Converter;

/**
 * 
 * @author Paulo Romano - [paulo-romano_133@hotmail.com] <br>
 * Classe responsável por atualizar as informações de um Cliente
 */
public class AtualizarClienteFORM {

	@NotNull(message = "Data de Nascimento não informada!")
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private String dataNascimento;
	
	@NotEmpty(message = "O campo Telefone não pode estar vazio!")
	@Size(max = 15, message = "O campo Telefone excedeu o limite de {max} caracteres!")
	@Pattern(regexp = "(\\(\\d{2}\\)\\s)(\\d{4,5}\\-\\d{4})", message = "Telefone inválido!")
	private String telefone;
	
	
	public String getDataNascimento() {
		return dataNascimento;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setDataNascimento(String dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}


	/**
	 * Método responsável por atualizar o Cliente
	 * @param cliente : Cliente
	 */
	public void atualizarCliente(Cliente cliente) {
		
		cliente.setDataNascimento(Converter.stringParaLocalDate(dataNascimento));
		cliente.setTelefone(telefone);
	}
}
