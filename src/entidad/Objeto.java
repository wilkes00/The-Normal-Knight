package entidad;

import Main.GamePanel;
import java.awt.Graphics2D;
import java.awt.Rectangle;
/**
 * Representa un objeto estatico dentro del juego
 */
public class Objeto extends GameObject{
    GamePanel gP;
    protected String nombre;
    private boolean tieneLlave = false;

    public Objeto(GamePanel gP){
        this.gP = gP;
        this.areaSolida = new Rectangle(8, 16, 32, 32);
    }
    /**
     * Dibuja el objeto en la pantalla considerando la posición de la cámara.
     * @param g2 el contexto gráfico donde se dibuja el objeto
     * @param camaraX la posición X de la cámara
     * @param camaraY la posición Y de la cámara
     */
    @Override
    public void draw(Graphics2D g2, int camaraX, int camaraY) {
        
        int pantallaX = mundoX - camaraX;
        int pantallaY = mundoY - camaraY;

        if (this.imagen == null) {
            System.err.println("Objeto.draw: imagen null en objeto en (" + this.mundoX + "," + this.mundoY + ")");
            return;
        }
        if (mundoX + imagen.getWidth() > camaraX && 
            mundoX - imagen.getWidth() < camaraX + gP.getAnchoPantalla() &&
            mundoY + imagen.getHeight() > camaraY &&
            mundoY - imagen.getHeight() < camaraY + gP.getAltoPantalla()) { 
            
            // Dibuja el sprite del objeto con las dimensiones a escala 
            g2.drawImage(imagen, pantallaX, pantallaY, gP.getTamTile(), gP.getTamTile(), null);
        }
    }
}
