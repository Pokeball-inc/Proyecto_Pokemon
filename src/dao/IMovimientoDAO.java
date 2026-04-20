package dao;

import java.util.List;

import model.Movimiento;
import model.Tipos;

public interface IMovimientoDAO {
    Movimiento buscarPorId(int id);
    List<Movimiento> listarTodos();
    List<Movimiento> listarPorTipo(Tipos tipo);
    
}