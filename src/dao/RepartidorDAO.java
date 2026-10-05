package dao;

import modelo.Repartidor;

import java.util.List;

/**
 * Define las operaciones disponibles para los repartidores.
 */
public interface RepartidorDAO {

    /**
     * Registra un nuevo repartidor.
     * @param repartidor repartidor que se desea almacenar
     * @return {@code true} si se guardó correctamente; {@code false} si ocurrió un error.
     */
    boolean guardar(Repartidor repartidor);

    /**
     * Obtiene todos los repartidores almacenados.
     *
     * @return lista de repartidores; estará vacía si no hay registros.
     */
    List<Repartidor> listarTodos();

    /**
     * Actualiza los datos de un repartidor existente.
     *
     * @param repartidor repartidor con el identificador y los nuevos datos.
     * @return {@code true} si se actualizó algún registro; {@code false} en caso contrario.
     */
    boolean actualizar(Repartidor repartidor);

    /**
     * Elimina un repartidor por su identificador.
     *
     * @param id identificador del repartidor.
     * @return {@code true} si se eliminó; {@code false} si no se pudo eliminar.
     */
    boolean eliminar(int id);
}
