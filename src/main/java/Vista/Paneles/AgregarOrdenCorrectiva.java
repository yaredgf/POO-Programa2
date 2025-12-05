package Vista.Paneles;

import Controlador.ControladorOrdenesCorrectivo;
import Modelo.Entidades.Equipo;
import Modelo.Entidades.Falla;
import Modelo.Entidades.OrdenDeTrabajoCorrectivo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class AgregarOrdenCorrectiva extends JPanel {
    ControladorOrdenesCorrectivo controlador;
    JPanel panelContenido;

    //Panel izquierdo
    JPanel contenidoIzq;
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
    JPanel contenidoDer;
    JComboBox<Falla> listaFallas;
    JButton btnAgregarFalla;
    ArrayList<Falla> fallasAgregadas;
    JList listaFallasAgregadas;
    DefaultListModel<Falla> modeloFallasAgregadas = new DefaultListModel<>();

    //Panel botones
    JPanel panelBotones;
    JButton btnAgregar;
    JButton btnCancelar;

    public AgregarOrdenCorrectiva(){
        InicializarComponentes();
    }

    private void InicializarComponentes() {
        controlador = new ControladorOrdenesCorrectivo();
        setLayout(new BorderLayout());
        panelContenido = new JPanel();
        panelContenido.setLayout(new GridLayout(1, 2, 0 , 5));


        contenidoIzq = new JPanel();
        contenidoIzq.setLayout(new BorderLayout());

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
        bloque.add(lblFechaRealizacion);
        bloque.add(txtFechaRealizacion);
        formulario.add(bloque);

        bloque = new JPanel();
        bloque.setLayout( new BorderLayout());
        bloque.setAlignmentX(0);

        JLabel lblObservaciones = new JLabel("Observaciones iniciales:");
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

        contenidoIzq.add(splitContIzq, BorderLayout.CENTER);


        contenidoDer = new JPanel();
        contenidoDer.setLayout(new BoxLayout(contenidoDer, BoxLayout.Y_AXIS));
        JLabel tituloFallas = new JLabel("Fallas");
        tituloFallas.setFont(new Font("Arial", Font.BOLD, 17));
        tituloFallas.setAlignmentX(0);
        contenidoDer.add(tituloFallas);

        bloque = new JPanel();
        bloque.setLayout(new GridLayout(1, 2,10, 0));
        listaFallas = new JComboBox<>();
        for(Falla f: controlador.BuscarTodasFallas()){
            listaFallas.addItem(f);
        }
        bloque.add(listaFallas);
        btnAgregarFalla = new JButton("Agregar");
        btnAgregarFalla.setFont(new Font("Arial", Font.BOLD, 15));
        btnAgregarFalla.addActionListener(e -> {
            fallasAgregadas.add((Falla)listaFallas.getSelectedItem());
            modeloFallasAgregadas.removeAllElements();
            modeloFallasAgregadas.addAll(fallasAgregadas);
            listaFallasAgregadas.revalidate();
            listaFallasAgregadas.repaint();
        });
        bloque.add(btnAgregarFalla);
        contenidoDer.add(bloque);

        bloque = new JPanel();
        bloque.setLayout(new BorderLayout());

        fallasAgregadas = new ArrayList<>();
        modeloFallasAgregadas = new DefaultListModel<>();
        listaFallasAgregadas = new JList<>(modeloFallasAgregadas);
        listaFallasAgregadas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.add(new JScrollPane(listaFallasAgregadas), BorderLayout.CENTER);
        panel.setBorder(BorderFactory.createEmptyBorder(10,0,10,0));

        bloque.add(panel,  BorderLayout.CENTER);


        JButton btnEliminar =  new JButton("Eliminar");
        btnEliminar.setFont(new Font("Arial", Font.BOLD, 15));
        btnEliminar.addActionListener(e -> {
            if(listaFallasAgregadas.isSelectionEmpty())
                JOptionPane.showMessageDialog(null, "No se ha seleccionado ninguna falla.", "Error", JOptionPane.ERROR_MESSAGE);
            else{
                EliminarFalla(((Falla)modeloFallasAgregadas.get(listaFallasAgregadas.getSelectedIndex())));

            }
        });

        bloque.add(btnEliminar,  BorderLayout.SOUTH);
        contenidoDer.add(bloque);
        contenidoIzq.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
        contenidoDer.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
        panelContenido.add(contenidoIzq);
        panelContenido.add(contenidoDer);
        panelContenido.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));

        add(panelContenido, BorderLayout.CENTER);

        panelBotones = new JPanel();
        panelBotones.setLayout(new BoxLayout(panelBotones, BoxLayout.X_AXIS));
        panelBotones.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        btnAgregar = new JButton("Agregar");
        btnAgregar.setFont(new Font("Arial", Font.BOLD, 15));
        btnAgregar.addActionListener(e -> {
            Agregar();
        });
        btnCancelar = new JButton("Volver");
        btnCancelar.setFont(new Font("Arial", Font.BOLD, 15));
        btnCancelar.addActionListener(e -> {
            Window w = SwingUtilities.getWindowAncestor(this);
            if (w != null) w.dispose();
        });
        btnAgregar.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnCancelar.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelBotones.add(btnAgregar);
        panelBotones.add(btnCancelar);

        add(panelBotones, BorderLayout.SOUTH);




    }
    private void Agregar(){
        try{

            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            OrdenDeTrabajoCorrectivo orden = new OrdenDeTrabajoCorrectivo();
            orden.setIdEquipo(equipoSeleccionado.getId());
            orden.setObservacionesIniciales(txtObservacionesIniciales.getText());
            orden.setFechaEjecucion(sdf.parse(txtFechaRealizacion.getText()));
            controlador.Agregar(orden);
        }
        catch(ParseException e){
            JOptionPane.showMessageDialog(null, "Fallo al agregar: Formato de fecha incorrecto.", "Error", JOptionPane.ERROR_MESSAGE);
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null, "Fallo al agregar: Llame a Dios.", "Error", JOptionPane.ERROR_MESSAGE);
        }

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

    private void EliminarFalla(Falla falla){
        modeloFallasAgregadas.remove(modeloFallasAgregadas.indexOf(falla));
        fallasAgregadas.remove(falla);
        listaFallasAgregadas.revalidate();
        listaFallasAgregadas.repaint();
    }
}
