package Modelo;

import Contratos.Colisionable;
import java.awt.Graphics2D;

public abstract class Proyectil extends EntidadJuego {
    protected int velocidad;
    protected int danio;
    protected int direccion;

    public Proyectil(double x, double y, int ancho, int alto, int velocidad, int danio, int direccion) {
        super(x, y, ancho, alto);
        this.velocidad = velocidad;
        this.danio = danio;
        this.direccion = direccion;
    }

    @Override
    public void actualizar() {
        setY(getY() + (velocidad * direccion));
        if (getY() < -50 || getY() > 1000) { 
            destruir();
        }
    }

    @Override
    public abstract void alColisionar(Colisionable otro);

    public int getDanio() { return danio; }
}
