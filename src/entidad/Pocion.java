package entidad;

import Main.GamePanel;

import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
/**
 * Clase que representa una pocion en el juego, hereda de la clase Objeto
 */
public class Pocion extends Objeto {
	/**
	 * Constructor de la clase Pocion, se encarga de inicializar la posicion del objeto en el mapa,
	 * asi como el mapa en el que se encuentra. Define el area de hitbox del objeto para que el jugador pueda recogerlo.
	 * @param gP Referencia al GamePanel principal.
	 * @param mapa	Mapa en el que el objeto sera colocado.
	 * @param x Coordenada en x
	 * @param y Coordenada en y
	 */
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
    /**
     * Metodo que carga la imagen de la pocion especifica para esta clase.
     */
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
