package controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;
import model.AdivinarNumero;
import model.Sesion;
import dao.EntrenadorDAO;
import java.net.URL;
import java.util.ResourceBundle;

public class CasinoNumeroController implements Initializable {

	@FXML
	private Label lblIntentos;
	@FXML
	private Label lblPokedolares;
	@FXML
	private TextField txtNumero;

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

				// actyualizamos  las ganancias en la bd
				entrenadorDAO.actualizarPokedollares(Sesion.entrenadorLogueado.getIdEntrenador(),
						Sesion.entrenadorLogueado.getPokedollares());
			} else {
				// si falla restamos un intento y actualizamos el texto de la pantalla
				juego.setIntentosRestantes(juego.getIntentosRestantes() - 1);
				lblIntentos.setText("Intentos: " + juego.getIntentosRestantes());

				// si llega a cero intentos mostramos que se acaba y revelamos el numero
				if (juego.getIntentosRestantes() <= 0) {
					lblIntentos.setText("GAME OVER. Era el: " + juego.getNumeroSecreto());
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

	// metodo para actualizar los pokedollares del entrenador en la vista
	private void actualizarUI() {
		if (Sesion.entrenadorLogueado != null) {
			lblPokedolares.setText(Sesion.entrenadorLogueado.getPokedollares() + " PD");
		}
	}
}