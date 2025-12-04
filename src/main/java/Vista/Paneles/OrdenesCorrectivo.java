package Vista.Paneles;

import Controlador.ControladorOrdenesCorrectivo;
import Modelo.Entidades.OrdenDeTrabajoCorrectivo;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class OrdenesCorrectivo extends JPanel {
    private ControladorOrdenesCorrectivo controlador;

    private JPanel panelContenido;
    private JPanel panelBotones;
    private JPanel panelLista;
    private JPanel panelDetalles;
    private JPanel panelCombo;
    private JList <OrdenDeTrabajoCorrectivo> listaUI;
    private DefaultListModel <OrdenDeTrabajoCorrectivo> listaUIModelo;
    private ArrayList<OrdenDeTrabajoCorrectivo> lista;
    private OrdenDeTrabajoCorrectivo ordenSeleccionada;

    private JButton btnAgregar;
    private JButton btnEditar;
    private JButton btnCerrar;
    private JButton btnCancelar;

    public OrdenesCorrectivo(){
        controlador = new ControladorOrdenesCorrectivo();
        InicializarComponentes();
    }

    private void InicializarComponentes() {
        setLayout(new BorderLayout());

        JComboBox<String> combo = new JComboBox<>();
        combo.addItem("Pendientes");
        combo.addItem("En curso");
        combo.addItem("Terminadas");
        combo.addItem("Canceladas");
        combo.addItem("Todas");

        combo.addActionListener(e -> {
            String filtro =  combo.getSelectedItem().toString();
            Filtrar(filtro);
        });

        panelCombo = new JPanel();
        panelCombo.setBackground(new Color(255, 255, 255));
        panelCombo.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panelCombo.setLayout(new BorderLayout());
        panelCombo.add(combo, BorderLayout.WEST);
        add(panelCombo, BorderLayout.NORTH);

        panelContenido = new JPanel();
        panelContenido.setLayout(new GridLayout(1, 2, 5, 5));
        panelContenido.setBackground(new Color(255, 255, 255));
        ArrayList<OrdenDeTrabajoCorrectivo>  lista = new ArrayList<>();
        lista = controlador.BuscarTodos();

        listaUIModelo = new DefaultListModel<>();
        this.listaUIModelo.addAll(lista);
        listaUI = new JList<>(this.listaUIModelo);
        listaUI.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        panelLista = new JPanel();
        panelLista.setLayout(new BorderLayout());
        panelLista.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panelLista.add(new JScrollPane(listaUI), BorderLayout.CENTER);
        panelContenido.add(panelLista);
        panelDetalles = new JPanel();
        panelDetalles.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panelDetalles.setBackground(new Color(255, 255, 255));
        JLabel uwu = new JLabel("Omg un texto lesgo sisi uwu.");
        uwu.setFont(new Font("Times New Roman", Font.BOLD, 20));
        panelDetalles.add(uwu);
        panelContenido.add(panelDetalles);

        add(panelContenido, BorderLayout.CENTER);

        panelBotones = new JPanel();
        panelBotones.setBackground(new Color(255, 255, 255));

        btnAgregar = new JButton("Agregar");
        btnAgregar.addActionListener(e -> {Agregar();});

        btnEditar = new JButton("Editar");
        btnEditar.addActionListener(e ->{
            Editar((OrdenDeTrabajoCorrectivo)this.listaUIModelo.get(this.listaUI.getSelectedIndex()));
        });

        btnCerrar = new JButton("Cerrar Orden");
        btnCerrar.addActionListener(e -> {
            Cerrar(((OrdenDeTrabajoCorrectivo)this.listaUIModelo.get(this.listaUI.getSelectedIndex())));
        });
        btnCancelar = new JButton("Cancelar Orden");
        btnCancelar.addActionListener(e -> {
            Cancelar((OrdenDeTrabajoCorrectivo)this.listaUIModelo.get(this.listaUI.getSelectedIndex()));
        });

        panelBotones.add(btnAgregar);
        panelBotones.add(btnEditar);
        panelBotones.add(btnCerrar);
        panelBotones.add(btnCancelar);

        add(panelBotones, BorderLayout.SOUTH);
    }

    private void Filtrar(String filtro){
        listaUIModelo.clear();
        lista = controlador.Filtrar(filtro);
        listaUIModelo.addAll(lista);
    }

    private void Agregar(){
        JFrame frame = new JFrame("Agregar Orden Correctiva");
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        JPanel panelContenido = new AgregarOrdenCorrectiva();
        frame.add(panelContenido);
        frame.setSize(1000, 600);
        frame.setVisible(true);
    }

    private void Cerrar(OrdenDeTrabajoCorrectivo orden){


    }

    private void Editar(OrdenDeTrabajoCorrectivo orden){

    }

    private void Cancelar(OrdenDeTrabajoCorrectivo orden){
        if(JOptionPane.showConfirmDialog(this, "¿Seguro que desea cancelar la orden de trabajo correctivo #"+orden.getId()+"?", "Confirmación", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION){
            String motivo = JOptionPane.showInputDialog(this, "Ingrese un motivo de cancelación: ", "Cancelar orden", JOptionPane.QUESTION_MESSAGE);
            controlador.Cancelar(orden.getId(),motivo);
            JOptionPane.showMessageDialog(this, "Orden cancelada correctamente");
        }
        else{
            JOptionPane.showMessageDialog(this, "Operación interrumpida.");
        }


    }
}

