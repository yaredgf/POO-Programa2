package Vista.Paneles;

import Controlador.ControladorEquipo;
import Modelo.Entidades.Equipo;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import java.awt.*;
import java.util.ArrayList;

import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

public class InventarioEquipos extends JPanel {
    private JTree arbolInventario;
    private DefaultTreeModel treeModel;
    private JTextArea detalleArea;
    private JButton btnEditar, btnEliminar, btnVerDetalle;
    private ControladorEquipo controladorEquipo;
    public InventarioEquipos() {
        setLayout(new BorderLayout());
        DefaultMutableTreeNode raiz = new DefaultMutableTreeNode("Inventario de equipos");
        controladorEquipo = new ControladorEquipo();
        ArrayList<Equipo> listaEquipos = controladorEquipo.BuscarTodos();
        for (Equipo e : listaEquipos) {
            if (e.getIdEquipoPrincipal() == 0) {
                DefaultMutableTreeNode nodoEquipo = crearNodoEquipo(e, listaEquipos);
                raiz.add(nodoEquipo);
            }
        }

        treeModel = new DefaultTreeModel(raiz);
        arbolInventario = new JTree(treeModel);
        arbolInventario.setRootVisible(true);
        arbolInventario.setShowsRootHandles(true);

        JScrollPane scrollArbol = new JScrollPane(arbolInventario);

        detalleArea = new JTextArea();
        detalleArea.setEditable(false);
        JScrollPane scrollDetalle = new JScrollPane(detalleArea);

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER));
        btnEditar = new JButton("Editar");
        btnEliminar = new JButton("Eliminar");
        btnVerDetalle = new JButton("Ver detalle");
        panelBotones.add(btnEditar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnVerDetalle);

        JSplitPane divisor = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, scrollArbol, scrollDetalle);
        divisor.setDividerLocation(300);

        add(divisor, BorderLayout.CENTER);
        add(panelBotones, BorderLayout.SOUTH);


        //Eventos
        btnEditar.addActionListener(e -> Editar());
        btnEliminar.addActionListener(e -> Eliminar());
        btnVerDetalle.addActionListener(e -> VerDetalle());


    }
    private void VerDetalle(){

    }
    private DefaultMutableTreeNode crearNodoEquipo(Equipo equipo, ArrayList<Equipo> equipos) {
        DefaultMutableTreeNode nodo = new DefaultMutableTreeNode(equipo);

        for (Equipo sub : equipos) {
            if (sub.getIdEquipoPrincipal() == equipo.getId()) {
                nodo.add(crearNodoEquipo(sub, equipos));
            }
        }

        return nodo;
    }
    private void Editar(){
        TreePath seleccion = arbolInventario.getSelectionPath();

        if(seleccion != null){
            DefaultMutableTreeNode nodo = (DefaultMutableTreeNode) seleccion.getLastPathComponent();
            if(nodo.getUserObject() instanceof Equipo){
                Equipo e = (Equipo) nodo.getUserObject();
                if(JOptionPane.showConfirmDialog(this,
                        "¿Desea editar el equipo "+e.getDescripcion()+" ?",
                        "Confirmar edición", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION){
                    JFrame frame = new JFrame("Editar equipo");
                    frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
                    frame.setLocationRelativeTo(null);
                    JPanel panelContenido = new RegistrarEquipo(e);
                    frame.add(panelContenido);
                    frame.setSize(1000, 600);
                    frame.setVisible(true);
                }
            }
        }
    }
    private void Eliminar(){
        //Para saber el elemento seleccionado
        TreePath seleccion = arbolInventario.getSelectionPath();

        if(seleccion != null){
            DefaultMutableTreeNode nodo = (DefaultMutableTreeNode) seleccion.getLastPathComponent();
            if(nodo.getUserObject() instanceof Equipo){
                Equipo e = (Equipo) nodo.getUserObject();
                if(JOptionPane.showConfirmDialog(this,
                        "¿Desea eliminar el equipo "+e.getDescripcion()+" ?",
                        "Confirmar eliminación", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION){
                    treeModel.removeNodeFromParent(nodo);
                    detalleArea.setText("");
                    controladorEquipo.EliminarEquipo(e.getId());
                }
            }
        }
    }

}
