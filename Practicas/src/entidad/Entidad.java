package entidad;

import java.awt.Image;

public class Entidad {
    protected int x, y, velocidad;
    protected Image salto, dash, derecha, izquierda;
    protected Image idle;
    protected String direccion;
    protected int contadorSprites = 0;
    protected int numeroSprite = 1;
    protected int cambiaSprite = 10;
    
}
