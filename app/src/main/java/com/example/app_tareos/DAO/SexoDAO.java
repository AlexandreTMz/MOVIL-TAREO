package com.example.app_tareos.DAO;

import com.example.app_tareos.MODEL.Sexo;
import com.example.app_tareos.MODEL.TipoCuenta;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SexoDAO {

    public List<Sexo> fn_Sexo(){
        Sexo ObjL_Sexo;
        List<Sexo> tempSexo = new ArrayList<>();

        ObjL_Sexo = new Sexo();
        ObjL_Sexo.setDatos("M - MASCULINO");
        ObjL_Sexo.setId_sexo(1);
        tempSexo.add(ObjL_Sexo);

        ObjL_Sexo = new Sexo();
        ObjL_Sexo.setDatos("F - FEMENINO");
        ObjL_Sexo.setId_sexo(2);
        tempSexo.add(ObjL_Sexo);

        return tempSexo;
    }

}
