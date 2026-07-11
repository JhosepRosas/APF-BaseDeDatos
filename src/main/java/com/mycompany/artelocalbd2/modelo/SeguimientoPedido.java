package com.mycompany.artelocalbd2.modelo;

import java.time.LocalDateTime;

public class SeguimientoPedido {

    private Integer idPedido;

    private LocalDateTime fechaHora;

    private String estado;

    private String ubicacion;

    private String responsable;


    public SeguimientoPedido() {
    }


    public SeguimientoPedido(Integer idPedido,
                             LocalDateTime fechaHora,
                             String estado,
                             String ubicacion,
                             String responsable) {

        this.idPedido = idPedido;
        this.fechaHora = fechaHora;
        this.estado = estado;
        this.ubicacion = ubicacion;
        this.responsable = responsable;
    }


    public Integer getIdPedido() {
        return idPedido;
    }


    public void setIdPedido(Integer idPedido) {
        this.idPedido = idPedido;
    }


    public LocalDateTime getFechaHora() {
        return fechaHora;
    }


    public void setFechaHora(LocalDateTime fechaHora) {
        this.fechaHora = fechaHora;
    }


    public String getEstado() {
        return estado;
    }


    public void setEstado(String estado) {
        this.estado = estado;
    }


    public String getUbicacion() {
        return ubicacion;
    }


    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }


    public String getResponsable() {
        return responsable;
    }


    public void setResponsable(String responsable) {
        this.responsable = responsable;
    }


    @Override
    public String toString() {

        return "SeguimientoPedido{" +
                "idPedido=" + idPedido +
                ", fechaHora=" + fechaHora +
                ", estado='" + estado + '\'' +
                ", ubicacion='" + ubicacion + '\'' +
                ", responsable='" + responsable + '\'' +
                '}';
    }
}