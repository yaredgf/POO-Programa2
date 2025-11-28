package Controlador;

import javax.swing.*;
import java.awt.*;

public class ControladorMenuP {
    private JPanel panelContenido;
    public ControladorMenuP(JPanel panelContenido){
        this.panelContenido = panelContenido;
    }
    ///
    /// Cambia el contenido del panel principal segun el item seleccionado en el menu.
    /// @param panel Panel a mostrar.
    ///
    public void cargarPanel(JPanel panel){
        panelContenido.removeAll();
        panelContenido.add(panel, BorderLayout.CENTER);
        panelContenido.revalidate();
        panelContenido.repaint();
    }
}
