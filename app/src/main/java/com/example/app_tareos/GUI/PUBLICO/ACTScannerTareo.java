package com.example.app_tareos.GUI.PUBLICO;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.AssetManager;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.example.app_tareos.BuildConfig;
import com.example.app_tareos.CONFIG.UrlServer;
import com.example.app_tareos.DAO.MarcadorDAO;
import com.example.app_tareos.DAO.TareoDAO;
import com.example.app_tareos.INTERFACE.IResultVolley;
import com.example.app_tareos.LIBS.ErrorToastyServer;
import com.example.app_tareos.LIBS.VolleyService;
import com.example.app_tareos.MODEL.Marcador;
import com.example.app_tareos.MODEL.Tareo;
import com.example.app_tareos.R;
import com.example.app_tareos.ROOMS.Preference_PerfilUsuario;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.Result;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import es.dmoral.toasty.Toasty;
import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class ACTScannerTareo extends AppCompatActivity implements ZXingScannerView.ResultHandler, IResultVolley {

    private ZXingScannerView escanerZXing;

    // DIALOGO SUPLENTE
    Dialog dlgTareo;
    //FloatingActionButton fbFlash;

    // voley
    VolleyService mVolleyService;
    ErrorToastyServer errorToastyServer;
    private String TAG = "MainActivity";

    // LS
    List<Tareo> lsTareo = new ArrayList<>();

    // LIST
    List<Marcador> lsMarcador = new ArrayList<>();

    // ADAPTAER ETAPA
    ArrayAdapter<CharSequence> adaptadorTurno;

    Spinner spTurno;

    // PREFERENCE ROOMS
    public Preference_PerfilUsuario userProfile;

    // PARAMETROS
    String StrP_fecha;
    String StrP_hora;
    String StrP_turno;
    String StrP_turnoDatos;
    String StrP_modo;
    String strPv_code;

    // DIALOGO
    ProgressDialog dlgCargando;

    // BOTONES
    Button btnSalirApp;
    Button btnFlash;

    // TEXVIEW
    TextView tvCodigoResultante;

    @Override
    public void onCreate(Bundle state) {
        super.onCreate(state);
        setContentView(R.layout.act_scanner_tareo);
        //getSupportActionBar().hide();

        escanerZXing = new ZXingScannerView(this);
        RelativeLayout rl = (RelativeLayout) findViewById(R.id.relative_scan_take_single);
        rl.addView(escanerZXing);
        escanerZXing.setResultHandler(this);
        escanerZXing.startCamera();
        escanerZXing.setSoundEffectsEnabled(true);
        escanerZXing.setAutoFocus(true);


        List<BarcodeFormat> myformat = new ArrayList<>();
        myformat.add(BarcodeFormat.CODE_128);
        myformat.add(BarcodeFormat.CODABAR);
        myformat.add(BarcodeFormat.CODE_39);
        escanerZXing.setFormats(myformat);

        //escanerZXing = findViewById(R.id.zxscan);
        //fbFlash = findViewById(R.id.fbFlash);

        btnSalirApp = findViewById(R.id.btnSalirApp);
        btnFlash = findViewById(R.id.btnFlash);

        tvCodigoResultante = findViewById(R.id.tvCodigoResultante);

        /// ERROR SERVER
        errorToastyServer = new ErrorToastyServer(this);

        // instanciado el room
        userProfile = Preference_PerfilUsuario.getInstance(this);

        // INTANCE SERVICE
        mVolleyService = new VolleyService(this, this);

        // DIALOGO DE CARGA
        dlgCargando = new ProgressDialog(ACTScannerTareo.this);

        // PARAMETROS
        Intent iin= getIntent();
        Bundle b = iin.getExtras();
        if(b!=null)
        {
            StrP_fecha = (String) b.get("tareo_fecha");
            StrP_hora = (String) b.get("tareo_hora");
            StrP_turno = (String) b.get("tareo_turno");
            StrP_modo = (String) b.get("tareo_modo");
            StrP_turnoDatos = (String) b.get("tareo_turnoDatos");
        }

        btnFlash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (escanerZXing.getFlash()) {
                    escanerZXing.setFlash(false);
                } else {
                    escanerZXing.setFlash(true);
                }
            }
        });

        btnSalirApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        mVolleyService.mtd_GetArrayVolley("MARCADOR", String.format(UrlServer.URL_SERVER + "marcador"));
    }

    @Override
    public void onResume() {
        super.onResume();
        // El "manejador" del resultado es esta misma clase, por eso implementamos ZXingScannerView.ResultHandler
        escanerZXing.setResultHandler(this);
        escanerZXing.startCamera(); // Comenzar la cámara en onResume
    }

    @Override
    public void onPause() {
        super.onPause();
        escanerZXing.stopCamera(); // Pausar en onPause
    }

    // Estamos sobrescribiendo un método de la interfaz ZXingScannerView.ResultHandler
    @Override
    public void handleResult(Result resultado) {
        strPv_code = resultado.getText();
        System.out.println("CODIGO:: "+strPv_code);
        //dando el fomato al mnsaje
        final String format = resultado.getBarcodeFormat().name();
        //concatenar
        final String mensajeTotal = strPv_code;
        //VARIABLE QUE SOLO ACEPTE QUE SE INGRESEN NUMEROS DEL 0-9
        final String regexStr = "^[0-9]*$";
        try {
            Uri uri=Uri.parse("android.resource://"+getPackageName()+"/raw/rigtone");
            Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), uri);
            r.play();
        } catch (Exception e) {
            Log.e(TAG, e.getLocalizedMessage());
        }

        if(TextUtils.isEmpty(strPv_code)){
            Toasty.info(getApplicationContext(),"Codigo no valido: "+strPv_code,Toasty.LENGTH_SHORT).show();
            System.out.println("1IF:: "+strPv_code);
            onResume();
            return;
        }

        if(strPv_code.length() < 8 || strPv_code.length() > 18){
            Toasty.info(getApplicationContext(),"Codigo no valido: "+strPv_code,Toasty.LENGTH_SHORT).show();
            onResume();
            return;
        }

        if(!strPv_code.matches(regexStr)){
            System.out.println("3IF:: "+strPv_code);
            Toasty.info(getApplicationContext(),"Codigo no valido: "+strPv_code,Toasty.LENGTH_SHORT).show();
            onResume();
            return;
        }

        try {
            //Toasty.info(getApplicationContext(),strPv_code,Toasty.LENGTH_SHORT).show();
            //Integer.parseInt(codigo);
            System.out.println(strPv_code);
            tvCodigoResultante.setText(strPv_code+"");
            mVolleyService.mtd_GetArrayVolley("EMPLEADO_TAREO", String.format(
                    UrlServer.URL_SERVER + "empleado/tareo/"+strPv_code+"/"+userProfile.getId_sede()
            ));
            dlgCargando.setTitle("Buscando empleado");
            dlgCargando.setMessage("Por favor espere...");
            dlgCargando.setIndeterminate(true);
            dlgCargando.setCanceledOnTouchOutside(false);
            dlgCargando.show();
        } catch(NumberFormatException e){
            Toasty.error(getApplicationContext(),"Codigo desconocido: "+strPv_code,Toasty.LENGTH_SHORT).show();
            System.out.println(e.getMessage());
        }
    }

    public void mtd_CerrarTareoDetallado() {
        dlgTareo = new Dialog(this);
        Tareo tareo = lsTareo.get(0);
        dlgTareo.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dlgTareo.setCancelable(false);
        dlgTareo.setContentView(R.layout.dg_registrar_tareo_cierre);

        // GUI
        TextView dlgFechaCierTareo = dlgTareo.findViewById(R.id.dlgFechaCierTareo);
        TextView dlgHoraCierTareo = dlgTareo.findViewById(R.id.dlgHoraCierTareo);
        TextView dlgNombrePersona = dlgTareo.findViewById(R.id.dlgNombrePersona);
        TextView dlgSedeCieTareo = dlgTareo.findViewById(R.id.dlgSedeCieTareo);
        TextView dlgHoraCieTareo = dlgTareo.findViewById(R.id.dlgHoraCieTareo);
        Button btnRegistrarCierre = dlgTareo.findViewById(R.id.btnCerrarTareo);
        ImageView dialogButton = dlgTareo.findViewById(R.id.imgCloseDi);

        // TRASFORM
        String strL_Fecha = StrP_fecha;
        strL_Fecha = strL_Fecha.substring(0, strL_Fecha.length() - 9);
        strL_Fecha = strL_Fecha.replaceAll("\\s+", "");

        // SET DATA
        dlgFechaCierTareo.setText(strL_Fecha);
        dlgHoraCierTareo.setText(StrP_hora);
        dlgNombrePersona.setText(tareo.getEmpleado().getPersona().getDatos().toUpperCase(Locale.ROOT));
        dlgSedeCieTareo.setText(tareo.getTareo());
        dlgHoraCieTareo.setText(tareo.getTareoSede());

        btnRegistrarCierre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
                dataPost.put("ta_fecha_c", StrP_fecha);
                dataPost.put("ta_hora_c", StrP_hora);
                dataPost.put("id_sueldo", intL_IdSueldo);
                String strL_userCreacion = userProfile.getUsuarioInfo().getUs_usuario();
                dataPost.put("userCreacion", strL_userCreacion);
                JSONObject json =  new JSONObject(dataPost);
                System.out.println(json);
                mVolleyService.mtd_PostObjectVolley("CERRAR_TAREO", String.format(UrlServer.URL_SERVER + "tareo/cierre"),json);

                dlgCargando.setTitle("Cerrando tareo");
                dlgCargando.setMessage("Por favor espere...");
                dlgCargando.setIndeterminate(true);
                dlgCargando.setCanceledOnTouchOutside(false);
                dlgCargando.show();
            }
        });

        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dlgTareo.dismiss();
                mtd_ResumeCamera();
            }
        });
        dlgTareo.show();
    }

    public void mtd_CerrarTareoSimple(){
        Tareo tareo = lsTareo.get(0);
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
        dataPost.put("ta_fecha_c", StrP_fecha);
        dataPost.put("ta_hora_c", StrP_hora);
        dataPost.put("id_sueldo", intL_IdSueldo);
        JSONObject json =  new JSONObject(dataPost);

        String strL_userCreacion = userProfile.getUsuarioInfo().getUs_usuario();
        dataPost.put("userCreacion", strL_userCreacion);
        System.out.println(json);
        mVolleyService.mtd_PostObjectVolley("CERRAR_TAREO", String.format(UrlServer.URL_SERVER + "tareo/cierre"),json);

        dlgCargando.setTitle("Cerrando tareo");
        dlgCargando.setMessage("Por favor espere...");
        dlgCargando.setIndeterminate(true);
        dlgCargando.setCanceledOnTouchOutside(false);
        dlgCargando.show();
    }

    public void mtd_RegistrarTareoDetallado() {
        dlgTareo = new Dialog(this);
        Tareo tareo = lsTareo.get(0);
        //Tareo tareo = new Tareo();
        dlgTareo.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dlgTareo.setCancelable(false);
        dlgTareo.setContentView(R.layout.dg_registrar_tareo_ingreso);

        // GUI
        TextView dlgFechaRegTareo = dlgTareo.findViewById(R.id.dlgFechaRegTareo);
        TextView dlgHoraRegTareo = dlgTareo.findViewById(R.id.dlgHoraRegTareo);
        TextView dlgNombrePersona = dlgTareo.findViewById(R.id.dlgNombrePersona);
        TextView dlgTurnoPersona = dlgTareo.findViewById(R.id.dlgTurnoPersona);
        Button btnRegistrarIngreso = dlgTareo.findViewById(R.id.btnRegistrarIngreso);

        // TRASFORM
        String strL_Fecha = StrP_fecha;
        strL_Fecha = strL_Fecha.substring(0, strL_Fecha.length() - 9);
        strL_Fecha = strL_Fecha.replaceAll("\\s+", "");
        // SET DATA
        dlgFechaRegTareo.setText(strL_Fecha);
        dlgHoraRegTareo.setText(StrP_hora);
        dlgNombrePersona.setText(tareo.getEmpleado().getPersona().getDatos());
        dlgTurnoPersona.setText(StrP_turnoDatos);

        // BUTTON
        btnRegistrarIngreso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    /*DATOS PERSONALES*/
                    String strL_TpDocumento = tareo.getEmpleado().getPersona().getId_tpdocumento();
                    int  intL_Nacionalidad = tareo.getEmpleado().getPersona().getId_nacionalidad();
                    int intL_Sede = tareo.getEmpleado().getSede().getId_sede();
                    int intL_Cargo = tareo.getEmpleado().getCargo().getId_cargo();
                    int intL_IdPersona = tareo.getEmpleado().getPersona().getId_persona();
                    int intL_IdSueldo = tareo.getId_sueldo();
                    int intL_SedeEm = tareo.getId_sede_em();

                    /* SUPERVISOR */
                    int intL_Usuario = userProfile.getUsuarioInfo().getId_usuario();

                    /* OBKJECT */
                    Map<String, Object> dataPost = new HashMap<>();
                    dataPost.put("id_marcador",  StrP_turno);
                    dataPost.put("id_persona",  intL_IdPersona);
                    dataPost.put("id_tpdocumento",  strL_TpDocumento);
                    dataPost.put("id_nacionalidad", intL_Nacionalidad);
                    dataPost.put("id_cargo",    intL_Cargo);
                    dataPost.put("id_sede", intL_Sede);
                    dataPost.put("id_sueldo", intL_IdSueldo);
                    dataPost.put("id_sede_em", intL_SedeEm);

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
                    dataPost.put("ta_fecha_r", StrP_fecha);
                    dataPost.put("ta_fecha_c", StrP_fecha);
                    dataPost.put("ta_hora_r", StrP_hora);
                    dataPost.put("ta_hora_c", StrP_hora);
                    dataPost.put("ta_remunerado", 1);

                    // usuario
                    dataPost.put("ta_usuario", intL_Usuario);

                    String strL_userCreacion = userProfile.getUsuarioInfo().getUs_usuario();
                    dataPost.put("userCreacion", strL_userCreacion);

                    JSONObject json =  new JSONObject(dataPost);
                    System.out.println(json);
                    mVolleyService.mtd_PostObjectVolley("REGISTRO_TAREO", String.format(UrlServer.URL_SERVER + "tareo/registro"),json);

                    dlgCargando.setTitle("Registrando tareo");
                    dlgCargando.setMessage("Por favor espere...");
                    dlgCargando.setIndeterminate(true);
                    dlgCargando.setCanceledOnTouchOutside(false);
                    dlgCargando.show();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });

        // CLOSE DIALOG
        ImageView dialogButton = dlgTareo.findViewById(R.id.imgCloseDi);
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dlgTareo.dismiss();
                mtd_ResumeCamera();
            }
        });
        dlgTareo.show();
    }

    public void mtd_RegistrarTareoSimple(){
        Tareo tareo = lsTareo.get(0);
        /*DATOS PERSONALES*/
        String strL_TpDocumento = tareo.getEmpleado().getPersona().getId_tpdocumento();
        int  intL_Nacionalidad = tareo.getEmpleado().getPersona().getId_nacionalidad();
        int intL_Sede = tareo.getEmpleado().getSede().getId_sede();
        int intL_Cargo = tareo.getEmpleado().getCargo().getId_cargo();
        int intL_IdPersona = tareo.getEmpleado().getPersona().getId_persona();
        int intL_IdSueldo = tareo.getId_sueldo();
        int intL_SedeEm = tareo.getId_sede_em();

        /* SUPERVISOR */
        int intL_Usuario = userProfile.getUsuarioInfo().getId_usuario();

        /* OBKJECT */
        Map<String, Object> dataPost = new HashMap<>();
        dataPost.put("id_marcador",  StrP_turno);
        dataPost.put("id_persona",  intL_IdPersona);
        dataPost.put("id_tpdocumento",  strL_TpDocumento);
        dataPost.put("id_nacionalidad", intL_Nacionalidad);
        dataPost.put("id_cargo",    intL_Cargo);
        dataPost.put("id_sede", intL_Sede);
        dataPost.put("id_sueldo", intL_IdSueldo);
        dataPost.put("id_sede_em", intL_SedeEm);

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
        dataPost.put("ta_fecha_r", StrP_fecha);
        dataPost.put("ta_fecha_c", StrP_fecha);
        dataPost.put("ta_hora_r", StrP_hora);
        dataPost.put("ta_hora_c", StrP_hora);
        dataPost.put("ta_remunerado", 1);

        // usuario
        dataPost.put("ta_usuario", intL_Usuario);

        String strL_userCreacion = userProfile.getUsuarioInfo().getUs_usuario();
        dataPost.put("userCreacion", strL_userCreacion);

        JSONObject json =  new JSONObject(dataPost);
        System.out.println(json);
        mVolleyService.mtd_PostObjectVolley("REGISTRO_TAREO", String.format(UrlServer.URL_SERVER + "tareo/registro"),json);

        dlgCargando.setTitle("Registrando tareo");
        dlgCargando.setMessage("Por favor espere...");
        dlgCargando.setIndeterminate(true);
        dlgCargando.setCanceledOnTouchOutside(false);
        dlgCargando.show();
    }

    @Override
    public void notifySuccessOject(String requestType, JSONObject response) {
        switch (requestType) {
            case "REGISTRO_TAREO":
                Toasty.success(this, "Tareo registrado!", Toasty.LENGTH_LONG).show();
                if (StrP_modo.equals("D")){
                    dlgTareo.dismiss();
                }
                dlgCargando.dismiss();
                mtd_ResumeCamera();
                break;
            case "CERRAR_TAREO":
                Toasty.success(this, "Tareo cerrado!", Toasty.LENGTH_LONG).show();
                if (StrP_modo.equals("D")){
                    dlgTareo.dismiss();
                }
                dlgCargando.dismiss();
                mtd_ResumeCamera();
                break;
        }
    }

    @Override
    public void notifySuccessArray(String requestType, JSONArray response) {
        switch (requestType) {
            case "EMPLEADO_TAREO":
                Log.d(TAG, "EMPLEADO:: " + requestType);
                Log.d(TAG, "EMPLEADO:: " + response);
                dlgCargando.dismiss();
                TareoDAO tareoDAO = new TareoDAO();
                lsTareo = tareoDAO.fn_RegistroTareoEmpleado(response);
                System.out.println(response.toString());
                if(lsTareo.size()>0){
                    Tareo tareo = lsTareo.get(0);
                    if(StrP_modo.equals("D")){
                        if(tareo.getId_tareo() == 0){
                            mtd_RegistrarTareoDetallado();
                        }else{
                            mtd_CerrarTareoDetallado();
                        }
                    }else{
                        if(tareo.getId_tareo() == 0){
                            mtd_RegistrarTareoSimple();
                        }else{
                            mtd_CerrarTareoSimple();
                        }
                    }
                }else{
                    Toasty.info(getApplicationContext(),"¡Persona no encontrada! "+strPv_code,Toasty.LENGTH_LONG).show();

                    new CountDownTimer(2000, 1000) {
                        @Override
                        public void onTick(long millisUntilFinished) {
                            // do something after 1s
                        }

                        @Override
                        public void onFinish() {
                            mtd_ResumeCamera();
                        }
                    }.start();

                }
                break;
            case "MARCADOR":
                lsMarcador.addAll(new MarcadorDAO().fn_Marcador(response));
                break;
        }
    }

    @Override
    public void notifyError(String requestType, VolleyError error) {
        dlgCargando.dismiss();
        switch (requestType) {
            case "EMPLEADO_TAREO":
                Toasty.info(getApplicationContext(),"¡Persona no encontrada! "+strPv_code,Toasty.LENGTH_LONG).show();
                mtd_ResumeCamera();
                break;
            case "REGISTRO_TAREO":
                errorToastyServer.showErrorServer(error);
                mtd_ResumeCamera();
                break;
            case "EMPLEADO_TAREO_CIERRE":
                errorToastyServer.showErrorServer(error);
                mtd_ResumeCamera();
                break;
            case "CERRAR_TAREO":
                errorToastyServer.showErrorServer(error);
                mtd_ResumeCamera();
                break;
        }
    }

    public void mtd_ResumeCamera(){
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                escanerZXing.resumeCameraPreview(ACTScannerTareo.this);
            }
        }, 100);
    }

}
