package model;



/**
 * Class LigaPokemon
 */
public class LigaPokemon {

  //
  // Fields
  //

  /**
   * lista de entrenadores de la liga
   */
  private Entrenador entrenadorLigaPokemon;
  
  //
  // Constructors
  //
  public LigaPokemon () { };
  
  //
  // Methods
  //


  //
  // Accessor methods
  //

  /**
   * Set the value of entrenadorLigaPokemon
   * lista de entrenadores de la liga
   * @param newVar the new value of entrenadorLigaPokemon
   */
  public void setEntrenadorLigaPokemon (Entrenador newVar) {
    entrenadorLigaPokemon = newVar;
  }

  /**
   * Get the value of entrenadorLigaPokemon
   * lista de entrenadores de la liga
   * @return the value of entrenadorLigaPokemon
   */
  public Entrenador getEntrenadorLigaPokemon () {
    return entrenadorLigaPokemon;
  }

  //
  // Other methods
  //

  /**
   * metodo para calcular las ganancias o perdidas de los pokedolares en la liga
   * pokemon
   */
  public void gananciasPokedolares()
  {
  }


  /**
   * metodo para recuperar la vida de los pokemon, entre combate y combate
   */
  public void recuperacionPokemon()
  {
  }


}
