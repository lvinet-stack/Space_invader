package Modelo;

import Contratos.Colisionable;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public abstract class EntidadJuego implements Colisionable{
    private double x;
    private double y;
    private final int ancho;
    private final int alto;
    private boolean activa = true;
    
    protected EntidadJuego(double x, double y, int ancho, int alto){
        if(ancho <= 0 || alto <= 0){
            throw new IllegalArgumentException("El tamaño d ela entidad debe ser mayor que 0");
        }
        this.x = x;
        this.y = y;
        this.ancho = ancho;
        this.alto = alto;
        
    }
    
    public abstract void actualizar();
    
    public abstract void dibujar(Graphics2D g);
    
    @Override
    public Rectangle getLimites(){
        return new Rectangle((int) x, (int) y, ancho, alto);
    }
    
    public void destruir(){
        activa = false;
    }
    
    public boolean isActiva(){
        return activa;
    }
    
    public double getX(){
        return x;
        
    }
    
    public double getY(){
        return y;
    }
    
    public void setX(double x){
        this.x = x;
    }
    
    public void setY(double y){
        this.y = y;
    }
    
    public int getAncho(){
        return ancho;
    }
    
    public int getAlto(){
        return alto;
    }
}
