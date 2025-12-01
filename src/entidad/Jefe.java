package entidad;
import Main.GamePanel;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;
import javax.imageio.ImageIO;
/**
 * Representa a un jefe enemigo en el juego.
 * Extiende de Entidad e implementa la interfaz Llave.
 */
public class Jefe extends Entidad implements Llave {
    private int cont = 0;
    private boolean tieneLlave = false;
    private boolean atacando = false;
    private int contadorAtaque = 0;
    private final int duracionAtaque = 40; // Ataque más lento (40 frames)
    private final int cooldownAtaque = 120; // Tiempo entre ataques (2 segundos)
    private int contadorCooldown = 0;
    private BufferedImage ataqueIzquierda1, ataqueIzquierda2, ataqueIzquierda3;
    private BufferedImage ataqueDerecha1, ataqueDerecha2, ataqueDerecha3;

    public Jefe(GamePanel gP) {
        super(gP);
        this.colision = true;
        this.areaSolida = new java.awt.Rectangle(20, 24, 56, 56); // Hitbox más grande para el troll
        this.direccion = "abajo";
        this.velocidad = 1; // Movimiento muy lento
        this.vida = 8;
        this.vidaMax = 8;
        this.cambiaSprite = 15; // Animación más lenta
        this.getSprites();
    }
    /**
     * Carga los sprites especificos del jefe.
     */
    public void getSprites(){
        try {
            // Sprites de movimiento
            arriba1 = ImageIO.read(getClass().getResourceAsStream("/resources/npcs/troll_atras1.png"));
            arriba2 = ImageIO.read(getClass().getResourceAsStream("/resources/npcs/troll_atras2.png"));
            
            izquierda1 = ImageIO.read(getClass().getResourceAsStream("/resources/npcs/troll_izquierda1.png"));
            izquierda2 = ImageIO.read(getClass().getResourceAsStream("/resources/npcs/troll_izquierda2.png"));
            
            derecha1 = ImageIO.read(getClass().getResourceAsStream("/resources/npcs/troll_derecho 1.png"));
            derecha2 = ImageIO.read(getClass().getResourceAsStream("/resources/npcs/troll_derecha2.png"));
            
            // Usar sprites de derecha para abajo (espejo horizontal)
            abajo1 = derecha1;
            abajo2 = derecha2;
            
            // Sprites de ataque
            ataqueIzquierda1 = izquierda1;
            ataqueIzquierda2 = ImageIO.read(getClass().getResourceAsStream("/resources/npcs/troll_2_ataqueizquierda.png"));
            ataqueIzquierda3 = ImageIO.read(getClass().getResourceAsStream("/resources/npcs/troll_3_ataqueizquierda.png"));
            
            ataqueDerecha1 = derecha1;
            ataqueDerecha2 = ImageIO.read(getClass().getResourceAsStream("/resources/npcs/troll_2_ataquederecha.png"));
            ataqueDerecha3 = ImageIO.read(getClass().getResourceAsStream("/resources/npcs/troll_3_ataquederecha.png"));
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * Define la logica de accion y movimiento del jefe.
     */
    @Override
    public void accion(){
        // Obtener posición del jugador
        int jugadorX = gP.getJugador().getMundoX();
        int jugadorY = gP.getJugador().getMundoY();
        
        // Calcular diferencias
        int deltaX = jugadorX - this.mundoX;
        int deltaY = jugadorY - this.mundoY;
        
        // Calcular distancia al jugador
        double distancia = Math.sqrt(deltaX * deltaX + deltaY * deltaY);
        
        // Si está cerca del jugador, intentar atacar
        if(distancia < 80 && !atacando && contadorCooldown == 0){
            atacando = true;
            contadorAtaque = 0;
            contadorCooldown = cooldownAtaque;
        }
        
        // Lógica para moverse hacia el jugador si lo observa
        if(observaJugador() && !atacando){
            // Determinar dirección principal
            if(Math.abs(deltaX) > Math.abs(deltaY)){
                // Moverse horizontalmente
                if(deltaX > 0){
                    this.direccion = "derecha";
                } else {
                    this.direccion = "izquierda";
                }
            } else {
                // Moverse verticalmente
                if(deltaY > 0){
                    this.direccion = "abajo";
                } else {
                    this.direccion = "arriba";
                }
            }
        // Si no observa al jugador, se mueve aleatoriamente
        } else if(!atacando){
            cont++;
            if(cont == 180){ // Cambio de dirección más espaciado
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
     * Verifica si el jefe puede "ver" al jugador.
     * @return true si el jugador está dentro del rango de visión, false en caso contrario.
     */
    private boolean observaJugador() {
        int rangoVision = 250; // Rango de visión más amplio
        // Calcular distancia al jugador
        int distanciaX = Math.abs(gP.getJugador().getMundoX() - this.mundoX);
        int distanciaY = Math.abs(gP.getJugador().getMundoY() - this.mundoY);
        // Verificar si está dentro del rango de visión
        return distanciaX <= rangoVision && distanciaY <= rangoVision;
    }
    
    @Override
    public void update() {
        accion();
        
        // Actualizar ataque
        if(atacando){
            contadorAtaque++;
            if(contadorAtaque >= duracionAtaque){
                atacando = false;
                contadorAtaque = 0;
            }
        }
        
        // Actualizar cooldown
        if(contadorCooldown > 0){
            contadorCooldown--;
        }
        
        // Movimiento solo si no está atacando
        if(!atacando){
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
        
        // Actualizar contador de invulnerabilidad
        if(invulnerable){
            contadorInvulnerabilidad++;
            if(contadorInvulnerabilidad >= tiempoInvulnerabilidad){
                invulnerable = false;
                contadorInvulnerabilidad = 0;
            }
        }
        
        // Si está atacando, verificar si golpea al jugador
        if(atacando && contadorAtaque == 10){ // Golpea en el frame 10 del ataque
            atacarJugador();
        }
    }
    
    /**
     * Verifica si el jefe golpea al jugador con su espada durante el ataque.
     */
    private void atacarJugador() {
        // Calcular el área de ataque (espada) frente al jefe
        int ataqueX = this.mundoX + this.areaSolida.x;
        int ataqueY = this.mundoY + this.areaSolida.y;
        int ataqueAncho = this.areaSolida.width;
        int ataqueAlto = this.areaSolida.height;
        
        // Extender el rango de ataque según la dirección
        int rangoEspada = 30; // Píxeles de alcance de la espada 
        
        switch(this.direccion){
            case "arriba":
                ataqueY -= rangoEspada;
                ataqueAlto += rangoEspada;
                break;
            case "abajo":
                ataqueAlto += rangoEspada;
                break;
            case "izquierda":
                ataqueX -= rangoEspada;
                ataqueAncho += rangoEspada;
                break;
            case "derecha":
                ataqueAncho += rangoEspada;
                break;
        }
        
        java.awt.Rectangle areaAtaque = new java.awt.Rectangle(ataqueX, ataqueY, ataqueAncho, ataqueAlto);
        
        // Verificar si el jugador está en el área de ataque
        entidad.Jugador jugador = gP.getJugador();
        java.awt.Rectangle areaJugador = new java.awt.Rectangle(
            jugador.getMundoX() + jugador.getAreaSolida().x,
            jugador.getMundoY() + jugador.getAreaSolida().y,
            jugador.getAreaSolida().width,
            jugador.getAreaSolida().height);
        
        // Si hay colisión, el jugador recibe daño
        if(areaAtaque.intersects(areaJugador) && jugador.getMapa() == this.getMapa()){
            jugador.recibirDamage(2); // El jefe hace 2 puntos de daño
        }
    }
    
    @Override
    public void draw(Graphics2D g2, int camaraX, int camaraY){
        BufferedImage sprite = null;
        
        // Si está atacando, usar sprites de ataque
        if(atacando){
            int spriteAtaque = (contadorAtaque / 13) % 3 + 1; // Alterna cada 13 frames entre 3 sprites
            
            switch(this.direccion){
                case "izquierda":
                    if(spriteAtaque == 1)
                        sprite = ataqueIzquierda1;
                    else if(spriteAtaque == 2)
                        sprite = ataqueIzquierda2;
                    else
                        sprite = ataqueIzquierda3;
                    break;
                case "derecha":
                    if(spriteAtaque == 1)
                        sprite = ataqueDerecha1;
                    else if(spriteAtaque == 2)
                        sprite = ataqueDerecha2;
                    else
                        sprite = ataqueDerecha3;
                    break;
                default:
                    // Para arriba y abajo, usar sprites normales
                    sprite = (this.numeroSprite == 1) ? this.abajo1 : this.abajo2;
                    break;
            }
        }
        // Si no está atacando, usar sprites normales
        else {
            switch(this.direccion){
                case "arriba":
                    sprite = (this.numeroSprite == 1) ? this.arriba1 : this.arriba2;
                    break;
                case "abajo":
                    sprite = (this.numeroSprite == 1) ? this.abajo1 : this.abajo2;
                    break;
                case "izquierda":
                    sprite = (this.numeroSprite == 1) ? this.izquierda1 : this.izquierda2;
                    break;
                case "derecha":
                    sprite = (this.numeroSprite == 1) ? this.derecha1 : this.derecha2;
                    break;
            }
            
            // Animación de sprites
            this.contadorSprites++;
            if(this.contadorSprites > this.cambiaSprite){
                if(this.numeroSprite == 1)
                    this.numeroSprite = 2;
                else
                    this.numeroSprite = 1;
                this.contadorSprites = 0;
            }
        }
        
        //calculo de la posicion en pantalla
        int jefePantallaX = this.mundoX - camaraX;
        int jefePantallaY = this.mundoY - camaraY;
        
        // Aplicar transparencia si está invulnerable
        if(invulnerable){
            g2.setComposite(java.awt.AlphaComposite.getInstance(java.awt.AlphaComposite.SRC_OVER, 0.5f));
        }
        
        //dibuja el sprite seleccionado - más grande que los enemigos normales
        g2.drawImage(sprite, jefePantallaX, jefePantallaY, gP.getTamTile() * 2, gP.getTamTile() * 2, null);
        
        // Restaurar opacidad normal
        if(invulnerable){
            g2.setComposite(java.awt.AlphaComposite.getInstance(java.awt.AlphaComposite.SRC_OVER, 1.0f));
        }
    }
    
    @Override
    public void setLlave(boolean tieneLlave){
        this.tieneLlave = tieneLlave;
    }
    @Override
    public boolean getLlave(){
        return this.tieneLlave;
    }
}
