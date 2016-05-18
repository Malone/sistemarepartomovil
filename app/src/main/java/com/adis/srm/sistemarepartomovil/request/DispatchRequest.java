package com.adis.srm.sistemarepartomovil.request;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

/**
 * Created by Equipo on 06/05/2016.
 */
public class DispatchRequest extends StringRequest {

    private static final String DISPATCH_REQUEST_URL = "https://api.myjson.com/bins/4p6y2";


    public DispatchRequest(Response.Listener<String> listener){
        super(DISPATCH_REQUEST_URL, listener, null);
    }

}
