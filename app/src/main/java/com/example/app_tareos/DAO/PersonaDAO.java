package com.example.app_tareos.DAO;

import com.androidbuts.multispinnerfilter.KeyPairBoolData;
import com.example.app_tareos.MODEL.Nacionalidad;
import com.example.app_tareos.MODEL.Persona;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class PersonaDAO {

    public List<Persona> fn_Persona(JSONArray response){
        Persona ObjL_Persona;
        List<Persona> tempPersona = new ArrayList<>();
        for (int i = 0; i <= response.length(); i++){
            try {
                JSONObject jsonObject = response.getJSONObject(i);
                ObjL_Persona = new Persona();
                ObjL_Persona.setDatos(jsonObject.get("datos").toString());
                ObjL_Persona.setPer_documento(jsonObject.getString("per_documento"));
                tempPersona.add(ObjL_Persona);
            }catch (Exception e){
            }
        }
        return tempPersona;
    }

    public List<Persona> fn_PersonaSuplente(JSONArray response){
        Persona ObjL_Persona;
        List<Persona> tempPersona = new ArrayList<>();
        for (int i = 0; i <= response.length(); i++){
            try {
                JSONObject jsonObject = response.getJSONObject(i);
                ObjL_Persona = new Persona();
                ObjL_Persona.setDatos(jsonObject.get("datos").toString());
                ObjL_Persona.setPer_documento(jsonObject.getString("per_documento"));
                ObjL_Persona.setBa_abreviatura(jsonObject.getString("banco"));
                ObjL_Persona.setPhb_cci(jsonObject.getString("phb_cuenta"));
                ObjL_Persona.setId_tpdocumento(jsonObject.getString("id_tpdocumento"));

                tempPersona.add(ObjL_Persona);
            }catch (Exception e){
            }
        }
        return tempPersona;
    }


    public List<KeyPairBoolData> fn_BuscarPersonaParaDescanso(JSONArray response){
        KeyPairBoolData ObjL_Persona;
        List<KeyPairBoolData> tempPersona = new ArrayList<>();
        for (int i = 0; i <= response.length(); i++){
            try {
                JSONObject jsonObject = response.getJSONObject(i);
                ObjL_Persona = new KeyPairBoolData();
                ObjL_Persona.setName(jsonObject.get("datos").toString());
                ObjL_Persona.setId(1);
                tempPersona.add(ObjL_Persona);
            }catch (Exception e){
            }
        }
        return tempPersona;
    }

}
