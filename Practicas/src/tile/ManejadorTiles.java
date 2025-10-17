package tile;

import Main.GamePanel;
import java.awt.Graphics2D;
import java.io.IOException;
import javax.imageio.ImageIO;

public class ManejadorTiles {
    private GamePanel gP;
    private int maxTiles = 10;
    Tile[] arregloTiles;

    public ManejadorTiles(GamePanel gP){
        this.gP = gP;
        this.arregloTiles = new Tile[this.maxTiles];
        getImagenesTiles();
    }
    public void getImagenesTiles(){
        try {
            arregloTiles[0] = new Tile();
            arregloTiles[0].setImagen(ImageIO.read(getClass().getResourceAsStream("/tiles/agua.png")));
            arregloTiles[1] = new Tile();
            arregloTiles[1].setImagen(ImageIO.read(getClass().getResourceAsStream("/tiles/arbol.png")));
            arregloTiles[2] = new Tile();
            arregloTiles[2].setImagen(ImageIO.read(getClass().getResourceAsStream("/tiles/arena.png")));
            arregloTiles[3] = new Tile();
            arregloTiles[3].setImagen(ImageIO.read(getClass().getResourceAsStream("/tiles/muro.png")));
            arregloTiles[4] = new Tile();
            arregloTiles[4].setImagen(ImageIO.read(getClass().getResourceAsStream("/tiles/pasto.png")));
            arregloTiles[5] = new Tile();
            arregloTiles[5].setImagen(ImageIO.read(getClass().getResourceAsStream("/tiles/suelo.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void draw(Graphics2D g2){
        int ren = 0, col = 0;
        int x = 0, y = 0;
        
        while (ren < gP.getAltoPantalla() && col < gP.getAnchoPantalla()) { 
            g2.drawImage(arregloTiles[4].getImagen(), x, y, gP.getTamTile(), gP.getTamTile(), null);
            col++;
            x += gP.getTamTile();
            if(col == gP.getMaxColPantalla()){
                col = 0;
                x = 0;
                ren++;
                y += gP.getTamTile();
            }
        }
    }
}
