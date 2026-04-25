package model;

public class Sesion {
    // creacion clase sesion para mantenerla mientras estamos logueados que guarde al entrenador obtenido
    public static Entrenador entrenadorLogueado;
    public static Boolean vista2D = true;

    // variables para liga
    // para saber si es combate de liga
    public static boolean modoLiga = false;
    // guarda el progreso de la liga actual
    public static LigaPokemon ligaActual = null;
    // para comprobar si nos curamos y cobrar menos
    public static boolean penalizacionLigaCura = false;


    public static void logOut() {
        entrenadorLogueado = null;

        // reestablezco las cosas de la liga
        modoLiga = false;
        ligaActual = null;
        penalizacionLigaCura = false;
    }
}
