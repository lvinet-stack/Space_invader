package Modelo.Enemigos;

import Modelo.Proyectil;
import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

public class EnemigoBasico extends Enemigo {

    public EnemigoBasico(double x, double y) {
        super(x, y, 30, 30, 10, 1); 
    }

    @Override
    public List<Proyectil> disparar() {
        return new ArrayList<>();
    }

    @Override
    public void dibujar(Graphics2D g) {
        g.setColor(Color.GREEN);
        g.fillRect((int) getX(), (int) getY(), getAncho(), getAlto());
    }
}
