package controller;

import bd.ConexionBBDD;
import dao.InventarioDAO;
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
import javafx.stage.Stage;
import model.Entrenador;
import model.Objeto;
import model.ObjetosTotales;
import model.Sesion;

import java.io.File;
import java.net.URL;
import java.sql.Connection;
import java.util.ResourceBundle;

import static dao.InventarioDAO.cargarObjetosTotales;

public class InventarioController implements Initializable {

    // Cargamos el entrenador logueado

    private Entrenador entrenadorActual = Sesion.entrenadorLogueado;


    // Caja de objetos

    @FXML
    private TilePane cajaObjetos;

    private Connection con;

    public void initialize(URL location, ResourceBundle resources) {

        // Cargar objetos al entrar

        cargarObjetos();

    }

    /**
     * Method para generar las celdas de los objetos disponibles en la BBDD en la caja de objetos
     * Considero que este metodo es mejor, puesto que en función de si tienes o no el objeto, se verá
     * de una forma u de otra, además de que se tiene que ver el objeto y la cantidad de items, etc..,
     * cosa que prefiero hacer directamente con el código */


    public void cargarObjetos () {
        try {
            // Limpiar la caja por si tenia algo antes

            cajaObjetos.getChildren().clear();



            // Instanciamos la conexion con la BBDD

            ConexionBBDD conexion = new ConexionBBDD();

            this.con = conexion.getConexion();
            cargarObjetosTotales(con);

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

                    /**
                     * Si la imagen es nula, entonces poner una imagen por defecto
                     * */

                    if (o.getImgObjeto().equals("vacio")){
                        System.out.println("Se ha generado un VBOX, pero la imagen es nula");
                    }

                    /**
                     * Caso contrario, se carga su imagen
                     * */


                    String rutaImagen = "";

                    rutaImagen = "imgs/Inventario/objetos/" + o.getImgObjeto();
                    System.out.println(rutaImagen);

                    String rutaImagenAdaptada = new File(rutaImagen).toURI().toString();
                    ImageView vistaImagen = new ImageView(new Image(rutaImagenAdaptada));

                    vistaImagen.setFitWidth(96);
                    vistaImagen.setFitHeight(96);
                    vistaImagen.setPreserveRatio(false);

                    // Ahora añadir la vistaImagen a la celda

                    celdaObjeto.getChildren().add(vistaImagen);

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
