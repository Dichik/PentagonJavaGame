package game.pieces;

import java.util.ArrayList;

public class Pentamimo {

    public static final ArrayList<Pentamimo> LIST = new ArrayList<>();

    public static final Pentamimo I;
    public static final Pentamimo J;

    static {
        I = new Pentamimo("orange", 4);
        I.setBlocks(Rotation.ROT0, 0, 1, 1, 1, 2, 1, 3, 1);
        I.setBlocks(Rotation.ROT90, 1, 0, 1, 1, 1, 2, 1, 3);
        I.setBlocks(Rotation.ROT180, 0, 2, 1, 2, 2, 2, 3, 2);
        I.setBlocks(Rotation.ROT270, 2, 0, 2, 1, 2, 2, 2, 3);
        J = new Pentamimo("orange", 3);
        J.setBlocks(Rotation.ROT0, 0, 0, 0, 1, 1, 1, 2, 1);
        J.setBlocks(Rotation.ROT90, 1, 0, 2, 0, 1, 1, 1, 2);
        J.setBlocks(Rotation.ROT180, 0, 1, 1, 1, 2, 1, 2, 2);
        J.setBlocks(Rotation.ROT270, 1, 0, 1, 1, 1, 2, 0, 2);

    }

    private int[][] blocksPositions;

    private String color;
    private int size;

    public Pentamimo(String color, int size) {
        this.blocksPositions = new int[4][size * 2];
        this.color = color;
        this.size = size;
        LIST.add(this);
    }

    public Square getSquareAt(Rotation currentRotation, int x, int y) {
        if ((this.blocksPositions[currentRotation.posInArray][0] == x && this.blocksPositions[currentRotation.posInArray][1] == y) ||
                (this.blocksPositions[currentRotation.posInArray][2] == x && this.blocksPositions[currentRotation.posInArray][3] == y) ||
                (this.blocksPositions[currentRotation.posInArray][4] == x && this.blocksPositions[currentRotation.posInArray][5] == y) ||
                (this.blocksPositions[currentRotation.posInArray][6] == x && this.blocksPositions[currentRotation.posInArray][7] == y)) {

            return new Square(color);
        } else {
            return null;
        }
    }

    private void setBlocks(Rotation rot, int... coords) {
        this.blocksPositions[rot.posInArray] = coords;
    }

    public int getSize() {
        return size;
    }

    public enum Rotation {
        ROT0(0),
        ROT90(1),
        ROT180(2),
        ROT270(3);

        private int posInArray;

        Rotation(int posInArray) {
            this.posInArray = posInArray;
        }

        public Rotation rotateClockwise() {
            if (posInArray > 0)
                return Rotation.values()[posInArray - 1];
            else
                return Rotation.values()[3];
        }

        public Rotation rotateCounterclockwise() {
            if (posInArray < 3)
                return Rotation.values()[posInArray + 1];
            else
                return Rotation.values()[0];
        }
    }
}
