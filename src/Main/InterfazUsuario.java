package Main;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
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
    Font font;
    private boolean estadoMensaje = false;
    private String mensaje = "";
    private final int MaxOpacidad = 50;
    private int opacidadTransicion = 0;
    private boolean juegoTerminado = false;
    private int numOpcion = 0;
    /**
     * Constructor de la InterfazUsuario.
     * @param gP referencia al GamePanel principal.
     * @param cE referencia al ControladorEventos.
     */
    public InterfazUsuario(GamePanel gP, ControladorEventos cE){
        this.gP = gP;
        this.cE = cE;
        this.font = new Font("Arial", Font.PLAIN, 30);
    }
    /**
     * Dibuja los elementos de la interfaz de usuario en pantalla.
     * @param g2 el contexto gráfico (Graphics2D) sobre el que se dibuja.
     */
    public void draw(Graphics2D g2){
        this.g2 = g2;
        g2.setFont(font);
        g2.setColor(Color.white);
        // Mostrar coordenadas del jugador para depuracion
        int renJugador = gP.jugador.getMundoY() / gP.getTamTile();
        int colJugador = gP.jugador.getMundoX() / gP.getTamTile();
        //g2.drawString("Col: " + colJugador + " Ren: " + renJugador, 20, 40);

        //Pantalla de inicio del juego
        if(gP.getEstadoJuego() == gP.getStartState()){
            drawPantallaInicio();
        }
        //Transicion entre mapas    
        else if(gP.getEstadoJuego() == gP.getTransitionState()){
            drawTransicion();
        }
        //Ventana de dialogo
        else if(gP.getEstadoJuego() == gP.getDialogueState()){
            dibujaPantallaDialogo();
        }
            
    }

    public void drawPantallaInicio(){
        // Titulo del juego
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 96F));
        String txt = "The Normal Knight";
        int x = getXCentrado(txt);
        int y = gP.getTamTile() * 3;
        //Sombra
        g2.setColor(Color.gray);
        g2.drawString(txt, x+5, y+5);
        //Texto
        g2.setColor(Color.white);
        g2.drawString(txt, x, y);
        //Menu
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 48F));
        txt = "Nuevo Juego";
        x = getXCentrado(txt);
        y += gP.getTamTile() * 6;
        g2.drawString(txt, x, y);
        if(numOpcion == 0)
            //reemplazar por drawImage y usar una imagen de cursor en lugar de ">"
            g2.drawString(">", x - gP.getTamTile(), y);

        txt = "Ayuda";
        x = getXCentrado(txt);
        y += gP.getTamTile();
        g2.drawString(txt, x, y);
        if(numOpcion == 1)
            g2.drawString(">", x - gP.getTamTile(), y);

        txt = "Salir";
        x = getXCentrado(txt);
        y += gP.getTamTile();
        g2.drawString(txt, x, y);
        if(numOpcion == 2)
            g2.drawString(">", x - gP.getTamTile(), y);

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

    public void dibujaPantallaDialogo(){
        int x = gP.getTamTile() * 2;
        int y = gP.getTamTile() / 2;
        int alto = gP.getTamTile() * 4;
        int ancho = gP.getAnchoPantalla() - (gP.getTamTile() * 4);
        dibujaSubVentana(x, y, alto, ancho);
    }

    public void dibujaSubVentana(int x, int y, int alto, int ancho){
        Color c = new Color(0,0, 0, 200);
        g2.setColor(c);
        g2.fillRoundRect(x, y, ancho, alto, 35, 35);

        c = new Color(255, 255, 255);
        g2.setColor(c);
        g2.setStroke(new BasicStroke(5));
        g2.drawRoundRect(x+5, y+5, ancho-10, alto-10, 25, 25);


    }

    public void mostrarMensaje(String mensaje){
        this.mensaje = mensaje;
        this.estadoMensaje = true;
    }

    public int getXCentrado(String text){
        int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        int x = gP.getAnchoPantalla() / 2 - length/2;
        return x;
    }
    public void decrementaNumOpcion(){
        if(this.numOpcion < 0)
            this.numOpcion = 2;
        else
            this.numOpcion--;
    }
    public void incrementaNumOpcion(){
        if(this.numOpcion > 2)
            this.numOpcion = 0;
        else
            this.numOpcion++;
    }

    public int getNumOpcion() {
        return this.numOpcion;
    }
}
