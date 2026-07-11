package com.mycompany.artelocalbd2.dao.oracle;

import com.mycompany.artelocalbd2.conexion.ConexionOracle;
import com.mycompany.artelocalbd2.dao.CRUD;
import com.mycompany.artelocalbd2.modelo.Artesano;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ArtesanoDAO implements CRUD<Artesano> {

    private static final String INSERT_SQL = "INSERT INTO artesano (id_artesano, nombre, correo, telefono, region) VALUES (?, ?, ?, ?, ?)";
    private static final String UPDATE_SQL = "UPDATE artesano SET nombre = ?, correo = ?, telefono = ?, region = ? WHERE id_artesano = ?";
    private static final String DELETE_SQL = "DELETE FROM artesano WHERE id_artesano = ?";
    private static final String SELECT_SQL = "SELECT id_artesano, nombre, correo, telefono, region FROM artesano WHERE id_artesano = ?";

    @Override
    public void insertar(Artesano objeto) {
        if (objeto == null) return;
        try (Connection conexion = ConexionOracle.conectar()) {
            if (conexion == null) return;
            try (PreparedStatement stmt = conexion.prepareStatement(INSERT_SQL)) {
                stmt.setInt(1, objeto.getIdArtesano());
                stmt.setString(2, objeto.getNombre());
                stmt.setString(3, objeto.getCorreo());
                stmt.setString(4, objeto.getTelefono());
                stmt.setString(5, objeto.getRegion());
                stmt.executeUpdate();
            }
        } catch (SQLException e) {
            System.out.println("Error insertando artesano (Oracle): " + e.getMessage());
        }
    }

    @Override
    public void actualizar(Artesano objeto) {
        if (objeto == null) return;
        try (Connection conexion = ConexionOracle.conectar()) {
            if (conexion == null) return;
            try (PreparedStatement stmt = conexion.prepareStatement(UPDATE_SQL)) {
                stmt.setString(1, objeto.getNombre());
                stmt.setString(2, objeto.getCorreo());
                stmt.setString(3, objeto.getTelefono());
                stmt.setString(4, objeto.getRegion());
                stmt.setInt(5, objeto.getIdArtesano());
                stmt.executeUpdate();
            }
        } catch (SQLException e) {
            System.out.println("Error actualizando artesano (Oracle): " + e.getMessage());
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
            System.out.println("Error eliminando artesano (Oracle): " + e.getMessage());
        }
    }

    @Override
    public Artesano buscar(int id) {
        try (Connection conexion = ConexionOracle.conectar()) {
            if (conexion == null) return null;
            try (PreparedStatement stmt = conexion.prepareStatement(SELECT_SQL)) {
                stmt.setInt(1, id);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        return new Artesano(
                                rs.getInt("id_artesano"),
                                rs.getString("nombre"),
                                rs.getString("correo"),
                                rs.getString("telefono"),
                                rs.getString("region")
                        );
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("Error buscando artesano (Oracle): " + e.getMessage());
        }
        return null;
    }
}
