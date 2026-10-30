package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


public class CasoDAO {

    private String url = "jdbc:sqlite:Basededatos.db";

    public CasoDAO() {
        crearTablaSiNoExiste();
    }

    private Connection conectar() throws SQLException {
        return DriverManager.getConnection(url);
    }

    private void crearTablaSiNoExiste() {
        String sql = "CREATE TABLE IF NOT EXISTS caso ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "clase TEXT, "
                + "apoderado_judicial TEXT, "
                + "demandante TEXT, "
                + "demandado TEXT, "
                + "cuantia TEXT, "
                + "despacho_judicial TEXT, "
                + "radicado TEXT UNIQUE, "
                + "actuaciones_seguimiento TEXT, "
                + "etapa_procesal TEXT, "
                + "actuaciones_a_realizar TEXT, "
                + "url_drive TEXT"
                + ");";

        try (Connection conn = conectar(); Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean guardarCaso(Caso c) {
        String sql = "INSERT INTO caso (clase, apoderado_judicial, demandante, demandado, cuantia, despacho_judicial, radicado, actuaciones_seguimiento, etapa_procesal, actuaciones_a_realizar, url_drive) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = conectar(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, c.getClase());
            ps.setString(2, c.getApoderadoJudicial());
            ps.setString(3, c.getDemandante());
            ps.setString(4, c.getDemandado());
            ps.setString(5, c.getCuantia());
            ps.setString(6, c.getDespachoJudicial());
            ps.setString(7, c.getRadicado());
            ps.setString(8, c.getActuacionesSeguimiento());
            ps.setString(9, c.getEtapaProcesal());
            ps.setString(10, c.getActuacionesARealizar());
            ps.setString(11, c.getUrlDrive());
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean actualizarCaso(Caso c) {
        String sql = "UPDATE caso SET clase = ?, apoderado_judicial = ?, demandante = ?, demandado = ?, cuantia = ?, despacho_judicial = ?, radicado = ?, actuaciones_seguimiento = ?, etapa_procesal = ?, actuaciones_a_realizar = ?, url_drive = ? WHERE id = ?";

        try (Connection conn = conectar(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, c.getClase());
            ps.setString(2, c.getApoderadoJudicial());
            ps.setString(3, c.getDemandante());
            ps.setString(4, c.getDemandado());
            ps.setString(5, c.getCuantia());
            ps.setString(6, c.getDespachoJudicial());
            ps.setString(7, c.getRadicado());
            ps.setString(8, c.getActuacionesSeguimiento());
            ps.setString(9, c.getEtapaProcesal());
            ps.setString(10, c.getActuacionesARealizar());
            ps.setString(11, c.getUrlDrive());
            ps.setInt(12, c.getId());
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean eliminarCaso(int id) {
        String sql = "DELETE FROM caso WHERE id = ?";
        try (Connection conn = conectar(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Caso> listarCasos() {
        List<Caso> lista = new ArrayList<>();
        String sql = "SELECT * FROM caso ORDER BY id DESC";

        try (Connection conn = conectar(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Caso c = new Caso();
                c.setId(rs.getInt("id"));
                c.setClase(rs.getString("clase"));
                c.setApoderadoJudicial(rs.getString("apoderado_judicial"));
                c.setDemandante(rs.getString("demandante"));
                c.setDemandado(rs.getString("demandado"));
                c.setCuantia(rs.getString("cuantia"));
                c.setDespachoJudicial(rs.getString("despacho_judicial"));
                c.setRadicado(rs.getString("radicado"));
                c.setActuacionesSeguimiento(rs.getString("actuaciones_seguimiento"));
                c.setEtapaProcesal(rs.getString("etapa_procesal"));
                c.setActuacionesARealizar(rs.getString("actuaciones_a_realizar"));
                c.setUrlDrive(rs.getString("url_drive"));
                lista.add(c);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }
}