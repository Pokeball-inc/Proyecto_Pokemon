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
import javafx.stage.Stage;
import model.Entrenador;
import model.Pokemon;
import model.Sesion;


public class EquipoController implements Initializable {

    private Entrenador entrenadorActual = Sesion.entrenadorLogueado;
    @FXML
    private ImageView botonSalir;
    @FXML
    private TilePane cajaPokemon;
    @FXML
    private ScrollPane cajaPokemonScroll;

    private List<Pokemon> ListaPokemon =  new ArrayList<>();

    public void initialize(URL location, ResourceBundle resources) {

        // Limpiar la lista, por si acaso (No paran de clonarse 🥀)
        ListaPokemon.clear();

        // Añadir a la lista, los pokemons del equipo y de la caja
        ListaPokemon.addAll(List.of(entrenadorActual.getEquipoPokemon()));
        ListaPokemon.addAll((entrenadorActual.getCajaPokemon()));

        // Entrenador actual

        System.out.println("Equipo: Entrenador "+ entrenadorActual.getNombreEntrenador());
        cargarInventario();
    }

    // Caja
    private void cargarInventario() {
        // Limpiamos el TilePane antes de cargar para evitar duplicados visuales
        cajaPokemon.getChildren().clear();

        for (Pokemon p : ListaPokemon) {
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

                celdaPokemon.setOnMouseClicked(e -> {
                    System.out.println("Has seleccionado a: " + p.getNombrePokemon());
                    System.out.println("Vitalidad: " + p.getVitalidad());
                    System.out.println("Ataque: " + p.getAtaque());
                    System.out.println("Tipo Principal: " + p.getTipoPrincipal());
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
