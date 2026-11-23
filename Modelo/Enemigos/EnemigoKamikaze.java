package Modelo.Enemigos;

import Modelo.Proyectil;
import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

public class EnemigoKamikaze extends Enemigo {
    private double velocidadCaida;
    private double velocidadPersecucion;
    private int danioContacto;

    public EnemigoKamikaze(double x, double y) {
        super(x, y, 25, 25, 30, 1);
        this.velocidadCaida = 2.0;
        this.velocidadPersecucion = 1.0;
        this.danioContacto = 1;
    }

    @Override
    public void actualizar() {
        if (isActiva()) {
            setY(getY() + velocidadCaida);
        }
    }

    @Override
    public List<Proyectil> disparar() { return new ArrayList<>(); }

    @Override
    public void dibujar(Graphics2D g) {
        g.setColor(Color.RED);
        g.fillPolygon(new int[]{(int) getX(), (int) getX() + getAncho(), (int) getX() + (getAncho() / 2)},
                new int[]{(int) getY(), (int) getY(), (int) getY() + getAlto()}, 3);
    }
}
