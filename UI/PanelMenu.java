package UI;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class PanelMenu extends JPanel {
    private static final Color FONDO = Color.BLACK;
    private static final Color VERDE = new Color(0, 230, 90);

    private final VentanaPrincipal ventana;
    private final JLabel lblTitulo;
    private final JLabel lblControles;
    private final JLabel lblPuntajes;
    private final JLabel lblPuntajeMaximo;
    private final JButton btnJugar;
    private final JButton btnSalir;

    public PanelMenu(VentanaPrincipal ventana) {
        this.ventana = ventana;
        setBackground(FONDO);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createEmptyBorder(80, 40, 40, 40));

        lblTitulo = new JLabel("SPACE INVADERS");
        lblTitulo.setFont(new Font(Font.MONOSPACED, Font.BOLD, 48));
        lblTitulo.setForeground(VERDE);
        lblTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);

        lblControles = new JLabel("<html><div style='text-align:center'>"
                + "← → : mover<br>ESPACIO : disparar<br>P : pausar</div></html>");
        
                lblPuntajes = new JLabel(
            "<html><div style='text-align:center'>"
            + "<b>PUNTAJE POR ENEMIGO</b><br><br>"
            + "Enemigo basico: 10 puntos<br>"
            + "Enemigo tirador: 20 puntos<br>"
            + "Enemigo kamikaze: 30 puntos<br>"
            + "Jefe final: 40 puntos"
            + "</div></html>"
        );

        lblPuntajes.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 16));
        lblPuntajes.setForeground(Color.YELLOW);
        lblPuntajes.setHorizontalAlignment(SwingConstants.CENTER);
        lblPuntajes.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        lblControles.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 18));
        lblControles.setForeground(Color.LIGHT_GRAY);
        lblControles.setHorizontalAlignment(SwingConstants.CENTER);
        lblControles.setAlignmentX(Component.CENTER_ALIGNMENT);

        btnJugar = crearBoton("JUGAR");
        lblPuntajeMaximo = new JLabel("PUNTAJE MÁXIMO: 0");
        lblPuntajeMaximo.setFont(new Font(Font.MONOSPACED, Font.BOLD, 20));
        lblPuntajeMaximo.setForeground(Color.CYAN);
        lblPuntajeMaximo.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnJugar.addActionListener(e -> this.ventana.iniciarPartida());

        btnSalir = crearBoton("SALIR");
        btnSalir.addActionListener(e -> System.exit(0));add(lblTitulo);

        add(Box.createRigidArea(new Dimension(0, 35)));

        add(lblControles);
        add(Box.createRigidArea(new Dimension(0, 25)));

        add(lblPuntajes);
        add(Box.createRigidArea(new Dimension(0, 20)));

        add(lblPuntajeMaximo);
        add(Box.createRigidArea(new Dimension(0, 30)));

        add(btnJugar);
        add(Box.createRigidArea(new Dimension(0, 20)));

        add(btnSalir);
    }

    public void actualizarPuntajeMaximo(int puntaje) {
        lblPuntajeMaximo.setText("PUNTAJE MÁXIMO: " + puntaje);
    }
    
    private JButton crearBoton(String texto) {
        JButton boton = new JButton(texto);
        boton.setFont(new Font(Font.MONOSPACED, Font.BOLD, 22));
        boton.setForeground(VERDE);
        boton.setBackground(FONDO);
        boton.setFocusPainted(false);
        boton.setBorder(BorderFactory.createLineBorder(VERDE, 2));
        boton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        boton.setAlignmentX(Component.CENTER_ALIGNMENT);
        boton.setMaximumSize(new Dimension(260, 50));
        boton.setPreferredSize(new Dimension(260, 50));
        return boton;
    }
}
