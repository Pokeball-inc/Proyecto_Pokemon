package dao;

import java.awt.dnd.DropTargetContext;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Random;

import bd.ConexionBBDD;
import model.Entrenador;

public class EntrenadorDAO {

	// metodo para crear entrenador en la base de datos
	public static void crearEntrenador(Connection con, Entrenador e) throws SQLException {
		// generamos un id nuevo para el entrenador y cnatidad aleatoria de pokedollares
		e.setIdEntrenador(obtenerIdEntrenador(con));
		e.setPokedollares(generarPokedollares());
		
		// consulta SQL para inserccion de datos
		String sql = "INSERT INTO ENTRENADOR VALUES(?, ?, ?, ?, ?, ?, ?)";

		// introducir los values
		PreparedStatement ps = con.prepareStatement(sql);
		ps.setString(1, e.getNombreEntrenador());
		// ps.setString(2, e.getGenero());
		ps.setInt(3, e.getPokedollares());
		// ps.setString(4, e.getTipoEntrenador());
		ps.setString(5, e.getContrasena());
		ps.setBoolean(6, e.getEsNPC());
		ps.setString(7, e.getImgPosteriorEntrenador());

		// ejecutamos consulta y creamos el entrenador
		ps.executeUpdate();
	}

	// metodo para obtener id del entrenador, en nuestro caso tenemos el
	// autoincrement, peor lo dejo como ejemplo
	private static int obtenerIdEntrenador(Connection con) throws SQLException {
		int idEntreandor = 0;
		// buscamos el id mas alto en la tabla
		String sql = "SELECT MAX(ID_ENTRENADOR) FROM ENTRENADOR";
		Statement st = con.createStatement();
		ResultSet rs = st.executeQuery(sql);

		// guardamos el valor obtenido
		while (rs.next()) {
			idEntreandor = rs.getInt(1);
		}
		// devolvemos el siuguiente id
		return idEntreandor + 1;
	}
	
	// metodo para generar cantidad inicial de pokedollares entre 800 y 1000
	private static int generarPokedollares() {
		Random rd = new Random();
		return rd.nextInt(1000 - 800 + 1) + 800;
	}

	
	// busca en la base de datos el ID y Pokedollares  de un entrenador basandose en su nombre y contraseña
public static void obtenerIDPokedollares(Connection con, Entrenador e)throws SQLException{
	
		// seleccionamos las columnas qa obtener
		String sql ="SELECT ID_ENTRENADOR, POKEDOLARES\r\n"
				+ "FROM ENTRENADOR \r\n"
				+ "WHERE NOM_ENTRENADOR = ?\r\n"
				+ "AND PASSWORD =?";
		
		// creamos el objeto ps con la conexión y el SQL
		PreparedStatement ps = con.prepareStatement(sql);
		// rellenamos los datos faltantes con los del entrenador
		ps.setString(1, e.getNombreEntrenador());
		ps.setString(2, e.getContrasena ());
		
		// lanzamos la consulta a MySQL y guardamos el resultado en rs
		ResultSet rs = ps.executeQuery();
		
		
		while(rs.next()) {
			// extraemos el valor de la columna ID_ENTRENADOR"y lo guardamos en el entrenador
			e.setIdEntrenador(rs.getInt("ID_ENTRENADOR"));
			// extraemos el valor de la columna "POKEDOLARES" y lo guardamos
			e.setPokedollares(rs.getInt("POKEDOLARES"));
		}
		
	}


	// metodo para el login
	public boolean login(String usuario, String contrasena) {
		
		// creamos la conexion con la base de datos
		ConexionBBDD conexion = new ConexionBBDD();
		Connection con = conexion.getConexion();
		
		// consulta para comprobar usuario y contraseña
		String sql = "SELECT * FROM entrenador WHERE NOM_ENTRENADOR = ? AND CONTRASENA = ?";

		try {
			PreparedStatement ps = con.prepareStatement(sql);

			// sustituimos los ? por los valores introducidos por el usuario
			ps.setString(1, usuario);
			ps.setString(2, contrasena);

			ResultSet rs = ps.executeQuery();

			// si encuentra un resultado entonces login correcto
			if (rs.next()) {
				return true;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		// sino  encuentra un resultado entonces se devuelve false
		return false;
	}

	// metodo para el registro de nuevos usuarios
	public boolean registrar(Entrenador e) {

		// creamos la conexion con la base de datos
		ConexionBBDD conexion = new ConexionBBDD();
		Connection con = conexion.getConexion();

		// sustituimos los ? por los valores introducidos por el usuario
		String sql = "INSERT INTO ENTRENADOR (NOM_ENTRENADOR, CONTRASENA) VALUES (?, ?)";

		try {
			PreparedStatement ps = con.prepareStatement(sql);

			// Sustituimos los ? por los valores introducidos por el usu
			ps.setString(1, e.getNombreEntrenador());
			ps.setString(2, e.getContrasena());

			int filas = ps.executeUpdate();

			// Si inserta 1 fila al menos el registro es correcto
			return filas > 0;

		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return false;
	}

	// metoro para comprobar que exista el usuariop
	public boolean existeUsuario(String usuario) {

		// creamos la conexion con la base de datos
		ConexionBBDD conexion = new ConexionBBDD();
		Connection con = conexion.getConexion();

		// buscamos si existe ese nombre de usuario
		String sql = "SELECT * FROM ENTRENADOR WHERE NOM_ENTRENADOR = ?";

		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, usuario);

			ResultSet rs = ps.executeQuery();
			
			// si devuelve algo el usuario ya existe
			return rs.next();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return false;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
