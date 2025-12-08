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
public class Fase implements java.io.Serializable
{
    private int id;
    private String tipoFrecuencia;
    private Frecuencia frecuencia;
    private int cantidadCiclos;
    private ArrayList<Tarea> listaTareas;
    private String partes;
    private String equipoParaMantenimiento;
    private String personalEncargado;
    private float horasEstimadas;

    public Fase()
    {
        this.id = -1;
        this.tipoFrecuencia = null;
        this.frecuencia = null;
        this.cantidadCiclos = 0;
        this.listaTareas = null;
        this.partes = null;
        this.equipoParaMantenimiento = null;
        this.personalEncargado = null;
        this.horasEstimadas = 0;
    }


    public Fase(int id, String tipoFrecuencia, Frecuencia frecuencia, int cantidadCiclos, ArrayList<Tarea> listaTareas, String partes, String equipoParaMantenimiento, String personalEncargado, float horasEstimadas)
    {
        this.id = id;
        this.tipoFrecuencia = tipoFrecuencia;
        this.frecuencia = frecuencia;
        this.cantidadCiclos = cantidadCiclos;
        this.listaTareas = listaTareas;
        this.partes = partes;
        this.equipoParaMantenimiento = equipoParaMantenimiento;
        this.personalEncargado = personalEncargado;
        this.horasEstimadas = horasEstimadas;
    }


    public void setId(int id)
    {
        this.id = id;
    }

    public int getId()
    {
        return this.id;
    }
    public void setTipoFrecuencia(String tipoFrecuencia)
    {
        this.tipoFrecuencia = tipoFrecuencia;
    }

    public String getTipoFrecuencia()
    {
        return this.tipoFrecuencia;
    }
    public void setFrecuencia(Frecuencia frecuencia)
    {
        this.frecuencia = frecuencia;
    }

    public Frecuencia getFrecuencia()
    {
        return this.frecuencia;
    }
    public void setCantidadCiclos(int cantidadCiclos)
    {
        this.cantidadCiclos = cantidadCiclos;
    }

    public int getCantidadCiclos()
    {
        return this.cantidadCiclos;
    }
    public void setListaTareas(ArrayList<Tarea> listaTareas)
    {
        this.listaTareas = listaTareas;
    }

    public ArrayList<Tarea> getListaTareas()
    {
        return this.listaTareas;
    }
    public void setPartes(String partes)
    {
        this.partes = partes;
    }

    public String getPartes()
    {
        return this.partes;
    }
    public void setEquipoParaMantenimiento(String equipoParaMantenimiento)
    {
        this.equipoParaMantenimiento = equipoParaMantenimiento;
    }

    public String getEquipoParaMantenimiento()
    {
        return this.equipoParaMantenimiento;
    }
    public void setPersonalEncargado(String personalEncargado)
    {
        this.personalEncargado = personalEncargado;
    }

    public String getPersonalEncargado()
    {
        return this.personalEncargado;
    }
    public void setHorasEstimadas(float horasEstimadas)
    {
            this.horasEstimadas = horasEstimadas;
    }

    public float getHorasEstimadas()
    {
        return this.horasEstimadas;
    }
}
