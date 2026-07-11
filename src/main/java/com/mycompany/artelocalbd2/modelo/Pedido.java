package com.mycompany.artelocalbd2.modelo;

import java.time.LocalDate;

public class Pedido {

    private Integer idPedido;
    private LocalDate fechaPedido;
    private String estado;
    private Double total;

    private Integer idCliente;


    public Pedido() {
    }


    public Pedido(Integer idPedido,
                  LocalDate fechaPedido,
                  String estado,
                  Double total,
                  Integer idCliente) {

        this.idPedido = idPedido;
        this.fechaPedido = fechaPedido;
        this.estado = estado;
        this.total = total;
        this.idCliente = idCliente;
    }


    public Integer getIdPedido() {
        return idPedido;
    }


    public void setIdPedido(Integer idPedido) {
        this.idPedido = idPedido;
    }


    public LocalDate getFechaPedido() {
        return fechaPedido;
    }


    public void setFechaPedido(LocalDate fechaPedido) {
        this.fechaPedido = fechaPedido;
    }


    public String getEstado() {
        return estado;
    }


    public void setEstado(String estado) {
        this.estado = estado;
    }


    public Double getTotal() {
        return total;
    }


    public void setTotal(Double total) {
        this.total = total;
    }


    public Integer getIdCliente() {
        return idCliente;
    }


    public void setIdCliente(Integer idCliente) {
        this.idCliente = idCliente;
    }


    @Override
    public String toString() {

        return "Pedido{" +
                "idPedido=" + idPedido +
                ", fechaPedido=" + fechaPedido +
                ", estado='" + estado + '\'' +
                ", total=" + total +
                ", idCliente=" + idCliente +
                '}';
    }
}