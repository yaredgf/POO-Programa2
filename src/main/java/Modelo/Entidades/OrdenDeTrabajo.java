package Modelo.Entidades;
import java.util.ArrayList;
import java.util.Date;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Usuario
 */
public class OrdenDeTrabajo implements java.io.Serializable
{
    private int id;
    private int idEquipo;
    private Date fechaInicio;
    private Date fechaFinalizacion;
    private float horasTrabajo;
    private int costoManoObra;
    private int costoEquipoMaterial;
    private String observaciones;
    private Frecuencia frecuencia;
    private int cantidadCiclos;
    private ArrayList<String> listaTareas;
    private String partes;
    private String personalEncargado;

    public OrdenDeTrabajo()
    {
        this.id = -1;
        this.idEquipo = 0;
        this.fechaInicio = null;
        this.fechaFinalizacion = null;
        this.horasTrabajo = 0;
        this.costoManoObra = 0;
        this.costoEquipoMaterial = 0;
        this.observaciones = null;
    }


    public OrdenDeTrabajo(int id, int idEquipo, Date fechaInicio, Date fechaFinalizacion, float horasTrabajo, int costoManoObra, int costoEquipoMaterial, String observaciones)
    {
        this.id = id;
        this.idEquipo = idEquipo;
        this.fechaInicio = fechaInicio;
        this.fechaFinalizacion = fechaFinalizacion;
        this.horasTrabajo = horasTrabajo;
        this.costoManoObra = costoManoObra;
        this.costoEquipoMaterial = costoEquipoMaterial;
        this.observaciones = observaciones;
    }


    public void setId(int id)
    {
        this.id = id;
    }

    public int getId()
    {
        return this.id;
    }
    public void setIdEquipo(int idEquipo)
    {
        this.idEquipo = idEquipo;
    }

    public int getIdEquipo()
    {
        return this.idEquipo;
    }
    public void setFechaInicio(Date fechaInicio)
    {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaInicio()
    {
           
        return this.fechaInicio;
    }
    public void setFechaFinalizacion(Date fechaFinalizacion)
    {
        this.fechaFinalizacion = fechaFinalizacion;
    }

    public Date getFechaFinalizacion()
    {
        return this.fechaFinalizacion;
    }
    public void setHorasTrabajo(float horasTrabajo)
    {
        this.horasTrabajo = horasTrabajo;
    }

    public float getHorasTrabajo()
    {
        return this.horasTrabajo;
    }
    public void setCostoManoObra(int costoManoObra)
    {
        this.costoManoObra = costoManoObra;
    }

    public int getCostoManoObra()
    {
        return this.costoManoObra;
    }
    public void setCostoEquipoMaterial(int costoEquipoMaterial)
    {
        this.costoEquipoMaterial = costoEquipoMaterial;
    }

    public int getCostoEquipoMaterial()
    {
        return this.costoEquipoMaterial;
    }
    public void setObservaciones(String observaciones)
    {
        this.observaciones = observaciones;
    }

    public String getObservaciones()
    {
        return this.observaciones;
    }
    public Frecuencia getFrecuencia() {
        return frecuencia;
    }

    public void setFrecuencia(Frecuencia frecuencia) {
        this.frecuencia = frecuencia;
    }

    public int getCantidadCiclos() {
        return cantidadCiclos;
    }

    public void setCantidadCiclos(int cantidadCiclos) {
        this.cantidadCiclos = cantidadCiclos;
    }

    public ArrayList<String> getListaTareas() {
        return listaTareas;
    }

    public void setListaTareas(ArrayList<String> listaTareas) {
        this.listaTareas = listaTareas;
    }

    public String getPartes() {
        return partes;
    }

    public void setPartes(String partes) {
        this.partes = partes;
    }

    public String getPersonalEncargado() {
        return personalEncargado;
    }

    public void setPersonalEncargado(String personalEncargado) {
        this.personalEncargado = personalEncargado;
    }

}
