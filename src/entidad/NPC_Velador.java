package entidad;

import Main.GamePanel;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;
import javax.imageio.ImageIO;

public class NPC_Velador extends Entidad {
    private int cont = 0;
    public NPC_Velador(GamePanel gP) {
        super(gP);
        this.colision = true;
        this.areaSolida = new java.awt.Rectangle(8, 16, 30, 25);
        this.direccion = "abajo";
        this.velocidad = 0;
        this.getSpritesNPC();
        this.setDialogos();
    }
    public void setDialogos(){
        this.dialogos[0] = "No deberias estar aqui...";
        this.dialogos[1] = "Vete antes de que sea demasiado tarde.";
    }
    /**
     * Carga los sprites especificos del NPC.
     */
    public void getSpritesNPC(){
        try{
            int size = 16;
            BufferedImage spritesheet = ImageIO.read(getClass().getResourceAsStream("/resources/npcs/layered.png"));
            this.abajo1 = spritesheet.getSubimage(3*size, 1*size, size, size);
            this.abajo2 = spritesheet.getSubimage(6*size, 1*size, size, size);
            this.arriba1 = spritesheet.getSubimage(5*size, 1*size, size, size);
            this.arriba2 = spritesheet.getSubimage(8*size, 1*size, size, size);
            this.izquierda1 = spritesheet.getSubimage(4*size, 1*size, size, size);
            this.izquierda2 = spritesheet.getSubimage(7*size, 1*size, size, size);
            this.derecha1 = voltearHorizontal(izquierda1);
            this.derecha2 = voltearHorizontal(izquierda2);
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
