package View; 
import model.CasoDAO; 
import java.awt.*;
import java.util.List;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import model.Caso;

public class VentanaCaso extends JPanel {

    private CasoDAO casoDAO;
    private JTable tablaCasos;
    private DefaultTableModel modeloTabla;
    private CardLayout cardLayout;
    private JPanel panelContenidoPrincipal;

    // Campos Formulario
    private JTextField txtClase, txtApoderado, txtDemandante, txtDemandado;
    private JTextField txtCuantia, txtDespacho, txtRadicado, txtEtapa, txtUrlDrive;
    private JTextArea txtSeguimiento, txtActuaciones;
    private JButton btnGuardar, btnEditar, btnEliminar, btnCancelar;

    private int idCasoSeleccionado = -1;

    // PALETA CORPORATIVA DE ALTO NIVEL
    private final Color COLOR_NEGRO_MENUS = new Color(20, 20, 20);
    private final Color COLOR_DORADO_LUJO = new Color(197, 160, 89);
    private final Color COLOR_BLANCO_PURO = new Color(255, 255, 255);
    private final Color COLOR_TEXTO_OSCURO = new Color(35, 35, 35);
    private final Color COLOR_FONDO_CARD = new Color(242, 244, 247);
    private final Color COLOR_ROJO_ACCION = new Color(180, 40, 40);
    private final Color COLOR_HOVER_SIDEBAR = new Color(35, 35, 35);

    public VentanaCaso() {
        casoDAO = new CasoDAO();
        setLayout(new BorderLayout());

        // Contenedor divido: Sidebar Lateral + Area Principal
        add(construirSidebarLateral(), BorderLayout.WEST);

        cardLayout = new CardLayout();
        panelContenidoPrincipal = new JPanel(cardLayout);

        panelContenidoPrincipal.add(construirVistaTabla(), "TABLA");
        panelContenidoPrincipal.add(construirVistaFormulario(), "FORMULARIO");

        add(panelContenidoPrincipal, BorderLayout.CENTER);
        actualizarDatos();
    }

    // --- BARRA LATERAL EJECUTIVA ---
    private JPanel construirSidebarLateral() {
        JPanel sidebar = new JPanel(new BorderLayout());
        sidebar.setPreferredSize(new Dimension(240, 0));
        sidebar.setBackground(COLOR_NEGRO_MENUS);

        // Header Sidebar (Logo / Firma)
        JPanel pnlBrand = new JPanel(new GridLayout(2, 1, 0, 5));
        pnlBrand.setOpaque(false);
        pnlBrand.setBorder(new EmptyBorder(30, 20, 30, 20));

        JLabel lblLogo = new JLabel("ACTUAL LEGAL");
        lblLogo.setFont(new Font("Serif", Font.BOLD, 20));
        lblLogo.setForeground(COLOR_DORADO_LUJO);

        JLabel lblSub = new JLabel("Bufete de Abogados");
        lblSub.setFont(new Font("SansSerif", Font.PLAIN, 11));
        lblSub.setForeground(new Color(180, 180, 180));

        pnlBrand.add(lblLogo);
        pnlBrand.add(lblSub);

        // Menú de Opciones
        JPanel pnlMenu = new JPanel(new GridLayout(6, 1, 0, 8));
        pnlMenu.setOpaque(false);
        pnlMenu.setBorder(new EmptyBorder(10, 15, 10, 15));

        JButton btnNavTabla = crearBotonSidebar("📂  Listado de Casos");
        JButton btnNavNuevo = crearBotonSidebar("➕  Nuevo Registro");

        btnNavTabla.addActionListener(e -> mostrarTabla());
        btnNavNuevo.addActionListener(e -> nuevoCaso());

        pnlMenu.add(btnNavTabla);
        pnlMenu.add(btnNavNuevo);

        sidebar.add(pnlBrand, BorderLayout.NORTH);
        sidebar.add(pnlMenu, BorderLayout.CENTER);

        return sidebar;
    }

    private JButton crearBotonSidebar(String texto) {
        JButton btn = new JButton(texto);
        btn.setFont(new Font("SansSerif", Font.BOLD, 12));
        btn.setForeground(COLOR_BLANCO_PURO);
        btn.setBackground(COLOR_NEGRO_MENUS);
        btn.setHorizontalAlignment(SwingConstants.LEFT);
        btn.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(40, 40, 40)),
                new EmptyBorder(12, 15, 12, 15)
        ));
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));

        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn.setBackground(COLOR_HOVER_SIDEBAR);
                btn.setForeground(COLOR_DORADO_LUJO);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn.setBackground(COLOR_NEGRO_MENUS);
                btn.setForeground(COLOR_BLANCO_PURO);
            }
        });

        return btn;
    }

    // --- VISTA TABLA CORPORATIVA ---
    private JPanel construirVistaTabla() {
        JPanel pnlMain = new JPanel(new BorderLayout(15, 15));
        pnlMain.setBackground(COLOR_FONDO_CARD);
        pnlMain.setBorder(new EmptyBorder(25, 25, 25, 25));

        // Header de la Vista
        JPanel pnlHeader = new JPanel(new BorderLayout());
        pnlHeader.setOpaque(false);

        JLabel lblTitulo = new JLabel("Control y Seguimiento de Procesos");
        lblTitulo.setFont(new Font("Serif", Font.BOLD, 22));
        lblTitulo.setForeground(COLOR_TEXTO_OSCURO);

        JButton btnNuevo = crearBotonAccion("+ Registrar Nuevo Caso", COLOR_DORADO_LUJO, COLOR_NEGRO_MENUS);
        btnNuevo.addActionListener(e -> nuevoCaso());

        pnlHeader.add(lblTitulo, BorderLayout.WEST);
        pnlHeader.add(btnNuevo, BorderLayout.EAST);

        // Configuración de Tabla de Lujo
        String[] columnas = {
            "ID", "No. Radicado", "Demandante", "Demandado", 
            "Clase Proceso", "Etapa Procesal", "Cuantía ($)"
        };

        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };

        tablaCasos = new JTable(modeloTabla);
        tablaCasos.setRowHeight(32);
        tablaCasos.setFont(new Font("SansSerif", Font.PLAIN, 13));
        tablaCasos.setSelectionBackground(new Color(230, 220, 195));
        tablaCasos.setSelectionForeground(COLOR_TEXTO_OSCURO);

        // Header de Tabla
        tablaCasos.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 13));
        tablaCasos.getTableHeader().setBackground(COLOR_NEGRO_MENUS);
        tablaCasos.getTableHeader().setForeground(COLOR_DORADO_LUJO);
        tablaCasos.getTableHeader().setPreferredSize(new Dimension(0, 38));

        // Ocultar ID
        tablaCasos.getColumnModel().getColumn(0).setMinWidth(0);
        tablaCasos.getColumnModel().getColumn(0).setMaxWidth(0);
        tablaCasos.getColumnModel().getColumn(0).setWidth(0);

        // Evento Doble Clic para abrir Google Drive
        tablaCasos.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int fila = tablaCasos.getSelectedRow();
                    if (fila >= 0) {
                        int modelRow = tablaCasos.convertRowIndexToModel(fila);
                        int id = Integer.parseInt(modeloTabla.getValueAt(modelRow, 0).toString());

                        List<Caso> lista = casoDAO.listarCasos();
                        for (Caso c : lista) {
                            if (c.getId() == id) {
                                abrirEnlaceDrive(c.getUrlDrive());
                                break;
                            }
                        }
                    }
                }
            }
        });

        JScrollPane scrollTabla = new JScrollPane(tablaCasos);
        scrollTabla.getViewport().setBackground(COLOR_BLANCO_PURO);
        scrollTabla.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220)));

        // Panel de Acciones Inferior
        JPanel pnlAcciones = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        pnlAcciones.setOpaque(false);

        JButton btnCargar = crearBotonAccion("Ver / Editar Caso", COLOR_NEGRO_MENUS, COLOR_BLANCO_PURO);
        btnCargar.addActionListener(e -> {
            int fila = tablaCasos.getSelectedRow();
            if (fila >= 0) {
                int modelRow = tablaCasos.convertRowIndexToModel(fila);
                int id = Integer.parseInt(modeloTabla.getValueAt(modelRow, 0).toString());
                cargarCasoEnFormulario(id);
            } else {
                JOptionPane.showMessageDialog(this, "Seleccione un caso de la lista para ver sus detalles.", "Aviso", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        pnlAcciones.add(btnCargar);

        pnlMain.add(pnlHeader, BorderLayout.NORTH);
        pnlMain.add(scrollTabla, BorderLayout.CENTER);
        pnlMain.add(pnlAcciones, BorderLayout.SOUTH);

        return pnlMain;
    }

    // --- VISTA FORMULARIO CORPORATIVO ---
    private JPanel construirVistaFormulario() {
        JPanel pnlMain = new JPanel(new BorderLayout(15, 15));
        pnlMain.setBackground(COLOR_FONDO_CARD);
        pnlMain.setBorder(new EmptyBorder(25, 25, 25, 25));

        // Banner Superior Formulario
        JLabel lblTitulo = new JLabel("Ficha de Registro y Gestión del Caso");
        lblTitulo.setFont(new Font("Serif", Font.BOLD, 22));
        lblTitulo.setForeground(COLOR_TEXTO_OSCURO);

        // Card Blanca para Campos
        JPanel pnlGrid = new JPanel(new GridLayout(5, 2, 15, 12));
        pnlGrid.setBackground(COLOR_BLANCO_PURO);
        pnlGrid.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220), 1),
                new EmptyBorder(20, 20, 20, 20)));

        txtClase = agregarCampoFormulario(pnlGrid, "Clase de Proceso:");
        txtApoderado = agregarCampoFormulario(pnlGrid, "Apoderado Judicial:");
        txtDemandante = agregarCampoFormulario(pnlGrid, "Demandante:");
        txtDemandado = agregarCampoFormulario(pnlGrid, "Demandado:");
        txtCuantia = agregarCampoFormulario(pnlGrid, "Cuantía ($):");
        txtDespacho = agregarCampoFormulario(pnlGrid, "Despacho Judicial:");
        txtRadicado = agregarCampoFormulario(pnlGrid, "No. Radicado:");
        txtEtapa = agregarCampoFormulario(pnlGrid, "Etapa Procesal:");
        txtUrlDrive = agregarCampoFormulario(pnlGrid, "Enlace Carpeta Google Drive:");

        txtSeguimiento = new JTextArea(2, 10);
        txtActuaciones = new JTextArea(2, 10);

        JPanel pnlAreas = new JPanel(new GridLayout(2, 1, 10, 10));
        pnlAreas.setOpaque(false);
        pnlAreas.add(crearAreaConEtiqueta("Seguimiento y Registro de Actuaciones:", txtSeguimiento));
        pnlAreas.add(crearAreaConEtiqueta("Actuaciones Pendientes por Realizar:", txtActuaciones));

        // Botonera Formulario
        JPanel pnlBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT, 12, 10));
        pnlBotones.setOpaque(false);

        btnGuardar = crearBotonAccion("Guardar Caso", COLOR_DORADO_LUJO, COLOR_NEGRO_MENUS);
        btnEditar = crearBotonAccion("Actualizar", COLOR_NEGRO_MENUS, COLOR_BLANCO_PURO);
        btnEliminar = crearBotonAccion("Eliminar", COLOR_ROJO_ACCION, COLOR_BLANCO_PURO);
        btnCancelar = crearBotonAccion("Volver al Listado", Color.LIGHT_GRAY, COLOR_TEXTO_OSCURO);

        pnlBotones.add(btnGuardar);
        pnlBotones.add(btnEditar);
        pnlBotones.add(btnEliminar);
        pnlBotones.add(btnCancelar);

        btnGuardar.addActionListener(e -> guardarCaso());
        btnEditar.addActionListener(e -> editarCaso());
        btnEliminar.addActionListener(e -> eliminarCaso());
        btnCancelar.addActionListener(e -> mostrarTabla());

        JPanel pnlCenter = new JPanel(new BorderLayout(10, 10));
        pnlCenter.setOpaque(false);
        pnlCenter.add(pnlGrid, BorderLayout.NORTH);
        pnlCenter.add(pnlAreas, BorderLayout.CENTER);

        pnlMain.add(lblTitulo, BorderLayout.NORTH);
        pnlMain.add(pnlCenter, BorderLayout.CENTER);
        pnlMain.add(pnlBotones, BorderLayout.SOUTH);

        return pnlMain;
    }

    private JTextField agregarCampoFormulario(JPanel container, String labelText) {
        JPanel pnlGroup = new JPanel(new BorderLayout(4, 4));
        pnlGroup.setOpaque(false);

        JLabel lbl = new JLabel(labelText);
        lbl.setFont(new Font("SansSerif", Font.BOLD, 12));
        lbl.setForeground(COLOR_TEXTO_OSCURO);

        JTextField txt = new JTextField();
        txt.setFont(new Font("SansSerif", Font.PLAIN, 13));
        txt.setPreferredSize(new Dimension(100, 32));
        txt.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                new EmptyBorder(2, 8, 2, 8)
        ));

        pnlGroup.add(lbl, BorderLayout.NORTH);
        pnlGroup.add(txt, BorderLayout.CENTER);
        container.add(pnlGroup);

        return txt;
    }

    private JPanel crearAreaConEtiqueta(String labelText, JTextArea area) {
        JPanel pnlGroup = new JPanel(new BorderLayout(4, 4));
        pnlGroup.setOpaque(false);

        JLabel lbl = new JLabel(labelText);
        lbl.setFont(new Font("SansSerif", Font.BOLD, 12));
        lbl.setForeground(COLOR_TEXTO_OSCURO);

        area.setFont(new Font("SansSerif", Font.PLAIN, 13));
        area.setLineWrap(true);
        area.setWrapStyleWord(true);
        area.setBorder(new EmptyBorder(5, 5, 5, 5));

        JScrollPane scroll = new JScrollPane(area);
        scroll.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));

        pnlGroup.add(lbl, BorderLayout.NORTH);
        pnlGroup.add(scroll, BorderLayout.CENTER);

        return pnlGroup;
    }

    private JButton crearBotonAccion(String texto, Color bg, Color fg) {
        JButton btn = new JButton(texto);
        btn.setFont(new Font("SansSerif", Font.BOLD, 12));
        btn.setBackground(bg);
        btn.setForeground(fg);
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setPreferredSize(new Dimension(140, 36));
        btn.setBorder(BorderFactory.createEmptyBorder());
        return btn;
    }

    // --- LÓGICA Y MÉTODOS DE SOPORTE ---
    public void mostrarTabla() {
        actualizarDatos();
        limpiarCampos();
        cardLayout.show(panelContenidoPrincipal, "TABLA");
    }

    public void nuevoCaso() {
        limpiarCampos();
        btnGuardar.setEnabled(true);
        btnEditar.setEnabled(false);
        btnEliminar.setEnabled(false);
        cardLayout.show(panelContenidoPrincipal, "FORMULARIO");
    }

    private boolean validarCamposObligatorios() {
        if (txtClase.getText().trim().isEmpty() || txtApoderado.getText().trim().isEmpty()
                || txtDemandante.getText().trim().isEmpty() || txtDemandado.getText().trim().isEmpty()
                || txtCuantia.getText().trim().isEmpty() || txtDespacho.getText().trim().isEmpty()
                || txtRadicado.getText().trim().isEmpty() || txtEtapa.getText().trim().isEmpty()
                || txtSeguimiento.getText().trim().isEmpty() || txtActuaciones.getText().trim().isEmpty()
                || txtUrlDrive.getText().trim().isEmpty()) {

            JOptionPane.showMessageDialog(this,
                    "Todos los 11 campos son requeridos obligatoriamente para la ficha del caso.",
                    "Información Incompleta", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        return true;
    }

    private void guardarCaso() {
        if (!validarCamposObligatorios()) return;

        Caso c = obtenerObjetoCaso();
        if (casoDAO.guardarCaso(c)) {
            JOptionPane.showMessageDialog(this, "Caso registrado exitosamente en el sistema.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            mostrarTabla();
        } else {
            JOptionPane.showMessageDialog(this, "No se pudo guardar. Verifique que el No. Radicado no se encuentre duplicado.", "Error de Guardado", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void editarCaso() {
        if (idCasoSeleccionado == -1) return;
        if (!validarCamposObligatorios()) return;

        Caso c = obtenerObjetoCaso();
        c.setId(idCasoSeleccionado);
        if (casoDAO.actualizarCaso(c)) {
            JOptionPane.showMessageDialog(this, "Información del caso actualizada correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            mostrarTabla();
        }
    }

    private void eliminarCaso() {
        if (idCasoSeleccionado == -1) return;

        int op = JOptionPane.showConfirmDialog(this, "¿Está seguro de eliminar de forma permanente este caso?", "Confirmar Eliminación", JOptionPane.YES_NO_OPTION);
        if (op == JOptionPane.YES_OPTION) {
            if (casoDAO.eliminarCaso(idCasoSeleccionado)) {
                JOptionPane.showMessageDialog(this, "Caso eliminado.");
                mostrarTabla();
            }
        }
    }

    public void cargarCasoEnFormulario(int id) {
        idCasoSeleccionado = id;
        List<Caso> lista = casoDAO.listarCasos();
        for (Caso c : lista) {
            if (c.getId() == id) {
                txtClase.setText(c.getClase());
                txtApoderado.setText(c.getApoderadoJudicial());
                txtDemandante.setText(c.getDemandante());
                txtDemandado.setText(c.getDemandado());
                txtCuantia.setText(c.getCuantia());
                txtDespacho.setText(c.getDespachoJudicial());
                txtRadicado.setText(c.getRadicado());
                txtEtapa.setText(c.getEtapaProcesal());
                txtSeguimiento.setText(c.getActuacionesSeguimiento());
                txtActuaciones.setText(c.getActuacionesARealizar());
                txtUrlDrive.setText(c.getUrlDrive());

                btnGuardar.setEnabled(false);
                btnEditar.setEnabled(true);
                btnEliminar.setEnabled(true);

                cardLayout.show(panelContenidoPrincipal, "FORMULARIO");
                break;
            }
        }
    }

    private Caso obtenerObjetoCaso() {
        return new Caso(
                txtClase.getText(), txtApoderado.getText(), txtDemandante.getText(),
                txtDemandado.getText(), txtCuantia.getText().trim(), txtDespacho.getText(),
                txtRadicado.getText(), txtSeguimiento.getText(), txtEtapa.getText(),
                txtActuaciones.getText(), txtUrlDrive.getText().trim()
        );
    }

    public void actualizarDatos() {
        modeloTabla.setRowCount(0);
        List<Caso> lista = casoDAO.listarCasos();
        for (Caso c : lista) {
            modeloTabla.addRow(new Object[]{
                c.getId(), c.getRadicado(), c.getDemandante(),
                c.getDemandado(), c.getClase(), c.getEtapaProcesal(), c.getCuantia()
            });
        }
    }

    private void limpiarCampos() {
        txtClase.setText(""); txtApoderado.setText(""); txtDemandante.setText("");
        txtDemandado.setText(""); txtCuantia.setText(""); txtDespacho.setText("");
        txtRadicado.setText(""); txtEtapa.setText(""); txtSeguimiento.setText("");
        txtActuaciones.setText(""); txtUrlDrive.setText("");
        idCasoSeleccionado = -1;
    }

    private void abrirEnlaceDrive(String url) {
        if (url != null && !url.trim().isEmpty()) {
            try {
                if (!url.startsWith("http://") && !url.startsWith("https://")) {
                    url = "https://" + url;
                }
                java.awt.Desktop.getDesktop().browse(new java.net.URI(url.trim()));
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "No se pudo abrir la dirección de Google Drive.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "El caso no tiene una URL de Google Drive registrada.", "Sin Enlace", JOptionPane.INFORMATION_MESSAGE);
        }
    }
}