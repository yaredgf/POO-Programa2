package Vista.Paneles;

import Controlador.ControladorFalla;
import Controlador.ControladorTarea;
import Modelo.Entidades.Falla;
import Modelo.Entidades.Tarea;

import javax.swing.*;
import java.awt.*;

public class ListaTareas extends JPanel {
    private JList<Tarea> listaTareas;
    private DefaultListModel<Tarea> modeloListaTareas = new DefaultListModel();
    private JButton btnAgregar;
    private JButton btnEditar;
    private JButton btnEliminar;
    private ControladorTarea controladorTarea;

    public ListaTareas() {
        this.inicializarComponentes();
    }

    public void inicializarComponentes() {
        this.setLayout(new BorderLayout());
        JPanel panelSuperior = new JPanel();
        panelSuperior.setLayout(new BorderLayout());
        JLabel lblTitulo = new JLabel("Lista de Tareas", 0);
        lblTitulo.setFont(new Font("Arial", 1, 20));
        lblTitulo.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));
        panelSuperior.add(lblTitulo, "North");
        this.modeloListaTareas.clear();
        this.controladorTarea = new ControladorTarea();
        this.modeloListaTareas.addAll(this.controladorTarea.BuscarTodos());
        this.listaTareas = new JList(this.modeloListaTareas);
        this.listaTareas.setSelectionMode(0);
        this.listaTareas.setLayoutOrientation(0);
        DefaultListCellRenderer renderer = new DefaultListCellRenderer();
        renderer.setHorizontalAlignment(0);
        this.listaTareas.setCellRenderer(renderer);
        panelSuperior.add(new JScrollPane(this.listaTareas), "Center");
        this.add(panelSuperior, "North");
        JPanel panelBotones = new JPanel(new FlowLayout(1));
        this.btnAgregar = new JButton("Agregar");
        this.btnEditar = new JButton("Editar");
        this.btnEliminar = new JButton("Eliminar");
        this.btnAgregar.addActionListener((e) -> this.Agregar());
        this.btnEditar.addActionListener((e) -> this.Editar((Tarea) this.modeloListaTareas.get(this.listaTareas.getSelectedIndex())));
        this.btnEliminar.addActionListener((e) -> this.Eliminar(((Tarea)this.modeloListaTareas.get(this.listaTareas.getSelectedIndex())).getId()));
        panelBotones.add(this.btnAgregar);
        panelBotones.add(this.btnEditar);
        panelBotones.add(this.btnEliminar);
        this.add(panelBotones, "South");
    }

    private void Agregar() {
        JFrame frame = new JFrame("Nueva falla");
        frame.setDefaultCloseOperation(2);
        frame.setLocationRelativeTo((Component)null);
        JPanel panelContenido = new RegistrarTarea();
        frame.add(panelContenido);
        frame.setSize(1000, 600);
        frame.setVisible(true);
    }

    private void Eliminar(int id) {
        if (JOptionPane.showConfirmDialog(this, "¿Desea eliminar la tarea " + this.controladorTarea.Buscar(id).getDescripcion() + "?", "Confirmar edición", 0) == 0) {
            this.controladorTarea.Eliminar(id);
            this.modeloListaTareas.removeElement(this.controladorTarea.Buscar(id));
            this.listaTareas.updateUI();
        }

    }

    private void Editar(Tarea t) {
        if (JOptionPane.showConfirmDialog(this, "¿Desea editar la tarea " + t.getDescripcion() + "?", "Confirmar edición", 0) == 0) {
            JFrame frame = new JFrame("Editar tarea");
            frame.setDefaultCloseOperation(2);
            frame.setLocationRelativeTo((Component)null);
            JPanel panelContenido = new RegistrarTarea(t);
            frame.add(panelContenido);
            frame.setSize(1000, 600);
            frame.setVisible(true);
        }

    }
}
