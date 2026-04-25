package model;

/**
 * Class Casino
 */
abstract public class Casino {

    //
    // Fields
    //

    private Entrenador entrenador;

    //
    // Constructor defecto
    //
    public Casino() {
    }

    ;

    // Constructor con entrenador
    public Casino(Entrenador entrenador) {
        this.entrenador = entrenador;
    }

    // Constructor copia
    public Casino(Casino c) {
        this.entrenador = c.entrenador;
    }

    //
    // Methods
    //

    public void setEntrenador(Entrenador entrenador) {
        this.entrenador = entrenador;
    }

    public Entrenador getEntrenador() {
        return entrenador;
    }

    //
    // Other methods
    //

    /**
     * metodo para el coste de la apuesta
     */
    public abstract void costeApuesta(int apuesta);

    /**
     * metodo par calcular la cantidad del premio si se gana la apuesta
     *
     */
    public abstract void cantidadPremio(int apuesta);

    // metodo para comprobar si el entrenador tiene dinero para apostar
    public boolean puedeApostar(int cantidad) {
        return entrenador != null && cantidad > 0 && entrenador.getPokedollares() >= cantidad;
    }

}
