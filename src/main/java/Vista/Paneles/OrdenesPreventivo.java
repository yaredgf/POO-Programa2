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
import javax.swing.BoxLayout;
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.WindowConstants;
/**
 *
 * @author Usuario
 */
public class OrdenesPreventivo extends JPanel
{
    private ControladorOrdenesPreventivo controlador;
    private JPanel panelTodo;    
    private JPanel panelContenido;

    private JPanel panelComboBox;
    private JPanel panelOrdenes;
    private JPanel panelDetallesOrden;
    private JPanel panelOpciones;    
    private JList <OrdenDeTrabajoPreventivo> listaUI;
    private DefaultListModel <OrdenDeTrabajoPreventivo> listaUIModelo;
    private ArrayList<OrdenDeTrabajoPreventivo> lista;
    private OrdenDeTrabajoPreventivo ordenSeleccionada;
    
    private JButton btnNuevaOrden;
    private JButton btnIniciarOrden;
    private JButton btnActualizar;
    private JButton btnCerrarOrden;
    private JButton btnCancelarOrden;
    
    public OrdenesPreventivo()
    {
        controlador = new ControladorOrdenesPreventivo();
        this.inicializarComponentes();
    }
    private void inicializarComponentes()
    {
        //p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        panelTodo = new JPanel();
        panelTodo.setLayout(new BoxLayout(panelTodo, BoxLayout.Y_AXIS));

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
        panelComboBox.setBackground(new Color(240, 240, 240));
        panelComboBox.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panelComboBox.setLayout(new BorderLayout());
        panelComboBox.add(combo, BorderLayout.WEST);
        panelTodo.add(panelComboBox);
                
        panelContenido = new JPanel();
        panelContenido.setLayout(new GridLayout(1,2,0,0));
        panelContenido.setBackground(new Color(50, 255, 50));
        
        // Los dos paneles del centro
        
        panelOrdenes = new JPanel();
        panelDetallesOrden = new JPanel();
        panelOrdenes.setLayout(new BoxLayout(panelOrdenes, BoxLayout.Y_AXIS));
        
        listaUIModelo = new DefaultListModel<>();
        
        if (lista == null)
            lista = new ArrayList<OrdenDeTrabajoPreventivo>();
        this.listaUIModelo.addAll(lista);
        listaUI = new JList<>(this.listaUIModelo);
        listaUI.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listaUI.addListSelectionListener(e ->{
            MostrarDetalles(listaUIModelo.get(listaUI.getSelectedIndex()));
        });
        panelOrdenes.add(new JScrollPane(listaUI), BorderLayout.CENTER);
        
        
        panelContenido.add(panelOrdenes);
        panelContenido.add(panelDetallesOrden);
        
        panelTodo.add(panelContenido);
        
        
        
        
        
        
        // Botones
        
        panelOpciones = new JPanel();
        panelOpciones.setLayout(new GridLayout(1, 5, 20, 5));
        panelOpciones.setBackground(new Color(50, 50, 255));
        
        btnNuevaOrden = new JButton("Nueva Orden");
        btnNuevaOrden.addActionListener(e -> {NuevaOrden();});
        btnIniciarOrden = new JButton("Iniciar Orden");
        btnIniciarOrden.addActionListener(e -> {IniciarOrden();});
        btnActualizar = new JButton("Actualizar");
        btnActualizar.addActionListener(e -> {Actualizar();});
        btnCerrarOrden = new JButton("Cerrar Orden");
        btnCerrarOrden.addActionListener(e -> {CerrarOrden();});
        btnCancelarOrden = new JButton("Cancelar Orden");
        btnCancelarOrden.addActionListener(e -> {CancelarOrden();});
        
        panelOpciones.add(btnNuevaOrden);
        panelOpciones.add(btnIniciarOrden);
        panelOpciones.add(btnActualizar);
        panelOpciones.add(btnCerrarOrden);
        panelOpciones.add(btnCancelarOrden);
        
        panelTodo.add(panelOpciones);
  
        
        
        
        
        
        
        
        
        
        add(panelTodo);
    }
    
    private void Filtrar(String filtro){
        listaUIModelo.clear();
        lista = controlador.Filtrar(filtro);
        listaUIModelo.addAll(lista);
    }
    
    private void MostrarDetalles(OrdenDeTrabajoPreventivo orden)
    {
        ordenSeleccionada = orden;
    }
    
    private void NuevaOrden()
    {
        
        JFrame frame = new JFrame("Agregar Orden Correctiva");
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        JPanel panelContenido = new AgregarOrdenPreventiva();
        frame.add(panelContenido);
        frame.setSize(1000, 600);
        frame.setVisible(true);
    }
    private void IniciarOrden()
    {
        if (controlador.Iniciar(ordenSeleccionada.getId()))
            JOptionPane.showMessageDialog(null,"Esta orden fue iniciada con éxito", "Operación exitosa", JOptionPane.INFORMATION_MESSAGE);
    }
    private void Actualizar()
    {
        JFrame frame = new JFrame("Agregar Orden Correctiva");
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        JPanel panelContenido = new EditarOrdenPreventiva(ordenSeleccionada);
        frame.add(panelContenido);
        frame.setSize(1000, 600);
        frame.setVisible(true);
    }
    private void CerrarOrden()
    {
        
        if (controlador.Cerrar(ordenSeleccionada))
            JOptionPane.showMessageDialog(null,"Esta orden fue iniciada con éxito", "Operación exitosa", JOptionPane.INFORMATION_MESSAGE);
    }
    private void CancelarOrden()
    {
        if(JOptionPane.showConfirmDialog(this, "¿Seguro que desea cancelar la orden de trabajo correctivo #"+ordenSeleccionada.getId()+"?", "Confirmación", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION){
            String motivo = JOptionPane.showInputDialog(this, "Ingrese un motivo de cancelación: ", "Cancelar orden", JOptionPane.QUESTION_MESSAGE);
            if (controlador.Cancelar(ordenSeleccionada.getId(),motivo))
                JOptionPane.showMessageDialog(this, "Orden cancelada correctamente");
        }
        else{
            JOptionPane.showMessageDialog(this, "Operación interrumpida.");
        }
    }

    
    



    
}
