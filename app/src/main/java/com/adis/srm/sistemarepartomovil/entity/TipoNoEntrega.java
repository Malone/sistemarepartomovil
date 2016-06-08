package com.adis.srm.sistemarepartomovil.entity;

import com.orm.SugarRecord;

/**
 * Created by Equipo on 20/05/2016.
 */
public class TipoNoEntrega extends SugarRecord{

    String idTipoNoEntrega;
    String tipo;

    public TipoNoEntrega() {
    }

    public TipoNoEntrega(String idTipoNoEntrega, String tipo) {
        this.idTipoNoEntrega = idTipoNoEntrega;
        this.tipo = tipo;
    }

    public String getIdTipoNoEntrega() {
        return idTipoNoEntrega;
    }

    public void setIdTipoNoEntrega(String idTipoNoEntrega) {
        this.idTipoNoEntrega = idTipoNoEntrega;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}
