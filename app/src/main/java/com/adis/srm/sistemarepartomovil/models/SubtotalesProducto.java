package com.adis.srm.sistemarepartomovil.models;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

/**
 * Created by Equipo on 12/05/2016.
 */
public class SubtotalesProducto {



    BigDecimal subTotal;
    BigDecimal descuento;
    BigDecimal iva;
    BigDecimal totalNeto;

    public SubtotalesProducto() {
        subTotal = new BigDecimal(0);
        descuento = new BigDecimal(0);
        iva = new BigDecimal(0);
        totalNeto = new BigDecimal(0);
    }

    public SubtotalesProducto(BigDecimal subTotal, BigDecimal descuento, BigDecimal iva, BigDecimal totalNeto) {
        this.subTotal = subTotal;
        this.descuento = descuento;
        this.iva = iva;
        this.totalNeto = totalNeto;
    }

    public BigDecimal getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(BigDecimal subTotal) {
        this.subTotal = subTotal;
    }

    public BigDecimal getDescuento() {
        return descuento;
    }

    public void setDescuento(BigDecimal descuento) {
        this.descuento = descuento;
    }

    public BigDecimal getIva() {
        return iva;
    }

    public void setIva(BigDecimal iva) {
        this.iva = iva;
    }

    public BigDecimal getTotalNeto() {
        MathContext mc = new MathContext(5);
        return getSubTotal().add(getIva(), mc).subtract(getDescuento(), mc);
    }

    public void setTotalNeto(BigDecimal totalNeto) {
        this.totalNeto = totalNeto;
    }

    public void sumarSubTotal(BigDecimal amount){
        setSubTotal(getSubTotal().add(amount).setScale(2, RoundingMode.CEILING));
    }

    public void sumarDescuento(BigDecimal amount){
        setDescuento(getDescuento().add(amount).setScale(2, RoundingMode.CEILING));
    }

    public void sumarIVA(BigDecimal amount){
        setIva(getIva().add(amount).setScale(2, RoundingMode.CEILING));
    }
}