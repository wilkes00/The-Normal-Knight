package entidad;

import Main.GamePanel;
import java.awt.image.BufferedImage;
/**
 * Clase base abstracta para todas las entidades del juego que se mueven, como el jugador,
 * enemigos y NPCs.
 * Contiene propiedades comunes como la posición en el mundo (heredado de GameObject), 
 * velocidad, dirección, sprites de animación y gestión de colisiones.
 */
public abstract class Entidad extends GameObject {
    GamePanel gP;
    protected int velocidad;
    protected BufferedImage arriba1, arriba2, abajo1, abajo2, derecha1, derecha2, izquierda1, izquierda2;
    protected String direccion;
    protected int contadorSprites = 0;
    protected int numeroSprite = 1;
    protected int cambiaSprite = 10;
 
    /**
     * Constructor de la clase Entidad.
     * @param gP referencia al GamePanel principal del juego.
     */
    public Entidad(GamePanel gP){
        this.gP = gP;
    }

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
     * el comportamiento específico de cada tipo de entidad en caso de necesitarlo.
     */
    public void update(){}
    
    public int getVelocidad() {
        return this.velocidad;
    }
    
    public String getDireccion() {
        return this.direccion;
    }
}
