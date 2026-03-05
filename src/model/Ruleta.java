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

  private String colorRuleta;
  /**
   * numeros de la ruleta 1-37
   */
  private int numerosRuleta;
  
  //
  // Constructors
  //
  public Ruleta () { };
  
  //
  // Methods
  //

  public void setColorRuleta (String color) {
    colorRuleta = color;
  }

  public String getColorRuleta () {
    return colorRuleta;
  }

  public void setNumerosRuleta (int newVar) {
    numerosRuleta = newVar;
  }

  public int getNumerosRuleta () {
    return numerosRuleta;
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
