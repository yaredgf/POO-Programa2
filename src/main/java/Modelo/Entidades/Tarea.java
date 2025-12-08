/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo.Entidades;

/**
 *
 * @author Usuario
 */
public class Tarea implements java.io.Serializable
{
    private int id;
    private String descripcion;

    public Tarea()
    {
        this.id = -1;
        this.descripcion = null;
    }


    public Tarea(int id, String descripcion)
    {
        this.id = id;
        this.descripcion = descripcion;
    }


    public void setId(int id)
    {
        this.id = id;
    }

    public int getId()
    {
        return this.id;
    }
    public void setDescripcion(String descripcion)
    {
        this.descripcion = descripcion;
    }

    public String getDescripcion()
    {
        return this.descripcion;
    }
    public String toString(){return this.id+" "+this.descripcion;}
}
