package Modelo;

import Contratos.Colisionable;

public abstract class Proyectil extends EntidadJuego {
    private int velocidad;
    private int danio;
    private int direccion;

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
