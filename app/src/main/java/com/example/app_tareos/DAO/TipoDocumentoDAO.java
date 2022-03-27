package com.example.app_tareos.DAO;

import com.example.app_tareos.MODEL.TipoDocumento;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class TipoDocumentoDAO {

    public List<TipoDocumento> fn_TipoDocumento(JSONArray response){
        TipoDocumento ObjL_tipoDocumento;
        List<TipoDocumento> tempEtapa = new ArrayList<>();
        for (int i = 0; i <= response.length(); i++){
            try {
                JSONObject jsonObject = response.getJSONObject(i);
                ObjL_tipoDocumento = new TipoDocumento();
                ObjL_tipoDocumento.setDatos(jsonObject.get("datos").toString());
                ObjL_tipoDocumento.setId_tpdocumento(jsonObject.get("id_tpdocumento").toString());
                tempEtapa.add(ObjL_tipoDocumento);
            }catch (Exception e){
            }
        }
        return tempEtapa;
    }
}
