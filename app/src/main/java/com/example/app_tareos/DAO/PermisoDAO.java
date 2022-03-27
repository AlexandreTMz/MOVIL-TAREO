package com.example.app_tareos.DAO;

import com.example.app_tareos.MODEL.Marcador;
import com.example.app_tareos.MODEL.Permiso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class PermisoDAO {

    public List<Permiso> fn_Permiso(JSONArray response){
        Permiso ObjL_Permiso;
        List<Permiso> tempPermiso = new ArrayList<>();
        for (int i = 0; i <= response.length(); i++){
            try {
                JSONObject jsonObject = response.getJSONObject(i);
                ObjL_Permiso = new Permiso();
                ObjL_Permiso.setPe_descripcion(jsonObject.get("pe_descripcion").toString());
                ObjL_Permiso.setId_permiso(jsonObject.getInt("id_permiso"));
                tempPermiso.add(ObjL_Permiso);
            }catch (Exception e){
            }
        }
        return tempPermiso;
    }

}