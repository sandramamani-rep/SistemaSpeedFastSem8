package dao.impl;

import dao.RepartidorDAO;
import modelo.Repartidor;
import util.ConexionBD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementa las operaciones JDBC para persistir y consultar repartidores.
 */
public class RepartidorDAOImpl implements RepartidorDAO {
    public RepartidorDAOImpl(){
    }

    /**
     * Registra un repartidor utilizando una sentencia preparada.
     *
     * @param repartidor repartidor que se desea almacenar.
     * @return {@code true} si se guardó correctamente; {@code false} si ocurrió un error.
     */
    @Override
    public boolean guardar(Repartidor repartidor){
        String sql = "INSERT INTO repartidor (nombre) VALUES (?)";
        try (Connection conn = ConexionBD.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, repartidor.getNombre());
            return stmt.executeUpdate() == 1;
        } catch (SQLException e) {
            System.out.println("Error al guardar repartidor: " + e.getMessage());
            return false;
        }
    }

    /**
     * Consulta todos los repartidor de la base de datos.
     *
     * @return lista de repartidor consultados.
     */
    @Override
    public List<Repartidor> listarTodos(){
        List<Repartidor> repartidores = new ArrayList<>();
        String sql = "SELECT id, nombre FROM repartidor";
        try (Connection conn = ConexionBD.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                repartidores.add(new Repartidor(rs.getInt("id"), rs.getString("nombre")));
            }
        } catch (SQLException e) {
            System.out.println("Error al listar repartidor: " + e.getMessage());
        }
        return repartidores;
    }

    /**
     * Actualiza los datos de un repartidor existente.
     *
     * @param repartidor repartidor con su ID y los nuevos datos.
     * @return {@code true} si la actualización fue exitosa; {@code false} en caso contrario.
     */
    @Override
    public boolean actualizar(Repartidor repartidor) {
        String sql = "UPDATE repartidor SET nombre = ? WHERE id = ?";
        try (Connection conexion = ConexionBD.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setString(1, repartidor.getNombre());
            ps.setInt(2, repartidor.getId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error al actualizar repartidor: " + e.getMessage());
            return false;
        }
    }

    /**
     * Elimina un repartidor de la base de datos.
     *
     * @param id identificador del repartidor que se eliminará.
     * @return {@code true} si se eliminó; {@code false} si ocurrió un error o existe una restricción.
     */
    @Override
    public boolean eliminar(int id) {
        String sql = "DELETE FROM repartidor WHERE id = ?";
        
        try (Connection conexion = ConexionBD.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error al eliminar repartidor: " + e.getMessage());
            return false;
        }
    }
}
