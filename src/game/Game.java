package game;

import framework.display.Window;
import framework.gamestates.GameStateManager;
import framework.resources.ResourceManager;
import game.states.MainMenu;

public class Game {

	public static final GameStateManager STATE_MANAGER = new GameStateManager();
	private static boolean running = false ;
	
	public static void main(String[] args) {
		System.out.println("[Game][Main]: Starting...");
		
		ResourceManager.readImageFiles();
		Window.create();
		
		startGame();
		System.out.println("[Game][Main]: Started!");
	}

	private static void startGame() {
		STATE_MANAGER.changeState(new MainMenu());
		running = true ;

	}

	public static boolean isRunning(){
		return running ;
	}
}
