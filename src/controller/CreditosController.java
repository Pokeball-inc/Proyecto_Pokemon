package controller;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import model.Sesion;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class CreditosController implements Initializable {
    @FXML
    private ImageView botonSalir;
    @FXML
    private ImageView elyass;
    @FXML
    private ImageView pablo;
    @FXML
    private ImageView isaias;
    @FXML
    private ImageView elyasstxt;
    @FXML
    private ImageView pablotxt;
    @FXML
    private ImageView isaiastxt;

    public void initialize(URL location, ResourceBundle resources) {
        main();
    }

    public void main() {

        /// Boton Salir
        botonSalir.setOnMouseClicked(event -> {

            /// Si el entrenador está logueado, te lleva a la principal, si no, al login

            if (Sesion.entrenadorLogueado != null) {
                try {
                    cargarPrincipal(event);
                } catch (IOException e) {
                    System.out.println("Error al cargar el principal");
                    throw new RuntimeException(e);
                }
            } else {
                try {
                    cargarLogin(event);
                } catch (IOException e) {
                    System.out.println("Error al cargar el login");
                    throw new RuntimeException(e);
                }
            }
        });


        ///  Lo que dicen los autores e.e


        elyass.setOnMouseEntered(event -> {
            elyasstxt.setVisible(true);
        });
        elyass.setOnMouseExited(event -> {
            elyasstxt.setVisible(false);
        });

        pablo.setOnMouseEntered(event -> {
            pablotxt.setVisible(true);
        });
        pablo.setOnMouseExited(event -> {
            pablotxt.setVisible(false);
        });

        isaias.setOnMouseEntered(event -> {
            isaiastxt.setVisible(true);
        });
        isaias.setOnMouseExited(event -> {
            isaiastxt.setVisible(false);
        });

    }

    /// Method para la pantalla Login

    public void cargarLogin(Event event) throws IOException {

        // Recibir el click
        javafx.scene.Node source = (javafx.scene.Node) event.getSource();

        // Recuperar la ventana
        Stage primaryStage = (Stage) source.getScene().getWindow();

        // Cargar el login

        Parent root = FXMLLoader.load(getClass().getResource("/view/login/login.fxml"));


        Scene scene = new Scene(root);

        // Cargar el CSS

        String css = this.getClass().getResource("/view/login/login.css").toExternalForm();
        scene.getStylesheets().add(css);

        // Titulo, forzar el tamaño de la ventana y bloquear cambio manual
        primaryStage.setTitle("PokeINC");
        primaryStage.setResizable(false);
        primaryStage.sizeToScene();

        // Cargar icono
        File file = new File("imgs/Login/Login-icon.png");

        if (file.exists()) {
            String imagePath = file.toURI().toString();
            primaryStage.getIcons().add(new Image(imagePath));
        } else {
            System.out.println("No se encontró el icono en: " + file.getAbsolutePath());
        }

        primaryStage.setScene(scene);

        primaryStage.show();

        // Mostrar la escena

        primaryStage.show();
    }

    /// Method para cargar la pantalla principal
    ///
    public void cargarPrincipal(Event event) throws IOException {
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
