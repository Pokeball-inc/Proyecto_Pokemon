package model;



/**
 * Class Ruleta
 */
public class Ruleta extends Casino {

  //
  // Fields
  //
	
	// enum para la eleccion de color del jugador
	public enum EleccionColor {
		NEGRO, ROJO
	}
	
	// enum para la combinacion de apuesta en la eleccion del jugador
	public enum formaApuesta {
		NUMEROSOLO, COLORSOLO, NUMYCOLOR
	}
	
  /**
   * color de la ruleta negro/rojo
   *    */

  private EleccionColor colorApuesta;
  
  /**
   * tipo de apuesta
   *    */

  private formaApuesta combinacionApuesta;
  
  /**
   * numeros de la ruleta 1-37
   */
  private int numeroApuesta;
  
  
  
  
  //
  // Constructors
  //
  public Ruleta () { };
  
  //
  // Methods
  //

  public void setColorApuesta (EleccionColor color) {
	  colorApuesta = color;
  }

  public EleccionColor getColorApuesta () {
    return colorApuesta;
  }
  
  public void setCombinacionApuesta (formaApuesta combinacion) {
	  combinacionApuesta = combinacion;
  }

  public formaApuesta getCombinacionApuesta() {
    return combinacionApuesta;
  }

  public void setNumeroApuesta (int numero) {
	  numeroApuesta = numero;
  }

  public int getNumeroApuesta () {
    return numeroApuesta;
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

	@Override
	// el costeApuesta descuenta los Pokedóllares al entrenador
	public void costeApuesta(int apuesta) {
		Entrenador entrenador = getEntrenador();
		// si el entrenador existe y la apuesta es mayor a 0 le descontamos lo apostado
		if (entrenador != null && apuesta > 0) {
			entrenador.setPokedollares(entrenador.getPokedollares() - apuesta);
		}
	}

	@Override
	// la cantidadPremio suma los Pokedóllares al entrenador
	public void cantidadPremio(int apuesta) {
		Entrenador entrenador = getEntrenador();
		// si el entrenador existe y la apuesta es mayor a 0 le entregamos sus ganacias
		if (entrenador != null && apuesta > 0) {
			entrenador.setPokedollares(entrenador.getPokedollares() + (apuesta * 2));
		}
	}


}
