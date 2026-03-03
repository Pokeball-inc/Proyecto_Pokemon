

/**
 * Class Objeto
 * clase Objeto para los objetos de los pokemon
 */
public class Objeto {

  //
  // Fields
  //
  /**

   * identificador de cada objeto
   *    */

  private int idObjeto = 1;
  /**
   * nombre del objeto
   */
  private String nombreObjeto;
  /**
   * descripcion del objeto
   */
  private String descripcionObjeto;
  /**
   * la imagen del objeto
   */
  private String imgObjeto;
  
  //
  // Constructors
  //
  public Objeto () { };
  
  //
  // Methods
  //


  //
  // Accessor methods
  //

  /**
   * Set the value of idObjeto
   * identificador de cada objeto
   * 
   * @param newVar the new value of idObjeto
   */
  public void setIdObjeto (int newVar) {
    idObjeto = newVar;
  }

  /**
   * Get the value of idObjeto
   * identificador de cada objeto
   * 
   * @return the value of idObjeto
   */
  public int getIdObjeto () {
    return idObjeto;
  }

  /**
   * Set the value of nombreObjeto
   * nombre del objeto
   * @param newVar the new value of nombreObjeto
   */
  public void setNombreObjeto (String newVar) {
    nombreObjeto = newVar;
  }

  /**
   * Get the value of nombreObjeto
   * nombre del objeto
   * @return the value of nombreObjeto
   */
  public String getNombreObjeto () {
    return nombreObjeto;
  }

  /**
   * Set the value of descripcionObjeto
   * descripcion del objeto
   * @param newVar the new value of descripcionObjeto
   */
  public void setDescripcionObjeto (String newVar) {
    descripcionObjeto = newVar;
  }

  /**
   * Get the value of descripcionObjeto
   * descripcion del objeto
   * @return the value of descripcionObjeto
   */
  public String getDescripcionObjeto () {
    return descripcionObjeto;
  }

  /**
   * Set the value of imgObjeto
   * la imagen del objeto
   * @param newVar the new value of imgObjeto
   */
  public void setImgObjeto (String newVar) {
    imgObjeto = newVar;
  }

  /**
   * Get the value of imgObjeto
   * la imagen del objeto
   * @return the value of imgObjeto
   */
  public String getImgObjeto () {
    return imgObjeto;
  }

  //
  // Other methods
  //

  /**
   * aplica el estado positivo y negativo del Pokemon
   * @param        Pokemon
   * @param        objeto
   */
  public void aplicarEstado(Pokemon Pokemon, Objeto objeto)
  {
  }


}
