package com.example.app_tareos.DAO;

import com.example.app_tareos.MODEL.DescansoDias;
import com.example.app_tareos.MODEL.Sexo;

import java.util.ArrayList;
import java.util.List;

public class DescansoDiasDAO {

    public List<DescansoDias> fn_DescansoDias(){
        DescansoDias ObjL_DescansosDias;
        List<DescansoDias> tempDescansoDias = new ArrayList<>();

        ObjL_DescansosDias = new DescansoDias();
        ObjL_DescansosDias.setId_dia("NONE");
        ObjL_DescansosDias.setDia("NO ESTABLECER");
        tempDescansoDias.add(ObjL_DescansosDias);

        ObjL_DescansosDias = new DescansoDias();
        ObjL_DescansosDias.setId_dia("Lunes");
        ObjL_DescansosDias.setDia("Lunes");
        tempDescansoDias.add(ObjL_DescansosDias);

        ObjL_DescansosDias = new DescansoDias();
        ObjL_DescansosDias.setId_dia("Martes");
        ObjL_DescansosDias.setDia("Martes");
        tempDescansoDias.add(ObjL_DescansosDias);

        ObjL_DescansosDias = new DescansoDias();
        ObjL_DescansosDias.setId_dia("Miercoles");
        ObjL_DescansosDias.setDia("Miercoles");
        tempDescansoDias.add(ObjL_DescansosDias);

        ObjL_DescansosDias = new DescansoDias();
        ObjL_DescansosDias.setId_dia("Jueves");
        ObjL_DescansosDias.setDia("Jueves");
        tempDescansoDias.add(ObjL_DescansosDias);

        ObjL_DescansosDias = new DescansoDias();
        ObjL_DescansosDias.setId_dia("Viernes");
        ObjL_DescansosDias.setDia("Viernes");
        tempDescansoDias.add(ObjL_DescansosDias);

        ObjL_DescansosDias = new DescansoDias();
        ObjL_DescansosDias.setId_dia("Sabado");
        ObjL_DescansosDias.setDia("Sabado");
        tempDescansoDias.add(ObjL_DescansosDias);

        ObjL_DescansosDias = new DescansoDias();
        ObjL_DescansosDias.setId_dia("Domingo");
        ObjL_DescansosDias.setDia("Domingo");
        tempDescansoDias.add(ObjL_DescansosDias);

        return tempDescansoDias;
    }

}