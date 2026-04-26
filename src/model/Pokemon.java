package model;

import bd.ConexionBBDD;
import dao.MovimientoDAO;
import javafx.scene.paint.Color;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author Elyass Douma Zouhairi
 * @author Pablo Serrano Conesa
 * @author Isaías Villarreal Méndez
 */
public class Pokemon {

    //
    // Fields
    //

    /**
     * id del Pokemon
     */
    private int idPokemon = 1;
    /**
     * el numero de la pokedex de cada Pokemon
     */
    private int numPokedex;
    /**
     * nombre del Pokemon
     */
    private String nombrePokemon;
    /**
     * mote del Pokemon
     */
    private String motePokemon;

    /**
     * * vitalidad  actual del Pokemon
     */

    private int vitalidad;

    /**
     * * vitalidad  máxima del Pokemon
     */

    // vida maxima del pokemon
    private int vitalidadMaxima;

    /**
     * * ataque del Pokemon
     */

    private int ataque;
    /**
     * defensa del Pokemon
     */
    private int defensa;
    /**
     * ataque especial del Pokemon
     */
    private int ataqueEspecial;
    /**
     * defensa especial del Pokemon
     */
    private int defensaEspecial;
    /**
     * velocidad del Pokemon
     */
    private int velocidad;
    /**
     * * nivel del Pokemon
     */

    private int nivel = 1;
    /**
     * experiencia del Pokemon
     */
    private int experiencia = 0;
    /**
     * conjunto de los 4 movimientos del Pokemon
     */
    private Movimiento[] movimientos = new Movimiento[4];
    /**
     * fertilidad del Pokemon
     */
    private int fertilidad = 5;

    /**
     * sexo del Pokemon
     */

    private Sexo sexo;
    /**
     * tipo principal del Pokemon
     */
    private Tipos tipoPrincipal;
    /**
     * tipo secundario del Pokemon
     */
    private Tipos tipoSecundario;
    /**
     * estado del Pokemon
     */
    private Estados estadoActual = Estados.SANO;

    /// turnos que quedan para que acabe el estado
    private int turnosEstadoRestantes = 0;

    /**
     * objeto que puede o no llevar el Pokemon
     */
    private Objeto objetoEquipado;
    /**
     * indica si es o no shiny el Pokemon
     */
    private Boolean esShiny = false;
    /**
     * Ratio de captura de pokemon
     */
    private Rareza ratioCaptura;
    /**
     * ubicacion de captura del Pokemon
     */
    private UbicacionPokemon ubicacion;
    /**
     * atributo para los estados permanentes
     */
    private Estados estadoPermanente = Estados.SANO;
    /**
     * indica el origen del Pokemon, si es crianza o captura
     */
    private String origen;

    /**
     * el spray de la imagen frontal del pokemon
     */
    private String imgFrontalPokemon;
    /**
     * el spray de la imagen frontal del pokemon en 3D
     */
    private String imgFrontalPokemon3D;
    /**
     * la imagen del spray del Pokemon por detras
     */
    private String imgPosteriorPokemon;
    /**
     * la imagen del spray del Pokemon por detras en 3D
     */
    private String imgPosteriorPokemon3D;
    /**
     * la imagen del spray del Pokemon shiny frontal
     */
    private String imgShinyFrontal;
    /**
     * la imagen del spray del Pokemon shiny frontal en 3D
     */
    private String imgShinyFrontal3D;
    /**
     * la imagen del spray del Pokemon shiny por detras
     */
    private String imgShinyPosterior;
    /**
     * la imagen del spray del Pokemon shiny por detras en 3D
     */
    private String imgShinyPosterior3D;

    /**
     * El nivel de que necesita el pokemon para evolucionar
     */
    private int nivelEvolucion;

    /**
     * El color del pokemon en funcion de su tipo
     */
    private Color color;

    /**
     * Movimiento que el Pokemon quiere aprender pero está esperando confirmación
     */
    private Movimiento movimientoPendiente = null;

    /**
     * Guarda el último movimiento aprendido directamente
     */
    private Movimiento ultimoMovimientoAprendido = null;

    //
    // Constructors
    //

    /**
     * Constructor por defecto de la clase Pokemon
     * */
    public Pokemon() {
    }

    ;

    /**
     * Setter para establecer el ID del pokemon
     * */

    public void setIdPokemon(int newVar) {
        idPokemon = newVar;
    }

    /**
     * Getter para recuperar el ID del pokemon
     * */

    public int getIdPokemon() {
        return idPokemon;
    }

    /**
     * Setter para establecer el numero de pokedex del pokemon
     * */

    public void setNumPokedex(int newVar) {
        numPokedex = newVar;
    }

    /**
     * Getter para recuperar el numero de pokedex del pokemon
     * */

    public int getNumPokedex() {
        return numPokedex;
    }

    /**
     * Setter para establecer el ID del pokemon
     * */

    public void setNombrePokemon(String newVar) {
        nombrePokemon = newVar;
    }

    /**
     * Setter para establecer el ID del pokemon
     * */

    public String getNombrePokemon() {
        return nombrePokemon;
    }

    /**
     * Setter para establecer el ID del pokemon
     * */

    public void setMotePokemon(String newVar) {
        motePokemon = newVar;
    }

    /**
     * Setter para establecer el ID del pokemon
     * */

    public String getMotePokemon() {
        return motePokemon;
    }

    /**
     * Setter para establecer el ID del pokemon
     * */

    /**
     * Cambiado el setVitalidad para que no pueda establecerse mas que la vitalidad
     * maxima en caso de curarse 
     * @param newVar la nueva vitalidad
     */
    public void setVitalidad(int newVar) {
        int maxVitalidadReal = this.getVitalidadMaxima();

        // si esta debilitado no sube la vida
        if (this.estadoActual == Estados.DEBILITADO && newVar > 0) {
            // si esta DEBILITADO, se queda en 0
            // comparamos con el maximo real con opbjeto
            if (newVar >= maxVitalidadReal) {
                this.vitalidad = maxVitalidadReal;
                this.estadoActual = Estados.SANO;
            } else {
                return; // No le dejamos curarse si esta muerto
            }
        }

        // si intentamos curar máas del maximo real,se queda en el
        if (newVar > maxVitalidadReal) {
            this.vitalidad = maxVitalidadReal;
        } else if (newVar <= 0) {
            this.vitalidad = 0;
            this.estadoActual = Estados.DEBILITADO;
        } else {
            this.vitalidad = newVar;
        }
    }

    public int getVitalidad() {
        return vitalidad;
    }

    public void setAtaque(int newVar) {
        ataque = newVar;
    }

    /**
     * Get con logica para tener encuenta los bonus de los objetos y estados
     *
     * @return El ataque final
     */
    public int getAtaque() {
        int atqFinal = this.ataque;
        if (this.objetoEquipado != null) {
            String nombreObj = this.objetoEquipado.getNombreObjeto().toLowerCase();
            // amuleto +30% Atq
            if (nombreObj.contains("amuleto")) {
                atqFinal += (this.ataque * 30 / 100);
            }
            // pesa +20% Atq
            if (nombreObj.contains("pesa")) {
                atqFinal += (this.ataque * 20 / 100);
            }
            // inyeccion +15% Atq
            else if (nombreObj.contains("inyeccion") || nombreObj.contains("inyección")) {
                atqFinal += (this.ataque * 15 / 100);
            }
            // chaleco o monoculo -15% Atq
            else if (nombreObj.contains("chaleco") || nombreObj.contains("monoculo") || nombreObj.contains("monóculo")) {
                atqFinal -= (this.ataque * 15 / 100);
            }
        }

        // Quemado: su ataque se reduce a la mitad
        if (this.estadoActual == Estados.QUEMADO) {
            return atqFinal / 2;
        }
        return atqFinal;
    }

    public void setDefensa(int newVar) {
        defensa = newVar;
    }

    /**
     * Get con logica para tener encuenta los bonus de los objetos
     *
     * @return La defensa final
     */
    public int getDefensa() {
        int defFinal = this.defensa;
        if (this.objetoEquipado != null) {
            String nombreObj = this.objetoEquipado.getNombreObjeto().toLowerCase();
            // coraza +30% Def
            if (nombreObj.contains("coraza")) {
                defFinal += (this.defensa * 30 / 100);
            }
            // pesa o chaleco  +20% Def
            else if (nombreObj.contains("pesa") || nombreObj.contains("chaleco")) {
                defFinal += (this.defensa * 20 / 100);
            }
            // monoculo o amuleo -15% Def
            else if (nombreObj.contains("monoculo") || nombreObj.contains("monóculo") || nombreObj.contains("amuleto")) {
                defFinal -= (this.defensa * 15 / 100);
            }
            // pluma -20% Def
            else if (nombreObj.contains("pluma") || nombreObj.contains("gafas")) {
                defFinal -= (this.defensa * 20 / 100);
            }
        }
        return defFinal;
    }

    public void setAtaqueEspecial(int newVar) {
        ataqueEspecial = newVar;
    }

    /**
     * Get con logica para tener encuenta los bonus de los objetos y estados
     *
     * @return El ataque especial final
     */
    public int getAtaqueEspecial() {
        int atqEspFinal = this.ataqueEspecial;
        if (this.objetoEquipado != null) {
            String nombreObj = this.objetoEquipado.getNombreObjeto().toLowerCase();
            // pilas +30% AtqEsp
            if (nombreObj.contains("pilas")) {
                atqEspFinal += (this.ataqueEspecial * 30 / 100);
            }
            // gafas  +20% AtqEsp
            else if (nombreObj.contains("gafas")) {
                atqEspFinal += (this.ataqueEspecial * 20 / 100);
            }
            // inyeccion o monoculo +15% AtqEsp
            else if (nombreObj.contains("inyeccion") || nombreObj.contains("inyección") || nombreObj.contains("monoculo") || nombreObj.contains("monóculo")) {
                atqEspFinal += (this.ataqueEspecial * 15 / 100);
            }
        }
        // Helado, su ataque especial se reduce a la mitad
        if (this.estadoActual == Estados.HELADO) {
            return atqEspFinal / 2;
        }
        return atqEspFinal;
    }

    public void setDefensaEspecial(int newVar) {
        defensaEspecial = newVar;
    }

    /**
     * Get con logica para tener encuenta los bonus de los objetos
     *
     * @return La defensa especial final
     */
    public int getDefensaEspecial() {
        int defEspFinal = this.defensaEspecial;
        if (this.objetoEquipado != null) {
            String nombreObj = this.objetoEquipado.getNombreObjeto().toLowerCase();
            // chaleco +20% DefEsp
            if (nombreObj.contains("chaleco")) {
                defEspFinal += (this.defensaEspecial * 20 / 100);
            }
            // monoculo +15% DefEsp
            else if (nombreObj.contains("monoculo") || nombreObj.contains("monóculo")) {
                defEspFinal += (this.defensaEspecial * 15 / 100);
            }
            // amuleto -15% DefEsp
            else if (nombreObj.contains("amuleto")) {
                defEspFinal -= (this.defensaEspecial * 15 / 100);
            }
            // pluma o pilas -20% DefEsp
            else if (nombreObj.contains("pluma") || nombreObj.contains("pilas")) {
                defEspFinal -= (this.defensaEspecial * 20 / 100);
            }
        }
        return defEspFinal;
    }

    public void setVelocidad(int newVar) {
        velocidad = newVar;
    }

    /**
     * Get con logica para tener encuenta los bonus de los objetos y estados
     *
     * @return La velocidad final
     */
    public int getVelocidad() {
        int velFinal = this.velocidad;
        if (this.objetoEquipado != null) {
            String nombreObj = this.objetoEquipado.getNombreObjeto().toLowerCase();
            // pluma  +30% Vel
            if (nombreObj.contains("pluma")) {
                velFinal += (this.velocidad * 30 / 100);
            }
            // chaleco o baston -15% Vel
            else if (nombreObj.contains("chaleco") || nombreObj.contains("bastón") || nombreObj.contains("baston") || nombreObj.contains("coraza") || nombreObj.contains("inyeccion") || nombreObj.contains("inyección")) {
                velFinal -= (this.velocidad * 15 / 100);
            }
            // pesa -20% Vel
            else if (nombreObj.contains("pesa")) {
                velFinal -= (this.velocidad * 20 / 100);
            }
        }

        // Paralizado: su velocidad se reduce a la mitad
        if (this.estadoActual == Estados.PARALIZADO) {
            return velFinal / 2;
        }
        return velFinal;
    }

    public void setNivel(int newVar) {
        nivel = newVar;
    }

    public int getNivel() {
        return nivel;
    }

    public void setExperiencia(int newVar) {
        experiencia = newVar;
    }

    public int getExperiencia() {
        return experiencia;
    }

    public void setMovimientos(Movimiento[] newVar) {
        movimientos = newVar;
    }

    public Movimiento[] getMovimientos() {
        return movimientos;
    }

    public void setFertilidad(int newVar) {
        fertilidad = newVar;
    }

    public int getFertilidad() {
        return fertilidad;
    }

    public void setSexo(Sexo newVar) {
        sexo = newVar;
    }

    public Sexo getSexo() {
        return sexo;
    }

    public void setTipoPrincipal(Tipos newVar) {
        tipoPrincipal = newVar;
    }

    public Tipos getTipoPrincipal() {
        return tipoPrincipal;
    }

    public void setTipoSecundario(Tipos newVar) {
        tipoSecundario = newVar;
    }

    public Tipos getTipoSecundario() {
        return tipoSecundario;
    }

    public void setEstadoActual(Estados newVar) {
        estadoActual = newVar;
    }

    public Estados getEstadoActual() {
        return estadoActual;
    }

    public void setTurnosEstadoRestantes(int t) {
        this.turnosEstadoRestantes = t;
    }

    public int getTurnosEstadoRestantes() {
        return turnosEstadoRestantes;
    }

    /**
     * Set con la logica para corregir el fallo con las vitalidades maximas y actuales de los pokemon al equipar el objeto
     * @param newVar recibe el objeto
     * */
    public void setObjetoEquipado(Objeto newVar) {
        // guardamos la vida maxima antes del objeto
        int maxAntes = this.getVitalidadMaxima();

        // hacemos la asignacion normal
        this.objetoEquipado = newVar;

        // calculamos la nueva vida maxima
        int maxDespues = this.getVitalidadMaxima();

        // ajustamos la vida actual a la maxima al tener el item equipado
        if (this.vitalidad >= maxAntes && maxDespues > maxAntes) {
            this.vitalidad = maxDespues;
        }
    }

    public Objeto getObjetoEquipado() {
        return objetoEquipado;
    }

    public void setEsShiny(Boolean newVar) {
        esShiny = newVar;
    }

    public Boolean getEsShiny() {
        return esShiny;
    }

    public void setRatioCaptura(Rareza newVar) {
        ratioCaptura = newVar;
    }

    public Rareza getRatioCaptura() {
        return ratioCaptura;
    }

    public void setUbicacion(UbicacionPokemon newVar) {
        ubicacion = newVar;
    }

    public UbicacionPokemon getUbicacion() {
        return ubicacion;
    }

    public void setEstadoPermanente(Estados newVar) {
        estadoPermanente = newVar;
    }

    public Estados getEstadoPermanente() {
        return estadoPermanente;
    }

    public void setOrigen(String newVar) {
        origen = newVar;
    }

    public String getOrigen() {
        return origen;
    }

    public void setImgFrontalPokemon(String newVar) {
        imgFrontalPokemon = newVar;
    }

    public String getImgFrontalPokemon() {
        return imgFrontalPokemon;
    }

    public void setImgFrontalPokemon3D(String newVar) {
        imgFrontalPokemon3D = newVar;
    }

    public String getImgFrontalPokemon3D() {
        return imgFrontalPokemon3D;
    }

    public void setImgPosteriorPokemon(String newVar) {
        imgPosteriorPokemon = newVar;
    }

    public String getImgPosteriorPokemon() {
        return imgPosteriorPokemon;
    }

    public void setImgPosteriorPokemon3D(String newVar) {
        imgPosteriorPokemon3D = newVar;
    }

    public String getImgPosteriorPokemon3D() {
        return imgPosteriorPokemon3D;
    }

    public String getImgShinyFrontal() {
        return imgShinyFrontal;
    }

    public void setImgShinyFrontal(String imgShinyFrontal) {
        this.imgShinyFrontal = imgShinyFrontal;
    }

    public String getImgShinyFrontal3D() {
        return imgShinyFrontal3D;
    }

    public void setImgShinyFrontal3D(String imgShinyFrontal3D) {
        this.imgShinyFrontal3D = imgShinyFrontal3D;
    }

    public String getImgShinyPosterior() {
        return imgShinyPosterior;
    }

    public void setImgShinyPosterior(String imgShinyPosterior) {
        this.imgShinyPosterior = imgShinyPosterior;
    }

    public String getImgShinyPosterior3D() {
        return imgShinyPosterior3D;
    }

    public void setImgShinyPosterior3D(String imgShinyPosterior3D) {
        this.imgShinyPosterior3D = imgShinyPosterior3D;
    }

    public void setNivelEvolucion(int newVar) {
        nivelEvolucion = newVar;
    }

    public int getNivelEvolucion() {
        return nivelEvolucion;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    // para usar en bd y enrtrenar
    public int getBaseVitalidadMaxima() {
        return this.vitalidadMaxima;
    }

    public int getBaseAtaque() {
        return this.ataque;
    }

    public int getBaseDefensa() {
        return this.defensa;
    }

    public int getBaseAtaqueEspecial() {
        return this.ataqueEspecial;
    }

    public int getBaseDefensaEspecial() {
        return this.defensaEspecial;
    }

    public int getBaseVelocidad() {
        return this.velocidad;
    }

    /**
     * Get con logica para tener encuenta los bonus de los objetos y estados
     *
     * @return La vitalidad maxima final
     */
    public int getVitalidadMaxima() {
        int vitMaxFinal = this.vitalidadMaxima;
        if (this.objetoEquipado != null) {
            String nombreObj = this.objetoEquipado.getNombreObjeto().toLowerCase();
            // baston +20% HP Máximo
            if (nombreObj.contains("bastón") || nombreObj.contains("baston")) {
                vitMaxFinal += (this.vitalidadMaxima * 20 / 100);
            }
        }
        return vitMaxFinal;
    }

    public void setVitalidadMaxima(int vitalidadMaxima) {
        this.vitalidadMaxima = vitalidadMaxima;
    }

    public Movimiento getMovimientoPendiente() {
        return movimientoPendiente;
    }

    public void setMovimientoPendiente(Movimiento movimientoPendiente) {
        this.movimientoPendiente = movimientoPendiente;
    }

    public Movimiento getUltimoMovimientoAprendido() {
        return ultimoMovimientoAprendido;
    }

    public void setUltimoMovimientoAprendido(Movimiento ultimoMovimientoAprendido) {
        this.ultimoMovimientoAprendido = ultimoMovimientoAprendido;
    }

    //
    // Other methods
    //

    /**
     * Metodo para ganar experiencia despues de cada combate va acumulando la
     * sobrante y establece el nuevo limite de experiencia necesaria para el
     * siguiente nivel
     *
     * @param puntos un numero con la cantidad de experiencia que gana
     */
    public void ganarExperiencia(int puntos) {
        // sumamos la experiencia nueva a la que ya teniamos
        this.setExperiencia(this.getExperiencia() + puntos);

        // calculamos cuanto necesita para subir (10 * nivel actual)
        int expNecesaria = 10 * this.getNivel();
        // comprobamos si ha llegado al limite para subir de nivel
        // usamos un while por si gana tanta experiencia que sube varios niveles de
        // golpe
        while (this.getExperiencia() >= expNecesaria) {

            // restamos la experiencia consumida para subir este nivel
            this.setExperiencia(this.getExperiencia() - expNecesaria);

            // llamamos al metodo que ya teniamos para aumentar los stats y el nivel
            this.subirNivel();

            // actualizamos el nuevo limite de experiencia
            expNecesaria = 10 * this.getNivel();

            System.out.println("¡Subida de nivel! Ahora eres nivel: " + this.getNivel());
        }
    }

    /**
     * Metodo para subir nivel, sube de nivel al pokemon y genera la subida de stats
     * entre 1 y 5 por nivel
     */
    public void subirNivel() {
        this.setNivel(this.getNivel() + 1);
        Random r = new Random();

        // calculamos lo que aumenta cada stat
        int aumentoVit = r.nextInt(5) + 1;

        // actualizamos la Vitalidad Maxima
        this.vitalidadMaxima = this.vitalidadMaxima + aumentoVit;

        // al subir de nivel el pokemon se cura
        if (this.estadoActual != Estados.DEBILITADO) {
            this.setVitalidad(this.getVitalidadMaxima());
        }

        // actualizamos el resto de stats
        this.ataque = this.ataque + (r.nextInt(5) + 1);
        this.defensa = this.defensa + (r.nextInt(5) + 1);
        this.ataqueEspecial = this.ataqueEspecial + (r.nextInt(5) + 1);
        this.defensaEspecial = this.defensaEspecial + (r.nextInt(5) + 1);
        this.velocidad = this.velocidad + (r.nextInt(5) + 1);

        // metodo para que aprenda ataques cada 3 niveles,
        if (this.nivel % 3 == 0) {
            try {
                // abrimos conexion a bd
                ConexionBBDD conector = new ConexionBBDD();
                Connection con = conector.getConexion();
                MovimientoDAO movDao = new MovimientoDAO(con);

                // buscamos todos los movimientos de su tipo principal
                List<Movimiento> movimientosDisponibles = movDao.listarPorTipo(this.tipoPrincipal);

                if (movimientosDisponibles != null && !movimientosDisponibles.isEmpty()) {
                    // elegimos uno al azar
                    Random rand = new Random();
                    Movimiento nuevoMov = movimientosDisponibles.get(rand.nextInt(movimientosDisponibles.size()));

                    // comprobamos que no lo tenga ya aprendido
                    boolean yaLoSabe = false;
                    for (Movimiento m : this.movimientos) {
                        if (m != null && m.getNombreMovimiento().equals(nuevoMov.getNombreMovimiento())) {
                            yaLoSabe = true;
                            break;
                        }
                    }

                    // si no lo sabe llamamos aprenderMovimiento
                    if (!yaLoSabe) {
                        this.aprenderMovimiento(nuevoMov);
                    } else {
                        System.out.println(this.nombrePokemon + " ya conoce " + nuevoMov.getNombreMovimiento() + "...");
                    }
                }
                con.close();

            } catch (Exception e) {
                System.out.println(
                        "Error al intentar aprender movimiento en nivel " + this.nivel + ": " + e.getMessage());
            }
        }
    }

    /**
     * Comprueba si el Pokemon ha alcanzado el nivel necesario para evolucionar.
     *
     * @return True si evoluciona, false en caso contrario.
     */
    public boolean comprobarEvolucion() {
        if (this.nivelEvolucion > 0 && this.nivel >= this.nivelEvolucion) {
            System.out.println("¡Atención! " + this.nombrePokemon + " está evolucionando...");
            return true;
        }
        return false;
    }

    /**
     * metodo atacar del Pokemon con salida (CONSTANTES): NEUTRO = VENTAJA X2
     * DOBLEVENTAJA x4 DESVENTAJA 1/2 DOBLEDESVENTAJA 1/4 * y comprueba si el tipo
     * de ataque es del mismo tipo que el pokemon que lo realiza x1.5 *
     * @param movimiento recibe el movimiento que se va a realizar
     * @param pokemon recibe el pokemon que recibe el ataque
     * @return Una cadena que indica la eficacia del golpe (VENTAJA, DESVENTAJA, INMUNE, NEUTRO)
     */
    /**
     * Ejecuta el ataque, aplica estados, calcula daño físico/especial y devuelve la
     * eficacia.
     */
    public String atacar(Movimiento movimiento, Pokemon pokemonDefensor) {

        String categoria = movimiento.getCategoriaDano();
        // aplicar estados
        if (movimiento.getEstadoAplicado() != null && movimiento.getEstadoAplicado() != Estados.SANO) {
            if (pokemonDefensor.getEstadoActual() == Estados.SANO) {
                pokemonDefensor.setEstadoActual(movimiento.getEstadoAplicado());

                Random r = new Random();
                if (movimiento.getEstadoAplicado() == Estados.DORMIDO) {
                    pokemonDefensor.setTurnosEstadoRestantes(r.nextInt(3) + 1); // 1-3 turnos
                } else {
                    pokemonDefensor.setTurnosEstadoRestantes(0);
                }
                System.out.println("¡Oh no! " + pokemonDefensor.getNombrePokemon() + " ahora está "
                        + movimiento.getEstadoAplicado());
            }
        }

        // filtro movimientos estado (sin potencia)
        if (categoria == null || categoria.equalsIgnoreCase("Estado") || movimiento.getPotencia() <= 0) {
            return "NEUTRO";
        }

        // calculo multiplicaciones tipo
        double m1 = TablaTipos.obtenerEficacia(movimiento.getTipo(), pokemonDefensor.getTipoPrincipal());
        double m2 = (pokemonDefensor.getTipoSecundario() != null)
                ? TablaTipos.obtenerEficacia(movimiento.getTipo(), pokemonDefensor.getTipoSecundario())
                : 1.0;

        double multiplicadorTipos = m1 * m2;

        // bonus por mismo tipo (STAB x1.5)
        double potenciaFinal = movimiento.getPotencia();
        if (movimiento.getTipo() == this.getTipoPrincipal() || movimiento.getTipo() == this.getTipoSecundario()) {
            potenciaFinal *= 1.5;
        }

        // calculo daño base (fisico vs especial)
        double danioBase = 0;
        if (categoria.equalsIgnoreCase("Fisico")) {
            danioBase = (this.getAtaque() * potenciaFinal) / (double) pokemonDefensor.getDefensa();
        } else {
            // por defecto tratamos como especial si no es fisico
            danioBase = (this.getAtaqueEspecial() * potenciaFinal) / (double) pokemonDefensor.getDefensaEspecial();
        }

        // daño final
        int danioFinal = (int) (danioBase * multiplicadorTipos);

        // Si no es inmune (x0), siempre debe quitar al menos 1 PS
        if (danioFinal < 1 && multiplicadorTipos > 0) {
            danioFinal = 1;
        }

        pokemonDefensor.setVitalidad(pokemonDefensor.getVitalidad() - danioFinal);

        // retorno eficacia (Para los textos del Log)
        if (multiplicadorTipos >= 2.0) {
            return "VENTAJA"; // el controlador pondrá "¡Es muy eficaz!"
        }
        if (multiplicadorTipos > 0 && multiplicadorTipos < 1.0) {
            return "DESVENTAJA"; // el controlador pondrá "No es muy eficaz..."
        }
        if (multiplicadorTipos == 0) {
            return "INMUNE"; // el controlador pondrá "No afecta a..."
        }

        return "NEUTRO";
    }

    /**
     * Metodo para recuperar un procentajde de vida aleatorio en 10% un maximo de
     * 50%
     */
    public void descansar() {
        Random r = new Random();
        // generamos un porcentaje aleatorio entre 10 y 50
        int porcentaje = r.nextInt(41) + 10;

        // calculamos la curacion basada en la Vitalidad Maxima
        int curacion = (this.vitalidadMaxima * porcentaje) / 100;

        System.out.println("INFO: " + this.nombrePokemon + " descansa y recupera un " + porcentaje + "% de vida.");

        // aplicamos la curacion usando el setter para no sobrepasar el maximo
        this.setVitalidad(this.vitalidad + curacion);
    }

    /**
     * Intenta añadir un nuevo movimiento al pokemon si tiene un hueco libre
     * actualiza la memoria correctamente
     * @param nuevoMovimiento recibe el nuevo movimiento a aprender
     * @return True si lo ha aprendido, false si ya tiene 4
     */
    public boolean aprenderMovimientoEnHuecoVacio(Movimiento nuevoMovimiento) {
        if (this.movimientos == null) {
            this.movimientos = new Movimiento[4];
        }

        for (int i = 0; i < 4; i++) {
            if (this.movimientos[i] == null) {
                this.movimientos[i] = nuevoMovimiento;
                return true; // se lo guarda y sale
            }
        }
        return false; // No hay hueco
    }

    /**
     * Metodo para que el pokemon pueda aprender un movimiento cada 3 niveles
     * @param nuevoMovimiento recibe un array de movimientos y puede aprender un ataque
     * de entre todos los del array *
     */
    public void aprenderMovimiento(Movimiento nuevoMovimiento) {
        // comprobamos si hay hueco en el array de 4 movimientos y si hay lo añadimos
        boolean aprendido = false;
        for (int i = 0; i < movimientos.length; i++) {
            if (movimientos[i] == null) {
                movimientos[i] = nuevoMovimiento;
                aprendido = true;
                this.ultimoMovimientoAprendido = nuevoMovimiento;
                System.out.println("INFO: " + nombrePokemon + " ha aprendido " + nuevoMovimiento.getNombreMovimiento());
                break;
            }
        }

        // Si no hay hueco, lo guardamos en la recámara para que la interfaz pregunte
        if (!aprendido) {
            this.movimientoPendiente = nuevoMovimiento;
            System.out.println("INFO: " + nombrePokemon + " quiere aprender " + nuevoMovimiento.getNombreMovimiento()
                    + ", pero debe olvidar uno.");
        }
    }

    /**
     * Metodo para reemplazar un movimiento equipado por otro
     * @param indiceNuevo donde ira el nuevo movimiento
     * @param nuevoMovimiento nuevo movimiento a aprender
     */
    public void reemplazarMovimiento(int indiceNuevo, Movimiento nuevoMovimiento) {
        if (indiceNuevo >= 0 && indiceNuevo < 4) {
            System.out.println(this.nombrePokemon + " olvidó " + movimientos[indiceNuevo].getNombreMovimiento()
                    + " y aprendió " + nuevoMovimiento.getNombreMovimiento());
            this.movimientos[indiceNuevo] = nuevoMovimiento;
            this.movimientoPendiente = null; // vaciamos el pendeinte
        }
    }

    /**
     * Método para limpiar todos los estados temporales que no siguen fuera del
     * combate
     */
    public void limpiarEstadosTemporales() {
        if (this.estadoActual == Estados.CONFUSO || this.estadoActual == Estados.AMEDENTRADO
                || this.estadoActual == Estados.ENAMORADO || this.estadoActual == Estados.APRESADO
                || this.estadoActual == Estados.MALDITO || this.estadoActual == Estados.DRENADORAS
                || this.estadoActual == Estados.CANTOMORTAL || this.estadoActual == Estados.CENTROATENCION
                || this.estadoActual == Estados.SOMNOLIENTO) {

            this.setEstadoActual(Estados.SANO);
            this.setTurnosEstadoRestantes(0); // reseteo contadotr

            // se limpia el estado actual antes pero si tiene uno permantente se le pone
            if (this.estadoActual == Estados.SANO && this.estadoPermanente != Estados.SANO) {
                this.estadoActual = this.estadoPermanente;
            }
        }
    }

    /**
     * Método para cambiar el color de fonde respecto al tipo del pokemon que
     * aparece
     */
    public void cambiarColor() {
        String tipo1 = getTipoPrincipal().toString();
        if (tipo1.equals("ACERO")) {
            setColor(Color.SILVER);
        } else if (tipo1.equals("AGUA")) {
            setColor(Color.BLUE);
        } else if (tipo1.equals("BICHO")) {
            setColor(Color.YELLOWGREEN);
        } else if (tipo1.equals("DRAGÓN")) {
            setColor(Color.INDIGO);
        } else if (tipo1.equals("ELÉCTRICO")) {
            setColor(Color.YELLOW);
        } else if (tipo1.equals("FANTASMA")) {
            setColor(Color.PURPLE);
        } else if (tipo1.equals("FUEGO")) {
            setColor(Color.RED);
        } else if (tipo1.equals("HADA")) {
            setColor(Color.PINK);
        } else if (tipo1.equals("HIELO")) {
            setColor(Color.CYAN);
        } else if (tipo1.equals("LUCHA")) {
            setColor(Color.BROWN);
        } else if (tipo1.equals("NORMAL")) {
            setColor(Color.LIGHTGRAY);
        } else if (tipo1.equals("PLANTA")) {
            setColor(Color.GREEN);
        } else if (tipo1.equals("PSÍQUICO")) {
            setColor(Color.MAGENTA);
        } else if (tipo1.equals("ROCA")) {
            setColor(Color.TAN);
        } else if (tipo1.equals("SINIESTRO")) {
            setColor(Color.DARKSLATEGRAY);
        } else if (tipo1.equals("TIERRA")) {
            setColor(Color.CHOCOLATE);
        } else if (tipo1.equals("VENENO")) {
            setColor(Color.VIOLET);
        } else if (tipo1.equals("VOLADOR")) {
            setColor(Color.SKYBLUE);
        } else {
            setColor(Color.BLACK);
        }
    }

    /**
     * Metodo que genera el sexo correcto para un Pokemon segun su numero de
     * Pokedex. Tiene en cuenta las especies que son 100% machos, 100% hembras o sin
     * genero.
     *
     * @param numPokedex El numero de la Pokedex del Pokemon a generar
     * @return El Sexo correspondiente (MACHO, HEMBRA o NEUTRO)
     */
    public static Sexo generarSexoPokemon(int numPokedex) {

        // IDs de especies que SOLO pueden ser HEMBRAS
        // (Nidoran F, Nidorina, Nidoqueen, Chansey, Kangaskhan, Jynx, Smoochum,
        // Miltank, Blissey)
        int[] soloHembras = {29, 30, 31, 113, 115, 124, 238, 241, 242};

        // IDs de especies que SOLO pueden ser MACHOS
        // (Nidoran M, Nidorino, Nidoking, Hitmonlee, Hitmonchan, Tauros, Tyrogue,
        // Hitmontop)
        int[] soloMachos = {32, 33, 34, 106, 107, 128, 236, 237};

        // IDs de especies SIN GENERO (NEUTRO)
        // (Magnemite, Magneton, Voltorb, Electrode, Staryu, Starmie, Ditto, Porygon,
        // Porygon2, Unown y Legendarios)
        int[] neutros = {81, 82, 100, 101, 120, 121, 132, 137, 233, 201, 144, 145, 146, 150, 151, 243, 244, 245, 249,
                250, 251};

        // comprobamos si el pokemon pertenece a la lista de Hembras
        for (int id : soloHembras) {
            if (numPokedex == id) {
                return Sexo.HEMBRA;
            }
        }

        // comprobamos si el pokemon pertenece a la lista de Machos
        for (int id : soloMachos) {
            if (numPokedex == id) {
                return Sexo.MACHO;
            }
        }

        // comprobamos si el pokemon pertenece a la lista de Neutros
        for (int id : neutros) {
            if (numPokedex == id) {
                return Sexo.NEUTRO;
            }
        }

        // Si no esta en ninguna lista especial, generamos Macho o Hembra al 50%
        java.util.Random r = new java.util.Random();
        if (r.nextBoolean()) {
            return Sexo.MACHO;
        } else {
            return Sexo.HEMBRA;
        }
    }

    /**
     * Método a prueba de balas: Limpia las carpetas raras de la base de datos,
     * y si la base de datos está vacía (NULL), deduce el nombre automáticamente
     * @param esFrontal recibe si es o no frontal
     * @param es3D recibe si es o no 3D
     * @return Devuelve la ruta de la imagen dependiendo de si es o no 3D y si es o no frontal
     */
    public String getRutaImagenActual(boolean esFrontal, boolean es3D) {
        String archivoRaw = "";

        //obtener el texto como esta en la BD
        if (this.esShiny) {
            if (es3D) {
                archivoRaw = esFrontal ? imgShinyFrontal3D : imgShinyPosterior3D;
            } else {
                archivoRaw = esFrontal ? imgShinyFrontal : imgShinyPosterior;
            }
        } else {
            if (es3D) {
                archivoRaw = esFrontal ? imgFrontalPokemon3D : imgPosteriorPokemon3D;
            } else {
                archivoRaw = esFrontal ? imgFrontalPokemon : imgPosteriorPokemon;
            }
        }

        //limpiar la ruta
        String nombreLimpio = "";
        if (archivoRaw != null && !archivoRaw.trim().isEmpty()) {
            int index = Math.max(archivoRaw.lastIndexOf('/'), archivoRaw.lastIndexOf('\\'));
            if (index >= 0) {
                nombreLimpio = archivoRaw.substring(index + 1);
            } else {
                nombreLimpio = archivoRaw;
            }
        }


        if (nombreLimpio == null || nombreLimpio.trim().isEmpty()) {
            String extension = es3D ? ".gif" : ".png";
            nombreLimpio = this.nombrePokemon.toLowerCase() + extension;
        }

        //montar la ruta correcta 
        if (this.esShiny) {
            if (es3D) {
                return "shiny/3DModelos/" + nombreLimpio;
            } else {
                return "transparent/shiny/" + nombreLimpio;
            }
        } else {
            if (es3D) {
                return "transparent/3DAnimados/" + nombreLimpio;
            } else {
                return "transparent/" + nombreLimpio;
            }
        }
    }

}