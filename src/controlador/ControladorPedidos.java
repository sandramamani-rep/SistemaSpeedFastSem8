package controlador;

import dao.PedidoDAO;
import dao.impl.PedidoDAOImpl;
import modelo.EstadoPedido;
import modelo.Pedido;

import java.util.List;

/**
 * Valida los datos de entrada y coordina las operaciones de pedidos
 * entre la vista y la capa DAO.
 */
public class ControladorPedidos {
    
    private final PedidoDAO pedidoDAO;

    public ControladorPedidos() {
        pedidoDAO = new PedidoDAOImpl();

    }

    /**
     * Valida y registra un pedido.
     *
     * @param direccion dirección de entrega.
     * @param tipo tipo de pedido: COMIDA, ENCOMIENDA o EXPRESS.
     * @param estado estado del pedido.
     * @return {@code true} si se registró; {@code false} si los datos no son válidos
     * o la persistencia falló.
     */
    public boolean registrarPedido(String direccion, String tipo, String estado) {
        if (direccion == null || direccion.isBlank()
                || tipo == null || tipo.isBlank()
                || estado == null || estado.isBlank()) {
            return false;
        }

        Pedido pedido = new Pedido(
                direccion.trim(),
                tipo.trim(),
                estado.trim()
        );

        return pedidoDAO.guardar(pedido);
    }

    /**
     * Recupera los pedidos almacenados para mostrarlos en la interfaz.
     *
     * @return lista de pedidos.
     */
    public List<Pedido> obtenerPedidos() {
        return pedidoDAO.listarTodos();
    }

    /**
     * Valida y actualiza los datos de un pedido.
     *
     * @param id identificador del pedido.
     * @param direccion nueva dirección de entrega.
     * @param tipo nuevo tipo de pedido.
     * @param estado nuevo estado del pedido.
     * @return {@code true} si se actualizó; {@code false} si los datos no son válidos
     * o la operación falló.
     */
    public boolean actualizar(int id, String direccion, String tipo, String estado) {
        if (id <= 0 || direccion == null || direccion.isBlank()
                || tipo == null || tipo.isBlank()
                || estado == null || estado.isBlank()) {
            return false;
        }

        Pedido pedido = new Pedido(
                id,
                direccion.trim(),
                tipo.trim(),
                estado.trim()
        );

        return pedidoDAO.actualizar(pedido);
    }

    /**
     * Elimina un pedido existente.
     *
     * @param id identificador del pedido.
     * @return {@code true} si se eliminó; {@code false} si no existe o la operación falló.
     */
    public boolean eliminar(int id) {
        if (id <= 0) {
            return false;
        }
        return pedidoDAO.eliminar(id);
    }

    /**
     * Marca un pedido como entregado.
     *
     * @param idPedido identificador del pedido.
     * @return {@code true} si se actualizó; {@code false} si el ID no es válido
     * o la operación falló.
     */
    public boolean marcarComoEntregado(int idPedido) {
        if (idPedido <= 0) {
            return false;
        }

        return pedidoDAO.actualizarEstado(
                idPedido,
                EstadoPedido.ENTREGADO.name()
        );
    }
}




