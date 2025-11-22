package entidad;

import Main.GamePanel;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;
import javax.imageio.ImageIO;
/**
 * Subclase de Entidad. Representa a un enemigo dentro del juego.
 * Tiene un comportamiento unico implementado en el metodo accion.
 * Maneja la logica especifica de esta entidad.
 */
public class Enemigo extends Entidad {
    private int cont = 0;
    public Enemigo(GamePanel gP) {
        super(gP);
        this.colision = true;
        this.areaSolida = new java.awt.Rectangle(8, 16, 32, 32);
        this.direccion = "abajo";
        this.velocidad = 1;
        this.getSprites();
    }
    /**
     * Carga los sprites especificos del enemigo.
     */
    public void getSprites(){
        try {
            BufferedImage spritesheet = ImageIO.read(getClass().getResourceAsStream("/npcs/Kolyog.png"));

            int frameWidth = 24;
            int frameHeight = 24;

            // --- Recortar las imágenes del spritesheet ---
            // Movimiento hacia abajo
            abajo1= spritesheet.getSubimage(0 * frameWidth, 0 * frameHeight, frameWidth, frameHeight);
            abajo2= spritesheet.getSubimage(1 * frameWidth, 0 * frameHeight, frameWidth, frameHeight);

            // Movimiento hacia izq
            izquierda1 = spritesheet.getSubimage(0 * frameWidth, 1 * frameHeight, frameWidth, frameHeight);
            izquierda2 = spritesheet.getSubimage(1 * frameWidth, 1 * frameHeight, frameWidth, frameHeight);

            //Movimiento hacia la derecha
            derecha1 = spritesheet.getSubimage(0 * frameWidth, 2 * frameHeight, frameWidth, frameHeight);
            derecha2 = spritesheet.getSubimage(1 * frameWidth, 2 * frameHeight, frameWidth, frameHeight);

            //Movimiento hacia arriba
            arriba1 = spritesheet.getSubimage(0 * frameWidth, 3 * frameHeight, frameWidth, frameHeight);
            arriba2 = spritesheet.getSubimage(1 * frameWidth, 3 * frameHeight, frameWidth, frameHeight);
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * Define la logica de accion y movimiento del enemigo.
     * Debe implementarse en las subclases de Entidad,
     * con excepcion de la subclase Jugador.
     */
    @Override
    public void accion(){
         // Obtener posición del jugador
        int jugadorX = gP.getJugador().getMundoX();
        int jugadorY = gP.getJugador().getMundoY();
        
        // Calcular diferencias
        int deltaX = jugadorX - this.mundoX;
        int deltaY = jugadorY - this.mundoY;
        
        
        // Lógica para moverse hacia el jugador si lo observa
        if(observaJugador()){
            // Determinar dirección principal
            if(Math.abs(deltaX) > Math.abs(deltaY)){
                // Moverse horizontalmente
                if(deltaX > 0){
                    this.direccion = "derecha";
                    this.velocidad = 3;
                } else {
                    this.direccion = "izquierda";
                    this.velocidad = 3;
                }
            } else {
                // Moverse verticalmente
                if(deltaY > 0){
                    this.direccion = "abajo";
                    this.velocidad = 3;
                } else {
                    this.direccion = "arriba";
                    this.velocidad = 3;
                }
            }
        // Si no observa al jugador, se mueve aleatoriamente
        } else{
            this.velocidad = 1;
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
    /**
     * Verifica si el enemigo puede "ver" al jugador.
     * @return true si el jugador está dentro del rango de visión, false en caso contrario.
     */
    private boolean observaJugador() {
        int rangoVision = 200; // Distancia máxima para "ver" al jugador
        // Calcular distancia al jugador
        int distanciaX = Math.abs(gP.getJugador().getMundoX() - this.mundoX);
        int distanciaY = Math.abs(gP.getJugador().getMundoY() - this.mundoY);
        // Verificar si está dentro del rango de visión
        return distanciaX <= rangoVision && distanciaY <= rangoVision;
    }
}
