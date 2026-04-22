package model;

public class Sesion {
	// creacion clase sesion para mantenerla mientras estamos logueados que guarde al entrenador obtenido
	public static Entrenador entrenadorLogueado;
	public static void logOut() {
		entrenadorLogueado = null;
	}
}
