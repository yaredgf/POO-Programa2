/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo.Entidades;
import java.util.ArrayList;
import java.util.Date;


/**
 *
 * @author Usuario
 */
public class Equipo implements java.io.Serializable
{
    private int id;
    private int idEquipoPrincipal;
    private String descripcion;
    private int tipoEquipo;
    private String ubicacionFisica;
    private String fabricante;
    private String serie;
    private Date fechaAdquisicion;
    private Date fechaPuestaEnServicio;
    private int mesesVidaUtil;
    private EstadoEquipo estadoEquipo;
    private float costoInicial;
    private String especificacionesTecnicas;
    private String informacionGarantia;
    private ArrayList<Fase> fasesMantenimiento;

    public Equipo()
    {
        this.id = -1;
        this.idEquipoPrincipal = 0;
        this.descripcion = null;
        this.tipoEquipo = -1;
        this.ubicacionFisica = null;
        this.fabricante = null;
        this.serie = null;
        this.fechaAdquisicion = null;
        this.fechaPuestaEnServicio = null;
        this.mesesVidaUtil = 0;
        this.estadoEquipo = null;
        this.costoInicial = 0;
        this.especificacionesTecnicas = null;
        this.informacionGarantia = null;
        this.fasesMantenimiento = null;
    }


    public Equipo(int id, int idEquipoPrincipal, String descripcion, int tipoEquipo, String ubicacionFisica, String fabricante, String serie, Date fechaAdquisicion, Date fechaPuestaEnServicio, int mesesVidaUtil, EstadoEquipo estadoEquipo, float costoInicial, String especificacionesTecnicas, String informacionGarantia, ArrayList<Fase> fasesMantenimiento)
    {
        this.id = id;
        this.idEquipoPrincipal = idEquipoPrincipal;
        this.descripcion = descripcion;
        this.tipoEquipo = tipoEquipo;
        this.ubicacionFisica = ubicacionFisica;
        this.fabricante = fabricante;
        this.serie = serie;
        this.fechaAdquisicion = fechaAdquisicion;
        this.fechaPuestaEnServicio = fechaPuestaEnServicio;
        this.mesesVidaUtil = mesesVidaUtil;
        this.estadoEquipo = estadoEquipo;
        this.costoInicial = costoInicial;
        this.especificacionesTecnicas = especificacionesTecnicas;
        this.informacionGarantia = informacionGarantia;
        this.fasesMantenimiento = fasesMantenimiento;
    }


    public void setId(int id)
    {
        this.id = id;
    }

    public int getId()
    {
            return this.id;
    }
    public void setIdEquipoPrincipal(int idEquipoPrincipal)
    {
        this.idEquipoPrincipal = idEquipoPrincipal;
    }

    public int getIdEquipoPrincipal()
    {
        return this.idEquipoPrincipal;
    }
    public void setDescripcion(String descripcion)
    {
        this.descripcion = descripcion;
    }

    public String getDescripcion()
    {
        return this.descripcion;
    }
    public void setTipoEquipo(int tipoEquipo)
    {
        this.tipoEquipo = tipoEquipo;
    }

    public int getTipoEquipo()
    {
        return this.tipoEquipo;
    }
    public void setUbicacionFisica(String ubicacionFisica)
    {
        this.ubicacionFisica = ubicacionFisica;
    }

    public String getUbicacionFisica()
    {
        return this.ubicacionFisica;
    }
    public void setFabricante(String fabricante)
    {
        this.fabricante = fabricante;
    }

    public String getFabricante()
    {
        return this.fabricante;
    }
    public void setSerie(String serie)
    {
        this.serie = serie;
    }

    public String getSerie()
    {
        return this.serie;
    }
    public void setFechaAdquisicion(Date fechaAdquisicion)
    {
            this.fechaAdquisicion = fechaAdquisicion;
    }

    public Date getFechaAdquisicion()
    {
        return this.fechaAdquisicion;
    }
    public void setFechaPuestaEnServicio(Date fechaPuestaEnServicio)
    {
        this.fechaPuestaEnServicio = fechaPuestaEnServicio;
    }

    public Date getFechaPuestaEnServicio()
    {
        return this.fechaPuestaEnServicio;
    }
    public void setMesesVidaUtil(int mesesVidaUtil)
    {
        this.mesesVidaUtil = mesesVidaUtil;
    }

    public int getMesesVidaUtil()
    {
        return this.mesesVidaUtil;
    }
    public void setEstadoEquipo(EstadoEquipo estadoEquipo)
    {
        this.estadoEquipo = estadoEquipo;
    }

    public EstadoEquipo getEstadoEquipo()
    {
        return this.estadoEquipo;
    }
    public void setCostoInicial(float costoInicial)
    {
        this.costoInicial = costoInicial;
    }

    public float getCostoInicial()
    {
        return this.costoInicial;
    }
    public void setEspecificacionesTecnicas(String especificacionesTecnicas)
    {
        this.especificacionesTecnicas = especificacionesTecnicas;
    }

    public String getEspecificacionesTecnicas()
    {
        return this.especificacionesTecnicas;
    }
    public void setInformacionGarantia(String informacionGarantia)
    {
        this.informacionGarantia = informacionGarantia;
    }

    public String getInformacionGarantia()
    {
        return this.informacionGarantia;
    }
    public void setFasesMantenimiento(ArrayList<Fase> fasesMantenimiento)
    {
        this.fasesMantenimiento = fasesMantenimiento;
    }

    public ArrayList<Fase> getFasesMantenimiento()
    {
        return this.fasesMantenimiento;
    }

    public String toString()
    {
        return this.id+" "+this.descripcion;
    }
}
