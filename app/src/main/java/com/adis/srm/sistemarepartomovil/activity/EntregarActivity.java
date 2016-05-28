package com.adis.srm.sistemarepartomovil.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.adis.srm.sistemarepartomovil.R;
import com.adis.srm.sistemarepartomovil.entity.Producto;
import com.adis.srm.sistemarepartomovil.models.FacturaListView;
import com.adis.srm.sistemarepartomovil.models.SubtotalesProducto;
import com.adis.srm.sistemarepartomovil.parsepersist.Retriever;

import java.util.List;

public class EntregarActivity extends AppCompatActivity {

    String numFactura;
    String idPedido;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entregar);

        Intent intent = getIntent();
        numFactura = intent.getStringExtra("invoiceNumber");
        List<Producto> productoList = Retriever.getProductoByInvoiceNumber(numFactura);
        SubtotalesProducto subtotalesProductoList = Retriever.getSubtotales(productoList);
        FacturaListView  facturaListView = Retriever.getClientByNumFactura(numFactura);
        idPedido = facturaListView.getNumPedido();

        TextView tvPedido = (TextView) findViewById(R.id.tvIdPedido);
        TextView tvNumFactura = (TextView) findViewById(R.id.tvNumFactura);
        TextView tvCliente = (TextView) findViewById(R.id.tvCliente);
        TextView tvDUI = (TextView) findViewById(R.id.tvDUI);
        TextView tvDireccion = (TextView) findViewById(R.id.tvDireccion);
        TextView tvTotal = (TextView) findViewById(R.id.tvTotal);
        TextView tvIVA = (TextView) findViewById(R.id.tvIVA);
        TextView tvNeto = (TextView) findViewById(R.id.tvNeto);

        tvPedido.setText(facturaListView.getNumPedido());
        tvNumFactura.setText(facturaListView.getFactura());
        tvCliente.setText(facturaListView.getCliente());
        tvDUI.setText(facturaListView.getDui());
        tvDireccion.setText(facturaListView.getDireccion());
        tvTotal.setText(String.valueOf(subtotalesProductoList.getSubTotal()));
        tvIVA.setText(String.valueOf(subtotalesProductoList.getIva()));
        tvNeto.setText(String.valueOf(subtotalesProductoList.getTotalNeto()));

        Button btnConfirmar = (Button) findViewById(R.id.btnConfirmar);
        btnConfirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Retriever.procesarPedido(numFactura,"entregado");
                AlertDialog.Builder builder = new AlertDialog.Builder(EntregarActivity.this);
                builder.setMessage("Pedido entregado")
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener(){
                            public void onClick(DialogInterface dialog, int d){
                                EntregarActivity.this.finish();
                            }
                        })
                        .create()
                        .show();
            }
        });

        Button btnCancelar = (Button) findViewById(R.id.btnCancelar);
        btnCancelar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                finish();
            }
        });


    }
}
