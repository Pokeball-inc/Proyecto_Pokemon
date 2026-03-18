package controller;

import javafx.animation.Interpolator;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Duration;

import javafx.scene.input.MouseEvent;

import java.awt.event.ActionEvent;
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

public class LoginController implements Initializable {

// ---------------------------- PARTICULAS DEL LOGIN ---------------------------- \\

	// Inicializar objeto Pane panelParticulas

	@FXML
	private Pane panelParticulas;

	@FXML
	private TextField textoUsuarioLogin;

	@FXML
	private TextField textoContrasenaLogin;

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

// ---------------------------- BOTÓN DE ACCEDER ---------------------------- \\

// Metodo para Acceder -- MouseEvent y cargar vista Principal

	@FXML
	public void accionAcceder(MouseEvent event) {
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

	void loguearse(ActionEvent event) {
		if (textoUsuarioLogin.getText().isEmpty()) {
			// lblError.setText("Error: inserta nombre de usuario");
		}

		// J Option Panel
		int opcion = JOptionPane.showConfirmDialog(null, "Usuario NO encontrado, ¿desea registrarlo?");
		// si la rspuesta es si
		if (opcion == JOptionPane.YES_NO_OPTION) {
			System.out.println("Usuario registrado");
		} else {
			textoUsuarioLogin.setText(" ");
			textoContrasenaLogin.setText(" ");
		}
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
