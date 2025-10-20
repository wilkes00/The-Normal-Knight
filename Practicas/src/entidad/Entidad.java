package entidad;

import java.awt.image.BufferedImage;

public class Entidad {
    protected int mundoX, mundoY, velocidad;
    protected BufferedImage arriba1, arriba2, abajo1, abajo2, derecha1, derecha2, izquierda1, izquierda2;
    protected String direccion;
    protected int contadorSprites = 0;
    protected int numeroSprite = 1;
    protected int cambiaSprite = 10;
    
}
