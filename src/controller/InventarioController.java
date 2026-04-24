package controller;

import bd.ConexionBBDD;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.Entrenador;
import model.Objeto;
import model.ObjetosTotales;
import model.Sesion;

import java.io.File;
import java.net.URL;
import java.sql.Connection;
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

    private Connection con;


    public void initialize(URL location, ResourceBundle resources) {

        // Cargar objetos al entrar
        cargarObjetos();

        cargarObjetosEntrenador();
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


            // Instanciamos la conexion con la BBDD

            ConexionBBDD conexion = new ConexionBBDD();

            this.con = conexion.getConexion();
            cargarObjetosTotales(con);
            cargarInventario(entrenadorActual.getIdEntrenador(), con);
            cajaObjetos.setAlignment(Pos.TOP_LEFT);
            cajaObjetos.setPadding(new Insets(50, 40, 30, 60));


            for (Objeto o : ObjetosTotales.todosLosObjetos) {
                try {

                    /**
                     * Crear un Vbox para el objeto, donde estará dentro su imagen */

                    VBox celdaObjeto = new VBox(-2);

                    // Darle estilo a la celda:
                    celdaObjeto.setStyle(
                            "-fx-background-color: rgba(255, 255, 255, 0.4);" +
                                    "-fx-background-radius: 15;" +
                                    "-fx-cursor: hand;" +
                                    "-fx-padding: 10;" +
                                    "-fx-border-insets: 5px;" +
                                    "-fx-background-insets: 5px;"
                    );

                    ImageView img = cargarImagen(o.getImgObjeto());

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

            cargarInfoObjeto(ObjetosTotales.todosLosObjetos.get(0));


        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    // Method para cargar la lista de objetos del entrenador

    public void cargarObjetosEntrenador() {
        try {
            System.out.println(entrenadorActual.getInventario().toString());
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

        txtPrecioObjeto.setText(String.valueOf(objeto.getPrecio()));

        // Ahora la descripción

        txtDescObjeto.setText(objeto.getDescripcionObjeto());

        // Luego pongo la cantidad, me falta conectar eso

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
