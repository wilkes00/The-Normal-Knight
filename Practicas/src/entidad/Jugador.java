package entidad;

import Main.GamePanel;
import Main.ManejadorTeclas;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Jugador extends Entidad{
    private GamePanel gP;
    private ManejadorTeclas mT;
    private final int pantallaX, pantallaY;

    public Jugador(GamePanel gP, ManejadorTeclas mT){
        this.gP = gP;
        this.mT = mT;
        this.pantallaX = gP.getAnchoPantalla() / 2 - gP.getTamTile() / 2;
        this.pantallaY = gP.getAltoPantalla() / 2 - gP.getTamTile() / 2;
        configuracionInicial();
        getSpritesJugador();
    }
    public void configuracionInicial(){
        this.mundoX = gP.getTamTile() * 38;
        this.mundoY = gP.getTamTile() * 23;
        this.velocidad = 4;
        this.direccion = "abajo";
    }
    public void getSpritesJugador(){
        try {
            this.arriba1 = ImageIO.read(getClass().getResourceAsStream("/spritesjugador/moverArriba1.png"));
            this.arriba2 = ImageIO.read(getClass().getResourceAsStream("/spritesjugador/moverArriba2.png"));
            this.abajo1 = ImageIO.read(getClass().getResourceAsStream("/spritesjugador/moverAbajo1.png"));
            this.abajo2 = ImageIO.read(getClass().getResourceAsStream("/spritesjugador/moverAbajo2.png"));
            this.izquierda1 = ImageIO.read(getClass().getResourceAsStream("/spritesjugador/moverIzquierda1.png"));
            this.izquierda2 = ImageIO.read(getClass().getResourceAsStream("/spritesjugador/moverIzquierda2.png"));
            this.derecha1 = ImageIO.read(getClass().getResourceAsStream("/spritesjugador/moverDerecha1.png"));
            this.derecha2 = ImageIO.read(getClass().getResourceAsStream("/spritesjugador/moverDerecha2.png"));

        }catch(IOException e){
            e.printStackTrace();
        }
    }
    public void update(){
        if(mT.getTeclaArriba()) {
			setY(getY() - getVelocidad());
            this.direccion = "arriba";
            this.contadorSprites++;

		}
		if(mT.getTeclaAbajo()) {
			setY(getY() + getVelocidad());
            this.direccion = "abajo";
            this.contadorSprites++;

		}
		if(mT.getTeclaDer()) {
            setX(getX() + getVelocidad());
            this.direccion = "derecha";
            this.contadorSprites++;

        }
		if(mT.getTeclaIzq()) {
            setX(getX() - getVelocidad());
            this.direccion = "izquierda";
            this.contadorSprites++;

		}

        if(this.contadorSprites > this.cambiaSprite){
            if (this.numeroSprite == 1) 
                this.numeroSprite = 2;
            else
                this.numeroSprite = 1;

            this.contadorSprites = 0;
        }
    }
    public void draw(Graphics2D g2){
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
        g2.drawImage(sprite, this.pantallaX, this.pantallaY, gP.getTamTile(), gP.getTamTile(), gP);
    }

    public int getX(){
        return this.mundoX;
    }
    public int getY(){
        return this.mundoY;
    }
    public int getVelocidad(){
        return this.velocidad;
    }
    public void setX(int valor){
        this.mundoX = valor;
    }
    public void setY(int valor){
        this.mundoY = valor;
    }
}
