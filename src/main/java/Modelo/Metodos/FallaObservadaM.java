package Modelo.Metodos;

import Modelo.Entidades.FallaObservada;
import Utilidades.Archivos;

import javax.swing.*;
import java.util.ArrayList;

public class FallaObservadaM {
    private ArrayList<FallaObservada> listaFallasObservadas = new ArrayList<>();
    private Archivos<ArrayList<FallaObservada>> arch = new Archivos<>();
    public FallaObservadaM(){
        listaFallasObservadas = arch.LeerArchivo("fallasObservadas");
    }
    public boolean Nuevo(FallaObservada falla){
        try{
            listaFallasObservadas.add(falla);
            arch.EscribirArchivo("fallasObservadas", listaFallasObservadas);
            return true;
        }catch(Exception e){
            JOptionPane.showMessageDialog(null,"Error al guardar la falla observada: "+e.getMessage()+"\n"+e.toString(), "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
    public FallaObservada Buscar(int id){
        for (FallaObservada f : listaFallasObservadas) {
            if(f.getIdFalla()==id)
                return f;
        }
        return null;
    }
    public boolean Editar(FallaObservada falla){
        FallaObservada f = Buscar(falla.getIdFalla());
        if(f!=null){
            listaFallasObservadas.set(listaFallasObservadas.indexOf(f), falla);
            arch.EscribirArchivo("fallasObservadas", listaFallasObservadas);
            return true;
        }
        else{
            JOptionPane.showMessageDialog(null, "Error: Falla no encontrada.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
    public boolean Eliminar(int id){
        FallaObservada f = Buscar(id);
        if(f!=null){
            listaFallasObservadas.remove(f);
            arch.EscribirArchivo("fallasObservadas", listaFallasObservadas);
            return true;
        }
        else{
            JOptionPane.showMessageDialog(null, "Error: Falla no encontrada.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
    public ArrayList<FallaObservada> Buscar(){
        return listaFallasObservadas;
    }

}
