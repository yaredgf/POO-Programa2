package Controlador;

import Modelo.Entidades.Equipo;
import Modelo.Metodos.EquipoM;

public class ControladorEquipo {
    EquipoM metodos;
    public ControladorEquipo(){
        metodos = new EquipoM();
    }
    public boolean RegistrarEquipo(Equipo equipo){
        return metodos.Nuevo(equipo);
    }

}
