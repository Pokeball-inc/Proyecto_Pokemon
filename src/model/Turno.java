package model;



/**
 * Class Turno
 * Ej:
 * Turno 1:
 * Entrenador: Pikachu usa Ataque R�pido.
 * Rival: Charmander usa Ascuas.
 */
public class Turno {

  //
  // Fields
  //

  /**
   * numero del turno actual
   */
  private int numeroTurnoActual = 1;  /**

   * accion realizada por el entrenador (el jugador)
   *    */

  private String accionEntrenador;
  /**
   * accion realizada por el entrenador rival
   */
  private String accionEntrenadorRival;


  ///
  /// Datos para el Log
  ///

  private String nombrePk1;
  private int nivelPk1;
  private String nombreEntrenador1;
  private String estadoPk1; // "OK" o "KO"

  private String nombrePk2;
  private int nivelPk2;
  private String nombreEntrenador2;
  private String estadoPk2; // "OK" o "KO"
  //
  // Constructors
  //
  public Turno () { };
  
  //
  // Methods
  //

  public void setNumeroTurnoActual (int newVar) {
    numeroTurnoActual = newVar;
  }

  public int getNumeroTurnoActual () {
    return numeroTurnoActual;
  }

  public void setAccionEntrenador (String newVar) {
    accionEntrenador = newVar;
  }

  public String getAccionEntrenador () {
    return accionEntrenador;
  }

  public void setAccionEntrenadorRival (String newVar) {
    accionEntrenadorRival = newVar;
  }

  public String getAccionEntrenadorRival () {
    return accionEntrenadorRival;
  }

  public String getEstadoPokemon1() {
    return estadoPk1;
  }

  public void setEstadoPokemon1(String estadoPokemon1) {
    this.estadoPk1 = estadoPokemon1;
  }

  public String getEstadoPokemon2() {
    return estadoPk2;
  }

  public void setEstadoPokemon2(String estadoPokemon2) {
    this.estadoPk2 = estadoPokemon2;
  }


  //
  // Other methods
  //

  /// Method para guardar los datos del pokemon1 en los logs

  public void setDatosPk1(String nombre, int nivel, String entrenador, String estado) {
    this.nombrePk1 = nombre;
    this.nivelPk1 = nivel;
    this.nombreEntrenador1 = entrenador;
    this.estadoPk1 = estado;
  }

  /// Method para guardar los datos del pokemon2 en los logs

  public void setDatosPk2(String nombre, int nivel, String entrenador, String estado) {
    this.nombrePk2 = nombre;
    this.nivelPk2 = nivel;
    this.nombreEntrenador2 = entrenador;
    this.estadoPk2 = estado;
  }

  /// Method para guardar los datos del pokemon, esto es para los Logs

  public String getDatosPokemon1() {
    return String.format("“%s”, %d, %s, %s", nombrePk1, nivelPk1, nombreEntrenador1, estadoPk1);
  }

  public String getDatosPokemon2() {
    return String.format("“%s”, %d, %s, %s", nombrePk2, nivelPk2, nombreEntrenador2, estadoPk2);
  }


}
