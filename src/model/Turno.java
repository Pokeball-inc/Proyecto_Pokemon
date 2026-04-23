package model;



/**
 * Class Turno
 * Ej:
 * Turno 1:
 * Entrenador: Pikachu usa Ataque R�pido.
 * Rival: Charmander usa Ascuas.
 */
public class Turno {

  //
  // Fields
  //

  /**
   * numero del turno actual
   */
  private int numeroTurnoActual = 1;  /**

   * accion realizada por el entrenador (el jugador)
   *    */

  private String accionEntrenador;
  /**
   * accion realizada por el entrenador rival
   */
  private String accionEntrenadorRival;
  
  // para saber el estado de salud en el momento del log
  private String estadoPokemon1; // "OK" o "KO"
  private String estadoPokemon2; // "OK" o "KO"
  //
  // Constructors
  //
  public Turno () { };
  
  //
  // Methods
  //

  public void setNumeroTurnoActual (int newVar) {
    numeroTurnoActual = newVar;
  }

  public int getNumeroTurnoActual () {
    return numeroTurnoActual;
  }

  public void setAccionEntrenador (String newVar) {
    accionEntrenador = newVar;
  }

  public String getAccionEntrenador () {
    return accionEntrenador;
  }

  public void setAccionEntrenadorRival (String newVar) {
    accionEntrenadorRival = newVar;
  }

  public String getAccionEntrenadorRival () {
    return accionEntrenadorRival;
  }

  public String getEstadoPokemon1() {
	return estadoPokemon1;
  }

  public void setEstadoPokemon1(String estadoPokemon1) {
	this.estadoPokemon1 = estadoPokemon1;
  }

  public String getEstadoPokemon2() {
	return estadoPokemon2;
  }

  public void setEstadoPokemon2(String estadoPokemon2) {
	this.estadoPokemon2 = estadoPokemon2;
  }

  //
  // Other methods
  //

}
