package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.CaraCruz;
import model.Sesion;
import dao.EntrenadorDAO;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class CasinoMonedaController implements Initializable {

	@FXML
	private Label lblResultado;
	@FXML
	private Label lblPokedollares;
	@FXML
	private TextField txtApuesta;
	@FXML 
	private ImageView imgMoneda;

	private CaraCruz juego;
	private EntrenadorDAO entrenadorDAO = new EntrenadorDAO();

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// iniciamos el juego con el entrenador de la sesion
		juego = new CaraCruz(Sesion.entrenadorLogueado);

		// mostramos el saldo actual del entrenador nada mas cargar la pantalla
		actualizarUI();
	}

	// metodo que se ejecuta al pulsar el boton CARAde pikachu (cara)
	@FXML
	void clickCara(MouseEvent event) {
		jugar(CaraCruz.Eleccion.CARA);
	}

	// metodo que se ejecuta al pulsar el boton CRUZ
	@FXML
	void clickCruz(MouseEvent event) {
		jugar(CaraCruz.Eleccion.CRUZ);
	}

	// metodo para la apuesta y el resultado
	private void jugar(CaraCruz.Eleccion eleccionUsuario) {
		
		lblResultado.setVisible(false);
	    lblResultado.setManaged(false);
	    
		try {
			// leemos la cantidad que el usuario quiere apostar desde el input
			int cantidadApuesta = Integer.parseInt(txtApuesta.getText());

			// comprobamos si el entrenador tiene dinero suficiente para esa apuesta
			if (juego.puedeApostar(cantidadApuesta)) {

				// ejecutamos la logica caracruz
				// este metodo ya descuenta si pierde o suma el doble si gana
				String mensajeResultado = juego.jugarMoneda(eleccionUsuario, cantidadApuesta);
				
				// definimos la ruta de la imagen segun lo que salio
				String rutaImagen;
	            String resultadoTexto;
				
				// obtenemos el resultado que ha salido 
				if (juego.getResultadoUltimoLanzamiento() == CaraCruz.Eleccion.CARA) {
					// si sale cara cargamos la imagen de pikachu
					rutaImagen = "imgs/Casino/VMoneda/caraMoneda.png";
	                resultadoTexto = "CARA";
				} else {
					// si sale cruz cargamos la imagen de la pokeball
					rutaImagen = "imgs/Casino/VMoneda/cruzMoneda.png";
	                resultadoTexto = "CRUZ";
				}

				// mostramos el mensaje que devuelve el resultado
				lblResultado.setText(mensajeResultado);
				
				// comprobamos si ha ganado para elegir el tipo de alerta
	            if (mensajeResultado.contains("ganado")) {
	                mostrarAlertaCasino("¡Victoria!", "¡Ha salido " + resultadoTexto + "!", 
	                                   "¡Enhorabuena! has ganado " + (cantidadApuesta * 2) + " pokedollares.", 
	                                   Alert.AlertType.INFORMATION, rutaImagen);
	            } else {
	                mostrarAlertaCasino("¡Derrota!", "¡Ha salido " + resultadoTexto + "!", 
	                                   "Lo sentimos, has perdido tu apuesta.", 
	                                   Alert.AlertType.WARNING, rutaImagen);
	            }

				// actyualizamos las ganancias en la bd
				entrenadorDAO.actualizarPokedollares(Sesion.entrenadorLogueado.getIdEntrenador(),Sesion.entrenadorLogueado.getPokedollares());

				// actualizamos el ui con los pokedollares que tiene el entrenador
				actualizarUI();

			} else {
				// si no tiene dinero suficiente avisamos al usuario
				mostrarMensajeError("¡No tienes suficientes Pokedóllares!");
			}

		} catch (NumberFormatException e) {
			mostrarMensajeError("¡Introduce una apuesta válida!");
		}
	}

	// metodo para actualizar los pokedollares del entrenador en la vista
	private void actualizarUI() {
		if (Sesion.entrenadorLogueado != null) {
			lblPokedollares.setText(Sesion.entrenadorLogueado.getPokedollares() + " Pokedóllares");
		}
	}
	
	
	// metodo para crear la ventana emergente con la imagen de la moneda
		private void mostrarAlertaCasino(String titulo, String cabecera, String contenido, Alert.AlertType tipo, String rutaImagenMoneda) {
	        Alert alerta = new Alert(tipo);
	        alerta.setTitle(titulo);
	        alerta.setHeaderText(cabecera);
	        alerta.setContentText(contenido);

	        try {
	            // creamos la imagen de la moneda que ha salido
	            String urlImagen = new File(rutaImagenMoneda).toURI().toString();
	            ImageView imagenMoneda = new ImageView(new Image(urlImagen));
	            
	            // ajustamos el tamaño para que quepa bien en el pop-up
	            imagenMoneda.setFitHeight(80);
	            imagenMoneda.setFitWidth(80);
	            
	            // establecemos esta imagen como el icono principal de la alerta
	            alerta.setGraphic(imagenMoneda); 
	        } catch (Exception e) {
	            System.out.println("Error al cargar la imagen en la alerta");
	        }

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

	
		// metodo nuevo para gestionar la aparicion del texto del label
		private void mostrarMensajeError(String texto) {
		    lblResultado.setText(texto);
		    lblResultado.setVisible(true);
		    lblResultado.setManaged(true);
		}
	
	    @FXML
	    // metodo para el boton para salir al menu principal
	    void clickSalir(MouseEvent event) { 
	        try {
	            // cargamos el FXML del menu principal
	            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/principal/vistaPrincipal.fxml"));
	            Parent root = loader.load();

	         // obtenemos la ventana actual a partir del boton pulsado
	            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

	            // creamos la escena con el tamaño original del menú principal
	            Scene scene = new Scene(root, 1074, 607);
	            
	            stage.setScene(scene);
	            stage.setTitle("Pokémon - Menú Principal");
	            stage.centerOnScreen();
	            stage.show();

	        } catch (IOException e) {
	            System.err.println("Error al intentar volver al Menú Principal: " + e.getMessage());
	            e.printStackTrace();
	        }
	    }
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}