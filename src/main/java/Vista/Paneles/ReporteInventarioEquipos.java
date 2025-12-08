package Vista.Paneles;

import Controlador.ControladorEquipo;
import Modelo.Entidades.Equipo;
import Utilidades.Pdf;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import java.awt.*;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

/**
 * Panel para generar el reporte de inventario de equipos usando JTree.
 * Muestra la jerarquía completa en un JTree y permite generar PDF para:
 *  - el nodo seleccionado (pregunta si incluir componentes)
 *  - todos los equipos raíz (pregunta si incluir componentes)
 *
 * Campos mostrados en el PDF: id, descripción, tipo (texto), estado, serie.
 */
public class ReporteInventarioEquipos extends JPanel {
    private ControladorEquipo controlador;
    private ArrayList<Equipo> listaEquipos;            // todos los equipos
    private ArrayList<Equipo> listaEquiposRaiz;        // equipos con idEquipoPrincipal == 0
    private JTree tree;
    private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    public ReporteInventarioEquipos() {
        setLayout(new BorderLayout());
        controlador = new ControladorEquipo();

        // Cargar datos
        listaEquipos = controlador.BuscarTodos();
        if (listaEquipos == null) listaEquipos = new ArrayList<>();

        listaEquiposRaiz = new ArrayList<>();
        for (Equipo e : listaEquipos) {
            if (e.getIdEquipoPrincipal() == 0) listaEquiposRaiz.add(e);
        }

        // Construir JTree
        DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode("Inventario de Equipos");
        listaEquiposRaiz.sort(Comparator.comparingInt(Equipo::getId));
        for (Equipo raiz : listaEquiposRaiz) {
            rootNode.add(crearNodoRecursivo(raiz));
        }
        tree = new JTree(new DefaultTreeModel(rootNode));
        tree.setRootVisible(true);
        tree.setShowsRootHandles(true);

        // Interfaz superior
        JLabel titulo = new JLabel("Reporte de Inventario de Equipos");
        titulo.setFont(new Font("Arial", Font.BOLD, 20));
        titulo.setBorder(BorderFactory.createEmptyBorder(12, 10, 12, 10));
        titulo.setHorizontalAlignment(JLabel.CENTER);
        add(titulo, BorderLayout.NORTH);

        // Panel central con el árbol
        add(new JScrollPane(tree), BorderLayout.CENTER);

        // Panel inferior con botones
        JPanel panelSur = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton btnGenerarSeleccionado = new JButton("Generar reporte del nodo seleccionado");
        btnGenerarSeleccionado.addActionListener(ae -> generarReporteNodoSeleccionado());
        panelSur.add(btnGenerarSeleccionado);

        JButton btnGenerarTodos = new JButton("Generar reporte para todos");
        btnGenerarTodos.addActionListener(ae -> generarReporteTodos());
        panelSur.add(btnGenerarTodos);

        add(panelSur, BorderLayout.SOUTH);
    }

    // Construye un nodo recursivo a partir de un Equipo (incluye hijos)
    private DefaultMutableTreeNode crearNodoRecursivo(Equipo e) {
        DefaultMutableTreeNode nodo = new DefaultMutableTreeNode(e);
        List<Equipo> hijos = obtenerComponentes(e);
        hijos.sort(Comparator.comparingInt(Equipo::getId));
        for (Equipo h : hijos) {
            nodo.add(crearNodoRecursivo(h));
        }
        return nodo;
    }

    // Acción: generar reporte para el nodo seleccionado (pregunta si incluir componentes)
    private void generarReporteNodoSeleccionado() {
        TreePath selPath = tree.getSelectionPath();
        if (selPath == null) {
            JOptionPane.showMessageDialog(this, "Seleccione un nodo del árbol primero.");
            return;
        }
        DefaultMutableTreeNode selNode = (DefaultMutableTreeNode) selPath.getLastPathComponent();
        Object userObj = selNode.getUserObject();
        if (!(userObj instanceof Equipo)) {
            JOptionPane.showMessageDialog(this, "Seleccione un equipo válido.");
            return;
        }
        Equipo seleccionado = (Equipo) userObj;

        int incluir = JOptionPane.showConfirmDialog(this,
                "¿Desea que el reporte incluya los componentes del equipo seleccionado?",
                "Incluir componentes", JOptionPane.YES_NO_OPTION);
        boolean incluirComponentes = (incluir == JOptionPane.YES_OPTION);

        ReportarEquipo(seleccionado, incluirComponentes);
    }

    // Acción: generar reporte para todos (pregunta si incluir componentes)
    private void generarReporteTodos() {
        int incluir = JOptionPane.showConfirmDialog(this,
                "¿Desea que el reporte para todos incluya los componentes de cada equipo?",
                "Incluir componentes", JOptionPane.YES_NO_OPTION);
        boolean incluirComponentes = (incluir == JOptionPane.YES_OPTION);

        ReportarTodos(incluirComponentes);
    }

    /**
     * Genera un PDF con todos los equipos raíz.
     * @param incluirComponentes si true recorre e imprime componentes; si false imprime solo los equipos raíz
     */
    private void ReportarTodos(boolean incluirComponentes) {
        Pdf pdf = new Pdf("ReporteInventarioEquipos");
        pdf.anadirPagina();
        try {
            pdf.anadirTituloSecuencial("Reporte de Inventario de Equipos");
            pdf.anadirLineaSecuencial(1.0f);

            // Resumen inicial
            int totalEquipos = listaEquipos.size();
            pdf.anadirTextoSecuencial("Fecha: " + sdf.format(new Date()));
            pdf.anadirTextoSecuencial("Total equipos: " + totalEquipos);
            pdf.anadirLineaSecuencial(0.5f);

            // Imprimir cada raíz
            listaEquiposRaiz.sort(Comparator.comparingInt(Equipo::getId));
            for (Equipo raiz : listaEquiposRaiz) {
                if (incluirComponentes) {
                    imprimirEquipoEnPdf(raiz, pdf, 0, true);
                } else {
                    imprimirEquipoEnPdf(raiz, pdf, 0, false); // imprime solo el nodo actual
                }
                pdf.anadirLineaSecuencial(0.5f);
            }

            pdf.guardar();
            pdf.cerrar();
            JOptionPane.showMessageDialog(this, "Reporte generado en carpeta reportes/");
        } catch (IOException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al generar PDF: " + ex.getMessage());
        }
    }

    /**
     * Genera un PDF para un solo equipo.
     * @param equipo equipo seleccionado
     * @param incluirComponentes si true recorre e imprime componentes; si false imprime solo el equipo
     */
    private void ReportarEquipo(Equipo equipo, boolean incluirComponentes) {
        Pdf pdf = new Pdf("ReporteEquipo_" + equipo.getId());
        pdf.anadirPagina();
        try {
            pdf.anadirTituloSecuencial("Reporte Equipo " + equipo.getId() + " - " + safe(equipo.getDescripcion()));
            pdf.anadirLineaSecuencial(1.0f);

            imprimirEquipoEnPdf(equipo, pdf, 0, incluirComponentes);

            pdf.guardar();
            pdf.cerrar();
            JOptionPane.showMessageDialog(this, "Reporte generado en carpeta reportes/");
        } catch (IOException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al generar PDF: " + ex.getMessage());
        }
    }

    /**
     * Imprime la información de un equipo en el PDF.
     * @param e equipo a imprimir
     * @param pdf utilidad Pdf
     * @param nivel nivel de sangría (0 = raíz)
     * @param incluirComponentes si true recorre e imprime componentes recursivamente
     * @throws IOException si falla la escritura en PDF
     */
    private void imprimirEquipoEnPdf(Equipo e, Pdf pdf, int nivel, boolean incluirComponentes) throws IOException {
        // Prefijo visual para indicar jerarquía
        StringBuilder prefijoBuilder = new StringBuilder();
        for (int i = 0; i < nivel; i++) prefijoBuilder.append("-");
        String prefijo = prefijoBuilder.toString();

        // Subtítulo con id y descripción
        pdf.anadirSubtituloSecuencial(prefijo + "Equipo ID: " + e.getId() + " - " + safe(e.getDescripcion()));

        String tipoTexto;
        try {
            tipoTexto = controlador.GetTipoEquipo(e.getTipoEquipo());
        } catch (Exception ex) {
            tipoTexto = String.valueOf(e.getTipoEquipo());
        }

        String[] encabezados = {"Campo", "Valor"};
        String[][] datos = {
                {"ID", String.valueOf(e.getId())},
                {"Descripción", safe(e.getDescripcion())},
                {"Tipo", safe(tipoTexto)},
                {"Estado", safeEstado(e.getEstadoEquipo())},
                {"Serie", safe(e.getSerie())}
        };
        pdf.anadirTablaSecuencial(encabezados, datos);

        if (!incluirComponentes) return;

        // Obtener componentes y recorrer recursivamente
        List<Equipo> componentes = obtenerComponentes(e);
        if (componentes != null && !componentes.isEmpty()) {
            componentes.sort(Comparator.comparingInt(Equipo::getId));
            for (Equipo comp : componentes) {
                imprimirEquipoEnPdf(comp, pdf, nivel + 1, true);
            }
        }
    }

    // Filtra listaEquipos para obtener los hijos directos del equipo padre
    private List<Equipo> obtenerComponentes(Equipo padre) {
        List<Equipo> comps = new ArrayList<>();
        for (Equipo e : listaEquipos) {
            if (e.getIdEquipoPrincipal() == padre.getId()) comps.add(e);
        }
        return comps;
    }

    private String safe(String s) {
        return s == null ? "" : s;
    }

    private String safeEstado(Object estado) {
        return estado == null ? "" : estado.toString();
    }
}
