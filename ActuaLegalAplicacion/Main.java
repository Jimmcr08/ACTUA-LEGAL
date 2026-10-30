import javax.swing.*;
import View.VentanaCaso; // O import model.VentanaCaso;
import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Sistema Integrado de Gestión - Actual Legal");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setContentPane(new VentanaCaso());
            frame.setSize(1150, 720);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}