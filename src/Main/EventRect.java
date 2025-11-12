package Main;

import java.awt.Rectangle;
/**
 * Representa un rectángulo de evento en el juego.
 * Se utiliza para definir áreas en el mapa donde se pueden activar eventos.
 */
public class EventRect extends Rectangle{
    private int eventRectDefaultX, eventRectDefaultY;
    private boolean eventoActivado = false;

    public EventRect(){}
    /**
     * Indica si el evento está activado.
     * @return true si el evento está activado, false en caso contrario.
     */
    public boolean isEventoActivado() {
        return eventoActivado;
    }
    /**
     * Establece el estado de activación del evento.
     * @param eventoActivado true para activar el evento, false para desactivarlo.
     */
    public void setEventoActivado(boolean eventoActivado) {
        this.eventoActivado = eventoActivado;
    }
    /**
     * Obtiene la posición X por defecto del rectángulo de evento.
     * @return la posición X por defecto.
     */
    public int getEventRectDefaultX() {
        return eventRectDefaultX;
    }
    /**
     * Establece la posición X por defecto del rectángulo de evento.
     * @param eventRectDefaultX la posición X por defecto a establecer.
     */
    public void setEventRectDefaultX(int eventRectDefaultX) {
        this.eventRectDefaultX = eventRectDefaultX;
    }
    /**
     * Obtiene la posición Y por defecto del rectángulo de evento.
     * @return la posición Y por defecto.
     */
    public int getEventRectDefaultY() {
        return eventRectDefaultY;
    }
    /**
     * Establece la posición Y por defecto del rectángulo de evento.
     * @param eventRectDefaultY la posición Y por defecto a establecer.
     */
    public void setEventRectDefaultY(int eventRectDefaultY) {
        this.eventRectDefaultY = eventRectDefaultY;
    }
}
