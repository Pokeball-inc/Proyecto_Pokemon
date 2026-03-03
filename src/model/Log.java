

/**
 * Class Log
 */
public class Log {

  //
  // Fields
  //

  /**
   * el nombre dle log
   */
  private String nombreLog;
  /**
   * lista con todos los turbnos realizados
   */
  private Turno turnosLog_;
  
  //
  // Constructors
  //
  public Log () { };
  
  //
  // Methods
  //


  //
  // Accessor methods
  //

  /**
   * Set the value of nombreLog
   * el nombre dle log
   * @param newVar the new value of nombreLog
   */
  public void setNombreLog (String newVar) {
    nombreLog = newVar;
  }

  /**
   * Get the value of nombreLog
   * el nombre dle log
   * @return the value of nombreLog
   */
  public String getNombreLog () {
    return nombreLog;
  }

  /**
   * Set the value of turnosLog_
   * lista con todos los turbnos realizados
   * @param newVar the new value of turnosLog_
   */
  public void setTurnosLog_ (Turno newVar) {
    turnosLog_ = newVar;
  }

  /**
   * Get the value of turnosLog_
   * lista con todos los turbnos realizados
   * @return the value of turnosLog_
   */
  public Turno getTurnosLog_ () {
    return turnosLog_;
  }

  //
  // Other methods
  //

}
