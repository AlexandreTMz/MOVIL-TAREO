package com.example.app_tareos.GUI.SUPERVISOR;

import android.app.Activity;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import com.android.volley.VolleyError;
import com.example.app_tareos.ADAPTADORES.ADTLEmpleado;
import com.example.app_tareos.ADAPTADORES.ADTLTareo;
import com.example.app_tareos.DAO.TareoDAO;
import com.example.app_tareos.GUI.PUBLICO.ACTScannerTareo;
import com.example.app_tareos.GUI.PUBLICO.ActivityEscanear;
import com.example.app_tareos.CONFIG.UrlServer;
import com.example.app_tareos.DAO.EmpleadoDAO;
import com.example.app_tareos.DAO.MarcadorDAO;
import com.example.app_tareos.INTERFACE.IResultVolley;
import com.example.app_tareos.LIBS.EditTexDialogDate;
import com.example.app_tareos.LIBS.EditTextDialogTime;
import com.example.app_tareos.LIBS.ErrorToastyServer;
import com.example.app_tareos.LIBS.VolleyService;
import com.example.app_tareos.MODEL.Empleado;
import com.example.app_tareos.MODEL.Marcador;
import com.example.app_tareos.MODEL.Tareo;
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
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import es.dmoral.toasty.Toasty;

public class FGRRegistrarTareo extends Fragment implements IResultVolley, ADTLTareo.ButtonCallback {

    // variables globales
    private boolean blnP_Seleccionado = false;

    // PREFERENCE ROOMS
    public Preference_PerfilUsuario userProfile;

    // voley
    VolleyService mVolleyService;
    ErrorToastyServer errorToastyServer;

    // ADAPTAER ETAPA
    ArrayAdapter<CharSequence> adaptadorTurno;

    // LIST
    List<Marcador> lsMarcador = new ArrayList<>();

    /*GUI*/
    ImageButton btnCamaraTareo;
    ImageButton btnBuscar;
    EditText edDniPersona;
    EditText edFechaTareo;
    EditText edHoraTareo;
    ListView lvEmpleado;
    Spinner spTurno;
    LinearLayout lyDatos;
    TextView tvTareoRegistro;
    TextView tvTareoSede;

    Button btnRegistrarTareo;

    // EDIT
    EditTexDialogDate eddFechaTareo;
    EditTextDialogTime eddHoraRegistro;

    // LISTAS
    List<Tareo> lsTareo = new ArrayList<>();

    // ADAPTERS
    ADTLTareo adaptadorListaEmpleado;

    // persona
    Tareo tareo;

    ProgressDialog dlgCargando;

    float objL_HightList;

    // TITULAR TOGGLE
    LabeledSwitch tglModoRegistro;

    // GLOBAL
    String strP_ModoCamara = "D";



    public FGRRegistrarTareo() {
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
        View view = inflater.inflate(R.layout.fgt_registrar_tareo, container, false);

        // Cambio de titulo
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Registrar tareo");

        // TAMAÑO
        objL_HightList = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 65, getResources().getDisplayMetrics());

        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN | WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        // CAMBIO
        tglModoRegistro = view.findViewById(R.id.tglModoRegistro);
        tglModoRegistro.setColorOn(Color.parseColor("#2ECC71"));
        tglModoRegistro.setColorOff(Color.parseColor("#ffffff"));
        tglModoRegistro.setLabelOff("SIMPLE");
        tglModoRegistro.setLabelOn("DETALLADO");
        tglModoRegistro.setOn(false);

        tglModoRegistro.setOnToggledListener(new OnToggledListener() {
            @Override
            public void onSwitched(ToggleableView toggleableView, boolean isOn) {
                if (!isOn) {
                    strP_ModoCamara = "S";
                } else {
                    strP_ModoCamara = "D";
                }
            }
        });

        // DIALOGO DE CARGA
        dlgCargando = new ProgressDialog(getContext());
        dlgCargando.setProgressStyle(ProgressDialog.STYLE_SPINNER);

        // ASIGN
        btnCamaraTareo = view.findViewById(R.id.btnCamaraTareo);
        btnBuscar = view.findViewById(R.id.btnBuscar);
        edDniPersona = view.findViewById(R.id.edDniPersona);

        edFechaTareo = view.findViewById(R.id.edFechaTareo);
        edHoraTareo = view.findViewById(R.id.edHoraTareo);
        eddHoraRegistro = new EditTextDialogTime(getContext(),edHoraTareo);
        eddFechaTareo = new EditTexDialogDate(getContext(), edFechaTareo);

        // DATOS
        tvTareoRegistro = view.findViewById(R.id.tvTareoRegistro);
        tvTareoSede = view.findViewById(R.id.tvTareoSede);
        lyDatos = view.findViewById(R.id.lyDatos);

        spTurno = view.findViewById(R.id.spTurno);

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String time = df.format(new Date());
        edFechaTareo.setText(time);

        // instanciado el room
        userProfile = Preference_PerfilUsuario.getInstance(getActivity());


        Calendar datetime = Calendar.getInstance();
        int houra = datetime.get(Calendar.HOUR_OF_DAY);
        int minute = datetime.get(Calendar.MINUTE);
        String am_pm = "";
        if (datetime.get(Calendar.AM_PM) == Calendar.AM)
            am_pm += " AM";
        else if (datetime.get(Calendar.AM_PM) == Calendar.PM)
            am_pm += " PM";
        edHoraTareo.setText(String.format("%02d", houra) + ":" + String.format("%02d", minute) + am_pm);

        // button
        btnRegistrarTareo = view.findViewById(R.id.btnRegistrarTareo);

        // Persona
        tareo = new Tareo();

        // ETAPA SPINNER
        adaptadorTurno = new ArrayAdapter(getActivity(), R.layout.spinner_item_opt, lsMarcador);
        spTurno.setAdapter(adaptadorTurno);

        btnCamaraTareo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                escanear();
            }
        });

        /// ERROR SERVER
        errorToastyServer = new ErrorToastyServer(getActivity());

        // INTANCE SERVICE
        mVolleyService = new VolleyService(this, getActivity());

        // LISTVIEW
        lvEmpleado = view.findViewById(R.id.lvPersona);

        // ADAPTERS
        adaptadorListaEmpleado = new ADTLTareo(getActivity(), lsTareo);
        adaptadorListaEmpleado.setButtonCallback(this);
        lvEmpleado.setAdapter(adaptadorListaEmpleado);

        // EDITPERSONA DNI
        edDniPersona.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                Utilidades.clearListObject(lsTareo);
                adaptadorListaEmpleado.notifyDataSetChanged();
                blnP_Seleccionado = false;
                lyDatos.setVisibility(View.GONE);
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
                mtd_VisibleDatos(false);
                Utilidades.clearListObject(lsTareo);
                blnP_Seleccionado = false;
                //lyDatos.setVisibility(View.GONE);
                if(edDniPersona.getText().length()>4){
                    mVolleyService.mtd_GetArrayVolley("EMPLEADO_TAREO", String.format(
                            UrlServer.URL_SERVER + "empleado/tareo/" + edDniPersona.getText().toString()+"/"+userProfile.getId_sede()
                    ));
                    System.out.println("DNI: "+edDniPersona.getText().toString()+" SEDE: "+userProfile.getId_sede());
                    dlgCargando.setTitle("Buscando empleado");
                    dlgCargando.setMessage("Porfavor espere...");
                    dlgCargando.setIndeterminate(true);
                    dlgCargando.setCanceledOnTouchOutside(false);
                    dlgCargando.show();
                }else{
                    Toasty.error(getContext(),"Ingrese un documento!",Toasty.LENGTH_SHORT).show();
                }
            }
        });

        mVolleyService.mtd_GetArrayVolley("MARCADOR", String.format(UrlServer.URL_SERVER + "marcador/1"));

        btnRegistrarTareo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(lsTareo.size()>0){
                    Tareo tareo = lsTareo.get(0);
                    if(tareo.getId_tareo() == 0){
                        dlgCargando.setTitle("Registrando tareo");
                        dlgCargando.setMessage("Por favor espere...");
                        dlgCargando.setIndeterminate(true);
                        dlgCargando.setCanceledOnTouchOutside(false);
                        dlgCargando.show();
                        mtd_RegistrarTareo();
                    }else{
                        dlgCargando.setTitle("Cerrando tareo");
                        dlgCargando.setMessage("Por favor espere...");
                        dlgCargando.setIndeterminate(true);
                        dlgCargando.setCanceledOnTouchOutside(false);
                        dlgCargando.show();
                        mtd_CerrarTareo();
                    }
                }else{
                    Toasty.info(getContext(),"Seleccione una persona!",Toasty.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }

    // REGISTRAR TAREO
    public void mtd_RegistrarTareo(){
        /*DATOS PERSONALES*/
        if(!blnP_Seleccionado){
            Toasty.error(getActivity(),"Seleccione a una persona!",Toasty.LENGTH_LONG).show();
            dlgCargando.dismiss();
            return;
        }

        if(edHoraTareo.getText().toString().isEmpty()){
            Toasty.error(getActivity(),"Ingrese una hora!",Toasty.LENGTH_LONG).show();
            dlgCargando.dismiss();
            return;
        }

        if(edFechaTareo.getText().toString().isEmpty()){
            Toasty.error(getActivity(),"Ingrese una fecha!",Toasty.LENGTH_LONG).show();
            dlgCargando.dismiss();
            return;
        }
        /*EMPLEADO*/
        String strL_Nombres = tareo.getEmpleado().getPersona().getDatos();
        int intL_IdPersona = tareo.getEmpleado().getPersona().getId_persona();
        String strL_TpDocumento = tareo.getEmpleado().getPersona().getId_tpdocumento();
        int  intL_Nacionalidad = tareo.getEmpleado().getPersona().getId_nacionalidad();
        int intL_Sede = tareo.getEmpleado().getSede().getId_sede();
        int intL_Cargo = tareo.getEmpleado().getCargo().getId_cargo();
        int intL_IdSueldo = tareo.getId_sueldo();
        int intL_SedeEm = tareo.getId_sede_em();
        /* SUPERVISOR */
        int intL_Usuario = userProfile.getUsuarioInfo().getId_usuario();
        String strL_userCreacion = userProfile.getUsuarioInfo().getUs_usuario();

        /*TAREO*/
        String strL_Turno = String.valueOf(((Marcador) spTurno.getSelectedItem()).getId_marcador());
        String strL_Fecha = edFechaTareo.getText().toString();
        String strL_Hora = edHoraTareo.getText().toString();
        strL_Hora = strL_Hora.substring(0, strL_Hora.length() - 3)+":00";
        strL_Hora = strL_Hora.replaceAll("\\s+", "");
        /* OBKJECT */
        Map<String, Object> dataPost = new HashMap<>();
        dataPost.put("id_marcador",  strL_Turno);
        dataPost.put("id_persona",  intL_IdPersona);
        dataPost.put("id_tpdocumento",  strL_TpDocumento);
        dataPost.put("id_nacionalidad", intL_Nacionalidad);
        dataPost.put("id_cargo",    intL_Cargo);
        dataPost.put("id_sede", intL_Sede);
        dataPost.put("id_sueldo", intL_IdSueldo);
        dataPost.put("id_sede_em", intL_SedeEm);
        dataPost.put("userCreacion", strL_userCreacion);

        /*  Estados
         *      1 Activo
         *      0 Cancelado
         * */
        int intL_Estado = 1;
        /*  Etapa
         *      0 Registrado
         *      1 Cerrado
         * */
        int intL_Etapa = 0;

        // tareo
        dataPost.put("ta_estado",  intL_Estado);
        dataPost.put("ta_etapa",  intL_Etapa);
        dataPost.put("ta_fecha_r", strL_Fecha+" "+strL_Hora);
        dataPost.put("ta_fecha_c", strL_Fecha+" "+strL_Hora);
        dataPost.put("ta_hora_r", strL_Hora);
        dataPost.put("ta_hora_c", strL_Hora);
        dataPost.put("ta_remunerado", 1);

        // usuario
        dataPost.put("ta_usuario", intL_Usuario);

        JSONObject json =  new JSONObject(dataPost);
        System.out.println(json);
        mVolleyService.mtd_PostObjectVolley("REGISTRO_TAREO", String.format(UrlServer.URL_SERVER + "tareo/registro"),json);
     }

    // CERRAR TAREO
    public void mtd_CerrarTareo(){
        /*DATOS PERSONALES*/
        if(!blnP_Seleccionado){
            Toasty.error(getActivity(),"Seleccione a una persona!",Toasty.LENGTH_LONG).show();
            dlgCargando.dismiss();
            return;
        }

        if(edHoraTareo.getText().toString().isEmpty()){
            Toasty.error(getActivity(),"Ingrese una hora!",Toasty.LENGTH_LONG).show();
            dlgCargando.dismiss();
            return;
        }

        if(edFechaTareo.getText().toString().isEmpty()){
            Toasty.error(getActivity(),"Ingrese una fecha!",Toasty.LENGTH_LONG).show();
            dlgCargando.dismiss();
            return;
        }

        /*DATOS TAREO*/
        String strL_Fecha = edFechaTareo.getText().toString();
        String strL_Hora = edHoraTareo.getText().toString();
        strL_Hora = strL_Hora.substring(0, strL_Hora.length() - 3)+":00";
        strL_Hora = strL_Hora.replaceAll("\\s+", "");
        /*TAREO*/
        int intL_IdTareo = tareo.getId_tareo();
        int intL_IdSueldo = tareo.getId_sueldo();
        /*  Estados
         *      1 Activo
         *      0 Cancelado
         * */
        int intL_Estado = 1;
        /*  Etapa
         *      0 Registrado
         *      1 Cerrado
         * */
        int intL_Etapa = 1;

        /* OBKJECT */
        Map<String, Object> dataPost = new HashMap<>();
        dataPost.put("id_tareo",  intL_IdTareo);
        dataPost.put("ta_estado",  intL_Estado);
        dataPost.put("ta_etapa",  intL_Etapa);
        dataPost.put("ta_fecha_c", strL_Fecha +" "+strL_Hora);
        dataPost.put("ta_hora_c", strL_Hora);
        dataPost.put("id_sueldo", intL_IdSueldo);
        JSONObject json =  new JSONObject(dataPost);
        System.out.println(json);
        mVolleyService.mtd_PostObjectVolley("CERRAR_TAREO", String.format(UrlServer.URL_SERVER + "tareo/cierre"),json);
    }

    private static final int CODIGO_INTENT = 2;
    private void escanear() {

        if(edFechaTareo.getText().toString().isEmpty()){
            Toasty.info(getContext(),"Ingrese una fecha valida!",Toasty.LENGTH_LONG).show();
            return;
        }

        if(edHoraTareo.getText().toString().isEmpty()){
            Toasty.info(getContext(),"Ingrese una hora valida!",Toasty.LENGTH_LONG).show();
            return;
        }


        String strL_Turno = String.valueOf(((Marcador) spTurno.getSelectedItem()).getId_marcador());
        String strL_TurnoDatos = String.valueOf(((Marcador) spTurno.getSelectedItem()).getDatos());
        String strL_Hora = edHoraTareo.getText().toString();
        String strL_Fecha = edFechaTareo.getText().toString();

        strL_Hora = strL_Hora.substring(0, strL_Hora.length() - 3)+":00";
        strL_Hora = strL_Hora.replaceAll("\\s+", "");
        strL_Fecha = strL_Fecha+" "+strL_Hora;

        Intent i = new Intent(getActivity(), ACTScannerTareo.class);
        i.putExtra("tareo_modo",   strP_ModoCamara);
        i.putExtra("tareo_fecha",   strL_Fecha);
        i.putExtra("tareo_hora",    strL_Hora);
        i.putExtra("tareo_turno",   strL_Turno);
        i.putExtra("tareo_turnoDatos",   strL_TurnoDatos);
        startActivityForResult(i, CODIGO_INTENT);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == CODIGO_INTENT) {
            if (resultCode == Activity.RESULT_OK) {
                if (data != null) {
                    String codigo = data.getStringExtra("codigo");
                    edDniPersona.setText(codigo);
                }
            }
        }
    }

    @Override
    public void notifySuccessOject(String requestType, JSONObject response) {
        switch (requestType) {
            case "REGISTRO_TAREO":
                dlgCargando.dismiss();
                mtd_ClearForm();
                Toasty.success(getActivity(), "Tareo registrado correctamente!", Toasty.LENGTH_LONG).show();
                break;
            case "CERRAR_TAREO":
                dlgCargando.dismiss();
                mtd_ClearForm();
                Toasty.success(getActivity(), "Tareo cerrado correctamente!", Toasty.LENGTH_LONG).show();
                break;
        }
    }

    @Override
    public void notifySuccessArray(String requestType, JSONArray response) {
        switch (requestType) {
            case "EMPLEADO_TAREO":
                dlgCargando.dismiss();
                mtd_VisibleDatos(false);
                System.out.println(response.toString());
                TareoDAO a = new TareoDAO();
                lsTareo.addAll(a.fn_RegistroTareoEmpleado(response));
                adaptadorListaEmpleado.notifyDataSetChanged();
                if (lsTareo.size() == 1) {
                    lvEmpleado.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, (int)objL_HightList));
                } else if (lsTareo.size() <= 0) {
                    blnP_Seleccionado = false;
                    Utilidades.clearListObject(lsTareo);
                    lvEmpleado.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0));
                    Toasty.info(getContext(),"No se encontraron resultados!",Toasty.LENGTH_SHORT).show();
                    System.out.println("ENTRO2");
                } else {
                    lvEmpleado.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 300));
                    System.out.println("ENTRO3");
                }
                break;
            case "MARCADOR":
                lsMarcador.addAll(new MarcadorDAO().fn_Marcador(response));
                adaptadorTurno.notifyDataSetChanged();
                break;
        }
    }

    @Override
    public void notifyError(String requestType, VolleyError error) {
        error.printStackTrace();
        switch (requestType) {
            case "EMPLEADO_TAREO":
                    errorToastyServer.showErrorServer(error);
                    dlgCargando.dismiss();
                break;
            case "REGISTRO_TAREO":
                    errorToastyServer.showErrorServer(error);
                    dlgCargando.dismiss();
                break;

            case "CERRAR_TAREO":
                errorToastyServer.showErrorServer(error);
                dlgCargando.dismiss();
                break;
        }
    }

    @Override
    public void onClickButton(View v, int position) {
        tareo = lsTareo.get(position);
        edDniPersona.setText(tareo.getEmpleado().getPersona().getPer_documento());

        if(!tareo.getTareo().isEmpty()){
            mtd_VisibleDatos(true);
            tvTareoRegistro.setText(tareo.getTareo());
            tvTareoSede.setText(tareo.getTareoSede());
        }

        Toasty.success(getActivity(), "Seleccionado: " + tareo.getEmpleado().getPersona().getDatos(), Toasty.LENGTH_SHORT).show();
        Utilidades.clearListObject(lsTareo);
        lsTareo.add(tareo);

        lvEmpleado.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, (int)objL_HightList));

        blnP_Seleccionado = true;
        adaptadorListaEmpleado.notifyDataSetChanged();
    }

    public void mtd_VisibleDatos(boolean isVisible){
        if(isVisible){
            lyDatos.setVisibility(View.VISIBLE);
        }else{
            lyDatos.setVisibility(View.GONE);
        }
    }

    public void mtd_ClearForm(){
        Calendar datetime = Calendar.getInstance();
        int houra = datetime.get(Calendar.HOUR_OF_DAY);
        int minute = datetime.get(Calendar.MINUTE);
        String am_pm = "";
        if (datetime.get(Calendar.AM_PM) == Calendar.AM)
            am_pm += " AM";
        else if (datetime.get(Calendar.AM_PM) == Calendar.PM)
            am_pm += " PM";
        //edHoraTareo.setText(String.format("%02d", houra) + ":" + String.format("%02d", minute) + am_pm);
        //edFechaTareo.setText(Utilidades.fn_GetDate());
        spTurno.setSelection(0);
        edDniPersona.setText("");
        Utilidades.clearListObject(lsTareo);
        blnP_Seleccionado = false;
        mtd_VisibleDatos(false);
        lvEmpleado.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0));
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
    }

}