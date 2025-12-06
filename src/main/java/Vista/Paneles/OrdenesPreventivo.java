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
        lista = new ArrayList<OrdenDeTrabajoPreventivo>();
        OrdenDeTrabajoPreventivo otp = new OrdenDeTrabajoPreventivo();
        OrdenDeTrabajoPreventivo otp1 = new OrdenDeTrabajoPreventivo();
        otp.setId(1);
        otp.setIdEquipo(1);
        lista.add(otp);
        otp1.setId(2);
        otp1.setIdEquipo(3);
        lista.add(otp1);
        
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
        btnIniciarOrden = new JButton("Iniciar Orden");
        btnActualizar = new JButton("Actualizar");
        btnCerrarOrden = new JButton("Cerrar Orden");
        btnCancelarOrden = new JButton("Cancelar Orden");
        
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
        JOptionPane.showMessageDialog(null,"Oopsie", Integer.toString(orden.getId()), JOptionPane.ERROR_MESSAGE);
    }
    
    private void NuevaOrden()
    {
        
    }
    private void IniciarOrden()
    {
        
    }
    private void Actualizar()
    {
        
    }
    private void CerrarOrden()
    {
        
    }
    private void CancelarOrden()
    {
        
    }

    
    



    
}
