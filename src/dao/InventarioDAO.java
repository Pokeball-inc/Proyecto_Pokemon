package dao;

import bd.ConexionBBDD;
import model.Entrenador;
import model.Objeto;
import model.ObjetosTotales;

import java.sql.*;
import java.util.Random;

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

}
