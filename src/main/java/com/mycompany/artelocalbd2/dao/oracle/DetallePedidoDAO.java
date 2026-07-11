package com.mycompany.artelocalbd2.dao.oracle;

import com.mycompany.artelocalbd2.conexion.ConexionOracle;
import com.mycompany.artelocalbd2.dao.CRUD;
import com.mycompany.artelocalbd2.modelo.DetallePedido;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DetallePedidoDAO implements CRUD<DetallePedido> {

    private static final String INSERT_SQL = "INSERT INTO detalle_pedido (id_detalle, cantidad, subtotal, id_pedido, id_producto) VALUES (?, ?, ?, ?, ?)";
    private static final String UPDATE_SQL = "UPDATE detalle_pedido SET cantidad = ?, subtotal = ?, id_pedido = ?, id_producto = ? WHERE id_detalle = ?";
    private static final String DELETE_SQL = "DELETE FROM detalle_pedido WHERE id_detalle = ?";
    private static final String SELECT_SQL = "SELECT id_detalle, cantidad, subtotal, id_pedido, id_producto FROM detalle_pedido WHERE id_detalle = ?";

    @Override
    public void insertar(DetallePedido objeto) {
        if (objeto == null) return;
        try (Connection conexion = ConexionOracle.conectar()) {
            if (conexion == null) return;
            try (PreparedStatement stmt = conexion.prepareStatement(INSERT_SQL)) {
                stmt.setInt(1, objeto.getIdDetalle());
                stmt.setInt(2, objeto.getCantidad());
                stmt.setDouble(3, objeto.getSubtotal());
                stmt.setInt(4, objeto.getIdPedido());
                stmt.setInt(5, objeto.getIdProducto());
                stmt.executeUpdate();
            }
        } catch (SQLException e) {
            System.out.println("Error insertando detalle_pedido (Oracle): " + e.getMessage());
        }
    }

    @Override
    public void actualizar(DetallePedido objeto) {
        if (objeto == null) return;
        try (Connection conexion = ConexionOracle.conectar()) {
            if (conexion == null) return;
            try (PreparedStatement stmt = conexion.prepareStatement(UPDATE_SQL)) {
                stmt.setInt(1, objeto.getCantidad());
                stmt.setDouble(2, objeto.getSubtotal());
                stmt.setInt(3, objeto.getIdPedido());
                stmt.setInt(4, objeto.getIdProducto());
                stmt.setInt(5, objeto.getIdDetalle());
                stmt.executeUpdate();
            }
        } catch (SQLException e) {
            System.out.println("Error actualizando detalle_pedido (Oracle): " + e.getMessage());
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
            System.out.println("Error eliminando detalle_pedido (Oracle): " + e.getMessage());
        }
    }

    @Override
    public DetallePedido buscar(int id) {
        try (Connection conexion = ConexionOracle.conectar()) {
            if (conexion == null) return null;
            try (PreparedStatement stmt = conexion.prepareStatement(SELECT_SQL)) {
                stmt.setInt(1, id);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        return new DetallePedido(
                                rs.getInt("id_detalle"),
                                rs.getInt("cantidad"),
                                rs.getDouble("subtotal"),
                                rs.getInt("id_pedido"),
                                rs.getInt("id_producto")
                        );
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("Error buscando detalle_pedido (Oracle): " + e.getMessage());
        }
        return null;
    }
}
