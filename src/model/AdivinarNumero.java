package model;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Class AdivinarNumero
 */
public class AdivinarNumero extends Casino {
	//
	// Fields
	//
	/**
	 * 
	 * numero entre 1 y 20 para adivinar 5 intentos
	 */

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
	// Constructor defecto
	public AdivinarNumero() {
		super();
	}

	// Constructor con entrenador
	public AdivinarNumero(Entrenador entrenador) {
		super(entrenador);
	}

	// Constructor copia
	public AdivinarNumero(AdivinarNumero an) {
		super(an);
	}

	//
	// Methods
	//

	//
	// Accessor methods
	//

	/**
	 * Set the value of numeroSecreto numero entre 1 y 20 para adivinar 5 intentos
	 * 
	 * @param newVar the new value of numeroSecreto
	 */
	public void setNumeroSecreto(int numero) {
		numeroSecreto = numero;
	}

	/**
	 * Get the value of numeroSecreto numero entre 1 y 20 para adivinar 5 intentos
	 * 
	 * @return the value of numeroSecreto
	 */
	public int getNumeroSecreto() {
		return numeroSecreto;
	}

	/**
	 * Set the value of numeroIntroducido numero introducido por el usuario
	 * 
	 * @param newVar the new value of numeroIntroducido
	 */
	public void setNumeroIntroducido(int numero) {
		numeroIntroducido = numero;
	}

	/**
	 * Get the value of numeroIntroducido numero introducido por el usuario
	 * 
	 * @return the value of numeroIntroducido
	 */
	public int getNumeroIntroducido() {
		return numeroIntroducido;
	}

	/**
	 * Set the value of intentosRestantes numeor de intentos maximo 5
	 * 
	 * @param newVar the new value of intentosRestantes
	 */
	public void setIntentosRestantes(int numero) {
		intentosRestantes = numero;
	}

	/**
	 * Get the value of intentosRestantes numeor de intentos maximo 5
	 * 
	 * @return the value of intentosRestantes
	 */
	public int getIntentosRestantes() {
		return intentosRestantes;
	}

	//
	// Other methods
	//

	/**
	 * la mecanica de adivinar el numero, intentos maximos 5
	 * 
	 * @param numeroUsuario
	 * @param numeroSecreto
	 */
	public void adivinarNumero(int numeroIntroducido, int numeroSecreto, int intentosRestantes, int apuesta) {
		Scanner sc = new Scanner(System.in);
		apuesta = 100;
		// empezamos el bucle
		do {
			Entrenador entrenador = getEntrenador();
			// comprobamos que haya entrenador
			if (entrenador == null) {
				System.out.println("No se encuentra al entrenador.");
				break;
			}

			// comprobamos si tiene Pokedóllares el entrenador
			if (apuesta > entrenador.getPokedollares()) {
				System.out.println("Pokedóllares insuficientes.");
				break;
			}
			// comprobamos si hay intentos
			if (intentosRestantes > 0) {
				// try catch por si no se introduce un numero
				try {
					// ramdom de 1 a 20 ( ambos incluidos)
					numeroSecreto = (int) (Math.random() * 20) + 1;
					System.out.println("Por favor introduzca un numero (1-20):");
					numeroIntroducido = Math.abs(sc.nextInt());
				} catch (InputMismatchException a) {
					System.out.println(a);
					System.out.println("Error, no es un número");
				}

			}
			intentosRestantes--;
			System.out.println("Número incorrecto, introduzca otro número:");
			// realizamos bucle mientras el numero secreto no coincida con el introducido y
			// si quedan intentos restantes
		} while ((numeroSecreto != numeroIntroducido) & (intentosRestantes > 0));

	}
	
	@Override
	// el costeApuesta descuenta los Pokedóllares al entrenador
	public void costeApuesta(int apuesta) {
		Entrenador entrenador = getEntrenador();
		// si el entrenador existe le descontamos la cantidad de la apuesta
		if (entrenador != null) {
			entrenador.setPokedollares(entrenador.getPokedollares() - 100);
		}
	}

	@Override
	// la cantidadPremio suma los Pokedóllares al entrenador
	public void cantidadPremio(int apuesta) {
		Entrenador entrenador = getEntrenador();
		// comprobamos que exista el entrenador
		if (entrenador != null) {
			// y dependiendo del numero de intentos damos una u otra recompensa
			if (intentosRestantes == 5) {
				entrenador.setPokedollares(entrenador.getPokedollares() + 1000);
			}
			else if(intentosRestantes == 4){
				entrenador.setPokedollares(entrenador.getPokedollares() + 750);
				System.out.println("Enhorabuena has ganado 750 Pokedóllares");
			}
			else if(intentosRestantes == 3){
				entrenador.setPokedollares(entrenador.getPokedollares() + 500);
				System.out.println("Enhorabuena has ganado 500 Pokedóllares");
			}
			else if(intentosRestantes == 2){
				entrenador.setPokedollares(entrenador.getPokedollares() + 250);
				System.out.println("Enhorabuena has ganado 250 Pokedóllares");
			}
			else{
				entrenador.setPokedollares(entrenador.getPokedollares() + 0);
			}
		}
	}

}
