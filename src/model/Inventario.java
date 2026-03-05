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

  public void setObjetoInventario (Objeto newVar) {
    objetoInventario = newVar;
  }

  public Objeto getObjetoInventario () {
    return objetoInventario;
  }

  //
  // Other methods
  //

}
