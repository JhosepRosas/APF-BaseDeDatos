package com.mycompany.artelocalbd2.modelo;

public class Cliente {

    private Integer idCliente;
    private String nombre;
    private String correo;
    private String telefono;


    public Cliente() {
    }


    public Cliente(Integer idCliente,
                   String nombre,
                   String correo,
                   String telefono) {

        this.idCliente = idCliente;
        this.nombre = nombre;
        this.correo = correo;
        this.telefono = telefono;
    }


    public Integer getIdCliente() {
        return idCliente;
    }


    public void setIdCliente(Integer idCliente) {
        this.idCliente = idCliente;
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


    @Override
    public String toString() {

        return "Cliente{" +
                "idCliente=" + idCliente +
                ", nombre='" + nombre + '\'' +
                ", correo='" + correo + '\'' +
                ", telefono='" + telefono + '\'' +
                '}';
    }
}