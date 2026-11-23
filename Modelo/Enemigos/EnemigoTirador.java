package Modelo.Enemigos;

import Modelo.Proyectil;
import Modelo.ProyectilEnemigo;
import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

public class EnemigoTirador extends Enemigo {
    private int frecuenciaDisparo;
    private int tiempoDesdeUltimoDisparo;

    public EnemigoTirador(double x, double y, int frecuencia) {
        super(x, y, 30, 30, 20, 1);
        this.frecuenciaDisparo = frecuencia;
        this.tiempoDesdeUltimoDisparo = 0;
    }

    @Override
    public void actualizar() {
        super.actualizar();
        if (isActiva()) tiempoDesdeUltimoDisparo++;
    }

    @Override
    public List<Proyectil> disparar() {
        List<Proyectil> disparos = new ArrayList<>();
        if (isActiva() && tiempoDesdeUltimoDisparo >= frecuenciaDisparo) {
            double xCentro = getX() + (getAncho() / 2.0) - 3;
            double ySalida = getY() + getAlto();
            disparos.add(new ProyectilEnemigo(xCentro, ySalida, 4, 1)); 
            tiempoDesdeUltimoDisparo = 0;
        }
        return disparos;
    }

    @Override
    public void dibujar(Graphics2D g) {
        String[] enemigo = {
            "### ###",
            "#######",
            " ## ##",
            "### ###",
            " ##### ",
            "  ###   "
        };

        dibujarSprite(g, enemigo, (int) getX(), (int) getY(), 5, Color.ORANGE);
    }
}
