package com.mycompany.artelocalbd2.web;

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

public class WebServer {

    private static final int PORT = 8080;

    public static void start() {
        try {
            HttpServer server = HttpServer.create(new InetSocketAddress(PORT), 0);
            server.createContext("/", new RootHandler());
            server.createContext("/status", new StatusHandler());
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