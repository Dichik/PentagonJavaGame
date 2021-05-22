package game.states;

import framework.gamestates.GameState;
import game.Game;

import java.awt.*;
import java.awt.event.KeyEvent;

import framework.display.Window;

public class Info extends GameState {

    private int selected;
    private String[] options;

    public Info() {
        options = new String[]{
                "Rules"
        };
        selected = 0;
        System.out.println("[Game][States]: Created info");
    }

    @Override
    protected void init() {
    }

    @Override
    public void tick() {

    }

    @Override
    public void render(Graphics graphics) {
        drawBackground(graphics);
        drawOptions(graphics);
    }

    private void drawBackground(Graphics graphics) {
        MainMenu.coloring(graphics);
    }

    private void drawOptions(Graphics graphics) {
        graphics.setColor(Color.WHITE);
        graphics.setFont(new Font("Roboto", Font.PLAIN + Font.ITALIC, 25));

        for (int i = 0; i < options.length; i++) {
            if (selected == i) {
                graphics.setColor(Color.GREEN);
            } else {
                graphics.setColor(Color.WHITE);
            }
            drawCenteredString(options[i], Window.WIDTH, Window.HEIGHT, graphics, i);
        }
    }

    private void drawCenteredString(String option, int width, int height,
                                    Graphics graphics, int diffBetweenLines) {
        MainMenu.drawingCenteredOne(option, width, graphics, diffBetweenLines);
    }

    @Override
    public void keyPressed(int key) {
        if (key == KeyEvent.VK_ESCAPE) {
            Game.STATE_MANAGER.backToPrevious();
        } else if (key == KeyEvent.VK_ENTER) {
            //url to the github
        }
    }

    @Override
    public void keyReleased(int key) {

    }
}
