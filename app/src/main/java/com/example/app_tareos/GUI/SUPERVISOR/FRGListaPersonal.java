package com.example.app_tareos.GUI.SUPERVISOR;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TableLayout;

import com.android.volley.VolleyError;
import com.example.app_tareos.ADAPTADORES.ADTREmpleadoLista;
import com.example.app_tareos.ADAPTADORES.ADTREmpleadoPermiso;
import com.example.app_tareos.CONFIG.UrlServer;
import com.example.app_tareos.DAO.EmpleadoDAO;
import com.example.app_tareos.DAO.MarcadorDAO;
import com.example.app_tareos.DAO.PermisoDAO;
import com.example.app_tareos.DAO.SexoDAO;
import com.example.app_tareos.INTERFACE.IResultVolley;
import com.example.app_tareos.LIBS.ErrorToastyServer;
import com.example.app_tareos.LIBS.TablaDinamica;
import com.example.app_tareos.LIBS.VolleyService;
import com.example.app_tareos.MODEL.Empleado;
import com.example.app_tareos.R;
import com.example.app_tareos.ROOMS.Preference_PerfilUsuario;
import com.example.app_tareos.UTILIDADES.Utilidades;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import es.dmoral.toasty.Toasty;


public class FRGListaPersonal extends Fragment implements IResultVolley, ADTREmpleadoLista.OnClicksListener {


    // PREFERENCE ROOMS
    public Preference_PerfilUsuario userProfile;

    // voley
    VolleyService mVolleyService;
    ErrorToastyServer errorToastyServer;

    // ADAPTADOR
    ADTREmpleadoLista objP_ADTREmpleadoLista;
    ArrayAdapter<CharSequence> adaptadorEstadoEmpleado;

    // RECYCLERVIEW
    RecyclerView rvwPersonasReg;

    // LISTAS
    List<Empleado> lsEmpleado = new ArrayList<>();

    List<EstadoEmpleado> lsEstado = new ArrayList<>();

    //
    Spinner spEstadoEmpleado;

    ProgressDialog dlgCargando;

    public FRGListaPersonal() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    int iCurrentSelection = 0;
    int intL_IdSede;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,  Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fgt_lista_personal, container, false);

        // Cambio de titulo
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Lista de personal");

        userProfile = Preference_PerfilUsuario.getInstance(getActivity());

        /// ERROR SERVER
        errorToastyServer = new ErrorToastyServer(getActivity());

        // INTANCE SERVICE
        mVolleyService = new VolleyService(this, getActivity());

        spEstadoEmpleado = view.findViewById(R.id.spEstadoEmpleado);

        // ADAPTERS
        rvwPersonasReg = view.findViewById(R.id.rvwPersonasReg);
        objP_ADTREmpleadoLista = new ADTREmpleadoLista(getContext(),lsEmpleado);
        objP_ADTREmpleadoLista.setOnClicksListenerCustom(this);
        rvwPersonasReg.setAdapter(objP_ADTREmpleadoLista);
        rvwPersonasReg.setLayoutManager(new LinearLayoutManager(getContext()));

        // Spiner estado
        lsEstado.add(new EstadoEmpleado("1","Activos"));
        lsEstado.add(new EstadoEmpleado("0","Inactivos"));
        lsEstado.add(new EstadoEmpleado("T","Todos"));
        adaptadorEstadoEmpleado = new ArrayAdapter(getActivity(), R.layout.spinner_item_opt, lsEstado);
        spEstadoEmpleado.setAdapter(adaptadorEstadoEmpleado);


        intL_IdSede = userProfile.getId_sede();


        iCurrentSelection = spEstadoEmpleado.getSelectedItemPosition();

        // DIALOGO DE CARGA
        dlgCargando = new ProgressDialog(getContext());
        dlgCargando.setProgressStyle(ProgressDialog.STYLE_SPINNER);

        spEstadoEmpleado.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                if (iCurrentSelection != position){
                    EstadoEmpleado objL_estado = lsEstado.get(position);
                    mVolleyService.mtd_GetArrayVolley("EMPLEADOS_SEDE", String.format(UrlServer.URL_SERVER + "persona/sedes/"+intL_IdSede+"/"+objL_estado.getId()));
                    dlgCargando.setTitle("Buscando empleados");
                    dlgCargando.setMessage("Porfavor espere...");
                    dlgCargando.setIndeterminate(true);
                    dlgCargando.setCanceledOnTouchOutside(false);
                    dlgCargando.show();
                }
                iCurrentSelection = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });

        // VOLEY
        mVolleyService.mtd_GetArrayVolley("EMPLEADOS_SEDE", String.format(UrlServer.URL_SERVER + "persona/sedes/"+intL_IdSede+"/1"));

        return view;
    }

    @Override
    public void notifySuccessOject(String requestType, JSONObject response) {

    }

    @Override
    public void notifySuccessArray(String requestType, JSONArray response) {
        switch (requestType) {
            case "EMPLEADOS_SEDE":
                dlgCargando.dismiss();
                Log.d("EMP", "EMPLEADO:: " + requestType);
                Log.d("EMP", "EMPLEADO:: " + response);
                dlgCargando.dismiss();
                if(lsEmpleado.size() > 0){
                    lsEmpleado.clear();
                }
                lsEmpleado.addAll(new EmpleadoDAO().fn_EmpleadoListaTotal(response));
                objP_ADTREmpleadoLista.notifyDataSetChanged();
                break;
        }
    }

    @Override
    public void notifyError(String requestType, VolleyError error) {
        switch (requestType) {
            case "EMPLEADOS_SEDE":
                dlgCargando.dismiss();
                errorToastyServer.showErrorServer(error);
                break;
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
    }

    @Override
    public void onClickSeleccionar(View v, int position) {

    }

    class EstadoEmpleado{
        String id;
        String label;

        public EstadoEmpleado(String id, String label) {
            this.id = id;
            this.label = label;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getLabel() {
            return label;
        }

        public void setLabel(String label) {
            this.label = label;
        }

        @Override
        public String toString() {
            return label;
        }
    }
}