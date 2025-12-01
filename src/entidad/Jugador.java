package entidad;

import Main.GamePanel;
import Main.ManejadorTeclas;
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
 * en la pantalla, ademas de la interacción con objetos y NPCs.
 *
 */
public class Jugador extends Entidad implements Llave{
    private ManejadorTeclas mT;
    private final int pantallaX, pantallaY;
    private boolean tieneLlave = false;
    private boolean atacando = false;
    private int contadorAtaque = 0;
    private final int duracionAtaque = 20; // Duración de la animación de ataque (20 frames)

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
        this.areaSolida = new Rectangle(20, 30, 25, 36);
        configuracionInicial();
        getSpritesJugador();
    }
    /**
     * Establece los valores iniciales por defecto del jugador, como su
     * posición en el mundo, velocidad y dirección.
     */
    public void configuracionInicial(){
        this.mundoX = gP.getTamTile() * 22;
        this.mundoY = gP.getTamTile() * 32;
        this.velocidad = 4;
        this.direccion = "abajo";
        this.colision = true;
        this.setMapa(gP.getMapaActual());
        this.vida = this.vidaMax; // Inicializar la vida del jugador
        this.tiempoInvulnerabilidad = 60; // 1 segundo para el jugador
    }
    /**
     * Carga las imágenes (sprites) del jugador para las diferentes
     * direcciones y animaciones de movimiento desde los recursos.
     */
    public void getSpritesJugador(){
        try {
            // Carga el spritesheet completo
            BufferedImage spritesheetJugador = ImageIO.read(getClass().getResourceAsStream("/resources/spritesjugador/player_spritesheet.png"));

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
            arriba3 = spritesheetJugador.getSubimage(2 * frameWidth, 3 * frameHeight, frameWidth, frameHeight);
            
            // Tercer sprite para abajo
            abajo3 = spritesheetJugador.getSubimage(2 * frameWidth, 0 * frameHeight, frameWidth, frameHeight);
            
            // Tercer sprite para izquierda
            izquierda3 = spritesheetJugador.getSubimage(2 * frameWidth, 1 * frameHeight, frameWidth, frameHeight);
            
            // Tercer sprite para derecha
            derecha3 = spritesheetJugador.getSubimage(2 * frameWidth, 2 * frameHeight, frameWidth, frameHeight);
            
            // Sprites de ataque
            ataqueAbajo1 = spritesheetJugador.getSubimage(3 * frameWidth, 0 * frameHeight, frameWidth, frameHeight);
            ataqueAbajo2 = spritesheetJugador.getSubimage(4 * frameWidth, 0 * frameHeight, frameWidth, frameHeight);
            
            ataqueIzquierda1 = spritesheetJugador.getSubimage(3 * frameWidth, 1 * frameHeight, frameWidth, frameHeight);
            ataqueIzquierda2 = spritesheetJugador.getSubimage(4 * frameWidth, 1 * frameHeight, frameWidth, frameHeight);
            
            ataqueDerecha1 = spritesheetJugador.getSubimage(3 * frameWidth, 2 * frameHeight, frameWidth, frameHeight);
            ataqueDerecha2 = spritesheetJugador.getSubimage(4 * frameWidth, 2 * frameHeight, frameWidth, frameHeight);
            
            ataqueArriba1 = spritesheetJugador.getSubimage(3 * frameWidth, 3 * frameHeight, frameWidth, frameHeight);
            ataqueArriba2 = spritesheetJugador.getSubimage(4 * frameWidth, 3 * frameHeight, frameWidth, frameHeight);
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
                else if(this.numeroSprite == 2)
                    this.numeroSprite = 3;
                else
                    this.numeroSprite = 1;
                this.contadorSprites = 0;
            }
        }
        // Lógica de interacción (NUEVO)
        if(mT.getTeclaE()){
            interactuarObjeto();
            mT.setTeclaE(false); // Desactivar para que no se ejecute 60 veces por segundo
        }
        
        // Lógica de ataque
        if(mT.getTeclaK() && !atacando){
            atacando = true;
            contadorAtaque = 0;
        }
        
        // Actualizar animación de ataque
        if(atacando){
            contadorAtaque++;
            // Detectar colisión con enemigos al inicio del ataque
            if(contadorAtaque == 1){
                atacarEnemigos();
            }
            if(contadorAtaque >= duracionAtaque){
                atacando = false;
                contadorAtaque = 0;
            }
        }
        
        // despues de moverse verifica si esta encima de un item
        revisarInteraccionItems();
        
        // Verificar colisión con enemigos
        revisarColisionEnemigos();
        
        // Actualizar contador de invulnerabilidad
        if(invulnerable){
            contadorInvulnerabilidad++;
            if(contadorInvulnerabilidad >= tiempoInvulnerabilidad){
                invulnerable = false;
                contadorInvulnerabilidad = 0;
            }
        }
    }
    /**
     * Gestiona la interacción del jugador con objetos en el juego.
     * Revisa si el jugador está frente a un objeto interactuable (como cofres o NPCs)
     * basado en su dirección actual, y llama al método de interacción correspondiente.
     */
    public void interactuarObjeto(){
        // Calculamos un área pequeña frente al jugador para ver qué está tocando
        // Basado en la dirección actual
        int interactuarX = this.mundoX + this.areaSolida.x;
        int interactuarY = this.mundoY + this.areaSolida.y;
        int interactuarAncho = this.areaSolida.width;
        int interactuarAlto = this.areaSolida.height;

        switch(direccion){
            case "arriba": interactuarY -= getVelocidad(); break;
            case "abajo": interactuarY += getVelocidad(); break;
            case "izquierda": interactuarX -= getVelocidad(); break;
            case "derecha": interactuarX += getVelocidad(); break;
        }

        Rectangle areaFutura = new Rectangle(interactuarX, interactuarY, interactuarAncho, interactuarAlto);

        // Buscar si hay un objeto en esa área
        for(GameObject obj : gP.getManejadorObjetos().getListaGameObjects()){
            // Ignoramos objetos no solidos (como pociones, esos se recogen pisandolos)
            // O podemos incluirlos si quieres recogerlos con E
            if(obj == this) continue;

            Rectangle areaObjeto = new Rectangle(
                obj.getMundoX() + obj.getAreaSolida().x,
                obj.getMundoY() + obj.getAreaSolida().y,
                obj.getAreaSolida().width,
                obj.getAreaSolida().height);

            if(obj.getMapa() == this.getMapa() && areaFutura.intersects(areaObjeto)){
                // Si chocamos con un Cofre, interactuamos
                if(obj instanceof Cofre){
                    ((Cofre)obj).interactuar();
                }
                //Interactuar con NPCs
                else if(obj instanceof Entidad){
                    if(((Entidad)obj).dialogos != null && ((Entidad)obj).dialogos[0] != null)
                        ((Entidad)obj).hablar();
                }
                //Para otros tipos de objetos, agregar aquí
            }
        }
    }
    /**
     * Detecta y daña a los enemigos dentro del rango de ataque.
     */
    public void atacarEnemigos() {
        ArrayList<GameObject> lista = gP.getManejadorObjetos().getListaGameObjects();
        
        // Calcular el área de ataque frente al jugador
        int ataqueX = this.mundoX + this.areaSolida.x;
        int ataqueY = this.mundoY + this.areaSolida.y;
        int ataqueAncho = this.areaSolida.width;
        int ataqueAlto = this.areaSolida.height;
        
        // Extender el rango de ataque según la dirección
        int rangoAtaque = 30; // Píxeles de alcance del ataque
        
        switch(this.direccion){
            case "arriba":
                ataqueY -= rangoAtaque;
                ataqueAlto += rangoAtaque;
                break;
            case "abajo":
                ataqueAlto += rangoAtaque;
                break;
            case "izquierda":
                ataqueX -= rangoAtaque;
                ataqueAncho += rangoAtaque;
                break;
            case "derecha":
                ataqueAncho += rangoAtaque;
                break;
        }
        
        Rectangle areaAtaque = new Rectangle(ataqueX, ataqueY, ataqueAncho, ataqueAlto);
        
        // Lista para almacenar enemigos a eliminar
        ArrayList<GameObject> enemigosAEliminar = new ArrayList<>();
        
        // Revisar colisión con enemigos
        for (GameObject obj : lista) {
            if(obj instanceof Enemigo || obj instanceof Jefe) {
                Entidad enemigo = (Entidad)obj;
                
                // Verificar que estén en el mismo mapa
                if(enemigo.getMapa() != this.getMapa())
                    continue;
                
                // Calcular área del enemigo
                Rectangle areaEnemigo = new Rectangle(
                    enemigo.getMundoX() + enemigo.getAreaSolida().x,
                    enemigo.getMundoY() + enemigo.getAreaSolida().y,
                    enemigo.getAreaSolida().width,
                    enemigo.getAreaSolida().height);
                
                // Si hay colisión con el ataque
                if(areaAtaque.intersects(areaEnemigo)) {
                    // Aplicar daño al enemigo solo si no es invulnerable
                    if(!enemigo.invulnerable){
                        enemigo.setVida(enemigo.getVida() - 1);
                        enemigo.invulnerable = true;
                        enemigo.contadorInvulnerabilidad = 0;
                        
                        // Si el enemigo murió, marcarlo para eliminación
                        if(enemigo.getVida() <= 0){
                            enemigosAEliminar.add(enemigo);
                            gP.playSoundEffect(5); // Sonido de enemigo derrotado
                            System.out.println("Enemigo derrotado!");
                        } else {
                            gP.playSoundEffect(4); // Sonido de golpear enemigo
                            System.out.println("Enemigo golpeado! Vida: " + enemigo.getVida() + "/" + enemigo.getVidaMax());
                        }
                    }
                }
            }
        }
        
        // Eliminar enemigos derrotados después de iterar
        for(GameObject enemigo : enemigosAEliminar){
            gP.getManejadorObjetos().removerGameObject(enemigo);
        }
    }
    
    /**
     * Revisa si el jugador está colisionando con enemigos y aplica daño si no es invulnerable.
     */
    public void revisarColisionEnemigos() {
        if(invulnerable) return; // Si es invulnerable, no recibe daño
        
        ArrayList<GameObject> lista = gP.getManejadorObjetos().getListaGameObjects();
        
        // Hitbox actual del jugador
        Rectangle areaJugador = new Rectangle(
            this.mundoX + this.areaSolida.x, 
            this.mundoY + this.areaSolida.y,
            this.areaSolida.width, 
            this.areaSolida.height);

        for (GameObject obj : lista) {
            // Verificar si el objeto es un enemigo
            if(obj instanceof Enemigo || obj instanceof Jefe) {
                Entidad enemigo = (Entidad)obj;
                
                // Verificar que estén en el mismo mapa
                if(enemigo.getMapa() != this.getMapa())
                    continue;
                
                // Calcular área del enemigo
                Rectangle areaEnemigo = new Rectangle(
                    enemigo.getMundoX() + enemigo.getAreaSolida().x,
                    enemigo.getMundoY() + enemigo.getAreaSolida().y,
                    enemigo.getAreaSolida().width,
                    enemigo.getAreaSolida().height);
                
                // Expandir ligeramente las áreas para detectar contacto
                areaJugador.grow(2, 2);  // Expande 2 píxeles en cada dirección
                areaEnemigo.grow(2, 2);
                
                // Si hay colisión, recibir daño
                if(areaJugador.intersects(areaEnemigo)) {
                    recibirDamage(1);
                    break; // Solo recibir daño de un enemigo por frame
                }
            }
        }
    }
    
    /**
     * El jugador recibe daño y se vuelve invulnerable temporalmente.
     * @param cantidad La cantidad de daño a recibir.
     */
    public void recibirDamage(int cantidad) {
        this.vida -= cantidad;
        this.invulnerable = true;
        this.contadorInvulnerabilidad = 0;
        
        gP.playSoundEffect(3);
        
        // Verificar si el jugador murió
        if(this.vida <= 0) {
            this.vida = 0;
            // Aquí puedes agregar lógica de game over
            System.out.println("El jugador ha sido derrotado!");
        }
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
            
            // Verificar que el objeto esté en el mismo mapa que el jugador
            if(obj.getMapa() != this.getMapa())
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
                // Determinar qué tipo de objeto se recogió y mostrar mensaje
                String nombreObjeto = obj.getClass().getSimpleName();
                if(obj instanceof Pocion){
                    // Curar al jugador 1 punto de vida
                    if(this.vida < this.vidaMax){
                        this.vida += 1;
                        gP.getIU().mostrarMensaje("¡Poción recogida! +1 Vida");
                        gP.playSoundEffect(6);
                    } else {
                        gP.getIU().mostrarMensaje("¡Vida al máximo!");
                    }
                } else if(obj instanceof Llave){
                    gP.getIU().mostrarMensaje("¡Llave obtenida!");
                    this.setLlave(true);
                } else {
                    gP.getIU().mostrarMensaje(nombreObjeto + " recogido");
                }

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
        
        // Si está atacando, usar sprites de ataque
        if(atacando){
            // Alterna entre los dos sprites de ataque
            int spriteAtaque = (contadorAtaque / 10) % 2 + 1; // Alterna cada 10 frames
            
            switch(this.direccion){
                case "arriba":
                    sprite = (spriteAtaque == 1) ? ataqueArriba1 : ataqueArriba2;
                    break;
                case "abajo":
                    sprite = (spriteAtaque == 1) ? ataqueAbajo1 : ataqueAbajo2;
                    break;
                case "izquierda":
                    sprite = (spriteAtaque == 1) ? ataqueIzquierda1 : ataqueIzquierda2;
                    break;
                case "derecha":
                    sprite = (spriteAtaque == 1) ? ataqueDerecha1 : ataqueDerecha2;
                    break;
            }
        }
        // Si no está atacando, usar sprites normales
        else {
            switch(this.direccion){
                case "arriba":
                    if(this.numeroSprite == 1)
                        sprite = this.arriba1;
                    else if(this.numeroSprite == 2)
                        sprite = this.arriba2;
                    else if(this.numeroSprite == 3)
                        sprite = this.arriba3;
                    break;
                case "abajo":
                    if(this.numeroSprite == 1)
                        sprite = this.abajo1;
                    else if(this.numeroSprite == 2)
                        sprite = this.abajo2;
                    else if(this.numeroSprite == 3)
                        sprite = this.abajo3;
                    break;
                case "izquierda":
                    if(this.numeroSprite == 1)
                        sprite = this.izquierda1;
                    else if(this.numeroSprite == 2)
                        sprite = this.izquierda2;
                    else if(this.numeroSprite == 3)
                        sprite = this.izquierda3;
                    break;
                case "derecha":
                    if(this.numeroSprite == 1)
                        sprite = this.derecha1;
                    else if(this.numeroSprite == 2)
                        sprite = this.derecha2;
                    else if(this.numeroSprite == 3)
                        sprite = this.derecha3;
                    break;
            }
        }

        //calculo de la posicion en pantalla
        //la posicion del jugador en pantalla es su posicion en el mundo menos la posicion de la esquina de la camara
        int jugadorPantallaX = this.mundoX - camaraX;
        int jugadorPantallaY = this.mundoY - camaraY;
        
        // Efecto de parpadeo cuando es invulnerable
        if(invulnerable){
            // Parpadear cada 10 frames (alternar visible/invisible)
            if(contadorInvulnerabilidad / 10 % 2 == 0){
                //dibuja el sprite seleccionado en las coordenadas calculadas
                g2.drawImage(sprite, jugadorPantallaX, jugadorPantallaY, gP.getTamTile() + 20, gP.getTamTile() + 20, null);
            }
            // Si no, no dibuja nada (invisible)
        } else {
            //dibuja el sprite seleccionado en las coordenadas calculadas
            g2.drawImage(sprite, jugadorPantallaX, jugadorPantallaY, gP.getTamTile() + 20, gP.getTamTile() + 20, null);
        }

        /* // ==== LA SIGUIENTE SECCION DE CODIGO ES PARA DEPURACION ====
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
        */ //=========================================================
    }
    /**
     * Devuelve si el jugador tiene una llave.
     */
    @Override
    public boolean getLlave() {
        return this.tieneLlave;
    }
    /**
     * Establece si el jugador tiene una llave.
     */
    @Override
    public void setLlave(boolean tieneLlave) {
        this.tieneLlave = tieneLlave;
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
