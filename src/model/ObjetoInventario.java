package model;

/**
 * Esta clase es para permitir la "cantidad" en los objetos */
public class ObjetoInventario {

    // Atributos
    private Objeto objeto;
    private int cantidad;

    // Constructores

    public ObjetoInventario(Objeto objeto, int cantidad) {
        this.objeto = objeto;
        this.cantidad = cantidad;
    }

    // Getters y Setters

    public Objeto getObjeto() {
        return objeto;
    }
    public void setObjeto(Objeto objeto) {}

    public int getCantidad() {
        return cantidad;
    }
    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

}
