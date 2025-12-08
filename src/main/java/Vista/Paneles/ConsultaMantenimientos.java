package Vista.Paneles;

import Controlador.ControladorEquipo;
import Controlador.ControladorOrdenesCorrectivo;
import Controlador.ControladorOrdenesPreventivo;
import Modelo.Entidades.*;
import Utilidades.Pdf;

import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import java.awt.*;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

/**
 * ConsultaMantenimientos
 * Panel que muestra la jerarquía de equipos (JTree) y el detalle de mantenimiento
 * (fases, órdenes preventivas por fase, órdenes correctivas) en la derecha.
 * Permite acciones sobre órdenes (Iniciar, Cerrar, Cancelar) y exportar PDF.
 */
public class ConsultaMantenimientos extends JPanel {
    private ControladorEquipo controladorEquipo;
    private ControladorOrdenesPreventivo controladorPreventivo;
    private ControladorOrdenesCorrectivo controladorCorrectivo;

    private ArrayList<Equipo> listaEquipos;
    private ArrayList<Equipo> listaEquiposRaiz;

    private JTree tree;
    private DefaultTreeModel treeModel;

    private JPanel detallePanel;
    private JLabel lblEquipoInfo;
    private JTable tblFases;
    private JTable tblOrdenesPreventivas;
    private JTable tblOrdenesCorrectivas;
    private DefaultTableModel modelFases;
    private DefaultTableModel modelPrev;
    private DefaultTableModel modelCorr;

    private JButton btnIniciarOrden;
    private JButton btnCerrarOrden;
    private JButton btnCancelarOrden;
    private JButton btnExportPdf;

    private JCheckBox chkIncluirComponentes;
    private JCheckBox chkExportPrev;
    private JCheckBox chkExportCorr;
    private JTextField txtDesde;
    private JTextField txtHasta;

    private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    public ConsultaMantenimientos() {
        setLayout(new BorderLayout());
        controladorEquipo = new ControladorEquipo();
        controladorPreventivo = new ControladorOrdenesPreventivo();
        controladorCorrectivo = new ControladorOrdenesCorrectivo();

        cargarEquipos();
        construirUI();
    }

    private void cargarEquipos() {
        listaEquipos = controladorEquipo.BuscarTodos();
        if (listaEquipos == null) listaEquipos = new ArrayList<>();
        listaEquiposRaiz = new ArrayList<>();
        for (Equipo e : listaEquipos) {
            if (e.getIdEquipoPrincipal() == 0) listaEquiposRaiz.add(e);
        }
        listaEquiposRaiz.sort(Comparator.comparingInt(Equipo::getId));
    }

    private void construirUI() {
        // Tree
        DefaultMutableTreeNode root = new DefaultMutableTreeNode("Inventario de Equipos");
        for (Equipo raiz : listaEquiposRaiz) {
            root.add(crearNodoRecursivo(raiz));
        }
        treeModel = new DefaultTreeModel(root);
        tree = new JTree(treeModel);
        tree.setRootVisible(true);
        tree.setShowsRootHandles(true);
        tree.addTreeSelectionListener(new TreeSelectionListener() {
            public void valueChanged(TreeSelectionEvent e) {
                onTreeSelection(e);
            }
        });

        // Detalle panel (derecha)
        detallePanel = new JPanel(new BorderLayout());
        detallePanel.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));

        lblEquipoInfo = new JLabel("Seleccione un equipo en el árbol");
        lblEquipoInfo.setFont(new Font("Arial", Font.BOLD, 14));
        detallePanel.add(lblEquipoInfo, BorderLayout.NORTH);

        // Center: tabs con fases y órdenes
        JTabbedPane tabs = new JTabbedPane();

        // Fases table
        modelFases = new DefaultTableModel(new String[]{"ID", "Tipo frecuencia", "Frecuencia", "Ciclos", "Horas estimadas", "Partes", "Personal"}, 0) {
            public boolean isCellEditable(int row, int col) { return false; }
        };
        tblFases = new JTable(modelFases);
        tabs.addTab("Fases (Programa)", new JScrollPane(tblFases));

        // Preventivas table
        modelPrev = new DefaultTableModel(new String[]{"ID", "Fase", "Fecha creación", "Fecha ejecución", "Inicio", "Fin", "Horas", "Costo MO", "Costo Mat", "Estado"}, 0) {
            public boolean isCellEditable(int row, int col) { return false; }
        };
        tblOrdenesPreventivas = new JTable(modelPrev);
        tabs.addTab("Órdenes Preventivas", new JScrollPane(tblOrdenesPreventivas));

        // Correctivas table
        modelCorr = new DefaultTableModel(new String[]{"ID", "Fecha creación", "Fecha ejecución", "Inicio", "Fin", "Horas", "Costo MO", "Costo Mat", "Estado"}, 0) {
            public boolean isCellEditable(int row, int col) { return false; }
        };
        tblOrdenesCorrectivas = new JTable(modelCorr);
        tabs.addTab("Órdenes Correctivas", new JScrollPane(tblOrdenesCorrectivas));

        detallePanel.add(tabs, BorderLayout.CENTER);

        // South: acciones y export
        JPanel panelSur = new JPanel(new BorderLayout());
        JPanel acciones = new JPanel(new FlowLayout(FlowLayout.LEFT));
        btnIniciarOrden = new JButton("Iniciar orden seleccionada");
        btnCerrarOrden = new JButton("Cerrar orden seleccionada");
        btnCancelarOrden = new JButton("Cancelar orden seleccionada");
        acciones.add(btnIniciarOrden);
        acciones.add(btnCerrarOrden);
        acciones.add(btnCancelarOrden);

        btnIniciarOrden.addActionListener(ae -> accionIniciarOrden());
        btnCerrarOrden.addActionListener(ae -> accionCerrarOrden());
        btnCancelarOrden.addActionListener(ae -> accionCancelarOrden());

        panelSur.add(acciones, BorderLayout.NORTH);

        JPanel exportPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        chkIncluirComponentes = new JCheckBox("Incluir componentes", true);
        chkExportPrev = new JCheckBox("Exportar preventivo", true);
        chkExportCorr = new JCheckBox("Exportar correctivo", true);
        exportPanel.add(chkIncluirComponentes);
        exportPanel.add(chkExportPrev);
        exportPanel.add(chkExportCorr);
        exportPanel.add(new JLabel("Desde (dd/MM/yyyy):"));
        txtDesde = new JTextField(8);
        exportPanel.add(txtDesde);
        exportPanel.add(new JLabel("Hasta (dd/MM/yyyy):"));
        txtHasta = new JTextField(8);
        exportPanel.add(txtHasta);

        btnExportPdf = new JButton("Exportar PDF (equipo seleccionado)");
        btnExportPdf.addActionListener(ae -> accionExportarPdfEquipo());
        exportPanel.add(btnExportPdf);

        panelSur.add(exportPanel, BorderLayout.SOUTH);

        detallePanel.add(panelSur, BorderLayout.SOUTH);

        // Split pane
        JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, new JScrollPane(tree), detallePanel);
        split.setDividerLocation(300);
        add(split, BorderLayout.CENTER);
    }

    private DefaultMutableTreeNode crearNodoRecursivo(Equipo e) {
        DefaultMutableTreeNode nodo = new DefaultMutableTreeNode(e);
        List<Equipo> hijos = obtenerComponentes(e);
        hijos.sort(Comparator.comparingInt(Equipo::getId));
        for (Equipo h : hijos) nodo.add(crearNodoRecursivo(h));
        return nodo;
    }

    private List<Equipo> obtenerComponentes(Equipo padre) {
        List<Equipo> comps = new ArrayList<>();
        for (Equipo e : listaEquipos) if (e.getIdEquipoPrincipal() == padre.getId()) comps.add(e);
        return comps;
    }

    private void onTreeSelection(TreeSelectionEvent e) {
        TreePath path = e.getPath();
        DefaultMutableTreeNode node = (DefaultMutableTreeNode) path.getLastPathComponent();
        Object obj = node.getUserObject();
        if (obj instanceof Equipo) {
            Equipo sel = (Equipo) obj;
            mostrarDetalleEquipo(sel);
        } else {
            lblEquipoInfo.setText("Seleccione un equipo");
            limpiarTablas();
        }
    }

    private void mostrarDetalleEquipo(Equipo equipo) {
        lblEquipoInfo.setText("Equipo: " + equipo.getId() + " - " + safe(equipo.getDescripcion()));
        // llenar fases
        modelFases.setRowCount(0);
        List<Fase> fases = equipo.getFasesMantenimiento();
        if (fases != null) {
            for (Fase f : fases) {
                modelFases.addRow(new Object[]{
                        f.getId(),
                        safe(f.getTipoFrecuencia()),
                        safe(f.getFrecuencia() == null ? "" : f.getFrecuencia().toString()),
                        f.getCantidadCiclos(),
                        f.getHorasEstimadas(),
                        safe(f.getPartes()),
                        safe(f.getPersonalEncargado())
                });
            }
        }

        // llenar ordenes preventivas (todas las fases)
        modelPrev.setRowCount(0);
        ArrayList<OrdenDeTrabajoPreventivo> todasPrev = controladorPreventivo.BuscarTodos();
        for (OrdenDeTrabajoPreventivo otp : todasPrev) {
            if (otp.getIdEquipo() != equipo.getId()) continue;
            modelPrev.addRow(new Object[]{
                    otp.getId(),
                    otp.getIdFase(),
                    formatDate(otp.getFechaCreacion()),
                    formatDate(otp.getFechaEjecucion()),
                    formatDate(otp.getFechaInicio()),
                    formatDate(otp.getFechaFinalizacion()),
                    otp.getHorasTrabajo(),
                    otp.getCostoManoObra(),
                    otp.getCostoEquipoMaterial(),
                    estadoOrden(otp)
            });
        }

        // llenar ordenes correctivas
        modelCorr.setRowCount(0);
        ArrayList<OrdenDeTrabajoCorrectivo> todasCorr = controladorCorrectivo.BuscarTodos();
        for (OrdenDeTrabajoCorrectivo otc : todasCorr) {
            if (otc.getIdEquipo() != equipo.getId()) continue;
            modelCorr.addRow(new Object[]{
                    otc.getId(),
                    formatDate(otc.getFechaCreacion()),
                    formatDate(otc.getFechaEjecucion()),
                    formatDate(otc.getFechaInicio()),
                    formatDate(otc.getFechaFinalizacion()),
                    otc.getHorasTrabajo(),
                    otc.getCostoManoObra(),
                    otc.getCostoEquipoMaterial(),
                    estadoOrden(otc)
            });
        }
    }

    private String estadoOrden(OrdenDeTrabajo o) {
        if (o.getFechaCancelacion() != null) return "Cancelada";
        if (o.getFechaFinalizacion() != null) return "Terminada";
        if (o.getFechaInicio() != null) return "En curso";
        return "Pendiente";
    }

    private void limpiarTablas() {
        modelFases.setRowCount(0);
        modelPrev.setRowCount(0);
        modelCorr.setRowCount(0);
    }


    private void accionIniciarOrden() {
        // Determinar orden seleccionada: priorizar preventiva, si no, correctiva
        int selPrev = tblOrdenesPreventivas.getSelectedRow();
        int selCorr = tblOrdenesCorrectivas.getSelectedRow();
        try {
            if (selPrev >= 0) {
                int id = (int) modelPrev.getValueAt(selPrev, 0);
                boolean ok = controladorPreventivo.Iniciar(id);
                if (ok) JOptionPane.showMessageDialog(this, "Orden preventiva iniciada.");
                mostrarNodoSeleccionadoRefrescar();
            } else if (selCorr >= 0) {
                int id = (int) modelCorr.getValueAt(selCorr, 0);
                boolean ok = controladorCorrectivo.Iniciar(id);
                if (ok) JOptionPane.showMessageDialog(this, "Orden correctiva iniciada.");
                mostrarNodoSeleccionadoRefrescar();
            } else {
                JOptionPane.showMessageDialog(this, "Seleccione una orden en las tablas.");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al iniciar orden: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void accionCerrarOrden() {
        int selPrev = tblOrdenesPreventivas.getSelectedRow();
        int selCorr = tblOrdenesCorrectivas.getSelectedRow();
        try {
            if (selPrev >= 0) {
                int id = (int) modelPrev.getValueAt(selPrev, 0);
                OrdenDeTrabajoPreventivo otp = (OrdenDeTrabajoPreventivo) controladorPreventivo.Buscar(id);
                // Si no quieres acceder a metodos internamente, reemplaza por ctrlPrev.Buscar(id) si existe
                if (otp == null) {
                    JOptionPane.showMessageDialog(this, "No se encontró la orden.");
                    return;
                }
                boolean ok = controladorPreventivo.Cerrar(otp);
                if (ok) JOptionPane.showMessageDialog(this, "Orden preventiva cerrada.");
                mostrarNodoSeleccionadoRefrescar();
            } else if (selCorr >= 0) {
                int id = (int) modelCorr.getValueAt(selCorr, 0);
                OrdenDeTrabajoCorrectivo otc = (OrdenDeTrabajoCorrectivo) controladorCorrectivo.Buscar(id);
                if (otc == null) {
                    JOptionPane.showMessageDialog(this, "No se encontró la orden.");
                    return;
                }
                boolean ok = controladorCorrectivo.Cerrar(otc);
                if (ok) JOptionPane.showMessageDialog(this, "Orden correctiva cerrada.");
                mostrarNodoSeleccionadoRefrescar();
            } else {
                JOptionPane.showMessageDialog(this, "Seleccione una orden en las tablas.");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al cerrar orden: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void accionCancelarOrden() {
        int selPrev = tblOrdenesPreventivas.getSelectedRow();
        int selCorr = tblOrdenesCorrectivas.getSelectedRow();
        try {
            String motivo = JOptionPane.showInputDialog(this, "Motivo de cancelación:");
            if (motivo == null || motivo.trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Cancelación abortada (motivo requerido).");
                return;
            }
            if (selPrev >= 0) {
                int id = (int) modelPrev.getValueAt(selPrev, 0);
                boolean ok = controladorPreventivo.Cancelar(id, motivo);
                if (ok) JOptionPane.showMessageDialog(this, "Orden preventiva cancelada.");
                mostrarNodoSeleccionadoRefrescar();
            } else if (selCorr >= 0) {
                int id = (int) modelCorr.getValueAt(selCorr, 0);
                boolean ok = controladorCorrectivo.Cancelar(id, motivo);
                if (ok) JOptionPane.showMessageDialog(this, "Orden correctiva cancelada.");
                mostrarNodoSeleccionadoRefrescar();
            } else {
                JOptionPane.showMessageDialog(this, "Seleccione una orden en las tablas.");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al cancelar orden: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Refresca la vista del nodo seleccionado (relee datos)
    private void mostrarNodoSeleccionadoRefrescar() {
        TreePath sel = tree.getSelectionPath();
        if (sel == null) return;
        DefaultMutableTreeNode node = (DefaultMutableTreeNode) sel.getLastPathComponent();
        Object obj = node.getUserObject();
        if (obj instanceof Equipo) {
            mostrarDetalleEquipo((Equipo) obj);
        }
    }

    /* ---------- Exportar PDF para equipo seleccionado ---------- */

    private void accionExportarPdfEquipo() {
        TreePath sel = tree.getSelectionPath();
        if (sel == null) {
            JOptionPane.showMessageDialog(this, "Seleccione un equipo en el árbol.");
            return;
        }
        DefaultMutableTreeNode node = (DefaultMutableTreeNode) sel.getLastPathComponent();
        Object obj = node.getUserObject();
        if (!(obj instanceof Equipo)) {
            JOptionPane.showMessageDialog(this, "Seleccione un equipo válido.");
            return;
        }
        Equipo equipo = (Equipo) obj;

        boolean incluirComponentes = chkIncluirComponentes.isSelected();
        boolean incluirPrev = chkExportPrev.isSelected();
        boolean incluirCorr = chkExportCorr.isSelected();

        Date desde = null, hasta = null;
        try {
            if (!txtDesde.getText().trim().isEmpty()) desde = sdf.parse(txtDesde.getText().trim());
            if (!txtHasta.getText().trim().isEmpty()) hasta = sdf.parse(txtHasta.getText().trim());
        } catch (ParseException ex) {
            JOptionPane.showMessageDialog(this, "Formato de fecha inválido. Use dd/MM/yyyy.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        generarPdfEquipo(equipo, incluirComponentes, incluirPrev, incluirCorr, desde, hasta);
    }

    private void generarPdfEquipo(Equipo equipo, boolean incluirComponentes, boolean incluirPrev, boolean incluirCorr, Date desde, Date hasta) {
        Pdf pdf = new Pdf("ConsultaMantenimiento_Equipo_" + equipo.getId());
        pdf.anadirPagina();
        try {
            pdf.anadirTituloSecuencial("Consulta de Mantenimientos - Equipo " + equipo.getId());
            pdf.anadirTextoSecuencial("Equipo: " + equipo.getId() + " - " + safe(equipo.getDescripcion()));
            pdf.anadirTextoSecuencial("Fecha generación: " + sdf.format(new Date()));
            pdf.anadirLineaSecuencial(0.5f);

            imprimirEquipoEnPdf(equipo, pdf, 0, incluirComponentes, incluirPrev, incluirCorr, desde, hasta);

            pdf.guardar();
            pdf.cerrar();
            JOptionPane.showMessageDialog(this, "PDF generado en carpeta reportes/");
        } catch (IOException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al generar PDF: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Reutiliza lógica similar a la clase de reportes: imprime fases y órdenes
    private void imprimirEquipoEnPdf(Equipo equipo, Pdf pdf, int nivel, boolean incluirComponentes, boolean incluirPrev, boolean incluirCorr, Date desde, Date hasta) throws IOException {
        String prefijo = "";
        for (int i = 0; i < nivel; i++) prefijo += "-";
        pdf.anadirSubtituloSecuencial(prefijo + "Equipo ID: " + equipo.getId() + " - " + safe(equipo.getDescripcion()));

        // Fases
        List<Fase> fases = equipo.getFasesMantenimiento();
        if (fases == null) fases = new ArrayList<>();
        if (!fases.isEmpty()) {
            pdf.anadirTextoSecuencial(prefijo + "Programa de mantenimiento:");
            for (Fase f : fases) {
                String[] encF = {"Campo", "Valor"};
                String[][] datF = {
                        {"ID fase", String.valueOf(f.getId())},
                        {"Tipo frecuencia", safe(f.getTipoFrecuencia())},
                        {"Frecuencia", safe(f.getFrecuencia() == null ? "" : f.getFrecuencia().toString())},
                        {"Ciclos", String.valueOf(f.getCantidadCiclos())},
                        {"Horas estimadas", String.valueOf(f.getHorasEstimadas())},
                        {"Partes", safe(f.getPartes())},
                        {"Personal", safe(f.getPersonalEncargado())}
                };
                pdf.anadirTablaSecuencial(encF, datF);

                if (incluirPrev) {
                    List<OrdenDeTrabajoPreventivo> ordenesPrev = obtenerOrdenesPreventivasPorEquipoYfase(equipo.getId(), f.getId(), desde, hasta);
                    if (!ordenesPrev.isEmpty()) {
                        pdf.anadirTextoSecuencial(prefijo + "  Órdenes preventivas (fase " + f.getId() + "):");
                        for (OrdenDeTrabajoPreventivo otp : ordenesPrev) {
                            imprimirOrdenPreventivaEnPdf(otp, pdf, prefijo + "    ");
                        }
                    }
                }
            }
        } else {
            pdf.anadirTextoSecuencial(prefijo + "No tiene programa de mantenimiento.");
        }

        if (incluirCorr) {
            List<OrdenDeTrabajoCorrectivo> ordenesCorr = obtenerOrdenesCorrectivasPorEquipo(equipo.getId(), desde, hasta);
            if (!ordenesCorr.isEmpty()) {
                pdf.anadirTextoSecuencial(prefijo + "Órdenes correctivas:");
                for (OrdenDeTrabajoCorrectivo otc : ordenesCorr) {
                    imprimirOrdenCorrectivaEnPdf(otc, pdf, prefijo + "  ");
                }
            } else {
                pdf.anadirTextoSecuencial(prefijo + "No hay órdenes correctivas en el rango seleccionado.");
            }
        }

        if (!incluirComponentes) return;

        List<Equipo> componentes = obtenerComponentes(equipo);
        if (componentes != null && !componentes.isEmpty()) {
            componentes.sort(Comparator.comparingInt(Equipo::getId));
            for (Equipo comp : componentes) {
                imprimirEquipoEnPdf(comp, pdf, nivel + 1, true, incluirPrev, incluirCorr, desde, hasta);
            }
        }
    }

    private List<OrdenDeTrabajoPreventivo> obtenerOrdenesPreventivasPorEquipoYfase(int idEquipo, int idFase, Date desde, Date hasta) {
        List<OrdenDeTrabajoPreventivo> ret = new ArrayList<>();
        ArrayList<OrdenDeTrabajoPreventivo> todas = controladorPreventivo.BuscarTodos();
        for (OrdenDeTrabajoPreventivo o : todas) {
            if (o.getIdEquipo() != idEquipo) continue;
            if (o.getIdFase() != idFase) continue;
            if (!fechaDentroRango(o, desde, hasta)) continue;
            ret.add(o);
        }
        ret.sort(Comparator.comparing(o -> o.getFechaCreacion() == null ? new Date(0) : o.getFechaCreacion()));
        return ret;
    }

    private List<OrdenDeTrabajoCorrectivo> obtenerOrdenesCorrectivasPorEquipo(int idEquipo, Date desde, Date hasta) {
        List<OrdenDeTrabajoCorrectivo> ret = new ArrayList<>();
        ArrayList<OrdenDeTrabajoCorrectivo> todas = controladorCorrectivo.BuscarTodos();
        for (OrdenDeTrabajoCorrectivo o : todas) {
            if (o.getIdEquipo() != idEquipo) continue;
            if (!fechaDentroRango(o, desde, hasta)) continue;
            ret.add(o);
        }
        ret.sort(Comparator.comparing(o -> o.getFechaCreacion() == null ? new Date(0) : o.getFechaCreacion()));
        return ret;
    }

    private boolean fechaDentroRango(OrdenDeTrabajo o, Date desde, Date hasta) {
        if (desde == null && hasta == null) return true;
        Date ref = o.getFechaCreacion();
        if (ref == null) ref = o.getFechaEjecucion();
        if (ref == null) return true;
        if (desde != null && ref.before(desde)) return false;
        if (hasta != null && ref.after(hasta)) return false;
        return true;
    }

    private void imprimirOrdenPreventivaEnPdf(OrdenDeTrabajoPreventivo otp, Pdf pdf, String indent) throws IOException {
        String[] encabezados = {"Campo", "Valor"};
        String[][] datos = {
                {"ID", String.valueOf(otp.getId())},
                {"Fase", String.valueOf(otp.getIdFase())},
                {"Fecha creación", formatDate(otp.getFechaCreacion())},
                {"Fecha ejecución", formatDate(otp.getFechaEjecucion())},
                {"Fecha inicio", formatDate(otp.getFechaInicio())},
                {"Fecha finalización", formatDate(otp.getFechaFinalizacion())},
                {"Horas trabajo", String.valueOf(otp.getHorasTrabajo())},
                {"Costo mano obra", String.valueOf(otp.getCostoManoObra())},
                {"Costo equipo/material", String.valueOf(otp.getCostoEquipoMaterial())},
                {"Observaciones iniciales", safe(otp.getObservacionesIniciales())},
                {"Observaciones finales", safe(otp.getObservacionesFinales())},
                {"Fecha cancelación", formatDate(otp.getFechaCancelacion())},
                {"Motivo cancelación", safe(otp.getMotivoCancelacion())}
        };
        pdf.anadirTextoSecuencial(indent + "Orden preventiva:");
        pdf.anadirTablaSecuencial(encabezados, datos);

        List<FallaObservada> fallas = otp.getFallasObservadas();
        if (fallas != null && !fallas.isEmpty()) {
            pdf.anadirTextoSecuencial(indent + "Fallas observadas:");
            for (FallaObservada fo : fallas) {
                String desc = buscarDescripcionFalla(fo.getIdFalla());
                String[][] dat = {
                        {"Falla (id)", fo.getIdFalla() + (desc.isEmpty() ? "" : " - " + desc)},
                        {"Causa", safe(fo.getCausa())},
                        {"Acciones tomadas", safe(fo.getAccionesTomadas())}
                };
                pdf.anadirTablaSecuencial(encabezados, dat);
            }
        }
    }

    private void imprimirOrdenCorrectivaEnPdf(OrdenDeTrabajoCorrectivo otc, Pdf pdf, String prefijo) throws IOException {
        String[] encabezados = {"Campo", "Valor"};
        String[][] datos = {
                {"ID", String.valueOf(otc.getId())},
                {"Fecha creación", formatDate(otc.getFechaCreacion())},
                {"Fecha ejecución", formatDate(otc.getFechaEjecucion())},
                {"Fecha inicio", formatDate(otc.getFechaInicio())},
                {"Fecha finalización", formatDate(otc.getFechaFinalizacion())},
                {"Horas trabajo", String.valueOf(otc.getHorasTrabajo())},
                {"Costo mano obra", String.valueOf(otc.getCostoManoObra())},
                {"Costo equipo/material", String.valueOf(otc.getCostoEquipoMaterial())},
                {"Observaciones iniciales", safe(otc.getObservacionesIniciales())},
                {"Observaciones finales", safe(otc.getObservacionesFinales())},
                {"Fecha cancelación", formatDate(otc.getFechaCancelacion())},
                {"Motivo cancelación", safe(otc.getMotivoCancelacion())}
        };
        pdf.anadirTextoSecuencial(prefijo + "Orden correctiva:");
        pdf.anadirTablaSecuencial(encabezados, datos);

        List<FallaObservada> rep = otc.getFallasReportadas();
        if (rep != null && !rep.isEmpty()) {
            pdf.anadirTextoSecuencial(prefijo + "Fallas reportadas:");
            for (FallaObservada fo : rep) {
                String desc = buscarDescripcionFalla(fo.getIdFalla());
                String[][] dat = {
                        {"Falla (id)", fo.getIdFalla() + (desc.isEmpty() ? "" : " - " + desc)},
                        {"Causa", safe(fo.getCausa())},
                        {"Acciones tomadas", safe(fo.getAccionesTomadas())}
                };
                pdf.anadirTablaSecuencial(encabezados, dat);
            }
        }

        List<FallaObservada> enc = otc.getFallasEncontradas();
        if (enc != null && !enc.isEmpty()) {
            pdf.anadirTextoSecuencial(prefijo + "Fallas encontradas:");
            for (FallaObservada fo2 : enc) {
                String desc2 = buscarDescripcionFalla(fo2.getIdFalla());
                String[][] dat2 = {
                        {"Falla (id)", fo2.getIdFalla() + (desc2.isEmpty() ? "" : " - " + desc2)},
                        {"Causa", safe(fo2.getCausa())},
                        {"Acciones tomadas", safe(fo2.getAccionesTomadas())}
                };
                pdf.anadirTablaSecuencial(encabezados, dat2);
            }
        }
    }

    // Buscar descripción de falla usando controladores
    private String buscarDescripcionFalla(int idFalla) {
        try {
            Falla f = controladorPreventivo.BuscarFalla(idFalla);
            if (f != null && f.getDescripcion() != null) return f.getDescripcion();
        } catch (Exception e) {}
        try {
            Falla f2 = controladorCorrectivo.BuscarFalla(idFalla);
            if (f2 != null && f2.getDescripcion() != null) return f2.getDescripcion();
        } catch (Exception e) {}
        return "";
    }

    private String formatDate(Date d) {
        if (d == null) return "";
        try { return sdf.format(d); } catch (Exception ex) { return d.toString(); }
    }

    private String safe(String s) { return s == null ? "" : s; }
}
