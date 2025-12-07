package Controlador;

import Modelo.Entidades.Equipo;
import Modelo.Entidades.Falla;
import Modelo.Entidades.OrdenDeTrabajo;
import Modelo.Entidades.OrdenDeTrabajoCorrectivo;
import Modelo.Metodos.EquipoM;
import Modelo.Metodos.FallaM;
import Modelo.Metodos.OrdenDeTrabajoM;

import java.util.ArrayList;
import java.util.Date;

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
    public boolean Cerrar(OrdenDeTrabajoCorrectivo otc)
    {
        otc.setFechaFinalizacion(new Date());
        return metodos.Editar(otc);
    }
    
    public boolean Iniciar(int id){
        OrdenDeTrabajoCorrectivo otc = (OrdenDeTrabajoCorrectivo)metodos.Buscar(id);
        otc.setFechaInicio(new Date());
        return metodos.Editar(otc);
    }

    public boolean Cancelar(int id, String motivo)
    {
        //Equipo equipo = GetEquipo(id);
        OrdenDeTrabajoCorrectivo otc = (OrdenDeTrabajoCorrectivo)metodos.Buscar(id);
        otc.setFechaCancelacion(new Date());
        return metodos.Editar(otc);
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
    
    public boolean Agregar(OrdenDeTrabajoCorrectivo otc)
    {
        otc.setFechaCreacion(new Date());
        otc.setId(GetUltimoId()+1);
        return metodos.Nuevo(otc);
    }
    
    public boolean Actualizar(OrdenDeTrabajoCorrectivo otc)
    {
        return metodos.Editar(otc);
    }
    
    public int GetUltimoId(){
        return metodos.Buscar().getLast().getId();
    }
}
