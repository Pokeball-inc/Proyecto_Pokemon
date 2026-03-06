package bd;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionBBDD {

	private static String url = "jdbc:mysql://localhost:3306/proyecto_pokemon";
	private static String login = "root";
	private static String password = "";
	 

	@SuppressWarnings("finally")
	public static Connection getConexion() {
		Connection connection = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			connection = DriverManager.getConnection(url, login, password);
			System.out.println("Conexión establecida");
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			try {
				connection.close();
				System.out.println("Conexión cerrada");
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return connection;
		}
	}

}

/*
 * 
 * TODO
 * terminar conexion y comproar, esta cogida de los apuntes
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * */