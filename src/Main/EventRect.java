package Main;

import java.awt.Rectangle;

public class EventRect extends Rectangle{
    private int eventRectDefaultX, eventRectDefaultY;
    private boolean eventoActivado = false;

    public EventRect(){}

    public boolean isEventoActivado() {
        return eventoActivado;
    }
    public void setEventoActivado(boolean eventoActivado) {
        this.eventoActivado = eventoActivado;
    }
    public int getEventRectDefaultX() {
        return eventRectDefaultX;
    }
    public void setEventRectDefaultX(int eventRectDefaultX) {
        this.eventRectDefaultX = eventRectDefaultX;
    }
    public int getEventRectDefaultY() {
        return eventRectDefaultY;
    }
    public void setEventRectDefaultY(int eventRectDefaultY) {
        this.eventRectDefaultY = eventRectDefaultY;
    }
}
