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
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javax.swing.JOptionPane;

public class ControladorOrdenesCorrectivo {
    OrdenDeTrabajoM metodos;
    public ControladorOrdenesCorrectivo() 
    {
        metodos = new  OrdenDeTrabajoM();
    }
    public ArrayList<OrdenDeTrabajoCorrectivo> BuscarTodos(){
        ArrayList<OrdenDeTrabajoCorrectivo> ret = new ArrayList<>();
        ArrayList<OrdenDeTrabajo> lista = metodos.Buscar();
        for(OrdenDeTrabajo o : lista)
        {
            if(o instanceof OrdenDeTrabajoCorrectivo)
            {
                ret.add((OrdenDeTrabajoCorrectivo)o);
            }
        }
        return ret;
    }
    public ArrayList<OrdenDeTrabajoCorrectivo> Filtrar(String filtro){
        ArrayList<OrdenDeTrabajoCorrectivo> lista = BuscarTodos();
        ArrayList<OrdenDeTrabajoCorrectivo> ret = new ArrayList<OrdenDeTrabajoCorrectivo>();
        //TODO
        /*
        Pendientes
        En curso
        Terminadas
        Canceladas
        Todas
        */
        switch(filtro)
        {
            case "Próximas":
                for(OrdenDeTrabajoCorrectivo o : lista)
                {
                    // Hoy < FechaEjecucion()
                    if ( new Date().before(o.getFechaEjecucion())) ret.add(o);
                }
                break;
            case "Pendientes":
                for(OrdenDeTrabajoCorrectivo o : lista)
                {
                    // not Hoy >= FechaEjecucion()
                    if (o.getFechaEjecucion().after(new Date())) continue;
                    
                    // cancelada
                    if (o.getFechaCancelacion() != null) continue;
                    
                    // no iniciada
                    if (o.getFechaInicio() == null) ret.add(o);
                }
                break;
            case "En curso":
                for(OrdenDeTrabajoCorrectivo o : lista)
                {
                    // not Hoy >= FechaEjecucion()
                    if (o.getFechaEjecucion().after(new Date())) continue;
                    
                    // cancelada
                    if (o.getFechaCancelacion() != null) continue;
                    
                    // no iniciada
                    if (o.getFechaInicio() == null) continue;
                    
                    // no terminada
                    if (o.getFechaFinalizacion()== null) ret.add(o);
                }
                break;
            case "Terminadas":
                for(OrdenDeTrabajoCorrectivo o : lista)
                {
                    // terminada
                    if (o.getFechaFinalizacion() != null) ret.add(o);
                }
                break;
            case "Canceladas":
                for(OrdenDeTrabajoCorrectivo o : lista)
                {
                    // cancelada
                    if (o.getFechaCancelacion() != null) ret.add(o);
                }
                break;
            case "Todas":
                return lista;
        }
        
        return ret;
    }
    
    public boolean Agregar(OrdenDeTrabajoCorrectivo otc)
    {
        otc.setFechaCreacion(new Date());
        otc.setId(GetUltimoId()+1);
        if(otc.getFallasReportadas()==null)
            otc.setFallasReportadas(new ArrayList<>());
        if(otc.getFallasEncontradas()==null)
            otc.setFallasEncontradas(new ArrayList<>());
        return metodos.Nuevo(otc);
    }

    public boolean Actualizar(OrdenDeTrabajoCorrectivo otc)
    {
        return metodos.Editar(otc);
    }

    public boolean Iniciar(int id){
        OrdenDeTrabajoCorrectivo otc = (OrdenDeTrabajoCorrectivo)metodos.Buscar(id);
        
        // Validar que no esté iniciada
        if (otc.getFechaInicio() != null)
        {
            JOptionPane.showMessageDialog(null,"Esta orden ya fue iniciada", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        // Validar que no esté terminada
        if (otc.getFechaFinalizacion()!= null)
        {
            JOptionPane.showMessageDialog(null,"Esta orden ya fue terminada", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        // Validar que no esté cancelada
        if (otc.getFechaCancelacion()!= null)
        {
            JOptionPane.showMessageDialog(null,"Esta orden ya fue cancelada", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        
        otc.setFechaInicio(new Date());
        return metodos.Editar(otc);
    }
    
    public boolean Cerrar(OrdenDeTrabajoCorrectivo otc)
    {
        otc.setFechaFinalizacion(new Date());
        
        
        // Validar que no esté cancelada
        if (otc.getFechaCancelacion()!= null)
        {
            JOptionPane.showMessageDialog(null,"Esta orden ya fue cancelada", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        // Validar que esté iniciada
        if (otc.getFechaInicio() == null)
        {
            JOptionPane.showMessageDialog(null,"Esta orden no ha sido iniciada", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return metodos.Editar(otc);
    }

    public boolean Cancelar(int id, String motivo)
    {
        //Equipo equipo = GetEquipo(id);
        OrdenDeTrabajoCorrectivo otc = (OrdenDeTrabajoCorrectivo)metodos.Buscar(id);
        
        // Validar que no esté terminada
        if (otc.getFechaFinalizacion() != null)
        {
            JOptionPane.showMessageDialog(null,"Esta orden ya fue terminada", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
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

    public Falla BuscarFalla(int id){
        FallaM m = new FallaM();
        return m.Buscar(id);
    }
    
    public int GetUltimoId(){
        return metodos.Buscar().getLast().getId();
    }
}