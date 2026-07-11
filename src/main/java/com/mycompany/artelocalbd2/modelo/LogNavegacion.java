package com.mycompany.artelocalbd2.modelo;

import java.time.LocalDateTime;

public class LogNavegacion {

    private String id;

    private Integer idCliente;

    private Integer idProducto;

    private LocalDateTime fecha;

    private String accion;

    private String dispositivo;


    public LogNavegacion() {
    }


    public LogNavegacion(String id,
                         Integer idCliente,
                         Integer idProducto,
                         LocalDateTime fecha,
                         String accion,
                         String dispositivo) {

        this.id = id;
        this.idCliente = idCliente;
        this.idProducto = idProducto;
        this.fecha = fecha;
        this.accion = accion;
        this.dispositivo = dispositivo;
    }


    public String getId() {
        return id;
    }


    public void setId(String id) {
        this.id = id;
    }


    public Integer getIdCliente() {
        return idCliente;
    }


    public void setIdCliente(Integer idCliente) {
        this.idCliente = idCliente;
    }


    public Integer getIdProducto() {
        return idProducto;
    }


    public void setIdProducto(Integer idProducto) {
        this.idProducto = idProducto;
    }


    public LocalDateTime getFecha() {
        return fecha;
    }


    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }


    public String getAccion() {
        return accion;
    }


    public void setAccion(String accion) {
        this.accion = accion;
    }


    public String getDispositivo() {
        return dispositivo;
    }


    public void setDispositivo(String dispositivo) {
        this.dispositivo = dispositivo;
    }


    @Override
    public String toString() {

        return "LogNavegacion{" +
                "id='" + id + '\'' +
                ", idCliente=" + idCliente +
                ", idProducto=" + idProducto +
                ", fecha=" + fecha +
                ", accion='" + accion + '\'' +
                ", dispositivo='" + dispositivo + '\'' +
                '}';
    }
}