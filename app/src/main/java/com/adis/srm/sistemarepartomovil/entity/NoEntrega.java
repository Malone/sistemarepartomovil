package com.adis.srm.sistemarepartomovil.entity;

import com.orm.SugarRecord;

/**
 * Created by Equipo on 20/05/2016.
 */
public class NoEntrega extends SugarRecord{

    String idNoEntrega;
    String tipo;

    public NoEntrega() {
    }

    public NoEntrega(String idNoEntrega, String tipo) {
        this.idNoEntrega = idNoEntrega;
        this.tipo = tipo;
    }

    public String getIdNoEntrega() {
        return idNoEntrega;
    }

    public void setIdNoEntrega(String idNoEntrega) {
        this.idNoEntrega = idNoEntrega;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}
