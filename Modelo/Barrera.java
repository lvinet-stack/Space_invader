package Modelo;

import Contratos.Colisionable;
import java.awt.Color;
import java.awt.Graphics2D;

public class Barrera extends EntidadJuego {
    private int resistencia;
    private final int resistenciaMaxima = 10;

    public Barrera(double x, double y) {
        super(x, y, 60, 30);
        this.resistencia = resistenciaMaxima;
    }

    public void recibirDanio(int danio) {
        resistencia -= danio;
        if (resistencia <= 0) destruir();
    }

    @Override
    public void actualizar() { }

    @Override
    public void dibujar(Graphics2D g) {
        if (resistencia > resistenciaMaxima * 0.6) g.setColor(Color.GREEN);
        else if (resistencia > resistenciaMaxima * 0.3) g.setColor(Color.YELLOW);
        else g.setColor(Color.RED);
        g.fillRect((int) getX(), (int) getY(), getAncho(), getAlto());
    }

    @Override
    public void alColisionar(Colisionable otro) { }
}
