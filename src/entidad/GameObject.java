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
    protected boolean colisionActivada = false;
    protected Rectangle areaSolida;
    protected int mapa;

    public abstract void draw(java.awt.Graphics2D g2, int camaraX, int camaraY);

    public boolean getColision(){
        return this.colisionActivada;
    }
    public void setColision(boolean colisionActivada){
        this.colisionActivada = colisionActivada;
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
