package com.example.app_tareos.MODEL;

public class Tareo {

    int id_tareo;
    Marcador marcador;
    Empleado empleado;
    int ta_estado;
    String ta_fecha_r;
    String ta_fecha_c;
    String ta_hora_r;
    String ta_hora_c;
    double ta_hrstrabajadas;
    int ta_usuario;
    String tareo;
    String tareoSede;
    int id_sueldo;
    int id_sede_em;

    public int getId_sede_em() {
        return id_sede_em;
    }

    public void setId_sede_em(int id_sede_em) {
        this.id_sede_em = id_sede_em;
    }

    public int getId_sueldo() {
        return id_sueldo;
    }

    public void setId_sueldo(int id_sueldo) {
        this.id_sueldo = id_sueldo;
    }

    public int getId_tareo() {
        return id_tareo;
    }

    public void setId_tareo(int id_tareo) {
        this.id_tareo = id_tareo;
    }

    public Marcador getMarcador() {
        return marcador;
    }

    public void setMarcador(Marcador marcador) {
        this.marcador = marcador;
    }

    public Empleado getEmpleado() {
        return empleado;
    }

    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
    }

    public int getTa_estado() {
        return ta_estado;
    }

    public void setTa_estado(int ta_estado) {
        this.ta_estado = ta_estado;
    }

    public String getTa_fecha_r() {
        return ta_fecha_r;
    }

    public void setTa_fecha_r(String ta_fecha_r) {
        this.ta_fecha_r = ta_fecha_r;
    }

    public String getTa_fecha_c() {
        return ta_fecha_c;
    }

    public void setTa_fecha_c(String ta_fecha_c) {
        this.ta_fecha_c = ta_fecha_c;
    }

    public String getTa_hora_r() {
        return ta_hora_r;
    }

    public void setTa_hora_r(String ta_hora_r) {
        this.ta_hora_r = ta_hora_r;
    }

    public String getTa_hora_c() {
        return ta_hora_c;
    }

    public void setTa_hora_c(String ta_hora_c) {
        this.ta_hora_c = ta_hora_c;
    }

    public double getTa_hrstrabajadas() {
        return ta_hrstrabajadas;
    }

    public void setTa_hrstrabajadas(double ta_hrstrabajadas) {
        this.ta_hrstrabajadas = ta_hrstrabajadas;
    }

    public int getTa_usuario() {
        return ta_usuario;
    }

    public void setTa_usuario(int ta_usuario) {
        this.ta_usuario = ta_usuario;
    }

    public String getTareo() {
        return tareo;
    }

    public void setTareo(String tareo) {
        this.tareo = tareo;
    }

    public String getTareoSede() {
        return tareoSede;
    }

    public void setTareoSede(String tareoSede) {
        this.tareoSede = tareoSede;
    }
}
