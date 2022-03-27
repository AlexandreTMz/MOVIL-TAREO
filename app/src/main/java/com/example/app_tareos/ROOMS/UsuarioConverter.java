package com.example.app_tareos.ROOMS;

import com.example.app_tareos.MODEL.Usuario;
import com.google.gson.Gson;
import com.skydoves.preferenceroom.PreferenceTypeConverter;

public class UsuarioConverter extends PreferenceTypeConverter<Usuario> {

    private final Gson gson;

    // default constructor will be called by PreferenceRoom
    public UsuarioConverter(Class<Usuario> clazz) {
        super(clazz);
        this.gson = new Gson();
    }

    @Override
    public String convertObject(Usuario usuario) {
        return gson.toJson(usuario);
    }

    @Override
    public Usuario convertType(String string) {
        return gson.fromJson(string, Usuario.class);
    }
}