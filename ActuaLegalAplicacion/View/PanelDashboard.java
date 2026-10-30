package View;

import java.awt.*;
import java.util.List;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import model.Caso;
import model.CasoDAO;

public class PanelDashboard extends JPanel {

    private CasoDAO casoDAO;
    private JLabel lblTotalCasos;
    private JLabel lblCasosTramite;
    private JTable tablaResumen;
    private DefaultTableModel modeloTabla;

    // PALETA CORPORATIVA
    private final Color COLOR_NEGRO = new Color(20, 20, 20);
    private final Color COLOR_DORADO = new Color(197, 160, 89);
    private final Color COLOR_FONDO = new Color(242, 244, 247);
    private final Color COLOR_BLANCO = Color.WHITE;

    public PanelDashboard() {
        casoDAO = new CasoDAO();
        
        setLayout(new BorderLayout(0, 20));
        setBackground(COLOR_FONDO);
        setBorder(new EmptyBorder(25, 25, 25, 25));

        // --- ENCABEZADO Y MÉTRICAS SUPERIORES ---
        JPanel pnlNorte = new JPanel(new BorderLayout(0, 15));
        pnlNorte.setOpaque(false);

        JLabel lblTitulo = new JLabel("Resumen Ejecutivo y Control de Casos");
        lblTitulo.setFont(new Font("Serif", Font.BOLD, 24));
        lblTitulo.setForeground(COLOR_NEGRO);

        // Contenedor de 2 tarjetas métricas
        JPanel pnlTarjetas = new JPanel(new GridLayout(1, 2, 20, 0));
        pnlTarjetas.setOpaque(false);

        lblTotalCasos = new JLabel("0", SwingConstants.CENTER);
        lblCasosTramite = new JLabel("0", SwingConstants.CENTER);

        pnlTarjetas.add(crearTarjeta("Total Casos Registrados", lblTotalCasos));
        pnlTarjetas.add(crearTarjeta("Procesos Activos / Trámite", lblCasosTramite));

        pnlNorte.add(lblTitulo, BorderLayout.NORTH);
        pnlNorte.add(pnlTarjetas, BorderLayout.CENTER);

        add(pnlNorte, BorderLayout.NORTH);

        // --- TABLA CENTRAL DE CASOS ---
        JPanel pnlTablaContainer = new JPanel(new BorderLayout(0, 10));
        pnlTablaContainer.setOpaque(false);

        JLabel lblSubtituloTabla = new JLabel("Listado General de Procesos Registrados (Doble clic para abrir Google Drive):");
        lblSubtituloTabla.setFont(new Font("SansSerif", Font.BOLD, 13));
        lblSubtituloTabla.setForeground(COLOR_NEGRO);

        String[] columnas = {"No. Radicado", "Parte Defendida", "Demandante", "Demandado", "Clase Proceso", "Etapa Procesal"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };

        tablaResumen = new JTable(modeloTabla);
        tablaResumen.setRowHeight(30);
        tablaResumen.setFont(new Font("SansSerif", Font.PLAIN, 12));
        tablaResumen.setSelectionBackground(new Color(230, 220, 195));
        tablaResumen.setSelectionForeground(COLOR_NEGRO);

        tablaResumen.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 12));
        tablaResumen.getTableHeader().setBackground(COLOR_NEGRO);
        tablaResumen.getTableHeader().setForeground(COLOR_DORADO);
        tablaResumen.getTableHeader().setPreferredSize(new Dimension(0, 35));

        // EVENTO DE DOBLE CLIC PARA ABRIR GOOGLE DRIVE DIRECTO DESDE LA TABLA
        tablaResumen.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int fila = tablaResumen.getSelectedRow();
                    if (fila >= 0) {
                        int modelRow = tablaResumen.convertRowIndexToModel(fila);
                        String radicado = modeloTabla.getValueAt(modelRow, 0).toString();

                        List<Caso> lista = casoDAO.listarCasos();
                        for (Caso c : lista) {
                            if (c.getRadicado() != null && c.getRadicado().equals(radicado)) {
                                abrirEnlaceDrive(c.getUrlDrive());
                                break;
                            }
                        }
                    }
                }
            }
        });

        JScrollPane scrollTabla = new JScrollPane(tablaResumen);
        scrollTabla.getViewport().setBackground(COLOR_BLANCO);
        scrollTabla.setBorder(BorderFactory.createLineBorder(new Color(210, 210, 210)));

        pnlTablaContainer.add(lblSubtituloTabla, BorderLayout.NORTH);
        pnlTablaContainer.add(scrollTabla, BorderLayout.CENTER);

        add(pnlTablaContainer, BorderLayout.CENTER);

        actualizarMetricas();
    }

    private JPanel crearTarjeta(String titulo, JLabel lblValor) {
        JPanel card = new JPanel(new BorderLayout(10, 10));
        card.setBackground(COLOR_NEGRO);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(COLOR_DORADO, 1),
                new EmptyBorder(15, 20, 15, 20)
        ));

        JLabel lblTit = new JLabel(titulo, SwingConstants.CENTER);
        lblTit.setFont(new Font("SansSerif", Font.BOLD, 13));
        lblTit.setForeground(COLOR_BLANCO);

        lblValor.setFont(new Font("Serif", Font.BOLD, 32));
        lblValor.setForeground(COLOR_DORADO);

        card.add(lblTit, BorderLayout.NORTH);
        card.add(lblValor, BorderLayout.CENTER);

        return card;
    }

    public void actualizarMetricas() {
        try {
            modeloTabla.setRowCount(0);
            List<Caso> casos = casoDAO.listarCasos();
            if (casos != null) {
                lblTotalCasos.setText(String.valueOf(casos.size()));

                long enTramite = 0;
                for (Caso c : casos) {
                    if (c.getEtapaProcesal() != null && !c.getEtapaProcesal().trim().isEmpty()) {
                        enTramite++;
                    }
                    modeloTabla.addRow(new Object[]{
                        c.getRadicado(),
                        c.getClienteRepresentado(),
                        c.getDemandante(),
                        c.getDemandado(),
                        c.getClase(),
                        c.getEtapaProcesal()
                    });
                }
                lblCasosTramite.setText(String.valueOf(enTramite));
            }
        } catch (Exception e) {
            lblTotalCasos.setText("0");
            lblCasosTramite.setText("0");
        }
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
            JOptionPane.showMessageDialog(this, "El caso seleccionado no tiene una URL de Google Drive registrada.", "Sin Enlace", JOptionPane.INFORMATION_MESSAGE);
        }
    }
}