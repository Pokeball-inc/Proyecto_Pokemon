package model;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Class Log
 */
public class Log {

    //
    // Fields
    //

    /**
     * el nombre del log
     */
    private String nombreLog;
    /**
     * lista con todos los turbnos realizados
     */
    private List<Turno> turnosLog;

    //
    // Constructors
    //
    public Log() {
        this.turnosLog = new ArrayList<>();
    }

    ;

    //
    // Methods
    //


    public void setNombreLog(String newVar) {
        nombreLog = newVar;
    }

    public String getNombreLog() {
        return nombreLog;
    }

    //
    // Other methods
    //


    /**
     * Method para generar el fichero con los logs
     */
    public void generarFicheroLog() {

        /// Cambiar el formato de la fecha y horas - Esto para el nombre del archivo

        DateTimeFormatter formatoTiempos = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        String fechaNombre = LocalDateTime.now().format(formatoTiempos);

        /// Ahora para el contenido del log

        DateTimeFormatter formatoContenido = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");

        File carpeta = new File("logs");
        if (!carpeta.exists()) {
            carpeta.mkdir();
        }

        File archivo = new File(carpeta, fechaNombre + ".log");

        /// Ahora la lógica de escritura

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(archivo))) {
            for (Turno turno : turnosLog) {
                String tiempoLog = LocalDateTime.now().format(formatoContenido);
                String linea = String.format("%s INFO %s pokemon={%s}, pokemonRival={%s}, turno=%d",
                        tiempoLog,
                        turno.getAccionEntrenador(),
                        turno.getDatosPokemon1(),
                        turno.getDatosPokemon2(),
                        turno.getNumeroTurnoActual()
                );
                writer.write(linea);
                writer.newLine();
            }
            System.out.println("Se ha generado el LOG -> " + archivo.getName() + " en la ruta: " + archivo.getAbsolutePath());
        } catch (IOException e) {
            System.out.println("Error al generar el LOG -> " + archivo.getName());
        }


    }

    ///  Method para añadir turno desde el combate

    public void añadirTurno(Turno turno) {
        this.turnosLog.add(turno);
    }


}
