package Modelo;

import Contratos.Colisionable;
import Contratos.EfectoPowerUp;
import java.awt.Color;
import java.awt.Graphics2D;

public class PowerUp extends EntidadJuego {
    private EfectoPowerUp efecto;
    private double velocidadCaida;
    private Color color;

    public PowerUp(double x, double y, EfectoPowerUp efecto, Color color) {
        super(x, y, 20, 20);
        this.efecto = efecto;
        this.color = color;
        this.velocidadCaida = 2.0;
    }

    @Override
    public void actualizar() {
        setY(getY() + velocidadCaida);
        if (getY() > 800) destruir();
    }

    public void aplicarEfecto(Jugador jugador) {
        efecto.aplicar(jugador);
        destruir();
    }

    @Override
    public void dibujar(Graphics2D g) {
        g.setColor(color);
        g.fillOval((int) getX(), (int) getY(), getAncho(), getAlto());
    }

    @Override
    public void alColisionar(Colisionable otro) { }
}
