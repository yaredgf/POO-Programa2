//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package Controlador;

import Modelo.Entidades.Falla;
import Modelo.Metodos.FallaM;
import java.util.ArrayList;

public class ControladorFalla {
    FallaM metodos = new FallaM();

    public boolean Editar(Falla falla) {
        boolean existeEqPrincipalP = false;
        return this.metodos.Editar(falla);
    }

    public boolean Registrar(Falla falla) {
        boolean existeEqPrincipalP = false;
        falla.setId(this.metodos.GetUltimoId() + 1);
        return this.metodos.Nuevo(falla);
    }

    public Falla Buscar(int id) {
        return this.metodos.Buscar(id);
    }

    public ArrayList<Falla> BuscarTodos() {
        return this.metodos.Buscar();
    }

    public void Eliminar(int id) {
        this.metodos.Eliminar(id);
    }
}
