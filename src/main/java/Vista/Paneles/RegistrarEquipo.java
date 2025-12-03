//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package Vista.Paneles;

import Controlador.ControladorEquipo;
import Modelo.Entidades.Equipo;
import Modelo.Entidades.EstadoEquipo;
import Modelo.Entidades.TipoEquipo;
import Modelo.Metodos.TipoEquipoM;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class RegistrarEquipo extends JPanel {
    private Equipo equipo;
    private int tipoOperacion = 0;
    private ControladorEquipo controladorEquipo;
    private JTextField txtEquipoPrincipal;
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
        this.tipoOperacion = 0;
        this.inicializarComponentes();
    }

    public RegistrarEquipo(Equipo equipo) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        this.tipoOperacion = 1;
        this.inicializarComponentes();
        this.equipo = equipo;
        this.txtEquipoPrincipal.setText(String.valueOf(equipo.getIdEquipoPrincipal()));
        this.txtDescripcion.setText(equipo.getDescripcion());
        this.comboTipo.setSelectedIndex(equipo.getTipoEquipo());
        this.txtUbicacion.setText(equipo.getUbicacionFisica());
        this.txtFabricante.setText(equipo.getFabricante());
        this.txtSerie.setText(equipo.getSerie());
        this.txtFechaAdquisicion.setText(sdf.format(equipo.getFechaAdquisicion()));
        this.txtFechaServicio.setText(sdf.format(equipo.getFechaPuestaEnServicio()));
        this.txtVidaUtil.setText(String.valueOf(equipo.getMesesVidaUtil()));
        this.comboEstado.setSelectedItem(equipo.getEstadoEquipo());
        this.txtCostoInicial.setText(String.valueOf(equipo.getCostoInicial()));
    }

    private void inicializarComponentes() {
        this.controladorEquipo = new ControladorEquipo();
        this.equipo = new Equipo();
        this.setLayout(new BorderLayout());
        JLabel lblTitulo = new JLabel("Registrar Equipo", 0);
        lblTitulo.setFont(new Font("Arial", 1, 20));
        lblTitulo.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));
        this.add(lblTitulo, "North");
        JPanel formulario = new JPanel(new GridLayout(0, 2, 10, 10));
        formulario.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        this.txtDescripcion = new JTextField();
        this.comboTipo = new JComboBox();
        TipoEquipoM tipoEquipoM = new TipoEquipoM();

        for(TipoEquipo t : tipoEquipoM.Buscar()) {
            this.comboTipo.addItem(t.getDescripcion());
        }

        this.txtEquipoPrincipal = new JTextField();
        this.txtUbicacion = new JTextField();
        this.txtFabricante = new JTextField();
        this.txtSerie = new JTextField();
        this.txtFechaAdquisicion = new JTextField();
        this.txtFechaServicio = new JTextField();
        this.txtVidaUtil = new JTextField();
        this.comboEstado = new JComboBox(EstadoEquipo.values());
        this.txtCostoInicial = new JTextField();
        this.txtEspecificaciones = new JTextField();
        this.txtGarantia = new JTextField();
        this.txtEquipoPrincipal.setText("0");
        formulario.add(new JLabel("Equipo principal(0 si no tiene):"));
        formulario.add(this.txtEquipoPrincipal);
        formulario.add(new JLabel("Descripción:"));
        formulario.add(this.txtDescripcion);
        formulario.add(new JLabel("Tipo de equipo:"));
        formulario.add(this.comboTipo);
        formulario.add(new JLabel("Ubicación física:"));
        formulario.add(this.txtUbicacion);
        formulario.add(new JLabel("Fabricante:"));
        formulario.add(this.txtFabricante);
        formulario.add(new JLabel("Serie:"));
        formulario.add(this.txtSerie);
        formulario.add(new JLabel("Fecha de adquisición (dd/MM/yyyy):"));
        formulario.add(this.txtFechaAdquisicion);
        formulario.add(new JLabel("Fecha puesta en servicio (dd/MM/yyyy):"));
        formulario.add(this.txtFechaServicio);
        formulario.add(new JLabel("Meses de vida útil:"));
        formulario.add(this.txtVidaUtil);
        formulario.add(new JLabel("Estado del equipo:"));
        formulario.add(this.comboEstado);
        formulario.add(new JLabel("Costo inicial:"));
        formulario.add(this.txtCostoInicial);
        formulario.add(new JLabel("Especificaciones técnicas:"));
        formulario.add(this.txtEspecificaciones);
        formulario.add(new JLabel("Información de garantía:"));
        formulario.add(this.txtGarantia);
        this.add(formulario, "Center");
        JPanel panelBotones = new JPanel(new FlowLayout(1, 20, 10));
        JButton btnGuardar = new JButton("Guardar");
        JButton btnLimpiar = new JButton("Limpiar");
        panelBotones.add(btnGuardar);
        panelBotones.add(btnLimpiar);
        this.add(panelBotones, "South");
        btnGuardar.addActionListener((e) -> {
            if (this.tipoOperacion == 1) {
                this.editarEquipo();
            } else {
                this.guardarEquipo();
            }

        });
        btnLimpiar.addActionListener((e) -> this.limpiarFormulario());
    }

    private void guardarEquipo() {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            Date fechaAdq = sdf.parse(this.txtFechaAdquisicion.getText());
            Date fechaServ;
            if (this.txtFechaServicio.getText().isEmpty()) {
                fechaServ = null;
            } else {
                fechaServ = sdf.parse(this.txtFechaServicio.getText());
            }

            this.equipo.setIdEquipoPrincipal(this.txtEquipoPrincipal.getText().isEmpty() ? 0 : Integer.parseInt(this.txtEquipoPrincipal.getText()));
            this.equipo.setDescripcion(this.txtDescripcion.getText());
            this.equipo.setTipoEquipo(this.comboTipo.getSelectedIndex());
            this.equipo.setUbicacionFisica(this.txtUbicacion.getText());
            this.equipo.setFabricante(this.txtFabricante.getText());
            this.equipo.setSerie(this.txtSerie.getText());
            this.equipo.setFechaAdquisicion(fechaAdq);
            this.equipo.setFechaPuestaEnServicio(fechaServ);
            this.equipo.setMesesVidaUtil(Integer.parseInt(this.txtVidaUtil.getText()));
            this.equipo.setEstadoEquipo((EstadoEquipo)this.comboEstado.getSelectedItem());
            this.equipo.setCostoInicial(Float.parseFloat(this.txtCostoInicial.getText()));
            this.equipo.setEspecificacionesTecnicas(this.txtEspecificaciones.getText());
            this.equipo.setInformacionGarantia(this.txtGarantia.getText());
            this.equipo.setFasesMantenimiento(new ArrayList());
            if (this.controladorEquipo.Registrar(this.equipo)) {
                JOptionPane.showMessageDialog(this, "Equipo registrado:\n" + this.equipo.getDescripcion(), "Registro exitoso", 1);
            }
        } catch (ParseException var4) {
            JOptionPane.showMessageDialog(this, "Error en formato de fecha", "Error", 0);
        } catch (NumberFormatException var5) {
            JOptionPane.showMessageDialog(this, "Error en campos numéricos. Por favor introduzca números donde corresponde.", "Error", 0);
        }

    }

    private void editarEquipo() {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            Date fechaAdq = sdf.parse(this.txtFechaAdquisicion.getText());
            Date fechaServ;
            if (this.txtFechaServicio.getText().isEmpty()) {
                fechaServ = null;
            } else {
                fechaServ = sdf.parse(this.txtFechaServicio.getText());
            }

            this.equipo.setIdEquipoPrincipal(this.txtEquipoPrincipal.getText().isEmpty() ? 0 : Integer.parseInt(this.txtEquipoPrincipal.getText()));
            this.equipo.setDescripcion(this.txtDescripcion.getText());
            this.equipo.setTipoEquipo(this.comboTipo.getSelectedIndex());
            this.equipo.setUbicacionFisica(this.txtUbicacion.getText());
            this.equipo.setFabricante(this.txtFabricante.getText());
            this.equipo.setSerie(this.txtSerie.getText());
            this.equipo.setFechaAdquisicion(fechaAdq);
            this.equipo.setFechaPuestaEnServicio(fechaServ);
            this.equipo.setMesesVidaUtil(Integer.parseInt(this.txtVidaUtil.getText()));
            this.equipo.setEstadoEquipo((EstadoEquipo)this.comboEstado.getSelectedItem());
            this.equipo.setCostoInicial(Float.parseFloat(this.txtCostoInicial.getText()));
            this.equipo.setEspecificacionesTecnicas(this.txtEspecificaciones.getText());
            this.equipo.setInformacionGarantia(this.txtGarantia.getText());
            this.equipo.setFasesMantenimiento(new ArrayList());
            if (this.controladorEquipo.Editar(this.equipo)) {
                JOptionPane.showMessageDialog(this, "Equipo editado:\n" + this.equipo.getDescripcion(), "Edición exitosa", 1);
            }
        } catch (ParseException var4) {
            JOptionPane.showMessageDialog(this, "Error en formato de fecha", "Error", 0);
        } catch (NumberFormatException var5) {
            JOptionPane.showMessageDialog(this, "Error en campos numéricos. Por favor introduzca números donde corresponde.", "Error", 0);
        }

    }

    private void limpiarFormulario() {
        this.txtEquipoPrincipal.setText("");
        this.txtDescripcion.setText("");
        this.comboTipo.setSelectedIndex(0);
        this.txtUbicacion.setText("");
        this.txtFabricante.setText("");
        this.txtSerie.setText("");
        this.txtFechaAdquisicion.setText("");
        this.txtFechaServicio.setText("");
        this.txtVidaUtil.setText("");
        this.comboEstado.setSelectedIndex(0);
        this.txtCostoInicial.setText("");
        this.txtEspecificaciones.setText("");
        this.txtGarantia.setText("");
    }
}
