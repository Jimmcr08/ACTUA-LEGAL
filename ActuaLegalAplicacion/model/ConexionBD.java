package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class ConexionBD {
    private static final String URL = "jdbc:sqlite:Basededatos.db";

    public static Connection conectar() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(URL);
            crearTablaSiNoExiste(conn);
        } catch (SQLException e) {
            System.err.println("Error de conexión SQLite: " + e.getMessage());
        }
        return conn;
    }

    private static void crearTablaSiNoExiste(Connection conn) {
        if (conn == null) return;

        String sql = "CREATE TABLE IF NOT EXISTS casos ("
                   + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                   + "clase TEXT NOT NULL, "
                   + "apoderado TEXT NOT NULL, "
                   + "demandante TEXT NOT NULL, "
                   + "demandado TEXT NOT NULL, "
                   + "cuantia TEXT NOT NULL, "
                   + "despacho TEXT NOT NULL, "
                   + "radicado TEXT NOT NULL UNIQUE, "
                   + "seguimiento TEXT NOT NULL, "
                   + "etapa TEXT NOT NULL, "
                   + "actuaciones TEXT NOT NULL, "
                   + "url_drive TEXT NOT NULL, "
                   + "cliente_representado TEXT NOT NULL"
                   + ");";

        try (Statement st = conn.createStatement()) {
            st.execute(sql);
        } catch (SQLException e) {
            System.err.println("Error al crear la tabla: " + e.getMessage());
        }
    }
}