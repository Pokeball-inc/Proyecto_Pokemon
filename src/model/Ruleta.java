package model;



/**
 * Class Ruleta
 */
public class Ruleta extends Casino {

  //
  // Fields
  //
  /**

   * color de la ruleta negro/rojo
   *    */

  private String colorNumero;
  /**
   * numeros de la ruleta 1-37
   */
  private int numerosRuleta_;
  
  //
  // Constructors
  //
  public Ruleta () { };
  
  //
  // Methods
  //


  //
  // Accessor methods
  //

  /**
   * Set the value of colorNumero
   * color de la ruleta negro/rojo
   * 
   * @param newVar the new value of colorNumero
   */
  public void setColorNumero (String newVar) {
    colorNumero = newVar;
  }

  /**
   * Get the value of colorNumero
   * color de la ruleta negro/rojo
   * 
   * @return the value of colorNumero
   */
  public String getColorNumero () {
    return colorNumero;
  }

  /**
   * Set the value of numerosRuleta_
   * numeros de la ruleta 1-37
   * @param newVar the new value of numerosRuleta_
   */
  public void setNumerosRuleta_ (int newVar) {
    numerosRuleta_ = newVar;
  }

  /**
   * Get the value of numerosRuleta_
   * numeros de la ruleta 1-37
   * @return the value of numerosRuleta_
   */
  public int getNumerosRuleta_ () {
    return numerosRuleta_;
  }

  //
  // Other methods
  //

  /**
   * metodo para apsotar ruleta  se puede elegir, numero, color o numero y color
   * @param        color
   * @param        numero
   */
  public void apostarRuleta(String color, int numero)
  {
  }


}
