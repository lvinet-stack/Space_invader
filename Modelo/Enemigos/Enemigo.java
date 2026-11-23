
package Modelo.Enemigos;
import Contratos.Colisionable;
import Contratos.Disparador;
import Contratos.EstrategiaMovimiento;
import Contratos.Puntuable;
import Modelo.EntidadJuego;
import Modelo.Jugador;
import Modelo.Proyectil;
import java.awt.Color;
import java.awt.Graphics2D;
import java.util.List;

public abstract class Enemigo extends EntidadJuego implements Disparador, Puntuable {
    public static final EstrategiaMovimiento HORIZONTAL = e -> e.setX(e.getX() + 1.0);

    public static final EstrategiaMovimiento DESCENSO = e -> e.setY(e.getY() + 1.0);

    public static final EstrategiaMovimiento ZIGZAG = e -> {
        e.setY(e.getY() + 2.2);
        e.setX(e.getX() + Math.sin(e.getY() / 18.0) * 3.0);
    };

    public static EstrategiaMovimiento persecucion(Jugador objetivo) {
        return e -> {
            e.setY(e.getY() + 2.0);
            e.setX(e.getX() + Math.signum(objetivo.getX() - e.getX()) * 1.6);
        };
    }

    private final int puntos;
    private int resistencia;
    private EstrategiaMovimiento estrategiaMovimiento;

    protected Enemigo(double x, double y, int ancho, int alto, int puntos, int resistencia) {
        super(x, y, ancho, alto);
        if (puntos < 0 || resistencia <= 0) {
            throw new IllegalArgumentException("Puntos y resistencia no válidos");
        }
        this.puntos = puntos;
        this.resistencia = resistencia;
    }

    @Override
    public void actualizar() {
        if (estrategiaMovimiento != null) {
            estrategiaMovimiento.mover(this);
        }
    }

    public void recibirDanio(int danio) {
        if (danio < 0) {
            throw new IllegalArgumentException("El daño no puede ser negativo");
        }
        resistencia = Math.max(0, resistencia - danio);
        if (resistencia == 0) {
            destruir();
        }
    }

    public final void setEstrategiaMovimiento(EstrategiaMovimiento estrategia) {
        if (estrategia == null) {
            throw new IllegalArgumentException("La estrategia no puede ser nula");
        }
        this.estrategiaMovimiento = estrategia;
    }

    @Override
    public int getPuntos() {
        return puntos;
    }

    public int getResistencia() {
        return resistencia;
    }

    @Override
    public void alColisionar(Colisionable otro) {
    }

    @Override
    public abstract List<Proyectil> disparar();

    protected static void dibujarSprite(Graphics2D g, String[] filas,
                                        int x, int y, int celda, Color color) {
        g.setColor(color);
        for (int f = 0; f < filas.length; f++) {
            for (int c = 0; c < filas[f].length(); c++) {
                if (filas[f].charAt(c) == '#') {
                    g.fillRect(x + c * celda, y + f * celda, celda, celda);
                }
            }
        }
    }
}
