package dao;

import model.Movimiento;
import model.Tipos;

import java.util.List;

public interface IMovimientoDAO {
    Movimiento buscarPorId(int id);

    List<Movimiento> listarTodos();

    List<Movimiento> listarPorTipo(Tipos tipo);

}