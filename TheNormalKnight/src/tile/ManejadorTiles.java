package tile;

import Main.GamePanel;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import javax.imageio.ImageIO;

public class ManejadorTiles {
    private GamePanel gP;
    private int maxTiles = 30;
    Tile[] arregloTiles;
    private int codigosMapaTiles[][][];
    private int maxMapas = 10;

    public ManejadorTiles(GamePanel gP){
        this.gP = gP;
        this.arregloTiles = new Tile[this.maxTiles];
        this.codigosMapaTiles = new int[maxMapas][gP.getMaxRenMundo()][gP.getMaxColMundo()];
        getImagenesTiles();
        cargaMapa("/mapas/mundo01.txt", gP.getMapaMundo());
        cargaMapa("/mapas/mapa01.txt", gP.getMapaMazmorra1());
        
    }

    public void cargaMapa(String ruta, int indiceMapa){
        try {
            InputStream mapa = getClass().getResourceAsStream(ruta);
            BufferedReader br = new BufferedReader(new InputStreamReader(mapa));
            
            int ren = 0; // La fila actual que estamos llenando en nuestro arreglo
            
            // Haremos un bucle mientras tengamos filas por llenar en el mapa
            while (ren < gP.getMaxRenMundo()) { 
                String renglonDatos = br.readLine(); // Lee la siguiente línea del archivo
                
                // Caso 1: Se acabó el archivo (fin de fichero)
                if(renglonDatos == null) {
                    break; // Salimos del bucle
                }
                
                // Caso 2: La línea está vacía (solucion del bug)
                if(renglonDatos.trim().isEmpty()) {
                    continue; // Salta esta iteración y lee la siguiente línea
                              // 'ren' no se incrementa, así que no perdemos una fila del mapa
                }
                
                // Caso 3: La línea tiene datos
                String codigos[] = renglonDatos.split(" ");
                
                int col = 0;
                // Llenamos todas las columnas para la fila actual
                while(col < gP.getMaxColMundo()) {
                    // Nos aseguramos que la línea del archivo no sea más corta
                    if(col < codigos.length) {
                        int codigo = Integer.parseInt(codigos[col]);
                        this.codigosMapaTiles[indiceMapa][ren][col] = codigo;
                    } else {
                        // Si la línea es más corta, rellenamos con 0 (agua)
                         this.codigosMapaTiles[indiceMapa][ren][col] = 0;
                    }
                    col++;
                }
                // Ya procesamos una fila válida, pasamos a la siguiente
                ren++;
            }
            br.close();
            
        } catch (Exception e) {
            //System.out.println("Error al cargar el mapa: " + ruta); //depuracion
            e.printStackTrace();
        }
    }

    public void getImagenesTiles(){
        try {
            BufferedImage spritesheet = ImageIO.read(getClass().getResourceAsStream("/tiles/allTiles.png"));
            int sizeTile = 16;
            
            arregloTiles[0] = new Tile();
            arregloTiles[0].setImagen(ImageIO.read(getClass().getResourceAsStream("/tiles/agua.png")));
            arregloTiles[0].setColision(true);

            arregloTiles[1] = new Tile();
            arregloTiles[1].setImagen(ImageIO.read(getClass().getResourceAsStream("/tiles/arbol.png")));
            arregloTiles[1].setColision(true);

            arregloTiles[2] = new Tile();
            arregloTiles[2].setImagen(ImageIO.read(getClass().getResourceAsStream("/tiles/arena.png")));

            arregloTiles[3] = new Tile();
            arregloTiles[3].setImagen(ImageIO.read(getClass().getResourceAsStream("/tiles/muro.png")));
            arregloTiles[3].setColision(true);

            arregloTiles[4] = new Tile();
            arregloTiles[4].setImagen(ImageIO.read(getClass().getResourceAsStream("/tiles/pasto.png")));
            
            arregloTiles[5] = new Tile();
            arregloTiles[5].setImagen(ImageIO.read(getClass().getResourceAsStream("/tiles/suelo.png")));

            arregloTiles[6] = new Tile();
            arregloTiles[6].setImagen(spritesheet.getSubimage(6 * sizeTile, 7 * sizeTile, sizeTile, sizeTile));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void draw(Graphics2D g2, int camaraX, int camaraY){
        int renMundo = 0, colMundo = 0;
        
        while (renMundo < gP.getMaxRenMundo() && colMundo < gP.getMaxColMundo()) {
            int codigoTile = this.codigosMapaTiles[gP.getMapaActual()][renMundo][colMundo];
            int mundoX = colMundo * gP.getTamTile();
            int mundoY = renMundo * gP.getTamTile();

            //la posicion en pantalla de un mosaico es su posicion en el mundo
            //menos la posicion actual de la camara
            int pantallaX = mundoX - camaraX;
            int pantallaY = mundoY - camaraY;

            //comprueba si el mosaico actual esta dentro del area visible de la camara
            //crucial para el rendimiento, para no dibujar el mapa entero
            if(mundoX + gP.getTamTile() > camaraX && 
                mundoX - gP.getTamTile() < camaraX + gP.getAnchoPantalla() && 
                mundoY + gP.getTamTile() > camaraY &&
                mundoY - gP.getTamTile() < camaraY + gP.getAltoPantalla()){

                    //dibuja el mosaico si esta dentro de la pantalla
                    g2.drawImage(this.arregloTiles[codigoTile].getImagen(), pantallaX, pantallaY, this.gP.getTamTile(), this.gP.getTamTile(), null);

                }
            //avanza a la siguiente columna del mapa
            colMundo++;
            //si se llega al final de una fila
            if(colMundo == gP.getMaxColMundo()){
                colMundo = 0;
                renMundo++;
            }
        }
    }

    public int getCodigoMapaTiles(int ren, int col){
        return this.codigosMapaTiles[gP.getMapaActual()][ren][col];
    }
    public boolean getColisionDeTile(int index){
        return this.arregloTiles[index].getColision();
    }
}
