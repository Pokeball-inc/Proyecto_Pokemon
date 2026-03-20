package main;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import java.io.File;

import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.scene.Scene;

public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
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
		} catch(Exception e) {
			System.out.println("Error: Se ha producido un error inesperado. "+e.getMessage());
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
