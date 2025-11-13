package entidad;

import Main.GamePanel;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public class Objeto extends GameObject{
    GamePanel gP;

    public Objeto(GamePanel gP){
        this.gP = gP;
        this.areaSolida = new Rectangle(8, 16, 32, 32);
    }

    @Override
    public void draw(Graphics2D g2, int camaraX, int camaraY) {
        
        int pantallaX = mundoX - camaraX;
        int pantallaY = mundoY - camaraY;

        if (this.imagen == null) {
            // Opción: dibujar marcador de depuración o simplemente devolver
            // g2.setColor(Color.magenta);
            // g2.fillRect(mundoX - camaraX, mundoY - camaraY, 16, 16);
            System.err.println("Objeto.draw: imagen null en objeto en (" + this.mundoX + "," + this.mundoY + ")");
            return;
        }
        if (mundoX + imagen.getWidth() > camaraX && 
            mundoX - imagen.getWidth() < camaraX + gP.getAnchoPantalla() &&
            mundoY + imagen.getHeight() > camaraY &&
            mundoY - imagen.getHeight() < camaraY + gP.getAltoPantalla()) { 
            
            // Dibuja el sprite del objeto 
            g2.drawImage(imagen, pantallaX, pantallaY, gP.getTamTile(), gP.getTamTile(), null);
        }
    }
}
