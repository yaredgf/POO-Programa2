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
    private int idFase;

    public OrdenDeTrabajoPreventivo()
    {
        this.fallasObservadas = null;
        this.idFase = -1;
    }


    public OrdenDeTrabajoPreventivo(ArrayList<FallaObservada> fallasObservadas, int idFase)
    {
        this.fallasObservadas = fallasObservadas;
        this.idFase = idFase;
    }


    public void setFallasObservadas(ArrayList<FallaObservada> fallasObservadas)
    {
        this.fallasObservadas = fallasObservadas;
    }

    public ArrayList<FallaObservada> getFallasObservadas()
    {
        return this.fallasObservadas;
    }
    
    public void setIdFase(int idFase)
    {
        this.idFase = idFase;
    }

    public int getIdFase()
    {
        return this.idFase;
    }
}
