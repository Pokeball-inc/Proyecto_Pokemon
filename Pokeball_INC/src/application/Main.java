package application;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import java.io.InputStream;
 
public class Main extends Application {
 
  @Override
  public void start(Stage stage) {
    try {
        // 1. CARGAR EL FXML 

        FXMLLoader loader = new FXMLLoader(getClass().getResource("Main.fxml"));
        Parent rootFXML = loader.load(); 

        // 2. CREAR LA ESCENA con el contenido del FXML
        Scene scene = new Scene(rootFXML, 320, 320);    
        
        // 3. CARGAR EL ICONO
        InputStream iconStream = getClass().getResourceAsStream("/resources/logo.png");
        if (iconStream != null) {
            stage.getIcons().add(new Image(iconStream));
        }

        // 4. CONFIGURAR EL STAGE
        stage.setTitle("Pokeball Inc.");
        stage.setScene(scene);
        stage.show();

    } catch (Exception e) {
        System.out.println("Error al iniciar la aplicación: " + e.getMessage());
        e.printStackTrace(); // Esto te dirá exactamente qué falló
    }
  }
 
  public static void main(String[] args) {
    launch(args);
  }
}