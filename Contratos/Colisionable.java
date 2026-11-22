
package Contratos;

import java.awt.Rectangle;
public interface Colisionable {
    Rectangle getLimites();
    
    void alColisionar(Colisionable otro);
}