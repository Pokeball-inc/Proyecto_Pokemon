package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class CapturaController implements Initializable {
    public void initialize(URL location, ResourceBundle resources) {

    }

    // Metodo para volver a la pantalla principal

    @FXML
    public void volverPrincipal(MouseEvent event) {
        try {
            System.out.println("Cargando la vista principal...");

            // Recibir el click
            javafx.scene.Node source = (javafx.scene.Node) event.getSource();

            // Recuperar la ventana
            Stage primaryStage = (Stage) source.getScene().getWindow();

            // Cargar la vista Principal
            Parent root = FXMLLoader.load(getClass().getResource("/view/principal/vistaPrincipal.fxml"));
            Scene scene = new Scene(root);

            // Cargar el CSS
            String css = this.getClass().getResource("/view/principal/vistaPrincipal.css").toExternalForm();
            scene.getStylesheets().add(css);

            // Titulo, forzar el tamaño de la ventana y bloquear cambio manual
            primaryStage.setTitle("PokeINC - Prinicipal");
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
            System.out.println("Error al cambiar de ventana - "+ e.getMessage());
            e.printStackTrace();
        }
    }
}
