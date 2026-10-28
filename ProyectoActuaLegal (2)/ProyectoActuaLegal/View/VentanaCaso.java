package View;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import model.Caso;
import model.CasoDAO;

public class VentanaCaso extends JFrame {

    private JTextField txtClase, txtApoderado, txtDemandante, txtDemandado;
    private JTextField txtCuantia, txtDespacho, txtRadicado, txtEtapa;
    private JTextArea txtSeguimiento, txtActuaciones;
    private JButton btnGuardar, btnEditar, btnEliminar, btnLimpiar;
    
    private JTable tablaCasos;
    private DefaultTableModel modeloTabla;

    private CasoDAO casoDAO;
    private int idCasoSeleccionado = -1;

    // Paleta de Colores Corporativa: Dorado, Negro y Blanco
    private final Color COLOR_NEGRO = new Color(18, 18, 18);
    private final Color COLOR_DORADO = new Color(212, 175, 55);
    private final Color COLOR_BLANCO = Color.WHITE;
    private final Color COLOR_GRIS_FONDO = new Color(245, 245, 245);
    private final Color COLOR_ROJO_ELIMINAR = new Color(180, 40, 40);

    public VentanaCaso() {
        casoDAO = new CasoDAO();

        setTitle("Actual Legal - Sistema de Gestión de Casos");
        setSize(1150, 720);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(COLOR_GRIS_FONDO);

        // --- ENCABEZADO NEGRO CON TEXTO DORADO Y BLANCO ---
        JPanel panelHeader = new JPanel();
        panelHeader.setBackground(COLOR_NEGRO);
        panelHeader.setPreferredSize(new Dimension(1150, 65));
        panelHeader.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 15));

        JLabel lblTitulo = new JLabel("ACTUAL LEGAL | GESTIÓN DE CASOS");
        lblTitulo.setForeground(COLOR_DORADO);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 22));
        panelHeader.add(lblTitulo);
        add(panelHeader, BorderLayout.NORTH);

        // --- FORMULARIO IZQUIERDO ---
        JPanel panelFormulario = new JPanel(new GridLayout(10, 2, 6, 6));
        panelFormulario.setBackground(COLOR_BLANCO);
        panelFormulario.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(COLOR_DORADO, 1), " Datos del Caso ",
                0, 0, new Font("Segoe UI", Font.BOLD, 13), COLOR_NEGRO));
        panelFormulario.setPreferredSize(new Dimension(430, 0));

        panelFormulario.add(new JLabel("Clase de proceso:"));
        txtClase = new JTextField();
        panelFormulario.add(txtClase);

        panelFormulario.add(new JLabel("Apoderado Judicial:"));
        txtApoderado = new JTextField();
        panelFormulario.add(txtApoderado);

        panelFormulario.add(new JLabel("Demandante:"));
        txtDemandante = new JTextField();
        panelFormulario.add(txtDemandante);

        panelFormulario.add(new JLabel("Demandado:"));
        txtDemandado = new JTextField();
        panelFormulario.add(txtDemandado);

        panelFormulario.add(new JLabel("Cuantía ($):"));
        txtCuantia = new JTextField();
        panelFormulario.add(txtCuantia);

        panelFormulario.add(new JLabel("Despacho Judicial:"));
        txtDespacho = new JTextField();
        panelFormulario.add(txtDespacho);

        panelFormulario.add(new JLabel("No. Radicado:"));
        txtRadicado = new JTextField();
        panelFormulario.add(txtRadicado);

        panelFormulario.add(new JLabel("Etapa Procesal:"));
        txtEtapa = new JTextField();
        panelFormulario.add(txtEtapa);

        panelFormulario.add(new JLabel("Seguimiento:"));
        txtSeguimiento = new JTextArea(2, 10);
        panelFormulario.add(new JScrollPane(txtSeguimiento));

        panelFormulario.add(new JLabel("Actuaciones a Realizar:"));
        txtActuaciones = new JTextArea(2, 10);
        panelFormulario.add(new JScrollPane(txtActuaciones));

        // --- BOTONES ESTILIZADOS ---
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 8, 10));
        panelBotones.setBackground(COLOR_BLANCO);

        btnGuardar = crearBoton("Guardar", COLOR_DORADO, COLOR_NEGRO);
        btnEditar = crearBoton("Editar", COLOR_NEGRO, COLOR_BLANCO);
        btnEliminar = crearBoton("Eliminar", COLOR_ROJO_ELIMINAR, COLOR_BLANCO);
        btnLimpiar = crearBoton("Limpiar", COLOR_GRIS_FONDO, COLOR_NEGRO);

        panelBotones.add(btnGuardar);
        panelBotones.add(btnEditar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnLimpiar);

        JPanel panelIzquierdo = new JPanel(new BorderLayout());
        panelIzquierdo.setBackground(COLOR_BLANCO);
        panelIzquierdo.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 0));
        panelIzquierdo.add(panelFormulario, BorderLayout.CENTER);
        panelIzquierdo.add(panelBotones, BorderLayout.SOUTH);

        add(panelIzquierdo, BorderLayout.WEST);

        // --- TABLA DERECHA ---
        String[] columnas = {"ID", "Radicado", "Demandante", "Demandado", "Clase", "Etapa", "Cuantía"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tablaCasos = new JTable(modeloTabla);
        tablaCasos.setRowHeight(28);
        tablaCasos.setSelectionBackground(COLOR_DORADO);
        tablaCasos.setSelectionForeground(COLOR_NEGRO);
        tablaCasos.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        tablaCasos.getTableHeader().setBackground(COLOR_NEGRO);
        tablaCasos.getTableHeader().setForeground(COLOR_DORADO);

        JScrollPane scrollTabla = new JScrollPane(tablaCasos);
        scrollTabla.getViewport().setBackground(COLOR_BLANCO);
        scrollTabla.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(COLOR_DORADO, 1), " Casos Registrados ",
                0, 0, new Font("Segoe UI", Font.BOLD, 13), COLOR_NEGRO));

        JPanel panelDerecho = new JPanel(new BorderLayout());
        panelDerecho.setBackground(COLOR_GRIS_FONDO);
        panelDerecho.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 10));
        panelDerecho.add(scrollTabla, BorderLayout.CENTER);

        add(panelDerecho, BorderLayout.CENTER);

        // --- EVENTOS ---
        btnGuardar.addActionListener(e -> guardarCaso());
        btnEditar.addActionListener(e -> editarCaso());
        btnEliminar.addActionListener(e -> eliminarCaso());
        btnLimpiar.addActionListener(e -> limpiarCampos());

        tablaCasos.getSelectionModel().addListSelectionListener(e -> seleccionarFilaTabla());

        cargarCasosEnTabla();
    }

    private JButton crearBoton(String texto, Color bg, Color fg) {
        JButton btn = new JButton(texto);
        btn.setBackground(bg);
        btn.setForeground(fg);
        btn.setFocusPainted(false);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btn.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(COLOR_NEGRO, 1),
                BorderFactory.createEmptyBorder(6, 12, 6, 12)));
        return btn;
    }

    private void cargarCasosEnTabla() {
        modeloTabla.setRowCount(0);
        List<Caso> lista = casoDAO.listarCasos();
        for (Caso c : lista) {
            Object[] fila = {
                c.getId(),
                c.getRadicado(),
                c.getDemandante(),
                c.getDemandado(),
                c.getClase(),
                c.getEtapaProcesal(),
                "$" + c.getCuantia()
            };
            modeloTabla.addRow(fila);
        }
    }

    private void guardarCaso() {
        try {
            Caso c = obtenerDatosFormulario();
            if (casoDAO.guardarCaso(c)) {
                JOptionPane.showMessageDialog(this, "Caso guardado exitosamente.");
                limpiarCampos();
                cargarCasosEnTabla();
            } else {
                JOptionPane.showMessageDialog(this, "Error al guardar el caso.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Verifique los datos ingresados.", "Atención", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void editarCaso() {
        if (idCasoSeleccionado == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione primero un caso de la tabla.");
            return;
        }

        try {
            Caso c = obtenerDatosFormulario();
            c.setId(idCasoSeleccionado);

            if (casoDAO.actualizarCaso(c)) {
                JOptionPane.showMessageDialog(this, "Caso actualizado correctamente.");
                limpiarCampos();
                cargarCasosEnTabla();
            } else {
                JOptionPane.showMessageDialog(this, "Error al actualizar el caso.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error en los datos a actualizar.");
        }
    }

    private void eliminarCaso() {
        if (idCasoSeleccionado == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un caso de la tabla para eliminar.");
            return;
        }

        int confirmar = JOptionPane.showConfirmDialog(this, "¿Está seguro de eliminar este caso?", "Confirmar Eliminación", JOptionPane.YES_NO_OPTION);
        if (confirmar == JOptionPane.YES_OPTION) {
            if (casoDAO.eliminarCaso(idCasoSeleccionado)) {
                JOptionPane.showMessageDialog(this, "Caso eliminado.");
                limpiarCampos();
                cargarCasosEnTabla();
            } else {
                JOptionPane.showMessageDialog(this, "Error al eliminar el caso.");
            }
        }
    }

    private void seleccionarFilaTabla() {
        int fila = tablaCasos.getSelectedRow();
        if (fila >= 0) {
            idCasoSeleccionado = Integer.parseInt(tablaCasos.getValueAt(fila, 0).toString());
            
            List<Caso> lista = casoDAO.listarCasos();
            for (Caso c : lista) {
                if (c.getId() == idCasoSeleccionado) {
                    txtClase.setText(c.getClase());
                    txtApoderado.setText(c.getApoderadoJudicial());
                    txtDemandante.setText(c.getDemandante());
                    txtDemandado.setText(c.getDemandado());
                    txtCuantia.setText(String.valueOf(c.getCuantia()));
                    txtDespacho.setText(c.getDespachoJudicial());
                    txtRadicado.setText(c.getRadicado());
                    txtEtapa.setText(c.getEtapaProcesal());
                    txtSeguimiento.setText(c.getActuacionesSeguimiento());
                    txtActuaciones.setText(c.getActuacionesARealizar());
                    break;
                }
            }
        }
    }

    private Caso obtenerDatosFormulario() {
        double cuantia = 0;
        if (!txtCuantia.getText().trim().isEmpty()) {
            cuantia = Double.parseDouble(txtCuantia.getText().trim());
        }

        return new Caso(
            txtClase.getText(),
            txtApoderado.getText(),
            txtDemandante.getText(),
            txtDemandado.getText(),
            cuantia,
            txtDespacho.getText(),
            txtRadicado.getText(),
            txtSeguimiento.getText(),
            txtEtapa.getText(),
            txtActuaciones.getText()
        );
    }

    private void limpiarCampos() {
        idCasoSeleccionado = -1;
        txtClase.setText("");
        txtApoderado.setText("");
        txtDemandante.setText("");
        txtDemandado.setText("");
        txtCuantia.setText("");
        txtDespacho.setText("");
        txtRadicado.setText("");
        txtEtapa.setText("");
        txtSeguimiento.setText("");
        txtActuaciones.setText("");
        tablaCasos.clearSelection();
    }
}