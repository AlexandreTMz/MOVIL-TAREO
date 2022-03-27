package com.example.app_tareos.MODEL;

public class DescansoDias {
    String id_dia;
    String dia;

    public DescansoDias(String id_dia, String dia) {
        this.id_dia = id_dia;
        this.dia = dia;
    }

    public DescansoDias() {
    }

    public String getId_dia() {
        return id_dia;
    }

    public void setId_dia(String id_dia) {
        this.id_dia = id_dia;
    }

    public String getDia() {
        return dia;
    }

    public void setDia(String dia) {
        this.dia = dia;
    }

    @Override
    public String toString() {
        return dia;
    }
}
