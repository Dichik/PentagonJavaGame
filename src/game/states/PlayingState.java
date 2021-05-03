package game.states;

import framework.gamestates.GameState;
import framework.resources.ResourceManager;
import game.Game;
import game.pieces.Grid;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
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

    private int points;
    private boolean lost;
    public int[][] countPieces;

    private static int randomInt(int SIZE) {
        return new Random().nextInt(SIZE);
    }

    @Override
    protected void init() {
        this.grid = new Grid();

        this.queue = new ArrayBlockingQueue<>(4);
        this.queue.add(Pentamimo.LIST.get(randomInt(Pentamimo.LIST.size())));
        this.queue.add(Pentamimo.LIST.get(randomInt(Pentamimo.LIST.size())));
        this.queue.add(Pentamimo.LIST.get(randomInt(Pentamimo.LIST.size())));

        this.placePentamimo();

        this.hold = null;

        this.lost = false;

        countPieces = new int[Grid.SIZE][Grid.SIZE];
        for (int i = 0; i < Grid.SIZE; i++) {
            for (int j = 0; j < Grid.SIZE; j++) {
                countPieces[i][j] = 0;
            }
        }

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
        this.drawBackground(graphics);
        this.drawGrid(graphics);
        this.drawQueue(graphics);

        if (this.lost)
            this.drawGameOverMessage(graphics);
    }

    /**
     * 2 options to move the figure:
     * 1 -> make a matrix with filled pieces and make a NULL only when there n = 1.
     * 2 -> jump over the figure, just find a first place where we can place our figure.
     *
     * @param key
     */
    @Override
    public void keyPressed(int key) {
        /**
         * should make ability to drag a piece
         * because when we use keys it makes lots of collapse and troubles.
         */
        if (key == KeyEvent.VK_RIGHT) {
            if (this.grid.allSquaresCanGoRight() && !lost) {
                this.grid.movePiecesRight();
                this.currentRotationOriginX++;
            }
        } else if (key == KeyEvent.VK_LEFT) {
            if (this.grid.allSquaresCanGoLeft() && !lost) {
                this.grid.movePiecesLeft();
                this.currentRotationOriginX--;
            }
        } else if (key == KeyEvent.VK_DOWN) {
            if (this.grid.allSquaresCanFall() && !lost) {
                this.grid.movePiecesDown();
                this.currentRotationOriginY++;
            }
        } else if (key == KeyEvent.VK_UP) {
            if (this.grid.allSquaresCanGoUP() && !lost) {
                this.grid.movePiecesUp();
                this.currentRotationOriginY--;
            }
        } else if (key == KeyEvent.VK_ESCAPE) {
            if (!lost) {
                Game.STATE_MANAGER.changeState(new PauseMenu());
            } else {
                int[] scores = ResourceManager.readTopScores();
                if (this.points > scores[8]) {
                    scores[8] = this.points;
                    Arrays.sort(scores);
                    ResourceManager.writeScores(getReversed(scores));
                }
                Game.STATE_MANAGER.backToPrevious(); //Reset
            }
        } else if (key == KeyEvent.VK_ENTER) {
            if (thereNoGreenPieces()) {
                if (grid.canSet()) grid.setAllSquaresToBeStopped();
            }
        }
    }

    public static Object[] getReversed(int[] arr) {
        List<int[]> list = Arrays.asList(arr);
        Collections.reverse(list);
        return list.toArray();
    }

    @Override
    public void keyReleased(int key) {
    }

    private void stopPieces() {
        for (int i = 0; i < Grid.SIZE; i++) {
            for (int j = 0; j < Grid.SIZE; j++) {
                if (this.grid.getLine(i)[j] != null) {
                    if (this.grid.getLine(i)[j].isStopping()) {
                        this.grid.getLine(i)[j].setFixed();
                    }
                }
            }
        }
    }

    private void nextPiece() {
        if (this.grid.allSquaresAreFixed() && thereNoGreenPieces()) {
            this.placePentamimo();
        }
    }

    private boolean thereNoGreenPieces() {
        for (int i = 0; i < Grid.SIZE; i++) {
            for (int j = 0; j < Grid.SIZE; j++) {
                Square square = this.grid.getLine(i)[j];
                if (square != null) {
                    if (square.hasAnotherColor()) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    private void drawBackground(Graphics graphics) {
        graphics.setColor(new Color(212, 206, 206));
        graphics.fillRect(0, 0, Window.WIDTH, Window.HEIGHT);

        graphics.setColor(Color.BLACK);
        graphics.drawRect(760, 15, 200, 537);
        graphics.setColor(new Color(175, 175, 175));
        graphics.fillRect(761, 16, 199, 536);

        graphics.setColor(Color.BLACK);
        graphics.drawLine(760, 32, 960, 32);
        graphics.drawLine(760, 190, 960, 190);
        graphics.drawLine(760, 375, 960, 375);

        graphics.setFont(new Font("Roboto", Font.ITALIC + Font.BOLD, 16));
        graphics.drawString("Next:", 820, 30);
    }

    private String countSquares(int x, boolean line) {
        int cnt = 0;
        for (int i = 0; i < Grid.SIZE - 1; i++) {
            for (int j = 0; j < Grid.SIZE - 1; j++) {
                Square square = this.grid.getLine(i)[j];
                if (square != null) {
                    if (line) {
                        if (i == x && square.isFixed()) cnt++;
                    } else {
                        if (j == x && square.isFixed()) cnt++;
                    }
                }
            }
        }
        return Integer.toString(cnt);
    }

    private void drawGrid(Graphics graphics) {
        for (int i = 0; i < Grid.SIZE; i++) {
            for (int j = 0; j < Grid.SIZE; j++) {
                countPieces[i][j] = 0;
            }
        }
        for (int i = 0; i < Grid.SIZE; i++) {
            for (int j = 0; j < Grid.SIZE; j++) {
                Square square = this.grid.getLine(i)[j];
                if (i == Grid.SIZE - 1 || j == Grid.SIZE - 1) {
                    if (j == Grid.SIZE - 1)
                        graphics.drawString(countSquares(i - 1, true), j * 30 + 10, i * 30 - 10);
                    else graphics.drawString(countSquares(j, false), j * 30 + 10, i * 30 - 10 + 30);
                    continue;
                }
                /*~~~~matrix~~~~*/
                if (square != null) {
                    if (square.isFixed()) {
                        countPieces[i][j]++;
                    }
                    if (!square.isFixed()) {
                        countPieces[i][j]++;
                    }
                }
                /*~~~~~~~~~~~~~~*/
                if (square != null) {
                    if (square.hasAnotherColor()) {
                        graphics.drawImage(ResourceManager.texture("block_" +
                                "green"
                                + ".png"), j * 30, i * 30, 30, 30, null);
                        continue;
                    }
                    if (!square.isFixed())
                        graphics.drawImage(ResourceManager.texture("block_" +
                                square.getColor()
                                + ".png"), j * 30, i * 30, 30, 30, null);
                    else {
                        graphics.drawImage(ResourceManager.texture("block_" +
                                square.getColor()
                                + "_dark.png"), j * 30, i * 30, 30, 30, null);
                    }
                } else {
                    graphics.drawImage(ResourceManager.texture("block_void.png"),
                            j * 30, i * 30, 30, 30, null);
                }
            }
        }
//        if (count-- > 0) {
//            for (int i = 0; i < Grid.LINES; i++) {
//                for (int j = 0; j < Grid.LINES; j++) {
//                    System.out.print(countPieces[i][j] + " ");
//                }
//                System.out.print("\n");
//            }
//            System.out.println("~~~~~~~~~~~~~~~");
//        }
    }

    private void drawQueue(Graphics graphics) {
        int count = 0;
        for (Pentamimo p : this.queue) {
            for (int i = 0; i < p.getSize(); i++) {
                for (int j = 0; j < p.getSize(); j++) {
                    Square square = p.getSquareAt(Pentamimo.Rotation.ROT0, i, j);
                    if (square != null)
                        graphics.drawImage(ResourceManager.texture("block_" + square.getColor() + ".png"),
                                820 + j * 30, 40 + count * 180 + i * 30, 30, 30, null);
                }
            }
            count++;
        }
        if (this.hold != null) {
            for (int i = 0; i < this.hold.getSize(); i++) {
                for (int j = 0; j < this.hold.getSize(); j++) {
                    Square square = this.hold.getSquareAt(Pentamimo.Rotation.ROT0, i, j);
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
            this.currentPentamimo = this.queue.poll();
            this.currentRotation = Pentamimo.Rotation.ROT0;
            this.grid.placePentamimo(currentPentamimo, 0, 0, currentRotation);
            this.queue.add(Pentamimo.LIST.get(randomInt(Pentamimo.LIST.size())));
        } catch (ArrayIndexOutOfBoundsException e) {
            this.lost = true;
        }
    }
    /*
    private void rotateClockwise() {
        this.fixRotationOrigin();

        if (this.grid.canPlaceHere(currentPentamimo, currentRotationOriginX, currentRotationOriginY, currentRotation.rotateClockwise())) {
            //this.grid.removeTetromino();
            this.currentRotation = this.currentRotation.rotateClockwise();
            this.grid.placePentamimo(currentPentamimo, currentRotationOriginX, currentRotationOriginY, currentRotation);
        }
    }

    private void rotateCounterclockwise() {
        this.fixRotationOrigin();

        if (this.grid.canPlaceHere(currentPentamimo, currentRotationOriginX, currentRotationOriginY, currentRotation.rotateCounterclockwise())) {
            //this.grid.removeTetromino();
            this.currentRotation = this.currentRotation.rotateCounterclockwise();
            this.grid.placePentamimo(currentPentamimo, currentRotationOriginX, currentRotationOriginY, currentRotation);
        }
    }

    private void fixRotationOrigin() {
        if (this.currentRotationOriginX < 0)
            this.currentRotationOriginX = 0;
        if (this.currentRotationOriginX + this.currentPentamimo.getSize() > Grid.LINES_SIZE)
            this.currentRotationOriginX = (Grid.LINES_SIZE - 1) - this.currentPentamimo.getSize();
        if (this.currentRotationOriginY + this.currentPentamimo.getSize() > Grid.LINES)
            this.currentRotationOriginY = (Grid.LINES - 1) - this.currentPentamimo.getSize();
    }
     */
}