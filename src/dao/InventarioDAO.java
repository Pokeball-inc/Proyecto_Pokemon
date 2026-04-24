package dao;

import model.*;

import java.sql.*;
import java.util.List;

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
        }}


       /**
        * Method para actualizar el inventario del Entrenador
        */


        public static void actualizarInventario(Connection con) {

           ///  Recuperar la lista de objetos del entrenador

            List<ObjetoInventario> listaMochila = Sesion.entrenadorLogueado.getInventario().getListaObjetos();

           /// Un update que actualizará en la tabla Inventario los datos del entrenador

            String sql = "INSERT INTO inventario (ID_ENTRENADOR, ID_OBJETO, CANTIDAD) VALUES (?, ?, ?) " +
                    "ON DUPLICATE KEY UPDATE CANTIDAD = VALUES(CANTIDAD)";

           try {

               PreparedStatement ps = con.prepareStatement(sql);

               for (ObjetoInventario objeto : listaMochila) {
                   ps.setInt(1, Sesion.entrenadorLogueado.getIdEntrenador());
                   ps.setInt(2, objeto.getObjeto().getIdObjeto());
                   ps.setInt(3, objeto.getCantidad());

                   ps.addBatch();
               }

               ps.executeBatch();

           } catch (SQLException ex) {
               System.err.println("Error al actualizar los objetos: " + ex.getMessage());
           }


       }
}




