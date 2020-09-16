package com.romano.Supermercado.usuario.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.romano.Supermercado.cliente.model.Cliente;
import com.romano.Supermercado.cliente.repository.ClienteRepository;
import com.romano.Supermercado.security.UsuarioSecurity;


/**
 * 
 * @author Paulo Romano - [paulo-romano_133@hotmail.com] <br>
 * Classe responsável por buscar o nome do Usuário <br>
 * Implementação da classe UserDetails
 */
@Service
public class UsuarioDetailsService implements UserDetailsService {
	
	@Autowired
	private ClienteRepository clienteRepository;

	
	/**
	 * Método responsável por receber um Usuário e retornar o UserDetails
	 */
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Cliente cliente = (clienteRepository.findByEmail(email)).get();
		
		if (cliente == null) {
			throw new UsernameNotFoundException(email);
		}
		
		return new UsuarioSecurity(cliente.getId(), cliente.getEmail(), cliente.getSenha(), cliente.getPerfis());
	}
}
