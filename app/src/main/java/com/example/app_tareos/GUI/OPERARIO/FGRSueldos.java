package com.example.app_tareos.GUI.OPERARIO;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.VolleyError;
import com.example.app_tareos.ADAPTADORES.ADTRSueldos;
import com.example.app_tareos.CONFIG.UrlServer;
import com.example.app_tareos.DAO.MarcadorDAO;
import com.example.app_tareos.DAO.SueldoDAO;
import com.example.app_tareos.INTERFACE.IResultVolley;
import com.example.app_tareos.LIBS.ErrorToastyServer;
import com.example.app_tareos.LIBS.VolleyService;
import com.example.app_tareos.MODEL.Marcador;
import com.example.app_tareos.MODEL.Permiso;
import com.example.app_tareos.MODEL.Sueldo;
import com.example.app_tareos.R;
import com.example.app_tareos.ROOMS.Preference_PerfilUsuario;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class FGRSueldos extends Fragment implements IResultVolley {

    // ADAPTADOR
    ADTRSueldos objP_ADTRSueldos;

    // RECICLER
    RecyclerView rcvSueldos;

    // SUELDOS
    Sueldo sueldo;

    // LISTA
    List<Sueldo> objL_listaSueldo;

    // voley
    VolleyService mVolleyService;
    ErrorToastyServer errorToastyServer;

    // PREFERENCE ROOMS
    public Preference_PerfilUsuario userProfile;

    public FGRSueldos() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fgr_sueldos, container, false);

        // RECYCLERVIEW
        rcvSueldos = view.findViewById(R.id.rcvSueldos);

        // ADAPTADORES
        objL_listaSueldo = new ArrayList<>();
        objP_ADTRSueldos = new ADTRSueldos(getContext(),objL_listaSueldo);
        rcvSueldos.setAdapter(objP_ADTRSueldos);
        rcvSueldos.setLayoutManager(new LinearLayoutManager(getContext()));

        // instanciado el room
        userProfile = Preference_PerfilUsuario.getInstance(getActivity());

        /// ERROR SERVER
        errorToastyServer = new ErrorToastyServer(getActivity());

        // INTANCE SERVICE
        mVolleyService = new VolleyService(this, getActivity());

        // Consultas
        mVolleyService.mtd_GetArrayVolley("SUELDOS", String.format(UrlServer.URL_SERVER + "empleado/sueldo/"+userProfile.getUsuarioInfo().getId_persona()));

        return view;
    }

    @Override
    public void notifySuccessOject(String requestType, JSONObject response) {
        System.out.println("DATA::"+response);
    }

    @Override
    public void notifySuccessArray(String requestType, JSONArray response) {
        System.out.println("DATA::"+response);
        switch (requestType) {
            case "SUELDOS":
                SueldoDAO sueldoDAO = new SueldoDAO();
                objL_listaSueldo.addAll(sueldoDAO.fn_SueldoEmpleado(response));
                System.out.println("OPS::"+objL_listaSueldo.size());
                objP_ADTRSueldos.notifyDataSetChanged();
                break;
        }
    }

    @Override
    public void notifyError(String requestType, VolleyError error) {
        System.out.println("DATA::"+requestType);
        error.printStackTrace();
    }
}