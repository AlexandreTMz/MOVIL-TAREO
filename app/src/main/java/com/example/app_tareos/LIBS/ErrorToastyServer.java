package com.example.app_tareos.LIBS;
import android.content.Context;
import android.widget.Toast;
import com.android.volley.NetworkResponse;
import com.android.volley.VolleyError;
import org.json.JSONObject;
import es.dmoral.toasty.Toasty;

public class ErrorToastyServer {
    Context mContext;

    public ErrorToastyServer(Context context){
        this.mContext = context;
    }

    public void showErrorServer(VolleyError volleyError){
        if(volleyError.networkResponse != null && volleyError.networkResponse.data != null){
            NetworkResponse errorRes = volleyError.networkResponse;
            try {
                JSONObject jsonResponse = new JSONObject(new String(errorRes.data,"UTF-8"));
                Toasty.error(this.mContext, jsonResponse.get("msg").toString(), Toast.LENGTH_LONG).show();
                System.out.println(new String(errorRes.data,"UTF-8"));
            }catch (Exception e){

            }
        }else{
            Toasty.error(this.mContext, "Error el servidor no responde!", Toast.LENGTH_LONG).show();
        }
    }
}
