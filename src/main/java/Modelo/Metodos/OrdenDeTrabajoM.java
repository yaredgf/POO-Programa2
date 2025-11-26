package Modelo.Metodos;

import Modelo.Entidades.OrdenDeTrabajo;
import Utilidades.Archivos;

import javax.swing.*;
import java.util.ArrayList;

public class OrdenDeTrabajoM {
    private ArrayList<OrdenDeTrabajo> listaOrdenesDeTrabajo = new ArrayList<>();
    private Archivos<ArrayList<OrdenDeTrabajo>> arch = new Archivos<>();

    public OrdenDeTrabajoM(){
        listaOrdenesDeTrabajo = arch.LeerArchivo("ordenesDeTrabajo");
    }

    public boolean Nuevo(OrdenDeTrabajo orden){
        try{
            listaOrdenesDeTrabajo.add(orden);
            arch.EscribirArchivo("ordenesDeTrabajo", listaOrdenesDeTrabajo);
            return true;
        }catch(Exception e){
            JOptionPane.showMessageDialog(null,"Error al guardar la orden de trabajo: "+e.getMessage()+"\n"+e.toString(), "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
    public OrdenDeTrabajo Buscar(int id){
        for (OrdenDeTrabajo o : listaOrdenesDeTrabajo){
            if(o.getId()==id)
                return o;
        }
        return null;
    }
    public ArrayList<OrdenDeTrabajo> Buscar(){
        return listaOrdenesDeTrabajo;
    }
    public boolean Editar(OrdenDeTrabajo orden){
        OrdenDeTrabajo o = Buscar(orden.getId());
        if(o!=null){
            listaOrdenesDeTrabajo.set(listaOrdenesDeTrabajo.indexOf(o), orden);
            arch.EscribirArchivo("ordenesDeTrabajo", listaOrdenesDeTrabajo);
            return true;
        }else{
            JOptionPane.showMessageDialog(null, "Error: Orden de trabajo no encontrada.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
    public boolean Eliminar(int id){
        OrdenDeTrabajo o = Buscar(id);
        if(o!=null){
            listaOrdenesDeTrabajo.remove(o);
            arch.EscribirArchivo("ordenesDeTrabajo", listaOrdenesDeTrabajo);
            return true;
        }else{
            JOptionPane.showMessageDialog(null, "Error: Orden de trabajo no encontrada.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
}
