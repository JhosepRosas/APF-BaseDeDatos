package com.mycompany.artelocalbd2.dao;

public interface CRUD<T> {

    void insertar(T objeto);

    void actualizar(T objeto);

    void eliminar(int id);

    T buscar(int id);

}