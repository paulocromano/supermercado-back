package com.romano.Supermercado.utils;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 
 * @author Paulo Romano - [paulo-romano_133@hotmail.com]
 * Classe responsável pela conversão de dados
 */
public final class Converter {

	/**
	 * Método responsável por converter um LocalDate para String <br>
	 * Pattern da Data: "dd/MM/yyyy"
	 * @param data : LocalDateTime
	 * @return String - Data convertida
	 */
	public static final String localDateParaString(LocalDate data) {
		try {
			return DateTimeFormatter.ofPattern("dd/MM/yyyy").format(data);
		}
		
		catch (NullPointerException e) {
			throw new NullPointerException("A data está nula!");
		}
	}
	
	
	/**
	 * Método responsável por converter uma data no formato de String para LocalDate <br>
	 * Pattern da Data: "dd/MM/yyyy"
	 * @param data : String 
	 * @return LocalDate - Data convertida
	 */
	public static final LocalDate stringParaLocalDate(String data) {
		try {
			DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
			LocalDate dataConvertida = LocalDate.parse(data, dateFormatter);
			
			return dataConvertida;
		}

		catch (NullPointerException e) {
			throw new NullPointerException("A data está nula!");
		}
	}
	
	
	/**
	 * Método responsável por converter um LocalDateTime com a data e horário atual para String <br>
	 * Pattern Data e Hora: dd/MM/yyyy HH:mm:ss
	 * @return String - Data convertida
	 */
	public static final String localDateTimeAtualParaString() {
	    LocalDateTime agora = LocalDateTime.now();
	    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
	    
	    return formatter.format(agora);
	}
	
	
	/**
	 * Método responsável por converter um LocalDateTime com a data e horário atual para String
	 * @param pattern : String
	 * @return String - Data convertida
	 */
	public static final String localDateTimeAtualParaString(String pattern) {
		try {
		    LocalDateTime agora = LocalDateTime.now();
		    DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
		    
		    return formatter.format(agora);
		}
		
		catch (IllegalArgumentException e) {
			throw new IllegalArgumentException("O formato do pattern de data é inválido!");
		}
	}
	
	/**
	 * Método responsável por formatar um Double para duas casas decimais e converter em String
	 * @param valor : Double
	 * @return String - Valor convertido com duas casas decimais
	 */
	public static final String doubleParaDuasCasasDecimaisEmString(Double valor) {		
		return new DecimalFormat("#.00").format(valor).replace(',', '.');
	}
}
