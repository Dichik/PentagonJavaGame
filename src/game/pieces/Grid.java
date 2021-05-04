package game.pieces;

public class Grid {
    public static final int SIZE = 16;
    private Square[][] singlePieces;

    private int[][] countPieces = new int[SIZE - 1][SIZE - 1];

    /**
     * countPieces should be an array 3-D where every i and j place have MAX two colors
     * it's like stack -> last in and first out.
     */

    public Grid() {
        this.singlePieces = new Square[SIZE][SIZE];
    }

    public void placePentamimo(Pentamimo pentamimo, int locationX, int locationY, Pentamimo.Rotation rotation) {
        for (int i = 0; i < pentamimo.getSize(); i++) {
            for (int j = 0; j < pentamimo.getSize(); j++) {
                if (pentamimo.getSquareAt(rotation, i, j) != null) {
                    if (singlePieces[i][j] == null)
                        this.singlePieces[locationY + i][locationX + j] = pentamimo.getSquareAt(rotation, i, j);
                    else singlePieces[i][j].add("orange");
                }
            }
        }
    }

    public Square[] getLine(int i) {
        return singlePieces[i];
    }

    public boolean allSquaresCanFall() {
        for (int i = Grid.SIZE - 1; i >= 0; i--) {
            for (int j = 0; j < Grid.SIZE; j++) {
                if (this.singlePieces[i][j] != null) {
                    if (!this.singlePieces[i][j].isFixed() && i == Grid.SIZE - 2) {
                        return false;
                    }
                }
            }
        }
        for (int i = 0; i < Grid.SIZE; i++) {
            Square square = getLine(Grid.SIZE - 2)[i];
            if (square != null) {
                if (square.hasAnotherColor()) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean allSquaresCanGoUP() {
        for (int i = 0; i < Grid.SIZE; i++) {
            for (int j = 0; j < Grid.SIZE; j++) {
                if (this.singlePieces[i][j] != null) {
                    if (!this.singlePieces[i][j].isFixed() && i == 0) {
                        return false;
                    }
                }
            }
        }
        for (int i = 0; i < Grid.SIZE; i++) {
            Square square = getLine(0)[i];
            if (square != null) {
                if (square.hasAnotherColor()) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean allSquaresCanGoLeft() {
        for (int i = 0; i < Grid.SIZE; i++) {
            for (int j = 0; j < Grid.SIZE; j++) {
                if (this.singlePieces[i][j] != null) {
                    if (!this.singlePieces[i][j].isFixed() && j == 0)
                        return false;
                }
            }
        }
        for (int i = 0; i < Grid.SIZE; i++) {
            Square square = getLine(i)[0];
            if (square != null) {
                if (square.hasAnotherColor()) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean allSquaresCanGoRight() {
        for (int i = 0; i < Grid.SIZE; i++) {
            for (int j = Grid.SIZE - 2; j >= 0; j--) {
                if (this.singlePieces[i][j] != null) {
                    if (!this.singlePieces[i][j].isFixed() && j == Grid.SIZE - 2) {
                        return false;
                    }
                }
            }
        }
        for (int i = 0; i < Grid.SIZE; i++) {
            Square square = getLine(i)[Grid.SIZE - 2];
            if (square != null) {
                if (square.hasAnotherColor()) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean allSquaresAreFixed() {
        for (int i = 0; i < Grid.SIZE; i++) {
            for (int j = 0; j < Grid.SIZE; j++) {
                if (this.singlePieces[i][j] != null) {
                    if (!this.singlePieces[i][j].isFixed()) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public void movePiecesDown() {
        for (int i = Grid.SIZE - 2; i >= 0; i--) {
            for (int j = 0; j < Grid.SIZE - 1; j++) {
                if (this.singlePieces[i][j] != null) {
                    if (!this.singlePieces[i][j].isFixed() || this.singlePieces[i][j].hasAnotherColor()) {
                        if (!this.singlePieces[i][j].isFixed()) {
                            if (this.singlePieces[i + 1][j] == null) {
                                this.singlePieces[i + 1][j] = this.singlePieces[i][j];
                            } else {
                                this.singlePieces[i + 1][j].add(this.singlePieces[i + 1][j].getColor());
                            }
                            if (!this.singlePieces[i][j].hasAnotherColor())
                                this.singlePieces[i][j] = null;
                            else this.singlePieces[i][j].removeColor();
                        } else {
                            if (this.singlePieces[i + 1][j] == null) {
                                this.singlePieces[i + 1][j] = new Square(this.singlePieces[i][j].getColor());
                            } else {
                                this.singlePieces[i + 1][j].add(this.singlePieces[i][j].getColor());
                            }
                            this.singlePieces[i][j].removeColor();
                        }
                    }
                }
            }
        }
    }

    public void movePiecesUp() {
        for (int i = 0; i < Grid.SIZE; i++) {
            for (int j = 0; j < Grid.SIZE; j++) {
                if (this.singlePieces[i][j] != null) {
                    if (!this.singlePieces[i][j].isFixed() || this.singlePieces[i][j].hasAnotherColor()) {
                        if (!this.singlePieces[i][j].isFixed()) {
                            if (this.singlePieces[i - 1][j] == null) {
                                this.singlePieces[i - 1][j] = this.singlePieces[i][j];
                            } else {
                                this.singlePieces[i - 1][j].add(this.singlePieces[i - 1][j].getColor());
                            }
                            if (!this.singlePieces[i][j].hasAnotherColor())
                                this.singlePieces[i][j] = null;
                            else this.singlePieces[i][j].removeColor();
                        } else {
                            if (this.singlePieces[i - 1][j] == null) {
                                this.singlePieces[i - 1][j] = new Square(this.singlePieces[i][j].getColor());
                            } else {
                                this.singlePieces[i - 1][j].add(this.singlePieces[i][j].getColor());
                            }
                            this.singlePieces[i][j].removeColor();
                        }
                    }
                }
            }
        }
    }

    public void movePiecesLeft() {
        for (int i = 0; i < Grid.SIZE; i++) {
            for (int j = 1; j < Grid.SIZE; j++) {
                if (this.singlePieces[i][j] != null) {
                    if (!this.singlePieces[i][j].isFixed() || this.singlePieces[i][j].hasAnotherColor()) {
                        if (!this.singlePieces[i][j].isFixed()) {
                            if (this.singlePieces[i][j - 1] == null) {
                                this.singlePieces[i][j - 1] = this.singlePieces[i][j];
                            } else {
                                this.singlePieces[i][j - 1].add(this.singlePieces[i][j - 1].getColor());
                            }
                            if (!this.singlePieces[i][j].hasAnotherColor())
                                this.singlePieces[i][j] = null;
                            else this.singlePieces[i][j].removeColor();
                        } else {
                            if (this.singlePieces[i][j - 1] == null) {
                                this.singlePieces[i][j - 1] = new Square(this.singlePieces[i][j].getColor());
                            } else {
                                this.singlePieces[i][j - 1].add(this.singlePieces[i][j].getColor());
                            }
                            this.singlePieces[i][j].removeColor();
                        }
                    }
                }
            }
        }
    }

    public void movePiecesRight() {
        for (int i = 0; i < Grid.SIZE; i++) {
            for (int j = Grid.SIZE - 2; j >= 0; j--) {
                if (this.singlePieces[i][j] != null) {
                    if (!this.singlePieces[i][j].isFixed() || this.singlePieces[i][j].hasAnotherColor()) {
                        if (!this.singlePieces[i][j].isFixed()) {
                            if (this.singlePieces[i][j + 1] == null) {
                                this.singlePieces[i][j + 1] = this.singlePieces[i][j];
                            } else {
                                this.singlePieces[i][j + 1].add(this.singlePieces[i][j + 1].getColor());
                            }
                            if (!this.singlePieces[i][j].hasAnotherColor())
                                this.singlePieces[i][j] = null;
                            else this.singlePieces[i][j].removeColor();
                        } else {
                            if (this.singlePieces[i][j + 1] == null) {
                                this.singlePieces[i][j + 1] = new Square(this.singlePieces[i][j].getColor());
                            } else {
                                this.singlePieces[i][j + 1].add(this.singlePieces[i][j].getColor());
                            }
                            this.singlePieces[i][j].removeColor();
                        }
                    }
                }
            }
        }
    }

    public void setAllSquaresToBeStopped() {
        for (int i = Grid.SIZE - 1; i >= 0; i--) {
            for (int j = 0; j < Grid.SIZE; j++) {
                if (this.singlePieces[i][j] != null) {
                    if (!this.singlePieces[i][j].isFixed() && !this.singlePieces[i][j].isStopping()) {
                        this.singlePieces[i][j].setStopping();
                        countPieces[i][j] = 1;
                    }
                }
            }
        }
    }

    public boolean canPlaceHere(Pentamimo pentamimo, int locationX, int locationY, Pentamimo.Rotation rotation) {
        for (int i = 0; i < pentamimo.getSize(); i++) {
            for (int j = 0; j < pentamimo.getSize(); j++) {
                if (pentamimo.getSquareAt(rotation, i, j) != null && this.singlePieces[locationY + i][locationX + j] != null && this.singlePieces[locationY + i][locationX + j].isFixed()) {

                    return false;
                }
            }
        }
        return true;
    }

    public boolean canSet() {
        for (int i = Grid.SIZE - 1; i >= 0; i--) {
            for (int j = 0; j < Grid.SIZE - 1; j++) {
                if (this.singlePieces[i][j] != null) {
                    if (!this.singlePieces[i][j].isFixed() && !this.singlePieces[i][j].isStopping()) {
                        if (i < Grid.SIZE - 2 && singlePieces[i + 1][j] != null)
                            if (singlePieces[i + 1][j].isFixed())
                                return false;
                        if (i > 0 && singlePieces[i - 1][j] != null)
                            if (singlePieces[i - 1][j].isFixed())
                                return false;
                        if (j < Grid.SIZE - 2 && singlePieces[i][j + 1] != null)
                            if (singlePieces[i][j + 1].isFixed())
                                return false;
                        if (j > 0 && singlePieces[i][j - 1] != null)
                            if (singlePieces[i][j - 1].isFixed())
                                return false;
                        if (i > 0 && j < Grid.SIZE - 2 && singlePieces[i - 1][j + 1] != null)
                            if (singlePieces[i - 1][j + 1].isFixed())
                                return false;
                        if (i > 0 && j > 0 && singlePieces[i - 1][j - 1] != null)
                            if (singlePieces[i - 1][j - 1].isFixed())
                                return false;
                        if (i < Grid.SIZE - 2 && j < Grid.SIZE - 2 && singlePieces[i + 1][j + 1] != null)
                            if (singlePieces[i + 1][j + 1].isFixed())
                                return false;
                        if (j > 0 && i < Grid.SIZE - 2 && singlePieces[i + 1][j - 1] != null)
                            if (singlePieces[i + 1][j - 1].isFixed())
                                return false;
                    }
                }
            }
        }
        System.out.println("Here");
        return true;
    }

    public void removePentamimo() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (this.singlePieces[i][j] != null) {
                    if (!this.singlePieces[i][j].isFixed()
                            || this.singlePieces[i][j].hasAnotherColor()) {
                        if(!this.singlePieces[i][j].isFixed())
                            this.singlePieces[i][j] = null;
                        else this.singlePieces[i][j].removeColor();
                    }
                }
            }
        }
    }

/**
 * when we touch the 16-th place we draw that line in light grey color
 * so that the user could see number of used squares in the line.
 */

}
