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
  private int cantidadMovimientos = 10;
  /**
   * para la animacion del movimiento
   */
  private String animacionMovimiento;
  
  //sera  "Físico" o "Especial" para saber qeu ataque o defensa usar si la especial o la normal
  private String categoriaDano; 
  
  
  //
  // Constructors
  //
  public Movimiento () { };
  
  //
  // Methods
  //

  public void setIdMovimiento (int newVar) {
    idMovimiento = newVar;
  }

  public int getIdMovimiento () {
    return idMovimiento;
  }

  public void setNombreMovimiento (String newVar) {
    nombreMovimiento = newVar;
  }

  public String getNombreMovimiento () {
    return nombreMovimiento;
  }

  public void setDescripcionMovimiento (String newVar) {
    descripcionMovimiento = newVar;
  }

  public String getDescripcionMovimiento () {
    return descripcionMovimiento;
  }

  public void setTipo (Tipos newVar) {
    tipo = newVar;
  }

  public Tipos getTipo () {
    return tipo;
  }

  public void setTipoMovimiento (TiposMovimiento newVar) {
    tipoMovimiento = newVar;
  }

  public TiposMovimiento getTipoMovimiento () {
    return tipoMovimiento;
  }

  public void setEstadoAplicado (Estados newVar) {
    estadoAplicado = newVar;
  }

  public Estados getEstadoAplicado () {
    return estadoAplicado;
  }

  public void setTurnos (int newVar) {
    turnos = newVar;
  }

  public int getTurnos () {
    return turnos;
  }

  public void setPotencia (int newVar) {
    potencia = newVar;
  }

  public int getPotencia () {
    return potencia;
  }

  public void setCantidadMovimientos (int newVar) {
    cantidadMovimientos = newVar;
  }

  public int getCantidadMovimientos () {
    return cantidadMovimientos;
  }


  public void setanimacionMovimiento (String newVar) {
    animacionMovimiento = newVar;
  }

  public String getAnimacionMovimiento () {
    return animacionMovimiento;
  }

  public String getCategoriaDano() {
	return categoriaDano;
}

  public void setCategoriaDano(String categoriaDano) {
	this.categoriaDano = categoriaDano;
  }
  
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

	// metodo apra reducir lso movimientos
	public void reducirUso() {
		if (this.cantidadMovimientos > 0) {
			this.cantidadMovimientos--;
		}
	}

}
