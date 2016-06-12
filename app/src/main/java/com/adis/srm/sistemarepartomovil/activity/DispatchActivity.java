package com.adis.srm.sistemarepartomovil.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.adis.srm.sistemarepartomovil.R;
import com.adis.srm.sistemarepartomovil.entity.Pedido;
import com.adis.srm.sistemarepartomovil.models.FacturaListView;
import com.adis.srm.sistemarepartomovil.parsepersist.Retriever;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.orm.SugarContext;

import java.util.ArrayList;
import java.util.List;

public class DispatchActivity extends AppCompatActivity {
    private ListView lvInvoices;
    private boolean isScanned = false;
    List<FacturaListView> scannedFacturaListView;
    FacturaListView scannedFacturaView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_despatch);

        SugarContext.init(this);

        List<FacturaListView> facturaList = Retriever.retrieveFacturaList();
        lvInvoices = (ListView) findViewById(R.id.lvInvoices);
        Button btnScan = (Button) findViewById(R.id.btnScan);
        final Activity activity = this;
        final EditText etSearchInvoice = (EditText) findViewById(R.id.edSearchInvoice);

        etSearchInvoice.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent event) {
                List<FacturaListView> facturaListView = new ArrayList<FacturaListView>();
                if (keyCode == EditorInfo.IME_ACTION_SEARCH ||
                    keyCode == EditorInfo.IME_ACTION_DONE ||
                    event.getAction() == KeyEvent.ACTION_DOWN &&
                    event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
                    if(!"".equalsIgnoreCase(String.valueOf(etSearchInvoice.getText()))){
                        FacturaListView facturaView = Retriever.getFacturaViewByNumFactura(String.valueOf(etSearchInvoice.getText()));
                        facturaListView.add(facturaView);
                    }
                    else{
                        facturaListView =  Retriever.retrieveFacturaList();
                    }
                    InvoiceAdapter invoiceAdapter = new InvoiceAdapter(getApplicationContext(), R.layout.row_invoice, facturaListView);
                    lvInvoices.setAdapter(invoiceAdapter);
                }
                return false;
            }
        });

        InvoiceAdapter invoiceAdapter = new InvoiceAdapter(getApplicationContext(), R.layout.row_invoice, facturaList);
        lvInvoices.setAdapter(invoiceAdapter);

        btnScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IntentIntegrator integrator = new IntentIntegrator(activity);
                integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
                integrator.setPrompt("Scan");
                integrator.setCameraId(0);
                integrator.setBeepEnabled(false);
                integrator.setBarcodeImageEnabled(false);
                integrator.initiateScan();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        isScanned = true;
        if(result != null) {
            if(result.getContents() == null) {
                Log.d("MainActivity", "Cancelled scan");
                Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show();
            } else {
                Log.d("MainActivity", "Scanned");
                Toast.makeText(this, "Scanned: " + result.getContents(), Toast.LENGTH_LONG).show();
                scannedFacturaListView = new ArrayList<FacturaListView>();
                scannedFacturaView = Retriever.getFacturaViewByNumFactura(result.getContents());
                scannedFacturaListView.add(scannedFacturaView);
                InvoiceAdapter invoiceAdapter = new InvoiceAdapter(getApplicationContext(), R.layout.row_invoice, scannedFacturaListView);
                lvInvoices.setAdapter(invoiceAdapter);
            }
        } else {
            // This is important, otherwise the result will not be passed to the fragment
            super.onActivityResult(requestCode, resultCode, data);
        }
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
            if(convertView == null) {
                if (facturaList.get(position).getFactura() != null){
                    convertView = inflater.inflate(R.layout.row_invoice, null);
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
                            Pedido pedido = Retriever.getPedidoById(facturaList.get(innerPosition).getNumPedido());
                            intent.putExtra("invoiceNumber", facturaList.get(innerPosition).getFactura());
                            intent.putExtra("idPedido", facturaList.get(innerPosition).getNumPedido());
                            intent.putExtra("pedido", pedido);
                            DispatchActivity.this.startActivity(intent);
                        }
                    });
                }else{
                    convertView = inflater.inflate(R.layout.notfound_invoice, null);
                }
            }

            return convertView;
        }

    }

    @Override
    protected void onRestart() {
        super.onRestart();
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
                    if(!"".equalsIgnoreCase(String.valueOf(etSearchInvoice.getText()))){
                        FacturaListView facturaView = Retriever.getFacturaViewByNumFactura(String.valueOf(etSearchInvoice.getText()));
                        facturaListView.add(facturaView);
                    }
                    else{
                        facturaListView=  Retriever.retrieveFacturaList();
                    }
                    InvoiceAdapter invoiceAdapter = new InvoiceAdapter(getApplicationContext(), R.layout.row_invoice, facturaListView);
                    lvInvoices.setAdapter(invoiceAdapter);
                }
                return false;
            }
        });

        InvoiceAdapter invoiceAdapter = new InvoiceAdapter(getApplicationContext(), R.layout.row_invoice, facturaList);
        lvInvoices.setAdapter(invoiceAdapter);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if(scannedFacturaView != null) {
            outState.putSerializable("scannedInvoice", scannedFacturaView);
        }
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        scannedFacturaView = (FacturaListView) savedInstanceState.getSerializable("scannedInvoice");
        if(scannedFacturaView != null) {
            List<FacturaListView> scannedFacturaListView = new ArrayList<FacturaListView>();
            scannedFacturaListView.add(scannedFacturaView);
            InvoiceAdapter invoiceAdapter = new InvoiceAdapter(getApplicationContext(), R.layout.row_invoice, scannedFacturaListView);
            lvInvoices.setAdapter(invoiceAdapter);
        }
    }
}












