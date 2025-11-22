package entidad;

import Main.GamePanel;
import Main.ManejadorTeclas;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
/**
 * Representa al jugador principal del juego. Hereda de Entidad.
 * Maneja la lógica específica del jugador, como la carga de sus sprites,
 * la actualización de su estado basada en la entrada del teclado, y su dibujado
 * en la pantalla.
 *
 */
public class Jugador extends Entidad{
    private ManejadorTeclas mT;
    private final int pantallaX, pantallaY;

    /**
     * Constructor para la clase Jugador.
     * Establece la referencia al GamePanel y al ManejadorTeclas, define la
     * posición del jugador en la pantalla (centrada) y su área de colisión.
     *
     * @param gP Referencia al GamePanel principal.
     * @param mT Referencia al ManejadorTeclas para la entrada.
     */
    public Jugador(GamePanel gP, ManejadorTeclas mT){
        super(gP);
        this.mT = mT;
        this.pantallaX = gP.getAnchoPantalla() / 2 - (gP.getTamTile() / 2);
        this.pantallaY = gP.getAltoPantalla() / 2 - (gP.getTamTile() / 2);
        this.areaSolida = new Rectangle(8, 16, 32, 32);
        configuracionInicial();
        getSpritesJugador();
    }
    /**
     * Establece los valores iniciales por defecto del jugador, como su
     * posición en el mundo, velocidad y dirección.
     */
    public void configuracionInicial(){
        this.mundoX = gP.getTamTile() * 23;
        this.mundoY = gP.getTamTile() * 21;
        this.velocidad = 4;
        this.direccion = "abajo";
        this.colision = true;
        this.setMapa(gP.getMapaActual());
    }
    /**
     * Carga las imágenes (sprites) del jugador para las diferentes
     * direcciones y animaciones de movimiento desde los recursos.
     */
    public void getSpritesJugador(){
        try {
            // Carga el spritesheet completo
            BufferedImage spritesheetJugador = ImageIO.read(getClass().getResourceAsStream("/spritesjugador/player_spritesheet.png")); // ¡CAMBIA ESTA RUTA!

            // El tamaño de cada frame en tu spritesheet es 24x24
            int frameWidth = 24;
            int frameHeight = 24;

            // --- Recortar las imágenes del spritesheet ---
            // Movimiento hacia abajo
            abajo1= spritesheetJugador.getSubimage(0 * frameWidth, 0 * frameHeight, frameWidth, frameHeight);
            abajo2= spritesheetJugador.getSubimage(1 * frameWidth, 0 * frameHeight, frameWidth, frameHeight);

            // Movimiento hacia izq
            izquierda1 = spritesheetJugador.getSubimage(0 * frameWidth, 1 * frameHeight, frameWidth, frameHeight);
            izquierda2 = spritesheetJugador.getSubimage(1 * frameWidth, 1 * frameHeight, frameWidth, frameHeight);

            //Movimiento hacia la derecha
            derecha1 = spritesheetJugador.getSubimage(0 * frameWidth, 2 * frameHeight, frameWidth, frameHeight);
            derecha2 = spritesheetJugador.getSubimage(1 * frameWidth, 2 * frameHeight, frameWidth, frameHeight);

            //Movimiento hacia arriba
            arriba1 = spritesheetJugador.getSubimage(0 * frameWidth, 3 * frameHeight, frameWidth, frameHeight);
            arriba2 = spritesheetJugador.getSubimage(1 * frameWidth, 3 * frameHeight, frameWidth, frameHeight);
            /* 
            this.arriba1 = ImageIO.read(getClass().getResourceAsStream("/spritesjugador/moverArriba1.png"));
            this.arriba2 = ImageIO.read(getClass().getResourceAsStream("/spritesjugador/moverArriba2.png"));
            this.abajo1 = ImageIO.read(getClass().getResourceAsStream("/spritesjugador/moverAbajo1.png"));
            this.abajo2 = ImageIO.read(getClass().getResourceAsStream("/spritesjugador/moverAbajo2.png"));
            this.izquierda1 = ImageIO.read(getClass().getResourceAsStream("/spritesjugador/moverIzquierda1.png"));
            this.izquierda2 = ImageIO.read(getClass().getResourceAsStream("/spritesjugador/moverIzquierda2.png"));
            this.derecha1 = ImageIO.read(getClass().getResourceAsStream("/spritesjugador/moverDerecha1.png"));
            this.derecha2 = ImageIO.read(getClass().getResourceAsStream("/spritesjugador/moverDerecha2.png"));
            */
        }catch(IOException e){
            e.printStackTrace();
            System.err.println("Error al cargar el spritesheet del jugador.");
        }
    }
    /**
     * Actualiza el estado del jugador en cada fotograma.
     * Revisa la entrada del teclado, actualiza la dirección,
     * comprueba colisiones y actualiza la posición del jugador si no hay colisión.
     * También gestiona la animación de sprites.
     */
    @Override
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
                
            //revisa la colision con tiles y objetos    
            this.colisionActivada = false;
            gP.getDetectorColisiones().revisaTile(this);
            gP.getDetectorColisiones().revisaObjeto(this, gP.getManejadorObjetos().getListaGameObjects());
            

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
        // despues de moverse verifica si esta encima de un item
        revisarInteraccionItems();
        
    }
    /**
     * Revisa si el jugador está colisionando con objetos NO sólidos (items)
     * para recogerlos.
     */
    public void revisarInteraccionItems() {
        
        ArrayList<GameObject> lista = gP.getManejadorObjetos().getListaGameObjects();
        
        // Hitbox actual del jugador
        Rectangle areaJugador = new Rectangle(
            this.mundoX + this.areaSolida.x, 
            this.mundoY + this.areaSolida.y,
            this.areaSolida.width, 
            this.areaSolida.height);

        // Bucle inverso para poder eliminar objetos de la lista de forma segura
        for (int i = lista.size() - 1; i >= 0; i--) {
            GameObject obj = lista.get(i);
            
            // El jugador no puede recogerse a si mismo.
            if(obj == this)
                continue;
            // Si el objeto ES sólido (colision=true), lo ignoramos.
            // (El DetectorColisiones ya se encargó de él).
            if(obj.getColision())
                continue;

            //Si es un item (colision=false), revisamos intersección
            Rectangle areaObjeto = new Rectangle(
                obj.getMundoX() + obj.getAreaSolida().x,
                obj.getMundoY() + obj.getAreaSolida().y,
                obj.getAreaSolida().width,
                obj.getAreaSolida().height);

            //El jugador está tocando el item?
            if (areaJugador.intersects(areaObjeto)) {
                //Si? Recogido.
                gP.setEstadoJuego(gP.getDialogueState());
                //Aquí va la logica para revisar que objeto recogio y
                //llamar al metodo necesario

                //Elimina el objeto del juego
                gP.getManejadorObjetos().removerGameObject(obj);
            }
        }
    }

    /**
     * Dibuja al jugador en la pantalla.
     * Selecciona el sprite correcto basado en la dirección y el estado de
     * la animación. Calcula la posición en pantalla relativa a la cámara.
     *
     * @param g2 El contexto gráfico (Graphics2D) sobre el que se dibuja.
     * @param camaraX La posición X actual de la cámara en el mundo.
     * @param camaraY La posición Y actual de la cámara en el mundo.
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

        //calculo de la posicion en pantalla
        //la posicion del jugador en pantalla es su posicion en el mundo menos la posicion de la esquina de la camara
        int jugadorPantallaX = this.mundoX - camaraX;
        int jugadorPantallaY = this.mundoY - camaraY;
        
        //dibuja el sprite seleccionado en las coordenadas calculadas
        g2.drawImage(sprite, jugadorPantallaX, jugadorPantallaY, gP.getTamTile(), gP.getTamTile(), null);

        // ==== LA SIGUIENTE SECCION DE CODIGO ES PARA DEPURACION ====
        g2.setColor(new Color(255, 0, 0, 100));
        
        // Calcula la posición exacta de la hitbox en la pantalla
        // Sumamos la posición relativa del área sólida (x, y) a la posición del jugador en pantalla
        int hitboxX = jugadorPantallaX + areaSolida.x;
        int hitboxY = jugadorPantallaY + areaSolida.y;
        
        //Dibuja el rectángulo relleno
        g2.fillRect(hitboxX, hitboxY, areaSolida.width, areaSolida.height);
        
        //Dibuja un borde blanco para que se vea mejor
        g2.setColor(Color.white);
        g2.drawRect(hitboxX, hitboxY, areaSolida.width, areaSolida.height);
    }

    //Getters y Setters
    public int getX(){
        return this.mundoX;
    }
    public int getY(){
        return this.mundoY;
    }
    public void setX(int valor){
        this.mundoX = valor;
    }
    public void setY(int valor){
        this.mundoY = valor;
    }
    public int getPantallaX(){
        return this.pantallaX;
    }
    public int getPantallaY(){
        return this.pantallaY;
    }
}
