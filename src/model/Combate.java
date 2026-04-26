package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author Elyass Douma Zouhairi
 * @author Pablo Serrano Conesa
 * @author Isaías Villarreal Méndez
 */

/**
 * Class Combate
 */
public class Combate {

    //
    // Fields
    //

    /**
     * La id del combate
     */
    private int idCombate = 1;
    /**
     * El entrenador del jugador en el combate
     */
    private Entrenador entrenador;
    /**
     * El entrenador rival en el combate
     */
    private Entrenador entrenadorRival;

    /** El Pokemon actual del jugador*/
    private Pokemon pokemonActualJugador;

    /**El Pokemon actual del rival*/
    private Pokemon pokemonActualRival;
    /**
     * Array donde se guarda el historial de turnos
     */
    private List<Turno> historialTurnos = new ArrayList<>();

    /**
     * Los pokemons derrotados del jugador
     */
    private int pokemonKOEntrenador = 0;
    /**
     * Los pokemons derrotados del rival
     */
    private int pokemonKORival = 0;

    /**
     * Parámetro para asociar el Log a su combate. Un log por combate completo
     */

    private Log logAsociado;

    //
    // Constructors
    //
    public Combate() {
    }

    ;

    //
    // Methods
    //

    public void setIdCombate(int newVar) {
        idCombate = newVar;
    }

    public int getIdCombate() {
        return idCombate;
    }

    public void setEntrenador(Entrenador newVar) {
        entrenador = newVar;
    }

    public Entrenador getEntrenador() {
        return entrenador;
    }

    public void setEntrenadorRival(Entrenador newVar) {
        entrenadorRival = newVar;
    }

    public Entrenador getEntrenadorRival() {
        return entrenadorRival;
    }

    /**
     * Establece el Pokémon que el jugador saca a pelear.
     * @param pokemonActualJugador Recibe el pokemon actual del jugador
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
     * @param pokemonActualRival Recibe el pokemon actual del rival
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
     * @param historialTurnos Recibe el Array de Turnos actual
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


    public void setPokemonKOEntrenador(int newVar) {
        pokemonKOEntrenador = newVar;
    }

    public int getPokemonKOEntrenador() {
        return pokemonKOEntrenador;
    }

    public void setPokemonKORival(int newVar) {
        pokemonKORival = newVar;
    }

    public int getPokemonKORival() {
        return pokemonKORival;
    }

    //
    // Other methods
    //

    /**
     * metodo para iniciar el combate
     *
     * @param jugador El entrenador jugador
     * @param rival El entrenador rival
     * @param log El objeto log para el registro de logs
     */
    public void empezarCombate(Entrenador jugador, Entrenador rival, Log log) {
        this.logAsociado = log;
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

        registrarEventoLog("inicioCombate");
    }

    /**
     * Metodo para encontrar al primer pokemon vivo
     *
     * @param e, el entrenador,jugador o rival
     * @return el primer Pokemon con vitalidad > 0
     */
    public Pokemon buscarPrimerPokemonVivo(Entrenador e) {
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
     * Cambia el pokemon actual del jugador por otro del equipo.
     *
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
            registrarEventoLog("cambio1");
            System.out.println("¡Has enviado a " + elegido.getNombrePokemon() + " a la batalla!");
            return true;
        } else {
            System.out.println("No puedes elegir a ese Pokémon.");
            return false;
        }
    }

    /**
     * Metodo para aplicar los efectos que se aplican a final de turno
     *
     * @param p Recibe el pokemon del jugador
     * @param rival Recibe el pokemon del rival
     *
     */
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
     * Comprueba si el estado actual impide atacar y devuelve el mensaje para el log.
     * @param p Recibe el pokemon a verificar si tiene un estado que le impida realizar algo
     * @return El mensaje de la restricción, o NULL si el Pokémon puede atacar normalmente.
     */
    public String verificarRestriccionEstado(Pokemon p) {
        Random r = new Random();
        Estados estado = p.getEstadoActual();

        switch (estado) {
            case PARALIZADO:
                if (r.nextDouble() < 0.125) {
                    return p.getNombrePokemon() + " está paralizado y no puede moverse.";
                }
                break;

            case DORMIDO:
                return p.getNombrePokemon() + " está profundamente dormido...";

            case CONGELADO:
                if (r.nextDouble() < 0.20) {
                    p.setEstadoActual(Estados.SANO);
                    return "¡" + p.getNombrePokemon() + " se ha descongelado!";

                }
                return p.getNombrePokemon() + " está congelado y no puede atacar.";

            case AMEDENTRADO:
                p.setEstadoActual(Estados.SANO);
                return p.getNombrePokemon() + " ha retrocedido y no puede atacar.";

            case SOMNOLIENTO:
                if (r.nextDouble() < 0.50) {
                    return p.getNombrePokemon() + " tiene mucho sueño y no puede atacar.";
                }
                break;

            case CONFUSO:
                if (r.nextInt(3) == 0) {
                    int danio = p.getVitalidadMaxima() / 10;
                    p.setVitalidad(p.getVitalidad() - danio);
                    return "¡" + p.getNombrePokemon() + " está tan confuso que se ha herido a sí mismo!";
                }
                break;

            case ENAMORADO:
                if (r.nextInt(4) == 0) {
                    return p.getNombrePokemon() + " está enamorado y no puede atacar.";
                }
                break;

            case DEBILITADO:
                return p.getNombrePokemon() + " está debilitado.";

            default:
                return null; //si es SANO o no entra en las probabilidades, devuelve null (puede atacar)
        }
        return null;
    }


    /**
     * Metodo para procesar un turno completo de combate decidiendo quien ataca primero en base a la  velocidad
     * validacion de estados, ejecucion de daño y registro en el log
     *
     * @param movJugador el movimiento elegido por el usuario
     * @param movRival el movimiento elegido por la "IA"
     */
    public void procesarTurno(Movimiento movJugador, Movimiento movRival) {
        // creamos el Turno para registrar lo que pasara
        Turno turnoActual = new Turno();
        turnoActual.setNumeroTurnoActual(this.getHistorialTurnos().size() + 1);

        // definir la accion del jugador

        turnoActual.setAccionEntrenador(movJugador.getNombreMovimiento());


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

        /*
         * */
        // guardamos los logs en el objeto turnoActual para el historial
//    if (jugadorVaPrimero) {
//        turnoActual.setAccionEntrenador(res1);
//        turnoActual.setAccionEntrenadorRival(res2);
//    } else {
//        turnoActual.setAccionEntrenador(res2);
//        turnoActual.setAccionEntrenadorRival(res1);
//    }
//
// // Comprobamos el estado del Pokemon del jugador para el log
//    if (this.getPokemonActualJugador().getVitalidad() > 0) {
//        turnoActual.setEstadoPokemon1("OK");
//    } else {
//        turnoActual.setEstadoPokemon1("KO");
//    }

        // Comprobamos el estado del Pokemon del rival para el log
        if (this.getPokemonActualRival().getVitalidad() > 0) {
            turnoActual.setEstadoPokemon2("OK");
        } else {
            turnoActual.setEstadoPokemon2("KO");
        }

        // Recoger los datos para añdirlos al log

        Pokemon pokemonJugador = this.getPokemonActualJugador();
        Pokemon pokemonRival = this.getPokemonActualRival();

        turnoActual.setDatosPk1(pokemonJugador.getNombrePokemon(), pokemonJugador.getNivel(), entrenador.getNombreEntrenador(),
                pokemonJugador.getVitalidad() > 0 ? "OK" : "KO");

        turnoActual.setDatosPk2(pokemonRival.getNombrePokemon(), pokemonRival.getNivel(), entrenadorRival.getNombreEntrenador(),
                pokemonRival.getVitalidad() > 0 ? "OK" : "KO");
        this.añadirTurno(turnoActual);

        if (this.logAsociado != null) {
            this.logAsociado.añadirTurno(turnoActual);
        }


    }


    /**
     * Metodo para validar estados antes de atacar, ejecutar el daño o alterar estadísticas
     *
     * @param atacante El pokemon que intenta realizar el movimiento
     * @param objetivo El pokemon que recibe el posible impacto
     * @param mov El movimiento a realizar
     * @return El mensaje de texto de lo ocurrido para el historial
     */
    private String ejecutarAccion(Pokemon atacante, Pokemon objetivo, Movimiento mov) {
        //verificamos si hay alguna restricci0n de estado (
        String mensajeRestriccion = verificarRestriccionEstado(atacante);

        if (mensajeRestriccion != null) {
            return mensajeRestriccion;
        }

        //preparamos el inicio del mensaje
        String logBase = "¡" + atacante.getNombrePokemon() + " usó " + mov.getNombreMovimiento() + "!\n";
        String desc = mov.getDescripcionMovimiento();

        //CASO 1 ataque con daño
        if (mov.getPotencia() > 0) {
            String eficacia = atacante.atacar(mov, objetivo);

            //si el ataque de daño tambien tenia un estado secundario
            if (mov.getEstadoAplicado() != Estados.SANO && objetivo.getVitalidad() > 0 && objetivo.getEstadoActual() == Estados.SANO) {
                //probabilidad del 30% de aplicar el efecto secundario
                if (new Random().nextInt(100) < 30) {
                    objetivo.setEstadoActual(mov.getEstadoAplicado());
                    return logBase + "(" + eficacia + ")\n¡" + objetivo.getNombrePokemon() + " ha sido " + mov.getEstadoAplicado() + "!";
                }
            }

            return logBase + "(" + eficacia + ")";
        }

        //CASO 2 es una mejora
        if (mov.getTipoMovimiento() == TiposMovimiento.MEJORA) {
            if (desc.contains("ataque")) {
                atacante.setAtaque(atacante.getAtaque() + 5);
                return logBase + "¡El Ataque de " + atacante.getNombrePokemon() + " subió!";
            } else if (desc.contains("defensa")) {
                atacante.setDefensa(atacante.getDefensa() + 5);
                return logBase + "¡La Defensa de " + atacante.getNombrePokemon() + " subió!";
            } else if (desc.contains("velocidad")) {
                atacante.setVelocidad(atacante.getVelocidad() + 5);
                return logBase + "¡La Velocidad de " + atacante.getNombrePokemon() + " subió!";
            }
            return logBase + "¡Las estadísticas de " + atacante.getNombrePokemon() + " mejoraron!";
        }

        ///CASO 3 baja stats o aplica problema
        if (mov.getTipoMovimiento() == TiposMovimiento.ESTADO) {

            //que aplica
            if (mov.getEstadoAplicado() != Estados.SANO) {
                //comprobamos que el rival no tenga ya un estado
                if (objetivo.getEstadoActual() == Estados.SANO) {
                    objetivo.setEstadoActual(mov.getEstadoAplicado());

                    //le damos unos turnos base si es sueño o canto mortal
                    objetivo.setTurnosEstadoRestantes(3);

                    return logBase + "¡" + objetivo.getNombrePokemon() + " ahora está " + mov.getEstadoAplicado() + "!";
                } else {
                    return logBase + "Pero falló... " + objetivo.getNombrePokemon() + " ya tiene un problema de estado.";
                }
            }

            //baja estadisticas
            else {
                //evitamos que las estadísticas bajen de 1
                if (desc.contains("velocidad")) {
                    objetivo.setVelocidad(Math.max(1, objetivo.getVelocidad() - 5));
                    return logBase + "¡La Velocidad de " + objetivo.getNombrePokemon() + " bajó!";
                } else if (desc.contains("ataque")) {
                    objetivo.setAtaque(Math.max(1, objetivo.getAtaque() - 5));
                    return logBase + "¡El Ataque de " + objetivo.getNombrePokemon() + " bajó!";
                } else if (desc.contains("defensa")) {
                    objetivo.setDefensa(Math.max(1, objetivo.getDefensa() - 5));
                    return logBase + "¡La Defensa de " + objetivo.getNombrePokemon() + " bajó!";
                }
                return logBase + "¡Las estadísticas de " + objetivo.getNombrePokemon() + " bajaron!";
            }
        }

        return logBase;
    }


    /**
     * Metodo para obtener el ultimo turno registrado en el historial
     *
     * @return el Turno mas reciente
     */
    public Turno obtenerUltimoTurno() {
        if (historialTurnos.isEmpty()) return null;
        return historialTurnos.get(historialTurnos.size() - 1);
    }

    /**
     * Metodo para tras la muerte de un pokemon  preparar el cambio.
     *
     * @param esJugador Booleano que devuelve si el pokemon muerto es el nuestro
     * @return Si al entrenador le quedan mas pokemon para seguir peleando
     */
    public boolean puedeContinuar(boolean esJugador) {
        if (esJugador) {
            this.setPokemonKOEntrenador(this.getPokemonKOEntrenador() + 1);
            registrarEventoLog("debilitado1");
            return buscarPrimerPokemonVivo(this.getEntrenador()) != null;
        } else {
            // el rival ha perdido un pokemon
            this.setPokemonKORival(this.getPokemonKORival() + 1);
            registrarEventoLog("debilitado2");

            // repartimos experiencia justo ahora que acaba de morir uno
            repartirExperienciaEquipo(this.getPokemonActualRival());

            Pokemon siguienteRival = buscarPrimerPokemonVivo(this.getEntrenadorRival());
            if (siguienteRival != null) {
                this.setPokemonActualRival(siguienteRival);
                return true;
            }
            return false;
        }
    }

    /**
     * Metodo para registrar los logs de forma que se añadan al archivo de Logs
     * @param accion Recibe el string a registrar
     */

    private void registrarEventoLog(String accion) {
        Turno t = new Turno();
        t.setNumeroTurnoActual(this.getHistorialTurnos().size() + 1);
        t.setAccionEntrenador(accion);

        t.setDatosPk1(pokemonActualJugador.getNombrePokemon(), pokemonActualJugador.getNivel(),
                entrenador.getNombreEntrenador(), pokemonActualJugador.getVitalidad() > 0 ? "OK" : "KO");
        t.setDatosPk2(pokemonActualRival.getNombrePokemon(), pokemonActualRival.getNivel(),
                entrenadorRival.getNombreEntrenador(), pokemonActualRival.getVitalidad() > 0 ? "OK" : "KO");

        this.añadirTurno(t);
        if (this.logAsociado != null) {
            this.logAsociado.añadirTurno(t);
        }
    }

    /**
     * Metodo para calcular y transferir los Pokedollares del perdedor al ganador
     *
     * @param ganador El entrenador que ha ganado
     * @param perdedor El entrenador que ha perdido
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
     * Metodo que reparte experiencia a todo el equipo respecto al nivel del rival derrotado
     * @param rivalDerrotado recibe el pokemon rival derrotado para calcula la exp a repartir
     */
    public void repartirExperienciaEquipo(Pokemon rivalDerrotado) {
        Pokemon[] equipo = this.getEntrenador().getEquipoPokemon();
        // recorremos el equipo y por cada pokemon
        for (Pokemon p : equipo) {
            if (p != null && p.getVitalidad() > 0) {
                // formula del enunciado :D
                int expGanada = (p.getNivel() + (rivalDerrotado.getNivel() * 10)) / 4;

                // añadimos la experiencia ganada generada por la formula
                p.ganarExperiencia(expGanada);
            }
        }
    }

    /**
     * Metodo para retirarse del combate
     */
    public void retirarse() {
        System.out.println("EL JUGADOR SE HA RETIRADO");

        // sumamos la derrota al marcador
        this.getEntrenador().sumarDerrota();

        // transferimos los Pokedollares al rival
        transferirPokedollares(this.getEntrenadorRival(), this.getEntrenador());

        System.out.println("Has abandonado el combate. Has perdido " +
                (this.getEntrenador().getNombreEntrenador()) + " Pokedollares.");
    }


    /**
     * Metodo para finalizar combate y establecer ganador
     */
    public void finalizarCombate() {
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
            registrarEventoLog("finGanaCombate");

        }
        // si gana el rival
        else {
            System.out.println("¡DERROTA! Te has quedado sin Pokémon.");
            this.getEntrenador().sumarDerrota();

            // añadimos el dienro al rival
            transferirPokedollares(this.getEntrenadorRival(), this.getEntrenador());

            registrarEventoLog("finPierdeCombate");
        }
    }

    /**
     * Metodo para añadir turno al historial
     * @param nuevoTurno Recibe el turno a añadir al historial
     */
    public void añadirTurno(Turno nuevoTurno) {
        this.historialTurnos.add(nuevoTurno);
    }


}
