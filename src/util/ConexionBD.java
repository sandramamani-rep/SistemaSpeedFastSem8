package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Administra la conexión JDBC con la base de datos MySQL de SpeedFast.
 */
public class ConexionBD {
    private static final String URL = "jdbc:mysql://localhost:3306/speedfast_db";
    private static final String USER = "root";
    private static final String PASSWORD = "admin123";

    public ConexionBD() {
    }

    /**
     * Establece una conexión con la base de datos configurada.
     *
     * @return conexión JDBC abierta.
     * @throws SQLException si ocurre un error al conectarse a MySQL.
     */
    public static Connection conectar() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
