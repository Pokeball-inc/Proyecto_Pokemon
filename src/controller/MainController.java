package controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    // MUSICA DE FONDO

    @FXML
    private ImageView iconoMusica;

    private MediaPlayer mediaPlayer;
    private boolean musicaActiva = false;


    // METODO INITIALIZE

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        // Cargar la musica

        cargarMusica();

        if (mediaPlayer != null) {
            mediaPlayer.play();
            musicaActiva = true;
        }
    }

    // Metodo para iniciar la musica

    private void cargarMusica() {
        try {
            String musica = "imgs/Principal/Principal.mp3";
            Media sound = new Media(new File(musica).toURI().toString());

            // Inicializar mediaPlayer
            mediaPlayer = new MediaPlayer(sound);

            // Establecer la musica en bucle
            mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);

        } catch (Exception e) {
            System.out.println("Error al cargar la música: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Metodo para alternar la musica

    @FXML
    private void alternarMusica(MouseEvent event) {
        try  {
            if (mediaPlayer != null) {
                // Si la variable musica es true, musica silenciada
                if (musicaActiva) {
                    mediaPlayer.setMute(true);
                    System.out.println("Musica silenciada");
                    musicaActiva = false;
                    iconoMusica.setOpacity(0.5);
                } else { // Caso contrario, musica reactivada
                    mediaPlayer.setMute(false);
                    musicaActiva = true;
                    System.out.println("Musica reanudada");
                    iconoMusica.setOpacity(1);

                }
            }
        } catch (Exception e) {
            System.out.println("Error al alternar la musica: " + e.getMessage());
        }
    }

}