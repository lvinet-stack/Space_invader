
package Contratos;

import Modelo.Enemigos.Enemigo;
@FunctionalInterface
public interface EstrategiaMovimiento {
    void mover(Enemigo enemigo);
}
