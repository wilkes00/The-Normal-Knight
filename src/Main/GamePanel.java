package Main;

import entidad.Jugador;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;
import tile.ManejadorTiles;

public class GamePanel extends JPanel implements Runnable{
	//Config de pantalla
	final int sizeOriginalTile = 16;
	final int escala = 3;
	final int sizeTile = sizeOriginalTile * escala;
	final int maxRenPantalla = 15;
	final int maxColPantalla = 26;
	final int anchoPantalla = sizeTile * maxColPantalla;
	final int altoPantalla = sizeTile * maxRenPantalla;
	
	//Sistema de juego
	Thread hebraJuego;
	ManejadorTeclas mT = new ManejadorTeclas(this);
	ManejadorTiles mTi = new ManejadorTiles(this);
	Jugador jugador = new Jugador(this, mT);
	InterfazUsuario iU = new InterfazUsuario(this);
	DetectorColisiones dC = new DetectorColisiones(this);
	ControladorEventos cE = new ControladorEventos(this);
	private int FPS = 60;

	//estado del juego
	private int estadoJuego;
	private int playState = 1;
	private int pauseState = 2;

	//configuracion del mundo
	private final int maxRenMundo = 50;
	private final int maxColMundo = 50;
	private final int anchoMundo = this.sizeTile * this.maxColMundo;
	private final int altoMundo = this.sizeTile * this.maxRenMundo;
	
	//Estados del mapa
	private final int mapaMundo = 0;
	private final int mapaMazmorra1 = 1;
	private final int maxMapas = 3;
	private int mapaActual = mapaMundo;
	
	public GamePanel() {
		this.setPreferredSize(new Dimension(this.anchoPantalla, this.altoPantalla));
		this.setBackground(Color.black);
		this.setDoubleBuffered(true);
		this.addKeyListener(mT);
		this.setFocusable(true);
		
	}
	public void iniciaHebraJuego() {
		hebraJuego = new Thread(this);
		hebraJuego.start();
	}

	@Override
	public void run() {
		double intervaloDibujo = 1000000000 / FPS;
		double delta = 0;
		long ultimaVez = System.nanoTime();
		long tiempoActual;
		
		while(hebraJuego != null) {
			tiempoActual = System.nanoTime();
			delta += (tiempoActual - ultimaVez) / intervaloDibujo;
			ultimaVez = tiempoActual;
			if(delta >= 1) {
				update();
				repaint();
				delta--;
			}
		}
	}
	public void update() {
		//ACTUALIZAR EL JUEGO
		jugador.update();
		//EVENTOS
		cE.revisaEventos();
	}
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D)g;

		//LOGICA DE LA CAMARA
		//calcula la posicion teorica de la esquina superior izquierda de la camara
		//basandose en la posicion del jugador. La camara intenta centrar al jugador
		int camaraX = this.jugador.getMundoX() - this.jugador.getPantallaX();
		int camaraY = this.jugador.getMundoY() - this.jugador.getPantallaY();

		//limita la camara en el eje X para que no se salga del mapa por la izquierda
		if(camaraX < 0)
			camaraX = 0;

		//limita la camara en el eje X para que no se salga del mapa por la derecha
		if(camaraX > this.getAnchoMundo() -this.getAnchoPantalla())
			camaraX = this.getAnchoMundo() - this.getAnchoPantalla();

		//limita la camara en el eje Y para que no se salga del mapa por arriba
		if(camaraY < 0)
			camaraY = 0;

		//limita la camara en el eje Y para que no se salga del mapa por abajo
		//el limite es el alto del mundo menos el alto de la pantalla
		if(camaraY > this.getAltoMundo() - this.getAltoPantalla())
			camaraY = this.getAltoMundo() - this.getAltoPantalla();

		//dibuja el mapa primero, pasandole la posicion corregida de la camara	
		this.mTi.draw(g2, camaraX, camaraY);
		//dibuja al jugador despues, pasandole la posicion de la camara
		this.jugador.draw(g2, camaraX, camaraY);
		//dibuja la interfaz de usuario al final
		this.iU.draw(g2);
		
		g2.dispose();
	}

	//getters y setters
	public int getTamTile(){
		return this.sizeTile;
	}

	public int getMaxRenPantalla(){
		return this.maxRenPantalla;
	}
	public int getMaxColPantalla(){
		return this.maxColPantalla;
	}
	public int getAnchoPantalla(){
		return this.anchoPantalla;
	}
	public int getAltoPantalla(){
		return this.altoPantalla;
	}
	public int getMaxRenMundo(){
		return this.maxRenMundo;
	}
	public int getMaxColMundo(){
		return this.maxColMundo;
	}
	public int getAnchoMundo(){
		return this.anchoMundo;
	}
	public int getAltoMundo(){
		return this.altoMundo;
	}
	public Jugador getJugador(){
		return this.jugador;
	}
	public DetectorColisiones getDetectorColisiones(){
		return this.dC;
	}
	public int getMapaMundo(){
		return this.mapaMundo;
	}
	public int getMapaMazmorra1(){
		return this.mapaMazmorra1;
	}
	public int getMapaActual(){
		return this.mapaActual;
	}
	public void setMapaActual(int mapaActual){
		this.mapaActual = mapaActual;
	}
	public int getMaxMapas(){
		return this.maxMapas;
	}
	public int getEstadoJuego(){
		return this.estadoJuego;
	}
	public void setEstadoJuego(int estadoJuego){
		this.estadoJuego = estadoJuego;
	}
}
