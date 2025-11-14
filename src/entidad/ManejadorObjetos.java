package entidad;

import Main.GamePanel;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import javax.imageio.ImageIO;
/**
 * Gestiona todos los objetos dentro del juego, tanto al jugador
 * como NPCs, enemigos e items.
 * Carga las imagenes de los objetos y las coloca en el mapa.
 * Actualiza a los objetos que son entidades
 */
public class ManejadorObjetos {
    ArrayList<Entidad> entidades = new ArrayList<>();
    ArrayList<Objeto> objetos = new ArrayList<>();
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
        Objeto pocion = new Objeto(this.gP);
        pocion.setMundoX(17 * gP.getTamTile());
        pocion.setMundoY(16 * gP.getTamTile());
        pocion.setImagen(objetoImagen[0]);
        pocion.setColision(false);
        pocion.getAreaSolida().setBounds(8, 32, 16, 16);
        pocion.setMapa(gP.getMapaMundo());
        objetos.add(pocion); // Añade el árbol a la lista de objetos estaticos      
    }

    /**
     * Actualiza solo las Entidades (cosas que se mueven).
     */
    public void update() {
        for (Entidad ent : entidades){
            ent.update();
        }
    }

    /**
     * Dibuja TODOS los GameObjects, ordenados por su coordenada Y.
     */
    public void draw(Graphics2D g2, int camaraX, int camaraY) {
        //lista temporal para dibujar todos los objetos
        ArrayList<GameObject> listaDibujable = new ArrayList<>();
        listaDibujable.addAll(entidades);
        listaDibujable.addAll(objetos);
        // 1. Ordena la lista completa por la "base" (pies) del objeto
        Collections.sort(listaDibujable, new Comparator<GameObject>() {
            @Override
            public int compare(GameObject g1, GameObject g2) {
                int y1 = g1.getMundoY() + g1.getAreaSolida().y + g1.getAreaSolida().height;
                int y2 = g2.getMundoY() + g2.getAreaSolida().y + g2.getAreaSolida().height;
                return Integer.compare(y1, y2);
            }
        });

        // 2. Dibuja la lista ordenada
        for (GameObject obj : listaDibujable) {
            if(obj.getMapa() == gP.getMapaActual())
                obj.draw(g2, camaraX, camaraY);
        }
    }
    // para agregar un objeto a la lista, en caso de ser necesario
    public void agregarGameObject(GameObject obj) {
        if (obj instanceof Entidad entidad)
            entidades.add(entidad);
        else if (obj instanceof Objeto objeto)
            objetos.add(objeto);
    }
    /**
     * Elimina un objeto de la lista a la que corresponde.
     * @param obj el elemento a eliminar
     */
    public void removerGameObject(GameObject obj) {
        if(obj instanceof Objeto objeto)
            this.objetos.remove(objeto);
        else if(obj instanceof Entidad entidad)
            this.entidades.remove(entidad);
    }
    /**
     * Obtiene una lista combinada de Entidades (NPC, enemigos) y de Objetos
     * estaticos (items, llaves, pociones, etc).
     */
    public ArrayList<GameObject> getListaGameObjects() {
        // Devuelve una lista combinada para el detector de colisiones
        ArrayList<GameObject> allObjects = new ArrayList<>();
        allObjects.addAll(entidades);
        allObjects.addAll(objetos);
        return allObjects;
    }
}
