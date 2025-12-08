/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Modelo.Entidades.Equipo;
import Modelo.Entidades.Falla;
import Modelo.Entidades.OrdenDeTrabajo;
import Modelo.Entidades.OrdenDeTrabajoPreventivo;
import Modelo.Metodos.EquipoM;
import Modelo.Metodos.FallaM;
import Modelo.Metodos.OrdenDeTrabajoM;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.JOptionPane;

/**
 *
 * @author Usuario
 */
public class ControladorOrdenesPreventivo 
{
    OrdenDeTrabajoM metodos;
    ControladorOrdenesPreventivo()
    {
        metodos = new  OrdenDeTrabajoM();
    }
        
    public ArrayList<OrdenDeTrabajoPreventivo> BuscarTodos(){
        ArrayList<OrdenDeTrabajoPreventivo> ret = new ArrayList<>();
        ArrayList<OrdenDeTrabajo> lista = metodos.Buscar();
        for(OrdenDeTrabajo o : lista)
        {
            if(o instanceof OrdenDeTrabajoPreventivo)
            {
                ret.add((OrdenDeTrabajoPreventivo)o);
            }
        }
        return ret;
    }
    public ArrayList<OrdenDeTrabajoPreventivo> Filtrar(String filtro){
        ArrayList<OrdenDeTrabajoPreventivo> lista = BuscarTodos();
        ArrayList<OrdenDeTrabajoPreventivo> ret = new ArrayList<OrdenDeTrabajoPreventivo>();
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
                for(OrdenDeTrabajoPreventivo o : lista)
                {
                    // Hoy < FechaEjecucion()
                    if ( new Date().before(o.getFechaEjecucion())) ret.add(o);
                }
                break;
            case "Pendientes":
                for(OrdenDeTrabajoPreventivo o : lista)
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
                for(OrdenDeTrabajoPreventivo o : lista)
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
                for(OrdenDeTrabajoPreventivo o : lista)
                {
                    // terminada
                    if (o.getFechaFinalizacion() != null) ret.add(o);
                }
                break;
            case "Canceladas":
                for(OrdenDeTrabajoPreventivo o : lista)
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
    
    
    public boolean Agregar(OrdenDeTrabajoPreventivo otp)
    {
        ///// Validar que el mantenimiento del equipo esté discontinuado
        Equipo equipo = GetEquipo(otp.getIdEquipo());
        if (equipo.getMantPrevActivo())
        {
            JOptionPane.showMessageDialog(null,"No es posible agregar una orden preventiva nueva a un equipo que tiene activo su mantenimiento preventivo", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        equipo.setMantPrevActivo(true);
        EquipoM m = new EquipoM();
        m.Editar(equipo);
        
        otp.setFechaCreacion(new Date());
        otp.setId(GetUltimoId()+1);
        return metodos.Nuevo(otp);
    }
    
    public boolean Actualizar(OrdenDeTrabajoPreventivo otp)
    {
        return metodos.Editar(otp);
    }
    
    public boolean Iniciar(int id){
        OrdenDeTrabajoPreventivo otp = (OrdenDeTrabajoPreventivo)metodos.Buscar(id);
        
        // Validar que no esté iniciada
        if (otp.getFechaInicio() != null)
        {
            JOptionPane.showMessageDialog(null,"Esta orden ya fue iniciada", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        // Validar que no esté terminada
        if (otp.getFechaFinalizacion()!= null)
        {
            JOptionPane.showMessageDialog(null,"Esta orden ya fue terminada", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        // Validar que no esté cancelada
        if (otp.getFechaCancelacion()!= null)
        {
            JOptionPane.showMessageDialog(null,"Esta orden ya fue cancelada", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        
        otp.setFechaInicio(new Date());
        return metodos.Editar(otp);
    }
    
    public boolean Cerrar(OrdenDeTrabajoPreventivo otp)
    {
        otp.setFechaFinalizacion(new Date());
        
        
        // Validar que no esté cancelada
        if (otp.getFechaCancelacion()!= null)
        {
            JOptionPane.showMessageDialog(null,"Esta orden ya fue cancelada", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        // Validar que esté iniciada
        if (otp.getFechaInicio() == null)
        {
            JOptionPane.showMessageDialog(null,"Esta orden no ha sido iniciada", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        return metodos.Editar(otp);
    }

    public boolean Cancelar(int id, String motivo)
    {
        //Equipo equipo = GetEquipo(id);
        OrdenDeTrabajoPreventivo otp = (OrdenDeTrabajoPreventivo)metodos.Buscar(id);
        
        // Validar que no esté terminada
        if (otp.getFechaFinalizacion() != null)
        {
            JOptionPane.showMessageDialog(null,"Esta orden ya fue terminada", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        // Desactivar el mantenimiento
        Equipo equipo = GetEquipo(otp.getIdEquipo());
        equipo.setMantPrevActivo(false);
        EquipoM m = new EquipoM();
        m.Editar(equipo);
        
        otp.setFechaCancelacion(new Date());
        return metodos.Editar(otp);
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
    
    public int GetUltimoId(){
        return metodos.Buscar().getLast().getId();
    }
}
