package com.example.app_tareos.MODEL;

public class Sueldo {
    String strP_FechaInicio;
    String strP_FechaFin;
    double dblP_sueldo;
    boolean isInactive;
    String strP_Estado;

    public String getStrP_FechaInicio() {
        return strP_FechaInicio;
    }

    public void setStrP_FechaInicio(String strP_FechaInicio) {
        this.strP_FechaInicio = strP_FechaInicio;
    }

    public String getStrP_FechaFin() {
        return strP_FechaFin;
    }

    public void setStrP_FechaFin(String strP_FechaFin) {
        this.strP_FechaFin = strP_FechaFin;
    }

    public double getDblP_sueldo() {
        return dblP_sueldo;
    }

    public void setDblP_sueldo(double dblP_sueldo) {
        this.dblP_sueldo = dblP_sueldo;
    }

    public boolean isInactive() {
        return isInactive;
    }

    public void setInactive(boolean inactive) {
        isInactive = inactive;
    }

    public String getStrP_Estado() {
        return strP_Estado;
    }

    public void setStrP_Estado(String strP_Estado) {
        this.strP_Estado = strP_Estado;
    }

    @Override
    public String toString() {
        return "Sueldo{" +
                "strP_FechaInicio='" + strP_FechaInicio + '\'' +
                ", strP_FechaFin='" + strP_FechaFin + '\'' +
                ", dblP_sueldo=" + dblP_sueldo +
                ", isInactive=" + isInactive +
                '}';
    }
}
