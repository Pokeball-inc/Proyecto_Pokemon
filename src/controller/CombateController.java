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
    private ImageView btnHuir; //o rendirse

    //Historial del Combate
    @FXML 
    private Label txtLogCombate; //ventana de texto para narrar los turnos

    //Variables

    private Combate combateActual; 
    private Entrenador jugador;
    private Entrenador rival;
    
    private int numeroTurno = 1; //contador de turnos
    
    //variables para la animacion del texto
    private Queue<String> colaMensajes = new LinkedList<>();
    private boolean escribiendoMensaje = false;
    
    private boolean controlesBloqueados = true; //bloquear botones 
    

    /**
     * Inicializa la pantalla de combate. 
     * Aquí cargas los datos del jugador, generas al rival y llamas a actualizarVista().
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //instanciar el combate
        combateActual = new Combate();
        
        animacionEntrada(); //animacion inicial 
     
    }

    /**
     * Refresca todos los textos, imágenes y barras de vida/estamina de la pantalla
     * leyendo los datos actuales del modelo.
     */
    private void actualizarVista() {
        Pokemon pJugador = combateActual.getPokemonActualJugador();
        Pokemon pRival = combateActual.getPokemonActualRival();

        //actualizar pantalla del jugador
        if (pJugador != null) {
            txtNombrePkmnJugador.setText(pJugador.getNombrePokemon());
            txtNivelPkmnJugador.setText("Lv" + pJugador.getNivel());
            txtVitalidadPkmnJugador.setText(pJugador.getVitalidad() + " / " + pJugador.getVitalidadMaxima());

            double porcentajeVidaJugador = (double) pJugador.getVitalidad() / pJugador.getVitalidadMaxima();
            if (porcentajeVidaJugador < 0) {
                porcentajeVidaJugador = 0; 
            }
            
            barraVitalidadPkmnJugador.setProgress(porcentajeVidaJugador);
            cambiarColorBarra(barraVitalidadPkmnJugador, porcentajeVidaJugador);

            Movimiento[] ataques = pJugador.getMovimientos();
            
            if (ataques[0] != null) {
                btnAtaque1.setText(ataques[0].getNombreMovimiento());
            } else {
                btnAtaque1.setText("-");
            }
            
            if (ataques[1] != null) {
                btnAtaque2.setText(ataques[1].getNombreMovimiento());
            } else {
                btnAtaque2.setText("-");
            }
            
            if (ataques[2] != null) {
                btnAtaque3.setText(ataques[2].getNombreMovimiento());
            } else {
                btnAtaque3.setText("-");
            }
            
            if (ataques[3] != null) {
                btnAtaque4.setText(ataques[3].getNombreMovimiento());
            } else {
                btnAtaque4.setText("-");
            }
        }

        //actualizar pantalla del rival
        if (pRival != null) {
            txtNombrePkmnRival.setText(pRival.getNombrePokemon());
            txtNivelPkmnRival.setText("Lv" + pRival.getNivel());
            txtVitalidadPkmnRival.setText(pRival.getVitalidad() + " / " + pRival.getVitalidadMaxima());

            double porcentajeVidaRival = (double) pRival.getVitalidad() / pRival.getVitalidadMaxima();
            if (porcentajeVidaRival < 0) {
                porcentajeVidaRival = 0;
            }
            
            barraVitalidadPkmnRival.setProgress(porcentajeVidaRival);
            cambiarColorBarra(barraVitalidadPkmnRival, porcentajeVidaRival);
        }
    }

    /**
     * metodo de apoyo para cambiar el color de la barra de vida estilo pokemon.
     */
    private void cambiarColorBarra(ProgressBar barra, double porcentaje) {
        //quitamos los colores anteriores para que no se mezclen
        barra.getStyleClass().removeAll("barra-verde", "barra-amarilla", "barra-roja");

        if (porcentaje > 0.5) {
            barra.setStyle("-fx-accent: #32cd32;"); //verde
        } else if (porcentaje > 0.2) {
            barra.setStyle("-fx-accent: #ffd700;"); //amarillo
        } else {
            barra.setStyle("-fx-accent: #ff4500;"); //rojo
        }
    }

   //Cambiar tamaño de botones (gracias Elyass XDDD)
    
    // --------------- INCREMENTAR Y DISMINUIR TAMAÑO DEL BOTON ENTRENAR AL PASAR EL CURSOR ---------------
    
    //BOTON LUCHAR
    @FXML
    private void aumentarTamañoBotonLuchar(MouseEvent event) {
        if (controlesBloqueados == true) {
            return;
        }
        if (btnLuchar != null) {
        	btnLuchar.setScaleX(btnLuchar.getScaleX() + 0.2);
        	btnLuchar.setScaleY(btnLuchar.getScaleY() + 0.2);
        }
    }

    @FXML
    private void disminuirTamañoBotonLuchar(MouseEvent event) {
        if (controlesBloqueados == true) {
            return;
        }
        if (btnLuchar != null) {
        	btnLuchar.setScaleX(btnLuchar.getScaleX() - 0.2);
        	btnLuchar.setScaleY(btnLuchar.getScaleY() - 0.2);
        }
    }
    
    //BOTON CAMBIAR POKEMON
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
        if (controlesBloqueados == true) {
            return;
        }
        if (btnCambiarPokemon != null) {
        	btnCambiarPokemon.setScaleX(btnCambiarPokemon.getScaleX() - 0.2);
        	btnCambiarPokemon.setScaleY(btnCambiarPokemon.getScaleY() - 0.2);
        }
    }
    
    //BOTON DESCANSAR
    @FXML
    private void aumentarTamañoBotonDescansar(MouseEvent event) {
        if (controlesBloqueados == true) {
            return;
        }
        if (btnDescanso != null) {
        	btnDescanso.setScaleX(btnDescanso.getScaleX() + 0.2);
        	btnDescanso.setScaleY(btnDescanso.getScaleY() + 0.2);
        }
    }

    @FXML
    private void disminuirTamañoBotonDescansar(MouseEvent event) {
        if (controlesBloqueados == true) {
            return;
        }
        if (btnDescanso != null) {
        	btnDescanso.setScaleX(btnDescanso.getScaleX() - 0.2);
        	btnDescanso.setScaleY(btnDescanso.getScaleY() - 0.2);
        }
    }
    
    //BOTON SALIR
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
        if (controlesBloqueados == true) {
            return;
        }
        if (btnHuir != null) {
        	btnHuir.setScaleX(btnHuir.getScaleX() - 0.2);
        	btnHuir.setScaleY(btnHuir.getScaleY() - 0.2);
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
        //esconder menu mostrar ataques
        panelMenuPrincipal.setVisible(false);
        panelMenuAtaques.setVisible(true);
        btnSalirMenuAtaque.setVisible(true);
    }
    
    @FXML
    public void clicVolverMenu(MouseEvent event) {
        if (controlesBloqueados == true) {
            return;
        }
        //ocultamos los ataques y mostramos el menu principal
        panelMenuAtaques.setVisible(false);
        panelMenuPrincipal.setVisible(true);
        btnSalirMenuAtaque.setVisible(false);
    }

    @FXML
    public void accionDescansar(MouseEvent event) {
        if (controlesBloqueados == true) {
            return;
        }
        //recuperar estamina y saltar turno (todo: adaptar a logica de turnos nueva)
        escribirLog("¡Tu " + combateActual.getPokemonActualJugador().getNombrePokemon() + " decide descansar para recuperar estamina!");
    }

    // ================= LÓGICA CENTRAL DEL COMBATE =================

    /**
     * el corazon del combate. recibe el ataque elegido por el jugador,
     * manda los ataques al modelo de combate y lee los resultados para la vista.
     * @param indiceAtaque el numero del boton pulsado (del 1 al 4)
     */
    private void ejecutarTurno(int indiceAtaque) {
        //volvemos a ocultar el panel de ataques automaticamente al elegir uno
        panelMenuAtaques.setVisible(false);
        panelMenuPrincipal.setVisible(true);
        
        Pokemon pJugador = combateActual.getPokemonActualJugador();
        
        //le restamos 1 al numero que le pasamos para coger el ataque del array 
        Movimiento movJugador = pJugador.getMovimientos()[indiceAtaque - 1];

        //validacion extra de seguridad por si el hueco del ataque esta vacio
        if (movJugador == null) {
            return; 
        }

        //la ia elige su ataque aleatorio
        Movimiento movRival = elegirAtaqueRival();

        //le pasamos los dos ataques a la logica del modelo
        combateActual.procesarTurno(movJugador, movRival);

        //leemos el historial generado para narrarlo en pantalla
        model.Turno ultimoTurno = combateActual.obtenerUltimoTurno();
        
        //escribimos lo que paso sin lanzar strings en blanco
        if (ultimoTurno != null) {
            if (!ultimoTurno.getAccionEntrenador().isEmpty()) {
                escribirLog(ultimoTurno.getAccionEntrenador());
            }
            if (!ultimoTurno.getAccionEntrenadorRival().isEmpty()) {
                escribirLog(ultimoTurno.getAccionEntrenadorRival());
            }
        }

        //comprobamos si alguien ha caido debilitado tras el cruce
        verificarMuertesYContinuar();

        //aumentar el contador de turnos y actualizar la pantalla visual
        numeroTurno++;
        actualizarVista();
    }

    /**
     * inteligencia artificial muy basica para que el rival elija ataque.
     * @return el movimiento elegido aleatoriamente
     */
    private Movimiento elegirAtaqueRival() {
        Pokemon pRival = combateActual.getPokemonActualRival();
        Random r = new Random();
        int ataqueElegido = r.nextInt(4);
        Movimiento mov = pRival.getMovimientos()[ataqueElegido];

        //si elige un hueco vacio, busca el primero que tenga disponible
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
     * revisa si algun pokemon ha muerto en este turno y gestiona los cambios.
     */
    private void verificarMuertesYContinuar() {
        Pokemon pJugador = combateActual.getPokemonActualJugador();
        Pokemon pRival = combateActual.getPokemonActualRival();

        //comprobar si el jugador ha muerto tras el ataque
        if (pJugador.getVitalidad() <= 0) {
            escribirLog("¡Tu " + pJugador.getNombrePokemon() + " se ha debilitado!");
            
            //comprobamos si quedan vivos en el equipo
            if (combateActual.puedeContinuar(true)) {
                //todo: abrir ventana para cambiar (bloquearemos el combate hasta que cambie)
                escribirLog("¡Te toca elegir a tu siguiente Pokémon!");
            } else {
                //si no quedan perdemos
                combateActual.finalizarCombate();
                escribirLog("¡Te has quedado sin Pokémon! Has perdido...");
            }
        }

        //comprobar si el rival ha muerto (ko)
        if (pRival.getVitalidad() <= 0) {
            escribirLog("¡El " + pRival.getNombrePokemon() + " enemigo se ha debilitado!");
            
            //si muere, el rival saca otro (el metodo puedecontinuar ya suma el ko)
            if (combateActual.puedeContinuar(false)) {
                escribirLog("¡El rival ha enviado a " + combateActual.getPokemonActualRival().getNombrePokemon() + "!");
            } else {
                //si no le quedan ganamos (6 kos), finalizamos
                combateActual.finalizarCombate();
                escribirLog("¡Has ganado el combate! No le quedan Pokémon al rival.");
            }
        }
    }

    /**
     * Añade un mensaje a la cola de texto para que aparezca animado.
     */
    private void escribirLog(String mensaje) {
        //lo ponemos a la cola
        colaMensajes.add(mensaje);
        //le decimos a la maquina que empiece a procesar la cola
        procesarColaMensajes();
    }

    /**
     * Máquina de escribir: Coge el primer mensaje de la cola, limpia la pantalla 
     * y lo escribe letra a letra.
     */
    private void procesarColaMensajes() {
        //si ya hay un mensaje escribiendose en pantalla, paramos.
        if (escribiendoMensaje == true) {
            return;
        }
        
        //si la fila de mensajes esta vacia la animacion ha acabado y devolvemos el control
        if (colaMensajes.isEmpty()) {
            controlesBloqueados = false; 
            return;
        }

        //si hay mensajes bloqueamos los controles y empezamos a escribir
        controlesBloqueados = true; 
        escribiendoMensaje = true; 
        String mensajeActual = colaMensajes.poll(); 

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
        if (controlesBloqueados == true) {
            return;
        }
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

            //cuando terminan lanzamos los textos a la cola
            aparicionPokemons.setOnFinished(eventoPokemons -> {
                escribirLog("¡Un combate aleatorio ha comenzado!");
                escribirLog("¡Nos enfrentamos a MisCojones no podemos fallar!"); //xd
                
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
                
                //al actualizar la vista aqui las barras de vida apareceran con el color correcto
                actualizarVista();
            });

            //arrancamos la animacion de crecimiento de pokeball
            aparicionPokemons.play();
        });

        //juntarlo todo(Entrada -> Pausa -> Salida)
        SequentialTransition animacionCompleta = new SequentialTransition(entrada, pausaGif, salida);
        animacionCompleta.play(); 
    }
}