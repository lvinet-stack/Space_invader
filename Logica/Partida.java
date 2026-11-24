package Logica;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import Modelo.Barrera;
import Contratos.Colisionable;
import Modelo.EntidadJuego;
import Modelo.Jugador;
import Modelo.PowerUp;
import Modelo.EfectosPowerUp;
import Modelo.Proyectil;
import Modelo.ProyectilEnemigo;
import Modelo.ProyectilJugador;
import Modelo.Enemigos.Enemigo;
import Modelo.Enemigos.EnemigoKamikaze;


public class Partida {
    private static final double PROBABILIDAD_MEJORA = 0.20;
    private final List<EntidadJuego> entidades = new ArrayList<>();
    private final Jugador jugador;
    private final GestorNiveles gestorNiveles;
    private final Random azar = new Random();
    private final int ancho;
    private final int alto;
    
    private int puntaje;
    private int nivel;
    private boolean terminada;
    private boolean victoria;
    private boolean pausada;
    
    public Partida(int ancho, int alto){
        this.ancho = ancho;
        this.alto = alto;
        this.jugador = new Jugador(0, alto - 60);
        this.jugador.setX((ancho - jugador.getAncho()) / 2.0);
        this.entidades.add(jugador);
        this.gestorNiveles = new GestorNiveles(this);
        this.gestorNiveles.cargarNivel(1);
    }
    
    public void actualizar(){
        if(terminada || pausada){
            return;
        }
        gestorNiveles.actualizarFormacion();
        
        entidades.forEach(EntidadJuego::actualizar);
        recolectarDisparosEnemigos();
        detectarColisiones();
        
        Predicate<EntidadJuego> fueraDePantalla = 
                e -> e.getY() > alto || e.getY() + e.getAlto() < 0;
        
        entidades.stream().filter(fueraDePantalla).forEach(EntidadJuego::destruir);
        
        entidades.removeIf(e -> !e.isActiva());
        
        evaluarEstado();
        
    }
    
    private void recolectarDisparosEnemigos(){
        de(Enemigo.class).stream().filter(Enemigo::isActiva).forEach(e -> e.disparar().forEach(this::agregarEntidad));
        
    }
    
   public void detectarColisiones() {
    List<Enemigo> enemigos = de(Enemigo.class);
    List<Barrera> barreras = de(Barrera.class);

    for (ProyectilJugador pJ : de(ProyectilJugador.class)) {
        for (Enemigo en : enemigos) {
            if (pJ.isActiva() && en.isActiva() && chocan(pJ, en)) {
                notificar(pJ, en);
                en.recibirDanio(pJ.getDanio());
                if (!en.isActiva()) {
                    enemigoDestruido(en);
                }
            }
        }
    }

    for (Proyectil pr : de(Proyectil.class)) {
        for (Barrera bar : barreras) {
            if (pr.isActiva() && bar.isActiva() && chocan(pr, bar)) {
                notificar(pr, bar);
                bar.recibirDanio(pr.getDanio());
            }
        }
    }

    for (ProyectilEnemigo pE : de(ProyectilEnemigo.class)) {
        if (pE.isActiva() && chocan(pE, jugador)) {
            notificar(pE, jugador);
            jugador.perderVida();
        }
    }

    for (EnemigoKamikaze eK : de(EnemigoKamikaze.class)) {
        if (eK.isActiva() && chocan(eK, jugador)) {
            notificar(eK, jugador);
            jugador.perderVida();
            eK.destruir();
        }
    }

    for (PowerUp pU : de(PowerUp.class)) {
        if (pU.isActiva() && chocan(pU, jugador)) {
            notificar(pU, jugador);
            pU.aplicarEfecto(jugador);
        }   
    }
}

    private boolean chocan(Colisionable a, Colisionable b){
        return a.getLimites().intersects(b.getLimites());
    }

    private void notificar(Colisionable a, Colisionable b){
        a.alColisionar(b);
        b.alColisionar(a);
    }

    private void enemigoDestruido(Enemigo enemigo){
        sumarPuntos(enemigo.getPuntos());
        if(azar.nextDouble() < PROBABILIDAD_MEJORA){
            agregarEntidad(EfectosPowerUp.crearAleatorio(enemigo.getX(), enemigo.getY()));
        }
    }

    private void evaluarEstado(){
        Predicate<Enemigo> esKamikaze = EnemigoKamikaze.class::isInstance;
        boolean enemigoLlegoAbajo = de(Enemigo.class).stream().filter(esKamikaze.negate()).anyMatch(e -> e.getLimites().getMaxY() >= jugador.getY());
        if(jugador.getVidas() <= 0 || enemigoLlegoAbajo){
            terminar(false);
            return;
        }
        revisarProgreso();
    }

    private void revisarProgreso(){
        if(!de(Enemigo.class).isEmpty()){
            return;
        }
        if(nivel < GestorNiveles.NIVEL_FINAL){
            sumarPuntos(gestorNiveles.calcularBonificacion(nivel));
            gestorNiveles.siguienteNivel();
        } else if (!gestorNiveles.isJefeCreado()){
            agregarEntidad(gestorNiveles.crearJefe());
        } else {
            sumarPuntos(gestorNiveles.calcularBonificacion(nivel));
            terminar(true);
        }
    }

    private void terminar(boolean gano){
        terminada = true;
        victoria = gano;
    }


    public void moverJugadorIzquierda(){
        if (!terminada && !pausada){
            jugador.moverIzquierda();
            mantenerJugadorEnPantalla();
        }
    }  

    public void moverJugadorDerecha(){
        if(!terminada && !pausada){
            jugador.moverDerecha();
            mantenerJugadorEnPantalla();
        }
    }

    public void jugadorDispara(){
        if(!terminada && !pausada){
            jugador.disparar().forEach(this::agregarEntidad);
        }
    }

    public void pausar(){
        pausada = !pausada;
    }

    private void mantenerJugadorEnPantalla(){
        double maximo = ancho - jugador.getAncho();
        jugador.setX(Math.max(0, Math.min(maximo, jugador.getX())));
    }



    public void agregarEntidad(EntidadJuego entidad){
        if(entidad == null){
            throw new IllegalArgumentException("La entidad no puede ser nula");
        }
        entidades.add(entidad);
    }

    public void sumarPuntos(int puntos){
        if(puntos < 0){
            throw new IllegalArgumentException("El puntaje no puede bajar");
        }
        puntaje += puntos; 
    }

    public List<EntidadJuego> getEntidades(){
        return Collections.unmodifiableList(entidades);
    }

    private <T> List<T> de(Class<T> tipo){
        return entidades.stream().filter(tipo::isInstance).map(tipo::cast).collect(Collectors.toList());
    }

    void setNivel(int nivel){
        this.nivel = nivel;
    }

    void limpiarEntidades(Predicate<EntidadJuego> filtro){
        entidades.removeIf(filtro);
    }

    public Jugador getJugador() {
        return jugador;
    }

    public int getPuntaje() {
        return puntaje;
    }

    public int getNivel() {
        return nivel;
    }
    
    public GestorNiveles getGestorNiveles() {
        return gestorNiveles;
    }
    public boolean isTerminada() {
        return terminada;
    }

    public boolean isVictoria() {
        return victoria;
    }

    public boolean isPausada() {
        return pausada;
    }

    public int getAncho() {
        return ancho;
    }

    public int getAlto() {
        return alto;
    }
}


