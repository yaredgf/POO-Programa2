package Vista;

import javax.swing.*;
import java.awt.*;

public class MenuPrincipal extends JFrame {
    ///
    /// Este panel es el que va a cambiar con el contenido segun corresponda.
    ///
    private JPanel panelContenido;
    public MenuPrincipal(){
        setTitle("Mantenimiento de Equipos");
        setSize(1000, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        //Barra de Menu
        JMenuBar menuBar = new JMenuBar();
        //Submenu equipos
        JMenu menuEquipos = new JMenu("Equipos");
        JMenuItem itemRegistrarEquipo = new JMenuItem("Registrar");
        JMenuItem itemInventario = new JMenuItem("Inventario");
        menuEquipos.add(itemRegistrarEquipo);
        menuEquipos.add(itemInventario);

        //submenu mantenimiento preventivo
        JMenu menuMantenimientoPrev = new JMenu("Mantenimiento Preventivo");
        JMenuItem itemProgramas = new JMenuItem("Programas");
        JMenuItem itemListaTareas = new JMenuItem("Lista de tareas");
        JMenuItem itemOrdenesTrabajo = new JMenuItem("Órdenes de trabajo");
        menuMantenimientoPrev.add(itemProgramas);
        menuMantenimientoPrev.add(itemListaTareas);
        menuMantenimientoPrev.add(itemOrdenesTrabajo);

        //submenu mantenimiento correctivo
        JMenu menuMantenimientoCorr = new JMenu("Mantenimiento Correctivo");
        JMenuItem itemCorrOrdenes = new JMenuItem("Órdenes de trabajo");
        menuMantenimientoCorr.add(itemCorrOrdenes);

        //Submenu fallas
        JMenu menuFallas = new JMenu("Fallas");
        JMenuItem itemRegistrarFalla = new JMenuItem("Registrar");
        JMenuItem itemListaFalla = new JMenuItem("Lista de fallas");
        menuFallas.add(itemRegistrarFalla);
        menuFallas.add(itemListaFalla);

        //Submenu reportes
        JMenu menuReportes = new JMenu("Reportes");
        JMenuItem itemReporteInventario = new JMenuItem("Inventario");
        JMenuItem itemReporteOperaciones = new JMenuItem("Operaciones de mantenimiento");
        JMenuItem itemReporteOrdenes = new JMenuItem("Órdenes de trabajo");
        JMenuItem itemReporteGraficos = new JMenuItem("Graficos de análisis");
        menuReportes.add(itemReporteInventario);
        menuReportes.add(itemReporteOperaciones);

        //Submenu ayuda
        JMenu menuAyuda = new JMenu("Ayuda");
        JMenuItem itemAcercaDe = new JMenuItem("Acerca de");
        JMenuItem itemManual = new JMenuItem("Manual de usuario");
        menuAyuda.add(itemAcercaDe);
        menuAyuda.add(itemManual);

        //Agregar submenus a la barra de menu
        menuBar.add(menuEquipos);
        menuBar.add(menuMantenimientoPrev);
        menuBar.add(menuMantenimientoCorr);
        menuBar.add(menuFallas);
        menuBar.add(menuReportes);
        menuBar.add(menuAyuda);

        //Setear la barra de menu a la ventana
        setJMenuBar(menuBar);

        panelContenido = new JPanel(new BorderLayout());
        add(panelContenido, BorderLayout.CENTER);

        //Los eventos para cada panel

    }
    ///
    /// Cambia el contenido del panel principal segun el item seleccionado en el menu.
    /// @param panel Panel a mostrar.
    ///
    private void cargarPanel(JPanel panel){
        panelContenido.removeAll();
        panelContenido.add(panel, BorderLayout.CENTER);
        panelContenido.revalidate();
        panelContenido.repaint();
    }

}
