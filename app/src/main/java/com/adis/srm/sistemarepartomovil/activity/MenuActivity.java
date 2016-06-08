package com.adis.srm.sistemarepartomovil.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.adis.srm.sistemarepartomovil.R;
import com.adis.srm.sistemarepartomovil.parsepersist.JsonConstructor;
import com.adis.srm.sistemarepartomovil.parsepersist.JsonParserPersister;
import com.adis.srm.sistemarepartomovil.request.DispatchRequest;
import com.adis.srm.sistemarepartomovil.request.SynchronizationRequest;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;

public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        Response.Listener<String> responseListener = new Response.Listener<String>(){
            @Override
            public void onResponse(String response){
                JsonParserPersister.parse(response);
            }
        };

        DispatchRequest dispatchRequest = new DispatchRequest(responseListener );
        RequestQueue queue = Volley.newRequestQueue(MenuActivity.this);
        queue.add(dispatchRequest);
    }

    public void despatchOnClick(View view){
        Intent intent = new Intent(MenuActivity.this, DispatchActivity.class);
        MenuActivity.this.startActivity(intent);
    }

    public void sincronizarOnClick(View view){

       String jsonString = "";

       try{
           jsonString = JsonConstructor.constructSyncJson().toString();
       }
       catch(JSONException e){}

        Response.Listener<String> responseListener = new Response.Listener<String>(){
            @Override
            public void onResponse(String response){
                JsonParserPersister.parse(response);
            }
        };

        SynchronizationRequest syncRequest = new SynchronizationRequest(jsonString, responseListener );
        RequestQueue queue = Volley.newRequestQueue(MenuActivity.this);
        queue.add(syncRequest);
    }

}
