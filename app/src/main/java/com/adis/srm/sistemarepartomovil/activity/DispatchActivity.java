package com.adis.srm.sistemarepartomovil.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.adis.srm.sistemarepartomovil.R;
import com.adis.srm.sistemarepartomovil.models.FacturaListView;
import com.adis.srm.sistemarepartomovil.parsepersist.Retriever;
import com.orm.SugarContext;

import java.util.ArrayList;
import java.util.List;

public class DispatchActivity extends AppCompatActivity {
    private ListView lvInvoices;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_despatch);

        SugarContext.init(this);

        List<FacturaListView> facturaList = Retriever.retrieveFacturaList();
        lvInvoices = (ListView) findViewById(R.id.lvInvoices);
        final EditText etSearchInvoice = (EditText) findViewById(R.id.edSearchInvoice);

        etSearchInvoice.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent event) {
                List<FacturaListView> facturaListView = new ArrayList<FacturaListView>();
                if (keyCode == EditorInfo.IME_ACTION_SEARCH ||
                        keyCode == EditorInfo.IME_ACTION_DONE ||
                        event.getAction() == KeyEvent.ACTION_DOWN &&
                                event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
                    facturaListView.add(Retriever.getFacturaViewByNumFactura(String.valueOf(etSearchInvoice.getText())));
                    InvoiceAdapter invoiceAdapter = new InvoiceAdapter(getApplicationContext(), R.layout.row_invoice, facturaListView);
                    lvInvoices.setAdapter(invoiceAdapter);
                }
                return false;
            }
        });

        InvoiceAdapter invoiceAdapter = new InvoiceAdapter(getApplicationContext(), R.layout.row_invoice, facturaList);
        lvInvoices.setAdapter(invoiceAdapter);
    }


    public class InvoiceAdapter extends ArrayAdapter{
        private List<FacturaListView> facturaList;
        private int resource;
        private LayoutInflater inflater;

        public InvoiceAdapter(Context context, int resource, List<FacturaListView> facturaList){
            super(context, resource, facturaList);
            this.facturaList = facturaList;
            this.resource = resource;
            inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent){
            final int innerPosition = position;
            if(convertView == null){
                convertView = inflater.inflate(R.layout.row_invoice, null);
            }

            ImageView ivInvoice = (ImageView) convertView.findViewById(R.id.ivFactura);
            TextView tvFactura = (TextView) convertView.findViewById(R.id.tvFactura);
            TextView tvCliente = (TextView) convertView.findViewById(R.id.tvCliente);
            TextView tvDireccion = (TextView) convertView.findViewById(R.id.tvDireccion);
            TextView tvTelefono = (TextView) convertView.findViewById(R.id.tvTelefono);

            tvFactura.setText(facturaList.get(position).getFactura());
            tvCliente.setText(facturaList.get(position).getCliente());
            tvDireccion.setText(facturaList.get(position).getDireccion());
            tvTelefono.setText(facturaList.get(position).getTelefono());

            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(DispatchActivity.this, InvoiceActivity.class);
                    intent.putExtra("invoiceNumber", facturaList.get(innerPosition).getFactura());
                    DispatchActivity.this.startActivity(intent);
                }
            });

            return convertView;
        }

    }
}












