package com.example.app_tareos.DAO;

import com.example.app_tareos.MODEL.Cargo;
import com.example.app_tareos.MODEL.Nacionalidad;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class CargoDAO {

    public List<Cargo> fn_Cargo(JSONArray response){
        Cargo ObjL_Cargo;
        List<Cargo> tempCargo = new ArrayList<>();
        for (int i = 0; i <= response.length(); i++){
            try {
                JSONObject jsonObject = response.getJSONObject(i);
                ObjL_Cargo = new Cargo();
                ObjL_Cargo.setDatos(jsonObject.get("datos").toString());
                ObjL_Cargo.setId_cargo(jsonObject.getInt("id_cargo"));
                tempCargo.add(ObjL_Cargo);
            }catch (Exception e){
            }
        }
        return tempCargo;
    }

}
