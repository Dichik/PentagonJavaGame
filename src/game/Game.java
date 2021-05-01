package game;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import framework.display.Window;
import framework.gamestates.GameStateManager;
import framework.resources.ResourceManager;

public class Game {

	public static final GameStateManager STATE_MANAGER = new GameStateManager();
	
	public static void main(String[] args) {
		System.out.println("[Game][Main]: Starting...");
		
		ResourceManager.readImageFiles();
		Window.create();
		
		startGame();
		System.out.println("[Game][Main]: Started!");
	}

	private static void startGame() {

	}
	
	private static class GameLoop implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			Game.STATE_MANAGER.tick();
		}
		
	}
}
