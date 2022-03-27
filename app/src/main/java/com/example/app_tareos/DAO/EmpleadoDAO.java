package com.example.app_tareos.DAO;

import com.example.app_tareos.MODEL.Banco;
import com.example.app_tareos.MODEL.Cargo;
import com.example.app_tareos.MODEL.Empleado;
import com.example.app_tareos.MODEL.Nacionalidad;
import com.example.app_tareos.MODEL.Persona;
import com.example.app_tareos.MODEL.Sede;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class EmpleadoDAO {

    public List<Empleado> fn_EmpleadoTareo(JSONArray response){
        Empleado ObjL_Empleado;
        List<Empleado> tempEmpleado = new ArrayList<>();
        for (int i = 0; i <= response.length(); i++){
            try {
                JSONObject jsonObject = response.getJSONObject(i);
                ObjL_Empleado = new Empleado();

                Persona persona = new Persona();
                persona.setDatos(jsonObject.getString("datos"));
                persona.setId_persona(jsonObject.getInt("id_persona"));
                persona.setId_tpdocumento(jsonObject.getString("id_tpdocumento"));
                persona.setId_nacionalidad(jsonObject.getInt("id_nacionalidad"));
                persona.setPer_documento(jsonObject.getString("per_documento"));
                ObjL_Empleado.setPersona(persona);

                Sede sede = new Sede();
                sede.setId_sede(jsonObject.getInt("id_sede"));
                sede.setDatos(jsonObject.getString("se_descripcion"));
                ObjL_Empleado.setSede(sede);

                Cargo cargo = new Cargo();
                cargo.setId_cargo(jsonObject.getInt("id_cargo"));
                cargo.setDatos(jsonObject.getString("ca_descripcion"));
                ObjL_Empleado.setCargo(cargo);


                tempEmpleado.add(ObjL_Empleado);
            }catch (Exception e){
            }
        }
        return tempEmpleado;
    }


    public List<Empleado> fn_EmpleadoPermiso(JSONArray response){
        Empleado ObjL_Empleado;
        List<Empleado> tempEmpleado = new ArrayList<>();
        for (int i = 0; i < response.length(); i++){
            try {
                JSONObject jsonObject = response.getJSONObject(i);
                ObjL_Empleado = new Empleado();
                ObjL_Empleado.setId_sueldo(jsonObject.getInt("id_sueldo"));

                Persona persona = new Persona();
                persona.setDatos(jsonObject.getString("datos"));
                persona.setId_persona(jsonObject.getInt("id_persona"));
                persona.setId_tpdocumento(jsonObject.getString("id_tpdocumento"));
                persona.setId_nacionalidad(jsonObject.getInt("id_nacionalidad"));
                persona.setPer_documento(jsonObject.getString("per_documento"));
                ObjL_Empleado.setPersona(persona);

                Sede sede = new Sede();
                sede.setId_sede(jsonObject.getInt("id_sede"));
                sede.setSe_descripcion(jsonObject.getString("se_descripcion"));
                sede.setSe_lugar(jsonObject.getString("se_lugar"));
                ObjL_Empleado.setSede(sede);

                Cargo cargo = new Cargo();
                cargo.setId_cargo(jsonObject.getInt("id_cargo"));
                cargo.setDatos(jsonObject.getString("ca_descripcion"));
                ObjL_Empleado.setCargo(cargo);


                tempEmpleado.add(ObjL_Empleado);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return tempEmpleado;
    }


    public List<Empleado> fn_EmpleadoListaTotal(JSONArray response){
        Empleado ObjL_Empleado;
        List<Empleado> tempEmpleado = new ArrayList<>();
        for (int i = 0; i < response.length(); i++){
            try {
                JSONObject jsonObject = response.getJSONObject(i);
                ObjL_Empleado = new Empleado();

                ObjL_Empleado.setMES(jsonObject.getString("MES"));
                ObjL_Empleado.setDIA(jsonObject.getString("DIA"));
                ObjL_Empleado.setANIO(jsonObject.getString("ANIO"));
                ObjL_Empleado.setCuentaCc(jsonObject.getString("cuenta"));
                ObjL_Empleado.setCuentaCci(jsonObject.getString("cci"));
                ObjL_Empleado.setCuentaCci(jsonObject.getString("cci"));
                ObjL_Empleado.setTitular(jsonObject.getString("titular"));

                Persona persona = new Persona();
                persona.setDatos(jsonObject.getString("datos"));
                persona.setId_persona(jsonObject.getInt("id_persona"));
                persona.setId_tpdocumento(jsonObject.getString("id_tpdocumento"));
                persona.setPer_documento(jsonObject.getString("per_documento"));

                Nacionalidad nacionalidad = new Nacionalidad();
                nacionalidad.setNa_descripcion(jsonObject.getString("na_descripcion"));
                persona.setNacionalidad(nacionalidad);

                ObjL_Empleado.setPersona(persona);

                Banco banco = new Banco();
                banco.setBa_nombre(jsonObject.getString("banco"));
                ObjL_Empleado.setBanco(banco);


                /*Sede sede = new Sede();
                sede.setId_sede(jsonObject.getInt("id_sede"));
                sede.setSe_descripcion(jsonObject.getString("se_descripcion"));
                sede.setSe_lugar(jsonObject.getString("se_lugar"));
                ObjL_Empleado.setSede(sede);*/

                Cargo cargo = new Cargo();
                cargo.setDatos(jsonObject.getString("ca_descripcion"));
                ObjL_Empleado.setCargo(cargo);

                tempEmpleado.add(ObjL_Empleado);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return tempEmpleado;
    }

}
