package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;

import model.Entrenador;
import model.Estados;
import model.Objeto;
import model.Pokemon;
import model.Sexo;
import model.Tipos;
import model.UbicacionPokemon;

public class PokemonDAO {

	public static void obtenerPokemon(Connection conexion, Entrenador e, UbicacionPokemon ubicacion) throws SQLException{
		// select con la informacion del pokemon 
		String sql = "SELECT "
	            + "P.ID_POKEMON, "
	            + "P.MOTE, "
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
	            + "PX.IMG_FRONTAL, "
	            + "PX.IMG_TRASERA "
	            + "FROM POKEMON P "
	            + "INNER JOIN POKEDEX PX ON PX.NUM_POKEDEX = P.NUM_POKEDEX "
	            + "WHERE P.ID_ENTRENADOR = ? AND P.UBICACION = ?";
		
		
		
		// añadir los objetos, movimientos y para evolucionar TODO
		/*
 				+ "		O.ID_OBJETO,\r\n"
				+ "		O.NOM_OBJETO,\r\n"
				+ "		O.DESCRIPCION\r\n"
				
				
								+ "LEFT JOIN OBJETO O \r\n"
				+ "	ON	P.ID_OBJETO = O.ID_OBJETO\r\n"
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
		while(rs.next()) {
			p = new Pokemon();
			p.setIdPokemon(rs.getInt("ID_POKEMON"));
			p.setNumPokedex(rs.getInt("NUM_POKEDEX"));
			p.setNombrePokemon(rs.getString("NOM_POKEMON"));
			p.setMotePokemon(rs.getString("MOTE"));
			p.setVitalidad(rs.getInt("VITALIDAD"));
			p.setAtaque(rs.getInt("ATAQUE"));
			p.setDefensa(rs.getInt("DEFENSA"));
			p.setAtaqueEspecial(rs.getInt("ATAQUE_SP"));
			p.setDefensaEspecial(rs.getInt("DEFENSA_SP"));
			p.setVelocidad(rs.getInt("VELOCIDAD"));
			p.setNivel(rs.getInt("NIVEL"));
			p.setExperiencia(rs.getInt("EXPERIENCIA"));
			// TODO mirar movimientos
			p.setMovimientos(null);
			p.setFertilidad(rs.getInt("FERTILIDAD"));
			//Lectura del sexo en caso de ser nulo 
			String sexoBd = rs.getString("SEXO");
            if (sexoBd != null) {
                p.setSexo(Sexo.valueOf(sexoBd.trim().toUpperCase()));
            } else {
                p.setSexo(Sexo.NEUTRO);
            }
			p.setTipoPrincipal(Tipos.valueOf(rs.getString("TIPO1").trim().toUpperCase()));
			//Lectura del tipo2 en caso de ser nulo 
			String tipo2Bd = rs.getString("TIPO2");
            if (tipo2Bd != null) {
                p.setTipoSecundario(Tipos.valueOf(tipo2Bd.trim().toUpperCase()));
            } else {
                p.setTipoSecundario(null);
            }
            //Lectura del estado en caso de ser nulo
            String estadoBd = rs.getString("ESTADO");
            if (estadoBd != null) {
                p.setEstado(Estados.valueOf(estadoBd.trim().toUpperCase()));
            } else {
                p.setEstado(Estados.SANO);
            }
			//TODO mirar para poner objets
			p.setObjetoEquipado(null);
			p.setEsShiny(rs.getBoolean("ES_SHINY"));
			p.setRatioCaptura(null);
			p.setUbicacion(ubicacion);
			//p.setNivelEvolucion(rs.getInt("NIVEL_EVOLUCION"));
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
	        // pasamos la lista directamente a la caja  si el equipo esta lleno
	        e.setCajaPokemon(listaAuxiliar);
	    }
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
