package com.example.app_tareos.GUI.PUBLICO;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.example.app_tareos.CONFIG.UrlServer;
import com.example.app_tareos.DAO.UsuarioDAO;
import com.example.app_tareos.INTERFACE.IResultVolley;
import com.example.app_tareos.LIBS.ErrorToastyServer;
import com.example.app_tareos.LIBS.VolleyService;
import com.example.app_tareos.R;
import com.example.app_tareos.ROOMS.Preference_PerfilUsuario;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import es.dmoral.toasty.Toasty;

public class ACTLogin extends AppCompatActivity implements IResultVolley {

    private String TAG = "MainActivity";

    // GUI
    Button btnIniciar;
    TextInputEditText edUsuario, edContrasenia;
    TextView tvwVersion;

    // voley
    VolleyService mVolleyService;
    ErrorToastyServer errorToastyServer;

    // progress
    ProgressDialog pgdLogin;

    // room
    Preference_PerfilUsuario userProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_login);

        // PROFILE
        userProfile = Preference_PerfilUsuario.getInstance(this);

        pgdLogin = new ProgressDialog(ACTLogin.this);
        pgdLogin.setProgressStyle(ProgressDialog.STYLE_SPINNER);

        /// ERROR SERVER
        errorToastyServer = new ErrorToastyServer(this);

        // INTANCE SERVICE
        mVolleyService = new VolleyService(this,this);

        // GUI INSTANCE
        btnIniciar = findViewById(R.id.btnIniciar);
        edUsuario = findViewById(R.id.edUsuario);
        edContrasenia = findViewById(R.id.edContrasenia);
        tvwVersion = findViewById(R.id.tvwVersion);

        btnIniciar = findViewById(R.id.btnIniciar);
        btnIniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String, String> dataUser = new HashMap<>();
                dataUser.put("usuario", edUsuario.getText().toString());
                dataUser.put("contrasenia",  edContrasenia.getText().toString());
                JSONObject jsonss = new JSONObject(dataUser);
                System.out.println(jsonss.toString());
                mVolleyService.mtd_PostObjectVolley("LOGIN",String.format(UrlServer.URL_SERVER+"usuario/login"),jsonss);

                pgdLogin.setMessage("Por favor espere...");
                pgdLogin.setIndeterminate(true);
                pgdLogin.setCanceledOnTouchOutside(false);
                pgdLogin.show();
            }
        });

        try {
            PackageManager manager = this.getPackageManager();
            PackageInfo info = manager.getPackageInfo(this.getPackageName(), PackageManager.GET_ACTIVITIES);
            tvwVersion.setText("v "+info.versionName);
        } catch (Exception e) {
            e.printStackTrace();
        }


        // shared
        if(userProfile.getSession()){
            edUsuario.setText(userProfile.getNickname());
        }
    }


    @Override
    public void notifySuccessOject(String requestType, JSONObject response) {
        Log.d(TAG, "SUCCESSO Volley requester " + requestType);
        Log.d(TAG, "SUCCESSO Volley JSON: " + response);
        switch (requestType){
            case "LOGIN":
                pgdLogin.dismiss();
                try {
                    new UsuarioDAO(this).loginUsuario(response);
                    Toasty.success(getApplicationContext(),"Bienvenido!",Toasty.LENGTH_LONG).show();
                }catch (Exception e){
                    e.printStackTrace();
                }
                //Toasty.success(getApplicationContext(),response.toString(),Toasty.LENGTH_LONG).show();
                break;
        }
    }

    @Override
    public void notifySuccessArray(String requestType, JSONArray response) {

    }

    @Override
    public void notifyError(String requestType, VolleyError error) {
        error.printStackTrace();
        errorToastyServer.showErrorServer(error);
        switch (requestType){
            case "LOGIN":
                pgdLogin.dismiss();
                //Toasty.error(getApplicationContext(),"Error, usuario o permiso incorrecto!",Toasty.LENGTH_LONG).show();
                errorToastyServer.showErrorServer(error);
                break;
        }
    }
}