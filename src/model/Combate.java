package model;



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
  private Turno turno;
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

  public void setEntrenador (Entrenador newVar) {
    entrenador = newVar;
  }

  public Entrenador getEntrenador () {
    return entrenador;
  }

  public void setEntrenadorRival (Entrenador newVar) {
    entrenadorRival = newVar;
  }

  public Entrenador getEntrenadorRival () {
    return entrenadorRival;
  }

  public void setTurno (Turno newVar) {
    turno = newVar;
  }

  public Turno getTurno () {
    return turno;
  }

  public void setPokemonKOEntrenador (int newVar) {
    pokemonKOEntrenador = newVar;
  }

  public int getPokemonKOEntrenador () {
    return pokemonKOEntrenador;
  }

  public void setPokemonKORival (int newVar) {
    pokemonKORival = newVar;
  }

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
