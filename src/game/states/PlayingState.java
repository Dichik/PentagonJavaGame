package game.states;

import framework.gamestates.GameState;
import framework.resources.ResourceManager;
import game.Game;
import game.pieces.Grid;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.stream.IntStream;

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

        public int getY() {
            return y;
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
    private int[][] answer;

    private ArrayBlockingQueue<Pentamimo> queue;
    private Pentamimo currentPentamimo;

    private Pentamimo hold;

    private boolean lost;
    private boolean showSolution = false;

    @Override
    protected void init() {
        this.grid = new Grid();

        this.queue = new ArrayBlockingQueue<>(4);
        this.queue.add(Pentamimo.LIST.get(Pentamimo.USED++));
        this.queue.add(Pentamimo.LIST.get(Pentamimo.USED++));
        this.queue.add(Pentamimo.LIST.get(Pentamimo.USED++));

        blockedSquares = new ArrayList<>();

        createSolution();

        createBlockedSquares(answer);

        this.placePentamimo();

        this.hold = null;

        this.lost = false;

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
        if (!showSolution) {
            this.drawGrid(graphics);
        } else this.drawSolution(graphics);
        this.drawQueue(graphics);

        if (!queue.isEmpty()) {
            lost = !this.grid.foundPlaceForPentamimo(currentPentamimo);
        }

        if (this.lost) {
            this.drawGameOverMessage(graphics);
        }
    }

    @Override
    public void keyPressed(int key) {
        if (key == KeyEvent.VK_RIGHT) {
            if (this.grid.allSquaresCanGoRight() && !lost && !showSolution) {
                this.grid.movePiecesRight();
            }
        } else if (key == KeyEvent.VK_LEFT) {
            if (this.grid.allSquaresCanGoLeft() && !lost && !showSolution) {
                this.grid.movePiecesLeft();
            }
        } else if (key == KeyEvent.VK_DOWN) {
            if (this.grid.allSquaresCanFall() && !lost && !showSolution) {
                this.grid.movePiecesDown();
            }
        } else if (key == KeyEvent.VK_UP) {
            if (this.grid.allSquaresCanGoUP() && !lost && !showSolution) {
                this.grid.movePiecesUp();
            }
        } else if (key == KeyEvent.VK_ESCAPE) {
            if (!lost && !showSolution) {
                Game.STATE_MANAGER.changeState(new PauseMenu());
            } else {
                Game.STATE_MANAGER.clearStack();
                Game.STATE_MANAGER.changeState(new MainMenu());
                Pentamimo.USED = 0;
                lost = false;
            }
        } else if (key == KeyEvent.VK_ENTER) {
            if (thereNoGreenPieces() && !showSolution) {
                if (grid.canSet()) {
                    grid.setAllSquaresToBeStopped();
                }
            }
        } else if (key == KeyEvent.VK_SPACE) {
            if (!lost && !showSolution) {
                this.rotateClockwise();
            }
        } else if (key == KeyEvent.VK_H) {
            if (!lost && !showSolution) {
                showTimes = 0;
            }
        } else if (key == KeyEvent.VK_S) {
            if (!lost) {
                int userOption = JOptionPane.showConfirmDialog(Window.window,
                        "Are you sure?");
                if (userOption == 0) {
                    showSolution = true;
                }
            } else {
                showSolution = true;
            }
        }
    }

    @Override
    public void keyReleased(int key) {
    }

    private void createSolution() {
        Solution solution = new Solution(Pentamimo.LIST);
        solution.findSolution();
        answer = solution.getSolution();
    }

    private void createBlockedSquares(int[][] answer) {
        int BLOCKED_PIECES = 10;
        for (int i = 0; i < BLOCKED_PIECES; i++) {
            Pair pair = new Pair(Math.abs(new Random().nextInt() % (Grid.SIZE - 1)),
                    Math.abs(new Random().nextInt() % (Grid.SIZE - 1)));
            if (answer[pair.getY()][pair.getX()] == 0 && !foundNear(pair)) {
                blockedSquares.add(pair);
                this.grid.getLine(pair.getY())[pair.getX()] = new Square("blue");
                this.grid.getLine(pair.getY())[pair.getX()].setFixed();
                this.grid.getLine(pair.getY())[pair.getX()].setStopping();
            } else i--;
        }
    }

    private boolean foundNear(Pair pair) {
        for (int i = -1; i < pair.getY() + 1; i++) {
            for (int j = -1; j < pair.getX() + 1; j++) {
                Pair p = new Pair(pair.getX() + j, pair.getY() + i);

                for (int q = 0; q < blockedSquares.size(); q++) {
                    if (blockedSquares.get(q).getX() == p.getX()
                            && blockedSquares.get(q).getY() == p.getY())
                        return true;
                }

            }
        }
        return false;
    }

    private boolean find(ArrayList<Pair> blockedSquares, Pair pair) {
        for (int i = 0; i < blockedSquares.size(); i++) {
            if (pair.equals(blockedSquares.get(i))) {
                return true;
            }
        }
        return false;
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

        graphics.setColor(new Color(175, 175, 175));
        graphics.fillRect(761, 16, 199, 536);

        graphics.setColor(Color.BLACK);
        graphics.drawRect(760, 15, 200, 537);

        int[] ys = {32, 190, 375};
        for (int i = 0; i < ys.length; i++) {
            graphics.drawLine(760, ys[i], 960, ys[i]);
        }

        graphics.setFont(new Font("Roboto", Font.ITALIC + Font.BOLD, 16));
        graphics.drawString("Next:", 820, 30);

        graphics.drawRect(70, 70, 420, 420);

        drawHowToPlay(graphics);
    }

    private void drawHowToPlay(Graphics graphics) {
        String[] buttons = {
                "SPACE - rotate figure",
                "H - get a tip",
                "ESC - pause the game",
                "S - get a solution",
                "Move pieces with arrows"
        };
        graphics.drawString("Press button:\n", 550, 200);
        graphics.drawLine(550, 205, 650, 205);
        graphics.drawLine(550, 370, 750, 370);

        IntStream.range(0, buttons.length)
                .forEach(i -> graphics.drawString(buttons[i], 550, 250 + i * 25));
        IntStream.range(0, 5).forEach(i -> {
            graphics.drawOval(535, 240 + 25 * i, 5, 5);
            graphics.fillOval(535, 240 + 25 * i, 5, 5);
        });
    }

    private String countSquares(int x, boolean line, boolean ans) {
        int cnt = 0;
        int inAnswer = 0;
        for (int i = 0; i < Grid.SIZE - 1; i++) {
            for (int j = 0; j < Grid.SIZE - 1; j++) {
                Square square = this.grid.getLine(i)[j];
                if (square != null) {
                    if (line) {
                        if (i == x && square.isFixed() && !square.containsBlue()) cnt++;
                    } else {
                        if (j == x && square.isFixed() && !square.containsBlue()) cnt++;
                    }
                }
                if (line) {
                    if (i == x && answer[i][j] == 1) inAnswer++;
                } else {
                    if (j == x && answer[i][j] == 1) inAnswer++;
                }
            }
        }
        if(!ans) return Integer.toString(inAnswer - cnt);
        else return Integer.toString(inAnswer);
    }

    private int showTimes = 5000;

    private void drawGrid(Graphics graphics) {
        int[][] matrix = grid.getMatrix();

        for (int i = 0; i < Grid.SIZE; i++) {
            for (int j = 0; j < Grid.SIZE; j++) {
                Square square = this.grid.getLine(i)[j];
                if (checkBorders(graphics, i, j, false)) continue;

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
                        if (matrix[i][j] == 2 && showTimes <= 500) {
                            graphics.setColor(Color.GRAY);
                            graphics.fillRect(j * 30 + 100 + 2, i * 30 + 100 + 2, 30 - 3, 30 - 3);
                            graphics.setColor(Color.BLACK);
                            showTimes++;
                        }
                    }
                }
            }
        }
    }

    private void drawSolution(Graphics graphics) {
        for (int i = 0; i < Grid.SIZE; i++) {
            for (int j = 0; j < Grid.SIZE; j++) {
                if (checkBorders(graphics, i, j, true)) continue;

                if (answer[i][j] != 0) {
                    graphics.drawImage(ResourceManager.texture("block_orange_dark.png"), j * 30 + 100, i * 30 + 100, 30, 30, null);
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

    private boolean checkBorders(Graphics graphics, int i, int j, boolean ans) {
        if (i == Grid.SIZE - 1 || j == Grid.SIZE - 1) {
            if (i == 0) return true;
            if (j == Grid.SIZE - 1)
                graphics.drawString(countSquares(i - 1, true, ans), j * 30 + 110, i * 30 - 10 + 100);
            else graphics.drawString(countSquares(j, false, ans), j * 30 + 110, i * 30 - 10 + 30 + 100);
            return true;
        }
        return false;
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
        if (!showSolution) {
            graphics.setColor(Color.BLACK);
            graphics.fillRect(0, Window.HEIGHT / 2 - 30, Window.WIDTH, 70);
            graphics.setColor(Color.RED);
            graphics.setFont(new Font("Arial", Font.PLAIN, 30));
            graphics.drawString("Game Over!", 15, Window.HEIGHT / 2);
            graphics.setFont(new Font("Arial", Font.PLAIN, 14));
            graphics.drawString("Press esc to return to title screen"
                    + "or press S to see the solution", 15, Window.HEIGHT / 2 + 30);
        }
    }

    private void placePentamimo() {
        try {
            if (!queue.isEmpty()) {
                if (this.grid.foundPlaceForPentamimo(queue.peek()))
                    this.currentPentamimo = this.queue.poll();
                else this.currentPentamimo = this.queue.peek();
            } else {
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