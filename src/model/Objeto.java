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

  /**
   * Getters y Setters
   * */
  public int getIdObjeto() {
    return idObjeto;
  }

  public void setIdObjeto(int idObjeto) {
    this.idObjeto = idObjeto;
  }

  public String getNombreObjeto() {
    return nombreObjeto;
  }

  public void setNombreObjeto(String nombreObjeto) {
    this.nombreObjeto = nombreObjeto;
  }

  public String getDescripcionObjeto() {
    return descripcionObjeto;
  }

  public void setDescripcionObjeto(String descripcionObjeto) {
    this.descripcionObjeto = descripcionObjeto;
  }

  public int getPrecio() {
    return precio;
  }

  public void setPrecio(int precio) {
    this.precio = precio;
  }

  public int getStatsBonus() {
    return statsBonus;
  }

  public void setStatsBonus(int statsBonus) {
    this.statsBonus = statsBonus;
  }

  public int getStatsMalus() {
    return statsMalus;
  }

  public void setStatsMalus(int statsMalus) {
    this.statsMalus = statsMalus;
  }

  public int getPorcentajeBonus() {
    return porcentajeBonus;
  }

  public void setPorcentajeBonus(int porcentajeBonus) {
    this.porcentajeBonus = porcentajeBonus;
  }

  public int getPorcentajeMalus() {
    return porcentajeMalus;
  }

  public void setPorcentajeMalus(int porcentajeMalus) {
    this.porcentajeMalus = porcentajeMalus;
  }

  public String getImgObjeto() {
    return imgObjeto;
  }

  public void setImgObjeto(String imgObjeto) {
    this.imgObjeto = imgObjeto;
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

  @Override
  public String toString() {
    return "Objeto{" +
            "idObjeto=" + idObjeto +
            ", nombreObjeto='" + nombreObjeto + '\'' +
            ", descripcionObjeto='" + descripcionObjeto + '\'' +
            ", precio=" + precio +
            ", statsBonus=" + statsBonus +
            ", statsMalus=" + statsMalus +
            ", porcentajeBonus=" + porcentajeBonus +
            ", porcentajeMalus=" + porcentajeMalus +
            ", imgObjeto='" + imgObjeto + '\'' +
            '}';
  }
}
