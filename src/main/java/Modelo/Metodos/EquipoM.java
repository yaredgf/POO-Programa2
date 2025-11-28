package Modelo.Metodos;

import Modelo.Entidades.Equipo;
import Utilidades.Archivos;

import javax.swing.*;
import java.util.ArrayList;

public class EquipoM {
    private Archivos<ArrayList<Equipo>> arch = new Archivos<>();
    private ArrayList<Equipo> listaEquipos = new ArrayList<>();
    public EquipoM(){
        listaEquipos = arch.LeerArchivo("equipos");
    }

    public boolean Nuevo(Equipo equipo){
        try{
            listaEquipos.add(equipo);
            arch.EscribirArchivo("equipos", listaEquipos);
            return true;
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null,"Error al guardar el equipo: "+e.getMessage()+"\n"+e.toString(), "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    public Equipo Buscar(int id){
        Equipo retVal = null;
        for (Equipo e : listaEquipos) {
            if(e.getId()==id)
                retVal = e;
        }
        return retVal;
    }

    public boolean Editar(Equipo equipo){
        Equipo e = Buscar(equipo.getId());
        if(e!=null){
            listaEquipos.set(listaEquipos.indexOf(e), equipo);
            arch.EscribirArchivo("equipos", listaEquipos);
            return true;
        }
        else{
            JOptionPane.showMessageDialog(null, "Error: Equipo no encontrado.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    public boolean Eliminar(int id){
        Equipo e = Buscar(id);
        if(e!=null){
            listaEquipos.remove(e);
            arch.EscribirArchivo("equipos", listaEquipos);
            return true;
        }
        else{
            JOptionPane.showMessageDialog(null, "Error: Equipo no encontrado.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    public ArrayList<Equipo> Buscar(){
        return listaEquipos;
    }

    public int GetUltimoId(){
        return listaEquipos.get(listaEquipos.size()-1).getId();
    }
}
