package model;


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
   * Id del entrenador */
  private int idEntrenador;

  /**
   * todos los objetos que tiene el entrenador disponible
   */
  private List<ObjetoInventario> listaObjetos;
  
  //
  // Constructors
  //
  public Inventario (int idEntrenador) {
    this.idEntrenador = idEntrenador;
    listaObjetos = new ArrayList<>();
  };


  // Getters y Setters

  public int getIdEntrenador() {
    return idEntrenador;
  }

  public void setIdEntrenador(int idEntrenador) {
    this.idEntrenador = idEntrenador;
  }

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
   * Method para añadir objetos al inventario, y su
   * */
  public void añadirObjeto(Objeto objeto, int cantidad) {
    listaObjetos.add(new ObjetoInventario(objeto, cantidad));
  }




}
