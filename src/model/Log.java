package model;



/**
 * Class Log
 */
public class Log {

  //
  // Fields
  //

  /**
   * el nombre del log
   */
  private String nombreLog;
  /**
   * lista con todos los turbnos realizados
   */
  private Turno turnosLog;
  
  //
  // Constructors
  //
  public Log () { };
  
  //
  // Methods
  //

  public void setNombreLog (String newVar) {
    nombreLog = newVar;
  }

  public String getNombreLog () {
    return nombreLog;
  }

  public void setTurnosLog (Turno newVar) {
    turnosLog = newVar;
  }

  public Turno getTurnosLog () {
    return turnosLog;
  }

  //
  // Other methods
  //

}
