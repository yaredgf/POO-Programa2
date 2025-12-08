/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Vista.Paneles;


import Controlador.ControladorOrdenesPreventivo;
import Modelo.Entidades.Equipo;
import Modelo.Entidades.Falla;
import Modelo.Entidades.FallaObservada;
import Modelo.Entidades.OrdenDeTrabajoPreventivo;

import javax.swing.*;
import java.awt.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
/**
 *
 * @author Usuario
 */
public class EditarOrdenPreventiva extends JPanel 
{
    OrdenDeTrabajoPreventivo orden;
    OrdenDeTrabajoPreventivo ordenEditable;
    JPanel panelContenido;

    JLabel titulo;
    JTabbedPane tabbedPane;
    ControladorOrdenesPreventivo controlador;

    //Panel izquierdo
    JPanel panelContenidoIzq;
    JLabel txtEquipoSeleccionado;
    JButton btnBuscarEquipo;

    //modal equipos
    JFrame modalEquipos;
    JList listaEquipos;
    DefaultListModel<Equipo> modeloEquipos = new DefaultListModel<>();
    Equipo equipoSeleccionado;

    JTextField txtFechaRealizacion;
    JTextArea txtObservacionesIniciales;


    //Panel derecho
    JPanel panelContenidoDer;
    JComboBox<Falla> listaFallasRepor;
    JButton btnAgregarFallaRepor;
    JList listaFallasAgregadasRepor;
    JTextField txtCausaRepor;
    JTextField txtAccionesRepor;
    DefaultListModel<Falla> modeloFallasAgregadasRepor = new DefaultListModel<>();

    JPanel panelContenidoDer2;
    JComboBox<Falla> listaFallasObserv;
    JButton btnAgregarFallaObserv;
    JList listaFallasAgregadasObserv;
    JTextField txtCausaObserv;
    JTextField txtAccionesObserv;
    DefaultListModel<Falla> modeloFallasAgregadasObserv = new DefaultListModel<>();

    //Panel botones
    JPanel panelBotones;
    JButton btnGuardar;
    JButton btnCancelar;

    public EditarOrdenPreventiva(OrdenDeTrabajoPreventivo orden) {

        this.orden = orden;

        ordenEditable = new OrdenDeTrabajoPreventivo();
        ordenEditable.setId(orden.getId());
        ordenEditable.setIdEquipo(orden.getIdEquipo());
        ordenEditable.setFechaEjecucion(orden.getFechaEjecucion());
        ordenEditable.setObservacionesIniciales(orden.getObservacionesIniciales());
        ordenEditable.setFallasObservadas(new ArrayList<>(orden.getFallasObservadas()));

        this.controlador = new ControladorOrdenesPreventivo();

        for (FallaObservada fo : ordenEditable.getFallasObservadas()) {
            Falla f = controlador.BuscarFalla(fo.getIdFalla());
            if (f != null) modeloFallasAgregadasRepor.addElement(f);
        }


        this.equipoSeleccionado = controlador.GetEquipo(orden.getIdEquipo());
        InicializarComponentes();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        txtFechaRealizacion.setText(sdf.format(orden.getFechaEjecucion()));
        txtObservacionesIniciales.setText(orden.getObservacionesIniciales());



    }

    private void InicializarComponentes(){
        setLayout(new BorderLayout());
        panelContenido = new JPanel();
        panelContenido.setLayout(new GridLayout(1, 2, 0 , 5));

        panelContenidoIzq = new JPanel();
        panelContenidoIzq.setLayout(new BorderLayout());

        JPanel panelEquipo = new JPanel();
        panelEquipo.setLayout(new BorderLayout());
        JPanel bloque = new JPanel();
        bloque.setLayout(new BorderLayout());
        bloque.setAlignmentX(0);
        btnBuscarEquipo = new JButton("Seleccionar Equipo");
        btnBuscarEquipo.setFont(new Font("Arial", Font.BOLD, 15));
        btnBuscarEquipo.addActionListener(e -> {
            BuscarEquipo();
        });
        btnBuscarEquipo.setAlignmentX(0);


        JPanel bloque2 = new JPanel();
        bloque2.setAlignmentX(0);
        bloque2.setLayout(new GridLayout(2, 1, 5, 0));
        JLabel lblEqSelec = new JLabel("Equipo seleccionado");
        lblEqSelec.setFont(new Font("Arial", Font.BOLD, 15));
        txtEquipoSeleccionado = new JLabel();
        txtEquipoSeleccionado.setText((equipoSeleccionado == null ? "No se ha seleccionado ninguno." : equipoSeleccionado.toString()));
        txtEquipoSeleccionado.setFont(new Font("Arial", Font.PLAIN, 15));
        txtEquipoSeleccionado.setAlignmentX(0);
        lblEqSelec.setAlignmentX(0);
        bloque2.add(lblEqSelec);
        bloque2.add(txtEquipoSeleccionado);

        bloque2.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));

        JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, btnBuscarEquipo, bloque2);
        split.setDividerSize(0);
        split.setResizeWeight(0.3);
        split.setBorder(null);

        bloque.add(split, BorderLayout.CENTER);
        panelEquipo.add(bloque, BorderLayout.CENTER);


        JPanel formulario = new  JPanel();
        formulario.setLayout(new BoxLayout(formulario, BoxLayout.Y_AXIS));

        bloque = new JPanel();
        bloque.setAlignmentX(0);
        bloque.setLayout(new GridLayout(2, 1, 0, 5));
        JLabel lblFechaRealizacion = new JLabel("Fecha realizacion (dd/mm/yyyy)");
        lblFechaRealizacion.setFont(new Font("Arial", Font.BOLD, 15));
        bloque.add(lblFechaRealizacion);
        txtFechaRealizacion = new JTextField();
        bloque.add(txtFechaRealizacion);
        formulario.add(bloque);

        bloque = new JPanel();
        bloque.setLayout( new BorderLayout());
        bloque.setAlignmentX(0);

        JLabel lblObservaciones = new JLabel("Observaciones:");
        lblObservaciones.setFont(new Font("Arial", Font.BOLD, 15));
        bloque.add(lblObservaciones, BorderLayout.NORTH);

        txtObservacionesIniciales = new JTextArea();
        txtObservacionesIniciales.setAlignmentX(0);
        txtObservacionesIniciales.setMinimumSize(new Dimension(Integer.MAX_VALUE, txtObservacionesIniciales.getPreferredSize().height));
        bloque.add(txtObservacionesIniciales, BorderLayout.CENTER);
        formulario.add(bloque);


        JSplitPane splitContIzq = new JSplitPane(JSplitPane.VERTICAL_SPLIT, panelEquipo, formulario);
        splitContIzq.setDividerSize(0);
        splitContIzq.setResizeWeight(0.08);
        splitContIzq.setBorder(null);

        panelContenidoIzq.add(splitContIzq, BorderLayout.CENTER);

        panelContenidoDer = new JPanel();
        panelContenidoDer.setLayout(new BoxLayout(panelContenidoDer, BoxLayout.Y_AXIS));
        JLabel tituloFallas = new JLabel("Fallas");
        tituloFallas.setFont(new Font("Arial", Font.BOLD, 17));
        tituloFallas.setAlignmentX(0);
        panelContenidoDer.add(tituloFallas);

        bloque = new JPanel();
        bloque.setLayout(new GridLayout(1, 2,10, 0));
        listaFallasRepor = new JComboBox<>();
        for(Falla f: controlador.BuscarTodasFallas()){
            listaFallasRepor.addItem(f);
        }
        bloque.add(listaFallasRepor);
        btnAgregarFallaRepor = new JButton("Agregar");
        btnAgregarFallaRepor.setFont(new Font("Arial", Font.BOLD, 15));
        btnAgregarFallaRepor.addActionListener(e -> {
            Falla f = (Falla) listaFallasRepor.getSelectedItem();
            if (f == null) return;

            FallaObservada nueva = new FallaObservada();
            nueva.setIdFalla(f.getId());
            nueva.setCausa("");
            nueva.setAccionesTomadas("");

            ordenEditable.getFallasObservadas().add(nueva);
            modeloFallasAgregadasRepor.addElement(f);
        });

        bloque.add(btnAgregarFallaRepor);
        panelContenidoDer.add(bloque);

        bloque = new JPanel();
        bloque.setLayout(new BorderLayout());


        listaFallasAgregadasRepor = new JList<>(modeloFallasAgregadasRepor);
        txtCausaRepor = new JTextField();
        txtAccionesRepor = new JTextField();
        // Selección inicial de fallas reportadas
        if (!modeloFallasAgregadasRepor.isEmpty()) {
            listaFallasAgregadasRepor.setSelectedIndex(0);

            FallaObservada fo = CargarFallaObservadaReportada(0);
            if (fo != null) {
                txtCausaRepor.setText(fo.getCausa());
                txtAccionesRepor.setText(fo.getAccionesTomadas());
            }
        } else {
            txtCausaRepor.setText("");
            txtAccionesRepor.setText("");
        }

        listaFallasAgregadasRepor.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listaFallasAgregadasRepor.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int index = listaFallasAgregadasRepor.getSelectedIndex();
                if (index == -1) return;

                FallaObservada fo = CargarFallaObservadaReportada(index);
                if (fo != null) {
                    txtCausaRepor.setText(fo.getCausa() != null ? fo.getCausa() : "");
                    txtAccionesRepor.setText(fo.getAccionesTomadas() != null ? fo.getAccionesTomadas() : "");
                } else {
                    txtCausaRepor.setText("");
                    txtAccionesRepor.setText("");
                }
            }
        });


        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.add(new JScrollPane(listaFallasAgregadasRepor), BorderLayout.CENTER);
        panel.setBorder(BorderFactory.createEmptyBorder(10,0,10,0));

        JPanel panelFormRepor = new JPanel();
        panelFormRepor.setLayout(new GridLayout(4, 1, 0, 5));
        JLabel lblCausa = new JLabel("Causa:");
        txtCausaRepor = new JTextField();
        txtCausaRepor.setAlignmentX(0);
        JLabel lblAcciones = new JLabel("Acciones tomadas:");
        txtAccionesRepor = new JTextField();
        txtAccionesRepor.setAlignmentX(0);
        panelFormRepor.add(lblCausa);
        panelFormRepor.add(txtCausaRepor);
        panelFormRepor.add(lblAcciones);
        panelFormRepor.add(txtAccionesRepor);
        JSplitPane splitFallaRepor = new JSplitPane(JSplitPane.VERTICAL_SPLIT, panel, panelFormRepor);
        splitFallaRepor.setDividerSize(0);
        splitFallaRepor.setResizeWeight(0.8);
        bloque.add(splitFallaRepor,  BorderLayout.CENTER);

        JButton btnActualizarFalla = new JButton("Actualizar");
        btnActualizarFalla.setFont(new Font("Arial", Font.BOLD, 15));
        btnActualizarFalla.addActionListener(e -> {
            if (listaFallasAgregadasRepor.isSelectionEmpty()) {
                JOptionPane.showMessageDialog(null, "No se ha seleccionado ninguna falla.", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                if (txtCausaRepor.getText().isEmpty() || txtAccionesRepor.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "No se ha llenado los campos para la falla.", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    int index = listaFallasAgregadasRepor.getSelectedIndex();
                    FallaObservada fo = CargarFallaObservadaReportada(index);
                    if (fo != null) {
                        fo.setCausa(txtCausaRepor.getText());
                        fo.setAccionesTomadas(txtAccionesRepor.getText());
                        JOptionPane.showMessageDialog(null, "Se actualizó la falla observada correctamente.", "Exito :)", JOptionPane.INFORMATION_MESSAGE);
                        int sel = listaFallasAgregadasRepor.getSelectedIndex();
                        listaFallasAgregadasRepor.setSelectedIndex(-1);
                        listaFallasAgregadasRepor.setSelectedIndex(sel);

                    }
                }
            }
        });


        JButton btnEliminar =  new JButton("Eliminar");
        btnEliminar.setFont(new Font("Arial", Font.BOLD, 15));
        btnEliminar.addActionListener(e -> {
            if(listaFallasAgregadasRepor.isSelectionEmpty())
                JOptionPane.showMessageDialog(null, "No se ha seleccionado ninguna falla.", "Error", JOptionPane.ERROR_MESSAGE);
            else{
                EliminarFallaRepor(modeloFallasAgregadasRepor.get(listaFallasAgregadasRepor.getSelectedIndex()));

            }
        });

        JPanel panpanel = new JPanel();
        panpanel.add(btnActualizarFalla);
        panpanel.add(btnEliminar);
        bloque.add(panpanel, BorderLayout.SOUTH);
        panelContenidoDer.add(bloque);

        tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Observadas", panelContenidoDer);

        panelContenidoDer2 = new JPanel();
        panelContenidoDer2 = new JPanel();
        panelContenidoDer2.setLayout(new BoxLayout(panelContenidoDer2, BoxLayout.Y_AXIS));

        JLabel tituloFallas2 = new JLabel("Fallas");
        tituloFallas2.setFont(new Font("Arial", Font.BOLD, 17));
        tituloFallas2.setAlignmentX(0);
        panelContenidoDer2.add(tituloFallas2);

        



        panelContenidoIzq.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
        panelContenidoDer.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
        panelContenido.add(panelContenidoIzq);
        panelContenido.add(tabbedPane);
        panelContenido.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));

        add(panelContenido, BorderLayout.CENTER);

        panelBotones = new JPanel();
        panelBotones.setLayout(new BoxLayout(panelBotones, BoxLayout.X_AXIS));
        panelBotones.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        btnGuardar = new JButton("Actualizar");
        btnGuardar.setFont(new Font("Arial", Font.BOLD, 15));
        btnGuardar.addActionListener(e -> {
            if(Guardar()){
                JOptionPane.showMessageDialog(this, "La orden se actualizó correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                Window w = SwingUtilities.getWindowAncestor(this);
                if (w != null) w.dispose();
            }
        });
        btnCancelar = new JButton("Volver");
        btnCancelar.setFont(new Font("Arial", Font.BOLD, 15));
        btnCancelar.addActionListener(e -> {
            Window w = SwingUtilities.getWindowAncestor(this);
            if (w != null) w.dispose();
        });
        btnGuardar.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnCancelar.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelBotones.add(btnGuardar);
        panelBotones.add(btnCancelar);

        add(panelBotones, BorderLayout.SOUTH);
    }

    private void BuscarEquipo(){
        JLabel lblEqSeleccion = new JLabel();
        //Carga el modal
        modalEquipos = new JFrame("Buscar Equipo");
        modalEquipos.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        JPanel contenido = new  JPanel();
        contenido.setLayout(new BoxLayout(contenido, BoxLayout.Y_AXIS));
        JPanel panelLista = new JPanel(new BorderLayout());
        modeloEquipos = new DefaultListModel<>();
        modeloEquipos.addAll(controlador.BuscarTodosEquipos());
        listaEquipos = new JList(modeloEquipos);
        listaEquipos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listaEquipos.addListSelectionListener(e ->{
            if(!e.getValueIsAdjusting()){
                equipoSeleccionado = (Equipo)modeloEquipos.get(listaEquipos.getSelectedIndex());
                lblEqSeleccion.setText(equipoSeleccionado.toString());
                lblEqSeleccion.revalidate();
                lblEqSeleccion.repaint();
                txtEquipoSeleccionado.setText(equipoSeleccionado.toString());
                txtEquipoSeleccionado.revalidate();
                txtEquipoSeleccionado.repaint();
            }
        });
        panelLista.add(new JScrollPane(listaEquipos),  BorderLayout.CENTER);
        contenido.add(panelLista);

        JPanel panelInferior = new JPanel(new GridLayout(1, 2, 10, 0));

        JPanel panelBotones = new JPanel();
        JButton btnVolver = new JButton("Volver");
        btnVolver.setFont(new Font("Arial", Font.BOLD, 15));
        btnVolver.addActionListener(e -> {
            modalEquipos.dispose();
        });
        panelBotones.add(btnVolver);
        panelInferior.add(panelBotones);

        JPanel panelTxtEqSelec = new JPanel();
        panelTxtEqSelec.setLayout(new BoxLayout(panelTxtEqSelec, BoxLayout.Y_AXIS));
        JLabel tituloEqSelec = new JLabel("Equipo seleccionado:");
        tituloEqSelec.setFont(new Font("Arial", Font.BOLD, 17));
        tituloEqSelec.setAlignmentX(0);
        panelTxtEqSelec.add(tituloEqSelec);
        lblEqSeleccion.setFont(new Font("Arial", Font.PLAIN, 15));
        if(equipoSeleccionado == null)
            lblEqSeleccion.setText("No se ha seleccionado ninguno.");
        else{
            lblEqSeleccion.setText(equipoSeleccionado.toString());
        }
        lblEqSeleccion.revalidate();
        lblEqSeleccion.repaint();

        lblEqSeleccion.setAlignmentX(0);
        panelTxtEqSelec.add(lblEqSeleccion);
        panelInferior.add(panelTxtEqSelec);

        modalEquipos.add(contenido, BorderLayout.CENTER);
        modalEquipos.add(panelInferior, BorderLayout.SOUTH);
        modalEquipos.pack();
        modalEquipos.setLocationRelativeTo(null);
        modalEquipos.setVisible(true);
    }

    private boolean Guardar(){
        try{
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            orden.setIdEquipo(equipoSeleccionado.getId());
            orden.setFechaEjecucion(sdf.parse(txtFechaRealizacion.getText()));
            orden.setObservacionesIniciales(txtObservacionesIniciales.getText());
            orden.setFallasObservadas(ordenEditable.getFallasObservadas());

            return controlador.Actualizar(orden);
        }
        catch(ParseException e){
            JOptionPane.showMessageDialog(this, "La fecha está en formato incorrecto.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    private FallaObservada CargarFallaObservadaReportada(int index) {

        if (index < 0 || index >= modeloFallasAgregadasRepor.size())
            return null;

        Falla fallaSeleccionada = modeloFallasAgregadasRepor.get(index);

         for (FallaObservada fo : ordenEditable.getFallasObservadas()) {
            if (fo.getIdFalla() == fallaSeleccionada.getId()) {
                return fo;
            }
        }

        return null;
    }

    

    private void EliminarFallaRepor(Falla falla){
        modeloFallasAgregadasRepor.remove(modeloFallasAgregadasRepor.indexOf(falla));
        ordenEditable.getFallasObservadas()
                .removeIf(fo -> fo.getIdFalla() == falla.getId());
        listaFallasAgregadasRepor.revalidate();
        listaFallasAgregadasRepor.repaint();
    }

}
