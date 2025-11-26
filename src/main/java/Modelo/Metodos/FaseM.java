package Modelo.Metodos;

import Modelo.Entidades.Fase;
import Utilidades.Archivos;

import javax.swing.*;
import java.util.ArrayList;

public class FaseM {
    private ArrayList<Fase> listaFases = new ArrayList<>();
    private Archivos<ArrayList<Fase>> arch = new Archivos<>();
    public FaseM(){
        listaFases = arch.LeerArchivo("fases");
    }
    public boolean Nuevo(Fase fase){
        try{
            listaFases.add(fase);
            arch.EscribirArchivo("fases", listaFases);
            return true;
        }catch(Exception e){
            JOptionPane.showMessageDialog(null,"Error al guardar la fase: "+e.getMessage()+"\n"+e.toString(), "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
    public Fase Buscar(int id){
        for (Fase f : listaFases){
            if(f.getId()==id)
                return f;
        }
        return null;
    }
    public ArrayList<Fase> Buscar(){
        return listaFases;
    }
    public boolean Editar(Fase fase){
        Fase f = Buscar(fase.getId());
        if(f!=null){
            listaFases.set(listaFases.indexOf(f), fase);
            arch.EscribirArchivo("fases", listaFases);
            return true;
        }
        else{
            JOptionPane.showMessageDialog(null, "Error: Fase no encontrada.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

    }
    public boolean Eliminar(int id){
        Fase f = Buscar(id);
        if(f!=null){
            listaFases.remove(f);
            arch.EscribirArchivo("fases", listaFases);
            return true;
        }
        else{
            JOptionPane.showMessageDialog(null, "Error: Fase no encontrada.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

    }
}
