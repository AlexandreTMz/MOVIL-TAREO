package com.example.app_tareos.MODEL;

public class Sexo {
    int id_sexo;
    String descripcion;
    String abreviatura;
    String datos;

    public int getId_sexo() {
        return id_sexo;
    }

    public void setId_sexo(int id_sexo) {
        this.id_sexo = id_sexo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getAbreviatura() {
        return abreviatura;
    }

    public void setAbreviatura(String abreviatura) {
        this.abreviatura = abreviatura;
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
