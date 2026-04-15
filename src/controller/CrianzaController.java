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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.ResourceBundle;

import bd.ConexionBBDD;
import dao.CapturaDao;
import dao.PokemonDAO;
import model.Entrenador;
import model.Pokemon;
import model.Sexo;
import model.UbicacionPokemon;

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
    private ImageView imgBebe; // Añadido para mostrar al hijo al eclosionar
    
    @FXML 
    private Text txtNombreMacho;
    
    @FXML 
    private Text txtNombreHembra;
    
    @FXML 
    private Text txtFertilidadMacho;
    
    @FXML 
    private Text txtFertilidadHembra;
    
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
    private Entrenador entrenadorActual;
    private Connection con;
    private CapturaDao capturaDao = new CapturaDao(); // Reutilizamos el DAO de captura para guardar al bebé
    
    // Listas para separar a los Pokémon aptos para criar
    private List<Pokemon> machosDisponibles = new ArrayList<>();
    private List<Pokemon> hembrasDisponibles = new ArrayList<>();
    
    // Los padres seleccionados actualmente
    private Pokemon machoElegido;
    private Pokemon hembraElegida;
    private Random rand = new Random();

    //Inicializar
    public void setEntrenador(Entrenador e) {
        this.entrenadorActual = e;
        
        // Conectar a BD
        ConexionBBDD conector = new ConexionBBDD();
        this.con = conector.getConexion();
        
        // Ocultar al bebe al principio
        if (imgBebe != null) {
            imgBebe.setVisible(false);
        }
        
        // Buscar candidatos para criar
        cargarCandidatos();
        
        // Mostrar los primeros candidatos por defecto (si hay)
        clicCambiarMacho(null);
        clicCambiarHembra(null);
    }

    private void cargarCandidatos() {
        machosDisponibles.clear();
        hembrasDisponibles.clear();
        
        // Juntamos todos los Pokémon del jugador (Equipo + Caja)
        List<Pokemon> todosMisPokemon = new ArrayList<>();
        
        // Añadir los del equipo (evitando los nulos)
        for (Pokemon p : entrenadorActual.getEquipoPokemon()) {
            if (p != null) todosMisPokemon.add(p);
        }
        // Añadir los de la caja
        todosMisPokemon.addAll(entrenadorActual.getCajaPokemon());
        
        // Separarlos por sexo y comprobar que TENGAN FERTILIDAD (> 0)
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
    
    //Botones para cambiar padres (generando aleatorio de los capturados por el entrenador)
    
    //Macho
    @FXML
    public void clicCambiarMacho(MouseEvent event) {
        if (!machosDisponibles.isEmpty()) {
            // Elegir uno aleatorio de la lista
            machoElegido = machosDisponibles.get(rand.nextInt(machosDisponibles.size()));
            
            // Actualizar vista
            txtNombreMacho.setText(machoElegido.getNombrePokemon());
            txtFertilidadMacho.setText("Fertilidad: " + machoElegido.getFertilidad() + "/5");
            
            String ruta = new File("imgs/Pokemons/" + machoElegido.getImgFrontalPokemon()).toURI().toString();
            imgMacho.setImage(new Image(ruta));
        } else {
            txtNombreMacho.setText("No hay Machos");
            imgMacho.setImage(null);
            machoElegido = null;
        }
    }

    //Hembra
    @FXML
    public void clicCambiarHembra(MouseEvent event) {
        if (!hembrasDisponibles.isEmpty()) {
            hembraElegida = hembrasDisponibles.get(rand.nextInt(hembrasDisponibles.size()));
            
            txtNombreHembra.setText(hembraElegida.getNombrePokemon());
            txtFertilidadHembra.setText("Fertilidad: " + hembraElegida.getFertilidad() + "/5");
            
            String ruta = new File("imgs/Pokemons/" + hembraElegida.getImgFrontalPokemon()).toURI().toString();
            imgHembra.setImage(new Image(ruta));
        } else {
            txtNombreHembra.setText("No hay Hembras");
            imgHembra.setImage(null);
            hembraElegida = null;
        }
    }

    //Boton de criar
    @FXML
    public void accionCriar(MouseEvent event) {
        //Comprobaciones de seguridad
        if (machoElegido == null || hembraElegida == null) {
            mostrarAlerta("Error", "Faltan padres", "Necesitas un Macho y una Hembra para criar.", AlertType.WARNING);
            return;
        }

        // Si la imagen del bebe de la crianza anterior estaba visible, la ocultamos
        if (imgBebe != null) imgBebe.setVisible(false);

     //Si devuelve true se ha pulsado abrir y se abre el huevo
        if (mostrarAlertaHuevo()) {
            eclosionarHuevo(); 
        }
    }
    
    //Metodo para la alerta del huevo 
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

    private void eclosionarHuevo() {
        //Crear al bebé (La especie base será la de la madre, regla común en Pokémon)
        Pokemon bebe = new Pokemon();
        bebe.setNombrePokemon(hembraElegida.getNombrePokemon());
        bebe.setNumPokedex(hembraElegida.getNumPokedex());
        bebe.setImgFrontalPokemon(hembraElegida.getImgFrontalPokemon());
        bebe.setImgPosteriorPokemon(hembraElegida.getImgPosteriorPokemon());
        bebe.setNivel(1); // El bebé nace a nivel 1
        bebe.setExperiencia(0);
        bebe.setFertilidad(5); // Fertilidad máxima al nacer
        bebe.setSexo(rand.nextBoolean() ? Sexo.MACHO : Sexo.HEMBRA ); // Sexo aleatorio
        
        //Mezclar el mote (Mitad madre, mitad padre, orden aleatorio)
        String moteP = machoElegido.getMotePokemon() != null ? machoElegido.getMotePokemon() : machoElegido.getNombrePokemon();
        String moteM = hembraElegida.getMotePokemon() != null ? hembraElegida.getMotePokemon() : hembraElegida.getNombrePokemon();
        
        String mitadPadre = moteP.substring(0, Math.max(1, moteP.length() / 2));
        String mitadMadre = moteM.substring(Math.max(0, moteM.length() / 2));
        
        String moteBebe = rand.nextBoolean() ? (mitadPadre + mitadMadre) : (mitadMadre + mitadPadre);
        bebe.setMotePokemon(moteBebe);

        //Heredar las mejores estadisticas
        bebe.setVitalidad(Math.max(machoElegido.getVitalidad(), hembraElegida.getVitalidad()));
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
            
            // Mostrar imagen del bebe recien nacido
            if (imgBebe != null) {
                String rutaBebe = new File("imgs/Pokemons/" + bebe.getImgFrontalPokemon()).toURI().toString();
                imgBebe.setImage(new Image(rutaBebe));
                imgBebe.setVisible(true); // Hace visible al bebé
            }

            //Mostrar alerta de exito
            String destino = guardadoEnEquipo ? "al EQUIPO" : "a la CAJA";
            mostrarAlerta("¡El huevo ha eclosionado!", 
                    "¡Enhorabuena! Tienes un nuevo " + bebe.getNombrePokemon(), 
                    "Su nuevo mote es: " + bebe.getMotePokemon() + "\nSe ha enviado " + destino + ".", 
                    AlertType.INFORMATION);

        } catch (Exception e) {
            mostrarAlerta("Error BD", "Error al guardar", "No se pudo guardar al bebé en la base de datos.", AlertType.ERROR);
            e.printStackTrace();
        }

        //Recargar candidatos (Por si alguno se quedó con 0 de fertilidad)
        cargarCandidatos();
        clicCambiarMacho(null);
        clicCambiarHembra(null);
    }

    //Boton de salir
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
            
            //Cargar el entrenador
            MainController mainCtrl = loader.getController();
            mainCtrl.setEntrenador(this.entrenadorActual);

            // Cargar el CSS
            String css = this.getClass().getResource("/view/principal/vistaPrincipal.css").toExternalForm();
            scene.getStylesheets().add(css);

            // Titulo, forzar el tamaño de la ventana y bloquear cambio manual
            primaryStage.setTitle("PokeINC - Prinicipal");
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

    ////Metodo para mostrar pop-up con mensajes
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

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		
	}
}