package Main;

import entidad.Jugador;
import entidad.ManejadorObjetos;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;
import tile.ManejadorTiles;
/**
 * Panel principal del juego donde se maneja la lógica del juego,
 * la actualización de entidades, el dibujo de gráficos y la gestión
 * de la cámara.
 */
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
	Sound sound = new Sound();
    ManejadorTiles mTi = new ManejadorTiles(this);
    ManejadorObjetos mObj = new ManejadorObjetos(this);
    ControladorEventos cE = new ControladorEventos(this);
    InterfazUsuario iU = new InterfazUsuario(this, cE);
    ManejadorTeclas mT = new ManejadorTeclas(this, iU);
	Jugador jugador = new Jugador(this, mT);
    DetectorColisiones dC = new DetectorColisiones(this);
	private final int FPS = 60;

	
	

	//estado del juego
	private int estadoJuego;
	private final int startState = 0;
	private final int playState = 1;
	private final int pauseState = 2;
	private final int transitionState = 3;
	private final int dialogueState = 4;

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
	/**
	 * Constructor del GamePanel.
	 * Inicializa las configuraciones del panel, establece las dimensiones,
	 * el fondo, y configura el manejo de entradas del teclado.
	 */
	public GamePanel() {
		this.setPreferredSize(new Dimension(this.anchoPantalla, this.altoPantalla));
		this.setBackground(Color.black);
		this.setDoubleBuffered(true);
		this.addKeyListener(mT);
		this.setFocusable(true);
		this.requestFocusInWindow();
		//inicializa el mundo
		this.setupGame();
		//agrega al jugador a la lista de objetos del ManejadorObjetos
		mObj.agregarGameObject(jugador);
	}
	/**
	 * Configura el estado inicial del juego,
	 * prepara el juego para comenzar.
	 * Establece el estado del juego en "start".
	 * Aqui iran mas configuraciones iniciales como el sonido.
	 * 
	 */
	public void setupGame() {
		cE.setJugador(this.jugador);
		this.estadoJuego = this.startState;
		playMusic(0);
	}
	/**
	 * Inicia la hebra principal del juego.
	 */
	public void iniciaHebraJuego() {
		hebraJuego = new Thread(this);
		hebraJuego.start();
	}
	/**
	 * Método principal de la hebra del juego.
	 * Controla el ciclo de actualización y dibujo del juego
	 * para mantener una tasa de frames constante.
	 */
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
	/**
	 * Actualiza el estado del juego.
	 * Llama a la actualización del jugador y revisa los eventos del juego.
	 */
	public void update() {
		//solo actualiza cuando el estado es play
		if(estadoJuego == playState) {
			mObj.update();
			cE.revisaEventos(); //revisa los eventos

		}
		else if(estadoJuego == pauseState){
			//aqui va la logica de pausa
		}
	}
	/**
	 * Dibuja los elementos del juego en el panel.
	 * Maneja la lógica de la cámara para centrar al jugador
	 * y dibuja el mapa, el jugador y la interfaz de usuario.
	 */
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

		if(this.estadoJuego == this.startState){
			iU.draw(g2);
		}
		else{
			//dibuja el mapa primero, pasandole la posicion corregida de la camara	
			this.mTi.draw(g2, camaraX, camaraY);

			//dibuja todos los objetos (jugador, npcs, enemigos, items, etc).
			mObj.draw(g2, camaraX, camaraY);

			//dibuja la interfaz de usuario al final
			this.iU.draw(g2);
		}
		g2.dispose();	
	}

	public void playMusic(int index){
		sound.setFile(index);
		sound.play();
		sound.loop();
	}
	public void stopMusic(){
		sound.stop();
	}
	public void playSoundEfect(int index){
		sound.setFile(index);
		sound.play();
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
	public ManejadorObjetos getManejadorObjetos(){
		return this.mObj;
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
	public int getTransitionState(){
		return this.transitionState;
	}
	public int getPlayState(){
		return this.playState;
	}
    public int getDialogueState() {
		return this.dialogueState;
    }
	public int getStartState(){
		return this.startState;
	}

}
