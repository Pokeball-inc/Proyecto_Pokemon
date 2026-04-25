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
            
            String nombreMacho;
            if (machoElegido.getMotePokemon() != null && !machoElegido.getMotePokemon().trim().isEmpty()) {
                nombreMacho = machoElegido.getMotePokemon();
            } else {
                nombreMacho = machoElegido.getNombrePokemon();
            }
            
            if (machoElegido.getEsShiny()) {
                nombreMacho += "★";
            }
            
            txtNombreMacho.setText(nombreMacho);
            txtFertilidadMacho.setText("Fertilidad: " + machoElegido.getFertilidad() + "/5");

            String rutaRelativa = machoElegido.getRutaImagenActual(true, !Sesion.vista2D);
            String rutaImagen = "imgs/Pokemons/sprites/crystal/" + rutaRelativa;
            File archivoMacho = new File(rutaImagen);
            
            if (archivoMacho.exists()) {
                imgMacho.setImage(new Image(archivoMacho.toURI().toString()));
            } else {
                System.out.println("ERROR CRIANZA: No se encontró al padre -> " + archivoMacho.getAbsolutePath());
                imgMacho.setImage(null);
            }
            
        } else {
            txtNombreMacho.setText("No hay Machos");
            imgMacho.setImage(null);
        }

        if (!hembrasDisponibles.isEmpty()) {
            hembraElegida = hembrasDisponibles.get(rand.nextInt(hembrasDisponibles.size()));
            
            String nombreHembra;
            if (hembraElegida.getMotePokemon() != null && !hembraElegida.getMotePokemon().trim().isEmpty()) {
                nombreHembra = hembraElegida.getMotePokemon();
            } else {
                nombreHembra = hembraElegida.getNombrePokemon();
            }
            
            if (hembraElegida.getEsShiny()) {
                nombreHembra += "★";
            }
            
            txtNombreHembra.setText(nombreHembra);
            txtFertilidadHembra.setText("Fertilidad: " + hembraElegida.getFertilidad() + "/5");

            String rutaRelativa = hembraElegida.getRutaImagenActual(true, !Sesion.vista2D);
            String rutaImagen = "imgs/Pokemons/sprites/crystal/" + rutaRelativa;
            File archivoHembra = new File(rutaImagen);
            
            if (archivoHembra.exists()) {
                imgHembra.setImage(new Image(archivoHembra.toURI().toString()));
            } else {
                System.out.println("ERROR CRIANZA: No se encontró a la madre -> " + archivoHembra.getAbsolutePath());
                imgHembra.setImage(null);
            }
            
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

    // --------------- INCREMENTAR Y DISMINUIR TAMAÑO DEL BOTON CAMBIAR MACHO AL PASAR EL CURSOR ---------------
    
    @FXML
    private void aumentarTamañoBotonCambiarMacho(MouseEvent event) {
        botonCambiarMacho.setScaleX(botonCambiarMacho.getScaleX() + 0.2);
        botonCambiarMacho.setScaleY(botonCambiarMacho.getScaleY() + 0.2);
    }

    @FXML
    private void disminuirTamañoBotonCambiarMacho(MouseEvent event) {
        botonCambiarMacho.setScaleX(botonCambiarMacho.getScaleX() - 0.2);
        botonCambiarMacho.setScaleY(botonCambiarMacho.getScaleY() - 0.2);
    }
    
    // --------------- INCREMENTAR Y DISMINUIR TAMAÑO DEL BOTON CAMBIAR HEMBRA AL PASAR EL CURSOR ---------------
    
    @FXML
    private void aumentarTamañoBotonCambiarHembra(MouseEvent event) {
        botonCambiarHembra.setScaleX(botonCambiarHembra.getScaleX() + 0.2);
        botonCambiarHembra.setScaleY(botonCambiarHembra.getScaleY() + 0.2);
    }

    @FXML
    private void disminuirTamañoBotonCambiarHembra(MouseEvent event) {
        botonCambiarHembra.setScaleX(botonCambiarHembra.getScaleX() - 0.2);
        botonCambiarHembra.setScaleY(botonCambiarHembra.getScaleY() - 0.2);
    }

    // --------------- INCREMENTAR Y DISMINUIR TAMAÑO DEL BOTON CRIAR AL PASAR EL CURSOR ---------------
    
    @FXML
    private void aumentarTamañoBotonCriar(MouseEvent event) {
        botonCriar.setScaleX(botonCriar.getScaleX() + 0.2);
        botonCriar.setScaleY(botonCriar.getScaleY() + 0.2);
    }

    @FXML
    private void disminuirTamañoBotonCriar(MouseEvent event) {
        botonCriar.setScaleX(botonCriar.getScaleX() - 0.2);
        botonCriar.setScaleY(botonCriar.getScaleY() - 0.2);
    }

    // --------------- INCREMENTAR Y DISMINUIR TAMAÑO DEL BOTON SALIR AL PASAR EL CURSOR ---------------
    
    @FXML
    private void aumentarTamañoBotonSalir(MouseEvent event) {
        botonSalir.setScaleX(botonSalir.getScaleX() + 0.2);
        botonSalir.setScaleY(botonSalir.getScaleY() + 0.2);
    }

    @FXML
    private void disminuirTamañoBotonSalir(MouseEvent event) {
        botonSalir.setScaleX(botonSalir.getScaleX() - 0.2);
        botonSalir.setScaleY(botonSalir.getScaleY() - 0.2);
    }
    
    // --------------- LOGICA DE LA SELECCION DE PADRES (CAJA PC) ---------------
    
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
     */
    private void abrirPanelSeleccion(List<Pokemon> listaMostrar) {
        contenedorPokemons.getChildren().clear();

        for (Pokemon p : listaMostrar) {
            javafx.scene.layout.VBox cajaPokemon = new javafx.scene.layout.VBox();
            cajaPokemon.setAlignment(javafx.geometry.Pos.CENTER);
            cajaPokemon.setSpacing(5);
            cajaPokemon.setStyle("-fx-background-color: rgba(255, 255, 255, 0.2); -fx-background-radius: 10; -fx-padding: 10; -fx-cursor: hand;");

            ImageView imgPoke = new ImageView();
            imgPoke.setFitHeight(80);
            imgPoke.setFitWidth(80);
            
            try {
                String rutaRelativa = p.getRutaImagenActual(true, !Sesion.vista2D);
                String rutaImagen = "imgs/Pokemons/sprites/crystal/" + rutaRelativa;
                File archivoMini = new File(rutaImagen);

                if (archivoMini.exists()) {
                    imgPoke.setImage(new Image(archivoMini.toURI().toString()));
                } else {
                    System.out.println("ERROR MINIATURA CRIANZA -> " + archivoMini.getAbsolutePath());
                }
            } catch (Exception e) {}

            String nombreAMostrar;
            if (p.getMotePokemon() != null && !p.getMotePokemon().trim().isEmpty()) {
                nombreAMostrar = p.getMotePokemon();
            } else {
                nombreAMostrar = p.getNombrePokemon();
            }

            if (p.getEsShiny()) {
                nombreAMostrar += "★";
            }

            Text txtNombre = new Text(nombreAMostrar + " (Nv." + p.getNivel() + ")");
            txtNombre.setFill(javafx.scene.paint.Color.WHITE);

            cajaPokemon.getChildren().addAll(imgPoke, txtNombre);
            cajaPokemon.setOnMouseClicked(event -> pokemonSeleccionado(p));
            contenedorPokemons.getChildren().add(cajaPokemon);
        }

        panelSeleccion.setVisible(true);
        panelSeleccion.toFront(); 
    }

    /**
     * Metodo que se ejecuta al seleccionar un Pokemon concreto dentro del panel de seleccion.
     */
    private void pokemonSeleccionado(Pokemon p) {
        
        String nombreAMostrar;
        if (p.getMotePokemon() != null && !p.getMotePokemon().trim().isEmpty()) {
            nombreAMostrar = p.getMotePokemon();
        } else {
            nombreAMostrar = p.getNombrePokemon();
        }
        
        if (p.getEsShiny()) {
            nombreAMostrar += "★";
        }

        if (seleccionandoMacho) {
            // Guardamos el macho y actualizamos la vista principal
            machoElegido = p;
            txtNombreMacho.setText(nombreAMostrar); 
            txtFertilidadMacho.setText("Fertilidad: " + machoElegido.getFertilidad() + "/5");
            
            try {
                String rutaRelativa = p.getRutaImagenActual(true, !Sesion.vista2D);
                String rutaImagen = "imgs/Pokemons/sprites/crystal/" + rutaRelativa;
                File archivoMacho = new File(rutaImagen);
                if (archivoMacho.exists()) {
                    imgMacho.setImage(new Image(archivoMacho.toURI().toString()));
                }
            } catch (Exception e) {}
            
        } else {
            // Guardamos la hembra y actualizamos la vista principal
            hembraElegida = p;
            txtNombreHembra.setText(nombreAMostrar); 
            txtFertilidadHembra.setText("Fertilidad: " + hembraElegida.getFertilidad() + "/5");
            
            try {
                String rutaRelativa = p.getRutaImagenActual(true, !Sesion.vista2D);
                String rutaImagen = "imgs/Pokemons/sprites/crystal/" + rutaRelativa;
                File archivoHembra = new File(rutaImagen);
                if (archivoHembra.exists()) {
                    imgHembra.setImage(new Image(archivoHembra.toURI().toString()));
                }
            } catch (Exception e) {}
        }

        // Cerramos el panel tras elegir
        cerrarSeleccion(null);
    }

    @FXML
    public void cerrarSeleccion(MouseEvent event) {
        panelSeleccion.setVisible(false);
    }

    // --------------- FIN LOGICA DE LA SELECCION DE PADRES ---------------

    @FXML
    public void accionCriar(MouseEvent event) {
        //Comprobaciones de seguridad
        if (machoElegido == null || hembraElegida == null) {
            mostrarAlerta("Error", "Faltan padres", "Necesitas un Macho y una Hembra para criar.", AlertType.WARNING);
            return;
        }

        //Si la imagen del bebe de la crianza anterior estaba visible, la ocultamos
        if (imgBebe != null) {
            imgBebe.setVisible(false);
        }

        //Si devuelve true se ha pulsado abrir y se abre el huevo
        if (mostrarAlertaHuevo()) {
            eclosionarHuevo(); 
        }
    }
    
    /**
     * Muestra una ventana de dialogo preguntando al jugador si desea abrir el huevo.
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
     * Logica para crear al nuevo Pokemon bebe (cria).
     */
    private void eclosionarHuevo() {
        //Crear al bebé (La especie base será la de la madre, regla común en Pokémon)
        Pokemon bebe = new Pokemon();
        bebe.setNombrePokemon(hembraElegida.getNombrePokemon());
        bebe.setNumPokedex(hembraElegida.getNumPokedex());
        
        //1 de cada 100 sale shiny por defecto pero si un padre lo es baja a 1 de cada 20
        int probabilidad = 100;
        if (machoElegido.getEsShiny() || hembraElegida.getEsShiny()) {
            probabilidad = 20;
        }
        bebe.setEsShiny(rand.nextInt(probabilidad) == 0);

        //copiamos las rutas de las imagenes de la madre
        bebe.setImgFrontalPokemon(hembraElegida.getImgFrontalPokemon());
        bebe.setImgFrontalPokemon3D(hembraElegida.getImgFrontalPokemon3D());
        bebe.setImgPosteriorPokemon(hembraElegida.getImgPosteriorPokemon());
        bebe.setImgPosteriorPokemon3D(hembraElegida.getImgPosteriorPokemon3D());
        
        bebe.setImgShinyFrontal(hembraElegida.getImgShinyFrontal());
        bebe.setImgShinyFrontal3D(hembraElegida.getImgShinyFrontal3D());
        bebe.setImgShinyPosterior(hembraElegida.getImgShinyPosterior());
        bebe.setImgShinyPosterior3D(hembraElegida.getImgShinyPosterior3D());

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
        int mejorHP = Math.max(machoElegido.getBaseVitalidadMaxima(), hembraElegida.getBaseVitalidadMaxima());
        bebe.setVitalidadMaxima(mejorHP);
        bebe.setVitalidad(mejorHP);
        bebe.setAtaque(Math.max(machoElegido.getBaseAtaque(), hembraElegida.getBaseAtaque()));
        bebe.setDefensa(Math.max(machoElegido.getBaseDefensa(), hembraElegida.getBaseDefensa()));
        bebe.setAtaqueEspecial(Math.max(machoElegido.getBaseAtaqueEspecial(), hembraElegida.getBaseAtaqueEspecial()));
        bebe.setDefensaEspecial(Math.max(machoElegido.getBaseDefensaEspecial(), hembraElegida.getBaseDefensaEspecial()));
        bebe.setVelocidad(Math.max(machoElegido.getBaseVelocidad(), hembraElegida.getBaseVelocidad()));
        
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
                //metodo inteligente par shiny 
                String rutaRelativa = bebe.getRutaImagenActual(true, !Sesion.vista2D);
                String rutaImagen = "imgs/Pokemons/sprites/crystal/" + rutaRelativa;
                File archivoBebe = new File(rutaImagen);
                
                System.out.println("Intentando eclosionar bebé en: " + rutaImagen);
                
                if (archivoBebe.exists()) {
                    imgBebe.setImage(new Image(archivoBebe.toURI().toString()));
                } else {
                    System.out.println("ERROR BEBÉ: La imagen del recién nacido no se encuentra -> " + archivoBebe.getAbsolutePath());
                }
                
                imgBebe.setVisible(true); 
            }

            //Preparamos la variable del mensaje de la alerta
            String destino;
            if (guardadoEnEquipo) {
                destino = "al EQUIPO";
            } else {
                destino = "a la CAJA";
            }
            
            // Añadimos la estrella al nombre en el anuncio si es Shiny
            String nombreAnuncio;
            if (bebe.getEsShiny()) {
                nombreAnuncio = bebe.getNombrePokemon() + " ★";
            } else {
                nombreAnuncio = bebe.getNombrePokemon();
            }

            //Mostrar alerta de exito
            mostrarAlerta("¡El huevo ha eclosionado!", 
                    "¡Enhorabuena! Tienes un nuevo " + nombreAnuncio, 
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

        //Recargar candidatos y refrescar vista
        cargarCandidatos();
        setEntrenador();
    }

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

    private void mostrarAlerta(String titulo, String cabecera, String contenido, AlertType tipo) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(cabecera);
        alerta.setContentText(contenido);
        
        try {
            String rutaIcono = new File("imgs/Login/Login-icon.png").toURI().toString();
            ImageView icono = new ImageView(new Image(rutaIcono));
            icono.setFitHeight(50);
            icono.setFitWidth(50);
            alerta.setGraphic(icono);
        } catch (Exception e) {
            System.out.println("No se puede cargar el icono personalizado");
        }
        
        DialogPane dialogPane = alerta.getDialogPane();
        
        try {
            Stage stage = (Stage) dialogPane.getScene().getWindow();
            stage.getIcons().add(new Image(new File("imgs/Login/Login-icon.png").toURI().toString()));
        } catch (Exception e) {
            System.out.println("No se puede cargar el icono personalizado");
        }
        
        if(tipo == AlertType.WARNING) {
            dialogPane.getStylesheets().add(getClass().getResource("/view/captura/alertas2.css").toExternalForm());
        } else {
            dialogPane.getStylesheets().add(getClass().getResource("/view/captura/alertas.css").toExternalForm());
        }
        
        alerta.showAndWait();
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {

        System.out.println("Entrenador: "+ entrenadorActual.getNombreEntrenador());

        setEntrenador();

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