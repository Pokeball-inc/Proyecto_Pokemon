package controller;

import javafx.animation.Interpolator;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;


public class LoginController implements Initializable {

// ---------------------------- PARTICULAS DEL LOGIN ---------------------------- \\

    // VINCULAR EL PANE CON EL FXML

    @FXML
    private Pane panelParticulas;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        // Generar 120 partículas al iniciar la pantalla

        for (int i = 0; i < 120; i++) {
            crearParticula();
        }
    }

    // METODO CREAR PARTICULA
    private void crearParticula() {

        // DARLE FORMA DE CIRCULO PEQUEÑERO A LA PARTICULA

        // RADIO ALEATORIO ENTRE 1 Y 3 PIXELES POR PARTICULA

        double radio = Math.random() * 2 + 1;
        Circle particula = new Circle(radio, Color.WHITE);

        // DARLE UN POCO DE BLUR O DESENFOQUE PARA QUE NO SEA TAN COMPACTO

        particula.setEffect(new GaussianBlur(Math.random() * 2 + 1));

        // OPACIDAD ALEATORIA POR PARTICULA PARA DARLE PROFUNDIDAD

        particula.setOpacity(Math.random() * 0.6 + 0.2);

        // POSICIÓN INICIAL DE LAS PARTICULAS

        // TENIENDO EN CUENTA QUE LA PANTALLA FIJA ES DE 1074 PX DE ANCHO, ENTONCES UN PUNTO ALEATORIO EN EL EJE X DE 0 A 1074
        particula.setLayoutX(Math.random() * 1074);

        // TENIENDO EN CUENTA QUE LA PANTALLA FIJA ES DE 620PX DE ALTO, PUES EN EL PUNTO MÁS BAJO EN EL EJE Y
        particula.setLayoutY(620);

        // AÑADIR LAS PARTICULAS AL PANEL
        panelParticulas.getChildren().add(particula);

        // ANIMACIÓN DE LAS PARTICULAS

        // DARLES UNA VELOCIDAD ALEATORIA ENTRE 8 Y 18 SEGUNDOS PARA EL EFECTO DE FLOTE SUAVE
        double duracionSegundos = Math.random() * 10 + 8;

        TranslateTransition animacion = new TranslateTransition(Duration.seconds(duracionSegundos), particula);

        // MOVERSE HACIA ARRIBA, -700, UN POCO MÁS ALTO DEL ALTO DE LA PANTALLA
        animacion.setByY(-700);
        animacion.setInterpolator(Interpolator.LINEAR); // MOVIMIENTO CONSTANTE

        // CUANDO LA PARTICULA LLEGUE HASTA ARRIBA, RESETEAR EL EVENTO PARA UN NUEVO COMIENZO DE OTRA PARTICULA

        animacion.setOnFinished(event -> {
            particula.setTranslateY(0); // REINICIAR EL DESPLAZAMIENTO
            particula.setLayoutX(Math.random() * 1074); // NUEVA POSICIÓN EN EL EJE X
            animacion.playFromStart(); // VOLVER A EMPEZAR DE 0
        });

        // RETRASO INICIAL PARA QUE NO SALGAN TODAS LAS PARTICULAS DE GOLPE

        animacion.setDelay(Duration.seconds(Math.random() * 10));
        animacion.play();
    }
}

// ---------------------------- PARTICULAS DEL LOGIN ---------------------------- \\


