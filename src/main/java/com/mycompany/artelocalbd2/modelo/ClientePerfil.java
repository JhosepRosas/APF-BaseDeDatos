package com.mycompany.artelocalbd2.modelo;

import java.util.List;

public class ClientePerfil {

    private String id;

    private Integer idCliente;

    private List<Integer> historialCompras;

    private List<Integer> listaDeseos;

    private String preferencias;


    public ClientePerfil() {
    }


    public ClientePerfil(String id,
                         Integer idCliente,
                         List<Integer> historialCompras,
                         List<Integer> listaDeseos,
                         String preferencias) {

        this.id = id;
        this.idCliente = idCliente;
        this.historialCompras = historialCompras;
        this.listaDeseos = listaDeseos;
        this.preferencias = preferencias;
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


    public List<Integer> getHistorialCompras() {
        return historialCompras;
    }


    public void setHistorialCompras(List<Integer> historialCompras) {
        this.historialCompras = historialCompras;
    }


    public List<Integer> getListaDeseos() {
        return listaDeseos;
    }


    public void setListaDeseos(List<Integer> listaDeseos) {
        this.listaDeseos = listaDeseos;
    }


    public String getPreferencias() {
        return preferencias;
    }


    public void setPreferencias(String preferencias) {
        this.preferencias = preferencias;
    }


    @Override
    public String toString() {

        return "ClientePerfil{" +
                "id='" + id + '\'' +
                ", idCliente=" + idCliente +
                ", historialCompras=" + historialCompras +
                ", listaDeseos=" + listaDeseos +
                ", preferencias='" + preferencias + '\'' +
                '}';
    }
}
