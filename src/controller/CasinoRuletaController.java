package controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ComboBox;
import javafx.event.ActionEvent;
import model.Ruleta;
import model.Sesion;
import dao.EntrenadorDAO;
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
	private ComboBox<Ruleta.formaApuesta> comboTipoApuesta;
	@FXML
	private ComboBox<Ruleta.EleccionColor> comboColor;

	private Ruleta juego;
	private EntrenadorDAO entrenadorDAO = new EntrenadorDAO();

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// inicializamos la ruleta con el entrenador de la sesion
		juego = new Ruleta();
		juego.setEntrenador(Sesion.entrenadorLogueado);

		// rellenamos los combobox con los valores de los enums
		comboTipoApuesta.getItems().setAll(Ruleta.formaApuesta.values());
		comboColor.getItems().setAll(Ruleta.EleccionColor.values());

		// actualizamos el ui con los pokedollares que tiene el entrenador
		actualizarUI();
	}

	@FXML
	void girarClick(ActionEvent event) {
		try {
			// leemos los valores de la interfaz
			int cantidad = Integer.parseInt(txtApuesta.getText());
			int numElegido = 0;

			// si el usuario no ha elegido numero ponemos 0 por defecto
			if (!txtNumeroElegido.getText().isEmpty()) {
				numElegido = Integer.parseInt(txtNumeroElegido.getText());
			}

			Ruleta.formaApuesta tipo = comboTipoApuesta.getValue();
			Ruleta.EleccionColor color = comboColor.getValue();

			// validamos que haya seleccionado el tipo de apuesta
			if (tipo == null) {
				lblResultado.setText("Selecciona un tipo de apuesta: ");
				return;
			}

			// llamamos al metodo jugar ruleta
			String mensaje = juego.jugarRuleta(tipo, numElegido, color, cantidad);

			// mostramos el resultado en la pantalla
			lblResultado.setText(mensaje);

			// si el mensaje no es de error, auctaualizamos la bd
			if (Sesion.entrenadorLogueado != null) {
				entrenadorDAO.actualizarPokedollares(Sesion.entrenadorLogueado.getIdEntrenador(),
						Sesion.entrenadorLogueado.getPokedollares());
			}

			// actualizamos el ui con los pokedollares que tiene el entrenador
			actualizarUI();

		} catch (NumberFormatException e) {
			lblResultado.setText("Revisa los numeros introducidos");
		}
	}

	// metodo para actualizar los pokedollares del entrenador en la vista
	private void actualizarUI() {
		if (Sesion.entrenadorLogueado != null) {
			lblPokedollares.setText(Sesion.entrenadorLogueado.getPokedollares() + " Pokedóllares");
		}
	}
}