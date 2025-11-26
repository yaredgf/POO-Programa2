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
public class TipoEquipo implements java.io.Serializable
{
    private int id;
    private String descripcion;

    public TipoEquipo()
    {
        this.id = -1;
        this.descripcion = null;
    }


    public TipoEquipo(int id, String descripcion)
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
}
