package model;



/**
 * Class Inventario
 */
public class Inventario {

  //
  // Fields
  //

  /**
   * todos los objetos que tiene el entrenador disponible
   */
  private Objeto objetoInventario;
  
  //
  // Constructors
  //
  public Inventario () { };
  
  //
  // Methods
  //


  //
  // Accessor methods
  //

  /**
   * Set the value of objetoInventario_
   * todos los objetos que tiene el entrenador disponible
   * @param newVar the new value of objetoInventario_
   */
  public void setObjetoInventario_ (Objeto newVar) {
    objetoInventario = newVar;
  }

  /**
   * Get the value of objetoInventario_
   * todos los objetos que tiene el entrenador disponible
   * @return the value of objetoInventario_
   */
  public Objeto getObjetoInventario_ () {
    return objetoInventario;
  }

  //
  // Other methods
  //

}
