package Modelo;

import Contratos.Colisionable;
import Contratos.Disparador;
import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

public class Jugador extends EntidadJuego implements Disparador {
    private int vidas;
    private double velocidad;
    private int tiempoRecarga;
    private int recargaActual;
    
    private boolean escudoActivo;
    private int tiempoEscudo;
    
    private boolean disparoDoble;
    private int tiempoDisparoDoble;

    public Jugador(double x, double y) {
        super(x, y, 40, 30);
        this.vidas = 3;
        this.velocidad = 6.0;
        this.tiempoRecarga = 15;
        this.recargaActual = 0;
        this.escudoActivo = false;
        this.disparoDoble = false;
    }

    public void moverIzquierda() {
        setX(getX() - velocidad);
    }

    public void moverDerecha() {
        setX(getX() + velocidad);
    }

    @Override
    public List<Proyectil> disparar() {
        List<Proyectil> disparos = new ArrayList<>();
        if (recargaActual <= 0) {
            double xCentro = getX() + (getAncho() / 2.0) - 3;
            double ySalida = getY();
            
            disparos.add(new ProyectilJugador(xCentro, ySalida));
            if (disparoDoble) {
                disparos.add(new ProyectilJugador(getX() + 5, ySalida));
                disparos.add(new ProyectilJugador(getX() + getAncho() - 11, ySalida));
            }
            recargaActual = tiempoRecarga;
        }
        return disparos;
    }

    public void perderVida() {
        if (vidas > 0) {
            if (escudoActivo) {
                escudoActivo = false; 
            } else {
                vidas--;
            }
        }
    }

    public void ganarVida() {
        if (vidas < 5) {
            vidas++;
        }
    }

    @Override
    public void actualizar() {
        if (recargaActual > 0) recargaActual--;
        
        if (escudoActivo) {
            tiempoEscudo--;
            if (tiempoEscudo <= 0) escudoActivo = false;
        }
        
        if (disparoDoble) {
            tiempoDisparoDoble--;
            if (tiempoDisparoDoble <= 0) disparoDoble = false;
        }
    }

    @Override
    public void dibujar(Graphics2D g) {
        g.setColor(escudoActivo ? Color.CYAN : Color.WHITE);
        g.fillPolygon(new int[]{(int) getX(), (int) getX() + getAncho(), (int) getX() + (getAncho() / 2)},
                new int[]{(int) getY() + getAlto(), (int) getY() + getAlto(), (int) getY()}, 3);
    }

    @Override
    public void alColisionar(Colisionable otro) {
    }

    public int getVidas() { return vidas; }
    public boolean isEscudoActivo() { return escudoActivo; }
    public void setEscudoActivo(boolean escudo) { 
        this.escudoActivo = escudo; 
        this.tiempoEscudo = 500;
    }
    public void setDisparoDoble(boolean doble) { 
        this.disparoDoble = doble; 
        this.tiempoDisparoDoble = 500; 
    }
}
