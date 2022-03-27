package com.example.app_tareos.MODEL;

public class Empleado {
    Persona persona;
    Cargo cargo;
    Sede sede;
    int phc_estado;
    String phc_fecha_r;
    String phc_fecha_c;
    int id_sueldo;
    boolean bln_IsVerMas;
    Banco banco;
    String cuentaCc;
    String cuentaCci;
    String titular;
    String MES,DIA,ANIO;


    public int getId_sueldo() {
        return id_sueldo;
    }

    public void setId_sueldo(int id_sueldo) {
        this.id_sueldo = id_sueldo;
    }
    public Persona getPersona() {
        return persona;
    }

    public void setPersona(Persona persona) {
        this.persona = persona;
    }

    public Cargo getCargo() {
        return cargo;
    }

    public void setCargo(Cargo cargo) {
        this.cargo = cargo;
    }

    public Sede getSede() {
        return sede;
    }

    public void setSede(Sede sede) {
        this.sede = sede;
    }

    public int getPhc_estado() {
        return phc_estado;
    }

    public void setPhc_estado(int phc_estado) {
        this.phc_estado = phc_estado;
    }

    public String getPhc_fecha_r() {
        return phc_fecha_r;
    }

    public void setPhc_fecha_r(String phc_fecha_r) {
        this.phc_fecha_r = phc_fecha_r;
    }

    public String getPhc_fecha_c() {
        return phc_fecha_c;
    }

    public void setPhc_fecha_c(String phc_fecha_c) {
        this.phc_fecha_c = phc_fecha_c;
    }

    public boolean isBln_IsVerMas() {
        return bln_IsVerMas;
    }

    public void setBln_IsVerMas(boolean bln_IsVerMas) {
        this.bln_IsVerMas = bln_IsVerMas;
    }

    public String getMES() {
        return MES;
    }

    public void setMES(String MES) {
        this.MES = MES;
    }

    public String getDIA() {
        return DIA;
    }

    public void setDIA(String DIA) {
        this.DIA = DIA;
    }

    public String getANIO() {
        return ANIO;
    }

    public void setANIO(String ANIO) {
        this.ANIO = ANIO;
    }

    public Banco getBanco() {
        return banco;
    }

    public void setBanco(Banco banco) {
        this.banco = banco;
    }

    public String getCuentaCc() {
        return cuentaCc;
    }

    public void setCuentaCc(String cuentaCc) {
        this.cuentaCc = cuentaCc;
    }

    public String getCuentaCci() {
        return cuentaCci;
    }

    public void setCuentaCci(String cuentaCci) {
        this.cuentaCci = cuentaCci;
    }

    public String getTitular() {
        return titular;
    }

    public void setTitular(String titular) {
        this.titular = titular;
    }

    @Override
    public String toString() {
        return "Empleado{" +
                "persona=" + persona +
                ", cargo=" + cargo +
                ", sede=" + sede +
                ", phc_estado=" + phc_estado +
                ", phc_fecha_r='" + phc_fecha_r + '\'' +
                ", phc_fecha_c='" + phc_fecha_c + '\'' +
                ", id_sueldo=" + id_sueldo +
                '}';
    }
}
