package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;

import model.Entrenador;
import model.Estados;
import model.Movimiento;
import model.Objeto;
import model.Pokemon;
import model.Sexo;
import model.Tipos;
import model.UbicacionPokemon;

/**
 * Clase de Pokemon Dao para extraer informacion de la bd o actualizara
 * */
public class PokemonDAO {

	/**
	 * metodo para obtenrr un pokemon completo de la bd y guardarlo en el objeto pokemon con los datos extraidos
	 * @param recive una conexion a nuestra bd
	 * @param y recibe el entrenador al que pertenece el pokemon 
	 * @param por ultimo recibe la ubicacion del pokemon, si esta en el equipo o la caja
	 * @exception se trata la clase Excepcion, osea todas
	 * @throws puede lanzar la excepcion sql
	 * */
	public static void obtenerPokemon(Connection conexion, Entrenador e, UbicacionPokemon ubicacion)
			throws SQLException {
		// select con la informacion del pokemon
		String sql = "SELECT " 
				+ "P.ID_POKEMON, " 
				+ "P.MOTE, " 
				+ "P.VITALIDAD, " 
				+ "P.VITALIDAD_MAXIMA, " 
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
				+ "PX.IMG_FRONTAL, " 
				+ "PX.IMG_TRASERA " 
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
		//instanciamos el DAO
		MovimientoDAO movDAO = new MovimientoDAO(conexion);
		while (rs.next()) {
			p = new Pokemon();
			p.setIdPokemon(rs.getInt("ID_POKEMON"));
			p.setNumPokedex(rs.getInt("NUM_POKEDEX"));
			p.setNombrePokemon(rs.getString("NOM_POKEMON"));
			p.setMotePokemon(rs.getString("MOTE"));
			p.setVitalidad(rs.getInt("VITALIDAD"));
			p.setVitalidadMaxima(rs.getInt("VITALIDAD_MAXIMA"));
			p.setAtaque(rs.getInt("ATAQUE"));
			p.setDefensa(rs.getInt("DEFENSA"));
			p.setAtaqueEspecial(rs.getInt("ATAQUE_SP"));
			p.setDefensaEspecial(rs.getInt("DEFENSA_SP"));
			p.setVelocidad(rs.getInt("VELOCIDAD"));
			p.setNivel(rs.getInt("NIVEL"));
			p.setExperiencia(rs.getInt("EXPERIENCIA"));
			// para extreaer los movimientos de la bd, creamos el array
			Movimiento[] movimientosCargados = new Movimiento[4];
			// preparamos la consulta SQL
			String sqlMovs = "SELECT ID_MOVIMIENTO FROM SET_MOVIMIENTOS WHERE ID_POKEMON = ? AND ES_ACTIVO = 1 LIMIT 4";
			// abrimos un try para gestionar la consulta de los movimientos
			try (PreparedStatement psMovs = conexion.prepareStatement(sqlMovs)) {
		        psMovs.setInt(1, p.getIdPokemon());
		     // ejecutamos la consulta y abrimos otro try para manejar los resultados
		        try (ResultSet rsMovs = psMovs.executeQuery()) { // Doble try para cerrar el ResultSet de movimientos
		            int i = 0;
		         // mientras la base de datos nos devuelva filas y no hayamos llenado los 4 huecos:
		            while (rsMovs.next() && i < 4) {
		            	movimientosCargados[i] = movDAO.buscarPorId(rsMovs.getInt("ID_MOVIMIENTO"));
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
			// TODO mirar para poner objects
			p.setObjetoEquipado(null);
			p.setEsShiny(rs.getBoolean("ES_SHINY"));
			p.setRatioCaptura(null);
			p.setUbicacion(ubicacion);
			// p.setNivelEvolucion(rs.getInt("NIVEL_EVOLUCION"));
			p.setImgFrontalPokemon(rs.getString("IMG_FRONTAL"));
			p.setImgPosteriorPokemon(rs.getString("IMG_TRASERA"));

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
	 * Metodo para actualizar la fertilidad despues de la crianza
	 * @param recive una conexion a nuestra bd
	 * @param y recibe el pokemon a actulizar sus crianza
	 * @exception se trata la clase Excepcion, osea todas
	 * @throws puede lanzar la excepcion sql
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
	 * Metodo para actualizar estadisticasen la bd despues del entrenamiento
	 * @param recive una conexion a nuestra bd
	 * @param y recibe el pokemon a actulizar sus stats
	 * @exception se trata la clase Excepcion, osea todas
	 * @throws puede lanzar la excepcion sql
	 */
	public static void actualizarStatsBD(Connection con, Pokemon p) {
		// sentencia sql para modificar las estadisticas del pokemon
		String sql = "UPDATE POKEMON SET VITALIDAD = ?, VITALIDAD_MAXIMA = ?, ATAQUE = ?, DEFENSA = ?, ATAQUE_SP = ?, DEFENSA_SP = ?, VELOCIDAD = ? WHERE ID_POKEMON = ?";
		try {
			// preparamos la consulta
			PreparedStatement ps = con.prepareStatement(sql);
			// introducimos los values
			ps.setInt(1, p.getVitalidad());
			ps.setInt(2, p.getVitalidadMaxima());
			ps.setInt(3, p.getAtaque());
			ps.setInt(4, p.getDefensa());
			ps.setInt(5, p.getAtaqueEspecial());
			ps.setInt(6, p.getDefensaEspecial());
			ps.setInt(7, p.getVelocidad());
			ps.setInt(8, p.getIdPokemon());

			// actualizamos en la bd
			ps.executeUpdate();
		} catch (Exception e) {
			// si hay error mostramos el trazado
			e.printStackTrace();
		}
	}
	
	/**
     * Metodo para actualizar todala informacion del pokemon despues de cada combate
     */
    public static void actualizarPostCombate(Connection con, Pokemon p) {
        String sql = "UPDATE POKEMON SET "
                   + "NIVEL = ?, "
                   + "EXPERIENCIA = ?, "
                   + "VITALIDAD = ?, "
                   + "VITALIDAD_MAXIMA = ?, "
                   + "ATAQUE = ?, "
                   + "DEFENSA = ?, "
                   + "ATAQUE_SP = ?, "
                   + "DEFENSA_SP = ?, "
                   + "VELOCIDAD = ? "
                   + "WHERE ID_POKEMON = ?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, p.getNivel());
            ps.setInt(2, p.getExperiencia());
            ps.setInt(3, p.getVitalidad());
            ps.setInt(4, p.getVitalidadMaxima());
            ps.setInt(5, p.getAtaque());
            ps.setInt(6, p.getDefensa());
            ps.setInt(7, p.getAtaqueEspecial());
            ps.setInt(8, p.getDefensaEspecial());
            ps.setInt(9, p.getVelocidad());
            ps.setInt(10, p.getIdPokemon());

            int filas = ps.executeUpdate();
            if (filas > 0) {
                System.out.println("LOG: " + p.getNombrePokemon() + " guardado correctamente (Nivel " + p.getNivel() + ").");
            }
        } catch (SQLException e) {
            System.err.println("Error al actualizar Pokemon tras combate: " + e.getMessage());
            e.printStackTrace();
        }
    }

}