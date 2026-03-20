package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

import model.Entrenador;
import model.Objeto;
import model.Pokemon;

public class PokemonDAO {

	public static void obtenerPokemon(Connection conexion, Entrenador e, int caja) throws SQLException{
		String sql = "SELECT P.ID_POKEMON,\r\n"
				+ "		PX.NUM_POKEDEX,\r\n"
				+ "		PX.NOM_POKEMON,\r\n"
				+ "		P.MOTE,\r\n"
				+ "		P.SEXO,\r\n"
				+ "		P.ATAQUE,\r\n"
				+ "		P.ATA_ESPECIAL,\r\n"
				+ "		P.DEFENSA,\r\n"
				+ "		P.CAJA,\r\n"
				+ "		PX.IMAGEN,\r\n"
				+ "		PX.NIVEL_EVOLUCION,\r\n"
				+ "		PX.NUM_POKEDEX_EVO,\r\n"
				+ "		PX.SONIDO,\r\n"
				+ "		PX.TIPO1,\r\n"
				+ "		PX.TIPO2\r\n"
				+ "		O.ID_OBJETO,\r\n"
				+ "		O.NOM_OBJETO,\r\n"
				+ "		O.DESCRIPCION,\r\n"
				+ "FROM POKEMON P\r\n"
				+ "INNER JOIN POKEDEX PX \r\n"
				+ "	ON	PX.NUM_POKEDEX = P.NUM_POKEDEX\r\n"
				+ "LEFT JOIN OBJETO O \r\n"
				+ "	ON	P.ID_OBJETO = O.ID_OBJETO\r\n"
				+ "WHERE P.ID_ENTRENADOR = ?\r\n"
				+ "	AND P.UBICACION= ?";
		// TODO ajustar a lo requerido abajo
		
		PreparedStatement ps = conexion.prepareStatement(sql);
		ps.setInt(1, e.getIdEntrenador());
		ps.setInt(2, caja);
		
		ResultSet rs = ps.executeQuery();
		
		LinkedList<Pokemon> listadoPokemon = new LinkedList<Pokemon>();
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
			p.setSexo(model.Sexo.valueOf(rs.getString("SEXO").trim().toUpperCase()));
			p.setTipoPrincipal(model.Tipos.valueOf(rs.getString("TIPO1").trim().toUpperCase()));
			p.setTipoSecundario(model.Tipos.valueOf(rs.getString("TIPO2").trim().toUpperCase()));
			p.setEstado(model.Estados.valueOf(rs.getString("ESTADO").trim().toUpperCase()));
			//TODO mirar para poner objets
			p.setObjetoEquipado(null);
			p.setEsShiny(rs.getBoolean("ES_SHINY"));
			p.setImgFrontal(rs.getString("IMAGEN"));
			p.setNivelEvolucion(rs.getInt("NIVEL_EVOLUCION"));
			p.setTipoPrimario(Tipo.convertirTipoDesdeString(rs.getString("TIPO1")));
			p.setTipoSecundario(Tipo.convertirTipoDesdeString(rs.getString("TIPO2")));
			
			//MovimientoCrud.obtenerMovimientoPokemon(p);
			
			
			listadoPokemon.add(p);
		}
		
		if(caja == 1) {
			e.setEquipoPrincipal(listadoPokemon);
		}else {
			e.setPokemonCaja(listadoPokemon);
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
