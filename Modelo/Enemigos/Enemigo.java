package Modelo.Enemigos;

import Contratos.Disparador;
import Contratos.EstrategiaMovimiento;
import Contratos.Puntuable;
import Modelo.EntidadJuego;
import java.awt.Graphics2D;

public abstract class Enemigo extends EntidadJuego implements Disparador, Puntuable {
    private int puntos;
    private int resistencia;
    private EstrategiaMovimiento estrategiaMovimiento;

    public Enemigo(double x, double y, int ancho, int alto, int puntos, int resistencia) {
        super(x, y, ancho, alto);
        this.puntos = puntos;
        this.resistencia = resistencia;
    }

    public void recibirDanio(int danio) {
        this.resistencia -= danio;
        if (this.resistencia <= 0) destruir();
    }

    public void setEstrategiaMovimiento(EstrategiaMovimiento estrategia) {
        this.estrategiaMovimiento = estrategia;
    }

    @Override
    public int getPuntos() { return puntos; }
    public int getResistencia() { return resistencia; }

    @Override
    public void actualizar() {
        if (estrategiaMovimiento != null && isActiva()) {
            estrategiaMovimiento.mover(this);
        }
    }
    
    @Override
    public void alColisionar(Contratos.Colisionable otro) { }
}
