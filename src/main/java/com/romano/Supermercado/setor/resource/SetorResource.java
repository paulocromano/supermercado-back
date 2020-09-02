package com.romano.Supermercado.setor.resource;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.romano.Supermercado.setor.dto.SetorDTO;
import com.romano.Supermercado.setor.form.SetorFORM;
import com.romano.Supermercado.setor.service.SetorService;


/**
 * 
 * @author Paulo Romano - [paulo-romano_133@hotmail.com]
 * Classe responsável por chamar os métodos do Service de Setor
 */
@RestController
@RequestMapping(value = "/setor")
public class SetorResource {
	
	@Autowired
	private SetorService setorService;
	
	
	/**
	 * Método responsável por chamar o serviço de listagem de todos os Setores
	 * @return ResponseEntity<List<SetorDTO>> - Retorna a resposta da requisição do método
	 * de listar todos os Setores da classe de Serviço
	 */
	@GetMapping("/listar-todos")
	public ResponseEntity<List<SetorDTO>> listarTodosSetores() {
		return setorService.listarTodosSetores();
	}
	
	
	/**
	 * Método responsável por chamar o serviço de cadastro de Setor
	 * @param setorFORM : SetorFORM
	 * @return ResponseEntity<Void> - Retorna a resposta do método de cadastro de Setor da
	 * classe de Serviço
	 */
	@PostMapping
	public ResponseEntity<Void> cadastrarSetor(@RequestBody @Valid SetorFORM setorFORM) {
		return setorService.cadastrarSetor(setorFORM);
	}
	
	
	/**
	 * Método responsável por remover um Setor
	 * @param id : Integer
	 * @return ResponseEntity<Void> - Retorna a resposta do método de remover um Setor da classe
	 * de Serviço 
	 * @throws SQLIntegrityConstraintViolationException
	 */
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> removerSetor(@PathVariable Integer id) throws SQLIntegrityConstraintViolationException {
		return setorService.removerSetor(id);
	}
}
