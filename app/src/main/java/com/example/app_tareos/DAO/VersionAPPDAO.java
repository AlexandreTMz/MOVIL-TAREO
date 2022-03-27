package com.example.app_tareos.DAO;

import com.example.app_tareos.MODEL.TipoDocumento;
import com.example.app_tareos.MODEL.VersionAPP;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class VersionAPPDAO {
    public VersionAPP fn_VersionAPP(JSONObject response){
        VersionAPP ObjL_VersionAPP = new VersionAPP();
        try {
            ObjL_VersionAPP.setVe_estado(response.getInt("estado"));
            ObjL_VersionAPP.setVe_version(response.getString("version"));
            return ObjL_VersionAPP;
        }catch (Exception e){

        }
        return ObjL_VersionAPP;
    }
}
