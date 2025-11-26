/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo.Entidades;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author Usuario
 */
public class OrdenDeTrabajoCorrectivo extends OrdenDeTrabajo implements java.io.Serializable
{
    private Date fechaCreacion;
    private Date fechaEjecucion;
    private ArrayList<FallaObservada> fallasReportadas;
    private ArrayList<FallaObservada> fallasEncontradas;

    public OrdenDeTrabajoCorrectivo()
    {
        this.fechaCreacion = null;
        this.fechaEjecucion = null;
        this.fallasReportadas = null;
        this.fallasEncontradas = null;
    }


    public OrdenDeTrabajoCorrectivo(Date fechaCreacion, Date fechaEjecucion, ArrayList<FallaObservada> fallasReportadas, ArrayList<FallaObservada> fallasEncontradas)
    {
        this.fechaCreacion = fechaCreacion;
        this.fechaEjecucion = fechaEjecucion;
        this.fallasReportadas = fallasReportadas;
        this.fallasEncontradas = fallasEncontradas;
    }
    
    public void setFechaCreacion(Date fechaCreacion)
    {
        this.fechaCreacion = fechaCreacion;
    }

    public Date getFechaCreacion()
    {
        return this.fechaCreacion;
    }
    public void setFechaEjecucion(Date fechaEjecucion)
    {
        this.fechaEjecucion = fechaEjecucion;
    }

    public Date getFechaEjecucion()
    {
        return this.fechaEjecucion;
    }
    public void setFallasReportadas(ArrayList<FallaObservada> fallasReportadas)
    {
    
        this.fallasReportadas = fallasReportadas;
    }

    public ArrayList<FallaObservada> getFallasReportadas()
    {
        return this.fallasReportadas;
    }
    public void setFallasEncontradas(ArrayList<FallaObservada> fallasEncontradas)
    {
        this.fallasEncontradas = fallasEncontradas;
    }

    public ArrayList<FallaObservada> getFallasEncontradas()
    {
        return this.fallasEncontradas;
    }
}
