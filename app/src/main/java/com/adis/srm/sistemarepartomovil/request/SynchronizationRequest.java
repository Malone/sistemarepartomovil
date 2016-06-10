package com.adis.srm.sistemarepartomovil.request;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Equipo on 03/06/2016.
 */
public class SynchronizationRequest extends StringRequest {

    private static final String LOGIN_REQUEST_URL = "http://192.168.0.113:8081/restapisrv/webapi/syncronize";
    private Map<String, String> params;

    public SynchronizationRequest(String json, Response.Listener<String> listener){
        super(Request.Method.POST, LOGIN_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("json", json);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }

    /*@Override
    public String getBodyContentType() {
        return "application/json;charset=UTF-8";
    }*/

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        HashMap<String, String> pars = new HashMap<String, String>();
        pars.put("Content-Type", "application/json; charset=utf-8");
        return pars;
    }

}
