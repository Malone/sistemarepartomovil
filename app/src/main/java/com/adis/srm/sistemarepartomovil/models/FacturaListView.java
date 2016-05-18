package com.adis.srm.sistemarepartomovil.models;

/**
 * Created by Equipo on 10/05/2016.
 */
public class FacturaListView {
    String numPedido;
    String factura;
    String cliente;
    String direccion;
    String telefono;
    String dui;

    public FacturaListView() {
    }

    public FacturaListView(String numPedido, String factura, String cliente,
                           String direccion, String telefono, String dui) {
        this.numPedido = numPedido;
        this.factura = factura;
        this.cliente = cliente;
        this.direccion = direccion;
        this.telefono = telefono;
        this.dui = dui;
    }

    public String getNumPedido() {
        return numPedido;
    }

    public void setNumPedido(String numPedido) {
        this.numPedido = numPedido;
    }

    public String getFactura() {
        return factura;
    }

    public void setFactura(String factura) {
        this.factura = factura;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getDui() {
        return dui;
    }

    public void setDui(String dui) {
        this.dui = dui;
    }
}
