package game.states;

import framework.gamestates.GameState;
import framework.resources.ResourceManager;
import game.Game;
import game.pieces.Grid;

import java.awt.*;
import java.awt.event.KeyEvent;
import framework.display.Window ;

public class PlayingState extends GameState {

	private Grid grid ;
	
	@Override
	protected void init() {
		grid = new Grid() ;
		//System.out.println("[Game][States]: Created playing state");
	}

	@Override
	public void render(Graphics graphics) {
		drawBackground(graphics) ;
		drawGrid(graphics);
	}

	@Override
	public void keyPressed(int key) {
		if(key == KeyEvent.VK_ESCAPE){
			Game.STATE_MANAGER.changeState(new PauseMenu());
		}
	}

	@Override
	public void keyReleased(int key) {}

	private void drawGrid(Graphics graphics){
		for(int i = 0 ; i < Grid.LINES; i ++ ){
			for(int j = 0 ; j < Grid.LINES_SIZE ; j ++ ){
				graphics.drawImage(ResourceManager.texture("block_void.png"),
						j*30, i*30-30, 30, 30, null) ;
			}
		}
	}
	private void drawBackground(Graphics graphics){
		graphics.setColor(new Color(212, 206, 206));
		graphics.fillRect(0,0, Window.getWidth(), Window.getHeight());
	}
}