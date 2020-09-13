package com.romano.Supermercado.cliente.form;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.romano.Supermercado.cliente.model.Cliente;
import com.romano.Supermercado.localidade.cidade.model.Cidade;
import com.romano.Supermercado.localidade.cidade.repository.CidadeRepository;
import com.romano.Supermercado.localidade.endereco.model.Endereco;

/**
 * 
 * @author Paulo Romano - [paulo-romano_133@hotmail.com]
 * Classe responsável por adicionar endereços alternativos do {@link Cliente}
 */
public class AdicionarEnderecoFORM {

	@NotNull(message = "Campo Logradouro não informado!")
	@NotEmpty(message = "O campo Logradouro não pode estar vazio!")
	@Size(min = 3, max = 50, message = "O campo Endereço deve ter entre {min} a {max} caracteres!")
	private String logradouro;
	
	@NotNull(message = "Campo Número não informado!")
	@NotEmpty(message = "O campo Número não pode estar vazio!")
	@Size(min = 1, max = 5, message = "O campo Número deve ter entre {min} a {max} caracteres!")
	private String numero;
	
	@NotEmpty(message = "O campo Complemento não pode estar vazio!")
	@Size(min = 3, max = 40, message = "O campo Complemento deve ter entre {min} a {max} caracteres!")
	private String complemento;
	
	@NotEmpty(message = "O campo Bairro não pode estar vazio!")
	@Size(min = 3, max = 20, message = "O campo Bairro deve ter entre {min} a {max} caracteres!")
	private String bairro;
	
	@NotNull(message = "Campo Cidade não informado!")
	@NotEmpty(message = "O campo Cidade não pode estar vazio!")
	@Size(min = 3, max = 30, message = "O campo Cidade deve ter entre {min} a {max} caracteres!")
	private String cidade;
	
	@NotNull(message = "Campo CEP não informado!")
	@NotEmpty(message = "O campo CEP não pode estar vazio!")
	@Size(max = 9, message = "O campo CEP excedeu o limite de {max} caracteres!")
	@Pattern(regexp = "\\d{5}-\\d{3}", message = "CEP inválido!")
	private String cep;
	
	@NotNull(message = "Campo UF não informado!")
	@NotEmpty(message = "O campo UF não pode estar vazio!")
	@Size(max = 2, message = "O campo UF excedeu o limite de {max} caracteres!")
	private String uf;

	
	public String getLogradouro() {
		return logradouro;
	}

	public String getNumero() {
		return numero;
	}

	public String getComplemento() {
		return complemento;
	}

	public String getBairro() {
		return bairro;
	}

	public String getCidade() {
		return cidade;
	}

	public String getCep() {
		return cep;
	}

	public String getUf() {
		return uf;
	}

	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public void setUf(String uf) {
		this.uf = uf;
	}
	
	
	/**
	 * Método responsável por atualizar os dados do {@link Cliente} com o novo {@link Endereco}
	 * @param cliente : {@link Cliente}
	 * @param cidadeRepository : {@link CidadeRepository}
	 */
	public void adicionarEndereco(Cliente cliente, CidadeRepository cidadeRepository) {
		Cidade cidadeCliente = cidadeRepository.findByNome(cidade);
		Endereco enderecoCliente = new Endereco(logradouro, numero, complemento, bairro, cep, cliente, cidadeCliente);
		
		cliente.adicionarEndereco(enderecoCliente);
	}
}
