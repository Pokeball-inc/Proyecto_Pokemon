package controller;

import javafx.animation.Interpolator;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class CapturaController implements Initializable {

    // PANEL PARTICULAS

    @FXML
    private Pane panelParticulas;


    public void initialize(URL location, ResourceBundle resources) {

        // Generar 120 partículas al iniciar la pantalla

        for (int i = 0; i < 240; i++) {
            crearParticula();
        }
    }


    // METODO CREAR PARTICULA

    private void crearParticula() {

        // DARLE FORMA DE CIRCULO PEQUEÑERO A LA PARTICULA

        // RADIO ALEATORIO ENTRE 1 Y 3 PIXELES POR PARTICULA

        double radio = Math.random() * 2 + 1;
        Circle particula = new Circle(radio, Color.RED);

        // DARLE UN POCO DE BLUR O DESENFOQUE PARA QUE NO SEA TAN COMPACTO

        particula.setEffect(new GaussianBlur(Math.random() * 2 + 1));

        // OPACIDAD ALEATORIA POR PARTICULA PARA DARLE PROFUNDIDAD

        particula.setOpacity(Math.random() * 0.6 + 0.2);

        // POSICIÓN INICIAL DE LAS PARTICULAS

        // TENIENDO EN CUENTA QUE LA PANTALLA FIJA ES DE 1074 PX DE ANCHO, ENTONCES UN
        // PUNTO ALEATORIO EN EL EJE X DE 0 A 1074
        particula.setLayoutX(Math.random() * 1074);

        // TENIENDO EN CUENTA QUE LA PANTALLA FIJA ES DE 620 PX DE ALTO, ENTONCES UN
        // PUNTO ALEATORIO EN EL EJE Y DE 0 A 620

        particula.setLayoutY(Math.random() * 620);

        // AÑADIR LAS PARTICULAS AL PANEL

        panelParticulas.getChildren().add(particula);

        // ANIMACIÓN DE LAS PARTICULAS

        // DARLES UNA VELOCIDAD ALEATORIA ENTRE 8 Y 18 SEGUNDOS PARA EL EFECTO DE FLOTE
        // SUAVE

        double duracionSegundos = Math.random() * 10 + 8;

        TranslateTransition animacion = new TranslateTransition(Duration.seconds(duracionSegundos), particula);

        // MOVERSE HACIA ABAJO, USANDO COMO REFERENCIA LA POSICION ACTUAL DE Y Y BAJANDO 300

        animacion.setByY(animacion.getByZ() + 300);
        animacion.setInterpolator(Interpolator.LINEAR); // MOVIMIENTO CONSTANTE

        // CUANDO LA PARTICULA LLEGUE HASTA ARRIBA, RESETEAR EL EVENTO PARA UN NUEVO
        // COMIENZO DE OTRA PARTICULA

        animacion.setOnFinished(event -> {
            particula.setTranslateY(0); // REINICIAR EL DESPLAZAMIENTO A 0
            particula.setLayoutY(Math.random() * 620); // NUEVA POSICIÓN EN EL EJE Y
            particula.setLayoutX(Math.random() * 1074); // NUEVA POSICIÓN EN EL EJE X
            animacion.playFromStart(); // VOLVER A EMPEZAR DE 0
        });

        animacion.play();
    }
    // ---------------------------- PARTICULAS ---------------------------- \\

    // BOTONES

    @FXML
    private ImageView botonCaptura;
    @FXML
    private ImageView botonCambiar;
    @FXML
    private ImageView botonSalir;

    // --------------- INCREMENTAR Y DISMINUIR TAMAÑO DEL BOTON CAPTURA AL PASAR EL CURSOR ---------------
    @FXML
    private void aumentarTamañoBotonCaptura(MouseEvent event) {

        botonCaptura.setScaleX(botonCaptura.getScaleX() + 0.2);
        botonCaptura.setScaleY(botonCaptura.getScaleY() + 0.2);

    }

    @FXML
    private void disminuirTamañoBotonCaptura(MouseEvent event) {

        botonCaptura.setScaleX(botonCaptura.getScaleX() - 0.2);
        botonCaptura.setScaleY(botonCaptura.getScaleY() - 0.2);

    }

    // --------------- INCREMENTAR Y DISMINUIR TAMAÑO DEL BOTON CAMBIAR AL PASAR EL CURSOR ---------------
    @FXML
    private void aumentarTamañoBotonCambiar(MouseEvent event) {

        botonCambiar.setScaleX(botonCambiar.getScaleX() + 0.2);
        botonCambiar.setScaleY(botonCambiar.getScaleY() + 0.2);

    }

    @FXML
    private void disminuirTamañoBotonCambiar(MouseEvent event) {

        botonCambiar.setScaleX(botonCambiar.getScaleX() - 0.2);
        botonCambiar.setScaleY(botonCambiar.getScaleY() - 0.2);

    }


    // --------------- INCREMENTAR Y DISMINUIR TAMAÑO DEL BOTON SALIR AL PASAR EL CURSOR ---------------
    @FXML
    private void aumentarTamañoBotonSalir(MouseEvent event) {

        botonSalir.setScaleX(botonSalir.getScaleX() + 0.2);
        botonSalir.setScaleY(botonSalir.getScaleY() + 0.2);

    }

    @FXML
    private void disminuirTamañoBotonSalir(MouseEvent event) {

        botonSalir.setScaleX(botonSalir.getScaleX() - 0.2);
        botonSalir.setScaleY(botonSalir.getScaleY() - 0.2);

    }


    // --------------- SALIR AL DARLE AL BOTON SALIR ---------------

    @FXML
    public void capturaSalir(MouseEvent event) {
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
            System.out.println("Error al cambiar de ventana - " + e.getMessage());
            e.printStackTrace();
        }
    }
}
