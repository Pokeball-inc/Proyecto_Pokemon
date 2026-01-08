package application;
	
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
 
public class Main extends Application {
 
  @Override
  public void start(Stage stage) {
    // Crear el layout y la escena
    StackPane root = new StackPane();
    Scene scene = new Scene(root, 320, 320);    
    
    // Cargar el icono 
    try {
    	Image logo = new Image(getClass().getResourceAsStream("/resources/logo.png"));
    	stage.getIcons().add(logo);
    } catch (Exception e) {
        System.out.println("No se pudo cargar el logo: " + e.getMessage());
    }
    // Establecer el título y mostrar
    stage.setTitle("Pokeball Inc.");
    stage.setScene(scene);
    stage.show();
  }
 
  public static void main(String[] args) {
    launch(args);
  }
}