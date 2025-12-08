package Vista.Paneles;

import Controlador.ControladorEquipo;
import Modelo.Entidades.Equipo;
import Modelo.Entidades.Fase;
import Modelo.Entidades.Frecuencia;
import Modelo.Entidades.Tarea;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class ListaFases extends JPanel {
    ControladorEquipo controlador;
    Equipo equipo;
    ArrayList<Fase> listaFases;

    JSplitPane splitPane;

    DefaultListModel<Fase> modeloListaFases;
    JList<Fase> listaFasesJList;
    Fase faseSeleccionada;
    int faseSeleccionadaIndex = -1;
    JPanel formulario;
    JComboBox<Frecuencia> txtTipoFreq;
    JTextField txtCiclos;
    JTextField txtPartes;
    JTextField txtHerramientas;
    JTextField txtPersonal;
    JTextField txtHorasEstimadas;
    JButton btnMostrarTareas;

    //Modal tareas
    JComboBox<Tarea> listaTareasDisponibles;
    JList<Tarea> listaTareas;
    DefaultListModel<Tarea> modeloListaTareas = new DefaultListModel<>();
    Tarea tareaSeleccionada;
    JButton btnAgregarTarea;
    JButton btnEliminarTarea;

    JPanel panelBotones;
    JButton btnAgregar;
    JButton btnGuardar;
    JButton btnEliminar;
    JButton btnCancelar;

    public ListaFases(Equipo e){
        controlador = new ControladorEquipo();
        setLayout(new BorderLayout());

        this.equipo = e;
        this.listaFases = equipo.getFasesMantenimiento();

        JLabel titulo = new JLabel();
        titulo.setFont(new Font("Arial", Font.BOLD, 20));
        titulo.setText("Fases del equipo "+equipo.getDescripcion());
        add(titulo, BorderLayout.NORTH);

        JPanel panelLista= new JPanel();
        panelLista.setLayout(new BorderLayout());
        modeloListaFases = new DefaultListModel<>();
        modeloListaFases.addAll(listaFases);
        listaFasesJList = new JList<>(modeloListaFases);
        listaFasesJList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listaFasesJList.setCellRenderer(new DefaultListCellRenderer(){
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                setText("Fase "+ (index+1));
                setOpaque(true);
                if(isSelected) setBackground(new Color(153, 204, 255));
                else setBackground(Color.WHITE);
                return this;
            }
        });

        panelLista.add(new JScrollPane(listaFasesJList), BorderLayout.CENTER);

        formulario = new JPanel();
        formulario.setLayout(new BoxLayout(formulario, BoxLayout.Y_AXIS));

        //Frecuencia
        JPanel bloque = new JPanel();
        bloque.setLayout(new GridLayout(2, 1, 0, 5));
        JLabel lbl = new JLabel();
        lbl.setFont(new Font("Arial", Font.BOLD, 15));
        lbl.setText("Frecuencia");
        bloque.add(lbl);
        txtTipoFreq = new JComboBox<>(Frecuencia.values());
        bloque.add(txtTipoFreq);
        formulario.add(bloque);

        bloque = new JPanel();
        bloque.setLayout(new GridLayout(2, 1, 0, 5));
        lbl = new JLabel();
        lbl.setFont(new Font("Arial", Font.BOLD, 15));
        lbl.setText("Cantidad de ciclos");
        bloque.add(lbl);
        txtCiclos = new JTextField();
        bloque.add(txtCiclos);
        formulario.add(bloque);

        bloque = new JPanel();
        bloque.setLayout(new GridLayout(2, 1, 0, 5));
        lbl = new JLabel();
        lbl.setFont(new Font("Arial", Font.BOLD, 15));
        lbl.setText("Partes");
        bloque.add(lbl);
        txtPartes = new JTextField();
        bloque.add(txtPartes);
        formulario.add(bloque);

        bloque = new JPanel();
        bloque.setLayout(new GridLayout(2, 1, 0, 5));
        lbl = new JLabel();
        lbl.setFont(new Font("Arial", Font.BOLD, 15));
        lbl.setText("Herramientas y equipo para mantenimiento");
        bloque.add(lbl);
        txtHerramientas = new JTextField();
        bloque.add(txtHerramientas);
        formulario.add(bloque);

        bloque = new JPanel();
        bloque.setLayout(new GridLayout(2, 1, 0, 5));
        lbl = new JLabel();
        lbl.setFont(new Font("Arial", Font.BOLD, 15));
        lbl.setText("Personal");
        bloque.add(lbl);
        txtPersonal = new JTextField();
        bloque.add(txtPersonal);
        formulario.add(bloque);

        bloque = new JPanel();
        bloque.setLayout(new GridLayout(2, 1, 0, 5));
        lbl = new JLabel();
        lbl.setFont(new Font("Arial", Font.BOLD, 15));
        lbl.setText("Horas estimadas");
        bloque.add(lbl);
        txtHorasEstimadas = new JTextField();
        bloque.add(txtHorasEstimadas);
        formulario.add(bloque);
        formulario.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        bloque = new JPanel();
        bloque.setLayout(new BorderLayout());
        bloque.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        btnMostrarTareas = new JButton("Mostrar tareas");
        btnMostrarTareas.addActionListener(ev->{
            MostrarTareas();
        });
        bloque.add(btnMostrarTareas, BorderLayout.CENTER);

        formulario.add(bloque);

        splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, panelLista, formulario);
        splitPane.setResizeWeight(0.15);
        add(splitPane, BorderLayout.CENTER);

        panelBotones = new JPanel();
        panelBotones.setLayout(new BoxLayout(panelBotones, BoxLayout.X_AXIS));
        btnAgregar = new JButton("Agregar fase");
        btnAgregar.addActionListener(ev->{AgregarFase();});
        btnEliminar = new JButton("Eliminar fase");
        btnEliminar.addActionListener(ev->{
            int index = listaFasesJList.getSelectedIndex();
            EliminarFase(index);
        });
        btnGuardar = new JButton("Guardar");
        btnGuardar.addActionListener(ev->{Guardar();});
        btnCancelar = new JButton("Cancelar");
        btnCancelar.addActionListener(ev->{Cancelar();});

        panelBotones.add(btnAgregar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnGuardar);
        panelBotones.add(btnCancelar);

        add(panelBotones, BorderLayout.SOUTH);


        listaFasesJList.addListSelectionListener(ev->{
            if (ev.getValueIsAdjusting()) return;

            int nuevoIndex = listaFasesJList.getSelectedIndex();
            if (nuevoIndex == -1) return;


            if (faseSeleccionada != null && faseSeleccionadaIndex >= 0) {
                try {

                    faseSeleccionada.setFrecuencia((Frecuencia) txtTipoFreq.getSelectedItem());

                    String ciclosTxt = txtCiclos.getText().trim();
                    int ciclos = ciclosTxt.isEmpty() ? 0 : Integer.parseInt(ciclosTxt);
                    faseSeleccionada.setCantidadCiclos(ciclos);

                    faseSeleccionada.setPartes(txtPartes.getText());
                    faseSeleccionada.setEquipoParaMantenimiento(txtHerramientas.getText());
                    faseSeleccionada.setPersonalEncargado(txtPersonal.getText());

                    String horasTxt = txtHorasEstimadas.getText().trim();
                    float horas = horasTxt.isEmpty() ? 0.0f : Float.parseFloat(horasTxt);
                    faseSeleccionada.setHorasEstimadas(horas);


                    if (modeloListaFases != null && faseSeleccionadaIndex >= 0 && faseSeleccionadaIndex < modeloListaFases.getSize()) {
                        modeloListaFases.set(faseSeleccionadaIndex, faseSeleccionada);
                    }
                } catch (NumberFormatException ex) {

                    JOptionPane.showMessageDialog(this, "Formato numérico inválido en ciclos/horas.", "Error", JOptionPane.ERROR_MESSAGE);

                }
            }


            faseSeleccionadaIndex = nuevoIndex;
            faseSeleccionada = modeloListaFases.get(faseSeleccionadaIndex);

            // proteger contra nulls
            if (faseSeleccionada != null) {
                txtTipoFreq.setSelectedItem(faseSeleccionada.getFrecuencia());
                txtCiclos.setText(String.valueOf(faseSeleccionada.getCantidadCiclos()));
                txtPartes.setText(faseSeleccionada.getPartes() != null ? faseSeleccionada.getPartes() : "");
                txtHerramientas.setText(faseSeleccionada.getEquipoParaMantenimiento() != null ? faseSeleccionada.getEquipoParaMantenimiento() : "");
                txtPersonal.setText(faseSeleccionada.getPersonalEncargado() != null ? faseSeleccionada.getPersonalEncargado() : "");
                txtHorasEstimadas.setText(String.valueOf(faseSeleccionada.getHorasEstimadas()));
            } else {
                // limpiar campos si algo raro pasó
                txtTipoFreq.setSelectedIndex(-1);
                txtCiclos.setText("");
                txtPartes.setText("");
                txtHerramientas.setText("");
                txtPersonal.setText("");
                txtHorasEstimadas.setText("");
            }
        });
    }
    private void MostrarTareas() {
        if (faseSeleccionada == null) {
            JOptionPane.showMessageDialog(null, "Seleccione una fase para mostrar las tareas disponibles.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Modal
        JFrame modal = new JFrame("Tareas disponibles");
        JPanel contenido = new JPanel(new BorderLayout());

        // Datos iniciales
        ArrayList<Tarea> todas = new ArrayList<>(controlador.BuscarTodasTareas()); // todas las tareas posibles
        ArrayList<Tarea> tareasSeleccionadas = faseSeleccionada.getListaTareas();   // referencia a la lista de la fase
        ArrayList<Tarea> tareasDisponibles = new ArrayList<>();

        // Filtrar disponibles (quitar las ya seleccionadas)
        if(tareasSeleccionadas != null){
            tareasSeleccionadas = faseSeleccionada.getListaTareas();
            for (Tarea t : todas) {
                boolean ya = false;
                for (Tarea ts : tareasSeleccionadas) {
                    if (ts.getId() == t.getId()) {
                        ya = true;
                        break;
                    }
                }
                if (!ya) {
                    tareasDisponibles.add(t);
                }
            }
        }else{
            tareasSeleccionadas = new ArrayList<>();
            tareasDisponibles = todas;
        }

        DefaultComboBoxModel<Tarea> comboModel = new DefaultComboBoxModel<>();
        for (Tarea t : tareasDisponibles) comboModel.addElement(t);
        JComboBox<Tarea> comboTareasDisponibles = new JComboBox<>(comboModel);

        DefaultListModel<Tarea> modeloListaTareas = new DefaultListModel<>();
        modeloListaTareas.addAll(tareasSeleccionadas);
        JList<Tarea> listaTareas = new JList<>(modeloListaTareas);
        listaTareas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);


        listaTareas.addListSelectionListener(ev -> {
            if (!ev.getValueIsAdjusting()) {
                int index = listaTareas.getSelectedIndex();
                if (index >= 0 && index < modeloListaTareas.size()) {
                    tareaSeleccionada = modeloListaTareas.get(index);
                } else {
                    tareaSeleccionada = null;
                }
            }
        });

        // Panel combo
        JPanel panelCombo = new JPanel(new BorderLayout());
        panelCombo.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panelCombo.add(comboTareasDisponibles, BorderLayout.CENTER);

        // Botón Agregar (usa la selección del combo)
        JButton btnAgregarTarea = new JButton("Agregar");
        ArrayList<Tarea> finalTareasSeleccionadas = tareasSeleccionadas;
        btnAgregarTarea.addActionListener(ev -> {
            @SuppressWarnings("unchecked")
            Tarea selCombo = (Tarea) comboTareasDisponibles.getSelectedItem();
            if (selCombo == null) {
                JOptionPane.showMessageDialog(null, "Seleccione una tarea disponible para agregar.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            boolean ya = finalTareasSeleccionadas.stream().anyMatch(t -> t.getId() == selCombo.getId());
            if (ya) {
                JOptionPane.showMessageDialog(null, "Esa tarea ya fue agregada a la fase.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Agregar a la lista de la fase y al modelo de la JList
            finalTareasSeleccionadas.add(selCombo);
            modeloListaTareas.addElement(selCombo);

            // Quitar del comboModel (disponibles)
            comboModel.removeElement(selCombo);

            // Actualizar selección del combo
            if (comboModel.getSize() > 0) {
                comboTareasDisponibles.setSelectedIndex(0);
            }

            faseSeleccionada.setListaTareas(finalTareasSeleccionadas);
        });

        JPanel panelBtnAgregarTarea = new JPanel(new BorderLayout());
        panelBtnAgregarTarea.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panelBtnAgregarTarea.add(btnAgregarTarea, BorderLayout.CENTER);

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, panelCombo, panelBtnAgregarTarea);
        splitPane.setResizeWeight(0.85);
        splitPane.setDividerSize(0);
        splitPane.setBorder(null);

        // Botón Eliminar (usa la selección de la JList y actualiza el combo)
        JButton btnEliminarTarea = new JButton("Eliminar");
        ArrayList<Tarea> finalTareasSeleccionadas1 = tareasSeleccionadas;
        btnEliminarTarea.addActionListener(ev -> {
            if (tareaSeleccionada == null) {
                JOptionPane.showMessageDialog(null, "Seleccione una tarea de la lista para eliminar.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            // Remover de la lista de la fase y del modelo
            finalTareasSeleccionadas1.removeIf(t -> t.getId() == tareaSeleccionada.getId());
            modeloListaTareas.removeElement(tareaSeleccionada);

            // Volver a agregar al combo de disponibles
            comboModel.addElement(tareaSeleccionada);
            comboTareasDisponibles.revalidate();
            comboTareasDisponibles.repaint();

            // Limpiar selección global y de la JList
            listaTareas.clearSelection();
            tareaSeleccionada = null;
        });

        // Botón Volver
        JButton btnCancelarTarea = new JButton("Volver");
        btnCancelarTarea.addActionListener(ev -> modal.dispose());

        // Panel de botones inferior
        JPanel panelBotones = new JPanel();
        panelBotones.setLayout(new BoxLayout(panelBotones, BoxLayout.X_AXIS));
        panelBotones.add(btnEliminarTarea);
        panelBotones.add(Box.createRigidArea(new Dimension(10, 0)));
        panelBotones.add(btnCancelarTarea);

        // Composición final
        contenido.add(splitPane, BorderLayout.NORTH);
        contenido.add(new JScrollPane(listaTareas), BorderLayout.CENTER);
        contenido.add(panelBotones, BorderLayout.SOUTH);

        modal.setContentPane(contenido);
        modal.setSize(500, 300);
        modal.setLocationRelativeTo(null);
        modal.setVisible(true);
    }



    private void AgregarFase(){
        if(JOptionPane.showConfirmDialog(null,
                "¿Seguro que desea agregar una fase?",
                "Confirmación",
                JOptionPane.YES_NO_OPTION) != JOptionPane.YES_OPTION)
            return;
        Fase f = new Fase();
        listaFases.add(f);
        modeloListaFases.removeAllElements();
        modeloListaFases.addAll(listaFases);
        listaFasesJList.revalidate();
        listaFasesJList.repaint();
    }

    private void EliminarFase(int index){
        if(JOptionPane.showConfirmDialog(null,
                "¿Seguro que desea eliminar la fase #"+(index+1)+"?",
                "Confirmación",
                JOptionPane.YES_NO_OPTION) != JOptionPane.YES_OPTION)
            return;

        listaFases.remove(index);
        modeloListaFases.removeAllElements();
        modeloListaFases.addAll(listaFases);
        listaFasesJList.revalidate();
        listaFasesJList.repaint();
    }

    private void Guardar(){
        if (faseSeleccionada != null && faseSeleccionadaIndex >= 0){
            listaFasesJList.clearSelection();
            listaFasesJList.setSelectedIndex(faseSeleccionadaIndex);
        }
        for(Fase f : listaFases){
            f.setId(listaFases.indexOf(f));
        }
        equipo.setFasesMantenimiento(listaFases);
        if(controlador.GuardarFases(equipo)){
            JOptionPane.showMessageDialog(null, "Fases guardadas correctamente.", "Exito", JOptionPane.INFORMATION_MESSAGE);
            Window w = SwingUtilities.getWindowAncestor(this);
            if (w != null) w.dispose();
        }
    }

    private void Cancelar(){
        if(JOptionPane.showConfirmDialog(null,
                "¿Seguro que desea salir sin guardar los cambios realizados?",
                "Confirmación",
                JOptionPane.YES_NO_OPTION) != JOptionPane.YES_OPTION)
            return;
        Window w = SwingUtilities.getWindowAncestor(this);
        if (w != null) w.dispose();
    }
}
