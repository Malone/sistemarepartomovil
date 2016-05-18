package com.adis.srm.sistemarepartomovil.request;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Equipo on 04/05/2016.
 */
public class LoginRequest extends StringRequest{

    private static final String LOGIN_REQUEST_URL = "https://api.myjson.com/bins/3c9d4";
    private Map<String, String> params;

    public LoginRequest(String username, String password, Response.Listener<String> listener){
        super(Request.Method.GET, LOGIN_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("username", username);
        params.put("password", password);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }

    @Override
    public String getBodyContentType() {
        return "application/x-www-form-urlencoded";
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        Map<String, String> pars = new HashMap<String, String>();
        pars.put("Content-Type", "application/x-www-form-urlencoded");
        return pars;
    }
}
