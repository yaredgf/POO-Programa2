package Vista.Paneles;

import Controlador.ControladorOrdenesCorrectivo;
import Controlador.ControladorOrdenesPreventivo;
import Modelo.Entidades.OrdenDeTrabajo;
import Modelo.Entidades.OrdenDeTrabajoCorrectivo;
import Modelo.Entidades.OrdenDeTrabajoPreventivo;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

import javax.swing.*;
import javax.swing.Timer;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

public class Graficos extends JPanel {
    private final ControladorOrdenesPreventivo controladorOrdenesPreventivo = new ControladorOrdenesPreventivo();
    private final ControladorOrdenesCorrectivo controladorOrdenesCorrectivo = new ControladorOrdenesCorrectivo();

    private final JCheckBox chkPreventivo = new JCheckBox("Preventivo", true);
    private final JCheckBox chkCorrectivo = new JCheckBox("Correctivo", true);
    private final JTextField txtDesde = new JTextField(8);
    private final JTextField txtHasta = new JTextField(8);
    private final JButton btnRefrescar = new JButton("Refrescar");

    private final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    private ChartPanel chartPanelMeses;
    private ChartPanel chartPanelEstados;

    private final Timer timer;

    public Graficos() {
        setLayout(new BorderLayout(8, 8));
        JPanel controles = crearPanelControles();
        add(controles, BorderLayout.NORTH);

        // Panel central con dos gráficos lado a lado
        JPanel charts = new JPanel(new GridLayout(1, 2, 8, 8));
        chartPanelMeses = new ChartPanel(GraficoVacioBarras("Órdenes por mes"));
        chartPanelEstados = new ChartPanel(GraficoVacioPie("Órdenes por estado"));
        charts.add(chartPanelMeses);
        charts.add(chartPanelEstados);
        add(charts, BorderLayout.CENTER);

        // Debounce timer: 600 ms
        timer = new Timer(600, e -> actualizarGraficosEnBackground());
        timer.setRepeats(false);

        // Listeners para actualización en "tiempo real"
        chkPreventivo.addItemListener(e -> refrescar());
        chkCorrectivo.addItemListener(e -> refrescar());
        txtDesde.getDocument().addDocumentListener(simpleDocListener());
        txtHasta.getDocument().addDocumentListener(simpleDocListener());
        btnRefrescar.addActionListener(e -> actualizarGraficosEnBackground());

        // Primera carga
        actualizarGraficosEnBackground();
    }

    private JPanel crearPanelControles() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 6));
        panel.add(new JLabel("Tipos:"));
        panel.add(chkPreventivo);
        panel.add(chkCorrectivo);
        panel.add(new JLabel("Desde (dd/MM/yyyy):"));
        panel.add(txtDesde);
        panel.add(new JLabel("Hasta (dd/MM/yyyy):"));
        panel.add(txtHasta);
        panel.add(btnRefrescar);
        return panel;
    }

    private DocumentListener simpleDocListener() {
        return new DocumentListener() {
            public void insertUpdate(DocumentEvent e) { refrescar(); }
            public void removeUpdate(DocumentEvent e) { refrescar(); }
            public void changedUpdate(DocumentEvent e) { refrescar(); }
        };
    }

    private void refrescar() {
        timer.restart();
    }

    
    private void actualizarGraficosEnBackground() {
        
        SwingWorker<Void, Void> worker = new SwingWorker<>() {
            private DefaultCategoryDataset datasetMeses;
            private DefaultPieDataset datasetEstados;

            @Override
            protected Void doInBackground() {
                boolean usePrev = chkPreventivo.isSelected();
                boolean useCorr = chkCorrectivo.isSelected();
                Date desde = parsearDate(txtDesde.getText().trim());
                Date hasta = parsearDate(txtHasta.getText().trim());

                // Construir dataset para "órdenes por mes"
                datasetMeses = construirDatasetOrdenesPorMes(usePrev, useCorr, desde, hasta);

                // Construir dataset para "órdenes por estado"
                datasetEstados = construirDatasetOrdenesPorEstado(usePrev, useCorr, desde, hasta);

                return null;
            }

            @Override
            protected void done() {
                
                JFreeChart chartMeses = ChartFactory.createBarChart(
                        "Órdenes por mes",
                        "Mes",
                        "Cantidad",
                        datasetMeses
                );
                
                CategoryPlot plot = chartMeses.getCategoryPlot();
                BarRenderer renderer = (BarRenderer) plot.getRenderer();
                renderer.setMaximumBarWidth(0.08);

                chartPanelMeses.setChart(chartMeses);

                JFreeChart chartEstados = ChartFactory.createPieChart(
                        "Órdenes por estado",
                        datasetEstados,
                        true,
                        true,
                        false
                );
                PiePlot piePlot = (PiePlot) chartEstados.getPlot();
                piePlot.setLabelGenerator(new StandardPieSectionLabelGenerator("{0}: {1} ({2})"));

                chartPanelEstados.setChart(chartEstados);
            }
        };
        worker.execute();
    }

    
    private DefaultCategoryDataset construirDatasetOrdenesPorMes(boolean preventivo, boolean correctivo, Date desde, Date hasta) {
        Map<String, Integer> contador = new LinkedHashMap<>();

        // Recolectar todas las órdenes según selección
        List<OrdenDeTrabajo> todas = new ArrayList<>();
        if (preventivo) {
            ArrayList<OrdenDeTrabajoPreventivo> prev = controladorOrdenesPreventivo.BuscarTodos();
            if (prev != null) todas.addAll(prev);
        }
        if (correctivo) {
            ArrayList<OrdenDeTrabajoCorrectivo> corr = controladorOrdenesCorrectivo.BuscarTodos();
            if (corr != null) todas.addAll(corr);
        }

        // Agrupar por mes-año usando fechaCreacion o fechaEjecucion
        for (OrdenDeTrabajo o : todas) {
            Date ref = o.getFechaCreacion();
            if (ref == null) ref = o.getFechaEjecucion();
            if (ref == null) continue;
            if (!fechaDentroRangoP(ref, desde, hasta)) continue;

            Calendar cal = Calendar.getInstance();
            cal.setTime(ref);
            int mes = cal.get(Calendar.MONTH); // 0..11
            int anio = cal.get(Calendar.YEAR);
            String key = String.format("%02d/%04d", mes + 1, anio); // MM/YYYY

            contador.put(key, contador.getOrDefault(key, 0) + 1);
        }

        // Ordenar claves cronológicamente
        List<String> claves = new ArrayList<>(contador.keySet());
        claves.sort((a, b) -> {
            try {
                SimpleDateFormat kf = new SimpleDateFormat("MM/yyyy");
                Date da = kf.parse(a);
                Date db = kf.parse(b);
                return da.compareTo(db);
            } catch (ParseException ex) {
                return a.compareTo(b);
            }
        });

        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (String k : claves) {
            dataset.addValue(contador.get(k), "Órdenes", k);
        }

        // Si no hay datos, agregar 0 para el mes actual para que el gráfico no quede vacío
        if (dataset.getColumnCount() == 0) {
            Calendar now = Calendar.getInstance();
            String key = String.format("%02d/%04d", now.get(Calendar.MONTH) + 1, now.get(Calendar.YEAR));
            dataset.addValue(0, "Órdenes", key);
        }

        return dataset;
    }

    // Construye dataset de órdenes por estado usando los métodos Filtrar de los controladores
    private DefaultPieDataset construirDatasetOrdenesPorEstado(boolean preventivo, boolean correctivo, Date desde, Date hasta) {
        int pendientes = 0;
        int terminadas = 0;
        int canceladas = 0;

        if (preventivo) {
            ArrayList<OrdenDeTrabajoPreventivo> allPrev = controladorOrdenesPreventivo.BuscarTodos();
            for (OrdenDeTrabajoPreventivo o : allPrev) {
                if (!fechaDentroRangoP(o.getFechaCreacion() == null ? o.getFechaEjecucion() : o.getFechaCreacion(), desde, hasta)) continue;
                if (o.getFechaCancelacion() != null) canceladas++;
                else if (o.getFechaFinalizacion() != null) terminadas++;
                else pendientes++;
            }
        }

        if (correctivo) {
            ArrayList<OrdenDeTrabajoCorrectivo> allCorr = controladorOrdenesCorrectivo.BuscarTodos();
            for (OrdenDeTrabajoCorrectivo o : allCorr) {
                if (!fechaDentroRangoP(o.getFechaCreacion() == null ? o.getFechaEjecucion() : o.getFechaCreacion(), desde, hasta)) continue;
                if (o.getFechaCancelacion() != null) canceladas++;
                else if (o.getFechaFinalizacion() != null) terminadas++;
                else pendientes++;
            }
        }

        DefaultPieDataset dataset = new DefaultPieDataset();
        dataset.setValue("Pendientes", pendientes);
        dataset.setValue("Terminadas", terminadas);
        dataset.setValue("Canceladas", canceladas);

        return dataset;
    }


    private Date parsearDate(String s) {
        if (s == null || s.isEmpty()) return null;
        try {
            return sdf.parse(s);
        } catch (ParseException ex) {
            return null;
        }
    }

    private boolean fechaDentroRangoP(Date ref, Date desde, Date hasta) {
        if (ref == null) return false;
        if (desde != null && ref.before(desde)) return false;
        if (hasta != null && ref.after(hasta)) return false;
        return true;
    }

    // Gráficos vacíos iniciales
    private JFreeChart GraficoVacioBarras(String title) {
        DefaultCategoryDataset ds = new DefaultCategoryDataset();
        ds.addValue(0, "Órdenes", "00/0000");
        return ChartFactory.createBarChart(title, "Mes", "Cantidad", ds);
    }

    private JFreeChart GraficoVacioPie(String title) {
        DefaultPieDataset ds = new DefaultPieDataset();
        ds.setValue("Pendientes", 0);
        ds.setValue("Terminadas", 0);
        ds.setValue("Canceladas", 0);
        JFreeChart chart = ChartFactory.createPieChart(title, ds, true, true, false);
        PiePlot plot = (PiePlot) chart.getPlot();
        plot.setLabelGenerator(new StandardPieSectionLabelGenerator("{0}: {1} ({2})"));
        return chart;
    }
}
