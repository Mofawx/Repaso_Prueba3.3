package dao;

import java.util.List;

import model.Curso;

/**
 * Contrato común para JSON y MySQL.
 */
public interface CursoDAO {
    List<Curso> listar();

    void guardar(Curso item);

    void actualizar(Curso item);

    void eliminar(String codigo);
}
