package entidad;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
/**
 * Clase base abstracta para todas las entidades del juego, como el jugador,
 * enemigos e ítems.
 * Contiene propiedades comunes como la posición en el mundo, velocidad, dirección,
 * sprites de animación y gestión de colisiones.
 *
 */
public abstract class Entidad {
    protected int mundoX, mundoY, velocidad;
    protected BufferedImage arriba1, arriba2, abajo1, abajo2, derecha1, derecha2, izquierda1, izquierda2;
    protected String direccion;
    protected int contadorSprites = 0;
    protected int numeroSprite = 1;
    protected int cambiaSprite = 10;
    
    //para colisones
    protected Rectangle areaSolida;
    protected boolean colisionActivada = false;

    /**
     * Establece el estado de colisión de la entidad.
     *
     * @param valor true si la colisión está activada, false si no.
     */
    public void setColisionActivada(boolean valor){
        this.colisionActivada = valor;
    }
    /**
     * Actualiza el estado de la entidad.
     * Este método debe ser implementado por las subclases para definir
     * el comportamiento específico de cada tipo de entidad.
     */
    public abstract void update();
}
