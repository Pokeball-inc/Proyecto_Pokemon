package model;



/**
 * Class Casino
 */
abstract public class Casino {

  //
  // Fields
  //

  private Entrenador entrenador;
  
  //
  // Constructor defecto
  //
  public Casino () { };
  
// Constructor con entrenador
  public Casino(Entrenador entrenador) {
      this.entrenador = entrenador;
  }
  

  // Constructor copia 
  public Casino(Casino c) {
      this.entrenador = c.entrenador;
  }
  
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
  public void setEntrenador (Entrenador entrenador) {
    this.entrenador = entrenador;
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
  public void costeApuesta(int apuesta)
  {
  }


  /**
   * metodo par calcular la cantidad del premio si se gana la apuesta
   * 
   */
  public void cantidadPremio(int apuesta)
  {
  }



}
