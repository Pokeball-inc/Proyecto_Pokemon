package controller;

import java.io.File;
import java.net.URL;
import java.sql.Connection;
import java.util.ResourceBundle;

import bd.ConexionBBDD;
import dao.PokemonDAO;
import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.Entrenador;
import model.Estados;
import model.Movimiento;
import model.Pokemon;
import model.Sesion;

public class DescansoLigaController implements Initializable {

    @FXML 
    private Button btnSiguienteCombate;
    
    @FXML 
    private Button btnCurarPokemon;
    
    @FXML 
    private Label txtInfoDescanso;

    private Entrenador jugador;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        jugador = Sesion.entrenadorLogueado;
        
        if (txtInfoDescanso != null) {
            txtInfoDescanso.setText("¡Has ganado! ¿Qué deseas hacer antes del siguiente desafío?");
        }
    }
    
    //Cambiar tamaño de botones (gracias Elyass XDDD)
    
    // --------------- INCREMENTAR Y DISMINUIR TAMAÑO DEL BOTON ENTRENAR AL PASAR EL CURSOR ---------------
    
    //BOTON SIGUIENTE COMBATE 
    @FXML
    private void aumentarTamañoBotonSiguienteCombate(MouseEvent event) {
        btnSiguienteCombate.setScaleX(1.2);
        btnSiguienteCombate.setScaleY(1.2);
    }

    @FXML
    private void disminuirTamañoBotonSiguienteCombate(MouseEvent event) {
        	btnSiguienteCombate.setScaleX(1.0); 
        	btnSiguienteCombate.setScaleY(1.0); 
    }
    
    //BOTON CURAR
    @FXML
    private void aumentarTamañoBotonCurarPokemon(MouseEvent event) {
        btnCurarPokemon.setScaleX(btnCurarPokemon.getScaleX() + 0.2);
        btnCurarPokemon.setScaleY(btnCurarPokemon.getScaleY() + 0.2);
        
    }

    @FXML
    private void disminuirTamañoBotonCurarPokemon(MouseEvent event) {
        	btnCurarPokemon.setScaleX(1.0);
        	btnCurarPokemon.setScaleY(1.0);
    }

    /**
     * Cura a todo el equipo y tras una pausa envia al jugador al siguiente combate
     */
    @FXML
    public void curarEquipo(MouseEvent event) {
        
        if (jugador != null && jugador.getEquipoPokemon() != null) {
            
            ///logica de curacion (Vida, Estado y PP)
            for (int i = 0; i < 6; i = i + 1) {
                Pokemon p = jugador.getEquipoPokemon()[i];
                if (p != null) {
                    p.setVitalidad(p.getVitalidadMaxima());
                    p.setEstadoActual(Estados.SANO);
                    
                    if (p.getMovimientos() != null) {
                        for (int j = 0; j < 4; j++) {
                            Movimiento mov = p.getMovimientos()[j];
                            if (mov != null) {
                                mov.setCantidadMovimientos(mov.getCantidadMovimientosMaximos());
                            }
                        }
                    }
                }
            }
            
            ///guardar en la BD 
            guardarProgresoBD();
            
            
            ///feedback
            if (txtInfoDescanso != null) {
                txtInfoDescanso.setText("¡Equipo curado! Prepárate para el siguiente combate...");
            }
            
            ///bloqueamos botones para evitar errores
            btnCurarPokemon.setDisable(true);
            btnSiguienteCombate.setDisable(true);

            ///creamos una pausa para que el jugador vea que se ha curado 
            PauseTransition pausaTransicion = new PauseTransition(Duration.seconds(2));
            pausaTransicion.setOnFinished(e -> {
                ejecutarCambioEscena(event); ///llamamos al metodo que carga el combate
            });
            pausaTransicion.play();
        }
    }

    /**
     * Metodo auxiliar para no repetir codigo de carga de FXML
     */
    private void ejecutarCambioEscena(MouseEvent event) {
    	try {
            Stage ventanaActual = (Stage) ((Node) event.getSource()).getScene().getWindow();
            
            ///carga la vista
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/ligaPokemon/Liga.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            
            ///aplicar CSS
            try {
                String css = this.getClass().getResource("/view/principal/vistaPrincipal.css").toExternalForm();
                scene.getStylesheets().add(css);
            } catch (Exception ex) {
                System.out.println("Aviso: No se pudo cargar el CSS.");
            }

            ventanaActual.setTitle("PokeINC - Combate Liga");
            
            ///icono de la ventana
            File file = new File("imgs/Login/Login-icon.png");
            if (file.exists()) {
                ventanaActual.getIcons().add(new Image(file.toURI().toString()));
            }

            ventanaActual.setScene(scene);
            ventanaActual.show();
            
        } catch (Exception e) {
            System.out.println("ERROR al cargar la vista de la Liga: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Llama al cambio de escena y nos lleva al siguiente combate 
     */
    @FXML
    public void irSiguienteCombate(MouseEvent event) {
        ejecutarCambioEscena(event);
    }

    /**
     * Metodo de apoyo para guardar el equipo curado (Vida y PP) en la Base de Datos.
     */
    private void guardarProgresoBD() {
        try {
            ConexionBBDD conector = new bd.ConexionBBDD();
            Connection con = conector.getConexion();
            
            //instanciamos el DAO de movimientos para poder guardar los PP al maximo
            dao.MovimientoDAO movDao = new dao.MovimientoDAO(con);
            
            for (int i = 0; i < 6; i = i + 1) {
                Pokemon p = jugador.getEquipoPokemon()[i];
                if (p != null) {
                    //guarda la vida y las stats
                    dao.PokemonDAO.actualizarStatsBD(con, p);
                    
                    //guarda los PP curados de cada ataque en la BD
                    for (int j = 0; j < 4; j++) {
                        Movimiento mov = p.getMovimientos()[j];
                        if (mov != null) {
                            movDao.actualizarPPs(p.getIdPokemon(), mov.getIdMovimiento(), mov.getCantidadMovimientos());
                        }
                    }
                }
            }
            con.close();
            System.out.println("Equipo curado y guardado en BD correctamente.");
        } catch (Exception e) {
            System.out.println("ERROR al guardar la cura en BD: " + e.getMessage());
        }
    }
}