package game.states;

import framework.gamestates.GameState;
import game.Game;
import framework.display.Window ;

import java.awt.*;
import java.awt.event.KeyEvent;


public class MainMenu extends GameState {

	protected String[] options;
	protected int selected;
	
	@Override
	protected void init() {
		options = new String[] {
				"Start Game",
				"Highlights",
				"Info",
				"Exit"
		};
		selected = 0;
		System.out.println("[Game][States]: Created main menu");
	}

	@Override
	public void tick() {}

	@Override
	public void render(Graphics graphics) {
		///graphics for mainMenu
		drawBackground(graphics);
		drawButtons(graphics);
		drawOptions(graphics);
	}

	// work with keys up -> and down <-, (PgUp, PgDn) ;

	@Override
	public void keyPressed(int key) {
		if(key == KeyEvent.VK_UP){
			if( selected < 3 ){
				selected ++ ;
			} else {
				selected = 0 ;
			}
		} else if(key == KeyEvent.VK_DOWN){
			if(selected > 0){
				selected -- ;
			} else {
				selected = 3;
			}
		}else if(key == KeyEvent.VK_ENTER){
			if(selected == 0){
				Game.STATE_MANAGER.changeState(new PlayingState());
			} else if(selected == 1){
				Game.STATE_MANAGER.changeState(new Info());
			}else if(selected == 2){
				Game.STATE_MANAGER.changeState(new HighscoresMenu());
			}else{
				System.exit(0);
			}
		}
	}

	@Override
	public void keyReleased(int key) {}

	private void drawBackground(Graphics graphics) {
		graphics.setColor(new Color(149, 38, 38));
		graphics.fillRect(0,0, Window.getHeight(), Window.getWidth());
	}
	private void drawOptions(Graphics graphics) {
		graphics.setColor(Color.WHITE);
		graphics.setFont(new Font("Arial", Font.PLAIN, 20));
		for(int i = 0; i < options.length; i ++ ){
			if(selected == i){
				graphics.setColor(Color.GREEN);
			} else {
				graphics.setColor(Color.WHITE);
			}
			graphics.drawString(options[i], Window.getWidth()/4, 190 + 100 * i);
		}
	}
	private void drawButtons(Graphics graphics) {

	}
}
