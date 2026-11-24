

package Logica;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import Contratos.EstrategiaMovimiento;
import Modelo.Barrera;
import Modelo.EntidadJuego;
import Modelo.PowerUp;
import Modelo.Proyectil;
import Modelo.ProyectilEnemigo;
import Modelo.Enemigos.Enemigo;
import Modelo.Enemigos.EnemigoBasico;
import Modelo.Enemigos.EnemigoKamikaze;
import Modelo.Enemigos.EnemigoTirador;
import java.awt.Color;

public class GestorNiveles {
    public static final int NIVEL_FINAL = 3;
    
    private static final int SEPARACION_X = 50;
    private static final int SEPARACION_Y = 40;
    private static final int MARGEN = 10;
    private static final int PASO_DESCENSO = 10;
    private static final int RESISTENCIA_JEFE = 40;
    private static final int PUNTOS_JEFE = 40;
    
    private record Fila(Supplier<Enemigo> fabrica, int columnas, boolean enFormacion){}
    
    private final Partida partida;
    
    private final List<Enemigo> formacion = new ArrayList<>();
    private final Function<Integer, Integer> bonificacion = nivel -> 500 * nivel;
            
    private int nivelActual;
    private boolean jefeCreado;
    private double direccionFormacion = 1;
    private double velocidadFormacion;
    
    public GestorNiveles(Partida partida){
        this.partida = partida;
    }
    
    public void cargarNivel(int nivel){
        nivelActual = nivel;
        partida.setNivel(nivel);
        jefeCreado = false;
        formacion.clear();
        direccionFormacion = 1;
        velocidadFormacion = 0.8 + 0.4 * nivel;
        
        Predicate<EntidadJuego> esBarrera = Barrera.class::isInstance;
        Predicate<EntidadJuego> esProyectil = Proyectil.class::isInstance;
        Predicate<EntidadJuego> esMejora = PowerUp.class::isInstance;
        partida.limpiarEntidades(esBarrera.or(esProyectil).or(esMejora));
        
        crearBarreras();
        crearOleada(filasDelNivel(nivel));
    }
    
    public void siguienteNivel(){
    if(nivelActual < NIVEL_FINAL){
        cargarNivel(nivelActual + 1);
    }
    }
    
    private List<Fila> filasDelNivel(int nivel){
        int frecuencia = 150 - 30 * nivel;
        Supplier<Enemigo> basico = () -> new EnemigoBasico(0, 0);
        Supplier<Enemigo> tirador = () -> new EnemigoTirador(0, 0, frecuencia);
        Supplier<Enemigo> kamikaze = () -> {
        EnemigoKamikaze k = new EnemigoKamikaze(0, 0);
            k.setEstrategiaMovimiento(nivel == 2
                    ? Enemigo.ZIGZAG
                    : Enemigo.persecucion(partida.getJugador()));
            return k;
        };
        
        return switch(nivel){
            case 1 -> List.of(
                new Fila(tirador, 6, true),
                new Fila(basico, 6, true),
                new Fila(basico, 6, true)
            );
            
            case 2 -> List.of(
                new Fila(tirador, 7, true),
                new Fila(tirador, 7, true),
                new Fila(basico, 7, true),
                new Fila(kamikaze, 3, false)
            );
                
            default -> List.of(
                new Fila(tirador, 8, true),
                new Fila(tirador, 8, true),
                new Fila(basico, 8, true),    
                new Fila(kamikaze, 4, false)
            );
        };

    }
    
    
    private void crearOleada(List<Fila> filas){
        
        EstrategiaMovimiento enBloque = e -> e.setX(e.getX()+ direccionFormacion * velocidadFormacion);
        
        for(int f = 0; f < filas.size(); f++){
            Fila fila = filas.get(f);
            double inicioX = (partida.getAncho() - fila.columnas() * SEPARACION_X) /2.0;
            for(int c = 0; c < fila.columnas(); c++){
                Enemigo enemigo = fila.fabrica().get();
                enemigo.setX(inicioX + c * SEPARACION_X);
                enemigo.setY(50 + f * SEPARACION_Y);
                if(fila.enFormacion()){
                    enemigo.setEstrategiaMovimiento(enBloque);
                    formacion.add(enemigo);
                }
                partida.agregarEntidad(enemigo);
            }
          
        }
    }
    
    private void crearBarreras(){
        for (int i = 0; i < 4; i++){
            double x = partida.getAncho() * (i+1)/ 5.0 - 30;
            partida.agregarEntidad(new Barrera(x, partida.getAlto() - 150));
        }
    }
    
    void actualizarFormacion(){
        formacion.removeIf(e -> !e.isActiva());
        boolean tocaBorde = formacion.stream().anyMatch(e -> (direccionFormacion > 0 && e.getX() 
                + e.getAncho() >= partida.getAncho() - MARGEN)
                || (direccionFormacion < 0 && e.getX() <= MARGEN));
        if(tocaBorde){
            direccionFormacion = -direccionFormacion;
            formacion.forEach(e -> e.setY(e.getY()+ PASO_DESCENSO));
        }
    }
        
        public int calcularBonificacion(int nivel){
            return bonificacion.apply(nivel);
        }
        
        public boolean isJefeCreado(){
            return jefeCreado;
        }
        
        public Enemigo crearJefe(){
            final int anchoPantalla = partida.getAncho();
            
            Enemigo jefe = new Enemigo(anchoPantalla / 2.0 - 60, 40, 120, 60, PUNTOS_JEFE, RESISTENCIA_JEFE){
                private int fase = 1;
                private int contador = 0;
                private double direccion = 1;
                
                @Override
                public void actualizar(){
                    double vidaRestante = (double) getResistencia() / RESISTENCIA_JEFE;
                    fase = vidaRestante > 0.66 ? 1 : vidaRestante > 0.33 ? 2 : 3;
                    
                    double nuevaX = getX() + direccion * 1.5 * fase;
                    if(nuevaX < 0 || nuevaX + getAncho() > anchoPantalla){
                        direccion = -direccion;
                    } else {
                        setX(nuevaX);
                    }
                    contador++;
                }
                
                @Override
                public List<Proyectil> disparar(){
                    List<Proyectil> disparos = new ArrayList<>();
                    
                    int cadencia = 80 - 15 * fase;
                    if( contador % cadencia != 0){
                        return disparos;
                    }
                    double centro = getX() + getAncho() / 2.0;
                    double salidaY = getY() + getAlto();
                    
                    for(int i = -(fase - 1); i<= fase - 1; i++){
                        disparos.add(new ProyectilEnemigo(centro + i * 30, salidaY, 3 + fase, 1));
                    }
                    return disparos;
                }
                
                @Override
                public void dibujar(Graphics2D g) {
                    int x = (int) getX();
                    int y = (int) getY();

                    String[] jefe = {
                        "        ####        ",
                        "      ########      ",
                        "    ############    ",
                        "  ################  ",
                        "####################",
                        "####  ########  ####",
                        "###    ######    ###",
                        "  ##  ########  ##  ",
                        "    ##  ####  ##    ",
                        "      ##    ##      "
                    };

                    dibujarSprite(g, jefe, x + 10, y + 5, 5, new Color(200, 40, 60));

                    // Barra de vida
                    double vidaRestante = (double) getResistencia() / RESISTENCIA_JEFE;

                    g.setColor(Color.GRAY);
                    g.fillRect(x, y - 10, getAncho(), 5);

                    g.setColor(Color.GREEN);
                    g.fillRect(x, y - 10, (int) (getAncho() * vidaRestante), 5);
                }
            };
            jefeCreado = true;
            return jefe;
    }
}




