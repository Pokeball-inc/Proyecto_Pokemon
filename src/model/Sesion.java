package model;

public class Sesion {
	// creacion clase sesion para mantenerla mientras estamos logueados que guarde al entrenador obtenido
	public static Entrenador entrenadorLogueado;
	public static Boolean vista2D = true;
	///para saber si el jugador se cura o no en la liga 
	public static boolean penalizacionCuraLiga = false;
	public static void logOut() {
		entrenadorLogueado = null;
	}
}
