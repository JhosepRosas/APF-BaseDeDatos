package com.mycompany.artelocalbd2.conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionPostgres {

    private static final String URL = 
            "jdbc:postgresql://localhost:5432/artelocal_db";

    private static final String USER = "postgres";
    private static final String PASSWORD = "franco123";


    public static Connection conectar() {

        Connection conexion = null;

        try {

            conexion = DriverManager.getConnection(
                    URL,
                    USER,
                    PASSWORD
            );

            System.out.println("PostgreSQL conectado correctamente");

        } catch (SQLException e) {

            System.out.println("Error PostgreSQL: " + e.getMessage());

        }

        return conexion;
    }
}