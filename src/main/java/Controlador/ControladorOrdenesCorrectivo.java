package Controlador;

import Modelo.Entidades.Equipo;
import Modelo.Entidades.Falla;
import Modelo.Entidades.OrdenDeTrabajo;
import Modelo.Entidades.OrdenDeTrabajoCorrectivo;
import Modelo.Metodos.EquipoM;
import Modelo.Metodos.FallaM;
import Modelo.Metodos.OrdenDeTrabajoM;

import java.util.ArrayList;

public class ControladorOrdenesCorrectivo {
    OrdenDeTrabajoM metodos;
    public ControladorOrdenesCorrectivo() {
        metodos = new  OrdenDeTrabajoM();
    }
    public ArrayList<OrdenDeTrabajoCorrectivo> BuscarTodos(){
        ArrayList<OrdenDeTrabajoCorrectivo> ret = new ArrayList<>();
        ArrayList<OrdenDeTrabajo> lista = metodos.Buscar();
        for(OrdenDeTrabajo o : lista){
            if(o instanceof OrdenDeTrabajoCorrectivo){
                ret.add((OrdenDeTrabajoCorrectivo)o);
            }
        }
        return ret;
    }
    public ArrayList<OrdenDeTrabajoCorrectivo> Filtrar(String filtro){
        ArrayList<OrdenDeTrabajoCorrectivo> ret = new ArrayList<>();
        //TODO
        return ret;
    }
    public void Cerrar(OrdenDeTrabajoCorrectivo o){
        //TODO
    }
    public void Cancelar(int id, String motivo){
        //TODO
    }
    public Equipo GetEquipo(int id){
        EquipoM m = new EquipoM();
        return m.Buscar(id);
    }
    public ArrayList<Equipo> BuscarTodosEquipos(){
        EquipoM m = new EquipoM();
        return m.Buscar();
    }
    public ArrayList<Falla> BuscarTodasFallas(){
        FallaM m = new FallaM();
        return m.Buscar();
    }
    public void Agregar(OrdenDeTrabajoCorrectivo o){
        o.setId(GetUltimoId()+1);
        //TODO
    }
    public int GetUltimoId(){
        return metodos.Buscar().getLast().getId();
    }
}
