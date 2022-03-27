package com.example.app_tareos.MODEL;

public class Usuario {
    int id_persona;
    String id_tpdocumento;
    int id_nacionalidad;
    String datos;
    int id_usuario;
    int id_tpusuario;
    String us_usuario;
    String tpu_descripcion;
    String per_documento;
    String us_contrasenia;

    public String getUs_contrasenia() {
        return us_contrasenia;
    }

    public void setUs_contrasenia(String us_contrasenia) {
        this.us_contrasenia = us_contrasenia;
    }

    public int getId_persona() {
        return id_persona;
    }

    public void setId_persona(int id_persona) {
        this.id_persona = id_persona;
    }

    public String getId_tpdocumento() {
        return id_tpdocumento;
    }

    public void setId_tpdocumento(String id_tpdocumento) {
        this.id_tpdocumento = id_tpdocumento;
    }

    public int getId_nacionalidad() {
        return id_nacionalidad;
    }

    public void setId_nacionalidad(int id_nacionalidad) {
        this.id_nacionalidad = id_nacionalidad;
    }

    public String getDatos() {
        return datos;
    }

    public void setDatos(String datos) {
        this.datos = datos;
    }

    public int getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(int id_usuario) {
        this.id_usuario = id_usuario;
    }

    public int getId_tpusuario() {
        return id_tpusuario;
    }

    public void setId_tpusuario(int id_tpusuario) {
        this.id_tpusuario = id_tpusuario;
    }

    public String getUs_usuario() {
        return us_usuario;
    }

    public void setUs_usuario(String us_usuario) {
        this.us_usuario = us_usuario;
    }

    public String getTpu_descripcion() {
        return tpu_descripcion;
    }

    public void setTpu_descripcion(String tpu_descripcion) {
        this.tpu_descripcion = tpu_descripcion;
    }

    public String getPer_documento() {
        return per_documento;
    }

    public void setPer_documento(String per_documento) {
        this.per_documento = per_documento;
    }


    @Override
    public String toString() {
        return "Usuario{" +
                "id_persona=" + id_persona +
                ", id_tpdocumento='" + id_tpdocumento + '\'' +
                ", id_nacionalidad=" + id_nacionalidad +
                ", datos='" + datos + '\'' +
                ", id_usuario=" + id_usuario +
                ", id_tpusuario=" + id_tpusuario +
                ", us_usuario='" + us_usuario + '\'' +
                ", tpu_descripcion='" + tpu_descripcion + '\'' +
                ", per_documento='" + per_documento + '\'' +
                ", us_contrasenia='" + us_contrasenia + '\'' +
                '}';
    }
}
