package com.mycompany.artelocalbd2.modelo;

public class ValoracionVendedor {

    private Integer idValoracion;
    private Integer puntuacion;
    private String comentario;

    private Integer idArtesano;
    private Integer idCliente;


    public ValoracionVendedor() {
    }


    public ValoracionVendedor(Integer idValoracion,
                              Integer puntuacion,
                              String comentario,
                              Integer idArtesano,
                              Integer idCliente) {

        this.idValoracion = idValoracion;
        this.puntuacion = puntuacion;
        this.comentario = comentario;
        this.idArtesano = idArtesano;
        this.idCliente = idCliente;
    }


    public Integer getIdValoracion() {
        return idValoracion;
    }


    public void setIdValoracion(Integer idValoracion) {
        this.idValoracion = idValoracion;
    }


    public Integer getPuntuacion() {
        return puntuacion;
    }


    public void setPuntuacion(Integer puntuacion) {
        this.puntuacion = puntuacion;
    }


    public String getComentario() {
        return comentario;
    }


    public void setComentario(String comentario) {
        this.comentario = comentario;
    }


    public Integer getIdArtesano() {
        return idArtesano;
    }


    public void setIdArtesano(Integer idArtesano) {
        this.idArtesano = idArtesano;
    }


    public Integer getIdCliente() {
        return idCliente;
    }


    public void setIdCliente(Integer idCliente) {
        this.idCliente = idCliente;
    }


    @Override
    public String toString() {

        return "ValoracionVendedor{" +
                "idValoracion=" + idValoracion +
                ", puntuacion=" + puntuacion +
                ", comentario='" + comentario + '\'' +
                ", idArtesano=" + idArtesano +
                ", idCliente=" + idCliente +
                '}';
    }
}