package com.romano.Supermercado.conexaoBanco;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;


/**
 * 
 * @author Paulo Romano - [paulo-romano_133@hotmail.com]
 * Classe responsável por fazer conexão com o Banco de Dados
 */
public class ConexaoBanco {	
	
	/**
	 * Método responsável por retornar a conexão com o Banco de Dados
	 * @return Connection - Conexão com o Banco de Dados
	 */
	public static Connection getConexao() {
		Connection connection = null;
		
		if (connection == null) {
			try {
				Properties properties = loadProperties();
				String url = properties.getProperty("dburl");
				connection = DriverManager.getConnection(url, properties);
			}
			catch (SQLException e) {
				throw new DataBaseException(e.getMessage());
			}

		}
		return connection;
	}

	
	/**
	 * Método responsável por retonar as informações necessárias de conexão com o Banco
	 * @return Properties - Propriedades para conexão com o Banco de Dados
	 */
	private static Properties loadProperties() {
		try (FileInputStream fileInputStream = new FileInputStream("src/main/resources/application.properties")) {
			Properties properties = new Properties();
			properties.load(fileInputStream);
			
			return properties;
		}
		catch (IOException e) {
			throw new DataBaseException(e.getMessage());
		}
	}
	
	
	/**
	 * Método responsável por fechar a conexão com o Banco de Dados
	 * @param connection : Connection
	 */
	public static void closeConnection(Connection connection) {
		try {
			connection.close();
		}
		
		catch (SQLException e) {
			throw new DataBaseException(e.getMessage());
		}
	}
	
	
	/**
	 * Método responsável por fechar o Statement
	 * @param statement : Statement
	 */
	public static void closeStatement(Statement statement) {
		try {
			if (statement != null) {
				statement.close();
			}
		}
		
		catch (SQLException e) {
			throw new DataBaseException(e.getMessage());
		}
	}
	
	
	/**
	 * Método responsável por fechar o ResultSet
	 * @param resultSet : ResultSet
	 */
	public static void closeResultSet(ResultSet resultSet) {
		try {
			if (resultSet != null) {
				resultSet.close();
			}
		}
		
		catch (SQLException e) {
			throw new DataBaseException(e.getMessage());
		}
	}
	
	
	/**
	 * Método responsável por fechar o ResultSet, Statement e Connection
	 * @param resultSet : ResultSet
	 * @param statement : Statement
	 * @param connection : Connection
	 */
	public static void close(ResultSet resultSet, Statement statement, Connection connection) {
		closeResultSet(resultSet);
		closeStatement(statement);
		closeConnection(connection);
	}
}
