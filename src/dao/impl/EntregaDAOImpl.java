package dao.impl;

import dao.EntregaDAO;
import modelo.Entrega;
import util.ConexionBD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementa las operaciones JDBC para persistir y consultar entregas.
 */
public class EntregaDAOImpl implements EntregaDAO {

    /**
     * Registra una entrega utilizando una sentencia preparada.
     *
     * @param entrega entrega que se desea almacenar.
     * @return {@code true} si se guardó correctamente; {@code false} si ocurrió un error.
     */
    @Override
    public boolean guardar(Entrega entrega) {
        String sql = "INSERT INTO entrega (id_pedido, id_repartidor, fecha, hora) "
                + "VALUES (?, ?, ?, ?)";

        try (Connection conexion = ConexionBD.conectar();
             PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, entrega.getIdPedido());
            stmt.setInt(2, entrega.getIdRepartidor());
            stmt.setDate(3, java.sql.Date.valueOf(entrega.getFecha()));
            stmt.setTime(4, java.sql.Time.valueOf(entrega.getHora()));
            return stmt.executeUpdate() == 1;
        } catch (SQLException e) {
            System.out.println("Error al guardar entrega: " + e.getMessage());
            return false;
        }
    }

    /**
     * Consulta todas las entregas de la base de datos.
     *
     * @return lista de entregas consultadas.
     */
    @Override
    public List<Entrega> listarTodos() {
        String sql = "SELECT id, id_pedido, id_repartidor, fecha, hora "
                + "FROM entrega ORDER BY id DESC";
        List<Entrega> entregas = new ArrayList<>();

        try (Connection conn = ConexionBD.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                entregas.add(new Entrega(
                        rs.getInt("id"),
                        rs.getInt("id_pedido"),
                        rs.getInt("id_repartidor"),
                        rs.getDate("fecha").toLocalDate(),
                        rs.getTime("hora").toLocalTime()
                ));
            }
        } catch (SQLException e) {
            System.out.println("Error al listar entregas: " + e.getMessage());
        }

        return entregas;
    }

    /**
     * Actualiza los datos de una entrega existente.
     * Conserva la fecha y la hora originales.
     * @param entrega entrega con su ID y los nuevos datos.
     * @return {@code true} si la actualización fue exitosa; {@code false} en caso contrario.
     */
    @Override
    public boolean actualizar(Entrega entrega) {
        String sql = "UPDATE entrega "
                + "SET id_pedido = ?, id_repartidor = ? "
                + "WHERE id = ?";

        try (Connection conn = ConexionBD.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, entrega.getIdPedido());
            stmt.setInt(2, entrega.getIdRepartidor());
            stmt.setInt(3, entrega.getId());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error al actualizar entrega: " + e.getMessage());
            return false;
        }
    }

    /**
     * Elimina una entrega de la base de datos.
     *
     * @param idEntrega identificador de la entrega que se eliminará.
     * @return {@code true} si se eliminó; {@code false} si ocurrió un error o existe una restricción.
     */
    @Override
    public boolean eliminar(int idEntrega) {
        String sql = "DELETE FROM entrega WHERE id = ?";

        try (Connection conn = ConexionBD.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idEntrega);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error al eliminar entrega: " + e.getMessage());
            return false;
        }
    }
}
