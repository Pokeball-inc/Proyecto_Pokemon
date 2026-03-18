package model;

// Con esta clase se guarda y carga la información relacionada con la mecánica de cambio de imagenes en la pantalla principal
// para el acceso a las diferentes vistas.

public class Seccion {
    private int id;
    private String nombre;
    private String rutaImagen; // La ruta de la imagen de la vista
    private String rutaFXML; // La ruta de la vista de la imagen
    private String rutaCSS; // La ruta del CSS de la vista de la imagen

    // Constructor por defecto

    public  Seccion() {
        int id = 0;
        String nombre = "";
        String rutaImagen = "";
        String rutaFXML = "";
        String rutaCSS = "";
    }

    // Constructor parametrizado

    public Seccion(int id,  String nombre, String rutaImagen, String rutaFXML, String rutaCSS) {
        this.id = id;
        this.nombre = nombre;
        this.rutaImagen = rutaImagen;
        this.rutaFXML = rutaFXML;
        this.rutaCSS = rutaCSS;
    }

    // Constructor copia

    public Seccion(Seccion c) {
        this.id = c.id;
        this.nombre = c.nombre;
        this.rutaImagen = c.rutaImagen;
        this.rutaFXML = c.rutaFXML;
        this.rutaCSS = c.rutaCSS;
    }

    // GETTERS Y SETTERS


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getRutaImagen() {
        return rutaImagen;
    }

    public void setRutaImagen(String rutaImagen) {
        this.rutaImagen = rutaImagen;
    }

    public String getRutaFXML() {
        return rutaFXML;
    }

    public void setRutaFXML(String rutaFXML) {
        this.rutaFXML = rutaFXML;
    }

    public String getRutaCSS() {
        return rutaCSS;
    }

    public void setRutaCSS(String rutaCSS) {
        this.rutaCSS = rutaCSS;
    }

    // Metodo toString();
    @Override
    public String toString() {
        return super.toString();
    }
}


