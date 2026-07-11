package com.mycompany.artelocalbd2.modelo;

public class DetallePedido {

    private Integer idDetalle;
    private Integer cantidad;
    private Double subtotal;

    private Integer idPedido;
    private Integer idProducto;


    public DetallePedido() {
    }


    public DetallePedido(Integer idDetalle,
                         Integer cantidad,
                         Double subtotal,
                         Integer idPedido,
                         Integer idProducto) {

        this.idDetalle = idDetalle;
        this.cantidad = cantidad;
        this.subtotal = subtotal;
        this.idPedido = idPedido;
        this.idProducto = idProducto;
    }


    public Integer getIdDetalle() {
        return idDetalle;
    }


    public void setIdDetalle(Integer idDetalle) {
        this.idDetalle = idDetalle;
    }


    public Integer getCantidad() {
        return cantidad;
    }


    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }


    public Double getSubtotal() {
        return subtotal;
    }


    public void setSubtotal(Double subtotal) {
        this.subtotal = subtotal;
    }


    public Integer getIdPedido() {
        return idPedido;
    }


    public void setIdPedido(Integer idPedido) {
        this.idPedido = idPedido;
    }


    public Integer getIdProducto() {
        return idProducto;
    }


    public void setIdProducto(Integer idProducto) {
        this.idProducto = idProducto;
    }


    @Override
    public String toString() {

        return "DetallePedido{" +
                "idDetalle=" + idDetalle +
                ", cantidad=" + cantidad +
                ", subtotal=" + subtotal +
                ", idPedido=" + idPedido +
                ", idProducto=" + idProducto +
                '}';
    }
}