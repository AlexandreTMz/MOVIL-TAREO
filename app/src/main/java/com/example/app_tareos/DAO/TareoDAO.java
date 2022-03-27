package com.example.app_tareos.DAO;

import com.example.app_tareos.MODEL.Cargo;
import com.example.app_tareos.MODEL.Empleado;
import com.example.app_tareos.MODEL.Persona;
import com.example.app_tareos.MODEL.Sede;
import com.example.app_tareos.MODEL.Tareo;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class TareoDAO {

    public List<Tareo> fn_RegistroTareoEmpleado(JSONArray response){
        Tareo ObjL_Tareo;
        List<Tareo> tempTareo = new ArrayList<>();
        for (int i = 0; i < response.length(); i++){
             try {
                JSONObject jsonObject = response.getJSONObject(i);
                ObjL_Tareo = new Tareo();
                ObjL_Tareo.setId_tareo(jsonObject.getInt("id_tareo"));
                ObjL_Tareo.setTareo(jsonObject.getString("registro"));
                ObjL_Tareo.setTareoSede(jsonObject.getString("sedeTurno"));
                ObjL_Tareo.setId_sueldo(jsonObject.getInt("id_sueldo"));
                ObjL_Tareo.setId_sede_em(jsonObject.getInt("id_sede_em"));

                Persona persona = new Persona();
                persona.setDatos(jsonObject.getString("datos"));
                persona.setId_persona(jsonObject.getInt("id_persona"));
                persona.setId_tpdocumento(jsonObject.getString("id_tpdocumento"));
                persona.setId_nacionalidad(jsonObject.getInt("id_nacionalidad"));
                persona.setPer_documento(jsonObject.getString("per_documento"));

                Empleado empleado = new Empleado();
                empleado.setPersona(persona);

                Sede sede = new Sede();
                sede.setId_sede(jsonObject.getInt("id_sede"));
                sede.setSe_descripcion(jsonObject.getString("se_descripcion"));
                sede.setSe_lugar(jsonObject.getString("se_lugar"));
                empleado.setSede(sede);

                Cargo cargo = new Cargo();
                cargo.setId_cargo(jsonObject.getInt("id_cargo"));
                cargo.setDatos(jsonObject.getString("cargo"));
                empleado.setCargo(cargo);

                ObjL_Tareo.setEmpleado(empleado);

                tempTareo.add(ObjL_Tareo);

            }catch (Exception e){
                 e.printStackTrace();
            }
        }
         return tempTareo;
    }

}
