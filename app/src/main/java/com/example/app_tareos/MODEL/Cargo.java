package com.example.app_tareos.MODEL;

public class Cargo {
    int id_cargo;
    String ca_descripcion;
    String ca_abreviatura;
    String datos;

    public int getId_cargo() {
        return id_cargo;
    }

    public void setId_cargo(int id_cargo) {
        this.id_cargo = id_cargo;
    }

    public String getCa_descripcion() {
        return ca_descripcion;
    }

    public void setCa_descripcion(String ca_descripcion) {
        this.ca_descripcion = ca_descripcion;
    }

    public String getCa_abreviatura() {
        return ca_abreviatura;
    }

    public void setCa_abreviatura(String ca_abreviatura) {
        this.ca_abreviatura = ca_abreviatura;
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
