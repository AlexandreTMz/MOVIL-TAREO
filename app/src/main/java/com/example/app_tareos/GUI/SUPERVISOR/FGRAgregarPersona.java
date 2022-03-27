package com.example.app_tareos.GUI.SUPERVISOR;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.example.app_tareos.ADAPTADORES.ADTLPersonas;
import com.example.app_tareos.ADAPTADORES.ADTRPersonas;
import com.example.app_tareos.CONFIG.UrlServer;
import com.example.app_tareos.DAO.CargoDAO;
import com.example.app_tareos.DAO.DescansoDiasDAO;
import com.example.app_tareos.DAO.NacionalidadDAO;
import com.example.app_tareos.DAO.PersonaDAO;
import com.example.app_tareos.DAO.SedeDAO;
import com.example.app_tareos.DAO.SexoDAO;
import com.example.app_tareos.DAO.TipoDocumentoDAO;
import com.example.app_tareos.INTERFACE.IResultVolley;
import com.example.app_tareos.LIBS.EditTexDialogDate;
import com.example.app_tareos.LIBS.ErrorToastyServer;
import com.example.app_tareos.LIBS.VolleyService;
import com.example.app_tareos.MODEL.Cargo;
import com.example.app_tareos.MODEL.DescansoDias;
import com.example.app_tareos.MODEL.Nacionalidad;
import com.example.app_tareos.MODEL.Persona;
import com.example.app_tareos.MODEL.Sede;
import com.example.app_tareos.MODEL.Sexo;
import com.example.app_tareos.MODEL.TipoDocumento;
import com.example.app_tareos.MODEL.Usuario;
import com.example.app_tareos.R;
import com.example.app_tareos.ROOMS.Preference_PerfilUsuario;
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
import java.util.function.Predicate;
import java.util.stream.Collectors;

import es.dmoral.toasty.Toasty;
import com.example.app_tareos.UTILIDADES.Utilidades;

public class FGRAgregarPersona extends Fragment implements IResultVolley, ADTRPersonas.OnClicksListener {

    // variables globales
    private boolean blnP_Seleccionado = false;

    // voley
    VolleyService mVolleyService;
    ErrorToastyServer errorToastyServer;
    private String TAG = "MainActivity";

    // LIST ARRAY FOR PUT DATA FROM DAO
    List<TipoDocumento> lsTipoDocumento = new ArrayList<>();
    List<Nacionalidad> lsNacionalidad = new ArrayList<>();
    List<Cargo> lsCargo = new ArrayList<>();
    List<Sexo> lsSexo = new ArrayList<>();
    List<Persona> lsPersona = new ArrayList<>();
    List<Sede> lsSede = new ArrayList<>();

    // ADAPTAER ETAPA
    ArrayAdapter<CharSequence> adaptadorTipoDocumento;
    ArrayAdapter<CharSequence> adaptadorNacionalidad;
    ArrayAdapter<CharSequence> adaptadorCargo;
    ArrayAdapter<CharSequence> adaptadorSexo;
    ArrayAdapter<CharSequence> adaptadorSede;
    ArrayAdapter<CharSequence> adaptadorDescansoDias;

    // ADAPTERS
    //ADTLPersonas adaptadorListaPersona;
    ADTRPersonas objP_AdaptadorEmpleado;

    // GUI
    Spinner spTipoDocumento;
    Spinner spNacionalidad;
    Spinner spCargo;
    Spinner spSexo;
    Spinner spSede;
    Spinner spDescansoReferencial;

    //ListView lvPersona;
    RecyclerView rvwPersonasReg;

    // EDIT REGISTRO FORM
    EditText edNombres;
    EditText edApellidoPaterno;
    EditText edApellidoMaterno;
    EditText edNrDocumento;
    EditText edFechaNacimiento;
    EditText edDireccion;
    EditText edCelular;
    EditText edCorreo;
    EditText edFechaIngreso;
    EditText edDniPersonaSuplente;

    // LIB EDITTEXT DATE
    EditTexDialogDate eddFechaNacimiento;
    EditTexDialogDate eddFechaIngreso;

    // TITULAR TOGGLE
    LabeledSwitch tglTitular;

    // BUTTON
    Button btnRegistraEmpleado;
    ImageButton btnRegistrarSuplente,btnBuscar;

    // ES TITULAR?
    int isTitular = 1;

    // PREFERENCE ROOMS
    public Preference_PerfilUsuario userProfile;

    // GUI
    ProgressBar pgrDownload,pgrPerson;

    // GLOBAL INT
    int intL_Progresos = 4;

    // progress
    ProgressDialog pgdRegistrandoTitular;
    ProgressDialog pgdRegistrandoSuplente;

    // DIALOGO SUPLENTE
    Dialog dlgSuplente;

    float objL_HightList;
    float objL_HightListMax;

    public FGRAgregarPersona() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fgt_agregar_persona, container, false);

        // Cambio de titulo
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Agregar empleado");


        // TAMAÑO
        objL_HightList = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 105, getResources().getDisplayMetrics());
        objL_HightListMax = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 300, getResources().getDisplayMetrics());

        pgdRegistrandoTitular = new ProgressDialog(getContext());
        pgdRegistrandoSuplente = new ProgressDialog(getContext());
        pgdRegistrandoTitular.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pgdRegistrandoSuplente.setProgressStyle(ProgressDialog.STYLE_SPINNER);

        // PROGRESS BAR
        pgrDownload = view.findViewById(R.id.pgrDownload);
        pgrPerson= view.findViewById(R.id.pgrPerson);
        //pgrDownload.setProgressDrawable(getActivity().getDrawable(R.drawable.dowload_progress));

        // instanciado el room
        userProfile = Preference_PerfilUsuario.getInstance(getActivity());

        System.out.println("Sede:::"+userProfile.getId_sede());

        // TOGGLE
        tglTitular = view.findViewById(R.id.tglTitular);
        tglTitular.setColorOn(Color.parseColor("#2ECC71"));
        tglTitular.setColorOff(Color.parseColor("#ffffff"));
        tglTitular.setLabelOff("NO");
        tglTitular.setLabelOn("SI");

        // SPINER
        spTipoDocumento = view.findViewById(R.id.spTipoDocumento);
        spNacionalidad = view.findViewById(R.id.spNacionalidad);
        spCargo = view.findViewById(R.id.spCargo);

        spSexo = view.findViewById(R.id.spSexo);
        spSede = view.findViewById(R.id.spSede);
        spDescansoReferencial = view.findViewById(R.id.spDescansoReferencial);

        // LISTVIEW
        //lvPersona = view.findViewById(R.id.lvPersona);
        rvwPersonasReg = view.findViewById(R.id.rvwPersonasReg);

        // ADAPTERS
        /*adaptadorListaPersona = new ADTLPersonas(getActivity(), lsPersona);
        adaptadorListaPersona.setButtonCallback(this);
        lvPersona.setAdapter(adaptadorListaPersona);*/
        // ALERTAS RECYCLER VIEW
        rvwPersonasReg = view.findViewById(R.id.rvwPersonasReg);
        objP_AdaptadorEmpleado = new ADTRPersonas(getContext(),lsPersona);
        objP_AdaptadorEmpleado.setOnClicksListenerCustom(this);
        rvwPersonasReg.setAdapter(objP_AdaptadorEmpleado);
        rvwPersonasReg.setLayoutManager(new LinearLayoutManager(getContext()));


        // EDIT
        edDniPersonaSuplente = view.findViewById(R.id.edDniPersona);
        edFechaNacimiento = view.findViewById(R.id.edFechaNacimiento);
        eddFechaNacimiento = new EditTexDialogDate(getContext(), edFechaNacimiento);

        edFechaIngreso = view.findViewById(R.id.edFechaIngreso);
        eddFechaIngreso = new EditTexDialogDate(getContext(), edFechaIngreso);

        edNombres = view.findViewById(R.id.edNombres);
        edApellidoPaterno = view.findViewById(R.id.edApellidoPaterno);
        edApellidoMaterno = view.findViewById(R.id.edApellidoMaterno);
        edNrDocumento = view.findViewById(R.id.edNrDocumento);
        edDireccion = view.findViewById(R.id.edDireccion);
        edCelular = view.findViewById(R.id.edCelular);
        edCorreo = view.findViewById(R.id.edCorreo);

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String time = df.format(new Date());
        edFechaIngreso.setText(time);
        edFechaNacimiento.setText(time);

        // bBUTTON
        btnRegistraEmpleado = view.findViewById(R.id.btnRegistraEmpleado);
        btnRegistrarSuplente = view.findViewById(R.id.btnRegistrarSuplente);
        btnBuscar = view.findViewById(R.id.btnBuscar);

        // ETAPA SPINNER
        adaptadorTipoDocumento = new ArrayAdapter(getActivity(), R.layout.spinner_item_opt, lsTipoDocumento);
        spTipoDocumento.setAdapter(adaptadorTipoDocumento);

        adaptadorNacionalidad = new ArrayAdapter(getActivity(), R.layout.spinner_item_opt, lsNacionalidad);
        spNacionalidad.setAdapter(adaptadorNacionalidad);

        adaptadorCargo = new ArrayAdapter(getActivity(), R.layout.spinner_item_opt, lsCargo);
        spCargo.setAdapter(adaptadorCargo);

        adaptadorSexo = new ArrayAdapter(getActivity(), R.layout.spinner_item_opt, new SexoDAO().fn_Sexo());
        spSexo.setAdapter(adaptadorSexo);

        adaptadorSede = new ArrayAdapter(getActivity(), R.layout.spinner_item_opt, lsSede);
        spSede.setAdapter(adaptadorSede);

        adaptadorDescansoDias = new ArrayAdapter(getActivity(), R.layout.spinner_item_opt, new DescansoDiasDAO().fn_DescansoDias());
        spDescansoReferencial.setAdapter(adaptadorDescansoDias);

        /// ERROR SERVER
        errorToastyServer = new ErrorToastyServer(getActivity());

        // INTANCE SERVICE
        mVolleyService = new VolleyService(this, getActivity());

        // layaout
        LinearLayout linearLayoutSuplente = view.findViewById(R.id.lySuplente);

        tglTitular.setOn(true);

        tglTitular.setOnToggledListener(new OnToggledListener() {
            @Override
            public void onSwitched(ToggleableView toggleableView, boolean isOn) {
                if (!isOn) {
                    linearLayoutSuplente.setVisibility(View.VISIBLE);
                    isTitular = 0;
                } else {
                    linearLayoutSuplente.setVisibility(View.GONE);
                    isTitular = 1;
                }
            }
        });

        // EDITPERSONA DNI
        btnBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pgrPerson.setVisibility(View.VISIBLE);
                Utilidades.clearListObject(lsPersona);
                if (edDniPersonaSuplente.getText().length() >= 2) {
                    Utilidades.clearListObject(lsPersona);
                    mVolleyService.mtd_GetArrayVolley("PERSONAS", String.format(UrlServer.URL_SERVER + "persona/suplente/tareo/" + edDniPersonaSuplente.getText().toString()));
                }else{
                    pgrPerson.setVisibility(View.GONE);
                    blnP_Seleccionado = false;
                    Utilidades.clearListObject(lsPersona);
                    rvwPersonasReg.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0));
                }
            }
        });

        // CALL ALL DATA START
        mVolleyService.mtd_GetArrayVolley("TIPO_DOCUMENTO", String.format(UrlServer.URL_SERVER + "tipo_documento"));
        mVolleyService.mtd_GetArrayVolley("NACIONALIDAD", String.format(UrlServer.URL_SERVER + "nacionalidad"));
        mVolleyService.mtd_GetArrayVolley("CARGO", String.format(UrlServer.URL_SERVER + "cargo"));
        mVolleyService.mtd_GetArrayVolley("SEDE", String.format(UrlServer.URL_SERVER + "sede"));

        // bnutton

        btnRegistraEmpleado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mtd_crearObjeto();
            }
        });

        // AÑADIOR SUPLENTE

        btnRegistrarSuplente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog();
            }
        });


        return view;
    }

    public void mtd_OcultarProgress(){
        --intL_Progresos;
        if(intL_Progresos <= 0){
            pgrDownload.setVisibility(View.GONE);
        }
    }

    @Override
    public void notifySuccessOject(String requestType, JSONObject response) {
        switch (requestType){
            case "REGISTRO_EMPLEADO":
                pgdRegistrandoTitular.dismiss();
                mtd_ClearInputsTitular();
                Utilidades.clearListObject(lsPersona);
                objP_AdaptadorEmpleado.notifyDataSetChanged();
                rvwPersonasReg.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0));
                Toasty.success(getActivity(),"Registro completado!",Toasty.LENGTH_LONG).show();
                break;
            case "REGISTRO_SUPLENTE":
                pgdRegistrandoSuplente.dismiss();
                Toasty.success(getActivity(),"Persona registrada!",Toasty.LENGTH_LONG).show();
                if(dlgSuplente != null){
                    dlgSuplente.hide();
                }
                break;
        }
    }


    @Override
    public void notifySuccessArray(String requestType, JSONArray response) {
        Log.d(TAG, "SUCCESSO Volley requester " + requestType);
        Log.d(TAG, "SUCCESSO Volley JSON: " + response);
        switch (requestType) {
            case "TIPO_DOCUMENTO":
                lsTipoDocumento.addAll(new TipoDocumentoDAO().fn_TipoDocumento(response));
                adaptadorTipoDocumento.notifyDataSetChanged();
                mtd_OcultarProgress();
                break;
            case "NACIONALIDAD":
                lsNacionalidad.addAll(new NacionalidadDAO().fn_Nacionalidad(response));
                adaptadorNacionalidad.notifyDataSetChanged();
                mtd_OcultarProgress();
                break;
            case "CARGO":
                lsCargo.addAll(new CargoDAO().fn_Cargo(response));
                adaptadorCargo.notifyDataSetChanged();
                mtd_OcultarProgress();
                break;
            case "SEDE":
                lsSede.addAll(new SedeDAO().fn_Sede(response));
                Sede findSede = new Sede();
                for(Sede sede : lsSede){
                    if(userProfile.getId_sede() == sede.getId_sede()){
                        findSede = sede;
                    }
                    System.out.println("Sedes: "+sede.toString());
                }
                lsSede.clear();
                lsSede.add(findSede);
                adaptadorSede.notifyDataSetChanged();
                mtd_OcultarProgress();
                break;
            case "PERSONAS":
                pgrPerson.setVisibility(View.GONE);

                if(lsPersona.size()>0){
                    lsPersona.clear();
                }
                lsPersona.addAll(new PersonaDAO().fn_PersonaSuplente(response));
                objP_AdaptadorEmpleado.notifyDataSetChanged();

                if (lsPersona.size() == 1) {
                    rvwPersonasReg.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, (int)objL_HightList));
                } else if (lsPersona.size() <= 0) {
                    blnP_Seleccionado = false;
                    Utilidades.clearListObject(lsPersona);
                    rvwPersonasReg.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0));
                    Toasty.info(getContext(),"No se encontraron resultados!",Toasty.LENGTH_SHORT).show();
                } else {
                    rvwPersonasReg.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, (int)objL_HightListMax));
                }
                break;
        }
    }

    @Override
    public void notifyError(String requestType, VolleyError error) {
        error.printStackTrace();
        switch (requestType) {
            case "TIPO_DOCUMENTO":
                    errorToastyServer.showErrorServer(error);
                break;
            case "REGISTRO_EMPLEADO":
                    pgdRegistrandoTitular.dismiss();
                    errorToastyServer.showErrorServer(error);
                break;
            case "REGISTRO_SUPLENTE":
                    pgdRegistrandoSuplente.dismiss();
                    errorToastyServer.showErrorServer(error);
                break;
        }
    }

    public void mtd_crearObjeto() {

        pgdRegistrandoTitular.setTitle("Registrando persona");
        pgdRegistrandoTitular.setMessage("Por favor espere...");
        pgdRegistrandoTitular.setIndeterminate(true);
        pgdRegistrandoTitular.setCanceledOnTouchOutside(false);
        pgdRegistrandoTitular.show();

        boolean blnL_Validacion = false;

        if(isTitular != 1){
            if(!blnP_Seleccionado){
                Toasty.error(getActivity(),"Seleccione a una persona!",Toasty.LENGTH_LONG).show();
                blnL_Validacion = true;
            }
        }

        if(blnL_Validacion){
            pgdRegistrandoTitular.dismiss();
            return;
        }

        /*DATOS PERSONALES*/
        String strL_Nombres = edNombres.getText().toString();
        String strL_ApellidoPaterno = edApellidoPaterno.getText().toString();
        String strL_ApellidoMaterno = edApellidoMaterno.getText().toString();
        String strL_TipoDocumento = String.valueOf(((TipoDocumento) spTipoDocumento.getSelectedItem()).getId_tpdocumento());
        String strL_NrDocumento =  edNrDocumento.getText().toString();
        String strL_FechaNacimiento = edFechaNacimiento.getText().toString();
        String strL_Sexo = String.valueOf(((Sexo) spSexo.getSelectedItem()).getId_sexo());
        String strL_Nacionalidad = String.valueOf(((Nacionalidad) spNacionalidad.getSelectedItem()).getId_nacionalidad());
        String strL_Direccion = edDireccion.getText().toString();
        String strL_Celular = edCelular.getText().toString();
        String strL_Correo = edCorreo.getText().toString();
        String strL_DescansoReferencial = String.valueOf(((DescansoDias) spDescansoReferencial.getSelectedItem()).getId_dia());

        if(strL_NrDocumento.length()<=4){
            Toasty.error(getActivity(),"Ingrese un documento valido!",Toasty.LENGTH_LONG).show();
            blnL_Validacion = true;
        }

        if(blnL_Validacion){
            pgdRegistrandoTitular.dismiss();
            return;
        }

        /*
        *   0 = inactivo
        *   1 = falta aprovar
        *   2 = aprovado / registrado por su
        * */
        int intL_Estado = 1;
        int intL_Usuario = userProfile.getUsuarioInfo().getId_usuario();

        /*EMPLEADO*/
        String strL_Cargo =  String.valueOf(((Cargo) spCargo.getSelectedItem()).getId_cargo());
        String strL_FechaIngreso = edFechaIngreso.getText().toString();
        //String strL_Banco = String.valueOf(((Banco) spBanco.getSelectedItem()).getId_banco());
        String strL_Sede = String.valueOf(((Sede) spSede.getSelectedItem()).getId_sede());
        //String strL_TipoCuenta = String.valueOf(((TipoCuenta) spTipoCuenta.getSelectedItem()).getId_tpcuenta());
        //String strL_NroCuenta = edNroCuenta.getText().toString();
        //String strL_NroCuentaCi = edNroCuentaCi.getText().toString();
        String strL_DiaDescanso = "2100-01-01";


        try {
            System.out.println("Revisandoooooooo: "+Utilidades.fn_ValidarFechaIngreso(strL_FechaIngreso));
            System.out.println("Revisandoooooooo: "+Utilidades.fn_ValidarFechaIngreso(strL_FechaIngreso));
            System.out.println("Revisandoooooooo: "+strL_FechaIngreso);
            if(!Utilidades.fn_ValidarFechaIngreso(strL_FechaIngreso)){
                pgdRegistrandoTitular.dismiss();
                Toasty.info(getContext(),"La fecha ingresada debe ser menor o igual, que el fin del siguiente mes de la fecha actual.",Toasty.LENGTH_SHORT).show();
                return;
            }

        }catch (Exception e){
            pgdRegistrandoTitular.dismiss();
            return;
        }

        /*SUPLENTE*/
        String strL_Suplente = edDniPersonaSuplente.getText().toString();

        /*PAGO*/
        String strL_Tipo = String.valueOf(isTitular);

        Map<String, Object> data = new HashMap<String, Object>();
        data.put("tipo_documento", strL_TipoDocumento);
        data.put("nacionalidad", strL_Nacionalidad);
        data.put("nombres", strL_Nombres);
        data.put("apellido_paterno", strL_ApellidoPaterno);
        data.put("apellido_materno", strL_ApellidoMaterno);
        data.put("documento", strL_NrDocumento);
        data.put("fecha_nacimiento", strL_FechaNacimiento);
        data.put("celular", strL_Celular);
        data.put("correo", strL_Correo);
        data.put("titular", 1);
        data.put("sexo", strL_Sexo);
        data.put("direccion", strL_Direccion);
        data.put("fecha_ingreso", strL_FechaIngreso+" 00:00:00");
        data.put("estado", intL_Estado);
        data.put("usuario", intL_Usuario);


        // empleado
        Map<String, Object> empleado = new HashMap<>();
        empleado.put("cargo", strL_Cargo);
        empleado.put("sede", strL_Sede);
        empleado.put("descanso", strL_DiaDescanso);
        empleado.put("descansoReferencia", strL_DescansoReferencial);

        JSONArray arrayEmpleado = new JSONArray();
        arrayEmpleado.put(new JSONObject(empleado));
        data.put("empleado", arrayEmpleado);

        if(blnL_Validacion){
            pgdRegistrandoTitular.dismiss();
            return;
        }

        // pago
        Map<String, Object> pago = new HashMap<String, Object>();

        if(isTitular == 1){
            pago.put("tipo", strL_Tipo);
            //pago.put("banco", strL_Banco);
            //pago.put("tipo_cuenta", strL_TipoCuenta);
            //pago.put("cuenta", strL_NroCuenta);
            //pago.put("cuentaCi", strL_NroCuentaCi);
        }else{
            pago.put("tipo", strL_Tipo);
            pago.put("documento", strL_Suplente);
        }

        JSONArray arrayPago = new JSONArray();
        arrayPago.put(new JSONObject(pago));
        data.put("pago", arrayPago);
        // end pago

        JSONObject json =  new JSONObject(data);
        System.out.println(json);

        mVolleyService.mtd_PostObjectVolley("REGISTRO_EMPLEADO", String.format(UrlServer.URL_SERVER + "supervisor/empleado"),json);

        pgdRegistrandoTitular.setMessage("Por favor espere...");
        pgdRegistrandoTitular.setIndeterminate(true);
        pgdRegistrandoTitular.setCanceledOnTouchOutside(false);
        pgdRegistrandoTitular.show();
    }

    public void showDialog(){
        dlgSuplente = new Dialog(getActivity());
        dlgSuplente.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dlgSuplente.setCancelable(false);
        dlgSuplente.setContentView(R.layout.dg_registrar_suplente);

        /*TAMAÑO*/
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        int width = metrics.widthPixels;
        int height = metrics.heightPixels;
        dlgSuplente.getWindow().setLayout(width, (4 * height)/5);
        /*END*/

        // START VIEW
        mtd_InitViewDialog(dlgSuplente);


        ImageView dialogButton = dlgSuplente.findViewById(R.id.imgCloseDi);
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dlgSuplente.dismiss();
                pgdRegistrandoSuplente.dismiss();
            }
        });
        dlgSuplente.show();
    }

    public void mtd_InitViewDialog(Dialog dialog){
        // GUI
        Spinner spTipoDocumentoSS;
        Spinner spNacionalidadSS;
        Spinner spSexoSS;

        EditText edNombresSS;
        EditText edApellidoPaternoSS;
        EditText edApellidoMaternoSS;
        EditText edNrDocumentoSS;
        EditText edFechaNacimientoSS;
        EditText edDireccionSS;
        EditText edCelularSS;
        EditText edCorreoSS;

        Button btnRegistraSustitutoSS;

        // EDIT
        edNombresSS = dialog.findViewById(R.id.edNombresSS);
        edApellidoPaternoSS = dialog.findViewById(R.id.edApellidoPaternoSS);
        edApellidoMaternoSS = dialog.findViewById(R.id.edApellidoMaternoSS);
        edNrDocumentoSS = dialog.findViewById(R.id.edNrDocumentoSS);
        edFechaNacimientoSS = dialog.findViewById(R.id.edFechaNacimientoSS);
        edDireccionSS = dialog.findViewById(R.id.edDireccionSS);
        edCelularSS = dialog.findViewById(R.id.edCelularSS);
        edCorreoSS = dialog.findViewById(R.id.edCorreoSS);

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String time = df.format(new Date());
        edFechaNacimientoSS.setText(time);
        new EditTexDialogDate(getContext(), edFechaNacimientoSS);

        // SPINER
        spTipoDocumentoSS = dialog.findViewById(R.id.spTipoDocumentoSS);
        spNacionalidadSS = dialog.findViewById(R.id.spNacionalidadSS);
        spSexoSS = dialog.findViewById(R.id.spSexoSS);

        btnRegistraSustitutoSS = dialog.findViewById(R.id.btnRegistraSustitutoSS);

        // ETAPA SPINNER
        spTipoDocumentoSS.setAdapter(adaptadorTipoDocumento);
        spNacionalidadSS.setAdapter(adaptadorNacionalidad);
        spSexoSS.setAdapter(adaptadorSexo);


        btnRegistraSustitutoSS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*DATOS PERSONALES*/
                String strL_Nombres = edNombresSS.getText().toString();
                String strL_ApellidoPaterno = edApellidoPaternoSS.getText().toString();
                String strL_ApellidoMaterno = edApellidoMaternoSS.getText().toString();
                String strL_TipoDocumento = String.valueOf(((TipoDocumento) spTipoDocumentoSS.getSelectedItem()).getId_tpdocumento());
                String strL_NrDocumento =  edNrDocumentoSS.getText().toString();
                String strL_FechaNacimiento = edFechaNacimientoSS.getText().toString();
                String strL_Sexo = String.valueOf(((Sexo) spSexoSS.getSelectedItem()).getId_sexo());
                String strL_Nacionalidad = String.valueOf(((Nacionalidad) spNacionalidadSS.getSelectedItem()).getId_nacionalidad());
                String strL_Direccion = edDireccionSS.getText().toString();
                String strL_Celular = edCelularSS.getText().toString();
                String strL_Correo = edCorreoSS.getText().toString();

                // pago
                //String strL_Banco = String.valueOf(((Banco) spBancoSS.getSelectedItem()).getId_banco());
                //String strL_TipoCuenta = String.valueOf(((TipoCuenta) spTipoCuentaSS.getSelectedItem()).getId_tpcuenta());
                //String strL_NroCuenta = edNroCuentaSS.getText().toString();
                //String strL_NroCuentaCi = edNroCuentaCiSS.getText().toString();

                // JSON
                Map<String, Object> data = new HashMap<>();
                data.put("tipo_documento", strL_TipoDocumento);
                data.put("nacionalidad", strL_Nacionalidad);
                data.put("nombres", strL_Nombres);
                data.put("apellido_paterno", strL_ApellidoPaterno);
                data.put("apellido_materno", strL_ApellidoMaterno);
                data.put("documento", strL_NrDocumento);
                data.put("fecha_nacimiento", strL_FechaNacimiento);
                data.put("celular", strL_Celular);
                data.put("correo", strL_Correo);
                data.put("titular", 1);
                data.put("sexo", strL_Sexo);
                data.put("direccion", strL_Direccion);
                /*
                 *   0 = inactivo
                 *   1 = falta aprovar
                 *   2 = aprovado / registrado por su
                 * */
                int intL_Estado = 2;
                int intL_Usuario = userProfile.getUsuarioInfo().getId_usuario();
                data.put("estado", intL_Estado);
                data.put("usuario", intL_Usuario);
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                String time = df.format(new Date());
                data.put("fecha_ingreso", time);

                // pago
                Map<String, Object> pago = new HashMap<>();
                pago.put("tipo", 1);
                //pago.put("banco", strL_Banco);
                //pago.put("tipo_cuenta", strL_TipoCuenta);
                //pago.put("cuenta", strL_NroCuenta);
                //pago.put("cuentaCi", strL_NroCuentaCi);

                JSONArray arrayPago = new JSONArray();
                arrayPago.put(new JSONObject(pago));
                data.put("pago", arrayPago);
                // end pago
                JSONObject json =  new JSONObject(data);
                System.out.println(json);

                boolean blnL_Validacion = false;

                if(strL_NrDocumento.length()<=4){
                    Toasty.error(getActivity(),"Ingrese un documento valido!",Toasty.LENGTH_LONG).show();
                    blnL_Validacion = true;
                }

                if(blnL_Validacion){
                    return;
                }

                mVolleyService.mtd_PostObjectVolley("REGISTRO_SUPLENTE", String.format(UrlServer.URL_SERVER + "persona/suplente"),json);
                pgdRegistrandoSuplente.setTitle("Registrando suplente");
                pgdRegistrandoSuplente.setMessage("Por favor espere...");
                pgdRegistrandoSuplente.setIndeterminate(true);
                pgdRegistrandoSuplente.setCanceledOnTouchOutside(false);
                pgdRegistrandoSuplente.show();

            }
        });


    }


    public void mtd_ClearInputsTitular(){
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String time = df.format(new Date());
        edNombres.setText("");
        edApellidoPaterno.setText("");
        edApellidoMaterno.setText("");
        edNrDocumento.setText("");
        edFechaNacimiento.setText(time);
        edDireccion.setText("");
        edCelular.setText("");
        edCorreo.setText("");
        edFechaIngreso.setText(time);
        //edNroCuenta.setText("");
        edDniPersonaSuplente.setText("");
        //edNroCuentaCi.setText("");
        spSexo.setSelection(0);
        spNacionalidad.setSelection(0);
        spCargo.setSelection(0);
        spSede.setSelection(0);
        //spBanco.setSelection(0);
        //spTipoCuenta.setSelection(0);
        spTipoDocumento.setSelection(0);
        spDescansoReferencial.setSelection(0);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
    }

    @Override
    public void onClickSeleccionar(View v, int position) {
        Persona persona = lsPersona.get(position);
        edDniPersonaSuplente.setText(persona.getPer_documento());
        Toasty.success(getActivity(), "Seleccionado: " + persona.getDatos(), Toasty.LENGTH_SHORT).show();
        Utilidades.clearListObject(lsPersona);
        lsPersona.add(persona);
        pgrPerson.setVisibility(View.GONE);
        blnP_Seleccionado = true;
        objP_AdaptadorEmpleado.notifyDataSetChanged();
    }


}