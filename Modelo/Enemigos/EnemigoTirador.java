package Modelo.Enemigos;

import Modelo.Proyectil;
import Modelo.ProyectilEnemigo;
import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

public class EnemigoTirador extends Enemigo {
    private int frecuencia;
    private int contador;

    public EnemigoTirador(double x, double y, int frecuencia) {
        super(x, y, 30, 30, 20, 1);
        this.frecuencia = frecuencia;
        this.contador = 0;
    }

    @Override
    public void actualizar() {
        super.actualizar();
        if (isActiva()) {
            contador++;
        }
    }

    @Override
    public List<Proyectil> disparar() {
        List<Proyectil> disparos = new ArrayList<>();
        if (isActiva() && contador % frecuencia == 0) {
            double xCentro = getX() + (getAncho() / 2.0) - 3;
            double ySalida = getY() + getAlto();
            disparos.add(new ProyectilEnemigo(xCentro, ySalida, 4, 1));
        }
        return disparos;
    }

    @Override
    public void dibujar(Graphics2D g) {
        g.setColor(Color.ORANGE);
        g.fillRect((int) getX(), (int) getY(), getAncho(), getAlto());
    }
}
