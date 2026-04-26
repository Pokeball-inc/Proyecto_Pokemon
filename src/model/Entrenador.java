package model;

/**
 * @author Elyass Douma Zouhairi
 * @author Pablo Serrano Conesa
 * @author Isaías Villarreal Méndez
 */

import java.util.ArrayList;


/**
 * Class Clase Entrenador, define al entrenador del jugador y a los entrenadores rivales que se generan
 */
public class Entrenador {

    //
    // Fields
    //
    /**
     * El identificador del entrenador
     *
     */

    private int idEntrenador = 1;
    /**
     * Nombre del entrenador
     */
    private String nombreEntrenador;
    /**
     * Es la contraseña del entrenador jugador
     */
    private String contrasena;
    /**
     * Genero del entrenador
     */
    private Generos genero;
    /**
     * Ciudad natal del entrenador
     *
     */

    private String ciudadOrigen;
    /**
     * Tiempo de juego del entrenador
     */

    private Long tiempoJuego;
    /**
     * Dinero del jugador, su unidad es Pokedollares [Empieza con 8000 Pokedollares]
     */

    private int pokedollares;
    /**
     * Los Pokemon ubicados en el EQUIPO
     */
    private Pokemon[] equipoPokemon;
    /**
     * Los Pokemon ubicados en la CAJA
     */
    private ArrayList<Pokemon> cajaPokemon;
    /**
     * Victorias del entrenador
     */
    private int victorias = 0;
    /**
     * Derrotas del entrenador
     */
    private int derrotas = 0;
    /**
     * Mochila con los objetos del entrenador
     */
    private Inventario inventario;
    /**
     * Para saber si es entrenador del juego o de un jugador
     */
    private Boolean esNPC;
    /**
     * Para el spray frontal del entrenador
     */
    private String imgFrontalEntrenador;
    /**
     * Para el spray posterior del entrenador
     */
    private String imgPosteriorEntrenador;


    /**
     * Constructor por defecto de la clase Entrenador
     * */
    public Entrenador() {
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
        this.inventario = new Inventario();
        this.esNPC = false;
        this.imgFrontalEntrenador = "";
        this.imgPosteriorEntrenador = "";
    }

    ;

    /**
     * Constructor completo para inicializar un Entrenador con todos sus atributos.
     * * @param idEntrenador Identificador único del entrenador en la base de datos
     * @param nombreEntrenador Nombre del entrenador
     * @param contrasena La clave para poder acceder a la cuenta del entrenador
     * @param genero El género del pokemon
     * @param ciudadOrigen Ciudad de origen del entrenador
     * @param tiempoJuego Tiempo total de juego
     * @param pokedollares Cantidad de Pokedollares que tiene el Entrenador
     * @param equipoPokemon Array con los 6 Pokémon que el entrenador tiene en el EQUIPO
     * @param cajaPokemon Lista con los Pokémon que el pokemon tiene en la CAJA
     * @param victorias Número total de combates ganados
     * @param derrotas Número total de combates perdidos
     * @param inventario Objeto que gestiona los ítems del entrenador (Antes también consumibles, pero no se implementaron al final)
     * @param esNPC Indica si el entrenador es un personaje no jugable (IA)
     * @param imgFrontalEntrenador Ruta del recurso para la imagen frontal del sprite
     * @param imgPosteriorEntrenador Ruta del recurso para la imagen de espalda del sprite
     */

    public Entrenador(int idEntrenador, String nombreEntrenador, String contrasena, Generos genero, String ciudadOrigen, long tiempoJuego, int pokedollares, Pokemon[] equipoPokemon, ArrayList<Pokemon> cajaPokemon, int victorias, int derrotas, Inventario inventario, boolean esNPC, String imgFrontalEntrenador, String imgPosteriorEntrenador) {
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
    }

    ;



    /**
     * Constructor copia del Entrenador
     * Crea una nueva instancia de Entrenador a partir de otro ya existente.
     * @param e Objeto Entrenador del cual se van a copiar los datos
     */

    public Entrenador(Entrenador e) {
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
        this.inventario = e.inventario;
        this.esNPC = e.esNPC;
        this.imgFrontalEntrenador = e.imgFrontalEntrenador;
        this.imgPosteriorEntrenador = e.imgPosteriorEntrenador;
    }


    //
    // Methods
    //

    public void setIdEntrenador(int newVar) {
        idEntrenador = newVar;
    }

    public int getIdEntrenador() {
        return idEntrenador;
    }

    public void setNombreEntrenador(String newVar) {
        nombreEntrenador = newVar;
    }

    public String getNombreEntrenador() {
        return nombreEntrenador;
    }

    public void setContrasena(String newVar) {
        contrasena = newVar;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setGenero(Generos newVar) {
        genero = newVar;
    }

    public Generos getGenero() {
        return genero;
    }

    public void setCiudadOrigen(String newVar) {
        ciudadOrigen = newVar;
    }

    public String getCiudadOrigen() {
        return ciudadOrigen;
    }

    public void setTiempoJuego(Long newVar) {
        tiempoJuego = newVar;
    }

    public Long getTiempoJuego() {
        return tiempoJuego;
    }

    public void setPokedollares(int newVar) {
        pokedollares = newVar;
    }

    public int getPokedollares() {
        return pokedollares;
    }

    public void setEquipoPokemon(Pokemon[] newVar) {
        equipoPokemon = newVar;
    }

    public Pokemon[] getEquipoPokemon() {
        return equipoPokemon;
    }

    public void setCajaPokemon(ArrayList<Pokemon> newVar) {
        cajaPokemon = newVar;
    }

    public ArrayList<Pokemon> getCajaPokemon() {
        return cajaPokemon;
    }

    public void setVictorias(int newVar) {
        victorias = newVar;
    }

    public int getVictorias() {
        return victorias;
    }

    public void setDerrotas(int newVar) {
        derrotas = newVar;
    }

    public int getDerrotas() {
        return derrotas;
    }

    public void setInventario(Inventario newVar) {
        inventario = newVar;
    }

    public Inventario getInventario() {
        return inventario;
    }

    public void setEsNPC(Boolean newVar) {
        esNPC = newVar;
    }

    public Boolean getEsNPC() {
        return esNPC;
    }

    public void setImgFrontalEntrenador(String newVar) {
        imgFrontalEntrenador = newVar;
    }

    public String getImgFrontalEntrenador() {
        return imgFrontalEntrenador;
    }

    public void setImgPosteriorEntrenador(String newVar) {
        imgPosteriorEntrenador = newVar;
    }

    public String getImgPosteriorEntrenador() {
        return imgPosteriorEntrenador;
    }

    //
    // Other methods
    //

    /**
     * Cura a todo el equipo del entrenador Vida al máximo y quita estados/debilitado
     */
    public void curarEquipoCompleto() {
        // Recorremos los 6 huecos del equipo
        for (Pokemon p : this.equipoPokemon) {
            if (p != null) {
                p.setVitalidad(p.getVitalidadMaxima());
                p.setEstadoActual(model.Estados.SANO);
                p.setTurnosEstadoRestantes(0);
                // restauramos los PP de todos sus movimientos
                if (p.getMovimientos() != null) {
                    for (Movimiento m : p.getMovimientos()) {
                        if (m != null) {
                            m.setCantidadMovimientos(15);
                        }
                    }
                }
            }
        }
        System.out.println("¡Tu equipo ha sido curado por completo!");
    }

    /**
     * Metodo para mover los Pokemon desde el equipo a la caja
     * @param pokemon Recibe el pokemon que se va a mover a la caja
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
            pokemon.setUbicacion(UbicacionPokemon.CAJA);
            cajaPokemon.add(pokemon);
            equipoPokemon[posicionEnEquipo] = null;
            System.out.println("¡El Pokémon ha sido movido a la caja PC!");
        } else {
            System.out.println("Ese Pokémon no se encuentra en tu equipo actual.");
        }
    }

    /**
     * Metodo para mover los Pokemon desde la caja al equipo
     * @param pokemon Recibe el pokemon que se va a mover al equipo
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
            pokemon.setUbicacion(UbicacionPokemon.EQUIPO);
            equipoPokemon[huecoEquipo] = pokemon; // Lo metemos al equipo
            cajaPokemon.remove(pokemon);          // Lo borra de la caja
            System.out.println("¡El Pokémon ha sido movido al equipo principal!");
        } else {
            System.out.println("No se ha encontrado a ese Pokémon en la caja.");
        }
    }


    /**
     * Metodo para entrenar un Pokemon mediante pokedollares con diferentes tipos de
     * entrenamiento
     * Entrenamiento pesado: se gastan 20*NIVEL pokedollars y se aumentan las
     * estadísticas de defensa, defensa especial y vitalidad en 5 puntos.
     * Entrenamiento furioso: se gastan 30*NIVEL pokedollars y se aumentan las
     * estadísticas de ataque, ataque especial y velocidad en 5 puntos.
     * Entrenamiento funcional: se gastan 40*NIVEL pokedollars y se aumentan las
     * estadísticas de velocidad, ataque, defensa y vitalidad en 5 puntos.
     * Entrenamiento onírico: se gastan 40*NIVEL pokedollars y se aumentan las
     * estadísticas de velocidad, ataque especial, defensa especial y vitalidad en 5
     * puntos.
     *
     * @param pokemon Recibe el pokemon a entrenar
     * @param tipoEntrenamiento Recibe el tipo de entrenamiento a realizar
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
     * Metodo para crear un pokemon dependiendo de una rareza establecida por nosotros, no implementado
     * @param rareza, recibe la rarezaz del pokemon
     * @return Devuelve el pokemon creado
     */
    private Pokemon crearPokemonPorRareza(Rareza rareza) {
        Pokemon nuevo = new Pokemon();
        int nivel = 1;
        //Diferentes tipos de rareza
        switch (rareza) {
            case BASICO: {
                String[] b = {"Caterpie", "Metapod", "Weedle", "Kakuna", "Pidgey", "Rattata", "Spearow", "Ekans", "Sandshrew", "Nidoran F", "Nidoran M", "Zubat", "Oddish", "Paras", "Venonat", "Diglett", "Meowth", "Pysduck", "Mankey", "Poliwag", "Abra", "Machop", "Bellsprout", "Tentacool", "Geodude", "Ponyta", "Slowpoke", "Magnemite", "Doduo", "Seel", "Grimer", "Shellder", "Gastly", "Drowzee", "Krabby", "Voltorb", "Exeggcute", "Cubone", "Koffing", "Rhyhorn", "Horsea", "Goldeen", "Staryu", "Magikarp", "Eevee", "Sentret", "Hoothoot", "Ledyba", "Spinarak", "Chinchou", "Pichu", "Cleffa", "Igglybuff", "Togepi", "Natu", "Mareep", "Marill", "Hoppip", "Sunkern", "Wooper", "Pineco", "Snubbull", "Teddiursa", "Slugma", "Swinub", "Remoraid", "Phanpy", "Tyrogue", "Smoochum", "Elekid", "Magby", "Larvitar"};
                nuevo.setNombrePokemon(b[(int) (Math.random() * b.length)]);
                nuevo.setNombrePokemon(b[(int) (Math.random() * b.length)]); //Para que coja solo un nombre dentro de la lista
                nuevo.setVitalidad(50);
                break;
            }
            case RARO: {
                String[] r = {"Bulbasaur", "Ivysaur", "Charmander", "Charmeleon", "Squirtle", "Wartortle", "Butterfree", "Beedrill", "Pidgeotto", "Raticate", "Fearow", "Arbok", "Pikachu", "Raichu", "Sandslash", "Nidorina", "Nidorino", "Clefairy", "Clefable", "Vulpix", "Ninetales", "Jigglypuff", "Wigglytuff", "Golbat", "Gloom", "Parasect", "Venomoth", "Dugtrio", "Persian", "Golduck", "Primeape", "Growlithe", "Arcanine", "Poliwhirl", "Kadabra", "Machoke", "Weepinbell", "Tentacruel", "Graveler", "Rapidash", "Slowbro", "Magneton", "Farfetchd", "Dodrio", "Dewgong", "Muk", "Cloyster", "Haunter", "Onix", "Hypno", "Kingler", "Electrode", "Marowak", "Hitmonlee", "Hitmonchan", "Lickitung", "Weezing", "Chansey", "Tangela", "Kangaskhan", "Seadra", "Seaking", "Mr Mime", "Scyther", "Jynx", "Electabuzz", "Magmar", "Pinsir", "Tauros", "Ditto", "Porygon", "Omanyte", "Omastar", "Kabuto", "Kabutops", "Aerodactyl", "Dratini", "Dragonair", "Chikorita", "Bayleef", "Cyndaquil", "Quilava", "Totodile", "Croconaw", "Furret", "Noctowl", "Ledian", "Ariados", "Lanturn", "Togetic", "Xatu", "Flaaffy", "Bellossom", "Azumarill", "Sudowoodo", "Skiploom", "Aipom", "Sunflora", "Yanma", "Quagsire", "Murkrow", "Misdreavus", "Unown", "Wobbuffet", "Girafarig", "Forretress", "Dunsparce", "Gligar", "Granbull", "Qwilfish", "Shuckle", "Heracross", "Sneasel", "Ursaring", "Magcargo", "Piloswine", "Corsola", "Octillery", "Delibird", "Mantine", "Skarmory", "Houndour", "Houndoom", "Donphan", "Stantler", "Smeargle", "Hitmontop", "Miltank", "Pupitar"};
                nuevo.setNombrePokemon(r[(int) (Math.random() * r.length)]);
                nuevo.setVitalidad(75);
                break;
            }
            case EPICO: {
                String[] e = {"Venusaur", "Charizard", "Blastoise", "Pidgeot", "Nidoqueen", "Nidoking", "Vileplume", "Poliwrath", "Alakazam", "Machamp", "Victreebel", "Golem", "Gengar", "Exeggutor", "Rhydon", "Starmie", "Gyarados", "Lapras", "Vaporeon", "Jolteon", "Flareon", "Snorlax", "Dragonite", "Meganium", "Typhlosion", "Feraligatr", "Crobat", "Ampharos", "Politoed", "Jumpluff", "Espeon", "Umbreon", "Slowking", "Steelix", "Scizor", "Kingdra", "Porygon2", "Blissey", "Tyranitar"};
                nuevo.setNombrePokemon(e[(int) (Math.random() * e.length)]);
                nuevo.setVitalidad(110);
                break;
            }
            case LEGENDARIO: {
                String[] l = {"Articuno", "Zapdos", "Moltres", "Mewtwo", "Mew", "Raikou", "Entei", "Suicune", "Lugia", "Ho-oh", "Celebi"};
                nuevo.setNombrePokemon(l[(int) (Math.random() * l.length)]);
                nuevo.setVitalidad(200);
                break;
            }
        }
        return nuevo;

    }

    /**
     * @deprecated - Lógica cambiada en CapturaController
     * Metodo generar encuentro aleatorio (CAPTURA), genera un encuentro aleatorio por rareza
     * @return devuelve el pokemon generado por rareza
     */
    public Pokemon generarEncuentroAleatorio() {
        double ratio = Math.random() * 100;
        if (ratio < 60) {
            return crearPokemonPorRareza(Rareza.BASICO);
        } else if (ratio < 85) {
            return crearPokemonPorRareza(Rareza.RARO);
        } else if (ratio < 95) {
            return crearPokemonPorRareza(Rareza.EPICO);
        } else {
            System.out.println("¡¡¡UNA PRESENCIA PODEROSA APARECE EN LA HIERBA!!!");
            return crearPokemonPorRareza(Rareza.LEGENDARIO);
        }
    }


    /**
     * @deprecated - Lógica cambiada en CapturaController
     * Metodo para capturar Pokemon (CAPTURA)
     * @param pokemonSalvaje Recibe el pokemon a capturar
     * @param bolaLanzada Recibe el tipo de pokeball que se va a lanzar
     */
    public void capturarPokemon(Pokemon pokemonSalvaje, TipoPokeBall bolaLanzada) {
        double intento = Math.random() * 255;

        double ratioFinal = pokemonSalvaje.getRatioCaptura().getRatio() * bolaLanzada.getMultiplicador();
        System.out.println("Lanzando " + bolaLanzada + " a " + pokemonSalvaje.getNombrePokemon() + "...");

        if (intento < ratioFinal) {
            System.out.println("¡Capturado! se ha añadido " + pokemonSalvaje.getNombrePokemon() + " a tu PC");
            this.cajaPokemon.add(pokemonSalvaje);
        } else {
            System.out.println("¡El Pokemon se ha escapado!");
        }
    }

    /**
     * @deprecated - Lógica cambiada en CapturaController
     * Metodo para comprobar si el entrenador tiene Pokemons vivos y puede luchar
     * @return Devuelve true o false dependiendo si tiene o nopokemon vivos
     */
    public boolean puedeLuchar() {
        if (equipoPokemon == null) {
            return false;
        }

        for (Pokemon p : equipoPokemon) {
            // verificamos que la posición no sea nula y que tenga vida
            if (p != null && p.getVitalidad() > 0) {
                return true; // en cuanto encuentra uno vivo, puede luchar
            }
        }
        return false;
    }

    /**
     * @deprecated - Lógica cambiada en el Combate / CombateController
     * Metodo para el combate Pokemon
     * @param rival Recibe el entrenador rival
     */
    public void combatir(Entrenador rival) {
        // instanciamos la clase que controla la pelea
        Combate nuevoCombate = new Combate();

        // iniciamos el combate con los dos entrenadores
        nuevoCombate.empezarCombate(this, rival, new Log());

        System.out.println("Iniciando combate entre " + this.nombreEntrenador + " y " + rival.getNombreEntrenador());
    }


    /**
     * @deprecated - Lógica cambiada en CrianzaController
     *
     * Metodo para criar pokemon, el Pokemon criado tendra :
     * Mote como mezcla (la mitad del mote será de la madre y la otra mitad del padre,
     * con el orden aleatorio).
     * Ataques mezclados (la mitad de los ataques serán del padre, la otra mitad serán
     * de la madre).
     * Tipos mezclados (el hijo tendrá, de forma aleatoria, los tipos de los padres,
     * pudiendo tener ambos tipos del padre o de la madre, un tipo de cada o, en caso
     * de compartir tipos sus progenitores, el tipo que comparten ambos).
     * Las mejores características de cada progenitor.
     *
     * @param pokemonMacho  recibe el pokemon macho
     * @param pokemonHembra recibe el pokemon hembra
     */
    public void criarPokemon(Pokemon pokemonMacho, Pokemon pokemonHembra) {
    }


    /**
     * incrementa en 1 el contador de victorias del entrenador
     */
    public void sumarVictoria() {
        this.victorias++;
        System.out.println("Estadísticas actualizadas: " + this.nombreEntrenador + " tiene " + this.victorias + " victorias.");
    }

    /**
     * incrementa en 1 el contador de derrotas del entrenador
     */
    public void sumarDerrota() {
        this.derrotas++;
        System.out.println("Estadísticas actualizadas: " + this.nombreEntrenador + " tiene " + this.derrotas + " derrotas.");
    }


}
