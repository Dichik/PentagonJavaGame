package game.states;

import framework.gamestates.GameState;
import framework.resources.ResourceManager;
import game.Game;
import game.pieces.Grid;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.*;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;

import framework.display.Window;
import game.pieces.Pentamimo;
import game.pieces.Square;

import javax.swing.*;

public class PlayingState extends GameState {
    class Pair {
        private int x;
        private int y;

        public Pair(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public int getX() {
            return x;
        }

        public void setX(int x) {
            this.x = x;
        }

        public int getY() {
            return y;
        }

        public void setY(int y) {
            this.y = y;
        }

        @Override
        public boolean equals(Object o) {
            Pair pair = (Pair) o;
            return x == pair.x &&
                    y == pair.y;
        }
    }

    /**
     * when we choose level we also set the timer for solving this game
     * and when the time is over we will get the massage about losing the game.
     */
    private Grid grid;

    private Pentamimo.Rotation currentRotation;
    private ArrayList<Pair> blockedSquares;

    private ArrayBlockingQueue<Pentamimo> queue;
    private Pentamimo currentPentamimo;

    private Pentamimo hold;

    private int points;
    /**
     * points should be calculated by special formula
     * (max_minutes - level_minutes) / 2 + level_minutes - finish_time
     * Problems:
     * 1.should calculate time
     * 2.should create a timer
     * ...
     */
    private boolean lost;

    @Override
    protected void init() {
        this.grid = new Grid();

        this.queue = new ArrayBlockingQueue<>(4);
        this.queue.add(Pentamimo.LIST.get(Pentamimo.USED++));
        this.queue.add(Pentamimo.LIST.get(Pentamimo.USED++));
        this.queue.add(Pentamimo.LIST.get(Pentamimo.USED++));

        blockedSquares = new ArrayList<>();
        //createBlockedSquares();

        this.placePentamimo();

        this.hold = null;

        this.lost = false;

        System.out.println("[Game][States]: Created playing state");
    }

    @Override
    public void tick() {

        if (lost) {
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        stopPieces();
        if (!lost) {
            nextPiece();
        }
    }

    @Override
    public void render(Graphics graphics) {
        this.drawBackground(graphics);
        this.drawGrid(graphics);
        this.drawQueue(graphics);

        if (!this.grid.foundPlaceForPentamimo(currentPentamimo)) {
            lost = true;
        }

        if (this.lost) {
            this.drawGameOverMessage(graphics);
        }
        /**
         * Bug #13
         * Add a button or a small instruction on the left panel
         * there add a button to get a tip -> place where we can place a pentamimo and
         * draw it in the grey for a second
         */


        /**
         * Bug #11
         * change drawGameOverMessage -> message with options = stay | left | restart
         */
    }

    private void createBlockedSquares() {
        int BLOCKED_PIECES = 10;
        for (int i = 0; i < BLOCKED_PIECES; i++) {
            Pair pair = new Pair(Math.abs(new Random().nextInt() % Grid.SIZE),
                    Math.abs(new Random().nextInt() % Grid.SIZE));
            if (!find(blockedSquares, pair)) {
                blockedSquares.add(pair);
                this.grid.getLine(pair.getY())[pair.getX()] = new Square("blue");
                this.grid.getLine(pair.getY())[pair.getX()].setFixed();
                this.grid.getLine(pair.getY())[pair.getX()].setStopping();
            } else i--;
        }
    }

    private boolean find(ArrayList<Pair> blockedSquares, Pair pair) {
        for (int i = 0; i < blockedSquares.size(); i++) {
            if (pair.equals(blockedSquares.get(i))) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void keyPressed(int key) {
        if (key == KeyEvent.VK_RIGHT) {
            if (this.grid.allSquaresCanGoRight() && !lost) {
                this.grid.movePiecesRight();
            }
        } else if (key == KeyEvent.VK_LEFT) {
            if (this.grid.allSquaresCanGoLeft() && !lost) {
                this.grid.movePiecesLeft();
            }
        } else if (key == KeyEvent.VK_DOWN) {
            if (this.grid.allSquaresCanFall() && !lost) {
                this.grid.movePiecesDown();
            }
        } else if (key == KeyEvent.VK_UP) {
            if (this.grid.allSquaresCanGoUP() && !lost) {
                this.grid.movePiecesUp();
            }
        } else if (key == KeyEvent.VK_ESCAPE) {
            if (!lost) {
                Game.STATE_MANAGER.changeState(new PauseMenu());
            } else {
//                int[] scores = ResourceManager.readTopScores();
//                if (this.points > scores[8]) {
//                    scores[8] = this.points;
//                    Arrays.sort(scores);
//                    ResourceManager.writeScores(getReversed(scores));
//                }
                Game.STATE_MANAGER.clearStack();
                Game.STATE_MANAGER.changeState(new MainMenu());
                Pentamimo.USED = 0;
                lost = false;
            }
        } else if (key == KeyEvent.VK_ENTER) {
            if (thereNoGreenPieces()) {
                if (grid.canSet()){
                    grid.setAllSquaresToBeStopped();
                }
            }
        } else if (key == KeyEvent.VK_SPACE && !lost) {
            this.rotateClockwise();
        } else if (key == KeyEvent.VK_S) {
            drawMatrix();
        }
    }

    private void drawMatrix() {
        for (int i = 0; i < Grid.SIZE; i++) {
            for (int j = 0; j < Grid.SIZE; j++) {
                if (this.grid.getLine(i)[j] != null) {
                    if (this.grid.getLine(i)[j].isFixed())
                        System.out.print(1 + " ");
                    else System.out.print(0 + " ");
                } else {
                    System.out.print(0 + " ");
                }
            }
            System.out.print("\n");
        }
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~");
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
        if (this.grid.allSquaresAreFixed() && thereNoGreenPieces() && !lost) {
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

        graphics.drawRect(70, 70, 420, 420);
    }

    private String countSquares(int x, boolean line) {
        int cnt = 0;
        for (int i = 0; i < Grid.SIZE - 1; i++) {
            for (int j = 0; j < Grid.SIZE - 1; j++) {
                Square square = this.grid.getLine(i)[j];
                if (square != null) {
                    if (line) {
                        if (i == x && square.isFixed()
                                && !square.getColor().equals("blue")) cnt++;
                    } else {
                        if (j == x && square.isFixed()
                                && !square.getColor().equals("blue")) cnt++;
                    }
                }
            }
        }
        return Integer.toString(cnt);
    }

    /**
     * Bug #7
     * when we move our figure and it cross black square,
     * then a number in the SIZE line / row increase by one
     */

    private void drawGrid(Graphics graphics) {
        for (int i = 0; i < Grid.SIZE; i++) {
            for (int j = 0; j < Grid.SIZE; j++) {
                Square square = this.grid.getLine(i)[j];
                if (i == Grid.SIZE - 1 || j == Grid.SIZE - 1) {
                    if (i == 0) continue;
                    if (j == Grid.SIZE - 1)
                        graphics.drawString(countSquares(i - 1, true), j * 30 + 110, i * 30 - 10 + 100);
                    else graphics.drawString(countSquares(j, false), j * 30 + 110, i * 30 - 10 + 30 + 100);
                    continue;
                }
                if (square != null) {
                    if (square.hasAnotherColor()) {
                        graphics.drawImage(ResourceManager.texture("block_" +
                                "green" + ".png"), j * 30 + 100, i * 30 + 100, 30, 30, null);
                        continue;
                    }
                    if (!square.isFixed())
                        graphics.drawImage(ResourceManager.texture("block_" +
                                square.getColor()
                                + ".png"), j * 30 + 100, i * 30 + 100, 30, 30, null);
                    else {
                        graphics.drawImage(ResourceManager.texture("block_" +
                                square.getColor()
                                + "_dark.png"), j * 30 + 100, i * 30 + 100, 30, 30, null);
                    }
                } else {
                    Pair pair = new Pair(j, i);
                    if (find(blockedSquares, pair)) {
                        graphics.drawImage(ResourceManager.texture("block_blue_dark.png"),
                                j * 30 + 100, i * 30 + 100, 30, 30, null);
                    } else {
                        graphics.drawImage(ResourceManager.texture("block_void.png"),
                                j * 30 + 100, i * 30 + 100, 30, 30, null);
                    }
                }
            }
        }
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
        stopPieces();
        graphics.setColor(Color.BLACK);
        graphics.fillRect(0, Window.HEIGHT / 2 - 30, Window.WIDTH, 70);
        graphics.setColor(Color.RED);
        graphics.setFont(new Font("Arial", Font.PLAIN, 30));
        graphics.drawString("Game Over!", 15, Window.HEIGHT / 2);
        graphics.setFont(new Font("Arial", Font.PLAIN, 14));
        graphics.drawString("Press esc to return to title screen", 15, Window.HEIGHT / 2 + 30);

        /**
         * go to the pause menu -> but add a parameter that will block "continue"
         */
    }

    private void placePentamimo() {
        if(lost){
            return ;
        }
        try {
            if (!queue.isEmpty())
                this.currentPentamimo = this.queue.poll();
            else {
                JOptionPane.showMessageDialog(Window.window,
                        "Congratulations!\nYou've done that");
                Game.STATE_MANAGER.clearStack();
                Game.STATE_MANAGER.changeState(new MainMenu());
                Pentamimo.USED = 0;
                this.currentPentamimo = null;
                return;
            }
            this.currentRotation = Pentamimo.Rotation.ROT0;
            if (Pentamimo.USED <= Pentamimo.LIST.size() || !queue.isEmpty())
                if (currentPentamimo != null && this.grid.foundPlaceForPentamimo(currentPentamimo))
                    this.grid.placePentamimo(currentPentamimo, 0, 0, currentRotation);

            if (Pentamimo.USED < Pentamimo.LIST.size())
                this.queue.add(Pentamimo.LIST.get(Pentamimo.USED++));
        } catch (ArrayIndexOutOfBoundsException e) {
            this.lost = true;
        }
    }

    private void rotateClockwise() {
        this.grid.removePentamimo();
        this.currentRotation = this.currentRotation.rotateClockwise();
        this.grid.placePentamimo(currentPentamimo, 0, 0, currentRotation);
    }
}