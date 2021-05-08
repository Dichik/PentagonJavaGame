package game.states;

import java.awt.*;
import java.awt.event.KeyEvent;

import framework.gamestates.GameState;
import framework.resources.ResourceManager;
import game.Game;
import framework.display.Window;

public class HighestScoreMenu extends GameState {

    private int[] scores;

    @Override
    protected void init() {
        scores = ResourceManager.readTopScores();
    }

    @Override
    public void tick() {

    }

    @Override
    public void render(Graphics graphics) {
        graphics.setFont(new Font("Roboto", Font.PLAIN
                + Font.ITALIC, 25));
        drawBackground(graphics);
        drawScores(graphics);
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

    private void drawBackground(Graphics graphics) {
        graphics.setColor(new Color(54, 51, 51));
        graphics.fillRect(0, 0, Window.getWidth(), Window.getHeight());
        graphics.setColor(Color.WHITE);

        drawCenteredString("Highest Scores", graphics, 0);
        drawCenteredString("Press ESC to return to main menu",
                graphics, 10.5);
    }

    private void drawScores(Graphics graphics) {
        graphics.setColor(Color.WHITE);
        for (int i = 0; i < scores.length; i++) {
            drawCenteredString((i + 1) + " - " + scores[i] + " points",
                    graphics, i + 1);
        }
    }

    private void drawCenteredString(String option, Graphics graphics,
                                    double diffBetweenLines) {
        FontMetrics fm = graphics.getFontMetrics();
        int x = (Window.getWidth() - fm.stringWidth(option)) / 2;
        double y = (fm.getAscent() + (20 + 100 * diffBetweenLines -
                (fm.getAscent() + fm.getDescent())) / 2);
        graphics.drawString(option, x, (int) y);
    }
}
