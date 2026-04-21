package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.Random;

import bd.ConexionBBDD;
import javafx.scene.paint.Color;
import model.Estados;
import model.Movimiento;
import model.Pokemon;
import model.Tipos;

public class CapturaDao {

	
	
	
	// metodo para crear el pokemon aleatorio desde la base de datos en captura
	public Pokemon crearPokemonAleatorio(Connection con) {
	    
	    Pokemon p = new Pokemon();

	    // de la  tabla pokedex ordenamos random y escogemos 1	
	    String sql = "SELECT * FROM POKEDEX ORDER BY RAND() LIMIT 1";

	    try {
	    	// preparamos la consulta
	        PreparedStatement ps = con.prepareStatement(sql);
	        
	        // ejecutamos y guardamos el resultado
	        ResultSet rs = ps.executeQuery();
	        
	        if (rs.next()) {
	        	// rellenamos los atributos basicos con los datos de las columnas de la bd
	            p.setNumPokedex(rs.getInt("NUM_POKEDEX"));
	            p.setNombrePokemon(rs.getString("NOM_POKEMON")); 
	            p.setImgFrontalPokemon(rs.getString("IMG_FRONTAL"));
	            p.setImgPosteriorPokemon(rs.getString("IMG_TRASERA"));

				generarStats(p);
	            
	            // empezamos enumerados
	            // si el tipo 1 no está vacio en la base de datos entonces: 
	            if (rs.getString("TIPO1") != null) {
	            	// convertimos el texto de la bd a mayusculas y lo buscamos en Enum Tipos
	                p.setTipoPrincipal(Tipos.valueOf(rs.getString("TIPO1").trim().toUpperCase()));

					// Le asignamos un color al pokemon en funcion de su tipo

					p.cambiarColor();


	            }
	            if (rs.getString("TIPO2") != null) {
	            	// hacemos lo mismo con el tipo 2
	                p.setTipoSecundario(Tipos.valueOf(rs.getString("TIPO2").trim().toUpperCase()));
	            } else {
					p.setTipoSecundario(null);
				}
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    // devolvemos el pokemon
	    return p;
	}
	
	
	// metodo para generar lso stats del pokemon atrapado
	public void generarStats(Pokemon p) {

	    Random r = new Random();
	    // para cada stat genera un numero aleatorio
	    p.setVitalidadMaxima(r.nextInt(50) + 50);       // 50-100
	    p.setAtaque(r.nextInt(20) + 10);          // 10-30
	    p.setDefensa(r.nextInt(20) + 10);	      // 10-30
	    p.setAtaqueEspecial(r.nextInt(20) + 10);  // 10-30
	    p.setDefensaEspecial(r.nextInt(20) + 10); // 10-30
	    p.setVelocidad(r.nextInt(20) + 10);		  // 10-30
	    // y nivel establecido a uno y experiencia a 0
	    p.setNivel(1);
	    p.setExperiencia(0);
	    p.setFertilidad(5); //fertilidad inicial a 5 
	    p.setEstadoActual(Estados.SANO);
	    //Generar el sexo aleatorio
	    int suerteSexo = r.nextInt(3);
	    if (suerteSexo == 0) {
	        p.setSexo(model.Sexo.MACHO);
	    } else if (suerteSexo == 1) {
	        p.setSexo(model.Sexo.HEMBRA);
	    } else {
	        p.setSexo(model.Sexo.NEUTRO); 
	    }
	}

	
	// metodo para guardar el pokemon capturado en la base de datos
	public void guardarPokemon(Connection con, Pokemon p, int idEntrenador, String ubicacion) {
		
		// preparamos la sentencia sql de insercion
		// ponemos un "?" por cada columna para que java rellene los huecos luego
		String sql = "INSERT INTO POKEMON (NUM_POKEDEX, ID_ENTRENADOR, MOTE, SEXO, VITALIDAD_MAXIMA, ATAQUE, DEFENSA, VELOCIDAD, ATAQUE_SP, DEFENSA_SP, NIVEL, EXPERIENCIA, FERTILIDAD, ESTADO, UBICACION) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

	    try {
	    	// preparamos la conexión para enviar esta consulta
	        PreparedStatement ps = con.prepareStatement(sql, java.sql.Statement.RETURN_GENERATED_KEYS); //Para que sql le diga el ID
	        
	        // rellenamos los values
	        ps.setInt(1, p.getNumPokedex());
	        ps.setInt(2, idEntrenador);
	        //Para el mote si es crianza (mezcla padres) y salvaje (su especie)
	        if (p.getMotePokemon() != null && !p.getMotePokemon().isEmpty()) {
	            ps.setString(3, p.getMotePokemon());
	        } else {
	            ps.setString(3, p.getNombrePokemon()); 
	        }
	        //Si es nulo el sexo toma valor neutro
	        if (p.getSexo() != null) {
	            ps.setString(4, p.getSexo().name());
	        } else {
	            ps.setString(4, "NEUTRO");
	        }
	        
	        ps.setInt(5, p.getVitalidadMaxima());
	        ps.setInt(6, p.getAtaque());
	        ps.setInt(7, p.getDefensa());
	        ps.setInt(8, p.getVelocidad());
	        ps.setInt(9, p.getAtaqueEspecial());
	        ps.setInt(10, p.getDefensaEspecial());
	        ps.setInt(11, p.getNivel());
	        ps.setInt(12, p.getExperiencia());
	        ps.setInt(13, p.getFertilidad());
	        //Si es nulo se le da estado SANO por defecto
	        if (p.getEstadoActual() != null) {
	            ps.setString(14, p.getEstadoActual().name()); 
	        } else {
	            ps.setString(14, "SANO");
	        }
	        ps.setString(15, ubicacion);

	        
	        //usamos el executeupdate para modificar la base de datos
	        ps.executeUpdate();
	        
	     //Pedimos las claves generadas (el ID autonumérico)
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                int idGenerado = rs.getInt(1); //Extraemos el número
                p.setIdPokemon(idGenerado);    //Se lo asignamos al objeto Java
                
                // asignamos un movimiento relacionado con el tipo dle pokemon
                asignarMovimientoInicial(con, p);
                //Chivato para comprobar 
                 System.out.println("¡Bebé/Captura guardado! Su ID real es: " + idGenerado);
            }

	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}


	// metodo para actualizar datos del pokemon en la base de datos
	public void actualizarPokemon(Connection con, Pokemon p, int idEntrenador, String ubicacion) {

		// preparamos la sentencia sql de actualizacion
		// ponemos un "?" por cada columna para que java rellene los huecos luego
		String sql = "UPDATE POKEMON SET ID_ENTRENADOR = ?, MOTE = ?, VITALIDAD_MAXIMA = ?, " +
				"ATAQUE = ?, DEFENSA = ?, VELOCIDAD = ?, ATAQUE_SP = ?, DEFENSA_SP = ?, " +
				"NIVEL = ?, EXPERIENCIA = ?, FERTILIDAD = ?, ESTADO = ?, UBICACION = ? " +
				"WHERE ID_POKEMON = ?";

		try {
			// preparamos la conexión para enviar esta consulta
			PreparedStatement ps = con.prepareStatement(sql, java.sql.Statement.RETURN_GENERATED_KEYS); //Para que sql le diga el ID

			// rellenamos los values
			ps.setInt(1, idEntrenador);
			//Para el mote si es crianza (mezcla padres) y salvaje (su especie)
			if (p.getMotePokemon() != null && !p.getMotePokemon().isEmpty()) {
				ps.setString(2, p.getMotePokemon());
			} else {
				ps.setString(2, p.getNombrePokemon());
			}

			ps.setInt(3, p.getVitalidadMaxima());
			ps.setInt(4, p.getAtaque());
			ps.setInt(5, p.getDefensa());
			ps.setInt(6, p.getVelocidad());
			ps.setInt(7, p.getAtaqueEspecial());
			ps.setInt(8, p.getDefensaEspecial());
			ps.setInt(9, p.getNivel());
			ps.setInt(10, p.getExperiencia());
			ps.setInt(11, p.getFertilidad());
			//Si es nulo se le da estado SANO por defecto
			if (p.getEstadoActual() != null) {
				ps.setString(12, p.getEstadoActual().name());
			} else {
				ps.setString(12, "SANO");
			}
			ps.setString(13, ubicacion);

			ps.setInt(14, p.getIdPokemon());

			ps.executeUpdate();

			System.out.println("¡Pokemon "+p.getNombrePokemon()+ " actualizado! Su ID real es: " + p.getIdPokemon());

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// metodo apra poner un movimiento inicial a ccada poikemon capturado
	private void asignarMovimientoInicial(Connection con, Pokemon p) {
		// mediante el DAO buscamos en la bd
	    MovimientoDAO movDAO = new MovimientoDAO(con);
	    
	    // obtenemos solo movimientos de su tipo que tengan POTENCIA > 0, para que pueda hacer daño
	    List<Movimiento> compatibles = movDAO.listarPorTipoYOfensivo(p.getTipoPrincipal());

	    if (!compatibles.isEmpty()) {
	        // elegimos uno al azar de la lista de movimientos que hacen daño
	        Random r = new Random();
	        Movimiento inicial = compatibles.get(r.nextInt(compatibles.size()));

	        // insertamos la relacion en la tabla SET_MOVIMIENTOS
	        String sql = "INSERT INTO SET_MOVIMIENTOS (ID_POKEMON, ID_MOVIMIENTO, ES_ACTIVO, PP) VALUES (?, ?, 1, 20)";

	        try (PreparedStatement ps = con.prepareStatement(sql)) {
	        	// ID del Pokemon recien creado
	            ps.setInt(1, p.getIdPokemon()); 
	            // ID del movimiento elegido
	            ps.setInt(2, inicial.getIdMovimiento()); 
	            ps.executeUpdate();
	            
	            // lo guardamos en el objeto Pokemon para que el controlador lo use
	            Movimiento[] iniciales = new model.Movimiento[4];
	            iniciales[0] = inicial;
	            p.setMovimientos(iniciales);
	            
	            System.out.println("LOG: Asignado movimiento ofensivo " + inicial.getNombreMovimiento() + " a " + p.getNombrePokemon());
	        } catch (java.sql.SQLException e) {
	            System.out.println("Error al asignar movimiento inicial: " + e.getMessage());
	        }
	    }
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
