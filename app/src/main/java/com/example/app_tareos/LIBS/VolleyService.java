package com.example.app_tareos.LIBS;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.app_tareos.INTERFACE.IResultVolley;

import org.json.JSONArray;
import org.json.JSONObject;

public class VolleyService {
    IResultVolley mResultCallback = null;
    Context mContext;

    public VolleyService(IResultVolley resultCallback, Context context){
        mResultCallback = resultCallback;
        mContext = context;
    }

    public void mtd_PostObjectVolley(final String requestType, String url, JSONObject jsnDato){
        Log.d("URL",url);
        try {
            RequestQueue queue = Volley.newRequestQueue(mContext);
            JsonObjectRequest jsonObj = new JsonObjectRequest(Request.Method.POST,url,jsnDato, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    if(mResultCallback != null)
                        mResultCallback.notifySuccessOject(requestType,response);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    if(mResultCallback != null)
                        mResultCallback.notifyError(requestType,error);
                }
            });
            queue.add(jsonObj);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void mtd_GetObjectVolley(final String requestType, String url){
        Log.d("URL",url);
        try {
            RequestQueue queue = Volley.newRequestQueue(mContext);
            JsonObjectRequest jsonObj = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    if(mResultCallback != null)
                        mResultCallback.notifySuccessOject(requestType, response);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    if(mResultCallback != null)
                        mResultCallback.notifyError(requestType, error);
                }
            });
            queue.add(jsonObj);
        }catch(Exception e){
        }
    }

    public void mtd_GetArrayVolley(final String requestType, String url){
        Log.d("URL",url);
        try {
            RequestQueue queue = Volley.newRequestQueue(mContext);
            JsonArrayRequest jsonObj = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    if(mResultCallback != null)
                        mResultCallback.notifySuccessArray(requestType, response);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    if(mResultCallback != null)
                        mResultCallback.notifyError(requestType, error);
                }
            });
            queue.add(jsonObj);
        }catch(Exception e){
        }
    }

}
