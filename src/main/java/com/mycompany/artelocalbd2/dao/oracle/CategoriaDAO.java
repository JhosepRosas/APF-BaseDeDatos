package com.mycompany.artelocalbd2.dao.oracle;

import com.mycompany.artelocalbd2.conexion.ConexionOracle;
import com.mycompany.artelocalbd2.dao.CRUD;
import com.mycompany.artelocalbd2.modelo.Categoria;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CategoriaDAO implements CRUD<Categoria> {

    private static final String INSERT_SQL = "INSERT INTO categoria (id_categoria, nombre_categoria, descripcion) VALUES (?, ?, ?)";
    private static final String UPDATE_SQL = "UPDATE categoria SET nombre_categoria = ?, descripcion = ? WHERE id_categoria = ?";
    private static final String DELETE_SQL = "DELETE FROM categoria WHERE id_categoria = ?";
    private static final String SELECT_SQL = "SELECT id_categoria, nombre_categoria, descripcion FROM categoria WHERE id_categoria = ?";

    @Override
    public void insertar(Categoria objeto) {
        if (objeto == null) return;
        try (Connection conexion = ConexionOracle.conectar()) {
            if (conexion == null) return;
            try (PreparedStatement stmt = conexion.prepareStatement(INSERT_SQL)) {
                stmt.setInt(1, objeto.getIdCategoria());
                stmt.setString(2, objeto.getNombreCategoria());
                stmt.setString(3, objeto.getDescripcion());
                stmt.executeUpdate();
            }
        } catch (SQLException e) {
            System.out.println("Error insertando categoria (Oracle): " + e.getMessage());
        }
    }

    @Override
    public void actualizar(Categoria objeto) {
        if (objeto == null) return;
        try (Connection conexion = ConexionOracle.conectar()) {
            if (conexion == null) return;
            try (PreparedStatement stmt = conexion.prepareStatement(UPDATE_SQL)) {
                stmt.setString(1, objeto.getNombreCategoria());
                stmt.setString(2, objeto.getDescripcion());
                stmt.setInt(3, objeto.getIdCategoria());
                stmt.executeUpdate();
            }
        } catch (SQLException e) {
            System.out.println("Error actualizando categoria (Oracle): " + e.getMessage());
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
            System.out.println("Error eliminando categoria (Oracle): " + e.getMessage());
        }
    }

    @Override
    public Categoria buscar(int id) {
        try (Connection conexion = ConexionOracle.conectar()) {
            if (conexion == null) return null;
            try (PreparedStatement stmt = conexion.prepareStatement(SELECT_SQL)) {
                stmt.setInt(1, id);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        return new Categoria(
                                rs.getInt("id_categoria"),
                                rs.getString("nombre_categoria"),
                                rs.getString("descripcion")
                        );
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("Error buscando categoria (Oracle): " + e.getMessage());
        }
        return null;
    }
}
