package Vista.Paneles;

import Controlador.ControladorEquipo;
import Controlador.ControladorOrdenesCorrectivo;
import Controlador.ControladorOrdenesPreventivo;
import Modelo.Entidades.Equipo;
import Modelo.Entidades.Falla;
import Modelo.Entidades.FallaObservada;
import Modelo.Entidades.Fase;
import Modelo.Entidades.OrdenDeTrabajo;
import Modelo.Entidades.OrdenDeTrabajoCorrectivo;
import Modelo.Entidades.OrdenDeTrabajoPreventivo;
import Utilidades.Pdf;

import javax.swing.*;
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
 * Panel para generar el Reporte de Operaciones de Mantenimiento.
 * Usa ControladorOrdenesPreventivo y ControladorOrdenesCorrectivo para obtener datos.
 *
 * Opciones:
 *  - Alcance: nodo seleccionado o todos los equipos raíz
 *  - Incluir componentes (sí/no)
 *  - Incluir preventivo / incluir correctivo
 *  - Rango de fechas (desde / hasta) opcional (formato dd/MM/yyyy)
 *
 * Salida: PDF generado con la clase Utilidades.Pdf
 */
public class ReporteOperacionesMantenimiento extends JPanel {
    private ControladorOrdenesPreventivo controladorOrdenPrev;
    private ControladorOrdenesCorrectivo controladorOrdenCorr;
    private ControladorEquipo controladorEquipo;
    private ArrayList<Equipo> listaEquipos;
    private ArrayList<Equipo> listaEquiposRaiz;
    private JTree arbol;
    private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    // UI controls
    private JCheckBox chkIncluirComponentes;
    private JCheckBox chkPreventivo;
    private JCheckBox chkCorrectivo;
    private JTextField txtFechaDesde;
    private JTextField txtFechaHasta;
    private JRadioButton rbSeleccionado;
    private JRadioButton rbTodos;

    public ReporteOperacionesMantenimiento() {
        setLayout(new BorderLayout());
        controladorOrdenPrev = new ControladorOrdenesPreventivo();
        controladorOrdenCorr = new ControladorOrdenesCorrectivo();
        controladorEquipo = new ControladorEquipo();

        // Cargar equipos
        listaEquipos = controladorEquipo.BuscarTodos();
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
        arbol = new JTree(new DefaultTreeModel(rootNode));
        arbol.setRootVisible(true);
        arbol.setShowsRootHandles(true);

        // Top title
        JLabel titulo = new JLabel("Reporte de Operaciones de Mantenimiento");
        titulo.setFont(new Font("Arial", Font.BOLD, 18));
        titulo.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        titulo.setHorizontalAlignment(JLabel.CENTER);
        add(titulo, BorderLayout.NORTH);

        // Center: tree
        add(new JScrollPane(arbol), BorderLayout.CENTER);

        // South: controls
        add(crearPanelControles(), BorderLayout.SOUTH);
    }

    private JPanel crearPanelControles() {
        JPanel panel = new JPanel(new BorderLayout());
        JPanel opciones = new JPanel(new GridLayout(2, 1));

        // Primera fila: alcance y tipos
        JPanel fila1 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        rbSeleccionado = new JRadioButton("Nodo seleccionado", true);
        rbTodos = new JRadioButton("Todos (raíces)");
        ButtonGroup bg = new ButtonGroup();
        bg.add(rbSeleccionado);
        bg.add(rbTodos);
        fila1.add(rbSeleccionado);
        fila1.add(rbTodos);

        chkIncluirComponentes = new JCheckBox("Incluir componentes", true);
        fila1.add(chkIncluirComponentes);

        chkPreventivo = new JCheckBox("Incluir preventivo", true);
        chkCorrectivo = new JCheckBox("Incluir correctivo", true);
        fila1.add(chkPreventivo);
        fila1.add(chkCorrectivo);

        opciones.add(fila1);

        // Segunda fila: rango de fechas y botones
        JPanel fila2 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        fila2.add(new JLabel("Fecha desde (dd/MM/yyyy):"));
        txtFechaDesde = new JTextField(8);
        fila2.add(txtFechaDesde);
        fila2.add(new JLabel("Fecha hasta (dd/MM/yyyy):"));
        txtFechaHasta = new JTextField(8);
        fila2.add(txtFechaHasta);

        JButton btnGenerar = new JButton("Generar reporte");
        btnGenerar.addActionListener(ae -> onGenerarReporte());
        fila2.add(btnGenerar);

        opciones.add(fila2);

        panel.add(opciones, BorderLayout.CENTER);
        return panel;
    }

    // Construye nodo recursivo para JTree
    private DefaultMutableTreeNode crearNodoRecursivo(Equipo e) {
        DefaultMutableTreeNode nodo = new DefaultMutableTreeNode(e);
        List<Equipo> hijos = obtenerComponentes(e);
        hijos.sort(Comparator.comparingInt(Equipo::getId));
        for (Equipo h : hijos) {
            nodo.add(crearNodoRecursivo(h));
        }
        return nodo;
    }

    // Obtener hijos directos
    private List<Equipo> obtenerComponentes(Equipo padre) {
        List<Equipo> comps = new ArrayList<>();
        for (Equipo e : listaEquipos) {
            if (e.getIdEquipoPrincipal() == padre.getId()) comps.add(e);
        }
        return comps;
    }

    // Acción principal
    private void onGenerarReporte() {
        boolean incluirComponentes = chkIncluirComponentes.isSelected();
        boolean incluirPrev = chkPreventivo.isSelected();
        boolean incluirCorr = chkCorrectivo.isSelected();

        Date desde = null, hasta = null;
        try {
            if (!txtFechaDesde.getText().trim().isEmpty()) desde = sdf.parse(txtFechaDesde.getText().trim());
            if (!txtFechaHasta.getText().trim().isEmpty()) hasta = sdf.parse(txtFechaHasta.getText().trim());
        } catch (ParseException ex) {
            JOptionPane.showMessageDialog(this, "Formato de fecha inválido. Use dd/MM/yyyy.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!incluirPrev && !incluirCorr) {
            JOptionPane.showMessageDialog(this, "Seleccione al menos un tipo de orden (preventivo o correctivo).", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (rbSeleccionado.isSelected()) {
            TreePath sel = arbol.getSelectionPath();
            if (sel == null) {
                JOptionPane.showMessageDialog(this, "Seleccione un nodo del árbol.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            DefaultMutableTreeNode nodo = (DefaultMutableTreeNode) sel.getLastPathComponent();
            Object obj = nodo.getUserObject();
            if (!(obj instanceof Equipo)) {
                JOptionPane.showMessageDialog(this, "Seleccione un equipo válido.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            Equipo equipo = (Equipo) obj;
            generarPdfParaEquipo(equipo, incluirComponentes, incluirPrev, incluirCorr, desde, hasta);
        } else {
            // Todos los equipos raíz
            generarPdfParaTodos(incluirComponentes, incluirPrev, incluirCorr, desde, hasta);
        }
    }

    // Generar PDF para un solo equipo (y opcionalmente sus componentes)
    private void generarPdfParaEquipo(Equipo equipo, boolean incluirComponentes, boolean incluirPrev, boolean incluirCorr, Date desde, Date hasta) {
        Pdf pdf = new Pdf("ReporteOperacionesMantenimiento_Equipo_" + equipo.getId());
        pdf.anadirPagina();
        try {
            pdf.anadirTituloSecuencial("Reporte de Operaciones de Mantenimiento");
            pdf.anadirTextoSecuencial("Equipo: " + equipo.getId() + " - " + safe(equipo.getDescripcion()));
            pdf.anadirTextoSecuencial("Fecha generación: " + sdf.format(new Date()));
            pdf.anadirLineaSecuencial(0.5f);

            imprimirEquipoConOperaciones(equipo, pdf, 0, incluirComponentes, incluirPrev, incluirCorr, desde, hasta);

            pdf.guardar();
            pdf.cerrar();
            JOptionPane.showMessageDialog(this, "Reporte generado en carpeta reportes/");
        } catch (IOException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al generar PDF: " + ex.getMessage());
        }
    }

    // Generar PDF para todos los equipos raíz
    private void generarPdfParaTodos(boolean incluirComponentes, boolean incluirPrev, boolean incluirCorr, Date desde, Date hasta) {
        Pdf pdf = new Pdf("ReporteOperacionesMantenimiento_Todos");
        pdf.anadirPagina();
        try {
            pdf.anadirTituloSecuencial("Reporte de Operaciones de Mantenimiento - Todos los equipos");
            pdf.anadirTextoSecuencial("Fecha generación: " + sdf.format(new Date()));
            pdf.anadirLineaSecuencial(0.5f);

            listaEquiposRaiz.sort(Comparator.comparingInt(Equipo::getId));
            for (Equipo raiz : listaEquiposRaiz) {
                imprimirEquipoConOperaciones(raiz, pdf, 0, incluirComponentes, incluirPrev, incluirCorr, desde, hasta);
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
     * Imprime en el PDF:
     *  - identificación y descripción del equipo
     *  - programa de mantenimiento (fases)
     *  - órdenes preventivas (por fase) y correctivas del equipo
     * Si incluirComponentes==true recorre recursivamente los componentes.
     */
    private void imprimirEquipoConOperaciones(Equipo equipo, Pdf pdf, int nivel, boolean incluirComponentes, boolean incluirPrev, boolean incluirCorr, Date desde, Date hasta) throws IOException {
        String prefijo = "";
        for (int i = 0; i < nivel; i++) prefijo += "-";

        pdf.anadirSubtituloSecuencial(prefijo + "Equipo ID: " + equipo.getId() + " - " + safe(equipo.getDescripcion()));

        // Programa de mantenimiento: fases
        List<Fase> fases = equipo.getFasesMantenimiento();
        if (fases == null) fases = new ArrayList<>();
        if (!fases.isEmpty()) {
            pdf.anadirTextoSecuencial(prefijo + "Programa de mantenimiento (fases):");
            for (Fase f : fases) {
                String[] encabezadosF = {"Campo", "Valor"};
                String[][] datosF = {
                        {"ID fase", String.valueOf(f.getId())},
                        {"Tipo frecuencia", safe(f.getTipoFrecuencia())},
                        {"Frecuencia", safe(f.getFrecuencia() == null ? "" : f.getFrecuencia().toString())},
                        {"Cantidad ciclos", String.valueOf(f.getCantidadCiclos())},
                        {"Horas estimadas", String.valueOf(f.getHorasEstimadas())},
                        {"Partes", safe(f.getPartes())},
                        {"Equipo para mantenimiento", safe(f.getEquipoParaMantenimiento())},
                        {"Personal encargado", safe(f.getPersonalEncargado())}
                };
                pdf.anadirTablaSecuencial(encabezadosF, datosF);

                // Si se incluyen preventivas, listar órdenes preventivas asociadas a esta fase
                if (incluirPrev) {
                    List<OrdenDeTrabajoPreventivo> ordenesPrev = obtenerOrdenesPreventivasPorEquipoYfase(equipo.getId(), f.getId(), desde, hasta);
                    if (!ordenesPrev.isEmpty()) {
                        pdf.anadirTextoSecuencial(prefijo + "  Órdenes preventivas (fase " + f.getId() + "):");
                        for (OrdenDeTrabajoPreventivo otp : ordenesPrev) {
                            imprimirOrdenPreventiva(otp, pdf, prefijo + "    ");
                        }
                    }
                }
            }
        } else {
            pdf.anadirTextoSecuencial(prefijo + "No tiene programa de mantenimiento (fases).");
        }

        // Órdenes correctivas del equipo
        if (incluirCorr) {
            List<OrdenDeTrabajoCorrectivo> ordenesCorr = obtenerOrdenesCorrectivasPorEquipo(equipo.getId(), desde, hasta);
            if (!ordenesCorr.isEmpty()) {
                pdf.anadirTextoSecuencial(prefijo + "Órdenes correctivas:");
                for (OrdenDeTrabajoCorrectivo otc : ordenesCorr) {
                    imprimirOrdenCorrectiva(otc, pdf, prefijo + "  ");
                }
            } else {
                pdf.anadirTextoSecuencial(prefijo + "No hay órdenes correctivas en el rango seleccionado.");
            }
        }

        // Si no incluir componentes, terminar aquí
        if (!incluirComponentes) return;

        // Recorrer componentes
        List<Equipo> componentes = obtenerComponentes(equipo);
        if (componentes != null && !componentes.isEmpty()) {
            componentes.sort(Comparator.comparingInt(Equipo::getId));
            for (Equipo comp : componentes) {
                imprimirEquipoConOperaciones(comp, pdf, nivel + 1, true, incluirPrev, incluirCorr, desde, hasta);
            }
        }
    }

    // Obtener órdenes preventivas del controlador y filtrar por equipo y fase y rango de fechas
    private List<OrdenDeTrabajoPreventivo> obtenerOrdenesPreventivasPorEquipoYfase(int idEquipo, int idFase, Date desde, Date hasta) {
        List<OrdenDeTrabajoPreventivo> ret = new ArrayList<>();
        ArrayList<OrdenDeTrabajoPreventivo> todas = controladorOrdenPrev.BuscarTodos();
        for (OrdenDeTrabajoPreventivo o : todas) {
            if (o.getIdEquipo() != idEquipo) continue;
            if (o.getIdFase() != idFase) continue;
            if (!fechaDentroRango(o, desde, hasta)) continue;
            ret.add(o);
        }
        // ordenar por fechaCreacion asc
        ret.sort(Comparator.comparing(o -> o.getFechaCreacion() == null ? new Date(0) : o.getFechaCreacion()));
        return ret;
    }

    // Obtener órdenes correctivas del controlador y filtrar por equipo y rango de fechas
    private List<OrdenDeTrabajoCorrectivo> obtenerOrdenesCorrectivasPorEquipo(int idEquipo, Date desde, Date hasta) {
        List<OrdenDeTrabajoCorrectivo> ret = new ArrayList<>();
        ArrayList<OrdenDeTrabajoCorrectivo> todas = controladorOrdenCorr.BuscarTodos();
        for (OrdenDeTrabajoCorrectivo o : todas) {
            if (o.getIdEquipo() != idEquipo) continue;
            if (!fechaDentroRango(o, desde, hasta)) continue;
            ret.add(o);
        }
        ret.sort(Comparator.comparing(o -> o.getFechaCreacion() == null ? new Date(0) : o.getFechaCreacion()));
        return ret;
    }

    // Comprueba si la orden cae dentro del rango (usa fechaCreacion y fechaEjecucion como referencia)
    private boolean fechaDentroRango(OrdenDeTrabajo o, Date desde, Date hasta) {
        if (desde == null && hasta == null) return true;
        Date referencia = o.getFechaCreacion();
        if (referencia == null) referencia = o.getFechaEjecucion();
        if (referencia == null) return true;
        if (desde != null && referencia.before(desde)) return false;
        if (hasta != null && referencia.after(hasta)) return false;
        return true;
    }

    // Imprime una orden preventiva con sus fallas observadas
    private void imprimirOrdenPreventiva(OrdenDeTrabajoPreventivo otp, Pdf pdf, String indent) throws IOException {
        String[] encabezados = {"Campo", "Valor"};
        String[][] datos = {
                {"ID", String.valueOf(otp.getId())},
                {"Tipo", "Preventivo"},
                {"Fecha creación", formatDate(otp.getFechaCreacion())},
                {"Fecha ejecución (programada)", formatDate(otp.getFechaEjecucion())},
                {"Fecha inicio (real)", formatDate(otp.getFechaInicio())},
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

        // Fallas observadas
        List<FallaObservada> fallas = otp.getFallasObservadas();
        if (fallas != null && !fallas.isEmpty()) {
            pdf.anadirTextoSecuencial(indent + "Fallas observadas:");
            for (FallaObservada fo : fallas) {
                String descFalla = buscarDescripcionFalla(fo.getIdFalla());
                String[] enc = {"Campo", "Valor"};
                String[][] dat = {
                        {"Falla (id)", String.valueOf(fo.getIdFalla()) + (descFalla.isEmpty() ? "" : " - " + descFalla)},
                        {"Causa", safe(fo.getCausa())},
                        {"Acciones tomadas", safe(fo.getAccionesTomadas())}
                };
                pdf.anadirTablaSecuencial(enc, dat);
            }
        }
    }

    // Imprime una orden correctiva con fallas reportadas y encontradas
    private void imprimirOrdenCorrectiva(OrdenDeTrabajoCorrectivo otc, Pdf pdf, String indent) throws IOException {
        String[] encabezados = {"Campo", "Valor"};
        String[][] datos = {
                {"ID", String.valueOf(otc.getId())},
                {"Tipo", "Correctivo"},
                {"Fecha creación", formatDate(otc.getFechaCreacion())},
                {"Fecha ejecución (reportada)", formatDate(otc.getFechaEjecucion())},
                {"Fecha inicio (real)", formatDate(otc.getFechaInicio())},
                {"Fecha finalización", formatDate(otc.getFechaFinalizacion())},
                {"Horas trabajo", String.valueOf(otc.getHorasTrabajo())},
                {"Costo mano obra", String.valueOf(otc.getCostoManoObra())},
                {"Costo equipo/material", String.valueOf(otc.getCostoEquipoMaterial())},
                {"Observaciones iniciales", safe(otc.getObservacionesIniciales())},
                {"Observaciones finales", safe(otc.getObservacionesFinales())},
                {"Fecha cancelación", formatDate(otc.getFechaCancelacion())},
                {"Motivo cancelación", safe(otc.getMotivoCancelacion())}
        };
        pdf.anadirTextoSecuencial(indent + "Orden correctiva:");
        pdf.anadirTablaSecuencial(encabezados, datos);

        // Fallas reportadas
        List<FallaObservada> rep = otc.getFallasReportadas();
        if (rep != null && !rep.isEmpty()) {
            pdf.anadirTextoSecuencial(indent + "Fallas reportadas:");
            for (FallaObservada fo : rep) {
                String desc = buscarDescripcionFalla(fo.getIdFalla());
                String[] enc = {"Campo", "Valor"};
                String[][] dat = {
                        {"Falla (id)", String.valueOf(fo.getIdFalla()) + (desc.isEmpty() ? "" : " - " + desc)},
                        {"Causa", safe(fo.getCausa())},
                        {"Acciones tomadas", safe(fo.getAccionesTomadas())}
                };
                pdf.anadirTablaSecuencial(enc, dat);
            }
        }

        // Fallas encontradas
        // Fallas encontradas
        List<FallaObservada> encontradas = otc.getFallasEncontradas();
        if (encontradas != null && !encontradas.isEmpty()) {
            pdf.anadirTextoSecuencial(indent + "Fallas encontradas:");
            for (FallaObservada fo2 : encontradas) {
                String desc2 = buscarDescripcionFalla(fo2.getIdFalla());
                String[] enc2 = {"Campo", "Valor"};
                String[][] dat2 = {
                        {"Falla (id)", String.valueOf(fo2.getIdFalla()) + (desc2.isEmpty() ? "" : " - " + desc2)},
                        {"Causa", safe(fo2.getCausa())},
                        {"Acciones tomadas", safe(fo2.getAccionesTomadas())}
                };
                pdf.anadirTablaSecuencial(enc2, dat2);
            }
        }
    }

    // Buscar descripción de falla usando los controladores (si existe)
    private String buscarDescripcionFalla(int idFalla) {
        try {
            Falla f = controladorOrdenPrev.BuscarFalla(idFalla); // intenta con preventivo (tiene BuscarFalla)
            if (f != null && f.getDescripcion() != null && !f.getDescripcion().isEmpty()) {
                return f.getDescripcion();
            }
        } catch (Exception ignored) {}

        try {
            Falla f2 = controladorOrdenCorr.BuscarFalla(idFalla); // intenta con correctivo
            if (f2 != null && f2.getDescripcion() != null && !f2.getDescripcion().isEmpty()) {
                return f2.getDescripcion();
            }
        } catch (Exception ignored) {}

        // Si no se encuentra, devolver cadena vacía
        return "";
    }

    // Formateo seguro de fechas
    private String formatDate(Date d) {
        if (d == null) return "";
        try {
            return sdf.format(d);
        } catch (Exception ex) {
            return d.toString();
        }
    }

    // Helpers seguros
    private String safe(String s) {
        return s == null ? "" : s;
    }

    private String safeEstado(Object estado) {
        return estado == null ? "" : estado.toString();
    }
}
