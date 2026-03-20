package controller;

import javafx.animation.Interpolator;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.text.Text;

import java.io.File;
import java.lang.reflect.Executable;
import java.net.URL;
import java.sql.Connection;
import java.util.ResourceBundle;

import dao.CapturaDao;
import model.Pokemon;
import bd.ConexionBBDD;

public class CapturaController implements Initializable {

    // PANEL PARTICULAS

    @FXML
    private Pane panelParticulas;

    @FXML
    private Text txtNombre; // para cambiar el nombre del pokemon

    @FXML
    private Text txtTipo1; // para el primer tipo

    @FXML
    private Text txtTipo2; // para el segundo tipo

    @FXML
    private Line barraTipos;

    @FXML
    private Text lblTipo1;

    @FXML
    private Text lblTipo2;

    @FXML
    private ImageView imgPokemonActual; // imagen del pokemon

    private Pokemon pokemonActual;

    @FXML
    private Text vidaCaptura;

    // botones
    @FXML
    private ImageView botonCaptura;

    @FXML
    private ImageView botonCambiar;

    @FXML
    private ImageView botonSalir;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        // sacamos el primer pokemon nada mas entrar 
         generarEncuentro();

        // 3. generamos las particulas (las 240 que pusimos para que mole mas) 
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
            Node source = (Node) event.getSource();

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


    // aqui esta a medio falta poner la extension del sprite donde estan los espacions grandes


    @FXML
    // --- metodo para pedir un pokemon al dao y actualizar la vista
    private void generarEncuentro() {

        //instanciamos el dao y la conexion
        CapturaDao dao = new CapturaDao();
        ConexionBBDD conexion = new ConexionBBDD();
        Connection con = conexion.getConexion();

        // comprobamos que la conexion no sea nula
        if (con != null) {
            // usamos el metodo del dao y guardamos el resultado en pokemonActual
            this.pokemonActual = dao.crearPokemonAleatorio(con);

            // comprovbamos que no sea null el pokemon
            if (this.pokemonActual != null) {
                // actualizamos el nombre en la vista
                txtNombre.setText(this.pokemonActual.getNombrePokemon());

                // cargamos la imagen correspondiente al nombre del pokemon
                try {
                    // pasamos el nombre a minusculas y ponemos extension del sprite
                    String nombreSprite = this.pokemonActual.getNombrePokemon().toLowerCase() + "                       ";

                    // buscamos el archivo en tu carpeta de sprites
                    String rutaImagen = "imgs/Pokemons/" + this.pokemonActual.getImgFrontalPokemon();
                    String rutaImagenAdaptada = new java.io.File(rutaImagen).toURI().toString();

                    // ESTABLECE EL TEXTO DE TIPO1 AL DELPOKEMON ACTUAL
                    this.txtTipo1.setText(this.pokemonActual.getTipoPrincipal().toString());


                    // ESTABLECE EL TEXTO DE TIPO2 AL DELPOKEMON ACTUAL Y ADAPTA LOS ELEMENTOS VISUALES

                    if (this.pokemonActual.getTipoSecundario() == null) {
                        this.txtTipo2.setText("");
                        this.lblTipo1.setText("Tipo");
                        this.lblTipo2.setVisible(false);
                        this.barraTipos.setVisible(false);
                        this.txtTipo1.setLayoutX(481);
                        this.txtTipo1.setLayoutY(384);
                        this.lblTipo1.setLayoutX(504);
                        this.lblTipo1.setLayoutY(409);
                    } // ESTABLECE EL TEXTO DE TIPO2 AL DELPOKEMON ACTUAL Y ADAPTA LOS ELEMENTOS VISUALES
                    else {
                        this.lblTipo1.setText("Tipo 1");
                        this.lblTipo2.setVisible(true);
                        this.barraTipos.setVisible(true);
                        this.txtTipo1.setLayoutX(411);
                        this.txtTipo1.setLayoutY(387);
                        this.lblTipo1.setLayoutX(437);
                        this.lblTipo1.setLayoutY(409);
                        this.txtTipo2.setText(this.pokemonActual.getTipoSecundario().toString());
                    }
                    // ESTABLECE LA IMAGEN DEL POKEMON Y FUERZA A QUE NO CAMBIE EL TAMAÑO DEL IMAGEVIEW

                    if (rutaImagenAdaptada != null) {
                        Image img = new Image(rutaImagenAdaptada);
                        imgPokemonActual.setImage(img);
                        imgPokemonActual.setLayoutX(-94);
                        imgPokemonActual.setLayoutY(-106);
                        imgPokemonActual.setFitWidth(353);
                        imgPokemonActual.setFitHeight(282);
                        imgPokemonActual.setPreserveRatio(false);
                    } else {
                        System.out.println(rutaImagen);
                        System.out.println("no se ha encontrado el archivo: " + nombreSprite);
                    }

                    // CAMBIAR EL TEXTO DE VIDA A LA GENERADA AUTOMÁTICAMENTE

                    if (this.pokemonActual.getVitalidad() != 0) {
                        vidaCaptura.setText("HP / "+this.pokemonActual.getVitalidad());
                    }

                } catch (Exception e) {
                    System.out.println("Error al generar el pokemon aleatorio: " + e.getMessage());
                    e.printStackTrace();
                }
            }
        } else {
            System.out.println("error: la conexion con la bbdd es nula");
        }
    }


    @FXML
    // accion para el botod de cambiar pokemon
    public void clicCambiar(MouseEvent event) {
        // simplemente refrescamos la pantalla llamando al metodo que genera un encuentro
        // esto pedira otro pokemon aleatorio al dao y actualizara el sprite y el nombre

        try {

            generarEncuentro();
        } catch (Exception e) {
            System.out.println("Error: Se ha producido un error inesperado. " + e.getMessage());
            e.printStackTrace();
        }
    }


}
