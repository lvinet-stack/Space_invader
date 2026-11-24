package Modelo.Enemigos;

import Modelo.Proyectil;
import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

public class EnemigoKamikaze extends Enemigo {

    public EnemigoKamikaze(double x, double y) {
        super(x, y, 25, 25, 30, 1);
        setEstrategiaMovimiento(DESCENSO);
    }

    @Override
    public List<Proyectil> disparar() { return new ArrayList<>(); }

    @Override
    public void dibujar(Graphics2D g) {
        String[] enemigo = {
            "#######",
            " ## ## ",
            " ## ##",
            " ## ## ",
            " ##### ",
            "  ###   "
        };

        dibujarSprite(g, enemigo, (int) getX(), (int) getY(), 5, Color.RED);
    }
}
