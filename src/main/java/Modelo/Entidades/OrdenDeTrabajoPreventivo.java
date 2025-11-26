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
public class OrdenDeTrabajoPreventivo extends OrdenDeTrabajo implements java.io.Serializable
{
    private ArrayList<FallaObservada> fallasObservadas;

    public OrdenDeTrabajoPreventivo()
    {
        this.fallasObservadas = null;
    }


    public OrdenDeTrabajoPreventivo(ArrayList<FallaObservada> fallasObservadas)
    {
        this.fallasObservadas = fallasObservadas;
    }


    public void setFallasObservadas(ArrayList<FallaObservada> fallasObservadas)
    {
        this.fallasObservadas = fallasObservadas;
    }

    public ArrayList<FallaObservada> getFallasObservadas()
    {
        return this.fallasObservadas;
    }
    
}
