package controlador;

import dao.RepartidorDAO;
import dao.impl.RepartidorDAOImpl;
import modelo.Repartidor;

import java.util.List;

/**
 * Valida los datos de entrada y coordina las operaciones de repartidores
 * entre la vista y la capa DAO.
 */
public class ControladorRepartidores {
    private final RepartidorDAO repartidorDAO;

    public ControladorRepartidores() {
        repartidorDAO = new RepartidorDAOImpl();
    }

    /**
     * Valida y registra un repartidor.
     *
     * @param nombre nombre del repartidor.
     * @return {@code true} si se registró; {@code false} si el nombre está vacío
     * o la operación falló.
     */
    public boolean registrarRepartidor(String nombre){
        if (nombre == null || nombre.isBlank()) {
            return false;
        }
        return repartidorDAO.guardar(new Repartidor(nombre));
    }

    /**
     * Obtiene los repartidores almacenados.
     *
     * @return lista de repartidores.
     */
    public List<Repartidor> obtenerRepartidores(){
        return this.repartidorDAO.listarTodos();
    }

    /**
     * Valida y actualiza el nombre de un repartidor.
     *
     * @param id identificador del repartidor.
     * @param nombre nuevo nombre.
     * @return {@code true} si se actualizó; {@code false} en caso contrario.
     */
    public boolean actualizar (int id, String nombre){
        if (id <= 0 || nombre== null || nombre.isBlank()) {
            return false;
        }
        return repartidorDAO.actualizar(new Repartidor(id, nombre));
    }

    /**
     * Elimina un repartidor.
     *
     * @param IdRepartidor identificador del repartidor.
     * @return {@code true} si se eliminó; {@code false} si la operación falló.
     */
    public boolean eliminar (int IdRepartidor){
        if (IdRepartidor <= 0) {
            return false;
        }
        return repartidorDAO.eliminar(IdRepartidor);
    }
}
