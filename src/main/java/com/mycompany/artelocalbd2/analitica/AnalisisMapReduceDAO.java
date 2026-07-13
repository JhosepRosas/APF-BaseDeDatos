package com.mycompany.artelocalbd2.analitica;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoDatabase;
import com.mycompany.artelocalbd2.conexion.ConexionMongo;
import org.bson.BsonJavaScript;
import org.bson.Document;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Implementa el proceso MapReduce básico requerido por la consigna del
 * Trabajo Final (Base de Datos II - ArteLocal).
 *
 * Objetivo: contar cuántas veces fue visitado cada producto, a partir
 * de la coleccion "log_navegacion", usando el comando nativo mapReduce
 * de MongoDB (fases Map y Reduce explícitas).
 *
 * Equivalente conceptual en Hadoop:
 *   - Mappers: procesan cada documento de log_navegacion en paralelo
 *     y emiten (id_producto, 1) si la accion fue "vista".
 *   - Shuffle & Sort: agrupa los pares intermedios por id_producto.
 *   - Reducers: suman los valores agrupados por cada id_producto.
 */
public class AnalisisMapReduceDAO {

    private static final String DB_NAME = "artelocal";
    private static final String COLLECTION_ORIGEN = "log_navegacion";
    private static final String COLLECTION_SALIDA = "productos_mas_visitados";

    // --- Funcion MAP (JavaScript, ejecutada del lado del servidor Mongo) ---
    private static final String MAP_FUNCTION =
            "function() { if (this.accion === 'vista') { emit(this.id_producto, 1); } }";

    // --- Funcion REDUCE (JavaScript, ejecutada del lado del servidor Mongo) ---
    private static final String REDUCE_FUNCTION =
            "function(id_producto, valores) { return Array.sum(valores); }";

    /**
     * Ejecuta el MapReduce sobre log_navegacion y devuelve un mapa
     * ordenado con {id_producto -> total de visitas}, de mayor a menor.
     */
    public Map<Integer, Integer> contarVisitasPorProducto() {

        Map<Integer, Integer> resultado = new LinkedHashMap<>();

        MongoClient client = ConexionMongo.conectar();
        if (client == null) {
            System.out.println("No se pudo conectar a MongoDB para el MapReduce.");
            return resultado;
        }

        try {
            MongoDatabase db = client.getDatabase(DB_NAME);

            // Comando nativo mapReduce equivalente al que se usa en mongosh
            Document comando = new Document("mapReduce", COLLECTION_ORIGEN)
                    .append("map", new BsonJavaScript(MAP_FUNCTION))
                    .append("reduce", new BsonJavaScript(REDUCE_FUNCTION))
                    .append("out", COLLECTION_SALIDA);

            Document respuesta = db.runCommand(comando);
            System.out.println("Resultado del comando mapReduce: " + respuesta.toJson());

            // Leer la coleccion de salida generada por el MapReduce
            db.getCollection(COLLECTION_SALIDA)
                    .find()
                    .sort(new Document("value", -1))
                    .forEach(doc -> resultado.put(
                            doc.getInteger("_id"),
                            doc.get("value", Number.class).intValue()
                    ));

        } catch (Exception e) {
            System.out.println("Error ejecutando MapReduce: " + e.getMessage());
        } finally {
            client.close();
        }

        return resultado;
    }

    /**
     * Punto de entrada de prueba: imprime el ranking de productos mas
     * visitados. Puede invocarse desde ArteLocalBD2.main() o ejecutarse
     * de forma independiente para la demostracion practica.
     */
    public static void main(String[] args) {
        AnalisisMapReduceDAO analisis = new AnalisisMapReduceDAO();
        Map<Integer, Integer> ranking = analisis.contarVisitasPorProducto();

        System.out.println("\n=== Ranking de productos mas visitados ===");
        ranking.forEach((idProducto, visitas) ->
                System.out.println("Producto " + idProducto + ": " + visitas + " visitas"));
    }
}