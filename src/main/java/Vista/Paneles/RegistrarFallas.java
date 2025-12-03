//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package Vista.Paneles;

import Controlador.ControladorFalla;
import Modelo.Entidades.Falla;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Window;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class RegistrarFallas extends JPanel {
    private Falla falla;
    private int tipoOperacion = 0;
    private ControladorFalla controladorFalla;
    private JTextField txtDescripcion;

    public RegistrarFallas() {
        this.tipoOperacion = 0;
        this.inicializarComponentes();
    }

    public RegistrarFallas(Falla falla) {
        this.tipoOperacion = 1;
        this.inicializarComponentes();
        this.falla = falla;
        this.txtDescripcion.setText(falla.getDescripcion());
    }

    private void inicializarComponentes() {
        this.controladorFalla = new ControladorFalla();
        this.falla = new Falla();
        this.setLayout(new BorderLayout());
        JLabel lblTitulo = new JLabel("Registrar Falla", 0);
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
                this.editarFalla();
            } else {
                this.guardarFalla();
                if (JOptionPane.showConfirmDialog(this, "¿Desea agregar otra falla?", "Confirmación", 0) == 0) {
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

    private void guardarFalla() {
        if (!this.txtDescripcion.getText().isEmpty()) {
            this.falla.setDescripcion(this.txtDescripcion.getText());
            if (this.controladorFalla.Registrar(this.falla)) {
                JOptionPane.showMessageDialog(this, "Falla registrada:\n" + this.falla.getDescripcion(), "Registro exitoso", 1);
            }
        }

    }

    private void editarFalla() {
        if (!this.txtDescripcion.getText().isEmpty()) {
            this.falla.setDescripcion(this.txtDescripcion.getText());
            if (this.controladorFalla.Editar(this.falla)) {
                JOptionPane.showMessageDialog(this, "Equipo editado:\n" + this.falla.getDescripcion(), "Edición exitosa", 1);
            }
        }

    }

    private void limpiarFormulario() {
        this.txtDescripcion.setText("");
    }
}
