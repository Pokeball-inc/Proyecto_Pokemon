package model;


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

  private int vitalidad;  /**

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
  private int fertilidad = 5;  /**

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
  private Estados estadoActual = Estados.NORMAL;
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
  private RatioCaptura ratioCaptura;
  /**
   * ubicacion de captura del Pokemon
   */
  private UbicacionPokemon ubicacion;
  /**
   * atributo para los estados permanentes
   */
  private Estados estadoPermanente = Estados.NORMAL;
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
  
  //
  // Constructors
  //
  public Pokemon () { };
  
  //
  // Methods
  //


  //
  // Accessor methods
  //

  /**
   * Set the value of idPokemon
   * id del Pokemon
   * @param newVar the new value of idPokemon
   */
  public void setIdPokemon (int newVar) {
    idPokemon = newVar;
  }

  /**
   * Get the value of idPokemon
   * id del Pokemon
   * @return the value of idPokemon
   */
  public int getIdPokemon () {
    return idPokemon;
  }

  /**
   * Set the value of numpokedex
   * el numero de la pokedex de cada Pokemon
   * @param newVar the new value of numpokedex
   */
  public void setNumPokedex (int newVar) {
    numPokedex = newVar;
  }

  /**
   * Get the value of numpokedex
   * el numero de la pokedex de cada Pokemon
   * @return the value of numpokedex
   */
  public int getNumPokedex () {
    return numPokedex;
  }

  /**
   * Set the value of nombrePokemon
   * nombre del Pokemon
   * @param newVar the new value of nombrePokemon
   */
  public void setNombrePokemon (String newVar) {
    nombrePokemon = newVar;
  }

  /**
   * Get the value of nombrePokemon
   * nombre del Pokemon
   * @return the value of nombrePokemon
   */
  public String getNombrePokemon () {
    return nombrePokemon;
  }

  /**
   * Set the value of motePokemon
   * mote del Pokemon
   * @param newVar the new value of motePokemon
   */
  public void setMotePokemon (String newVar) {
    motePokemon = newVar;
  }

  /**
   * Get the value of motePokemon
   * mote del Pokemon
   * @return the value of motePokemon
   */
  public String getMotePokemon () {
    return motePokemon;
  }

  /**
   * Set the value of vitalidad
   * vitalidad del Pokemon
   * 
   * @param newVar the new value of vitalidad
   */
  public void setVitalidad (int newVar) {
    vitalidad = newVar;
  }

  /**
   * Get the value of vitalidad
   * vitalidad del Pokemon
   * 
   * @return the value of vitalidad
   */
  public int getVitalidad () {
    return vitalidad;
  }

  /**
   * Set the value of ataque
   * ataque del Pokemon
   * 
   * @param newVar the new value of ataque
   */
  public void setAtaque (int newVar) {
    ataque = newVar;
  }

  /**
   * Get the value of ataque
   * ataque del Pokemon
   * 
   * @return the value of ataque
   */
  public int getAtaque () {
    return ataque;
  }

  /**
   * Set the value of defensa
   * defensa del Pokemon
   * @param newVar the new value of defensa
   */
  public void setDefensa (int newVar) {
    defensa = newVar;
  }

  /**
   * Get the value of defensa
   * defensa del Pokemon
   * @return the value of defensa
   */
  public int getDefensa () {
    return defensa;
  }

  /**
   * Set the value of ataqueEspecial
   * ataque especial del Pokemon
   * @param newVar the new value of ataqueEspecial
   */
  public void setAtaqueEspecial (int newVar) {
    ataqueEspecial = newVar;
  }

  /**
   * Get the value of ataqueEspecial
   * ataque especial del Pokemon
   * @return the value of ataqueEspecial
   */
  public int getAtaqueEspecial () {
    return ataqueEspecial;
  }

  /**
   * Set the value of defensaEspecial
   * defensa especial del Pokemon
   * @param newVar the new value of defensaEspecial
   */
  public void setDefensaEspecial (int newVar) {
    defensaEspecial = newVar;
  }

  /**
   * Get the value of defensaEspecial
   * defensa especial del Pokemon
   * @return the value of defensaEspecial
   */
  public int getDefensaEspecial () {
    return defensaEspecial;
  }

  /**
   * Set the value of velocidad
   * velocidad del Pokemon
   * @param newVar the new value of velocidad
   */
  public void setVelocidad (int newVar) {
    velocidad = newVar;
  }

  /**
   * Get the value of velocidad
   * velocidad del Pokemon
   * @return the value of velocidad
   */
  public int getVelocidad () {
    return velocidad;
  }

  /**
   * Set the value of nivel
   * nivel del Pokemon
   * 
   * @param newVar the new value of nivel
   */
  public void setNivel (int newVar) {
    nivel = newVar;
  }

  /**
   * Get the value of nivel
   * nivel del Pokemon
   * 
   * @return the value of nivel
   */
  public int getNivel () {
    return nivel;
  }

  /**
   * Set the value of experiencia
   * experiencia del Pokemon
   * @param newVar the new value of experiencia
   */
  public void setExperiencia (int newVar) {
    experiencia = newVar;
  }

  /**
   * Get the value of experiencia
   * experiencia del Pokemon
   * @return the value of experiencia
   */
  public int getExperiencia () {
    return experiencia;
  }

  /**
   * Set the value of movimientos
   * conjunto de los 4 movimientos del Pokemon
   * @param newVar the new value of movimientos
   */
  public void setMovimientos (Movimiento[] newVar) {
    movimientos = newVar;
  }

  /**
   * Get the value of movimientos
   * conjunto de los 4 movimientos del Pokemon
   * @return the value of movimientos
   */
  public Movimiento[] getMovimientos () {
    return movimientos;
  }

  /**
   * Set the value of fertilidad
   * fertilidad del Pokemon
   * @param newVar the new value of fertilidad
   */
  public void setFertilidad (int newVar) {
    fertilidad = newVar;
  }

  /**
   * Get the value of fertilidad
   * fertilidad del Pokemon
   * @return the value of fertilidad
   */
  public int getFertilidad () {
    return fertilidad;
  }

  /**
   * Set the value of sexo
   * sexo del Pokemon
   * 
   * @param newVar the new value of sexo
   */
  public void setSexo (Sexo newVar) {
    sexo = newVar;
  }

  /**
   * Get the value of sexo
   * sexo del Pokemon
   * 
   * @return the value of sexo
   */
  public Sexo getSexo () {
    return sexo;
  }

  /**
   * Set the value of tipoPrincipal
   * tipo del Pokemon
   * @param newVar the new value of tipoPrincipal
   */
  public void setTipoPrincipal (Tipos newVar) {
    tipoPrincipal = newVar;
  }

  /**
   * Get the value of tipoPrincipal
   * tipo del Pokemon
   * @return the value of tipoPrincipal
   */
  public Tipos getTipoPrincipal () {
    return tipoPrincipal;
  }

  /**
   * Set the value of tipoSecundario
   * @param newVar the new value of tipoSecundario
   */
  public void setTipoSecundario (Tipos newVar) {
    tipoSecundario = newVar;
  }

  /**
   * Get the value of tipoSecundario
   * @return the value of tipoSecundario
   */
  public Tipos getTipoSecundario () {
    return tipoSecundario;
  }

  /**
   * Set the value of estado
   * estado del Pokemon
   * @param newVar the new value of estado
   */
  public void setEstado (Estados newVar) {
    estadoActual = newVar;
  }

  /**
   * Get the value of estado
   * estado del Pokemon
   * @return the value of estado
   */
  public Estados getEstado () {
    return estadoActual;
  }

  /**
   * Set the value of objetoEquipado
   * objeto que puede o no llevar el Pokemon
   * @param newVar the new value of objetoEquipado
   */
  public void setObjetoEquipado (Objeto newVar) {
    objetoEquipado = newVar;
  }

  /**
   * Get the value of objetoEquipado
   * objeto que puede o no llevar el Pokemon
   * @return the value of objetoEquipado
   */
  public Objeto getObjetoEquipado () {
    return objetoEquipado;
  }

  /**
   * Set the value of esShiny
   * indica si es o no shiny el Pokemon
   * @param newVar the new value of esShiny
   */
  public void setEsShiny (Boolean newVar) {
    esShiny = newVar;
  }

  /**
   * Get the value of esShiny
   * indica si es o no shiny el Pokemon
   * @return the value of esShiny
   */
  public Boolean getEsShiny () {
    return esShiny;
  }

  /**
   * Set the value of ratioCaptura
   * Ratio de captura de pokemon
   * @param newVar the new value of ratioCaptura
   */
  public void setRatioCaptura (RatioCaptura newVar) {
    ratioCaptura = newVar;
  }

  /**
   * Get the value of ratioCaptura
   * Ratio de captura de pokemon
   * @return the value of ratioCaptura
   */
  public RatioCaptura geRratioCaptura () {
    return ratioCaptura;
  }

  /**
   * Set the value of ubicacion
   * ubicacion de captura del Pokemon
   * @param newVar the new value of ubicacion
   */
 
  public void setUbicacion (UbicacionPokemon newVar) {
    ubicacion = newVar;
  }
  
  /**
   * Get the value of ubicacion
   * ubicacion de captura del Pokemon
   * @return the value of ubicacion
   */
 public UbicacionPokemon getUbicacion () {
    return ubicacion;
  }

  /**
   * Set the value of estadoPermanente
   * atributo para los estados permanentes
   * @param newVar the new value of estadoPermanente
   */
  public void setEstadoPermanente (Estados newVar) {
    estadoPermanente = newVar;
  }

  /**
   * Get the value of estadoPermanente
   * atributo para los estados permanentes
   * @return the value of estadoPermanente
   */
  public Estados getEstadoPermanente () {
    return estadoPermanente;
  }

  /**
   * Set the value of origen
   * indica el origen del Pokemon, si es crianza o captura
   * @param newVar the new value of origen
   */
  public void setOrigen (String newVar) {
    origen = newVar;
  }

  /**
   * Get the value of origen
   * indica el origen del Pokemon, si es crianza o captura
   * @return the value of origen
   */
  public String getOrigen () {
    return origen;
  }

  /**
   * Set the value of imgFrontalPokemon
   * el spray de la imagen frontal del pokemon
   * @param newVar the new value of imgFrontalPokemon
   */
  public void setImgFrontalPokemon (String newVar) {
    imgFrontalPokemon = newVar;
  }

  /**
   * Get the value of imgFrontalPokemon
   * el spray de la imagen frontal del pokemon
   * @return the value of imgFrontalPokemon
   */
  public String getImgFrontalPokemon () {
    return imgFrontalPokemon;
  }

  /**
   * Set the value of imgPosteriorPokemon
   * la imagen del spray del Pokemon por detras
   * @param newVar the new value of imgPosteriorPokemon
   */
  public void setImgPosteriorPokemon (String newVar) {
    imgPosteriorPokemon = newVar;
  }

  /**
   * Get the value of imgPosteriorPokemon
   * la imagen del spray del Pokemon por detras
   * @return the value of imgPosteriorPokemon
   */
  public String getImgPosteriorPokemon () {
    return imgPosteriorPokemon;
  }

  //
  // Other methods
  //

  /**
   * Metodo para subir nivel
   */
  public void subirNivel()
  {
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


}
