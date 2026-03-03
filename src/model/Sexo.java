package model;



/**
 * Class Sexo
 */
public class Sexo {

  //
  // Fields
  //

  /**
   * si es macho el Pokemon
   */
  private String macho;  /**

   * si es hembra el pokemon
   *    */

  private String hembra;
  /**
   * si el pokemon no tiene sexo
   */
  private String neutro;
  
  //
  // Constructors
  //
  public Sexo () { };
  
  //
  // Methods
  //


  //
  // Accessor methods
  //

  /**
   * Set the value of macho
   * si es macho el Pokemon
   * @param newVar the new value of macho
   */
  public void setMacho (String newVar) {
    macho = newVar;
  }

  /**
   * Get the value of macho
   * si es macho el Pokemon
   * @return the value of macho
   */
  public String getMacho () {
    return macho;
  }

  /**
   * Set the value of hembra
   * si es hembra el pokemon
   * 
   * @param newVar the new value of hembra
   */
  public void setHembra (String newVar) {
    hembra = newVar;
  }

  /**
   * Get the value of hembra
   * si es hembra el pokemon
   * 
   * @return the value of hembra
   */
  public String getHembra () {
    return hembra;
  }

  /**
   * Set the value of neutro
   * si el pokemon no tiene sexo
   * @param newVar the new value of neutro
   */
  public void setNeutro (String newVar) {
    neutro = newVar;
  }

  /**
   * Get the value of neutro
   * si el pokemon no tiene sexo
   * @return the value of neutro
   */
  public String getNeutro () {
    return neutro;
  }

  //
  // Other methods
  //

}
