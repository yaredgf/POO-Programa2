//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package Modelo.Metodos;

import Modelo.Entidades.Falla;
import Utilidades.Archivos;
import java.awt.Component;
import java.util.ArrayList;
import javax.swing.JOptionPane;

public class FallaM {
    private Archivos<ArrayList<Falla>> arch = new Archivos();
    private ArrayList<Falla> listaFallas = new ArrayList();

    public FallaM() {
        this.listaFallas = (ArrayList)this.arch.LeerArchivo("fallas");
    }

    public boolean Nuevo(Falla falla) {
        try {
            this.listaFallas.add(falla);
            this.arch.EscribirArchivo("fallas", this.listaFallas);
            return true;
        } catch (Exception e) {
            JOptionPane.showMessageDialog((Component)null, "Error al guardar la falla: " + e.getMessage() + "\n" + e.toString(), "Error", 0);
            return false;
        }
    }

    public Falla Buscar(int id) {
        Falla retVal = null;

        for(Falla f : this.listaFallas) {
            if (f.getId() == id) {
                retVal = f;
            }
        }

        return retVal;
    }

    public boolean Editar(Falla falla) {
        Falla f = this.Buscar(falla.getId());
        if (f != null) {
            this.listaFallas.set(this.listaFallas.indexOf(f), falla);
            this.arch.EscribirArchivo("fallas", this.listaFallas);
            return true;
        } else {
            JOptionPane.showMessageDialog((Component)null, "Error: Falla no encontrada.", "Error", 0);
            return false;
        }
    }

    public boolean Eliminar(int id) {
        Falla f = this.Buscar(id);
        if (f != null) {
            this.listaFallas.remove(f);
            this.arch.EscribirArchivo("fallas", this.listaFallas);
            return true;
        } else {
            JOptionPane.showMessageDialog((Component)null, "Error: Falla no encontrada.", "Error", 0);
            return false;
        }
    }

    public ArrayList<Falla> Buscar() {
        return this.listaFallas;
    }

    public int GetUltimoId() {
        return ((Falla)this.listaFallas.get(this.listaFallas.size() - 1)).getId();
    }
}
