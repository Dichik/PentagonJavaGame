package framework.display;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JPanel;

public class GameScreen extends JPanel {

	public GameScreen() {

	}
	
	@Override
	protected void paintComponent(Graphics g) {

	}
	
	private static class Keyboard implements KeyListener {

		@Override
		public void keyTyped(KeyEvent e) {

		}

		@Override
		public void keyPressed(KeyEvent e) {

		}

		@Override
		public void keyReleased(KeyEvent e) {

		}
	}
}
