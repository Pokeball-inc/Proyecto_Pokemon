package controller;

import javafx.animation.Interpolator;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.Entrenador;
import model.Sesion;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.sql.Connection;
import java.util.ResourceBundle;

import dao.EntrenadorDAO;

import javax.swing.JOptionPane;

import bd.ConexionBBDD;
import dao.PokemonDAO;
import model.UbicacionPokemon;

public class LoginController implements Initializable {

// ---------------------------- PARTICULAS DEL LOGIN ---------------------------- \\

    // Inicializar objeto Pane panelParticulas

    @FXML
    private Pane panelParticulas;

    @FXML
    private TextField loginusuario;

    @FXML
    private TextField logincontra;

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
// ---------------------------- PARTICULAS DEL LOGIN ---------------------------- \\

// ---------------------------- BOTÓN DE SALIR ---------------------------- \\

// Metodo para  Salir -- MouseEvent.

    @FXML
    public void accionSalir(MouseEvent event) {
        try {
            System.out.println("El juego se ha cerrado correctamente");
            System.exit(0);
        } catch (Exception e) {
            System.out.println("El juego no se ha cerrado correctamente" + e.getMessage());
        }
    }

// ---------------------------- BOTÓN DE ACCEDER CON LOGIN  ---------------------------- \\

// Metodo para Acceder -- MouseEvent  cargar vista Principal

    // metodo con usuario y contraseña comprobando en base de datos, si usuario
    // nuevo da opcion de crear


    @FXML
    public void accionAcceder(MouseEvent event) {
        // obtencion de datos entrantes
        String usuario = loginusuario.getText();
        String contrasena = logincontra.getText();

        // declaramos la conexioon
        ConexionBBDD conexion = new ConexionBBDD();
        Connection con = conexion.getConexion();

        // comprobar campos vacios
        if (usuario.isEmpty() || contrasena.isEmpty()) {
            // mostramos un mensaje indicando que faltan datos
            JOptionPane.showMessageDialog(null, "Rellena todos los campos");
            return;
        }

        EntrenadorDAO dao = new EntrenadorDAO();
        
     // usamos el login ue nos devuelve el entrenadore cargado
        Entrenador entrenadorLogueado = dao.login(usuario, contrasena);

     // si entrenadorLogueado no es null, el login es correcto
        if (entrenadorLogueado != null) {
            // si login es correcto lo mostramos
            System.out.println("Login correcto: " + entrenadorLogueado.getNombreEntrenador()); 

            try {
            	
                //cargar los pokemon del entrenador de la BD
                PokemonDAO.obtenerPokemon(con, entrenadorLogueado, UbicacionPokemon.EQUIPO);
                PokemonDAO.obtenerPokemon(con, entrenadorLogueado, UbicacionPokemon.CAJA);
                System.out.println("Pokémon del entrenador descargados de la base de datos.");
                
               Sesion.entrenadorLogueado = entrenadorLogueado;

                // cargamos la vista pantalla principal
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/principal/vistaPrincipal.fxml"));
                Parent root = loader.load();


                // Configuramos la escena y la mostramos
                Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                Scene scene = new Scene(root);
                primaryStage.setScene(scene);
                primaryStage.show();

            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {
            // si el login falla pero se han introducido datos , preguntamos si quiere registrarse
            int opcion = JOptionPane.showConfirmDialog(null, "Usuario no existe. ¿Quieres registrarlo?");

            if (opcion == JOptionPane.YES_OPTION) {

                // creamos un nuevo entrenador con los datos introducidosICO
                Entrenador nuevo = new Entrenador();
                nuevo.setNombreEntrenador(usuario);
                nuevo.setContrasena(contrasena);

                // comprobamos si el usuario ya existe en la base de datos
                if (!dao.existeUsuario(usuario)) {
                    // si no existe, lo registramos
                    if (dao.registrar(nuevo)) {
                        JOptionPane.showMessageDialog(null, "Usuario registrado correctamente");
                    } else {
                        JOptionPane.showMessageDialog(null, "Error al registrar");
                    }

                } else {
                    // si ya existe, lo mostramos
                    JOptionPane.showMessageDialog(null, "El usuario ya existe");
                }

            } else {
                // si el usuario dice que no, limpiamos los campos
                loginusuario.clear();
                logincontra.clear();
            }
        }
    }


}
