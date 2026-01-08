package model;

public class Pokemon {
	private String nombre;
	private String mote;
	private double vitalidad;
	private double ataque;
	private double defensa;
	private double ataqueEspecial;
	private double defensaEspecial;
	private double velocidad;
	private double estamina;
	private int nivel;
	private Movimiento[] movimientos = new Movimiento[4];
	private Tipo[] tipos = new Tipo[2];
	private double fertilidad;
	private Objeto objeto;

	public enum Sexo {
		MACHO, HEMBRA
	}

	public enum Tipo {
		AGUA, BICHO, DRAGON, FANTASMA, ELECTRICO, FUEGO, HIELO, LUCHA, NORMAL, PLANTA, PSIQUICO, ROCA, VENENO, TIERRA,
		VOLADOR
	}

	public enum Estado {
		PARALIZADO, QUEMADO, ENVENENADO, GR_ENVENENADO, DORMIDO, CONGELADO, HELADO, SOMNOLIENTO, POKERUS, DEBILITADO,
		CONFUSO, ENAMORADO, ATRAPADO, MALDITO, DRENADORAS, CANTO_MORTAL, CENTRO_DE_ATENCION, AMEDRENTADO

	}
	
	
}
