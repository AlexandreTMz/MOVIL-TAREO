package com.example.app_tareos.DAO;

import com.example.app_tareos.MODEL.Banco;
import com.example.app_tareos.MODEL.Sede;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SedeDAO {

    public List<Sede> fn_Sede(JSONArray response){
        Sede ObjL_Sede;
        List<Sede> tempSede = new ArrayList<>();
        for (int i = 0; i <= response.length(); i++){
            try {
                JSONObject jsonObject = response.getJSONObject(i);
                ObjL_Sede = new Sede();
                ObjL_Sede.setDatos(jsonObject.get("datos").toString());
                ObjL_Sede.setId_sede(jsonObject.getInt("id_sede"));
                tempSede.add(ObjL_Sede);
            }catch (Exception e){
            }
        }
        return tempSede;
    }

}
