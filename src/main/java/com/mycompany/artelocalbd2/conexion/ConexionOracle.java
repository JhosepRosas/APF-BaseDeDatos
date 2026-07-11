package com.mycompany.artelocalbd2.conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionOracle {

    private static final String URL =
            "jdbc:oracle:thin:@artelocal_high?TNS_ADMIN=E:/wallet";

    private static final String USER = "ARTELOCAL_APP";

    private static final String PASSWORD = "Artelocal123";

    public static Connection conectar() {

        Connection conexion = null;

        try {

            conexion = DriverManager.getConnection(
                    URL,
                    USER,
                    PASSWORD
            );

            System.out.println("✓ Oracle conectado correctamente");

        } catch (SQLException e) {

            System.out.println("✗ Error Oracle: " + e.getMessage());

        }

        return conexion;
    }

}