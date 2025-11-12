package Main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
/**
 * Manejador de entradas del teclado.
 * Escucha las teclas presionadas y liberadas,
 * y actualiza el estado de las teclas de movimiento.
 */
public class ManejadorTeclas implements KeyListener{
	GamePanel gP;
	private boolean teclaArriba, teclaAbajo, teclaIzq, teclaDer;

	/**
	 * Constructor del ManejadorTeclas.
	 * @param gP Referencia al GamePanel principal.
	 */
	public ManejadorTeclas(GamePanel gP) {
		this.gP = gP;
	}
	/**
	 * Maneja el evento de tipeo de tecla.
	 * @param e el evento de tecla tipeada.
	 */
	@Override
	public void keyTyped (KeyEvent e) {
		
	}
	/**
	 * Maneja el evento de presionar una tecla.
	 * Actualiza el estado de las teclas de movimiento.
	 * @param e el evento de tecla presionada.
	 */
	@Override
	public void keyPressed(KeyEvent e) {
		switch(e.getKeyCode()) {
			case KeyEvent.VK_W : teclaArriba = true;
				break;
			case KeyEvent.VK_S : teclaAbajo = true;
				break;
			case KeyEvent.VK_A : teclaIzq = true;
				break;
			case KeyEvent.VK_D : teclaDer = true;
				break;
		}
	}
	/**
	 * Maneja el evento de liberar una tecla.
	 * Actualiza el estado de las teclas de movimiento.
	 * @param e el evento de tecla liberada.
	 */
	@Override
	public void keyReleased(KeyEvent e) {
		switch(e.getKeyCode()) {
		case KeyEvent.VK_W : teclaArriba = false;
			break;
		case KeyEvent.VK_S : teclaAbajo = false;
			break;
		case KeyEvent.VK_A : teclaIzq = false;
			break;
		case KeyEvent.VK_D : teclaDer = false;
			break;
		}
	}
	
	//getters de las teclas	
	public boolean getTeclaArriba() {
		return this.teclaArriba;
	}
	public boolean getTeclaAbajo() {
		return this.teclaAbajo;
	}
	public boolean getTeclaIzq() {
		return this.teclaIzq;
	}
	public boolean getTeclaDer() {
		return this.teclaDer;
	}
}