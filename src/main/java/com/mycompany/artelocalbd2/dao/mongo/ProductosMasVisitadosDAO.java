package com.mycompany.artelocalbd2.dao.mongo;


import com.mycompany.artelocalbd2.conexion.ConexionMongo;
import com.mycompany.artelocalbd2.modelo.ProductoMasVisitado;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;


import org.bson.Document;

import java.util.ArrayList;
import java.util.List;



public class ProductosMasVisitadosDAO {


    private final String DATABASE = "artelocal";
    private final String COLLECTION = "productos_mas_visitados";



    public List<ProductoMasVisitado> listarProductosMasVisitados(){


        List<ProductoMasVisitado> lista = new ArrayList<>();


        MongoClient cliente = ConexionMongo.conectar();


        if(cliente == null){

            System.out.println("No se pudo conectar a MongoDB");

            return lista;
        }



        try{


            MongoDatabase database =
                    cliente.getDatabase(DATABASE);



            MongoCollection<Document> coleccion =
                    database.getCollection(COLLECTION);



            for(Document doc : coleccion.find()){


                String nombre =
                        doc.getString("nombre_producto");



                Integer visitas =
                        doc.getInteger("visitas");



                lista.add(
                    new ProductoMasVisitado(
                        nombre,
                        visitas
                    )
                );

            }



        }catch(Exception e){


            System.out.println(
                "Error consultando productos más visitados: "
                + e.getMessage()
            );


        }finally{


            ConexionMongo.cerrar();

        }


        return lista;

    }

}