package Main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class ManejadorTeclas implements KeyListener{
	private boolean teclaSalto, teclaDash, teclaIzq, teclaDer;
	@Override
	public void keyTyped (KeyEvent e) {
		
	}
	@Override
	public void keyPressed(KeyEvent e) {
		switch(e.getKeyCode()) {
			case KeyEvent.VK_SPACE: teclaSalto = true;
				break;
			case KeyEvent.VK_SHIFT : teclaDash = true;
				break;
			case KeyEvent.VK_A : teclaIzq = true;
				break;
			case KeyEvent.VK_D : teclaDer = true;
				break;
		}
	}
	@Override
	public void keyReleased(KeyEvent e) {
		switch(e.getKeyCode()) {
		case KeyEvent.VK_SPACE : teclaSalto = false;
			break;
		case KeyEvent.VK_SHIFT : teclaDash = false;
			break;
		case KeyEvent.VK_A : teclaIzq = false;
			break;
		case KeyEvent.VK_D : teclaDer = false;
			break;
		}
	}
	public boolean getTeclaSalto() {
		return this.teclaSalto;
	}
	public boolean getTeclaDash() {
		return this.teclaDash;
	}
	public boolean getTeclaIzq() {
		return this.teclaIzq;
	}
	public boolean getTeclaDer() {
		return this.teclaDer;
	}
}