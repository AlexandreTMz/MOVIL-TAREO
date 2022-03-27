package com.example.app_tareos.MODEL;

public class Banco {
    int id_banco;
    String ba_nombre;
    String ba_abreviatura;
    String datos;

    public int getId_banco() {
        return id_banco;
    }

    public void setId_banco(int id_banco) {
        this.id_banco = id_banco;
    }

    public String getBa_nombre() {
        return ba_nombre;
    }

    public void setBa_nombre(String ba_nombre) {
        this.ba_nombre = ba_nombre;
    }

    public String getBa_abreviatura() {
        return ba_abreviatura;
    }

    public void setBa_abreviatura(String ba_abreviatura) {
        this.ba_abreviatura = ba_abreviatura;
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
