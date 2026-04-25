package model;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Class AdivinarNumero
 */
public class AdivinarNumero extends Casino {
    /**
     *
     * numero entre 1 y 20 para adivinar 5 intentos
     */
    private static final int APUESTACOSTEFIJO = 150;
    private int numeroSecreto;
    /**
     * numero introducido por el usuario
     */
    private int numeroIntroducido;
    /**
     * numeor de intentos maximo 5
     */
    private int intentosRestantes;

    // si acierta pasa a verdadero
    private boolean acertado;

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

    public void setNumeroSecreto(int numero) {
        numeroSecreto = numero;
    }

    public int getNumeroSecreto() {
        return numeroSecreto;
    }

    public void setNumeroIntroducido(int numero) {
        numeroIntroducido = numero;
    }

    public int getNumeroIntroducido() {
        return numeroIntroducido;
    }

    public void setIntentosRestantes(int numero) {
        intentosRestantes = numero;
    }

    public int getIntentosRestantes() {
        return intentosRestantes;
    }

    public void setacertado(boolean bool) {
        acertado = bool;
    }

    public boolean getAcertado() {
        return acertado;
    }
    //
    // Other methods
    //

    /**
     * la mecanica de adivinar el numero, intentos maximos 5
     */
    public void adivinarNumero() {
        Scanner sc = new Scanner(System.in);

        Entrenador entrenador = getEntrenador();
        // comprobamos que haya entrenador
        if (entrenador == null) {
            System.out.println("No se encuentra al entrenador.");
            return;
        }

        // comprobamos si tiene Pokedóllares el entrenador
        if (!puedeApostar(APUESTACOSTEFIJO)) {
            System.out.println("Pokedóllares insuficientes.");
            return;
        }

        // cobramos la apuesta
        costeApuesta(APUESTACOSTEFIJO);

        // ramdom de 1 a 20 ( ambos incluidos)
        this.numeroSecreto = (int) (Math.random() * 20) + 1;
        // establecemos en 5 los intentos restantes
        this.intentosRestantes = 5;
        // y el boolean acertado en false
        this.acertado = false;

        // empezamos el bucle
        do {
            System.out.println("Intentos restantes: " + intentosRestantes);
            System.out.println("Por favor introduzca un numero (1-20):");

            // try catch por si no se introduce un numero
            try {
                this.numeroIntroducido = Math.abs(sc.nextInt());
                // comrpobamos sia certo
                if (numeroIntroducido == numeroSecreto) {
                    acertado = true;
                } else {
                    intentosRestantes--;
                    if (intentosRestantes > 0) {
                        System.out.println("¡Número incorrecto! Sigue probando suerte");
                    }
                }
            } catch (InputMismatchException a) {
                System.out.println("Error, no es un número válido.");
                sc.next();
            }
            // realizamos bucle mientras el numero secreto no coincida con el introducido y
            // si quedan intentos restantes
        } while (!acertado && (intentosRestantes > 0));

        // cuando gane o agote intentos mostramos uno u otor mensaje y ñe pàgamos si
        // gana
        if (acertado) {
            System.out.println("¡Enhorabuena! has acertado el número: " + numeroSecreto);
            cantidadPremio(APUESTACOSTEFIJO);
        } else {
            System.out.println("Has perdido, el número era: " + numeroSecreto);
        }

    }

    @Override
    // el costeApuesta descuenta los Pokedóllares al entrenador
    public void costeApuesta(int apuesta) {
        Entrenador entrenador = getEntrenador();
        // si el entrenador existe le descontamos la cantidad de la apuesta
        if (entrenador != null) {
            entrenador.setPokedollares(entrenador.getPokedollares() - apuesta);
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
                System.out.println("Enhorabuena has ganado 1000 Pokedóllares");
            } else if (intentosRestantes == 4) {
                entrenador.setPokedollares(entrenador.getPokedollares() + 750);
                System.out.println("Enhorabuena has ganado 750 Pokedóllares");
            } else if (intentosRestantes == 3) {
                entrenador.setPokedollares(entrenador.getPokedollares() + 500);
                System.out.println("Enhorabuena has ganado 500 Pokedóllares");
            } else if (intentosRestantes == 2) {
                entrenador.setPokedollares(entrenador.getPokedollares() + 250);
                System.out.println("Enhorabuena has ganado 250 Pokedóllares");
            } else {
                entrenador.setPokedollares(entrenador.getPokedollares() + 0);
            }
        }
    }

}
