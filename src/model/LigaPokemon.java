package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Class LigaPokemon
 */
public class LigaPokemon {

  //
  // Fields
  //

	/**
     * nuestro entrenador
     */
    private Entrenador jugador;
  /**
   * lista de entrenadores de la liga
   */
  private List<Entrenador> altoMando;
  
  /**
   * nuestra copia
   */
  private Entrenador Doppelganger;
  
  /**
   * para ver en qeu combate estamos
   */
  private int combateActual;
  
  /**
   * comprobacion descanso
   */
  private boolean haDescansadoAntes;
  
  
  //
  // Constructors
  //
  public LigaPokemon() {
      this.altoMando = new ArrayList<>();
      this.setCombateActual(1);
      this.setHaDescansadoAntes(false);
  }
  
  //
  // Methods
  //

  public Entrenador getJugador() {
	return jugador;
}

  public void setJugador(Entrenador jugador) {
	this.jugador = jugador;
  }

  public List<Entrenador> getAltoMando() {
	return altoMando;
}

  public void setAltoMando(List<Entrenador> altoMando) {
	this.altoMando = altoMando;
  }
  

  public Entrenador getDoppelganger() {
	return Doppelganger;
}

  public void setDoppelganger(Entrenador doppelganger) {
	Doppelganger = doppelganger;
  }

  public int getCombateActual() {
	return combateActual;
}

  public void setCombateActual(int combateActual) {
	this.combateActual = combateActual;
  }

  public boolean isHaDescansadoAntes() {
	return haDescansadoAntes;
}

  public void setHaDescansadoAntes(boolean haDescansadoAntes) {
	this.haDescansadoAntes = haDescansadoAntes;
  }

  //
  // Other methods
  //
  
  /**
   * preparacion de la liga
   */
  public void iniciarLiga() {
      if (this.altoMando != null && !this.altoMando.isEmpty()) {
          // desordenamos la lista para cada inicio
          Collections.shuffle(this.altoMando);
      }
      this.combateActual = 1;
      this.haDescansadoAntes = false;
      System.out.println("¡Comienza el desafío de la Liga Pokémon!");
  }
  
  /**
   * obtenemos el rival que toca
   * @return Entrenador rival, del alto mando si es 1-5 o el doppelganger si es 6
   */
  public Entrenador obtenerRivalActual() {
      // del combate 1 al 5 son alto mando
      if (combateActual >= 1 && combateActual <= 5) {
          return altoMando.get(combateActual - 1);
      } 
      // el combate 6 es contra doppelganger
      else if (combateActual == 6) {
          return Doppelganger;
      }
      return null;
  }
  
  
  /**
   * metodo para calcular las ganancias o perdidas de los pokedolares en la liga
   * @param victoria true si el jugador ha ganado y  false si ha perdido
   */
  public void gananciasPokedolares(boolean victoria)
  {
	  if (jugador == null) {
		  return;
	  }

      // aplicamos la pennalizacion si se pierde
      if (!victoria) {
          int penalizacion = jugador.getPokedollares() / 2;
          jugador.setPokedollares(jugador.getPokedollares() - penalizacion);
          System.out.println("Has perdido en la Liga Pokémon. Se te han descontado " + penalizacion + " Pokedóllares.");
          return;
      }

      // recompensas basadas en el combate en que esta y el descanso
      int recompensa = 0;
      
      switch (this.combateActual) {
      case 1:
          recompensa = 1000; 
          break;
      case 2:
          if (haDescansadoAntes) {
              recompensa = 1000;
          } else {
              recompensa = 2000;
          }
          break;
      case 3:
          if (haDescansadoAntes) {
              recompensa = 2000;
          } else {
              recompensa = 4000;
          }
          break;
      case 4:
          if (haDescansadoAntes) {
              recompensa = 3000;
          } else {
              recompensa = 6000;
          }
          break;
      case 5:
          if (haDescansadoAntes) {
              recompensa = 4000;
          } else {
              recompensa = 8000;
          }
          break;
      case 6: 
          if (haDescansadoAntes) {
              recompensa = 5000;
          } else {
              recompensa = 10000;
          }
          break;
  }

      // sumamos el dinero al jugador
      jugador.setPokedollares(jugador.getPokedollares() + recompensa);
      System.out.println("¡Has vencido en el combate " + combateActual + "! Ganas " + recompensa + " Pokedóllares.");
  }
  
  /**
   * para avanzar al siguiente combate y resetea el haDescansadoAntes
   */
  public void avanzarSiguienteCombate() {
      this.combateActual++;
      this.haDescansadoAntes = false;
  }


  /**
   * metodo para recuperar la vida de los pokemon, entre combate y combate
   */
  public void recuperacionPokemon(){
	  if (jugador != null) {
          
          // curamos 
          jugador.curarEquipoCompleto();
          
          // activamos que hemos curado 
          this.haDescansadoAntes = true;
          System.out.println("Has usado el descanso. La recompensa del próximo combate será reducida al 50%.");
      }
  }


}
