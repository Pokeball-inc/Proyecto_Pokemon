package dao;

import java.awt.dnd.DropTargetContext;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Random;

import model.Entrenador;

public class EntrenadorDAO {

	// metodo para crear entrenador
	public static void crearEntrenador(Connection con, Entrenador e)throws SQLException {
		e.setIdEntrenador(obtenerIdEntrenador(con));
		e.setPokedollares(generarPokedollares());
		String sql = "INSERT INTO ENTRENADOR VALUES(?, ?, ?, ?, ?, ?, ?)";
		
		// introducir los values 
		PreparedStatement ps = con.prepareCall(sql);
		ps.setString(1, e.getNombreEntrenador());
		//ps.setString(2, e.getGenero());
		ps.setInt(3, e.getPokedollares());
		//ps.setString(4, e.getTipoEntrenador());
		ps.setString(5, e.getContrasena());
		ps.setBoolean(6, e.getEsNPC());
		ps.setString(7, e.getImgPosteriorEntrenador());
		
		
		// para ejecutar y crear el entrenador
		ps.executeUpdate();
	}	
	
	
	// metodo para obtener id del entrenador, en nuestro caso tenemos el autoincrement, peor lo dejo como ejemplo
	private static int obtenerIdEntrenador(Connection con) throws SQLException {
		int idEntreandor = 0;
		String sql = "SELECT MAX(ID_ENTRENADOR) FROM ENTRENADOR";
		Statement st = con.createStatement();
		ResultSet rs = st.executeQuery(sql);
		
		while(rs.next()) {
			idEntreandor = rs.getInt(1);
		}
		
		return  idEntreandor +1;
	}
	
	
	private static int generarPokedollares() {
		Random rd = new Random();
		return rd.nextInt(800)+1000;
	}
	
	
	
	// Obtener Pokedollares por id
	private static void obtenerIDPokedollares(Connection con, Entrenador e) throws SQLException{
		String sql = "SELECT ID_ENTRENADOR, POKEDOLLARES\r\n"
				+ "FROM ENTRENADOR \r\n"
				+ "WHERE NOM_ENTRENADOR \r\n"
				+ "AND PASSWORD = ?";
		
		PreparedStatement ps = con.prepareStatement(sql);
		ps.setString(1, e.getNombreEntrenador());
		ps.setString(2, e.getContrasena());
		ps.setInt(3, e.getPokedollares());
		
		ResultSet rs = ps.executeQuery();
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
