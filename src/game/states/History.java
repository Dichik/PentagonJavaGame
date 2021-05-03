package game.states;

import framework.display.Window;
import framework.gamestates.GameState;
import framework.resources.ResourceManager;
import game.Game;

import java.awt.*;
import java.awt.event.KeyEvent;

public class History extends GameState {

    private String[] datesOfActions;

    @Override
    protected void init() {
        datesOfActions = ResourceManager.readHistory();
    }

    @Override
    public void tick() {
    }

    @Override
    public void render(Graphics graphics) {
        drawBackground(graphics) ;
        drawHistory(graphics) ;
    }

    private void drawHistory(Graphics graphics) {

    }

    private void drawBackground(Graphics graphics){
        graphics.setColor(new Color(54, 51, 51));
        graphics.fillRect(0, 0, framework.display.Window.getWidth(), Window.getHeight());

        //draw strings NAMES
    }

    @Override
    public void keyPressed(int key) {
        if(key == KeyEvent.VK_ESCAPE){
            Game.STATE_MANAGER.backToPrevious();
        }
    }

    @Override
    public void keyReleased(int key) {

    }

}
