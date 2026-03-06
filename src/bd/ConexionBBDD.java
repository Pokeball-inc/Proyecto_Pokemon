package bd;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class ConexionBBDD {
	
	public Connection dataBaseLink;
	

	public Connection getConexion() {
		String dataBaseName = "proyecto_pokemoM";
		String dataBaseUser = "root";
		String dataBasePassword = "";
		String url = "jdbc:mysql://localhost:3306/" + dataBaseName;
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			dataBaseLink = DriverManager.getConnection(url, dataBaseUser, dataBasePassword);
			System.out.println("Conexión establecida");
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		} 
			return dataBaseLink;
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