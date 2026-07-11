package com.mycompany.artelocalbd2.dao.postgres;

import com.mycompany.artelocalbd2.conexion.ConexionPostgres;
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
        if (objeto == null) {
            System.out.println("ProductoDAO.insertar: objeto nulo");
            return;
        }

        try (Connection conexion = ConexionPostgres.conectar()) {
            if (conexion == null) {
                System.out.println("ProductoDAO.insertar: no se pudo conectar a PostgreSQL");
                return;
            }

            try (PreparedStatement stmt = conexion.prepareStatement(INSERT_SQL)) {
                stmt.setInt(1, objeto.getIdProducto());
                stmt.setString(2, objeto.getNombre());
                stmt.setString(3, objeto.getDescripcionBasica());
                stmt.setDouble(4, objeto.getPrecio());
                stmt.setInt(5, objeto.getStock());
                stmt.setInt(6, objeto.getIdArtesano());
                stmt.setInt(7, objeto.getIdCategoria());

                int filas = stmt.executeUpdate();
                System.out.println("Producto insertado correctamente, filas afectadas: " + filas);
            }

        } catch (SQLException e) {
            System.out.println("Error insertando producto: " + e.getMessage());
        }
    }

    @Override
    public void actualizar(Producto objeto) {
        if (objeto == null) {
            System.out.println("ProductoDAO.actualizar: objeto nulo");
            return;
        }

        try (Connection conexion = ConexionPostgres.conectar()) {
            if (conexion == null) {
                System.out.println("ProductoDAO.actualizar: no se pudo conectar a PostgreSQL");
                return;
            }

            try (PreparedStatement stmt = conexion.prepareStatement(UPDATE_SQL)) {
                stmt.setString(1, objeto.getNombre());
                stmt.setString(2, objeto.getDescripcionBasica());
                stmt.setDouble(3, objeto.getPrecio());
                stmt.setInt(4, objeto.getStock());
                stmt.setInt(5, objeto.getIdArtesano());
                stmt.setInt(6, objeto.getIdCategoria());
                stmt.setInt(7, objeto.getIdProducto());

                int filas = stmt.executeUpdate();
                System.out.println("Producto actualizado correctamente, filas afectadas: " + filas);
            }

        } catch (SQLException e) {
            System.out.println("Error actualizando producto: " + e.getMessage());
        }
    }

    @Override
    public void eliminar(int id) {
        try (Connection conexion = ConexionPostgres.conectar()) {
            if (conexion == null) {
                System.out.println("ProductoDAO.eliminar: no se pudo conectar a PostgreSQL");
                return;
            }

            try (PreparedStatement stmt = conexion.prepareStatement(DELETE_SQL)) {
                stmt.setInt(1, id);
                int filas = stmt.executeUpdate();
                System.out.println("Producto eliminado correctamente, filas afectadas: " + filas);
            }

        } catch (SQLException e) {
            System.out.println("Error eliminando producto: " + e.getMessage());
        }
    }

    @Override
    public Producto buscar(int id) {
        try (Connection conexion = ConexionPostgres.conectar()) {
            if (conexion == null) {
                System.out.println("ProductoDAO.buscar: no se pudo conectar a PostgreSQL");
                return null;
            }

            try (PreparedStatement stmt = conexion.prepareStatement(SELECT_SQL)) {
                stmt.setInt(1, id);
                ResultSet rs = stmt.executeQuery();

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

        } catch (SQLException e) {
            System.out.println("Error buscando producto: " + e.getMessage());
        }

        return null;
    }
}
