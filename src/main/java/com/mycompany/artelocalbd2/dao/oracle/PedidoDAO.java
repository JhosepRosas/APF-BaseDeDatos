package com.mycompany.artelocalbd2.dao.oracle;

import com.mycompany.artelocalbd2.conexion.ConexionOracle;
import com.mycompany.artelocalbd2.dao.CRUD;
import com.mycompany.artelocalbd2.modelo.Pedido;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;
import java.time.LocalDate;

public class PedidoDAO implements CRUD<Pedido> {

    private static final String INSERT_SQL = "INSERT INTO pedido (id_pedido, fecha_pedido, estado, total, id_cliente) VALUES (?, ?, ?, ?, ?)";
    private static final String UPDATE_SQL = "UPDATE pedido SET fecha_pedido = ?, estado = ?, total = ?, id_cliente = ? WHERE id_pedido = ?";
    private static final String DELETE_SQL = "DELETE FROM pedido WHERE id_pedido = ?";
    private static final String SELECT_SQL = "SELECT id_pedido, fecha_pedido, estado, total, id_cliente FROM pedido WHERE id_pedido = ?";

    @Override
    public void insertar(Pedido objeto) {
        if (objeto == null) return;
        try (Connection conexion = ConexionOracle.conectar()) {
            if (conexion == null) return;
            try (PreparedStatement stmt = conexion.prepareStatement(INSERT_SQL)) {
                stmt.setInt(1, objeto.getIdPedido());
                stmt.setDate(2, objeto.getFechaPedido() != null ? Date.valueOf(objeto.getFechaPedido()) : null);
                stmt.setString(3, objeto.getEstado());
                stmt.setDouble(4, objeto.getTotal());
                stmt.setInt(5, objeto.getIdCliente());
                stmt.executeUpdate();
            }
        } catch (SQLException e) {
            System.out.println("Error insertando pedido (Oracle): " + e.getMessage());
        }
    }

    @Override
    public void actualizar(Pedido objeto) {
        if (objeto == null) return;
        try (Connection conexion = ConexionOracle.conectar()) {
            if (conexion == null) return;
            try (PreparedStatement stmt = conexion.prepareStatement(UPDATE_SQL)) {
                stmt.setDate(1, objeto.getFechaPedido() != null ? Date.valueOf(objeto.getFechaPedido()) : null);
                stmt.setString(2, objeto.getEstado());
                stmt.setDouble(3, objeto.getTotal());
                stmt.setInt(4, objeto.getIdCliente());
                stmt.setInt(5, objeto.getIdPedido());
                stmt.executeUpdate();
            }
        } catch (SQLException e) {
            System.out.println("Error actualizando pedido (Oracle): " + e.getMessage());
        }
    }

    @Override
    public void eliminar(int id) {
        try (Connection conexion = ConexionOracle.conectar()) {
            if (conexion == null) return;
            try (PreparedStatement stmt = conexion.prepareStatement(DELETE_SQL)) {
                stmt.setInt(1, id);
                stmt.executeUpdate();
            }
        } catch (SQLException e) {
            System.out.println("Error eliminando pedido (Oracle): " + e.getMessage());
        }
    }

    @Override
    public Pedido buscar(int id) {
        try (Connection conexion = ConexionOracle.conectar()) {
            if (conexion == null) return null;
            try (PreparedStatement stmt = conexion.prepareStatement(SELECT_SQL)) {
                stmt.setInt(1, id);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        Date sqlDate = rs.getDate("fecha_pedido");
                        LocalDate localDate = sqlDate != null ? sqlDate.toLocalDate() : null;
                        return new Pedido(
                                rs.getInt("id_pedido"),
                                localDate,
                                rs.getString("estado"),
                                rs.getDouble("total"),
                                rs.getInt("id_cliente")
                        );
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("Error buscando pedido (Oracle): " + e.getMessage());
        }
        return null;
    }
}
