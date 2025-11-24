package entidad;

import Main.GamePanel;
import javax.imageio.ImageIO;

public class Cofre extends Objeto implements Llave{
    private boolean abierto;
    private boolean tieneLlave;
    private String texto;
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
    private void cargarImagen(){
        try {
            setImagen(ImageIO.read(getClass().getResourceAsStream("/resources/tiles/Cofre_Cerrado.png")));
            setImagen2(ImageIO.read(getClass().getResourceAsStream("/resources/tiles/Cofre_Abierto.png")));
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void interactuar(){
        if(abierto == false){
            try {
                setImagen(getImagen2());
                abierto = true;
                System.out.println("Cofre abierto!");
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