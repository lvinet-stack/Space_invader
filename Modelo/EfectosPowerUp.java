package Modelo;

import Contratos.EfectoPowerUp;
import java.awt.Color;
import java.util.Random;

public class EfectosPowerUp {
    private static final Random random = new Random();

    public static final EfectoPowerUp ESCUDO = jugador -> jugador.setEscudoActivo(true);
    public static final EfectoPowerUp DISPARO_DOBLE = jugador -> jugador.setDisparoDoble(true);
    public static final EfectoPowerUp VIDA_EXTRA = jugador -> jugador.ganarVida();

    public static PowerUp crearAleatorio(double x, double y) {
        int tipo = random.nextInt(3);
        return switch (tipo) {
            case 0 -> new PowerUp(x, y, ESCUDO, Color.CYAN);
            case 1 -> new PowerUp(x, y, DISPARO_DOBLE, Color.MAGENTA);
            default -> new PowerUp(x, y, VIDA_EXTRA, Color.GREEN);
        };
    }
}
