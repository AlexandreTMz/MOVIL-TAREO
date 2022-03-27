package com.example.app_tareos.MODEL;

public class Nacionalidad {

    int id_nacionalidad;
    String na_descripcion;
    String na_abreviatura;
    String datos;

    public int getId_nacionalidad() {
        return id_nacionalidad;
    }

    public void setId_nacionalidad(int id_nacionalidad) {
        this.id_nacionalidad = id_nacionalidad;
    }

    public String getNa_descripcion() {
        return na_descripcion;
    }

    public void setNa_descripcion(String na_descripcion) {
        this.na_descripcion = na_descripcion;
    }

    public String getNa_abreviatura() {
        return na_abreviatura;
    }

    public void setNa_abreviatura(String na_abreviatura) {
        this.na_abreviatura = na_abreviatura;
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
