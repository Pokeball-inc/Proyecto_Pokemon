package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Class Combate
 */
public class Combate {

  //
  // Fields
  //

  /**
   * el id de la clase combate
   */
  private int idCombate = 1;
  /**
   * entrenador en la clase combate (el jugador)
   */
  private Entrenador entrenador;
  /**
   * entrenador rival en combate
   */
  private Entrenador entrenadorRival;
  
  // pokemon actual nuestro
  private Pokemon pokemonActualJugador;
  
  // pokemon actual del rival
  private Pokemon pokemonActualRival;
  /**
   * cada turno del combate
   */
  private List<Turno> historialTurnos = new ArrayList<>();

  /**
   * pokemon derrotados el jugador
   */
  private int pokemonKOEntrenador = 0;
  /**
   * pokemon debilitados del rival
   */
  private int pokemonKORival = 0;
  
  //
  // Constructors
  //
  public Combate () { };
  
  //
  // Methods
  //

  public void setIdCombate (int newVar) {
    idCombate = newVar;
  }


  public int getIdCombate () {
    return idCombate;
  }

  public void setEntrenador (Entrenador newVar) {
    entrenador = newVar;
  }

  public Entrenador getEntrenador () {
    return entrenador;
  }

  public void setEntrenadorRival (Entrenador newVar) {
    entrenadorRival = newVar;
  }

  public Entrenador getEntrenadorRival () {
    return entrenadorRival;
  }
  
  /**
   * Establece el Pokémon que el jugador saca a pelear.
   */
  public void setPokemonActualJugador(Pokemon pokemonActualJugador) {
      this.pokemonActualJugador = pokemonActualJugador;
  }

  /**
   * Obtiene el Pokémon que el jugador tiene actualmente en el campo.
   */
  public Pokemon getPokemonActualJugador() {
      return pokemonActualJugador;
  }
  
  /**
   * Establece el Pokémon que el rival saca a pelear.
   */
  public void setPokemonActualRival(Pokemon pokemonActualRival) {
      this.pokemonActualRival = pokemonActualRival;
  }

  /**
   * Obtiene el Pokémon que el rival tiene actualmente en el campo.
   */
  public Pokemon getPokemonActualRival() {
      return pokemonActualRival;
  }

  /**
   * Permite asignar un historial completo de turnos.
   */
  public void setHistorialTurnos(List<Turno> historialTurnos) {
      this.historialTurnos = historialTurnos;
  }
  
  /**
   * Obtiene la lista de todos los turnos ocurridos (útil para guardar en la BD).
   */
  public List<Turno> getHistorialTurnos() {
      return historialTurnos;
  }


  public void setPokemonKOEntrenador (int newVar) {
    pokemonKOEntrenador = newVar;
  }

  public int getPokemonKOEntrenador () {
    return pokemonKOEntrenador;
  }

  public void setPokemonKORival (int newVar) {
    pokemonKORival = newVar;
  }

  public int getPokemonKORival () {
    return pokemonKORival;
  }

  //
  // Other methods
  //

  /**
   * metodo para iniciar el combate
   * @param entrenador, el entrenador jugador, nosotros
   * @param entrenadorRival, el bot
   */
  public void empezarCombate(Entrenador jugador, Entrenador rival){
	  this.setEntrenador(jugador);
	    this.setEntrenadorRival(rival);
	    
	    Pokemon pJugador = buscarPrimerPokemonVivo(jugador);
	    Pokemon pRival = buscarPrimerPokemonVivo(rival);

	    if (pJugador != null && pRival != null) {
	        // reseteamos turnos estado
	        pJugador.setTurnosEstadoRestantes(0);
	        pRival.setTurnosEstadoRestantes(0);
	        
	        this.setPokemonActualJugador(pJugador);
	        this.setPokemonActualRival(pRival);
	        System.out.println("¡Combate listo!");
	    }
  }
  
  /**
   * metodo para encontrar al primer pokemon vivo
   * @param e, el entrenador,jugador o rival
   * @return el primer Pokemon con vitalidad > 0
   */
  private Pokemon buscarPrimerPokemonVivo(Entrenador e) {
      // recorremos el array de 6 pokemon del equipo
      for (Pokemon p : e.getEquipoPokemon()) {
          // si el hueco no está vacio y el pokemon tiene más de 0 de vida
          if (p != null && p.getVitalidad() > 0) {
              return p; // devolvemos este pokemon para que sea el pokemonActual
          }
      }
      return null; // si todos están debilitados, devuelve null
  }
  
  
  
  /**
   * cambia el pokemon actual del jugador por otro del equipo.
   * @param indice El número del hueco del equipo, 0 a 5 por el array de 6 del equipo
   * @return true si el cambio fue posible, false si el pokemon elegido no tiene vida
   */
  public boolean cambiarPokemonJugador(int indice) {
      Pokemon elegido = this.getEntrenador().getEquipoPokemon()[indice];
      
      // verificamos que el hueco no sea nulo y que tenga vida
      if (elegido != null && elegido.getVitalidad() > 0) {
    	  // reseteamos los turnos de estado
    	  elegido.setTurnosEstadoRestantes(0);
          this.setPokemonActualJugador(elegido);
          System.out.println("¡Has enviado a " + elegido.getNombrePokemon() + " a la batalla!");
          return true;
      } else {
          System.out.println("No puedes elegir a ese Pokémon.");
          return false;
      }
  }

  /**
   * metodo para aplicar los efectos que se aplican a final de turno
   * @param recibe el pokemon nuestro y el rival por parametro
   * */
  public void aplicarEfectosFinalDeTurno(Pokemon p, Pokemon rival) {
     if (p == null || p.getVitalidad() <= 0) return;
     // obtenemos la vida maxima para usarla 
     int psTotales = p.getVitalidadMaxima();

     switch (p.getEstadoActual()) {
         case QUEMADO:
             // pierde 1/16 de sus PS totales
             p.setVitalidad(p.getVitalidad() - (psTotales / 16));
             break;

         case ENVENENADO:
             // pierde 1/8 de sus PS totales
             p.setVitalidad(p.getVitalidad() - (psTotales / 8));
             break;

         case GRAVEMENTEENMVENEDADO:
             // daño aumenta en 1/16 cada turno (TODO usar turnosEstadoRestantes para el contador)
             int turnos = p.getTurnosEstadoRestantes() + 1;
             p.setTurnosEstadoRestantes(turnos);
             p.setVitalidad(p.getVitalidad() - (psTotales * turnos / 16));
             break;

         case HELADO:
             // pierde 1/16 de sus PS totales
             p.setVitalidad(p.getVitalidad() - (psTotales / 16));
             break;

         case MALDITO:
             // pPierde 1/4 de sus PS totales
             p.setVitalidad(p.getVitalidad() - (psTotales / 4));
             break;

         case DRENADORAS:
             // pierde 1/8 y los recupera el rival
             int robo = psTotales / 8;
             p.setVitalidad(p.getVitalidad() - robo);
             // Si el rival existe, recupera la vida robada
             if (rival != null) {
                 rival.setVitalidad(rival.getVitalidad() + robo);
             }
             break;

         case CANTOMORTAL:
             // se debilita a los 3 turnos
             p.setTurnosEstadoRestantes(p.getTurnosEstadoRestantes() + 1);
             if (p.getTurnosEstadoRestantes() >= 3) {
                 p.setVitalidad(0);
                 p.setEstadoActual(Estados.DEBILITADO);
             }
             break;
             
         case DORMIDO:
             // Se despertara despues de 1-3 turnos
             p.setTurnosEstadoRestantes(p.getTurnosEstadoRestantes() - 1);
             if (p.getTurnosEstadoRestantes() <= 0) {
                 p.setEstadoActual(Estados.SANO);
                 System.out.println(p.getNombrePokemon() + " se ha despertado.");
             }
             break;

         default:
             break;
     }
  }

/**
 * Comprueba si el estado actual del Pokémon le permite realizar un movimiento.
 * @param recibe el pokemon afectado cvomo parametro
 * @return true si puede atacar, false si el estado lo impide
 * */
public boolean puedeAtacarEsteTurno(Pokemon p) {
	Random r = new Random();
    Estados estado = p.getEstadoActual();

    switch (estado) {
        case PARALIZADO:
            // 12,5% de probabilidad de no atacar
            if (r.nextDouble() < 0.125) {
                System.out.println(p.getNombrePokemon() + " está paralizado y no puede moverse.");
                return false;
            }
            break;

        case DORMIDO:
            // no puede atacar mientras duerme
            System.out.println(p.getNombrePokemon() + " está profundamente dormido...");
            return false;

        case CONGELADO:
            // 20% de probabilidad de descongelarse
            if (r.nextDouble() < 0.20) {
                p.setEstadoActual(Estados.SANO);
                System.out.println("¡" + p.getNombrePokemon() + " se ha descongelado!");
                return true;
            }
            System.out.println(p.getNombrePokemon() + " está congelado y no puede atacar.");
            return false;

        case AMEDENTRADO:
            // no puede atacar este turno, luego se cura solo
            System.out.println(p.getNombrePokemon() + " ha retrocedido y no puede atacar.");
            p.setEstadoActual(Estados.SANO); 
            return false;

        case SOMNOLIENTO:
            // 50% de probabilidad de no atacar
            if (r.nextDouble() < 0.50) {
                System.out.println(p.getNombrePokemon() + " tiene mucho sueño y no puede atacar.");
                return false;
            }
            break;

        case CONFUSO:
            // 1/3 de probabilidad de herirse a sí mismo
            if (r.nextInt(3) == 0) {
                int danio = p.getVitalidadMaxima() / 10; // Daño por confusión (10% vida max)
                p.setVitalidad(p.getVitalidad() - danio);
                System.out.println("¡" + p.getNombrePokemon() + " está tan confuso que se ha herido a sí mismo!");
                return false;
            }
            break;

        case ENAMORADO:
            // 1/4 de probabilidad de no atacar
            if (r.nextInt(4) == 0) {
                System.out.println(p.getNombrePokemon() + " está enamorado y no puede atacar.");
                return false;
            }
            break;

        case DEBILITADO:
            return false;
            
        default:
            return true;
    }
    return true;
}



/**
 * metodo para procesar un turno completo de combate decidiendo quien ataca primero en base a la  velocidad
 *  validacion de estados, ejecucion de daño y registro en el log
 * @param movJugador el movimiento elegido por el usuario
 * @param movRival el movimiento elegido por la "IA"
 */
public void procesarTurno(Movimiento movJugador, Movimiento movRival) {
    // creamos el Turno para registrar lo que pasara
    Turno turnoActual = new Turno();
    turnoActual.setNumeroTurnoActual(this.getHistorialTurnos().size() + 1);

    // decidimos el orden de actuacion segun la velocidad de los pokemon
    Pokemon primero, segundo;
    Movimiento movPrimero, movSegundo;
    
    boolean jugadorVaPrimero = this.getPokemonActualJugador().getVelocidad() >= this.getPokemonActualRival().getVelocidad();

    if (jugadorVaPrimero) {
        primero = this.getPokemonActualJugador();
        movPrimero = movJugador;
        segundo = this.getPokemonActualRival();
        movSegundo = movRival;
    } else {
        primero = this.getPokemonActualRival();
        movPrimero = movRival;
        segundo = this.getPokemonActualJugador();
        movSegundo = movJugador;
    }

    // el primer pokemon intenta atacar
    String res1 = ejecutarAccion(primero, segundo, movPrimero);
    
    // el segundo pokemon intenta atacar solo si sigue vivo tras el primer movimiento
    String res2 = "";
    if (segundo.getVitalidad() > 0) {
        res2 = ejecutarAccion(segundo, primero, movSegundo);
    } else {
        res2 = segundo.getNombrePokemon() + " se encuentra debilitado.";
    }

    // aplicamos los efectos de estados al final del turno
    aplicarEfectosFinalDeTurno(this.getPokemonActualJugador(), this.getPokemonActualRival());
    aplicarEfectosFinalDeTurno(this.getPokemonActualRival(), this.getPokemonActualJugador());

    // guardamos los logs en el objeto turnoActual para el historial
    if (jugadorVaPrimero) {
        turnoActual.setAccionEntrenador(res1);
        turnoActual.setAccionEntrenadorRival(res2);
    } else {
        turnoActual.setAccionEntrenador(res2);
        turnoActual.setAccionEntrenadorRival(res1);
    }
    
    this.añadirTurno(turnoActual);
}



/**
 * metodo para validar estados antes de atacar y ejecutar el daño
 * @param atacante pokemon que intenta realizar el movimiento
 * @param objetivo pokemon que recibe el posible impacto
 * @param mov movimiento a realizar
 * @return el mensaje de texto de lo ocurrido para el historial
 */
private String ejecutarAccion(Pokemon atacante, Pokemon objetivo, Movimiento mov) {
    // comprobamos si puede atacar
    if (this.puedeAtacarEsteTurno(atacante)) {
        // si puede atacar, realizamos el daño y obtenemos la eficacia 
        String eficacia = atacante.atacar(mov, objetivo);
        return atacante.getNombrePokemon() + " usó " + mov.getNombreMovimiento() + " (" + eficacia + ")";
    } else {
        // si no puede atacar, devolvemos el motivo para el log del turno
        return atacante.getNombrePokemon() + " no pudo atacar debido a su estado: " + atacante.getEstadoActual();
    }
}























  
  /**
   * metodo para tras la muerte de un pokemon  preparar el cambio.
   * @param esJugador, true si el pokemon muerto es el nuestro
   * @return true, si al entrenador le quedan mas pokemon para seguir peleando
   */
  public boolean puedeContinuar(boolean esJugador) {
      if (esJugador) {
          // aumentamos el contador de KOs del jugador
          this.setPokemonKOEntrenador(this.getPokemonKOEntrenador() + 1);
          
          // comprobamos si queda alguien vivo en el equipo
          return buscarPrimerPokemonVivo(this.getEntrenador()) != null;
      } else {
          // logica para el rival, que sacara el primero vivo
          this.setPokemonKORival(this.getPokemonKORival() + 1);
          Pokemon siguienteRival = buscarPrimerPokemonVivo(this.getEntrenadorRival());
          
          if (siguienteRival != null) {
              this.setPokemonActualRival(siguienteRival);
              return true;
          }
          return false;
      }
  }

  
  /**
   * metodo para calcular y transferir los Pokedollares del perdedor al ganador
   * @param ganador ,el entrenador que ha gando
   * @param perdedor, el entrenador que ha eprdido
   */
  public void transferirPokedollares(Entrenador ganador, Entrenador perdedor) {
      // calculamos el tercio del dinero del perdedor
      int botin = perdedor.getPokedollares() / 3;
      
      // actualizamos los  Pokedollares de ambos
      perdedor.setPokedollares(perdedor.getPokedollares() - botin);
      ganador.setPokedollares(ganador.getPokedollares() + botin);
      
      System.out.println("¡Botín de combate! " + ganador.getNombreEntrenador() + 
                         " ha ganado " + botin + " Pokedollares.");
  }
  
  /**
   * metodo que reparte experiencia a todo el equipo respecto al nivel del rival derrotado
   */
  public void repartirExperienciaEquipo(Pokemon rivalDerrotado) {
      Pokemon[] equipo = this.getEntrenador().getEquipoPokemon();
      // recorremos el equipo y por cada pokemon
      for (Pokemon p : equipo) {
          if (p != null) {
              // formula del enunciado :D
              int expGanada = (p.getNivel() + (rivalDerrotado.getNivel() * 10)) / 4;

              // añadimos la experiencia ganada generada por la formula
              p.ganarExperiencia(expGanada);
          }
      }
  }

  /**
   * metodo para retirarse del combate
   */
  public void retirarse(){System.out.println("EL JUGADOR SE HA RETIRADO");
  
  // sumamos la derrota al marcador
  this.getEntrenador().sumarDerrota();
  
  // transferimos los Pokedollares al rival
  transferirPokedollares(this.getEntrenadorRival(), this.getEntrenador());
  
  System.out.println("Has abandonado el combate. Has perdido " + 
                     (this.getEntrenador().getNombreEntrenador()) + " Pokedollares.");
  }


  /**
   * metodo para finalizar combate y establecer ganador
   */
  public void finalizarCombate(){
	  System.out.println("EL COMBATE HA FINALIZADO");
      
      // si ganamos nosotros
      if (this.getPokemonKORival() >= 6 || buscarPrimerPokemonVivo(this.getEntrenadorRival()) == null) {
            
          System.out.println("¡ENHORABUENA! Has ganado el combate.");
          this.getEntrenador().sumarVictoria();
            
          //  hhacemos el reparto de Pokedollares
          transferirPokedollares(this.getEntrenador(), this.getEntrenadorRival());
            
          // añadimos la xp a todo el equipo en base al nivel del ultimo pokemon del rival
          if (this.getPokemonActualRival() != null) {
              repartirExperienciaEquipo(this.getPokemonActualRival());
          }

      } 
      // si gana el rival
      else {
          System.out.println("¡DERROTA! Te has quedado sin Pokémon.");
          this.getEntrenador().sumarDerrota();
            
          // añadimos el dienro al rival
          transferirPokedollares(this.getEntrenadorRival(), this.getEntrenador());
      }
  }
  
  /**
   * metodo para añadir turno al historial
   */
  public void añadirTurno(Turno nuevoTurno) {
      this.historialTurnos.add(nuevoTurno);
  }


}
