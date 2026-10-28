import javax.swing.SwingUtilities;
import View.VentanaCaso;

public class Main {
    public static void main(String[] args) {
        // Lanza la aplicación
        SwingUtilities.invokeLater(() -> {
            VentanaCaso ventana = new VentanaCaso();
            ventana.setVisible(true);
        });
    }
}