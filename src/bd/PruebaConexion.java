package bd;

import java.sql.Connection;

public class PruebaConexion {
    public static void main(String[] args) {
        // 1. Instanciamos tu clase
        ConexionBBDD conector = new ConexionBBDD();
        
        // 2. Intentamos obtener la conexión
        Connection miConexion = conector.getConexion();
        
        // 3. Verificamos si es nula o no
        if (miConexion != null) {
            System.out.println("✅ ¡ÉXITO! Se ha conectado a PROYECTO_POKEMON.");
        } else {
            System.err.println("❌ ERROR: La conexión es null. Revisa los logs de arriba.");
        }
    }
}