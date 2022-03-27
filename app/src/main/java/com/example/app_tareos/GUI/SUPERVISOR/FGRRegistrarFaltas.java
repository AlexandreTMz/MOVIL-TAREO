package com.example.app_tareos.GUI.SUPERVISOR;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;

import com.android.volley.VolleyError;
import com.example.app_tareos.ADAPTADORES.ADTLEmpleado;
import com.example.app_tareos.ADAPTADORES.ADTREmpleadoFalta;
import com.example.app_tareos.ADAPTADORES.ADTRPersonas;
import com.example.app_tareos.CONFIG.UrlServer;
import com.example.app_tareos.DAO.EmpleadoDAO;
import com.example.app_tareos.DAO.MarcadorDAO;
import com.example.app_tareos.DAO.PermisoDAO;
import com.example.app_tareos.GUI.PUBLICO.ACTScannerPermiso;
import com.example.app_tareos.INTERFACE.IResultVolley;
import com.example.app_tareos.LIBS.EditTexDialogDate;
import com.example.app_tareos.LIBS.EditTextDialogTime;
import com.example.app_tareos.LIBS.ErrorToastyServer;
import com.example.app_tareos.LIBS.VolleyService;
import com.example.app_tareos.MODEL.Empleado;
import com.example.app_tareos.MODEL.Marcador;
import com.example.app_tareos.MODEL.Permiso;
import com.example.app_tareos.MODEL.Persona;
import com.example.app_tareos.R;
import com.example.app_tareos.ROOMS.Preference_PerfilUsuario;
import com.example.app_tareos.UTILIDADES.Utilidades;
import com.github.angads25.toggle.interfaces.OnToggledListener;
import com.github.angads25.toggle.model.ToggleableView;
import com.github.angads25.toggle.widget.LabeledSwitch;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import es.dmoral.toasty.Toasty;

public class FGRRegistrarFaltas extends Fragment implements IResultVolley, ADTREmpleadoFalta.OnClicksListener{

    // variables globales
    private boolean blnP_Seleccionado = false;

    // GUI
    EditText edFechaPermiso;
    EditText edDniPersona;
    ImageButton btnCamaraTareo;
    ImageButton btnBuscar;
    Button btnRegistrarPermiso;

    // RECYCLERVIEW
    //ListView lvEmpleado;
    RecyclerView rvwPersonasReg;

    // LIB EDITTEXT DATE
    EditTexDialogDate eddFechaTareo;

    // ADAPTAER MARCADOR
    ArrayAdapter<CharSequence> adaptadorMarcador;

    // LIST
    List<Permiso> lsPermiso = new ArrayList<>();
    List<Marcador> lsMarcador = new ArrayList<>();

    Spinner spTipo;

    // voley
    VolleyService mVolleyService;
    ErrorToastyServer errorToastyServer;

    // LISTAS
    List<Empleado> lsEmpleado = new ArrayList<>();

    // ADAPTERS
    //ADTLEmpleado adaptadorListaEmpleado;
    ADTREmpleadoFalta objP_AdaptadorEmpleado;

    // persona
    Empleado empleado;

    // ES TITULAR?
    int isRemunerado = 1;

    // PREFERENCE ROOMS
    public Preference_PerfilUsuario userProfile;

    ProgressDialog dlgCargando;

    EditTextDialogTime eddHoraRegistro;

    float objL_HightList;
    float objL_HightListMax;

    public FGRRegistrarFaltas() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fgt_registrar_faltas, container, false);

        // Cambio de titulo
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Registrar falta / descanso");

        // Persona
        empleado = new Empleado();

        // TAMAÑO
        objL_HightList = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 100, getResources().getDisplayMetrics());
        objL_HightListMax = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 300, getResources().getDisplayMetrics());

        // DIALOGO DE CARGA
        dlgCargando = new ProgressDialog(getContext());
        dlgCargando.setProgressStyle(ProgressDialog.STYLE_SPINNER);


        btnRegistrarPermiso = view.findViewById(R.id.btnRegistrarPermiso);
        btnBuscar = view.findViewById(R.id.btnBuscar);
        btnCamaraTareo = view.findViewById(R.id.btnCamaraTareo);

        btnCamaraTareo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toasty.success(getContext(), "Press", Toasty.LENGTH_LONG).show();
                escanear();
            }
        });

        // instanciado el room
        userProfile = Preference_PerfilUsuario.getInstance(getActivity());

        /*ASUING*/
        edFechaPermiso = view.findViewById(R.id.edFechaTareo);
        edDniPersona = view.findViewById(R.id.edDniPersona);

        // LIB EDITTEXT DATE
        eddFechaTareo =  new EditTexDialogDate(getContext(), edFechaPermiso);
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String time = df.format(new Date());
        edFechaPermiso.setText(time);

        spTipo = view.findViewById(R.id.spTipo);


        /// ERROR SERVER
        errorToastyServer = new ErrorToastyServer(getActivity());

        // INTANCE SERVICE
        mVolleyService = new VolleyService(this, getActivity());

        // ETAPA MARCADOR TIPO
        adaptadorMarcador = new ArrayAdapter(getActivity(), R.layout.spinner_item_opt, lsMarcador);
        spTipo.setAdapter(adaptadorMarcador);

        // LISTVIEW
        //lvEmpleado = view.findViewById(R.id.lvPersona);
        rvwPersonasReg = view.findViewById(R.id.rvwPersonasReg);

        // ADAPTERS
        rvwPersonasReg = view.findViewById(R.id.rvwPersonasReg);
        objP_AdaptadorEmpleado = new ADTREmpleadoFalta(getContext(),lsEmpleado);
        objP_AdaptadorEmpleado.setOnClicksListenerCustom(this);
        rvwPersonasReg.setAdapter(objP_AdaptadorEmpleado);
        rvwPersonasReg.setLayoutManager(new LinearLayoutManager(getContext()));


        // EDITPERSONA DNI
        edDniPersona.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {

            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
                // TODO Auto-generated method stub
            }

            @Override
            public void afterTextChanged(Editable arg0) {
                // TODO Auto-generated method stub
            }
        });

        btnBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utilidades.clearListObject(lsEmpleado);
                blnP_Seleccionado = false;
                //lyDatos.setVisibility(View.GONE);
                if(edDniPersona.getText().length()>4){
                    mVolleyService.mtd_GetArrayVolley("EMPLEADO_PERMISO", String.format(
                            UrlServer.URL_SERVER + "persona/empleado/" + edDniPersona.getText().toString()+"/"+userProfile.getId_sede()
                    ));
                    dlgCargando.setTitle("Buscando empleado");
                    dlgCargando.setMessage("Por favor espere...");
                    dlgCargando.setIndeterminate(true);
                    dlgCargando.setCanceledOnTouchOutside(false);
                    dlgCargando.show();
                }else{
                    Toasty.error(getContext(),"Ingrese un documento!",Toasty.LENGTH_SHORT).show();
                }
            }
        });

        btnRegistrarPermiso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mtd_RegistrarDescansoFaltas();
            }
        });

        mVolleyService.mtd_GetArrayVolley("MARCADOR", String.format(UrlServer.URL_SERVER + "marcador/4"));

        return view;
    }

    public void mtd_RegistrarDescansoFaltas(){
        /*DATOS PERSONALES*/
        if(!blnP_Seleccionado){
            Toasty.error(getActivity(),"Seleccione a una persona!",Toasty.LENGTH_LONG).show();
            return;
        }

        /*EMPLEADO*/
        //String strL_Nombres = empleado.getPersona().getDatos();
        int intL_IdPersona = empleado.getPersona().getId_persona();
        String strL_TpDocumento = empleado.getPersona().getId_tpdocumento();
        int  intL_Nacionalidad = empleado.getPersona().getId_nacionalidad();
        int intL_Sede = empleado.getSede().getId_sede();
        int intL_Cargo = empleado.getCargo().getId_cargo();
        int intL_IdSueldo = empleado.getId_sueldo();

        /* SUPERVISOR */
        int intL_Usuario = userProfile.getUsuarioInfo().getId_usuario();

        /*TAREO*/
        String strL_Tipo = String.valueOf(((Marcador) spTipo.getSelectedItem()).getId_marcador());

        String strL_userCreacion = userProfile.getUsuarioInfo().getUs_usuario();

        String strL_Fecha = edFechaPermiso.getText().toString();
        int intL_Remunerado = isRemunerado;

        /* OBKJECT */
        Map<String, Object> dataPost = new HashMap<>();
        dataPost.put("id_marcador",  strL_Tipo);
        dataPost.put("id_permiso",  0);
        dataPost.put("id_persona",  intL_IdPersona);
        dataPost.put("id_tpdocumento",  strL_TpDocumento);
        dataPost.put("id_nacionalidad", intL_Nacionalidad);
        dataPost.put("id_cargo",    intL_Cargo);
        dataPost.put("id_sede", intL_Sede);
        dataPost.put("id_sueldo", intL_IdSueldo);

        // tareo
        dataPost.put("ta_estado",  1);
        dataPost.put("trs_fecha_r", strL_Fecha);
        dataPost.put("trs_remunerado", intL_Remunerado);

        // usuario
        dataPost.put("ta_usuario", intL_Usuario);
        dataPost.put("userCreacion", strL_userCreacion);

        JSONObject json =  new JSONObject(dataPost);
        System.out.println(json);

        mVolleyService.mtd_PostObjectVolley("REGISTRO_DESCFALTA", String.format(UrlServer.URL_SERVER + "tareo/descanso/falta"),json);

        dlgCargando.setTitle("Registrando");
        dlgCargando.setMessage("Por favor espere...");
        dlgCargando.setIndeterminate(true);
        dlgCargando.setCanceledOnTouchOutside(false);
        dlgCargando.show();

    }


    private static final int CODIGO_INTENT = 2;
    private void escanear() {
        Intent i = new Intent(getActivity(), ACTScannerPermiso.class);
        i.putExtra("fecha",edFechaPermiso.getText().toString());
        startActivityForResult(i, CODIGO_INTENT);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == CODIGO_INTENT) {
            if (resultCode == Activity.RESULT_OK) {
                if (data != null) {
                    String codigo = data.getStringExtra("codigo");
                    edDniPersona.setText(codigo);
                    btnBuscar.performClick();
                }
            }
        }
    }

    @Override
    public void notifySuccessOject(String requestType, JSONObject response) {
        switch (requestType) {
            case "REGISTRO_DESCFALTA":
                dlgCargando.dismiss();
                mtd_ClearForm();
                System.out.println(response.toString());
                Toasty.success(getActivity(),"Se registro correctamente!",Toasty.LENGTH_LONG).show();
                break;
        }
    }

    @Override
    public void notifySuccessArray(String requestType, JSONArray response) {
        System.out.println(response.toString());
        switch (requestType) {
            case "EMPLEADO_PERMISO":
                Log.d("EMP", "EMPLEADO:: " + requestType);
                Log.d("EMP", "EMPLEADO:: " + response);
                dlgCargando.dismiss();
                lsEmpleado.addAll(new EmpleadoDAO().fn_EmpleadoPermiso(response));
                if (lsEmpleado.size() == 1) {
                    rvwPersonasReg.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, (int)objL_HightList));
                } else if (lsEmpleado.size() <= 0) {
                    blnP_Seleccionado = false;
                    Utilidades.clearListObject(lsEmpleado);
                    rvwPersonasReg.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0));
                    Toasty.info(getContext(),"No se encontraron resultados!",Toasty.LENGTH_SHORT).show();
                } else {
                    blnP_Seleccionado = false;
                    rvwPersonasReg.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, (int)objL_HightListMax));
                }
                break;
            case "MARCADOR":
                lsMarcador.addAll(new MarcadorDAO().fn_Marcador(response));
                adaptadorMarcador.notifyDataSetChanged();
                break;
        }
    }

    @Override
    public void notifyError(String requestType, VolleyError error) {
        switch (requestType) {
            case "REGISTRO_DESCFALTA":
                dlgCargando.dismiss();
                errorToastyServer.showErrorServer(error);
                break;
        }
    }

    public void mtd_ClearForm(){
        edFechaPermiso.setText(Utilidades.fn_GetDate());
        edDniPersona.setText("");
        Utilidades.clearListObject(lsEmpleado);
        blnP_Seleccionado = false;
        rvwPersonasReg.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0));
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
    }

    @Override
    public void onClickSeleccionar(View v, int position) {
        empleado = lsEmpleado.get(position);
        System.out.println("Listado:: "+empleado.toString());
        //edDniPersonaSuplente.setText(persona.getPersona().getPer_documento());
        Toasty.success(getActivity(), "Seleccionado: " + empleado.getPersona().getDatos(), Toasty.LENGTH_SHORT).show();
        Utilidades.clearListObject(lsEmpleado);
        lsEmpleado.add(empleado);
        //pgrPerson.setVisibility(View.GONE);
        blnP_Seleccionado = true;
        objP_AdaptadorEmpleado.notifyDataSetChanged();
    }
}