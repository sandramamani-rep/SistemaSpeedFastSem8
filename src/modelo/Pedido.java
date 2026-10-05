package modelo;


/**
 * Representa un pedido genérico dentro del sistema de reparto SpeedFast.
 *
 */
public class Pedido {
    private int id;
    private String direccion;
    private String tipo;
    private String estado;

    /**
     * Construye un pedido con sus datos principales.
     *
     * @param direccion dirección donde se debe entregar el pedido.
     * @param tipo tipo de pedido.
     * @param  estado estado del pedido
     */
    public Pedido(String direccion, String tipo, String estado) {
        this.direccion = direccion;
        this.tipo = tipo;
        this.estado = estado;
    }

    /**
     * Construye un pedido con sus datos principales.
     *
     * @param id identificador único del pedido.
     * @param direccion dirección donde se debe entregar el pedido.
     * @param tipo tipo de pedido.
     * @param  estado estado del pedido
     */
    public Pedido(int id, String direccion, String tipo, String estado) {
        this.id = id;
        this.direccion = direccion;
        this.tipo = tipo;
        this.estado = estado;

    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    /**
     * Devuelve una representación textual del pedido.
     * @return información del pedido.
     */
    @Override
    public String toString() {
        return id+ " - " + direccion;
    }
}
