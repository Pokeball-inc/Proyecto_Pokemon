package model;


import dao.InventarioDAO;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

/**
 * Class Inventario
 */
public class Inventario {

    //
    // Fields
    //


    /**
     * todos los objetos que tiene el entrenador disponible
     */
    private List<ObjetoInventario> listaObjetos;

    //
    // Constructors
    //
    public Inventario() {
        listaObjetos = new ArrayList<>();
    }

    ;


    // Getters y Setters


    public List<ObjetoInventario> getListaObjetos() {
        return listaObjetos;
    }

    public void setListaObjetos(List<ObjetoInventario> listaObjetos) {
        this.listaObjetos = listaObjetos;
    }


    //
    // Methods
    //

    /**
     * Method para añadir objetos al inventario, y la cantidad
     *
     */
    public void añadirObjeto(Objeto objeto, int cantidad) {

        // Recorremos toda la lista de objetos

        for (ObjetoInventario objetoInventario : listaObjetos) {

            // Si detectamos que el objeto que queremos añadir, ya existe, entonces simplemente le sumamos la cantidad

            if (objetoInventario.getObjeto().getIdObjeto() == objeto.getIdObjeto()) {
                objetoInventario.setCantidad(objetoInventario.getCantidad() + cantidad);
                return;
            }
        }
        // Si no se detecta nada arriba (pq se sale del bucle), añadir a la lista

        listaObjetos.add(new ObjetoInventario(objeto, cantidad));

    }

    /**
     * toString del inventario
     */

    @Override
    public String toString() {
        return "Inventario{" +
                "listaObjetos=" + String.valueOf(listaObjetos) +
                '}';
    }
}
