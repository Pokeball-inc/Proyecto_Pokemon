

/**
 * Class Pokedex
 */
public class Pokedex {

  //
  // Fields
  //

  /**
   * lista o diccionario de Pokemon
   */
  private Pokemon listaPokemon;
  
  //
  // Constructors
  //
  public Pokedex () { };
  
  //
  // Methods
  //


  //
  // Accessor methods
  //

  /**
   * Set the value of listaPokemon
   * lista o diccionario de Pokemon
   * @param newVar the new value of listaPokemon
   */
  public void setListaPokemon (Pokemon newVar) {
    listaPokemon = newVar;
  }

  /**
   * Get the value of listaPokemon
   * lista o diccionario de Pokemon
   * @return the value of listaPokemon
   */
  public Pokemon getListaPokemon () {
    return listaPokemon;
  }

  //
  // Other methods
  //

  /**
   * metodo para registrar un nuevo Pokemon visto
   * @param        Pokemon
   */
  public void registrarNuevoPokemon(Pokemon Pokemon)
  {
  }


  /**
   * pone el simbolo de pokeball en la Pokedex del Pokemon capturado
   * @param        Pokemon
   */
  public void marcarPokemonCapturado(Pokemon Pokemon)
  {
  }


  /**
   * consulta la informacion del Pokemon
   * @param        Pokemon
   */
  public void consultarPokemon(Pokemon Pokemon)
  {
  }


}
