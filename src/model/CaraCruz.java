package model;

/**
 * Class CaraCruz
 */
public class CaraCruz extends Casino {
    // enum para la eleccion del jugador
    public enum Eleccion {
        CARA, CRUZ
    }

    private Eleccion resultadoUltimoLanzamiento;

    // Constructor defecto
    public CaraCruz() {
        super();
    }

    // Constructor con entrenador
    public CaraCruz(Entrenador entrenador) {
        super(entrenador);
    }

    // Constructor copia
    public CaraCruz(CaraCruz cc) {
        super(cc);
    }

    // getters setters
    public Eleccion getResultadoUltimoLanzamiento() {
        return resultadoUltimoLanzamiento;
    }

    // metodo para jugar el cara o cruz
    public String jugarMoneda(Eleccion eleccionJugador, int apuesta) {
        Entrenador entrenador = getEntrenador();
        // comprobamos que haya entrenador
        if (entrenador == null) {
            return "No se encuentra al entrenador.";
        }
        // comprobamos si la apuesta es menor o igual a cero
        if (apuesta <= 0) {
            return "La apuesta debe ser mayor que 0.";
        }
        // comprobamos si tiene Pokedóllares el entrenador
        if (!puedeApostar(apuesta)) {
            return "Pokedóllares insuficientes.";
        }

        // una vez se cumplan que puede apostar se le descuentan los Pokedóllares al
        // entrenador
        costeApuesta(apuesta);
        // ramdom para el 50%
        int num = (int) (Math.random() * 2);

        // guardamos el resultado en la variable de clase para que el controlador la vea 
        if (num == 0) {
            this.resultadoUltimoLanzamiento = Eleccion.CARA;
        } else {
            this.resultadoUltimoLanzamiento = Eleccion.CRUZ;
        }

        // comprobamos si coincide con la eleccion del jugador
        if (this.resultadoUltimoLanzamiento == eleccionJugador) {
            cantidadPremio(apuesta);
            return "Ha salido " + this.resultadoUltimoLanzamiento + ". ¡Has ganado! Ahora tienes: " + entrenador.getPokedollares()
                    + " Pokedólares";
        } else {
            return "Ha salido " + this.resultadoUltimoLanzamiento + ". ¡Has perdido! Ahora tienes: " + entrenador.getPokedollares()
                    + " Pokedólares";
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
    // la cantidadPremio suma los Pokedóllares al entrenador
    public void cantidadPremio(int apuesta) {
        Entrenador entrenador = getEntrenador();
        // si el entrenador existe y la apuesta es mayor a 0 le entregamos sus ganacias
        if (entrenador != null && apuesta > 0) {
            entrenador.setPokedollares(entrenador.getPokedollares() + (apuesta * 2));
        }
    }

}
