package Modelo.Metodos;

import Modelo.Entidades.TipoEquipo;
import Utilidades.Archivos;

import javax.swing.*;
import java.util.ArrayList;

public class TipoEquipoM {
    private ArrayList<TipoEquipo> listaTiposEquipos = new ArrayList<>();
    private Archivos<ArrayList<TipoEquipo>> arch = new Archivos<>();

    public TipoEquipoM(){
        listaTiposEquipos = arch.LeerArchivo("tiposEquipos");
    }
    public boolean Nuevo(TipoEquipo tipo){
        try{
            listaTiposEquipos.add(tipo);
            arch.EscribirArchivo("tiposEquipos", listaTiposEquipos);
            return true;
        }catch(Exception e){
            JOptionPane.showMessageDialog(null,"Error al guardar el tipo de equipo: "+e.getMessage()+"\n"+e.toString(), "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
    public TipoEquipo Buscar(int id){
        for (TipoEquipo t : listaTiposEquipos){
            if(t.getId()==id)
                return t;
        }
        return null;
    }
    public ArrayList<TipoEquipo> Buscar(){
        return listaTiposEquipos;
    }
    public boolean Editar(TipoEquipo tipo){
        TipoEquipo t = Buscar(tipo.getId());
        if(t!=null){
            listaTiposEquipos.set(listaTiposEquipos.indexOf(t), tipo);
            arch.EscribirArchivo("tiposEquipos", listaTiposEquipos);
            return true;
        }else{
            JOptionPane.showMessageDialog(null, "Error: Tipo de equipo no encontrado.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
    public boolean Eliminar(int id){
        TipoEquipo t = Buscar(id);
        if(t!=null){
            listaTiposEquipos.remove(t);
            arch.EscribirArchivo("tiposEquipos", listaTiposEquipos);
            return true;
        }else{
            JOptionPane.showMessageDialog(null, "Error: Tipo de equipo no encontrado.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
}
