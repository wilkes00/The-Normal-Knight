package entidad;

import Main.GamePanel;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
/**
 * Gestiona todos los objetos dentro del juego, tanto al jugador
 * como NPCs, enemigos e items.
 * Carga las imagenes de los objetos y las coloca en el mapa.
 * Actualiza a los objetos que son entidades
 */
public class ManejadorObjetos {
    ArrayList<Entidad> entidades = new ArrayList<>();
    ArrayList<Objeto> objetos = new ArrayList<>();
    Entidad npc[] = new Entidad[10];
    private GamePanel gP;

    public ManejadorObjetos(GamePanel gP) {
        this.gP = gP;
        colocarObjetosEstaticos();
        setNPC();
    }
    
    /**
     * Configura los NPCs y enemigos en el juego.
     */
    public void setNPC(){
        npc[0] = new NPC(gP);
        npc[0].mundoX = gP.getTamTile() *28;
        npc[0].mundoY = gP.getTamTile() *16;
        npc[0].setMapa(gP.getMapaMundo());
        agregarGameObject(npc[0]);

        npc[1] = new Enemigo(gP);
        npc[1].mundoX = gP.getTamTile() *19;
        npc[1].mundoY = gP.getTamTile() *16;
        npc[1].setMapa(gP.getMapaMundo());
        agregarGameObject(npc[1]);
    }
    /**
     * Coloca los objetos estaticos en el mapa.
     */
    public void colocarObjetosEstaticos() {
        // Añade la pocion a la lista de objetos estaticos
        objetos.add(new Pocion(this.gP, this.gP.getMapaMundo(),18, 16)); 
        
        // Cofres
        objetos.add(new Cofre(this.gP, gP.getMapaMazmorra1(), 3, 3, false));
        objetos.add(new Cofre(this.gP, gP.getMapaMazmorra1(), 8, 3, false));
        objetos.add(new Cofre(this.gP, gP.getMapaMazmorra1(), 13, 3, false));
        objetos.add(new Cofre(this.gP, gP.getMapaMazmorra1(), 18, 3, false));
        objetos.add(new Cofre(this.gP, gP.getMapaMazmorra1(), 3, 6, false));
        objetos.add(new Cofre(this.gP, gP.getMapaMazmorra1(), 8, 6, true));
        objetos.add(new Cofre(this.gP, gP.getMapaMazmorra1(), 13, 6, false));
        objetos.add(new Cofre(this.gP, gP.getMapaMazmorra1(), 18, 6, false));
        objetos.add(new Cofre(this.gP, gP.getMapaMazmorra2(), 2, 13, false));
        
    }

    /**
     * Actualiza solo las Entidades (objetos que se mueven).
     */
    public void update() {
        for (Entidad ent : entidades){
            if(ent.getMapa() == gP.getMapaActual()) {
                ent.update();
            }
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

    /**
     * Agrega un objeto a la lista correspondiente (Entidad u Objeto).
     * @param obj el elemento a agregar
     */
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
