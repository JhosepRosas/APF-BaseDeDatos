package com.mycompany.artelocalbd2.modelo;

import java.util.List;

public class ProductoDetalle {

    private String id;
    private Integer idProducto;

    private String descripcionCompleta;
    private List<String> imagenes;
    private String materiales;
    private String caracteristicas;


    public ProductoDetalle() {
    }


    public ProductoDetalle(String id,
                           Integer idProducto,
                           String descripcionCompleta,
                           List<String> imagenes,
                           String materiales,
                           String caracteristicas) {

        this.id = id;
        this.idProducto = idProducto;
        this.descripcionCompleta = descripcionCompleta;
        this.imagenes = imagenes;
        this.materiales = materiales;
        this.caracteristicas = caracteristicas;
    }


    public String getId() {
        return id;
    }


    public void setId(String id) {
        this.id = id;
    }


    public Integer getIdProducto() {
        return idProducto;
    }


    public void setIdProducto(Integer idProducto) {
        this.idProducto = idProducto;
    }


    public String getDescripcionCompleta() {
        return descripcionCompleta;
    }


    public void setDescripcionCompleta(String descripcionCompleta) {
        this.descripcionCompleta = descripcionCompleta;
    }


    public List<String> getImagenes() {
        return imagenes;
    }


    public void setImagenes(List<String> imagenes) {
        this.imagenes = imagenes;
    }


    public String getMateriales() {
        return materiales;
    }


    public void setMateriales(String materiales) {
        this.materiales = materiales;
    }


    public String getCaracteristicas() {
        return caracteristicas;
    }


    public void setCaracteristicas(String caracteristicas) {
        this.caracteristicas = caracteristicas;
    }


    @Override
    public String toString() {

        return "ProductoDetalle{" +
                "id='" + id + '\'' +
                ", idProducto=" + idProducto +
                ", descripcionCompleta='" + descripcionCompleta + '\'' +
                ", imagenes=" + imagenes +
                ", materiales='" + materiales + '\'' +
                ", caracteristicas='" + caracteristicas + '\'' +
                '}';
    }
}