package entidad;

import Main.GamePanel;
import Main.ManejadorTeclas;
import java.awt.Graphics2D;
import java.awt.Rectangle;
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
        this.pantallaX = gP.getAnchoPantalla() / 2 - (gP.getTamTile() / 2);
        this.pantallaY = gP.getAltoPantalla() / 2 - (gP.getTamTile() / 2);
        this.areaSolida = new Rectangle(8, 16, 32, 32);
        configuracionInicial();
        getSpritesJugador();
    }
    public void configuracionInicial(){
        this.mundoX = gP.getTamTile() * 23;
        this.mundoY = gP.getTamTile() * 21;
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
        if(mT.getTeclaArriba() || mT.getTeclaAbajo() || mT.getTeclaIzq() || mT.getTeclaDer()){
            if(mT.getTeclaArriba())
                this.direccion = "arriba";
            else if(mT.getTeclaAbajo())
                this.direccion = "abajo";
            else if(mT.getTeclaIzq())
                this.direccion = "izquierda";
            else if(mT.getTeclaDer())
                this.direccion = "derecha";
                
            //revisa la colision con tiles    
            this.colisionActivada = false;
            gP.getDetectorColisiones().revisaTile(this);


            //si no hubo colision
            if(this.colisionActivada == false){
                switch(this.direccion){
                    case "arriba":
                        setY(getY() - getVelocidad());
                        break;
                    case "abajo":
                        setY(getY() + getVelocidad());
                        break;
                    case "izquierda":
                        setX(getX() - getVelocidad());
                        break;
                    case "derecha":
                        setX(getX() + getVelocidad());
                        break;
                }
            }
            this.contadorSprites++;

            if(this.contadorSprites > this.cambiaSprite){
                if(this.numeroSprite == 1)
                    this.numeroSprite = 2;
                else
                    this.numeroSprite = 1;
                this.contadorSprites = 0;
            }
        }
        
    }
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

        //calculo de la posicion en pantalla
        //la posicion del jugador en pantalla es su posicion en el mundo menos la posicion de la esquina de la camara
        int jugadorPantallaX = this.mundoX - camaraX;
        int jugadorPantallaY = this.mundoY - camaraY;
        
        //dibuja el sprite seleccionado en las coordenadas calculadas
        g2.drawImage(sprite, jugadorPantallaX, jugadorPantallaY, gP.getTamTile(), gP.getTamTile(), null);
    }

    //Getters y Setters
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
    public int getMundoX(){
        return this.mundoX;
    }
    public int getMundoY(){
        return this.mundoY;
    }
    public int getPantallaX(){
        return this.pantallaX;
    }
    public int getPantallaY(){
        return this.pantallaY;
    }
    public Rectangle getAreaSolida(){
        return this.areaSolida;
    }
    public int getAreaSolidaX(){
        return this.areaSolida.x;
    }
    public int getAreaSolidaY(){
        return this.areaSolida.y;
    }
    public void setAreaSolidaX(int x){
        this.areaSolida.x = x;
    }
    public void setAreaSolidaY(int y){
        this.areaSolida.y = y;
    }
    public int getAreaSolidaAncho(){
        return this.areaSolida.width;
    }
    public int getAreaSolidaAlto(){
        return this.areaSolida.height;
    }
    public String getDireccion(){
        return this.direccion;
    }
}
