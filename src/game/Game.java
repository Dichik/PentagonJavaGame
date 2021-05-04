package game;

import framework.display.Window;
import framework.gamestates.GameStateManager;
import framework.resources.ResourceManager;
import game.states.MainMenu;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Game {

    public static final GameStateManager STATE_MANAGER = new GameStateManager();
    private static boolean running = false;
    private static Timer timer;
    public static long startTime;
    public static long endTime;

    public static void main(String[] args) {
        System.out.println("[Game][Main]: Starting...");

        ResourceManager.readImageFiles();
        Window.create();
        startTime = System.nanoTime();
        startGame();
        System.out.println("[Game][Main]: Started!");
    }

    private static void startGame() {
        STATE_MANAGER.changeState(new MainMenu());
        timer = new Timer(20, new GameLoop());
        running = true;
        timer.start();
    }

    public static boolean isRunning() {
        return running;
    }

    private static class GameLoop implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            Game.STATE_MANAGER.tick();
        }

    }
}
