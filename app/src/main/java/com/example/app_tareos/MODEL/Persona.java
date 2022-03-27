package com.example.app_tareos.MODEL;

public class Persona {
    int id_persona;
    String per_nombre;
    String per_apellido_paterno;
    String per_apellido_materno;
    int id_nacionalidad;
    String id_tpdocumento;
    String per_documento;
    String datos;
    int id_banco;
    String ba_abreviatura;
    String phb_cuenta;
    String phb_cci;
    Nacionalidad nacionalidad;

    public int getId_persona() {
        return id_persona;
    }

    public void setId_persona(int id_persona) {
        this.id_persona = id_persona;
    }

    public String getPer_nombre() {
        return per_nombre;
    }

    public void setPer_nombre(String per_nombre) {
        this.per_nombre = per_nombre;
    }

    public String getPer_apellido_paterno() {
        return per_apellido_paterno;
    }

    public void setPer_apellido_paterno(String per_apellido_paterno) {
        this.per_apellido_paterno = per_apellido_paterno;
    }

    public String getPer_apellido_materno() {
        return per_apellido_materno;
    }

    public void setPer_apellido_materno(String per_apellido_materno) {
        this.per_apellido_materno = per_apellido_materno;
    }

    public int getId_nacionalidad() {
        return id_nacionalidad;
    }

    public void setId_nacionalidad(int id_nacionalidad) {
        this.id_nacionalidad = id_nacionalidad;
    }

    public String getId_tpdocumento() {
        return id_tpdocumento;
    }

    public void setId_tpdocumento(String id_tpdocumento) {
        this.id_tpdocumento = id_tpdocumento;
    }

    public String getPer_documento() {
        return per_documento;
    }

    public void setPer_documento(String per_documento) {
        this.per_documento = per_documento;
    }

    public String getDatos() {
        return datos;
    }

    public void setDatos(String datos) {
        this.datos = datos;
    }

    public int getId_banco() {
        return id_banco;
    }

    public void setId_banco(int id_banco) {
        this.id_banco = id_banco;
    }

    public String getBa_abreviatura() {
        return ba_abreviatura;
    }

    public void setBa_abreviatura(String ba_abreviatura) {
        this.ba_abreviatura = ba_abreviatura;
    }

    public String getPhb_cuenta() {
        return phb_cuenta;
    }

    public void setPhb_cuenta(String phb_cuenta) {
        this.phb_cuenta = phb_cuenta;
    }

    public String getPhb_cci() {
        return phb_cci;
    }

    public void setPhb_cci(String phb_cci) {
        this.phb_cci = phb_cci;
    }

    public Nacionalidad getNacionalidad() {
        return nacionalidad;
    }

    public void setNacionalidad(Nacionalidad nacionalidad) {
        this.nacionalidad = nacionalidad;
    }

    @Override
    public String toString() {
        return "Persona{" +
                "id_persona=" + id_persona +
                ", per_nombre='" + per_nombre + '\'' +
                ", per_apellido_paterno='" + per_apellido_paterno + '\'' +
                ", per_apellido_materno='" + per_apellido_materno + '\'' +
                ", id_nacionalidad=" + id_nacionalidad +
                ", id_tpdocumento='" + id_tpdocumento + '\'' +
                ", per_documento='" + per_documento + '\'' +
                ", datos='" + datos + '\'' +
                ", id_banco=" + id_banco +
                ", ba_abreviatura='" + ba_abreviatura + '\'' +
                ", phb_cuenta='" + phb_cuenta + '\'' +
                ", phb_cci='" + phb_cci + '\'' +
                '}';
    }
}
