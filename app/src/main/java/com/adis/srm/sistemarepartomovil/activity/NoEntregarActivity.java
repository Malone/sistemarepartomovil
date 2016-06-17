package com.adis.srm.sistemarepartomovil.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.adis.srm.sistemarepartomovil.R;
import com.adis.srm.sistemarepartomovil.entity.Pedido;
import com.adis.srm.sistemarepartomovil.entity.ReporteNoEntrega;
import com.adis.srm.sistemarepartomovil.parsepersist.Retriever;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class NoEntregarActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    String idNoEntrega;
    HashMap<String, String> tiposKeyValue = new HashMap<String, String>();
    String numFactura;
    String numPedido;
    Pedido pedido;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_no_entregar);

        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(this);
        Button btnReportar = (Button) findViewById(R.id.btnReportar);
        TextView tvNoFactura = (TextView) findViewById(R.id.tvNoFactura);
        Intent intent = getIntent();
        pedido = (Pedido) intent.getExtras().getSerializable("pedido");
        tvNoFactura.setText(pedido.getNumeroFactura());
        final EditText etMotivo = (EditText) findViewById(R.id.etMotivo);


        tiposKeyValue = Retriever.getTiposNoEntrega();
        List<String> tipos = getTipos(tiposKeyValue);

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, tipos);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);

        btnReportar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ReporteNoEntrega reporteNoEntrega = new ReporteNoEntrega();
                reporteNoEntrega.setIdNoEntrega(idNoEntrega);
                reporteNoEntrega.setMotivo(etMotivo.getText().toString());
                reporteNoEntrega.setPedido(pedido);
                reporteNoEntrega.save();
                Retriever.procesarPedido(pedido.getNumeroFactura(), "no entregado");
                AlertDialog.Builder builder = new AlertDialog.Builder(NoEntregarActivity.this);
                builder.setMessage("Incidente Reportado")
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener(){
                            public void onClick(DialogInterface dialog, int d){
                                NoEntregarActivity.this.finish();
                            }
                        })
                        .create()
                        .show();
            }
        });

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String valor = parent.getItemAtPosition(position).toString();
        for(Map.Entry<String, String> entry : tiposKeyValue.entrySet()){
            if(valor.equalsIgnoreCase(tiposKeyValue.get(entry.getKey()))){
                idNoEntrega = entry.getKey();
                break;
            }
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0){}

    private List<String> getTipos(HashMap<String, String> tipos){
        List<String> tiposNoEntrega = new ArrayList<String>();
        Iterator iterator = tipos.values().iterator();
        while(iterator.hasNext()){
            tiposNoEntrega.add((String)iterator.next());
        }
        return tiposNoEntrega;
    }
}
