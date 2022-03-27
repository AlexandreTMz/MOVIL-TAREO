package com.example.app_tareos.DAO;

import com.example.app_tareos.MODEL.Sede;
import com.example.app_tareos.MODEL.Sueldo;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SueldoDAO {
    public List<Sueldo> fn_SueldoEmpleado(JSONArray response){
        Sueldo ObjL_Sueldo;
        List<Sueldo> tempSueldo = new ArrayList<>();
        for (int i = 0; i < response.length(); i++){
            try {
                JSONObject jsonObject = response.getJSONObject(i);
                ObjL_Sueldo = new Sueldo();
                ObjL_Sueldo.setStrP_FechaFin(jsonObject.getString("ta_vigenciaFin"));
                ObjL_Sueldo.setDblP_sueldo(jsonObject.getDouble("ta_total"));
                ObjL_Sueldo.setStrP_FechaInicio(jsonObject.getString("ta_vigenciaInicio"));
                ObjL_Sueldo.setInactive(jsonObject.getInt("ta_estado") == 1);
                ObjL_Sueldo.setStrP_Estado(jsonObject.getString("estado"));
                tempSueldo.add(ObjL_Sueldo);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return tempSueldo;
    }
}
