package com.mycompany.artelocalbd2.dao.cassandra;

import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.cql.BoundStatement;
import com.datastax.oss.driver.api.core.cql.PreparedStatement;
import com.datastax.oss.driver.api.core.cql.ResultSet;
import com.datastax.oss.driver.api.core.cql.Row;
import com.mycompany.artelocalbd2.conexion.ConexionCassandra;
import com.mycompany.artelocalbd2.dao.CRUD;
import com.mycompany.artelocalbd2.modelo.SeguimientoPedido;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

public class SeguimientoPedidoDAO implements CRUD<SeguimientoPedido> {

    @Override
    public void insertar(SeguimientoPedido objeto) {
        if (objeto == null) return;
        try (CqlSession session = ConexionCassandra.conectar()) {
            if (session == null) return;
            PreparedStatement ps = session.prepare(
                    "INSERT INTO seguimiento_pedido (id_pedido, fecha_hora, estado, ubicacion, responsable) VALUES (?, ?, ?, ?, ?)"
            );
            Instant instant = objeto.getFechaHora() != null ? objeto.getFechaHora().toInstant(ZoneOffset.UTC) : null;
            BoundStatement bs = ps.bind(
                    objeto.getIdPedido(),
                    instant,
                    objeto.getEstado(),
                    objeto.getUbicacion(),
                    objeto.getResponsable()
            );
            session.execute(bs);
        } catch (Exception e) {
            System.out.println("Error insertando seguimiento_pedido (Cassandra): " + e.getMessage());
        }
    }

    @Override
    public void actualizar(SeguimientoPedido objeto) {
        if (objeto == null) return;
        try (CqlSession session = ConexionCassandra.conectar()) {
            if (session == null) return;
            PreparedStatement ps = session.prepare(
                    "UPDATE seguimiento_pedido SET fecha_hora = ?, estado = ?, ubicacion = ?, responsable = ? WHERE id_pedido = ?"
            );
            Instant instant = objeto.getFechaHora() != null ? objeto.getFechaHora().toInstant(ZoneOffset.UTC) : null;
            BoundStatement bs = ps.bind(
                    instant,
                    objeto.getEstado(),
                    objeto.getUbicacion(),
                    objeto.getResponsable(),
                    objeto.getIdPedido()
            );
            session.execute(bs);
        } catch (Exception e) {
            System.out.println("Error actualizando seguimiento_pedido (Cassandra): " + e.getMessage());
        }
    }

    @Override
    public void eliminar(int id) {
        try (CqlSession session = ConexionCassandra.conectar()) {
            if (session == null) return;
            PreparedStatement ps = session.prepare(
                    "DELETE FROM seguimiento_pedido WHERE id_pedido = ?"
            );
            BoundStatement bs = ps.bind(id);
            session.execute(bs);
        } catch (Exception e) {
            System.out.println("Error eliminando seguimiento_pedido (Cassandra): " + e.getMessage());
        }
    }

    @Override
    public SeguimientoPedido buscar(int id) {
        try (CqlSession session = ConexionCassandra.conectar()) {
            if (session == null) return null;
            PreparedStatement ps = session.prepare(
                    "SELECT id_pedido, fecha_hora, estado, ubicacion, responsable FROM seguimiento_pedido WHERE id_pedido = ?"
            );
            BoundStatement bs = ps.bind(id);
            ResultSet rs = session.execute(bs);
            Row row = rs.one();
            if (row != null) {
                Instant instant = row.getInstant("fecha_hora");
                LocalDateTime ldt = instant != null ? LocalDateTime.ofInstant(instant, ZoneOffset.UTC) : null;
                return new SeguimientoPedido(
                        row.getInt("id_pedido"),
                        ldt,
                        row.getString("estado"),
                        row.getString("ubicacion"),
                        row.getString("responsable")
                );
            }
        } catch (Exception e) {
            System.out.println("Error buscando seguimiento_pedido (Cassandra): " + e.getMessage());
        }
        return null;
    }
}
