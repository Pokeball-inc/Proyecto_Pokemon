package controller;

import java.io.File;
import java.net.URL;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
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
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import bd.ConexionBBDD;
import dao.CapturaDao;
import model.Combate;
import model.Entrenador;
import model.Movimiento;
import model.Pokemon;
import model.Sesion;

public class CombateController implements Initializable {

    //Elementos vista
	
	@FXML
	private ImageView fondoCombateAleatorio;

    //Pokemon del Jugador
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

    //Pokemon del Rival 
    @FXML 
    private ImageView imgPokemonRival;
    
    @FXML 
    private ImageView imgPkmnEntrenadorRival;
    
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
    

    //Botones
    @FXML 
    private Button btnAtaque1;
    
    @FXML 
    private Button btnAtaque2;
    
    @FXML 
    private Button btnAtaque3;
    
    @FXML 
    private Button btnAtaque4;
    
    @FXML 
    private ImageView btnDescansar;
    
    @FXML 
    private ImageView btnCambiarPokemon;
    
    @FXML 
    private ImageView btnLuchar;
    
    @FXML 
    private ImageView btnMochila;
    
    @FXML 
    private ImageView btnHuir; //o rendirse

    //Historial del Combate
    @FXML 
    private TextArea txtLogCombate; //ventana de texto para narrar los turnos

    //Variables

    private Combate combateActual; 
    private Entrenador jugador;
    private Entrenador rival;
    
    private int numeroTurno = 1; //contador de turnos


    /**
     * Inicializa la pantalla de combate. 
     * Aquí cargas los datos del jugador, generas al rival y llamas a actualizarVista().
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //instanciar el combate
        combateActual = new Combate();
        
     
    }

    /**
     * Refresca todos los textos, imágenes y barras de vida/estamina de la pantalla
     * leyendo los datos actuales del modelo.
     */
    private void actualizarVista() {
        Pokemon pJugador = combateActual.getPokemonActualJugador();
        Pokemon pRival = combateActual.getPokemonActualRival();

        if (pJugador != null) {
            //actualizar imagen, nombre, vida y estamina del jugador
            //poner los nombres de los 4 ataques en los botones btnAtaque1, btnAtaque2...
        }

        if (pRival != null) {
            //actualizar imagen, nombre, vida y estamina del rival
        }
    }

   //Cambiar tamaño de botones (gracias Elyass XDDD)
    
    // --------------- INCREMENTAR Y DISMINUIR TAMAÑO DEL BOTON ENTRENAR AL PASAR EL CURSOR ---------------
    
    //ENTRENAMIENTO PESADO
    @FXML
    private void aumentarTamañoBotonLuchar(MouseEvent event) {
        if (btnLuchar != null) {
        	btnLuchar.setScaleX(btnLuchar.getScaleX() + 0.2);
        	btnLuchar.setScaleY(btnLuchar.getScaleY() + 0.2);
        }
    }

    @FXML
    private void disminuirTamañoBotonLuchar(MouseEvent event) {
        if (btnLuchar != null) {
        	btnLuchar.setScaleX(btnLuchar.getScaleX() - 0.2);
        	btnLuchar.setScaleY(btnLuchar.getScaleY() - 0.2);
        }
    }
    
    //ENTRENAMIENTO FURIOSO
    @FXML
    private void aumentarTamañoBotonCambiarPokemon(MouseEvent event) {
        if (btnCambiarPokemon != null) {
        	btnCambiarPokemon.setScaleX(btnCambiarPokemon.getScaleX() + 0.2);
        	btnCambiarPokemon.setScaleY(btnCambiarPokemon.getScaleY() + 0.2);
        }
    }

    @FXML
    private void disminuirTamañoBotonCambiarPokemon(MouseEvent event) {
        if (btnCambiarPokemon != null) {
        	btnCambiarPokemon.setScaleX(btnCambiarPokemon.getScaleX() - 0.2);
        	btnCambiarPokemon.setScaleY(btnCambiarPokemon.getScaleY() - 0.2);
        }
    }
    
    //ENTRENAMIENTO FUNCIONAL
    @FXML
    private void aumentarTamañoBotonMochila(MouseEvent event) {
        if (btnMochila != null) {
        	btnMochila.setScaleX(btnMochila.getScaleX() + 0.2);
        	btnMochila.setScaleY(btnMochila.getScaleY() + 0.2);
        }
    }

    @FXML
    private void disminuirTamañoBotonMochila(MouseEvent event) {
        if (btnMochila != null) {
        	btnMochila.setScaleX(btnMochila.getScaleX() - 0.2);
        	btnMochila.setScaleY(btnMochila.getScaleY() - 0.2);
        }
    }
    
    //ENTRENAMIENTO ONIRICO
    @FXML
    private void aumentarTamañoBotonSalir(MouseEvent event) {
        if (btnHuir != null) {
        	btnHuir.setScaleX(btnHuir.getScaleX() + 0.2);
        	btnHuir.setScaleY(btnHuir.getScaleY() + 0.2);
        }
    }

    @FXML
    private void disminuirTamañoBotonSalir(MouseEvent event) {
        if (btnHuir != null) {
        	btnHuir.setScaleX(btnHuir.getScaleX() - 0.2);
        	btnHuir.setScaleY(btnHuir.getScaleY() - 0.2);
        }
    }
    
    // ================= METODOS DE LOS BOTONES =================

    @FXML
    public void usarAtaque1(MouseEvent event) {
        ejecutarTurno(1); 
    }

    @FXML
    public void usarAtaque2(MouseEvent event) {
        ejecutarTurno(2);
    }

    @FXML
    public void usarAtaque3(MouseEvent event) {
        ejecutarTurno(3);
    }

    @FXML
    public void usarAtaque4(MouseEvent event) {
        ejecutarTurno(4);
    }

    @FXML
    public void accionDescansar(MouseEvent event) {
        //recuperar estamina y saltar turno
        escribirLog("¡Tu " + combateActual.getPokemonActualJugador().getNombrePokemon() + " decide descansar para recuperar estamina!");
        turnoRival();
    }

    @FXML
    public void accionCambiarPokemon(MouseEvent event) {
       
    }

    @FXML
    public void accionHuir(MouseEvent event) {
        combateActual.retirarse();
        escribirLog("Has huido del combate...");
        //cerrar ventana y volver al menu principal
    }

    @FXML
    public void emitirSonidoJugador(MouseEvent event) {
        //logica para reproducir el arhivo de sonido pokemon
    }

 // ================= LÓGICA CENTRAL DEL COMBATE =================

    /**
     * El corazon del combate. Recibe el ataque elegido por el jugador,
     * evalua si puede atacar, calcula el daño/efecto, da paso al rival,
     * aplica efectos de final de turno y comprueba si algun Pokemon se debilita.
     * @param indiceAtaque El numero del boton pulsado (del 1 al 4)
     */
    private void ejecutarTurno(int indiceAtaque) {
        Pokemon pJugador = combateActual.getPokemonActualJugador();
        Pokemon pRival = combateActual.getPokemonActualRival();

        //comprobar si el jugador puede atacar (estados como Dormido/Paralizado)
        if (!combateActual.puedeAtacarEsteTurno(pJugador)) {
            //si esta dormido o congelado, salta su turno de ataque y narramos en el log
            escribirLog(pJugador.getNombrePokemon() + " no ha podido atacar debido a su estado.");
        } else {
            //le restamos 1 al numero que le pasas para coger el ataque del array 
            Movimiento mov = pJugador.getMovimientos()[indiceAtaque - 1];
            
            //validacion extra de seguridad por si el hueco del ataque esta vacio
            if (mov != null) {
                escribirLog(pJugador.getNombrePokemon() + " usa " + mov.getNombreMovimiento() + "!");
                
                //TODO: LOGICA DE DAÑO DE TIPOS (X2,/2 ETC)
            }
        }

        //comprobar si el rival ha muerto (KO) tras nuestro ataque
        if (pRival.getVitalidad() <= 0) {
            pRival.setVitalidad(0); //evitamos vidas negativas
            escribirLog("¡El " + pRival.getNombrePokemon() + " enemigo se ha debilitado!");
            
            //si muere, el rival saca otro (el metodo puedeContinuar ya suma el KO)
            if (combateActual.puedeContinuar(false)) {
                //actualizamos la variable para apuntar al nuevo rival que acaba de salir
                pRival = combateActual.getPokemonActualRival(); 
                escribirLog("¡El rival ha enviado a " + pRival.getNombrePokemon() + "!");
            } else {
                //si no le quedan ganamos (6 KOs), finalizamos y salimos del metodo
                combateActual.finalizarCombate();
                escribirLog("¡Has ganado el combate! No le quedan Pokémon al rival.");
                actualizarVista();
                return; //cortamos aqui para que no siga ejecutando el turno
            }
        } else {
            //si el rival sigue vivo tras nuestro golpe, realiza su ataque
            turnoRival();
        }

        //comprobar si el jugador ha muerto tras el ataque de la IA
        if (pJugador.getVitalidad() <= 0) {
            pJugador.setVitalidad(0); //evitamos vidas negativas
            escribirLog("¡Tu " + pJugador.getNombrePokemon() + " se ha debilitado!");
            
            //comprobamos si quedan vivos en el equipo
            if (combateActual.puedeContinuar(true)) {
                //abrir ventana para cambiar (Bloquearemos el combate hasta que cambie)
                escribirLog("¡Te toca elegir a tu siguiente Pokémon!");
            } else {
                //si no quedan perdemos
                combateActual.finalizarCombate();
                escribirLog("¡Te has quedado sin Pokémon! Has perdido...");
                actualizarVista();
                return; //cortamos aqui
            }
        }

        //aplicar efectos de final de turno (Veneno, Quemadura, Drenadoras...)
        //solo se aplican si ambos siguen vivos al terminar de pegarse
        if (pJugador.getVitalidad() > 0 && pRival.getVitalidad() > 0) {
            combateActual.aplicarEfectosFinalDeTurno(pJugador, pRival);
            
            //una ultima comprobación rápida por si el veneno/quemadura ha matado a alguien
            if (pJugador.getVitalidad() <= 0 || pRival.getVitalidad() <= 0) {
                escribirLog("Alguien ha caído por los estados alterados...");
                //(Se gestionara el KO al pulsar cualquier boton en el turno que viene)
            }
        }

        //aumentar el contador de turnos y actualizar la pantalla visual
        numeroTurno++;
        actualizarVista();
    }

    /**
     * Lógica muy básica para que el rival decida qué hacer.
     */
    private void turnoRival() {
        
    }

    /**
     * Añade un nuevo mensaje a la caja de texto narrativo del combate y hace autoscroll.
     */
    private void escribirLog(String mensaje) {
        txtLogCombate.appendText("Turno " + numeroTurno + ": " + mensaje + "\n");
    }
    
  //Boton de salir
    @FXML
    public void combateSalir(MouseEvent event) {
        try {
            //si el combate sigue en curso y el jugador sale, cuenta como retirada
            if (combateActual != null && combateActual.getPokemonKOEntrenador() < 6 && combateActual.getPokemonKORival() < 6) {
                combateActual.retirarse(); //pierde 1/3 de su dinero
            }

            System.out.println("Cargando la vista principal...");

            //Recibir el click
            Node source = (Node) event.getSource();

            //Recuperar la ventana
            Stage primaryStage = (Stage) source.getScene().getWindow();

            //Cargar la vista Principal 
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/principal/vistaPrincipal.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);

            //Cargar el CSS
            String css = this.getClass().getResource("/view/principal/vistaPrincipal.css").toExternalForm();
            scene.getStylesheets().add(css);

            //Titulo, forzar el tamaño de la ventana y bloquear cambio manual
            primaryStage.setTitle("PokeINC - Principal");
            primaryStage.setResizable(false);

            //Cargar icono
            File file = new File("imgs/Login/Login-icon.png");

            if (file.exists()) {
                String imagePath = file.toURI().toString();
                primaryStage.getIcons().add(new Image(imagePath));
            } else {
                System.out.println("No se encontró el icono en: " + file.getAbsolutePath());
            }

            //Cambiar la escena 
            primaryStage.setScene(scene);

            //Mostrar la escena
            primaryStage.show();
        } catch (Exception e) {
            System.out.println("Error al cambiar de ventana - " + e.getMessage());
            e.printStackTrace();
        }
    }
}