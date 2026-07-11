package com.mycompany.artelocalbd2.modelo;

public class Categoria {

    private Integer idCategoria;
    private String nombreCategoria;
    private String descripcion;


    public Categoria() {
    }


    public Categoria(Integer idCategoria,
                     String nombreCategoria,
                     String descripcion) {

        this.idCategoria = idCategoria;
        this.nombreCategoria = nombreCategoria;
        this.descripcion = descripcion;
    }


    public Integer getIdCategoria() {
        return idCategoria;
    }


    public void setIdCategoria(Integer idCategoria) {
        this.idCategoria = idCategoria;
    }


    public String getNombreCategoria() {
        return nombreCategoria;
    }


    public void setNombreCategoria(String nombreCategoria) {
        this.nombreCategoria = nombreCategoria;
    }


    public String getDescripcion() {
        return descripcion;
    }


    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }


    @Override
    public String toString() {

        return "Categoria{" +
                "idCategoria=" + idCategoria +
                ", nombreCategoria='" + nombreCategoria + '\'' +
                ", descripcion='" + descripcion + '\'' +
                '}';
    }
}