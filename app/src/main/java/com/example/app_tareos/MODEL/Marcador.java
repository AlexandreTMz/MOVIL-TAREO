package com.example.app_tareos.MODEL;

public class Marcador {
    int id_marcador;
    String ma_descripcion;
    String ma_abreviatura;
    String datos;

    public int getId_marcador() {
        return id_marcador;
    }

    public void setId_marcador(int id_marcador) {
        this.id_marcador = id_marcador;
    }

    public String getMa_descripcion() {
        return ma_descripcion;
    }

    public void setMa_descripcion(String ma_descripcion) {
        this.ma_descripcion = ma_descripcion;
    }

    public String getMa_abreviatura() {
        return ma_abreviatura;
    }

    public void setMa_abreviatura(String ma_abreviatura) {
        this.ma_abreviatura = ma_abreviatura;
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
