package com.example.app_tareos.GUI.OPERARIO;

import android.content.pm.ActivityInfo;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
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
import com.example.app_tareos.GUI.SUPERVISOR.FGRReporteTareo;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import es.dmoral.toasty.Toasty;

public class FGRFiltroReporteTareoOP extends Fragment implements IResultVolley {

    /*GUI*/
    EditText edFechaInicio;
    EditText edFechaFin;
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

    // PREFERENCE ROOMS
    public Preference_PerfilUsuario userProfile;

    public FGRFiltroReporteTareoOP() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fgt_filtro_reporte_tareo_op, container, false);

        // Cambio de titulo
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Filtro reporte");

        // Orientacion vertical siempre
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        // iniciando
        lsMarcador = new ArrayList<>();

        /*ASING TO*/
        edFechaInicio = view.findViewById(R.id.edFechaInicio);
        edFechaFin = view.findViewById(R.id.edFechaFin);

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
                mtd_BuscarTareos();
                //Utilidades.mdt_ChangeFragment(getActivity(), new FGRReporteTareo());
            }
        });

        // instanciado el room
        userProfile = Preference_PerfilUsuario.getInstance(getActivity());

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
        String strL_Documento = userProfile.getUsuarioInfo().getPer_documento();

        /* OBKJECT */
        Map<String, Object> dataPost = new HashMap<>();
        dataPost.put("id_marcador",  strL_Turno);
        dataPost.put("fechaInicio",  strL_FechaInicio);
        dataPost.put("fechaFin",  strL_FechaFin);
        dataPost.put("documento",  strL_Documento);

        JSONObject json =  new JSONObject(dataPost);
        System.out.println(json);

        mVolleyService.mtd_PostObjectVolley("TAREO", String.format(UrlServer.URL_SERVER + "tareo/reporte/operario"),json);
    }

    List<String[]> lsResultado = new ArrayList<>();

    @Override
    public void notifySuccessOject(String requestType, JSONObject response) {
        switch (requestType) {
            case "TAREO":
                System.out.println(response.toString());
                 try{
                    System.out.println(response.toString());
                    JSONObject jsonObject = response;
                    JSONArray jsonArray = jsonObject.getJSONArray("data");

                    if(lsResultado.size()>0){
                        lsResultado.clear();
                    }

                    if(jsonArray.length()>0){
                        for (int i =0; i < jsonArray.length(); i++){
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                            lsResultado.add(new String[]{
                                    jsonObject1.getString("id_tareo"),
                                    jsonObject1.getString("per_documento"),
                                    jsonObject1.getString("datos"),
                                    jsonObject1.getString("ca_descripcion"),
                                    jsonObject1.getString("marcador"),
                                    jsonObject1.getString("ta_fecha_r"),
                                    jsonObject1.getString("ta_hora_r"),
                                    jsonObject1.getString("ta_fecha_c"),
                                    jsonObject1.getString("ta_hora_c"),
                                    jsonObject1.getString("estado"),
                                    jsonObject1.getString("pagoXEsteDia")
                            });
                        }
                        Utilidades.mdt_ChangeFragment(getActivity(), new FGRReporteTareoOperario(lsResultado));
                    }else{
                        Toasty.info(getContext(),"No se encontraron datos",Toasty.LENGTH_SHORT).show();
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
        switch (requestType) {
            case "TAREO":
                errorToastyServer.showErrorServer(error);
                error.printStackTrace();
                break;
        }
    }

}