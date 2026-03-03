package model;


/**
 * Class CaraCruz
 */
class CaraCruz extends Casino {
	
	public enum Eleccion{
		CARA,
		CRUZ
	}

	private int apuesta;


    // Constructor defecto
    public CaraCruz() {
        super();
    }

    // Constructor con entrenador
    public CaraCruz(Entrenador entrenador) {
        super(entrenador);
    }

    // Constructor copia
    public CaraCruz(CaraCruz otro) {
        super(otro);
    }
	
	
    // metodo para jugar el cara o cruz
    public String jugarMoneda(Eleccion eleccionJugador, int apuesta) {
    	Entrenador entrenador = getEntrenador();
    	
    	if( entrenador == null) {
    		return "No se encuentra al entrenador.";
    		}
    	if(apuesta <= 0 || apuesta > entrenador.getPokedollares()) {
    		return "Pokedóllares insuficientes.";
    	}
    	
    	costeApuesta(apuesta);
    	
    	int num = (int)(Math.random() * 2);
    	
    	Eleccion resultado;
    	
    	if(num == 0) {
    		resultado = Eleccion.CARA;
    	}
    	else {
    		resultado = Eleccion.CRUZ;
    	}
    	
    	if(resultado == eleccionJugador) {
    		cantidadPremio(apuesta);
    		return "Ha salido " + resultado + ". ¡Has ganado! Ahora tienes: " + entrenador.getPokedollares() + " Pokedólares";
    	}
    	else {
    		return "Ha salido " + resultado + ". ¡Has perdido! Ahora tienes: " + entrenador.getPokedollares() + " Pokedólares";
    	}
    }
    
    @Override
    public void costeApuesta(int apuesta) {
    	Entrenador entrenador = getEntrenador();
    	entrenador.setPokedollares(entrenador.getPokedollares() - apuesta);
    }
    
    @Override
    public void cantidadPremio(int apuesta) {
    	Entrenador entrenador = getEntrenador();
    	entrenador.setPokedollares(entrenador.getPokedollares() + (apuesta * 2));
    }
      
    
    
    


}
