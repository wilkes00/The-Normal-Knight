package entidad;
import Main.GamePanel;
import java.awt.Graphics2D;
/**
 * Representa a un jefe enemigo en el juego.
 * Extiende de Entidad e implementa la interfaz Llave.
 */
public class Jefe extends Entidad implements Llave {

    public Jefe(GamePanel gP) {
        super(gP);
        this.colision = true;
        this.areaSolida = new java.awt.Rectangle(12, 16, 22, 22);
        this.direccion = "abajo";
        this.velocidad = 1;
        this.vida = 6; // Jefe tiene 6 puntos de vida
        this.vidaMax = 6;
        this.getSprites();
    }
    /**
     * Carga los sprites especificos del jefe.
     */
    public void getSprites(){
        // Lógica para cargar los sprites del jefe
    }
    /**
     * Define la logica de accion y movimiento del jefe.
     * Debe implementarse en las subclases de Entidad,
     * con excepcion de la subclase Jugador.
     */
    @Override
    public void update() {
        // Lógica de actualización del jefe
    }
    @Override
    public void draw(Graphics2D g2, int camaraX, int camaraY){
        // Lógica de dibujo del jefe
    }
    @Override
    public void setLlave(boolean tieneLlave){
        // Lógica para establecer la llave
    }
    @Override
    public boolean getLlave(){
        // Lógica para obtener la llave
        return true;
    }
}
