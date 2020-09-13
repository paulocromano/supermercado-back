package com.romano.Supermercado.config;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;


/**
 * 
 * @author Paulo Romano - [paulo-romano_133@hotmail.com]
 * Classe responsável por ler o cabeçalho Location do Back-End
 */
@Component
public class HeaderExposureFilter implements Filter {
	
	@Override
		public void init(FilterConfig filterConfig) throws ServletException {

		}

	
	/**
	 * Método responsável por interceptar todas as Requisições
	 */
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		HttpServletResponse httpServletResponse = (HttpServletResponse) response;
		httpServletResponse.addHeader("access-control-expose-headers", "location");
		
		chain.doFilter(request, httpServletResponse);
	}
	
	
	@Override
	public void destroy() {

	}
}
