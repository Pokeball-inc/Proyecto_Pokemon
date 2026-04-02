package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.event.ActionEvent;

public class CasinoNumeroController {

	

	@FXML
	private Label lblIntentos;

	private int intentosRestantes = 5; 
	
	
	
	@FXML
	// evento actualizar intentos 
	void onAdivinarClick(ActionEvent event) {
	    intentosRestantes--; // Restamos un intento

	    // Actualizamos lo que se muestra
	    lblIntentos.setText("Intentos restantes: " + intentosRestantes);

	    if (intentosRestantes <= 0) {
	        System.out.println("¡Game Over!");
	        // TODO ver la logica y adaptar al controllador 
	    }
	}
	
	
	/*
@FXML
private TextField txtNumero;

@FXML
void onAdivinarClick(ActionEvent event) {
    try {
        // Obtenemos el texto y lo pasamos a número
        int numeroUsuario = Integer.parseInt(txtNumero.getText());
        
        // Comparamos con el número secreto...
        if (numeroUsuario == numeroSecreto) {
            System.out.println("¡Ganaste!");
        }
        
        // Limpiamos el campo para el siguiente intento
        txtNumero.clear();
        
    } catch (NumberFormatException e) {
        // Por si el usuario escribe letras en lugar de números
        lblIntentos.setText("¡Escribe un número válido!");
    }
}
	 */
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
