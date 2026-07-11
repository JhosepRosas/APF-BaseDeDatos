package com.mycompany.artelocalbd2.dao.mongo;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mycompany.artelocalbd2.conexion.ConexionMongo;
import com.mycompany.artelocalbd2.dao.CRUD;
import com.mycompany.artelocalbd2.modelo.ClientePerfil;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.ArrayList;

public class ClientePerfilDAO implements CRUD<ClientePerfil> {

    private static final String DB_NAME = "artelocal";
    private static final String COLLECTION_NAME = "cliente_perfil";

    private MongoCollection<Document> getCollection(MongoClient client) {
        MongoDatabase db = client.getDatabase(DB_NAME);
        return db.getCollection(COLLECTION_NAME);
    }

    private Document modelToDocument(ClientePerfil objeto) {
        Document doc = new Document();
        if (objeto.getId() != null && !objeto.getId().isEmpty()) {
            doc.append("_id", new ObjectId(objeto.getId()));
        }
        doc.append("id_cliente", objeto.getIdCliente());
        doc.append("historial_compras", objeto.getHistorialCompras() != null ? objeto.getHistorialCompras() : new ArrayList<Integer>());
        doc.append("lista_deseos", objeto.getListaDeseos() != null ? objeto.getListaDeseos() : new ArrayList<Integer>());
        doc.append("preferencias", objeto.getPreferencias());
        return doc;
    }

    private ClientePerfil documentToModel(Document doc) {
        if (doc == null) return null;
        return new ClientePerfil(
                doc.get("_id") != null ? doc.get("_id").toString() : null,
                doc.getInteger("id_cliente"),
                doc.getList("historial_compras", Integer.class),
                doc.getList("lista_deseos", Integer.class),
                doc.getString("preferencias")
        );
    }

    @Override
    public void insertar(ClientePerfil objeto) {
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
            System.out.println("Error insertando cliente perfil (Mongo): " + e.getMessage());
        } finally {
            try { client.close(); } catch (Exception ignored) {}
        }
    }

    @Override
    public void actualizar(ClientePerfil objeto) {
        if (objeto == null || objeto.getIdCliente() == null) return;
        MongoClient client = ConexionMongo.conectar();
        if (client == null) return;
        try {
            Document doc = modelToDocument(objeto);
            getCollection(client).replaceOne(Filters.eq("id_cliente", objeto.getIdCliente()), doc);
        } catch (Exception e) {
            System.out.println("Error actualizando cliente perfil (Mongo): " + e.getMessage());
        } finally {
            try { client.close(); } catch (Exception ignored) {}
        }
    }

    @Override
    public void eliminar(int id) {
        MongoClient client = ConexionMongo.conectar();
        if (client == null) return;
        try {
            getCollection(client).deleteOne(Filters.eq("id_cliente", id));
        } catch (Exception e) {
            System.out.println("Error eliminando cliente perfil (Mongo): " + e.getMessage());
        } finally {
            try { client.close(); } catch (Exception ignored) {}
        }
    }

    @Override
    public ClientePerfil buscar(int id) {
        MongoClient client = ConexionMongo.conectar();
        if (client == null) return null;
        try {
            Document doc = getCollection(client).find(Filters.eq("id_cliente", id)).first();
            return documentToModel(doc);
        } catch (Exception e) {
            System.out.println("Error buscando cliente perfil (Mongo): " + e.getMessage());
        } finally {
            try { client.close(); } catch (Exception ignored) {}
        }
        return null;
    }
}
