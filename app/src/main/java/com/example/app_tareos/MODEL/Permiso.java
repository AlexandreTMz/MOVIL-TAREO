package com.example.app_tareos.MODEL;

public class Permiso {

    int id_permiso;
    String pe_descripcion;

    public int getId_permiso() {
        return id_permiso;
    }

    public void setId_permiso(int id_permiso) {
        this.id_permiso = id_permiso;
    }

    public String getPe_descripcion() {
        return pe_descripcion;
    }

    public void setPe_descripcion(String pe_descripcion) {
        this.pe_descripcion = pe_descripcion;
    }

    @Override
    public String toString() {
        return pe_descripcion;
    }
}
