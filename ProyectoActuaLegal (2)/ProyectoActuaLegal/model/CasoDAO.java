package model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CasoDAO {

    public CasoDAO() {
        crearTablaSiNoExiste();
    }

    private void crearTablaSiNoExiste() {
        String sql = "CREATE TABLE IF NOT EXISTS caso ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "clase TEXT, "
                + "apoderado_judicial TEXT, "
                + "demandante TEXT, "
                + "demandado TEXT, "
                + "cuantia REAL, "
                + "despacho_judicial TEXT, "
                + "radicado TEXT UNIQUE, "
                + "actuaciones_seguimiento TEXT, "
                + "etapa_procesal TEXT, "
                + "actuaciones_a_realizar TEXT"
                + ");";

        try (Connection con = conexion.conectar();
             Statement stmt = con.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            System.err.println("Error al crear la tabla: " + e.getMessage());
        }
    }

    // 1. CREAR / GUARDAR
    public boolean guardarCaso(Caso c) {
        String sql = "INSERT INTO caso (clase, apoderado_judicial, demandante, demandado, cuantia, "
                + "despacho_judicial, radicado, actuaciones_seguimiento, etapa_procesal, actuaciones_a_realizar) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection con = conexion.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, c.getClase());
            ps.setString(2, c.getApoderadoJudicial());
            ps.setString(3, c.getDemandante());
            ps.setString(4, c.getDemandado());
            ps.setDouble(5, c.getCuantia());
            ps.setString(6, c.getDespachoJudicial());
            ps.setString(7, c.getRadicado());
            ps.setString(8, c.getActuacionesSeguimiento());
            ps.setString(9, c.getEtapaProcesal());
            ps.setString(10, c.getActuacionesARealizar());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error al guardar: " + e.getMessage());
            return false;
        }
    }

    // 2. LEER / LISTAR TODOS LOS CASOS
    public List<Caso> listarCasos() {
        List<Caso> lista = new ArrayList<>();
        String sql = "SELECT * FROM caso ORDER BY id DESC";

        try (Connection con = conexion.conectar();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Caso c = new Caso();
                c.setId(rs.getInt("id"));
                c.setClase(rs.getString("clase"));
                c.setApoderadoJudicial(rs.getString("apoderado_judicial"));
                c.setDemandante(rs.getString("demandante"));
                c.setDemandado(rs.getString("demandado"));
                c.setCuantia(rs.getDouble("cuantia"));
                c.setDespachoJudicial(rs.getString("despacho_judicial"));
                c.setRadicado(rs.getString("radicado"));
                c.setActuacionesSeguimiento(rs.getString("actuaciones_seguimiento"));
                c.setEtapaProcesal(rs.getString("etapa_procesal"));
                c.setActuacionesARealizar(rs.getString("actuaciones_a_realizar"));
                
                lista.add(c);
            }

        } catch (SQLException e) {
            System.err.println("Error al listar casos: " + e.getMessage());
        }
        return lista;
    }

    // 3. EDITAR / ACTUALIZAR
    public boolean actualizarCaso(Caso c) {
        String sql = "UPDATE caso SET clase=?, apoderado_judicial=?, demandante=?, demandado=?, "
                + "cuantia=?, despacho_judicial=?, radicado=?, actuaciones_seguimiento=?, "
                + "etapa_procesal=?, actuaciones_a_realizar=? WHERE id=?";

        try (Connection con = conexion.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, c.getClase());
            ps.setString(2, c.getApoderadoJudicial());
            ps.setString(3, c.getDemandante());
            ps.setString(4, c.getDemandado());
            ps.setDouble(5, c.getCuantia());
            ps.setString(6, c.getDespachoJudicial());
            ps.setString(7, c.getRadicado());
            ps.setString(8, c.getActuacionesSeguimiento());
            ps.setString(9, c.getEtapaProcesal());
            ps.setString(10, c.getActuacionesARealizar());
            ps.setInt(11, c.getId());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error al actualizar: " + e.getMessage());
            return false;
        }
    }

    // 4. ELIMINAR
    public boolean eliminarCaso(int id) {
        String sql = "DELETE FROM caso WHERE id=?";

        try (Connection con = conexion.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error al eliminar: " + e.getMessage());
            return false;
        }
    }
}