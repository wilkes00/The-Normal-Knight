package entidad;

import javax.swing.ImageIcon;

public class Entidad {
    protected int x, y, velocidad;
    protected ImageIcon salto, dash, derecha, izquierda, cambioMovimiento, caida, ataque;
    protected ImageIcon idle;
    protected String direccion;
    protected int contadorSprites = 0;
    protected int numeroSprite = 1;
    protected int cambiaSprite = 10;
    
}
