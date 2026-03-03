
import java.util.*;


/**
 * Class Entrenador
 */
public class Entrenador {

  //
  // Fields
  //
  /**

   * el identificador del entrenador
   *    */

  private int idEntrenador = 1;
  /**
   * nombre del entrenador
   */
  private String nombreEntrenador;
  /**
   * es la contraseña del entrenador jugador
   */
  private String contrasena;
  /**
   * genero del entrenador
   */
  private Generos genero;  /**

   * ciudad natal del entrenador
   *    */

  private String ciudadOrigen;  /**

   * tiempo de juego del entrenador
   * comprobar  como almacenarlo  y guardarlo   */

  private Long tiempoJuego;  /**

   * dinero del jugador, su unidad es Pokedollares
   * empieza con 800-1000 pokedolarres   */

  private int pokedollares;
  /**
   * los Pokemon del equipo
   */
  private Pokemon[] equipoPokemon;
  /**
   * los Pokemon capturados que no estan en el equipo
   */
  private Pokemon[] cajaPokemon;
  /**
   * victorias del entrenador
   */
  private int victorias = 0;
  /**
   * derrotas del entrenador
   */
  private int derrotas = 0;
  /**
   * mochila con los objetos del entrenador
   */
  private Objeto[] inventario;
  /**
   * para saber si es entrenador del juego o de un jugador
   */
  private Boolean esNPC;
  /**
   * para el spray frontal del entrenador
   */
  private String imgFrontalEntrenador;
  /**
   * para el spray posterior del entrenador
   */
  private String imgPosteriorEntrenador;
  
  //
  // Constructors
  //
  public Entrenador () { };
  
  //
  // Methods
  //


  //
  // Accessor methods
  //

  /**
   * Set the value of idEntrenador
   * el identificador del entrenador
   * 
   * @param newVar the new value of idEntrenador
   */
  public void setIdEntrenador (int newVar) {
    idEntrenador = newVar;
  }

  /**
   * Get the value of idEntrenador
   * el identificador del entrenador
   * 
   * @return the value of idEntrenador
   */
  public int getIdEntrenador () {
    return idEntrenador;
  }

  /**
   * Set the value of nombreEntrenador
   * nombre del entrenador
   * @param newVar the new value of nombreEntrenador
   */
  public void setNombreEntrenador (String newVar) {
    nombreEntrenador = newVar;
  }

  /**
   * Get the value of nombreEntrenador
   * nombre del entrenador
   * @return the value of nombreEntrenador
   */
  public String getNombreEntrenador () {
    return nombreEntrenador;
  }

  /**
   * Set the value of contrasena
   * es la contraseña del entrenador jugador
   * @param newVar the new value of contrasena
   */
  public void setContrasena (String newVar) {
    contrasena = newVar;
  }

  /**
   * Get the value of contrasena
   * es la contraseña del entrenador jugador
   * @return the value of contrasena
   */
  public String getContrasena () {
    return contrasena;
  }

  /**
   * Set the value of genero
   * genero del entrenador
   * @param newVar the new value of genero
   */
  public void setGenero (Generos newVar) {
    genero = newVar;
  }

  /**
   * Get the value of genero
   * genero del entrenador
   * @return the value of genero
   */
  public Generos getGenero () {
    return genero;
  }

  /**
   * Set the value of ciudadOrigen
   * ciudad natal del entrenador
   * 
   * @param newVar the new value of ciudadOrigen
   */
  public void setCiudadOrigen (String newVar) {
    ciudadOrigen = newVar;
  }

  /**
   * Get the value of ciudadOrigen
   * ciudad natal del entrenador
   * 
   * @return the value of ciudadOrigen
   */
  public String getCiudadOrigen () {
    return ciudadOrigen;
  }

  /**
   * Set the value of tiempoJuego
   * tiempo de juego del entrenador
   * comprobar  como almacenarlo  y guardarlo
   * @param newVar the new value of tiempoJuego
   */
  public void setTiempoJuego (Long newVar) {
    tiempoJuego = newVar;
  }

  /**
   * Get the value of tiempoJuego
   * tiempo de juego del entrenador
   * comprobar  como almacenarlo  y guardarlo
   * @return the value of tiempoJuego
   */
  public Long getTiempoJuego () {
    return tiempoJuego;
  }

  /**
   * Set the value of pokedollares
   * dinero del jugador, su unidad es Pokedollares
   * empieza con 800-1000 pokedolarres
   * @param newVar the new value of pokedollares
   */
  public void setPokedollares (int newVar) {
    pokedollares = newVar;
  }

  /**
   * Get the value of pokedollares
   * dinero del jugador, su unidad es Pokedollares
   * empieza con 800-1000 pokedolarres
   * @return the value of pokedollares
   */
  public int getPokedollares () {
    return pokedollares;
  }

  /**
   * Set the value of equipoPokemon
   * los Pokemon del equipo
   * @param newVar the new value of equipoPokemon
   */
  public void setEquipoPokemon (Pokemon[] newVar) {
    equipoPokemon = newVar;
  }

  /**
   * Get the value of equipoPokemon
   * los Pokemon del equipo
   * @return the value of equipoPokemon
   */
  public Pokemon[] getEquipoPokemon () {
    return equipoPokemon;
  }

  /**
   * Set the value of cajaPokemon
   * los Pokemon capturados que no estan en el equipo
   * @param newVar the new value of cajaPokemon
   */
  public void setCajaPokemon (Pokemon[] newVar) {
    cajaPokemon = newVar;
  }

  /**
   * Get the value of cajaPokemon
   * los Pokemon capturados que no estan en el equipo
   * @return the value of cajaPokemon
   */
  public Pokemon[] getCajaPokemon () {
    return cajaPokemon;
  }

  /**
   * Set the value of victorias
   * victorias del entrenador
   * @param newVar the new value of victorias
   */
  public void setVictorias (int newVar) {
    victorias = newVar;
  }

  /**
   * Get the value of victorias
   * victorias del entrenador
   * @return the value of victorias
   */
  public int getVictorias () {
    return victorias;
  }

  /**
   * Set the value of derrotas
   * derrotas del entrenador
   * @param newVar the new value of derrotas
   */
  public void setDerrotas (int newVar) {
    derrotas = newVar;
  }

  /**
   * Get the value of derrotas
   * derrotas del entrenador
   * @return the value of derrotas
   */
  public int getDerrotas () {
    return derrotas;
  }

  /**
   * Set the value of inventario
   * mochila con los objetos del entrenador
   * @param newVar the new value of inventario
   */
  public void setInventario (Objeto[] newVar) {
    inventario = newVar;
  }

  /**
   * Get the value of inventario
   * mochila con los objetos del entrenador
   * @return the value of inventario
   */
  public Objeto[] getInventario () {
    return inventario;
  }

  /**
   * Set the value of esNPC
   * para saber si es entrenador del juego o de un jugador
   * @param newVar the new value of esNPC
   */
  public void setEsNPC (Boolean newVar) {
    esNPC = newVar;
  }

  /**
   * Get the value of esNPC
   * para saber si es entrenador del juego o de un jugador
   * @return the value of esNPC
   */
  public Boolean getEsNPC () {
    return esNPC;
  }

  /**
   * Set the value of imgFrontalEntrenador
   * para el spray frontal del entrenador
   * @param newVar the new value of imgFrontalEntrenador
   */
  public void setImgFrontalEntrenador (String newVar) {
    imgFrontalEntrenador = newVar;
  }

  /**
   * Get the value of imgFrontalEntrenador
   * para el spray frontal del entrenador
   * @return the value of imgFrontalEntrenador
   */
  public String getImgFrontalEntrenador () {
    return imgFrontalEntrenador;
  }

  /**
   * Set the value of imgPosteriorEntrenador
   * para el spray posterior del entrenador
   * @param newVar the new value of imgPosteriorEntrenador
   */
  public void setImgPosteriorEntrenador (String newVar) {
    imgPosteriorEntrenador = newVar;
  }

  /**
   * Get the value of imgPosteriorEntrenador
   * para el spray posterior del entrenador
   * @return the value of imgPosteriorEntrenador
   */
  public String getImgPosteriorEntrenador () {
    return imgPosteriorEntrenador;
  }

  //
  // Other methods
  //

  /**
   * para mover los Pokemon desde el equipo a la caja
   * @param        pokemon
   */
  public void moverPokemonACaja(Pokemon pokemon)
  {
  }


  /**
   * para mover los Pokemon desde la caja al equipo principal
   * @param        pokemon
   */
  public void moverPokemonAEquipo(Pokemon pokemon)
  {
  }


  /**
   * para entrenar un Pokemon mediante pokedollares con diferentes tipos de
   * entrenamiento
   * Entrenamiento pesado: se gastan 20*NIVEL pokédollars y se aumentan las
   * estadísticas de defensa, defensa especial y vitalidad en 5 puntos.
   * Entrenamiento furioso: se gastan 30*NIVEL pokédollars y se aumentan las
   * estadísticas de ataque, ataque especial y velocidad en 5 puntos.
   * Entrenamiento funcional: se gastan 40*NIVEL pokédollars y se aumentan las
   * estadísticas de velocidad, ataque, defensa y vitalidad en 5 puntos.
   * Entrenamiento onírico: se gastan 40*NIVEL pokédollars y se aumentan las
   * estadísticas de velocidad, ataque especial, defensa especial y vitalidad en 5
   * puntos.
   * @param        pokemon
   * @param        tipoEntrenamiento
   */
  public void entrenarPokemon(Pokemon pokemon, TipoEntrenamiento tipoEntrenamiento)
  {
  }


  /**
   * metodo para capturar Pokemon
   * @param        pokemon
   */
  public void capturarPokemon(Pokemon pokemon)
  {
  }


  /**
   * metodo para el comabte Pokemon
   */
  public void combatir()
  {
  }


  /**
   * metodo para criar pokemon, el Pokemon criado tendra :
   * Mote como mezcla (la mitad del mote será de la madre y la otra mitad del padre,
   * con el orden aleatorio).
   * Ataques mezclados (la mitad de los ataques serán del padre, la otra mitad serán
   * de la madre).
   * Tipos mezclados (el hijo tendrá, de forma aleatoria, los tipos de los padres,
   * pudiendo tener ambos tipos del padre o de la madre, un tipo de cada o, en caso
   * de compartir tipos sus progenitores, el tipo que comparten ambos).
   * Las mejores características de cada progenitor.
   * @param        pokemonMacho
   * @param        pokemonHembra
   */
  public void criarPokemon(Pokemon pokemonMacho, Pokemon pokemonHembra)
  {
  }


}
