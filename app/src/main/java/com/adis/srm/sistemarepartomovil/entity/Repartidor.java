package com.adis.srm.sistemarepartomovil.entity;

import com.orm.SugarRecord;

/**
 * Created by Equipo on 06/05/2016.
 */
public class Repartidor extends SugarRecord {

    Long repartidorIdent;
    String nombreApellido;
    Long ruta;
    Long almacen;
    String fechaConsulta;

    public Repartidor() {
    }

    public Repartidor(Long idRepartidor, String nombre, Long ruta, Long almacen, String fechaConsulta) {
        this.repartidorIdent = idRepartidor;
        this.nombreApellido = nombre;
        this.ruta = ruta;
        this.almacen = almacen;
        this.fechaConsulta = fechaConsulta;
    }

    public Long getIdRepartidor() {
        return repartidorIdent;
    }

    public void setIdRepartidor(Long idRepartidor) {
        this.repartidorIdent = idRepartidor;
    }

    public String getNombre() {
        return nombreApellido;
    }

    public void setNombre(String nombre) {
        this.nombreApellido = nombre;
    }

    public Long getRuta() {
        return ruta;
    }

    public void setRuta(Long ruta) {
        this.ruta = ruta;
    }

    public Long getAlmacen() {
        return almacen;
    }

    public void setAlmacen(Long almacen) {
        this.almacen = almacen;
    }

    public String getFechaConsulta() {
        return fechaConsulta;
    }

    public void setFechaConsulta(String fechaConsulta) {
        this.fechaConsulta = fechaConsulta;
    }
}
