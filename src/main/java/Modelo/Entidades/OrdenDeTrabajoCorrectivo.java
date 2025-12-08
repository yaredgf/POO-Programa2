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
    private ArrayList<FallaObservada> fallasReportadas;
    private ArrayList<FallaObservada> fallasEncontradas;

    public OrdenDeTrabajoCorrectivo()
    {
        this.fallasReportadas = new ArrayList<FallaObservada>();
        this.fallasEncontradas = new ArrayList<FallaObservada>();
    }


    public OrdenDeTrabajoCorrectivo(ArrayList<FallaObservada> fallasReportadas, ArrayList<FallaObservada> fallasEncontradas)
    {
        this.fallasReportadas = fallasReportadas;
        this.fallasEncontradas = fallasEncontradas;
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
