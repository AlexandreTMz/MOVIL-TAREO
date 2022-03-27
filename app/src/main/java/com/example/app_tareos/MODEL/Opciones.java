package com.example.app_tareos.MODEL;

public class Opciones {

    int id_Opcion;
    String strtitulo,strdescripcion,strcolor,stricono;

    int intIngresos;

    public Opciones() {
    }

    public Opciones(String strtitulo, String strdescripcion, String strcolor, String stricono, int intIngresos) {
        this.strtitulo = strtitulo;
        this.strdescripcion = strdescripcion;
        this.strcolor = strcolor;
        this.stricono = stricono;
        this.intIngresos = intIngresos;
    }

    public String getStrtitulo() {
        return strtitulo;
    }

    public void setStrtitulo(String strtitulo) {
        this.strtitulo = strtitulo;
    }

    public String getStrdescripcion() {
        return strdescripcion;
    }

    public void setStrdescripcion(String strdescripcion) {
        this.strdescripcion = strdescripcion;
    }

    public String getStrcolor() {
        return strcolor;
    }

    public void setStrcolor(String strcolor) {
        this.strcolor = strcolor;
    }

    public String getStricono() {
        return stricono;
    }

    public void setStricono(String stricono) {
        this.stricono = stricono;
    }

    public int getIntIngresos() {
        return intIngresos;
    }

    public void setIntIngresos(int intIngresos) {
        this.intIngresos = intIngresos;
    }

    public int getId_Opcion() {
        return id_Opcion;
    }

    public void setId_Opcion(int id_Opcion) {
        this.id_Opcion = id_Opcion;
    }
}
