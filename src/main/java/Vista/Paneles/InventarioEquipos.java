//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package Vista.Paneles;

import Controlador.ControladorEquipo;
import Modelo.Entidades.Equipo;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

public class InventarioEquipos extends JPanel {
    private JTree arbolInventario;
    private DefaultTreeModel treeModel;
    private JTextArea detalleArea;
    private JButton btnEditar;
    private JButton btnEliminar;
    private JButton btnVerDetalle;
    private JButton btnEditarFases;
    private ControladorEquipo controladorEquipo;

    public InventarioEquipos() {
        this.setLayout(new BorderLayout());
        JLabel lblTitulo = new JLabel("Inventario de equipos", 0);
        lblTitulo.setFont(new Font("Arial", 1, 20));
        lblTitulo.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));
        this.add(lblTitulo, "North");
        DefaultMutableTreeNode raiz = new DefaultMutableTreeNode("Inventario de equipos");
        this.controladorEquipo = new ControladorEquipo();
        ArrayList<Equipo> listaEquipos = this.controladorEquipo.BuscarTodos();

        for(Equipo e : listaEquipos) {
            if (e.getIdEquipoPrincipal() == 0) {
                DefaultMutableTreeNode nodoEquipo = this.crearNodoEquipo(e, listaEquipos);
                raiz.add(nodoEquipo);
            }
        }

        this.treeModel = new DefaultTreeModel(raiz);
        this.arbolInventario = new JTree(this.treeModel);
        this.arbolInventario.setRootVisible(true);
        this.arbolInventario.setShowsRootHandles(true);
        JScrollPane scrollArbol = new JScrollPane(this.arbolInventario);
        this.detalleArea = new JTextArea();
        this.detalleArea.setEditable(false);
        this.detalleArea.setFont(new Font("Arial", 0, 16));
        JScrollPane scrollDetalle = new JScrollPane(this.detalleArea);
        JPanel panelBotones = new JPanel(new FlowLayout(1));
        this.btnEditar = new JButton("Editar");
        this.btnEliminar = new JButton("Eliminar");
        this.btnVerDetalle = new JButton("Ver detalle");
        this.btnEditarFases = new JButton("Editar fases");
        panelBotones.add(this.btnEditar);
        panelBotones.add(this.btnEliminar);
        panelBotones.add(this.btnVerDetalle);
        panelBotones.add(this.btnEditarFases);
        JSplitPane divisor = new JSplitPane(1, scrollArbol, scrollDetalle);
        divisor.setDividerLocation(300);
        this.add(divisor, "Center");
        this.add(panelBotones, "South");
        this.btnEditar.addActionListener((ex) -> this.Editar());
        this.btnEliminar.addActionListener((ex) -> this.Eliminar());
        this.btnVerDetalle.addActionListener((ex) -> this.VerDetalle());
        this.btnEditarFases.addActionListener(e -> this.EditarFases());
    }

    private void VerDetalle() {
        TreePath path = this.arbolInventario.getSelectionPath();
        if (path != null) {
            DefaultMutableTreeNode nodo = (DefaultMutableTreeNode)path.getLastPathComponent();
            Object obj = nodo.getUserObject();
            if (obj instanceof Equipo) {
                Equipo eq = (Equipo)obj;
                this.detalleArea.setText(this.formatearDetalle(eq));
            }
        }

    }

    private String formatearDetalle(Equipo eq) {
        Equipo eqPrincipal = this.controladorEquipo.Buscar(eq.getIdEquipoPrincipal());
        int var10000 = eq.getId();
        return "ID: " + var10000 + "\nEquipo principal: " + (eqPrincipal != null && eqPrincipal.getIdEquipoPrincipal() != 0 ? eqPrincipal.getDescripcion() : "No tiene") + "\nDescripción: " + eq.getDescripcion() + "\nUbicación: " + eq.getUbicacionFisica() + "\nFabricante: " + eq.getFabricante() + "\nSerie: " + eq.getSerie() + "\nEstado: " + String.valueOf(eq.getEstadoEquipo()) + "\nCosto inicial: " + eq.getCostoInicial();
    }

    private DefaultMutableTreeNode crearNodoEquipo(Equipo equipo, ArrayList<Equipo> equipos) {
        DefaultMutableTreeNode nodo = new DefaultMutableTreeNode(equipo);

        for(Equipo sub : equipos) {
            if (sub.getIdEquipoPrincipal() == equipo.getId()) {
                nodo.add(this.crearNodoEquipo(sub, equipos));
            }
        }

        return nodo;
    }

    private void Editar() {
        TreePath seleccion = this.arbolInventario.getSelectionPath();
        if (seleccion != null) {
            DefaultMutableTreeNode nodo = (DefaultMutableTreeNode)seleccion.getLastPathComponent();
            if (nodo.getUserObject() instanceof Equipo) {
                Equipo e = (Equipo)nodo.getUserObject();
                if (JOptionPane.showConfirmDialog(this, "¿Desea editar el equipo " + e.getDescripcion() + " ?", "Confirmar edición", 0) == 0) {
                    JFrame frame = new JFrame("Editar equipo");
                    frame.setDefaultCloseOperation(2);
                    frame.setLocationRelativeTo((Component)null);
                    JPanel panelContenido = new RegistrarEquipo(e);
                    frame.add(panelContenido);
                    frame.setSize(1000, 600);
                    frame.setVisible(true);
                }
            }
        }

    }
    private void EditarFases() {
        TreePath seleccion = this.arbolInventario.getSelectionPath();
        if (seleccion != null) {
            DefaultMutableTreeNode nodo = (DefaultMutableTreeNode)seleccion.getLastPathComponent();
            if (nodo.getUserObject() instanceof Equipo) {
                Equipo e = (Equipo)nodo.getUserObject();
                if (JOptionPane.showConfirmDialog(this, "¿Desea editar las fases del equipo " + e.getDescripcion() + " ?", "Confirmar edición", 0) == 0) {
                    JFrame frame = new JFrame("Editar fases");
                    frame.setDefaultCloseOperation(2);
                    frame.setLocationRelativeTo((Component)null);
                    JPanel panelContenido = new ListaFases(e);
                    frame.add(panelContenido);
                    frame.setSize(1000, 600);
                    frame.setVisible(true);
                }
            }
        }
    }
    private void Eliminar() {
        TreePath seleccion = this.arbolInventario.getSelectionPath();
        if (seleccion != null) {
            DefaultMutableTreeNode nodo = (DefaultMutableTreeNode)seleccion.getLastPathComponent();
            if (nodo.getUserObject() instanceof Equipo) {
                Equipo e = (Equipo)nodo.getUserObject();
                if (JOptionPane.showConfirmDialog(this, "¿Desea eliminar el equipo " + e.getDescripcion() + " ?", "Confirmar eliminación", 0) == 0) {
                    this.treeModel.removeNodeFromParent(nodo);
                    this.detalleArea.setText("");
                    this.controladorEquipo.EliminarEquipo(e.getId());
                }
            }
        }

    }
}
