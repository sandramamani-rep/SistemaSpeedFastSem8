package modelo;

/**
 * Define los estados posibles de un pedido dentro del sistema SpeedFast.
 */
public enum EstadoPedido {
    /**
     * El pedido fue creado y está pendiente de ser retirado.
     */
    PENDIENTE,

    /**
     * El pedido fue retirado por un repartidor y se encuentra
     * en proceso de entrega.
     */
    EN_REPARTO,

    /**
     * El pedido fue entregado correctamente al destinatario.
     */
    ENTREGADO
}
