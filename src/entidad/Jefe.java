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
    }

    @Override
    public void update() {
        // Lógica de actualización del jefe
    }
    @Override
    public void draw(Graphics2D g2, int camaraX, int camaraY){
        // Lógica de dibujo del jefe
    }
    @Override
    public void setLlave(){
        // Lógica para establecer la llave
    }
    @Override
    public boolean getLlave(){
        // Lógica para obtener la llave
        return true;
    }
}
