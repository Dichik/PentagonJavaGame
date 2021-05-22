package game.states;

import framework.display.Window;
import game.Game;
import game.pieces.Pentamimo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.Collections;

public class PauseMenu extends MainMenu {

    @Override
    protected void init() {
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
        if (key == KeyEvent.VK_DOWN) {
            selected = (selected + 1) % options.length;
        } else if (key == KeyEvent.VK_UP) {
            if (selected > 0)
                selected--;
            else selected = options.length - 1;
        } else if (key == KeyEvent.VK_ENTER) {
            if (selected == 0) {
                Game.STATE_MANAGER.backToPrevious();
            } else if (selected == 1) {
                int userOption = JOptionPane.showConfirmDialog(Window.window,
                        "Attention!\n" + "Your results will be unsaved!");
                if(userOption == 0) {
                    Game.STATE_MANAGER.clearStack();
                    Game.STATE_MANAGER.changeState(new MainMenu());
                    Pentamimo.USED = 0;
                    Collections.shuffle(Pentamimo.LIST);
                }
            }
        } else if (key == KeyEvent.VK_ESCAPE) {
            Game.STATE_MANAGER.backToPrevious();
        }
    }

}
