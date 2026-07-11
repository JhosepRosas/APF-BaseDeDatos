package com.mycompany.artelocalbd2.modelo;

public class Artesano {

    private Integer idArtesano;
    private String nombre;
    private String correo;
    private String telefono;
    private String region;


    public Artesano() {
    }


    public Artesano(Integer idArtesano, String nombre,
                    String correo, String telefono,
                    String region) {

        this.idArtesano = idArtesano;
        this.nombre = nombre;
        this.correo = correo;
        this.telefono = telefono;
        this.region = region;
    }


    public Integer getIdArtesano() {
        return idArtesano;
    }


    public void setIdArtesano(Integer idArtesano) {
        this.idArtesano = idArtesano;
    }


    public String getNombre() {
        return nombre;
    }


    public void setNombre(String nombre) {
        this.nombre = nombre;
    }


    public String getCorreo() {
        return correo;
    }


    public void setCorreo(String correo) {
        this.correo = correo;
    }


    public String getTelefono() {
        return telefono;
    }


    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }


    public String getRegion() {
        return region;
    }


    public void setRegion(String region) {
        this.region = region;
    }


    @Override
    public String toString() {

        return "Artesano{" +
                "idArtesano=" + idArtesano +
                ", nombre='" + nombre + '\'' +
                ", correo='" + correo + '\'' +
                ", telefono='" + telefono + '\'' +
                ", region='" + region + '\'' +
                '}';
    }
}