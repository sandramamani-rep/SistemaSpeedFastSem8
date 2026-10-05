package dao;

import modelo.Pedido;

import java.util.List;

/**
 * Define las operaciones disponibles para los pedidos.
 */
public interface PedidoDAO {

    /**
     * Registra un nuevo pedido.
     * @param pedido pedido que se desea almacenar
     * @return {@code true} si se guardó correctamente; {@code false} si ocurrió un error.
     */
    boolean guardar(Pedido pedido);

    /**
     * Obtiene todos los pedidos almacenados.
     *
     * @return lista de pedidos; estará vacía si no hay registros.
     */
    List<Pedido> listarTodos();

    /**
     * Actualiza los datos de un pedido existente.
     *
     * @param pedido pedido con el identificador y los nuevos datos.
     * @return {@code true} si se actualizó algún registro; {@code false} en caso contrario.
     */
    boolean actualizar(Pedido pedido);

    /**
     * Elimina un pedido por su identificador.
     *
     * @param id identificador del pedido.
     * @return {@code true} si se eliminó; {@code false} si no se pudo eliminar.
     */
    boolean eliminar(int id);

    /**
     * Actualiza el estado de un pedido.
     *
     * @param idPedido identificador del pedido.
     * @param estado nuevo estado.
     * @return {@code true} si el pedido se actualizó; {@code false} en caso contrario.
     */
    boolean actualizarEstado(int idPedido, String estado);
}
