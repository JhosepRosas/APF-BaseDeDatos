package com.mycompany.artelocalbd2.dao.postgres;

import com.mycompany.artelocalbd2.conexion.ConexionPostgres;
import com.mycompany.artelocalbd2.dao.CRUD;
import com.mycompany.artelocalbd2.modelo.ValoracionVendedor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ValoracionVendedorDAO implements CRUD<ValoracionVendedor> {

    private static final String INSERT_SQL = "INSERT INTO valoracion_vendedor (id_valoracion, puntuacion, comentario, id_artesano, id_cliente) VALUES (?, ?, ?, ?, ?)";
    private static final String UPDATE_SQL = "UPDATE valoracion_vendedor SET puntuacion = ?, comentario = ?, id_artesano = ?, id_cliente = ? WHERE id_valoracion = ?";
    private static final String DELETE_SQL = "DELETE FROM valoracion_vendedor WHERE id_valoracion = ?";
    private static final String SELECT_SQL = "SELECT id_valoracion, puntuacion, comentario, id_artesano, id_cliente FROM valoracion_vendedor WHERE id_valoracion = ?";

    @Override
    public void insertar(ValoracionVendedor objeto) {
        if (objeto == null) return;
        try (Connection conexion = ConexionPostgres.conectar()) {
            if (conexion == null) return;
            try (PreparedStatement stmt = conexion.prepareStatement(INSERT_SQL)) {
                stmt.setInt(1, objeto.getIdValoracion());
                stmt.setInt(2, objeto.getPuntuacion());
                stmt.setString(3, objeto.getComentario());
                stmt.setInt(4, objeto.getIdArtesano());
                stmt.setInt(5, objeto.getIdCliente());
                stmt.executeUpdate();
            }
        } catch (SQLException e) {
            System.out.println("Error insertando valoracion_vendedor (PostgreSQL): " + e.getMessage());
        }
    }

    @Override
    public void actualizar(ValoracionVendedor objeto) {
        if (objeto == null) return;
        try (Connection conexion = ConexionPostgres.conectar()) {
            if (conexion == null) return;
            try (PreparedStatement stmt = conexion.prepareStatement(UPDATE_SQL)) {
                stmt.setInt(1, objeto.getPuntuacion());
                stmt.setString(2, objeto.getComentario());
                stmt.setInt(3, objeto.getIdArtesano());
                stmt.setInt(4, objeto.getIdCliente());
                stmt.setInt(5, objeto.getIdValoracion());
                stmt.executeUpdate();
            }
        } catch (SQLException e) {
            System.out.println("Error actualizando valoracion_vendedor (PostgreSQL): " + e.getMessage());
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
            System.out.println("Error eliminando valoracion_vendedor (PostgreSQL): " + e.getMessage());
        }
    }

    @Override
    public ValoracionVendedor buscar(int id) {
        try (Connection conexion = ConexionPostgres.conectar()) {
            if (conexion == null) return null;
            try (PreparedStatement stmt = conexion.prepareStatement(SELECT_SQL)) {
                stmt.setInt(1, id);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        return new ValoracionVendedor(
                                rs.getInt("id_valoracion"),
                                rs.getInt("puntuacion"),
                                rs.getString("comentario"),
                                rs.getInt("id_artesano"),
                                rs.getInt("id_cliente")
                        );
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("Error buscando valoracion_vendedor (PostgreSQL): " + e.getMessage());
        }
        return null;
    }
}
