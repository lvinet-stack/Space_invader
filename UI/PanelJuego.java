package UI;

import Logica.GestorNiveles;
import Logica.Partida;
import Modelo.EntidadJuego;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.HashSet;
import java.util.Set;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

public class PanelJuego extends JPanel {
    private static final int RETARDO_MS = 16; // ~60 FPS

    private final VentanaPrincipal ventana;
    private final Partida partida;
    private final GestorNiveles gestorNiveles;
    private final Timer temporizador;
    private final Set<Integer> teclas = new HashSet<>();

    public PanelJuego(VentanaPrincipal ventana, Partida partida) {
      this.ventana = ventana;
      this.partida = partida;
      this.gestorNiveles = partida.getGestorNiveles();
      this.temporizador = new Timer(RETARDO_MS, e -> actualizarJuego());

      setPreferredSize(new Dimension(partida.getAncho(), partida.getAlto()));
      setBackground(Color.BLACK);
      setFocusable(true);

      addKeyListener(new KeyAdapter() {
          @Override
          public void keyPressed(KeyEvent e) {

              int tecla = e.getKeyCode();

              if (tecla == KeyEvent.VK_P) {
                  if (teclas.add(tecla)) {
                      partida.pausar();
                      repaint();
                  }
                  return;
              }

              if (tecla == KeyEvent.VK_ESCAPE && partida.isPausada()) {
                  detener();
                  teclas.clear();
                  ventana.mostrarMenu();
                  return;
              }

              teclas.add(tecla);
          }

          @Override
          public void keyReleased(KeyEvent e) {
              teclas.remove(e.getKeyCode());
          }
      });
    }

    public void iniciar() {
        teclas.clear();
        temporizador.start();

        SwingUtilities.invokeLater(() -> {
            requestFocusInWindow();
        });
    }

    public void detener() {
        temporizador.stop();
    }

    private void actualizarJuego() {
        procesarTeclas();
        partida.actualizar();
        repaint();
        if (partida.isTerminada()) {
            detener();
            ventana.mostrarGameOver();
        }
    }

    private void procesarTeclas() {
        if (teclas.contains(KeyEvent.VK_LEFT) || teclas.contains(KeyEvent.VK_A)) {
            partida.moverJugadorIzquierda();
        }
        if (teclas.contains(KeyEvent.VK_RIGHT) || teclas.contains(KeyEvent.VK_D)) {
            partida.moverJugadorDerecha();
        }
        if (teclas.contains(KeyEvent.VK_SPACE)) {
            partida.jugadorDispara();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        for (EntidadJuego entidad : partida.getEntidades()) {
            entidad.dibujar(g2);
        }
        dibujarHUD(g2);
    }

    private void dibujarHUD(Graphics2D g) {
        g.setColor(Color.WHITE);
        g.setFont(new Font(Font.MONOSPACED, Font.BOLD, 16));
        g.drawString("Puntaje: " + partida.getPuntaje(), 15, 25);

        String nivel = gestorNiveles.isJefeCreado()
                ? "JEFE FINAL"
                : "Nivel: " + partida.getNivel() + "/" + GestorNiveles.NIVEL_FINAL;
        g.drawString(nivel, partida.getAncho() / 2 - 55, 25);
        g.drawString("Vidas: " + partida.getJugador().getVidas(), partida.getAncho() - 110, 25);

        if (partida.isPausada()) {

        g.setFont(new Font(Font.MONOSPACED, Font.BOLD, 48));

        String texto = "PAUSA";
        int ancho = g.getFontMetrics().stringWidth(texto);

        g.drawString(
            texto,
            (partida.getAncho() - ancho) / 2,
            partida.getAlto() / 2
        );

        g.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 18));

        String instruccion = "Presione ESC para volver al menu";
        int anchoTexto = g.getFontMetrics().stringWidth(instruccion);

        g.drawString(
            instruccion,
            (partida.getAncho() - anchoTexto) / 2,
            partida.getAlto() / 2 + 40
        );
    }
    }
}
