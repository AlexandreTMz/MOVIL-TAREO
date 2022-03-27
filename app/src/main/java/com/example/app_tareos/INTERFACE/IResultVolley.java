package com.example.app_tareos.INTERFACE;

import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONObject;

public interface IResultVolley {
    public void notifySuccessOject(String requestType, JSONObject response);
    public void notifySuccessArray(String requestType, JSONArray response);
    public void notifyError(String requestType, VolleyError error);
}