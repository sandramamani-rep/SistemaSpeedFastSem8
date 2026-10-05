package controlador;

import dao.EntregaDAO;
import dao.impl.EntregaDAOImpl;
import modelo.Entrega;
import java.util.List;

/** Valida la selección y coordina el registro de entregas. */
public class ControladorEntregas {
    private final EntregaDAO entregaDAO;
    private final ControladorPedidos controladorPedidos = new ControladorPedidos();

    public ControladorEntregas() {
        entregaDAO = new EntregaDAOImpl();
    }

    /**
     * Registra una entrega asociando un pedido con un repartidor.
     * Al completar el registro, marca el pedido asociado como ENTREGADO.
     * La edición de una entrega no modifica automáticamente el estado de los pedidos.
     *
     * @param idPedido identificador del pedido.
     * @param idRepartidor identificador del repartidor.
     * @return {@code true} si se registró la entrega y se actualizó el estado;
     * {@code false} si los IDs no son válidos o alguna operación falló.
     */
    public boolean registrarEntrega(int idPedido, int idRepartidor) {
        if (idPedido <= 0 || idRepartidor <= 0) {
            return false;
        }

        boolean entregaGuardada = entregaDAO.guardar(new Entrega(idPedido, idRepartidor));

        if (!entregaGuardada) {
            return false;
        }
            
        return controladorPedidos.marcarComoEntregado(idPedido);
            
    }

    /**
     * Obtiene todas las entregas registradas.
     *
     * @return lista de entregas.
     */
    public List<Entrega> obtenerEntregas() {
        return entregaDAO.listarTodos();
    }

    /**
     * Actualiza el pedido y el repartidor asignados a una entrega.
     * La edición de una entrega no modifica automáticamente el estado de los pedidos.
     * @param idEntrega identificador de la entrega.
     * @param idPedido nuevo identificador del pedido.
     * @param idRepartidor nuevo identificador del repartidor.
     * @return {@code true} si se actualizó; {@code false} si algún ID no es válido
     * o la operación falló.
     */
    public boolean actualizar(int idEntrega, int idPedido, int idRepartidor) {
        if (idEntrega <= 0 || idPedido <= 0 || idRepartidor <= 0) {
            return false;
        }

        Entrega entrega = new Entrega(idEntrega, idPedido, idRepartidor,
                java.time.LocalDate.now(),
                java.time.LocalTime.now().withNano(0));

        return entregaDAO.actualizar(entrega);
    }

    /**
     * Elimina una entrega.
     *
     * @param idEntrega identificador de la entrega.
     * @return {@code true} si se eliminó; {@code false} en caso contrario.
     */
    public boolean eliminar(int idEntrega) {
        if (idEntrega <= 0) {
            return false;
        }
        return entregaDAO.eliminar(idEntrega);
    }
}
