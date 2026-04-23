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
   * precio del objeto
   */
  private int precio;
  /**
   * buffs del objeto
   */
  private int statsBonus;
  /**
   * nerfs del objeto
   */
  private int statsMalus;
  /**
   * porcentaje del bonus/buff
   */
  private int porcentajeBonus;
  /**
   * porcentaje del malus/nerf
   */
  private int porcentajeMalus;
  /**
   * la imagen del objeto
   */
  private String imgObjeto;
  
  //
  // Constructor por defecto
  //
  public Objeto () {
    this.idObjeto = 1;
    this.nombreObjeto = "";
    this.descripcionObjeto = "";
    this.precio = 0;
    this.statsBonus = 0;
    this.statsMalus = 0;
    this.porcentajeBonus = 0;
    this.porcentajeMalus = 0;
    this.imgObjeto = "Null";
  };


  /**
   * Constructor parametrizado
   * */
  public Objeto(int idObjeto, String nombreObjeto, String descripcionObjeto, int precio, int statsBonus, int statsMalus, int porcentajeBonus, int porcentajeMalus, String imgObjeto) {
    this.idObjeto = idObjeto;
    this.nombreObjeto = nombreObjeto;
    this.descripcionObjeto = descripcionObjeto;
    this.precio = precio;
    this.statsBonus = statsBonus;
    this.statsMalus = statsMalus;
    this.porcentajeBonus = porcentajeBonus;
    this.porcentajeMalus = porcentajeMalus;
    this.imgObjeto = imgObjeto;
  }
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
