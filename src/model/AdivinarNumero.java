package model;



/**
 * Class AdivinarNumero
 */
public class AdivinarNumero extends Casino {

  //
  // Fields
  //
  /**

   * numero entre 1 y 20 para adivinar
   * 5 intentos   */

  private int numeroSecreto;
  /**
   * numero introducido por el usuario
   */
  private int numeroIntroducido;
  /**
   * numeor de intentos maximo 5
   */
  private int intentosRestantes = 5;
  
  //
  // Constructors
  //
  public AdivinarNumero () { };
  
  //
  // Methods
  //


  //
  // Accessor methods
  //

  /**
   * Set the value of numeroSecreto
   * numero entre 1 y 20 para adivinar
   * 5 intentos
   * @param newVar the new value of numeroSecreto
   */
  public void setNumeroSecreto (int newVar) {
    numeroSecreto = newVar;
  }

  /**
   * Get the value of numeroSecreto
   * numero entre 1 y 20 para adivinar
   * 5 intentos
   * @return the value of numeroSecreto
   */
  public int getNumeroSecreto () {
    return numeroSecreto;
  }

  /**
   * Set the value of numeroIntroducido
   * numero introducido por el usuario
   * @param newVar the new value of numeroIntroducido
   */
  public void setNumeroIntroducido (int newVar) {
    numeroIntroducido = newVar;
  }

  /**
   * Get the value of numeroIntroducido
   * numero introducido por el usuario
   * @return the value of numeroIntroducido
   */
  public int getNumeroIntroducido () {
    return numeroIntroducido;
  }

  /**
   * Set the value of intentosRestantes
   * numeor de intentos maximo 5
   * @param newVar the new value of intentosRestantes
   */
  public void setIntentosRestantes (int newVar) {
    intentosRestantes = newVar;
  }

  /**
   * Get the value of intentosRestantes
   * numeor de intentos maximo 5
   * @return the value of intentosRestantes
   */
  public int getIntentosRestantes () {
    return intentosRestantes;
  }

  //
  // Other methods
  //

  /**
   * la mecanica de adivinar el numero, intentos maximos 5
   * @param        numeroUsuario
   * @param        numeroSecreto
   */
  public void adivinarNumero(int numeroUsuario, int numeroSecreto)
  {
  }


}
