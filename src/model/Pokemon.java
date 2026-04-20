package model;


import javafx.scene.paint.Color;

import java.util.*;


/**
 * Class Pokemon
 */
public class Pokemon{

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
  private String motePokemon;  /**

   * vitalidad del Pokemon
   *    */

  private int vitalidad;  
  
  // vida maxima del pokemon
  private int vitalidadMaxima;
  
  /**

   * ataque del Pokemon
   *    */

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
  private int velocidad;  /**

   * nivel del Pokemon
   *    */

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
   *    */

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
   * la imagen del spray del Pokemon por detras
   */
  private String imgPosteriorPokemon;
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
  public Pokemon () { };
  
  public void setIdPokemon (int newVar) {
    idPokemon = newVar;
  }

  public int getIdPokemon () {
    return idPokemon;
  }

  public void setNumPokedex (int newVar) {
    numPokedex = newVar;
  }

  public int getNumPokedex () {
    return numPokedex;
  }

  public void setNombrePokemon (String newVar) {
    nombrePokemon = newVar;
  }

  public String getNombrePokemon () {
    return nombrePokemon;
  }

  public void setMotePokemon (String newVar) {
    motePokemon = newVar;
  }

  public String getMotePokemon () {
    return motePokemon;
  }

  public void setVitalidad (int newVar) {
	// Si intentamos curar más del máximo, se queda en el máximo
	    if (newVar > this.vitalidadMaxima) {
	        this.vitalidad = this.vitalidadMaxima;
	    } else {
	        this.vitalidad = newVar;
	    }
  }

  public int getVitalidad () {
    return vitalidad;
  }

  public void setAtaque (int newVar) {
    ataque = newVar;
  }

  public int getAtaque () {
    return ataque;
  }

  public void setDefensa (int newVar) {
    defensa = newVar;
  }

  public int getDefensa () {
    return defensa;
  }

  public void setAtaqueEspecial (int newVar) {
    ataqueEspecial = newVar;
  }

  public int getAtaqueEspecial () {
    return ataqueEspecial;
  }

  public void setDefensaEspecial (int newVar) {
    defensaEspecial = newVar;
  }

  public int getDefensaEspecial () {
    return defensaEspecial;
  }

  public void setVelocidad (int newVar) {
    velocidad = newVar;
  }

  public int getVelocidad () {
    return velocidad;
  }

  public void setNivel (int newVar) {
    nivel = newVar;
  }

  public int getNivel () {
    return nivel;
  }

  public void setExperiencia (int newVar) {
    experiencia = newVar;
  }

  public int getExperiencia () {
    return experiencia;
  }

  public void setMovimientos (Movimiento[] newVar) {
    movimientos = newVar;
  }

  public Movimiento[] getMovimientos () {
    return movimientos;
  }

  public void setFertilidad (int newVar) {
    fertilidad = newVar;
  }

  public int getFertilidad () {
    return fertilidad;
  }

  public void setSexo (Sexo newVar) {
    sexo = newVar;
  }

  public Sexo getSexo () {
    return sexo;
  }

  public void setTipoPrincipal (Tipos newVar) {
    tipoPrincipal = newVar;
  }

  public Tipos getTipoPrincipal () {
    return tipoPrincipal;
  }

  public void setTipoSecundario (Tipos newVar) {
    tipoSecundario = newVar;
  }

  public Tipos getTipoSecundario () {
    return tipoSecundario;
  }

  public void setEstadoActual (Estados newVar) {
    estadoActual = newVar;
  }

  public Estados getEstadoActual () {
    return estadoActual;
  }
  

  public void setTurnosEstadoRestantes(int t) { 
	  this.turnosEstadoRestantes = t; 
	  }
  
  public int getTurnosEstadoRestantes() {
	  return turnosEstadoRestantes; 
	  }

  public void setObjetoEquipado (Objeto newVar) {
    objetoEquipado = newVar;
  }

  public Objeto getObjetoEquipado () {
    return objetoEquipado;
  }

  public void setEsShiny (Boolean newVar) {
    esShiny = newVar;
  }

  public Boolean getEsShiny () {
    return esShiny;
  }

  public void setRatioCaptura (Rareza newVar) {
    ratioCaptura = newVar;
  }

  public Rareza getRatioCaptura () {
    return ratioCaptura;
  }

  public void setUbicacion (UbicacionPokemon newVar) {
    ubicacion = newVar;
  }

 public UbicacionPokemon getUbicacion () {
    return ubicacion;
  }

  public void setEstadoPermanente (Estados newVar) {
    estadoPermanente = newVar;
  }

  public Estados getEstadoPermanente () {
    return estadoPermanente;
  }

  public void setOrigen (String newVar) {
    origen = newVar;
  }

  public String getOrigen () {
    return origen;
  }

  public void setImgFrontalPokemon (String newVar) {
    imgFrontalPokemon = newVar;
  }

  public String getImgFrontalPokemon () {
    return imgFrontalPokemon;
  }

  public void setImgPosteriorPokemon (String newVar) {
    imgPosteriorPokemon = newVar;
  }

  public String getImgPosteriorPokemon () {
    return imgPosteriorPokemon;
  }
  
  public void setNivelEvolucion (int newVar) {
	  nivelEvolucion = newVar;
	  }

	  public int getNivelEvolucion () {
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
   * Metodo para subir nivel
   */
  public void subirNivel()
  {
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
  }


  /**
   * metodo atacar del Pokemon
   * con salida (CONSTANTES):
   * NEUTRO  =
   * VENTAJA X2
   * DOBLEVENTAJA x4
   * DESVENTAJA 1/2
   * DOBLEDESVENTAJA 1/4
   * 
   * y comprueba si el tipo de ataque es del mismo tipo que el pokemon que lo realiza
   * x1.5
   * @param        movimiento
   * @param        pokemon
   */
  public void atacar(Movimiento movimiento, Pokemon pokemon)
  {
  }


  /**
   * metodo para recuperar un procentajde de vida aleatorio en 10% un maximo de 50%
   */
  public void descansar()
  {
  }


  /**
   * @param        movimiento recibe un array de movimientos y puede aprender un
   * ataque cada 3 niveles
   * 
   */
  public void aprenderMovimiento(Movimiento[] movimiento)
  {
  }

  // Metodo para cambiar de color


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



}
