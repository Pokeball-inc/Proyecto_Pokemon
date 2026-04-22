package model;

import javafx.scene.paint.Color;

import java.util.*;

/**
 * @author Elyass Douma Zouhairi
 * @author Pablo Serrano Conesa
 * @author Isaías Villarreal Méndez
 * Class Pokemon
 */
public class Pokemon {

	//
	// Fields
	//

	/**
	 * id del Pokemon
	 */
	private int idPokemon = 1;
	/**
	 * el numero de la pokedex de cada Pokemon
	 */
	private int numPokedex;
	/**
	 * nombre del Pokemon
	 */
	private String nombrePokemon;
	/**
	 * mote del Pokemon
	 */
	private String motePokemon;
	/**
	 * 
	 * vitalidad del Pokemon
	 */

	private int vitalidad;

	// vida maxima del pokemon
	private int vitalidadMaxima;

	/**
	 * 
	 * ataque del Pokemon
	 */

	private int ataque;
	/**
	 * defensa del Pokemon
	 */
	private int defensa;
	/**
	 * ataque especial del Pokemon
	 */
	private int ataqueEspecial;
	/**
	 * defensa especial del Pokemon
	 */
	private int defensaEspecial;
	/**
	 * velocidad del Pokemon
	 */
	private int velocidad;
	/**
	 * 
	 * nivel del Pokemon
	 */

	private int nivel = 1;
	/**
	 * experiencia del Pokemon
	 */
	private int experiencia = 0;
	/**
	 * conjunto de los 4 movimientos del Pokemon
	 */
	private Movimiento[] movimientos = new Movimiento[4];
	/**
	 * fertilidad del Pokemon
	 */
	private int fertilidad = 5;

	/**
	 * sexo del Pokemon
	 */

	private Sexo sexo;
	/**
	 * tipo del Pokemon
	 */
	private Tipos tipoPrincipal;
	private Tipos tipoSecundario;
	/**
	 * estado del Pokemon
	 */
	private Estados estadoActual = Estados.SANO;

	// turnos que quedan para que acabe el estado
	private int turnosEstadoRestantes = 0;

	/**
	 * objeto que puede o no llevar el Pokemon
	 */
	private Objeto objetoEquipado;
	/**
	 * indica si es o no shiny el Pokemon
	 */
	private Boolean esShiny = false;
	/**
	 * Ratio de captura de pokemon
	 */
	private Rareza ratioCaptura;
	/**
	 * ubicacion de captura del Pokemon
	 */
	private UbicacionPokemon ubicacion;
	/**
	 * atributo para los estados permanentes
	 */
	private Estados estadoPermanente = Estados.SANO;
	/**
	 * indica el origen del Pokemon, si es crianza o captura
	 */
	private String origen;
	/**
	 * el spray de la imagen frontal del pokemon
	 */
	private String imgFrontalPokemon;
	/**
	 * el spray de la imagen frontal del pokemon en 3D
	 */
	private String imgFrontalPokemon3D;
	/**
	 * la imagen del spray del Pokemon por detras
	 */
	private String imgPosteriorPokemon;
	/**
	 * la imagen del spray del Pokemon por detras en 3D
	 */
	private String imgPosteriorPokemon3D;
	/**
	 * El nivel de que necesita el pokemon para evolucionar
	 */
	private int nivelEvolucion;

	/**
	 * El color del pokemon en funcion de su tipo
	 */
	private Color color;

	//
	// Constructors
	//
	public Pokemon() {
	};

	public void setIdPokemon(int newVar) {
		idPokemon = newVar;
	}

	public int getIdPokemon() {
		return idPokemon;
	}

	public void setNumPokedex(int newVar) {
		numPokedex = newVar;
	}

	public int getNumPokedex() {
		return numPokedex;
	}

	public void setNombrePokemon(String newVar) {
		nombrePokemon = newVar;
	}

	public String getNombrePokemon() {
		return nombrePokemon;
	}

	public void setMotePokemon(String newVar) {
		motePokemon = newVar;
	}

	public String getMotePokemon() {
		return motePokemon;
	}

	/**
	 * cambiado el setVitalidad para que no pueda establecerse mas que la vitalidad maxima en caso de curarse
	 * 
	 * */
	public void setVitalidad(int newVar) {
		// Si intentamos curar más del máximo, se queda en el máximo
		if (newVar > this.vitalidadMaxima) {
			this.vitalidad = this.vitalidadMaxima;
		} else {
			this.vitalidad = newVar;
		}
	}

	public int getVitalidad() {
		return vitalidad;
	}

	public void setAtaque(int newVar) {
		ataque = newVar;
	}

	public int getAtaque() {
		// Quemado: su ataque se reduce a la mitad
	    if (this.estadoActual == Estados.QUEMADO) {
	        return this.ataque / 2;
	    }
		return ataque;
	}

	public void setDefensa(int newVar) {
		defensa = newVar;
	}

	public int getDefensa() {
		return defensa;
	}

	public void setAtaqueEspecial(int newVar) {
		ataqueEspecial = newVar;
	}

	public int getAtaqueEspecial() {
		// Helado: su ataque especial se reduce a la mitad
	    if (this.estadoActual == Estados.HELADO) {
	        return this.ataqueEspecial / 2;
	    }
		return ataqueEspecial;
	}

	public void setDefensaEspecial(int newVar) {
		defensaEspecial = newVar;
	}

	public int getDefensaEspecial() {
		return defensaEspecial;
	}

	public void setVelocidad(int newVar) {
		
		velocidad = newVar;
	}

	public int getVelocidad() {
		// Paralizado: su velocidad se reduce a la mitad
	    if (this.estadoActual == Estados.PARALIZADO) {
	        return this.velocidad / 2;
	    }
		return velocidad;
	}

	public void setNivel(int newVar) {
		nivel = newVar;
	}

	public int getNivel() {
		return nivel;
	}

	public void setExperiencia(int newVar) {
		experiencia = newVar;
	}

	public int getExperiencia() {
		return experiencia;
	}

	public void setMovimientos(Movimiento[] newVar) {
		movimientos = newVar;
	}

	public Movimiento[] getMovimientos() {
		return movimientos;
	}

	public void setFertilidad(int newVar) {
		fertilidad = newVar;
	}

	public int getFertilidad() {
		return fertilidad;
	}

	public void setSexo(Sexo newVar) {
		sexo = newVar;
	}

	public Sexo getSexo() {
		return sexo;
	}

	public void setTipoPrincipal(Tipos newVar) {
		tipoPrincipal = newVar;
	}

	public Tipos getTipoPrincipal() {
		return tipoPrincipal;
	}

	public void setTipoSecundario(Tipos newVar) {
		tipoSecundario = newVar;
	}

	public Tipos getTipoSecundario() {
		return tipoSecundario;
	}

	public void setEstadoActual(Estados newVar) {
		estadoActual = newVar;
	}

	public Estados getEstadoActual() {
		return estadoActual;
	}

	public void setTurnosEstadoRestantes(int t) {
		this.turnosEstadoRestantes = t;
	}

	public int getTurnosEstadoRestantes() {
		return turnosEstadoRestantes;
	}

	public void setObjetoEquipado(Objeto newVar) {
		objetoEquipado = newVar;
	}

	public Objeto getObjetoEquipado() {
		return objetoEquipado;
	}

	public void setEsShiny(Boolean newVar) {
		esShiny = newVar;
	}

	public Boolean getEsShiny() {
		return esShiny;
	}

	public void setRatioCaptura(Rareza newVar) {
		ratioCaptura = newVar;
	}

	public Rareza getRatioCaptura() {
		return ratioCaptura;
	}

	public void setUbicacion(UbicacionPokemon newVar) {
		ubicacion = newVar;
	}

	public UbicacionPokemon getUbicacion() {
		return ubicacion;
	}

	public void setEstadoPermanente(Estados newVar) {
		estadoPermanente = newVar;
	}

	public Estados getEstadoPermanente() {
		return estadoPermanente;
	}

	public void setOrigen(String newVar) {
		origen = newVar;
	}

	public String getOrigen() {
		return origen;
	}

	public void setImgFrontalPokemon(String newVar) {
		imgFrontalPokemon = newVar;
	}

	public String getImgFrontalPokemon() {
		return imgFrontalPokemon;
	}

	public void setImgFrontalPokemon3D(String newVar) {
		imgFrontalPokemon3D = newVar;
	}

	public String getImgFrontalPokemon3D() {
		return imgFrontalPokemon3D;
	}

	public void setImgPosteriorPokemon(String newVar) {
		imgPosteriorPokemon = newVar;
	}

	public String getImgPosteriorPokemon() {
		return imgPosteriorPokemon;
	}

	public void setImgPosteriorPokemon3D(String newVar) {
		imgPosteriorPokemon3D = newVar;
	}

	public String getImgPosteriorPokemon3D() {
		return imgPosteriorPokemon3D;
	}

	public void setNivelEvolucion(int newVar) {
		nivelEvolucion = newVar;
	}

	public int getNivelEvolucion() {
		return nivelEvolucion;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public int getVitalidadMaxima() {
		return vitalidadMaxima;
	}

	public void setVitalidadMaxima(int vitalidadMaxima) {
		this.vitalidadMaxima = vitalidadMaxima;
	}

	//
	// Other methods
	//
	
	/**
	 * metodo para ganar experiencia despues de cada combate
	 * va acumuladno la sobrante y establece el nuevo limite de experiencia necesaria para el siguiente nivel
	 * @param recibe un nit con la cantidad de experiencia que gana
	 */
	public void ganarExperiencia(int puntos) {
	    // sumamos la experiencia nueva a la que ya teniamos
	    this.setExperiencia(this.getExperiencia() + puntos);

	    // calculamos cuanto necesita para subir (10 * nivel actual)
	    int expNecesaria = 10 * this.getNivel();

	    // comprobamos si ha llegado al limite para subir de nivel
	    // usamos un while por si gana tanta experiencia que sube varios niveles de golpe
	    while (this.getExperiencia() >= expNecesaria) {
	        
	        // restamos la experiencia consumida para subir este nivel
	        this.setExperiencia(this.getExperiencia() - expNecesaria);
	        
	        // llamamos al metodo que ya teniamos para aumentar los stats y el nivel
	        this.subirNivel();
	        
	        // actualizamos el nuevo limite de experiencia
	        expNecesaria = 10 * this.getNivel();
	        
	        System.out.println("¡Subida de nivel! Ahora eres nivel: " + this.getNivel());
	    }
	}

	/**
	 * Metodo para subir nivel, sube de nivel al pokemon y genera la subida de stats entre 1 y 5 por nivel
	 */
	public void subirNivel() {
		this.setNivel(this.getNivel() + 1);
		Random r = new Random();

		// calculamos lo que aumenta cada stat
		int aumentoVit = r.nextInt(5) + 1;

		// actualizamos la Vitalidad Maxima
		this.setVitalidadMaxima(this.getVitalidadMaxima() + aumentoVit);

		// al subir de nivel el pokemon se cura
		this.setVitalidad(this.getVitalidadMaxima());

		// actualizamos el resto de stats
		this.setAtaque(this.getAtaque() + r.nextInt(5) + 1);
		this.setDefensa(this.getDefensa() + r.nextInt(5) + 1);
		this.setAtaqueEspecial(this.getAtaqueEspecial() + r.nextInt(5) + 1);
		this.setDefensaEspecial(this.getDefensaEspecial() + r.nextInt(5) + 1);
		this.setVelocidad(this.getVelocidad() + r.nextInt(5) + 1);

		// metodo para que aprenda ataques cada 3 niveles,
		// TODO controlador para buscar lkos movimientos
		if (this.nivel % 3 == 0) {
			System.out.println("¡" + this.nombrePokemon + " puede aprender un nuevo movimiento!");
		}
	}

	/**
	 * metodo atacar del Pokemon con salida (CONSTANTES): NEUTRO = VENTAJA X2
	 * DOBLEVENTAJA x4 DESVENTAJA 1/2 DOBLEDESVENTAJA 1/4
	 * 
	 * y comprueba si el tipo de ataque es del mismo tipo que el pokemon que lo
	 * realiza x1.5
	 * 
	 * @param movimiento recibe el movimiento de pokemon que va a realizar
	 * @param pokemon recibe el pokemon que ataca y el que recibe el ataque
	 */
	public void atacar(Movimiento movimiento, Pokemon pokemonAtacante, Pokemon pokemonDefensor) {
	}

	/**
	 * metodo para recuperar un procentajde de vida aleatorio en 10% un maximo de
	 * 50%
	 */
	public void descansar() {
		Random r = new Random();
		// generamos un porcentaje aleatorio entre 10 y 50
		int porcentaje = r.nextInt(41) + 10;

		// calculamos la curacion basada en la Vitalidad Maxima
		int curacion = (this.vitalidadMaxima * porcentaje) / 100;

		System.out.println("INFO: " + this.nombrePokemon + " descansa y recupera un " + porcentaje + "% de vida.");

		// aplicamos la curacion usando el setter para no sobrepasar el maximo
		this.setVitalidad(this.vitalidad + curacion);
	}

	/**
	 * metodo para que el pokemon pueda aprender un movimiento cada 3 niveles
	 * @param movimiento recibe un array de movimientos y puede aprender un ataque de entre todos los del array
	 *                   
	 * 
	 */
	public void aprenderMovimiento(Movimiento nuevoMovimiento) {
		// comprobamos si hay hueco en el array de 4 movimientos y si hay lo añadimos
		boolean aprendido = false;
		for (int i = 0; i < movimientos.length; i++) {
			if (movimientos[i] == null) {
				movimientos[i] = nuevoMovimiento;
				aprendido = true;
				System.out.println("INFO: " + nombrePokemon + " ha aprendido " + nuevoMovimiento.getNombreMovimiento());
				break;
			}
		}

		if (!aprendido) {
			// si queremos cambiarlo por alguno de los que tenemos
			System.out.println("INFO: El Pokémon ya conoce 4 movimientos. Debes olvidar uno.");
		}
	}

	  /**
	   * metodo para limpiar todos los estados temporales que no siguen fuera del combate
	   */
	  public void limpiarEstadosTemporales() {
	      if (this.estadoActual == Estados.CONFUSO || 
	          this.estadoActual == Estados.AMEDENTRADO || 
	          this.estadoActual == Estados.ENAMORADO ||
	          this.estadoActual == Estados.APRESADO ||
	          this.estadoActual == Estados.MALDITO ||
	          this.estadoActual == Estados.DRENADORAS ||
	          this.estadoActual == Estados.CANTOMORTAL ||
	          this.estadoActual == Estados.CENTROATENCION ||
	          this.estadoActual == Estados.SOMNOLIENTO) {
	          
	          this.setEstadoActual(Estados.SANO);
	          this.setTurnosEstadoRestantes(0); // reseteo contadotr 
	      }
	  }
	
/**
 * Metodo para cambiar el color de fonde respecto al tipo del pokemon que aparece
 * */
	public void cambiarColor() {
		String tipo1 = getTipoPrincipal().toString();
		if (tipo1.equals("ACERO")) {
			setColor(Color.SILVER);
		} else if (tipo1.equals("AGUA")) {
			setColor(Color.BLUE);
		} else if (tipo1.equals("BICHO")) {
			setColor(Color.YELLOWGREEN);
		} else if (tipo1.equals("DRAGÓN")) {
			setColor(Color.INDIGO);
		} else if (tipo1.equals("ELÉCTRICO")) {
			setColor(Color.YELLOW);
		} else if (tipo1.equals("FANTASMA")) {
			setColor(Color.PURPLE);
		} else if (tipo1.equals("FUEGO")) {
			setColor(Color.RED);
		} else if (tipo1.equals("HADA")) {
			setColor(Color.PINK);
		} else if (tipo1.equals("HIELO")) {
			setColor(Color.CYAN);
		} else if (tipo1.equals("LUCHA")) {
			setColor(Color.BROWN);
		} else if (tipo1.equals("NORMAL")) {
			setColor(Color.LIGHTGRAY);
		} else if (tipo1.equals("PLANTA")) {
			setColor(Color.GREEN);
		} else if (tipo1.equals("PSÍQUICO")) {
			setColor(Color.MAGENTA);
		} else if (tipo1.equals("ROCA")) {
			setColor(Color.TAN);
		} else if (tipo1.equals("SINIESTRO")) {
			setColor(Color.DARKSLATEGRAY);
		} else if (tipo1.equals("TIERRA")) {
			setColor(Color.CHOCOLATE);
		} else if (tipo1.equals("VENENO")) {
			setColor(Color.VIOLET);
		} else if (tipo1.equals("VOLADOR")) {
			setColor(Color.SKYBLUE);
		} else {
			setColor(Color.BLACK);
		}
	}
	
	/**
	 * Metodo que genera el sexo correcto para un Pokemon segun su numero de Pokedex.
	 * Tiene en cuenta las especies que son 100% machos, 100% hembras o sin genero.
	 * @param numPokedex El numero de la Pokedex del Pokemon a generar
	 * @return El Sexo correspondiente (MACHO, HEMBRA o NEUTRO)
	 */
	public static Sexo generarSexoPokemon(int numPokedex) {
		
		// IDs de especies que SOLO pueden ser HEMBRAS
		// (Nidoran F, Nidorina, Nidoqueen, Chansey, Kangaskhan, Jynx, Smoochum, Miltank, Blissey)
		int[] soloHembras = {29, 30, 31, 113, 115, 124, 238, 241, 242};

		// IDs de especies que SOLO pueden ser MACHOS
		// (Nidoran M, Nidorino, Nidoking, Hitmonlee, Hitmonchan, Tauros, Tyrogue, Hitmontop)
		int[] soloMachos = {32, 33, 34, 106, 107, 128, 236, 237};

		// IDs de especies SIN GENERO (NEUTRO)
		// (Magnemite, Magneton, Voltorb, Electrode, Staryu, Starmie, Ditto, Porygon, Porygon2, Unown y Legendarios)
		int[] neutros = {81, 82, 100, 101, 120, 121, 132, 137, 233, 201, 144, 145, 146, 150, 151, 243, 244, 245, 249, 250, 251};

		// comprobamos si el pokemon pertenece a la lista de Hembras
		for (int id : soloHembras) {
			if (numPokedex == id) {
				return Sexo.HEMBRA;
			}
		}

		// comprobamos si el pokemon pertenece a la lista de Machos
		for (int id : soloMachos) {
			if (numPokedex == id) {
				return Sexo.MACHO;
			}
		}

		// comprobamos si el pokemon pertenece a la lista de Neutros
		for (int id : neutros) {
			if (numPokedex == id) {
				return Sexo.NEUTRO;
			}
		}

		// Si no esta en ninguna lista especial, generamos Macho o Hembra al 50%
		java.util.Random r = new java.util.Random();
		if (r.nextBoolean()) {
			return Sexo.MACHO;
		} else {
			return Sexo.HEMBRA;
		}
	}

}
