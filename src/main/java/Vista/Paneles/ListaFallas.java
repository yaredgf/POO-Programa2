//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package Vista.Paneles;

import Controlador.ControladorFalla;
import Modelo.Entidades.Falla;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class ListaFallas extends JPanel {
    private JList<Falla> listaFallas;
    private DefaultListModel<Falla> modeloListaFallas = new DefaultListModel();
    private JButton btnAgregar;
    private JButton btnEditar;
    private JButton btnEliminar;
    private ControladorFalla controladorFalla;

    public ListaFallas() {
        this.inicializarComponentes();
    }

    public void inicializarComponentes() {
        this.setLayout(new BorderLayout());
        JPanel panelSuperior = new JPanel();
        panelSuperior.setLayout(new BorderLayout());
        JLabel lblTitulo = new JLabel("Lista de fallas", 0);
        lblTitulo.setFont(new Font("Arial", 1, 20));
        lblTitulo.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));
        panelSuperior.add(lblTitulo, "North");
        this.modeloListaFallas.clear();
        this.controladorFalla = new ControladorFalla();
        this.modeloListaFallas.addAll(this.controladorFalla.BuscarTodos());
        this.listaFallas = new JList(this.modeloListaFallas);
        this.listaFallas.setSelectionMode(0);
        this.listaFallas.setLayoutOrientation(0);
        DefaultListCellRenderer renderer = new DefaultListCellRenderer();
        renderer.setHorizontalAlignment(0);
        this.listaFallas.setCellRenderer(renderer);
        panelSuperior.add(new JScrollPane(this.listaFallas), "Center");
        this.add(panelSuperior, "North");
        JPanel panelBotones = new JPanel(new FlowLayout(1));
        this.btnAgregar = new JButton("Agregar");
        this.btnEditar = new JButton("Editar");
        this.btnEliminar = new JButton("Eliminar");
        this.btnAgregar.addActionListener((e) -> this.Agregar());
        this.btnEditar.addActionListener((e) -> this.Editar((Falla)this.modeloListaFallas.get(this.listaFallas.getSelectedIndex())));
        this.btnEliminar.addActionListener((e) -> this.Eliminar(((Falla)this.modeloListaFallas.get(this.listaFallas.getSelectedIndex())).getId()));
        panelBotones.add(this.btnAgregar);
        panelBotones.add(this.btnEditar);
        panelBotones.add(this.btnEliminar);
        this.add(panelBotones, "South");
    }

    private void Agregar() {
        JFrame frame = new JFrame("Nueva falla");
        frame.setDefaultCloseOperation(2);
        frame.setLocationRelativeTo((Component)null);
        JPanel panelContenido = new RegistrarFallas();
        frame.add(panelContenido);
        frame.setSize(1000, 600);
        frame.setVisible(true);
    }

    private void Eliminar(int id) {
        if (JOptionPane.showConfirmDialog(this, "¿Desea eliminar la falla " + this.controladorFalla.Buscar(id).getDescripcion() + "?", "Confirmar edición", 0) == 0) {
            this.controladorFalla.Eliminar(id);
            this.modeloListaFallas.removeElement(this.controladorFalla.Buscar(id));
            this.listaFallas.updateUI();
        }

    }

    private void Editar(Falla f) {
        if (JOptionPane.showConfirmDialog(this, "¿Desea editar la falla " + f.getDescripcion() + "?", "Confirmar edición", 0) == 0) {
            JFrame frame = new JFrame("Editar falla");
            frame.setDefaultCloseOperation(2);
            frame.setLocationRelativeTo((Component)null);
            JPanel panelContenido = new RegistrarFallas(f);
            frame.add(panelContenido);
            frame.setSize(1000, 600);
            frame.setVisible(true);
        }

    }
}
