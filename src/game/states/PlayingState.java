package game.states;

import framework.gamestates.GameState;
import framework.resources.ResourceManager;
import game.Game;
import game.pieces.Grid;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;

import framework.display.Window;
import game.pieces.Pentamimo;
import game.pieces.Square;

public class PlayingState extends GameState {
    /**
     * when we choose level we also set the timer for solving this game
     * and when the time is over we will get the massage about losing the game.
     */
    private Grid grid;

    private int currentRotationOriginX;
    private int currentRotationOriginY;
    private Pentamimo.Rotation currentRotation;

    private ArrayBlockingQueue<Pentamimo> queue;
    private Pentamimo currentPentamimo;

    private Pentamimo hold;

    private boolean lost;

    private static int randomInt(int SIZE) {
        return new Random().nextInt(SIZE);
    }

    @Override
    protected void init() {
        grid = new Grid();

        queue = new ArrayBlockingQueue<>(4);
        queue.add(Pentamimo.LIST.get(randomInt(Pentamimo.LIST.size())));
        queue.add(Pentamimo.LIST.get(randomInt(Pentamimo.LIST.size())));
        queue.add(Pentamimo.LIST.get(randomInt(Pentamimo.LIST.size())));

        placePentamimo();

        hold = null;

        lost = false;

        System.out.println("[Game][States]: Created playing state");
    }

    @Override
    public void tick() {
        if (!lost) {
            stopPieces();
            nextPiece();
        }
    }

    @Override
    public void render(Graphics graphics) {
        drawBackground(graphics);
        drawGrid(graphics);
        drawQueue(graphics);

        if (lost)
            drawGameOverMessage(graphics);
    }

    @Override
    public void keyPressed(int key) {
        /**
         * should make ability to drag a piece
         * because when we use keys it makes lots of collapse and troubles.
         */
        if (key == KeyEvent.VK_RIGHT) {
            if (grid.allSquaresCanGoRight() && !lost) {
                grid.movePiecesRight();
                currentRotationOriginX++;
            }
        } else if (key == KeyEvent.VK_LEFT) {
            if (grid.allSquaresCanGoLeft() && !lost) {
                grid.movePiecesLeft();
                currentRotationOriginX--;
            }
        } else if (key == KeyEvent.VK_DOWN) {
            if (grid.allSquaresCanFall() && !lost) {
                grid.movePiecesDown();
                currentRotationOriginY++;
            }
        } else if (key == KeyEvent.VK_UP) {
            if (grid.allSquaresCanGoUP() && !lost) {
                grid.movePiecesUp();
                currentRotationOriginY--;
            }
        } else if (key == KeyEvent.VK_ESCAPE) {
            if (!lost) {
                Game.STATE_MANAGER.changeState(new PauseMenu());
            } else {
//					int[] scores = ResourceManager.readHighscoresFile();
//					if(this.lines > scores[8]) {
//						scores[8] = this.lines;
//						MathHelper.sortArry(scores);
//						ResourceManager.writeHighscores(scores);
//					}
                Game.STATE_MANAGER.backToPrevious(); //Reset
            }
        } else if (key == KeyEvent.VK_ENTER) {
            grid.setAllSquaresToBeStopped();
        }
    }

    @Override
    public void keyReleased(int key) {
    }

    private void stopPieces() {
        for (int i = 0; i < Grid.LINES; i++) {
            for (int j = 0; j < Grid.LINES_SIZE; j++) {
                if (this.grid.getLine(i)[j] != null) {
                    if (grid.getLine(i)[j].isStopping()) {
                        grid.getLine(i)[j].setFixed();
                    }
                }
            }
        }
    }

    private void nextPiece() {
        if (grid.allSquaresAreFixed()) {
            placePentamimo();
        }
    }

    private void drawBackground(Graphics graphics) {
        graphics.setColor(new Color(212, 206, 206));
        graphics.fillRect(0, 0, Window.WIDTH, Window.HEIGHT);

        graphics.setFont(new Font("Roboto", Font.ITALIC + Font.BOLD, 16));
        graphics.setColor(Color.BLACK);
        graphics.drawString("Next:", 820, 30);
    }

    private int count = 0;

    private void drawGrid(Graphics graphics) {
        for (int i = 2; i < Grid.LINES; i++) {
            for (int j = 0; j < Grid.LINES_SIZE; j++) {
                Square square = grid.getLine(i)[j];
                if (square != null) {
                    if (!square.isFixed())
                        graphics.drawImage(ResourceManager.texture("block_" + square.getColor() + ".png"),
                                j * 30, i * 30 - 40, 30, 30, null);
                    else {
                        graphics.drawImage(ResourceManager.texture("block_" + square.getColor() + "_dark.png"),
                                j * 30, i * 30 - 40, 30, 30, null);
                    }
                } else {
                    graphics.drawImage(ResourceManager.texture("block_void.png"),
                            j * 30, i * 30 - 40, 30, 30, null);
                }
            }
        }
    }

    private void drawQueue(Graphics graphics) {
        int count = 0;
        for (Pentamimo p : queue) {
            for (int i = 0; i < p.getSize(); i++) {
                for (int j = 0; j < p.getSize(); j++) {
                    Square square = p.getSquareAt(Pentamimo.Rotation.ROT0, i, j);
                    if (square != null)
                        graphics.drawImage(ResourceManager.texture("block_" + square.getColor() + ".png"),
                                820 + j * 30, 40 + count * 150 + i * 30, 30, 30, null);
                }
            }
            count++;
        }
        if (hold != null) {
            for (int i = 0; i < hold.getSize(); i++) {
                for (int j = 0; j < hold.getSize(); j++) {
                    Square square = hold.getSquareAt(Pentamimo.Rotation.ROT0, i, j);
                    if (square != null)
                        graphics.drawImage(ResourceManager.texture("block_" + square.getColor() + ".png"),
                                520 + j * 30, 340 + i * 30, 30, 30, null);
                }
            }
        }
    }

    private void drawGameOverMessage(Graphics graphics) {
        graphics.setColor(Color.BLACK);
        graphics.fillRect(0, Window.HEIGHT / 2 - 30, Window.WIDTH, 70);
        graphics.setColor(Color.RED);
        graphics.setFont(new Font("Arial", Font.PLAIN, 30));
        graphics.drawString("Game Over!", 15, Window.HEIGHT / 2);
        graphics.setFont(new Font("Arial", Font.PLAIN, 14));
        graphics.drawString("Press esc to return to title screen", 15, Window.HEIGHT / 2 + 30);
    }

    private void placePentamimo() {
        try {
            currentPentamimo = queue.poll();
            currentRotationOriginX = 3;
            currentRotation = Pentamimo.Rotation.ROT0;
            grid.placePentamimo(currentPentamimo, 0, 2, currentRotation);
            queue.add(Pentamimo.LIST.get(randomInt(Pentamimo.LIST.size())));
        } catch (ArrayIndexOutOfBoundsException e) {
            lost = true;
        }
    }

    private void rotateClockwise() {
        fixRotationOrigin();

        if (grid.canPlaceHere(currentPentamimo, currentRotationOriginX,
                currentRotationOriginY, currentRotation.rotateClockwise())) {
            //this.grid.removeTetromino();
            currentRotation = currentRotation.rotateClockwise();
            grid.placePentamimo(currentPentamimo, currentRotationOriginX,
                    currentRotationOriginY, currentRotation);
        }
    }

    private void rotateCounterclockwise() {
        this.fixRotationOrigin();

        if (grid.canPlaceHere(currentPentamimo, currentRotationOriginX,
                currentRotationOriginY, currentRotation.rotateCounterclockwise())) {
            //this.grid.removeTetromino();
            currentRotation = currentRotation.rotateCounterclockwise();
            grid.placePentamimo(currentPentamimo, currentRotationOriginX,
                    currentRotationOriginY, currentRotation);
        }
    }

    private void fixRotationOrigin() {
        if (currentRotationOriginX < 0)
            currentRotationOriginX = 0;
        if (currentRotationOriginX + currentPentamimo.getSize() > Grid.LINES_SIZE)
            currentRotationOriginX = (Grid.LINES_SIZE - 1) - currentPentamimo.getSize();
        if (currentRotationOriginY + currentPentamimo.getSize() > Grid.LINES)
            currentRotationOriginY = (Grid.LINES - 1) - currentPentamimo.getSize();
    }
}