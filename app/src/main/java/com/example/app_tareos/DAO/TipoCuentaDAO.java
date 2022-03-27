package com.example.app_tareos.DAO;

 import com.example.app_tareos.MODEL.TipoCuenta;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class TipoCuentaDAO {

    public List<TipoCuenta> fn_TipoCuenta(JSONArray response){
        TipoCuenta ObjL_TipoCuenta;
        List<TipoCuenta> tempTipoCuenta = new ArrayList<>();
        for (int i = 0; i <= response.length(); i++){
            try {
                JSONObject jsonObject = response.getJSONObject(i);
                ObjL_TipoCuenta = new TipoCuenta();
                ObjL_TipoCuenta.setDatos(jsonObject.get("datos").toString());
                ObjL_TipoCuenta.setId_tpcuenta(jsonObject.getInt("id_tpcuenta"));
                tempTipoCuenta.add(ObjL_TipoCuenta);
            }catch (Exception e){
            }
        }
        return tempTipoCuenta;
    }

}
