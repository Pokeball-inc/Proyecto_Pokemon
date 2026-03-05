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

  //
  // Other methods
  //

}
