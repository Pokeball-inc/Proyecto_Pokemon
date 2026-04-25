package controller;

import bd.ConexionBBDD;
import dao.CapturaDao;
import dao.MovimientoDAO;
import dao.PokemonDAO;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

import java.io.File;
import java.net.URL;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import model.*;


public class EquipoController implements Initializable {

    private CapturaDao capturaDao = new CapturaDao();
    private Connection con;

    private Entrenador entrenadorActual = Sesion.entrenadorLogueado;
    @FXML
    private TilePane cajaPokemon;
    @FXML
    private StackPane sonidoPokemon;
    @FXML
    private ImageView fotoPokemon;
    @FXML
    private Text motePokemon;
    @FXML
    private Text nombrePokemon;
    @FXML
    private ImageView tipo1Pokemon;
    @FXML
    private ImageView tipo2Pokemon;
    @FXML
    private ImageView sexoPokemon;
    @FXML
    private ImageView infoPokemon;
    @FXML
    private Text atkPokemon;
    @FXML
    private Text hpPokemonActual;
    @FXML
    private Text hpPokemonMaxima;
    @FXML
    private Text atkPokemonEspecial;
    @FXML
    private Text defensaPokemon;
    @FXML
    private Text defensaPokemonEspecial;
    @FXML
    private Text shinyPokemon;
    @FXML
    private Text velocidadPokemon;
    @FXML
    private Text fertilidadPokemon;
    @FXML
    private Text textoMoverEquipo;
    @FXML
    private Rectangle rectMoverEquipo;
    @FXML
    private Pane panelDetalles;
    private MediaPlayer mediaPlayer;
    private Pokemon pokemonSeleccionado;
    @FXML
    private ImageView curarEquipo;
    

    private List<Pokemon> ListaPokemon = new ArrayList<>();

    public void initialize(URL location, ResourceBundle resources) {

        // Limpiar la lista, por si acaso (No paran de clonarse 🥀)
        ListaPokemon.clear();

        // Añadir los pokemons del equipo y la caja a lista de pokemons totales
        if (entrenadorActual != null) {

            //instanciamos la conexion
            ConexionBBDD conexion = new ConexionBBDD();
            this.con = conexion.getConexion();

            if (entrenadorActual.getEquipoPokemon() != null) {

                for (Pokemon p : entrenadorActual.getEquipoPokemon()) {
                    if (p != null) ListaPokemon.add(p);
                }
            }

            if (entrenadorActual.getCajaPokemon() != null) {
                ListaPokemon.addAll(entrenadorActual.getCajaPokemon());
            }

            if (!ListaPokemon.isEmpty()) {
                System.out.println("Equipo cargado para: " + entrenadorActual.getNombreEntrenador());
                cargarInventario();
            } else {
                System.out.println("El entrenador no tiene pokemons.");
                panelDetalles.setVisible(false);
            }
        }
    }

    // Caja
    private void cargarInventario() {
        // Limpiamos el TilePane antes de cargar para evitar duplicados visuales
        cajaPokemon.getChildren().clear();

        for (Pokemon p : ListaPokemon) {
            pokemonSeleccionado = p;
            try {
                // Crear una Celda Vbox con v: -2, para que el texto y la imagen no estén tan alejados -- Cambiado

                // Crear un StackPane, los VBox apilan los elementos y no deja superponer "imágenes" entre sí, la marca
                // de pokeball no va

                VBox celdaPokemon = new VBox(-2);
                // Alineamos la celda
                celdaPokemon.setAlignment(Pos.CENTER);

                // Darle estilo a la celda:
                celdaPokemon.setStyle(
                        "-fx-background-color: rgba(255, 255, 255, 0.4);" +
                                "-fx-background-radius: 15;" +
                                "-fx-cursor: hand;" +
                                "-fx-padding: 10;"
                );

                /**
                 * Si el pokemon está dentro del Equipo, entonces le generamos un poequeño ImageView en la parte
                 * superior derecha, de modo que se distingan claramente
                 * del resto de pokemons de la caja */

                boolean pokemonEnEquipo = Arrays.asList(entrenadorActual.getEquipoPokemon()).contains(pokemonSeleccionado);

                if (pokemonEnEquipo) {
                    File marcaEquipoArc = new File("imgs/Equipo/marcaEquipo.png");
                    ImageView marcaEquipo = new ImageView(new Image(marcaEquipoArc.toURI().toString()));

                    /**
                     * Crear un HBox para meter la marca del Equipo, de esta forma sale arriba a la derecha, e ignora
                     * las limitaciones del Vbox
                     * */
                    HBox contenedorMarcaEquipo = new HBox(marcaEquipo);
                    contenedorMarcaEquipo.setAlignment(Pos.TOP_RIGHT);

                    marcaEquipo.setFitWidth(15);
                    marcaEquipo.setFitHeight(15);
                    marcaEquipo.setPreserveRatio(true);

                    celdaPokemon.getChildren().add(contenedorMarcaEquipo);
                }


                // Crear el ImageView

                // En función del Tipo de imagen que desee el usario

                String rutaImagen = "";

                if (Sesion.vista2D) {

                    rutaImagen = "imgs/Pokemons/sprites/crystal/transparent/" + this.pokemonSeleccionado.getImgFrontalPokemon();

                } else {
                    rutaImagen = "imgs/Pokemons/sprites/crystal/transparent/" + this.pokemonSeleccionado.getImgFrontalPokemon3D();
                }

                String rutaImagenAdaptada = new File(rutaImagen).toURI().toString();
                ImageView vistaImagen = new ImageView(new Image(rutaImagenAdaptada));

                vistaImagen.setFitWidth(128);
                vistaImagen.setFitHeight(128);
                vistaImagen.setPreserveRatio(false);

                // Crear el label y colocarlo debajo de la imagen, el Vbox lo hacía solo, pero ahora con el StackPane no
                if (p.getMotePokemon() != null) {
                    Label nombre = new Label(p.getMotePokemon() + " Nvl: " + p.getNivel());
                    nombre.setStyle("-fx-text-fill: black; -fx-font-weight: bold;");
                    celdaPokemon.getChildren().addAll(vistaImagen, nombre);
                } else {
                    Label nombre = new Label(p.getNombrePokemon() + " Nvl: " + p.getNivel());
                    nombre.setStyle("-fx-text-fill: black; -fx-font-weight: bold;");

                    // Meter la imagen y el label
                    celdaPokemon.getChildren().addAll(vistaImagen, nombre);
                }


                // Obviamente, no podía faltar un efecto de pasar el ratón e.e
                // la celda brilla al pasar el ratón por encima
                celdaPokemon.setOnMouseEntered(e ->
                        celdaPokemon.setStyle("-fx-background-color: rgba(255, 255, 255, 0.7); -fx-background-radius: 15; -fx-cursor: hand; -fx-padding: 10;")
                );
                celdaPokemon.setOnMouseExited(e ->
                        celdaPokemon.setStyle("-fx-background-color: rgba(255, 255, 255, 0.4); -fx-background-radius: 15; -fx-cursor: hand; -fx-padding: 10;")
                );


                // Al darle click, cargar el pokemon en el panel de visualizacion
                celdaPokemon.setOnMouseClicked(e -> {
                    try {
                        System.out.println("Has seleccionado a: " + p.getNombrePokemon());
                        mostrarDetallesPokemon(p);

                    } catch (Exception ex) {
                        System.out.println(ex.getMessage());

                    }


                });

                // Añadir el Tile al vbox
                cajaPokemon.getChildren().add(celdaPokemon);

                // Añadir margen
                cajaPokemon.setHgap(20);
                cajaPokemon.setVgap(20);



            } catch (Exception e) {
                System.out.println("Error cargando imagen de pokemon: " + e.getMessage());
            }
        }
        // Mostrar el primer pokemon de la lista por defecto, para que no salga el charizard ese xd

        mostrarDetallesPokemon(ListaPokemon.getFirst()); //Supongo que GetFirst es mejor que get(0)
    }

    // Metodo para mostrar detalles de pokemons

    private void mostrarDetallesPokemon(Pokemon p) {
        if (p == null) return;

        try {
            sonidoPokemon.setVisible(false);
            // Actualizamos la referencia y nos aseguramos de que tenga color
            pokemonSeleccionado = p;
            pokemonSeleccionado.cambiarColor();

            // buscamos el archivo en tu carpeta de sprites
            // En función del Tipo de imagen que desee el usuario

            String rutaImagen = "";

            if (Sesion.vista2D) {

                rutaImagen = "imgs/Pokemons/sprites/crystal/transparent/" + this.pokemonSeleccionado.getImgFrontalPokemon();

            } else {
                rutaImagen = "imgs/Pokemons/sprites/crystal/transparent/" + this.pokemonSeleccionado.getImgFrontalPokemon3D();
            }

            String rutaImagenAdaptada = new File(rutaImagen).toURI().toString();
            fotoPokemon.setImage(new Image(rutaImagenAdaptada));
            fotoPokemon.setFitWidth(180);
            fotoPokemon.setFitHeight(190);
            fotoPokemon.setX(55);
            fotoPokemon.setY(30);
            fotoPokemon.setPreserveRatio(false);

            // Lógica para el sexo
            if (pokemonSeleccionado.getSexo() == Sexo.MACHO) {
                String rutaIcono = "imgs/Captura/sexo/Macho.png";
                sexoPokemon.setImage(new Image(new File(rutaIcono).toURI().toString()));
            } else if (pokemonSeleccionado.getSexo() == Sexo.HEMBRA) {
                String rutaIcono = "imgs/Captura/sexo/Hembra.png";
                sexoPokemon.setImage(new Image(new File(rutaIcono).toURI().toString()));
            } else {
                String rutaIcono = "imgs/Captura/sexo/NEUTRO2.0.png";
                sexoPokemon.setImage(new Image(new File(rutaIcono).toURI().toString()));
            }

            // Aplicamos nombre, nivel, mote y sus respectivos colores de tipo
            nombrePokemon.setText(pokemonSeleccionado.getNombrePokemon() + " Nvl[" + pokemonSeleccionado.getNivel() + "]");
            nombrePokemon.setFill(pokemonSeleccionado.getColor());
            nombrePokemon.setX(-30);
            nombrePokemon.setTextAlignment(TextAlignment.CENTER);

            motePokemon.setText(pokemonSeleccionado.getMotePokemon());
            motePokemon.setFill(pokemonSeleccionado.getColor());
            motePokemon.setTextAlignment(TextAlignment.CENTER);

            // Cambiar el texto de las estadisticas
            atkPokemon.setText(String.valueOf(pokemonSeleccionado.getAtaque()));
            atkPokemonEspecial.setText(String.valueOf(pokemonSeleccionado.getAtaqueEspecial()));
            hpPokemonActual.setText(String.valueOf(pokemonSeleccionado.getVitalidad()));
            hpPokemonMaxima.setText(String.valueOf(pokemonSeleccionado.getVitalidadMaxima()));
            defensaPokemon.setText(String.valueOf(pokemonSeleccionado.getDefensa()));
            defensaPokemonEspecial.setText(String.valueOf(pokemonSeleccionado.getDefensaEspecial()));
            velocidadPokemon.setText(String.valueOf(pokemonSeleccionado.getVelocidad()));
            fertilidadPokemon.setText(String.valueOf(pokemonSeleccionado.getFertilidad()));


            // Revisar si es shiny
            if (pokemonSeleccionado.getEsShiny()) {
                shinyPokemon.setText("✅");
            } else {
                shinyPokemon.setText("❌");
            }


            // Añadir descripciones al pasar el cursor

            // Ataque

            Tooltip.install(atkPokemon, new Tooltip("Ataque Normal;\n - Se contrarresta con Defensa Normal"));

            // Ataque Especial

            Tooltip.install(atkPokemonEspecial, new Tooltip("Ataque Especial;\n - Se contrarresta con Defensa Especial"));

            // Vitalidad Actual

            Tooltip.install(hpPokemonActual, new Tooltip("Vitalidad actual y máxima;\n - Vitalidad actual: La vitalidad que tiene el pokemon actualmente \n - Vitalidad máxima: La vitalidad máxima que puede tener el pokemon en las mejores condiciones"));

            // Vitalidad Maxima

            Tooltip.install(hpPokemonMaxima, new Tooltip("Vitalidad actual y máxima;\n - Vitalidad actual: La vitalidad que tiene el pokemon actualmente \n - Vitalidad máxima: La vitalidad máxima que puede tener el pokemon en las mejores condiciones"));

            // Defensa

            Tooltip.install(defensaPokemon, new Tooltip("Defensa Normal;\n - Estadística que reduce el daño ocasionado\n por el ataque normal."));

            // Defensa especial

            Tooltip.install(defensaPokemonEspecial, new Tooltip("Defensa Especial;\n - Estadística que reduce el daño ocasionado\n por el ataque especial."));

            // Velocidad

            Tooltip.install(velocidadPokemon, new Tooltip("Velocidad;\n - Estadística que aumenta la velocidad del pokemon\n - En un combate el pokemon con más velocidad atacará primero."));

            // Fertilidad

            Tooltip.install(fertilidadPokemon, new Tooltip("Fertilidad;\n - Estadística que define si un pokemon puede reproducirse en Crianza"));

            // Shiny

            Tooltip.install(shinyPokemon, new Tooltip("Shiny; \n Define si el pokemon tiene su apariencia normal o la especial"));


            // Hacer lo mismo con el icono de info

            String shiny;

            if (pokemonSeleccionado.getEsShiny()) {
                shiny = "Si";
            } else {
                shiny = "No";
            }

            String mov1 = "Sin movimiento";
            String mov2 = "Sin movimiento";
            String mov3 = "Sin movimiento";
            String mov4 = "Sin movimiento";

            if (pokemonSeleccionado.getMovimientos()[0] != null) {
                mov1 = pokemonSeleccionado.getMovimientos()[0].getNombreMovimiento();
            }
            if (pokemonSeleccionado.getMovimientos()[1] != null) {
                mov2 = pokemonSeleccionado.getMovimientos()[1].getNombreMovimiento();
            }
            if (pokemonSeleccionado.getMovimientos()[2] != null) {
                mov3 = pokemonSeleccionado.getMovimientos()[2].getNombreMovimiento();
            }
            if (pokemonSeleccionado.getMovimientos()[3] != null) {
                mov4 = pokemonSeleccionado.getMovimientos()[3].getNombreMovimiento();
            }

            String tipo2 = "Sin tipo secundario";

            if (pokemonSeleccionado.getTipoSecundario() != null) {
                tipo2 = pokemonSeleccionado.getTipoSecundario().toString();
            }

            Tooltip.install(infoPokemon, new Tooltip(
                    "===================================== INFORMACIÓN COMPLETA =====================================" +
                            "\n================= Datos pokemon ================= " +
                            "\n » ID: " + pokemonSeleccionado.getIdPokemon() +
                            "\n » Número de pokedex: " + pokemonSeleccionado.getNumPokedex() +
                            "\n » Nombre: " + pokemonSeleccionado.getNombrePokemon() +
                            "\n » Tipo principal: " + pokemonSeleccionado.getTipoPrincipal() +
                            "\n » Tipo secundario: " + tipo2 +
                            "\n » Shiny: " + shiny +
                            // "\n » Origen: " + pokemonSeleccionado.getOrigen() + Por ahora no 😡
                            "\n » Mote: " + pokemonSeleccionado.getMotePokemon() +
                            "\n » Nivel: " + pokemonSeleccionado.getNivel() +
                            "\n » Experiencia: " + pokemonSeleccionado.getExperiencia() +
                            "\n================= Estadísticas ================= " +
                            "\n » Vitalidad Máxima: " + pokemonSeleccionado.getVitalidadMaxima() +
                            "\n » Vitalidad Actual: " + pokemonSeleccionado.getVitalidad() +
                            "\n » Ataque: " + pokemonSeleccionado.getAtaque() +
                            "\n » Defensa: " + pokemonSeleccionado.getDefensa() +
                            "\n » Ataque especial: " + pokemonSeleccionado.getAtaqueEspecial() +
                            "\n » Defensa especial: " + pokemonSeleccionado.getDefensaEspecial() +
                            "\n » Velocidad: " + pokemonSeleccionado.getVelocidad() +
                            "\n » Fertilidad: " + pokemonSeleccionado.getFertilidad() +
                            "\n================= Movimientos ================= " +
                            "\n » Movimiento 1: " + mov1 +
                            "\n » Movimiento 2: " + mov2 +
                            "\n » Movimiento 3: " + mov3 +
                            "\n » Movimiento 4: " + mov4 +
                            "\n================================================================================================"));
            // Cambiar la imagen del Tipo principal
            String rutaTipo = "imgs/Equipo/Tipos/" + pokemonSeleccionado.getTipoPrincipal() + ".png";
            tipo1Pokemon.setImage(new Image(new File(rutaTipo).toURI().toString()));

            // Cambiar la imagen del Tipo secundario
            if (pokemonSeleccionado.getTipoSecundario() != null) {
                String rutaTipo2 = "imgs/Equipo/Tipos/" + pokemonSeleccionado.getTipoSecundario() + ".png";
                tipo2Pokemon.setImage(new Image(new File(rutaTipo2).toURI().toString()));
                tipo2Pokemon.setVisible(true); // Asegurarnos de que se vea
            } else {
                tipo2Pokemon.setVisible(false); // Ocultar si no tiene un tipo secundario
            }

            /**
             * Aquí implemento la lógica para meter y sacar del equipo a los pokemons
             * 1. Si el pokemon está dentro del equipo, que el icono de Añadir equipo salga como Sacar de equipo en rojo
             * */

            boolean pokemonEnEquipo = Arrays.asList(entrenadorActual.getEquipoPokemon()).contains(pokemonSeleccionado);

            Pane paneMoverEquipo = (Pane) textoMoverEquipo.getParent();
            if (pokemonEnEquipo) {
                textoMoverEquipo.setText("Sacar del equipo");
                rectMoverEquipo.setFill(Paint.valueOf("#ff4646"));

                // Al darle click al boton, que saque al pokemon del equipo

                paneMoverEquipo.setOnMouseClicked(event -> {
                    // Crear un array Equipo que contenga el equipo del entrenador

                    Pokemon[] equipo = entrenadorActual.getEquipoPokemon();
                    int indiceBorrar = -1;


                    // Un bucle que busque el índice donde está el pokemonSeleccionado en ese array
                    // y guarde su índice para el posterior borrado

                    for (int i = 0; i < equipo.length; i++) {
                        if (equipo[i] != null && equipo[i].equals(pokemonSeleccionado)) {
                            indiceBorrar = i;
                            break;
                        }
                    }

                    // Ahora añadir el pokemon a la caja antes de borrarlo del equipo

                    if (indiceBorrar != -1) {
                        pokemonSeleccionado.setUbicacion(UbicacionPokemon.CAJA);

                        // Eliminar el pokemon del equipo desplazando todos los pokemons a partir de su indice
                        // hacia atrás.

                        pokemonSeleccionado.setUbicacion(UbicacionPokemon.CAJA);
                        entrenadorActual.getCajaPokemon().add(pokemonSeleccionado);

                        for (int i = indiceBorrar; i < equipo.length - 1; i++) {
                            equipo[i] = equipo[i + 1];
                        }

                        // El ultimo pokemon se duplica, lo pongo en null

                        equipo[equipo.length - 1] = null;

                        // Y actualizo la bd
                        capturaDao.actualizarPokemon(this.con, pokemonSeleccionado, entrenadorActual.getIdEntrenador(), pokemonSeleccionado.getUbicacion().name());

                        // Recargar todo
                        recargarTodo();


                    }
                });
            } else {
                textoMoverEquipo.setText("Añadir al equipo");
                rectMoverEquipo.setFill(Paint.valueOf("#59ee52"));


                paneMoverEquipo.setOnMouseClicked(event -> {

                    // Primero buscamos hueco libre

                    int huecoLibre = -1;
                    Pokemon[] equipo = entrenadorActual.getEquipoPokemon();

                    for (int i = 0; i < equipo.length; i++) {
                        if (equipo[i] == null) {
                            huecoLibre = i;
                            break;
                        }
                    }

                    // Ahora que tenemos el hueco libre, metemos el pokemon ahi

                    if (huecoLibre != -1) {

                        // Lo sacamos de la caja

                        entrenadorActual.getCajaPokemon().remove(pokemonSeleccionado);
                        pokemonSeleccionado.setUbicacion(UbicacionPokemon.EQUIPO);
                        // Lo metemos en el equipo
                        equipo[huecoLibre] = pokemonSeleccionado;

                        // Guardar en la bases de datos

                        capturaDao.actualizarPokemon(this.con, pokemonSeleccionado, entrenadorActual.getIdEntrenador(), "EQUIPO");

                        // recargar

                        recargarTodo();
                    }
                });
            }

            /// Si el pokemon es de la primera generación, entonces que tenga sonido, no he encontrado los sonidos de la
            /// segunda

            reproducirSonido(p.getIdPokemon());

            } catch(Exception ex){
                System.out.println(ex.getMessage());
            }
    }

    // Metodo para recargar la informacion visual

    private void recargarTodo() {
        ListaPokemon.clear();

        // Si hay pokemons en equipo, se añaden a la lista pokemon
        if (entrenadorActual.getEquipoPokemon() != null) {
            for (Pokemon poke : entrenadorActual.getEquipoPokemon()) {
                if (poke != null) ListaPokemon.add(poke);
            }
        }

        // Si hay pokemons en la caja, se añaden a la lista de pokemons

        if (entrenadorActual.getCajaPokemon() != null) {
            ListaPokemon.addAll(entrenadorActual.getCajaPokemon());
        }

        cargarInventario();
        mostrarDetallesPokemon(pokemonSeleccionado);
    }


    // Metodo para reproducir sonido

    private void reproducirSonido(int idPokemon) {


        if (idPokemon <= 151) {
            try {
                String sonido = "sonidos/Pokemon/" + idPokemon + ".wav";
                Media sound = new Media(new File(sonido).toURI().toString());

                // Detiene el anterior para que no pete

                if (mediaPlayer != null) {
                    mediaPlayer.stop();
                    mediaPlayer.dispose();
                }

                // Inicializar mediaPlayer

                mediaPlayer = new MediaPlayer(sound);
                sonidoPokemon.setVisible(true);

                sonidoPokemon.setOnMouseClicked(event -> {
                    mediaPlayer.play();
                });


            } catch (Exception e) {
                System.out.println("Error al cargar el sonido: " + e.getMessage());
                e.printStackTrace();
            }
        } else if (idPokemon % 2 == 0) {
            try {
                String sonido = "sonidos/Pokemon/3.wav";
                Media sound = new Media(new File(sonido).toURI().toString());

                // Detiene el anterior para que no pete

                if (mediaPlayer != null) {
                    mediaPlayer.stop();
                    mediaPlayer.dispose();
                }

                // Inicializar mediaPlayer

                mediaPlayer = new MediaPlayer(sound);
                sonidoPokemon.setVisible(true);

                sonidoPokemon.setOnMouseClicked(event -> {
                    mediaPlayer.play();
                });


            } catch (Exception e) {
                System.out.println("Error al cargar el sonido: " + e.getMessage());
                e.printStackTrace();
            }
        } else {
            try {
                String sonido = "sonidos/Pokemon/142.wav";
                Media sound = new Media(new File(sonido).toURI().toString());

                // Detiene el anterior para que no pete

                if (mediaPlayer != null) {
                    mediaPlayer.stop();
                    mediaPlayer.dispose();
                }

                // Inicializar mediaPlayer

                mediaPlayer = new MediaPlayer(sound);
                sonidoPokemon.setVisible(true);

                sonidoPokemon.setOnMouseClicked(event -> {
                    mediaPlayer.play();
                });


            } catch (Exception e) {
                System.out.println("Error al cargar el sonido: " + e.getMessage());
                e.printStackTrace();
            }

        }
    }
    
    //BOTON curar
    @FXML
    private void aumentarTamanoBotonCurarEquipo(MouseEvent event) {
    	curarEquipo.setScaleX(curarEquipo.getScaleX() + 0.2);
        curarEquipo.setScaleY(curarEquipo.getScaleY() + 0.2);
    }
    
    @FXML
    private void disminuirTamanoBotonCurarEquipo(MouseEvent event) {
    	curarEquipo.setScaleX(curarEquipo.getScaleX() - 0.2);
        curarEquipo.setScaleY(curarEquipo.getScaleY() - 0.2);
    }
    
    @FXML
    // boton curar pokemon
    public void clickCurarPokemon(MouseEvent event) {
        Entrenador jugador = Sesion.entrenadorLogueado;
        
        //comprobamos si necesita curar
        boolean necesitaCura = false;
        for (Pokemon p : jugador.getEquipoPokemon()) {
            if (p != null) {
                //comprobamos si le falta vida
                if (p.getVitalidad() < p.getVitalidadMaxima()) {
                    necesitaCura = true;
                    break;
                }
                //comprobamos si le faltan PPs en algun movimiento
                for (Movimiento m : p.getMovimientos()) {
                    if (m != null && m.getCantidadMovimientos() < m.getCantidadMovimientosMaximos()) {
                        necesitaCura = true;
                        break;
                    }
                }
            }
            if (necesitaCura) break;
        }

        //si no necesita cura salta alerta error
        if (!necesitaCura) {
            mostrarAlertaEstado("Equipo en forma", "Tus Pokémon ya están a tope de energía y PPs. ¡No necesitan descansar!", Alert.AlertType.WARNING, "imgs/Equipo/alertaCurar.png");
            return;
        }

        //si necesita cura
        jugador.curarEquipoCompleto();
        
        try {
            ConexionBBDD conector = new ConexionBBDD();
            Connection con = conector.getConexion();
            MovimientoDAO movDao = new MovimientoDAO(con);
            
            for (Pokemon p : jugador.getEquipoPokemon()) {
                if (p != null) {
                    PokemonDAO.actualizarStatsBD(con, p);
                    movDao.restaurarPPsCompletos(p.getIdPokemon());
                }
            }
            con.close();
            System.out.println("Curación guardada en la Base de Datos.");
        } catch (Exception e) {
            System.out.println("Error al guardar la cura en BD: " + e.getMessage());
        }
        
        if (pokemonSeleccionado != null) {
            mostrarDetallesPokemon(pokemonSeleccionado);
        }
        
        //alerta de exito
        mostrarAlertaEstado("Centro Pokémon", "¡Tus Pokémon han recuperado toda su energía y sus PPs!", Alert.AlertType.INFORMATION, "imgs/Equipo/curarEquipo.png");
    }

    /**
     * Método auxiliar para generar las alertas con tu estilo personalizado
     */
    private void mostrarAlertaEstado(String titulo, String contenido, Alert.AlertType tipo, String rutaIconoCentral) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(contenido);

        // Icono central personalizado
        try {
            String ruta = new File(rutaIconoCentral).toURI().toString();
            ImageView icono = new ImageView(new Image(ruta));
            icono.setFitHeight(50);
            icono.setFitWidth(50);
            alerta.setGraphic(icono);
        } catch (Exception e) {
            System.out.println("No se pudo cargar el icono central de la alerta");
        }
        
     //aplicamos el CSS para diferenciar entre exito y fracaso
        try {
            if (tipo == Alert.AlertType.WARNING) {
                alerta.getDialogPane().getStylesheets().add(getClass().getResource("/view/captura/alertas2.css").toExternalForm());
            } else {
                alerta.getDialogPane().getStylesheets().add(getClass().getResource("/view/captura/alertas.css").toExternalForm());
            }
        } catch (Exception e) {
            System.out.println("Aviso: No se pudo cargar el CSS de la alerta.");
        }

        // Icono superior de la ventana
        try {
            Stage stageAlerta = (Stage) alerta.getDialogPane().getScene().getWindow();
            File fileIcono = new File("imgs/Login/Login-icon.png");
            if (fileIcono.exists()) {
                stageAlerta.getIcons().add(new Image(fileIcono.toURI().toString()));
            }
        } catch (Exception e) {}

        alerta.showAndWait();
    }

    //Boton de salir
    @FXML
    public void equipoSalir(MouseEvent event) {
        try {

            System.out.println("Cargando la vista principal...");

            // Recibir el click
            Node source = (Node) event.getSource();

            // Recuperar la ventana
            Stage primaryStage = (Stage) source.getScene().getWindow();

            // Cargar la vista Principal
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/principal/vistaPrincipal.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);


            // Cargar el CSS
            String css = this.getClass().getResource("/view/principal/vistaPrincipal.css").toExternalForm();
            scene.getStylesheets().add(css);

            // Titulo, forzar el tamaño de la ventana y bloquear cambio manual
            primaryStage.setTitle("PokeINC - Principal");
            primaryStage.setResizable(false);

            // Cargar icono
            File file = new File("imgs/Login/Login-icon.png");

            if (file.exists()) {
                String imagePath = file.toURI().toString();
                primaryStage.getIcons().add(new Image(imagePath));
            } else {
                System.out.println("No se encontró el icono en: " + file.getAbsolutePath());
            }

            // Cambiar la escena del login por la nueva
            primaryStage.setScene(scene);

            // Mostrar la escena
            primaryStage.show();

        } catch (Exception e) {
            System.out.println("Error al cambiar de ventana - " + e.getMessage());
            e.printStackTrace();
        }
    }

}
