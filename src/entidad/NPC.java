package entidad;
import Main.GamePanel;
import java.io.IOException;
import java.util.Random;
import javax.imageio.ImageIO;

/**
 * Subclase de Entidad. Representa a un NPC dentro del juego.
 * Tiene un comportamiento unico implementado en el metodo accion.
 * Maneja la logica especifica de esta entidad.
 */
public class NPC extends Entidad{
    private int cont = 0;
    public NPC(GamePanel gP){
        super(gP);
        this.colision = true;
        this.areaSolida = new java.awt.Rectangle(8, 16, 32, 32);
        this.direccion = "abajo";
        this.velocidad = 2;
        this.getSpritesNPC();
    }
    /**
     * Carga los sprites especificos del NPC.
     */
    public void getSpritesNPC(){
        try{
            this.arriba1 = ImageIO.read(getClass().getResourceAsStream("/spritesjugador/moverArriba1.png"));
            this.arriba2 = ImageIO.read(getClass().getResourceAsStream("/spritesjugador/moverArriba2.png"));
            this.abajo1 = ImageIO.read(getClass().getResourceAsStream("/spritesjugador/moverAbajo1.png"));
            this.abajo2 = ImageIO.read(getClass().getResourceAsStream("/spritesjugador/moverAbajo2.png"));
            this.izquierda1 = ImageIO.read(getClass().getResourceAsStream("/spritesjugador/moverIzquierda1.png"));               
            this.izquierda2 = ImageIO.read(getClass().getResourceAsStream("/spritesjugador/moverIzquierda2.png"));
            this.derecha1 = ImageIO.read(getClass().getResourceAsStream("/spritesjugador/moverDerecha1.png"));
            this.derecha2 = ImageIO.read(getClass().getResourceAsStream("/spritesjugador/moverDerecha2.png"));
        } catch(IOException e){
            e.printStackTrace();
        }
    }
    /**
     * Define la logica de accion y movimiento del NPC.
     * Debe implementarse en las subclases de Entidad,
     * con excepcion de la subclase Jugador.
     */
    @Override
    public void accion(){
        cont++;
        if(cont == 120){
            Random ran = new Random();
            int i = ran.nextInt(100) + 1;
            if(i <= 25)
                direccion = "arriba";
            else if(i > 25 && i <= 50)
                direccion = "abajo";
            else if(i > 50 && i <= 75)
                direccion = "derecha";
            else if(i > 75 && i <= 100)
                direccion = "izquierda";
            cont = 0;
        }
    }
}
