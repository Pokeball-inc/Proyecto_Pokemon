package dao;

import bd.ConexionBBDD;
import model.Entrenador;

import java.sql.*;
import java.util.Random;

public class EntrenadorDAO {

    // metodo para crear entrenador en la base de datos
    public static void crearEntrenador(Connection con, Entrenador e) throws SQLException {
        // generamos cnatidad aleatoria de pokedollares
        e.setPokedollares(generarPokedollares());

        // consulta SQL para inserccion de datos
        String sql = "INSERT INTO ENTRENADOR (NOM_ENTRENADOR, POKEDOLARES, CONTRASENA, ES_NPC, IMG_ENTRENADOR) VALUES(?, ?, ?, ?, ?)";

        // introducir los values
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, e.getNombreEntrenador());
        // ps.setString(2, e.getGenero());
        ps.setInt(2, e.getPokedollares());
        // ps.setString(4, e.getTipoEntrenador());
        ps.setString(3, e.getContrasena());
        ps.setBoolean(4, e.getEsNPC());
        ps.setString(5, e.getImgPosteriorEntrenador());

        // ejecutamos consulta y creamos el entrenador
        ps.executeUpdate();

        // extraemos el id de la base de datos que esta auto increment
        ResultSet rs = ps.getGeneratedKeys();
        if (rs.next()) {
            int idGenerado = rs.getInt(1);
            // Se lo asignamos al objeto para que ya tenga su ID real
            e.setIdEntrenador(idGenerado);
            System.out.println("Entrenador creado con ID: " + idGenerado);
        }
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

    // busca en la base de datos el ID y Pokedollares de un entrenador basandose en
    // su nombre y contraseña
    public static void obtenerIDPokedollares(Connection con, Entrenador e) throws SQLException {

        // seleccionamos las columnas qa obtener
        String sql = "SELECT ID_ENTRENADOR, POKEDOLARES\r\n" + "FROM ENTRENADOR \r\n" + "WHERE NOM_ENTRENADOR = ?\r\n"
                + "AND CONTRASENA =?";

        // creamos el objeto ps con la conexión y el SQL
        PreparedStatement ps = con.prepareStatement(sql);
        // rellenamos los datos faltantes con los del entrenador
        ps.setString(1, e.getNombreEntrenador());
        ps.setString(2, e.getContrasena());

        // lanzamos la consulta a MySQL y guardamos el resultado en rs
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            // extraemos el valor de la columna ID_ENTRENADOR"y lo guardamos en el
            // entrenador
            e.setIdEntrenador(rs.getInt("ID_ENTRENADOR"));
            // extraemos el valor de la columna "POKEDOLARES" y lo guardamos
            e.setPokedollares(rs.getInt("POKEDOLARES"));
        }

    }

    // metodo para el login y devuelve el entrenador logueado
    public Entrenador login(String usuario, String contrasena) {

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

            // si encuentra un resultado, creamos el Entrenador con sus Pokedolares
            if (rs.next()) {
                Entrenador e = new Entrenador();
                e.setIdEntrenador(rs.getInt("ID_ENTRENADOR"));
                e.setNombreEntrenador(rs.getString("NOM_ENTRENADOR"));
                e.setPokedollares(rs.getInt("POKEDOLARES"));
                e.setEsNPC(rs.getBoolean("ES_NPC"));
                e.setImgPosteriorEntrenador(rs.getString("IMG_ENTRENADOR"));

                return e; // Login correcto: devolvemos el entrenador con sus datos
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        // sino encuentra el entrenador entonces se devuelve null
        return null;
    }

    // metodo para el registro de nuevos usuarios
    public boolean registrar(Entrenador e) {

        // creamos la conexion con la base de datos
        ConexionBBDD conexion = new ConexionBBDD();
        Connection con = conexion.getConexion();

        // sustituimos los ? por los valores introducidos por el usuario
        String sql = "INSERT INTO ENTRENADOR (NOM_ENTRENADOR, CONTRASENA) VALUES (?, ?)";

        try {
            // preparamos la sentencia
            PreparedStatement ps = con.prepareStatement(sql);

            // Sustituimos los ? por los valores introducidos por el usuario
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
            // preparamos la sentencia
            PreparedStatement ps = con.prepareStatement(sql);
            // pasamos el nombre del usuario al primer ?
            ps.setString(1, usuario);

            ResultSet rs = ps.executeQuery();

            // si devuelve algo el usuario ya existe
            return rs.next();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    // metodo para actualizar los pokedolares para casino, y seguramente tambien
    // apra combate
    public boolean actualizarPokedollares(int idEntrenador, int nuevoSaldo) {
        // creamos la conexion con la base de datos
        ConexionBBDD conexion = new ConexionBBDD();
        Connection con = conexion.getConexion();

        // sentencia sql para modificar el saldo del entrenador especifico
        String sql = "UPDATE ENTRENADOR SET POKEDOLARES = ? WHERE ID_ENTRENADOR = ?";

        try {
            // preparamos la consulta
            PreparedStatement ps = con.prepareStatement(sql);
            // pasamos los parametros de nuevo saldo e id
            ps.setInt(1, nuevoSaldo);
            ps.setInt(2, idEntrenador);

            // actualizamos en la bd
            int filasAfectadas = ps.executeUpdate();
            // devuelve true si modificamos algo
            return filasAfectadas > 0;
        } catch (Exception e) {
            // si hay error mostramos el trazado y devuelve false
            e.printStackTrace();
            return false;
        } finally {
            // cerramos conexion
            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    /**
     * Metodo para actualizar victorias, derrotas y Pokedollares tras un combate
     */
    public boolean actualizarEstadisticasPostCombate(Entrenador e) {
        ConexionBBDD conexion = new ConexionBBDD();
        Connection con = conexion.getConexion();

        // preparamos la consulta para actualizar los 3 valores a la vez respecto al ID
        String sql = "UPDATE ENTRENADOR SET VICTORIAS = ?, DERROTAS = ?, POKEDOLARES = ? WHERE ID_ENTRENADOR = ?";

        try {
            PreparedStatement ps = con.prepareStatement(sql);

            // Obtenemos los datos actualizados del  Entrenador
            ps.setInt(1, e.getVictorias());
            ps.setInt(2, e.getDerrotas());
            ps.setInt(3, e.getPokedollares());
            ps.setInt(4, e.getIdEntrenador());

            int filas = ps.executeUpdate();
            return filas > 0;

        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        } finally {
            try {
                if (con != null) con.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    /**
     * mtodo para buscar un entrenador por su ID para la liga.
     */
    public Entrenador buscarPorId(int id) {
        ConexionBBDD conexion = new ConexionBBDD();
        Connection con = conexion.getConexion();

        String sql = "SELECT * FROM ENTRENADOR WHERE ID_ENTRENADOR = ?";

        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Entrenador e = new Entrenador();
                e.setIdEntrenador(rs.getInt("ID_ENTRENADOR"));
                e.setNombreEntrenador(rs.getString("NOM_ENTRENADOR"));
                e.setPokedollares(rs.getInt("POKEDOLARES"));
                e.setEsNPC(rs.getBoolean("ES_NPC"));
                e.setImgFrontalEntrenador(rs.getString("IMG_ENTRENADOR"));

                return e; // devolvemos al entrenador cargado
            }

        } catch (Exception ex) {
            System.out.println("Error al buscar entrenador por ID: " + ex.getMessage());
            ex.printStackTrace();
        } finally {
            try {
                if (con != null) con.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }

        return null;
    }
}
