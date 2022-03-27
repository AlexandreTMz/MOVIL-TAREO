package com.example.app_tareos.GUI.SUPERVISOR;

import android.app.ProgressDialog;
import android.content.pm.ActivityInfo;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.android.volley.VolleyError;
import com.example.app_tareos.CONFIG.UrlServer;
import com.example.app_tareos.DAO.MarcadorDAO;
import com.example.app_tareos.INTERFACE.IResultVolley;
import com.example.app_tareos.LIBS.EditTexDialogDate;
import com.example.app_tareos.LIBS.ErrorToastyServer;
import com.example.app_tareos.LIBS.VolleyService;
import com.example.app_tareos.MODEL.Marcador;
import com.example.app_tareos.R;
import com.example.app_tareos.ROOMS.Preference_PerfilUsuario;
import com.example.app_tareos.UTILIDADES.Utilidades;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import es.dmoral.toasty.Toasty;

public class FGRFiltroReporteTareoSU extends Fragment implements IResultVolley{


    /*GUI*/
    EditText edFechaInicio;
    EditText edFechaFin;
    EditText edDocumento;
    Spinner spTurno;

    // LIBS
    EditTexDialogDate eddFechaInicio;
    EditTexDialogDate eddFechaFin;

    // ADAPTAER ETAPA
    ArrayAdapter<CharSequence> adaptadorTurno;

    // LIST
    List<Marcador> lsMarcador;

    // voley
    VolleyService mVolleyService;
    ErrorToastyServer errorToastyServer;

    // progress
    ProgressDialog pgdBuscandoReporte;

    // PREFERENCE ROOMS
    public Preference_PerfilUsuario userProfile;

    public FGRFiltroReporteTareoSU() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fgt_filtro_reporte_tareo_su, container, false);

        // Cambio de titulo
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Filtro reporte");

        // Orientacion vertical siempre
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        // iniciando
        lsMarcador = new ArrayList<>();

        userProfile = Preference_PerfilUsuario.getInstance(getActivity());

        pgdBuscandoReporte = new ProgressDialog(getContext());
        pgdBuscandoReporte.setProgressStyle(ProgressDialog.STYLE_SPINNER);

        /*ASING TO*/
        edFechaInicio = view.findViewById(R.id.edFechaInicio);
        edFechaFin = view.findViewById(R.id.edFechaFin);
        edDocumento = view.findViewById(R.id.edDocumento);

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String time = df.format(new Date());
        edFechaInicio.setText(time);
        edFechaFin.setText(time);

        eddFechaInicio = new EditTexDialogDate(getContext(),edFechaInicio);
        eddFechaFin = new EditTexDialogDate(getContext(),edFechaFin);

        spTurno = view.findViewById(R.id.spTurno);

        // ETAPA SPINNER
        adaptadorTurno = new ArrayAdapter(getActivity(), R.layout.spinner_item_opt, lsMarcador);
        spTurno.setAdapter(adaptadorTurno);

        Button btnBuscar = view.findViewById(R.id.btnBuscarTareo);

        btnBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pgdBuscandoReporte.setTitle("Buscando tareo");
                pgdBuscandoReporte.setMessage("Por favor espere...");
                pgdBuscandoReporte.setIndeterminate(true);
                pgdBuscandoReporte.setCanceledOnTouchOutside(false);
                pgdBuscandoReporte.show();
                mtd_BuscarTareos();
                //Utilidades.mdt_ChangeFragment(getActivity(), new FGRReporteTareo());
            }
        });

        /// ERROR SERVER
        errorToastyServer = new ErrorToastyServer(getActivity());

        // INTANCE SERVICE
        mVolleyService = new VolleyService(this, getActivity());

        // Consultas
        mVolleyService.mtd_GetArrayVolley("MARCADOR", String.format(UrlServer.URL_SERVER + "marcador/1"));

        return view;
    }

    public void mtd_BuscarTareos(){
        /*TAREO*/
        String strL_Turno = String.valueOf(((Marcador) spTurno.getSelectedItem()).getId_marcador());
        String strL_FechaInicio = edFechaInicio.getText().toString();
        String strL_FechaFin = edFechaFin.getText().toString();
        String strL_Documento = edDocumento.getText().toString();
        int intL_IdSede = userProfile.getId_sede();

        /* OBKJECT */
        Map<String, Object> dataPost = new HashMap<>();
        dataPost.put("id_marcador",  strL_Turno);
        dataPost.put("fechaInicio",  strL_FechaInicio);
        dataPost.put("fechaFin",  strL_FechaFin);
        dataPost.put("documento",  strL_Documento);
        dataPost.put("id_sede",  intL_IdSede);
        JSONObject json =  new JSONObject(dataPost);
        System.out.println(json);
        mVolleyService.mtd_PostObjectVolley("TAREO", String.format(UrlServer.URL_SERVER + "tareo/reporte"),json);
    }


    List<String[]> lsResultado = new ArrayList<>();

    @Override
    public void notifySuccessOject(String requestType, JSONObject response) {
        switch (requestType) {
            case "TAREO":
                pgdBuscandoReporte.dismiss();
                try{
                    System.out.println(response.toString());
                    JSONObject jsonObject = response;
                    JSONArray jsonArray = jsonObject.getJSONArray("data");

                    if(jsonArray.length()>0){
                        lsResultado = new ArrayList<>();
                        for (int i =0; i < jsonArray.length(); i++){
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                            lsResultado.add(new String[]{
                                    jsonObject1.getString("id_tareo"),
                                    jsonObject1.getString("datos"),
                                    jsonObject1.getString("sede_datos"),
                                    jsonObject1.getString("estado"),
                                    jsonObject1.getString("etapa"),
                                    jsonObject1.getString("fecha_ingreso"),
                                    jsonObject1.getString("hora_ingreso"),
                                    jsonObject1.getString("fecha_cierre"),
                                    jsonObject1.getString("hora_cierre"),
                                    jsonObject1.getString("marcador_datos")
                            });
                        }
                        Utilidades.mdt_ChangeFragment(getActivity(), new FGRReporteTareo(lsResultado));
                    }else{
                        Toasty.info(getContext(),"No se encontro registro!",Toasty.LENGTH_LONG).show();
                    }

                    System.out.println(jsonArray.length());
                }catch (Exception e){
                    e.printStackTrace();
                }
            break;
        }
    }

    @Override
    public void notifySuccessArray(String requestType, JSONArray response) {
        switch (requestType) {
            case "MARCADOR":
                MarcadorDAO marcadorDAO = new MarcadorDAO();
                List<Marcador> objL_LsMarcador = marcadorDAO.fn_Marcador(response);
                Marcador marcador = new Marcador();
                marcador.setId_marcador(0);
                marcador.setDatos("TODOS");
                objL_LsMarcador.add(0,marcador);
                lsMarcador.addAll(objL_LsMarcador);
                adaptadorTurno.notifyDataSetChanged();
                break;
        }
    }

    @Override
    public void notifyError(String requestType, VolleyError error) {
        pgdBuscandoReporte.dismiss();
        switch (requestType) {
            case "TAREO":
                errorToastyServer.showErrorServer(error);
                error.printStackTrace();
                break;
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
    }
}