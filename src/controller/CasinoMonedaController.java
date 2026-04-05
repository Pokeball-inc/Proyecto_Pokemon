package controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import model.CaraCruz;
import model.Sesion;
import dao.EntrenadorDAO;
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
		try {
			// leemos la cantidad que el usuario quiere apostar desde el input
			int cantidadApuesta = Integer.parseInt(txtApuesta.getText());

			// comprobamos si el entrenador tiene dinero suficiente para esa apuesta
			if (juego.puedeApostar(cantidadApuesta)) {

				// ejecutamos la logica caracruz
				// este metodo ya descuenta si pierde o suma el doble si gana
				String mensajeResultado = juego.jugarMoneda(eleccionUsuario, cantidadApuesta);
				
				// obtenemos el resultado que ha salido 
				if (juego.getResultadoUltimoLanzamiento() == CaraCruz.Eleccion.CARA) {
					// si sale cara cargamos la imagen de pikachu
					imgMoneda.setImage(new javafx.scene.image.Image(getClass().getResourceAsStream("/imgs/Casino/VMoneda/caraMoneda.png")));
				} else {
					// si sale cruz cargamos la imagen de jigglypuff
					imgMoneda.setImage(new javafx.scene.image.Image(getClass().getResourceAsStream("/imgs/Casino/VMoneda/cruzMoneda.png")));
				}

				// mostramos el mensaje que devuelve el resultado
				lblResultado.setText(mensajeResultado);

				// actyualizamos las ganancias en la bd
				entrenadorDAO.actualizarPokedollares(Sesion.entrenadorLogueado.getIdEntrenador(),
						Sesion.entrenadorLogueado.getPokedollares());

				// actualizamos el ui con los pokedollares que tiene el entrenador
				actualizarUI();

			} else {
				// si no tiene dinero suficiente avisamos al usuario
				lblResultado.setText("¡No tienes suficientes Pokedóllares!");
			}

		} catch (NumberFormatException e) {
			lblResultado.setText("¡Introduce una apuesta válida!");
		}
	}

	// metodo para actualizar los pokedollares del entrenador en la vista
	private void actualizarUI() {
		if (Sesion.entrenadorLogueado != null) {
			lblPokedollares.setText(Sesion.entrenadorLogueado.getPokedollares() + " Pokedóllares");
		}
	}
}