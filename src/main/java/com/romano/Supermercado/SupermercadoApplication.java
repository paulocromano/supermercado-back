package com.romano.Supermercado;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.romano.Supermercado.setor.model.Setor;
import com.romano.Supermercado.setor.repository.SetorRepository;

@SpringBootApplication
public class SupermercadoApplication {

	public static void main(String[] args) {
		SpringApplication.run(SupermercadoApplication.class, args);
		
		List<Setor> listaSetor = new ArrayList<>();	
		
		//SetorRepository.removerSetor(3);
		
		listaSetor = SetorRepository.buscarTodosSetores();
		listaSetor.forEach(setor -> System.out.println(setor.getNome()));
	}

}
