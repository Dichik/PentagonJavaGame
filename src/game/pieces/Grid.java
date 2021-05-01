package game.pieces;

public class Grid {
    public static final int LINES = 16;
    public static final int LINES_SIZE = 16;
    private Square[][] singlePieces;

    public Grid() {
        this.singlePieces = new Square[LINES][LINES_SIZE];
    }

    public void placePentamimo(Pentamimo pentamimo, int locationX, int locationY, Pentamimo.Rotation rotation) {
        for (int i = 0; i < pentamimo.getSize(); i++) {
            for (int j = 0; j < pentamimo.getSize(); j++) {
                if (pentamimo.getSquareAt(rotation, i, j) != null) {
                    this.singlePieces[locationY + i][locationX + j] = pentamimo.getSquareAt(rotation, i, j);
                }
            }
        }
    }

    public Square[] getLine(int i) {
        return singlePieces[i];
    }

    public boolean allSquaresCanFall() {
        for (int i = Grid.LINES - 1; i >= 0; i--) {
            for (int j = 0; j < Grid.LINES_SIZE; j++) {
                if (this.singlePieces[i][j] != null) {
                    if ((!this.singlePieces[i][j].isFixed() && i == Grid.LINES - 1) || (!this.singlePieces[i][j].isFixed() && this.singlePieces[i + 1][j] != null && this.singlePieces[i + 1][j].isFixed())) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public boolean allSquaresCanGoUP() {
        for (int i = 0; i < Grid.LINES; i++) {
            for (int j = 0; j < Grid.LINES_SIZE; j++) {
                Square from = singlePieces[i][j];
                Square to = singlePieces[i - 1][j];
                if (from != null && !from.isFixed() && i == 2
                        || (!from.isFixed() && to != null && to.isFixed())) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean allSquaresCanGoLeft() {
        for (int i = 0; i < Grid.LINES; i++) {
            for (int j = 0; j < Grid.LINES_SIZE; j++) {
                Square from = singlePieces[i][j];
                Square to = singlePieces[i][j - 1];
                if (from != null && !from.isFixed() && j == 0 || (!from.isFixed()
                        && to != null && to.isFixed())) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean allSquaresCanGoRight() {
        for (int i = 0; i < Grid.LINES; i++) {
            for (int j = Grid.LINES_SIZE - 1; j >= 0; j--) {
                Square from = singlePieces[i][j];
                Square to = singlePieces[i][j + 1];
                if (from != null && !from.isFixed() && j == Grid.LINES_SIZE - 1
                        || (!from.isFixed() && to != null && to.isFixed())) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean allSquaresAreFixed() {
        for (int i = 0; i < Grid.LINES; i++) {
            for (int j = 0; j < Grid.LINES_SIZE; j++) {
                if (singlePieces[i][j] != null && !singlePieces[i][j].isFixed()) {
                    return false;
                }
            }
        }
        return true;
    }

    public void movePiecesDown() {
        for (int i = Grid.LINES - 2; i >= 0; i--) {
            for (int j = 0; j < Grid.LINES_SIZE; j++) {
                if (singlePieces[i][j] != null && !singlePieces[i][j].isFixed()) {
                    singlePieces[i + 1][j] = singlePieces[i][j];
                    singlePieces[i][j] = null;
                }
            }
        }
    }

    public void movePiecesUp() {
        for (int i = 0; i < Grid.LINES; i++) {
            for (int j = 0; j < Grid.LINES_SIZE; j++) {
                if (singlePieces[i][j] != null) {
                    if (!singlePieces[i][j].isFixed()) {
                        singlePieces[i - 1][j] = singlePieces[i][j];
                        singlePieces[i][j] = null;
                    }
                }
            }
        }
    }

    public void movePiecesLeft() {
        for (int i = 0; i < Grid.LINES; i++) {
            for (int j = 1; j < Grid.LINES_SIZE; j++) {
                if (singlePieces[i][j] != null) {
                    if (!singlePieces[i][j].isFixed()) {
                        singlePieces[i][j - 1] = singlePieces[i][j];
                        singlePieces[i][j - 1].resetStopTime();
                        singlePieces[i][j] = null;
                    }
                }
            }
        }
    }

    public void movePiecesRight() {
        for (int i = 0; i < Grid.LINES; i++) {
            for (int j = Grid.LINES_SIZE - 2; j >= 0; j--) {
                if (this.singlePieces[i][j] != null && !singlePieces[i][j].isFixed()) {
                    singlePieces[i][j + 1] = singlePieces[i][j];
                    singlePieces[i][j + 1].resetStopTime();
                    singlePieces[i][j] = null;
                }
            }
        }
    }

    public void setAllSquaresToBeStopped() {
        for (int i = Grid.LINES - 1; i >= 0; i--) {
            for (int j = 0; j < Grid.LINES_SIZE; j++) {
                if (singlePieces[i][j] != null && !singlePieces[i][j].isFixed() && !singlePieces[i][j].isStopping()) {
                    singlePieces[i][j].setStopping();
                }
            }
        }
    }

    public boolean canPlaceHere(Pentamimo tetromino, int locationX, int locationY, Pentamimo.Rotation rotation) {
        for (int i = 0; i < tetromino.getSize(); i++) {
            for (int j = 0; j < tetromino.getSize(); j++) {
                if (tetromino.getSquareAt(rotation, i, j) != null && singlePieces[locationY + i][locationX + j] != null && this.singlePieces[locationY + i][locationX + j].isFixed()) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * when we touch the 16-th place we draw that line in light grey color
     * so that the user could see number of used squares in the line.
     */

}
