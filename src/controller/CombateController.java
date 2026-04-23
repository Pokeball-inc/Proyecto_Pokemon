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
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
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
    private Label txtLogCombate; //ventana de texto para narrar los turnos

    //Variables

    private Combate combateActual; 
    private Entrenador jugador;
    private Entrenador rival;
    
    private int numeroTurno = 1; //contador de turnos
    
    //Variables para la animación del texto
    private Queue<String> colaMensajes = new LinkedList<>();
    private boolean escribiendoMensaje = false;


    /**
     * Inicializa la pantalla de combate. 
     * Aquí cargas los datos del jugador, generas al rival y llamas a actualizarVista().
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //instanciar el combate
        combateActual = new Combate();
        
        animacionEntrada();//animacion inicial 
     
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
     * Añade un mensaje a la cola de texto para que aparezca animado.
     */
    private void escribirLog(String mensaje) {
        //lo ponemos a la cola
        colaMensajes.add(mensaje);
        //le decimos a la máquina que empiece a procesar la cola
        procesarColaMensajes();
    }

    /**
     * Máquina de escribir: Coge el primer mensaje de la cola, limpia la pantalla 
     * y lo escribe letra a letra.
     */
    private void procesarColaMensajes() {
        //si ya hay un mensaje escribiendose en pantalla, o no hay nada en la cola, paramos.
        if (escribiendoMensaje || colaMensajes.isEmpty()) {
            return;
        }

        escribiendoMensaje = true; //para que no se pisen los textos
        String mensajeActual = colaMensajes.poll(); //sacamos el primer mensaje de la fila

        if (txtLogCombate != null) {
            txtLogCombate.setText(""); //esto vacia el texto anterior

            // Un contador para saber por que letra vamos
            final int[] indiceLetra = {0}; 
            
            //creamos la animacion que se repetira por cada letra
            Timeline animacionTexto = new Timeline();
            
            KeyFrame frame = new KeyFrame(
                //velocidad letras: 30 milisegundos
                Duration.millis(30), 
                evento -> {
                    //escribimos la letra exacta en la que estamos
                	String textoActual = txtLogCombate.getText();
                    txtLogCombate.setText(textoActual + mensajeActual.charAt(indiceLetra[0]));
                    indiceLetra[0]++;
                }
            );

            animacionTexto.getKeyFrames().add(frame);
            animacionTexto.setCycleCount(mensajeActual.length()); //repetir tantas veces como letras 

            //se termina de escribir la frase
            animacionTexto.setOnFinished(e -> {
                //esperamos 1.5s para que el jugador lo lea
                PauseTransition pausaLectura = new PauseTransition(Duration.seconds(1.5));
                pausaLectura.setOnFinished(eventoPausa -> {
                    //desbloqueamos y llamamos al siguiente mensaje
                    escribiendoMensaje = false;
                    procesarColaMensajes(); 
                });
                pausaLectura.play();
            });

            animacionTexto.play(); //iniciar la animacion
            
        } else {
            //log de seguridad por consola
            System.out.println(mensajeActual);
            escribiendoMensaje = false;
            procesarColaMensajes();
        }
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
    
    /**
     * Realiza la animacion de entrada de los entrenadores estilo RPG clasico.
     * Los entrenadores entran, hacen su pose (GIF), se retiran y aparecen los Pokemon.
     */
    private void animacionEntrada() {
        //pokemon ocultos y entrenadores visibles
        imgPokemonJugador.setVisible(false);
        imgPokemonRival.setVisible(false);
        imgEntrenadorJugador.setVisible(true);
        imgEntrenadorRival.setVisible(true);

        //desplazamos entrenadores fuera para que vengan de ahi
        imgEntrenadorJugador.setTranslateX(-400); //izquierda
        imgEntrenadorRival.setTranslateX(400);    //derecha

        //ENTRADA
        //movemos el jugador hacia la posición 0  en 1 segundo
        TranslateTransition entradaJugador = new TranslateTransition(Duration.seconds(1), imgEntrenadorJugador);
        entradaJugador.setToX(0);

        //movemos el rival hacia la posición 0 en 1 segundo
        TranslateTransition entradaRival = new TranslateTransition(Duration.seconds(1), imgEntrenadorRival);
        entradaRival.setToX(0);

        //ParallelTransition agrupa animaciones para que ocurran a la vez 
        ParallelTransition entrada = new ParallelTransition(entradaJugador, entradaRival);

        //pausa gif entrenador rival
        //le damos 1.5 segundos para que termine el gesto
        PauseTransition pausaGif = new PauseTransition(Duration.seconds(1.5));

        //SALIDA
        //el jugador se va hacia atrás por la izquierda (-400 px)
        TranslateTransition salidaJugador = new TranslateTransition(Duration.seconds(1), imgEntrenadorJugador);
        salidaJugador.setToX(-400); 

        //el rival se va hacia atrás por la derecha (+400 px)
        TranslateTransition salidaRival = new TranslateTransition(Duration.seconds(1), imgEntrenadorRival);
        salidaRival.setToX(400);

        ParallelTransition salida = new ParallelTransition(salidaJugador, salidaRival);

        //terminan de salir por pantalla
        salida.setOnFinished(evento -> {
            //escondemos a los entrenadores
            imgEntrenadorJugador.setVisible(false);
            imgEntrenadorRival.setVisible(false);

            //hacemos a los pokemon enanos antes de sacarlos (efecto pokeball)
            imgPokemonJugador.setScaleX(0);
            imgPokemonJugador.setScaleY(0);
            imgPokemonRival.setScaleX(0);
            imgPokemonRival.setScaleY(0);

            //ahora los hacemos visibles 
            imgPokemonJugador.setVisible(true);
            imgPokemonRival.setVisible(true);

            //animacion como salir de pokeball
            ScaleTransition scaleJugador = new ScaleTransition(Duration.seconds(0.5), imgPokemonJugador);
            scaleJugador.setToX(1);
            scaleJugador.setToY(1);

            ScaleTransition scaleRival = new ScaleTransition(Duration.seconds(0.5), imgPokemonRival);
            scaleRival.setToX(1);
            scaleRival.setToY(1);

            //agrupamos para que ambos crezcan a la vez
            ParallelTransition aparicionPokemons = new ParallelTransition(scaleJugador, scaleRival);

            // Cuando terminan lanzamos los textos a la cola
            aparicionPokemons.setOnFinished(eventoPokemons -> {
                escribirLog("¡Un combate aleatorio ha comenzado!");
                escribirLog("¡Nos enfrentamos a MisCojones no podemos fallar!"); // XD
                
                if (combateActual.getPokemonActualRival() != null) {
                    escribirLog("¡El rival saca a " + combateActual.getPokemonActualRival().getNombrePokemon() + "!");
                } else {
                    escribirLog("ERROR: ¡El rival no tiene Pokémon en su equipo!");
                }
                
                if (combateActual.getPokemonActualJugador() != null) {
                    escribirLog("¡Ve, " + combateActual.getPokemonActualJugador().getNombrePokemon() + "!");
                } else {
                    escribirLog("ERROR: ¡No tienes Pokémon sanos en tu equipo!");
                }
            });

            //arrancamos la animacion de crecimiento de pokeball
            aparicionPokemons.play();
        });

        //juntarlo todo (Entrada -> Pausa -> Salida)
        SequentialTransition animacionCompleta = new SequentialTransition(entrada, pausaGif, salida);
        animacionCompleta.play(); 
    }
}