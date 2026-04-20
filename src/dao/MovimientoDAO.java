package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import model.Estados;
import model.Movimiento;
import model.Tipos;
import model.TiposMovimiento;

public class MovimientoDAO implements IMovimientoDAO {

	private Connection connection;
	
	@Override
	// metodo para obtener un movimiento por su ID
	public Movimiento buscarPorId(int id) {
		String sql = "SELECT * FROM MOVIMIENTO WHERE ID_MOVIMIENTO = ?";
		try (PreparedStatement ps = connection.prepareStatement(sql)) {
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				return obtenerMovimiento(rs);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;

	}
	
	
	 // metodo para obtener toda la informacion de la base de datos de movimiento
    public Movimiento obtenerMovimiento(ResultSet rs) throws SQLException {
        // creamos el objeto movimiento que vamos a ir rellenando
        Movimiento mov = new Movimiento();
        
        // sacamos los datos basicos de las columnas de la tabla y los metemos en el objeto
        mov.setIdMovimiento(rs.getInt("ID_MOVIMIENTO"));
        mov.setNombreMovimiento(rs.getString("NOM_MOVIMIENTO"));
        // pasamos la descripcion a minusculas para que luego buscar palabras sea mas facil
        String desc = rs.getString("DESCRIPCION").toLowerCase();
        mov.setDescripcionMovimiento(desc);
        mov.setPotencia(rs.getInt("POTENCIA"));
        mov.setTurnos(rs.getInt("TURNOS_EFECTO"));
        
        // para el tipo elemental, cogemos el string y lo convertimos al enum que tenemos
        mov.setTipo(Tipos.valueOf(rs.getString("TIPO_ELEMENTAL").toUpperCase()));

        // aqui empezamos clasificacion si es ataque, mejora o estado
        String categoriaBD = rs.getString("TIPO_CATEGORIA"); 

        if (categoriaBD.equalsIgnoreCase("Estado")) {
            // si en la descripción leemos que sube algo o restaura, es que nos mejora a nosotros
            if (desc.contains("sube") || desc.contains("aumenta") || desc.contains("restaura")) {
                mov.setTipoMovimiento(TiposMovimiento.MEJORA);
                // el estado que dejamos es sano
                mov.setEstadoAplicado(Estados.SANO); 
            } else {
                // si es estado, es que le vamos a afectar al rival
                mov.setTipoMovimiento(TiposMovimiento.ESTADO);
                // analizamos la descripción para ver qué estado le vamos a meter
                mov.setEstadoAplicado(identificarEstado(desc));
            }
        } else {
            // si la descripcion dice que es fisico o especial, lo tratamos directamente como un ataque de daño
            mov.setTipoMovimiento(TiposMovimiento.ATAQUE);
            // algunos ataques de daño también pueden meter estados secundarios
            mov.setEstadoAplicado(identificarEstado(desc));
        }

        // devolvemos el movimiento ya listo para usarse en el combate
        return mov;
    }
    
    // metodo para revisar las descripciones de los movimientos y ver que estado aplican
    private Estados identificarEstado(String desc) {
    	if (desc.contains("paralizar") || desc.contains("paraliza")) {
            return Estados.PARALIZADO;
        }
        if (desc.contains("quemar") || desc.contains("quema")) {
            return Estados.QUEMADO;
        }
        if (desc.contains("envenenar") || desc.contains("envenena") || desc.contains("veneno")) {
            if (desc.contains("gravemente")) {
                return Estados.GRAVEMENTEENMVENEDADO;
            } else {
                return Estados.ENVENENADO;
            }
        }
        if (desc.contains("dormir") || desc.contains("duerme") || desc.contains("sueño")) {
            return Estados.DORMIDO;
        }
        if (desc.contains("congelar") || desc.contains("congela")) {
            return Estados.CONGELADO;
        }
        if (desc.contains("helado")) {
            return Estados.HELADO;
        }
        if (desc.contains("confundir") || desc.contains("confunde") || desc.contains("mareo")) {
            return Estados.CONFUSO;
        }
        if (desc.contains("enamora") || desc.contains("atracción")) {
            return Estados.ENAMORADO;
        }
        if (desc.contains("atrapa") || desc.contains("oprime") || desc.contains("apresa")) {
            return Estados.APRESADO;
        }
        if (desc.contains("maldición") || desc.contains("maldice") || desc.contains("maldito")) {
            return Estados.MALDITO;
        }
        if (desc.contains("huida imposible")) {
            return Estados.HUIDAIMPOSIBLE;
        }
        if (desc.contains("drenadoras")) {
            return Estados.DRENADORAS;
        }
        if (desc.contains("canto mortal")) {
            return Estados.CANTOMORTAL;
        }
        if (desc.contains("amedrentar") || desc.contains("retroceder")) {
            return Estados.AMEDENTRADO;
        }
        if (desc.contains("somnoliento")) {
            return Estados.SOMNOLIENTO;
        }
        if (desc.contains("pokerus")) {
            return Estados.POKERUS;
        }
        if (desc.contains("debilitado")) {
            return Estados.DEBILITADO;
        }
        if (desc.contains("centro atención")) {
            return Estados.CENTROATENCION;
        }

        return Estados.SANO;
    }


	@Override
	public List<Movimiento> listarTodos() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Movimiento> listarPorTipo(Tipos tipo) {
		// TODO Auto-generated method stub
		return null;
	}

}
