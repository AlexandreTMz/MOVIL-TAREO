package com.example.app_tareos.GUI.SUPERVISOR;

import android.app.Activity;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;

import com.android.volley.VolleyError;
import com.example.app_tareos.ADAPTADORES.ADTLEmpleado;
import com.example.app_tareos.ADAPTADORES.ADTLTareo;
import com.example.app_tareos.GUI.PUBLICO.ActivityEscanear;
import com.example.app_tareos.CONFIG.UrlServer;
import com.example.app_tareos.DAO.TareoDAO;
import com.example.app_tareos.INTERFACE.IResultVolley;
import com.example.app_tareos.LIBS.EditTexDialogDate;
import com.example.app_tareos.LIBS.ErrorToastyServer;
import com.example.app_tareos.LIBS.VolleyService;
import com.example.app_tareos.MODEL.Tareo;
import com.example.app_tareos.R;
import com.example.app_tareos.ROOMS.Preference_PerfilUsuario;
import com.example.app_tareos.UTILIDADES.Utilidades;

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


public class FGRCerrarTareo extends Fragment implements IResultVolley, ADTLEmpleado.ButtonCallback {
    // variables globales
    private boolean blnP_Seleccionado = false;

    // PREFERENCE ROOMS
    public Preference_PerfilUsuario userProfile;

    private static final int PERMISSION_REQUEST_CODE = 200;
    private final String TAG = "Debug_MainActivity";
    // voley
    VolleyService mVolleyService;
    ErrorToastyServer errorToastyServer;

    /*GUI*/
    ImageButton btnCamaraTareo;
    EditText edDniPersona;
    EditText edFechaTareo;
    EditText edHoraTareo;
    ListView lvEmpleado;
    TextView tvEmpleado;
    TextView tvTareoRegistro;
    LinearLayout lyDatos;


    Button btnCerrarTareo;

    // EDIT
    EditTexDialogDate eddFechaTareo;
    // LISTAS
    List<Tareo> lsTareo = new ArrayList<>();

    // ADAPTERS
    ADTLTareo adaptadorListaTareo;

    // persona
    Tareo tareo;

    // dialog
    int hour = -1, min = -1;

    public FGRCerrarTareo() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fgt_cerrar_tareo, container, false);

        // ASIGN
        btnCamaraTareo = view.findViewById(R.id.btnCamaraTareo);
        edDniPersona = view.findViewById(R.id.edDniPersona);

        edFechaTareo = view.findViewById(R.id.edFechaTareo);
        edHoraTareo = view.findViewById(R.id.edHoraTareo);
        eddFechaTareo = new EditTexDialogDate(getContext(), edFechaTareo);

        tvEmpleado = view.findViewById(R.id.tvEmpleado);
        tvTareoRegistro = view.findViewById(R.id.tvTareoRegistro);

        lyDatos = view.findViewById(R.id.lyDatos);


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
        btnCerrarTareo = view.findViewById(R.id.btnCerrarTareo);


        // Persona
        tareo = new Tareo();

        //checkCameraPermission();

        btnCamaraTareo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toasty.success(getContext(), "Press", Toasty.LENGTH_LONG).show();
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
        adaptadorListaTareo = new ADTLTareo(getActivity(), lsTareo);
        //adaptadorListaTareo.setButtonCallback(this);
        lvEmpleado.setAdapter(adaptadorListaTareo);

        // EDITPERSONA DNI
        edDniPersona.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                // When user changed the Text
                System.out.println("TEXT: " + cs.toString());
                if (cs.length() >= 4) {
                    Utilidades.clearListObject(lsTareo);
                    mVolleyService.mtd_GetArrayVolley("EMPLEADO_TAREO_CIERRE", String.format(UrlServer.URL_SERVER + "tareo/cierre/" + cs.toString()));
                }
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

        edHoraTareo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (hour == -1 || min == -1) {
                    Calendar c = Calendar.getInstance();
                    hour = c.get(Calendar.HOUR);
                    min = c.get(Calendar.MINUTE);
                }
                showTimeDialog(view, hour, min);
            }
        });

        mVolleyService.mtd_GetArrayVolley("MARCADOR", String.format(UrlServer.URL_SERVER + "marcador"));

        btnCerrarTareo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mtd_CerrarTareo();
            }
        });

        return view;
    }

    public void mtd_CerrarTareo(){
        /*DATOS PERSONALES*/
        if(!blnP_Seleccionado){
            Toasty.error(getActivity(),"Seleccione a una persona!",Toasty.LENGTH_LONG).show();
            return;
        }

        /*TAREO*/
        int intL_IdTareo = tareo.getId_tareo();

        /*TAREO*/
        String strL_Fecha = edFechaTareo.getText().toString();
        String strL_Hora = edHoraTareo.getText().toString();
        strL_Hora = strL_Hora.substring(0, strL_Hora.length() - 3)+":00";
        /*  Estados
         *   0 registro
         *   1 cerrado
         *   3 cancelado
         * */
        int intL_Estado = 1;
        /* OBKJECT */
        Map<String, Object> dataPost = new HashMap<>();
        dataPost.put("id_tareo",  intL_IdTareo);
        dataPost.put("ta_estado",  intL_Estado);
        dataPost.put("ta_fecha_c", strL_Fecha+" "+strL_Hora);
        dataPost.put("ta_hora_c", strL_Hora);

        JSONObject json =  new JSONObject(dataPost);
        mVolleyService.mtd_PostObjectVolley("CERRAR_TAREO", String.format(UrlServer.URL_SERVER + "tareo/cierre"),json);
        System.out.println(json);
    }

    public void showTimeDialog(View v, int hour, int min) {

        TimePickerDialog timePickerDialog = new TimePickerDialog(
                getActivity(),
                android.R.style.Theme_Holo_Light_Dialog_NoActionBar,
                timeSetListener,
                hour,
                min,
                false);
        timePickerDialog.setTitle("Ingrese la hora de entrada!:");
        timePickerDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        timePickerDialog.show();

        //(new TimePickerDialog(getActivity(), android.R.style.Theme_Holo_Light_Dialog_NoActionBar,  timeSetListener, hour, min, false)).show();
    }

    public TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            String AM_PM;
            hour = hourOfDay;
            min = minute;
            if (hourOfDay < 12) {
                AM_PM = "AM";
            } else {
                AM_PM = "PM";
            }
            edHoraTareo.setText(String.format("%02d", hour) + " : " + String.format("%02d", min) + " " + AM_PM);
        }
    };

    private static final int CODIGO_INTENT = 2;

    private void escanear() {
        Intent i = new Intent(getActivity(), ActivityEscanear.class);
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
            case "CERRAR_TAREO":
                Toasty.success(getActivity(), "Tareo cerrado!", Toasty.LENGTH_LONG).show();
                break;
        }
    }

    @Override
    public void notifySuccessArray(String requestType, JSONArray response) {
        switch (requestType) {
            case "EMPLEADO_TAREO_CIERRE":
                lsTareo.addAll(new TareoDAO().fn_RegistroTareoEmpleado(response));
                adaptadorListaTareo.notifyDataSetChanged();
                if (lsTareo.size() == 1) {
                    lvEmpleado.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 100));
                } else if (lsTareo.size() <= 0) {
                    lvEmpleado.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0));
                } else {
                    lvEmpleado.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 300));
                }
                break;
        }
    }

    @Override
    public void notifyError(String requestType, VolleyError error) {
        error.printStackTrace();
        switch (requestType) {
            case "EMPLEADO_TAREO_CIERRE":
                errorToastyServer.showErrorServer(error);
                break;
            case "CERRAR_TAREO":
                errorToastyServer.showErrorServer(error);
                break;
        }
    }

    @Override
    public void onClickButton(View v, int position) {
        tareo = lsTareo.get(position);
        edDniPersona.setText(tareo.getEmpleado().getPersona().getPer_documento());

        lyDatos.setVisibility(View.VISIBLE);
        tvEmpleado.setText(tareo.getEmpleado().getCargo().getDatos().toUpperCase(Locale.ROOT));
        tvTareoRegistro.setText(tareo.getTareo());

        Toasty.success(getActivity(), "Seleccionado: " + tareo.getEmpleado().getPersona().getDatos(), Toasty.LENGTH_SHORT).show();
        Utilidades.clearListObject(lsTareo);
        blnP_Seleccionado = true;
        adaptadorListaTareo.notifyDataSetChanged();
    }


}