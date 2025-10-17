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
	
	Thread hebraJuego;
	ManejadorTeclas mT = new ManejadorTeclas();
	ManejadorTiles mTi = new ManejadorTiles(this);
	Jugador jugador = new Jugador(this, mT);
	private int FPS = 60;
	
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
		jugador.update();
	}
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D)g;
		mTi.draw(g2);
		jugador.draw(g2);
		g2.dispose();
	}
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
}
