/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Vista.Paneles;

import Controlador.ControladorOrdenesPreventivo;
import Modelo.Entidades.OrdenDeTrabajoPreventivo;

import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
/**
 *
 * @author Usuario
 */
public class OrdenesPreventivo extends JPanel
{
    private ControladorOrdenesPreventivo controlador;
    private JPanel panelContenido;
    private JPanel panelComboBox;
    private JPanel panelOrdenes;
    private JPanel panelDetallesOrden;
    private JPanel panelOpciones;    
    private JList <OrdenDeTrabajoPreventivo> listaUI;
    private DefaultListModel <OrdenDeTrabajoPreventivo> listaUIModelo;
    private ArrayList<OrdenDeTrabajoPreventivo> lista;
    private OrdenDeTrabajoPreventivo ordenSeleccionada;
    
    public OrdenesPreventivo()
    {
        this.inicializarComponentes();
    }
    private void inicializarComponentes()
    {
        setLayout(new BorderLayout());

        JComboBox<String> combo = new JComboBox<>();
        combo.addItem("Próximas");
        combo.addItem("Pendientes");
        combo.addItem("En curso");
        combo.addItem("Terminadas");
        combo.addItem("Canceladas");
        combo.addItem("Todas");

        combo.addActionListener(e -> {
            String filtro =  combo.getSelectedItem().toString();
            Filtrar(filtro);
        });
        
        
        panelComboBox = new JPanel();
        panelComboBox.setBackground(new Color(255, 255, 255));
        panelComboBox.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panelComboBox.setLayout(new BorderLayout());
        panelComboBox.add(combo, BorderLayout.WEST);
        add(panelComboBox, BorderLayout.NORTH);
        
        panelContenido = new JPanel();
        panelContenido.setLayout(new GridLayout(1, 2, 5, 5));
        panelContenido.setBackground(new Color(50, 255, 50));
        ArrayList<OrdenDeTrabajoPreventivo>  lista = new ArrayList<>();
        //lista = controlador.BuscarTodos();
        add(panelContenido, BorderLayout.CENTER);
        
        panelOpciones = new JPanel();
        panelOpciones.setLayout(new GridLayout(1, 2, 5, 5));
        panelOpciones.setBackground(new Color(50, 50, 255));
        add(panelOpciones, BorderLayout.CENTER);
    }
    
    private void Filtrar(String filtro){
        listaUIModelo.clear();
        lista = controlador.Filtrar(filtro);
        listaUIModelo.addAll(lista);
    }
}
