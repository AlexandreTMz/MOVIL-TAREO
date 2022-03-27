package com.example.app_tareos.DAO;

import com.example.app_tareos.MODEL.Banco;
import com.example.app_tareos.MODEL.Marcador;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MarcadorDAO {

    public List<Marcador> fn_Marcador(JSONArray response){
        Marcador ObjL_Marcador;
        List<Marcador> tempMarcador = new ArrayList<>();
        for (int i = 0; i <= response.length(); i++){
            try {
                JSONObject jsonObject = response.getJSONObject(i);
                ObjL_Marcador = new Marcador();
                ObjL_Marcador.setDatos(jsonObject.get("datos").toString());
                ObjL_Marcador.setId_marcador(jsonObject.getInt("id_marcador"));
                tempMarcador.add(ObjL_Marcador);
            }catch (Exception e){
            }
        }
        return tempMarcador;
    }

}
