package tile;

import Main.GamePanel;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import javax.imageio.ImageIO;
/**
 * Gestiona todos los mosaicos (tiles) del juego.
 * Se encarga de cargar las imágenes de los tiles, leer los archivos de mapa (.txt),
 * almacenar la estructura del mapa y dibujar el mapa en la pantalla
 * en función de la posición de la cámara.
 *
 */
public class ManejadorTiles {
    private GamePanel gP;
    private int maxTiles = 30;
    Tile[] arregloTiles;
    private int codigosMapaTiles[][][];
    /**
     * Constructor del ManejadorTiles.
     * Inicializa los arreglos, carga las imágenes de los tiles (getImagenesTiles)
     * y carga los mapas desde los archivos de recursos (cargaMapa).
     *
     * @param gP Referencia al GamePanel principal.
     */
    public ManejadorTiles(GamePanel gP){
        this.gP = gP;
        this.arregloTiles = new Tile[this.maxTiles];
        this.codigosMapaTiles = new int[gP.getMaxMapas()][gP.getMaxRenMundo()][gP.getMaxColMundo()];
        getImagenesTiles();
        cargaMapa("/mapas/mundo01.txt", gP.getMapaMundo());
        cargaMapa("/mapas/mapa01.txt", gP.getMapaMazmorra1());
        
    }
    /**
     * Carga un mapa desde un archivo de texto y lo almacena en la matriz
     * de códigos del mapa.
     *
     * @param ruta La ruta del recurso al archivo .txt del mapa (ej: "/mapas/mundo01.txt").
     * @param indiceMapa El índice (ID) del mapa donde se almacenarán los datos (ej: gP.getMapaMundo()).
     */
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


    public BufferedImage combinarImagenes(BufferedImage fondo, BufferedImage encima) {
        // Crea una nueva imagen en blanco del tamaño del fondo
        BufferedImage imagenCombinada = new BufferedImage(fondo.getWidth(), fondo.getHeight(), BufferedImage.TYPE_INT_ARGB);
    
        Graphics2D g = imagenCombinada.createGraphics(); // Obtiene los gráficos de la nueva imagen
        g.drawImage(fondo, 0, 0, null); // 1. Dibuja la imagen de fondo
        g.drawImage(encima, 0, 0, null); // 2. Dibuja la imagen de encima
        g.dispose(); // Libera los recursos
        return imagenCombinada; // Devuelve la imagen ya combinada

    }
    /**
     * Carga todas las imágenes de los tiles desde los recursos y las
     * almacena en el arreglo de Tiles.
     * Asigna propiedades de colisión a los tiles correspondientes (agua, muro, etc.).
     */
    public void getImagenesTiles(){
        try {
            BufferedImage spritesheet = ImageIO.read(getClass().getResourceAsStream("/tiles/allTiles.png"));
            BufferedImage tilesPadding = ImageIO.read(getClass().getResourceAsStream("/tiles/TilesetPadding.png"));
            BufferedImage decorations = ImageIO.read(getClass().getResourceAsStream("/tiles/Decorations.png"));
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
            BufferedImage gema = spritesheet.getSubimage(6 * sizeTile, 7 * sizeTile, sizeTile, sizeTile);
            arregloTiles[6].setImagen(combinarImagenes(arregloTiles[4].getImagen(), gema));


            int paddedSize = 17; // (16px del tile + 1px de padding)

            // Tile 10: Esquina sup-izq de pasto (Col 0, Fila 0)
            arregloTiles[10] = new Tile();
            arregloTiles[10].setImagen(tilesPadding.getSubimage(1 + (0 * paddedSize), 1 + (0 * paddedSize), sizeTile, sizeTile));

            // Tile 11: Borde superior de pasto (Col 1, Fila 0)
            arregloTiles[11] = new Tile();
            arregloTiles[11].setImagen(tilesPadding.getSubimage(1 + (1 * paddedSize), 1 + (0 * paddedSize), sizeTile, sizeTile));

            // Tile 12: Centro de pasto (Col 1, Fila 2)
            arregloTiles[12] = new Tile();
            arregloTiles[12].setImagen(tilesPadding.getSubimage(1 + (1 * paddedSize), 1 + (2 * paddedSize), sizeTile, sizeTile));
            // Este sería tu nuevo tile de pasto (como el 4)

            // Tile 13: Esquina sup-izq de agua (Col 0, Fila 5)
            arregloTiles[13] = new Tile();
            arregloTiles[13].setImagen(tilesPadding.getSubimage(1 + (0 * paddedSize), 1 + (5 * paddedSize), sizeTile, sizeTile));
            arregloTiles[13].setColision(true); // El agua es sólida

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * Dibuja la porción visible del mapa en la pantalla.
     * Calcula qué tiles están dentro del área de la cámara y los dibuja
     * en sus posiciones correspondientes en la pantalla.
     *
     * @param g2 El contexto gráfico (Graphics2D) sobre el que se dibuja.
     * @param camaraX La posición X actual de la cámara en el mundo.
     * @param camaraY La posición Y actual de la cámara en el mundo.
     */
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

    //getters
    public int getCodigoMapaTiles(int ren, int col){
        return this.codigosMapaTiles[gP.getMapaActual()][ren][col];
    }
    public boolean getColisionDeTile(int index){
        return this.arregloTiles[index].getColision();
    }
   
}
