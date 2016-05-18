package com.adis.srm.sistemarepartomovil.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.adis.srm.sistemarepartomovil.R;

public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
    }

    public void despatchOnClick(View view){
        Intent intent = new Intent(MenuActivity.this, DispatchActivity.class);
        MenuActivity.this.startActivity(intent);
    }
}
