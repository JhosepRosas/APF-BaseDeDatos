package com.mycompany.artelocalbd2.dao.oracle;

import com.mycompany.artelocalbd2.conexion.ConexionOracle;
import com.mycompany.artelocalbd2.dao.CRUD;
import com.mycompany.artelocalbd2.modelo.Producto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ProductoDAO implements CRUD<Producto> {

    private static final String INSERT_SQL = "INSERT INTO producto (id_producto, nombre, descripcion_basica, precio, stock, id_artesano, id_categoria) VALUES (?, ?, ?, ?, ?, ?, ?)";
    private static final String UPDATE_SQL = "UPDATE producto SET nombre = ?, descripcion_basica = ?, precio = ?, stock = ?, id_artesano = ?, id_categoria = ? WHERE id_producto = ?";
    private static final String DELETE_SQL = "DELETE FROM producto WHERE id_producto = ?";
    private static final String SELECT_SQL = "SELECT id_producto, nombre, descripcion_basica, precio, stock, id_artesano, id_categoria FROM producto WHERE id_producto = ?";

    @Override
    public void insertar(Producto objeto) {
        if (objeto == null) return;
        try (Connection conexion = ConexionOracle.conectar()) {
            if (conexion == null) return;
            try (PreparedStatement stmt = conexion.prepareStatement(INSERT_SQL)) {
                stmt.setInt(1, objeto.getIdProducto());
                stmt.setString(2, objeto.getNombre());
                stmt.setString(3, objeto.getDescripcionBasica());
                stmt.setDouble(4, objeto.getPrecio());
                stmt.setInt(5, objeto.getStock());
                stmt.setInt(6, objeto.getIdArtesano());
                stmt.setInt(7, objeto.getIdCategoria());
                stmt.executeUpdate();
            }
        } catch (SQLException e) {
            System.out.println("Error insertando producto (Oracle): " + e.getMessage());
        }
    }

    @Override
    public void actualizar(Producto objeto) {
        if (objeto == null) return;
        try (Connection conexion = ConexionOracle.conectar()) {
            if (conexion == null) return;
            try (PreparedStatement stmt = conexion.prepareStatement(UPDATE_SQL)) {
                stmt.setString(1, objeto.getNombre());
                stmt.setString(2, objeto.getDescripcionBasica());
                stmt.setDouble(3, objeto.getPrecio());
                stmt.setInt(4, objeto.getStock());
                stmt.setInt(5, objeto.getIdArtesano());
                stmt.setInt(6, objeto.getIdCategoria());
                stmt.setInt(7, objeto.getIdProducto());
                stmt.executeUpdate();
            }
        } catch (SQLException e) {
            System.out.println("Error actualizando producto (Oracle): " + e.getMessage());
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
            System.out.println("Error eliminando producto (Oracle): " + e.getMessage());
        }
    }

    @Override
    public Producto buscar(int id) {
        try (Connection conexion = ConexionOracle.conectar()) {
            if (conexion == null) return null;
            try (PreparedStatement stmt = conexion.prepareStatement(SELECT_SQL)) {
                stmt.setInt(1, id);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        return new Producto(
                                rs.getInt("id_producto"),
                                rs.getString("nombre"),
                                rs.getString("descripcion_basica"),
                                rs.getDouble("precio"),
                                rs.getInt("stock"),
                                rs.getInt("id_artesano"),
                                rs.getInt("id_categoria")
                        );
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("Error buscando producto (Oracle): " + e.getMessage());
        }
        return null;
    }
}
