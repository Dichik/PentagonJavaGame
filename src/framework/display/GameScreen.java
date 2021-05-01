package framework.display;

import game.Game;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JPanel;

public class GameScreen extends JPanel {

	public GameScreen() {
		super();
		this.setFocusable(true);
		this.addKeyListener(new Keyboard());
		System.out.println("[Framework][Display]: Created game screen");
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		if(Game.isRunning()){
			Game.STATE_MANAGER.render(g);
		}
		super.repaint();
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
