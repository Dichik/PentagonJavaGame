package game.states;

import game.Game;

import java.awt.*;
import java.awt.event.KeyEvent;

public class PauseMenu extends MainMenu {

	@Override
	protected void init() {
		/**
		 * maybe add option to save the game stage
		 * it means we can start the game from the saved stage -> a bit difficult
		 */
		this.options = new String[]{
				"Continue",
				"To Menu"
		};
	}

	@Override
	public void render(Graphics graphics) {
		super.render(graphics);
	}

	@Override
	public void keyPressed(int key) {

		/**
		 * when we go to menu, there should be a message
		 * about unsaved results of the current game
		*/
		if(key == KeyEvent.VK_DOWN){
			if(selected < options.length - 1)
				selected ++ ;
			else selected = 0 ;
		} else if(key == KeyEvent.VK_UP){
			if(selected > 0)
				selected -- ;
			else selected = options.length - 1;
		} else if(key == KeyEvent.VK_ENTER){
			if(selected == 0){
				Game.STATE_MANAGER.backToPrevious();
			} else {
				Game.STATE_MANAGER.clearStack();
				Game.STATE_MANAGER.changeState(new MainMenu());
			}
		} else if(key == KeyEvent.VK_ESCAPE){
			Game.STATE_MANAGER.backToPrevious();
		}
	}
	
}
