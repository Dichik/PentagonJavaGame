package framework.gamestates;

import java.awt.Graphics;
import java.util.Stack;

public class GameStateManager {

    private Stack<GameState> gameStackStates = null;

    public GameStateManager() {
        gameStackStates = new Stack<>();
        System.out.println("[Framework][GameStates]: Created game state manager");
    }

    public void changeState(GameState state) {
        gameStackStates.add(state);
    }

    public void backToPrevious() {
        gameStackStates.pop();
    }

    public void clearStack() {
        gameStackStates.clear();
    }

    public void init() {
        gameStackStates.peek().init();
    }

    public void render(Graphics graphics) {
        gameStackStates.peek().render(graphics);
    }

    public void keyPressed(int key) {
        gameStackStates.peek().keyPressed(key);
    }

    public void keyReleased(int key) {
        gameStackStates.peek().keyReleased(key);
    }

    public void tick() {
        this.gameStackStates.peek().tick();
    }
}
