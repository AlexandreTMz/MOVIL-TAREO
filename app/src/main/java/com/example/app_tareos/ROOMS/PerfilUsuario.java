package com.example.app_tareos.ROOMS;

import com.example.app_tareos.MODEL.Sede;
import com.example.app_tareos.MODEL.Usuario;
import com.skydoves.preferenceroom.KeyName;
import com.skydoves.preferenceroom.PreferenceEntity;
import com.skydoves.preferenceroom.TypeConverter;

import java.util.ArrayList;
import java.util.List;

@PreferenceEntity("PerfilUsuario")
public class PerfilUsuario {

    protected final boolean login = false;
    @KeyName("vista") protected final int userVista = 1;
    @KeyName("session") protected final boolean userSession = false;
    @KeyName("visits") protected final int visitCount = 1;
    //@KeyName("usuario") protected final Usuario usuario = null;
    @TypeConverter(UsuarioConverter.class)
    protected Usuario usuarioInfo;
    @KeyName("id_sede") protected final int idSede = 0;
    @KeyName("sede") protected final String se_datos = null;
    @KeyName("nickname")    protected final String userNickName = null;
    @KeyName("sedePermitidas") protected final String sedesPermitidas = null;

}
