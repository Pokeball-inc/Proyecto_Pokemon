

/**
 * Class Turno
 * Ej:
 * Turno 1:
 * Entrenador: Pikachu usa Ataque Rápido.
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


  //
  // Accessor methods
  //

  /**
   * Set the value of numeroTurnoActual
   * numero del turno actual
   * @param newVar the new value of numeroTurnoActual
   */
  public void setNumeroTurnoActual (int newVar) {
    numeroTurnoActual = newVar;
  }

  /**
   * Get the value of numeroTurnoActual
   * numero del turno actual
   * @return the value of numeroTurnoActual
   */
  public int getNumeroTurnoActual () {
    return numeroTurnoActual;
  }

  /**
   * Set the value of accionEntrenador
   * accion realizada por el entrenador (el jugador)
   * 
   * @param newVar the new value of accionEntrenador
   */
  public void setAccionEntrenador (String newVar) {
    accionEntrenador = newVar;
  }

  /**
   * Get the value of accionEntrenador
   * accion realizada por el entrenador (el jugador)
   * 
   * @return the value of accionEntrenador
   */
  public String getAccionEntrenador () {
    return accionEntrenador;
  }

  /**
   * Set the value of accionEntrenadorRival
   * accion realizada por el entrenador rival
   * @param newVar the new value of accionEntrenadorRival
   */
  public void setAccionEntrenadorRival (String newVar) {
    accionEntrenadorRival = newVar;
  }

  /**
   * Get the value of accionEntrenadorRival
   * accion realizada por el entrenador rival
   * @return the value of accionEntrenadorRival
   */
  public String getAccionEntrenadorRival () {
    return accionEntrenadorRival;
  }

  //
  // Other methods
  //

}
