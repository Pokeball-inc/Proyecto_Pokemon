

/**
 * Class Combate
 */
public class Combate {

  //
  // Fields
  //

  /**
   * el id de la clase combate
   */
  private int idCombate = 1;
  /**
   * entrenador en la clase combate ( el jugador)
   */
  private Entrenador entrenador;
  /**
   * entrenador rival en combate
   */
  private Entrenador entrenadorRival;
  /**
   * cada turno del combate
   */
  private Turno turno = 1;
  /**
   * pokemon derrotados el jugador
   */
  private int pokemonKOEntrenador = 0;
  /**
   * pokemon debilitados del rival
   */
  private int pokemonKORival = 0;
  
  //
  // Constructors
  //
  public Combate () { };
  
  //
  // Methods
  //


  //
  // Accessor methods
  //

  /**
   * Set the value of idCombate
   * el id de la clase combate
   * @param newVar the new value of idCombate
   */
  public void setIdCombate (int newVar) {
    idCombate = newVar;
  }

  /**
   * Get the value of idCombate
   * el id de la clase combate
   * @return the value of idCombate
   */
  public int getIdCombate () {
    return idCombate;
  }

  /**
   * Set the value of entrenador
   * entrenador en la clase combate ( el jugador)
   * @param newVar the new value of entrenador
   */
  public void setEntrenador (Entrenador newVar) {
    entrenador = newVar;
  }

  /**
   * Get the value of entrenador
   * entrenador en la clase combate ( el jugador)
   * @return the value of entrenador
   */
  public Entrenador getEntrenador () {
    return entrenador;
  }

  /**
   * Set the value of entrenadorRival
   * entrenador rival en combate
   * @param newVar the new value of entrenadorRival
   */
  public void setEntrenadorRival (Entrenador newVar) {
    entrenadorRival = newVar;
  }

  /**
   * Get the value of entrenadorRival
   * entrenador rival en combate
   * @return the value of entrenadorRival
   */
  public Entrenador getEntrenadorRival () {
    return entrenadorRival;
  }

  /**
   * Set the value of turno
   * cada turno del combate
   * @param newVar the new value of turno
   */
  public void setTurno (Turno newVar) {
    turno = newVar;
  }

  /**
   * Get the value of turno
   * cada turno del combate
   * @return the value of turno
   */
  public Turno getTurno () {
    return turno;
  }

  /**
   * Set the value of pokemonKOEntrenador
   * pokemon derrotados el jugador
   * @param newVar the new value of pokemonKOEntrenador
   */
  public void setPokemonKOEntrenador (int newVar) {
    pokemonKOEntrenador = newVar;
  }

  /**
   * Get the value of pokemonKOEntrenador
   * pokemon derrotados el jugador
   * @return the value of pokemonKOEntrenador
   */
  public int getPokemonKOEntrenador () {
    return pokemonKOEntrenador;
  }

  /**
   * Set the value of pokemonKORival
   * pokemon debilitados del rival
   * @param newVar the new value of pokemonKORival
   */
  public void setPokemonKORival (int newVar) {
    pokemonKORival = newVar;
  }

  /**
   * Get the value of pokemonKORival
   * pokemon debilitados del rival
   * @return the value of pokemonKORival
   */
  public int getPokemonKORival () {
    return pokemonKORival;
  }

  //
  // Other methods
  //

  /**
   * metodo para iniciar el combate
   * @param        entrenador
   * @param        entrenadorRival
   */
  public void empezarCombate(Entrenador entrenador, Entrenador entrenadorRival)
  {
  }


  /**
   * metodo para retirarse del combate
   */
  public void retirarse()
  {
  }


  /**
   * metodo para finalizar combate
   */
  public void finalizarCombate()
  {
  }


}
