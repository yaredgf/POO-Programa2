package Vista.Paneles;

import Controlador.ControladorEquipo;
import Modelo.Entidades.Equipo;
import Modelo.Entidades.EstadoEquipo;
import Modelo.Entidades.TipoEquipo;
import Modelo.Metodos.TipoEquipoM;

import javax.swing.*;
import java.awt.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class RegistrarEquipo extends JPanel {
    ControladorEquipo controladorEquipo;
    private JTextField txtDescripcion;
    private JComboBox<String> comboTipo;
    private JTextField txtUbicacion;
    private JTextField txtFabricante;
    private JTextField txtSerie;
    private JTextField txtFechaAdquisicion;
    private JTextField txtFechaServicio;
    private JTextField txtVidaUtil;
    private JComboBox<EstadoEquipo> comboEstado;
    private JTextField txtCostoInicial;
    private JTextField txtEspecificaciones;
    private JTextField txtGarantia;

    public RegistrarEquipo() {
         controladorEquipo = new ControladorEquipo();

        setLayout(new BorderLayout());
        JLabel lblTitulo = new JLabel("Registrar Equipo", JLabel.CENTER);
        add(lblTitulo, BorderLayout.NORTH);

        JPanel formulario = new JPanel(new GridLayout(0, 2, 10, 10));
        formulario.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        txtDescripcion = new JTextField();
        //Cargar el combobox de tipos de equipos
        comboTipo = new JComboBox<>();
        TipoEquipoM tipoEquipoM = new TipoEquipoM();
        for(TipoEquipo t : tipoEquipoM.Buscar()){
            comboTipo.addItem(t.getDescripcion());
        }

        txtUbicacion = new JTextField();
        txtFabricante = new JTextField();
        txtSerie = new JTextField();
        txtFechaAdquisicion = new JTextField();
        txtFechaServicio = new JTextField();
        txtVidaUtil = new JTextField();
        comboEstado = new JComboBox<>(EstadoEquipo.values());
        txtCostoInicial = new JTextField();
        txtEspecificaciones = new JTextField();
        txtGarantia = new JTextField();

        formulario.add(new JLabel("Descripción:")); formulario.add(txtDescripcion);
        formulario.add(new JLabel("Tipo de equipo:")); formulario.add(comboTipo);
        formulario.add(new JLabel("Ubicación física:")); formulario.add(txtUbicacion);
        formulario.add(new JLabel("Fabricante:")); formulario.add(txtFabricante);
        formulario.add(new JLabel("Serie:")); formulario.add(txtSerie);
        formulario.add(new JLabel("Fecha de adquisición (dd/MM/yyyy):")); formulario.add(txtFechaAdquisicion);
        formulario.add(new JLabel("Fecha puesta en servicio (dd/MM/yyyy):")); formulario.add(txtFechaServicio);
        formulario.add(new JLabel("Meses de vida útil:")); formulario.add(txtVidaUtil);
        formulario.add(new JLabel("Estado del equipo:")); formulario.add(comboEstado);
        formulario.add(new JLabel("Costo inicial:")); formulario.add(txtCostoInicial);
        formulario.add(new JLabel("Especificaciones técnicas:")); formulario.add(txtEspecificaciones);
        formulario.add(new JLabel("Información de garantía:")); formulario.add(txtGarantia);

        add(formulario, BorderLayout.CENTER);

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        JButton btnGuardar = new JButton("Guardar");
        JButton btnLimpiar = new JButton("Limpiar");
        panelBotones.add(btnGuardar);
        panelBotones.add(btnLimpiar);
        add(panelBotones, BorderLayout.SOUTH);

        btnGuardar.addActionListener(e -> guardarEquipo());
        btnLimpiar.addActionListener(e -> limpiarFormulario());




    }
    private void guardarEquipo() {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            Date fechaAdq = sdf.parse(txtFechaAdquisicion.getText());
            Date fechaServ = sdf.parse(txtFechaServicio.getText());

            Equipo equipo = new Equipo();
            equipo.setDescripcion(txtDescripcion.getText());
            equipo.setTipoEquipo(comboTipo.getSelectedIndex()); // índice como tipo
            equipo.setUbicacionFisica(txtUbicacion.getText());
            equipo.setFabricante(txtFabricante.getText());
            equipo.setSerie(txtSerie.getText());
            equipo.setFechaAdquisicion(fechaAdq);
            equipo.setFechaPuestaEnServicio(fechaServ);
            equipo.setMesesVidaUtil(Integer.parseInt(txtVidaUtil.getText()));
            equipo.setEstadoEquipo((EstadoEquipo) comboEstado.getSelectedItem());
            equipo.setCostoInicial(Float.parseFloat(txtCostoInicial.getText()));
            equipo.setEspecificacionesTecnicas(txtEspecificaciones.getText());
            equipo.setInformacionGarantia(txtGarantia.getText());
            equipo.setFasesMantenimiento(new ArrayList<>()); // inicial vacío

            JOptionPane.showMessageDialog(this,
                    "Equipo registrado:\n" + equipo.getDescripcion(),
                    "Registro exitoso", JOptionPane.INFORMATION_MESSAGE);

            controladorEquipo.RegistrarEquipo(equipo);

        } catch (ParseException ex) {
            JOptionPane.showMessageDialog(this, "Error en formato de fecha", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Error en campos numéricos", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void limpiarFormulario() {
        txtDescripcion.setText("");
        comboTipo.setSelectedIndex(0);
        txtUbicacion.setText("");
        txtFabricante.setText("");
        txtSerie.setText("");
        txtFechaAdquisicion.setText("");
        txtFechaServicio.setText("");
        txtVidaUtil.setText("");
        comboEstado.setSelectedIndex(0);
        txtCostoInicial.setText("");
        txtEspecificaciones.setText("");
        txtGarantia.setText("");
    }
}
