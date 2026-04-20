package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import model.Entrenador;
import model.Pokemon;
import model.Sesion;
import model.Sexo;

import static model.Sexo.MACHO;


public class EquipoController implements Initializable {

    private Entrenador entrenadorActual = Sesion.entrenadorLogueado;
    @FXML
    private ImageView botonSalir;
    @FXML
    private TilePane cajaPokemon;
    @FXML
    private ScrollPane cajaPokemonScroll;

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
    private Text hpPokemon;
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

    private Pokemon pokemonSeleccionado;

    private List<Pokemon> ListaPokemon = new ArrayList<>();

    public void initialize(URL location, ResourceBundle resources) {

        // Limpiar la lista, por si acaso (No paran de clonarse 🥀)
        ListaPokemon.clear();

        // Añadir los pokemons del equipo y la caja a lista de pokemons totales
        if (entrenadorActual != null) {

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
            }
        }
    }

    // Caja
    private void cargarInventario() {
        // Limpiamos el TilePane antes de cargar para evitar duplicados visuales
        cajaPokemon.getChildren().clear();
        pokemonSeleccionado = ListaPokemon.get(0);

        for (Pokemon p : ListaPokemon) {
            pokemonSeleccionado = p;
            try {
                // Crear una Celda Vbox con v: -2, para que el texto y la imagen no estén tan alejados

                VBox celdaPokemon = new VBox(-4);
                // Alineamos la celda
                celdaPokemon.setAlignment(Pos.CENTER);

                // Darle estilo a la celda:
                celdaPokemon.setStyle(
                        "-fx-background-color: rgba(255, 255, 255, 0.4);" +
                                "-fx-background-radius: 15;" +
                                "-fx-cursor: hand;" +
                                "-fx-padding: 10;"
                );

                // Crear el ImageView
                String rutaPokemon = new File("imgs/Pokemons/" + p.getImgFrontalPokemon()).toURI().toString();
                ImageView vistaImagen = new ImageView(new Image(rutaPokemon));
                vistaImagen.setFitWidth(132);
                vistaImagen.setFitHeight(132);
                vistaImagen.setPreserveRatio(true);

                // Crear el label
                if (p.getMotePokemon() != null) {
                    Label nombre = new Label(p.getMotePokemon() + " Nvl: " + p.getNivel());
                    nombre.setStyle("-fx-text-fill: black; -fx-font-weight: bold;");
                    // Meter la imagen y el label
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

                // Mostrar el primer pokemon de la lista por defecto, para que no salga el charizard ese xd

                mostrarDetallesPokemon(ListaPokemon.get(0));


            } catch (Exception e) {
                System.out.println("Error cargando imagen de pokemon: " + e.getMessage());
            }
        }
    }

    // Metodo para mostrar detalles de pokemons

    private void mostrarDetallesPokemon(Pokemon p) {
        if (p == null) return;

        try {
            // Actualizamos la referencia y nos aseguramos de que tenga color
            pokemonSeleccionado = p;
            pokemonSeleccionado.cambiarColor();

            // buscamos el archivo en tu carpeta de sprites
            String rutaImagen = "imgs/Pokemons/" + pokemonSeleccionado.getImgFrontalPokemon();
            String rutaImagenAdaptada = new File(rutaImagen).toURI().toString();
            fotoPokemon.setImage(new Image(rutaImagenAdaptada));

            // Lógica para el sexo
            if (pokemonSeleccionado.getSexo() == MACHO) {
                String rutaIcono = "imgs/Captura/sexo/Macho.png";
                sexoPokemon.setImage(new Image(new File(rutaIcono).toURI().toString()));
            } else if (pokemonSeleccionado.getSexo() == Sexo.HEMBRA) {
                String rutaIcono = "imgs/Captura/sexo/Hembra.png";
                sexoPokemon.setImage(new Image(new File(rutaIcono).toURI().toString()));
            } else {
                String rutaIcono = "imgs/Captura/sexo/NEUTRO2.0.png";
                sexoPokemon.setImage(new Image(new File(rutaIcono).toURI().toString()));
            }

            // Aplicamos nombre, mote y sus respectivos colores de tipo
            nombrePokemon.setText(pokemonSeleccionado.getNombrePokemon());
            nombrePokemon.setFill(pokemonSeleccionado.getColor());
            nombrePokemon.setTextAlignment(TextAlignment.CENTER);

            motePokemon.setText(pokemonSeleccionado.getMotePokemon());
            motePokemon.setFill(pokemonSeleccionado.getColor());
            motePokemon.setTextAlignment(TextAlignment.CENTER);

            // Cambiar el texto de las estadisticas
            atkPokemon.setText(String.valueOf(pokemonSeleccionado.getAtaque()));
            atkPokemonEspecial.setText(String.valueOf(pokemonSeleccionado.getAtaqueEspecial()));
            hpPokemon.setText(String.valueOf(pokemonSeleccionado.getVitalidad()));
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

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
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
