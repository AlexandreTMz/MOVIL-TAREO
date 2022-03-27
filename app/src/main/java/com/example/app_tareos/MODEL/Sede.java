package com.example.app_tareos.MODEL;

public class Sede {
    int id_sede;
    String se_descripcion;
    String se_lugar;
    String datos;

    public int getId_sede() {
        return id_sede;
    }

    public void setId_sede(int id_sede) {
        this.id_sede = id_sede;
    }

    public String getSe_descripcion() {
        return se_descripcion;
    }

    public void setSe_descripcion(String se_descripcion) {
        this.se_descripcion = se_descripcion;
    }

    public String getSe_lugar() {
        return se_lugar;
    }

    public void setSe_lugar(String se_lugar) {
        this.se_lugar = se_lugar;
    }

    public String getDatos() {
        return datos;
    }

    public void setDatos(String datos) {
        this.datos = datos;
    }

    @Override
    public String toString() {
        return datos;
    }
}
