package Modelo.Metodos;

import Modelo.Entidades.Falla;
import Utilidades.Archivos;

import javax.swing.*;
import java.util.ArrayList;

public class FallaM {
    private Archivos<ArrayList<Falla>> arch = new Archivos<>();
    private ArrayList<Falla> listaFallas = new ArrayList<>();
    public FallaM(){
        listaFallas = arch.LeerArchivo("fallas");
    }

    public boolean Nuevo(Falla falla){
        try{
            listaFallas.add(falla);
            arch.EscribirArchivo("fallas", listaFallas);
            return true;
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null,"Error al guardar la falla: "+e.getMessage()+"\n"+e.toString(), "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    public Falla Buscar(int id){
        Falla retVal = null;
        for (Falla f : listaFallas) {
            if(f.getId()==id)
                retVal = f;
        }
        return retVal;
    }

    public boolean Editar(Falla falla){
        Falla f = Buscar(falla.getId());
        if(f!=null){
            listaFallas.set(listaFallas.indexOf(f), falla);
            arch.EscribirArchivo("fallas", listaFallas);
            return true;
        }
        else{
            JOptionPane.showMessageDialog(null, "Error: Falla no encontrada.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    public boolean Eliminar(int id){
        Falla f = Buscar(id);
        if(f!=null){
            listaFallas.remove(f);
            arch.EscribirArchivo("fallas", listaFallas);
            return true;
        }
        else{
            JOptionPane.showMessageDialog(null, "Error: Falla no encontrada.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    public ArrayList<Falla> Buscar(){
        return listaFallas;
    }
}
