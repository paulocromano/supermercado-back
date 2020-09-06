package com.romano.Supermercado.cliente.form;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

import com.romano.Supermercado.cliente.localidade.cidade.model.Cidade;
import com.romano.Supermercado.cliente.localidade.cidade.repository.CidadeRepository;
import com.romano.Supermercado.cliente.localidade.endereco.model.Endereco;
import com.romano.Supermercado.cliente.model.Cliente;
import com.romano.Supermercado.utils.Converter;

/**
 * 
 * @author Paulo Romano - [paulo-romano_133@hotmail.com]
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
	
	
	public String getDataNascimento() {
		return dataNascimento;
	}

	public String getTelefone() {
		return telefone;
	}

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

	public void setDataNascimento(String dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public void setEndereco(String logradouro) {
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
	 * Método responsável por atualizar o Produto com os novos dados
	 * @param produto : Produto
	 * @param cidadeRepository : CidadeRepository
	 */
	public void atualizarCliente(Cliente cliente, CidadeRepository cidadeRepository) {
		Cidade cidadeCliente = cidadeRepository.findByNome(cidade);
		Endereco enderecoCliente = new Endereco(logradouro, numero, complemento, bairro, cep, cliente, cidadeCliente);
		
		cliente.setDataNascimento(Converter.stringParaLocalDate(dataNascimento));
		cliente.setTelefone(telefone);
		cliente.adicionarEndereco(enderecoCliente);
	}
}
