package com.example.app_tareos.MODEL;

public class TipoDocumento {

    String id_tpdocumento;
    String tpd_descripcion;
    String tpd_abreviatura;
    String datos;

    public String getId_tpdocumento() {
        return id_tpdocumento;
    }

    public void setId_tpdocumento(String id_tpdocumento) {
        this.id_tpdocumento = id_tpdocumento;
    }

    public String getTpd_descripcion() {
        return tpd_descripcion;
    }

    public void setTpd_descripcion(String tpd_descripcion) {
        this.tpd_descripcion = tpd_descripcion;
    }

    public String getTpd_abreviatura() {
        return tpd_abreviatura;
    }

    public void setTpd_abreviatura(String tpd_abreviatura) {
        this.tpd_abreviatura = tpd_abreviatura;
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
