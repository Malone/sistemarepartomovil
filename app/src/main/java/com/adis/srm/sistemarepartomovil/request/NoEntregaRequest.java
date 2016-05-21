package com.adis.srm.sistemarepartomovil.request;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

/**
 * Created by Equipo on 20/05/2016.
 */
public class NoEntregaRequest extends StringRequest{

    private static final String LOGIN_REQUEST_URL = "https://api.myjson.com/bins/4a37c";

    public NoEntregaRequest(Response.Listener<String> listener){
        super(Request.Method.GET, LOGIN_REQUEST_URL, listener, null);
    }

}
