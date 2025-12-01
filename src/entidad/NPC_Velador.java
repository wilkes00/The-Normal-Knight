package entidad;

import Main.GamePanel;
import java.io.IOException;
import javax.imageio.ImageIO;
/**
 * Clase NPC de tipo Velador, este NPC no se mueve por lo que la logica de
 * accion no mueve al personaje.
 */
public class NPC_Velador extends Entidad {
    /**
     * Constructor de la clase, se dedica de configuraciones iniciales para el NPC,
     * tales como: la colision, hitbox, velocidad de movimiento, carga de sprites y dialogos.
     * @param gP Referencia al GamePanel principal.
     */
    public NPC_Velador(GamePanel gP) {
        super(gP);
        this.colision = true;
        this.areaSolida = new java.awt.Rectangle(8, 16, 30, 25);
        this.direccion = "abajo";
        this.velocidad = 0;
        this.getSpritesNPC();
        this.setDialogos();
    }
    /**
     * Metodo para inicializar los dialogos unicos del personaje.
     */
    public void setDialogos(){
        this.dialogos[0] = "Tened cuidado forastero, este cementerio es peligroso.";
        this.dialogos[1] = "Ni un alma ha regresado de la mazmorra.";
        this.dialogos[2] = "Dicen que hay un gran tesoro escondido alli.";
        this.dialogos[3] = "Si tan solo fuese mas joven, yo mismo iria a buscarlo...";
        
    }
    /**
     * Carga los sprites especificos del NPC.
     */
    public void getSpritesNPC(){
        try{
            int size = 16;
            this.abajo1 = ImageIO.read(getClass().getResourceAsStream("/resources/npcs/Npc_1.png"));
            this.abajo2 = ImageIO.read(getClass().getResourceAsStream("/resources/npcs/Npc2.png"));
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
        
    }
}
