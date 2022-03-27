package com.example.app_tareos.MODEL;

public class TipoCuenta {
    int id_tpcuenta;
    String tpc_descripcion;
    String tpc_abreviatura;
    String datos;

    public int getId_tpcuenta() {
        return id_tpcuenta;
    }

    public void setId_tpcuenta(int id_tpcuenta) {
        this.id_tpcuenta = id_tpcuenta;
    }

    public String getTpc_descripcion() {
        return tpc_descripcion;
    }

    public void setTpc_descripcion(String tpc_descripcion) {
        this.tpc_descripcion = tpc_descripcion;
    }

    public String getTpc_abreviatura() {
        return tpc_abreviatura;
    }

    public void setTpc_abreviatura(String tpc_abreviatura) {
        this.tpc_abreviatura = tpc_abreviatura;
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
