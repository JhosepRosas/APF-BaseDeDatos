package com.mycompany.artelocalbd2.modelo;

public class Producto {

    private Integer idProducto;
    private String nombre;
    private String descripcionBasica;
    private Double precio;
    private Integer stock;

    private Integer idArtesano;
    private Integer idCategoria;


    public Producto() {
    }


    public Producto(Integer idProducto,
                    String nombre,
                    String descripcionBasica,
                    Double precio,
                    Integer stock,
                    Integer idArtesano,
                    Integer idCategoria) {

        this.idProducto = idProducto;
        this.nombre = nombre;
        this.descripcionBasica = descripcionBasica;
        this.precio = precio;
        this.stock = stock;
        this.idArtesano = idArtesano;
        this.idCategoria = idCategoria;
    }


    public Integer getIdProducto() {
        return idProducto;
    }


    public void setIdProducto(Integer idProducto) {
        this.idProducto = idProducto;
    }


    public String getNombre() {
        return nombre;
    }


    public void setNombre(String nombre) {
        this.nombre = nombre;
    }


    public String getDescripcionBasica() {
        return descripcionBasica;
    }


    public void setDescripcionBasica(String descripcionBasica) {
        this.descripcionBasica = descripcionBasica;
    }


    public Double getPrecio() {
        return precio;
    }


    public void setPrecio(Double precio) {
        this.precio = precio;
    }


    public Integer getStock() {
        return stock;
    }


    public void setStock(Integer stock) {
        this.stock = stock;
    }


    public Integer getIdArtesano() {
        return idArtesano;
    }


    public void setIdArtesano(Integer idArtesano) {
        this.idArtesano = idArtesano;
    }


    public Integer getIdCategoria() {
        return idCategoria;
    }


    public void setIdCategoria(Integer idCategoria) {
        this.idCategoria = idCategoria;
    }


    @Override
    public String toString() {

        return "Producto{" +
                "idProducto=" + idProducto +
                ", nombre='" + nombre + '\'' +
                ", descripcionBasica='" + descripcionBasica + '\'' +
                ", precio=" + precio +
                ", stock=" + stock +
                ", idArtesano=" + idArtesano +
                ", idCategoria=" + idCategoria +
                '}';
    }
}