package model;

public enum TipoPokeBall {

    //Tipos de pokerball que afectan al ratio de captura
    POKEBALL(1.0),
    SUPERBALL(1.5),
    ULTRABALL(2.0),
    MASTERBALL(255.0);

    private final double multiplicador;

    TipoPokeBall(double multiplicador) {
        this.multiplicador = multiplicador;
    }

    //Getter
    public double getMultiplicador() {
        return multiplicador;
    }


}
