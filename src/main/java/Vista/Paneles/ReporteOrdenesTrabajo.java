package Vista.Paneles;

import Controlador.ControladorOrdenesCorrectivo;
import Controlador.ControladorOrdenesPreventivo;
import Modelo.Entidades.Falla;
import Modelo.Entidades.FallaObservada;
import Modelo.Entidades.OrdenDeTrabajo;
import Modelo.Entidades.OrdenDeTrabajoCorrectivo;
import Modelo.Entidades.OrdenDeTrabajoPreventivo;
import Utilidades.Pdf;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

/**
 * Panel para generar el Reporte de Órdenes de Trabajo clasificado por fecha.
 *
 * Opciones:
 *  - Estado: Pendientes / Terminadas / Canceladas / Todas
 *  - Tipos: Preventivo / Correctivo (se pueden combinar)
 *  - Rango de fechas: desde / hasta (dd/MM/yyyy)
 *
 * Salida: PDF generado con Utilidades.Pdf
 */
public class ReporteOrdenesTrabajo extends JPanel {
    private ControladorOrdenesPreventivo controladorPrev;
    private ControladorOrdenesCorrectivo controladorCorr;
    private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    // UI
    private JComboBox<String> comboEstado;
    private JCheckBox chkPreventivo;
    private JCheckBox chkCorrectivo;
    private JTextField txtDesde;
    private JTextField txtHasta;
    private JButton btnGenerar;

    public ReporteOrdenesTrabajo() {
        setLayout(new BorderLayout());
        controladorPrev = new ControladorOrdenesPreventivo();
        controladorCorr = new ControladorOrdenesCorrectivo();

        JLabel titulo = new JLabel("Reporte de Órdenes de Trabajo (por fecha)");
        titulo.setFont(new Font("Arial", Font.BOLD, 18));
        titulo.setHorizontalAlignment(SwingConstants.CENTER);
        titulo.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(titulo, BorderLayout.NORTH);

        add(crearPanelControles(), BorderLayout.CENTER);
    }

    private JPanel crearPanelControles() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(6, 6, 6, 6);
        c.anchor = GridBagConstraints.WEST;

        // Estado
        c.gridx = 0; c.gridy = 0;
        panel.add(new JLabel("Estado de las órdenes:"), c);
        c.gridx = 1;
        comboEstado = new JComboBox<>(new String[] {"Todas", "Pendientes", "Terminadas", "Canceladas"});
        panel.add(comboEstado, c);

        // Tipos
        c.gridx = 0; c.gridy = 1;
        panel.add(new JLabel("Órdenes incluidas:"), c);
        c.gridx = 1;
        JPanel tipos = new JPanel(new FlowLayout(FlowLayout.LEFT, 4, 0));
        chkPreventivo = new JCheckBox("Preventivo", true);
        chkCorrectivo = new JCheckBox("Correctivo", true);
        tipos.add(chkPreventivo);
        tipos.add(chkCorrectivo);
        panel.add(tipos, c);

        // Rango de fechas
        c.gridx = 0; c.gridy = 2;
        panel.add(new JLabel("Fecha desde (dd/MM/yyyy):"), c);
        c.gridx = 1;
        txtDesde = new JTextField(10);
        panel.add(txtDesde, c);

        c.gridx = 0; c.gridy = 3;
        panel.add(new JLabel("Fecha hasta (dd/MM/yyyy):"), c);
        c.gridx = 1;
        txtHasta = new JTextField(10);
        panel.add(txtHasta, c);

        // Botón generar
        c.gridx = 0; c.gridy = 4; c.gridwidth = 2; c.anchor = GridBagConstraints.CENTER;
        btnGenerar = new JButton("Generar reporte");
        btnGenerar.addActionListener(ae -> onGenerar());
        panel.add(btnGenerar, c);

        return panel;
    }

    private void onGenerar() {
        // Validaciones básicas
        boolean incluirPrev = chkPreventivo.isSelected();
        boolean incluirCorr = chkCorrectivo.isSelected();
        if (!incluirPrev && !incluirCorr) {
            JOptionPane.showMessageDialog(this, "Seleccione al menos un tipo de orden (preventivo o correctivo).", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Date desde = null, hasta = null;
        try {
            if (!txtDesde.getText().trim().isEmpty()) desde = sdf.parse(txtDesde.getText().trim());
            if (!txtHasta.getText().trim().isEmpty()) hasta = sdf.parse(txtHasta.getText().trim());
        } catch (ParseException ex) {
            JOptionPane.showMessageDialog(this, "Formato de fecha inválido. Use dd/MM/yyyy.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String estado = (String) comboEstado.getSelectedItem();
        generarReporte(incluirPrev, incluirCorr, estado, desde, hasta);
    }

    private void generarReporte(boolean incluirPrev, boolean incluirCorr, String estadoFiltro, Date desde, Date hasta) {
        Pdf pdf = new Pdf("ReporteOrdenesTrabajo");
        pdf.anadirPagina();
        try {
            pdf.anadirTituloSecuencial("Reporte de Órdenes de Trabajo");
            pdf.anadirTextoSecuencial("Fecha generación: " + sdf.format(new Date()));
            pdf.anadirTextoSecuencial("Filtros: Estado=" + estadoFiltro + " | Preventivo=" + incluirPrev + " | Correctivo=" + incluirCorr);
            if (desde != null) pdf.anadirTextoSecuencial("Desde: " + sdf.format(desde));
            if (hasta != null) pdf.anadirTextoSecuencial("Hasta: " + sdf.format(hasta));
            pdf.anadirLineaSecuencial(0.5f);

            // Recolectar órdenes según tipos
            List<OrdenDeTrabajo> todas = new ArrayList<>();
            if (incluirPrev) {
                ArrayList<OrdenDeTrabajoPreventivo> prev = controladorPrev.BuscarTodos();
                if (prev != null) todas.addAll(prev);
            }
            if (incluirCorr) {
                ArrayList<OrdenDeTrabajoCorrectivo> corr = controladorCorr.BuscarTodos();
                if (corr != null) todas.addAll(corr);
            }

            // Filtrar por fecha y estado
            List<OrdenDeTrabajo> filtradas = new ArrayList<>();
            for (OrdenDeTrabajo o : todas) {
                if (!ordenCumpleEstado(o, estadoFiltro)) continue;
                if (!ordenDentroRango(o, desde, hasta)) continue;
                filtradas.add(o);
            }

            // Ordenar por fecha de referencia (fechaCreacion o fechaEjecucion) ascendente
            filtradas.sort(Comparator.comparing(o -> {
                Date d = o.getFechaCreacion();
                if (d == null) d = o.getFechaEjecucion();
                if (d == null) return new Date(0);
                return d;
            }));

            // Resumen
            pdf.anadirTextoSecuencial("Total órdenes incluidas: " + filtradas.size());
            pdf.anadirLineaSecuencial(0.5f);

            // Imprimir cada orden en tabla
            for (OrdenDeTrabajo o : filtradas) {
                imprimirOrdenEnPdf(o, pdf);
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

    // Determina si la orden cumple el filtro de estado
    private boolean ordenCumpleEstado(OrdenDeTrabajo o, String estadoFiltro) {
        switch (estadoFiltro) {
            case "Pendientes":
                return o.getFechaFinalizacion() == null && o.getFechaCancelacion() == null;
            case "Terminadas":
                return o.getFechaFinalizacion() != null;
            case "Canceladas":
                return o.getFechaCancelacion() != null;
            case "Todas":
            default:
                return true;
        }
    }

    // Determina si la orden cae dentro del rango de fechas (usa fechaCreacion o fechaEjecucion)
    private boolean ordenDentroRango(OrdenDeTrabajo o, Date desde, Date hasta) {
        if (desde == null && hasta == null) return true;
        Date ref = o.getFechaCreacion();
        if (ref == null) ref = o.getFechaEjecucion();
        if (ref == null) return true;
        if (desde != null && ref.before(desde)) return false;
        if (hasta != null && ref.after(hasta)) return false;
        return true;
    }

    // Imprime una orden (preventiva o correctiva) con sus campos y fallas
    private void imprimirOrdenEnPdf(OrdenDeTrabajo o, Pdf pdf) throws IOException {
        String tipo = (o instanceof OrdenDeTrabajoPreventivo) ? "Preventivo" : "Correctivo";
        pdf.anadirTextoSecuencial("Orden ID: " + o.getId() + "   Tipo: " + tipo);
        String[] encabezados = {"Campo", "Valor"};
        String[][] datos = {
                {"ID", String.valueOf(o.getId())},
                {"Equipo (id)", String.valueOf(o.getIdEquipo())},
                {"Fecha creación", formatDate(o.getFechaCreacion())},
                {"Fecha ejecución (programada)", formatDate(o.getFechaEjecucion())},
                {"Fecha inicio (real)", formatDate(o.getFechaInicio())},
                {"Fecha finalización", formatDate(o.getFechaFinalizacion())},
                {"Horas trabajo", String.valueOf(o.getHorasTrabajo())},
                {"Costo mano obra", String.valueOf(o.getCostoManoObra())},
                {"Costo equipo/material", String.valueOf(o.getCostoEquipoMaterial())},
                {"Observaciones iniciales", safe(o.getObservacionesIniciales())},
                {"Observaciones finales", safe(o.getObservacionesFinales())},
                {"Fecha cancelación", formatDate(o.getFechaCancelacion())},
                {"Motivo cancelación", safe(o.getMotivoCancelacion())}
        };
        pdf.anadirTablaSecuencial(encabezados, datos);

        // Fallas asociadas según tipo
        if (o instanceof OrdenDeTrabajoPreventivo) {
            OrdenDeTrabajoPreventivo otp = (OrdenDeTrabajoPreventivo) o;
            List<FallaObservada> fallas = otp.getFallasObservadas();
            if (fallas != null && !fallas.isEmpty()) {
                pdf.anadirTextoSecuencial("Fallas observadas:");
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
        } else if (o instanceof OrdenDeTrabajoCorrectivo) {
            OrdenDeTrabajoCorrectivo otc = (OrdenDeTrabajoCorrectivo) o;
            List<FallaObservada> rep = otc.getFallasReportadas();
            if (rep != null && !rep.isEmpty()) {
                pdf.anadirTextoSecuencial("Fallas reportadas:");
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
                pdf.anadirTextoSecuencial("Fallas encontradas:");
                for (FallaObservada fo : enc) {
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
    }

    // Intenta obtener la descripción de la falla usando los controladores disponibles
    private String buscarDescripcionFalla(int idFalla) {
        try {
            Falla f = controladorPrev.BuscarFalla(idFalla);
            if (f != null && f.getDescripcion() != null) return f.getDescripcion();
        } catch (Exception ignored) {}
        try {
            Falla f2 = controladorCorr.BuscarFalla(idFalla);
            if (f2 != null && f2.getDescripcion() != null) return f2.getDescripcion();
        } catch (Exception ignored) {}
        return "";
    }

    private String formatDate(Date d) {
        if (d == null) return "";
        try { return sdf.format(d); } catch (Exception ex) { return d.toString(); }
    }

    private String safe(String s) { return s == null ? "" : s; }
}
