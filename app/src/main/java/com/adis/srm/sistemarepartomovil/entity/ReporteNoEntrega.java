package com.adis.srm.sistemarepartomovil.entity;

import com.orm.SugarRecord;

/**
 * Created by Equipo on 21/05/2016.
 */
public class ReporteNoEntrega extends SugarRecord {

    String idNoEntrega;
    String numFactura;
    String motivo;

    public ReporteNoEntrega() {
    }

    public ReporteNoEntrega(String idNoEntrega, String numFactura, String motivo) {
        this.idNoEntrega = idNoEntrega;
        this.numFactura = numFactura;
        this.motivo = motivo;
    }

    public String getIdNoEntrega() {
        return idNoEntrega;
    }

    public void setIdNoEntrega(String idNoEntrega) {
        this.idNoEntrega = idNoEntrega;
    }

    public String getNumFactura() {
        return numFactura;
    }

    public void setNumFactura(String numFactura) {
        this.numFactura = numFactura;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }
}
