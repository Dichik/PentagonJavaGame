package game.states;

import framework.gamestates.GameState;
import game.Game;

import java.awt.*;
import java.awt.event.KeyEvent;

public class Info extends GameState {

    private int selected;

    public Info() {
        selected = 0;
    }

    @Override
    protected void init() {

    }

    @Override
    public void tick() {

    }

    @Override
    public void render(Graphics graphics) {

    }

    @Override
    public void keyPressed(int key) {
        if (key == KeyEvent.VK_ESCAPE) {
            Game.STATE_MANAGER.backToPrevious();
        }
    }

    @Override
    public void keyReleased(int key) {

    }
}
