package com.example.app_tareos.LIBS;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.PowerManager;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.FileProvider;

import com.example.app_tareos.BuildConfig;
import com.example.app_tareos.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class DownloadTask extends AsyncTask<String, Integer, String> {

    private Activity context;
    private PowerManager.WakeLock mWakeLock;
    private Dialog mProgressDialog;
    private String objP_version;
    private String objP_Path;
    ProgressBar pgbPb_Update;
    TextView tvw_descarga;

    public DownloadTask(Activity context, Dialog _progressDialog, String version) {
        this.context = context;
        this.mProgressDialog = _progressDialog;
        this.objP_version = version;
        this.objP_Path = Environment.getExternalStorageDirectory()+"/INSTALL/"+this.objP_version;
    }

    @Override
    protected String doInBackground(String... sUrl) {
        InputStream input = null;
        OutputStream output = null;
        HttpURLConnection connection = null;
        try {
            URL url = new URL(sUrl[0]);
            connection = (HttpURLConnection) url.openConnection();
            connection.connect();

            // expect HTTP 200 OK, so we don't mistakenly save error report
            // instead of the file
            if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                return "Server returned HTTP " + connection.getResponseCode() + " " + connection.getResponseMessage();
            }

            // this will be useful to display download percentage
            // might be -1: server did not report the length
            int fileLength = connection.getContentLength();

            // download the file
            input = connection.getInputStream();
            File directory = new File(this.objP_Path);
            if (!directory.exists()) {
                System.out.println("YESS:: "+this.objP_Path);
                directory.mkdirs();
            }else{
                System.out.println("NOO:: "+this.objP_Path);
            }
            //System.out.println("SSSS:: "+this.objP_Path+"/"+this.objP_version+".apk");
            output = new FileOutputStream(this.objP_Path+"/"+this.objP_version+".apk");

            byte data[] = new byte[4096];
            long total = 0;
            int count;
            while ((count = input.read(data)) != -1) {
                // allow canceling with back button
                if (isCancelled()) {
                    input.close();
                    return null;
                }
                total += count;
                // publishing the progress....
                if (fileLength > 0) // only if total length is known
                    publishProgress((int) (total * 100 / fileLength));
                output.write(data, 0, count);
            }
            OpenNewVersion();
        } catch (Exception e) {
            e.printStackTrace();
            return e.toString();
        } finally {
            try {
                if (output != null)
                    output.close();
                if (input != null)
                    input.close();
            } catch (IOException ignored) {
                ignored.printStackTrace();
            }

            if (connection != null)
                connection.disconnect();
        }
        return null;
    }
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        // take CPU lock to prevent CPU from going off if the user
        // presses the power button during download
        PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        mWakeLock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, getClass().getName());
        mWakeLock.acquire();


        mProgressDialog.setContentView(R.layout.dlg_update);

        pgbPb_Update = mProgressDialog.findViewById(R.id.pgb_update);
        tvw_descarga = mProgressDialog.findViewById(R.id.tvw_descarga);

        mProgressDialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        mProgressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mProgressDialog.setCanceledOnTouchOutside(false);
        mProgressDialog.show();

    }

    @Override
    protected void onProgressUpdate(Integer... progress) {
        super.onProgressUpdate(progress);
        // if we get here, length is known, now set indeterminate to false
        pgbPb_Update.setIndeterminate(false);
        pgbPb_Update.setMax(100);
        pgbPb_Update.setProgress(progress[0]);
        String msg = "";
        if(progress[0]>99){
            msg="Finalizado... ";
        }else {
            msg="Descargando... "+progress[0]+"%";
        }
        tvw_descarga.setText(msg);
    }

    @Override
    protected void onPostExecute(String result) {
        mWakeLock.release();
        mProgressDialog.dismiss();
        //mProgressDialog.dismiss();
        if (result != null) {
            System.out.println(result);
            Toast.makeText(context, "Download error: " + result, Toast.LENGTH_LONG).show();
        }
    }

    void OpenNewVersion() {
        File apkFile = new File(this.objP_Path+"/"+this.objP_version+".apk");
        Uri uri = FileProvider.getUriForFile(context, BuildConfig.APPLICATION_ID + ".provider", apkFile);
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setType("*/*");
        intent.setDataAndType(uri, "application/vnd.android.package-archive");
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        context.getApplicationContext().startActivity(intent);
    }
}
