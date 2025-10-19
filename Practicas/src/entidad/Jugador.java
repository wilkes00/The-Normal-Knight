package entidad;

import Main.GamePanel;
import Main.ManejadorTeclas;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;

public class Jugador extends Entidad{
    private GamePanel gP;
    private ManejadorTeclas mT;

    public Jugador(GamePanel gP, ManejadorTeclas mT){
        this.gP = gP;
        this.mT = mT;
        configuracionInicial();
        getSpritesJugador();
    }
    public void configuracionInicial(){
        this.x = 100;
        this.y = 100;
        this.velocidad = 4;
        this.direccion = "idle";
    }
    public void getSpritesJugador(){
        this.idle = Toolkit.getDefaultToolkit().createImage(getClass().getResource("/images/spritesjugador/_Idle.gif"));
        this.salto = Toolkit.getDefaultToolkit().createImage(getClass().getResource("/images/spritesjugador/__Jump.gif"));
        this.dash = Toolkit.getDefaultToolkit().createImage(getClass().getResource("/images/spritesjugador/__Dash.gif"));
        this.derecha = Toolkit.getDefaultToolkit().createImage(getClass().getResource("/images/spritesjugador/__Run.gif"));
        this.izquierda = Toolkit.getDefaultToolkit().createImage(getClass().getResource("/images/spritesjugador/__Run.gif"));
    }
    public void update(){
        boolean movimiento = false;
        if(mT.getTeclaSalto()) {
			setY(getY() - getVelocidad());
            this.direccion = "salto";
            this.contadorSprites++;
            movimiento = true;

		}
		if(mT.getTeclaDash()) {
			setX(getX()+ getVelocidad());
            this.direccion = "dash";
            this.contadorSprites++;
            movimiento = true;

		}
		if(mT.getTeclaDer()) {
            setX(getX() + getVelocidad());
            this.direccion = "derecha";
            this.contadorSprites++;
            movimiento = true;

        }
		if(mT.getTeclaIzq()) {
            setX(getX() - getVelocidad());
            this.direccion = "izquierda";
            this.contadorSprites++;
            movimiento = true;

		}

        if(!movimiento){
            this.direccion = "idle";
            this.numeroSprite = 1;
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
        Image sprite = null;
        int anchoSprite = 160;  //ancho
        int altoSprite = 160; //alto
        java.awt.geom.AffineTransform old = g2.getTransform();

        switch(this.direccion){
            case "idle":
                g2.drawImage(this.idle, getX(), getY(), anchoSprite, altoSprite, gP);
                break;
            case "salto":
                g2.drawImage(this.salto, getX(), getY(), anchoSprite, altoSprite, gP);
                break;
            case "dash":
                g2.drawImage(this.dash, getX(), getY(), anchoSprite, altoSprite, gP);
                break;
            case "izquierda":
                g2.translate(getX() + anchoSprite, getY());
                g2.scale(-1, 1);
                g2.drawImage(this.izquierda, 0, 0, anchoSprite, altoSprite, gP);
                break;
            case "derecha":
                g2.drawImage(this.derecha, getX(), getY(), anchoSprite, altoSprite, gP);
                break;

        }
        g2.setTransform(old);
        g2.drawImage(sprite, getX(), getY(), gP.getTamTile(), gP.getTamTile(), gP);
    }

    public int getX(){
        return this.x;
    }
    public int getY(){
        return this.y;
    }
    public int getVelocidad(){
        return this.velocidad;
    }
    public void setX(int valor){
        this.x = valor;
    }
    public void setY(int valor){
        this.y = valor;
    }
}
