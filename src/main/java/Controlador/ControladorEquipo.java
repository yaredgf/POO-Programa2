package Controlador;

import Modelo.Entidades.Equipo;
import Modelo.Entidades.Tarea;
import Modelo.Entidades.TipoEquipo;
import Modelo.Metodos.EquipoM;
import Modelo.Metodos.TareaM;
import Modelo.Metodos.TipoEquipoM;

import javax.swing.*;
import java.util.ArrayList;

public class ControladorEquipo {
    EquipoM metodos;
    public ControladorEquipo(){
        metodos = new EquipoM();
    }

    public boolean Editar(Equipo equipo){
        //Validacion de datos
        boolean existeEqPrincipalP = false;
        if(equipo.getId() == equipo.getIdEquipoPrincipal()){
            JOptionPane.showMessageDialog(null, "Error: No sea bruto xd.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if(equipo.getIdEquipoPrincipal() != 0){
            for(Equipo e : BuscarTodos()){
                if(e.getId() == equipo.getIdEquipoPrincipal()){
                    existeEqPrincipalP = true;
                    break;
                }
            }
        }
        else{
            existeEqPrincipalP = true;
        }

        if(!existeEqPrincipalP){
            JOptionPane.showMessageDialog(null, "Error: El equipo principal no existe.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if(equipo.getFechaPuestaEnServicio() != null) {
            if (equipo.getFechaPuestaEnServicio().before(equipo.getFechaAdquisicion())) {
                JOptionPane.showMessageDialog(null, "Error: La fecha de puesta en servicio no puede ser antes de la fecha de adquisición.", "Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        }

        if(equipo.getMesesVidaUtil() <= 0 || equipo.getCostoInicial() <= 0){
            JOptionPane.showMessageDialog(null, "Los meses de vida útil y el costo inicial deben ser mayores a 0.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        return metodos.Editar(equipo);
    }
    public boolean Registrar(Equipo equipo){
        //Validacion de datos
        boolean existeEqPrincipalP = false;
        if(equipo.getIdEquipoPrincipal() != 0){
            for(Equipo e : BuscarTodos()){
                if(e.getId() == equipo.getIdEquipoPrincipal()){
                    existeEqPrincipalP = true;
                    break;
                }
            }
        }
        else{
            existeEqPrincipalP = true;
        }
        if(!existeEqPrincipalP){
            JOptionPane.showMessageDialog(null, "Error: El equipo principal no existe.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if(equipo.getFechaPuestaEnServicio() != null) {
            if (equipo.getFechaPuestaEnServicio().before(equipo.getFechaAdquisicion())) {
                JOptionPane.showMessageDialog(null, "Error: La fecha de puesta en servicio no puede ser antes de la fecha de adquisición.", "Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        }
        if(equipo.getMesesVidaUtil() <= 0 || equipo.getCostoInicial() <= 0){
            JOptionPane.showMessageDialog(null, "Los meses de vida útil y el costo inicial deben ser mayores a 0.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        equipo.setId(metodos.GetUltimoId()+1);
        
        
        
        return metodos.Nuevo(equipo);
    }
    public Equipo Buscar(int id){
        return metodos.Buscar(id);
    }
    public ArrayList<Equipo> BuscarTodos(){
        return metodos.Buscar();
    }
    public void EliminarEquipo(int id){
        for(Equipo e : BuscarTodos()){
            if(e.getIdEquipoPrincipal() == id){
                JOptionPane.showMessageDialog(null, "Error: No se puede eliminar un equipo que tenga sub-equipos.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }
        metodos.Eliminar(id);
    }
    public boolean GuardarFases(Equipo equipo){
        return Editar(equipo);
    }
    public ArrayList<Tarea> BuscarTodasTareas(){
        TareaM tm = new TareaM();
        return tm.Buscar();
    }

    public String GetTipoEquipo(int id){
        TipoEquipoM tm = new TipoEquipoM();
        TipoEquipo t = tm.Buscar(id);
        if(t == null) return "";
        return t.getDescripcion();
    }
}
