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

	// metodo para crear entrenador ( id de entrenador autoincremente en bbdd)
	public static void crearEntrenador(Connection con, Entrenador e) throws SQLException {
        e.setPokedollares(generarPokedollares());
        
        // al ser AUTO_INCREMENT, no enviamos el ID
        // especificamos las columnas para no tener errores de conteo
        String sql = "INSERT INTO ENTRENADOR (NOM_ENTRENADOR, POKEDOLLARES, CONTRASENA) VALUES (?, ?, ?)";
        
        // establecemos en un try los datos del entrenador
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, e.getNombreEntrenador());
            ps.setInt(2, e.getPokedollares());
            ps.setString(3, e.getContrasena()); // Password en texto plano

            
            ps.executeUpdate();
        }
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
	
	
	// para generar la cantidad inicial de pokedollares entre 800 y 1000
	private static int generarPokedollares() {
		Random rd = new Random();
		return rd.nextInt(201)+800;
	}
	
	
	/*
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
		
		while(rs.next()) {
			e.set
		}
	}
	*/
	
	// metodo de login
	public static Entrenador login(Connection con, String nombre, String password) throws SQLException {
        Entrenador entrenadorLogueado = null;
        
        String sql = "SELECT * FROM ENTRENADOR WHERE NOM_ENTRENADOR = ? AND CONTRASENA = ?";
        
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, nombre);
            ps.setString(2, password); 
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                	// creamos el entrenador y añadimos los datos de la base de datos
                    entrenadorLogueado = new Entrenador();
                    entrenadorLogueado.setIdEntrenador(rs.getInt("ID_ENTRENADOR"));
                    entrenadorLogueado.setNombreEntrenador(rs.getString("NOM_ENTRENADOR"));
                    entrenadorLogueado.setPokedollares(rs.getInt("POKEDOLLARES"));
                    entrenadorLogueado.setContrasena(rs.getString("CONTRASENA"));
                }
            }
        }
        return entrenadorLogueado;
    }
	
	
	
	// metodo para obtener dinero (por si se necesita refrescar solo eso)
    public static void cargarPokedollares(Connection con, Entrenador e) throws SQLException {
        String sql = "SELECT POKEDOLLARES FROM ENTRENADOR WHERE ID_ENTRENADOR = ?";
        
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, e.getIdEntrenador());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                e.setPokedollares(rs.getInt("POKEDOLLARES"));
            }
        }
    }
	
	
	
	
	
	
	
	
}
