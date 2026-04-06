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
	 */

	private EleccionColor colorApuesta;

	/**
	 * tipo de apuesta
	 */

	private formaApuesta combinacionApuesta;

	/**
	 * numeros de la ruleta 1-37
	 */
	private int numeroApuesta;

	private int multiplicadorAplicado = 0;

	private int ultimoNumero;

	private String ultimoColor;

	private int ultimoPremio;

	//
	// Constructors
	//
	public Ruleta() {
	};

	//
	// Methods
	//

	public void setColorApuesta(EleccionColor color) {
		colorApuesta = color;
	}

	public EleccionColor getColorApuesta() {
		return colorApuesta;
	}

	public void setCombinacionApuesta(formaApuesta combinacion) {
		combinacionApuesta = combinacion;
	}

	public formaApuesta getCombinacionApuesta() {
		return combinacionApuesta;
	}

	public void setNumeroApuesta(int numero) {
		numeroApuesta = numero;
	}

	public int getNumeroApuesta() {
		return numeroApuesta;
	}

	public void setMultiplicadorAplicado(int numero) {
		multiplicadorAplicado = numero;
	}

	public int getMultiplicadorAplicado() {
		return multiplicadorAplicado;
	}

	public int getUltimoNumero() {
		return ultimoNumero;
	}

	public void setUltimoNumero(int numero) {
		this.ultimoNumero = numero;
	}

	public String getUltimoColor() {
		return ultimoColor;
	}

	public void setUltimoColor(String color) {
		this.ultimoColor = color;
	}

	public int getUltimoPremio() {
		return ultimoPremio;
	}

	public void setUltimoPremio(int premio) {
		this.ultimoPremio = premio;
	}

	/**
	 * metodo para apsotar ruleta se puede elegir, numero, color o numero y color
	 * 
	 * @param color
	 * @param numero
	 */

// metodo para jugar la ruleta
	public String jugarRuleta(formaApuesta tipo, int numElegido, EleccionColor colorElegido, int cantidad) {
		Entrenador entrenador = getEntrenador();

		// es necesario tener dinero para poder apostar
		if (!puedeApostar(cantidad)) {
			return "No tienes suficientes pokedollars.";
		}

		// cobramos la apuesta
		costeApuesta(cantidad);

		// generamos el resultado aleatorio entre 1 y 37 y el color entre ROJO y NEGRO
		int numGanador = (int) (Math.random() * 37) + 1;
		EleccionColor colorGanador;
		if (Math.random() < 0.5) {
			colorGanador = EleccionColor.ROJO; // Si el azar es menor a 0.5, es rojo
		} else {
			colorGanador = EleccionColor.NEGRO; // Si no, es negro
		}

		// guardamos el numero y color que han salido para la alerta
		this.ultimoNumero = numGanador;
		this.ultimoColor = colorGanador.toString();

		// reseteamos el premio
		this.multiplicadorAplicado = 0;
		boolean aciertoNum = (numElegido == numGanador);
		boolean aciertoCol = (colorElegido == colorGanador);

		// determinamos el multiplicador segun las reglas
		if (tipo == formaApuesta.NUMEROSOLO && aciertoNum) {
			// si se acierta numero se gana lo apostado x10
			this.multiplicadorAplicado = 10;
		}
		// si se acierta color se gana lo apostado x2
		else if (tipo == formaApuesta.COLORSOLO && aciertoCol) {
			this.multiplicadorAplicado = 2;
		}
		// si se acierta numero y color se gana lo apostado por 20, si en algun caso
		// acierta algun parametro suelto se le da ganancia correspoppndiente
		else if (tipo == formaApuesta.NUMYCOLOR) {
			if (aciertoNum && aciertoCol) {
				this.multiplicadorAplicado = 20;
			} else if (aciertoNum) {
				this.multiplicadorAplicado = 10;
			} else if (aciertoCol) {
				this.multiplicadorAplicado = 2;
			}
		}

		// calculamos y guardamos el premio final para la alerta
		this.ultimoPremio = cantidad * this.multiplicadorAplicado;

		// si el multiplicador es mayor a 0 se ha ganado
		if (this.multiplicadorAplicado > 0) {
			int premioTotal = cantidad * this.multiplicadorAplicado;

			cantidadPremio(cantidad);

			return "¡HAS GANADO!";
		} else {
			// si no acierta nada se pierde lo apostado
			return "¡HAS PERDIDO!";
		}
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
	public void cantidadPremio(int apuesta) {
		Entrenador entrenador = getEntrenador();
		if (entrenador != null && apuesta > 0) {
			// usamos el multiplicador que calculamos en jugarRuleta
			int premioFinal = apuesta * multiplicadorAplicado;
			entrenador.setPokedollares(entrenador.getPokedollares() + premioFinal);
		}
	}

}
