import javax.swing.*;
import java.awt.*;


public class TurnosFrame extends JFrame {


    private JButton btnGuardar;
    private JButton btnSalir;

    // --- CONSTRUCTOR ---
    public TurnosFrame() {
        setTitle("Reserva de Turnos - FINAL");
        setSize(400, 300); // Tamaño pequeño
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);


        JPanel testPanel = new JPanel();
        testPanel.add(new JLabel("¡VENTANA DE TURNOS CARGADA AL FIN!"));
        add(testPanel);

    }

}