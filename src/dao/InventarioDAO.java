package dao;

import model.Inventario;
import model.Objeto;
import model.ObjetosTotales;
import model.Sesion;

import java.sql.*;

public class InventarioDAO {


    /**
     * Method para recuperar todos los objetos que existen en la base de datos
     */

    public static void cargarObjetosTotales(Connection con) {
        // buscamos el id mas alto en la tabla
        String sql = "SELECT * FROM OBJETO";
        try {

            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);

            // Limpiar la lista de objetos totales antes de cargar los objetos, por si acaso xd

            ObjetosTotales.todosLosObjetos.clear();


            // Ahora guardar los objetos en todosLosObjetos

            while (rs.next()) {
                Objeto obj = new Objeto(
                        rs.getInt("ID_OBJETO"),
                        rs.getString("NOM_OBJETO"),
                        rs.getString("DESCRIPCION"),
                        rs.getInt("PRECIO"),
                        rs.getInt("STATS_BONUS"),
                        rs.getInt("STATS_MALUS"),
                        rs.getInt("PORCENTAJE_BONUS"),
                        rs.getInt("PORCENTAJE_MALUS"),
                        rs.getString("RUTAIMAGEN")
                );

                // Y ahora guardarlo en la clase

                ObjetosTotales.todosLosObjetos.add(obj);
            }

        } catch (SQLException ex) {
            System.err.println("Error al cargar los objetos: " + ex.getMessage());
        }

    }

    /**
     * Method para cargar el inventario del Entrenador
     */


   public static void cargarInventario(int idEntrenador, Connection con) {

        ///  Instanciar un nuevo Inventario
        Inventario inventario = new Inventario();

        /// Un select multitabla que sacará everything sobre los objetos y su cantidad que estén en el inventario
       /// del entrenador

        String sql ="SELECT o.*, i.CANTIDAD FROM inventario i " +
               "JOIN objeto o ON i.ID_OBJETO = o.ID_OBJETO " +
               "WHERE i.ID_ENTRENADOR = ?";
        try {

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1,idEntrenador);
            ResultSet rs = ps.executeQuery();


            /// Ahora creamos objetos usando los resultados del select anterior, donde nos devolvía everything sobre
            /// los objetos

            while (rs.next()) {

                Objeto obj = new Objeto(
                        rs.getInt("ID_OBJETO"),
                        rs.getString("NOM_OBJETO"),
                        rs.getString("DESCRIPCION"),
                        rs.getInt("PRECIO"),
                        rs.getInt("STATS_BONUS"),
                        rs.getInt("STATS_MALUS"),
                        rs.getInt("PORCENTAJE_BONUS"),
                        rs.getInt("PORCENTAJE_MALUS"),
                        rs.getString("RUTAIMAGEN")
                );
                inventario.añadirObjeto(obj, rs.getInt("CANTIDAD"));
            }

            // Y aplicarselo al entrenador

            Sesion.entrenadorLogueado.setInventario(inventario);

        } catch (SQLException ex) {
            System.err.println("Error al cargar los objetos: " + ex.getMessage());
        }
    }
}




