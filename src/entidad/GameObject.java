package entidad;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;

/**
 * Clase abstracta base para todos los objetos del juego.
 * Contiene propiedades comunes como la posición en el mundo,
 * la imagen del objeto y la gestión de colisiones.
 */
public abstract class GameObject {
    BufferedImage imagen;
    protected int mundoX, mundoY;
    protected boolean colision = false;
    protected boolean colisionActivada = false;
    //rectangulo para colisiones predeterminado para todos los objetos
    protected Rectangle areaSolida = new Rectangle(0,0,32,32);
    protected int mapa;

    public abstract void draw(java.awt.Graphics2D g2, int camaraX, int camaraY);

    
    public boolean getColision(){
        return this.colision;
    }
    public void setColision(boolean colision){
        this.colision = colision;
    }
    public boolean  getColisionActivada(){
        return this.colisionActivada;
    }
    /**
     * Establece el estado de colisión de la entidad.
     * @param valor true si la colisión está activada, false si no.
     */
    public void setColisionActivada(boolean valor){
        this.colisionActivada = valor;
    }
    public int getMundoX(){
        return this.mundoX;
    }
    public int getMundoY(){
        return this.mundoY;
    }
    public void setMundoX(int mundoX){
        this.mundoX = mundoX;
    }
    public void setMundoY(int mundoY){
        this.mundoY = mundoY;
    }
    public Rectangle getAreaSolida(){
        return this.areaSolida;
    }
    public int getAreaSolidaX(){
        return this.areaSolida.x;
    }
    public int getAreaSolidaY(){
        return this.areaSolida.y;
    }
    public void setAreaSolidaX(int x){
        this.areaSolida.x = x;
    }
    public void setAreaSolidaY(int y){
        this.areaSolida.y = y;
    }
    public int getAreaSolidaAncho(){
        return this.areaSolida.width;
    }
    public int getAreaSolidaAlto(){
        return this.areaSolida.height;
    }
    public BufferedImage getImagen(){
        return this.imagen;
    }
    public void setImagen(BufferedImage imagen){
        this.imagen = imagen;
    }
    public int getMapa() {
        return this.mapa;
    }

    public void setMapa(int mapa) {
        this.mapa = mapa;
    }
}
