package controller;

import java.io.File;
import java.net.URL;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import javafx.animation.ParallelTransition;
import javafx.animation.PauseTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.TranslateTransition;
import javafx.util.Duration;
import java.util.LinkedList;
import java.util.Queue;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.ScaleTransition;

import bd.ConexionBBDD;
import dao.CapturaDao;
import dao.MovimientoDAO;
import dao.PokemonDAO;
import model.*;

public class CombateController implements Initializable {

	// Elementos vista

	@FXML
	private ImageView fondoCombateAleatorio;

	// Pokemon del Jugador
	@FXML
	private ImageView imgPokemonJugador;

	@FXML
	private ImageView imgEntrenadorJugador;

	@FXML
	private Text txtNombrePkmnJugador;

	@FXML
	private Text txtVitalidadPkmnJugador;

	@FXML
	private Text txtNivelPkmnJugador;

	@FXML
	private ImageView imgSexoPkmnJugador;

	@FXML
	private ProgressBar barraVitalidadPkmnJugador;

	// Pokemon del Rival
	@FXML
	private ImageView imgPokemonRival;

	@FXML
	private ImageView imgEntrenadorRival;

	@FXML
	private Text txtNombrePkmnRival;

	@FXML
	private Text txtVitalidadPkmnRival;

	@FXML
	private Text txtNivelPkmnRival;

	@FXML
	private ImageView imgSexoPkmnRival;

	@FXML
	private ProgressBar barraVitalidadPkmnRival;

	// Botones
	@FXML
	private Button btnAtaque1;

	@FXML
	private Button btnAtaque2;

	@FXML
	private Button btnAtaque3;

	@FXML
	private Button btnAtaque4;

	@FXML
	private Pane panelMenuPrincipal;

	@FXML
	private Pane panelMenuAtaques;

	@FXML
	private ImageView btnSalirMenuAtaque;

	@FXML
	private ImageView btnDescansar;

	@FXML
	private ImageView btnCambiarPokemon;

	@FXML
	private ImageView btnLuchar;

	@FXML
	private ImageView btnDescanso;

	@FXML
	private ImageView btnHuir; // o rendirse

	@FXML
	private ImageView estadoPokemonJugador;

	@FXML
	private ImageView estadoPokemonRival;

	// menu de cambio de pokemon
	@FXML
	private Pane panelCambioPokemon;

	@FXML
	private Button btnPokemon1;
	@FXML
	private Button btnPokemon2;
	@FXML
	private Button btnPokemon3;
	@FXML
	private Button btnPokemon4;
	@FXML
	private Button btnPokemon5;
	@FXML
	private Button btnPokemon6;

	@FXML
	private ImageView btnVolverCambio; // boton para cancelar el cambio

	// Historial del Combate
	@FXML
	private Label txtLogCombate; // ventana de texto para narrar los turnos

	// Variable para el log

	private Log logActual = new Log();

	// Variables

	private Combate combateActual;
	private Entrenador jugador;
	private Entrenador rival;

	private int numeroTurno = 1; // contador de turnos

	// variables para la animacion del texto
	private Queue<String> colaMensajes = new LinkedList<>();
	private boolean escribiendoMensaje = false;

	private boolean controlesBloqueados = true; // bloquear botones

	/// Variables que Bloquean la vista para que no se adelante a la memoria de Java
	private boolean vistaJugadorBloqueada = false;
	private boolean vistaRivalBloqueada = false;

	/***
	 * inicializa la pantalla de combate.
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		colaMensajes.clear();
	    escribiendoMensaje = false;
	    controlesBloqueados = false;
		// instanciar el combate
		combateActual = new Combate();

		// cargamos el jugador
		jugador = Sesion.entrenadorLogueado;

		// logica liga empiezo aqui a ver si no revienta
		if (Sesion.modoLiga && Sesion.ligaActual != null) {

			// obtenemos al enemigo ramdom como preparamos ligapokemon
			rival = Sesion.ligaActual.obtenerRivalActual();

			// si doppelganger no tiene equipo guardado, le generamos uno aleatorio la
			// primera vez
			if (Sesion.ligaActual.getCombateActual() == 6) {
				
				Pokemon[] equipoEspejo = new Pokemon[6];
				
				// Recorremos el equipo del jugador y creamos clones exactos para el rival
				for (int i = 0; i < 6; i++) {
					if (jugador.getEquipoPokemon()[i] != null) {
						Pokemon original = jugador.getEquipoPokemon()[i];
						Pokemon clon = new Pokemon();
						
						// Copiamos datos básicos
						clon.setNombrePokemon(original.getNombrePokemon());
						clon.setNumPokedex(original.getNumPokedex());
						clon.setNivel(original.getNivel());
						clon.setTipoPrincipal(original.getTipoPrincipal());
						clon.setTipoSecundario(original.getTipoSecundario());
						clon.setEsShiny(original.getEsShiny());
						
						// Copiamos las estadísticas 
						clon.setVitalidadMaxima(original.getVitalidadMaxima());
						clon.setVitalidad(original.getVitalidadMaxima()); 
						clon.setAtaque(original.getBaseAtaque());
						clon.setDefensa(original.getBaseDefensa());
						clon.setAtaqueEspecial(original.getBaseAtaqueEspecial());
						clon.setDefensaEspecial(original.getBaseDefensaEspecial());
						clon.setVelocidad(original.getBaseVelocidad());
						clon.setEstadoActual(Estados.SANO);
						
						// Copiamos las imágenes para que te mire de frente
						clon.setImgFrontalPokemon(original.getImgFrontalPokemon());
						clon.setImgFrontalPokemon3D(original.getImgFrontalPokemon3D());
						
						// Clonamos los movimientos para no compartir los PP en la memoria
						Movimiento[] movsClon = new Movimiento[4];
						for(int j = 0; j < 4; j++) {
							if(original.getMovimientos()[j] != null) {
								Movimiento mOrig = original.getMovimientos()[j];
								Movimiento mClon = new Movimiento();
								mClon.setNombreMovimiento(mOrig.getNombreMovimiento());
								mClon.setPotencia(mOrig.getPotencia());
								mClon.setTipo(mOrig.getTipo());
								mClon.setCategoriaDano(mOrig.getCategoriaDano());
								// El clon tiene todos sus PPs listos para usarlos
								mClon.setCantidadMovimientos(mOrig.getCantidadMovimientosMaximos());
								mClon.setCantidadMovimientosMaximos(mOrig.getCantidadMovimientosMaximos());
								movsClon[j] = mClon;
							}
						}
						clon.setMovimientos(movsClon);
						
						// Añadimos el clon al equipo del Doppelganger
						equipoEspejo[i] = clon;
					}
				}
				// Le asignamos tu equipo clonado al rival
				rival.setEquipoPokemon(equipoEspejo);
			}

			// ocultamos los botones que no usamos en la liga
			if (btnHuir != null) btnHuir.setVisible(false);
			if (btnDescanso != null) btnDescanso.setVisible(false);

		} else {
			// Combate aleatorio
			rival = generarRivalAleatorio();
			cargarFondoAleatorio();
		}

		// empezamos el combate
		combateActual.empezarCombate(jugador, rival, logActual);

		/// Obligamos a los menus a estar arriba del todo asi aunque el texto o las
		/// imagenes crezcan nunca tapan
		if (panelMenuPrincipal != null)
			panelMenuPrincipal.toFront();
		if (panelMenuAtaques != null)
			panelMenuAtaques.toFront();
		if (panelCambioPokemon != null)
			panelCambioPokemon.toFront();

		/// hacemos invisibles al raton las imagenes para que nada tape los botones
		if (imgPokemonJugador != null)
			imgPokemonJugador.setMouseTransparent(true);
		if (imgPokemonRival != null)
			imgPokemonRival.setMouseTransparent(true);
		if (txtLogCombate != null)
			txtLogCombate.setMouseTransparent(true);
		if (estadoPokemonJugador != null)
			estadoPokemonJugador.setMouseTransparent(true);
		if (estadoPokemonRival != null)
			estadoPokemonRival.setMouseTransparent(true);
		if (imgSexoPkmnJugador != null)
			imgSexoPkmnJugador.setMouseTransparent(true);
		if (imgSexoPkmnRival != null)
			imgSexoPkmnRival.setMouseTransparent(true);

		// arrancamos la animacion
		animacionEntrada();

		// asignamos CSS a los 4 botones
		if (btnAtaque1 != null)
			btnAtaque1.getStyleClass().add("boton-ataque");
		if (btnAtaque2 != null)
			btnAtaque2.getStyleClass().add("boton-ataque");
		if (btnAtaque3 != null)
			btnAtaque3.getStyleClass().add("boton-ataque");
		if (btnAtaque4 != null)
			btnAtaque4.getStyleClass().add("boton-ataque");
	}

	/**
	 * Busca en la carpeta de fondos y elige uno al azar para el combate actual.
	 */
	private void cargarFondoAleatorio() {
		try {
			/// ruta a la carpeta
			File carpetaFondos = new File("imgs/Combate/FondoCombateAleatorio");

			/// leemos todos los archivos que hay dentro de la carpeta
			File[] archivos = carpetaFondos.listFiles();

			/// comprobamos que la carpeta existe y que al menos tiene 1 archivo
			if (archivos != null && archivos.length > 0) {

				Random r = new Random();

				/// elegimos un archivo al azar de la lista
				File imagenElegida = archivos[r.nextInt(archivos.length)];

				/// si nuestro ImageView esta bien enlazado le ponemos la foto
				if (fondoCombateAleatorio != null) {
					fondoCombateAleatorio.setImage(new Image(imagenElegida.toURI().toString()));
				}

			} else {
				System.out.println("Aviso: No se encontraron imagenes en la carpeta de fondos.");
			}
		} catch (Exception e) {
			System.out.println("ERROR al cargar el fondo aleatorio: " + e.getMessage());
		}
	}

	/**
	 * Actualiza los iconos de estado en la vista.
	 */

	private void actualizarIconosEstado() {
		Pokemon pJugador = combateActual.getPokemonActualJugador();
		Pokemon pRival = combateActual.getPokemonActualRival();

		// actualizar estado del Jugador
		if (pJugador != null && estadoPokemonJugador != null) {
			gestionarImagenEstado(pJugador, estadoPokemonJugador);
		}

		// actualizar estado del Rival
		if (pRival != null && estadoPokemonRival != null) {
			gestionarImagenEstado(pRival, estadoPokemonRival);
		}
	}

	/**
	 * Logica interna para decidir que imagen poner o si se oculta
	 */

	private void gestionarImagenEstado(Pokemon p, ImageView imgView) {
		Estados estado = p.getEstadoActual();

		// si el pokemon esta sano o debilitado no mostramos icono
		if (estado == Estados.SANO || estado == Estados.DEBILITADO || p.getVitalidad() <= 0) {
			imgView.setVisible(false);
			return;
		}

		String nombreImagen = "";

		// ruta a estado imagen
		switch (estado) {
		case PARALIZADO:
			nombreImagen = "Paralizado.png";
			break;
		case QUEMADO:
			nombreImagen = "Quemado.png";
			break;
		case DORMIDO:
		case SOMNOLIENTO:
			nombreImagen = "Dormido.png";
			break;
		case ENVENENADO:
		case GRAVEMENTEENMVENEDADO:
			nombreImagen = "Envenenado.png";
			break;
		case CONGELADO:
		case HELADO:
			nombreImagen = "Congelado.png";
			break;
		default:
			imgView.setVisible(false);
			return;
		}

		// cargar la imagen desde la carpeta

		try {
			File file = new File("imgs/Combate/estados/" + nombreImagen);
			if (file.exists()) {
				imgView.setImage(new Image(file.toURI().toString()));
				imgView.setVisible(true);
			} else {
				imgView.setVisible(false);
			}
		} catch (Exception e) {
			imgView.setVisible(false);

		}
	}

	/**
	 * Refresca todos los textos, imagenes y barras de vida de la pantalla leyendo
	 * los datos actuales del modelo.
	 */
	private void actualizarVista() {
		try {
			Pokemon pJugador = combateActual.getPokemonActualJugador();
			Pokemon pRival = combateActual.getPokemonActualRival();

			/// Actualizar pantalla del jugador (Solo si no está bloqueada)
			if (pJugador != null && vistaJugadorBloqueada == false) {
				txtNombrePkmnJugador.setText(pJugador.getNombrePokemon());
				txtNivelPkmnJugador.setText("Lv" + pJugador.getNivel());
				txtVitalidadPkmnJugador.setText(pJugador.getVitalidad() + " / " + pJugador.getVitalidadMaxima());

				double porcentajeVidaJugador = (double) pJugador.getVitalidad() / pJugador.getVitalidadMaxima();
				if (porcentajeVidaJugador < 0) {
					porcentajeVidaJugador = 0;
				}

				/// obtenemos la ruta de la base de datos segun la vista elegida
				String rutaJ = "";
				if (Sesion.vista2D == true) {
					rutaJ = pJugador.getImgPosteriorPokemon();
				} else {
					rutaJ = pJugador.getImgPosteriorPokemon3D();
				}

				/// cargamos la imagen del jugador con el prefijo base
				if (rutaJ != null) {
					String prefijoBase = "imgs/Pokemons/sprites/crystal/transparent/";
					String rutaCompletaJ = prefijoBase + rutaJ;
					File fileJ = new File(rutaCompletaJ);

					if (fileJ.exists() == true) {
						imgPokemonJugador.setImage(new Image(fileJ.toURI().toString()));
					}
				}

				barraVitalidadPkmnJugador.setProgress(porcentajeVidaJugador);
				cambiarColorBarra(barraVitalidadPkmnJugador, porcentajeVidaJugador);

				/// actualizamos los textos de los botones de ataque para que muestren los PP
				Movimiento[] ataques = pJugador.getMovimientos();

				if (ataques[0] != null) {
					btnAtaque1.setText(ataques[0].getNombreMovimiento() + "\n(" + ataques[0].getCantidadMovimientos()
							+ "/" + ataques[0].getCantidadMovimientosMaximos() + ")");
				} else {
					btnAtaque1.setText("-");
				}
				if (ataques[1] != null) {
					btnAtaque2.setText(ataques[1].getNombreMovimiento() + "\\n(" + ataques[1].getCantidadMovimientos()
							+ "/" + ataques[1].getCantidadMovimientosMaximos() + ")");
				} else {
					btnAtaque2.setText("-");
				}
				if (ataques[2] != null) {
					btnAtaque3.setText(ataques[2].getNombreMovimiento() + "\\n(" + ataques[2].getCantidadMovimientos()
							+ "/" + ataques[2].getCantidadMovimientosMaximos() + ")");
				} else {
					btnAtaque3.setText("-");
				}
				if (ataques[3] != null) {
					btnAtaque4.setText(ataques[3].getNombreMovimiento() + "\\n(" + ataques[3].getCantidadMovimientos()
							+ "/" + ataques[3].getCantidadMovimientosMaximos() + ")");
				} else {
					btnAtaque4.setText("-");
				}
			}

			/// Actualizar pantalla del rival (Solo si no está bloqueada)
			if (pRival != null && vistaRivalBloqueada == false) {
				txtNombrePkmnRival.setText(pRival.getNombrePokemon());
				txtNivelPkmnRival.setText("Lv" + pRival.getNivel());
				txtVitalidadPkmnRival.setText(pRival.getVitalidad() + " / " + pRival.getVitalidadMaxima());

				double porcentajeVidaRival = (double) pRival.getVitalidad() / pRival.getVitalidadMaxima();
				if (porcentajeVidaRival < 0) {
					porcentajeVidaRival = 0;
				}

				/// obtenemos la ruta del rival segun la vista elegida
				String rutaR = "";
				if (Sesion.vista2D == true) {
					rutaR = pRival.getImgFrontalPokemon();
				} else {
					rutaR = pRival.getImgFrontalPokemon3D();
				}

				/// cargamos la imagen del rival con el prefijo base
				if (rutaR != null) {
					String prefijoBase = "imgs/Pokemons/sprites/crystal/transparent/";
					String rutaCompletaR = prefijoBase + rutaR;
					File fileR = new File(rutaCompletaR);

					if (fileR.exists() == true) {
						imgPokemonRival.setImage(new Image(fileR.toURI().toString()));
					}
				}

				barraVitalidadPkmnRival.setProgress(porcentajeVidaRival);
				cambiarColorBarra(barraVitalidadPkmnRival, porcentajeVidaRival);

				/// actualizamos los estados
				actualizarIconosEstado();
			}
		} catch (Exception e) {
			System.out.println("Error silencioso en actualizarVista esquivado: " + e.getMessage());
		}
	}

	/**
	 * metodo de apoyo para cambiar el color de la barra de vida estilo pokemon.
	 */
	private void cambiarColorBarra(ProgressBar barra, double porcentaje) {
		// quitamos los colores anteriores para que no se mezclen
		barra.getStyleClass().removeAll("barra-verde", "barra-amarilla", "barra-roja");

		if (porcentaje > 0.5) {
			barra.setStyle("-fx-accent: #32cd32;"); // verde
		} else if (porcentaje > 0.2) {
			barra.setStyle("-fx-accent: #ffd700;"); // amarillo
		} else {
			barra.setStyle("-fx-accent: #ff4500;"); // rojo
		}
	}

	// Cambiar tamaño de botones (gracias Elyass XDDD)

	// --------------- INCREMENTAR Y DISMINUIR TAMAÑO DEL BOTON ENTRENAR AL PASAR EL
	// CURSOR ---------------

	// BOTON LUCHAR
	@FXML
	private void aumentarTamañoBotonLuchar(MouseEvent event) {
		if (controlesBloqueados == true) {
			return;
		}
		if (btnLuchar != null) {
			btnLuchar.setScaleX(1.2);
			btnLuchar.setScaleY(1.2);
		}
	}

	@FXML
	private void disminuirTamañoBotonLuchar(MouseEvent event) {
		if (btnLuchar != null) {
			btnLuchar.setScaleX(1.0);
			btnLuchar.setScaleY(1.0);
		}
	}

	// BOTON CAMBIAR POKEMON
	@FXML
	private void aumentarTamañoBotonCambiarPokemon(MouseEvent event) {
		if (controlesBloqueados == true) {
			return;
		}
		if (btnCambiarPokemon != null) {
			btnCambiarPokemon.setScaleX(btnCambiarPokemon.getScaleX() + 0.2);
			btnCambiarPokemon.setScaleY(btnCambiarPokemon.getScaleY() + 0.2);
		}
	}

	@FXML
	private void disminuirTamañoBotonCambiarPokemon(MouseEvent event) {
		if (btnCambiarPokemon != null) {
			btnCambiarPokemon.setScaleX(1.0);
			btnCambiarPokemon.setScaleY(1.0);
		}
	}

	// BOTON DESCANSAR
	@FXML
	private void aumentarTamañoBotonDescansar(MouseEvent event) {
		if (controlesBloqueados == false && btnDescanso != null) {
			btnDescanso.setScaleX(1.2);
			btnDescanso.setScaleY(1.2);
		}
	}

	@FXML
	private void disminuirTamañoBotonDescansar(MouseEvent event) {
		if (btnDescanso != null) {
			btnDescanso.setScaleX(1.0);
			btnDescanso.setScaleY(1.0);
		}
	}

	// BOTON SALIR
	@FXML
	private void aumentarTamañoBotonSalir(MouseEvent event) {
		if (controlesBloqueados == true) {
			return;
		}
		if (btnHuir != null) {
			btnHuir.setScaleX(btnHuir.getScaleX() + 0.2);
			btnHuir.setScaleY(btnHuir.getScaleY() + 0.2);
		}
	}

	@FXML
	private void disminuirTamañoBotonSalir(MouseEvent event) {
		if (btnHuir != null) {
			btnHuir.setScaleX(1.0);
			btnHuir.setScaleY(1.0);
		}
	}

	// ================= METODOS DE LOS BOTONES =================

	@FXML
	public void usarAtaque1(MouseEvent event) {
		if (controlesBloqueados == true) {
			return;
		}
		ejecutarTurno(1);
	}

	@FXML
	public void usarAtaque2(MouseEvent event) {
		if (controlesBloqueados == true) {
			return;
		}
		ejecutarTurno(2);
	}

	@FXML
	public void usarAtaque3(MouseEvent event) {
		if (controlesBloqueados == true) {
			return;
		}
		ejecutarTurno(3);
	}

	@FXML
	public void usarAtaque4(MouseEvent event) {
		if (controlesBloqueados == true) {
			return;
		}
		ejecutarTurno(4);
	}

	@FXML
	public void accionLuchar(MouseEvent event) {
		if (controlesBloqueados == true) {
			return;
		}
		// esconder menu mostrar ataques
		panelMenuPrincipal.setVisible(false);
		panelMenuAtaques.setVisible(true);
		btnSalirMenuAtaque.setVisible(true);
	}

	@FXML
	public void clicVolverMenu(MouseEvent event) {
		if (controlesBloqueados == true) {
			return;
		}
		// ocultamos los ataques y mostramos el menu principal
		panelMenuAtaques.setVisible(false);
		panelMenuPrincipal.setVisible(true);
		btnSalirMenuAtaque.setVisible(false);
	}

	@FXML
	public void accionDescansar(MouseEvent event) {

		if (controlesBloqueados == true) {
			return;
		}

		/// ocultamos menus para que no pueda pulsar nada mas mientras se cura
		panelMenuAtaques.setVisible(false);
		panelMenuPrincipal.setVisible(true);
		btnSalirMenuAtaque.setVisible(false);

		Pokemon pJugador = combateActual.getPokemonActualJugador();
		Pokemon pRival = combateActual.getPokemonActualRival();

		/// el jugador descansa
		pJugador.descansar();
		escribirLog("¡Tu " + pJugador.getNombrePokemon() + " se echa una siesta y recupera vitalidad!");
		escribirLog("@UPDATE_VISTA@"); /// la barra sube para curarse justo despues del texto

		/// el rival ataca en ese mismo turno
		Movimiento movRival = elegirAtaqueRival();
		escribirLog(
				"¡El " + pRival.getNombrePokemon() + " rival aprovecha y usa " + movRival.getNombreMovimiento() + "!");

		/// usamos el metodo atacar para calcular y restar el daño
		pRival.atacar(movRival, pJugador);

		escribirLog("@UPDATE_VISTA@"); /// la barra vuelve a bajar por el daño del ataque

		/// comprobamos si el rival nos ha debilitado de ese golpe
		verificarMuertesYContinuar();

		// pasamos de turno
		numeroTurno = numeroTurno + 1;

	}

	@FXML
	public void accionCambiarPokemon(MouseEvent event) {
		if (controlesBloqueados == true) {
			return;
		}
		// abrimos el menu de cambio sin ser obligatorio el mismo
		abrirMenuCambio(false);
	}

	@FXML
	public void accionCancelarCambio(MouseEvent event) {
		// ocultamos el panel de equipo y volvemos al menu principal de lucha
		panelCambioPokemon.setVisible(false);
		panelMenuPrincipal.setVisible(true);
	}

	@FXML
	public void clicPkmn1(MouseEvent event) {
		realizarCambio(0);
	}

	@FXML
	public void clicPkmn2(MouseEvent event) {
		realizarCambio(1);
	}

	@FXML
	public void clicPkmn3(MouseEvent event) {
		realizarCambio(2);
	}

	@FXML
	public void clicPkmn4(MouseEvent event) {
		realizarCambio(3);
	}

	@FXML
	public void clicPkmn5(MouseEvent event) {
		realizarCambio(4);
	}

	@FXML
	public void clicPkmn6(MouseEvent event) {
		realizarCambio(5);
	}

	/**
	 * Metodo central que ejecuta el cambio de Pokemon.
	 */
	private void realizarCambio(int indiceEquipo) {

		Pokemon antiguoPokemon = combateActual.getPokemonActualJugador();

		/// Bajamos el telón del jugador
		vistaJugadorBloqueada = true;

		/// intentamos hacer el cambio en el modelo pasandole el numero del hueco
		boolean cambioExitoso = combateActual.cambiarPokemonJugador(indiceEquipo);

		/// si la logica rechaza el cambio abortamos
		if (cambioExitoso == false) {
			vistaJugadorBloqueada = false;
			return;
		}

		/// recuperamos al nuevo Pokemon para usar su nombre en los textos
		Pokemon nuevoPokemon = combateActual.getPokemonActualJugador();

		/// si el Pokemon anterior seguia vivo avisamos de que lo retiramos
		if (antiguoPokemon.getVitalidad() > 0) {
			escribirLog("¡Vuelve, " + antiguoPokemon.getNombrePokemon() + "!");
			escribirLog("@ANIM_JUGADOR_OUT@"); /// el Pokemon viejo se encoge
		}

		escribirLog("¡Adelante, " + nuevoPokemon.getNombrePokemon() + "!");
		/// usamos el nuevo comando que sube el telon
		escribirLog("@MOSTRAR_NUEVO_JUGADOR@");

		/// ocultamos el panel de cambio y volvemos al combate
		panelCambioPokemon.setVisible(false);
		panelMenuPrincipal.setVisible(true);

		/// si el bot0n de volver era visible significa que el cambio fue manual
		/// (gastamos el turno)
		if (btnVolverCambio.isVisible() == true) {

			Movimiento movRival = elegirAtaqueRival();
			escribirLog("¡El " + combateActual.getPokemonActualRival().getNombrePokemon()
					+ " rival aprovecha el cambio y usa " + movRival.getNombreMovimiento() + "!");

			/// el rival nos ataca al nuevo pokemon que acaba de salir
			combateActual.getPokemonActualRival().atacar(movRival, nuevoPokemon);

			escribirLog("@UPDATE_VISTA@"); // ordenamos que baje nuestra vida por el ataque recibido

			/// comprobamos si nos lo ha matado de un solo golpe al salir
			verificarMuertesYContinuar();
		}
	}

	// ================= LÓGICA CENTRAL DEL COMBATE =================

	/**
	 * El corazon del combate en la interfaz.
	 */
	private void ejecutarTurno(int indiceAtaque) {

		/// ocultamos el panel de ataques para que el jugador no pulse dos veces
		panelMenuAtaques.setVisible(false);
		panelMenuPrincipal.setVisible(true);
		btnSalirMenuAtaque.setVisible(false);

		Pokemon pJugador = combateActual.getPokemonActualJugador();

		/// le restamos 1 para el indice del array
		Movimiento movJugador = pJugador.getMovimientos()[indiceAtaque - 1];

		/// si no hay ataque en ese boton se aborta
		if (movJugador == null) {
			return;
		}

		/// comprobamos si quedan PP
		if (movJugador.getCantidadMovimientos() <= 0) {
			escribirLog("¡No te quedan usos para " + movJugador.getNombreMovimiento() + "!");
			return;
		}

		/// si quedan -1
		movJugador.reducirUso();

		/// el rival elige su ataque con la IA basica
		Movimiento movRival = elegirAtaqueRival();

		/// averiguamos quien ataca primero para saber el orden de los textos
		boolean jugadorVaPrimero = false;
		if (pJugador.getVelocidad() >= combateActual.getPokemonActualRival().getVelocidad()) {
			jugadorVaPrimero = true;
		}

		/// clase Combate hace todos los calculos
		combateActual.procesarTurno(movJugador, movRival);

		/// recuperamos el resultado
		Turno ultimo = combateActual.obtenerUltimoTurno();

		/// metemos textos y animaciones en cola de mensajes
		if (ultimo != null) {
			if (jugadorVaPrimero == true) {
				if (ultimo.getAccionEntrenador() != null) {
					escribirLog(ultimo.getAccionEntrenador());
					escribirLog("@UPDATE_VISTA@");
				}

				if (ultimo.getEstadoPokemon2() != null && !ultimo.getEstadoPokemon2().equals("KO")
						&& ultimo.getAccionEntrenadorRival() != null
						&& !ultimo.getAccionEntrenadorRival().contains("debilitado")) {
					escribirLog(ultimo.getAccionEntrenadorRival());
					escribirLog("@UPDATE_VISTA@");
				}
			} else {
				if (ultimo.getAccionEntrenadorRival() != null) {
					escribirLog(ultimo.getAccionEntrenadorRival());
					escribirLog("@UPDATE_VISTA@");
				}

				if (ultimo.getEstadoPokemon1() != null && !ultimo.getEstadoPokemon1().equals("KO")
						&& ultimo.getAccionEntrenador() != null
						&& !ultimo.getAccionEntrenador().contains("debilitado")) {
					escribirLog(ultimo.getAccionEntrenador());
					escribirLog("@UPDATE_VISTA@");
				}
			}
		}

		/// comprobamos si hay muertes para sacar al siguiente Pokemon
		verificarMuertesYContinuar();

		/// sumamos un turno al contador global
		numeroTurno = numeroTurno + 1;
	}

	// metodo para generar un rival aleatorio conectando con la bd
	private Entrenador generarRivalAleatorio() {
		Entrenador rivalGenerado = new Entrenador();
		Random r = new Random();

		// generar nombre aleatorio con la lista epica
		String[] nombresPosibles = { "Cazabichos Paco", "Joven Chano", "Campista Ana", "Mister Pro", "Perico Palotes",
				"Pichote", "Manolo Lama", "LuisReApruebameXfa", "Paco el del Bar", "Cuñao Experto", "ElBicho SIUUU",
				"GigaChad", "Dominguero Furioso", "Becario Explotado", "Señora de los Gatos", "Otaku Sudoroso",
				"El Xokas", "Vegetta777", "Ibai Llanos", "Juan Cuesta", "Amador Rivas", "Cani de Poligono",
				"Abuela con Chancla" };
		String nombreElegido = nombresPosibles[r.nextInt(nombresPosibles.length)];
		rivalGenerado.setNombreEntrenador(nombreElegido);

		// cargar imagen aleatoria desde la carpeta
		try {
			// ruta
			File carpeta = new File("imgs/Combate/Entrenadores/Aleatorio");

			// leemos todos los archivos de la carpeta
			File[] archivos = carpeta.listFiles();

			if (archivos != null && archivos.length > 0) {
				// elegimos un archivo al azar de la lista
				File imagenElegida = archivos[r.nextInt(archivos.length)];

				// ponemos la imagen directamente en el imageview
				imgEntrenadorRival.setImage(new Image(imagenElegida.toURI().toString()));
			} else {
				System.out.println("ERROR: no se han encontrado imagenes en la carpeta del rival.");
			}
		} catch (Exception e) {
			System.out.println("ERROR al cargar la carpeta de imagenes: " + e.getMessage());
		}

		// calcular el nivel mas alto de nuestro equipo para balancear al rival
		int nivelBaseJugador = 5; // nivel por defecto si hay algun error
		if (jugador != null && jugador.getEquipoPokemon() != null) {
			for (Pokemon miPokemon : jugador.getEquipoPokemon()) {
				// comprobamos que el hueco no este vacio y buscamos el nivel mayor
				if (miPokemon != null && miPokemon.getNivel() > nivelBaseJugador) {
					nivelBaseJugador = miPokemon.getNivel();
				}
			}
		}

		// abrimos conexion a la bd para sacar los datos
		Connection con = null;
		dao.MovimientoDAO movDAO = null;
		try {
			bd.ConexionBBDD conector = new bd.ConexionBBDD();
			con = conector.getConexion();
			// instanciamos tu movimientodao que necesita la conexion por parametro
			movDAO = new dao.MovimientoDAO(con);
		} catch (Exception e) {
			System.out.println("ERROR al conectar con la bd: " + e.getMessage());
		}

		// generar equipo pokemon conectando a la base de datos
		Pokemon[] equipoRival = new Pokemon[6];

		for (int i = 0; i < 6; i++) {
			Pokemon p = null;

			if (con != null) {
				try {
					// generamos un id aleatorio
					int idAleatorioPkmn = r.nextInt(251) + 1;

					// sacamos el pokemon por su id usando el nuevo metodo de la pokedex
					p = dao.PokemonDAO.obtenerPokemonDesdePokedex(con, idAleatorioPkmn);
				} catch (Exception e) {
					System.out.println("error sacando pokemon de la bd " + e.getMessage());
				}
			}

			// si falla la bd o devuelve null creamos uno de repuesto para que no explote
			if (p == null) {
				p = new Pokemon();
				p.setNombrePokemon("Rata Aleatoria " + (i + 1));
			}

			// le ponemos el nivel balanceado
			p.setNivel(nivelBaseJugador + r.nextInt(6));

			// probabilidad del 5% de que el pokemon del rival sea shiny
			if (r.nextInt(100) < 5) {
				p.setEsShiny(true);
			} else {
				p.setEsShiny(false);
			}

			// llamamos al metodo para generar estadisticas aleatorias
			generarEstadisticasAleatorias(p);

			// generar 4 movimientos aleatorios desde la bd
			Movimiento[] movimientos = new Movimiento[4];
			for (int j = 0; j < 4; j++) {
				Movimiento mov = null;

				if (movDAO != null) {
					try {
						// respetamos el limite de 251 para los movimientos de tu compañero
						int idAleatorioMov = r.nextInt(251) + 1;

						// sacamos el movimiento por su id usando tu metodo buscarporid
						mov = movDAO.buscarPorId(idAleatorioMov);
					} catch (Exception e) {
						System.out.println("ERROR sacando movimiento de la bd");
					}
				}

				// repuesto si falla la consulta
				if (mov == null) {
					mov = new Movimiento();
					mov.setNombreMovimiento("Placaje Letal");
					mov.setCategoriaDano("Fisico");
					mov.setPotencia(40);
					mov.setTipo(Tipos.NORMAL);
				}

				movimientos[j] = mov;
			}
			p.setMovimientos(movimientos);

			// guardamos el pokemon en el hueco del equipo
			equipoRival[i] = p;
		}

		// le damos el equipo al rival
		rivalGenerado.setEquipoPokemon(equipoRival);

		return rivalGenerado;
	}

	// metodo auxiliar para generar estadisticas aleatorias escaladas por nivel
	private void generarEstadisticasAleatorias(Pokemon p) {
		Random r = new Random();
		int nivel = p.getNivel();

		// formula estadistica base mas extra aleatorio escalado con el nivel
		p.setVitalidadMaxima((nivel * 3) + r.nextInt(20) + 10);
		p.setVitalidad(p.getVitalidadMaxima());
		p.setAtaque((nivel * 2) + r.nextInt(15) + 5);
		p.setDefensa((nivel * 2) + r.nextInt(15) + 5);
		p.setAtaqueEspecial((nivel * 2) + r.nextInt(15) + 5);
		p.setDefensaEspecial((nivel * 2) + r.nextInt(15) + 5);
		p.setVelocidad((nivel * 2) + r.nextInt(15) + 5);
		p.setEstadoActual(model.Estados.SANO);
	}

	/**
	 * inteligencia artificial muy basica para que el rival elija ataque.
	 * 
	 * @return el movimiento elegido aleatoriamente
	 */
	private Movimiento elegirAtaqueRival() {
		Pokemon pRival = combateActual.getPokemonActualRival();
		Random r = new Random();
		int ataqueElegido = r.nextInt(4);
		Movimiento mov = pRival.getMovimientos()[ataqueElegido];

		// si elige un hueco vacio, busca el primero que tenga disponible
		if (mov == null) {
			for (int i = 0; i < 4; i++) {
				if (pRival.getMovimientos()[i] != null) {
					return pRival.getMovimientos()[i];
				}
			}
		}
		return mov;
	}

	/**
	 * Revisa si algun pokemon ha muerto en este turno y gestiona los cambios.
	 */
	private void verificarMuertesYContinuar() {
		Pokemon pJugador = combateActual.getPokemonActualJugador();
		Pokemon pRival = combateActual.getPokemonActualRival();

		/// CASO 1 el jugador muere
		if (pJugador.getVitalidad() <= 0) {
			pJugador.setVitalidad(0);
			pJugador.setEstadoActual(Estados.DEBILITADO);

			escribirLog("¡Tu " + pJugador.getNombrePokemon() + " se ha debilitado!");
			escribirLog("@ANIM_JUGADOR_OUT@"); /// encoge y desaparece
			escribirLog("@BARRA_CERO_JUGADOR@");/// vaciamos la barra sin cambiar los datos del pokemon

			registrarEventoEspecial("debilitado1", "El jugador ha perdido a " + pJugador.getNombrePokemon());

			vistaJugadorBloqueada = true;
			boolean podemosContinuar = combateActual.puedeContinuar(true);

			/// comprobamos si quedan vivos en el equipo
			if (podemosContinuar == true) {
				escribirLog("¡Te toca elegir a tu siguiente Pokémon!");
				escribirLog("@ABRIR_MENU_CAMBIO@"); /// usamos el comando en vez de abrirlo de golpe
			} else {
				/// si no quedan perdemos
				vistaJugadorBloqueada = false;
				combateActual.finalizarCombate();
				logActual.generarFicheroLog();
				if (Sesion.modoLiga && Sesion.ligaActual != null) {
					escribirLog("¡Has sido derrotado! Tu desafío en la Liga Pokemon termina aquí...");
					Sesion.ligaActual.gananciasPokedolares(false);
					registrarEventoEspecial("finPierdeLiga", "El entrenador ha perdido en la Liga Pokemon");
					escribirLog("@FIN_LIGA_PERDER@");
				} else {
					escribirLog("¡Te has quedado sin Pokémon! Has perdido...");
					registrarEventoEspecial("finPierdeCombate", "El entrenador ha perdido el combate");
					escribirLog("@FIN_COMBATE@"); // este comando guarda en BD y vuelve al combate seleccion al final
													// del texto
				}
			}
		}

		/// CASO 2 el rival ha muerto
		if (pRival.getVitalidad() <= 0) {
			pRival.setVitalidad(0);
			pRival.setEstadoActual(Estados.DEBILITADO);

			escribirLog("¡El " + pRival.getNombrePokemon() + " enemigo se ha debilitado!");
			escribirLog("@ANIM_RIVAL_OUT@"); /// el rival encoge y desaparece
			escribirLog("@BARRA_CERO_RIVAL@");/// vaciamos la barra sin cambiar los datos del pokemon

			/// guardamos los niveles de los pokemon
			int[] nivelesAntes = new int[6];
			for (int i = 0; i < 6; i = i + 1) {
				Pokemon p = jugador.getEquipoPokemon()[i];
				if (p != null) {
					nivelesAntes[i] = p.getNivel();
				}
			}

			vistaRivalBloqueada = true;

			// Antes de cambiar el pokemon rival, guardar todo
			Pokemon pj = combateActual.getPokemonActualJugador();
			String guardarNomPk1  = pj.getNombrePokemon();
			int    guardarNivPk1  = pj.getNivel();
			String guardarEnt1    = combateActual.getEntrenador().getNombreEntrenador();
			String guardarEstPk1  = pj.getVitalidad() > 0 ? "OK" : "KO";

			String guardarNomPk2  = pRival.getNombrePokemon();
			int    guardarNivPk2  = pRival.getNivel();
			String guardarEnt2    = combateActual.getEntrenadorRival().getNombreEntrenador();
			String guardarEstPk2  = "KO";

			// repartimos la XP
			boolean rivalTieneMas = combateActual.puedeContinuar(false);
			/// mostrar xp ganada
			for (int i = 0; i < 6; i = i + 1) {
				Pokemon miPkmn = jugador.getEquipoPokemon()[i];
				if (miPkmn != null && miPkmn.getVitalidad() > 0) {
					int expGanada = (miPkmn.getNivel() + (pRival.getNivel() * 10)) / 4;
					miPkmn.ganarExperiencia(expGanada);
					escribirLog(miPkmn.getNombrePokemon() + " ha ganado " + expGanada + " puntos de EXP.");
				}
			}

			registrarEventoEspecial("debilitado2", "",
					guardarNomPk1, guardarNivPk1, guardarEnt1, guardarEstPk1,
					guardarNomPk2, guardarNivPk2, guardarEnt2, guardarEstPk2);
			/// comprobamos si suben de nivel y tiene ataques pendientes
			for (int i = 0; i < 6; i = i + 1) {
				Pokemon p = jugador.getEquipoPokemon()[i];
				if (p != null) {
					if (p.getNivel() > nivelesAntes[i]) {
						escribirLog("¡" + p.getNombrePokemon() + " ha subido al nivel " + p.getNivel() + "!");

						// mostramos si aprende un movimiento cuando aun no tiene los 4
						if (p.getUltimoMovimientoAprendido() != null) {
							Movimiento nuevoAtaque = p.getUltimoMovimientoAprendido();
							escribirLog("¡" + p.getNombrePokemon() + " ha aprendido "
									+ p.getUltimoMovimientoAprendido().getNombreMovimiento() + "!");
							// para actualizar movimiento en la bd
							try {
								ConexionBBDD conector = new ConexionBBDD();
								Connection con = conector.getConexion();
								MovimientoDAO movDao = new MovimientoDAO(con);

								// insertamos el movimiento en la bd
								movDao.insertarMovimientoBD(p.getIdPokemon(), nuevoAtaque);

								con.close();
							} catch (Exception e) {
								System.out.println(
										"Error al guardar el nuevo ataque aprendido en combate: " + e.getMessage());
							}
							// aprendemos en el hueco vacio
							p.aprenderMovimientoEnHuecoVacio(nuevoAtaque);
							// actualizar si es el pokemon peleando
							if (p == combateActual.getPokemonActualJugador()) {
								escribirLog("@UPDATE_VISTA@"); // asi el guion "-" cambiara por el nombre del ataque
							}
							p.setUltimoMovimientoAprendido(null); // se vacia para el siguiente
						}

						/// si al subir de nivel se le ha quedado un ataque pendiente salta la ventana
						if (p.getMovimientoPendiente() != null) {
							gestionarAprendizajeMovimiento(p);
						}
					}
				}
			}

			/// si muere el rival saca otro
			if (rivalTieneMas == true) {
				escribirLog("¡El rival ha enviado a " + combateActual.getPokemonActualRival().getNombrePokemon() + "!");
				escribirLog("@MOSTRAR_NUEVO_RIVAL@");
			} else {
				vistaRivalBloqueada = false;
				combateActual.finalizarCombate();
				logActual.generarFicheroLog();
				if (Sesion.modoLiga && Sesion.ligaActual != null) {
					Sesion.ligaActual.gananciasPokedolares(true);
					if (Sesion.ligaActual.getCombateActual() == 6) {
						escribirLog("¡HAS DERROTADO DOPPELGANGER! ¡ERES EL NUEVO CAMPEÓN DE LA LIGA POKÉMON!");
						// TODO
						// NOTA: Aquí es donde deberías ejecutar tu DAO en el futuro para guardar el
						// equipo del jugador
						// en la base de datos, y así en su próximo intento cargará a esos Pokémon como
						// el Doppelganger.

						Sesion.modoLiga = false;
						registrarEventoEspecial("finGanaLiga", "El entrenador ha ganado la LIGA POKEMON");
						escribirLog("@FIN_LIGA_GANAR@");
					} else {
						escribirLog("¡Has vencido a " + rival.getNombreEntrenador() + " del Alto Mando!");
						Sesion.ligaActual.avanzarSiguienteCombate();
						registrarEventoEspecial("finGanaCombateLiga", "El entrenador avanza en la liga");
						escribirLog("@FIN_COMBATE_LIGA@");
					}
				} else {
					escribirLog("¡Has ganado el combate! No le quedan Pokémon al rival.");
					registrarEventoEspecial("finGanaCombate", "El entrenador ha ganado el combate");
					escribirLog("@FIN_COMBATE@"); // este comando guarda en BD y sale
				}
			}
		}

	}

	/**
	 * metodo para registrar eventos que no son ataques (muertes) en
	 * el historial
	 */
	private void registrarEventoEspecial(String tipoEvento, String descripcion) {
		Turno evento = new Turno();
		evento.setNumeroTurnoActual(combateActual.getHistorialTurnos().size() + 1);

		// identificamos el evento en la accion para el Log posterior
		evento.setAccionEntrenador(tipoEvento);
		evento.setAccionEntrenadorRival("");

		Pokemon pj = combateActual.getPokemonActualJugador();
		Pokemon pr = combateActual.getPokemonActualRival();

		if (pj != null) {
			evento.setDatosPk1(
					pj.getNombrePokemon(),
					pj.getNivel(),
					combateActual.getEntrenador().getNombreEntrenador(),
					pj.getVitalidad() > 0 ? "OK" : "KO"
			);
		}

		if (pr != null) {
			evento.setDatosPk2(
					pr.getNombrePokemon(),
					pr.getNivel(),
					combateActual.getEntrenadorRival().getNombreEntrenador(),
					pr.getVitalidad() > 0 ? "OK" : "KO"
			);
		}
		combateActual.añadirTurno(evento);
		logActual.añadirTurno(evento);
	}

	/**
	 * metodo para registrar eventos que no son ataques (fin de combate) en
	 * el historial
	 */

	private void registrarEventoEspecial(String tipoEvento, String descripcion,
										 String nomPk1, int nivPk1, String ent1, String estPk1,
										 String nomPk2, int nivPk2, String ent2, String estPk2) {

		Turno evento = new Turno();
		evento.setNumeroTurnoActual(combateActual.getHistorialTurnos().size() + 1);
		evento.setAccionEntrenador(tipoEvento);
		evento.setAccionEntrenadorRival("");
		evento.setDatosPk1(nomPk1, nivPk1, ent1, estPk1);
		evento.setDatosPk2(nomPk2, nivPk2, ent2, estPk2);

		combateActual.añadirTurno(evento);
		logActual.añadirTurno(evento);
	}

	/**
	 * Añade un mensaje a la cola de texto para que aparezca animado.
	 */
	private void escribirLog(String mensaje) {
		// lo ponemos a la cola
		colaMensajes.add(mensaje);
		// le decimos a la maquina que empiece a procesar la cola
		procesarColaMensajes();
	}

	/**
	 * Máquina de escribir con comandos visuales.
	 */
	private void procesarColaMensajes() {
		if (escribiendoMensaje == true) {
			return;
		}

		if (colaMensajes.isEmpty()) {
			controlesBloqueados = false;
			return;
		}

		controlesBloqueados = true;
		escribiendoMensaje = true;
		String mensajeActual = colaMensajes.poll();

		/// si el mensaje esta vacio lo saltamos para no romper el reloj
		if (mensajeActual == null || mensajeActual.trim().isEmpty() == true) {
			escribiendoMensaje = false;
			procesarColaMensajes();
			return;
		}

		try {
			/// SISTEMA DE COMANDOS PARA SINCRONIZAR ANIMACIONES Y TEXTO
			if (mensajeActual.startsWith("@")) {

				switch (mensajeActual) {

				case "@MOSTRAR_NUEVO_RIVAL@":

					vistaRivalBloqueada = false;
					actualizarVista();

					imgPokemonRival.setVisible(true);
					imgPokemonRival.setScaleX(0);
					imgPokemonRival.setScaleY(0);

					txtNombrePkmnRival.setVisible(true);
					txtNivelPkmnRival.setVisible(true);
					txtVitalidadPkmnRival.setVisible(true);
					barraVitalidadPkmnRival.setVisible(true);
					if (imgSexoPkmnRival != null) {
						imgSexoPkmnRival.setVisible(true);
					}

					ScaleTransition stInR = new ScaleTransition(Duration.seconds(0.5), imgPokemonRival);
					stInR.setToX(1);
					stInR.setToY(1);
					stInR.setOnFinished(e -> {
						escribiendoMensaje = false;
						procesarColaMensajes();
					});
					stInR.play();
					return;

				case "@MOSTRAR_NUEVO_JUGADOR@":
					vistaJugadorBloqueada = false;
					actualizarVista();

					imgPokemonJugador.setVisible(true);
					imgPokemonJugador.setScaleX(0);
					imgPokemonJugador.setScaleY(0);

					txtNombrePkmnJugador.setVisible(true);
					txtNivelPkmnJugador.setVisible(true);
					txtVitalidadPkmnJugador.setVisible(true);
					barraVitalidadPkmnJugador.setVisible(true);
					if (imgSexoPkmnJugador != null) {
						imgSexoPkmnJugador.setVisible(true);
					}

					ScaleTransition stInJ = new ScaleTransition(Duration.seconds(0.5), imgPokemonJugador);
					stInJ.setToX(1);
					stInJ.setToY(1);
					stInJ.setOnFinished(e -> {
						escribiendoMensaje = false;
						procesarColaMensajes();
					});
					stInJ.play();
					return;

				case "@UPDATE_VISTA@":
					actualizarVista();
					escribiendoMensaje = false;
					procesarColaMensajes();
					return;

				case "@ENTRENADORES_IN@":
					/// los entrenadores se mueven de los lados al centro
					TranslateTransition inJ = new TranslateTransition(Duration.seconds(1), imgEntrenadorJugador);
					inJ.setToX(0);
					TranslateTransition inR = new TranslateTransition(Duration.seconds(1), imgEntrenadorRival);
					inR.setToX(0);

					ParallelTransition entrada = new ParallelTransition(inJ, inR);
					entrada.setOnFinished(e -> {
						escribiendoMensaje = false;
						procesarColaMensajes();
					});
					entrada.play();
					return;

				case "@ENTRENADORES_OUT@":
					TranslateTransition outJ = new TranslateTransition(Duration.seconds(1), imgEntrenadorJugador);
					outJ.setToX(-400);
					TranslateTransition outR = new TranslateTransition(Duration.seconds(1), imgEntrenadorRival);
					outR.setToX(400);

					ParallelTransition salida = new ParallelTransition(outJ, outR);
					salida.setOnFinished(e -> {
						imgEntrenadorJugador.setVisible(false);
						imgEntrenadorRival.setVisible(false);
						escribiendoMensaje = false;
						procesarColaMensajes();
					});
					salida.play();
					return;

				case "@ANIM_RIVAL_OUT@":
					ScaleTransition stOutR = new ScaleTransition(Duration.seconds(0.5), imgPokemonRival);
					stOutR.setToX(0);
					stOutR.setToY(0);
					stOutR.setOnFinished(e -> {
						imgPokemonRival.setVisible(false);
						escribiendoMensaje = false;
						procesarColaMensajes();
					});
					stOutR.play();
					return;

				case "@ANIM_JUGADOR_OUT@":
					ScaleTransition stOutJ = new ScaleTransition(Duration.seconds(0.5), imgPokemonJugador);
					stOutJ.setToX(0);
					stOutJ.setToY(0);
					stOutJ.setOnFinished(e -> {
						imgPokemonJugador.setVisible(false);
						escribiendoMensaje = false;
						procesarColaMensajes();
					});
					stOutJ.play();
					return;

				case "@BARRA_CERO_JUGADOR@":
					barraVitalidadPkmnJugador.setProgress(0);
					txtVitalidadPkmnJugador.setText("0");
					escribiendoMensaje = false;
					procesarColaMensajes();
					return;

				case "@BARRA_CERO_RIVAL@":
					barraVitalidadPkmnRival.setProgress(0);
					txtVitalidadPkmnRival.setText("0");
					escribiendoMensaje = false;
					procesarColaMensajes();
					return;

				case "@ABRIR_MENU_CAMBIO@":
					abrirMenuCambio(true);
					escribiendoMensaje = false;
					procesarColaMensajes();
					return;

				case "@FIN_COMBATE@":
					logActual.generarFicheroLog();
					guardarProgresoBD();
					panelMenuPrincipal.setVisible(false);
					panelMenuAtaques.setVisible(false);
					volverASeleccionCombate(5);
					escribiendoMensaje = false;
					return;
					
				case "@FIN_COMBATE_LIGA@":
					logActual.generarFicheroLog();
                    guardarProgresoBD();
                    panelMenuPrincipal.setVisible(false); 
                    panelMenuAtaques.setVisible(false);
                    volverADescansoLiga(4); // Nos lleva a Descanso liga
                    escribiendoMensaje = false;
                    return;
                    
                case "@FIN_LIGA_GANAR@":
                case "@FIN_LIGA_PERDER@":
					logActual.generarFicheroLog();
                    guardarProgresoBD();
                    panelMenuPrincipal.setVisible(false); 
                    panelMenuAtaques.setVisible(false);
                    volverASeleccionCombate(5); // Nos lleva al menú general
                    escribiendoMensaje = false;
                    return;
				}
			}

			/// si no es un comando escribimos texto en la pantalla
			if (txtLogCombate != null) {

				txtLogCombate.setText(""); // vaciamos el texto anterior
				final int[] indiceLetra = { 0 };

				Timeline animacionTexto = new Timeline();
				KeyFrame frame = new KeyFrame(Duration.millis(30), evento -> {
					try {
						String textoActual = txtLogCombate.getText();
						txtLogCombate.setText(textoActual + mensajeActual.charAt(indiceLetra[0]));
						indiceLetra[0] = indiceLetra[0] + 1;
					} catch (Exception ex) {
					}
				});

				animacionTexto.getKeyFrames().add(frame);
				animacionTexto.setCycleCount(mensajeActual.length());

				animacionTexto.setOnFinished(e -> {
					PauseTransition pausaLectura = new PauseTransition(Duration.seconds(1.5));
					pausaLectura.setOnFinished(eventoPausa -> {
						escribiendoMensaje = false;
						procesarColaMensajes();
					});
					pausaLectura.play();
				});

				animacionTexto.play();

			} else {
				System.out.println(mensajeActual);
				escribiendoMensaje = false;
				procesarColaMensajes();
			}
		} catch (Exception e) {
			System.out.println("Crasheo evitado en la maquina de comandos: " + e.getMessage());
			escribiendoMensaje = false;
			/// forzamos el desbloqueo si ocurre algun error interno
			controlesBloqueados = false;
			procesarColaMensajes();
		}
	}

	// boton de salir a seleccion combate
	@FXML
	public void combateSalir(MouseEvent event) {
		if (controlesBloqueados == true) {
			return;
		}

		if (Sesion.modoLiga) {
			System.out.println("No puedes huri de un combate de la Liga Pokémon");
			return; // lo paramos que no se pueda hacer nada con el boton huir
		}

		// bloqueamos los controles para que no haga clics compulsivos mientras huye
		controlesBloqueados = true;

		// ocultamos menus por si acaso
		panelMenuAtaques.setVisible(false);
		panelMenuPrincipal.setVisible(true);
		btnSalirMenuAtaque.setVisible(false);

		try {
			// si el combate sigue en curso y el jugador sale cuenta como retirada
			if (combateActual != null && combateActual.getPokemonKOEntrenador() < 6
					&& combateActual.getPokemonKORival() < 6) {
				combateActual.retirarse(); // pierde 1/3 de su dinero

				// actualizamos en bd
				logActual.generarFicheroLog(); // Generar log al huir
				guardarProgresoBD();
			}

			// mandamos el mensaje a la pantalla
			escribirLog("¡Has huido del combate! Pierdes algo de dinero por el panico...");

			// guardamos la referencia a la ventana actual antes de la pausa
			Stage ventanaActual = (Stage) ((Node) event.getSource()).getScene().getWindow();

			// creamos una pausa de 3 segundos para que de tiempo a leer el texto de huida
			PauseTransition pausaHuida = new PauseTransition(Duration.seconds(3));
			pausaHuida.setOnFinished(e -> {
				try {
					System.out.println("cargando la vista principal tras huir...");

					// cargar la vista principal
					FXMLLoader loader = new FXMLLoader(
							getClass().getResource("/view/SeleccionCombate/SeleccionCombate.fxml"));
					Parent root = loader.load();
					Scene scene = new Scene(root);

					// cargar el css
					String css = this.getClass().getResource("/view/principal/vistaPrincipal.css").toExternalForm();
					scene.getStylesheets().add(css);

					// titulo, forzar el tamaño de la ventana y bloquear cambio manual
					ventanaActual.setTitle("PokeINC - Seleccion Combate");
					ventanaActual.setResizable(false);

					// cargar icono
					File file = new File("imgs/Login/Login-icon.png");
					if (file.exists()) {
						String imagePath = file.toURI().toString();
						ventanaActual.getIcons().add(new Image(imagePath));
					}

					// cambiar la escena
					ventanaActual.setScene(scene);
					ventanaActual.show();

				} catch (Exception ex) {
					System.out.println("error al cambiar de ventana tras huir: " + ex.getMessage());
					ex.printStackTrace();
				}
			});

			// arrancamos el temporizador
			pausaHuida.play();

		} catch (Exception e) {
			System.out.println("error en la retirada: " + e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * Orden de entrada tipo pokemon clasico sincronizado con el texto.
	 */
	private void animacionEntrada() {

		/// los pokemon empiezan invisibles y encogidos
		imgPokemonJugador.setVisible(false);
		imgPokemonRival.setVisible(false);
		imgPokemonJugador.setScaleX(0);
		imgPokemonJugador.setScaleY(0);
		imgPokemonRival.setScaleX(0);
		imgPokemonRival.setScaleY(0);

		/// ocultamos todos los elementos de las cajas de informacion
		txtNombrePkmnJugador.setVisible(false);
		txtNivelPkmnJugador.setVisible(false);
		txtVitalidadPkmnJugador.setVisible(false);
		barraVitalidadPkmnJugador.setVisible(false);
		if (imgSexoPkmnJugador != null) {
			imgSexoPkmnJugador.setVisible(false);
		}

		txtNombrePkmnRival.setVisible(false);
		txtNivelPkmnRival.setVisible(false);
		txtVitalidadPkmnRival.setVisible(false);
		barraVitalidadPkmnRival.setVisible(false);
		if (imgSexoPkmnRival != null) {
			imgSexoPkmnRival.setVisible(false);
		}

		/// los entrenadores empiezan visibles fuera de la pantalla
		imgEntrenadorJugador.setVisible(true);
		imgEntrenadorRival.setVisible(true);
		imgEntrenadorJugador.setTranslateX(-400); /// izquierda
		imgEntrenadorRival.setTranslateX(400); /// derech

		/// primero el texto inicial
		if (Sesion.modoLiga && Sesion.ligaActual != null) {
			if (Sesion.ligaActual.getCombateActual() == 6) {
				escribirLog("¡Te enfrentas al Campeón  " + rival.getNombreEntrenador() + "!");
			} else {
				escribirLog("¡Combate del Alto Mando contra:  " + rival.getNombreEntrenador() + ". Combate"
						+ Sesion.ligaActual.getCombateActual() + "/5!");
			}
		} else {
			escribirLog("¡Un combate aleatorio ha comenzado!");
		}

		escribirLog("@ENTRENADORES_IN@");
		escribirLog("¡Nos enfrentamos a " + rival.getNombreEntrenador() + " no podemos fallar!");
		escribirLog("@ENTRENADORES_OUT@");

		/// TURNO RIVAL
		if (combateActual.getPokemonActualRival() != null) {
			escribirLog("¡El rival saca a " + combateActual.getPokemonActualRival().getNombrePokemon() + "!");
			escribirLog("@MOSTRAR_NUEVO_RIVAL@");
		} else {
			escribirLog("ERROR: ¡El rival no tiene Pokémon en su equipo!");
		}

		// TURNO NUESTRO
		if (combateActual.getPokemonActualJugador() != null) {
			escribirLog("¡Ve, " + combateActual.getPokemonActualJugador().getNombrePokemon() + "!");
			escribirLog("@MOSTRAR_NUEVO_JUGADOR@");
		} else {
			escribirLog("ERROR: ¡No tienes Pokémon sanos en tu equipo!");
		}
	}

	// metodo para volver a la pantalla de seleccion
	private void volverASeleccionCombate(int segundosPausa) {
		controlesBloqueados = true;

		// usamos el panel principal para encontrar la ventana
		Stage ventanaActual = (Stage) panelMenuPrincipal.getScene().getWindow();

		PauseTransition pausaSalida = new PauseTransition(Duration.seconds(segundosPausa));
		pausaSalida.setOnFinished(e -> {
			try {

				FXMLLoader loader = new FXMLLoader(
						getClass().getResource("/view/SeleccionCombate/SeleccionCombate.fxml"));
				Parent root = loader.load();
				Scene scene = new Scene(root);

				// cargamos su css
				URL urlCss = this.getClass().getResource("/view/.css");
				if (urlCss != null) {
					// si lo encuentra lo aplica
					scene.getStylesheets().add(urlCss.toExternalForm());
				} else {
					// si no lo encuentra avisa
					System.out
							.println("Aviso: No se encontró vistaPrincipal.css. Cargando pantalla sin estilos extra.");
				}

				ventanaActual.setTitle("PokeINC - Seleccion Combate");
				ventanaActual.setResizable(false);

				ventanaActual.setScene(scene);
				ventanaActual.show();

			} catch (Exception ex) {
				System.out.println("ERROR al volver a la seleccion: " + ex.getMessage());
				ex.printStackTrace();
			}
		});

		pausaSalida.play();
	}

	// metodo nuevo exclusivo para ir al descanso de la liga
	private void volverADescansoLiga(int segundosPausa) {
		controlesBloqueados = true;

		Stage ventanaActual = (Stage) panelMenuPrincipal.getScene().getWindow();

		PauseTransition pausaSalida = new PauseTransition(Duration.seconds(segundosPausa));
		pausaSalida.setOnFinished(e -> {
			try {
				FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/ligaPokemon/DescansoLiga.fxml"));
				Parent root = loader.load();
				Scene scene = new Scene(root);

				ventanaActual.setTitle("PokeINC - Descanso Liga");
				ventanaActual.setResizable(false);
				ventanaActual.setScene(scene);
				ventanaActual.show();

			} catch (Exception ex) {
				System.out.println("ERROR al volver a DescansoLiga: " + ex.getMessage());
				ex.printStackTrace();
			}
		});

		pausaSalida.play();
	}

	/**
	 * Abre el menu lateral para elegir a un nuevo Pokemon.
	 * 
	 * @param obligatorio true si nuestro Pokemon ha muerto y no podemos cancelar el
	 *                    cambio
	 */
	private void abrirMenuCambio(boolean obligatorio) {

		/// ocultamos los otros menus y mostramos este
		panelMenuPrincipal.setVisible(false);
		panelMenuAtaques.setVisible(false);
		panelCambioPokemon.setVisible(true);

		/// si es obligatorio cambiar ocultamos el boton de volver
		if (obligatorio == true) {
			btnVolverCambio.setVisible(false);
		} else {
			btnVolverCambio.setVisible(true);
		}

		/// metemos los botones en un array para recorrerlos
		Button[] botonesEquipo = { btnPokemon1, btnPokemon2, btnPokemon3, btnPokemon4, btnPokemon5, btnPokemon6 };
		Pokemon[] miEquipo = jugador.getEquipoPokemon();

		/// recorremos los 6 huecos de nuestro equipo
		for (int i = 0; i < 6; i = i + 1) {

			if (miEquipo[i] != null) {
				Pokemon p = miEquipo[i];

				/// intentamos cargar la imagen frontal para hacer el icono
				String rutaImagen = p.getImgFrontalPokemon();

				if (rutaImagen != null) {
					// aplicamos el prefijo base para encontrar la carpeta imgs correctamente
					String prefijoBase = "imgs/Pokemons/sprites/crystal/transparent/";
					String rutaIcono = prefijoBase + rutaImagen;
					File imgFile = new File(rutaIcono);

					if (imgFile.exists() == true) {
						ImageView icono = new ImageView(new Image(imgFile.toURI().toString()));

						/// forzamos el tamaño para que sea un icono pequeño
						icono.setFitWidth(40);
						icono.setFitHeight(40);
						icono.setPreserveRatio(true);

						/// e ponemos la imagen al boton
						botonesEquipo[i].setGraphic(icono);
					}
				}

				/// escribimos el nombre y la vida
				botonesEquipo[i]
						.setText(p.getNombrePokemon() + "\nPS: " + p.getVitalidad() + " / " + p.getVitalidadMaxima());
				botonesEquipo[i].setVisible(true);

				/// si esta debilitado o es el que ya esta luchando desactivamos el boton para
				/// no poder pulsarlo
				if (p.getVitalidad() <= 0 || p == combateActual.getPokemonActualJugador()) {
					botonesEquipo[i].setDisable(true);
				} else {
					botonesEquipo[i].setDisable(false);
				}

			} else {
				/// si el hueco del equipo esta vacio ocultamos el boton
				botonesEquipo[i].setVisible(false);
			}
		}
	}

	/**
	 * metodo para guardar el estado actual del equipo del jugador en la bd
	 */
	private void guardarProgresoBD() {
		System.out.println("Guardando progreso en la base de datos...");
		try {
			// abrimos la conexión
			ConexionBBDD conector = new bd.ConexionBBDD();
			Connection con = conector.getConexion();
			MovimientoDAO movDao = new MovimientoDAO(con);

			// recorremos nuestro equipo y actualizamos cada Pokémon
			for (Pokemon p : jugador.getEquipoPokemon()) {
				if (p != null) {
					PokemonDAO.actualizarStatsBD(con, p);

					// guardamos los PP actuales de cada ataque
					if (p.getMovimientos() != null) {
						for (int i = 0; i < 4; i++) {
							Movimiento mov = p.getMovimientos()[i];
							if (mov != null) {
								movDao.actualizarPPs(p.getIdPokemon(), mov.getIdMovimiento(),
										mov.getCantidadMovimientos());
							}
						}
					}
				}
			}

			con.close();
			System.out.println("¡Progreso guardado correctamente!");

		} catch (Exception e) {
			System.out.println("ERROR al guardar el progreso en la BD: " + e.getMessage());
		}
	}

	/**
	 * Lanza una ventana emergente para elegir que ataque olvidar
	 */
	private void gestionarAprendizajeMovimiento(Pokemon p) {
		Movimiento nuevoMov = p.getMovimientoPendiente();
		if (nuevoMov == null)
			return; // si no hay nada pendiente no hacemos nada

		Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
		alerta.setTitle("¡Aprender nuevo movimiento!");
		alerta.setHeaderText(p.getNombrePokemon() + " quiere aprender " + nuevoMov.getNombreMovimiento()
				+ ".\nPero ya conoce 4 movimientos. ¿Deseas olvidar uno?");

		// creamos los botones con los nombres de los 4 ataques actuales
		ButtonType btn1 = new ButtonType("Olvidar " + p.getMovimientos()[0].getNombreMovimiento());
		ButtonType btn2 = new ButtonType("Olvidar " + p.getMovimientos()[1].getNombreMovimiento());
		ButtonType btn3 = new ButtonType("Olvidar " + p.getMovimientos()[2].getNombreMovimiento());
		ButtonType btn4 = new ButtonType("Olvidar " + p.getMovimientos()[3].getNombreMovimiento());
		ButtonType btnCancelar = new ButtonType("No aprender", ButtonBar.ButtonData.CANCEL_CLOSE);

		// Los metemos en la alerta
		alerta.getButtonTypes().setAll(btn1, btn2, btn3, btn4, btnCancelar);

		// Mostramos la ventana y esperamos a que el jugador haga clic
		Optional<ButtonType> resultado = alerta.showAndWait();

		// comprobamos que se pulsa
		if (resultado.isPresent()) {
			int idMovViejo = -1; // guardaremos ID del ataque a sustituir

			if (resultado.get() == btn1) {
				idMovViejo = p.getMovimientos()[0].getIdMovimiento();
				escribirLog("1, 2 y... ¡Puf! Olvidó " + p.getMovimientos()[0].getNombreMovimiento() + " y aprendió "
						+ nuevoMov.getNombreMovimiento() + ".");
				p.reemplazarMovimiento(0, nuevoMov);

			} else if (resultado.get() == btn2) {
				idMovViejo = p.getMovimientos()[1].getIdMovimiento();
				escribirLog("1, 2 y... ¡Puf! Olvidó " + p.getMovimientos()[1].getNombreMovimiento() + " y aprendió "
						+ nuevoMov.getNombreMovimiento() + ".");
				p.reemplazarMovimiento(1, nuevoMov);

			} else if (resultado.get() == btn3) {
				idMovViejo = p.getMovimientos()[2].getIdMovimiento();
				escribirLog("1, 2 y... ¡Puf! Olvidó " + p.getMovimientos()[2].getNombreMovimiento() + " y aprendió "
						+ nuevoMov.getNombreMovimiento() + ".");
				p.reemplazarMovimiento(2, nuevoMov);

			} else if (resultado.get() == btn4) {
				idMovViejo = p.getMovimientos()[3].getIdMovimiento();
				escribirLog("1, 2 y... ¡Puf! Olvidó " + p.getMovimientos()[3].getNombreMovimiento() + " y aprendió "
						+ nuevoMov.getNombreMovimiento() + ".");
				p.reemplazarMovimiento(3, nuevoMov);

			} else {
				p.setMovimientoPendiente(null); // si se cancela no lo aprende
				escribirLog(p.getNombrePokemon() + " no aprendió " + nuevoMov.getNombreMovimiento() + ".");
			}

			// si borramos un ataque lo actualizamos en la bg
			if (idMovViejo != -1) {
				try {
					ConexionBBDD conector = new ConexionBBDD();
					Connection con = conector.getConexion();

					// instanciamos y actualizamos
					MovimientoDAO movDao = new MovimientoDAO(con);
					movDao.cambiarMovimientoBD(p.getIdPokemon(), idMovViejo, nuevoMov);

					con.close();
				} catch (Exception e) {
					System.out.println("Error de conexión al guardar el ataque: " + e.getMessage());
				}
			}
		}
	}
}