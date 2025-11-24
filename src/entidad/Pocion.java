package entidad;

import Main.GamePanel;

import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Pocion extends Objeto {
    public Pocion(GamePanel gP, int mapa, int x, int y) {
        super(gP);
        this.mundoX = x * gP.getTamTile();
        this.mundoY = y * gP.getTamTile();
        this.setMapa(mapa);

        this.nombre = "Pocion";
        this.colision = false;

        // Ajuste de hitbox específico para pociones (son pequeñas)
        this.areaSolida.x = 4;
        this.areaSolida.y = 4;
        this.areaSolida.width = 24;
        this.areaSolida.height = 24;
        
        cargarImagen();
    }

    private void cargarImagen() {
        try {
            int size = 16;
            BufferedImage spritesheet = ImageIO.read(getClass().getResourceAsStream("/resources/tiles/allTiles.png"));
            //pocion verde
            setImagen(spritesheet.getSubimage(0 * size, 8 * size, size, size)); 
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
