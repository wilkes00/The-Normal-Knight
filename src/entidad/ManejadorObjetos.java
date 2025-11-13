package entidad;

import java.awt.Graphics2D;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import javax.imageio.ImageIO;
import Main.GamePanel;
import java.awt.image.BufferedImage;

public class ManejadorObjetos {
    ArrayList<GameObject> listaGameObjects = new ArrayList<>();
    BufferedImage[] objetoImagen;
    private final int maxObjetosImagenes = 20;
    private GamePanel gP;

    public ManejadorObjetos(GamePanel gP) {
        this.gP = gP;
        objetoImagen = new BufferedImage[maxObjetosImagenes];
        getImagenesObjetos();
        colocarObjetosEstaticos();
    }

    public void getImagenesObjetos() {
        try {
            // Carga la hoja de sprites completa
            BufferedImage spritesheet = ImageIO.read(getClass().getResourceAsStream("/tiles/allTiles.png"));
            int sizeTile = 16; // El tamaño base de la cuadrícula

            // --- Recortar Sprites (usando coordenadas de Decorations.png) ---
            // Poción Azul: (Col 0, Fila 7) -> (x=0, y=112)
            BufferedImage pocionAzul;

            // Poción Rosa: (Col 1, Fila 7) -> (x=16, y=112)
            BufferedImage pocionRosa = spritesheet.getSubimage(1 * sizeTile, 7 * sizeTile, sizeTile, sizeTile);

            // Poción Naranja/Amarilla: (Col 0, Fila 8) -> (x=0, y=128)
            BufferedImage pocionNaranja = spritesheet.getSubimage(0 * sizeTile, 8 * sizeTile, sizeTile, sizeTile);
            objetoImagen[0] = spritesheet.getSubimage(0 * sizeTile, 8 * sizeTile, sizeTile, sizeTile);

        } catch (IOException e) { 
            e.printStackTrace(); 
            System.err.println("Error al cargar el spritesheet de decoraciones.");
        }
    }

    public void colocarObjetosEstaticos() {
        Objeto roca = new Objeto(this.gP);
        roca.setMundoX(17 * gP.getTamTile());
        roca.setMundoY(16 * gP.getTamTile());
        roca.setImagen(objetoImagen[0]);
        roca.getAreaSolida().setBounds(8, 32, 16, 16);
        listaGameObjects.add(roca); // Añade el árbol a la lista        
    }

    /**
     * Actualiza SÓLO las Entidades (cosas que se mueven).
     */
    public void update() {
        for (GameObject obj : listaGameObjects) {
            // Comprueba si el objeto es una instancia de Entidad
            if (obj instanceof Entidad) {
                // Si lo es, llama a su método update()
                ((Entidad) obj).update();
            }
        }
    }

    /**
     * Dibuja TODOS los GameObjects, ordenados por su coordenada Y.
     */
    public void draw(Graphics2D g2, int camaraX, int camaraY) {
        
        // 1. Ordena la lista completa por la "base" (pies) del objeto
        Collections.sort(listaGameObjects, new Comparator<GameObject>() {
            @Override
            public int compare(GameObject g1, GameObject g2) {
                int y1 = g1.getMundoY() + g1.getAreaSolida().y + g1.getAreaSolida().height;
                int y2 = g2.getMundoY() + g2.getAreaSolida().y + g2.getAreaSolida().height;
                return Integer.compare(y1, y2);
            }
        });

        // 2. Dibuja la lista ordenada
        for (GameObject obj : listaGameObjects) {
            obj.draw(g2, camaraX, camaraY);
        }
    }
    // para agregar un objeto a la lista, en caso de ser necesario
    public void agregarGameObject(GameObject obj) {
        this.listaGameObjects.add(obj);
    }
    //elimina un objeto de la lista
    public void removerGameObject(GameObject obj) {
        this.listaGameObjects.remove(obj);
    }
    // Getter para el Detector de Colisiones
    public ArrayList<GameObject> getListaGameObjects() {
        return listaGameObjects;
    }
    
}
