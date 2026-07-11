package com.mycompany.artelocalbd2.conexion;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;


public class ConexionMongo {


    private static MongoClient cliente;


    private static final String URL =
            "mongodb://127.0.0.1:27017";


    public static MongoClient conectar(){


        try {

            cliente = MongoClients.create(URL);

            System.out.println("✓ MongoDB conectado correctamente");

            return cliente;


        } catch(Exception e){

            System.out.println(
                "✗ Error MongoDB: " + e.getMessage()
            );

            return null;
        }

    }



    public static void cerrar(){

        if(cliente != null){

            cliente.close();

            System.out.println("✓ Conexion MongoDB cerrada");

        }

    }

}