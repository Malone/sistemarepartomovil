package com.adis.srm.sistemarepartomovil.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.adis.srm.sistemarepartomovil.R;
import com.adis.srm.sistemarepartomovil.entity.Producto;
import com.adis.srm.sistemarepartomovil.entity.TransaccionItem;
import com.adis.srm.sistemarepartomovil.parsepersist.JsonParserPersister;
import com.adis.srm.sistemarepartomovil.parsepersist.Retriever;

import java.math.BigDecimal;

public class EditInvoiceItemActivity extends AppCompatActivity {

    String numFactura = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_invoice_item);

        Intent intent = getIntent();
        String idProducto = intent.getStringExtra("idProducto");
        numFactura = intent.getStringExtra("invoiceNumber");

        final Producto producto = Retriever.getProductoByIdProductoIdInvoice(numFactura, idProducto);

        TextView tvCodigo = (TextView) findViewById(R.id.tvCodigo);
        TextView tvDescripcion = (TextView) findViewById(R.id.tvDescripcion);
        TextView tvCU = (TextView) findViewById(R.id.tvCU);
        TextView tvDescuento = (TextView) findViewById(R.id.tvDescuento);
        final EditText etCantidad = (EditText) findViewById(R.id.etCantidad);
        etCantidad.setInputType(EditorInfo.TYPE_CLASS_NUMBER);
        TextView tvTotal = (TextView) findViewById(R.id.tvTotal);

        tvCodigo.setText(String.valueOf(producto.getIdProducto()));
        tvDescripcion.setText(producto.getDescripcion());
        tvCU.setText(String.valueOf(producto.getCostoUnitario()));
        tvDescuento.setText(String.valueOf(producto.getDescuento()));
        etCantidad.setText(String.valueOf(producto.getCantidad()));
        tvTotal.setText(String.valueOf(producto.getCostoTotal()));

        Button btnModificar = (Button) findViewById(R.id.btnModificar);
        btnModificar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if(etCantidad.getText().toString().length() > 0){
                    Long cantidad = Long.valueOf(etCantidad.getText().toString());
                    modifyCTIVA(cantidad, producto);
                    registrarModificacion(cantidad, producto, numFactura);
                    finish();
                    startActivity(getIntent());
                }
                else{
                    Toast.makeText(EditInvoiceItemActivity.this, "Digite una cantidad", Toast.LENGTH_LONG).show();
                }
            }
        });

        Button btnEliminar = (Button) findViewById(R.id.btnEliminar);
        btnEliminar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(EditInvoiceItemActivity.this)
                        .setTitle("Delete entry")
                        .setMessage("Are you sure you want to delete this entry?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                producto.delete();
                                Intent intent = new Intent(EditInvoiceItemActivity.this, InvoiceActivity.class);
                                intent.putExtra("invoiceNumber", numFactura);
                                EditInvoiceItemActivity.this.startActivity(intent);
                            }
                        })
                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // do nothing
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            }
        });
    }

    private void registrarModificacion(Long cantidad, Producto producto, String numFactura) {
        TransaccionItem transaccionItem = new TransaccionItem();
        transaccionItem.setNumFactura(numFactura);
        transaccionItem.setCantidad(cantidad);
        transaccionItem.setNumProducto(producto.getIdProducto());
        transaccionItem.save();
    }

    private void modifyCTIVA(Long cantidad, Producto producto){
        BigDecimal costoTotal = JsonParserPersister.getCostoTotal(cantidad, producto.getCostoUnitario(), producto.getDescuento());
        producto.setCostoTotal(costoTotal);
        producto.setIva(costoTotal);
        producto.setCantidad(cantidad);
        producto.save();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.putExtra("invoiceNumber", numFactura);
        finish();
    }

}
