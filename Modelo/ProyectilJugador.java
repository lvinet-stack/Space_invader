package Modelo;

import Contratos.Colisionable;
import java.awt.Color;
import java.awt.Graphics2D;

public class ProyectilJugador extends Proyectil {
    private Color color;

    public ProyectilJugador(double x, double y) {
        super(x, y, 6, 15, 8, 1, -1);
        this.color = Color.YELLOW;
    }
    
    public ProyectilJugador(double x, double y, int velocidad, Color color) {
        super(x, y, 6, 15, velocidad, 1, -1);
        this.color = color;
    }

    @Override
    public void alColisionar(Colisionable otro) { destruir(); }

    @Override
    public void dibujar(Graphics2D g) {
        g.setColor(color);
        g.fillRect((int) getX(), (int) getY(), getAncho(), getAlto());
    }
}
