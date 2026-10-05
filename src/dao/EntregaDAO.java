package dao;

import modelo.Entrega;

import java.util.List;

/**
 * Define las operaciones disponibles para las entregas.
 */
public interface EntregaDAO {

    /**
     * Registra una nueva entrega.
     * @param entrega entrega que se desea almacenar
     * @return {@code true} si se guardó correctamente; {@code false} si ocurrió un error.
     */
    boolean guardar(Entrega entrega);

    /**
     * Obtiene todas las entregas almacenados.
     *
     * @return lista de entregas; estará vacía si no hay registros.
     */
    List<Entrega> listarTodos();

    /**
     * Actualiza los datos de una entrega existente.
     *
     * @param entrega entrega con el identificador y los nuevos datos.
     * @return {@code true} si se actualizó algún registro; {@code false} en caso contrario.
     */
    boolean actualizar(Entrega entrega);

    /**
     * Elimina una entrega por su identificador.
     *
     * @param id identificador de la entrega.
     * @return {@code true} si se eliminó; {@code false} si no se pudo eliminar.
     */
    boolean eliminar(int id);
}
