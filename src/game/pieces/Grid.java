package game.pieces;

public class Grid {
    public static final int SIZE = 13;
    private Square[][] singlePieces;

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
                                this.singlePieces[i + 1][j].add("orange");
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
                                this.singlePieces[i - 1][j].add("orange");
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
                                this.singlePieces[i][j - 1].add("orange");
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
                                this.singlePieces[i][j + 1].add("orange");
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
                    }
                }
            }
        }
    }

    public boolean canSet() {
        for (int i = Grid.SIZE - 1; i >= 0; i--) {
            for (int j = 0; j < Grid.SIZE - 1; j++) {
                if (this.singlePieces[i][j] != null) {
                    if (!this.singlePieces[i][j].isFixed() && !this.singlePieces[i][j].isStopping()) {
                        if (i < Grid.SIZE - 2 && singlePieces[i + 1][j] != null)
                            if (singlePieces[i + 1][j].isFixed()
                                    && !singlePieces[i + 1][j].getColor().equals("blue"))
                                return false;
                        if (i > 0 && singlePieces[i - 1][j] != null)
                            if (singlePieces[i - 1][j].isFixed()
                                    && !singlePieces[i - 1][j].getColor().equals("blue"))
                                return false;
                        if (j < Grid.SIZE - 2 && singlePieces[i][j + 1] != null)
                            if (singlePieces[i][j + 1].isFixed()
                                    && !singlePieces[i][j + 1].getColor().equals("blue"))
                                return false;
                        if (j > 0 && singlePieces[i][j - 1] != null)
                            if (singlePieces[i][j - 1].isFixed()
                                    && !singlePieces[i][j - 1].getColor().equals("blue"))
                                return false;
                        if (i > 0 && j < Grid.SIZE - 2 && singlePieces[i - 1][j + 1] != null)
                            if (singlePieces[i - 1][j + 1].isFixed()
                                    && !singlePieces[i - 1][j + 1].getColor().equals("blue"))
                                return false;
                        if (i > 0 && j > 0 && singlePieces[i - 1][j - 1] != null)
                            if (singlePieces[i - 1][j - 1].isFixed()
                                    && !singlePieces[i - 1][j - 1].getColor().equals("blue"))
                                return false;
                        if (i < Grid.SIZE - 2 && j < Grid.SIZE - 2 && singlePieces[i + 1][j + 1] != null)
                            if (singlePieces[i + 1][j + 1].isFixed()
                                    && !singlePieces[i + 1][j + 1].getColor().equals("blue"))
                                return false;
                        if (j > 0 && i < Grid.SIZE - 2 && singlePieces[i + 1][j - 1] != null)
                            if (singlePieces[i + 1][j - 1].isFixed()
                                    && !singlePieces[i + 1][j - 1].getColor().equals("blue"))
                                return false;
                    }
                }
            }
        }
        return true;
    }

    public void removePentamimo() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (this.singlePieces[i][j] != null) {
                    if (!this.singlePieces[i][j].isFixed()
                            || this.singlePieces[i][j].hasAnotherColor()) {
                        if (!this.singlePieces[i][j].isFixed())
                            this.singlePieces[i][j] = null;
                        else this.singlePieces[i][j].removeColor();
                    }
                }
            }
        }
    }

    public boolean foundPlaceForPentamimo(Pentamimo pentamimo) {
        if (checkForRotation(pentamimo, Pentamimo.Rotation.ROT0))
            return true;
        if (checkForRotation(pentamimo, Pentamimo.Rotation.ROT90))
            return true;
        if (checkForRotation(pentamimo, Pentamimo.Rotation.ROT180))
            return true;
        if (checkForRotation(pentamimo, Pentamimo.Rotation.ROT270))
            return true;
        return false;
    }

    private boolean checkForRotation(Pentamimo pentamimo, Pentamimo.Rotation rotation) {
        int[][] blocks = pentamimo.getBlocksPositions();
//
//        int diffByY = maxY - minY + 1;
//        int diffByX = maxX - minX + 1;
//
//        int ROWS = Grid.SIZE - diffByX;
//        int COLUMNS = Grid.SIZE - diffByY;
//
//        for (int i = 0; i < ROWS - 2; i++) {
//            for (int j = 0; j < COLUMNS - 2; j++) {
//                boolean was = true;
//                for (int q = 0; q < 10; q += 2) {
//                    int coordinateX = blocks[rotation.getPosInArray()][q];
//                    int coordinateY = blocks[rotation.getPosInArray()][q + 1];
//                    if (this.singlePieces[coordinateY + i][coordinateX + j] == null) {
//                        if (i + coordinateY < ROWS && singlePieces[i + coordinateY + 1][j + coordinateX] != null)
//                            was = false;
//                        if (i + coordinateY > 0 && singlePieces[i + coordinateY - 1][j + coordinateX] != null)
//                            was = false;
//                        if (j + coordinateX < COLUMNS && singlePieces[i + coordinateY][j + coordinateX + 1] != null)
//                            was = false;
//                        if (j + coordinateX > 0 && singlePieces[i + coordinateY][j + coordinateX - 1] != null)
//                            was = false;
//                        if (i + coordinateY > 0 && j + coordinateX < COLUMNS
//                                && singlePieces[i + coordinateY - 1][j + coordinateX + 1] != null)
//                            was = false;
//                        if (i + coordinateY > 0 && j + coordinateX > 0
//                                && singlePieces[i + coordinateY - 1][j + coordinateX - 1] != null)
//                            was = false;
//                        if (i + coordinateY < ROWS && j + coordinateX < COLUMNS && singlePieces[i + coordinateY + 1][j + coordinateX + 1] != null)
//                            was = false;
//                        if (j + coordinateX > 0 && i + coordinateY < ROWS
//                                && singlePieces[i + coordinateY + 1][j + coordinateX - 1] != null)
//                            was = false;
//                    }
//                }
//                if (was) return true;
//            }
//        }

        /**
         * ??? has a bug, but where
         */
        for (int i = 1; i < SIZE - 1; i++) {
            for (int j = 0; j < SIZE - 1; j++) {
                boolean was = true;
                for (int q = 0; q < 10; q += 2) {
                    int coordinateX = blocks[rotation.getPosInArray()][q];
                    int coordinateY = blocks[rotation.getPosInArray()][q + 1];

                    System.out.println(coordinateX + " " + coordinateY);

                    if (coordinateX + j < SIZE - 2 && coordinateY + i < SIZE - 2
                            && this.singlePieces[coordinateY + i][coordinateX + j] == null) {
                        //add a case when there is a blocked square
                        if (i + coordinateY + 1 < SIZE - 2 && this.singlePieces[coordinateY + i + 1][coordinateX + j] != null)
                            was = false;
                        if (i + coordinateY - 1 >= 0 && this.singlePieces[coordinateY + i - 1][coordinateX + j] != null)
                            was = false;
                        if (coordinateX + j + 1 < SIZE - 2 && this.singlePieces[coordinateY + i][coordinateX + j + 1] != null)
                            was = false;
                        if (coordinateX + j - 1 >= 0 && this.singlePieces[coordinateY + i][coordinateX + j - 1] != null)
                            was = false;
                        if (coordinateY + i + 1 < SIZE - 2 && coordinateX + j - 1 >= 0
                                && this.singlePieces[coordinateY + i + 1][coordinateX + j - 1] != null)
                            was = false;
                        if (coordinateY + i + 1 < SIZE - 2 && coordinateX + j + 1 < SIZE - 2
                                && this.singlePieces[coordinateY + i + 1][coordinateX + j + 1] != null)
                            was = false;
                        if (coordinateY + i - 1 >= 0 && coordinateX + j - 1 >= 0
                                && this.singlePieces[coordinateY + i - 1][coordinateX + j - 1] != null)
                            was = false;
                        if (coordinateY + i - 1 >= 0 && coordinateX + j + 1 < SIZE - 2
                                && this.singlePieces[coordinateY + i - 1][coordinateX + j + 1] != null)
                            was = false;
                    }
                }
                if (was) {
                    //System.out.println(i + " " + j);
                    return true;
                }
            }
        }
        return false;
    }
/** !!!
 * when we touch the 16-th place we draw that line in light grey color
 * so that the user could see number of used squares in the line.
 */
}
