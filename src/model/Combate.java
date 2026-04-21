package model;

import java.util.ArrayList;
import java.util.List;

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
   * @param        entrenador
   * @param        entrenadorRival
   */
  public void empezarCombate(Entrenador jugador, Entrenador rival){
	  this.setEntrenador(jugador);
	    this.setEntrenadorRival(rival);
	    
	    // Buscamos el primer pokemon vivo de cada uno 
	    this.setPokemonActualJugador(buscarPrimerPokemonVivo(jugador));
	    this.setPokemonActualRival(buscarPrimerPokemonVivo(rival));
	    
	    // verificamos que ambos tengan alguien para pelear
	    if (this.getPokemonActualJugador() != null && this.getPokemonActualRival() != null) {
	        System.out.println("¡Combate listo! Sale " + this.getPokemonActualJugador().getNombrePokemon());
	    } else {
	        System.out.println("Error: Uno de los entrenadores no tiene Pokemon listos.");
	    }
  }
  
  /**
   * Metodo para encontrar al primer pokemon vivo
   * @param e, El entrenador,jugador o rival
   * @return El primer Pokemon con vitalidad > 0
   */
  private Pokemon buscarPrimerPokemonVivo(Entrenador e) {
      // Recorremos el array de 6 pokemon del equipo
      for (Pokemon p : e.getEquipoPokemon()) {
          // Si el hueco no está vacío y el pokemon tiene más de 0 de vida
          if (p != null && p.getVitalidad() > 0) {
              return p; // Devolvemos este pokemon para que sea el pokemonActual
          }
      }
      return null; // Si todos están debilitados, devuelve null
  }


  /**
   * metodo para retirarse del combate
   */
  public void retirarse()
  {
  }


  /**
   * metodo para finalizar combate
   */
  public void finalizarCombate()
  {
  }
  
  /**
   * metodo para añadir turno al historial
   */
  public void añadirTurno(Turno nuevoTurno) {
      this.historialTurnos.add(nuevoTurno);
  }


}
