package com.romano.Supermercado.setor.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.romano.Supermercado.conexaoBanco.ConexaoBanco;
import com.romano.Supermercado.conexaoBanco.DataBaseException;
import com.romano.Supermercado.conexaoBanco.DataBaseIntegrityException;
import com.romano.Supermercado.setor.model.Setor;


/**
 * 
 * @author Paulo Romano - [paulo-romano_133@hotmail.com]
 * Classe responsável por acessar os dados de Setor no Banco de Dados
 */
public class SetorRepository {
	
	/**
	 * Método responsável por listar todos os Setores
	 * @return List<Setor> 
	 */
	public static List<Setor> buscarTodosSetores() {
		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet = null;
		
		try {
			connection = ConexaoBanco.getConexao();
			statement = connection.createStatement();
			resultSet = statement.executeQuery("SELECT * FROM setor");
			
			List<Setor> listaSetor = new ArrayList<>();
			
			while (resultSet.next()) {
				listaSetor.add(
					new Setor( 
						resultSet.getInt("id"),
						resultSet.getString("nome")
					));
			}
			
			return listaSetor;
		}
		
		catch (SQLException e) {
			throw new DataBaseException(e.getMessage());
		}
		
		finally {
			ConexaoBanco.close(resultSet, statement, connection);
		}
	}
	
	
	/**
	 * Método responsável por inserir um novo Setor no Banco de Dados
	 * @param setor : Setor
	 */
	public static void cadastrarSetor(Setor setor) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		
		try {
			connection = ConexaoBanco.getConexao();
			preparedStatement = connection.prepareStatement("INSERT INTO setor (nome) VALUES (?)");
			
			preparedStatement.setString(1, setor.getNome());
			
			preparedStatement.executeUpdate();
		}
		
		catch (SQLException e) {
			throw new DataBaseException(e.getMessage());
		}
		
		finally {
			ConexaoBanco.closeStatement(preparedStatement);
			ConexaoBanco.closeConnection(connection);
		}
	}
	
	
	/**
	 * Método responsável por remover um Setor
	 * @param id : Integer
	 */
	public static void removerSetor(Integer id) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		
		try {
			connection = ConexaoBanco.getConexao();
			preparedStatement = connection.prepareStatement("DELETE FROM setor WHERE id = ?");
			
			preparedStatement.setInt(1, id);
			
			preparedStatement.executeUpdate();
		}
		
		catch (SQLException e) {
			throw new DataBaseIntegrityException(e.getMessage());
		}
		
		finally {
			ConexaoBanco.closeStatement(preparedStatement);
			ConexaoBanco.closeConnection(connection);
		}
	}
}
