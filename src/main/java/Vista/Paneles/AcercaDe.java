package Vista.Paneles;

import javax.swing.*;
import java.awt.*;

public class AcercaDe extends JPanel {
    public AcercaDe(){
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        JLabel lblAcercaDe = new JLabel("Acerca de");
        lblAcercaDe.setFont(new Font("Arial", Font.BOLD, 20));
        lblAcercaDe.setAlignmentX(0.5f);
        add(lblAcercaDe);
        JLabel label = new JLabel("Programa 2 Mantenimiento de equipos");
        label.setFont(new Font("Arial", Font.PLAIN, 15));
        label.setAlignmentX(0.5f);
        add(label);
        label = new JLabel("Santiago Obando Morales & Yared Guido Fallas ");
        label.setFont(new Font("Arial", Font.PLAIN, 15));
        label.setAlignmentX(0.5f);
        add(label);
        label = new JLabel("II SEM 2025 - Programación Orientada a Objetos");
        label.setFont(new Font("Arial", Font.PLAIN, 15));
        label.setAlignmentX(0.5f);
        add(label);
    }
}
