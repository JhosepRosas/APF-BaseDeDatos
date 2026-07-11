package com.mycompany.artelocalbd2.dao.oracle;

import com.mycompany.artelocalbd2.conexion.ConexionOracle;
import com.mycompany.artelocalbd2.dao.CRUD;
import com.mycompany.artelocalbd2.modelo.Cliente;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ClienteDAO implements CRUD<Cliente> {

    private static final String INSERT_SQL = "INSERT INTO cliente (id_cliente, nombre, correo, telefono) VALUES (?, ?, ?, ?)";
    private static final String UPDATE_SQL = "UPDATE cliente SET nombre = ?, correo = ?, telefono = ? WHERE id_cliente = ?";
    private static final String DELETE_SQL = "DELETE FROM cliente WHERE id_cliente = ?";
    private static final String SELECT_SQL = "SELECT id_cliente, nombre, correo, telefono FROM cliente WHERE id_cliente = ?";

    @Override
    public void insertar(Cliente objeto) {
        if (objeto == null) return;
        try (Connection conexion = ConexionOracle.conectar()) {
            if (conexion == null) return;
            try (PreparedStatement stmt = conexion.prepareStatement(INSERT_SQL)) {
                stmt.setInt(1, objeto.getIdCliente());
                stmt.setString(2, objeto.getNombre());
                stmt.setString(3, objeto.getCorreo());
                stmt.setString(4, objeto.getTelefono());
                stmt.executeUpdate();
            }
        } catch (SQLException e) {
            System.out.println("Error insertando cliente (Oracle): " + e.getMessage());
        }
    }

    @Override
    public void actualizar(Cliente objeto) {
        if (objeto == null) return;
        try (Connection conexion = ConexionOracle.conectar()) {
            if (conexion == null) return;
            try (PreparedStatement stmt = conexion.prepareStatement(UPDATE_SQL)) {
                stmt.setString(1, objeto.getNombre());
                stmt.setString(2, objeto.getCorreo());
                stmt.setString(3, objeto.getTelefono());
                stmt.setInt(4, objeto.getIdCliente());
                stmt.executeUpdate();
            }
        } catch (SQLException e) {
            System.out.println("Error actualizando cliente (Oracle): " + e.getMessage());
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
            System.out.println("Error eliminando cliente (Oracle): " + e.getMessage());
        }
    }

    @Override
    public Cliente buscar(int id) {
        try (Connection conexion = ConexionOracle.conectar()) {
            if (conexion == null) return null;
            try (PreparedStatement stmt = conexion.prepareStatement(SELECT_SQL)) {
                stmt.setInt(1, id);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        return new Cliente(
                                rs.getInt("id_cliente"),
                                rs.getString("nombre"),
                                rs.getString("correo"),
                                rs.getString("telefono")
                        );
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("Error buscando cliente (Oracle): " + e.getMessage());
        }
        return null;
    }
}
