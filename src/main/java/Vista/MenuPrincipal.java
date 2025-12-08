//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package Vista;

import Controlador.ControladorMenuP;
import Vista.Paneles.*;

import java.awt.BorderLayout;
import java.awt.Component;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

public class MenuPrincipal extends JFrame {
    private JPanel panelContenido;

    public MenuPrincipal() {
        this.setTitle("Mantenimiento de Equipos");
        this.setSize(1000, 600);
        this.setDefaultCloseOperation(3);
        this.setLocationRelativeTo((Component)null);
        JMenuBar menuBar = new JMenuBar();
        JMenu menuEquipos = new JMenu("Equipos");
        JMenuItem itemRegistrarEquipo = new JMenuItem("Registrar");
        JMenuItem itemInventario = new JMenuItem("Inventario");
        menuEquipos.add(itemRegistrarEquipo);
        menuEquipos.add(itemInventario);
        JMenu menuMantenimientoPrev = new JMenu("Mantenimiento Preventivo");
        JMenuItem itemPrevOrdenes = new JMenuItem("Órdenes de trabajo");
        menuMantenimientoPrev.add(itemPrevOrdenes);
        JMenu menuMantenimientoCorr = new JMenu("Mantenimiento Correctivo");
        JMenuItem itemCorrOrdenes = new JMenuItem("Órdenes de trabajo");
        menuMantenimientoCorr.add(itemCorrOrdenes);
        JMenu menuFallasYTareas = new JMenu("Fallas y Tareas");
        JMenuItem itemAdminFalla = new JMenuItem("Administrar Fallas");
        JMenuItem itemAdminTareas = new JMenuItem("Administrar Tareas");
        menuFallasYTareas.add(itemAdminFalla);
        menuFallasYTareas.add(itemAdminTareas);
        JMenu menuReportes = new JMenu("Reportes");
        JMenuItem itemReporteInventario = new JMenuItem("Inventario");
        JMenuItem itemReporteOperaciones = new JMenuItem("Operaciones de mantenimiento");
        JMenuItem itemReporteOT = new JMenuItem("Órdenes de trabajo");
        JMenuItem itemReporteConsulta = new JMenuItem("Consulta de mantenimiento");
        JMenuItem itemGraficos = new JMenuItem("Graficos de análisis");

        menuReportes.add(itemReporteInventario);
        menuReportes.add(itemReporteOperaciones);
        menuReportes.add(itemReporteOT);
        menuReportes.add(itemGraficos);
        menuReportes.addSeparator();
        menuReportes.add(itemReporteConsulta);

        JMenu menuAyuda = new JMenu("Ayuda");
        JMenuItem itemAcercaDe = new JMenuItem("Acerca de");
        JMenuItem itemManual = new JMenuItem("Manual de usuario");
        menuAyuda.add(itemAcercaDe);
        menuAyuda.add(itemManual);
        menuBar.add(menuEquipos);
        menuBar.add(menuMantenimientoPrev);
        menuBar.add(menuMantenimientoCorr);
        menuBar.add(menuFallasYTareas);
        menuBar.add(menuReportes);
        menuBar.add(menuAyuda);
        this.setJMenuBar(menuBar);
        this.panelContenido = new JPanel(new BorderLayout());
        this.add(this.panelContenido, "Center");
        ControladorMenuP controlador = new ControladorMenuP(this.panelContenido);
        itemRegistrarEquipo.addActionListener((e) -> controlador.cargarPanel(new RegistrarEquipo()));
        itemInventario.addActionListener((e) -> controlador.cargarPanel(new InventarioEquipos()));
        itemAdminFalla.addActionListener((e) -> controlador.cargarPanel(new ListaFallas()));
        itemCorrOrdenes.addActionListener(e -> controlador.cargarPanel(new OrdenesCorrectivo()));
        itemAdminTareas.addActionListener(e-> controlador.cargarPanel(new ListaTareas()));
        itemAcercaDe.addActionListener(e -> controlador.cargarPanel(new AcercaDe()));
        itemReporteInventario.addActionListener(e->controlador.cargarPanel(new ReporteInventarioEquipos()));
        itemPrevOrdenes.addActionListener(e -> controlador.cargarPanel(new OrdenesPreventivo()));
        itemReporteOperaciones.addActionListener(e->controlador.cargarPanel(new ReporteOperacionesMantenimiento()));
        itemPrevOrdenes.addActionListener(e -> controlador.cargarPanel(new OrdenesPreventivo()));
        itemReporteOT.addActionListener(e->controlador.cargarPanel(new ReporteOrdenesTrabajo()));
        itemReporteConsulta.addActionListener(e->controlador.cargarPanel(new ConsultaMantenimientos()));
        itemGraficos.addActionListener(e -> controlador.cargarPanel(new Graficos()));
    }
}
