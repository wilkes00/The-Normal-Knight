package Main;

import entidad.Entidad;
import entidad.GameObject;
import java.awt.Rectangle;
import java.util.ArrayList;
/**
 * Clase responsable de detectar colisiones entre entidades y el entorno del juego.
 */
public class DetectorColisiones {
    GamePanel gP;
    /**
     * Constructor de la clase DetectorColisiones.
     * @param gP referencia al GamePanel principal.
     */
    public DetectorColisiones(GamePanel gP){
        this.gP = gP;
    }
    
    /**
     * Revisa las colisiones de una entidad con los tiles del mapa.
     * @param entidad la entidad cuya colisión se va a revisar.
     */
    public void revisaTile(Entidad entidad){
        int izquierdaEntidadMundoX = (entidad).getMundoX() + (entidad).getAreaSolidaX();
        int derechaEntidadMundoX = (entidad).getMundoX() + (entidad).getAreaSolidaX() + (entidad).getAreaSolidaAncho() - 1;

        int arribaEntidadMundoY = (entidad).getMundoY() + (entidad).getAreaSolidaY();
        int abajoEntidadMundoY = (entidad).getMundoY() + (entidad).getAreaSolidaY() +
        (entidad).getAreaSolidaAlto() - 1;

        int colIzquierdaEntidad = izquierdaEntidadMundoX / this.gP.getTamTile();
        int colDerechaEntidad = derechaEntidadMundoX / this.gP.getTamTile();
        int renArribaEntidad = arribaEntidadMundoY / this.gP.getTamTile();
        int renAbajoEntidad = abajoEntidadMundoY / this.gP.getTamTile();

        int numTile1, numTile2;

        switch((entidad).getDireccion()){
            case "arriba":
                renArribaEntidad = (arribaEntidadMundoY - (entidad).getVelocidad()) / this.gP.getTamTile();
                numTile1 = gP.mTi.getCodigoMapaTiles(renArribaEntidad, colIzquierdaEntidad);
                numTile2 = gP.mTi.getCodigoMapaTiles(renArribaEntidad, colDerechaEntidad);
                if(gP.mTi.getColisionDeTile(numTile1) || gP.mTi.getColisionDeTile(numTile2)){
                    (entidad).setColisionActivada(true);
                }
                break;
            case "abajo":
                renAbajoEntidad = (abajoEntidadMundoY + (entidad).getVelocidad()) / this.gP.getTamTile();
                numTile1 = gP.mTi.getCodigoMapaTiles(renAbajoEntidad, colIzquierdaEntidad);
                numTile2 = gP.mTi.getCodigoMapaTiles(renAbajoEntidad, colDerechaEntidad);
                if(gP.mTi.getColisionDeTile(numTile1) || gP.mTi.getColisionDeTile(numTile2)){
                    (entidad).setColisionActivada(true);
                }
                break;
            case "izquierda":
                colIzquierdaEntidad = (izquierdaEntidadMundoX - (entidad).getVelocidad()) / this.gP.getTamTile();
                numTile1 = gP.mTi.getCodigoMapaTiles(renArribaEntidad, colIzquierdaEntidad);
                numTile2 = gP.mTi.getCodigoMapaTiles(renAbajoEntidad, colIzquierdaEntidad);
                if(gP.mTi.getColisionDeTile(numTile1) || gP.mTi.getColisionDeTile(numTile2)){
                    (entidad).setColisionActivada(true);
                }
                break;
            case "derecha":
                colDerechaEntidad = (derechaEntidadMundoX + (entidad).getVelocidad()) / this.gP.getTamTile();
                numTile1 = gP.mTi.getCodigoMapaTiles(renArribaEntidad, colDerechaEntidad);
                numTile2 = gP.mTi.getCodigoMapaTiles(renAbajoEntidad, colDerechaEntidad);
                if(gP.mTi.getColisionDeTile(numTile1) || gP.mTi.getColisionDeTile(numTile2)){
                    (entidad).setColisionActivada(true);
                }
                break;
            default: break;
        }
    }
    /**
     * Revisa la colisión de una entidad contra la lista de todos los GameObjects.
     * @param entidad La entidad que se mueve (ej. Jugador)
     * @param lista La lista de todos los GameObjects del ManejadorObjetos
     */
    public void revisaObjeto(Entidad entidad, ArrayList<GameObject> lista) {
        for (GameObject obj : lista) {
            // 1. Una entidad no puede colisionar consigo misma
            if (obj == entidad) {
                continue;
            }
            // 2. Si el objeto no es sólido, saltar
            if (!obj.getColision()) { 
                continue;
            }

            // 3. Obtén el área sólida de la entidad (Jugador)
            Rectangle areaEntidad = new Rectangle(
                entidad.getMundoX() + entidad.getAreaSolida().x, 
                entidad.getMundoY() + entidad.getAreaSolida().y,
                entidad.getAreaSolida().width, 
                entidad.getAreaSolida().height);

            // 4. Obtén el área sólida del Objeto (Árbol, Roca)
            Rectangle areaObjeto = new Rectangle(
                obj.getMundoX() + obj.getAreaSolida().x,
                obj.getMundoY() + obj.getAreaSolida().y,
                obj.getAreaSolida().width,
                obj.getAreaSolida().height);

            // 5. Simula el próximo movimiento de la entidad
            switch (entidad.getDireccion()) {
                case "arriba": areaEntidad.y -= entidad.getVelocidad(); break;
                case "abajo":  areaEntidad.y += entidad.getVelocidad(); break;
                case "izquierda": areaEntidad.x -= entidad.getVelocidad(); break;
                case "derecha":   areaEntidad.x += entidad.getVelocidad(); break;
            }

            // 6. Comprueba si se intersectarían
            if (areaEntidad.intersects(areaObjeto)) {
                entidad.setColisionActivada(true);
            }
        }
    }
}
