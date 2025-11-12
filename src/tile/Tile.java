package tile;

import java.awt.image.BufferedImage;
/**
 * Representa un único mosaico (tile) en el mapa del juego.
 * Contiene la imagen del mosaico y una bandera que indica si es
 * sólido (produce colisión) o no.
 */
public class Tile {
    private BufferedImage imagen;
    private boolean colision = false;

    //getters y setters
    public BufferedImage getImagen(){
        return this.imagen;
    }
    public void setImagen(BufferedImage imagen){
        this.imagen = imagen;
    }
    public boolean getColision(){
        return this.colision;
    }
    public void setColision(boolean colision){
        this.colision = colision;
    }
}
