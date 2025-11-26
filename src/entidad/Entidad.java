package entidad;

import Main.GamePanel;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
/**
 * Clase base abstracta para todas las entidades del juego que se mueven, como el jugador,
 * enemigos y NPCs.
 * Contiene propiedades comunes como la posición en el mundo (heredado de GameObject), 
 * velocidad, dirección, sprites de animación y gestión de colisiones.
 */
public abstract class Entidad extends GameObject {
    GamePanel gP;
    protected int velocidad;
    protected BufferedImage arriba1, arriba2, abajo1, abajo2, derecha1, derecha2, izquierda1, izquierda2;
    protected String direccion;
    protected int contadorSprites = 0;
    protected int numeroSprite = 1;
    protected int cambiaSprite = 10;
    protected String[] dialogos = new String[20];
    protected int indiceDialogo = 0;
 
    /**
     * Constructor de la clase Entidad.
     * @param gP referencia al GamePanel principal del juego.
     */
    public Entidad(GamePanel gP){
        this.gP = gP;
    }

    /**
     * Actualiza el estado de la entidad.
     * Debe ser implementado en las subclases de Entidad que tengan un comportamiento
     * diferente que no se haya implementado en accion
     */
    public void update(){ 
        accion();
        //verifica colisiones
        this.colisionActivada = false;  
        gP.getDetectorColisiones().revisaTile(this);
        gP.getDetectorColisiones().revisaObjeto(this, gP.getManejadorObjetos().getListaGameObjects());
    
         //si no hubo colision
        if(this.colisionActivada == false){
            switch(this.direccion){
                case "arriba":
                    setMundoY(getMundoY() - getVelocidad());
                    break;
                case "abajo":
                    setMundoY(getMundoY() + getVelocidad());
                    break;
                case "izquierda":
                    setMundoX(getMundoX() - getVelocidad());
                    break;
                case "derecha":
                    setMundoX(getMundoX() + getVelocidad());
                    break;
            }
        }
        
    }
    /**
     * Metodo abstracto que incluye la logica de accion y movimiento de
     * la Entidad especifica. Debe implementarse en las subclases de Entidad,
     * no es un metodo con la firma abstract porque el Jugador no lo utiliza.
     */
    public void accion(){}
    
    public void hablar(){
        if(dialogos[indiceDialogo] != null){
            gP.setEstadoJuego(gP.getDialogueState());
            gP.getIU().setDialogoActual(dialogos[indiceDialogo]);
            indiceDialogo++;
        }
        else{
            indiceDialogo = 0; //reinicia el dialogo
            gP.setEstadoJuego(gP.getPlayState());
        }

        switch(gP.getJugador().getDireccion()){
            case "arriba":
                direccion = "abajo";
                break;
            case "abajo":
                direccion = "arriba";
                break;
            case "izquierda":
                direccion = "derecha";
                break;
            case "derecha":
                direccion = "izquierda";
                break;
        }
    }
    /**
     * Dibuja la entidad en la pantalla.
     * @param g2 el objeto Graphics2D para dibujar.
     * @param camaraX La coordenada X de la camara.
     * @param camaraY La coordenada Y de la camara.
     */
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
        int EntidadPantallaX = this.mundoX - camaraX;
        int EntidadPantallaY = this.mundoY - camaraY;
        
        //dibuja el sprite seleccionado en las coordenadas calculadas
        g2.drawImage(sprite, EntidadPantallaX, EntidadPantallaY, gP.getTamTile(), gP.getTamTile(), null);

        /* // ==== LA SIGUIENTE SECCION DE CODIGO ES PARA DEPURACION ====
        g2.setColor(new Color(255, 0, 0, 100));
        int hitboxX = EntidadPantallaX + areaSolida.x;
        int hitboxY = EntidadPantallaY + areaSolida.y;
        
        //Dibuja el rectángulo relleno
        g2.fillRect(hitboxX, hitboxY, areaSolida.width, areaSolida.height);
        
        //Dibuja un borde blanco para que se vea mejor
        g2.setColor(Color.white);
        g2.drawRect(hitboxX, hitboxY, areaSolida.width, areaSolida.height);
        */ //=========================================================
    }
     /**
     * Voltea horizontalmente una imagen.
     */
    protected BufferedImage voltearHorizontal(BufferedImage img){
        AffineTransform tx = AffineTransform.getScaleInstance(-1, 1);
        tx.translate(-img.getWidth(), 0);
        AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
        return op.filter(img, null);
    }
    
    //getters y setters
    public int getVelocidad() {
        return this.velocidad;
    }
    
    public String getDireccion() {
        return this.direccion;
    }
}
