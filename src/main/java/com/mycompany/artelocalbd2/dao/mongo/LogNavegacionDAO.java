package com.mycompany.artelocalbd2.dao.mongo;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mycompany.artelocalbd2.conexion.ConexionMongo;
import com.mycompany.artelocalbd2.dao.CRUD;
import com.mycompany.artelocalbd2.modelo.LogNavegacion;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.time.LocalDateTime;
import java.sql.Timestamp;
import java.util.Date;

public class LogNavegacionDAO implements CRUD<LogNavegacion> {

    private static final String DB_NAME = "artelocal";
    private static final String COLLECTION_NAME = "log_navegacion";

    private MongoCollection<Document> getCollection(MongoClient client) {
        MongoDatabase db = client.getDatabase(DB_NAME);
        return db.getCollection(COLLECTION_NAME);
    }

    private Document modelToDocument(LogNavegacion objeto) {
        Document doc = new Document();
        if (objeto.getId() != null && !objeto.getId().isEmpty()) {
            doc.append("_id", new ObjectId(objeto.getId()));
        }
        doc.append("id_cliente", objeto.getIdCliente());
        doc.append("id_producto", objeto.getIdProducto());
        if (objeto.getFecha() != null) {
            doc.append("fecha", Timestamp.valueOf(objeto.getFecha()));
        }
        doc.append("accion", objeto.getAccion());
        doc.append("dispositivo", objeto.getDispositivo());
        return doc;
    }

    private LogNavegacion documentToModel(Document doc) {
        if (doc == null) return null;
        Date date = doc.getDate("fecha");
        LocalDateTime ldt = null;
        if (date != null) {
            ldt = new Timestamp(date.getTime()).toLocalDateTime();
        }
        return new LogNavegacion(
                doc.get("_id") != null ? doc.get("_id").toString() : null,
                doc.getInteger("id_cliente"),
                doc.getInteger("id_producto"),
                ldt,
                doc.getString("accion"),
                doc.getString("dispositivo")
        );
    }

    @Override
    public void insertar(LogNavegacion objeto) {
        if (objeto == null) return;
        MongoClient client = ConexionMongo.conectar();
        if (client == null) return;
        try {
            Document doc = modelToDocument(objeto);
            getCollection(client).insertOne(doc);
            if (doc.get("_id") != null) {
                objeto.setId(doc.get("_id").toString());
            }
        } catch (Exception e) {
            System.out.println("Error insertando log de navegacion (Mongo): " + e.getMessage());
        } finally {
            try { client.close(); } catch (Exception ignored) {}
        }
    }

    @Override
    public void actualizar(LogNavegacion objeto) {
        if (objeto == null || objeto.getIdCliente() == null) return;
        MongoClient client = ConexionMongo.conectar();
        if (client == null) return;
        try {
            Document doc = modelToDocument(objeto);
            getCollection(client).replaceOne(Filters.eq("id_cliente", objeto.getIdCliente()), doc);
        } catch (Exception e) {
            System.out.println("Error actualizando log de navegacion (Mongo): " + e.getMessage());
        } finally {
            try { client.close(); } catch (Exception ignored) {}
        }
    }

    @Override
    public void eliminar(int id) {
        MongoClient client = ConexionMongo.conectar();
        if (client == null) return;
        try {
            getCollection(client).deleteMany(Filters.eq("id_cliente", id));
        } catch (Exception e) {
            System.out.println("Error eliminando log de navegacion (Mongo): " + e.getMessage());
        } finally {
            try { client.close(); } catch (Exception ignored) {}
        }
    }

    @Override
    public LogNavegacion buscar(int id) {
        MongoClient client = ConexionMongo.conectar();
        if (client == null) return null;
        try {
            Document doc = getCollection(client).find(Filters.eq("id_cliente", id)).first();
            return documentToModel(doc);
        } catch (Exception e) {
            System.out.println("Error buscando log de navegacion (Mongo): " + e.getMessage());
        } finally {
            try { client.close(); } catch (Exception ignored) {}
        }
        return null;
    }
}
