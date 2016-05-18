package com.adis.srm.sistemarepartomovil.entity;

import com.orm.SugarRecord;
import com.orm.dsl.Unique;

import java.math.BigDecimal;
import java.math.MathContext;

/**
 * Created by Equipo on 06/05/2016.
 */
public class Producto extends SugarRecord {

    @Unique
    Long idProducto;
    String nombre;
    Long cantidad;
    BigDecimal costoUnitario;
    BigDecimal costoTotal;
    BigDecimal descuento;
    BigDecimal iva;
    String descripcion;
    Pedido pedido;

    public static final BigDecimal ivaPorcentaje = new BigDecimal(13);
    public static final BigDecimal ONE_HUNDRED = new BigDecimal(100);

    public Producto(){}

    public Producto(Long id, String nombre, Long cantidad, BigDecimal costoUnitario,
                    BigDecimal costoTotal, BigDecimal descuento, String descripcion, Pedido pedido) {
        this.idProducto = id;
        this.nombre = nombre;
        this.cantidad = cantidad;
        this.costoUnitario = costoUnitario;
        this.costoTotal = costoTotal;
        this.descuento = descuento;
        this.descripcion = descripcion;
        this.pedido = pedido;
        setIva(costoTotal);
    }


    public Long getIdProducto() {
        return idProducto;
    }


    public void setIdProducto(Long id) {
        this.idProducto = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Long getCantidad() {
        return cantidad;
    }

    public void setCantidad(Long cantidad) {
        this.cantidad = cantidad;
    }

    public BigDecimal getCostoUnitario() {
        return costoUnitario;
    }

    public void setCostoUnitario(BigDecimal costoUnitario) {
        this.costoUnitario = costoUnitario;
    }

    public BigDecimal getDescuento() {
        return descuento;
    }

    public void setDescuento(BigDecimal descuento) {
        this.descuento = descuento;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    public BigDecimal getCostoTotal() {
        return costoTotal;
    }

    public void setCostoTotal(BigDecimal costoTotal) {
        this.costoTotal = costoTotal;
    }

    public BigDecimal getIva() {
        return iva;
    }

    public void setIva(BigDecimal costoTotal) {
        this.iva = costoTotal.multiply(ivaPorcentaje).divide(ONE_HUNDRED).setScale(2, BigDecimal.ROUND_CEILING);
    }
}
