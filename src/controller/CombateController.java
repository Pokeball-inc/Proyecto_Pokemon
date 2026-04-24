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
import dao.PokemonDAO;
import model.Combate;
import model.Entrenador;
import model.Estados;
import model.Movimiento;
import model.Pokemon;
import model.Sesion;
import model.Turno;

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
    
    //menu de cambio de pokemon
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
    private ImageView btnVolverCambio; //boton para cancelar el cambio

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
     * inicializa la pantalla de combate. 
     * */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //instanciar el combate
        combateActual = new Combate();
        
        //cargamos el jugador
        jugador = Sesion.entrenadorLogueado;
        
        //creamos el rival aleatorio 
        rival = generarRivalAleatorio();
        
        //empezamos el combate 
        
        combateActual.empezarCombate(jugador, rival);
        
        //arrancamos la animacion
        animacionEntrada(); 
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
            
            String rutaJ = Sesion.vista2D ? pJugador.getImgPosteriorPokemon() : pJugador.getImgPosteriorPokemon3D();
            if (rutaJ != null) {
                File fileJ = new File(rutaJ);
                if (fileJ.exists()) {
                    imgPokemonJugador.setImage(new Image(fileJ.toURI().toString()));
                }
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
            
            String rutaR = Sesion.vista2D ? pRival.getImgFrontalPokemon() : pRival.getImgFrontalPokemon3D();
            if (rutaR != null) {
                File fileR = new File(rutaR);
                if (fileR.exists()) {
                    imgPokemonRival.setImage(new Image(fileR.toURI().toString()));
                }
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
        
        //ocultamos menus para que no pueda pulsar nada mas
        panelMenuAtaques.setVisible(false);
        panelMenuPrincipal.setVisible(true);
        btnSalirMenuAtaque.setVisible(false);
        
        Pokemon pJugador = combateActual.getPokemonActualJugador();
        Pokemon pRival = combateActual.getPokemonActualRival();
        
        //el jugador descansa
        pJugador.descansar();
        escribirLog("¡Tu " + pJugador.getNombrePokemon() + " se echa una siesta y recupera vitalidad!");
        
        //pasa turno y ataca el rival
        Movimiento movRival = elegirAtaqueRival();
        escribirLog("¡El " + pRival.getNombrePokemon() + " rival aprovecha y usa " + movRival.getNombreMovimiento() + "!");
        
        //usamos el metodo atacar para calcular y restar el daño
        pRival.atacar(movRival, pJugador);
        
        //comprobamos si el rival nos ha debilitado 
        verificarMuertesYContinuar();
        
        //pasamos de turno y refrescamos las barras de vida de la pantalla
        numeroTurno++;
        actualizarVista();
    }
    
    @FXML
    public void accionCambiarPokemon(MouseEvent event) {
        if (controlesBloqueados == true) {
            return;
        }
        // abrimos el menu de cambio  sin ser obligatorio el mismo
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
    	System.out.println("¡Hice clic en el Pkmn 1!");
    		 realizarCambio(0); }

    @FXML
    public void clicPkmn2(MouseEvent event) {
    	System.out.println("¡Hice clic en el Pkmn 1!");
    		realizarCambio(1); }

    @FXML
    public void clicPkmn3(MouseEvent event) { 
    		realizarCambio(2); }

    @FXML
    public void clicPkmn4(MouseEvent event) { 
    		realizarCambio(3); }

    @FXML
    public void clicPkmn5(MouseEvent event) { 
    		realizarCambio(4); }

    @FXML
    public void clicPkmn6(MouseEvent event) { 
    		realizarCambio(5); }
    
    //metodo central que ejecuta el cambio en la logica
    private void realizarCambio(int indiceEquipo) {
        Pokemon antiguoPokemon = combateActual.getPokemonActualJugador();
        
        //intentamos hacer el cambio en el modelo pasandole el numero del hueco
        boolean cambioExitoso = combateActual.cambiarPokemonJugador(indiceEquipo);
        
        //si la logica rechaza el cambio abortamos
        if (cambioExitoso == false) {
            return;
        }
        
        //recuperamos al nuevo pokemon para usar su nombre en los textos
        Pokemon nuevoPokemon = combateActual.getPokemonActualJugador();
        
        //si el pokemon anterior seguia vivo avisamos de que lo retiramos
        if (antiguoPokemon.getVitalidad() > 0) {
            escribirLog("¡Vuelve, " + antiguoPokemon.getNombrePokemon() + "!");
        }
        
        escribirLog("¡Adelante, " + nuevoPokemon.getNombrePokemon() + "!");
        
        //ocultamos el panel de cambio y volvemos al combate
        panelCambioPokemon.setVisible(false);
        panelMenuPrincipal.setVisible(true);
        
        //si el boton de volver era visible significa que el cambio fue manual (gastamos el turno)
        if (btnVolverCambio.isVisible() == true) {
            Movimiento movRival = elegirAtaqueRival();
            escribirLog("¡El " + combateActual.getPokemonActualRival().getNombrePokemon() + " rival aprovecha el cambio y usa " + movRival.getNombreMovimiento() + "!");
            
            //el rival nos ataca al nuevo pokemon que acaba de salir
            combateActual.getPokemonActualRival().atacar(movRival, nuevoPokemon);
            
            //comprobamos si nos lo ha matado de un solo golpe al salir
            verificarMuertesYContinuar();
        }
        
        //actualizamos graficos
        actualizarVista();
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
        btnSalirMenuAtaque.setVisible(false);
        
        Pokemon pJugador = combateActual.getPokemonActualJugador();
        
        //le restamos 1 al numero que le pasamos para coger el ataque del array 
        Movimiento movJugador = pJugador.getMovimientos()[indiceAtaque - 1];

        //validacion extra de seguridad por si el hueco del ataque esta vacio
        if (movJugador == null) {
            return; 
        }

        //la ia elige su ataque aleatorio
        Movimiento movRival = elegirAtaqueRival();

        // calculamos velocidad, estados, daño y llenaráa la lista de turnos
        combateActual.procesarTurno(movJugador, movRival);

        // leemos lo que ha pasado del historial 
        Turno ultimo = combateActual.obtenerUltimoTurno();
        if (ultimo != null) {
            // añadimos a la cola de mensajes animados
            escribirLog(ultimo.getAccionEntrenador());
            escribirLog(ultimo.getAccionEntrenadorRival());
        }

        //comprobamos si alguien ha caido debilitado tras el cruce
        verificarMuertesYContinuar();

        //aumentar el contador de turnos y actualizar la pantalla visual
        numeroTurno++;
        actualizarVista();
    }
    
  //metodo para generar un rival aleatorio conectando con la bd
    private Entrenador generarRivalAleatorio() {
        Entrenador rivalGenerado = new Entrenador();
        Random r = new Random();

        //generar nombre aleatorio con la lista epica
        String[] nombresPosibles = {
            "Cazabichos Paco", "Joven Chano", "Campista Ana", "Mister Pro",
            "Perico Palotes", "Pichote", "Manolo Lama", "LuisReApruebameXfa",
            "Paco el del Bar", "Cuñao Experto", "ElBicho SIUUU", "GigaChad",
            "Dominguero Furioso", "Becario Explotado", "Señora de los Gatos",
            "Otaku Sudoroso", "El Xokas", "Vegetta777", "Ibai Llanos",
            "Juan Cuesta", "Amador Rivas", "Cani de Poligono", "Abuela con Chancla"
        };
        String nombreElegido = nombresPosibles[r.nextInt(nombresPosibles.length)];
        rivalGenerado.setNombreEntrenador(nombreElegido);

        //cargar imagen aleatoria desde la carpeta
        try {
            //ruta
            File carpeta = new File("imgs/Combate/Entrenadores/Aleatorio"); 
            
            //leemos todos los archivos de la carpeta 
            File[] archivos = carpeta.listFiles();

            if (archivos != null && archivos.length > 0) {
                //elegimos un archivo al azar de la lista
                File imagenElegida = archivos[r.nextInt(archivos.length)];
                
                //ponemos la imagen directamente en el imageview 
                imgEntrenadorRival.setImage(new Image(imagenElegida.toURI().toString()));
            } else {
                System.out.println("ERROR: no se han encontrado imagenes en la carpeta del rival.");
            }
        } catch (Exception e) {
            System.out.println("ERROR al cargar la carpeta de imagenes: " + e.getMessage());
        }

        //calcular el nivel mas alto de nuestro equipo para balancear al rival
        int nivelBaseJugador = 5; //nivel por defecto si hay algun error
        if (jugador != null && jugador.getEquipoPokemon() != null) {
            for (Pokemon miPokemon : jugador.getEquipoPokemon()) {
                //comprobamos que el hueco no este vacio y buscamos el nivel mayor
                if (miPokemon != null && miPokemon.getNivel() > nivelBaseJugador) {
                    nivelBaseJugador = miPokemon.getNivel();
                }
            }
        }

        //abrimos conexion a la bd para sacar los datos 
        Connection con = null;
        dao.MovimientoDAO movDAO = null;
        try {
            bd.ConexionBBDD conector = new bd.ConexionBBDD();
            con = conector.getConexion();
            //instanciamos tu movimientodao que necesita la conexion por parametro
            movDAO = new dao.MovimientoDAO(con);
        } catch (Exception e) {
            System.out.println("ERROR al conectar con la bd: " + e.getMessage());
        }

        //generar equipo pokemon conectando a la base de datos
        Pokemon[] equipoRival = new Pokemon[6];

        for (int i = 0; i < 6; i++) {
            Pokemon p = null;
            
            if (con != null) {
                try {
                    //generamos un id aleatorio 
                    int idAleatorioPkmn = r.nextInt(251) + 1;
                    
                    //sacamos el pokemon por su id usando el nuevo metodo de la pokedex
                    p = dao.PokemonDAO.obtenerPokemonDesdePokedex(con, idAleatorioPkmn);
                } catch (Exception e) {
                    System.out.println("error sacando pokemon de la bd " + e.getMessage());
                }
            }
            
            //si falla la bd o devuelve null creamos uno de repuesto para que no explote
            if (p == null) {
                p = new Pokemon();
                p.setNombrePokemon("Rata Aleatoria " + (i + 1));
            }
            
            //le ponemos el nivel balanceado
            p.setNivel(nivelBaseJugador + r.nextInt(6)); 
            
            //probabilidad del 5% de que el pokemon del rival sea shiny 
            if (r.nextInt(100) < 5) {
                p.setEsShiny(true);
            } else {
                p.setEsShiny(false);
            }
            
            //llamamos al metodo para generar estadisticas aleatorias
            generarEstadisticasAleatorias(p);

            //generar 4 movimientos aleatorios desde la bd
            Movimiento[] movimientos = new Movimiento[4];
            for (int j = 0; j < 4; j++) {
                Movimiento mov = null;
                
                if (movDAO != null) {
                    try {
                        //respetamos el limite de 251 para los movimientos de tu compañero
                        int idAleatorioMov = r.nextInt(251) + 1;
                        
                        //sacamos el movimiento por su id usando tu metodo buscarporid
                        mov = movDAO.buscarPorId(idAleatorioMov);
                    } catch (Exception e) {
                        System.out.println("ERROR sacando movimiento de la bd");
                    }
                }
                
                //repuesto si falla la consulta
                if (mov == null) {
                    mov = new Movimiento();
                    mov.setNombreMovimiento("Placaje Letal");
                }
                
                movimientos[j] = mov;
            }
            p.setMovimientos(movimientos);

            //guardamos el pokemon en el hueco del equipo
            equipoRival[i] = p;
        }

        //le damos el equipo al rival
        rivalGenerado.setEquipoPokemon(equipoRival);

        return rivalGenerado;
    }

    //metodo auxiliar para generar estadisticas aleatorias escaladas por nivel
    private void generarEstadisticasAleatorias(Pokemon p) {
        Random r = new Random();
        int nivel = p.getNivel();
        
        //formula estadistica base mas extra aleatorio escalado con el nivel
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
        	pJugador.setVitalidad(0);
        	pJugador.setEstadoActual(Estados.DEBILITADO);
            escribirLog("¡Tu " + pJugador.getNombrePokemon() + " se ha debilitado!");
            
            registrarEventoEspecial("debilitado1", "El jugador ha perdido a " + pJugador.getNombrePokemon());
            
            //comprobamos si quedan vivos en el equipo
            if (combateActual.puedeContinuar(true)) {
                escribirLog("¡Te toca elegir a tu siguiente Pokémon!");
                abrirMenuCambio(true);
            } else {
                //si no quedan perdemos
                combateActual.finalizarCombate();
                escribirLog("¡Te has quedado sin Pokémon! Has perdido...");
                
                registrarEventoEspecial("finPierdeCombate", "El entrenador ha perdido el combate");
                //volvemos al menu despues de 4s
                volverAlMenuPrincipal(4);
            }
        }

        //comprobar si el rival ha muerto (ko)
        if (pRival.getVitalidad() <= 0) {
        	pRival.setVitalidad(0);
        	pRival.setEstadoActual(Estados.DEBILITADO);
            escribirLog("¡El " + pRival.getNombrePokemon() + " enemigo se ha debilitado!");
            
            registrarEventoEspecial("debilitado2", "El rival ha perdido a " + pRival.getNombrePokemon());
            // ---------------------------
            
            //si muere, el rival saca otro (el metodo puedecontinuar ya suma el ko)
            if (combateActual.puedeContinuar(false)) {
                escribirLog("¡El rival ha enviado a " + combateActual.getPokemonActualRival().getNombrePokemon() + "!");
            } else {
                //si no le quedan ganamos (6 kos), finalizamos
                combateActual.finalizarCombate();
                escribirLog("¡Has ganado el combate! No le quedan Pokémon al rival.");
                
                registrarEventoEspecial("finGanaCombate", "El entrenador ha ganado el combate");
                //volvemos al menu despues de 4s
                volverAlMenuPrincipal(4);
            }
        }
        actualizarVista();
    }
    
    /**
     *  metodo para registrar eventos que no son ataques (muertes, fin de combate) en el historial
     */
    private void registrarEventoEspecial(String tipoEvento, String descripcion) {
        Turno evento = new Turno();
        evento.setNumeroTurnoActual(combateActual.getHistorialTurnos().size() + 1);
        
        // identificamos el evento en la accion para el Log posterior
        evento.setAccionEntrenador("[" + tipoEvento.toUpperCase() + "] " + descripcion);
        evento.setAccionEntrenadorRival(""); 

        // guardamos el estado vital actual para el log - OK KO
        evento.setEstadoPokemon1(combateActual.getPokemonActualJugador().getVitalidad() > 0 ? "OK" : "KO");
        evento.setEstadoPokemon2(combateActual.getPokemonActualRival().getVitalidad() > 0 ? "OK" : "KO");

        combateActual.añadirTurno(evento);
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
    
  //boton de salir
    @FXML
    public void combateSalir(MouseEvent event) {
        if (controlesBloqueados == true) {
            return;
        }

        //bloqueamos los controles para que no haga clics compulsivos mientras huye
        controlesBloqueados = true;

        //ocultamos menus por si acaso
        panelMenuAtaques.setVisible(false);
        panelMenuPrincipal.setVisible(true);
        btnSalirMenuAtaque.setVisible(false);

        try {
            //si el combate sigue en curso y el jugador sale, cuenta como retirada
            if (combateActual != null && combateActual.getPokemonKOEntrenador() < 6 && combateActual.getPokemonKORival() < 6) {
                combateActual.retirarse(); //pierde 1/3 de su dinero
            }

            //mandamos el mensaje a la pantalla
            escribirLog("¡Has huido del combate! Pierdes algo de dinero por el panico...");

            //guardamos la referencia a la ventana actual antes de la pausa para luego continuar en el mismo sitio despues de la pausa
            Stage ventanaActual = (Stage) ((Node) event.getSource()).getScene().getWindow();

            //creamos una pausa de 3 segundos para que de tiempo a leer el texto de huida
            PauseTransition pausaHuida = new PauseTransition(Duration.seconds(3));
            pausaHuida.setOnFinished(e -> {
                try {
                    System.out.println("cargando la vista principal tras huir...");

                    //cargar la vista principal 
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/principal/vistaPrincipal.fxml"));
                    Parent root = loader.load();
                    Scene scene = new Scene(root);

                    //cargar el css
                    String css = this.getClass().getResource("/view/principal/vistaPrincipal.css").toExternalForm();
                    scene.getStylesheets().add(css);

                    //titulo, forzar el tamaño de la ventana y bloquear cambio manual
                    ventanaActual.setTitle("PokeINC - Principal");
                    ventanaActual.setResizable(false);

                    //cargar icono
                    File file = new File("imgs/Login/Login-icon.png");
                    if (file.exists()) {
                        String imagePath = file.toURI().toString();
                        ventanaActual.getIcons().add(new Image(imagePath));
                    }

                    //cambiar la escena 
                    ventanaActual.setScene(scene);
                    ventanaActual.show();

                } catch (Exception ex) {
                    System.out.println("error al cambiar de ventana tras huir: " + ex.getMessage());
                    ex.printStackTrace();
                }
            });
            
            //arrancamos el temporizador
            pausaHuida.play();

        } catch (Exception e) {
            System.out.println("error en la retirada: " + e.getMessage());
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
                escribirLog("¡Nos enfrentamos a " + rival.getNombreEntrenador() + " no podemos fallar!"); // ahora no es Misco
                
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
    
    //metodo para volver a la pantalla de seleccion 
    private void volverAlMenuPrincipal(int segundosPausa) {
        controlesBloqueados = true;
        
        //usamos el panel principal para encontrar la ventana
        Stage ventanaActual = (Stage) panelMenuPrincipal.getScene().getWindow();

        PauseTransition pausaSalida = new PauseTransition(Duration.seconds(segundosPausa));
        pausaSalida.setOnFinished(e -> {
            try {
         
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/SeleccionCombate.fxml"));
                Parent root = loader.load();
                Scene scene = new Scene(root);

                //cargamos su css 
                String css = this.getClass().getResource("/view/SeleccionCombate.css").toExternalForm();
                scene.getStylesheets().add(css);

                ventanaActual.setTitle("PokeINC - Seleccion de Combate");
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
    
    /**
     * abre el menu para elegir a un nuevo pokemon.
     * @param obligatorio true si nuestro pokemon ha muerto y no podemos cancelar el cambio
     */
    private void abrirMenuCambio(boolean obligatorio) {
        //ocultamos los otros menus y mostramos este
        panelMenuPrincipal.setVisible(false);
        panelMenuAtaques.setVisible(false);
        panelCambioPokemon.setVisible(true);
        
        //si es obligatorio cambiar porque nos han matado ocultamos el boton de volver
        if (obligatorio == true) {
            btnVolverCambio.setVisible(false);
        } else {
            btnVolverCambio.setVisible(true);
        }
        
        //metemos los botones en un array para recorrerlos facil
        Button[] botonesEquipo = {btnPokemon1, btnPokemon2, btnPokemon3, btnPokemon4, btnPokemon5, btnPokemon6};
        Pokemon[] miEquipo = jugador.getEquipoPokemon();
        
        //recorremos los 6 huecos
        for (int i = 0; i < 6; i++) {
            if (miEquipo[i] != null) {
                Pokemon p = miEquipo[i];
                
                //creamos la imagen en pequeñito
                String rutaImagen = p.getImgFrontalPokemon();
                if (rutaImagen != null) {
                    File imgFile = new File(rutaImagen);
                    if (imgFile.exists()) {
                        ImageView icono = new ImageView(new Image(imgFile.toURI().toString()));
                        //forzamos el tamaño para que sea un icono
                        icono.setFitWidth(40); 
                        icono.setFitHeight(40);
                        icono.setPreserveRatio(true);
                        
                        //le ponemos la imagen al boton
                        botonesEquipo[i].setGraphic(icono);
                    }
                }
                
                //escribimos el nombre y la vida separados por un salto de linea (\n)
                botonesEquipo[i].setText(p.getNombrePokemon() + "\nPS: " + p.getVitalidad() + " / " + p.getVitalidadMaxima());
                botonesEquipo[i].setVisible(true);
                
                //si esta debilitado o ya esta luchando deshabilitamos el boton
                if (p.getVitalidad() <= 0 || p == combateActual.getPokemonActualJugador()) {
                    botonesEquipo[i].setDisable(true);
                } else {
                    botonesEquipo[i].setDisable(false);
                }
            } else {
                //si el hueco del equipo esta vacio ocultamos el boton
                botonesEquipo[i].setVisible(false);
            }
        }
    }
}