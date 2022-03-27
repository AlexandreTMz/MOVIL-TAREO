package com.example.app_tareos.GUI.PUBLICO;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.example.app_tareos.CONFIG.UrlServer;
import com.example.app_tareos.DAO.MarcadorDAO;
import com.example.app_tareos.DAO.TareoDAO;
import com.example.app_tareos.INTERFACE.IResultVolley;
import com.example.app_tareos.LIBS.EditTextDialogTime;
import com.example.app_tareos.LIBS.ErrorToastyServer;
import com.example.app_tareos.LIBS.VolleyService;
import com.example.app_tareos.MODEL.Marcador;
import com.example.app_tareos.MODEL.Tareo;
import com.example.app_tareos.R;
import com.example.app_tareos.ROOMS.Preference_PerfilUsuario;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.zxing.Result;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import es.dmoral.toasty.Toasty;
import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class ACTScannerPermiso extends AppCompatActivity implements ZXingScannerView.ResultHandler{

    private ZXingScannerView escanerZXing;

    // DIALOGO SUPLENTE
    FloatingActionButton fbFlash;

    // voley
    VolleyService mVolleyService;
    ErrorToastyServer errorToastyServer;
    private String TAG = "MainActivity"; 

    @Override
    public void onCreate(Bundle state) {
        super.onCreate(state);
        setContentView(R.layout.act_scanner_permiso);

        escanerZXing = findViewById(R.id.zxscan);
        fbFlash = findViewById(R.id.fbFlash);

        fbFlash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (escanerZXing.getFlash()) {
                    escanerZXing.setFlash(false);
                } else {
                    escanerZXing.setFlash(true);
                }
            }
        });
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
        String codigo = resultado.getText();

        try {
            Integer.parseInt(codigo);
            // Si quieres que se siga escaneando después de haber leído el código, descomenta lo siguiente:
            // Si la descomentas no recomiendo que llames a finish
            //        escanerZXing.resumeCameraPreview(this);
            // Obener código/texto leído
             System.out.println(codigo);
            // Preparar un Intent para regresar datos a la actividad que nos llamó
            Intent intentRegreso = new Intent();
            intentRegreso.putExtra("codigo", codigo);
            setResult(Activity.RESULT_OK, intentRegreso);
            // Cerrar la actividad. Ahora mira onActivityResult de MainActivity
            finish();
        } catch(NumberFormatException e){
            System.out.println(e.getMessage());
        }
    }

}
