package com.example.app_tareos.GUI.SUPERVISOR;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.VolleyError;
import com.androidbuts.multispinnerfilter.KeyPairBoolData;
import com.androidbuts.multispinnerfilter.MultiSpinnerListener;
import com.androidbuts.multispinnerfilter.MultiSpinnerSearch;
import com.androidbuts.multispinnerfilter.SingleSpinnerListener;
import com.androidbuts.multispinnerfilter.SingleSpinnerSearch;
import com.example.app_tareos.ADAPTADORES.ADTLDescansoPersona;
import com.example.app_tareos.ADAPTADORES.ADTLPersonas;
import com.example.app_tareos.CONFIG.UrlServer;
import com.example.app_tareos.DAO.BancoDAO;
import com.example.app_tareos.DAO.CargoDAO;
import com.example.app_tareos.DAO.NacionalidadDAO;
import com.example.app_tareos.DAO.PersonaDAO;
import com.example.app_tareos.DAO.SedeDAO;
import com.example.app_tareos.DAO.SexoDAO;
import com.example.app_tareos.DAO.TipoCuentaDAO;
import com.example.app_tareos.DAO.TipoDocumentoDAO;
import com.example.app_tareos.INTERFACE.IResultVolley;
import com.example.app_tareos.LIBS.EditTexDialogDate;
import com.example.app_tareos.LIBS.ErrorToastyServer;
import com.example.app_tareos.LIBS.VolleyService;
import com.example.app_tareos.MODEL.DescansoPersona;
import com.example.app_tareos.MODEL.Sexo;
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


public class FRGRegistrarDescanso extends Fragment implements IResultVolley {

    String TAG = "XDDD";
    List<KeyPairBoolData> lsPersonasSede = new ArrayList<>();
    List<KeyPairBoolData> lsSeleccionadasPersonasSede = new ArrayList<>();

    MultiSpinnerSearch multiSelectSpinnerWithSearch;
    // voley
    VolleyService mVolleyService;
    ErrorToastyServer errorToastyServer;
    // PREFERENCE ROOMS
    public Preference_PerfilUsuario userProfile;

    //GUI
    Button btnRegistrarDescanso;
    EditText edFechaDescanso;
    EditTexDialogDate dlgFechaDescanso;

    private ListView lsErrorDescanso;

    List<DescansoPersona> lsErrores = new ArrayList<>();

    // ADAPTERS
    ADTLDescansoPersona adpErrores;

    // LINEAR
    LinearLayout lyDescanso;

    public FRGRegistrarDescanso() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,  Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fgt_registrar_descanso, container, false);

        // Cambio de titulo
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Programar descanso");

        lsErrorDescanso = view.findViewById(R.id.lsErrorDescanso);

        btnRegistrarDescanso = view.findViewById(R.id.btnRegistrarDescanso);
        edFechaDescanso = view.findViewById(R.id.edFechaDescanso);
        lyDescanso = view.findViewById(R.id.lyDescanso);

        edFechaDescanso.setText(Utilidades.fn_GetDate());
        dlgFechaDescanso = new EditTexDialogDate(getContext(),edFechaDescanso);

        userProfile = Preference_PerfilUsuario.getInstance(getActivity());

        /// ERROR SERVER
        errorToastyServer = new ErrorToastyServer(getActivity());

        // INTANCE SERVICE
        mVolleyService = new VolleyService(this, getActivity());

        // LISTVIEW
        adpErrores = new ADTLDescansoPersona(getActivity(), lsErrores);
        lsErrorDescanso.setAdapter(adpErrores);

        int intL_IdSede = userProfile.getId_sede();
        /* OBKJECT */
        Map<String, Object> dataPost = new HashMap<>();
        dataPost.put("id_sede",  intL_IdSede);
        JSONObject json =  new JSONObject(dataPost);
        System.out.println(json);
        mVolleyService.mtd_PostObjectVolley("PERSONAS", String.format(UrlServer.URL_SERVER + "persona/sedes/descanso"),json);

        /**
         * Search MultiSelection Spinner (With Search/Filter Functionality)
         *
         *  Using MultiSpinnerSearch class
         */
        multiSelectSpinnerWithSearch = view.findViewById(R.id.multipleItemSelectionSpinner);

        // Pass true If you want searchView above the list. Otherwise false. default = true.
        multiSelectSpinnerWithSearch.setSearchEnabled(true);

        // A text that will display in search hint.
        multiSelectSpinnerWithSearch.setSearchHint("Buscar personas...");

        // Set text that will display when search result not found...
        multiSelectSpinnerWithSearch.setEmptyTitle("Persona no encontrada");

        // If you will set the limit, this button will not display automatically.
        multiSelectSpinnerWithSearch.setShowSelectAllButton(true);

        //A text that will display in clear text button
        multiSelectSpinnerWithSearch.setClearText("Cerrar y limpiar");

        // Removed second parameter, position. Its not required now..
        // If you want to pass preselected items, you can do it while making listArray,
        // pass true in setSelected of any item that you want to preselect
        multiSelectSpinnerWithSearch.setItems(lsPersonasSede, new MultiSpinnerListener() {
            @Override
            public void onItemsSelected(List<KeyPairBoolData> items) {
                if(lsSeleccionadasPersonasSede.size()>0){
                    Utilidades.clearListObject(lsSeleccionadasPersonasSede);
                }
                for (int i = 0; i < items.size(); i++) {
                    if (items.get(i).isSelected()) {
                        //Log.i(TAG, i + " : " + items.get(i).getName() + " : " + items.get(i).isSelected());
                        lsSeleccionadasPersonasSede.add(items.get(i));
                    }
                }
            }
        });

        multiSelectSpinnerWithSearch.setLimit(-1, new MultiSpinnerSearch.LimitExceedListener() {
            @Override
            public void onLimitListener(KeyPairBoolData data) {

            }
        });

        btnRegistrarDescanso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String StrgL_Fecha = edFechaDescanso.getText().toString();
                if(lyDescanso.getVisibility() == View.GONE){
                    lyDescanso.setVisibility(View.VISIBLE);
                }
                for (int i = 0; i < lsSeleccionadasPersonasSede.size(); i++) {
                    if (lsSeleccionadasPersonasSede.get(i).isSelected()) {
                        mtd_RegistrarDescanso(lsSeleccionadasPersonasSede.get(i).getId(),StrgL_Fecha);
                        //Log.i("SELECCIOANDO", i + " : " + lsSeleccionadasPersonasSede.get(i).getName() + " : " + lsSeleccionadasPersonasSede.get(i).isSelected());
                    }
                }
            }
        });
        return view;
    }


    public void mtd_RegistrarDescanso(long Id_persona, String Fecha){
        /* OBKJECT */
        Map<String, Object> dataPost = new HashMap<>();
        dataPost.put("id_persona",  Id_persona);
        dataPost.put("de_fecha",  Fecha);
        JSONObject json =  new JSONObject(dataPost);
        System.out.println(json);
        mVolleyService.mtd_PostObjectVolley("DESCANSO", String.format(UrlServer.URL_SERVER + "empleado/descanso"),json);
        Utilidades.clearListObject(lsErrores);
        adpErrores.notifyDataSetChanged();
    }

    @Override
    public void notifySuccessOject(String requestType, JSONObject response) {
        switch (requestType) {
            case "PERSONAS":
                try{
                    System.out.println(response.toString());
                    JSONObject jsonObject = response;
                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                    if(jsonArray.length()>0){
                        for (int i =0; i < jsonArray.length(); i++){
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                            KeyPairBoolData keyPairBoolData = new KeyPairBoolData();
                            keyPairBoolData.setName(jsonObject1.getString("datos"));
                            keyPairBoolData.setId(jsonObject1.getInt("id_persona"));
                            lsPersonasSede.add(keyPairBoolData);
                            //System.out.println(jsonObject1.getString("datos"));
                        }
                    }else{
                        Toasty.info(getContext(),"No se encontro registro!",Toasty.LENGTH_LONG).show();
                    }
                    //multiSelectSpinnerWithSearch.notify();
                    System.out.println(jsonArray.length());
                }catch (Exception e){
                    e.printStackTrace();
                }
                break;
            case "DESCANSO":
                    Toasty.success(getContext(),"Descanso registrado correctamente!",Toasty.LENGTH_LONG).show();
                    System.out.println(response.toString());
                break;
        }
    }

    @Override
    public void notifySuccessArray(String requestType, JSONArray response) {

    }

    @Override
    public void notifyError(String requestType, VolleyError volleyError) {
        switch (requestType) {
            case "PERSONAS":
                errorToastyServer.showErrorServer(volleyError);
                break;
            case "DESCANSO":
                System.out.println("entro1");
                if(volleyError.networkResponse != null && volleyError.networkResponse.data != null){
                    NetworkResponse errorRes = volleyError.networkResponse;
                    try {
                        JSONObject jsonResponse = new JSONObject(new String(errorRes.data,"UTF-8"));
                        DescansoPersona descansoPersona = new DescansoPersona();
                        descansoPersona.setPersona(jsonResponse.get("msg").toString());
                        descansoPersona.setError("Ya cuenta con un descanso en este día");
                        descansoPersona.setEstado(0);
                        lsErrores.add(descansoPersona);
                        adpErrores.notifyDataSetChanged();
                    }catch (Exception e){
                        //e.printStackTrace();
                    }
                }
                break;
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
    }
}