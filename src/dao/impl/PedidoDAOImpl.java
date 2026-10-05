package dao.impl;

import dao.PedidoDAO;
import modelo.Pedido;
import util.ConexionBD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementa las operaciones JDBC para persistir y consultar pedidos.
 */
public class PedidoDAOImpl implements PedidoDAO {

    public PedidoDAOImpl() {
    }

    /**
     * Registra un pedido utilizando una sentencia preparada.
     *
     * @param pedido pedido que se desea almacenar.
     * @return {@code true} si se guardó correctamente; {@code false} si ocurrió un error.
     */
    public boolean guardar(Pedido pedido) {
        String sql = "INSERT INTO pedido (direccion, tipo, estado) VALUES (?, ?, ?)";

        try (Connection conn = ConexionBD.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, pedido.getDireccion());
            stmt.setString(2, pedido.getTipo());
            stmt.setString(3, pedido.getEstado());
            return stmt.executeUpdate() == 1;
        } catch (SQLException e) {
            System.out.println("Error al guardar pedido: " + e.getMessage());
            return false;
        }
    }

    /**
     * Consulta todos los pedidos de la base de datos.
     *
     * @return lista de pedidos consultados.
     */
    public List<Pedido> listarTodos() {
        List<Pedido> pedidos = new ArrayList<>();
        String sql = "SELECT id, direccion, tipo, estado FROM pedido ORDER BY id DESC";

        try (Connection conn = ConexionBD.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                pedidos.add(new Pedido(
                        rs.getInt("id"),
                        rs.getString("direccion"),
                        rs.getString("tipo"),
                        rs.getString("estado")
                ));
            }
        } catch (SQLException e) {
            System.out.println("Error al listar pedidos: " + e.getMessage());
        }

        return pedidos;
    }

    /**
     * Actualiza los datos de un pedido existente.
     *
     * @param pedido pedido con su ID y los nuevos datos.
     * @return {@code true} si la actualización fue exitosa; {@code false} en caso contrario.
     */
    public boolean actualizar(Pedido pedido) {
        String sql = "UPDATE pedido SET direccion = ?, tipo = ?, estado = ? WHERE id = ?";

        try (Connection conn = ConexionBD.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, pedido.getDireccion());
            stmt.setString(2, pedido.getTipo());
            stmt.setString(3, pedido.getEstado());
            stmt.setInt(4, pedido.getId());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error al actualizar pedido: " + e.getMessage());
            return false;
        }
    }

    /**
     * Elimina un pedido de la base de datos.
     *
     * @param id identificador del pedido que se eliminará.
     * @return {@code true} si se eliminó; {@code false} si ocurrió un error o existe una restricción.
     */
    public boolean eliminar(int id) {
        String sql = "DELETE FROM pedido WHERE id = ?";

        try (Connection conn = ConexionBD.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error al eliminar pedido: " + e.getMessage());
            return false;
        }
    }

    /**
     * Actualiza el estado de un pedido.
     *
     * @param idPedido identificador del pedido.
     * @param estado nuevo estado.
     * @return {@code true} si el pedido se actualizó; {@code false} en caso contrario.
     */
    @Override
    public boolean actualizarEstado(int idPedido, String estado) {
        String sql = "UPDATE pedido SET estado = ? WHERE id = ?";

        try (Connection conn = ConexionBD.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, estado);
            stmt.setInt(2, idPedido);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error al actualizar estado del pedido: " + e.getMessage());
            return false;
        }
    }
}
