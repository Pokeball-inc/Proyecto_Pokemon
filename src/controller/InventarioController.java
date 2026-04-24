package controller;

import bd.ConexionBBDD;
import dao.EntrenadorDAO;
import dao.InventarioDAO;
import dao.PokemonDAO;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.DialogPane;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.*;

import java.io.File;
import java.net.URL;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import static dao.InventarioDAO.cargarInventario;
import static dao.InventarioDAO.cargarObjetosTotales;

public class InventarioController implements Initializable {

    // Cargamos el entrenador logueado

    private Entrenador entrenadorActual = Sesion.entrenadorLogueado;


    // Caja de objetos

    @FXML
    private TilePane cajaObjetos;
    @FXML
    private ImageView fotoObjeto;
    @FXML
    private Text txtnombreObjeto;
    @FXML
    private Text txtcantidadObjeto;
    @FXML
    private Text txtPrecioObjeto;
    @FXML
    private Text txtDescObjeto;
    @FXML
    private Pane usarObjeto;
    @FXML
    private Pane comprarObjeto;
    @FXML
    private Text textSaldo;
    @FXML
    private Pane panelDetalles;
    @FXML
    private javafx.scene.layout.Pane panelSeleccion;
    @FXML
    private javafx.scene.layout.TilePane contenedorPokemons;


    private Connection con;
    private List<Pokemon> ListaPokemon = new ArrayList<>();


    public void initialize(URL location, ResourceBundle resources) {

        ConexionBBDD conexion = new ConexionBBDD();

        this.con = conexion.getConexion();

        // cargar los objetosTotales

        cargarObjetosTotales(con);

        // Cargar objetos al entrar
        cargarObjetos();

        cargarInfoObjeto(model.ObjetosTotales.todosLosObjetos.get(0));
    }

    /**
     * Method para generar las celdas de los objetos disponibles en la BBDD en la caja de objetos
     * Considero que este metodo es mejor, puesto que en función de si tienes o no el objeto, se verá
     * de una forma u de otra, además de que se tiene que ver el objeto y la cantidad de items, etc..,
     * cosa que prefiero hacer directamente con el código
     */


    public void cargarObjetos() {
        try {
            // Limpiar la caja por si tenia algo antes

            cajaObjetos.getChildren().clear();


            textSaldo.setText(String.valueOf("Disponibles: " + entrenadorActual.getPokedollares()) + " Pd");


            // Cargar los datos de los pokemons actualizados

            cargarObjetosTotales(con);
            PokemonDAO.obtenerPokemon(con, entrenadorActual, UbicacionPokemon.CAJA);
            PokemonDAO.obtenerPokemon(con, entrenadorActual, UbicacionPokemon.EQUIPO);

            cargarInventario(entrenadorActual.getIdEntrenador(), con);
            cajaObjetos.setAlignment(Pos.TOP_LEFT);
            cajaObjetos.setPadding(new Insets(50, 40, 30, 60));

            for (Objeto o : ObjetosTotales.todosLosObjetos) {
                try {
                    /**
                     * Crear un Vbox para el objeto, donde estará dentro su imagen */

                    VBox celdaObjeto = new VBox(-2);

                    // Darle estilo a la celda:
                    celdaObjeto.setStyle("-fx-background-color: rgba(255, 255, 255, 0.4);" + "-fx-background-radius: 15;" + "-fx-cursor: hand;" + "-fx-padding: 10;" + "-fx-border-insets: 5px;" + "-fx-background-insets: 5px;");


                    ImageView img = cargarImagen(o.getImgObjeto());

                    // Uso de streams, para revisar si el objeto de la iteración y el de la lista completa son iguales

                    boolean existe = entrenadorActual.getInventario().getListaObjetos().stream().anyMatch(objetoInventario -> objetoInventario.getObjeto().getIdObjeto() == o.getIdObjeto());

                    // Si el entrenador no lo tiene;

                    if (!existe) {
                        // EFECTO BLANCO Y NEGRO (O ALGO ASI)

                        ColorAdjust ca = new ColorAdjust();
                        ca.setContrast(-0.52);
                        ca.setSaturation(-0.92);

                        img.setEffect(ca);
                    }

                    // Ahora añadir la vistaImagen a la celda

                    celdaObjeto.getChildren().add(img);
                    celdaObjeto.setOnMouseClicked(event -> {
                        try {
                            System.out.println("Se ha clickeado en el objeto " + o.getNombreObjeto());

                            // Method para cargar la información del objeto

                            cargarInfoObjeto(o);

                        } catch (Exception e) {
                            System.out.println("Error al dar click en InventarioController: " + e.getMessage());
                        }
                    });

                    // Y añadir la celda a la caja de Objetos

                    cajaObjetos.getChildren().addAll(celdaObjeto);


                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }


        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    // Method para cargar la info del objeto clickeado en el panel

    public void cargarInfoObjeto(Objeto objeto) {

        // Cambiar la imagen del ImageView al del objeto

        fotoObjeto.setImage(cargarImagen(objeto.getImgObjeto()).getImage());
        fotoObjeto.setPreserveRatio(true);
        fotoObjeto.setFitHeight(80);
        fotoObjeto.setFitWidth(80);

        // Ahora el nombre del objeto

        txtnombreObjeto.setText(objeto.getNombreObjeto());

        txtnombreObjeto.autosize();

        // Ahora el precio del objeto

        txtPrecioObjeto.setText(String.valueOf(objeto.getPrecio()) + " Pd");

        // Ahora la descripción

        txtDescObjeto.setText(objeto.getDescripcionObjeto());

        // Ahora la cantidad

        int cantidad = entrenadorActual.getInventario().getListaObjetos().stream().filter(objetoInventario -> objetoInventario.getObjeto().getIdObjeto() == objeto.getIdObjeto())
                .mapToInt(objetoInventario -> objetoInventario.getCantidad()).sum();

        // El sum devuelve 0 si no encuentra nada en el stream, mejor que un if xd

        txtcantidadObjeto.setText(String.valueOf("x" + cantidad));


        // Modificar botones de Comprar y Usar en función de la cantidad que poseas


            usarObjeto.setVisible(true);

            /// El botón de comprar centrado

            Rectangle rectComprar = (Rectangle) comprarObjeto.getChildren().get(0);
            comprarObjeto.setLayoutX(33);
            comprarObjeto.setLayoutY(404);
            comprarObjeto.setPrefHeight(Region.USE_COMPUTED_SIZE);
            comprarObjeto.setPrefWidth(Region.USE_COMPUTED_SIZE);
            rectComprar.setWidth(102);
            rectComprar.setHeight(37);
            rectComprar.setLayoutX(0);
            rectComprar.setLayoutY(0);

        ///  Evento de mouse para comprar

        comprarObjeto.setOnMouseClicked(event -> {
            if (entrenadorActual.getPokedollares() >= objeto.getPrecio()) {

                int nuevoSaldo = entrenadorActual.getPokedollares() - objeto.getPrecio();

                entrenadorActual.setPokedollares(nuevoSaldo);

                entrenadorActual.getInventario().añadirObjeto(objeto, 1);

                new EntrenadorDAO().actualizarPokedollares(entrenadorActual.getIdEntrenador(), nuevoSaldo);
                InventarioDAO.actualizarInventario(con);

                System.out.println("Has comprado "+ objeto.getNombreObjeto() +" por "+ String.valueOf(objeto.getPrecio()) + "!");

                /// Recargar

                cargarObjetos();

                ///  Mostrar último objeto comprado
                cargarInfoObjeto(objeto);
            } else {

                /// Aviso de saldo insuficiente

                mostrarAlerta("¡Error al comprar objeto!", "No tienes suficiente saldo para realizar esta compra",
                        "Saldo necesario: "+ String.valueOf(objeto.getPrecio()) + "\nTu saldo: " + String.valueOf(entrenadorActual.getPokedollares()), Alert.AlertType.WARNING );
            }
        });


        /// Evento de Mouse para equipar o desequipar un objeto

        usarObjeto.setOnMouseClicked(event -> {
           abrirPanelSeleccion(ListaPokemon, objeto);
        });




    }






    ///  Method para cargar un Panel donde salgan todos los pokemons
    private void abrirPanelSeleccion(List<Pokemon> listaMostrar, Objeto objeto) {

        // Cargar todos los pokemons

        ListaPokemon.clear();
        if (entrenadorActual.getEquipoPokemon() != null) {
            for (Pokemon p : entrenadorActual.getEquipoPokemon()) {
                if (p != null) ListaPokemon.add(p);
            }
        }
        if (entrenadorActual.getCajaPokemon() != null) {
            ListaPokemon.addAll(entrenadorActual.getCajaPokemon());
        }

        contenedorPokemons.getChildren().clear();

        //Vaciamos lo que hubiera de antes en la cuadricula
        contenedorPokemons.getChildren().clear();

        //Por cada Pokemon en la lista, creamos su miniatura
        for (Pokemon p : ListaPokemon) {
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
                // En función del Tipo de imagen que desee el usuario

                String rutaImagen = "";

                if (Sesion.vista2D) {

                    rutaImagen = "imgs/Pokemons/sprites/crystal/transparent/" + p.getImgFrontalPokemon();

                } else {
                    rutaImagen = "imgs/Pokemons/sprites/crystal/transparent/" + p.getImgFrontalPokemon3D();
                }

                String rutaImagenAdaptada = new File(rutaImagen).toURI().toString();
                imgPoke.setImage(new Image(rutaImagenAdaptada));


                ///  Ahora añadir que al darle click, se equipe o desequipe el objeto en cuestion

                cajaPokemon.setOnMouseClicked(event -> {

                    System.out.println("Has seleccionado a "+ p.getMotePokemon());

                    if (p.getObjetoEquipado() != null) {
                        System.out.println("Tiene equipado -> " + p.getObjetoEquipado().getNombreObjeto());
                    } else {
                        System.out.println("Objeto nulo");
                    }

                });
            }
            catch (Exception e) {
                System.out.println(e.getMessage());
            }

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

            //Añadimos la cajita a la cuadricula general
            contenedorPokemons.getChildren().add(cajaPokemon);

            // Lógica para añadir o quitar objetos en funcion

            cajaPokemon.setOnMouseClicked(event -> {

                // Si el pokemon ya tiene un objeto equipado, se desequipa

               if (p.getObjetoEquipado() != null) {

                   Objeto objetoAQuitar = p.getObjetoEquipado();

                   if (objetoAQuitar == objeto) {

                       // Añadirlo al inventario del entrenador

                       entrenadorActual.getInventario().añadirObjeto(objetoAQuitar,  1);

                       System.out.println("Se ha desequipado el objeto " + objeto.getNombreObjeto());

                       PokemonDAO.actualizarObjetoPokemon(con, p, null);
                   } else {

                       // Añadirlo al inventario del entrenador

                       entrenadorActual.getInventario().añadirObjeto(objetoAQuitar,  1);

                       PokemonDAO.actualizarObjetoPokemon(con, p, null);

                       // Recuperamos la cantidad de objetos disponibles

                       int cantidadDisponible = entrenadorActual.getInventario().getListaObjetos().stream().filter(objetoInventario -> objetoInventario.getObjeto().getIdObjeto() == objeto.getIdObjeto())
                               .mapToInt(objetoInventario -> objetoInventario.getCantidad()).sum();

                       if (cantidadDisponible > 0) {
                           entrenadorActual.getInventario().añadirObjeto(objeto, -1);
                       } else {
                           return;
                       }

                       // Y lo añadimos

                       PokemonDAO.actualizarObjetoPokemon(con, p, objeto);


                       System.out.println("Objeto "+ objeto.getNombreObjeto() + " Equipado.");

                   }

               } else {

                   // Recuperamos la cantidad de objetos disponibles

                   int cantidadDisponible = entrenadorActual.getInventario().getListaObjetos().stream().filter(objetoInventario -> objetoInventario.getObjeto().getIdObjeto() == objeto.getIdObjeto())
                           .mapToInt(objetoInventario -> objetoInventario.getCantidad()).sum();

                   if (cantidadDisponible > 0) {
                       entrenadorActual.getInventario().añadirObjeto(objeto, -1);
                   } else {
                       return;
                   }

                   // Y lo añadimos

                   PokemonDAO.actualizarObjetoPokemon(con, p, objeto);


                   System.out.println("Objeto "+ objeto.getNombreObjeto() + " Equipado.");
               }

               InventarioDAO.actualizarInventario(con);

               panelSeleccion.setVisible(false);

               cargarObjetos();
               cargarInfoObjeto(objeto);

            });
        }

        //Mostramos el panel gigante y lo traemos al frente
        panelSeleccion.setVisible(true);
        panelSeleccion.toFront();
    }


    /**
     * Boton/Accion para cerrar la caja de seleccion sin haber elegido ningun Pokemon.
     * @param event El evento de raton
     */
    @FXML
    public void cerrarSeleccion(MouseEvent event) {
        panelSeleccion.setVisible(false);
    }



    // Method para cargar la imagen de un objeto, si ya me canse de repetir codigo xd

    public ImageView cargarImagen(String rutaImagen) {

        if (rutaImagen.equals("vacio") || rutaImagen.equals("")) {
            rutaImagen = "vacio.png";
        }
        rutaImagen = "imgs/Inventario/objetos/" + rutaImagen;


        String rutaImagenAdaptada = new File(rutaImagen).toURI().toString();
        ImageView vistaImagen = new ImageView(new Image(rutaImagenAdaptada));

        vistaImagen.setFitWidth(96);
        vistaImagen.setFitHeight(96);
        vistaImagen.setPreserveRatio(false);


        return vistaImagen;
    }

    //Metodo para mostrar pop-up con mensajes

    private void mostrarAlerta(String titulo, String cabecera, String contenido, Alert.AlertType tipo) {
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
        if(tipo == Alert.AlertType.WARNING) {
            dialogPane.getStylesheets().add(getClass().getResource("/view/captura/alertas2.css").toExternalForm());
        } else {
            dialogPane.getStylesheets().add(getClass().getResource("/view/captura/alertas.css").toExternalForm()); //Enlace con archivo alertas css personalizado
        }



        alerta.showAndWait(); //muestra la ventana en la pantalla y para el codigo hasta que se acepta o se cierra la ventana

    }

    //Boton de salir
    @FXML
    public void inventarioSalir(MouseEvent event) {
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
}
