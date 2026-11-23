package Modelo;

import Contratos.Colisionable;
import java.awt.Color;
import java.awt.Graphics2D;

public class ProyectilEnemigo extends Proyectil {
    
    public ProyectilEnemigo(double x, double y) {
        super(x, y, 6, 15, 4, 1, 1);
    }

    public ProyectilEnemigo(double x, double y, int velocidad, int danio) {
        super(x, y, 6, 15, velocidad, danio, 1);
    }

    @Override
    public void alColisionar(Colisionable otro) { destruir(); }

    @Override
    public void dibujar(Graphics2D g) {
        g.setColor(Color.RED);
        g.fillRect((int) getX(), (int) getY(), getAncho(), getAlto());
    }
}
