package com.example.app_tareos.MODEL;

public class VersionAPP {
    int id_version;
    String ve_observacion;
    String ve_version;
    String ve_fecha;
    String ve_archivo;
    int ve_estado;

    public int getId_version() {
        return id_version;
    }

    public void setId_version(int id_version) {
        this.id_version = id_version;
    }

    public String getVe_observacion() {
        return ve_observacion;
    }

    public void setVe_observacion(String ve_observacion) {
        this.ve_observacion = ve_observacion;
    }

    public String getVe_version() {
        return ve_version;
    }

    public void setVe_version(String ve_version) {
        this.ve_version = ve_version;
    }

    public String getVe_fecha() {
        return ve_fecha;
    }

    public void setVe_fecha(String ve_fecha) {
        this.ve_fecha = ve_fecha;
    }

    public String getVe_archivo() {
        return ve_archivo;
    }

    public void setVe_archivo(String ve_archivo) {
        this.ve_archivo = ve_archivo;
    }

    public int getVe_estado() {
        return ve_estado;
    }

    public void setVe_estado(int ve_estado) {
        this.ve_estado = ve_estado;
    }

    @Override
    public String toString() {
        return "VersionAPP{" +
                "id_version=" + id_version +
                ", ve_observacion='" + ve_observacion + '\'' +
                ", ve_version='" + ve_version + '\'' +
                ", ve_fecha='" + ve_fecha + '\'' +
                ", ve_archivo='" + ve_archivo + '\'' +
                ", ve_estado=" + ve_estado +
                '}';
    }
}
