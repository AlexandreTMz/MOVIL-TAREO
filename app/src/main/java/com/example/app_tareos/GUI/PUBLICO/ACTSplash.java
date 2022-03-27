package com.example.app_tareos.GUI.PUBLICO;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;

import com.android.volley.VolleyError;
import com.example.app_tareos.BuildConfig;
import com.example.app_tareos.CONFIG.UrlServer;
import com.example.app_tareos.DAO.UsuarioDAO;
import com.example.app_tareos.DAO.VersionAPPDAO;
import com.example.app_tareos.INTERFACE.IResultVolley;
import com.example.app_tareos.LIBS.DownloadTask;
import com.example.app_tareos.LIBS.ErrorToastyServer;
import com.example.app_tareos.LIBS.PermissionUtility;
import com.example.app_tareos.LIBS.VolleyService;
import com.example.app_tareos.MODEL.VersionAPP;
import com.example.app_tareos.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import es.dmoral.toasty.Toasty;

public class ACTSplash extends AppCompatActivity implements IResultVolley {

    // Duración en milisegundos que se mostrará el splash
    private final int DURACION_SPLASH = 1500; // 2 segundos

    // PERMISO
    private final int MY_PERMISSIONS_REQUEST_USE_CAMERA = 0x00AF;

    private final String TAG = "Debug_MainActivity";

    // voley
    VolleyService mVolleyService;
    ErrorToastyServer errorToastyServer;

    // progress
    ProgressDialog pgdLogin;

    // declare the dialog as a member field of your activity
    Dialog mProgressDialog;


    // PERMISOS
    private String[] PERMISSIONS = {
            android.Manifest.permission.READ_EXTERNAL_STORAGE,
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
            android.Manifest.permission.CAMERA,
            android.Manifest.permission.WAKE_LOCK
    };
    private PermissionUtility permissionUtility;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_splash);

        // instantiate it within the onCreate method
        mProgressDialog = new Dialog(this);
        mProgressDialog.setCanceledOnTouchOutside(false);

        /// ERROR SERVER
        errorToastyServer = new ErrorToastyServer(this);

        // INTANCE SERVICE
        mVolleyService = new VolleyService(this, this);

        //checkCameraPermission();
        permissionUtility = new PermissionUtility(this, PERMISSIONS);
        if (permissionUtility.arePermissionsEnabled()) {
            Log.d(TAG, "Permission granted 1");
            mtd_DescargarVersion();
        } else {
            Log.d(TAG, "Permission no - granted 1");
            permissionUtility.requestMultiplePermissions();
        }
    }

    public void mtd_DescargarVersion() {
        try {
            PackageManager manager = this.getPackageManager();
            PackageInfo info = manager.getPackageInfo(this.getPackageName(), PackageManager.GET_ACTIVITIES);
            System.out.println("VERSION::" + info.versionName);
            // SERVICE
            Map<String, String> dataUser = new HashMap<>();
            dataUser.put("version", info.versionName);
            JSONObject jsonss = new JSONObject(dataUser);
            mVolleyService.mtd_PostObjectVolley("APP_VERSION", String.format(UrlServer.URL_SERVER + "app_version"), jsonss);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void paseLogin() {
        new Handler().postDelayed(new Runnable() {
            public void run() {
                // Cuando pasen los 3 segundos, pasamos a la actividad principal de la aplicación
                //Intent intent = new Intent(getApplicationContext(), ActhomeActivity.class);
                Intent intent = new Intent(getApplicationContext(), ACTLogin.class);
                startActivity(intent);
                finish();
            }

            ;
        }, DURACION_SPLASH);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (permissionUtility.onRequestPermissionsResult(requestCode, permissions, grantResults)) {
            Log.d(TAG, "Permission granted 2");
            mtd_DescargarVersion();
        }

    }

    @Override
    public void notifySuccessOject(String requestType, JSONObject response) {
        switch (requestType) {
            case "APP_VERSION":
                //pgdLogin.dismiss();
                try {
                    System.out.println("VERSION::" + response);
                    VersionAPP objL_versionAPP = new VersionAPPDAO().fn_VersionAPP(response);
                    if (objL_versionAPP.getVe_estado() == 0) {
                        final DownloadTask downloadTask = new DownloadTask(ACTSplash.this, mProgressDialog, objL_versionAPP.getVe_version());
                        downloadTask.execute(UrlServer.URL_INSTALL + objL_versionAPP.getVe_version() + "/APP.apk");
                        System.out.println(UrlServer.URL_INSTALL + objL_versionAPP.getVe_version() + "/APP.apk");
                    } else {
                        System.out.println("Paso a login");
                        paseLogin();
                    }
                    System.out.println(objL_versionAPP.toString());
                    //Toasty.success(getApplicationContext(), "Bienvenido!", Toasty.LENGTH_LONG).show();
                } catch (Exception e) {
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
        switch (requestType) {
            case "APP_VERSION":
                //pgdLogin.dismiss();
                //Toasty.error(getApplicationContext(),"Error, usuario o permiso incorrecto!",Toasty.LENGTH_LONG).show();
                errorToastyServer.showErrorServer(error);
                break;
        }
    }

}