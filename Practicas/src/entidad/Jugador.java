package entidad;

import Main.GamePanel;
import Main.ManejadorTeclas;
import java.awt.Graphics2D;
import javax.swing.ImageIcon;

public class Jugador extends Entidad{
    private GamePanel gP;
    private ManejadorTeclas mT;
    private double dx = 0; // Velocidad horizontal (para mover izquierda/derecha)
    private double dy = 0; // Velocidad vertical (para salto y gravedad)
    // Banderas de estado
    private boolean enTransicion = false;
    private boolean mirandoIzquierda = false; // Para saber la última dirección

    // Contadores de animación
    private int contadorTransicion = 0;
    private final int DURACION_TRANSICION = 10;
    // Constantes de Física (ajustables para la sensación de juego)
    private static final double GRAVEDAD = 0.8; 
    private static final double FUERZA_SALTO = -15.0; // Negativo porque Y=0 es la parte superior de la pantalla
    private static final double VELOCIDAD_MAX_CAIDA = 12.0;
    private boolean enElSuelo = true;

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
    public void getSpritesJugador() {
    this.idle = new ImageIcon(getClass().getResource("/images/spritesjugador/_Idle.gif"));
    this.salto = new ImageIcon(getClass().getResource("/images/spritesjugador/__Jump.gif"));
    this.dash = new ImageIcon(getClass().getResource("/images/spritesjugador/__Dash.gif"));
    this.derecha = new ImageIcon(getClass().getResource("/images/spritesjugador/__Run.gif"));
    this.izquierda = new ImageIcon(getClass().getResource("/images/spritesjugador/__Run.gif"));
    this.cambioMovimiento = new ImageIcon(getClass().getResource("/images/spritesjugador/__TurnAround.gif"));
    this.caida = new ImageIcon(getClass().getResource("/images/spritesjugador/__Fall.gif"));
}
    public void update(){
        boolean movimiento = false;
        if (!enElSuelo){
            dy += GRAVEDAD;
            if (dy > VELOCIDAD_MAX_CAIDA) {
                dy = VELOCIDAD_MAX_CAIDA;
            }
        }
        
        if(mT.getTeclaSalto() && enElSuelo) {
			dy = FUERZA_SALTO;
            enElSuelo = false;
            movimiento = true;
            this.direccion = "salto";  
		}
        setY(getY() + (int) dy);
        // temporal
        if (getY() > 100) { // Asumiendo que el suelo está en y=100
            setY(100);
            enElSuelo = true;
            dy = 0;
        }

		if(mT.getTeclaDash()) {
            if(mirandoIzquierda) {
                setX(getX() - getVelocidad() * 2);
            } else
			    setX(getX() + getVelocidad() * 2);

            this.direccion = "dash";
            movimiento = true;
            enTransicion = false;
		}
		else if(mT.getTeclaDer()) {
            setX(getX() + getVelocidad());
            movimiento = true;
            if(mirandoIzquierda && enElSuelo){
                enTransicion = true;
                contadorTransicion = 0;
            }
            mirandoIzquierda = false;
            this.direccion = "derecha";
        }
		else if(mT.getTeclaIzq()) {
            setX(getX() - getVelocidad());
            movimiento = true;
            if(!mirandoIzquierda && enElSuelo){
                enTransicion = true;
                contadorTransicion = 0;
            }
            mirandoIzquierda = true;
            this.direccion = "izquierda";
		}

        if(!movimiento && enElSuelo)
            this.direccion = "idle";

        if(!enElSuelo &&  dy > 0)
            this.direccion = "caida";

        if(enTransicion){
            contadorTransicion++;
            if(contadorTransicion >= DURACION_TRANSICION){
                enTransicion = false;
                contadorTransicion = 0;
            }
        }
    }

    public void draw(Graphics2D g2){
        int anchoSprite = 180;  //ancho
        int altoSprite = 180; //alto
        java.awt.geom.AffineTransform old = g2.getTransform();
        if(enTransicion){
            if(mirandoIzquierda){
                g2.translate(getX() + anchoSprite, getY());
                g2.scale(-1, 1);
                g2.drawImage(this.cambioMovimiento.getImage(), 0, 0, anchoSprite, altoSprite, gP);
            }else{
                g2.drawImage(this.cambioMovimiento.getImage(), getX(), getY(), anchoSprite, altoSprite, gP);
            }
        }else{
            switch(this.direccion){
                case "idle":
                    if(mirandoIzquierda){
                        g2.translate(getX() + anchoSprite, getY());
                        g2.scale(-1, 1);
                        g2.drawImage(this.idle.getImage(), 0, 0, anchoSprite, altoSprite, gP);
                    }else{
                        g2.drawImage(this.idle.getImage(), getX(), getY(), anchoSprite, altoSprite, gP);
                    }
                    break;
                case "salto":
                    if(mirandoIzquierda){
                        g2.translate(getX() + anchoSprite, getY());
                        g2.scale(-1, 1);
                        g2.drawImage(this.salto.getImage(), 0, 0, anchoSprite, altoSprite, gP);
                    }else{
                        g2.drawImage(this.salto.getImage(), getX(), getY(), anchoSprite, altoSprite, gP);
                    }
                    break;
                case "dash":
                    if(mirandoIzquierda){
                        g2.translate(getX() + anchoSprite, getY());
                        g2.scale(-1, 1);
                        g2.drawImage(this.dash.getImage(), 0, 0, anchoSprite, altoSprite, gP);
                    }else{
                        g2.drawImage(this.dash.getImage(), getX(), getY(), anchoSprite, altoSprite, gP);
                    }
                    break;
                case "derecha":
                    g2.drawImage(this.derecha.getImage(), getX(), getY(), anchoSprite, altoSprite, gP);
                    break;
                case "izquierda":
                    g2.translate(getX() + anchoSprite, getY());
                    g2.scale(-1, 1);
                    g2.drawImage(this.izquierda.getImage(), 0, 0, anchoSprite, altoSprite, gP);
                    break;
                case "caida":
                    if(mirandoIzquierda){
                        g2.translate(getX() + anchoSprite, getY());
                        g2.scale(-1, 1);
                        g2.drawImage(this.caida.getImage(), 0, 0, anchoSprite, altoSprite, gP);
                    }else{
                        g2.drawImage(this.caida.getImage(), getX(), getY(), anchoSprite, altoSprite, gP);
                    }
                    break;
            }
        }
        
        g2.setTransform(old);
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
    public void saltar(){
        if (enElSuelo) {
            this.dy = FUERZA_SALTO;
            this.enElSuelo = false;
        }
    }
}
