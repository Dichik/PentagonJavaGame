package game.states;

import framework.gamestates.GameState;
import game.Game;

import java.awt.Graphics;
import java.awt.event.KeyEvent;

public class PlayingState extends GameState {


	
	@Override
	protected void init() {

		//System.out.println("[Game][States]: Created playing state");
	}

	@Override
	public void tick() {

	}

	@Override
	public void render(Graphics graphics) {

	}

	@Override
	public void keyPressed(int key) {
		if(key == KeyEvent.VK_ESCAPE){
			Game.STATE_MANAGER.changeState(new PauseMenu());
		}
	}

	@Override
	public void keyReleased(int key) {}

}