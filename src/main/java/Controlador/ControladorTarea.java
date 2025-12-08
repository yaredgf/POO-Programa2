package Controlador;

import Modelo.Entidades.Falla;
import Modelo.Entidades.Tarea;
import Modelo.Metodos.TareaM;

import java.util.ArrayList;

public class ControladorTarea {
    TareaM metodos = new TareaM();

    public boolean Editar(Tarea tarea) {
        boolean existeEqPrincipalP = false;
        return this.metodos.Editar(tarea);
    }

    public boolean Registrar(Tarea tarea) {
        boolean existeEqPrincipalP = false;
        tarea.setId(this.metodos.GetUltimoId() + 1);
        return this.metodos.Nuevo(tarea);
    }

    public Tarea Buscar(int id) {
        return this.metodos.Buscar(id);
    }

    public ArrayList<Tarea> BuscarTodos() {
        return this.metodos.Buscar();
    }

    public void Eliminar(int id) {
        this.metodos.Eliminar(id);
    }
}
