
package Contratos;

import Modelo.Jugador;

@FunctionalInterface
public interface EfectoPowerUp {
    void aplicar(Jugador jugador);
}
