package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.sql.Connection;
import java.util.Optional;
import java.util.Random;
import java.util.ResourceBundle;

import bd.ConexionBBDD;
import dao.EntrenadorDAO;
import dao.PokemonDAO;
import model.Entrenador;
import model.Pokemon;
import model.Sesion;

public class EntrenamientoController implements Initializable {

    //Elementos vista
    
    @FXML 
    private ListView<Pokemon> listaPokemon; //El menú lateral con scroll
    
    // Elementos de la vista central 
    @FXML 
    private ImageView imgPokemonCentral;
    
    @FXML 
    private ImageView imgSexoPokemon;
    
    @FXML 
    private ImageView imgTipo1Pokemon;
    
    @FXML 
    private ImageView imgTipo2Pokemon;
    
    @FXML 
    private ImageView imgShiny;
    
    @FXML
    private ImageView imgFondoPantalla; // para cambiar el fondo al pasar el cursor
    
    @FXML 
    private Text txtNombreCentral;
    
    @FXML 
    private Text txtNivelCentral;
    
    @FXML
    private Text txtPokedollares;
    
    @FXML
    private Text txtAtkPokemon;
    
    @FXML
    private Text txtHpPokemon;
    
    @FXML
    private Text txtDefensaPokemon;
    
    @FXML
    private Text txtAtkSpPokemon;
    
    @FXML
    private Text txtDefensaSpPokemon;
    
    @FXML
    private Text txtVelocidadPokemon;

    //Botones
    
    @FXML
    private ImageView botonEntrenamientoPesado; 
    
    @FXML
    private ImageView botonEntrenamientoFurioso; 
    
    @FXML
    private ImageView botonEntrenamientoFuncional;
    
    @FXML
    private ImageView botonEntrenamientoOnirico; 
    
    @FXML
    private ImageView botonSalir;

    //Variables
    private Entrenador entrenadorActual = Sesion.entrenadorLogueado;
    private Connection con;
    private Pokemon pokemonSeleccionado; //Guarda el pokemon al que le hacemos clic en la lista

    // rutas de las imagenes de fondo
    private final String FONDO_DEFAULT = "imgs/Entrenamiento/FondoEntrenamientoPredeterminado.png";
    private final String FONDO_PESADO = "imgs/Entrenamiento/FondoEntrenamientoPesado.png";
    private final String FONDO_FURIOSO = "imgs/Entrenamiento/FondoEntrenamientoFurioso.png";
    private final String FONDO_FUNCIONAL = "imgs/Entrenamiento/FondoEntrenamientoFuncional.png";
    private final String FONDO_ONIRICO = "imgs/Entrenamiento/FondoEntrenamientoOnirico.png";

    //Metodo mostrar entrenadorActual
    public void setEntrenador() {

        //Conectar a BD
        ConexionBBDD conector = new ConexionBBDD();
        this.con = conector.getConexion();
        
        //Actualizar UI del dinero
        if (txtPokedollares != null) {
            txtPokedollares.setText("Pokedollares: " + entrenadorActual.getPokedollares());
        }

        //Cargar los pokemon (Equipo + Caja) en el menu lateral
        cargarPokemonEnLista();
        
        //Elegir uno al azar para que presida la sala de entrenamiento al entrar
        seleccionarPokemonAleatorio();
    }

  //Metodo para cambiar el fondo de pantalla 
    private void cambiarFondoPantalla(String rutaFondo) {
        //prueba enlazado con el id
        if (imgFondoPantalla == null) {
            System.out.println("ERROR: imgFondoPantalla es NULL. ¡El fx:id no está conectado desde Scene Builder!");
            return;
        }

        try {
            File archivoFondo = new File(rutaFondo);
            
            //comprobar ruta
            if (archivoFondo.exists()) {
                //System.out.println("Imagen encontrada y cargada: " + archivoFondo.getAbsolutePath());
                imgFondoPantalla.setImage(new Image(archivoFondo.toURI().toString()));
            } else {
                System.out.println("LA RUTA ESTÁ MAL: No encuentro la imagen en -> " + archivoFondo.getAbsolutePath());
            }
        } catch (Exception e) {
            System.out.println("Error cambiando fondo: " + e.getMessage());
        }
    }

    //Cambiar tamaño de botones (gracias Elyass XDDD)
    
    // --------------- INCREMENTAR Y DISMINUIR TAMAÑO DEL BOTON ENTRENAR AL PASAR EL CURSOR ---------------
    
    //ENTRENAMIENTO PESADO
    @FXML
    private void aumentarTamañoBotonEntrenamientoPesado(MouseEvent event) {
        if (botonEntrenamientoPesado != null) {
        	botonEntrenamientoPesado.setScaleX(botonEntrenamientoPesado.getScaleX() + 0.2);
        	botonEntrenamientoPesado.setScaleY(botonEntrenamientoPesado.getScaleY() + 0.2);
            cambiarFondoPantalla(FONDO_PESADO);
        }
    }

    @FXML
    private void disminuirTamañoBotonEntrenamientoPesado(MouseEvent event) {
        if (botonEntrenamientoPesado != null) {
        	botonEntrenamientoPesado.setScaleX(botonEntrenamientoPesado.getScaleX() - 0.2);
        	botonEntrenamientoPesado.setScaleY(botonEntrenamientoPesado.getScaleY() - 0.2);
            cambiarFondoPantalla(FONDO_DEFAULT);
        }
    }
    
    //ENTRENAMIENTO FURIOSO
    @FXML
    private void aumentarTamañoBotonEntrenamientoFurioso(MouseEvent event) {
        if (botonEntrenamientoFurioso != null) {
        	botonEntrenamientoFurioso.setScaleX(botonEntrenamientoFurioso.getScaleX() + 0.2);
        	botonEntrenamientoFurioso.setScaleY(botonEntrenamientoFurioso.getScaleY() + 0.2);
            cambiarFondoPantalla(FONDO_FURIOSO);
        }
    }

    @FXML
    private void disminuirTamañoBotonEntrenamientoFurioso(MouseEvent event) {
        if (botonEntrenamientoFurioso != null) {
        	botonEntrenamientoFurioso.setScaleX(botonEntrenamientoFurioso.getScaleX() - 0.2);
        	botonEntrenamientoFurioso.setScaleY(botonEntrenamientoFurioso.getScaleY() - 0.2);
            cambiarFondoPantalla(FONDO_DEFAULT);
        }
    }
    
    //ENTRENAMIENTO FUNCIONAL
    @FXML
    private void aumentarTamañoBotonEntrenamientoFuncional(MouseEvent event) {
        if (botonEntrenamientoFuncional != null) {
        	botonEntrenamientoFuncional.setScaleX(botonEntrenamientoFuncional.getScaleX() + 0.2);
        	botonEntrenamientoFuncional.setScaleY(botonEntrenamientoFuncional.getScaleY() + 0.2);
            cambiarFondoPantalla(FONDO_FUNCIONAL);
        }
    }

    @FXML
    private void disminuirTamañoBotonEntrenamientoFuncional(MouseEvent event) {
        if (botonEntrenamientoFuncional != null) {
        	botonEntrenamientoFuncional.setScaleX(botonEntrenamientoFuncional.getScaleX() - 0.2);
        	botonEntrenamientoFuncional.setScaleY(botonEntrenamientoFuncional.getScaleY() - 0.2);
            cambiarFondoPantalla(FONDO_DEFAULT);
        }
    }
    
    //ENTRENAMIENTO ONIRICO
    @FXML
    private void aumentarTamañoBotonEntrenamientoOnirico(MouseEvent event) {
        if (botonEntrenamientoOnirico != null) {
        	botonEntrenamientoOnirico.setScaleX(botonEntrenamientoOnirico.getScaleX() + 0.2);
        	botonEntrenamientoOnirico.setScaleY(botonEntrenamientoOnirico.getScaleY() + 0.2);
            cambiarFondoPantalla(FONDO_ONIRICO);
        }
    }

    @FXML
    private void disminuirTamañoBotonEntrenamientoOnirico(MouseEvent event) {
        if (botonEntrenamientoOnirico != null) {
        	botonEntrenamientoOnirico.setScaleX(botonEntrenamientoOnirico.getScaleX() - 0.2);
        	botonEntrenamientoOnirico.setScaleY(botonEntrenamientoOnirico.getScaleY() - 0.2);
            cambiarFondoPantalla(FONDO_DEFAULT);
        }
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

    // --------------- LOGICA DE EJECUCION DE LOS ENTRENAMIENTOS ---------------

    //accion para el boton de entrenamiento pesado
    @FXML
    public void clicEntrenamientoPesado(MouseEvent event) {
        //coste 20. sube: vitalidad(+5), defensa(+5), defensa especial(+5)
        procesarEntrenamiento("Pesado", 20, 5, 0, 5, 0, 5, 0); 
    }

    //accion para el boton de entrenamiento furioso
    @FXML
    public void clicEntrenamientoFurioso(MouseEvent event) {
        //coste 30. sube: ataque(+5), ataque especial(+5), velocidad(+5)
        procesarEntrenamiento("Furioso", 30, 0, 5, 0, 5, 0, 5); 
    }

    //accion para el boton de entrenamiento funcional
    @FXML
    public void clicEntrenamientoFuncional(MouseEvent event) {
        //coste 40. sube: vitalidad(+5), ataque(+5), defensa(+5), velocidad(+5)
        procesarEntrenamiento("Funcional", 40, 5, 5, 5, 0, 0, 5); 
    }

    //accion para el boton de entrenamiento onirico
    @FXML
    public void clicEntrenamientoOnirico(MouseEvent event) {
        //coste 40. sube: vitalidad(+5), ataque especial(+5), defensa especial(+5), velocidad(+5)
        procesarEntrenamiento("Onírico", 40, 5, 0, 0, 5, 5, 5); 
    }

    //Metodo general para comprobar dinero, restar y subir stats
    private void procesarEntrenamiento(String nombreEntrenamiento, int costeMultiplicador, int subeHp, int subeAtk, int subeDef, int subeAtkSp, int subeDefSp, int subeVel) {
        
        //comprobamos que haya un pokemon seleccionado
        if (pokemonSeleccionado == null) {
            mostrarAlerta("Error", "¡Falta Pokémon!", "Selecciona primero un Pokémon del menú lateral para entrenarlo.", AlertType.WARNING);
            return;
        }

        //calculamos el coste del entrenamiento en base a su nivel
        int coste = pokemonSeleccionado.getNivel() * costeMultiplicador;
        
        //comprobamos fondos del entrenador
        if (entrenadorActual.getPokedollares() < coste) {
            mostrarAlerta("Sin fondos", "¡Te faltan Pokedollars!", 
                "El Entrenamiento " + nombreEntrenamiento + " para un Pokémon de Nvl " + pokemonSeleccionado.getNivel() + 
                " cuesta " + coste + " Pokedollars.\nActualmente tienes: " + entrenadorActual.getPokedollares(), 
                AlertType.WARNING);
            return;
        }

        //restamos el dinero EN MEMORIA
        entrenadorActual.setPokedollares(entrenadorActual.getPokedollares() - coste);
        
        //subimos el resto de estadisticas
        pokemonSeleccionado.setVitalidadMaxima(pokemonSeleccionado.getVitalidadMaxima() + subeHp);
        pokemonSeleccionado.setAtaque(pokemonSeleccionado.getAtaque() + subeAtk);
        pokemonSeleccionado.setDefensa(pokemonSeleccionado.getDefensa() + subeDef);
        pokemonSeleccionado.setAtaqueEspecial(pokemonSeleccionado.getAtaqueEspecial() + subeAtkSp);
        pokemonSeleccionado.setDefensaEspecial(pokemonSeleccionado.getDefensaEspecial() + subeDefSp);
        pokemonSeleccionado.setVelocidad(pokemonSeleccionado.getVelocidad() + subeVel);

        //refrescamos la vista para aplicar los cambios en tiempo real en la pantalla
        if (txtPokedollares != null) {
            txtPokedollares.setText("Pokedollars: " + entrenadorActual.getPokedollares());
        }
        actualizarVistaCentral();
        listaPokemon.refresh(); 

        //guardar datos en la BASE DE DATOS
        try {
            PokemonDAO.actualizarStatsBD(con, pokemonSeleccionado);
            
            EntrenadorDAO entrenadorDao = new EntrenadorDAO(); 
            //Grande Isaias xDDD
            entrenadorDao.actualizarPokedollares(entrenadorActual.getIdEntrenador(), entrenadorActual.getPokedollares());
        } catch (Exception e) {
            System.out.println("Error al actualizar la base de datos: " + e.getMessage());
        }

        //mostramos la alerta de exito sin avisos porque ya le hemos avisado antes
        mostrarAlerta("¡Entrenamiento Completado!", 
            "¡El Entrenamiento " + nombreEntrenamiento + " ha sido un éxito!", 
            pokemonSeleccionado.getNombrePokemon() + " se ha vuelto más fuerte.\n" +
            "Se han cobrado " + coste + " Pokedollars por los servicios.", 
            AlertType.INFORMATION);

        //al cerrar el dialogo volvemos a poner el fondo normal
        cambiarFondoPantalla(FONDO_DEFAULT);
    }

    //Metodo para pedir confirmacion antes de gastar dinero
    private boolean mostrarConfirmacion(String titulo, String cabecera, String contenido) {
        //ventana del tipo confirmacion (con botones de aceptar/cancelar)
        Alert alerta = new Alert(AlertType.CONFIRMATION); 
        
        //Textos de la ventana
        alerta.setTitle(titulo); 
        alerta.setHeaderText(cabecera); 
        alerta.setContentText(contenido); 
        
        //Parte visual de la alerta
        try {
            String rutaIcono = new File("imgs/Login/Login-icon.png").toURI().toString();
            ImageView icono = new ImageView(new Image(rutaIcono));
            icono.setFitHeight(50);
            icono.setFitWidth(50);
            alerta.setGraphic(icono); 
        } catch (Exception e) {
            System.out.println("error cargando el icono de alerta");
        }
        
        DialogPane dialogPane = alerta.getDialogPane(); 
        
        //Icono esquina superior izquierda
        try {
            Stage stage = (Stage) dialogPane.getScene().getWindow();
            stage.getIcons().add(new Image(new File("imgs/Login/Login-icon.png").toURI().toString()));
        } catch (Exception e) {
            System.out.println("error cargando el icono superior");
        }
        
        //Aplicamos el estilo css de tus alertas
        dialogPane.getStylesheets().add(getClass().getResource("/view/captura/alertas.css").toExternalForm()); 
        
        //Esperamos la respuesta del usuario
        Optional<ButtonType> resultado = alerta.showAndWait();
        
        //Devuelve true solo si pulsa el boton "OK" / "Aceptar"
        return resultado.isPresent() && resultado.get() == ButtonType.OK;
    }

    //Logica de la lista lateral (parte grafica con ayuda de Gemini xDD)

    private void configurarDiseñoLista() {
        listaPokemon.setCellFactory(param -> new ListCell<Pokemon>() {
            
        	@Override
            protected void updateItem(Pokemon pokemon, boolean empty) {
                super.updateItem(pokemon, empty);

                if (empty || pokemon == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    //Creamos una cajita horizontal
                    HBox fila = new HBox(15); 
                    fila.setAlignment(javafx.geometry.Pos.CENTER_LEFT);
                    fila.setPadding(new javafx.geometry.Insets(10, 10, 10, 10)); 
                    
                    //Limitamos el ancho de la fila al ancho de la lista para eliminar barra scroll horizontal
                    fila.setMaxWidth(listaPokemon.getWidth() - 20);
                    //Para que se adapte si cambias el tamaño de la ventana:
                    fila.prefWidthProperty().bind(listaPokemon.widthProperty().subtract(20));

                    //Creamos su imagen (Comprobando si es Shiny)
                    ImageView img = new ImageView();
                    img.setFitHeight(50);
                    img.setFitWidth(50);
                    try {
                        String ruta;
                        if (pokemon.getEsShiny() != null && pokemon.getEsShiny() == true) {
                            ruta = new File("imgs/Pokemons/shiny/" + pokemon.getImgFrontalPokemon()).toURI().toString();
                        } else {
                            ruta = new File("imgs/Pokemons/" + pokemon.getImgFrontalPokemon()).toURI().toString();
                        }
                        img.setImage(new Image(ruta));
                    } catch (Exception e) {}

                    //Creamos su nombre y nivel
                    VBox textos = new VBox(5); 
                    textos.setAlignment(javafx.geometry.Pos.CENTER_LEFT);
                    
                    //Comprobamos si tiene mote 
                    String nombreAMostrar;
                    if (pokemon.getMotePokemon() != null && !pokemon.getMotePokemon().trim().isEmpty()) {
                        nombreAMostrar = pokemon.getMotePokemon();
                    } else {
                        nombreAMostrar = pokemon.getNombrePokemon();
                    }
                                            
                    Label lblNombre = new Label(nombreAMostrar);
                    lblNombre.setStyle("-fx-font-weight: bold; -fx-text-fill: white; -fx-font-size: 16px;");
                    
                    Label lblNivel = new Label("Lvl " + pokemon.getNivel());
                    lblNivel.setStyle("-fx-text-fill: #cccccc; -fx-font-size: 14px;");
                    
                    textos.getChildren().addAll(lblNombre, lblNivel);

                    //Lo metemos todo en la cajita
                    fila.getChildren().addAll(img, textos);
                    
                    setGraphic(fila);
                }
            }
        });

        //Se ejecuta al hacer clic en un Pokemon del panel lateral
        listaPokemon.getSelectionModel().selectedItemProperty().addListener((observable, viejoPokemon, nuevoPokemon) -> {
            if (nuevoPokemon != null) {
                pokemonSeleccionado = nuevoPokemon;
                actualizarVistaCentral();
            }
        });
    }

    //Metodo para cargar en la lista 
    private void cargarPokemonEnLista() {
        ObservableList<Pokemon> pokemonParaMostrar = FXCollections.observableArrayList();
        
        //Añadir los del equipo 
        for (Pokemon p : entrenadorActual.getEquipoPokemon()) {
            if (p != null) {
                pokemonParaMostrar.add(p);
            }
        }
        
        //Añadir los de la caja 
        for (Pokemon p : entrenadorActual.getCajaPokemon()) {
            if (p != null) {
                pokemonParaMostrar.add(p);
            }
        }
        
        listaPokemon.setItems(pokemonParaMostrar);
    }
    
    //Metodo para elegir un pokemon al azar al entrar a la sala
    private void seleccionarPokemonAleatorio() {
        ObservableList<Pokemon> todosLosPokemon = listaPokemon.getItems();
        
        if (todosLosPokemon != null && !todosLosPokemon.isEmpty()) {
            Random rand = new Random();
            Pokemon aleatorio = todosLosPokemon.get(rand.nextInt(todosLosPokemon.size()));
            
            // Le decimos a la lista que seleccione este pokemon visualmente. 
            //se llama a actualizarVistaCentral automaticamente
            listaPokemon.getSelectionModel().select(aleatorio);
            
            // Si hay muchos, hace que la barra de scroll baje hasta 
            listaPokemon.scrollTo(aleatorio);
        }
    }
    
    //Metodo para actualizar el centro de la pantalla al hacer click en la lista
    private void actualizarVistaCentral() {
        if (pokemonSeleccionado == null) return;
        
        //Actualizamos Textos (Nombre y Nivel)
        if (txtNombreCentral != null) {
            String nombreAMostrar;
            if (pokemonSeleccionado.getMotePokemon() != null && !pokemonSeleccionado.getMotePokemon().trim().isEmpty()) {
                nombreAMostrar = pokemonSeleccionado.getMotePokemon();
            } else {
                nombreAMostrar = pokemonSeleccionado.getNombrePokemon();
            }
            txtNombreCentral.setText(nombreAMostrar);
        }
        
        if (txtNivelCentral != null) {
            txtNivelCentral.setText("Lvl " + pokemonSeleccionado.getNivel());
        }
        
        //Actualizamos Imagen Central y comprobamos si es SHINY
        if (imgPokemonCentral != null) {
            try {
                String rutaImagen;
                
                if (pokemonSeleccionado.getEsShiny() != null && pokemonSeleccionado.getEsShiny() == true) {
                    //mostramos el icono de Shiny
                    if (imgShiny != null) imgShiny.setVisible(true);
                    //cargamos la imagen desde la carpeta Shiny
                    rutaImagen = new File("imgs/Pokemons/shiny/" + pokemonSeleccionado.getImgFrontalPokemon()).toURI().toString();
                } else {
                    //ocultamos el icono de Shiny
                    if (imgShiny != null) imgShiny.setVisible(false);
                    //cargamos la imagen normal
                    rutaImagen = new File("imgs/Pokemons/" + pokemonSeleccionado.getImgFrontalPokemon()).toURI().toString();
                }
                
                imgPokemonCentral.setImage(new Image(rutaImagen));
            } catch (Exception e) {
                System.out.println("Error cargando imagen central: " + e.getMessage());
            }
        }
        
        //Actualizamos Imagen SEXO
        if (imgSexoPokemon != null && pokemonSeleccionado.getSexo() != null) {
            String rutaSexo = "";
            if (pokemonSeleccionado.getSexo() == model.Sexo.MACHO) {
                rutaSexo = "imgs/Entrenamiento/sexo/macho.png"; 
            } else if (pokemonSeleccionado.getSexo() == model.Sexo.HEMBRA) {
                rutaSexo = "imgs/Entrenamiento/sexo/Hembra.png";
            } else {
                rutaSexo = "imgs/Entrenamiento/sexo/Neutro.png";
            }
            
            File archivoSexo = new File(rutaSexo);
            if(archivoSexo.exists()) {
                imgSexoPokemon.setImage(new Image(archivoSexo.toURI().toString()));
            } else {
                System.out.println("ERROR SEXO: No encuentro la imagen en -> " + archivoSexo.getAbsolutePath());
                imgSexoPokemon.setImage(null);
            }
        }

        //Actualizamos Imagen TIPO 1
        if (imgTipo1Pokemon != null && pokemonSeleccionado.getTipoPrincipal() != null) {
            String nombreTipo = pokemonSeleccionado.getTipoPrincipal().name();
            File archivoTipo1 = new File("imgs/Entrenamiento/Tipos/" + nombreTipo + ".png"); 
            
            if(archivoTipo1.exists()) {
                imgTipo1Pokemon.setImage(new Image(archivoTipo1.toURI().toString()));
            } else {
                System.out.println("ERROR TIPO 1: No encuentro la imagen en -> " + archivoTipo1.getAbsolutePath());
                imgTipo1Pokemon.setImage(null);
            }
        }

        //Actualizamos Imagen TIPO 2
        if (imgTipo2Pokemon != null) {
            if (pokemonSeleccionado.getTipoSecundario() != null) {
                String nombreTipo2 = pokemonSeleccionado.getTipoSecundario().name();
                File archivoTipo2 = new File("imgs/Entrenamiento/Tipos/" + nombreTipo2 + ".png");
                
                if(archivoTipo2.exists()) {
                    imgTipo2Pokemon.setImage(new Image(archivoTipo2.toURI().toString()));
                    //Lo mostramos por si estaba oculto
                    imgTipo2Pokemon.setVisible(true); 
                } else {
                    System.out.println("ERROR TIPO 2: No encuentro la imagen en -> " + archivoTipo2.getAbsolutePath());
                    imgTipo2Pokemon.setImage(null);
                }
            } else {
                //Si no tiene segundo tipo, quitamos la imagen y la ocultamos
                imgTipo2Pokemon.setImage(null);
                imgTipo2Pokemon.setVisible(false);
            }
        }
        
        //Actualizamos las estadisticas (Stats) del Pokemon
        
        //Vitalidad (HP)
        if (txtHpPokemon != null) {
            txtHpPokemon.setText(String.valueOf(pokemonSeleccionado.getVitalidadMaxima()));
        }
        
        //Ataque
        if (txtAtkPokemon != null) {
            txtAtkPokemon.setText(String.valueOf(pokemonSeleccionado.getAtaque()));
        }
        
        //Defensa
        if (txtDefensaPokemon != null) {
            txtDefensaPokemon.setText(String.valueOf(pokemonSeleccionado.getDefensa()));
        }
        
        //Ataque Especial
        if (txtAtkSpPokemon != null) {
            txtAtkSpPokemon.setText(String.valueOf(pokemonSeleccionado.getAtaqueEspecial()));
        }
        
        //Defensa Especial
        if (txtDefensaSpPokemon != null) {
            txtDefensaSpPokemon.setText(String.valueOf(pokemonSeleccionado.getDefensaEspecial()));
        }
        
        //Velocidad
        if (txtVelocidadPokemon != null) {
            txtVelocidadPokemon.setText(String.valueOf(pokemonSeleccionado.getVelocidad()));
        }
    }

    //Boton de salir
    @FXML
    public void entrenamientoSalir(MouseEvent event) {
        try {
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

    //Metodo para mostrar pop-up con mensajes
    private void mostrarAlerta(String titulo, String cabecera, String contenido, AlertType tipo) {
        //ventana del tipo que pasemos informacion, advertencia... lo que queramos
        Alert alerta = new Alert(tipo); 
        
        //Textos de la ventana
        alerta.setTitle(titulo); 
        alerta.setHeaderText(cabecera); 
        alerta.setContentText(contenido); 
        
        //Parte visual de la alerta
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
            dialogPane.getStylesheets().add(getClass().getResource("/view/captura/alertas.css").toExternalForm()); 
        }
        
        alerta.showAndWait(); 
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {

        // Mostrar entrenador actual
        System.out.println("Entrenador en Entrenamiento: " + entrenadorActual.getNombreEntrenador());

        // ponemos el fondo default al principio
        cambiarFondoPantalla(FONDO_DEFAULT);

        // Preparamos el diseño visual del ListView ANTES de cargar los datos
        configurarDiseñoLista();
        
        // Conectamos BD y cargamos la info del entrenador (y selecciona el aleatorio)
        setEntrenador();
    }
}