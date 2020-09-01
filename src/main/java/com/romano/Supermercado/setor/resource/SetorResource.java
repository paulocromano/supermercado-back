package com.romano.Supermercado.setor.resource;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.romano.Supermercado.setor.dto.SetorDTO;
import com.romano.Supermercado.setor.service.SetorService;


/**
 * 
 * @author Paulo Romano - [paulo-romano_133@hotmail.com]
 * Classe responsável por chamar os métodos do Service de Setor
 */
//@CrossOrigin
@RestController
@RequestMapping(value = "/setor")
public class SetorResource {
	
	@Autowired
	private SetorService setorService;
	
	
	/**
	 * Método responsável por chamar o serviço de listagem de todos os setores
	 * @return ResponseEntity<List<SetorDTO>> 
	 */
	@GetMapping("/listar-todos")
	public ResponseEntity<List<SetorDTO>> listarTodosSetores() {
		return setorService.listarTodosSetores();
	}

}
