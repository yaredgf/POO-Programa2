package Modelo.Metodos;

import Modelo.Entidades.Tarea;
import Utilidades.Archivos;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class TareaM {
    private Archivos<ArrayList<Tarea>> arch = new Archivos();
    private ArrayList<Tarea> listaTareas = new ArrayList();

    public TareaM() {
        this.listaTareas = (ArrayList)this.arch.LeerArchivo("tareas");
    }

    public boolean Nuevo(Tarea tarea) {
        try {
            this.listaTareas.add(tarea);
            this.arch.EscribirArchivo("tareas", this.listaTareas);
            return true;
        } catch (Exception e) {
            JOptionPane.showMessageDialog((Component)null, "Error al guardar la tarea: " + e.getMessage() + "\n" + e.toString(), "Error", 0);
            return false;
        }
    }

    public Tarea Buscar(int id) {
        Tarea retVal = null;

        for(Tarea t : this.listaTareas) {
            if (t.getId() == id) {
                retVal = t;
            }
        }

        return retVal;
    }

    public boolean Editar(Tarea tarea) {
        Tarea t = this.Buscar(tarea.getId());
        if (t != null) {
            this.listaTareas.set(this.listaTareas.indexOf(t), tarea);
            this.arch.EscribirArchivo("tareas", this.listaTareas);
            return true;
        } else {
            JOptionPane.showMessageDialog((Component)null, "Error: Tarea no encontrada.", "Error", 0);
            return false;
        }
    }

    public boolean Eliminar(int id) {
        Tarea tarea = this.Buscar(id);
        if (tarea != null) {
            this.listaTareas.remove(tarea);
            this.arch.EscribirArchivo("tareas", this.listaTareas);
            return true;
        } else {
            JOptionPane.showMessageDialog((Component)null, "Error: Tarea no encontrada.", "Error", 0);
            return false;
        }
    }

    public ArrayList<Tarea> Buscar() {
        return this.listaTareas;
    }

    public int GetUltimoId() {
        return ((Tarea)this.listaTareas.get(this.listaTareas.size() - 1)).getId();
    }
}
