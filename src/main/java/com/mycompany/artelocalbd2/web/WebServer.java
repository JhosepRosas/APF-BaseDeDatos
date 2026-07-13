package com.mycompany.artelocalbd2.web;

import com.mycompany.artelocalbd2.analitica.AnalisisMapReduceDAO;
import com.mycompany.artelocalbd2.conexion.ConexionCassandra;
import com.mycompany.artelocalbd2.conexion.ConexionMongo;
import com.mycompany.artelocalbd2.conexion.ConexionOracle;
import com.mycompany.artelocalbd2.conexion.ConexionPostgres;
import com.mongodb.client.MongoClient;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import com.datastax.oss.driver.api.core.CqlSession;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.Statement;
import java.util.List;
import java.util.ArrayList;
import org.bson.Document;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.result.UpdateResult;
import com.mongodb.client.result.DeleteResult;
import com.datastax.oss.driver.api.core.cql.ResultSet;

public class WebServer {

    private static final int PORT = 8081;

    public static void start() {
        try {
            HttpServer server = HttpServer.create(new InetSocketAddress(PORT), 0);
            server.createContext("/", new RootHandler());
            server.createContext("/status", new StatusHandler());
            server.createContext("/modificar", new ModificarHandler());
            server.createContext("/mapreduce", new MapReduceHandler());
            server.setExecutor(null);
            server.start();
            System.out.println("Servidor web iniciado en http://localhost:" + PORT);
            System.out.println("Abre esa dirección en tu navegador para ver la página de conexión.");
        } catch (IOException e) {
            System.out.println("Error iniciando servidor web: " + e.getMessage());
        }
    }

    private static class RootHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            URI requestURI = exchange.getRequestURI();
            if ("/".equals(requestURI.getPath())) {
                String html = loadIndexHtml();
                exchange.getResponseHeaders().add("Content-Type", "text/html; charset=UTF-8");
                exchange.sendResponseHeaders(200, html.getBytes(StandardCharsets.UTF_8).length);
                try (OutputStream os = exchange.getResponseBody()) {
                    os.write(html.getBytes(StandardCharsets.UTF_8));
                }
            } else {
                exchange.sendResponseHeaders(404, -1);
            }
        }
    }

    private static class StatusHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            String html = "<h2>Estado de conexión</h2>" +
                    "<ul>" +
                    "<li>Oracle: " + getOracleStatus() + "</li>" +
                    "<li>PostgreSQL: " + getPostgresStatus() + "</li>" +
                    "<li>MongoDB: " + getMongoStatus() + "</li>" +
                    "<li>Cassandra: " + getCassandraStatus() + "</li>" +
                    "</ul>" +
                    "<p>Recarga la página para volver a probar.</p>";

            exchange.getResponseHeaders().add("Content-Type", "text/html; charset=UTF-8");
            exchange.sendResponseHeaders(200, html.getBytes(StandardCharsets.UTF_8).length);
            try (OutputStream os = exchange.getResponseBody()) {
                os.write(html.getBytes(StandardCharsets.UTF_8));
            }
        }
    }

    private static class MapReduceHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            String html;
            try {
                AnalisisMapReduceDAO analisis = new AnalisisMapReduceDAO();
                java.util.Map<Integer, Integer> ranking = analisis.contarVisitasPorProducto();

                StringBuilder sb = new StringBuilder();
                sb.append("<h2>Productos más visitados (MapReduce)</h2>");

                if (ranking.isEmpty()) {
                    sb.append("<p>No hay datos disponibles en log_navegacion o no se pudo conectar a MongoDB.</p>");
                } else {
                    sb.append("<table style='width:100%; border-collapse: collapse;'>");
                    sb.append("<tr><th style='text-align:left; border-bottom:1px solid #ccc;'>ID Producto</th>")
                      .append("<th style='text-align:left; border-bottom:1px solid #ccc;'>Visitas</th></tr>");
                    for (java.util.Map.Entry<Integer, Integer> entry : ranking.entrySet()) {
                        sb.append("<tr><td>").append(entry.getKey()).append("</td>")
                          .append("<td>").append(entry.getValue()).append("</td></tr>");
                    }
                    sb.append("</table>");
                }
                html = sb.toString();
            } catch (Exception e) {
                html = "<p style='color:#e74c3c;'>Error ejecutando MapReduce: " + e.getMessage() + "</p>";
            }

            byte[] responseBytes = html.getBytes(StandardCharsets.UTF_8);
            exchange.getResponseHeaders().add("Content-Type", "text/html; charset=UTF-8");
            exchange.sendResponseHeaders(200, responseBytes.length);
            try (OutputStream os = exchange.getResponseBody()) {
                os.write(responseBytes);
            }
        }
    }

    private static class ModificarHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            if (!"POST".equalsIgnoreCase(exchange.getRequestMethod())) {
                exchange.sendResponseHeaders(405, -1);
                return;
            }

            InputStream is = exchange.getRequestBody();
            String body = new String(is.readAllBytes(), StandardCharsets.UTF_8);

            String responseText = "";
            int statusCode = 200;

            try {
                Document doc = Document.parse(body);
                String base = doc.getString("base");
                String query = doc.getString("query");

                if (base == null || query == null || query.trim().isEmpty()) {
                    statusCode = 400;
                    responseText = "Faltan parámetros 'base' o 'query'.";
                } else {
                    responseText = ejecutarModificarEnBase(base, query);
                }
            } catch (Exception e) {
                statusCode = 500;
                responseText = "Error procesando solicitud: " + e.getMessage();
            }

            byte[] responseBytes = responseText.getBytes(StandardCharsets.UTF_8);
            exchange.getResponseHeaders().add("Content-Type", "text/plain; charset=UTF-8");
            exchange.sendResponseHeaders(statusCode, responseBytes.length);
            try (OutputStream os = exchange.getResponseBody()) {
                os.write(responseBytes);
            }
        }
    }

    private static String ejecutarModificarEnBase(String base, String query) {
        switch (base.toLowerCase()) {
            case "oracle":
                return ejecutarSQLOracle(query);
            case "postgres":
                return ejecutarSQLPostgres(query);
            case "mongo":
                return ejecutarMongo(query);
            case "cassandra":
                return ejecutarCassandra(query);
            default:
                return "Base de datos no soportada: " + base;
        }
    }

    private static String ejecutarSQLOracle(String query) {
        try (Connection conn = ConexionOracle.conectar()) {
            if (conn == null) {
                return "Error: No se pudo conectar a Oracle.";
            }
            try (Statement stmt = conn.createStatement()) {
                int rows = stmt.executeUpdate(query);
                return "Sentencia ejecutada en Oracle. Filas afectadas: " + rows;
            }
        } catch (Exception e) {
            return "Error al ejecutar en Oracle: " + e.getMessage();
        }
    }

    private static String ejecutarSQLPostgres(String query) {
        try (Connection conn = ConexionPostgres.conectar()) {
            if (conn == null) {
                return "Error: No se pudo conectar a PostgreSQL.";
            }
            try (Statement stmt = conn.createStatement()) {
                int rows = stmt.executeUpdate(query);
                return "Sentencia ejecutada en PostgreSQL. Filas afectadas: " + rows;
            }
        } catch (Exception e) {
            return "Error al ejecutar en PostgreSQL: " + e.getMessage();
        }
    }

    private static String ejecutarCassandra(String query) {
        try (CqlSession session = ConexionCassandra.conectar()) {
            if (session == null) {
                return "Error: No se pudo conectar a Cassandra.";
            }
            ResultSet rs = session.execute(query);
            boolean applied = rs.wasApplied();
            return "Sentencia CQL ejecutada en Cassandra. Aplicada: " + applied;
        } catch (Exception e) {
            return "Error al ejecutar en Cassandra: " + e.getMessage();
        }
    }

    private static String ejecutarMongo(String query) {
        MongoClient mongoClient = ConexionMongo.conectar();
        if (mongoClient == null) {
            return "Error: No se pudo conectar a MongoDB.";
        }
        try {
            String cmd = query.trim();
            if (cmd.endsWith(";")) {
                cmd = cmd.substring(0, cmd.length() - 1);
            }
            if (!cmd.startsWith("db.")) {
                return "Error: Comando de MongoDB inválido. Debe empezar con 'db.' (ej: db.coleccion.updateOne(...))";
            }
            int firstDot = 2;
            int secondDot = cmd.indexOf('.', 3);
            if (secondDot == -1) {
                return "Error: Comando de MongoDB inválido. Formato esperado: db.coleccion.operacion(...)";
            }
            int firstParen = cmd.indexOf('(', secondDot);
            int lastParen = cmd.lastIndexOf(')');
            if (firstParen == -1 || lastParen == -1 || lastParen <= firstParen) {
                return "Error: Comando de MongoDB inválido. Faltan paréntesis o argumentos.";
            }

            String collectionName = cmd.substring(firstDot + 1, secondDot).trim();
            String operation = cmd.substring(secondDot + 1, firstParen).trim();
            String argsStr = cmd.substring(firstParen + 1, lastParen).trim();

            MongoDatabase db = mongoClient.getDatabase("artelocal");
            MongoCollection<Document> collection = db.getCollection(collectionName);

            List<String> jsonArgs = parseJsonArgs(argsStr);

            switch (operation) {
                case "insertOne":
                    if (jsonArgs.isEmpty()) return "Error: insertOne requiere 1 documento JSON.";
                    Document docInsert = Document.parse(jsonArgs.get(0));
                    collection.insertOne(docInsert);
                    return "Documento insertado en MongoDB, colección: " + collectionName;

                case "updateOne":
                case "updateMany":
                    if (jsonArgs.size() < 2) return "Error: " + operation + " requiere 2 documentos JSON (filtro y actualización).";
                    Document filterUpdate = Document.parse(jsonArgs.get(0));
                    Document updateDoc = Document.parse(jsonArgs.get(1));
                    UpdateResult updateRes;
                    if ("updateOne".equals(operation)) {
                        updateRes = collection.updateOne(filterUpdate, updateDoc);
                    } else {
                        updateRes = collection.updateMany(filterUpdate, updateDoc);
                    }
                    return "MongoDB " + operation + " completado. Emparejados: " + updateRes.getMatchedCount() + ", Modificados: " + updateRes.getModifiedCount();

                case "deleteOne":
                case "deleteMany":
                    if (jsonArgs.isEmpty()) return "Error: " + operation + " requiere 1 documento JSON (filtro).";
                    Document filterDelete = Document.parse(jsonArgs.get(0));
                    DeleteResult deleteRes;
                    if ("deleteOne".equals(operation)) {
                        deleteRes = collection.deleteOne(filterDelete);
                    } else {
                        deleteRes = collection.deleteMany(filterDelete);
                    }
                    return "MongoDB " + operation + " completado. Eliminados: " + deleteRes.getDeletedCount();

                default:
                    return "Operación de MongoDB no soportada: " + operation + ". Soportadas: insertOne, updateOne, updateMany, deleteOne, deleteMany.";
            }
        } catch (Exception e) {
            return "Error al ejecutar en MongoDB: " + e.getMessage();
        } finally {
            try {
                mongoClient.close();
            } catch (Exception e) {
                // ignore
            }
        }
    }

    private static List<String> parseJsonArgs(String argsStr) {
        List<String> list = new ArrayList<>();
        int braceCount = 0;
        int start = -1;
        boolean insideQuote = false;
        char quoteChar = 0;
        for (int i = 0; i < argsStr.length(); i++) {
            char c = argsStr.charAt(i);
            if (insideQuote) {
                if (c == quoteChar && argsStr.charAt(i - 1) != '\\') {
                    insideQuote = false;
                }
            } else {
                if (c == '"' || c == '\'') {
                    insideQuote = true;
                    quoteChar = c;
                } else if (c == '{') {
                    if (braceCount == 0) {
                        start = i;
                    }
                    braceCount++;
                } else if (c == '}') {
                    braceCount--;
                    if (braceCount == 0 && start != -1) {
                        list.add(argsStr.substring(start, i + 1));
                        start = -1;
                    }
                }
            }
        }
        return list;
    }

    private static String loadIndexHtml() {
        try (InputStream resource = WebServer.class.getResourceAsStream("/index.html")) {
            if (resource == null) {
                return "<html><body><h1>Archivo index.html no encontrado</h1></body></html>";
            }
            java.io.BufferedReader reader = new java.io.BufferedReader(new java.io.InputStreamReader(resource, StandardCharsets.UTF_8));
            StringBuilder html = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                html.append(line).append('\n');
            }
            return html.toString();
        } catch (IOException e) {
            return "<html><body><h1>Error cargando página: " + e.getMessage() + "</h1></body></html>";
        }
    }

    private static String getOracleStatus() {
        Connection connection = ConexionOracle.conectar();
        if (connection == null) {
            return "No conectado";
        }
        try {
            connection.close();
            return "Conectado";
        } catch (Exception e) {
            return "Conectado (error al cerrar): " + e.getMessage();
        }
    }

    private static String getPostgresStatus() {
        Connection connection = ConexionPostgres.conectar();
        if (connection == null) {
            return "No conectado";
        }
        try {
            connection.close();
            return "Conectado";
        } catch (Exception e) {
            return "Conectado (error al cerrar): " + e.getMessage();
        }
    }

    private static String getMongoStatus() {
        MongoClient client = ConexionMongo.conectar();
        if (client == null) {
            return "No conectado";
        }
        try {
            client.close();
            return "Conectado";
        } catch (Exception e) {
            return "Conectado (error al cerrar): " + e.getMessage();
        }
    }

    private static String getCassandraStatus() {
        CqlSession session = ConexionCassandra.conectar();
        if (session == null) {
            return "No conectado";
        }
        try {
            session.close();
            return "Conectado";
        } catch (Exception e) {
            return "Conectado (error al cerrar): " + e.getMessage();
        }
    }
}