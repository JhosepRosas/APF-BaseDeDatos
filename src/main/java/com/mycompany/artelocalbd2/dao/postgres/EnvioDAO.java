package com.mycompany.artelocalbd2.dao.postgres;

import com.mycompany.artelocalbd2.conexion.ConexionPostgres;
import com.mycompany.artelocalbd2.dao.CRUD;
import com.mycompany.artelocalbd2.modelo.Envio;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;
import java.time.LocalDate;

public class EnvioDAO implements CRUD<Envio> {

    private static final String INSERT_SQL = "INSERT INTO envio (id_envio, empresa_transporte, fecha_envio, estado_envio, id_pedido) VALUES (?, ?, ?, ?, ?)";
    private static final String UPDATE_SQL = "UPDATE envio SET empresa_transporte = ?, fecha_envio = ?, estado_envio = ?, id_pedido = ? WHERE id_envio = ?";
    private static final String DELETE_SQL = "DELETE FROM envio WHERE id_envio = ?";
    private static final String SELECT_SQL = "SELECT id_envio, empresa_transporte, fecha_envio, estado_envio, id_pedido FROM envio WHERE id_envio = ?";

    @Override
    public void insertar(Envio objeto) {
        if (objeto == null) return;
        try (Connection conexion = ConexionPostgres.conectar()) {
            if (conexion == null) return;
            try (PreparedStatement stmt = conexion.prepareStatement(INSERT_SQL)) {
                stmt.setInt(1, objeto.getIdEnvio());
                stmt.setString(2, objeto.getEmpresaTransporte());
                stmt.setDate(3, objeto.getFechaEnvio() != null ? Date.valueOf(objeto.getFechaEnvio()) : null);
                stmt.setString(4, objeto.getEstadoEnvio());
                stmt.setInt(5, objeto.getIdPedido());
                stmt.executeUpdate();
            }
        } catch (SQLException e) {
            System.out.println("Error insertando envio (PostgreSQL): " + e.getMessage());
        }
    }

    @Override
    public void actualizar(Envio objeto) {
        if (objeto == null) return;
        try (Connection conexion = ConexionPostgres.conectar()) {
            if (conexion == null) return;
            try (PreparedStatement stmt = conexion.prepareStatement(UPDATE_SQL)) {
                stmt.setString(1, objeto.getEmpresaTransporte());
                stmt.setDate(2, objeto.getFechaEnvio() != null ? Date.valueOf(objeto.getFechaEnvio()) : null);
                stmt.setString(3, objeto.getEstadoEnvio());
                stmt.setInt(4, objeto.getIdPedido());
                stmt.setInt(5, objeto.getIdEnvio());
                stmt.executeUpdate();
            }
        } catch (SQLException e) {
            System.out.println("Error actualizando envio (PostgreSQL): " + e.getMessage());
        }
    }

    @Override
    public void eliminar(int id) {
        try (Connection conexion = ConexionPostgres.conectar()) {
            if (conexion == null) return;
            try (PreparedStatement stmt = conexion.prepareStatement(DELETE_SQL)) {
                stmt.setInt(1, id);
                stmt.executeUpdate();
            }
        } catch (SQLException e) {
            System.out.println("Error eliminando envio (PostgreSQL): " + e.getMessage());
        }
    }

    @Override
    public Envio buscar(int id) {
        try (Connection conexion = ConexionPostgres.conectar()) {
            if (conexion == null) return null;
            try (PreparedStatement stmt = conexion.prepareStatement(SELECT_SQL)) {
                stmt.setInt(1, id);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        Date sqlDate = rs.getDate("fecha_envio");
                        LocalDate localDate = sqlDate != null ? sqlDate.toLocalDate() : null;
                        return new Envio(
                                rs.getInt("id_envio"),
                                rs.getString("empresa_transporte"),
                                localDate,
                                rs.getString("estado_envio"),
                                rs.getInt("id_pedido")
                        );
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("Error buscando envio (PostgreSQL): " + e.getMessage());
        }
        return null;
    }
}
