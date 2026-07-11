package com.mycompany.artelocalbd2.modelo;

import java.time.LocalDate;

public class Envio {

    private Integer idEnvio;
    private String empresaTransporte;
    private LocalDate fechaEnvio;
    private String estadoEnvio;

    private Integer idPedido;


    public Envio() {
    }


    public Envio(Integer idEnvio,
                 String empresaTransporte,
                 LocalDate fechaEnvio,
                 String estadoEnvio,
                 Integer idPedido) {

        this.idEnvio = idEnvio;
        this.empresaTransporte = empresaTransporte;
        this.fechaEnvio = fechaEnvio;
        this.estadoEnvio = estadoEnvio;
        this.idPedido = idPedido;
    }


    public Integer getIdEnvio() {
        return idEnvio;
    }


    public void setIdEnvio(Integer idEnvio) {
        this.idEnvio = idEnvio;
    }


    public String getEmpresaTransporte() {
        return empresaTransporte;
    }


    public void setEmpresaTransporte(String empresaTransporte) {
        this.empresaTransporte = empresaTransporte;
    }


    public LocalDate getFechaEnvio() {
        return fechaEnvio;
    }


    public void setFechaEnvio(LocalDate fechaEnvio) {
        this.fechaEnvio = fechaEnvio;
    }


    public String getEstadoEnvio() {
        return estadoEnvio;
    }


    public void setEstadoEnvio(String estadoEnvio) {
        this.estadoEnvio = estadoEnvio;
    }


    public Integer getIdPedido() {
        return idPedido;
    }


    public void setIdPedido(Integer idPedido) {
        this.idPedido = idPedido;
    }


    @Override
    public String toString() {

        return "Envio{" +
                "idEnvio=" + idEnvio +
                ", empresaTransporte='" + empresaTransporte + '\'' +
                ", fechaEnvio=" + fechaEnvio +
                ", estadoEnvio='" + estadoEnvio + '\'' +
                ", idPedido=" + idPedido +
                '}';
    }
}