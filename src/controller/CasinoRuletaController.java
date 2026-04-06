package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DialogPane;
import javafx.event.ActionEvent;
import model.Ruleta;
import model.Sesion;
import dao.EntrenadorDAO;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class CasinoRuletaController implements Initializable {

	@FXML
	private Label lblResultado;
	@FXML
	private Label lblPokedollares;
	@FXML
	private TextField txtApuesta;
	@FXML
	private TextField txtNumeroElegido;
	@FXML 
	private Button botonRojo;
    @FXML 
    private Button botonNegro;

	private Ruleta juego;
	private EntrenadorDAO entrenadorDAO = new EntrenadorDAO();
	private Ruleta.EleccionColor colorSeleccionado = null;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// inicializamos la ruleta con el entrenador de la sesion
		juego = new Ruleta();
		juego.setEntrenador(Sesion.entrenadorLogueado);

		// actualizamos el ui con los pokedollares que tiene el entrenador
		actualizarUI();
	}

	
    @FXML
 // metodo para el boton rojo
    void seleccionarRojo(ActionEvent event) {
        colorSeleccionado = Ruleta.EleccionColor.ROJO;
        // Efecto visual: borde dorado para saber que está seleccionado
        botonRojo.setStyle("-fx-border-color: #FFD700; -fx-border-width: 3px; -fx-background-color: red; -fx-text-fill: white;");
        botonNegro.setStyle("-fx-background-color: black; -fx-text-fill: white;"); 
    }

    @FXML
 // metodo para el boton negro
    void seleccionarNegro(ActionEvent event) {
        colorSeleccionado = Ruleta.EleccionColor.NEGRO;
        // Efecto visual: borde dorado
        botonNegro.setStyle("-fx-border-color: #FFD700; -fx-border-width: 3px; -fx-background-color: black; -fx-text-fill: white;");
        botonRojo.setStyle("-fx-background-color: red; -fx-text-fill: white;"); 
    }
	
	
    @FXML
    void girarClick(ActionEvent event) {
        try {
            // validamos que se haya escrito una cantidad para apostar
            if (txtApuesta.getText().isEmpty()) {
                mostrarAlertaRuleta("¡ESPERA!", "Falta la apuesta", "Introduce Pokedóllares", Alert.AlertType.WARNING);
                return;
            }
            // convertimos el texto de la apuesta a int para asegurar
            int cantidad = Integer.parseInt(txtApuesta.getText());

            // comprobamos que se ha elegido
            // valor por defecto si no se elige snumero
            int numElegido = -1; 
            boolean tieneNumero = !txtNumeroElegido.getText().isEmpty();
            boolean tieneColor = (colorSeleccionado != null);
            Ruleta.formaApuesta tipo;

            // comprobamos el tipo de apuesta segun los campos rellenos
            if (tieneNumero && tieneColor) {
            	// numero y color
                tipo = Ruleta.formaApuesta.NUMYCOLOR;
                numElegido = Integer.parseInt(txtNumeroElegido.getText());
            } else if (tieneNumero) {
            	// numero
                tipo = Ruleta.formaApuesta.NUMEROSOLO;
                numElegido = Integer.parseInt(txtNumeroElegido.getText());
            } else if (tieneColor) {
            	// color
                tipo = Ruleta.formaApuesta.COLORSOLO;
            } else {
                // si no hay nada seleccionado avisamos y paramos el juego
                mostrarAlertaRuleta("MESA VACÍA", "Hagan sus apuestas", "Elige un número o color", Alert.AlertType.INFORMATION);
                return;
            }

            // ejecutamos la logica del juego en Ruleta
            juego.jugarRuleta(tipo, numElegido, colorSeleccionado, cantidad);
            
            // recuperamos los resultados que ha generado la ruleta mediante los getters
            int numGanador = juego.getUltimoNumero(); 
            String colorGanador = juego.getUltimoColor();
            int premio = juego.getUltimoPremio();

            // preparamos los textos de la alerta
            String cabecera;
            String textoPremio;
            Alert.AlertType tipoAlerta;
            // comprobamos el numero seleccionado con el numero ganador
            boolean acertoNumero = (numElegido == numGanador);
            // comparamos el color seleccionado con el color ganador
            boolean acertoColor = (colorSeleccionado != null && colorSeleccionado.toString().equals(colorGanador));

            if (premio > 0) {
                tipoAlerta = Alert.AlertType.INFORMATION;
                
                // se acierta numero y color
                if (acertoNumero && acertoColor) {
                    cabecera = "¡¡¡PREMIO GORDO!!!";
                    textoPremio = "¡Has acertado el número y el color!\nGanaste: +" + premio + " Pokedóllares";
                } 
                // se acierta numero
                else if (acertoNumero) {
                    cabecera = "¡BIEN! ACERTASTE EL NÚMERO";
                    textoPremio = "El color no es correcto, pero el número sí.\nGanaste: +" + premio + " Pokedóllares";
                } 
                // se acierta color
                else {
                    cabecera = "¡ALGO ES ALGO! ACERTASTE EL COLOR";
                    textoPremio = "El numero no es correcto, pero el color sí.\nGanaste: +" + premio + " Pokedóllares";
                }
            } else {
                // no se acierta nada
                cabecera = "HAS PERDIDO...";
                textoPremio = "No has acertado ni el número ni el color.\nSuerte para la próxima";
                tipoAlerta = Alert.AlertType.WARNING;
            }

            // preparamos el resultado
            String resultado = "Ha salido el " + numGanador + " (" + colorGanador + ")";

            // mostramos la alerta
            mostrarAlertaRuleta(cabecera, resultado, textoPremio, tipoAlerta);

         // actyualizamos las ganancias en la bd
            if (Sesion.entrenadorLogueado != null) {
                entrenadorDAO.actualizarPokedollares(Sesion.entrenadorLogueado.getIdEntrenador(),
                        Sesion.entrenadorLogueado.getPokedollares());
            }

            // Refrescamos la etiqueta de dinero en la pantalla
            actualizarUI();
            
            // Limpiamos los campos y colores para la siguiente jugada
            resetearApuesta(); 

        } catch (NumberFormatException e) {
            // Si el usuario mete letras en lugar de números, saltará este error
            mostrarAlertaRuleta("ERROR", "Formato no válido", "Introduce solo números", Alert.AlertType.ERROR);
        }
    }

    
 // dejamos la vista de la ruleta por defecto sin las cosas marcadas y numero introducidos
    private void resetearApuesta() {
        // borramos la elección interna del color
        colorSeleccionado = null;
        
        // limpiamos los cuadros de texto
        txtNumeroElegido.clear();
        txtApuesta.clear();
        
        // quitamos el borde dorado de los botones
        botonRojo.setStyle("-fx-background-color: red; -fx-text-fill: white; -fx-background-radius: 10;");
        botonNegro.setStyle("-fx-background-color: black; -fx-text-fill: white; -fx-background-radius: 10;");
    }


	// metodo para crear la ventana emergente con el resultadod e la ruleta
    private void mostrarAlertaRuleta(String mensajeGanador, String resultadoRuleta, String cantidadGanada, Alert.AlertType tipo) {
        Alert alerta = new Alert(tipo);
        
        // configuramos los textos de la alerta
        alerta.setTitle("Resultado de la Ruleta");
        
        // mostrara  "¡HAS GANADO!" o "HAS PERDIDO"
        alerta.setHeaderText(mensajeGanador);
        
        // muestra el numero, color y, si se gana el premio, el premio
        alerta.setContentText(resultadoRuleta + "\n" + cantidadGanada);

        DialogPane dialogPane = alerta.getDialogPane();
        
     // ponemos el icono de pokemon en la esquina de la ventana
        try {
            Stage stage = (Stage) dialogPane.getScene().getWindow();
            stage.getIcons().add(new Image(new File("imgs/Login/Login-icon.png").toURI().toString()));
        } catch (Exception e) { }

     // vinculamos el css correspondiente segun si es victoria o derrota
        if(tipo == Alert.AlertType.WARNING) {
            dialogPane.getStylesheets().add(getClass().getResource("/view/captura/alertas2.css").toExternalForm());
        } else {
            dialogPane.getStylesheets().add(getClass().getResource("/view/captura/alertas.css").toExternalForm());
        }

        alerta.showAndWait(); // bloquea la aplicacion hasta se cierra el pop-up
    }
    
	// metodo para actualizar los pokedollares del entrenador en la vista
	private void actualizarUI() {
		if (Sesion.entrenadorLogueado != null) {
			lblPokedollares.setText(Sesion.entrenadorLogueado.getPokedollares() + " Pokedóllares");
		}
	}
	
    @FXML
    // metodo para el boton para volver al casino
    void clickSalir(MouseEvent event) { 
        try {
            // cargamos el FXML del menu principal
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/casino/Casino.fxml"));
            Parent root = loader.load();

         // obtenemos la ventana actual a partir del boton pulsado
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // creamos la escena con el tamaño original del menú principal
            Scene scene = new Scene(root, 720, 720);
            
            stage.setScene(scene);
            stage.setTitle("Pokémon - Casino");
            stage.centerOnScreen();
            stage.show();

        } catch (IOException e) {
            System.err.println("Error al intentar volver al Menú Principal: " + e.getMessage());
            e.printStackTrace();
        }
    }

}