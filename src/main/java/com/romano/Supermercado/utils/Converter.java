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
	public static String converterLocalDateParaString(LocalDate data) {
		return DateTimeFormatter.ofPattern("dd/MM/yyyy").format(data);
	}
	
	
	/**
	 * Método responsável por converter um LocalDate para String <br>
	 * @param data : LocalDateTime
	 * @param pattern : String
	 * @return String - Data convertida
	 */
	public static String converterLocalDateParaString(LocalDate data, String pattern) {
		return DateTimeFormatter.ofPattern(pattern).format(data);
	}
	
	
	/**
	 * Método responsável por converter uma data no formato de String para LocalDate <br>
	 * Pattern da Data: "dd/MM/yyyy"
	 * @param data : String 
	 * @return LocalDate - Data convertida
	 */
	public static LocalDate converterStringParaLocalDate(String data) {
		DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		LocalDate dataConvertida = LocalDate.parse(data, dateFormatter);
		
		return dataConvertida;
	}
	
	
	/**
	 * Método responsável por converter uma data no formato de String para LocalDate
	 * @param data : String 
	 * @param pattern : String
	 * @return LocalDate - Data convertida
	 */
	public static LocalDate converterStringParaLocalDate(String data, String pattern) {
		DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(pattern);
		LocalDate dataConvertida = LocalDate.parse(data, dateFormatter);
		
		return dataConvertida;
	}
	
	
	/**
	 * Método responsável por converter um LocalDateTime com a data e horário atual para String
	 * @return String - Data convertida
	 */
	public static String converterLocalDateTimeAtualParaString() {
	    LocalDateTime agora = LocalDateTime.now();
	    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
	    
	    return formatter.format(agora);
	}
	
	/**
	 * Método responsável por formatar um Double para duas casas decimais e converter em String
	 * @param valor : Double
	 * @return String - Valor convertido com duas casas decimais
	 */
	public static String converterDoubleParaDuasCasasDecimaisEmString(Double valor) {		
		return new DecimalFormat("#.00").format(valor).replace(',', '.');
	}
}
