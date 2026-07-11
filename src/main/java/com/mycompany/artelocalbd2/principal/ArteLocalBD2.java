package com.mycompany.artelocalbd2.principal;

import com.mycompany.artelocalbd2.conexion.ConexionOracle;
import com.mycompany.artelocalbd2.conexion.ConexionPostgres;
import com.mycompany.artelocalbd2.conexion.ConexionMongo;
import com.mycompany.artelocalbd2.conexion.ConexionCassandra;

import java.sql.Connection;


public class ArteLocalBD2 {

    public static void main(String[] args) {


        System.out.println("================================");
        System.out.println(" PRUEBA DE CONEXIONES DATABASES ");
        System.out.println("================================");


        // ===========================
        // ORACLE CLOUD
        // ===========================

        System.out.println("\n--- Oracle Cloud ---");

        Connection oracle = ConexionOracle.conectar();

        if (oracle != null) {

            System.out.println("✓ Oracle Cloud conectado correctamente");

            try {

                oracle.close();
                System.out.println("✓ Conexion Oracle cerrada");

            } catch (Exception e) {

                System.out.println("Error cerrando Oracle");
            }

        } else {

            System.out.println("✗ Oracle Cloud no conectado");

        }



        // ===========================
        // POSTGRESQL
        // ===========================

        System.out.println("\n--- PostgreSQL ---");


        Connection postgres = ConexionPostgres.conectar();


        if(postgres != null){

            System.out.println("✓ PostgreSQL conectado correctamente");


            try {

                postgres.close();
                System.out.println("✓ Conexion PostgreSQL cerrada");

            } catch(Exception e){

                System.out.println("Error cerrando PostgreSQL");

            }


        } else {

            System.out.println("✗ PostgreSQL no conectado");

        }




        // ===========================
        // MONGODB
        // ===========================

        System.out.println("\n--- MongoDB ---");


        if(ConexionMongo.conectar()!=null){

            System.out.println("✓ MongoDB conectado correctamente");

            ConexionMongo.cerrar();


        }else{

            System.out.println("✗ MongoDB no conectado");

        }




        // ===========================
        // CASSANDRA
        // ===========================

        System.out.println("\n--- Cassandra ---");


        if(ConexionCassandra.conectar()!=null){

            System.out.println("✓ Cassandra conectado correctamente");

            ConexionCassandra.cerrar();


        }else{

            System.out.println("✗ Cassandra no conectado");

        }



        System.out.println("\n================================");
        System.out.println(" FIN DE PRUEBA DE CONEXIONES ");
        System.out.println("================================");


    }

}