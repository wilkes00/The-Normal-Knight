package tile;

import java.awt.image.BufferedImage;

public class Tile {
    private BufferedImage imagen;
    private boolean colision = false;

    public BufferedImage getImagen(){
        return this.imagen;
    }
    public void setImagen(BufferedImage imagen){
        this.imagen = imagen;
    }
    public boolean getColision(){
        return this.colision;
    }
}
