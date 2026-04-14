package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Random;

import bd.ConexionBBDD;
import javafx.scene.paint.Color;
import model.Estados;
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

					cambiarColor(p);


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
	    p.setVitalidad(r.nextInt(50) + 50);       // 50-100
	    p.setAtaque(r.nextInt(20) + 10);          // 10-30
	    p.setDefensa(r.nextInt(20) + 10);	      // 10-30
	    p.setAtaqueEspecial(r.nextInt(20) + 10);  // 10-30
	    p.setDefensaEspecial(r.nextInt(20) + 10); // 10-30
	    p.setVelocidad(r.nextInt(20) + 10);		  // 10-30
	    // y nivel establecido a uno y experiencia a 0
	    p.setNivel(1);
	    p.setExperiencia(0);
	    p.setFertilidad(5); //fertilidad inicial a 5 
	    p.setEstado(Estados.SANO);
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

	public void cambiarColor(Pokemon p){
		String tipo1 = p.getTipoPrincipal().toString();
		if (tipo1.equals("ACERO")) {
			p.setColor(Color.SILVER);
		} else if (tipo1.equals("AGUA")) {
			p.setColor(Color.BLUE);
		} else if (tipo1.equals("BICHO")) {
			p.setColor(Color.YELLOWGREEN);
		} else if (tipo1.equals("DRAGÓN")) {
			p.setColor(Color.INDIGO);
		} else if (tipo1.equals("ELÉCTRICO")) {
			p.setColor(Color.YELLOW);
		} else if (tipo1.equals("FANTASMA")) {
			p.setColor(Color.PURPLE);
		} else if (tipo1.equals("FUEGO")) {
			p.setColor(Color.RED);
		} else if (tipo1.equals("HADA")) {
			p.setColor(Color.PINK);
		} else if (tipo1.equals("HIELO")) {
			p.setColor(Color.CYAN);
		} else if (tipo1.equals("LUCHA")) {
			p.setColor(Color.BROWN);
		} else if (tipo1.equals("NORMAL")) {
			p.setColor(Color.LIGHTGRAY);
		} else if (tipo1.equals("PLANTA")) {
			p.setColor(Color.GREEN);
		} else if (tipo1.equals("PSÍQUICO")) {
			p.setColor(Color.MAGENTA);
		} else if (tipo1.equals("ROCA")) {
			p.setColor(Color.TAN);
		} else if (tipo1.equals("SINIESTRO")) {
			p.setColor(Color.DARKSLATEGRAY);
		} else if (tipo1.equals("TIERRA")) {
			p.setColor(Color.CHOCOLATE);
		} else if (tipo1.equals("VENENO")) {
			p.setColor(Color.VIOLET);
		} else if (tipo1.equals("VOLADOR")) {
			p.setColor(Color.SKYBLUE);
		} else {
			p.setColor(Color.BLACK);
		}
	}
	
	
	// metodo para guardar el pokemon capturado en la base de datos
	public void guardarPokemon(Connection con, Pokemon p, int idEntrenador, String ubicacion) {
		
		// preparamos la sentencia sql de insercion
		// ponemos un "?" por cada columna para que java rellene los huecos luego
		String sql = "INSERT INTO POKEMON (NUM_POKEDEX, ID_ENTRENADOR, MOTE, SEXO, VITALIDAD, ATAQUE, DEFENSA, VELOCIDAD, ATAQUE_SP, DEFENSA_SP, NIVEL, EXPERIENCIA, FERTILIDAD, UBICACION) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

	    try {
	    	// preparamos la conexión para enviar esta consulta
	        PreparedStatement ps = con.prepareStatement(sql);
	        
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
	        
	        ps.setInt(5, p.getVitalidad());
	        ps.setInt(6, p.getAtaque());
	        ps.setInt(7, p.getDefensa());
	        ps.setInt(8, p.getVelocidad());
	        ps.setInt(9, p.getAtaqueEspecial());
	        ps.setInt(10, p.getDefensaEspecial());
	        ps.setInt(11, p.getNivel());
	        ps.setInt(12, p.getExperiencia());
	        ps.setInt(13, p.getFertilidad());
	        ps.setString(14, ubicacion);

	        
	        // usamos el executeupdate para modificar la base de datos
	        ps.executeUpdate();

	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
