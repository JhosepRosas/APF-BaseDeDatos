package com.mycompany.artelocalbd2.conexion;


import com.datastax.oss.driver.api.core.CqlSession;



public class ConexionCassandra {


    private static CqlSession sesion;



    public static CqlSession conectar(){


        try {


            sesion = CqlSession.builder()
                    .withKeyspace("artelocal")
                    .build();


            System.out.println("✓ Cassandra conectado correctamente");


            return sesion;



        }catch(Exception e){


            System.out.println(
                "✗ Error Cassandra: "
                + e.getMessage()
            );


            return null;

        }


    }



    public static void cerrar(){


        if(sesion != null){

            sesion.close();

            System.out.println(
                "✓ Conexion Cassandra cerrada"
            );

        }

    }


}