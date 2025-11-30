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
        // Solo verificar colisiones si la entidad está en el mapa actual
        if(entidad.getMapa() != gP.getMapaActual()){
            return;
        }
        
        // Calcular las coordenadas del área sólida en el mundo
        int izquierdaEntidadMundoX = (entidad).getMundoX() + (entidad).getAreaSolidaX();
        int derechaEntidadMundoX = izquierdaEntidadMundoX + (entidad).getAreaSolidaAncho();

        int arribaEntidadMundoY = (entidad).getMundoY() + (entidad).getAreaSolidaY();
        int abajoEntidadMundoY = arribaEntidadMundoY + (entidad).getAreaSolidaAlto();

        int numTile1, numTile2;

        switch((entidad).getDireccion()){
            case "arriba":
                // Calcular la nueva posición del borde superior después del movimiento
                int nuevoArribaY = arribaEntidadMundoY - (entidad).getVelocidad();
                int renArribaEntidad = nuevoArribaY / this.gP.getTamTile();
                
                // Calcular qué tiles ocupa horizontalmente (izquierda y derecha del área sólida)
                int colIzquierda = izquierdaEntidadMundoX / this.gP.getTamTile();
                int colDerecha = (derechaEntidadMundoX - 1) / this.gP.getTamTile();
                
                numTile1 = gP.mTi.getCodigoMapaTiles(renArribaEntidad, colIzquierda);
                numTile2 = gP.mTi.getCodigoMapaTiles(renArribaEntidad, colDerecha);
                
                if(gP.mTi.getColisionDeTile(numTile1) || gP.mTi.getColisionDeTile(numTile2)){
                    (entidad).setColisionActivada(true);
                }
                break;
                
            case "abajo":
                // Calcular la nueva posición del borde inferior después del movimiento
                int nuevoAbajoY = abajoEntidadMundoY + (entidad).getVelocidad();
                int renAbajoEntidad = nuevoAbajoY / this.gP.getTamTile();
                
                // Calcular qué tiles ocupa horizontalmente
                colIzquierda = izquierdaEntidadMundoX / this.gP.getTamTile();
                colDerecha = (derechaEntidadMundoX - 1) / this.gP.getTamTile();
                
                numTile1 = gP.mTi.getCodigoMapaTiles(renAbajoEntidad, colIzquierda);
                numTile2 = gP.mTi.getCodigoMapaTiles(renAbajoEntidad, colDerecha);
                
                if(gP.mTi.getColisionDeTile(numTile1) || gP.mTi.getColisionDeTile(numTile2)){
                    (entidad).setColisionActivada(true);
                }
                break;
                
            case "izquierda":
                // Calcular la nueva posición del borde izquierdo después del movimiento
                int nuevoIzquierdaX = izquierdaEntidadMundoX - (entidad).getVelocidad();
                int colIzquierdaEntidad = nuevoIzquierdaX / this.gP.getTamTile();
                
                // Calcular qué tiles ocupa verticalmente
                int renArriba = arribaEntidadMundoY / this.gP.getTamTile();
                int renAbajo = (abajoEntidadMundoY - 1) / this.gP.getTamTile();
                
                numTile1 = gP.mTi.getCodigoMapaTiles(renArriba, colIzquierdaEntidad);
                numTile2 = gP.mTi.getCodigoMapaTiles(renAbajo, colIzquierdaEntidad);
                
                if(gP.mTi.getColisionDeTile(numTile1) || gP.mTi.getColisionDeTile(numTile2)){
                    (entidad).setColisionActivada(true);
                }
                break;
                
            case "derecha":
                // Calcular la nueva posición del borde derecho después del movimiento
                int nuevoDerechaX = derechaEntidadMundoX + (entidad).getVelocidad();
                int colDerechaEntidad = nuevoDerechaX / this.gP.getTamTile();
                
                // Calcular qué tiles ocupa verticalmente
                renArriba = arribaEntidadMundoY / this.gP.getTamTile();
                renAbajo = (abajoEntidadMundoY - 1) / this.gP.getTamTile();
                
                numTile1 = gP.mTi.getCodigoMapaTiles(renArriba, colDerechaEntidad);
                numTile2 = gP.mTi.getCodigoMapaTiles(renAbajo, colDerechaEntidad);
                
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
            
            // 2. Solo verificar colisiones con objetos del mismo mapa
            if(obj.getMapa() != entidad.getMapa()){
                continue;
            }
            
            // 3. Si el objeto no es sólido, saltar
            if (!obj.getColision()) { 
                continue;
            }

            // 4. Obtén el área sólida de la entidad (Jugador)
            Rectangle areaEntidad = new Rectangle(
                entidad.getMundoX() + entidad.getAreaSolida().x, 
                entidad.getMundoY() + entidad.getAreaSolida().y,
                entidad.getAreaSolida().width, 
                entidad.getAreaSolida().height);

            // 5. Obtén el área sólida del Objeto (Árbol, Roca)
            Rectangle areaObjeto = new Rectangle(
                obj.getMundoX() + obj.getAreaSolida().x,
                obj.getMundoY() + obj.getAreaSolida().y,
                obj.getAreaSolida().width,
                obj.getAreaSolida().height);

            // 6. Simula el próximo movimiento de la entidad
            switch (entidad.getDireccion()) {
                case "arriba": areaEntidad.y -= entidad.getVelocidad(); break;
                case "abajo":  areaEntidad.y += entidad.getVelocidad(); break;
                case "izquierda": areaEntidad.x -= entidad.getVelocidad(); break;
                case "derecha":   areaEntidad.x += entidad.getVelocidad(); break;
            }

            // 7. Comprueba si se intersectarían
            if (areaEntidad.intersects(areaObjeto)) {
                entidad.setColisionActivada(true);
            }
        }
    }
}
