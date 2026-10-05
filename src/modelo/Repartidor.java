package modelo;

/**
 * Representa un repartidor registrado en el sistema SpeedFast.
 */
public class Repartidor {

    private int id;
    private String nombre;

    /**
     * Crea un repartidor con el nombre indicado.
     *
     * @param nombre nombre del repartidor.
     */
    public Repartidor(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Crea un repartidor con el id nombre indicado.
     * @param id identificador del repartidor
     * @param nombre nombre del repartidor.
     */
    public Repartidor(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return id + " - " + nombre;
    }
}
