package Main;

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
        g2.drawString("Col: " + colJugador + " Ren: " + renJugador, 20, 40);

        if(gP.getEstadoJuego() == gP.getTransitionState()){
            drawTransicion();
        }
    }
    /**
     * Dibuja una transición de pantalla oscura.
     * Incrementa la opacidad de un rectangulo negro hasta cubrir toda la pantalla.
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
    
    public void mostrarMensaje(String mensaje){
        this.mensaje = mensaje;
        this.estadoMensaje = true;
    }
}
