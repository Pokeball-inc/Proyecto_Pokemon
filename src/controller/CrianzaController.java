package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ButtonBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.control.DialogPane;

import java.io.File;
import java.net.URL;
import java.sql.Connection;
import java.util.*;

import bd.ConexionBBDD;
import dao.CapturaDao;
import dao.PokemonDAO;
import model.*;

/**
 * Clase CrianzaController
 * Controlador encargado de gestionar la logica y la vista del sistema de crianza.
 * Permite seleccionar dos Pokemon (Macho y Hembra) para tener una cria, 
 * gestionando la herencia de estadisticas, tipos y la eclosion del huevo.
 */
public class CrianzaController implements Initializable {

    //Elementos vista
    
    @FXML 
    private ImageView fondoPokemon;
    
    @FXML 
    private ImageView imgMacho;
    
    @FXML 
    private ImageView imgHembra;
    
    @FXML
    private ImageView imgHuevo;
    
    @FXML 
    private ImageView imgBebe; //Añadido para mostrar al hijo al eclosionar
    
    @FXML 
    private Text txtNombreMacho;
    
    @FXML 
    private Text txtNombreHembra;
    
    @FXML 
    private Text txtFertilidadMacho;
    
    @FXML 
    private Text txtFertilidadHembra;
    
    //Elementos de la Caja PC 
    
    @FXML 
    private javafx.scene.layout.Pane panelSeleccion;
    
    @FXML 
    private javafx.scene.layout.TilePane contenedorPokemons;
    
    @FXML 
    private Text txtTituloSeleccion;
    
    @FXML
    private ImageView btnCerrarSeleccion;
    
    //Botones
    @FXML
    private ImageView botonCambiarMacho;
    
    @FXML
    private ImageView botonCambiarHembra;
    
    @FXML
    private ImageView botonCriar;
    
    @FXML
    private ImageView botonSalir;

    //Variables
    private Entrenador entrenadorActual = Sesion.entrenadorLogueado;
    private Connection con;
    private CapturaDao capturaDao = new CapturaDao(); //Reutilizamos el DAO de captura para guardar al bebé
    
    //Listas para separar a los Pokémon aptos para criar
    private List<Pokemon> machosDisponibles = new ArrayList<>();
    private List<Pokemon> hembrasDisponibles = new ArrayList<>();
    
    //Los padres seleccionados actualmente
    private Pokemon machoElegido;
    private Pokemon hembraElegida;
    private Random rand = new Random();
    
    //Variable para saber si estamos eligiendo al padre o a la madre
    private boolean seleccionandoMacho = true; 

    /**
     * Metodo que prepara la conexion a la base de datos, carga los candidatos a padres
     * y muestra los primeros Pokemon disponibles en pantalla de forma predeterminada.
     */
    public void setEntrenador() {

        // Conectar a BD
        ConexionBBDD conector = new ConexionBBDD();
        this.con = conector.getConexion();

        //Ocultar al bebe al principio
        if (imgBebe != null) {
            imgBebe.setVisible(false);
        }
        
        //Buscar candidatos para criar
        cargarCandidatos();
        
        //Mostrar los primeros candidatos por defecto al entrar a la pantalla 
        if (!machosDisponibles.isEmpty()) {
            machoElegido = machosDisponibles.get(rand.nextInt(machosDisponibles.size()));
            
            //Comprobamos mote del macho
            String nombreMacho;
            if (machoElegido.getMotePokemon() != null && !machoElegido.getMotePokemon().trim().isEmpty()) {
                nombreMacho = machoElegido.getMotePokemon();
            } else {
                nombreMacho = machoElegido.getNombrePokemon();
            }
            
            txtNombreMacho.setText(nombreMacho);
            txtFertilidadMacho.setText("Fertilidad: " + machoElegido.getFertilidad() + "/5");
            String ruta = new File(machoElegido.getImgFrontalPokemon()).toURI().toString();
            imgMacho.setImage(new Image(ruta));
        } else {
            txtNombreMacho.setText("No hay Machos");
            imgMacho.setImage(null);
        }

        if (!hembrasDisponibles.isEmpty()) {
            hembraElegida = hembrasDisponibles.get(rand.nextInt(hembrasDisponibles.size()));
            
            //Comprobamos mote de la hembra
            String nombreHembra;
            if (hembraElegida.getMotePokemon() != null && !hembraElegida.getMotePokemon().trim().isEmpty()) {
                nombreHembra = hembraElegida.getMotePokemon();
            } else {
                nombreHembra = hembraElegida.getNombrePokemon();
            }
            
            txtNombreHembra.setText(nombreHembra);
            txtFertilidadHembra.setText("Fertilidad: " + hembraElegida.getFertilidad() + "/5");
            String ruta = new File(hembraElegida.getImgFrontalPokemon()).toURI().toString();
            imgHembra.setImage(new Image(ruta));
        } else {
            txtNombreHembra.setText("No hay Hembras");
            imgHembra.setImage(null);
        }
    }

    /**
     * Metodo que recorre todo el equipo y caja del entrenador, y separa
     * a los Pokemon en listas de machos y hembras siempre que tengan fertilidad disponible.
     */
    private void cargarCandidatos() {
        machosDisponibles.clear();
        hembrasDisponibles.clear();
        
        //Juntamos todos los Pokémon del jugador (Equipo + Caja)
        List<Pokemon> todosMisPokemon = new ArrayList<>();
        
        //Añadir los del equipo 
        for (Pokemon p : entrenadorActual.getEquipoPokemon()) {
            if (p != null) todosMisPokemon.add(p);
        }
        //Añadir los de la caja
        todosMisPokemon.addAll(entrenadorActual.getCajaPokemon());
        
        //Separarlos por sexo y comprobar que TENGAN FERTILIDAD (> 0)
        for (Pokemon p : todosMisPokemon) {
            if (p.getFertilidad() > 0) {
                if (p.getSexo() == Sexo.MACHO) {
                    machosDisponibles.add(p);
                } else if (p.getSexo() == Sexo.HEMBRA) {
                    hembrasDisponibles.add(p);
                }
            }
        }
    }

    //Cambiar tamaño de botones (gracias Elyass XDDD)
    
    // --------------- INCREMENTAR Y DISMINUIR TAMAÑO DEL BOTON CAMBIAR MACHO AL PASAR EL CURSOR ---------------
    
    /**
     * Aumenta el tamaño del boton de cambiar macho al pasar el cursor.
     * @param event El evento de raton
     */
    @FXML
    private void aumentarTamañoBotonCambiarMacho(MouseEvent event) {
        botonCambiarMacho.setScaleX(botonCambiarMacho.getScaleX() + 0.2);
        botonCambiarMacho.setScaleY(botonCambiarMacho.getScaleY() + 0.2);
    }

    /**
     * Disminuye el tamaño del boton de cambiar macho al apartar el cursor.
     * @param event El evento de raton
     */
    @FXML
    private void disminuirTamañoBotonCambiarMacho(MouseEvent event) {
        botonCambiarMacho.setScaleX(botonCambiarMacho.getScaleX() - 0.2);
        botonCambiarMacho.setScaleY(botonCambiarMacho.getScaleY() - 0.2);
    }
    
    // --------------- INCREMENTAR Y DISMINUIR TAMAÑO DEL BOTON CAMBIAR HEMBRA AL PASAR EL CURSOR ---------------
    
    /**
     * Aumenta el tamaño del boton de cambiar hembra al pasar el cursor.
     * @param event El evento de raton
     */
    @FXML
    private void aumentarTamañoBotonCambiarHembra(MouseEvent event) {
        botonCambiarHembra.setScaleX(botonCambiarHembra.getScaleX() + 0.2);
        botonCambiarHembra.setScaleY(botonCambiarHembra.getScaleY() + 0.2);
    }

    /**
     * Disminuye el tamaño del boton de cambiar hembra al apartar el cursor.
     * @param event El evento de raton
     */
    @FXML
    private void disminuirTamañoBotonCambiarHembra(MouseEvent event) {
        botonCambiarHembra.setScaleX(botonCambiarHembra.getScaleX() - 0.2);
        botonCambiarHembra.setScaleY(botonCambiarHembra.getScaleY() - 0.2);
    }

    // --------------- INCREMENTAR Y DISMINUIR TAMAÑO DEL BOTON CRIAR AL PASAR EL CURSOR ---------------
    
    /**
     * Aumenta el tamaño del boton de criar al pasar el cursor.
     * @param event El evento de raton
     */
    @FXML
    private void aumentarTamañoBotonCriar(MouseEvent event) {
        botonCriar.setScaleX(botonCriar.getScaleX() + 0.2);
        botonCriar.setScaleY(botonCriar.getScaleY() + 0.2);
    }

    /**
     * Disminuye el tamaño del boton de criar al apartar el cursor.
     * @param event El evento de raton
     */
    @FXML
    private void disminuirTamañoBotonCriar(MouseEvent event) {
        botonCriar.setScaleX(botonCriar.getScaleX() - 0.2);
        botonCriar.setScaleY(botonCriar.getScaleY() - 0.2);
    }

    // --------------- INCREMENTAR Y DISMINUIR TAMAÑO DEL BOTON SALIR AL PASAR EL CURSOR ---------------
    
    /**
     * Aumenta el tamaño del boton de salir al pasar el cursor.
     * @param event El evento de raton
     */
    @FXML
    private void aumentarTamañoBotonSalir(MouseEvent event) {
        botonSalir.setScaleX(botonSalir.getScaleX() + 0.2);
        botonSalir.setScaleY(botonSalir.getScaleY() + 0.2);
    }

    /**
     * Disminuye el tamaño del boton de salir al apartar el cursor.
     * @param event El evento de raton
     */
    @FXML
    private void disminuirTamañoBotonSalir(MouseEvent event) {
        botonSalir.setScaleX(botonSalir.getScaleX() - 0.2);
        botonSalir.setScaleY(botonSalir.getScaleY() - 0.2);
    }
    
    // --------------- LOGICA DE LA SELECCION DE PADRES (CAJA PC) ---------------
    
    /**
     * Accion al hacer clic en el boton de cambiar el macho. Abre el panel de seleccion.
     * @param event El evento de raton
     */
    @FXML
    public void clicCambiarMacho(MouseEvent event) {
        if (!machosDisponibles.isEmpty()) {
            seleccionandoMacho = true;
            txtTituloSeleccion.setText("Elige al Padre (Macho)");
            abrirPanelSeleccion(machosDisponibles);
        } else {
            mostrarAlerta("Sin Machos", "No hay candidatos", "No tienes ningún Pokémon macho con fertilidad disponible.", AlertType.WARNING);
        }
    }

    /**
     * Accion al hacer clic en el boton de cambiar la hembra. Abre el panel de seleccion.
     * @param event El evento de raton
     */
    @FXML
    public void clicCambiarHembra(MouseEvent event) {
        if (!hembrasDisponibles.isEmpty()) {
            seleccionandoMacho = false;
            txtTituloSeleccion.setText("Elige a la Madre (Hembra)");
            abrirPanelSeleccion(hembrasDisponibles);
        } else {
            mostrarAlerta("Sin Hembras", "No hay candidatas", "No tienes ningún Pokémon hembra con fertilidad disponible.", AlertType.WARNING);
        }
    }

    /**
     * Abre el panel emergente y genera una cuadricula visual con los Pokemon pasados por parametro.
     * @param listaMostrar La lista de Pokemon a renderizar en la vista.
     */
    private void abrirPanelSeleccion(List<Pokemon> listaMostrar) {
        //Vaciamos lo que hubiera de antes en la cuadricula
        contenedorPokemons.getChildren().clear();

        //Por cada Pokemon en la lista, creamos su miniatura
        for (Pokemon p : listaMostrar) {
            //Creamos una cajita vertical
            javafx.scene.layout.VBox cajaPokemon = new javafx.scene.layout.VBox();
            cajaPokemon.setAlignment(javafx.geometry.Pos.CENTER);
            cajaPokemon.setSpacing(5);
            //Efecto cristalizado para que parezca un boton
            cajaPokemon.setStyle("-fx-background-color: rgba(255, 255, 255, 0.2); -fx-background-radius: 10; -fx-padding: 10; -fx-cursor: hand;");

            //Creamos su imagen
            ImageView imgPoke = new ImageView();
            imgPoke.setFitHeight(80);
            imgPoke.setFitWidth(80);
            try {
                String ruta = new File(p.getImgFrontalPokemon()).toURI().toString();
                imgPoke.setImage(new Image(ruta));
            } catch (Exception e) {}

            //Comprobamos si tiene mote, si no, usamos el nombre de la especie
            String nombreAMostrar;
            if (p.getMotePokemon() != null && !p.getMotePokemon().trim().isEmpty()) {
                nombreAMostrar = p.getMotePokemon();
            } else {
                nombreAMostrar = p.getNombrePokemon();
            }

            //Creamos su nombre y nivel
            Text txtNombre = new Text(nombreAMostrar + " (Nv." + p.getNivel() + ")");
            txtNombre.setFill(javafx.scene.paint.Color.WHITE);

            //Lo metemos todo en la cajita
            cajaPokemon.getChildren().addAll(imgPoke, txtNombre);

            //Asignamos el evento de click a la cajita
            cajaPokemon.setOnMouseClicked(event -> pokemonSeleccionado(p));

            //Añadimos la cajita a la cuadricula general
            contenedorPokemons.getChildren().add(cajaPokemon);
        }

        //Mostramos el panel gigante y lo traemos al frente
        panelSeleccion.setVisible(true);
        panelSeleccion.toFront(); 
    }

    /**
     * Metodo que se ejecuta al seleccionar un Pokemon concreto dentro del panel de seleccion.
     * @param p El pokemon elegido por el jugador.
     */
    private void pokemonSeleccionado(Pokemon p) {
        
        //Comprobamos si el pokemon que acabamos de hacer clic tiene mote
        String nombreAMostrar;
        if (p.getMotePokemon() != null && !p.getMotePokemon().trim().isEmpty()) {
            nombreAMostrar = p.getMotePokemon();
        } else {
            nombreAMostrar = p.getNombrePokemon();
        }

        if (seleccionandoMacho) {
            //Guardamos el macho y actualizamos la vista principal
            machoElegido = p;
            txtNombreMacho.setText(nombreAMostrar); //Aqui ponemos mote
            txtFertilidadMacho.setText("Fertilidad: " + machoElegido.getFertilidad() + "/5");
            try {
                imgMacho.setImage(new Image(new File(machoElegido.getImgFrontalPokemon()).toURI().toString()));
            } catch (Exception e) {}
            
        } else {
            //Guardamos la hembra y actualizamos la vista principal
            hembraElegida = p;
            txtNombreHembra.setText(nombreAMostrar); //Aqui ponemos mote
            txtFertilidadHembra.setText("Fertilidad: " + hembraElegida.getFertilidad() + "/5");
            try {
                imgHembra.setImage(new Image(new File(hembraElegida.getImgFrontalPokemon()).toURI().toString()));
            } catch (Exception e) {}
        }

        //Cerramos el panel tras elegir
        cerrarSeleccion(null);
    }

    /**
     * Boton/Accion para cerrar la caja de seleccion sin haber elegido ningun Pokemon.
     * @param event El evento de raton
     */
    @FXML
    public void cerrarSeleccion(MouseEvent event) {
        panelSeleccion.setVisible(false);
    }

    // --------------- FIN LOGICA DE LA SELECCION DE PADRES ---------------

    /**
     * Metodo que se activa al hacer clic en el boton "Criar". Comprueba que 
     * existen los padres y lanza la ventana del huevo.
     * @param event El evento de raton
     */
    @FXML
    public void accionCriar(MouseEvent event) {
        //Comprobaciones de seguridad
        if (machoElegido == null || hembraElegida == null) {
            mostrarAlerta("Error", "Faltan padres", "Necesitas un Macho y una Hembra para criar.", AlertType.WARNING);
            return;
        }

        //Si la imagen del bebe de la crianza anterior estaba visible, la ocultamos
        if (imgBebe != null) imgBebe.setVisible(false);

        //Si devuelve true se ha pulsado abrir y se abre el huevo
        if (mostrarAlertaHuevo()) {
            eclosionarHuevo(); 
        }
    }
    
    /**
     * Muestra una ventana de dialogo preguntando al jugador si desea abrir el huevo.
     * @return true si el jugador decide abrirlo, false si decide dejarlo/cancelar.
     */
    private boolean mostrarAlertaHuevo() {
        Alert alertaHuevo = new Alert(AlertType.INFORMATION);
        alertaHuevo.setTitle("¡Un Huevo Pokémon!");
        alertaHuevo.setHeaderText("¡Tus Pokémon han puesto un Huevo!");
        alertaHuevo.setContentText("¿Qué deseas hacer con él?");

        DialogPane dialogPane = alertaHuevo.getDialogPane();

        //Cargamos el css de la alerta
        try {
            dialogPane.getStylesheets().add(getClass().getResource("/view/captura/alertas.css").toExternalForm());
        } catch (Exception e) {
            System.out.println("Error cargando CSS de la alerta");
        }

        //Icono esquina superior izquierda
        try {
            Stage stage = (Stage) dialogPane.getScene().getWindow();
            stage.getIcons().add(new Image(new File("imgs/Login/Login-icon.png").toURI().toString()));
        } catch (Exception e) {}

        //Icono central
        try {
            String rutaHuevo = new File("imgs/Crianza/Huevo.png").toURI().toString(); 
            ImageView iconoHuevo = new ImageView(new Image(rutaHuevo));
            iconoHuevo.setFitHeight(60); 
            iconoHuevo.setFitWidth(60);
            alertaHuevo.setGraphic(iconoHuevo);
        } catch (Exception e) {}

        //Botones personalizados de abrir o cancelar 
        ButtonType btnAbrir = new ButtonType("Abrir Huevo");
        ButtonType btnCancelar = new ButtonType("Dejarlo", ButtonBar.ButtonData.CANCEL_CLOSE);
        alertaHuevo.getButtonTypes().setAll(btnAbrir, btnCancelar);

        //Mostrar ventana y esperar respuesta
        Optional<ButtonType> resultado = alertaHuevo.showAndWait();
        
        //Devuelve true solo si el usuario pulsa abrir
        return resultado.isPresent() && resultado.get() == btnAbrir;
    }

    /**
     * Logica para crear al nuevo Pokemon bebe (cria). Genera sus stats base heredados,
     * mezcla los motes, actualiza la fertilidad de los padres y guarda todo en base de datos.
     */
    private void eclosionarHuevo() {
        //Crear al bebé (La especie base será la de la madre, regla común en Pokémon)
        Pokemon bebe = new Pokemon();
        bebe.setNombrePokemon(hembraElegida.getNombrePokemon());
        bebe.setNumPokedex(hembraElegida.getNumPokedex());
        bebe.setImgFrontalPokemon(hembraElegida.getImgFrontalPokemon());
        bebe.setImgPosteriorPokemon(hembraElegida.getImgPosteriorPokemon());
        bebe.setNivel(1); //El bebé nace a nivel 1
        bebe.setExperiencia(0);
        bebe.setFertilidad(5); //Fertilidad máxima al nacer
        
        // Sexo realista basado en la especie
        bebe.setSexo(Pokemon.generarSexoPokemon(bebe.getNumPokedex()));
        
        //Mezclar el mote (Mitad madre, mitad padre, orden aleatorio)
        String moteP;
        if (machoElegido.getMotePokemon() != null && !machoElegido.getMotePokemon().trim().isEmpty()) {
            moteP = machoElegido.getMotePokemon();
        } else {
            moteP = machoElegido.getNombrePokemon();
        }
        
        String moteM;
        if (hembraElegida.getMotePokemon() != null && !hembraElegida.getMotePokemon().trim().isEmpty()) {
            moteM = hembraElegida.getMotePokemon();
        } else {
            moteM = hembraElegida.getNombrePokemon();
        }
        
        String mitadPadre = moteP.substring(0, Math.max(1, moteP.length() / 2));
        String mitadMadre = moteM.substring(Math.max(0, Math.max(1, moteM.length() / 2)));
        
        String moteBebe;
        if (rand.nextBoolean()) {
            moteBebe = mitadPadre + mitadMadre;
        } else {
            moteBebe = mitadMadre + mitadPadre;
        }
        bebe.setMotePokemon(moteBebe);

        //Heredar las mejores estadisticas
        bebe.setVitalidadMaxima(Math.max(machoElegido.getVitalidadMaxima(), hembraElegida.getVitalidadMaxima()));
        bebe.setVitalidad(bebe.getVitalidadMaxima());
        bebe.setAtaque(Math.max(machoElegido.getAtaque(), hembraElegida.getAtaque()));
        bebe.setDefensa(Math.max(machoElegido.getDefensa(), hembraElegida.getDefensa()));
        bebe.setAtaqueEspecial(Math.max(machoElegido.getAtaqueEspecial(), hembraElegida.getAtaqueEspecial()));
        bebe.setDefensaEspecial(Math.max(machoElegido.getDefensaEspecial(), hembraElegida.getDefensaEspecial()));
        bebe.setVelocidad(Math.max(machoElegido.getVelocidad(), hembraElegida.getVelocidad()));
        
        //Heredar tipos(Aleatorio entre los de los padres)
        bebe.setTipoPrincipal(hembraElegida.getTipoPrincipal());
        bebe.setTipoSecundario(machoElegido.getTipoPrincipal());

        //Actualizar fertilidad de los padres
        machoElegido.setFertilidad(machoElegido.getFertilidad() - 1);
        hembraElegida.setFertilidad(hembraElegida.getFertilidad() - 1);
        
        //Actualizar la fertilidad en la BD
        PokemonDAO.actualizarFertilidadBD(con, machoElegido);
        PokemonDAO.actualizarFertilidadBD(con, hembraElegida);

        //Buscar hueco (Equipo o Caja)
        boolean guardadoEnEquipo = false;
        Pokemon[] equipo = entrenadorActual.getEquipoPokemon(); 
        
        for (int i = 0; i < equipo.length; i++) {
            if (equipo[i] == null) {
                equipo[i] = bebe;
                bebe.setUbicacion(UbicacionPokemon.EQUIPO);
                guardadoEnEquipo = true;
                break;
            }
        }
        
        if (!guardadoEnEquipo) {
            entrenadorActual.getCajaPokemon().add(bebe);
            bebe.setUbicacion(UbicacionPokemon.CAJA);
        }

        //Guardar bebe en la BD
        try {
            capturaDao.guardarPokemon(this.con, bebe, entrenadorActual.getIdEntrenador(), bebe.getUbicacion().name());
            
            //Ocultar el huevo y mostrar el bebe para que quede de fondo de la alerta
            if (imgHuevo != null) {
                imgHuevo.setVisible(false);
            }

            if (imgBebe != null) {
                String rutaBebe = new File(bebe.getImgFrontalPokemon()).toURI().toString();
                imgBebe.setImage(new Image(rutaBebe));
                imgBebe.setVisible(true); 
            }

            //Preparamos la variable del mensaje de la alerta
            String destino;
            if (guardadoEnEquipo) {
                destino = "al EQUIPO";
            } else {
                destino = "a la CAJA";
            }

            //Mostrar alerta de exito (Pausa el codigo hasta que se pulse aceptar)
            mostrarAlerta("¡El huevo ha eclosionado!", 
                    "¡Enhorabuena! Tienes un nuevo " + bebe.getNombrePokemon(), 
                    "Su nuevo mote es: " + bebe.getMotePokemon() + "\nSe ha enviado " + destino + ".", 
                    AlertType.INFORMATION);

            //Al pulsar aceptar se reinicia la vista para la siguiente crianza
            if (imgBebe != null) {
                imgBebe.setVisible(false);
            }
            
            if (imgHuevo != null) {
                String rutaHuevo = new File("imgs/Crianza/Huevo.png").toURI().toString(); 
                imgHuevo.setImage(new Image(rutaHuevo));
                imgHuevo.setVisible(true);

                imgBebe.setLayoutX(403);
                imgBebe.setLayoutY(278);
            }

        } catch (Exception e) {
            mostrarAlerta("Error BD", "Error al guardar", "No se pudo guardar al bebé en la base de datos.", AlertType.ERROR);
            e.printStackTrace();
        }

        //Recargar candidatos (Por si alguno se quedó con 0 de fertilidad)
        cargarCandidatos();
        //Refrescar los padres actuales por si se quedaron vacios tras la crianza
        setEntrenador();
    }

    /**
     * Metodo para volver a la pantalla del menu principal.
     * @param event El evento de raton
     */
    @FXML
    public void crianzaSalir(MouseEvent event) {
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
                System.out.println("No se encontró el icono en: " + file.getAbsolutePath());
            }

            // Cambiar la escena del login por la nueva
            primaryStage.setScene(scene);

            // Mostrar la escena
            primaryStage.show();
        } catch (Exception e) {
            System.out.println("Error al cambiar de ventana - " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Metodo de utilidad para lanzar y mostrar alertas de informacion en pantalla.
     * @param titulo El texto superior de la ventana.
     * @param cabecera El texto principal en negrita.
     * @param contenido La descripcion detallada del mensaje.
     * @param tipo El tipo de alerta (INFORMATION, WARNING, ERROR).
     */
    private void mostrarAlerta(String titulo, String cabecera, String contenido, AlertType tipo) {
        Alert alerta = new Alert(tipo); //ventana del tipo que pasemos informacion, advertencia... lo que queramos
        //Textos de la ventana
        alerta.setTitle(titulo); //Texto barra superior
        alerta.setHeaderText(cabecera); //Texto grande en negrita
        alerta.setContentText(contenido); //Texto en pequeño
        
        //Parte visual de la alerta
        //Icono central
        try {
            String rutaIcono = new File("imgs/Login/Login-icon.png").toURI().toString();
            ImageView icono = new ImageView(new Image(rutaIcono));
            icono.setFitHeight(50);
            icono.setFitWidth(50);
            alerta.setGraphic(icono); //sustituye el que hay por defecto
        } catch (Exception e) {
            System.out.println("No se puede cargar el icono personalizado");
        }
        
        DialogPane dialogPane = alerta.getDialogPane(); //panel principal de alerta
        
        //Icono esquina superior izquierda
        try {
            Stage stage = (Stage) dialogPane.getScene().getWindow();
            stage.getIcons().add(new Image(new File("imgs/Login/Login-icon.png").toURI().toString()));
        } catch (Exception e) {
            System.out.println("No se puede cargar el icono personalizado");
        }
        
        //Para diferenciar entre exito y fracaso 
        if(tipo == AlertType.WARNING) {
            dialogPane.getStylesheets().add(getClass().getResource("/view/captura/alertas2.css").toExternalForm());
        } else {
            dialogPane.getStylesheets().add(getClass().getResource("/view/captura/alertas.css").toExternalForm()); //Enlace con archivo alertas css personalizado
        }
        
        alerta.showAndWait(); //muestra la ventana en la pantalla y para el codigo hasta que se acepta o se cierra la ventana
    }

    /**
     * Metodo de inicializacion llamado automaticamente por JavaFX al cargar la vista FXML.
     */
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {

        // Mostrar entrenador actual

        System.out.println("Entrenador: "+ entrenadorActual.getNombreEntrenador());

        setEntrenador();

        // Revisar que machos están cargando y luego las hembras

        System.out.println("---- Machos disponibles ----");
        for (Pokemon p : machosDisponibles) {
            System.out.println(p.getNombrePokemon());
        }

        System.out.println("---- Hembras disponibles ----");
        for (Pokemon p : hembrasDisponibles) {
            System.out.println(p.getNombrePokemon());
        }
        
    }
}