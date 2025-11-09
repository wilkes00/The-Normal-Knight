package Main;

import entidad.Entidad;
import entidad.Jugador;

public class DetectorColisiones {
    GamePanel gP;

    public DetectorColisiones(GamePanel gP){
        this.gP = gP;
    }

    public void revisaTile(Entidad entidad){
        if(entidad instanceof Jugador){
            int izquierdaEntidadMundoX = ((Jugador)entidad).getMundoX() + ((Jugador)entidad).getAreaSolidaX();
            int derechaEntidadMundoX = ((Jugador)entidad).getMundoX() + ((Jugador)entidad).getAreaSolidaX() +
            ((Jugador)entidad).getAreaSolidaAncho();

            int arribaEntidadMundoY = ((Jugador)entidad).getMundoY() + ((Jugador)entidad).getAreaSolidaY();
            int abajoEntidadMundoY = ((Jugador)entidad).getMundoY() + ((Jugador)entidad).getAreaSolidaY() +
            ((Jugador)entidad).getAreaSolidaAlto();

            int colIzquierdaEntidad = izquierdaEntidadMundoX / this.gP.getTamTile();
            int colDerechaEntidad = derechaEntidadMundoX / this.gP.getTamTile();
            int renArribaEntidad = arribaEntidadMundoY / this.gP.getTamTile();
            int renAbajoEntidad = abajoEntidadMundoY / this.gP.getTamTile();

            int numTile1, numTile2;

            switch(((Jugador)entidad).getDireccion()){
                case "arriba":
                    renArribaEntidad = (arribaEntidadMundoY - ((Jugador)entidad).getVelocidad()) / this.gP.getTamTile();
                    numTile1 = gP.mTi.getCodigoMapaTiles(renArribaEntidad, colIzquierdaEntidad);
                    numTile2 = gP.mTi.getCodigoMapaTiles(renArribaEntidad, colDerechaEntidad);
                    if(gP.mTi.getColisionDeTile(numTile1) || gP.mTi.getColisionDeTile(numTile2)){
                        ((Jugador)entidad).setColisionActivada(true);
                    }
                    break;
                case "abajo":
                    renAbajoEntidad = (abajoEntidadMundoY + ((Jugador)entidad).getVelocidad()) / this.gP.getTamTile();
                    numTile1 = gP.mTi.getCodigoMapaTiles(renAbajoEntidad, colIzquierdaEntidad);
                    numTile2 = gP.mTi.getCodigoMapaTiles(renAbajoEntidad, colDerechaEntidad);
                    if(gP.mTi.getColisionDeTile(numTile1) || gP.mTi.getColisionDeTile(numTile2)){
                        ((Jugador)entidad).setColisionActivada(true);
                    }
                    break;
                case "izquierda":
                    colIzquierdaEntidad = (izquierdaEntidadMundoX - ((Jugador)entidad).getVelocidad()) / this.gP.getTamTile();
                    numTile1 = gP.mTi.getCodigoMapaTiles(renArribaEntidad, colIzquierdaEntidad);
                    numTile2 = gP.mTi.getCodigoMapaTiles(renAbajoEntidad, colIzquierdaEntidad);
                    if(gP.mTi.getColisionDeTile(numTile1) || gP.mTi.getColisionDeTile(numTile2)){
                        ((Jugador)entidad).setColisionActivada(true);
                    }
                    break;
                case "derecha":
                    colDerechaEntidad = (derechaEntidadMundoX + ((Jugador)entidad).getVelocidad()) / this.gP.getTamTile();
                    numTile1 = gP.mTi.getCodigoMapaTiles(renArribaEntidad, colDerechaEntidad);
                    numTile2 = gP.mTi.getCodigoMapaTiles(renAbajoEntidad, colDerechaEntidad);
                    if(gP.mTi.getColisionDeTile(numTile1) || gP.mTi.getColisionDeTile(numTile2)){
                        ((Jugador)entidad).setColisionActivada(true);
                    }
                    break;
                default: break;
            }
        }
    }
}
