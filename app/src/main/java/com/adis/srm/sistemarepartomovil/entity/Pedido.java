package com.adis.srm.sistemarepartomovil.entity;

import com.orm.SugarRecord;

/**
 * Created by Equipo on 06/05/2016.
 */
public class Pedido extends SugarRecord {

    Long numeroPedido;
    String fechaSolicitud;
    String numeroFactura;
    String estado;
    Long correlativo;

    public Pedido() {
    }

    public Pedido(Long numeroPedido, String fechaSolicitud, String numeroFactura,
                  String estado, Long correlativo) {
        this.numeroPedido = numeroPedido;
        this.fechaSolicitud = fechaSolicitud;
        this.numeroFactura = numeroFactura;
        this.estado = estado;
        this.correlativo = correlativo;
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

    public Long getCorrelativo() {
        return correlativo;
    }

    public void setCorrelativo(Long correlativo) {
        this.correlativo = correlativo;
    }
}
