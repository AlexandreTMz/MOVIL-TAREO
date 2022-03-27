package com.example.app_tareos.DAO;

import android.app.Activity;
import android.content.Intent;

import com.example.app_tareos.GUI.PUBLICO.ACTHome;
import com.example.app_tareos.MODEL.Usuario;
import com.example.app_tareos.ROOMS.Preference_PerfilUsuario;

import org.json.JSONObject;

public class UsuarioDAO {
    private Activity ObjP_activity;

    public UsuarioDAO(Activity activity) {
        this.ObjP_activity = activity;
    }

    public void loginUsuario(JSONObject dataObject) throws Exception {
        Usuario usuario = new Usuario();
        usuario.setId_usuario(dataObject.getInt("id_persona"));
        usuario.setId_tpdocumento(dataObject.getString("id_tpdocumento"));
        usuario.setId_nacionalidad(dataObject.getInt("id_nacionalidad"));
        usuario.setDatos(dataObject.getString("datos"));
        usuario.setId_tpusuario(dataObject.getInt("id_tpusuario"));
        usuario.setUs_usuario(dataObject.getString("us_usuario"));
        usuario.setTpu_descripcion(dataObject.getString("tpu_descripcion"));
        usuario.setPer_documento(dataObject.getString("per_documento"));
        usuario.setUs_contrasenia(dataObject.getString("us_contrasenia"));
        usuario.setId_persona(dataObject.getInt("id_persona"));
        Preference_PerfilUsuario userProfile = Preference_PerfilUsuario.getInstance(ObjP_activity);
        userProfile.putUsuarioInfo(usuario);
        userProfile.putNickname(dataObject.getString("us_usuario"));
        userProfile.putSession(true);
        userProfile.putId_sede(dataObject.getInt("id_sede"));
        userProfile.putSede(dataObject.getString("sede"));
        userProfile.putSedePermitidas(dataObject.getString("sedesPermitidas"));

        Intent intent = new Intent(ObjP_activity, ACTHome.class);
        ObjP_activity.startActivity(intent);

    }


}
