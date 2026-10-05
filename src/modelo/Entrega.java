package modelo;

import java.time.LocalDate;
import java.time.LocalTime;

/** Relaciona un pedido con el repartidor que realiza su entrega. */
public class Entrega {
    private int id;
    private int idPedido;
    private int idRepartidor;
    private LocalDate fecha;
    private LocalTime hora;

    /**
     * Crea una entrega con los datos
     * @param idPedido identificador del pedido
     * @param idRepartidor identificador del repartidor
     */
    public Entrega(int idPedido, int idRepartidor) {
        this(0, idPedido, idRepartidor, java.time.LocalDate.now(), java.time.LocalTime.now().withNano(0));
    }

    /**
     * Crea una entrega con los datos
     * @param id identificador de la entrega
     * @param idPedido identificador del pedido
     * @param idRepartidor identificador del repartidor
     * @param fecha fecha de registro de la entrega
     * @param hora hora de registro de la entrega
     */
    public Entrega(int id, int idPedido, int idRepartidor, LocalDate fecha, LocalTime hora) {
        this.id = id;
        this.idPedido = idPedido;
        this.idRepartidor = idRepartidor;
        this.fecha = fecha;
        this.hora = hora;
    }

    /**
     * Crea una entrega con los datos
     * @param id identificador de la entrega
     * @param idPedido identificador del pedido
     * @param idRepartidor identificador del repartidor
     */
    public Entrega(int id, int idPedido, int idRepartidor) {
        this.id = id;
        this.idPedido = idPedido;
        this.idRepartidor = idRepartidor;
    }

    public int getId() { return id; }
    public int getIdPedido() { return idPedido; }
    public int getIdRepartidor() { return idRepartidor; }
    public LocalDate getFecha() { return fecha; }
    public LocalTime getHora() { return hora; }

    @Override
    public String toString() {
        return "Entrega{" +
                "fecha=" + fecha +
                ", id=" + id +
                ", idPedido=" + idPedido +
                ", idRepartidor=" + idRepartidor +
                ", hora=" + hora +
                '}';
    }
}
