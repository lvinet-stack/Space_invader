package Modelo;

import Contratos.Disparador;
import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

public class Jugador extends EntidadJuego implements Disparador {
    private int vidas;
    private double velocidad;
    private int recarga;
    private int tiempoDesdeUltimoDisparo;
    
    private boolean escudoActivo;
    private int tiempoEscudo;
    private boolean disparoDoble;
    private int tiempoDisparoDoble;

    public Jugador(double x, double y) {
        super(x, y, 40, 30);
        this.vidas = 3;
        this.velocidad = 6.0;
        this.recarga = 15;
        this.tiempoDesdeUltimoDisparo = 0;
        this.escudoActivo = false;
        this.disparoDoble = false;
    }

    public void moverIzquierda() { setX(getX() - velocidad); }
    public void moverDerecha() { setX(getX() + velocidad); }

    @Override
    public List<Proyectil> disparar() {
        List<Proyectil> disparos = new ArrayList<>();
        if (tiempoDesdeUltimoDisparo >= recarga) {
            double xCentro = getX() + (getAncho() / 2.0) - 3;
            double ySalida = getY();
            
            disparos.add(new ProyectilJugador(xCentro, ySalida));
            if (disparoDoble) {
                disparos.add(new ProyectilJugador(getX() + 5, ySalida));
                disparos.add(new ProyectilJugador(getX() + getAncho() - 11, ySalida));
            }
            tiempoDesdeUltimoDisparo = 0;
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
        if (vidas < 5) vidas++;
    }

    @Override
    public void actualizar() {
        if (tiempoDesdeUltimoDisparo < recarga) tiempoDesdeUltimoDisparo++;
        
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
        String[] nave = {
            "    ##    ",
            "   ####   ",
            "  ######  ",
            " ######## ",
            "####  ####",
            "###    ###"
        };

        g.setColor(escudoActivo ? Color.CYAN : Color.GREEN);

        int celda = 4;

        for (int fila = 0; fila < nave.length; fila++) {
            for (int columna = 0; columna < nave[fila].length(); columna++) {
                if (nave[fila].charAt(columna) == '#') {
                    g.fillRect(
                        (int) getX() + columna * celda,
                        (int) getY() + fila * celda,
                        celda,
                        celda
                    );
                }
            }
        }
    }

    @Override
    public void alColisionar(Contratos.Colisionable otro) { }

    public int getVidas() { return vidas; }
    public void setEscudoActivo(boolean activo) { 
        this.escudoActivo = activo; 
        this.tiempoEscudo = 400; //6.6 segundos
    }
    public void setDisparoDoble(boolean doble) { 
        this.disparoDoble = doble; 
        this.tiempoDisparoDoble = 400; 
    }
}
