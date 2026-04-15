package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.Entrenador;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.util.ResourceBundle;

import bd.ConexionBBDD;

public class CasinoController implements Initializable {
	
	private Entrenador entrenadorActual;
	private Connection con;
	
	//Metodo para conexion entrenador con BD
	public void setEntrenador(Entrenador e) {
		this.entrenadorActual = e;
		
		// Conectar a BD
        ConexionBBDD conector = new ConexionBBDD();
        this.con = conector.getConexion();
	}

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    // metodo para el botón RULETA
    @FXML
    private void abrirRuleta(ActionEvent event) {
        cambiarEscena(event, "/view/casino/Ruleta.fxml", "Casino - Ruleta");
    }

    // metodo para el botón COIN FLIP
    @FXML
    private void abrirCoinFlip(ActionEvent event) {
        cambiarEscena(event, "/view/casino/Moneda.fxml", "Casino - Coin Flip");
    }

    // metodo para el botón NÚMERO SECRETO
    @FXML
    private void abrirNumeroSecreto(ActionEvent event) {
        cambiarEscena(event, "/view/casino/Numero.fxml", "Casino - Número Secreto");
    }

    //metodo auxiliar para no repetir codigo al cambiar de ventana
    private void cambiarEscena(ActionEvent event, String fxmlPath, String titulo) {
        try {
            // cargamos el archivo FXML del juego seleccionado
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();

            // obtenemos la ventana actual a partir del botón pulsado
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // cambiamos la escena
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle(titulo);
            stage.show();

        } catch (IOException e) {
            System.err.println("Error al cargar la vista: " + fxmlPath);
            e.printStackTrace();
        }
    }
    
    @FXML
    // metodo para el boton para salir al menu principal
    void clickSalir(MouseEvent event) { 
        try {
            // cargamos el FXML del menu principal
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/principal/vistaPrincipal.fxml"));
            Parent root = loader.load();

         // obtenemos la ventana actual a partir del boton pulsado
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // creamos la escena con el tamaño original del menú principal
            Scene scene = new Scene(root, 1074, 607);
            
            //Cargar el entrenador
            MainController mainCtrl = loader.getController();
            mainCtrl.setEntrenador(this.entrenadorActual);
            
            stage.setScene(scene);
            stage.setTitle("Pokémon - Menú Principal");
            stage.centerOnScreen();
            stage.show();

        } catch (IOException e) {
            System.err.println("Error al intentar volver al Menú Principal: " + e.getMessage());
            e.printStackTrace();
        }
    }
}