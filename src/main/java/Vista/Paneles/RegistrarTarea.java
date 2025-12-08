package Vista.Paneles;

import Controlador.ControladorTarea;
import Modelo.Entidades.Tarea;

import javax.swing.*;
import java.awt.*;

public class RegistrarTarea extends JPanel {
    private Tarea tarea;
    private int tipoOperacion = 0;
    private ControladorTarea controladorTarea;
    private JTextField txtDescripcion;

    public RegistrarTarea() {
        this.tipoOperacion = 0;
        this.inicializarComponentes();
    }

    public RegistrarTarea(Tarea tarea) {
        this.tipoOperacion = 1;
        this.inicializarComponentes();
        this.tarea = tarea;
        this.txtDescripcion.setText(tarea.getDescripcion());
    }

    private void inicializarComponentes() {
        this.controladorTarea = new ControladorTarea();
        this.tarea = new Tarea();
        this.setLayout(new BorderLayout());
        JLabel lblTitulo = new JLabel("Registrar Tarea", 0);
        lblTitulo.setFont(new Font("Arial", 1, 20));
        lblTitulo.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));
        this.add(lblTitulo, "North");
        JPanel formulario = new JPanel(new GridLayout(0, 2, 10, 10));
        formulario.setBorder(BorderFactory.createEmptyBorder(200, 150, 200, 150));
        this.txtDescripcion = new JTextField();
        formulario.add(new JLabel("Descripción:"));
        formulario.add(this.txtDescripcion);
        this.add(formulario, "Center");
        JPanel panelBotones = new JPanel(new FlowLayout(1, 20, 10));
        JButton btnGuardar = new JButton("Guardar");
        JButton btnLimpiar = new JButton("Limpiar");
        panelBotones.add(btnGuardar);
        panelBotones.add(btnLimpiar);
        this.add(panelBotones, "South");
        btnGuardar.addActionListener((e) -> {
            if (this.tipoOperacion == 1) {
                this.editarTarea();
            } else {
                this.guardarTarea();
                if (JOptionPane.showConfirmDialog(this, "¿Desea agregar otra tarea?", "Confirmación", 0) == 0) {
                    this.limpiarFormulario();
                } else {
                    Window window = SwingUtilities.getWindowAncestor(this);
                    if (window instanceof JFrame) {
                        ((JFrame)window).dispose();
                    }
                }
            }

        });
        btnLimpiar.addActionListener((e) -> this.limpiarFormulario());
    }

    private void guardarTarea() {
        if (!this.txtDescripcion.getText().isEmpty()) {
            this.tarea.setDescripcion(this.txtDescripcion.getText());
            if (this.controladorTarea.Registrar(this.tarea)) {
                JOptionPane.showMessageDialog(this, "Tarea registrada:\n" + this.tarea.getDescripcion(), "Registro exitoso", 1);
            }
        }

    }

    private void editarTarea() {
        if (!this.txtDescripcion.getText().isEmpty()) {
            this.tarea.setDescripcion(this.txtDescripcion.getText());
            if (this.controladorTarea.Editar(this.tarea)) {
                JOptionPane.showMessageDialog(this, "Tarea editada:\n" + this.tarea.getDescripcion(), "Edición exitosa", 1);
            }
        }

    }

    private void limpiarFormulario() {
        this.txtDescripcion.setText("");
    }
}
