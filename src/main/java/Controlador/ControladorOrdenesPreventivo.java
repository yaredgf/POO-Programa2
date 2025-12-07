/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Modelo.Entidades.OrdenDeTrabajoPreventivo;
import java.util.ArrayList;

/**
 *
 * @author Usuario
 */
public class ControladorOrdenesPreventivo 
{
    public boolean Crear(OrdenDeTrabajoPreventivo otp)
    {
        
        return true;
    }
    
    public ArrayList<OrdenDeTrabajoPreventivo> Buscar()
    {
        
        return null;
    }
    
    public OrdenDeTrabajoPreventivo Buscar(int indice)
    {
        
        return null;
    }
    
    public boolean Actualizar(int indice)
    {
        
        return true;
    }
    
    public boolean Cerrar(int indice)
    {
        
        return true;
    }
    
    public boolean Cancelar(int indice)
    {
        
        return true;
    }
    
    public ArrayList<OrdenDeTrabajoPreventivo> Filtrar(String filtro){
        ArrayList<OrdenDeTrabajoPreventivo> ret = new ArrayList<>();
        //TODO
        return ret;
    }
    
}
