package UI;

import Logica.Partida;
import java.awt.CardLayout;
import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

public class VentanaPrincipal extends JFrame {
    private static final int ANCHO = 800;
    private static final int ALTO = 700;
    private static final String MENU = "MENU";
    private static final String JUEGO = "JUEGO";
    private static final String GAME_OVER = "GAME_OVER";

    private final CardLayout cardLayout = new CardLayout();
    private final JPanel contenedor = new JPanel(cardLayout);
    private final PanelMenu panelMenu;
    private final PanelGameOver panelGameOver;
    private PanelJuego panelJuego;
    private Partida partida;
    private int puntajeMaximo;

    public VentanaPrincipal() {
        super("Space Invaders");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        panelMenu = new PanelMenu(this);
        panelGameOver = new PanelGameOver(this);
        panelMenu.setPreferredSize(new Dimension(ANCHO, ALTO));
        panelGameOver.setPreferredSize(new Dimension(ANCHO, ALTO));

        contenedor.add(panelMenu, MENU);
        contenedor.add(panelGameOver, GAME_OVER);
        add(contenedor);

        pack();
        setLocationRelativeTo(null);
        mostrarMenu();
    }

    public void mostrarMenu() {
        registrarPuntaje();
        detenerJuego();
        panelMenu.actualizarPuntajeMaximo(puntajeMaximo);
        cardLayout.show(contenedor, MENU);
    }

    public void mostrarJuego() {
        cardLayout.show(contenedor, JUEGO);
        panelJuego.iniciar();
    }

    public void mostrarGameOver() {
        registrarPuntaje();
        panelGameOver.mostrarResultado(partida.isVictoria(), partida.getPuntaje());
        cardLayout.show(contenedor, GAME_OVER);
    }

    public void iniciarPartida() {
        detenerJuego();
        partida = new Partida(ANCHO, ALTO);
        panelJuego = new PanelJuego(this, partida);
        contenedor.add(panelJuego, JUEGO);
        mostrarJuego();
    }

    public void reiniciarPartida() {
        iniciarPartida();
    }

    private void registrarPuntaje() {
        if (partida != null) {
            puntajeMaximo = Math.max(puntajeMaximo, partida.getPuntaje());
        }
    }
    
    private void detenerJuego() {
        if (panelJuego != null) {
            panelJuego.detener();
            contenedor.remove(panelJuego);
            panelJuego = null;
        }
    }
}
