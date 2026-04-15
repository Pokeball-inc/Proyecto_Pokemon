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
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.Entrenador;
import model.Seccion;
import model.Sesion;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class MainController implements Initializable {
	
	// Variable para guardar al usuario logueado
    private Entrenador entrenadorActual = Sesion.entrenadorLogueado;

    // Método para recibir al entrenador desde el Login
    public void setEntrenador() {
        System.out.println("Entrenador: " + entrenadorActual.getNombreEntrenador());
    }

    // MUSICA DE FONDO

    @FXML
    private ImageView iconoMusica;

    private MediaPlayer mediaPlayer;
    private boolean musicaActiva = false;


    // PANEL PARTICULAS

    @FXML
    private Pane panelParticulas;


    // METODO INITIALIZE

    @Override
    public void initialize(URL location, ResourceBundle resources) {


        // Mostrar el entrenador logueado

        setEntrenador();
        // Generar 120 partículas al iniciar la pantalla

        for (int i = 0; i < 120; i++) {
            crearParticula();
        }

        // Cargar la musica

        cargarMusica();
        inicializarLista();
        flotarPaneles(vidrioPrincipal, -5, 1000);
        flotarPaneles(vidrioPosterior, -5, 1000);
        flotarPaneles(vidrioAnterior, -5, 1000);

        if (mediaPlayer != null) {
            mediaPlayer.play();
            musicaActiva = true;
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

        // TENIENDO EN CUENTA QUE LA PANTALLA FIJA ES DE 1074 PX DE ANCHO, ENTONCES UN
        // PUNTO ALEATORIO EN EL EJE X DE 0 A 1074
        particula.setLayoutX(Math.random() * 1074);

        // TENIENDO EN CUENTA QUE LA PANTALLA FIJA ES DE 620PX DE ALTO, PUES EN EL PUNTO
        // MÁS BAJO EN EL EJE Y
        particula.setLayoutY(620);

        // AÑADIR LAS PARTICULAS AL PANEL
        panelParticulas.getChildren().add(particula);

        // ANIMACIÓN DE LAS PARTICULAS

        // DARLES UNA VELOCIDAD ALEATORIA ENTRE 8 Y 18 SEGUNDOS PARA EL EFECTO DE FLOTE
        // SUAVE
        double duracionSegundos = Math.random() * 10 + 8;

        TranslateTransition animacion = new TranslateTransition(Duration.seconds(duracionSegundos), particula);

        // MOVERSE HACIA ARRIBA, -700, UN POCO MÁS ALTO DEL ALTO DE LA PANTALLA
        animacion.setByY(-700);
        animacion.setInterpolator(Interpolator.LINEAR); // MOVIMIENTO CONSTANTE

        // CUANDO LA PARTICULA LLEGUE HASTA ARRIBA, RESETEAR EL EVENTO PARA UN NUEVO
        // COMIENZO DE OTRA PARTICULA

        animacion.setOnFinished(event -> {
            particula.setTranslateY(0); // REINICIAR EL DESPLAZAMIENTO
            particula.setLayoutX(Math.random() * 1074); // NUEVA POSICIÓN EN EL EJE X
            animacion.playFromStart(); // VOLVER A EMPEZAR DE 0
        });

        // RETRASO INICIAL PARA QUE NO SALGAN TODAS LAS PARTICULAS DE GOLPE

        animacion.setDelay(Duration.seconds(Math.random() * 10));
        animacion.play();
    }
// ---------------------------- PARTICULAS ---------------------------- \\

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
        try {
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

    // ---------------------- BOTONES CAMBIO ---------------------------------------//

    // --------------- ALTERNAR MODOS ----------------

    private boolean botonesActivos = true;
    @FXML
    private Pane botones;

    @FXML
    private void modos(MouseEvent event) {
        try {
            // Si los botones están activos, se desactivan
            if (botonesActivos) {
                botones.setVisible(true);
                System.out.println("Botones activados");
                botonesActivos = false;
            } else { // Caso contrario, musica reactivada
                botones.setVisible(false);
                System.out.println("Botones desactivados");
                botonesActivos = true;
            }

        } catch (Exception e) {
            System.out.println("Error al alternar la visibilidad de los botones: " + e.getMessage());
        }
    }

    // --------------- SISTEMA DE NAVEGACION DE MODOS ---------------

    // Declaracion de variables

    private List<Seccion> listaSecciones = new ArrayList<>();
    private int indiceActual = 1;

    @FXML
    private ImageView vidrioPrincipal;
    @FXML
    private ImageView vidrioAnterior;
    @FXML
    private ImageView vidrioPosterior;

    // Inicializar la lista de modos

    public void inicializarLista() {
        listaSecciones.add(new Seccion(0, "CREDITOS", "/imgs/Principal/vidrio_panel_creditos.png", "/view/creditos/creditos.fxml", ""));
        listaSecciones.add(new Seccion(1, "CAPTURA", "/imgs/Principal/vidrio_panel_captura.png", "/view/captura/captura.fxml", ""));
        listaSecciones.add(new Seccion(2, "CRIANZA", "/imgs/Principal/vidrio_panel_crianza.png", "/view/crianza/crianza.fxml", ""));
        listaSecciones.add(new Seccion(3, "EQUIPO", "/imgs/Principal/vidrio_panel_equipo.png", "/view/equipo/equipo.fxml", ""));
        listaSecciones.add(new Seccion(4, "COMBATE", "/imgs/Principal/vidrio_panel_equipo.png", "/view/equipo/equipo.fxml", ""));
        listaSecciones.add(new Seccion(5, "ENTRENAMIENTO", "/imgs/Principal/vidrio_panel_entrenamiento.png", "/crianza/entrenamiento/entrenamiento.fxml", ""));
        listaSecciones.add(new Seccion(6, "POKEDEX", "/imgs/Principal/vidrio_panel_pokedex.png", "/view/pokedex/pokedex.fxml", ""));
        listaSecciones.add(new Seccion(7, "CASINO", "/imgs/Principal/vidrio_panel_casino.png", "/view/casino/casino.fxml", ""));
        actualizarLista();
    }

    private void actualizarLista() {
        try {
            // Elemento principal
            Seccion principal = listaSecciones.get(indiceActual);

            // Cargar las imágenes

            File filePrincipal = new File(principal.getRutaImagen().substring(1));
            vidrioPrincipal.setImage(new Image(filePrincipal.toURI().toString()));

            // Si el nombre de la vista principal es igual al nombre de la vista, le inserta el MouseAction que le permite ir a su vista
            if (principal.getNombre().equals("CAPTURA")) {
                vidrioPrincipal.setOnMouseClicked(this::accederCaptura);
            } else if (principal.getNombre().equals("CRIANZA")) {
                vidrioPrincipal.setOnMouseClicked(this::accederCrianza);
            } else if (principal.getNombre().equals("EQUIPO")) {
                vidrioPrincipal.setOnMouseClicked(this::accederEquipo);
            } else if (principal.getNombre().equals("COMBATE")) {
                vidrioPrincipal.setOnMouseClicked(this::accederEquipo);
            } else if (principal.getNombre().equals("ENTRENAMIENTO")) {
                vidrioPrincipal.setOnMouseClicked(this::accederEntrenamiento);
            } else if (principal.getNombre().equals("POKEDEX")) {
                vidrioPrincipal.setOnMouseClicked(this::accederPokedex);
            } else if (principal.getNombre().equals("CASINO")) {
                vidrioPrincipal.setOnMouseClicked(this::accederCasino);
            }
            // Elemento anterior
            if (indiceActual > 0) {
                Seccion anterior = listaSecciones.get(indiceActual - 1);
                File fileAnterior = new File(anterior.getRutaImagen().substring(1));
                vidrioAnterior.setImage(new Image(fileAnterior.toURI().toString()));
                vidrioAnterior.setVisible(true);
            } else {
                vidrioAnterior.setVisible(false);
            }

            // Elemento posterior
            if (indiceActual < listaSecciones.size() - 1) {
                Seccion posterior = listaSecciones.get(indiceActual + 1);
                File filePosterior = new File(posterior.getRutaImagen().substring(1));
                vidrioPosterior.setImage(new Image(filePosterior.toURI().toString()));
                vidrioPosterior.setVisible(true);
            } else {
                vidrioPosterior.setVisible(false);
            }
        } catch (Exception e) {
            System.out.println("Error cargando imágenes: " + e.getMessage());
        }
    }


    // --------------- CAMBIAR INDICE AL DARLE AL PANEL ANTERIOR ----------------

    public void clickAnterior(MouseEvent event) {
        if (indiceActual > 0) {
            indiceActual--;
            actualizarLista();
            System.out.println("Navegando a la izquierda. Índice actual: " + indiceActual);
        }
    }
    // --------------- CAMBIAR INDICE AL DARLE AL PANEL POSTERIOR ----------------

    public void clickPosterior(MouseEvent event) {

        if (indiceActual < listaSecciones.size() - 1) {
            indiceActual++;
            actualizarLista();
            System.out.println("Navegando a la derecha. Índice actual: " + indiceActual);
        }
    }

    // --------------- APLICAR EFECTO FLOTAR A LOS PANELES ----------------

    private void flotarPaneles(ImageView panel, double distancia, int duracion) {

        // Inicializar el TranslateTransition para la animacion
        TranslateTransition tt = new TranslateTransition(Duration.millis(duracion), panel);

        // Movimiento en el eje Y

        tt.setByY(distancia);

        // Poner en bucle

        tt.setCycleCount(TranslateTransition.INDEFINITE);
        tt.setAutoReverse(true);

        tt.play();
    }

    // --------------- INCREMENTAR Y DISMINUIR TAMAÑO DEL PANEL PRINCIPAL AL PASAR EL CURSOR ---------------
    @FXML
    private void aumentarTamañoPanelPrincipal(MouseEvent event) {

        vidrioPrincipal.setScaleX(vidrioPrincipal.getScaleX() + 0.2);
        vidrioPrincipal.setScaleY(vidrioPrincipal.getScaleY() + 0.2);

    }

    @FXML
    private void disminuirTamañoPanelPrincipal(MouseEvent event) {

        vidrioPrincipal.setScaleX(vidrioPrincipal.getScaleX() - 0.2);
        vidrioPrincipal.setScaleY(vidrioPrincipal.getScaleY() - 0.2);

    }

    // --------------- INCREMENTAR Y DISMINUIR TAMAÑO DEL PANEL ANTERIOR AL PASAR EL CURSOR ---------------
    @FXML
    private void aumentarTamañoPanelAnterior(MouseEvent event) {

        vidrioAnterior.setScaleX(vidrioAnterior.getScaleX() + 0.2);
        vidrioAnterior.setScaleY(vidrioAnterior.getScaleY() + 0.2);

    }

    @FXML
    private void disminuirTamañoPanelAnterior(MouseEvent event) {

        vidrioAnterior.setScaleX(vidrioAnterior.getScaleX() - 0.2);
        vidrioAnterior.setScaleY(vidrioAnterior.getScaleY() - 0.2);

    }


    // --------------- INCREMENTAR Y DISMINUIR TAMAÑO DEL PANEL POSTERIOR AL PASAR EL CURSOR ---------------
    @FXML
    private void aumentarTamañoPanelPosterior(MouseEvent event) {

        vidrioPosterior.setScaleX(vidrioPosterior.getScaleX() + 0.2);
        vidrioPosterior.setScaleY(vidrioPosterior.getScaleY() + 0.2);

    }

    @FXML
    private void disminuirTamañoPanelPosterior(MouseEvent event) {

        vidrioPosterior.setScaleX(vidrioPosterior.getScaleX() - 0.2);
        vidrioPosterior.setScaleY(vidrioPosterior.getScaleY() - 0.2);

    }


    // --------------- CAPTURA ----------------

    @FXML
    public void accederCaptura(MouseEvent event) {
        try {
            System.out.println("Cargando la vista captura...");

            // cargamos la vista principal
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/captura/captura.fxml"));
            Parent root = loader.load(); 

            // Recibir el click
            javafx.scene.Node source = (javafx.scene.Node) event.getSource();

            // Recuperar la ventana
            Stage primaryStage = (Stage) source.getScene().getWindow();
            
            Scene scene = new Scene(root);

            // Cargar el CSS
            String css = this.getClass().getResource("/view/captura/captura.css").toExternalForm();
            scene.getStylesheets().add(css);

            // Titulo, forzar el tamaño de la ventana y bloquear cambio manual
            primaryStage.setTitle("PokeINC - Captura");
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

            mediaPlayer.stop();
            primaryStage.show();
        } catch (Exception e) {
            System.out.println("Error al cambiar de ventana - " + e.getMessage());
            e.printStackTrace();
        }
    }


    // --------------- CRIANZA ----------------

    @FXML
    public void accederCrianza(MouseEvent event) {
        try {
            System.out.println("Cargando la vista crianza...");

            // Recibir el click
            javafx.scene.Node source = (javafx.scene.Node) event.getSource();

            // Recuperar la ventana
            Stage primaryStage = (Stage) source.getScene().getWindow();

            // Cargar la vista Principal
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/crianza/crianza.fxml"));
            Parent root = loader.load();
            
            Scene scene = new Scene(root);

            // Titulo, forzar el tamaño de la ventana y bloquear cambio manual
            primaryStage.setTitle("PokeINC - Crianza");
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
            mediaPlayer.stop();

            primaryStage.show();
        } catch (Exception e) {
            System.out.println("Error al cambiar de ventana - " + e.getMessage());
            e.printStackTrace();
        }
    }


    // --------------- EQUIPO ----------------

    @FXML
    public void accederEquipo(MouseEvent event) {
        try {
            System.out.println("Cargando la vista Equipo...");

            // Recibir el click
            javafx.scene.Node source = (javafx.scene.Node) event.getSource();

            // Recuperar la ventana
            Stage primaryStage = (Stage) source.getScene().getWindow();

            // Cargar la vista Principal
            Parent root = FXMLLoader.load(getClass().getResource("/view/equipo/equipo.fxml"));
            Scene scene = new Scene(root);


            // Titulo, forzar el tamaño de la ventana y bloquear cambio manual
            primaryStage.setTitle("PokeINC - Equipo");
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

            mediaPlayer.stop();
            primaryStage.show();
        } catch (Exception e) {
            System.out.println("Error al cambiar de ventana - " + e.getMessage());
            e.printStackTrace();
        }
    }


    // --------------- ENTRENAMIENTO ----------------

    @FXML
    public void accederEntrenamiento(MouseEvent event) {
        try {
            System.out.println("Cargando la vista Entrenamiento...");

            // Recibir el click
            javafx.scene.Node source = (javafx.scene.Node) event.getSource();

            // Recuperar la ventana
            Stage primaryStage = (Stage) source.getScene().getWindow();

            // Cargar la vista Principal
            Parent root = FXMLLoader.load(getClass().getResource("/view/entrenamiento/entrenamiento.fxml"));
            Scene scene = new Scene(root);


            // Titulo, forzar el tamaño de la ventana y bloquear cambio manual
            primaryStage.setTitle("PokeINC - Entrenamiento");
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

            mediaPlayer.stop();
            primaryStage.show();
        } catch (Exception e) {
            System.out.println("Error al cambiar de ventana - " + e.getMessage());
            e.printStackTrace();
        }
    }


    // --------------- POKEDEX ----------------

    @FXML
    public void accederPokedex(MouseEvent event) {
        try {
            System.out.println("Cargando la vista Pokedex...");

            // Recibir el click
            javafx.scene.Node source = (javafx.scene.Node) event.getSource();

            // Recuperar la ventana
            Stage primaryStage = (Stage) source.getScene().getWindow();

            // Cargar la vista Principal
            Parent root = FXMLLoader.load(getClass().getResource("/view/pokedex/pokedex.fxml"));
            Scene scene = new Scene(root);


            // Titulo, forzar el tamaño de la ventana y bloquear cambio manual
            primaryStage.setTitle("PokeINC - Pokedex");
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

            mediaPlayer.stop();
            primaryStage.show();
        } catch (Exception e) {
            System.out.println("Error al cambiar de ventana - " + e.getMessage());
            e.printStackTrace();
        }
    }


    // --------------- CASINO ----------------

    @FXML
    public void accederCasino(MouseEvent event) {
        try {
            System.out.println("Cargando la vista Casino...");

            // Recibir el click
            javafx.scene.Node source = (javafx.scene.Node) event.getSource();

            // Recuperar la ventana
            Stage primaryStage = (Stage) source.getScene().getWindow();

            // Cargar la vista Principal
            Parent root = FXMLLoader.load(getClass().getResource("/view/casino/casino.fxml"));
            Scene scene = new Scene(root, 720, 720);


            // Titulo, forzar el tamaño de la ventana y bloquear cambio manual
            primaryStage.setTitle("PokeINC - Casino");
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
            primaryStage.sizeToScene();  
            primaryStage.centerOnScreen();

            // Mostrar la escena

            mediaPlayer.stop();
            primaryStage.show();
        } catch (Exception e) {
            System.out.println("Error al cambiar de ventana - " + e.getMessage());
            e.printStackTrace();
        }
    }
}
