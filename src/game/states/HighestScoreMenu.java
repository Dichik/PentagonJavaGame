package game.states;

import java.awt.*;
import java.awt.event.KeyEvent;

import framework.gamestates.GameState;
import framework.resources.ResourceManager;
import game.Game;

public class HighestScoreMenu extends GameState{

	private int[] scores ;

	@Override
	protected void init() {
		scores = ResourceManager.readTopScores();
		/*
		also add an ability to see history of your actions
		( date | how much time you was in the game | you achievement that time )
		it's looks like ->
		Your highest scores:
		1 -> 54
		2 -> 45
		(...) -> maybe 10 times or less
		--------------------
		History:
		25-06-2021 | 3 minutes 21 seconds | get 54 scores
		(...)
		*/
	}

	@Override
	public void tick() {}

	@Override
	public void render(Graphics graphics) {
		drawBackground(graphics);
		drawScores(graphics);
	}

	@Override
	public void keyPressed(int key) {
		if(key == KeyEvent.VK_ESCAPE){
			Game.STATE_MANAGER.backToPrevious();
		}
	}

	@Override
	public void keyReleased(int key) {}

	private void drawBackground(Graphics graphics) {
		graphics.setColor(new Color(54, 51, 51));
		graphics.fillRect(0,0, framework.display.Window.getWidth(), framework.display.Window.getHeight());
		graphics.setColor(Color.WHITE);

		graphics.setFont(new Font("Roboto", Font.PLAIN + Font.ITALIC, 25));
		graphics.drawString("Highest Scores", 30, 40);
		graphics.setFont(new Font("Roboto", Font.PLAIN + Font.ITALIC, 25));
		graphics.drawString("Press ESC to return to main menu", 10, 280);
	}
	
	private void drawScores(Graphics graphics) {
		graphics.setColor(Color.WHITE);
		graphics.setFont(new Font("Roboto", Font.PLAIN + Font.ITALIC, 25));
		for(int i = 0 ; i < scores.length ; i ++ ){
			graphics.drawString((i+1)+" - "+this.scores[i], 60, 80+i*20);
		}
	}
}
