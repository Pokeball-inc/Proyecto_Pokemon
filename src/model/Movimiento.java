package model;


import java.util.*;


/**
 * Class Movimiento
 */
public class Movimiento {

  //
  // Fields
  //
  /**

   * identificador de cada movimiento
   *    */

  private int idMovimiento = 1;
  /**
   * nombre del movimiento
   */
  private String nombreMovimiento;
  /**
   * descripcion del movimiento
   */
  private String descripcionMovimiento;
  /**
   * tipo ( atributo) del movimiento
   */
  private Tipos tipo;
  /**
   * tipo de movimiento, si es ataque, mejora o estado
   */
  private TiposMovimiento tipoMovimiento;  /**

   * el estado que aplica el ataque
   *    */

  private Estados estadoAplicado;
  /**
   * los turnos que dura el estado aplicado o la mejora
   */
  private int turnos;
  /**
   * la potencia del movimiento tipo ataque
   */
  private int potencia;
  /**
   * cantidad total de movimientos de cada moviento
   */
  private int cantidadMovimientos = 5;
  /**
   * para la animacion del movimiento
   */
  //private String animacionMovimiento;
  
  //
  // Constructors
  //
  public Movimiento () { };
  
  //
  // Methods
  //


  //
  // Accessor methods
  //

  /**
   * Set the value of idMovimiento
   * identificador de cada movimiento
   * 
   * @param newVar the new value of idMovimiento
   */
  public void setIdMovimiento (int newVar) {
    idMovimiento = newVar;
  }

  /**
   * Get the value of idMovimiento
   * identificador de cada movimiento
   * 
   * @return the value of idMovimiento
   */
  public int getIdMovimiento () {
    return idMovimiento;
  }

  /**
   * Set the value of nombreMovimiento
   * nombre del movimiento
   * @param newVar the new value of nombreMovimiento
   */
  public void setNombreMovimiento (String newVar) {
    nombreMovimiento = newVar;
  }

  /**
   * Get the value of nombreMovimiento
   * nombre del movimiento
   * @return the value of nombreMovimiento
   */
  public String getNombreMovimiento () {
    return nombreMovimiento;
  }

  /**
   * Set the value of descripcionMovimiento
   * descripcion del movimiento
   * @param newVar the new value of descripcionMovimiento
   */
  public void setDescripcionMovimiento (String newVar) {
    descripcionMovimiento = newVar;
  }

  /**
   * Get the value of descripcionMovimiento
   * descripcion del movimiento
   * @return the value of descripcionMovimiento
   */
  public String getDescripcionMovimiento () {
    return descripcionMovimiento;
  }

  /**
   * Set the value of tipo
   * tipo ( atributo) del movimiento
   * @param newVar the new value of tipo
   */
  public void setTipo (Tipos newVar) {
    tipo = newVar;
  }

  /**
   * Get the value of tipo
   * tipo ( atributo) del movimiento
   * @return the value of tipo
   */
  public Tipos getTipo () {
    return tipo;
  }

  /**
   * Set the value of tipoMovimiento
   * tipo de movimiento, si es ataque, mejora o estado
   * @param newVar the new value of tipoMovimiento
   */
  public void setTipoMovimiento (TiposMovimiento newVar) {
    tipoMovimiento = newVar;
  }

  /**
   * Get the value of tipoMovimiento
   * tipo de movimiento, si es ataque, mejora o estado
   * @return the value of tipoMovimiento
   */
  public TiposMovimiento getTipoMovimiento () {
    return tipoMovimiento;
  }

  /**
   * Set the value of estadoAplicado
   * el estado que aplica el ataque
   * 
   * @param newVar the new value of estadoAplicado
   */
  public void setEstadoAplicado (Estados newVar) {
    estadoAplicado = newVar;
  }

  /**
   * Get the value of estadoAplicado
   * el estado que aplica el ataque
   * 
   * @return the value of estadoAplicado
   */
  public Estados getEstadoAplicado () {
    return estadoAplicado;
  }

  /**
   * Set the value of turnos
   * los turnos que dura el estado aplicado o la mejora
   * @param newVar the new value of turnos
   */
  public void setTurnos (int newVar) {
    turnos = newVar;
  }

  /**
   * Get the value of turnos
   * los turnos que dura el estado aplicado o la mejora
   * @return the value of turnos
   */
  public int getTurnos () {
    return turnos;
  }

  /**
   * Set the value of potencia
   * la potencia del movimiento tipo ataque
   * @param newVar the new value of potencia
   */
  public void setPotencia (int newVar) {
    potencia = newVar;
  }

  /**
   * Get the value of potencia
   * la potencia del movimiento tipo ataque
   * @return the value of potencia
   */
  public int getPotencia () {
    return potencia;
  }

  /**
   * Set the value of cantidadMovimientos
   * cantidad total de movimientos de cada moviento
   * @param newVar the new value of cantidadMovimientos
   */
  public void setCantidadMovimientos (int newVar) {
    cantidadMovimientos = newVar;
  }

  /**
   * Get the value of cantidadMovimientos
   * cantidad total de movimientos de cada moviento
   * @return the value of cantidadMovimientos
   */
  public int getCantidadMovimientos () {
    return cantidadMovimientos;
  }

  /**
   * Set the value of animacionMovimiento
   * para la animacion del movimiento
   * @param newVar the new value of animacionMovimiento
   */
  /*
  public void setanimacionMovimiento (String newVar) {
    animacionMovimiento = newVar;
  }
*/
  /**
   * Get the value of animacionMovimiento
   * para la animacion del movimiento
   * @return the value of animacionMovimiento
   */
  /*
  public String getAnimacionMovimiento () {
    return animacionMovimiento;
  }
*/
  //
  // Other methods
  //

  /**
   * metodo para aplicar los estados del ataque tipo estado
   * @param        tipoMovimiento
   */
  public void aplicarEstado(TiposMovimiento movimientoEstado)
  {
  }


  /**
   * metodo para aplicar la mejora del ataque tipo mejor
   * @param        newparameter
   */
  public void aplicarMejora(TiposMovimiento movimientoMejora)
  {
  }


  /**
   * metodo para realizar da�o del ataque tipo atque
   * @param        newparameter
   */
  public void aplicarDanio(TiposMovimiento movimientoDanio)
  {
  }


}
