package com.adis.srm.sistemarepartomovil.entity;

import com.orm.SugarRecord;
import com.orm.dsl.Unique;

import java.util.Date;

/**
 * Created by Equipo on 06/05/2016.
 */
public class Pedido extends SugarRecord {

    Long numeroPedido;
    String fechaSolicitud;
    String numeroFactura;
    String estado;

    public Pedido() {
    }

    public Pedido(Long numeroPedido, String fechaSolicitud, String numeroFactura, String estado) {
        this.numeroPedido = numeroPedido;
        this.fechaSolicitud = fechaSolicitud;
        this.numeroFactura = numeroFactura;
        this.estado = estado;
    }

    public Long getNumeroPedido() {
        return numeroPedido;
    }

    public void setNumeroPedido(Long numeroPedido) {
        this.numeroPedido = numeroPedido;
    }

    public String getFechaSolicitud() {
        return fechaSolicitud;
    }

    public void setFechaSolicitud(String fechaSolicitud) {
        this.fechaSolicitud = fechaSolicitud;
    }

    public String getNumeroFactura() {
        return numeroFactura;
    }

    public void setNumeroFactura(String numeroFactura) {
        this.numeroFactura = numeroFactura;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
