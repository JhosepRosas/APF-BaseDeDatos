package com.mycompany.artelocalbd2.modelo;

public class ProductoMasVisitado {

    private String nombreProducto;
    private int visitas;


    public ProductoMasVisitado() {
    }


    public ProductoMasVisitado(String nombreProducto, int visitas) {
        this.nombreProducto = nombreProducto;
        this.visitas = visitas;
    }


    public String getNombreProducto() {
        return nombreProducto;
    }


    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }


    public int getVisitas() {
        return visitas;
    }


    public void setVisitas(int visitas) {
        this.visitas = visitas;
    }


    @Override
    public String toString() {
        return nombreProducto + " - " + visitas + " visitas";
    }
}