package controller;

import java.io.File;
import java.net.URL;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import bd.ConexionBBDD;
import dao.PokemonDAO;
import model.Combate;
import model.Entrenador;
import model.Pokemon;
import model.Sesion;

/**
 * Clase SeleccionCombateController
 * Controlador encargado de gestionar la vista del menu de seleccion de combate
 * (Combate Aleatorio o Liga Pokemon).
 */
public class SeleccionCombateController implements Initializable {

	/**
	 * Entrenador logueado en la sesion actual.
	 */
	private Entrenador entrenadorActual = Sesion.entrenadorLogueado;
	
	/**
	 * Conexion a la base de datos MySQL.
	 */
	private Connection con;

	@FXML
	private ImageView botonSalirEleccionCombate;
	@FXML
	private ImageView botonAccesoLiga;
	@FXML
	private ImageView botonAccesoCombateAleatorio;

	/**
	 * Metodo que inicializa el controlador al cargar la vista.
	 * Establece la conexion a la base de datos y muestra datos de control en consola.
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// Inicializamos la conexion a la base de datos para futuras consultas
		ConexionBBDD conector = new ConexionBBDD();
		this.con = conector.getConexion();

		// Mostramos por consola el entrenador actual
		System.out.println("Equipo: Entrenador " + entrenadorActual.getNombreEntrenador());
	}

	// --------------- INCREMENTAR Y DISMINUIR TAMAÑO DEL BOTON ACCESO LIGA ---------------
	
	/**
	 * Aumenta el tamaño del boton de acceso a la Liga al pasar el cursor sobre el.
	 * @param event El evento de raton
	 */
	@FXML
	private void aumentarTamanoBotonAccesoLiga(MouseEvent event) {
		botonAccesoLiga.setScaleX(botonAccesoLiga.getScaleX() + 0.2);
		botonAccesoLiga.setScaleY(botonAccesoLiga.getScaleY() + 0.2);
	}

	/**
	 * Disminuye el tamaño del boton de acceso a la Liga al apartar el cursor.
	 * @param event El evento de raton
	 */
	@FXML
	private void disminuirTamanoBotonAccesoLiga(MouseEvent event) {
		botonAccesoLiga.setScaleX(botonAccesoLiga.getScaleX() - 0.2);
		botonAccesoLiga.setScaleY(botonAccesoLiga.getScaleY() - 0.2);
	}

	// --------------- INCREMENTAR Y DISMINUIR TAMAÑO DEL BOTON ACCESO COMBATE ALEATORIO ---------------
	
	/**
	 * Aumenta el tamaño del boton de acceso a combate aleatorio al pasar el cursor.
	 * @param event El evento de raton
	 */
	@FXML
	private void aumentarTamanoBotonAccesoCombateAleatorio(MouseEvent event) {
		botonAccesoCombateAleatorio.setScaleX(botonAccesoCombateAleatorio.getScaleX() + 0.2);
		botonAccesoCombateAleatorio.setScaleY(botonAccesoCombateAleatorio.getScaleY() + 0.2);
	}

	/**
	 * Disminuye el tamaño del boton de acceso a combate aleatorio al apartar el cursor.
	 * @param event El evento de raton
	 */
	@FXML
	private void disminuirTamanoBotonAccesoCombateAleatorio(MouseEvent event) {
		botonAccesoCombateAleatorio.setScaleX(botonAccesoCombateAleatorio.getScaleX() - 0.2);
		botonAccesoCombateAleatorio.setScaleY(botonAccesoCombateAleatorio.getScaleY() - 0.2);
	}

	/**
	 * Metodo para gestionar la transicion a la vista de la Liga Pokemon.
	 * @param event El evento de raton al hacer clic
	 */
	@FXML
	private void clickAccesoLiga(MouseEvent event) {
		Combate verificador = new Combate();
		
		// si devuelve null es que no hay pokemon vivos y no deja entrar
        if (verificador.buscarPrimerPokemonVivo(Sesion.entrenadorLogueado) == null) {
            
            Alert alerta = new Alert(Alert.AlertType.WARNING);
            alerta.setTitle("¡Cuidado!");
            alerta.setHeaderText("Tu equipo no puede luchar");
            alerta.setContentText("¡Todos tus Pokémon están debilitados! Ve a curarlos antes de buscar problemas.");
            alerta.showAndWait();
            
            return; 
        }
        //empezamos el try catch para cargar la vista
     			try {
     				System.out.println("Cargando la vista de combate...");

     				// Recibir el click
     				Node source = (Node) event.getSource();

     				// Recuperar la ventana
     				Stage primaryStage = (Stage) source.getScene().getWindow();

     				// Cargar la vista de Combate
     				FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/ligaPokemon/Liga.fxml"));
     				Parent root = loader.load();
     				Scene scene = new Scene(root);

     				// Cargar el CSS 
     				try {
     					String css = this.getClass().getResource("/view/combateAleatorio/combateAleatorio.css").toExternalForm();
     					scene.getStylesheets().add(css);
     				} catch (Exception e) {
     					System.out.println("No se encontró el CSS del combate, cargando sin estilos extra.");
     				}

     				// Titulo, forzar el tamaño de la ventana y bloquear cambio manual
     				primaryStage.setTitle("PokeINC - Combate Aleatorio");
     				primaryStage.setResizable(false);

     				// Cargar icono
     				File file = new File("imgs/Login/Login-icon.png");

     				if (file.exists()) {
     					String imagePath = file.toURI().toString();
     					primaryStage.getIcons().add(new Image(imagePath));
     				} else {
     					System.out.println("No se encontró el icono en: " + file.getAbsolutePath());
     				}

     				// Cambiar la escena de seleccion por la del combate
     				primaryStage.setScene(scene);

     				// Mostrar la escena
     				primaryStage.show();

     			} catch (Exception e) {
     				System.out.println("Error al cambiar de ventana - " + e.getMessage());
     				e.printStackTrace();
     			}
		
	}

	/**
	 * Metodo para gestionar la transicion a la vista de Combate Aleatorio.
	 * @param event El evento de raton al hacer clic
	 */
		@FXML
		private void clickAccesoCombateAleatorio(MouseEvent event) {
			Combate verificador = new Combate();
			
		// si devuelve null es que no hay pokemon vivos y no deja entrar
        if (verificador.buscarPrimerPokemonVivo(Sesion.entrenadorLogueado) == null) {
            
            Alert alerta = new Alert(Alert.AlertType.WARNING);
            alerta.setTitle("¡Cuidado!");
            alerta.setHeaderText("Tu equipo no puede luchar");
            alerta.setContentText("¡Todos tus Pokémon están debilitados! Ve a curarlos antes de buscar problemas.");
            alerta.showAndWait();
            
            return; 
        }
        	// empezamos el try catch para cargar la vista
			try {
				System.out.println("Cargando la vista de combate...");

				// Recibir el click
				Node source = (Node) event.getSource();

				// Recuperar la ventana
				Stage primaryStage = (Stage) source.getScene().getWindow();

				// Cargar la vista de Combate
				FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/combateAleatorio/combateAleatorio.fxml"));
				Parent root = loader.load();
				Scene scene = new Scene(root);

				// Cargar el CSS 
				try {
					String css = this.getClass().getResource("/view/combateAleatorio/combateAleatorio.css").toExternalForm();
					scene.getStylesheets().add(css);
				} catch (Exception e) {
					System.out.println("No se encontró el CSS del combate, cargando sin estilos extra.");
				}

				// Titulo, forzar el tamaño de la ventana y bloquear cambio manual
				primaryStage.setTitle("PokeINC - Combate Aleatorio");
				primaryStage.setResizable(false);

				// Cargar icono
				File file = new File("imgs/Login/Login-icon.png");

				if (file.exists()) {
					String imagePath = file.toURI().toString();
					primaryStage.getIcons().add(new Image(imagePath));
				} else {
					System.out.println("No se encontró el icono en: " + file.getAbsolutePath());
				}

				// Cambiar la escena de seleccion por la del combate
				primaryStage.setScene(scene);

				// Mostrar la escena
				primaryStage.show();

			} catch (Exception e) {
				System.out.println("Error al cambiar de ventana - " + e.getMessage());
				e.printStackTrace();
			}
		}

	/**
	 * Metodo para salir del menu de seleccion de combate y volver a la vista principal.
	 * @param event El evento de raton al hacer clic en el boton de salir
	 */
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
				System.out.println("No se encontro el icono en: " + file.getAbsolutePath());
			}

			// Cambiar la escena actual por la nueva
			primaryStage.setScene(scene);

			// Mostrar la escena
			primaryStage.show();

		} catch (Exception e) {
			System.out.println("Error al cambiar de ventana - " + e.getMessage());
			e.printStackTrace();
		}
	}

	

	/**
	 * Metodo que comprueba si un Pokemon cumple los requisitos de nivel para evolucionar 
	 * en la base de datos y lanza el proceso de transformacion si es asi.
	 * @param pokemon El pokemon a evaluar tras ganar experiencia
	 */
	public void comprobarEvolucion(Pokemon pokemon) {
		//preguntamos a la bd a quien evoluciona
		String evolucionTarget = PokemonDAO.comprobarEvolucionDisponible(con, pokemon);

		if (evolucionTarget != null) {

			//comprobamos si es un pokemon con multiples evoluciones (como Eevee o Tyrogue)
			if (evolucionTarget.equalsIgnoreCase("Varios")) {
				if (pokemon.getNombrePokemon().equalsIgnoreCase("Eevee")) {
					evolucionTarget = mostrarOpcionesEevee();
				} else if (pokemon.getNombrePokemon().equalsIgnoreCase("Tyrogue")) {
					evolucionTarget = mostrarOpcionesTyrogue();
				}
			}

			//si el usuario no ha cancelado el dialogo y tenemos una evolucion valida
			if (evolucionTarget != null && !evolucionTarget.trim().isEmpty()) {

				//guardamos la nueva especie en la base de datos
				PokemonDAO.ejecutarEvolucionBD(con, pokemon, evolucionTarget);

				//mostramos el mensaje de enhorabuena
				Alert alerta = new Alert(Alert.AlertType.INFORMATION);
				alerta.setTitle("¡Evolución completada!");
				alerta.setHeaderText("¡Qué está pasando!");
				alerta.setContentText("¡Felicidades! Tu Pokémon ha evolucionado a " + evolucionTarget + ".");

				//intentamos aplicar el css de alertas
				try {
					alerta.getDialogPane().getStylesheets()
							.add(getClass().getResource("/view/captura/alertas.css").toExternalForm());
				} catch (Exception e) {
					System.out.println("No se pudo cargar el CSS de las alertas.");
				}

				alerta.showAndWait();
			}
		}
	}

	/**
	 * Ventana especial con desplegable (ChoiceDialog) para que el jugador 
	 * elija libremente la evolucion de Eevee.
	 * @return El nombre del Pokemon elegido como evolucion, o null si cancela.
	 */
	private String mostrarOpcionesEevee() {
		List<String> opciones = new ArrayList<>();
		opciones.add("Vaporeon");
		opciones.add("Jolteon");
		opciones.add("Flareon");
		opciones.add("Espeon");
		opciones.add("Umbreon");

		//creamos un dialogo desplegable
		ChoiceDialog<String> dialog = new ChoiceDialog<>("Vaporeon", opciones);
		dialog.setTitle("¡Eevee está evolucionando!");
		dialog.setHeaderText("Tu Eevee está listo para dar el siguiente paso.");
		dialog.setContentText("Elige la evolución que deseas:");

		//aplicamos el estilo CSS a la ventana de eleccion
		try {
			dialog.getDialogPane().getStylesheets()
					.add(getClass().getResource("/view/captura/alertas.css").toExternalForm());
		} catch (Exception e) {
			System.out.println("No se pudo cargar el CSS del ChoiceDialog.");
		}

		//esperamos la respuesta del usuario
		Optional<String> result = dialog.showAndWait();

		//si elige una opcion y pulsa en Aceptar, devolvemos el nombre seleccionado
		if (result.isPresent()) {
			return result.get();
		}

		//si cierra la ventana o pulsa en Cancelar, la evolucion no ocurre
		return null;
	}
	
	/**
	 * Ventana especial con desplegable (ChoiceDialog) para que el jugador 
	 * elija libremente la evolucion de Tyrogue.
	 * @return El nombre del Pokemon elegido como evolucion, o null si cancela.
	 */
	private String mostrarOpcionesTyrogue() {
		List<String> opciones = new ArrayList<>();
		opciones.add("Hitmonlee");
		opciones.add("Hitmonchan");
		opciones.add("Hitmontop");

		//creamos un dialogo desplegable
		ChoiceDialog<String> dialog = new ChoiceDialog<>("Hitmonlee", opciones);
		dialog.setTitle("¡Tyrogue está evolucionando!");
		dialog.setHeaderText("Tu Tyrogue está listo para dar el siguiente paso.");
		dialog.setContentText("Elige la evolución que deseas:");

		//aplicamos el estilo CSS a la ventana de eleccion
		try {
			dialog.getDialogPane().getStylesheets()
					.add(getClass().getResource("/view/captura/alertas.css").toExternalForm());
		} catch (Exception e) {
			System.out.println("No se pudo cargar el CSS del ChoiceDialog.");
		}

		//esperamos la respuesta del usuario
		Optional<String> result = dialog.showAndWait();

		//si elige una opcion y pulsa en Aceptar, devolvemos el nombre seleccionado
		if (result.isPresent()) {
			return result.get();
		}

		//si cierra la ventana o pulsa en Cancelar, la evolucion no ocurre
		return null;
	}

}