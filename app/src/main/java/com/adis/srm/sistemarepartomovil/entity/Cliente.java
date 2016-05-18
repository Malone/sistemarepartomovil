package com.adis.srm.sistemarepartomovil.entity;

import com.orm.SugarRecord;
import com.orm.dsl.Unique;

/**
 * Created by Equipo on 06/05/2016.
 */
public class Cliente extends SugarRecord {

    @Unique
    Long idCliente;
    String name;
    String direccion;
    String telefono;
    String dui;
    String nrc;
    String nit;
    String latitud;
    String longitud;
    Pedido pedido;

    public Cliente() {
    }

    public Cliente(Long idCliente, String name, String direccion, String telefono,
                   String dui, String nrc, String nit, String latitud, String longitud, Pedido pedido) {
        this.idCliente = idCliente;
        this.name = name;
        this.direccion = direccion;
        this.telefono = telefono;
        this.dui = dui;
        this.nrc = nrc;
        this.nit = nit;
        this.latitud = latitud;
        this.longitud = longitud;
        this.pedido = pedido;
    }

    public Long getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Long idCliente) {
        this.idCliente = idCliente;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getNrc() {
        return nrc;
    }

    public void setNrc(String nrc) {
        this.nrc = nrc;
    }

    public String getNit() {
        return nit;
    }

    public void setNit(String nit) {
        this.nit = nit;
    }

    public String getLatitud() {
        return latitud;
    }

    public void setLatitud(String latitud) {
        this.latitud = latitud;
    }

    public String getLongitud() {
        return longitud;
    }

    public void setLongitud(String longitud) {
        this.longitud = longitud;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }
}
