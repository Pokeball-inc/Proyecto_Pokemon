package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import model.AdivinarNumero;
import model.Sesion;
import dao.EntrenadorDAO;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class CasinoNumeroController implements Initializable {

	@FXML
	private Label lblIntentos;
	@FXML
	private Label lblPokedollares;
	@FXML
	private TextField txtNumero;
	@FXML
	private Button btnReiniciar;

	private AdivinarNumero juego;
	private EntrenadorDAO entrenadorDAO = new EntrenadorDAO();

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// iniciamos el juego con el entrenador de la sesion
		juego = new AdivinarNumero(Sesion.entrenadorLogueado);

		// comprobamos que pueda apostar, coste fijo de 150 para jugar
		if (juego.puedeApostar(150)) {
			// descontamos el coste
			juego.costeApuesta(150);

			// guardamos en la bd
			entrenadorDAO.actualizarPokedollares(Sesion.entrenadorLogueado.getIdEntrenador(),
					Sesion.entrenadorLogueado.getPokedollares());
			// actualizamos el ui con los pokedollares que tiene el entrenador
			actualizarUI();

			// generamos el numero secreto aleatorio entre 1 y 20
			juego.setNumeroSecreto((int) (Math.random() * 20) + 1);
			// reiniciamos los intentos a 5 para empezar la partida
			juego.setIntentosRestantes(5);
			// marcamos que todavia no ha acertado
			juego.setacertado(false);
		} else {
			// si no tiene dinero avisamos y bloqueamos la caja de texto para que no juegue
			lblIntentos.setText("¡Dinero insuficiente!");
			txtNumero.setDisable(true);
		}
		// ocultamos el boton
	    btnReiniciar.setVisible(false);
	}

	@FXML
	void adivinarClick(ActionEvent event) {
		try {
			// si ya no quedan intentos o ya gano salimos del metodo para no hacer nada
			if (juego.getIntentosRestantes() <= 0 || juego.getAcertado()) {
				return;
			}
			// leemos el numero que el usuario escribio
			int numUser = Integer.parseInt(txtNumero.getText());
			juego.setNumeroIntroducido(numUser);

			// comparamos el numero del usuario con el secreto generado al inicio
			if (numUser == juego.getNumeroSecreto()) {
				// si acierta marcamos victoria y llamamos al premio segun los intentos que le quedaron
				juego.setacertado(true);
				juego.cantidadPremio(150);

				lblIntentos.setText("¡HAS GANADO!");
				
				// bloqueamos la entrada de datos y mostramos boton reiniciar
	            txtNumero.setDisable(true); 
	            btnReiniciar.setVisible(true);

				// actyualizamos  las ganancias en la bd
				entrenadorDAO.actualizarPokedollares(Sesion.entrenadorLogueado.getIdEntrenador(),
						Sesion.entrenadorLogueado.getPokedollares());
			} else {
				// si falla restamos un intento y actualizamos el texto de la pantalla
				juego.setIntentosRestantes(juego.getIntentosRestantes() - 1);
				lblIntentos.setText("Intentos restantes: " + juego.getIntentosRestantes());

				// si llega a cero intentos mostramos que se acaba y revelamos el numero
				if (juego.getIntentosRestantes() <= 0) {
					lblIntentos.setText("GAME OVER. Era el: " + juego.getNumeroSecreto());
					
					// bloqueamos la entrada de datos y mostramos boton reiniciar
		            txtNumero.setDisable(true); 
		            btnReiniciar.setVisible(true);
				}
			}
			// actualizamos el ui con los pokedollares que tiene el entrenador
			actualizarUI();
			// limpiamos la caja de texto para que sea mas comodo escribir el siguiente numero
			txtNumero.clear();

		} catch (NumberFormatException e) {
			lblIntentos.setText("¡Número no válido!");
		}
	}
	
	@FXML
	// metodo para reiniciar el juego al acabarlo
	void reiniciarJuego(ActionEvent event) {
	    // volvemos a comprobar si tiene dinero para otra partida
	    if (juego.puedeApostar(150)) {
	        juego.costeApuesta(150);
	        
	        // actualizamos la bd
	        entrenadorDAO.actualizarPokedollares(Sesion.entrenadorLogueado.getIdEntrenador(),
	                Sesion.entrenadorLogueado.getPokedollares());

	        // reseteamos la logica del juego
	        juego.setNumeroSecreto((int) (Math.random() * 20) + 1);
	        juego.setIntentosRestantes(5);
	        juego.setacertado(false);

	        // limpiamos la interfaz
	        lblIntentos.setText("Intentos restantes: 5");
	        txtNumero.setDisable(false);
	        txtNumero.clear();
	        btnReiniciar.setVisible(false); // Escondemos el botón de reiniciar hasta que acabe esta partida
	        actualizarUI();
	        
	        System.out.println("Juego reiniciado. Nuevo número secreto generado.");
	    } else {
	        lblIntentos.setText("No tienes dinero para reintentar");
	    }
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