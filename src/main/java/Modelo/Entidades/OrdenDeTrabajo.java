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
    private String observacionesIniciales;
    private String observacionesFinales;
    private Date fechaCancelacion;
    private String motivoCancelacion;

    public OrdenDeTrabajo()
    {
        this.id = -1;
        this.idEquipo = 0;
        this.fechaInicio = null;
        this.fechaFinalizacion = null;
        this.horasTrabajo = 0;
        this.costoManoObra = 0;
        this.costoEquipoMaterial = 0;
        this.observacionesIniciales = null;
        this.observacionesFinales = null;
        this.fechaCancelacion = null;
        this.motivoCancelacion = null;
    }


    public OrdenDeTrabajo(int id, int idEquipo, Date fechaInicio, Date fechaFinalizacion, float horasTrabajo, int costoManoObra, int costoEquipoMaterial, String observacionesIniciales, String observacionesFinales, Date fechaCancelacion, String motivoCancelacion)
    {
        this.id = id;
        this.idEquipo = idEquipo;
        this.fechaInicio = fechaInicio;
        this.fechaFinalizacion = fechaFinalizacion;
        this.horasTrabajo = horasTrabajo;
        this.costoManoObra = costoManoObra;
        this.costoEquipoMaterial = costoEquipoMaterial;
        this.observacionesIniciales = observacionesIniciales;
        this.observacionesFinales = observacionesFinales;
        this.fechaCancelacion = fechaCancelacion;
        this.motivoCancelacion = motivoCancelacion;
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
    public void setObservacionesIniciales(String observacionesIniciales)
    {
        this.observacionesIniciales = observacionesIniciales;
    }

    public String getObservacionesIniciales()
    {
            return this.observacionesIniciales;
    }
    public void setObservacionesFinales(String observacionesFinales)
    {
        this.observacionesFinales = observacionesFinales;
    }

    public String getObservacionesFinales()
    {
            return this.observacionesFinales;
    }
    public void setFechaCancelacion(Date fechaCancelacion)
    {
        this.fechaCancelacion = fechaCancelacion;
    }

    public Date getFechaCancelacion()
    {
        return this.fechaCancelacion;
    }
    public void setMotivoCancelacion(String motivoCancelacion)
    {
        this.motivoCancelacion = motivoCancelacion;
    }

    public String getMotivoCancelacion()
    {
        return this.motivoCancelacion;
    }

    public String toString()
    {
        return "Orden "+this.id;
    }
}
