package Main;

import entidad.Jugador;

/**
 * Clase que se dedica a gestionar los eventos dentro del juego.
 */
public class ControladorEventos {
    GamePanel gP;
    private Jugador jugador;
    EventRect eventRect[][][];
    private boolean touchEvent = true;
    private int anteriorEventoX, anteriorEventoY;
    private int mapaDestino, renDestino, colDestino;
    /**
     * Constructor de ControladorEventos.
     * 
     * @param gP referencia a GamePanel principal.
     */
    public ControladorEventos(GamePanel gP){
        this.gP = gP;
        this.anteriorEventoX = 0; //almacena la posicion inicial del jugador
        this.anteriorEventoY = 0; //almacena la posicion inicial del jugador
        
        // inicializa el array tridimensional de eventRect
        eventRect = new EventRect[gP.getMaxMapas()][gP.getMaxRenMundo()][gP.getMaxColMundo()]; 

        //inicializa las posiciones de los eventRect
        for(int mapa = 0; mapa < gP.getMaxMapas(); mapa++) {
            for(int ren = 0; ren < gP.getMaxRenMundo(); ren++) {
                for(int col = 0; col < gP.getMaxColMundo(); col++) {     
                    eventRect[mapa][ren][col] = new EventRect();
                    eventRect[mapa][ren][col].x = 16;
                    eventRect[mapa][ren][col].y = 16;
                    eventRect[mapa][ren][col].width = 4;
                    eventRect[mapa][ren][col].height = 4;
                    eventRect[mapa][ren][col].setEventRectDefaultX(eventRect[mapa][ren][col].x); 
                    eventRect[mapa][ren][col].setEventRectDefaultY(eventRect[mapa][ren][col].y);
                }
            }
        }
    }
    /**
     * Revisa si el jugador ha activado algún evento basado en su posición actual.
     */
    public void revisaEventos(){
        if(jugador == null) return; // Evitar Null pointer exception si aún no se ha asignado
        
        int distanciaX = Math.abs(jugador.getMundoX() - anteriorEventoX);
        int distanciaY = Math.abs(jugador.getMundoY() - anteriorEventoY);
        int distancia = Math.max(distanciaX, distanciaY);
        if(distancia > gP.getTamTile()){
            touchEvent = true;
        }
        if(touchEvent){

            //evento de teletransporte a la mazmorra 1
            if(hit(0, 7, 23, "arriba")){
                mapaDestino = gP.getMapaMazmorra1();
                renDestino = 13;
                colDestino = 12; 
                gP.setEstadoJuego(gP.getTransitionState()); // cambiar estado a transición
            }
            //evento de teletransporte de la mazmorra 1 al mundo 
            else if(hit(1, 13, 12, "abajo")){ 
                mapaDestino = gP.getMapaMundo();
                renDestino = 7;
                colDestino = 23; 
                gP.setEstadoJuego(gP.getTransitionState()); // cambiar estado a transición
            }
            
            //evento de teletransporte a la mazmorra 2
            if(hit(0, 31, 5, "arriba")){
                mapaDestino = gP.getMapaMazmorra2();
                renDestino = 13;
                colDestino = 12; 
                gP.setEstadoJuego(gP.getTransitionState()); // cambiar estado a transición
            }
            //evento de teletransporte de la mazmorra 2 al mundo
            else if(hit(2, 13, 12, "abajo")){ 
                mapaDestino = gP.getMapaMundo();
                renDestino = 31;
                colDestino = 5; 
                gP.setEstadoJuego(gP.getTransitionState()); // cambiar estado a transición
            }
            
        }
    }

    /**
     * Comprueba si el jugador ha activado un evento en una celda específica.
     * @param mapa el mapa en el que se verifica el evento
     * @param ren fila
     * @param col columna
     * @param reqDireccion la dirección requerida para activar el evento
     * @return true si el evento ha sido activado, false en caso contrario
     */
    public boolean hit(int mapa, int ren, int col, String reqDireccion){
        boolean hit = false;

        if(mapa == gP.getMapaActual()){
            // Ajustar las coordenadas para la colisión
            jugador.setAreaSolidaX(jugador.getMundoX() + jugador.getAreaSolidaX());
            jugador.setAreaSolidaY(jugador.getMundoY() + jugador.getAreaSolidaY());
            eventRect[mapa][ren][col].x = col * gP.getTamTile() + eventRect[mapa][ren][col].x;
            eventRect[mapa][ren][col].y = ren * gP.getTamTile() + eventRect[mapa][ren][col].y;
            // Revisar intersección
            if(jugador.getAreaSolida().intersects(eventRect[mapa][ren][col])){
                if(jugador.getDireccion().contentEquals(reqDireccion) || reqDireccion.contentEquals("any")){
                    hit = true;
                }
            }
            // Reajustar las coordenadas después de la colisión
            jugador.setAreaSolidaX(jugador.getAreaSolidaX() - jugador.getMundoX());
            jugador.setAreaSolidaY(jugador.getAreaSolidaY() - jugador.getMundoY());
            eventRect[mapa][ren][col].x = eventRect[mapa][ren][col].x - col * gP.getTamTile();
            eventRect[mapa][ren][col].y = eventRect[mapa][ren][col].y - ren * gP.getTamTile();
        }
        
        return hit;
    }
    
    /**
     * Teletransporta al jugador a la ubicación de destino especificada.
     */
    public void teleport(){
        gP.setMapaActual(mapaDestino);
        jugador.setX(colDestino * gP.getTamTile());
        jugador.setY(renDestino * gP.getTamTile());
        jugador.setMapa(mapaDestino);
        anteriorEventoX = jugador.getMundoX();
        anteriorEventoY = jugador.getMundoY();
        touchEvent = false;
    }
    /**
     * Apunta a la instancia de jugador del GamePanel.
     * Guarda la posicion del jugador en X y Y en el mundo para
     * la logica de controlar eventos.
     * @param jugador referencia del Jugador en GamePanel
     */
    public void setJugador(Jugador jugador){
        this.jugador = jugador;
        this.anteriorEventoX = jugador.getMundoX();
        this.anteriorEventoY = jugador.getMundoY();
    }
}
