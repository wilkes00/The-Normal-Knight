package Main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

public class InterfazUsuario {
    GamePanel gP;
    Font font;
    private boolean estadoMensaje = false;
    private String mensaje = "";
    private int contadorMensaje = 0;
    private boolean juegoTerminado = false;

    public InterfazUsuario(GamePanel gP){
        this.gP = gP;
        this.font = new Font("Arial", Font.PLAIN, 30);
    }

    public void draw(Graphics2D g2){
        g2.setFont(font);
        g2.setColor(Color.white);
        // Mostrar coordenadas del jugador para depuracion
        int renJugador = gP.jugador.getMundoY() / gP.getTamTile();
        int colJugador = gP.jugador.getMundoX() / gP.getTamTile();
        g2.drawString("Col: " + colJugador + " Ren: " + renJugador, 20, 40);
        

        if(estadoMensaje == true){
            g2.setFont(g2.getFont().deriveFont(30F));
            g2.drawString(mensaje, gP.getAnchoPantalla()/2 - 50, gP.getAltoPantalla()/2);
            estadoMensaje = false;
        }

    }

    public void mostrarMensaje(String mensaje){
        this.mensaje = mensaje;
        this.estadoMensaje = true;
    }
}
