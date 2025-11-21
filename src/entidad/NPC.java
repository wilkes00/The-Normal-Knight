package entidad;
import Main.GamePanel;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;
import javax.imageio.ImageIO;

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

    @Override
    public void draw(Graphics2D g2, int camaraX, int camaraY){
        BufferedImage sprite = null;
        switch(this.direccion){
            case "arriba":
                if(this.numeroSprite == 1)
                    sprite = this.arriba1;
                if(this.numeroSprite == 2)
                    sprite = this.arriba2;
                break;
            case "abajo":
                if(this.numeroSprite == 1)
                    sprite = this.abajo1;
                if(this.numeroSprite == 2)
                    sprite = this.abajo2;
                break;
            case "izquierda":
                if(this.numeroSprite == 1)
                    sprite = this.izquierda1;
                if(this.numeroSprite == 2)
                    sprite = this.izquierda2;
                break;
            case "derecha":
                if(this.numeroSprite == 1)
                    sprite = this.derecha1;
                if(this.numeroSprite == 2)
                    sprite = this.derecha2;
                break;
        }
        this.contadorSprites++;

            if(this.contadorSprites > this.cambiaSprite){
                if(this.numeroSprite == 1)
                    this.numeroSprite = 2;
                else
                    this.numeroSprite = 1;
                this.contadorSprites = 0;
            }

        //calculo de la posicion en pantalla
        //la posicion del jugador en pantalla es su posicion en el mundo menos la posicion de la esquina de la camara
        int NPCPantallaX = this.mundoX - camaraX;
        int NPCPantallaY = this.mundoY - camaraY;
        
        //dibuja el sprite seleccionado en las coordenadas calculadas
        g2.drawImage(sprite, NPCPantallaX, NPCPantallaY, gP.getTamTile(), gP.getTamTile(), null);
    }
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
