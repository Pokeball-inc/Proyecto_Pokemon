package model;



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

  public void setIdObjeto (int newVar) {
    idObjeto = newVar;
  }

  public int getIdObjeto () {
    return idObjeto;
  }

  public void setNombreObjeto (String newVar) {
    nombreObjeto = newVar;
  }

  public String getNombreObjeto () {
    return nombreObjeto;
  }

  public void setDescripcionObjeto (String newVar) {
    descripcionObjeto = newVar;
  }

  public String getDescripcionObjeto () {
    return descripcionObjeto;
  }

  public void setImgObjeto (String newVar) {
    imgObjeto = newVar;
  }

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
