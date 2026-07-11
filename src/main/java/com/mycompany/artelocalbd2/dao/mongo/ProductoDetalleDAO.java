package com.mycompany.artelocalbd2.dao.mongo;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mycompany.artelocalbd2.conexion.ConexionMongo;
import com.mycompany.artelocalbd2.dao.CRUD;
import com.mycompany.artelocalbd2.modelo.ProductoDetalle;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.ArrayList;

public class ProductoDetalleDAO implements CRUD<ProductoDetalle> {

    private static final String DB_NAME = "artelocal";
    private static final String COLLECTION_NAME = "producto_detalle";

    private MongoCollection<Document> getCollection(MongoClient client) {
        MongoDatabase db = client.getDatabase(DB_NAME);
        return db.getCollection(COLLECTION_NAME);
    }

    private Document modelToDocument(ProductoDetalle objeto) {
        Document doc = new Document();
        if (objeto.getId() != null && !objeto.getId().isEmpty()) {
            doc.append("_id", new ObjectId(objeto.getId()));
        }
        doc.append("id_producto", objeto.getIdProducto());
        doc.append("descripcion_completa", objeto.getDescripcionCompleta());
        doc.append("imagenes", objeto.getImagenes() != null ? objeto.getImagenes() : new ArrayList<String>());
        doc.append("materiales", objeto.getMateriales());
        doc.append("caracteristicas", objeto.getCaracteristicas());
        return doc;
    }

    private ProductoDetalle documentToModel(Document doc) {
        if (doc == null) return null;
        return new ProductoDetalle(
                doc.get("_id") != null ? doc.get("_id").toString() : null,
                doc.getInteger("id_producto"),
                doc.getString("descripcion_completa"),
                doc.getList("imagenes", String.class),
                doc.getString("materiales"),
                doc.getString("caracteristicas")
        );
    }

    @Override
    public void insertar(ProductoDetalle objeto) {
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
            System.out.println("Error insertando producto detalle (Mongo): " + e.getMessage());
        } finally {
            try { client.close(); } catch (Exception ignored) {}
        }
    }

    @Override
    public void actualizar(ProductoDetalle objeto) {
        if (objeto == null || objeto.getIdProducto() == null) return;
        MongoClient client = ConexionMongo.conectar();
        if (client == null) return;
        try {
            Document doc = modelToDocument(objeto);
            getCollection(client).replaceOne(Filters.eq("id_producto", objeto.getIdProducto()), doc);
        } catch (Exception e) {
            System.out.println("Error actualizando producto detalle (Mongo): " + e.getMessage());
        } finally {
            try { client.close(); } catch (Exception ignored) {}
        }
    }

    @Override
    public void eliminar(int id) {
        MongoClient client = ConexionMongo.conectar();
        if (client == null) return;
        try {
            getCollection(client).deleteOne(Filters.eq("id_producto", id));
        } catch (Exception e) {
            System.out.println("Error eliminando producto detalle (Mongo): " + e.getMessage());
        } finally {
            try { client.close(); } catch (Exception ignored) {}
        }
    }

    @Override
    public ProductoDetalle buscar(int id) {
        MongoClient client = ConexionMongo.conectar();
        if (client == null) return null;
        try {
            Document doc = getCollection(client).find(Filters.eq("id_producto", id)).first();
            return documentToModel(doc);
        } catch (Exception e) {
            System.out.println("Error buscando producto detalle (Mongo): " + e.getMessage());
        } finally {
            try { client.close(); } catch (Exception ignored) {}
        }
        return null;
    }
}
