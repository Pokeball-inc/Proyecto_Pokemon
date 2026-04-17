package controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.Entrenador;
import model.Sesion;

public class SeleccionCombateController implements Initializable {

	private Entrenador entrenadorActual = Sesion.entrenadorLogueado;
	@FXML
	private ImageView botonSalirEleccionCombate;
	@FXML
	private ImageView botonAccesoLiga;
	@FXML
	private ImageView botonAccesoCombateAleatorio;

	public void initialize(URL location, ResourceBundle resources) {

		// Entrenador actual

		System.out.println("Equipo: Entrenador " + entrenadorActual.getNombreEntrenador());

	}

	// --------------- INCREMENTAR Y DISMINUIR TAMAÑO DEL BOTON ACCESO LIGA
	// ---------------
	@FXML
	private void aumentarTamañoBoaumentarTamanoBotonAccesoLigatonAccesoLiga(MouseEvent event) {
		botonAccesoLiga.setScaleX(botonAccesoLiga.getScaleX() + 0.2);
		botonAccesoLiga.setScaleY(botonAccesoLiga.getScaleY() + 0.2);
	}

	@FXML
	private void disminuirTamanoBotonAccesoLiga(MouseEvent event) {
		botonAccesoLiga.setScaleX(botonAccesoLiga.getScaleX() - 0.2);
		botonAccesoLiga.setScaleY(botonAccesoLiga.getScaleY() - 0.2);
	}

	// --------------- INCREMENTAR Y DISMINUIR TAMAÑO DEL BOTON ACCESO COMBATE
	// ALEATORIO ---------------
	@FXML
	private void aumentarTamanoBotoncAccesoCombateAleatorio(MouseEvent event) {
		botonAccesoCombateAleatorio.setScaleX(botonAccesoCombateAleatorio.getScaleX() + 0.2);
		botonAccesoCombateAleatorio.setScaleY(botonAccesoCombateAleatorio.getScaleY() + 0.2);
	}

	@FXML
	private void disminuirTamanoBotoncAccesoCombateAleatorio(MouseEvent event) {
		botonAccesoCombateAleatorio.setScaleX(botonAccesoCombateAleatorio.getScaleX() - 0.2);
		botonAccesoCombateAleatorio.setScaleY(botonAccesoCombateAleatorio.getScaleY() - 0.2);
	}

	
	
	
	@FXML
	private void clickAccesoLiga(MouseEvent event) {
		// Por ahora lo dejamos vacío o con un log para probar que funciona
		System.out.println("Clic detectado: Cambiando a la Liga (Próximamente)");

		/*
		 * Aquí irá tu lógica de cambio de escena: FXMLLoader loader = new
		 * FXMLLoader(getClass().getResource("/view/liga/Liga.fxml")); ...
		 */
	}

	
	@FXML
	private void clickAccesoCombateAleatorio(MouseEvent event) {
		// Por ahora lo dejamos vacío o con un log para probar que funciona
		System.out.println("Clic detectado: Cambiando a la Liga (Próximamente)");

		/*
		 * Aquí irá tu lógica de cambio de escena: FXMLLoader loader = new
		 * FXMLLoader(getClass().getResource("/view/liga/Liga.fxml")); ...
		 */
	}

	
	
	// Boton de salir
	@FXML
	public void seleccionCombateSalir(MouseEvent event) {
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
