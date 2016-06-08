package com.adis.srm.sistemarepartomovil.entity;

import com.orm.SugarRecord;

/**
 * Created by Equipo on 01/06/2016.
 */
public class TransaccionItem extends SugarRecord {

    String numFactura;
    Long numProducto;
    Long cantidad;

    public TransaccionItem() {
    }

    public TransaccionItem(String numFactura, Long numProducto, Long cantidad) {
        this.numFactura = numFactura;
        this.numProducto = numProducto;
        this.cantidad = cantidad;
    }

    public String getNumFactura() {
        return numFactura;
    }

    public void setNumFactura(String numFactura) {
        this.numFactura = numFactura;
    }

    public Long getNumProducto() {
        return numProducto;
    }

    public void setNumProducto(Long idProducto) {
        this.numProducto = idProducto;
    }

    public Long getCantidad() {
        return cantidad;
    }

    public void setCantidad(Long cantidad) {
        this.cantidad = cantidad;
    }
}
