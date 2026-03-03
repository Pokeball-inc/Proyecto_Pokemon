

/**
 * Class Casino
 */
abstract public class Casino {

  //
  // Fields
  //

  private Entrenador entrenador;
  
  //
  // Constructors
  //
  public Casino () { };
  
  //
  // Methods
  //


  //
  // Accessor methods
  //

  /**
   * Set the value of entrenador
   * @param newVar the new value of entrenador
   */
  public void setEntrenador (Entrenador newVar) {
    entrenador = newVar;
  }

  /**
   * Get the value of entrenador
   * @return the value of entrenador
   */
  public Entrenador getEntrenador () {
    return entrenador;
  }

  //
  // Other methods
  //

  /**
   * metodo para el coste de la apuesta
   */
  public void costeApuesta()
  {
  }


  /**
   * metodo par calcular la cantidad del premio si se gana la apuesta
   * 
   */
  public void cantidadPremio()
  {
  }


}
