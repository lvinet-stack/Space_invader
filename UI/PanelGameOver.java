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

public class PanelGameOver extends JPanel {
    private static final Color FONDO = Color.BLACK;
    private static final Color VERDE = new Color(0, 230, 90);
    private static final Color ROJO = new Color(230, 50, 50);

    private final VentanaPrincipal ventana;
    private final JLabel lblTitulo;
    private final JLabel lblPuntaje;
    private final JButton btnReiniciar;
    private final JButton btnMenu;

    public PanelGameOver(VentanaPrincipal ventana) {
        this.ventana = ventana;
        setBackground(FONDO);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createEmptyBorder(120, 40, 40, 40));

        lblTitulo = new JLabel("GAME OVER");
        lblTitulo.setFont(new Font(Font.MONOSPACED, Font.BOLD, 56));
        lblTitulo.setForeground(ROJO);
        lblTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);

        lblPuntaje = new JLabel("Puntaje: 0");
        lblPuntaje.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 26));
        lblPuntaje.setForeground(Color.WHITE);
        lblPuntaje.setAlignmentX(Component.CENTER_ALIGNMENT);

        btnReiniciar = crearBoton("JUGAR DE NUEVO");
        btnReiniciar.addActionListener(e -> this.ventana.reiniciarPartida());

        btnMenu = crearBoton("MENÚ");
        btnMenu.addActionListener(e -> this.ventana.mostrarMenu());

        add(lblTitulo);
        add(Box.createRigidArea(new Dimension(0, 40)));
        add(lblPuntaje);
        add(Box.createRigidArea(new Dimension(0, 70)));
        add(btnReiniciar);
        add(Box.createRigidArea(new Dimension(0, 20)));
        add(btnMenu);
    }

    public void mostrarResultado(boolean victoria, int puntaje) {
        lblTitulo.setText(victoria ? "¡VICTORIA!" : "GAME OVER");
        lblTitulo.setForeground(victoria ? VERDE : ROJO);
        lblPuntaje.setText("Puntaje: " + puntaje);
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
        boton.setMaximumSize(new Dimension(300, 50));
        boton.setPreferredSize(new Dimension(300, 50));
        return boton;
    }
}
