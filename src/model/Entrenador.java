package model;


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
   * es la contrase�a del entrenador jugador
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
  private ArrayList <Pokemon> cajaPokemon;
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
  // Constructors por defecto
  //
  public Entrenador () {
	  super();
	  this.idEntrenador = 0;
	  this.nombreEntrenador = "";
	  this.contrasena = "";
	  this.genero = Generos.Neutro;
	  this.ciudadOrigen = "";
	  this.tiempoJuego = (long) 0;
	  this.pokedollares = 0;
	  this.equipoPokemon = new Pokemon[6];
	  this.cajaPokemon = new ArrayList<>();
	  this.victorias = 0;
	  this.derrotas = 0;
	  this.inventario = new Objeto[50];
	  this.esNPC = false;
	  this.imgFrontalEntrenador = "";
	  this.imgPosteriorEntrenador = "";
  };
  
  //Constructor todos los parametros
  public Entrenador (int idEntrenador, String nombreEntrenador, String contrasena, Generos genero, String ciudadOrigen, long tiempoJuego, int pokedollares, Pokemon[] equipoPokemon, ArrayList<Pokemon> cajaPokemon, int victorias, int derrotas, Objeto[] inventario, boolean esNPC, String imgFrontalEntrenador, String imgPosteriorEntrenador) {
	  super();
	  this.idEntrenador = idEntrenador;
	  this.nombreEntrenador = nombreEntrenador;
	  this.contrasena = contrasena;
	  this.genero = genero;
	  this.ciudadOrigen = ciudadOrigen;
	  this.tiempoJuego = tiempoJuego;
	  this.pokedollares = pokedollares;
	  this.equipoPokemon = equipoPokemon;
	  this.cajaPokemon = cajaPokemon;
	  this.victorias = victorias;
	  this.derrotas = derrotas;
	  this.inventario = inventario;
	  this.esNPC = esNPC;
	  this.imgFrontalEntrenador = imgFrontalEntrenador;
	  this.imgPosteriorEntrenador = imgPosteriorEntrenador;
  };
  
  //Constructor copia
  public Entrenador (Entrenador e) {
	  this.idEntrenador = e.idEntrenador;
	  this.nombreEntrenador = e.nombreEntrenador;
	  this.contrasena = e.contrasena;
	  this.genero = e.genero;
	  this.ciudadOrigen = e.ciudadOrigen;
	  this.tiempoJuego = e.tiempoJuego;
	  this.pokedollares = e.pokedollares;
	  this.equipoPokemon = e.equipoPokemon.clone(); //para que si hay varios entrenadores no compartan el mismo 
	  this.cajaPokemon = new ArrayList<>(e.cajaPokemon);
	  this.victorias = e.victorias;
	  this.derrotas = e.derrotas;
	  this.inventario = e.inventario.clone();
	  this.esNPC = e.esNPC;
	  this.imgFrontalEntrenador = e.imgFrontalEntrenador;
	  this.imgPosteriorEntrenador = e.imgPosteriorEntrenador;
  };
  
  
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
   * es la contrase�a del entrenador jugador
   * @param newVar the new value of contrasena
   */
  public void setContrasena (String newVar) {
    contrasena = newVar;
  }

  /**
   * Get the value of contrasena
   * es la contrase�a del entrenador jugador
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
  public void setCajaPokemon (ArrayList<Pokemon> newVar) {
    cajaPokemon = newVar;
  }

  /**
   * Get the value of cajaPokemon
   * los Pokemon capturados que no estan en el equipo
   * @return the value of cajaPokemon
   */
  public ArrayList<Pokemon> getCajaPokemon () {
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
  public void moverPokemonACaja(Pokemon pokemon) {
      // 1. Buscamos el Pokémon dentro del equipo
      int posicionEnEquipo = -1;
      
      for (int i = 0; i < equipoPokemon.length; i++) {
          if (equipoPokemon[i] != null && equipoPokemon[i].equals(pokemon)) {
              posicionEnEquipo = i;
              break;
          }
      }
      // 2.Si lo encontramos en el equipo
      if (posicionEnEquipo != -1) {
          cajaPokemon.add(pokemon);               //Añadir caja (crece)
          equipoPokemon[posicionEnEquipo] = null; // Lo borramos del equipo
          System.out.println("¡El Pokémon ha sido movido a la caja PC!");
      } else {
          System.out.println("Ese Pokémon no se encuentra en tu equipo actual.");
      }
  }

  /**
   * para mover los Pokemon desde la caja al equipo principal
   * @param        pokemon
   */
  public void moverPokemonAEquipo(Pokemon pokemon) {
      // 1. Buscamos si hay un hueco libre en el equipo
      int huecoEquipo = -1; 
      
      for (int i = 0; i < equipoPokemon.length; i++) {
          if (equipoPokemon[i] == null) {
              huecoEquipo = i; 
              break; 
          }
      }
      //Equipo lleno
      if (huecoEquipo == -1) {
          System.out.println("Tu equipo ya está lleno (6 Pokémon).");
          return; 
      }

      // 2. Buscamos y movemos gracias
      if (cajaPokemon.contains(pokemon)) { //Busca por ti si está dentro
          equipoPokemon[huecoEquipo] = pokemon; // Lo metemos al equipo
          cajaPokemon.remove(pokemon);          // Lo borra de la caja 
          System.out.println("¡El Pokémon ha sido movido al equipo principal!");
      } else {
          System.out.println("No se ha encontrado a ese Pokémon en la caja.");
      }
  }


  /**
   * para entrenar un Pokemon mediante pokedollares con diferentes tipos de
   * entrenamiento
   * Entrenamiento pesado: se gastan 20*NIVEL pok�dollars y se aumentan las
   * estad�sticas de defensa, defensa especial y vitalidad en 5 puntos.
   * Entrenamiento furioso: se gastan 30*NIVEL pok�dollars y se aumentan las
   * estad�sticas de ataque, ataque especial y velocidad en 5 puntos.
   * Entrenamiento funcional: se gastan 40*NIVEL pok�dollars y se aumentan las
   * estad�sticas de velocidad, ataque, defensa y vitalidad en 5 puntos.
   * Entrenamiento on�rico: se gastan 40*NIVEL pok�dollars y se aumentan las
   * estad�sticas de velocidad, ataque especial, defensa especial y vitalidad en 5
   * puntos.
   * @param        pokemon
   * @param        tipoEntrenamiento
   */
public void entrenarPokemon(Pokemon pokemon, TipoEntrenamiento tipoEntrenamiento) {
      int coste = 0;

      // PASO 1: Calculamos el coste según el tipo de entrenamiento
      switch (tipoEntrenamiento) {
          case ENTRENAMIENTOPESADOM:
              coste = 20 * pokemon.getNivel();
              break;
          case ENTRENAMIENTOFURIOSO:
              coste = 30 * pokemon.getNivel();
              break;
          case ENTRENAMIENTOFUNCIONAL:
          case ENTRENAMIENTOONIRICO:
              coste = 40 * pokemon.getNivel(); // Ambos cuestan lo mismo
              break;
      }

      // PASO 2: Comprobamos si hay dinero suficiente (una sola comprobación para todos)
      if (this.pokedollares >= coste) {
          
          this.pokedollares = this.pokedollares - coste;

          // PASO 3: Subimos las estadísticas según el tipo
          switch (tipoEntrenamiento) {
              case ENTRENAMIENTOPESADOM:
                  pokemon.setDefensa(pokemon.getDefensa() + 5);
                  pokemon.setDefensaEspecial(pokemon.getDefensaEspecial() + 5);
                  pokemon.setVitalidad(pokemon.getVitalidad() + 5);
                  break;
                  
              case ENTRENAMIENTOFURIOSO:
                  pokemon.setAtaque(pokemon.getAtaque() + 5);
                  pokemon.setAtaqueEspecial(pokemon.getAtaqueEspecial() + 5);
                  pokemon.setVelocidad(pokemon.getVelocidad() + 5);
                  break;
                  
              case ENTRENAMIENTOFUNCIONAL:
                  pokemon.setVelocidad(pokemon.getVelocidad() + 5);
                  pokemon.setAtaque(pokemon.getAtaque() + 5);
                  pokemon.setDefensa(pokemon.getDefensa() + 5);
                  pokemon.setVitalidad(pokemon.getVitalidad() + 5);
                  break;
                  
              case ENTRENAMIENTOONIRICO:
                  pokemon.setVelocidad(pokemon.getVelocidad() + 5);
                  pokemon.setAtaqueEspecial(pokemon.getAtaqueEspecial() + 5);
                  pokemon.setDefensaEspecial(pokemon.getDefensaEspecial() + 5);
                  pokemon.setVitalidad(pokemon.getVitalidad() + 5);
                  break;
          }
          
          System.out.println("¡Entrenamiento " + tipoEntrenamiento + " completado con éxito!");
          
      } else {
          System.out.println("No tienes suficientes pokédollares. Necesitas " + coste + " y tienes " + this.pokedollares);
      }
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
   * Mote como mezcla (la mitad del mote ser� de la madre y la otra mitad del padre,
   * con el orden aleatorio).
   * Ataques mezclados (la mitad de los ataques ser�n del padre, la otra mitad ser�n
   * de la madre).
   * Tipos mezclados (el hijo tendr�, de forma aleatoria, los tipos de los padres,
   * pudiendo tener ambos tipos del padre o de la madre, un tipo de cada o, en caso
   * de compartir tipos sus progenitores, el tipo que comparten ambos).
   * Las mejores caracter�sticas de cada progenitor.
   * @param        pokemonMacho
   * @param        pokemonHembra
   */
  public void criarPokemon(Pokemon pokemonMacho, Pokemon pokemonHembra)
  {
  }
  

}
