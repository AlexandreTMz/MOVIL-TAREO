package com.example.app_tareos.DAO;

import com.example.app_tareos.MODEL.Nacionalidad;
import com.example.app_tareos.MODEL.TipoDocumento;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class NacionalidadDAO {

    public List<Nacionalidad> fn_Nacionalidad(JSONArray response){
        Nacionalidad ObjL_Nacionalidad;
        List<Nacionalidad> tempNacionalidad = new ArrayList<>();
        for (int i = 0; i <= response.length(); i++){
            try {
                JSONObject jsonObject = response.getJSONObject(i);
                ObjL_Nacionalidad = new Nacionalidad();
                ObjL_Nacionalidad.setDatos(jsonObject.get("datos").toString());
                ObjL_Nacionalidad.setId_nacionalidad(jsonObject.getInt("id_nacionalidad"));
                tempNacionalidad.add(ObjL_Nacionalidad);
            }catch (Exception e){
            }
        }
        return tempNacionalidad;
    }

}
