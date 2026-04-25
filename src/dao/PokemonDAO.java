package dao;

import model.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Clase PokemonDAO
 * Encargada de gestionar todas las operaciones de acceso a datos (consultas,
 * actualizaciones, etc.) relacionadas con la entidad Pokemon en la base de datos MySQL.
 */
public class PokemonDAO {


    /**
     * Recupera la lista de Pokemon de la base de datos para un entrenador especifico
     * y segun su ubicacion (EQUIPO o CAJA), y los asigna al objeto Entrenador.
     * * @param conexion La conexion activa a la base de datos.
     *
     * @param e         El entrenador del cual queremos recuperar los Pokemon.
     * @param ubicacion La ubicacion de los Pokemon a recuperar (EQUIPO o CAJA).
     * @throws SQLException Si ocurre un error al ejecutar la consulta SQL.
     */
    public static void obtenerPokemon(Connection conexion, Entrenador e, UbicacionPokemon ubicacion)
            throws SQLException {
        // select con la informacion del pokemon
        String sql = "SELECT "
                + "P.ID_POKEMON, "
                + "P.MOTE, "
                + "P.VITALIDAD_MAXIMA, "
                + "P.VITALIDAD, "
                + "P.ATAQUE, "
                + "P.DEFENSA, "
                + "P.ATAQUE_SP, "
                + "P.DEFENSA_SP, "
                + "P.VELOCIDAD, "
                + "P.NIVEL, "
                + "P.EXPERIENCIA, "
                + "P.FERTILIDAD, "
                + "P.SEXO,"
                + "P.ESTADO,"
                + "P.ES_SHINY, "
                + "PX.NUM_POKEDEX, "
                + "PX.NOM_POKEMON, "
                + "PX.TIPO1, "
                + "PX.TIPO2, "
                + "PX.EVOLUCION_1, "
                + "PX.NIVEL_EVOLUCION_1, "
                + "PX.EVOLUCIONA_A,"
                + "PX.IMG_FRONTAL, "
                + "PX.IMG_TRASERA, "
                + "PX.IMG_FRONTAL3D, "
                + "PX.IMG_TRASERA3D, "
                + "PX.IMG_SHINY_FRONTAL, "
                + "PX.IMG_SHINY_TRASERA, "
                + "PX.IMG_SHINY_FRONTAL3D, "
                + "PX.IMG_SHINY_TRASERA3D, "
                + "P.ID_OBJETO "
                + "FROM POKEMON P "
                + "INNER JOIN POKEDEX PX ON PX.NUM_POKEDEX = P.NUM_POKEDEX "
                + "WHERE P.ID_ENTRENADOR = ? AND P.UBICACION = ?";

        // añadir los objetos, movimientos y para evolucionar TODO
        /*
         * + "		O.ID_OBJETO,\r\n" + "		O.NOM_OBJETO,\r\n" +
         * "		O.DESCRIPCION\r\n"
         * * * + "LEFT JOIN OBJETO O \r\n" + "	ON	P.ID_OBJETO = O.ID_OBJETO\r\n"
         */

        // preparamos la consulta
        PreparedStatement ps = conexion.prepareStatement(sql);
        // introducimos los values
        ps.setInt(1, e.getIdEntrenador());
        ps.setString(2, ubicacion.name());

        // lanzamos la consulta a MySQL y guardamos el resultado en rs
        ResultSet rs = ps.executeQuery();

        // lista temporal para ir guardando lo que saque el ResultSet
        ArrayList<Pokemon> listaAuxiliar = new ArrayList<>();

        // creamos el pokemon y asignamos los valores respectivos a la bd
        Pokemon p;
        //insanciamos el DAO
        MovimientoDAO movDAO = new MovimientoDAO(conexion);
        while (rs.next()) {
            p = new Pokemon();
            p.setIdPokemon(rs.getInt("ID_POKEMON"));
            p.setNumPokedex(rs.getInt("NUM_POKEDEX"));
            p.setNombrePokemon(rs.getString("NOM_POKEMON"));
            p.setMotePokemon(rs.getString("MOTE"));
            p.setVitalidadMaxima(rs.getInt("VITALIDAD_MAXIMA"));
            p.setVitalidad(rs.getInt("VITALIDAD"));
            p.setAtaque(rs.getInt("ATAQUE"));
            p.setDefensa(rs.getInt("DEFENSA"));
            p.setAtaqueEspecial(rs.getInt("ATAQUE_SP"));
            p.setDefensaEspecial(rs.getInt("DEFENSA_SP"));
            p.setVelocidad(rs.getInt("VELOCIDAD"));
            p.setNivel(rs.getInt("NIVEL"));
            p.setExperiencia(rs.getInt("EXPERIENCIA"));
            //para extreaer los movimientos de la bd, creamos el array
            Movimiento[] movimientosCargados = new Movimiento[4];

            ///añadimos la columna PP a la consulta
            String sqlMovs = "SELECT ID_MOVIMIENTO, PP FROM SET_MOVIMIENTOS WHERE ID_POKEMON = ? AND ES_ACTIVO = 1 LIMIT 4";

            // abrimos un try para gestionar la consulta de los movimientos
            try (PreparedStatement psMovs = conexion.prepareStatement(sqlMovs)) {
                psMovs.setInt(1, p.getIdPokemon());
                // ejecutamos la consulta y abrimos otro try para manejar los resultados
                try (ResultSet rsMovs = psMovs.executeQuery()) {
                    int i = 0;
                    // mientras la base de datos nos devuelva filas y no hayamos llenado los 4 huecos:
                    while (rsMovs.next() && i < 4) {

                        ///recuperamos el movimiento de la BD
                        Movimiento mov = movDAO.buscarPorId(rsMovs.getInt("ID_MOVIMIENTO"));

                        if (mov != null) {
                            ///cargan los PP actuales
                            mov.setCantidadMovimientos(rsMovs.getInt("PP"));

                            // Lo guardamos en el array
                            movimientosCargados[i] = mov;
                        }
                        i++;
                    }
                }
            }

            p.setMovimientos(movimientosCargados);
            p.setFertilidad(rs.getInt("FERTILIDAD"));
            // Lectura del sexo en caso de ser nulo
            String sexoBd = rs.getString("SEXO");
            if (sexoBd != null) {
                p.setSexo(Sexo.valueOf(sexoBd.trim().toUpperCase()));
            } else {
                p.setSexo(Sexo.NEUTRO);
            }
            p.setTipoPrincipal(Tipos.valueOf(rs.getString("TIPO1").trim().toUpperCase()));
            // Lectura del tipo2 en caso de ser nulo
            String tipo2Bd = rs.getString("TIPO2");
            if (tipo2Bd != null) {
                p.setTipoSecundario(Tipos.valueOf(tipo2Bd.trim().toUpperCase()));
            } else {
                p.setTipoSecundario(null);
            }
            // Lectura del estado en caso de ser nulo
            String estadoBd = rs.getString("ESTADO");
            if (estadoBd != null) {
                p.setEstadoActual(Estados.valueOf(estadoBd.trim().toUpperCase()));
            } else {
                p.setEstadoActual(Estados.SANO);
            }
            p.setObjetoEquipado(null);
            p.setEsShiny(rs.getBoolean("ES_SHINY"));
            p.setRatioCaptura(null);
            p.setUbicacion(ubicacion);
            // p.setNivelEvolucion(rs.getInt("NIVEL_EVOLUCION"));
            p.setImgFrontalPokemon(rs.getString("IMG_FRONTAL"));
            p.setImgPosteriorPokemon(rs.getString("IMG_TRASERA"));
            p.setImgFrontalPokemon3D(rs.getString("IMG_FRONTAL3D"));
            p.setImgPosteriorPokemon3D(rs.getString("IMG_TRASERA3D"));


            ///  Logica para recuperar el objeto, mientras no sea 0 o nulo

            InventarioDAO.cargarObjetosTotales(conexion);

            int idObj = rs.getInt("ID_OBJETO");
            if (idObj > 0) {
                Objeto equipado = ObjetosTotales.todosLosObjetos.stream().filter(obj -> obj.getIdObjeto() == idObj).findFirst().orElse(null);
                p.setObjetoEquipado(equipado);

            } else {
                p.setObjetoEquipado(null);
            }

            // añadimos el pokemon a la lista auxiliar
            listaAuxiliar.add(p);

        }
        // rellenamos al entrenador segun donde este el pokemon
        if (ubicacion == UbicacionPokemon.EQUIPO) {
            // pasamos la lista al array de 6
            Pokemon[] equipo = new Pokemon[6];
            for (int i = 0; i < listaAuxiliar.size() && i < 6; i++) {
                // introducimos los valores de la lsita en el array
                equipo[i] = listaAuxiliar.get(i);
            }
            // ponemos el equipo en equipoPokemon del entrenadr
            e.setEquipoPokemon(equipo);

        } else if (ubicacion == UbicacionPokemon.CAJA) {
            // pasamos la lista directamente a la caja si el equipo esta lleno
            e.setCajaPokemon(listaAuxiliar);
        }

    }

    /**
     * Actualiza el valor de fertilidad de un Pokemon en la base de datos,
     * generalmente despues de que este haya participado en un proceso de crianza.
     * * @param con La conexion activa a la base de datos.
     *
     * @param p El Pokemon cuya fertilidad se va a actualizar.
     */
    public static void actualizarFertilidadBD(Connection con, Pokemon p) {
        String sql = "UPDATE POKEMON SET FERTILIDAD = ? WHERE ID_POKEMON = ?";
        try {
            // 1. Imprimimos los datos ANTES de enviarlos a la BD
            System.out.println("Intentando actualizar a " + p.getNombrePokemon() + " | ID: " + p.getIdPokemon()
                    + " | Nueva Fertilidad: " + p.getFertilidad());

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, p.getFertilidad());
            ps.setInt(2, p.getIdPokemon());

            // 2. Guardamos el numero de filas que se han modificado
            int filasAfectadas = ps.executeUpdate();

            // 3. Comprobamos si realmente se ha actualizado algo
            System.out.println("Filas actualizadas en la BD: " + filasAfectadas);

            if (filasAfectadas == 0) {
                System.out.println("¡ALERTA! No se actualizó ningún Pokémon. Revisa si el ID existe en tu tabla.");
            }

        } catch (Exception e) {
            System.out.println("¡ERROR SQL AL ACTUALIZAR LA FERTILIDAD!");
            e.printStackTrace();
        }
    }

    /**
     * Actualiza todas las estadisticas de combate de un Pokemon en la base de datos,
     * ademas de su nivel y experiencia, usualmente tras un entrenamiento o combate.
     * * @param con La conexion activa a la base de datos.
     *
     * @param p El Pokemon con las nuevas estadisticas a guardar.
     */
    public static void actualizarStatsBD(Connection con, Pokemon p) {
        // sentencia sql para modificar las estadisticas del pokemon
        String sql = "UPDATE POKEMON SET VITALIDAD = ?, VITALIDAD_MAXIMA = ?, ATAQUE = ?, DEFENSA = ?, ATAQUE_SP = ?, DEFENSA_SP = ?, VELOCIDAD = ?, NIVEL = ?, EXPERIENCIA = ? WHERE ID_POKEMON = ?";
        try {
            // preparamos la consulta
            PreparedStatement ps = con.prepareStatement(sql);

            int vitParaGuardar = p.getVitalidad();
            int vitMaxBase = p.getBaseVitalidadMaxima();

            // para nunca guardar en la bd una vitalidad  mayor a la vitalidad mazima
            if (vitParaGuardar > vitMaxBase) {
                vitParaGuardar = vitMaxBase;
            }

            // introducimos los values
            ps.setInt(1, p.getVitalidad());
            ps.setInt(2, p.getBaseVitalidadMaxima());
            ps.setInt(3, p.getBaseAtaque());
            ps.setInt(4, p.getBaseDefensa());
            ps.setInt(5, p.getBaseAtaqueEspecial());
            ps.setInt(6, p.getBaseDefensaEspecial());
            ps.setInt(7, p.getBaseVelocidad());
            ps.setInt(8, p.getNivel());
            ps.setInt(9, p.getExperiencia());
            ps.setInt(10, p.getIdPokemon());

            // actualizamos en la bd
            ps.executeUpdate();
        } catch (Exception e) {
            // si hay error mostramos el trazado
            e.printStackTrace();
        }
    }

    //Metodos evolucion

    /**
     * Consulta la Pokedex para verificar si el Pokemon actual ha alcanzado o superado
     * el nivel necesario para evolucionar.
     * * @param con La conexion activa a la base de datos.
     *
     * @param p El Pokemon a evaluar.
     * @return El nombre de la especie a la que evoluciona, o null si no cumple los requisitos.
     */
    public static String comprobarEvolucionDisponible(Connection con, Pokemon p) {
        String sql = "SELECT NIVEL_EVOLUCION_1, EVOLUCIONA_A FROM POKEDEX WHERE NUM_POKEDEX = ?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, p.getNumPokedex());
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                int nivelReq = rs.getInt("NIVEL_EVOLUCION_1");
                String evolucionaA = rs.getString("EVOLUCIONA_A");

                // si tiene nivel suficiente y tiene una evolucion asignada
                if (nivelReq > 0 && p.getNivel() >= nivelReq && evolucionaA != null) {
                    return evolucionaA;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Ejecuta la transformacion de un Pokemon en la base de datos, actualizando
     * su referencia (ID_POKEDEX) a la de su nueva evolucion.
     * * @param con La conexion activa a la base de datos.
     *
     * @param p                    El Pokemon que va a evolucionar.
     * @param nombreNuevaEvolucion El nombre de la especie a la que se transforma.
     */
    public static void ejecutarEvolucionBD(Connection con, Pokemon p, String nombreNuevaEvolucion) {
        try {
            // buscamos el id de la pokedex de la nueva evolucion
            String sqlPokedex = "SELECT NUM_POKEDEX FROM POKEDEX WHERE NOM_POKEMON = ?";
            PreparedStatement ps1 = con.prepareStatement(sqlPokedex);
            ps1.setString(1, nombreNuevaEvolucion);
            ResultSet rs = ps1.executeQuery();

            if (rs.next()) {
                int nuevoNumPokedex = rs.getInt("NUM_POKEDEX");

                // actualizamos nuestro pokemon en la tabla
                String sqlUpdate = "UPDATE POKEMON SET NUM_POKEDEX = ? WHERE ID_POKEMON = ?";
                PreparedStatement ps2 = con.prepareStatement(sqlUpdate);
                ps2.setInt(1, nuevoNumPokedex);
                ps2.setInt(2, p.getIdPokemon());
                ps2.executeUpdate();

                // actualizamos el objeto en memoria para que no haya que reiniciar
                p.setNombrePokemon(nombreNuevaEvolucion);
                p.setNumPokedex(nuevoNumPokedex);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    ///  Metodo para actualizar el objeto que tiene equipado el pokemon

    public static void actualizarObjetoPokemon(Connection con, Pokemon p, Objeto objeto) {

        String sql = "UPDATE POKEMON SET ID_OBJETO = ? WHERE ID_POKEMON = ?";

        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ///  Si el objeto no es nulo, entonces se almacena, si no, se marca como nulo en la base de datos
            if (objeto != null) {
                ps.setInt(1, objeto.getIdObjeto());
            } else {
                ps.setNull(1, java.sql.Types.INTEGER);
            }

            ps.setInt(2, p.getIdPokemon());
            ps.executeUpdate();

            p.setObjetoEquipado(objeto);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    //metodo para recuperar un pokemon base directamente de la pokedex
    public static Pokemon obtenerPokemonDesdePokedex(Connection con, int numPokedex) {
        Pokemon p = new Pokemon();
        String sql = "SELECT * FROM POKEDEX WHERE NUM_POKEDEX = ?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, numPokedex);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                p.setNumPokedex(rs.getInt("NUM_POKEDEX"));
                p.setNombrePokemon(rs.getString("NOM_POKEMON"));

                //leemos los tipos de la pokedex
                p.setTipoPrincipal(Tipos.valueOf(rs.getString("TIPO1").trim().toUpperCase()));
                String tipo2Bd = rs.getString("TIPO2");
                if (tipo2Bd != null) {
                    p.setTipoSecundario(Tipos.valueOf(tipo2Bd.trim().toUpperCase()));
                }

                //leemos las imagenes 2d y 3d
                p.setImgFrontalPokemon(rs.getString("IMG_FRONTAL"));
                p.setImgPosteriorPokemon(rs.getString("IMG_TRASERA"));
                p.setImgFrontalPokemon3D(rs.getString("IMG_FRONTAL3D"));
                p.setImgPosteriorPokemon3D(rs.getString("IMG_TRASERA3D"));

                //shiny
                p.setImgShinyFrontal(rs.getString("IMG_SHINY_FRONTAL"));
                p.setImgShinyPosterior(rs.getString("IMG_SHINY_TRASERA"));
                p.setImgShinyFrontal3D(rs.getString("IMG_SHINY_FRONTAL3D"));
                p.setImgShinyPosterior3D(rs.getString("IMG_SHINY_TRASERA3D"));

                return p;
            }
        } catch (Exception e) {
            System.out.println("ERROR al buscar pokemon en la pokedex: " + e.getMessage());
        }
        return null;
    }

}