package controller;

import javafx.animation.Interpolator;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.text.Text;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TextInputDialog;

import java.io.File;
import java.net.URL;
import java.sql.Connection;
import java.util.Optional;
import java.util.Random;
import java.util.ResourceBundle;

import dao.CapturaDao;
import dao.PokemonDAO;
import model.Entrenador;
import model.Pokemon;
import model.Sesion;
import model.UbicacionPokemon;
import bd.ConexionBBDD;

public class CapturaController implements Initializable {

    // PANEL PARTICULAS

    @FXML
    private Pane panelParticulas;

    @FXML ImageView fondoPokemon;
    @FXML
    private Text txtNombre; // para cambiar el nombre del pokemon

    @FXML
    private Text txtTipo1; // para el primer tipo

    @FXML
    private Text txtTipo2; // para el segundo tipo

    @FXML
    private Line barraTipos;

    @FXML
    private Text lblTipo1;

    @FXML
    private Text lblTipo2;

    @FXML
    private ImageView imgPokemonActual; // imagen del pokemon

    @FXML
    private ImageView imgSexo; //para las imagenes del sexo
    
    
    private Pokemon pokemonActual;

    @FXML
    private Text vidaCaptura;

    // botones
    @FXML
    private ImageView botonCaptura;

    @FXML
    private ImageView botonCambiar;

    @FXML
    private ImageView botonSalir;
    
    
    private Connection con;


    private Entrenador entrenadorActual = Sesion.entrenadorLogueado;
    

    private CapturaDao capturaDao = new CapturaDao();
    
    // obtener el entrenador de la bd
    public void setEntrenador() {
    	if (this.entrenadorActual != null) {
    	try {
    		// creamos conexion a bd
            ConexionBBDD conector = new ConexionBBDD();
            Connection con = conector.getConexion();
            
            // llamamos al dao para rellenar el array de equipo del entrenador
            PokemonDAO.obtenerPokemon(con, entrenadorActual, UbicacionPokemon.EQUIPO);
            
            // para comprobar
            System.out.println("Entrenador: " + this.entrenadorActual.getNombreEntrenador());

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        }
    }



    @Override
    public void initialize(URL location, ResourceBundle resources) {

        // Cargar el entrenador

        setEntrenador();

        // sacamos el primer pokemon nada mas entrar
         generarEncuentro();
         
         

        // 3. generamos las particulas (las 240 que pusimos para que mole mas) 
        for (int i = 0; i < 240; i++) {
            crearParticula();
        }
    }


    // METODO CREAR PARTICULA

    private void crearParticula() {

        // DARLE FORMA DE CIRCULO PEQUEÑERO A LA PARTICULA

        // RADIO ALEATORIO ENTRE 1 Y 3 PIXELES POR PARTICULA

        double radio = Math.random() * 2 + 1;
        Circle particula = new Circle(radio, this.pokemonActual.getColor()); // Color del pokemon

        // DARLE UN POCO DE BLUR O DESENFOQUE PARA QUE NO SEA TAN COMPACTO

        particula.setEffect(new GaussianBlur(Math.random() * 2 + 1));

        // OPACIDAD ALEATORIA POR PARTICULA PARA DARLE PROFUNDIDAD

        particula.setOpacity(Math.random() * 0.6 + 0.2);

        // POSICIÓN INICIAL DE LAS PARTICULAS

        // TENIENDO EN CUENTA QUE LA PANTALLA FIJA ES DE 1074 PX DE ANCHO, ENTONCES UN
        // PUNTO ALEATORIO EN EL EJE X DE 0 A 1074
        particula.setLayoutX(Math.random() * 1074);

        // TENIENDO EN CUENTA QUE LA PANTALLA FIJA ES DE 620 PX DE ALTO, ENTONCES UN
        // PUNTO ALEATORIO EN EL EJE Y DE 0 A 620

        particula.setLayoutY(Math.random() * 620);

        // AÑADIR LAS PARTICULAS AL PANEL

        panelParticulas.getChildren().add(particula);

        // ANIMACIÓN DE LAS PARTICULAS

        // DARLES UNA VELOCIDAD ALEATORIA ENTRE 8 Y 18 SEGUNDOS PARA EL EFECTO DE FLOTE

        double duracionSegundos = Math.random() * 10 + 8;

        TranslateTransition animacion = new TranslateTransition(Duration.seconds(duracionSegundos), particula);

        // MOVERSE HACIA ABAJO, USANDO COMO REFERENCIA LA POSICION ACTUAL DE Y Y BAJANDO 300

        animacion.setByY(animacion.getByZ() + 300);
        animacion.setInterpolator(Interpolator.LINEAR); // MOVIMIENTO CONSTANTE

        // CUANDO LA PARTICULA LLEGUE HASTA ARRIBA, RESETEAR EL EVENTO PARA UN NUEVO
        // COMIENZO DE OTRA PARTICULA

        animacion.setOnFinished(event -> {
            particula.setTranslateY(0); // REINICIAR EL DESPLAZAMIENTO A 0
            particula.setLayoutY(Math.random() * 620); // NUEVA POSICIÓN EN EL EJE Y
            particula.setLayoutX(Math.random() * 1074); // NUEVA POSICIÓN EN EL EJE X
            animacion.playFromStart(); // VOLVER A EMPEZAR DE 0
        });

        animacion.play();
    }
    // ---------------------------- PARTICULAS ---------------------------- \\


    // --------------- INCREMENTAR Y DISMINUIR TAMAÑO DEL BOTON CAPTURA AL PASAR EL CURSOR ---------------
    @FXML
    private void aumentarTamañoBotonCaptura(MouseEvent event) {

        botonCaptura.setScaleX(botonCaptura.getScaleX() + 0.2);
        botonCaptura.setScaleY(botonCaptura.getScaleY() + 0.2);

    }

    @FXML
    private void disminuirTamañoBotonCaptura(MouseEvent event) {

        botonCaptura.setScaleX(botonCaptura.getScaleX() - 0.2);
        botonCaptura.setScaleY(botonCaptura.getScaleY() - 0.2);

    }

    // --------------- INCREMENTAR Y DISMINUIR TAMAÑO DEL BOTON CAMBIAR AL PASAR EL CURSOR ---------------
    @FXML
    private void aumentarTamañoBotonCambiar(MouseEvent event) {

        botonCambiar.setScaleX(botonCambiar.getScaleX() + 0.2);
        botonCambiar.setScaleY(botonCambiar.getScaleY() + 0.2);

    }

    @FXML
    private void disminuirTamañoBotonCambiar(MouseEvent event) {

        botonCambiar.setScaleX(botonCambiar.getScaleX() - 0.2);
        botonCambiar.setScaleY(botonCambiar.getScaleY() - 0.2);

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


    // --------------- SALIR AL DARLE AL BOTON SALIR ---------------

    @FXML
    public void capturaSalir(MouseEvent event) {
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


    // aqui esta a medio falta poner la extension del sprite donde estan los espacions grandes


    @FXML
    // --- metodo para pedir un pokemon al dao y actualizar la vista
    private void generarEncuentro() {

        //instanciamos el dao y la conexion
        ConexionBBDD conexion = new ConexionBBDD();
        this.con = conexion.getConexion();

        // comprobamos que la conexion no sea nula
        if (con != null) {
            // usamos el metodo del dao y guardamos el resultado en pokemonActual
            this.pokemonActual = capturaDao.crearPokemonAleatorio(con);

            // comprobamos que no sea null el pokemon
            if (this.pokemonActual != null) {
            	
            		
            		// logica para elegir el sexo realista basado en la Pokedex
                model.Sexo sexoRealista = Pokemon.generarSexoPokemon(this.pokemonActual.getNumPokedex());
                this.pokemonActual.setSexo(sexoRealista);
                
                String rutaIcono = "";
                
                // asignamos el icono dependiendo del sexo que nos haya devuelto el metodo
                if (sexoRealista == model.Sexo.MACHO) {
                    rutaIcono = "imgs/Captura/sexo/Macho.png"; 
                } else if (sexoRealista == model.Sexo.HEMBRA) {
                    rutaIcono = "imgs/Captura/sexo/Hembra.png";
                } else {
                    rutaIcono = "imgs/Captura/sexo/Neutro2.0.png";
                }

                //Actualizar el icono de la vista
                try {
                    File archivoIcono = new File(rutaIcono);
                    
                    if (archivoIcono.exists()) {
                        Image imagenCargada = new Image(archivoIcono.toURI().toString());
                        imgSexo.setImage(imagenCargada);
                    } else {
                        System.out.println("No encuentro el archivo de imagen en: " + archivoIcono.getAbsolutePath());
                        imgSexo.setImage(null); // Lo dejamos en blanco para que no muestre cosas falsas
                    }
                } catch (Exception e) {
                    System.out.println("Error raro al poner la imagen del sexo: " + e.getMessage());
                }
            	
            	
                // actualizamos el nombre en la vista
                txtNombre.setText(this.pokemonActual.getNombrePokemon());

                // cargamos la imagen correspondiente al nombre del pokemon
                try {
                    String rutaImagen = this.pokemonActual.getImgFrontalPokemon();
                    String rutaImagenAdaptada = new File(rutaImagen).toURI().toString();

                    // ESTABLECE EL TEXTO DE TIPO1 AL DELPOKEMON ACTUAL
                    this.txtTipo1.setText(this.pokemonActual.getTipoPrincipal().toString());


                    // ESTABLECE EL TEXTO DE TIPO2 AL DELPOKEMON ACTUAL Y ADAPTA LOS ELEMENTOS VISUALES

                    if (this.pokemonActual.getTipoSecundario() == null) {
                        this.txtTipo2.setText("");
                        this.lblTipo1.setText("Tipo");
                        this.lblTipo2.setVisible(false);
                        this.barraTipos.setVisible(false);
                        this.txtTipo1.setLayoutX(481);
                        this.txtTipo1.setLayoutY(384);
                        this.lblTipo1.setLayoutX(504);
                        this.lblTipo1.setLayoutY(409);
                    } // ESTABLECE EL TEXTO DE TIPO2 AL DELPOKEMON ACTUAL Y ADAPTA LOS ELEMENTOS VISUALES
                    else {
                        this.lblTipo1.setText("Tipo 1");
                        this.lblTipo2.setVisible(true);
                        this.barraTipos.setVisible(true);
                        this.txtTipo1.setLayoutX(411);
                        this.txtTipo1.setLayoutY(387);
                        this.lblTipo1.setLayoutX(437);
                        this.lblTipo1.setLayoutY(409);
                        this.txtTipo2.setText(this.pokemonActual.getTipoSecundario().toString());
                    }
                    // ESTABLECE LA IMAGEN DEL POKEMON Y FUERZA A QUE NO CAMBIE EL TAMAÑO DEL IMAGEVIEW

                    if (rutaImagenAdaptada != null) {
                       // System.out.println(rutaImagenAdaptada);
                        Image img = new Image(rutaImagenAdaptada);
                        imgPokemonActual.setImage(img);
                        imgPokemonActual.setLayoutX(-62);
                        imgPokemonActual.setLayoutY(-78);
                        imgPokemonActual.setFitWidth(150);
                        imgPokemonActual.setFitHeight(150);
                        imgPokemonActual.setX(70);
                        imgPokemonActual.setY(30);
                        imgPokemonActual.setPreserveRatio(false);
                    } else {
                      //  System.out.println(rutaImagen);
                        System.out.println("no se ha encontrado el archivo: " + pokemonActual.getNombrePokemon());
                    }

                    // CAMBIAR EL TEXTO DE VIDA A LA GENERADA AUTOMÁTICAMENTE

                    if (this.pokemonActual.getVitalidadMaxima() != 0) {
                        vidaCaptura.setText("HP / "+this.pokemonActual.getVitalidadMaxima());
                    }

                    // CAMBIAR EL COLOR DE LAS PARTICULAS EN PANTALLA EN FUNCION DEL POKEMON QUE HA SALIDO
                    pokemonActual.cambiarColor();
                    Color colorCambio = this.pokemonActual.getColor();
                    for (Node capturaParticulas : panelParticulas.getChildren()){
                        if (capturaParticulas instanceof Circle circle){
                            circle.setFill(colorCambio);
                        }
                    }

                    // CAMBIAR EL COLOR DE FONDO DEL POKEMON EN FUNCION DE SU TIPO

                        String rutaFondo = "imgs/Captura/fondosTipos/fondo" + this.pokemonActual.getTipoPrincipal().toString().toLowerCase() + ".png";
                        String rutaFondoAdaptado = new File(rutaFondo).toURI().toString();
                        Image imgFondo = new Image(rutaFondoAdaptado);

                        double altura = fondoPokemon.getFitHeight();
                        double ancho = fondoPokemon.getFitWidth();

                        fondoPokemon.setImage(imgFondo);
                        fondoPokemon.setFitHeight(altura);
                        fondoPokemon.setFitWidth(ancho);
                        fondoPokemon.setPreserveRatio(false);
                } catch (Exception e) {
                    System.out.println("Error al generar el pokemon aleatorio: " + e.getMessage());
                    e.printStackTrace();
                }
            }
        } else {
            System.out.println("error: la conexion con la bbdd es nula");
        }
    }




    @FXML
    // accion para el boton de cambiar pokemon
    public void clicCambiar(MouseEvent event) {
        // simplemente refrescamos la pantalla llamando al metodo que genera un encuentro
        // esto pedira otro pokemon aleatorio al dao y actualizara el sprite y el nombre

        try {
            generarEncuentro();


        } catch (Exception e) {
            System.out.println("Error: Se ha producido un error inesperado. " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    

    // metodo para capturar el pokemon
    @FXML
    void lanzarPokeball(MouseEvent event) {
        // si no hay entrenador o pokemon, mostramos fallo
        if (this.entrenadorActual == null || this.pokemonActual == null) {
            System.out.println("Error: No hay entrenador logueado o pokemon generado.");
            return;
        }

        Random r = new Random();
        
        //probabilidad 2/3 de captura
        if (r.nextInt(3) < 2) { 
        	// input + logica mote, muestra el nombre dle atrapado y pide mote
            TextInputDialog dialogMote = new TextInputDialog(pokemonActual.getNombrePokemon());
            dialogMote.setTitle("¡Pokémon Atrapado!");
            dialogMote.setHeaderText("¡Has atrapado a " + pokemonActual.getNombrePokemon() + "!");
            dialogMote.setContentText("Introduce un mote para tu Pokémon:");
            
           // obtenemos la ventana del dialogo para personalizarla
            Stage stageMote = (Stage) dialogMote.getDialogPane().getScene().getWindow();
            // añadimos el icono de la pokeball de Elyass xD
            stageMote.getIcons().add(new Image(new File("imgs/Login/Login-icon.png").toURI().toString()));
            
            // css para el formato de las aletas
            dialogMote.getDialogPane().getStylesheets().add(getClass().getResource("/view/captura/alertas.css").toExternalForm());
            // para evitar el NullPointerException uso esta clase lo tuve que buscar
            Optional<String> resultado = dialogMote.showAndWait();
            
            // si el usuario escribe algo y da a OK, se pone el mote y si cancela o deja vacio se queda el original
            if (resultado.isPresent() && !resultado.get().trim().isEmpty()) {
                pokemonActual.setMotePokemon(resultado.get());
            } else {
                pokemonActual.setMotePokemon(pokemonActual.getNombrePokemon());
            }
        	
        	
            // establecemos que no esta guardado en el equipo por defecto
            boolean guardadoEnEquipo = false;
            // obtenemos los pokemon del equipo
            Pokemon[] equipo = entrenadorActual.getEquipoPokemon(); 
            
            // intentamos meter al nuevo equipo
            for (int i = 0; i < equipo.length; i++) {
            	// si hay hueco en el array
                if (equipo[i] == null) {
                	// introduce el pokemon al array
                    equipo[i] = pokemonActual;
                    // establecemos la ubicacion del pokemon en equipo
                    pokemonActual.setUbicacion(UbicacionPokemon.EQUIPO);
                    // y lo damos como guardado
                    guardadoEnEquipo = true;
                    break;
                }
            }
            
            // si no podemos, a la caja
            if (!guardadoEnEquipo) {
            	// lo añadimos a la lista de la caja pokemon
                entrenadorActual.getCajaPokemon().add(pokemonActual);
                // cambiamos su ubicacion a la caja
                pokemonActual.setUbicacion(UbicacionPokemon.CAJA);
            }

            // guardamos en la bd usando el dao inicializado previamente con su metodo guardar pokemon
            capturaDao.guardarPokemon(this.con, pokemonActual, entrenadorActual.getIdEntrenador(), pokemonActual.getUbicacion().name());
            //System.out.println("¡Atrapado! " + pokemonActual.getNombrePokemon() + " enviado a " + pokemonActual.getUbicacion());
            String mensajeCuerpo = "¡Has atrapado a " + pokemonActual.getNombrePokemon() + "!";
            String mensajeDetalle = "Ahora tu " + pokemonActual.getNombrePokemon() + " se llama " + pokemonActual.getMotePokemon() + ".\nFue enviado a: " + pokemonActual.getUbicacion();
            mostrarAlerta("¡Enhorabuena!", mensajeCuerpo, mensajeDetalle, AlertType.INFORMATION);
            
            // generamos otro encuento 
            generarEncuentro(); 
            
        } else {
        		//System.out.println("¡Se ha escapado! Inténtalo de nuevo.");
        		mostrarAlerta("¡Oh no!","¡El pokemon se ha escapado","¡ "+pokemonActual.getNombrePokemon()+" ha huido de la Pokeball!",AlertType.WARNING);
            if (r.nextInt(3) < 2) {
                generarEncuentro();
            }
        }
           
    }
    
  //Metodo para mostrar pop-up con mensajes
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
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    


}
