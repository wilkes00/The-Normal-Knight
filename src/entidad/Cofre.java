package entidad;

import Main.GamePanel;
import javax.imageio.ImageIO;
/**
 * Clase que representa un cofre en el juego.
 */
public class Cofre extends Objeto implements Llave{
    private boolean abierto;
    private boolean tieneLlave;
    private String texto;
    /**
     * Constructor de la clase Cofre.
     * @param gP Panel del juego.
     * @param mapa Número del mapa donde se encuentra el cofre.
     * @param x Coordenada X en el mundo.
     * @param y Coordenada Y en el mundo.
     * @param tieneLlave Indica si el cofre contiene una llave.
     */
    public Cofre(GamePanel gP, int mapa, int x, int y, boolean tieneLlave) {
        super(gP);
        this.mundoX = x * gP.getTamTile();
        this.mundoY = y * gP.getTamTile();
        this.setMapa(mapa);
        this.nombre = "Cofre";
        this.colision = true;
        this.tieneLlave = tieneLlave;
        // Ajuste de hitbox específico para cofres
        this.areaSolida.x = 16;
        this.areaSolida.y = 32;
        this.areaSolida.width = 16;
        this.areaSolida.height = 16;

        cargarImagen();
    }
    /**
     * Carga las imágenes del cofre cerrado y abierto.
     */
    private void cargarImagen(){
        try {
            setImagen(ImageIO.read(getClass().getResourceAsStream("/resources/tiles/Cofre_Cerrado.png")));
            setImagen2(ImageIO.read(getClass().getResourceAsStream("/resources/tiles/Cofre_Abierto.png")));
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * Interacción del jugador con el cofre.
     */
    public void interactuar(){
        if(abierto == false){
            try {
                gP.playSoundEffect(2); //Reproduce el sonido de abrir cofre
                setImagen(getImagen2());
                abierto = true;
                
                if(tieneLlave){
                    gP.getJugador().setLlave(true);
                    gP.setEstadoJuego(gP.getDialogueState());
                    gP.getIU().setDialogoActual("¡Has encontrado una llave dentro del cofre!");
                }
                
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    /**
     * Devuelve si el cofre contiene una llave.
     */
    @Override
    public boolean getLlave() {
        return this.tieneLlave;
    }
    /**
     * Establece si el cofre contiene una llave.
     */
    @Override
    public void setLlave(boolean tieneLlave) {
        this.tieneLlave = tieneLlave;
    }
}