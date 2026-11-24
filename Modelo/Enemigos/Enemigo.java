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

    /** Estrategia: desciende lentamente. */
    public static final EstrategiaMovimiento DESCENSO = e -> e.setY(e.getY() + 1.0);

    /** Estrategia: desciende oscilando de lado a lado. */
    public static final EstrategiaMovimiento ZIGZAG = e -> {
        e.setY(e.getY() + 2.2);
        e.setX(e.getX() + Math.sin(e.getY() / 18.0) * 3.0);
    };

    /**
     * Fábrica de estrategia: desciende acercándose horizontalmente al jugador.
     *
     * @param objetivo jugador al que se persigue
     */
    public static EstrategiaMovimiento persecucion(Jugador objetivo) {
        return e -> {
            e.setY(e.getY() + 2.0);
            e.setX(e.getX() + Math.signum(objetivo.getX() - e.getX()) * 1.6);
        };
    }

    private final int puntos;
    private int resistencia;
    private EstrategiaMovimiento estrategiaMovimiento;

    /**
     * @param puntos      puntos que entrega al ser destruido (no negativos)
     * @param resistencia impactos que soporta (positiva)
     */
    protected Enemigo(double x, double y, int ancho, int alto, int puntos, int resistencia) {
        super(x, y, ancho, alto);
        if (puntos < 0 || resistencia <= 0) {
            throw new IllegalArgumentException("Puntos y resistencia no válidos");
        }
        this.puntos = puntos;
        this.resistencia = resistencia;
    }

    /** Se mueve según su estrategia actual. */
    @Override
    public void actualizar() {
        if (estrategiaMovimiento != null) {
            estrategiaMovimiento.mover(this);
        }
    }

    /**
     * Resta resistencia; al llegar a cero el enemigo se destruye.
     *
     * @throws IllegalArgumentException si el daño es negativo
     */
    public void recibirDanio(int danio) {
        if (danio < 0) {
            throw new IllegalArgumentException("El daño no puede ser negativo");
        }
        resistencia = Math.max(0, resistencia - danio);
        if (resistencia == 0) {
            destruir();
        }
    }

    /** Cambia la forma de moverse (incluso en plena partida). */
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
        // El daño lo aplica Partida mediante recibirDanio().
    }

    /** Cada tipo de enemigo dispara a su manera (lista vacía si no dispara ahora). */
    @Override
    public abstract List<Proyectil> disparar();

    /** Dibuja un sprite retro: cada '#' es un cuadrado de lado "celda". */
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
