package Main;

import entidad.Heart;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;
/**
 * Gestiona la interfaz de usuario (UI) del juego.
 * Se encarga de dibujar elementos en pantalla como mensajes,
 * transiciones y otros componentes visuales relacionados con la UI.
 * 
 */
public class InterfazUsuario {
    GamePanel gP;
    ControladorEventos cE;
    Graphics2D g2;
    Font pixelFont, txtFont;
    Heart heart;
    BufferedImage cursor;
    private boolean estadoMensaje = false;
    private String mensaje = "";
    private int contadorMensaje = 0;
    private final int tiempoMensaje = 180; // 3 segundos a 60 FPS
    private String dialogoActual = "";
    private final int MaxOpacidad = 50;
    private int opacidadTransicion = 0;
    private boolean juegoTerminado = false;
    private int numOpcion = 0;
    private boolean mostrandoControles = false;
    /**
     * Constructor de la InterfazUsuario.
     * @param gP referencia al GamePanel principal.
     * @param cE referencia al ControladorEventos.
     */
    public InterfazUsuario(GamePanel gP, ControladorEventos cE){
        this.gP = gP;
        this.cE = cE;
        try {
            InputStream f = getClass().getResourceAsStream("/resources/fonts/PixelScriptRegular-0WnDG.otf");
            pixelFont = Font.createFont(Font.TRUETYPE_FONT, f);
            f = getClass().getResourceAsStream("/resources/fonts/dogicapixel.ttf");
            txtFont = Font.createFont(Font.TRUETYPE_FONT, f);
            cursor = ImageIO.read(getClass().getResourceAsStream("/resources/menus/cursor.png"));
        } catch (FontFormatException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //Inicializa los corazones de vida
        heart = new Heart(gP);
    }
    /**
     * Dibuja los elementos de la interfaz de usuario en pantalla.
     * @param g2 el contexto gráfico (Graphics2D) sobre el que se dibuja.
     */
    public void draw(Graphics2D g2){
        this.g2 = g2;
        
         //  ==== Coordenadas del jugador DEPURACION ====
        g2.setFont(txtFont.deriveFont(Font.PLAIN, 18F));
        g2.setColor(Color.white);
        int renJugador = gP.jugador.getMundoY() / gP.getTamTile();
        int colJugador = gP.jugador.getMundoX() / gP.getTamTile();
        g2.drawString("Col: " + colJugador + " Ren: " + renJugador, 20, 100);
         // ============================================
        g2.setFont(pixelFont);
        g2.setColor(Color.white);
        
        

        //Pantalla de inicio del juego
        if(gP.getEstadoJuego() == gP.getStartState()){
            drawPantallaInicio();
        }
        //Pantalla de ayuda
        else if(gP.getEstadoJuego() == gP.getHelpState()){
            drawPantallaAyuda();
        }
        //Transicion entre mapas    
        else if(gP.getEstadoJuego() == gP.getTransitionState()){
            drawTransicion();
        }
        else if(gP.getEstadoJuego() == gP.getPlayState()){
            drawVidaJugador();
            drawMensaje();
        }
        //Ventana de dialogo
        else if(gP.getEstadoJuego() == gP.getDialogueState()){
            drawVidaJugador();
            dibujaPantallaDialogo();
            drawMensaje();
        }
        else if(gP.getEstadoJuego() == gP.getPauseState()){
            drawVidaJugador();
            if(mostrandoControles){
                drawControlesPausa();
            } else {
                drawMenuPausa();
            }
            drawMensaje();
        }
        //Pantalla de Game Over
        else if(gP.getEstadoJuego() == gP.getGameOverState()){
            drawPantallaGameOver();
        }
            
    }
    /**
     * Dibuja la pantalla de inicio del juego.
     * Muestra el título del juego y las opciones del menú.
     */
    public void drawPantallaInicio(){
        //Fondo
        BufferedImage img = null;
        try {
            img = ImageIO.read(getClass().getResourceAsStream("/resources/menus/Menu-nuevo.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        g2.drawImage(img, 0, 0, gP.getAnchoPantalla(), gP.getAltoPantalla(), null);
        // Titulo del juego
        g2.setFont(g2.getFont().deriveFont(Font.TRUETYPE_FONT, 86F));
        String txt = "The Normal Knight";
        int x = getXCentrado(txt) - 15;
        int y = gP.getTamTile() * 5;
        //Sombra
        g2.setColor(Color.gray);
        g2.drawString(txt, x+4, y+4);
        //Texto
        g2.setColor(Color.white);
        g2.drawString(txt, x, y);
        //Menu
        g2.setFont(g2.getFont().deriveFont(Font.TRUETYPE_FONT, 48F));
        txt = "Nuevo Juego";
        x = getXCentrado(txt);
        y += gP.getTamTile() * 4;
        //sombra
        g2.setColor(Color.gray);
        g2.drawString(txt, x+4, y+4);
        //texto
        g2.setColor(Color.white);
        g2.drawString(txt, x, y);
        if(numOpcion == 0)
            g2.drawImage(cursor, x - gP.getTamTile(), y - gP.getTamTile(), gP.getTamTile(), gP.getTamTile(), null);

        txt = "Ayuda";
        x = getXCentrado(txt);
        y += gP.getTamTile();
        //sombra
        g2.setColor(Color.gray);
        g2.drawString(txt, x+4, y+4);
        //texto
        g2.setColor(Color.white);
        g2.drawString(txt, x, y);
        if(numOpcion == 1)
            g2.drawImage(cursor, x - gP.getTamTile(), y - gP.getTamTile(), gP.getTamTile(), gP.getTamTile(), null);


        txt = "Salir";
        x = getXCentrado(txt);
        y += gP.getTamTile();
        //sombra
        g2.setColor(Color.gray);
        g2.drawString(txt, x+4, y+4);
        //texto
        g2.setColor(Color.white);
        g2.drawString(txt, x, y);
        if(numOpcion == 2)
            g2.drawImage(cursor, x - gP.getTamTile(), y - gP.getTamTile(), gP.getTamTile(), gP.getTamTile(), null);


    }
    /**
     * Dibuja la vida del jugador en forma de corazones.
     */
    public void drawVidaJugador(){
        int x = gP.getTamTile() / 2;
        int y = gP.getTamTile() / 2;
        int i = 0;
        //Dibuja corazones completos
        while(i < gP.jugador.getVida() / 2){
            g2.drawImage(heart.getFullHeart(), x, y, gP.getTamTile(), gP.getTamTile(), null);
            i++;
            x += gP.getTamTile();
        }
        //Dibuja medio corazon si la vida es impar
        if(gP.getJugador().getVida() % 2 == 1){
            g2.drawImage(heart.getHalfHeart(), x, y, gP.getTamTile(), gP.getTamTile(), null);
            i++;
            x += gP.getTamTile();
        }
        //Dibuja corazones vacios
        while(i < gP.getJugador().getVidaMax() / 2){
            g2.drawImage(heart.getEmptyHeart(), x, y, gP.getTamTile(), gP.getTamTile(), null);
            i++;
            x += gP.getTamTile();
        }
       
    }
    /**
     * Dibuja la pantalla de ayuda del juego.
     * Muestra los controles y las instrucciones básicas.
     */
    public void drawPantallaAyuda(){
        //Fondo
        BufferedImage img = null;
        try {
            img = ImageIO.read(getClass().getResourceAsStream("/resources/menus/menu_controles.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        g2.drawImage(img, 0, 0, gP.getAnchoPantalla(), gP.getAltoPantalla(), null);
    }
    /**
     * Dibuja una transición de pantalla oscura.
     * Incrementa la opacidad de un rectangulo negro hasta cubrir toda la pantalla.
     * Despues llama a cE.teleport donde se gestiona la logica de posicion del jugador
     * en el mapa y coordenadas
     */
    public void drawTransicion(){
        opacidadTransicion++;
        g2.setColor(new Color(0,0,0, opacidadTransicion*5));
        g2.fillRect(0, 0, gP.getAnchoPantalla(), gP.getAltoPantalla());
        if(opacidadTransicion >= MaxOpacidad){
            opacidadTransicion = 0;
            gP.setEstadoJuego(gP.getPlayState());
            cE.teleport();
        }
    }
    /**
     * Dibuja la pantalla de Game Over.
     */
    public void drawPantallaGameOver(){
        // Fondo oscuro
        g2.setColor(new Color(0, 0, 0, 200));
        g2.fillRect(0, 0, gP.getAnchoPantalla(), gP.getAltoPantalla());
        
        // Texto "HAS MUERTO"
        g2.setFont(pixelFont.deriveFont(Font.BOLD, 72F));
        g2.setColor(Color.RED);
        String texto = "HAS MUERTO";
        int x = getXCentrado(texto);
        int y = gP.getAltoPantalla() / 2 - gP.getTamTile() * 2;
        
        // Sombra del texto
        g2.setColor(Color.BLACK);
        g2.drawString(texto, x + 5, y + 5);
        g2.setColor(Color.RED);
        g2.drawString(texto, x, y);
        
        // Texto "ENTER para continuar"
        g2.setFont(txtFont.deriveFont(Font.PLAIN, 28F));
        g2.setColor(Color.WHITE);
        texto = "Presiona ENTER para volver al menu";
        x = getXCentrado(texto);
        y += gP.getTamTile() * 3;
        g2.drawString(texto, x, y);
    }
    /**
     * Dibuja la ventana de diálogo en pantalla.
     */
    public void dibujaPantallaDialogo(){
        int x = gP.getTamTile() * 2;
        int y = gP.getTamTile() / 2;
        int alto = gP.getTamTile() * 4;
        int ancho = gP.getAnchoPantalla() - (gP.getTamTile() * 4);
        dibujaSubVentana(x, y, alto, ancho);

        g2.setFont(txtFont.deriveFont(Font.TRUETYPE_FONT, 22F));
        x += gP.getTamTile();
        y += gP.getTamTile();
        
        // Dividir el diálogo en líneas si contiene \n
        String[] lineas = dialogoActual.split("\n");
        for(String linea : lineas){
            g2.drawString(linea, x, y);
            y += 30; // Espaciado entre líneas
        }
    }
    /**
     * Dibuja una subventana con bordes redondeados.
     * @param x la posición X de la subventana
     * @param y la posición Y de la subventana
     * @param alto la altura de la subventana
     * @param ancho el ancho de la subventana
     */
    public void dibujaSubVentana(int x, int y, int alto, int ancho){
        Color c = new Color(0,0, 0, 200);
        g2.setColor(c);
        g2.fillRoundRect(x, y, ancho, alto, 35, 35);

        c = new Color(255, 255, 255);
        g2.setColor(c);
        g2.setStroke(new BasicStroke(5));
        g2.drawRoundRect(x+5, y+5, ancho-10, alto-10, 25, 25);


    }
    /**
     * Dibuja la pantalla de controles dentro del menú de pausa.
     */
    public void drawControlesPausa(){
        //Dibuja ventana de controles
        dibujaSubVentana(gP.getTamTile()*4, gP.getTamTile()*2, gP.getAltoPantalla() - gP.getTamTile()*4, gP.getAnchoPantalla() - gP.getTamTile()*8);
        
        g2.setFont(txtFont.deriveFont(Font.TRUETYPE_FONT, 48F));
        String texto = "CONTROLES";
        int x = getXCentrado(texto);
        int y = gP.getTamTile() * 3;
        g2.setColor(Color.white);
        g2.drawString(texto, x, y);
        
        // Lista de controles
        g2.setFont(txtFont.deriveFont(Font.TRUETYPE_FONT, 20F));
        y += gP.getTamTile() * 2;
        int margenIzq = gP.getTamTile() * 6;
        
        g2.drawString("W - Mover arriba", margenIzq, y);
        y += gP.getTamTile();
        g2.drawString("S - Mover abajo", margenIzq, y);
        y += gP.getTamTile();
        g2.drawString("A - Mover izquierda", margenIzq, y);
        y += gP.getTamTile();
        g2.drawString("D - Mover derecha", margenIzq, y);
        y += gP.getTamTile();
        g2.drawString("K - Atacar", margenIzq, y);
        y += gP.getTamTile();
        g2.drawString("ENTER - Interactuar", margenIzq, y);
        y += gP.getTamTile();
        g2.drawString("ESC - Pausar", margenIzq, y);
        
        // Instrucción para volver
        y += gP.getTamTile();
        g2.setFont(txtFont.deriveFont(Font.TRUETYPE_FONT, 18F));
        texto = "Presiona ESC para volver";
        x = getXCentrado(texto);
        g2.drawString(texto, x, y);
    }
    /**
     * Dibuja el menú de pausa en pantalla.
     */
    public void drawMenuPausa(){
        //Dibuja pantalla de pausa
        dibujaSubVentana(gP.getTamTile()*4, gP.getTamTile()*2, gP.getAltoPantalla() - gP.getTamTile()*4, gP.getAnchoPantalla() - gP.getTamTile()*8);
        g2.setFont(txtFont.deriveFont(Font.TRUETYPE_FONT, 72F));
        String texto = "PAUSA";
        int x = getXCentrado(texto);
        int y = gP.getAltoPantalla() / 4;
        g2.drawString(texto, x, y);

        g2.setFont(txtFont.deriveFont(Font.TRUETYPE_FONT, 22F));
        texto = "Opciones";
        x = getXCentrado(texto);
        y += gP.getTamTile() * 3;
        g2.drawString(texto, x, y);
        if(numOpcion == 0)
            //reemplazar por drawImage y usar una imagen de cursor en lugar de ">"
            g2.drawString(">", x - gP.getTamTile(), y);
            
        texto = "Controles";
        x = getXCentrado(texto);
        y += gP.getTamTile();
        g2.drawString(texto, x, y);
        if(numOpcion == 1)
            //reemplazar por drawImage y usar una imagen de cursor en lugar de ">"
            g2.drawString(">", x - gP.getTamTile(), y);

        texto = "Salir del juego";
        x = getXCentrado(texto);
        y += gP.getTamTile();
        g2.drawString(texto, x, y);
        if(numOpcion == 2)
            //reemplazar por drawImage y usar una imagen de cursor en lugar de ">"
            g2.drawString(">", x - gP.getTamTile(), y);
    }
    /**
     * Dibuja un mensaje pequeño en la esquina izquierda de la pantalla.
     * El mensaje se muestra durante un tiempo limitado.
     */
    public void drawMensaje(){
        if(estadoMensaje){
            contadorMensaje++;
            
            g2.setFont(txtFont.deriveFont(Font.TRUETYPE_FONT, 18F));
            int x = gP.getTamTile() / 2;
            int y = gP.getTamTile() * 3;
            
            // Fondo semi-transparente
            int anchoTexto = (int)g2.getFontMetrics().getStringBounds(mensaje, g2).getWidth();
            int padding = 10;
            g2.setColor(new Color(0, 0, 0, 180));
            g2.fillRoundRect(x - padding, y - 25, anchoTexto + padding * 2, 35, 10, 10);
            
            // Borde
            g2.setColor(new Color(255, 255, 255, 200));
            g2.setStroke(new BasicStroke(2));
            g2.drawRoundRect(x - padding, y - 25, anchoTexto + padding * 2, 35, 10, 10);
            
            // Texto del mensaje
            g2.setColor(Color.white);
            g2.drawString(mensaje, x, y);
            
            // Desactivar mensaje después del tiempo establecido
            if(contadorMensaje >= tiempoMensaje){
                estadoMensaje = false;
                contadorMensaje = 0;
            }
        }
    }
    /**
     * Muestra un mensaje en pantalla.
     * @param mensaje el mensaje a mostrar.
     */
    public void mostrarMensaje(String mensaje){
        this.mensaje = mensaje;
        this.estadoMensaje = true;
        this.contadorMensaje = 0; // Reiniciar el contador
    }
    /**
     * Calcula la posición X centrada para un texto dado.
     * @param text el texto a centrar.
     * @return la posición X centrada.
     */
    public int getXCentrado(String text){
        int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        int x = gP.getAnchoPantalla() / 2 - length/2;
        return x;
    }
    /**
     * Decrementa el número de opción del menú.
     * Si el número de opción es menor que 0, lo reinicia a 2
     */
    public void decrementaNumOpcion(){
        if(this.numOpcion < 0)
            this.numOpcion = 2;
        else
            this.numOpcion--;
    }
    /**
     * Incrementa el número de opción del menú.
     * Si el número de opción es mayor que 2, lo reinicia a 0
     */
    public void incrementaNumOpcion(){
        if(this.numOpcion > 2)
            this.numOpcion = 0;
        else
            this.numOpcion++;
    }
    //getters y setters
    public int getNumOpcion() {
        return this.numOpcion;
    }
    public void setDialogoActual(String dialogoActual) {
        this.dialogoActual = dialogoActual;
    }
    public boolean getMostrandoControles() {
        return this.mostrandoControles;
    }
    public void setMostrandoControles(boolean mostrandoControles) {
        this.mostrandoControles = mostrandoControles;
    }
}
