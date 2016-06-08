package com.adis.srm.sistemarepartomovil.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.adis.srm.sistemarepartomovil.R;
import com.adis.srm.sistemarepartomovil.entity.Pedido;
import com.adis.srm.sistemarepartomovil.entity.Producto;
import com.adis.srm.sistemarepartomovil.models.SubtotalesProducto;
import com.adis.srm.sistemarepartomovil.parsepersist.Retriever;

import java.util.List;

public class InvoiceActivity extends AppCompatActivity {

    ListView lvProductos;
    String numFactura;
    String numPedido;
    Pedido pedido;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invoice);
        Intent intent = getIntent();
        numFactura = intent.getStringExtra("invoiceNumber");
        numPedido = intent.getStringExtra("numPedido");
        pedido = (Pedido) intent.getExtras().getSerializable("pedido");

        List<Producto> productoList = Retriever.getProductoByInvoiceNumber(numFactura);
        SubtotalesProducto subtotalesProductoList = Retriever.getSubtotales(productoList);

        lvProductos = (ListView) findViewById(R.id.lvProductos);
        TextView tvSubtotal = (TextView) findViewById(R.id.tvSubtotal);
        TextView tvDescuento = (TextView) findViewById(R.id.tvDescuento);
        TextView tvIVA = (TextView) findViewById(R.id.tvIVA);
        TextView tvTotalNeto = (TextView) findViewById(R.id.tvTotalNeto);

        tvSubtotal.setText(String.valueOf(subtotalesProductoList.getSubTotal()));
        tvDescuento.setText(String.valueOf(subtotalesProductoList.getDescuento()));
        tvIVA.setText(String.valueOf(subtotalesProductoList.getIva()));
        tvTotalNeto.setText(String.valueOf(subtotalesProductoList.getTotalNeto()));


        InvoiceAdapter invoiceAdapter = new InvoiceAdapter(getApplicationContext(), R.layout.detail_invoice, productoList);
        lvProductos.setAdapter(invoiceAdapter);

    }

    public class InvoiceAdapter extends ArrayAdapter {
        private List<Producto> productoList;
        private int resource;
        private LayoutInflater inflater;

        public InvoiceAdapter(Context context, int resource, List<Producto> productoList){
            super(context, resource, productoList);
            this.productoList = productoList;
            this.resource = resource;
            inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent){
            final int innerPosition = position;
            if(convertView == null){
                convertView = inflater.inflate(R.layout.detail_invoice, null);
            }


            TextView tvCantidad = (TextView) convertView.findViewById(R.id.tvCantidadDetail);
            TextView tvDescripcion = (TextView) convertView.findViewById(R.id.tvDescripcionDetail);
            TextView tvCU = (TextView) convertView.findViewById(R.id.tvCUDetail);
            TextView tvCT = (TextView) convertView.findViewById(R.id.tvCTDetail);

            tvCantidad.setText(String.valueOf(productoList.get(position).getCantidad()));
            tvDescripcion.setText(productoList.get(position).getDescripcion());
            tvCU.setText(String.valueOf(productoList.get(position).getCostoUnitario()));
            tvCT.setText(String.valueOf(productoList.get(position).getCostoTotal()));

            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(InvoiceActivity.this, EditInvoiceItemActivity.class);
                    intent.putExtra("idProducto", String.valueOf(productoList.get(innerPosition).getIdProducto()));
                    intent.putExtra("invoiceNumber", numFactura);
                    InvoiceActivity.this.startActivity(intent);
                }
            });

            return convertView;
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();  // Always call the superclass method first

        setContentView(R.layout.activity_invoice);
        Intent intent = getIntent();
        numFactura = intent.getStringExtra("invoiceNumber");
        List<Producto> productoList = Retriever.getProductoByInvoiceNumber(numFactura);
        SubtotalesProducto subtotalesProductoList = Retriever.getSubtotales(productoList);

        lvProductos = (ListView) findViewById(R.id.lvProductos);
        TextView tvSubtotal = (TextView) findViewById(R.id.tvSubtotal);
        TextView tvDescuento = (TextView) findViewById(R.id.tvDescuento);
        TextView tvIVA = (TextView) findViewById(R.id.tvIVA);
        TextView tvTotalNeto = (TextView) findViewById(R.id.tvTotalNeto);

        tvSubtotal.setText(String.valueOf(subtotalesProductoList.getSubTotal()));
        tvDescuento.setText(String.valueOf(subtotalesProductoList.getDescuento()));
        tvIVA.setText(String.valueOf(subtotalesProductoList.getIva()));
        tvTotalNeto.setText(String.valueOf(subtotalesProductoList.getTotalNeto()));


        InvoiceAdapter invoiceAdapter = new InvoiceAdapter(getApplicationContext(), R.layout.detail_invoice, productoList);
        lvProductos.setAdapter(invoiceAdapter);
    }

    public void onClickEntregar(View v){
        Intent intent = new Intent(InvoiceActivity.this, EntregarActivity.class);
        intent.putExtra("invoiceNumber", numFactura);
        InvoiceActivity.this.startActivity(intent);
    }

    public void onClickNoEntregar(View v){
        Intent intent = new Intent(InvoiceActivity.this, NoEntregarActivity.class);
        intent.putExtra("pedido", pedido);
        InvoiceActivity.this.startActivity(intent);
    }
}
