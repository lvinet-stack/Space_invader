package Modelo.Enemigos;

import Modelo.Proyectil;
import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

public class EnemigoBasico extends Enemigo {
    private double velocidadMovimiento;

    public EnemigoBasico(double x, double y) {
        super(x, y, 30, 30, 10, 1);
        this.velocidadMovimiento = 1.0;
    }

    @Override
    public List<Proyectil> disparar() { return new ArrayList<>(); }

    @Override
    public void dibujar(Graphics2D g) {
        g.setColor(Color.BLUE);
        g.fillRect((int) getX(), (int) getY(), getAncho(), getAlto());
    }
}
