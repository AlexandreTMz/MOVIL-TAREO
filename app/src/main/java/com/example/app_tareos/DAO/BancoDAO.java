package com.example.app_tareos.DAO;

import com.example.app_tareos.MODEL.Banco;
import com.example.app_tareos.MODEL.Cargo;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class BancoDAO {
    public List<Banco> fn_Banco(JSONArray response){
        Banco ObjL_Banco;
        List<Banco> tempCargo = new ArrayList<>();
        for (int i = 0; i <= response.length(); i++){
            try {
                JSONObject jsonObject = response.getJSONObject(i);
                ObjL_Banco = new Banco();
                ObjL_Banco.setDatos(jsonObject.get("datos").toString());
                ObjL_Banco.setId_banco(jsonObject.getInt("id_banco"));
                tempCargo.add(ObjL_Banco);
            }catch (Exception e){
            }
        }
        return tempCargo;
    }
}
