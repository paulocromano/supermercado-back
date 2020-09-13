package com.romano.Supermercado.security;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.romano.Supermercado.cliente.enums.PerfilCliente;


/**
 * 
 * @author Paulo Romano - [paulo-romano_133@hotmail.com]
 * Interface responsável por trabalhar com Usuários
 */
public class UsuarioSecurity implements UserDetails {

	private static final long serialVersionUID = 1L;
	
	private Long id;
	private String email;
	private String senha;
	private Collection<? extends GrantedAuthority> autorizacoes;
	
	
	public UsuarioSecurity() {
		
	}
	
	/**
	 * Construtor
	 * @param id: Long
	 * @param email : String
	 * @param senha : String
	 * @param autorizacoes : Set de {@link PerfilCliente}
	 */
	public UsuarioSecurity(Long id, String email, String senha, Set<PerfilCliente> perfis) {
		this.id = id;
		this.email = email;
		this.senha = senha;
		this.autorizacoes = perfis.stream().map(perfil -> new SimpleGrantedAuthority(perfil.getDescricao())).collect(Collectors.toList());
	}


	public Long getId() {
		return id;
	}
	
	/**
	 * Método responsável por verificar se o Usuário tem um perfil válido
	 * @param perfil : {@link PerfilCliente}
	 * @return Boolean
	 */
	public Boolean hasRole(PerfilCliente perfil) {
		return getAuthorities().contains(new SimpleGrantedAuthority(perfil.getDescricao()));
	}

	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return autorizacoes;
	}

	@Override
	public String getPassword() {
		return senha;
	}

	@Override
	public String getUsername() {
		return email;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
}
