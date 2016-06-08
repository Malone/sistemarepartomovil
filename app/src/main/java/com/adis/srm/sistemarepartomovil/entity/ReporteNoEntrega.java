package com.adis.srm.sistemarepartomovil.entity;

import com.orm.SugarRecord;

/**
 * Created by Equipo on 21/05/2016.
 */
public class ReporteNoEntrega extends SugarRecord {

    String idNoEntrega;
    String motivo;
    Pedido pedido;

    public ReporteNoEntrega() {
    }

    public ReporteNoEntrega(String idNoEntrega, String motivo, Pedido pedido) {
        this.idNoEntrega = idNoEntrega;
        this.motivo = motivo;
        this.pedido = pedido;
    }

    public String getIdNoEntrega() {
        return idNoEntrega;
    }

    public void setIdNoEntrega(String idNoEntrega) {
        this.idNoEntrega = idNoEntrega;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }
}
