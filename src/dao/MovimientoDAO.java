package dao;

import model.Estados;
import model.Movimiento;
import model.Tipos;
import model.TiposMovimiento;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MovimientoDAO implements IMovimientoDAO {

    private Connection connection;

    // cosntructor
    public MovimientoDAO(Connection connection) {
        this.connection = connection;
    }

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

            // para que se pueda establecer si es ata o def normal o especial
            mov.setCategoriaDano(categoriaBD);
            // algunos ataques de daño también pueden meter estados secundarios
            mov.setEstadoAplicado(identificarEstado(desc));
        }

        ///generamos los PP maximos y actuales automaticamente según su potencia
        mov.generarCantidadMovimientos();

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
    // metodo para listar todos los movimientos
    public List<Movimiento> listarTodos() {
        List<Movimiento> movimientos = new ArrayList<>();
        String sql = "SELECT * FROM MOVIMIENTO";
        // preparamos el trycatch
        try (PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            // recorremos todos los movimientos y los añadimos a la lista
            while (rs.next()) {
                movimientos.add(obtenerMovimiento(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return movimientos;
    }

    @Override
    // metodo para filtrar por el tipo de ataque respecto al tipo del pokemon
    public List<Movimiento> listarPorTipo(Tipos tipo) {
        List<Movimiento> lista = new ArrayList<>();
        // Buscamos en la tabla MOVIMIENTO por la columna TIPO_ELEMENTAL
        String sql = "SELECT * FROM MOVIMIENTO WHERE TIPO_ELEMENTAL = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            // el enum se pasa a String para la consulta SQL
            ps.setString(1, tipo.toString());
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                //añadimos a la lista los movimientos
                lista.add(obtenerMovimiento(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    // mtodo para obtener movimientos que coincidan con el tipo del Pokemon y que hagan daño
    public List<Movimiento> listarPorTipoYOfensivo(Tipos tipo) {
        // creamos una lista vacia
        List<Movimiento> lista = new ArrayList<>();

        //  preparamos la consulta SQL.
        // Filtramos por el tipo elemental y usamos POTENCIA > 0 para descartar ataques de estado
        String sql = "SELECT * FROM MOVIMIENTO WHERE TIPO_ELEMENTAL = ? AND POTENCIA > 0";

        // abrimos el try-with-resources para gestionar la consulta de forma segura.
        try (PreparedStatement ps = connection.prepareStatement(sql)) {

            // sustituimos el ? por el nombre del tipo del Pokemon
            ps.setString(1, tipo.toString());

            // ejecutamos la consulta en la bd
            ResultSet rs = ps.executeQuery();

            // mientras la base de datos encuentre filas que cumplan los requisitos lo añadimos a la lista, buscando por id
            while (rs.next()) {
                lista.add(buscarPorId(rs.getInt("ID_MOVIMIENTO")));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

    // metodo para actualizar los pp de los pokemon al suar los ataques
    public void actualizarPPs(int idPokemon, int idMovimiento, int nuevosPP) {
        String sql = "UPDATE SET_MOVIMIENTOS SET PP = ? WHERE ID_POKEMON = ? AND ID_MOVIMIENTO = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, nuevosPP);
            ps.setInt(2, idPokemon);
            ps.setInt(3, idMovimiento);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * reemplaza un movimiento viejo por uno nuevo en la tabla SET_MOVIMIENTOS
     */
    public void cambiarMovimientoBD(int idPokemon, int idMovimientoViejo, Movimiento nuevoMovimiento) {
        // actualizamos el ID del movimiento y le ponemos los PP al máximo
        String sql = "UPDATE SET_MOVIMIENTOS SET ID_MOVIMIENTO = ?, PP = ? WHERE ID_POKEMON = ? AND ID_MOVIMIENTO = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, nuevoMovimiento.getIdMovimiento());

            ///guarda el maximo de PP
            ps.setInt(2, nuevoMovimiento.getCantidadMovimientosMaximos());

            ps.setInt(3, idPokemon);
            ps.setInt(4, idMovimientoViejo);

            ps.executeUpdate();
            System.out.println("BD: Movimiento actualizado con éxito.");
        } catch (SQLException e) {
            System.out.println("Error al cambiar movimiento en BD: " + e.getMessage());
        }
    }

    /**
     * restaura todos los PP de los movimientos de un pokemon al maximo
     */
    public void restaurarPPsCompletos(int idPokemon) {
        //buscamos los movimientos tiene equipados el Pokemon 
        String sqlSelect = "SELECT ID_MOVIMIENTO FROM SET_MOVIMIENTOS WHERE ID_POKEMON = ?";

        //preparamos el update para actualizar cada uno por separado a su maximo
        String sqlUpdate = "UPDATE SET_MOVIMIENTOS SET PP = ? WHERE ID_POKEMON = ? AND ID_MOVIMIENTO = ?";

        try (PreparedStatement psSelect = connection.prepareStatement(sqlSelect);
             PreparedStatement psUpdate = connection.prepareStatement(sqlUpdate)) {

            psSelect.setInt(1, idPokemon);
            ResultSet rs = psSelect.executeQuery();

            //recorremos los ataques que tiene equipados
            while (rs.next()) {
                int idMov = rs.getInt("ID_MOVIMIENTO");

                ///buscamos por id
                Movimiento mov = buscarPorId(idMov);

                if (mov != null) {
                    //actualizamos los PP con su maximo
                    psUpdate.setInt(1, mov.getCantidadMovimientosMaximos());
                    psUpdate.setInt(2, idPokemon);
                    psUpdate.setInt(3, idMov);
                    psUpdate.executeUpdate();
                }
            }
            System.out.println("BD: Todos los PPs del Pokémon " + idPokemon + " han sido restaurados a su máximo original.");

        } catch (SQLException e) {
            System.out.println("Error al restaurar PPs en BD: " + e.getMessage());
        }
    }

    /**
     * Inserta un nuevo movimiento en  SET_MOVIMIENTOS
     */
    public void insertarMovimientoBD(int idPokemon, Movimiento nuevoMovimiento) {
        String sql = "INSERT INTO SET_MOVIMIENTOS (ID_POKEMON, ID_MOVIMIENTO, PP, ES_ACTIVO) VALUES (?, ?, ?, 1)";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, idPokemon);
            ps.setInt(2, nuevoMovimiento.getIdMovimiento());
            ps.setInt(3, nuevoMovimiento.getCantidadMovimientosMaximos());

            ///insertado siempre con el maximo
            ps.setInt(3, nuevoMovimiento.getCantidadMovimientosMaximos());

            ps.executeUpdate();
            System.out.println("BD: Nuevo movimiento aprendido e insertado con éxito.");
        } catch (SQLException e) {
            System.out.println("Error al insertar nuevo movimiento en BD: " + e.getMessage());
        }
    }
}